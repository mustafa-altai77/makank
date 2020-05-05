package com.example.makank.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.GpsLocationTracker;
import com.example.makank.LoadingDialog;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Member;
import com.example.makank.data.model.Person;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.makank.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.STATUS;
import static com.example.makank.SharedPref.mCtx;


public class ContactActivity extends AppCompatActivity {
    ImageView buttonScan, imageStatus;
    EditText personalID, id_text;
    CircleImageView circleImageView;
    Button receive, check;
    IntentIntegrator qrScan;
    TextView status, firstNo, secondTwo;
    Person person;
    double locationLatitude;
    double locationLongitude;

    String TAG = "";
    Typeface typeface;
    LoadingDialog loadingDialog;
    Alert alert;
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        buttonScan = findViewById(R.id.qr_image);
        circleImageView = findViewById(R.id.image_status);
        personalID = findViewById(R.id.p_text);
        firstNo = findViewById(R.id.first);
        secondTwo = findViewById(R.id.second);
        receive = findViewById(R.id.receive);
        status = findViewById(R.id.status_id);
        check = findViewById(R.id.check_status);

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String my_id = sharedPreferences.getString(USER_ID, "id");
        final String my_status = sharedPreferences.getString(STATUS, "status");



        GpsLocationTracker mGpsLocationTracker = new GpsLocationTracker(ContactActivity.this);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        personalID.setTypeface(typeface);
        status.setTypeface(typeface);
        firstNo.setTypeface(typeface);
        secondTwo.setTypeface(typeface);
        check.setTypeface(typeface);
        receive.setTypeface(typeface);
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);
        Toast.makeText(this, "" + my_status, Toast.LENGTH_SHORT).show();
        if (my_status.equals("1")) {
            circleImageView.setBackgroundColor(R.color.colorAccent);
            status.setText("الحالة :مصاب ");
        } else if (my_status.equals("2")) {
            circleImageView.setBackgroundColor(R.color.yellow);
            status.setText("الحالة : مخالط");
        } else {
            circleImageView.setBackgroundColor(R.color.green);
            status.setText("الحالة : سليم");
        }
        if (mGpsLocationTracker.canGetLocation()) {
            locationLatitude = mGpsLocationTracker.getLatitude();
            locationLongitude = mGpsLocationTracker.getLongitude();
            Log.i(TAG, String.format("latitude: %s", locationLatitude));
            Log.i(TAG, String.format("longitude: %s", locationLongitude));
            Toast.makeText(this, locationLatitude + "" + locationLongitude + "", Toast.LENGTH_SHORT).show();
        } else {
            mGpsLocationTracker.showSettingsAlert();
        }
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


                String result = personalID.getText().toString();
                if (personalID.getText().toString().equals("")) {
                    //Toast.makeText(ContactActivity.this, "يرجى ادخال رقم التعريف الشخصي", Toast.LENGTH_SHORT).show();
                    alert.showAlertError("يرجى ادخال رقم التعريف ");
                    return;
                }
                if (personalID.getText().toString().equals(my_id)) {
                    //Toast.makeText(ContactActivity.this, "ادخال خاطئ", Toast.LENGTH_SHORT).show();
                    alert.showAlertError("ادخال خاطئ");
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
                alert.showAlertError("لا توجد نتيجة");
                // Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
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
       /*final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ContactActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");*/
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Member> call = apiService.addSeen(result, my_id, locationLatitude, locationLongitude);
       // progressDoalog.show();
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

                    String statu = response.body().getStatus();
                    Toast.makeText(ContactActivity.this, ""+statu, Toast.LENGTH_SHORT).show();

                   if (statu.equals("3")) {
                       CaseName="هذا الشخص سليم";
                    } else if (statu.equals("2")) {
                       CaseName="هذا الشخص مخالط";

                   } else if (statu.equals("1")) {
                       CaseName="هذا الشخص مصاب";

                   }
                    alert.showAlertSuccess(""+CaseName);
                }

            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
               // progressDoalog.dismiss();
                loadingDialog.dismissDialog();
                //Toast.makeText(ContactActivity.this, "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();
                alert.showAlertError("تــأكد من إتصالك بالإنترنت");

            }
        });
    }

}
