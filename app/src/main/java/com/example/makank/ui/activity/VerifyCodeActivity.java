package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.SmsListener;
import com.example.makank.SmsReceiver;
import com.example.makank.data.model.Details;
import com.example.makank.data.model.SendNumber;
import com.example.makank.data.model.Verify;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.ui.Test.TestActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.mCtx;

public class VerifyCodeActivity extends AppCompatActivity {
    PinEntryEditText editTextCode;
    LoadingDialog loadingDialog;
    TextView mTextField, message,title;
    Alert alert;
    Details details;
    private long time_left = 600000;
    private CountDownTimer countDownTimer;
    private boolean timeRunning;
    Typeface typeface;
    String storeMobile;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        editTextCode = findViewById(R.id.editTextCode);
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);
        mTextField = findViewById(R.id.edTimer);
        message = findViewById(R.id.messageWaite);
        title=findViewById(R.id.textView);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        editTextCode.setTypeface(typeface);
        mTextField.setTypeface(typeface);
        message.setTypeface(typeface);
        title.setTypeface(typeface);
       storeMobile = getIntent().getStringExtra("mobile");
        startStop();
        editTextCode.setFocusable(true);
        message.setText(getResources().getString(R.string.input_verify) + " " + storeMobile + " " + "خلال");
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                editTextCode.setText(messageText);
                Log.d("Text", messageText);
            }
        });


        if (editTextCode != null) {
            editTextCode.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    String code = editTextCode.getText().toString().trim();
                    if (code.isEmpty() || code.length() < 4) {
                        editTextCode.setError("Enter valid code");
                        editTextCode.requestFocus();
                        return;
                    }
                    //verifying the code entered manually
                    verifyVerificationCode(code);
                    //  stopTime();
                }
            });
        }

    }

    private void verifyVerificationCode(String code) {
        loadingDialog.startLoadingDialog(false);
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Verify> call = apiService.verfiy(code);
        call.enqueue(new Callback<Verify>() {
            @Override
            public void onResponse(Call<Verify> call, Response<Verify> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    //progressDoalog.dismiss();
                    String token = response.body().getToken();
                    SharedPref.getInstance(VerifyCodeActivity.this).storeToken("Bearer " + token);
                    getDetails();

                } else
                    // Toast.makeText(VerifyCodeActivity.this, response.errorBody() + "", Toast.LENGTH_SHORT).show();
                    alert.showErrorDialog(getResources().getString(R.string.error_code));
               // editTextCode.clearComposingText();
            }

            @Override
            public void onFailure(Call<Verify> call, Throwable t) {
                loadingDialog.dismissDialog();

                // alert.showWarningDialog();
            }
        });
    }

    public void startStop() {
        if (timeRunning) {
            stopTime();
        } else {
            startTime();
        }
    }

    public void startTime() {
        countDownTimer = new CountDownTimer(time_left, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left = millisUntilFinished;
                updateTime();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timeRunning = true;
    }

    public void stopTime() {
        countDownTimer.cancel();
        timeRunning = false;
    }

    public void updateTime() {
        int minutes = (int) time_left / 60000;
        int seconds = (int) time_left % 60000 /1000;
        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        mTextField.setText(timeLeftText);

    }

    private void getDetails(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(TOKEN, "token");
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);

        loadingDialog.startLoadingDialog();
        Call<Details> call = apiService.getMyData(token);
        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                loadingDialog.dismissDialog();
                    if(!response.isSuccessful()){
                        Intent intent = new Intent(VerifyCodeActivity.this, Areas.class);
                        startActivity(intent);
                        finish();
                    }else {
                            details = response.body();
                            int id_person = details.getId();
                            String f_name = details.getFirst_name();
                            String s_name = details.getSecond_name();
                            String l_name = details.getLast_name();
                            String qr_code = details.getQr_code();
                            String gender = details.getGender();
                            String age = details.getAge();
                            String status = details.getStatus();
                            SharedPref.getInstance(VerifyCodeActivity.this).storeUserID(String.valueOf(id_person), f_name, s_name, l_name, qr_code, gender, age, status);
                            // Toast.makeText(VerifyCodeActivity.this, f_name+"", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VerifyCodeActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }

            }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {
                loadingDialog.dismissDialog();
                alert.showWarningDialog();
            }
        });

    }
}
