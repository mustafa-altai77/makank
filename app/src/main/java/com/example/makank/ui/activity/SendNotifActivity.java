package com.example.makank.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.GpsLocationTracker;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Person;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.PHONE;
import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;

public class SendNotifActivity extends AppCompatActivity {
    EditText area, notifi;
    Button send;
    ImageView maerker;
    double Latitude;
    double Longitude;
    Typeface typeface;
    LoadingDialog loadingDialog;
    Alert alert;
    TextView infoNot, editTextMobile;
    Toolbar toolbar;
    ImageView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_bell);
        notifi = findViewById(R.id.disc_status);
        area = findViewById(R.id.area_Edit);
        editTextMobile = findViewById(R.id.number);
        edit=findViewById(R.id.image_event);

        send = findViewById(R.id.send_notification);
        infoNot = findViewById(R.id.infoNoti);
        //LocationId = findViewById(R.id.LocationID);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String phoneNum = sharedPreference.getString(PHONE, "phone");
        editTextMobile.setText(phoneNum);
        editTextMobile.setEnabled(false);
        notifi.setTypeface(typeface);
        send.setTypeface(typeface);
        area.setTypeface(typeface);
        infoNot.setTypeface(typeface);
//        LocationId.setTypeface(typeface);
        loadingDialog = new LoadingDialog(this);
        alert = new Alert(this);
//        maerker.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                GpsLocationTracker mGpsLocationTracker = new GpsLocationTracker(SendNotifActivity.this);
//                if (mGpsLocationTracker.canGetLocation()) {
//                    Latitude = mGpsLocationTracker.getLatitude();
//                    Longitude = mGpsLocationTracker.getLongitude();
////                   Log.i(TAG, String.format("latitude: %s", Latitude));
////                   Log.i(TAG, String.format("longitude: %s",Longitude));
//                    //  Toast.makeText(SendNotifActivity.this, Latitude + "" + Longitude + "", Toast.LENGTH_SHORT).show();
//                } else {
//                    mGpsLocationTracker.showSettingsAlert();
//                }
//            }
//        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextMobile.setEnabled(true);

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notifi.getText().toString().equals("")) {
                    //Toast.makeText(SendNotifActivity.this, "اوصف الحالة", Toast.LENGTH_SHORT).show();
                    alert.showErrorDialog(getResources().getString(R.string.disc_case));
                    return;
                }
                if (area.getText().toString().equals("")) {
                    // Toast.makeText(SendNotifActivity.this, "وصف المنطقة", Toast.LENGTH_SHORT).show();
                    alert.showErrorDialog(getResources().getString(R.string.disc_area));
                    return;
                }
                String mobile = editTextMobile.getText().toString();
                if(mobile.isEmpty() || mobile.length() < 10){
//                    editTextMobile.setErrorEnabled(true);
                    editTextMobile.setError(getResources().getString(R.string.error_phone_number));
                    editTextMobile.requestFocus();
                    SpannableString efr = new SpannableString(getResources().getString(R.string.error_phone_number));
                    Toast toast = Toast.makeText(SendNotifActivity.this,efr, Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.TRANSPARENT);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
                    text.setTextColor(Color.RED);
                    text.setTextSize(Integer.valueOf(20));
                    if (text != null) text.setGravity(Gravity.CENTER);
                    toast.show();
                    return;
                }
                final String my_id = sharedPreference.getString(USER_ID, "id");
                String notif = notifi.getText().toString();
                String local = area.getText().toString();

                sendNotification(my_id, local, notif,mobile);
            }
        });
    }

    private void sendNotification(String my_id, String local, String notif,String phone) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(TOKEN, "token");
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Person> call = apiService.sendNotifi(token,my_id, local,notif,phone);
        // progressDoalog.show();
        loadingDialog.startLoadingDialog();
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                if (response.isSuccessful()) {
                    loadingDialog.dismissDialog();
                    alert.showSuccessDialog(getResources().getString(R.string.success_notification),getResources().getString(R.string.success_approve_notification),1);
                }
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                loadingDialog.dismissDialog();
               alert.showWarningDialog();
            }
        });
    }
}
