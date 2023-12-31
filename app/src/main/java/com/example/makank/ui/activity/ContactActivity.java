package com.example.makank.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.GpsLocationTracker;
import com.example.makank.LoadingDialog;
import com.example.makank.Scanner;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Member;
import com.example.makank.data.model.Person;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.makank.R;
import com.journeyapps.barcodescanner.CaptureActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;


public class ContactActivity extends AppCompatActivity {
    ImageView buttonScan;
    TextView personal_id, info1, info2, Pname;
    // EditText personalID;
    Button add;
    Button check, recive;
    IntentIntegrator qrScan;
    LinearLayout layout;
    Typeface typeface;
    LoadingDialog loadingDialog;
    Alert alert;
    Person personList;
    double Latitude;
    double Longitude;
    String TAG = "";
    int p_id;
    String holdQr;
    CircleImageView statusImage;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        GpsLocationTracker mGpsLocationTracker = new GpsLocationTracker(ContactActivity.this);

        buttonScan = findViewById(R.id.qr_image);
        // personalID = findViewById(R.id.p_id);
        personal_id = findViewById(R.id.p_number);
        info1 = findViewById(R.id.infor);
        info2 = findViewById(R.id.infor2);
        Pname = findViewById(R.id.p_name);
        add = findViewById(R.id.add);
        check = findViewById(R.id.check_status);
        recive = findViewById(R.id.cancel);
        statusImage = findViewById(R.id.img_status);

        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        // personalID.setTypeface(typeface);
        personal_id.setTypeface(typeface);
        info1.setTypeface(typeface);
        info2.setTypeface(typeface);
        Pname.setTypeface(typeface);
        add.setTypeface(typeface);
        check.setTypeface(typeface);
        recive.setTypeface(typeface);

        if (mGpsLocationTracker.canGetLocation()) {
            Latitude = mGpsLocationTracker.getLatitude();
            Longitude = mGpsLocationTracker.getLongitude();
            Log.i(TAG, String.format("latitude: %s", Latitude));
            Log.i(TAG, String.format("longitude: %s", Longitude));
        } else {
            mGpsLocationTracker.showSettingsAlert();
        }


        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);

        layout = findViewById(R.id.anotherPerson);
        layout.setVisibility(View.INVISIBLE);
        //intializing scan object
        qrScan = new IntentIntegrator(this);
        recive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, QrCodeActivity.class);
                startActivity(intent);
            }
        });
        //attaching onclick listener
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, Scanner.class);
                startActivityForResult(intent, 1);
            }
        });
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, Scanner.class);
                startActivityForResult(intent, 1);
            }
        });
       /* check.setOnClickListener(new View.OnClickListener() {

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
                //String result = personalID.getText().toString();
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
                addSee(my_id, Longitude, Latitude);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String result="";
//        if (result== null) {
//            //   Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
//            alert.showErrorDialog(getResources().getString(R.string.no_result));
//
//        } else
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

        Call<Person> call = apiService.getSaw(token, result);
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
//                        personal_id.setText(Integer.toString(p_id));


                        final String statu = response.body().getStatus();
                        String name = response.body().getFirst_name() + " " + response.body().getSecond_name() + " " + response.body().getLast_name();

                        if (statu.equals("3")) {
                            statusImage.setImageResource(R.color.green);
                            CaseName = getResources().getString(R.string.healthy_case);
                        } else if (statu.equals("2")) {
                            statusImage.setImageResource(R.color.yellow);
                            CaseName = getResources().getString(R.string.suspicious_case);

                        } else if (statu.equals("1")) {
                            statusImage.setImageResource(R.color.colorAccent);
                            CaseName = getResources().getString(R.string.sufferer_case);
                            ;

                        }
                        //  alert.showAlertSuccess(name+"\n"+""+CaseName);
                        //  alert.showSuccessDialog("تم البحث",name + " \n "+CaseName,1);
                        personal_id.setText(CaseName);
                    }
                    //Intent intent = new Intent(RegisterActivity.this, DiseaseActivity.class);
                    //startActivity(intent);
                    //finish();
                } else {
                    alert.showErrorDialog(getResources().getString(R.string.this_number_not_found));
                    loadingDialog.dismissDialog();
                    //personalID.setText("");
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

    private void addSee(String my_id, double locationLatitude, double locationLongitude) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(TOKEN, "token");
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Member> call = apiService.addSeen(token, my_id, p_id, locationLatitude, locationLongitude);
        loadingDialog.startLoadingDialog();
        call.enqueue(new Callback<Member>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                String CaseName = null;
                if (response.isSuccessful()) {
                    //  progressDoalog.dismiss();
                    loadingDialog.dismissDialog();
                    //Toast.makeText(ContactActivity.this, "done", Toast.LENGTH_SHORT).show();
//                    final String statu = response.body().getStatus();
//                    String name = response.body().getFirst_name() + " " + response.body().getSecond_name() + " " + response.body().getLast_name();
//
//                    if (statu.equals("3")) {
//                        CaseName = getResources().getString(R.string.healthy_case);
//                    } else if (statu.equals("2")) {
//                        CaseName = getResources().getString(R.string.suspicious_case);
//
//                    } else if (statu.equals("1")) {
//                        CaseName = getResources().getString(R.string.sufferer_case);;
//
//                    }
                    //  alert.showAlertSuccess(name+"\n"+""+CaseName);
                    alert.showSuccessDialog(getResources().getString(R.string.contact_screen), getResources().getString(R.string.success_contact), 1);
                    // alert.showAlertInTest("تم التواصل بنجاح", name + "\n" + " - " + CaseName, "موافق");
                } else {

                    // alert.showAlertError("هذا الرقم لايــوجد");
                    alert.showErrorDialog(getResources().getString(R.string.this_number_not_found));
                    loadingDialog.dismissDialog();
                    // personalID.setText("");
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                loadingDialog.dismissDialog();
                //Toast.makeText(ContactActivity.this, "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();
                // alert.showAlertError("تــأكد من إتصالك بالإنترنت");
                alert.showWarningDialog();

            }
        });

    }
}
