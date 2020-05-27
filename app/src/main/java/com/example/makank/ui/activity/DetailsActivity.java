package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.data.model.Details;
import com.example.makank.data.model.Person;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.PHONE;
import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.mCtx;

public class DetailsActivity extends AppCompatActivity {
    private Details personList;
    LoadingDialog loadingDialog;
    Alert alert;
    TextView F_name, gen, age_;
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        F_name = findViewById(R.id.f_na);
        gen = findViewById(R.id.gender_pref);
        start =findViewById(R.id.start_home);
        age_ = findViewById(R.id.age_prf);
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(TOKEN, "token");
        loadingDialog.startLoadingDialog();
        Call<Details> call = apiService.getMyData(token);
        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    if(response.body().toString().isEmpty()) {
                        Intent intent = new Intent(DetailsActivity.this, Areas.class);
                        startActivity(intent);
                        finish();
                    }else {
                        int id_person = response.body().getId();
                        String f_name = response.body().getFirst_name();
                        String s_name = response.body().getSecond_name();
                        String l_name = response.body().getLast_name();
                        String qr_code = response.body().getQr_code();
                        String gender = response.body().getGender();
                        String age = response.body().getAge();
                        String status = response.body().getStatus();

                        SharedPref.getInstance(DetailsActivity.this).storeUserID(String.valueOf(id_person), f_name, s_name, l_name, qr_code, gender, age, status);
                        Toast.makeText(DetailsActivity.this, f_name+"", Toast.LENGTH_SHORT).show();

                        F_name.setText("" + f_name + " " + s_name + " " + l_name);
                        gen.setText(gender);
                        age_.setText(age);
                    }

                }

            }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {
                loadingDialog.dismissDialog();
                alert.showWarningDialog();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
