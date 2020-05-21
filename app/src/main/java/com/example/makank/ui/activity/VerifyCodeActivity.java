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
import com.example.makank.data.model.SendNumber;
import com.example.makank.data.model.Verify;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCodeActivity extends AppCompatActivity {
     EditText editTextCode;
    LoadingDialog loadingDialog;
    Alert alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        editTextCode = findViewById(R.id.editTextCode);
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);
        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 4) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);
            }
        });
    }

    private void verifyVerificationCode(String code) {
        loadingDialog.startLoadingDialog();
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Verify> call = apiService.verfiy(code);
        call.enqueue(new Callback<Verify>() {
            @Override
            public void onResponse(Call<Verify> call, Response<Verify> response) {

                if (response.isSuccessful()) {
                    //progressDoalog.dismiss();
                    loadingDialog.dismissDialog();
                    String token = response.body().getToken();
                    Toast.makeText(VerifyCodeActivity.this, token+"", Toast.LENGTH_SHORT).show();
                    SharedPref.getInstance(VerifyCodeActivity.this).storeToken("Bearer "+token);

                    Intent intent = new Intent(VerifyCodeActivity.this, StateActivity.class);
                    startActivity(intent);
                    finish();

                }
                else
                    Toast.makeText(VerifyCodeActivity.this,response.errorBody()+ "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Verify> call, Throwable t) {
                loadingDialog.dismissDialog();

                // alert.showWarningDialog();
            }
        });
    }
}
