package com.example.makank.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.makank.GpsLocationTracker;
import com.example.makank.R;
import com.example.makank.data.ApiClient;
import com.example.makank.data.ApiInterface;
import com.example.makank.data.Local;
import com.example.makank.data.Member;
import com.example.makank.data.Person;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.USER_LOCAL;
import static com.example.makank.SharedPref.mCtx;

public class SendNotifActivity extends AppCompatActivity {
     EditText area,notifi;
     Button send;
     ImageView maerker;
    double Latitude;
    double Longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bell);
         notifi=findViewById(R.id.disc_status);
         area = findViewById(R.id.area_Edit);
         send = findViewById(R.id.send_notification);
        maerker = findViewById(R.id.location_image);
       maerker.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.M)
           @Override
           public void onClick(View v) {
               GpsLocationTracker mGpsLocationTracker = new GpsLocationTracker(SendNotifActivity.this);
               if (mGpsLocationTracker.canGetLocation())
               {
                   Latitude = mGpsLocationTracker.getLatitude();
                   Longitude = mGpsLocationTracker.getLongitude();
//                   Log.i(TAG, String.format("latitude: %s", Latitude));
//                   Log.i(TAG, String.format("longitude: %s",Longitude));
                   Toast.makeText(SendNotifActivity.this,Latitude+ ""+Longitude+ "", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   mGpsLocationTracker.showSettingsAlert();
               }
           }
       });
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
         String local = area.getText().toString();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notifi.getText().toString().equals("")) {
                    Toast.makeText(SendNotifActivity.this, "اوصف الحالة", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (area.getText().toString().equals("")) {
                    Toast.makeText(SendNotifActivity.this, "وصف المنطقة", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String my_id = sharedPreference.getString(USER_ID, "id");
                String notif = notifi.getText().toString();

                sendNotification(my_id,local,notif);
            }
        });
    }
    private void sendNotification(String my_id, String local ,String notif) {
       // Toast.makeText(SendNotifActivity.this, member_id+my_id+"", Toast.LENGTH_SHORT).show();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(SendNotifActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
            ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Person> call = apiService.sendNotifi(my_id,local,notif);
        progressDoalog.show();

        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();

                    Toast.makeText(SendNotifActivity.this, "don", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(SendNotifActivity.this, "خطاء في النظام الخارجي" , Toast.LENGTH_SHORT).show();

            }
        });
    }
}


