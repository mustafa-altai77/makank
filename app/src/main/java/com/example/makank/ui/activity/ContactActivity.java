package com.example.makank.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.GpsLocationTracker;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Member;
import com.example.makank.data.model.Person;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.makank.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;


public class ContactActivity extends AppCompatActivity {
    ImageView buttonScan, imageStatus;
    EditText personalID, id_text;
    Button receive, check;
    IntentIntegrator qrScan;
    TextView status;
    Person person;
    double locationLatitude;
    double locationLongitude;

    String TAG = "";

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        buttonScan = findViewById(R.id.qr_image);
        imageStatus = findViewById(R.id.image_status);
        personalID = findViewById(R.id.p_text);
        receive = findViewById(R.id.receive);
        status = findViewById(R.id.status_id);
        check = findViewById(R.id.check_status);

        GpsLocationTracker mGpsLocationTracker = new GpsLocationTracker(ContactActivity.this);
        if (mGpsLocationTracker.canGetLocation()) {
            locationLatitude = mGpsLocationTracker.getLatitude();
            locationLongitude = mGpsLocationTracker.getLongitude();
            Log.i(TAG, String.format("latitude: %s", locationLatitude));
            Log.i(TAG, String.format("longitude: %s", locationLongitude));
            Toast.makeText(this, locationLatitude + "" + locationLongitude + "", Toast.LENGTH_SHORT).show();
        } else {
            mGpsLocationTracker.showSettingsAlert();
        }

        //intializing scan object
        qrScan = new IntentIntegrator(this);

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
                SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String my_id = sharedPreferences.getString(USER_ID, "id");

                String result = personalID.getText().toString();
                if (personalID.getText().toString().equals("")) {
                    Toast.makeText(ContactActivity.this, "يرجى ادخال رقم التعريف الشخصي", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (personalID.getText().toString().equals(my_id)) {
                    Toast.makeText(ContactActivity.this, "ادخال خاطئ", Toast.LENGTH_SHORT).show();
                } else
                    //else
                    addSee(result, my_id, locationLatitude, locationLongitude);
            }
        });
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, QrCodeActivity.class);
                startActivity(intent);
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
                SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                final String my_id = sharedPreferences.getString(USER_ID, "id");
                addSee(result.getContents(), my_id, locationLatitude, locationLongitude);
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    personalID.setText(obj.getString(String.valueOf(requestCode)));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    personalID.setText(result.getContents());

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void addSee(String result, String my_id, double locationLatitude, double locationLongitude) {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ContactActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Member> call = apiService.addSeen(result, my_id, locationLatitude, locationLongitude);
        progressDoalog.show();
        call.enqueue(new Callback<Member>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();

                    Toast.makeText(ContactActivity.this, "done", Toast.LENGTH_SHORT).show();
                    String statu = response.body().getStatus();
                    if (statu.equals("3")) {
                        status.setText("سليم");
                        imageStatus.setBackgroundColor(R.color.green);
                    } else if (statu.equals("2")) {
                        status.setText("مخالط");
                        imageStatus.setBackgroundColor(R.color.yellow);
                    } else if (statu.equals("1")) {
                        status.setText("مصاب");
                        imageStatus.setBackgroundColor(R.color.colorAccent);
                    }
                }

            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(ContactActivity.this, "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
