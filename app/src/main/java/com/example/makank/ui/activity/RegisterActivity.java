package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.data.ApiClient;
import com.example.makank.data.ApiInterface;
import com.example.makank.data.Person;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button btn;
    EditText bDay;
    RadioGroup radioGroupGender;
    RadioButton radioButton;
    String local_id , ln;
    EditText f_name, s_name, l_name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn = findViewById(R.id.don_register);
        f_name = findViewById(R.id.f_name);
        s_name = findViewById(R.id.s_name);
        l_name = findViewById(R.id.l_name);
        phone = findViewById(R.id.phone);
        bDay = findViewById(R.id.age);

        radioGroupGender = findViewById(R.id.gender_radiogroup);

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

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(RegisterActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
        final String first_name = f_name.getText().toString();
        final String second_name = s_name.getText().toString();
        final String last_name = l_name.getText().toString();
        final String phone_number = phone.getText().toString();
        final String age = bDay.getText().toString();
        int selectedID = radioGroupGender.getCheckedRadioButtonId();
        radioButton = findViewById(selectedID);
        final String gender = (String) radioButton.getText();

        if (TextUtils.isEmpty(first_name)) {
            Toast.makeText(getApplicationContext(), "يجب ملئ جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(second_name)) {
            Toast.makeText(getApplicationContext(), "يجب ملئ جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(last_name)) {

            Toast.makeText(getApplicationContext(), "يجب ملئ جميع الحقول", Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(phone_number)) {
            Toast.makeText(getApplicationContext(), "يجب ملئ جميع الحقول", Toast.LENGTH_SHORT).show();
            return;

        }
//        String ageText = bDay.getText().toString();

        if (TextUtils.isEmpty(age)) {
            Toast.makeText(getApplicationContext(), "يجب ملئ جميع الحقول", Toast.LENGTH_SHORT).show();
            return;

        }
        Person person = new Person(first_name, second_name, last_name, phone_number, gender, age, local_id);
        progressDoalog.show();


        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Person> call = apiService.getUserRegi(person);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();

                    String id_person = String.valueOf(response.body().getId());
                    String f_name = response.body().getFirst_name();
                    String s_name = response.body().getSecond_name();
                    String l_name = response.body().getLast_name();
                    String phone = response.body().getPhone();
                    String gender = response.body().getGender();
                    String age = response.body().getAge();
                    String status = response.body().getStatus();

                    Toast.makeText(RegisterActivity.this, id_person+"", Toast.LENGTH_SHORT).show();

                    SharedPref.getInstance(RegisterActivity.this).storeUserID(id_person,f_name,s_name,l_name,phone,gender,age,status);
                    Intent intent = new Intent(RegisterActivity.this, DiseaseActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(RegisterActivity.this, "done", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(RegisterActivity.this, "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }
}

