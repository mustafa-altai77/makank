package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.Scanner;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Member;
import com.example.makank.data.model.Person;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;

public class AddGroupActivity extends AppCompatActivity {
    ImageView buttonScan;
    TextView  info1, info2, Pname,personal_id;
   // EditText personalID;
    Button add, cancel,check;
    IntentIntegrator qrScan;
    LinearLayout layout;
    Typeface typeface;
    LoadingDialog loadingDialog;
    Alert alert;
    Person personList;
    int p_id;
    String holdQr;
    CircleImageView statusImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        buttonScan = findViewById(R.id.qr_image);
     //   personalID = findViewById(R.id.p_id);
        //personal_id = findViewById(R.id.p_number);

        info1 = findViewById(R.id.infor);
        info2 = findViewById(R.id.infor2);
        Pname = findViewById(R.id.p_name);
        add = findViewById(R.id.add);
       check = findViewById(R.id.check_status);
        cancel = findViewById(R.id.cancel);
        statusImage = findViewById(R.id.img_status);
        personal_id = findViewById(R.id.p_number);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        //personalID.setTypeface(typeface);
        //personal_id.setTypeface(typeface);
        personal_id.setTypeface(typeface);
        info1.setTypeface(typeface);
        info2.setTypeface(typeface);
        Pname.setTypeface(typeface);
        add.setTypeface(typeface);
        check.setTypeface(typeface);
        cancel.setTypeface(typeface);
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);

        layout = findViewById(R.id.anotherPerson);
        layout.setVisibility(View.INVISIBLE);



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //attaching onclick listener
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGroupActivity.this, Scanner.class);
                startActivityForResult(intent, 1);
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //qrScan.setCaptureActivity(QrCodeS.class);
              //  qrScan.initiateScan();
                Intent intent = new Intent(AddGroupActivity.this, Scanner.class);
                startActivityForResult(intent, 1);
            }
        });
      /*  check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                if (TextUtils.isEmpty(id))
                String id = personalID.getText().toString();
                //else
                if (personalID.getText().toString().equals(""))
                    //  Toast.makeText(AddGroupActivity.this, "يرجى ادخال رقم التعريف الشخصي", Toast.LENGTH_SHORT).show();
                    alert.showErrorDialog(getResources().getString(R.string.place_enter_id));
                else

                    getStatus(id);

            }
        });*/
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String member_id = personalID.getText().toString();
                SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String my_id = sharedPreferences.getString(USER_ID, "id");
               /* if (personalID.getText().toString().equals("")) {
                    alert.showErrorDialog(getResources().getString(R.string.place_enter_id));
                    return;
                } else if (personalID.getText().toString().equals(my_id)) {
                    alert.showErrorDialog(getResources().getString(R.string.input_error));
                    personalID.setText("");
                    return;
                } else*/
                    addMember(my_id);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result=null;

       // IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (requestCode == 1)
                try {
                    result = data.getStringExtra("qrCode");
                    if (result != null) {

                        //if qrcode has nothing in it

                        layout.setVisibility(View.VISIBLE);
                        //if qr contains data
                        try {
                            //converting the data to json
                            JSONObject obj = new JSONObject(result);
                            //setting values to textviews
                            //  personalID.setText(obj.getString(String.valueOf(requestCode)));
                            holdQr = obj.getString(String.valueOf(requestCode));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                            //  personalID.setText(result.getContents());
                            holdQr = result;
//                    getStatus(result.getContents());
                            getStatus(result);
                        }

                    } else {
                        super.onActivityResult(requestCode, resultCode, data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    alert.showErrorDialog(getResources().getString(R.string.no_result));

                }
    }

    private void getStatus(String result) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(TOKEN, "token");
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);

        Call<Person> call = apiService.getSaw(token,result);
        //   progressDoalog.show();
        loadingDialog.startLoadingDialog();
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                String CaseName = null;
                if (response.isSuccessful()) {
                    //  progressDoalog.dismiss();
                    loadingDialog.dismissDialog();
                    //   Toast.makeText(AddGroupActivity.this, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();
                    layout.setVisibility(View.VISIBLE);
                    if (response.code() == 200) {
                        personList = response.body();
                        final String f_name = personList.getFirst_name();
                        final String s_name = personList.getSecond_name();
                        final String l_name = personList.getLast_name();
                        p_id = personList.getId();

                        Pname.setText("" + f_name + " " + s_name + " " + l_name);
                        final String statu = response.body().getStatus();
                        if (statu.equals("3")) {
                            statusImage.setImageResource(R.color.green);
                            CaseName = getResources().getString(R.string.healthy_case);
                        } else if (statu.equals("2")) {
                            CaseName = getResources().getString(R.string.suspicious_case);
                            statusImage.setImageResource(R.color.yellow);

                        } else if (statu.equals("1")) {
                            CaseName = getResources().getString(R.string.sufferer_case);
                            statusImage.setImageResource(R.color.colorAccent);
                            ;

                        }
                        personal_id.setText(CaseName);
                       // personal_id.setText(Integer.toString(p_id));
                    }
                    //Intent intent = new Intent(RegisterActivity.this, DiseaseActivity.class);
                    //startActivity(intent);
                    //finish();
                } else {
                    alert.showErrorDialog(getResources().getString(R.string.this_number_not_found));
                    loadingDialog.dismissDialog();
                   // personalID.setText("");
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                // progressDoalog.dismiss();
                loadingDialog.dismissDialog();
                //  Toast.makeText(AddGroupActivity.this, "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();
               alert.showWarningDialog();
            }
        });
    }

    private void addMember(String my_id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(TOKEN, "token");
       /* final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(AddGroupActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");*/
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Member> call = apiService.addMem(token,my_id, p_id);
        // progressDoalog.show();
        loadingDialog.startLoadingDialog();
        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {

                if (response.isSuccessful()) {
                    //progressDoalog.dismiss();
                    loadingDialog.dismissDialog();

                    String id_person = String.valueOf(response.body().getId());
                    //  Toast.makeText(AddGroupActivity.this, "don", Toast.LENGTH_SHORT).show();
                    //alert.showAlertSuccess("تمت الإضافة");
                    alert.showSuccessDialog(getResources().getString(R.string.add_member),getResources().getString(R.string.requested_done),1);
                    //personalID.setText("");

                } else
                    alert.showErrorDialog(getResources().getString(R.string.cannot_add_this_person));
                //personalID.setText("");

            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                //   progressDoalog.dismiss();
                loadingDialog.dismissDialog();
                alert.showSuccessDialog(getResources().getString(R.string.add_member),getResources().getString(R.string.requested_done),1);
               // personalID.setText("");

                //Toast.makeText(AddGroupActivity.this, "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();
                //alert.showAlertError("الرجاء التأكد من إتصالك بالإنترنت");

            }
        });
    }
    class capture extends CaptureActivity
    {

    }
}

