package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Person;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.mCtx;

public class RegisterActivity extends AppCompatActivity {
    Button btn;
   // EditText bDay;
    RadioGroup radioGroupGender;
    RadioButton radioButton;
    String local_id, ln, holdKeyPhone;
    TextInputLayout f_name,s_name, l_name, bDay;
    TextView textView, Fmale, Male, info;
    Typeface typeface;
    LoadingDialog loadingDialog;
    Alert alert;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        btn = findViewById(R.id.don_register);
        f_name = findViewById(R.id.f_name);
        s_name = findViewById(R.id.s_name);
        l_name = findViewById(R.id.l_name);
        bDay = findViewById(R.id.age);
        textView = findViewById(R.id.type_gender);
        Male = findViewById(R.id.male);
        Fmale = findViewById(R.id.female);
        info = findViewById(R.id.infoInsert);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        btn.setTypeface(typeface);
        f_name.getEditText().setTypeface(typeface);
        f_name.setTypeface(typeface);
        s_name.getEditText().setTypeface(typeface);
        s_name.setTypeface(typeface);
        l_name.getEditText().setTypeface(typeface);
        l_name.setTypeface(typeface);
        bDay.getEditText().setTypeface(typeface);
        bDay.setTypeface(typeface);
        textView.setTypeface(typeface);
        Male.setTypeface(typeface);
        Fmale.setTypeface(typeface);
        info.setTypeface(typeface);
        btn.setTypeface(typeface);
        radioGroupGender = findViewById(R.id.gender_radiogroup);
        loadingDialog = new LoadingDialog(this);
        alert = new Alert(this);
        local_id = getIntent().getStringExtra("local_id");
        ln = getIntent().getStringExtra("local_name");
        SharedPref.getInstance(RegisterActivity.this).storeUserLocal(ln);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPerson();
            }
        });
    }
    private void createPerson() {
        loadingDialog.startLoadingDialog();
        final String first_name = f_name.getEditText().getText().toString();
        final String second_name = s_name.getEditText().getText().toString();
        final String last_name = l_name.getEditText().getText().toString();
        final String age = bDay.getEditText().getText().toString();
        int selectedID = radioGroupGender.getCheckedRadioButtonId();
        radioButton = findViewById(selectedID);
        final String gender = (String) radioButton.getText();
        String message = getResources().getString(R.string.fill_field);

        if (first_name.isEmpty()) {
            f_name.setError(message);
            alert.showErrorDialog(message);
            loadingDialog.dismissDialog();
            return;
        }
        if (second_name.isEmpty()) {
            s_name.setError(message);
            alert.showErrorDialog(message);
            loadingDialog.dismissDialog();
            return;
        }
        if (last_name.isEmpty()) {
            l_name.setError(message);
            alert.showErrorDialog(message);
            loadingDialog.dismissDialog();
            return;

        }
       String ageText = bDay.getEditText().toString();

        if (age.isEmpty()) {
            bDay.setError(message);
            alert.showErrorDialog(message);
            loadingDialog.dismissDialog();
            return;

        }



        Person person = new Person(first_name, second_name, last_name, gender, age, local_id);
        loadingDialog.startLoadingDialog(false);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(TOKEN, "token");
        Call<Person> call = apiService.getUserRegi(token,person);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    //progressDoalog.dismiss();
                    String id_person = String.valueOf(response.body().getId());
                    String f_name = response.body().getFirst_name();
                    String s_name = response.body().getSecond_name();
                    String l_name = response.body().getLast_name();
                    String qr_code = response.body().getQr_code();
                    String gender = response.body().getGender();
                    String age = response.body().getAge();
                    String status = response.body().getStatus();

                    SharedPref.getInstance(RegisterActivity.this).storeUserID(id_person, f_name, s_name, l_name,qr_code, gender, age, status);
                    Intent intent = new Intent(RegisterActivity.this, DiseaseActivity.class);
                    startActivity(intent);
                    finish();

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
