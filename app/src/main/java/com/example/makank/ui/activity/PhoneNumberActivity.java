package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.data.model.Person;
import com.example.makank.data.model.SendNumber;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneNumberActivity extends AppCompatActivity {
    private EditText editTextMobile;
    LoadingDialog loadingDialog;
    Alert alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);
        setContentView(R.layout.activity_phone_number);
        editTextMobile = findViewById(R.id.editTextMobile);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }
             sendNumber(mobile);
            }
        });
    }

    private void sendNumber(String mobile) {
        loadingDialog.startLoadingDialog();
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<SendNumber> call = apiService.sendNum(mobile);
        call.enqueue(new Callback<SendNumber>() {
            @Override
            public void onResponse(Call<SendNumber> call, Response<SendNumber> response) {

                if (response.isSuccessful()) {
                    //progressDoalog.dismiss();
                    loadingDialog.dismissDialog();
                    String mesag = response.body().getMessage();
                    Toast.makeText(PhoneNumberActivity.this, mesag+"", Toast.LENGTH_SHORT).show();
                    SharedPref.getInstance(PhoneNumberActivity.this).storeNumber(mobile);

                    Intent intent = new Intent(PhoneNumberActivity.this, VerifyCodeActivity.class);
                    startActivity(intent);
                    finish();

                }
                else
                    Toast.makeText(PhoneNumberActivity.this,response.errorBody()+ "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SendNumber> call, Throwable t) {
                loadingDialog.dismissDialog();

               // alert.showWarningDialog();
            }
        });
    }

}

