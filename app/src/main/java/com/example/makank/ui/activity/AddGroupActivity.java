package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.R;
import com.example.makank.data.ApiClient;
import com.example.makank.data.ApiInterface;
import com.example.makank.data.Member;
import com.example.makank.data.Person;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;

public class AddGroupActivity extends AppCompatActivity {
    ImageView buttonScan;
    TextView personal_id;
    EditText personalID;
    Button add ,check,cancel;
    IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        buttonScan = findViewById(R.id.qr_image);
        personalID = findViewById(R.id.p_id);
        personal_id =findViewById(R.id.p_number);
        add = findViewById(R.id.add);
        check = findViewById(R.id.check_status);
        cancel = findViewById(R.id.cancel);

        //intializing scan object
        qrScan = new IntentIntegrator(this);
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
                qrScan.initiateScan();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                if (TextUtils.isEmpty(id))
                String id = personalID.getText().toString();
                //else
                if (personalID.getText().toString().equals(""))
                    Toast.makeText(AddGroupActivity.this, "يرجى ادخال رقم التعريف الشخصي", Toast.LENGTH_SHORT).show();
                else
                getStatus(id);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String member_id = personalID.getText().toString();
                SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String my_id = sharedPreferences.getString(USER_ID, "id");
                if (personalID.getText().toString().equals("")) {
                    Toast.makeText(AddGroupActivity.this, "يرجى ادخال رقم التعريف الشخصي", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                if (personalID.getText().toString().equals(my_id)) {
                    Toast.makeText(AddGroupActivity.this, "ادخال خاطئ", Toast.LENGTH_SHORT).show();
                    return;
                }else
                addMember(member_id,my_id);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    personalID.setText(obj.getString(String.valueOf(requestCode)));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    personalID.setText(result.getContents());
//                    getStatus(result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void getStatus(String result){
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(AddGroupActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Person> call = apiService.getSaw(result);
        progressDoalog.show();
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();

                    Toast.makeText(AddGroupActivity.this, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();
                    if (response.code()==200){
                        return;
                    }
                    //Intent intent = new Intent(RegisterActivity.this, DiseaseActivity.class);
                    //startActivity(intent);
                    //finish();
                }

            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(AddGroupActivity.this, "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addMember(String member_id, String my_id) {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(AddGroupActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Member> call = apiService.addMem(my_id,member_id);
        progressDoalog.show();

        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();

                    String id_person = String.valueOf(response.body().getId());
                    Toast.makeText(AddGroupActivity.this, "don", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(AddGroupActivity.this, "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
