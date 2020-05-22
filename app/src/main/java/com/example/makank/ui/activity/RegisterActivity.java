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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.mCtx;

public class RegisterActivity extends AppCompatActivity {
    Button btn;
    EditText bDay;
    RadioGroup radioGroupGender;
    RadioButton radioButton;
    String local_id, ln, holdKeyPhone;
    EditText f_name, s_name, l_name, phone, editPhone;
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
//        phone = findViewById(R.id.phone);
//        editPhone = findViewById(R.id.edit_phone);
        bDay = findViewById(R.id.age);
        textView = findViewById(R.id.type_gender);
        Male = findViewById(R.id.male);
        Fmale = findViewById(R.id.female);
        info = findViewById(R.id.infoInsert);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        btn.setTypeface(typeface);
        f_name.setTypeface(typeface);
        s_name.setTypeface(typeface);
        l_name.setTypeface(typeface);
//        phone.setTypeface(typeface);
        bDay.setTypeface(typeface);
        textView.setTypeface(typeface);
        Male.setTypeface(typeface);
        Fmale.setTypeface(typeface);
        info.setTypeface(typeface);
        btn.setTypeface(typeface);
//        editPhone.setText(holdKeyPhone);
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
        final String first_name = f_name.getText().toString().trim();
        final String second_name = s_name.getText().toString().trim();
        final String last_name = l_name.getText().toString().trim();
//        final String phone_number = phone.getText().toString().trim();
        final String age = bDay.getText().toString().trim();
        int selectedID = radioGroupGender.getCheckedRadioButtonId();
        radioButton = findViewById(selectedID);
        final String gender = (String) radioButton.getText();
////        int length = phone_number.length();
        String message = getResources().getString(R.string.fill_field);

        if (TextUtils.isEmpty(first_name)) {
            alert.showErrorDialog(message);
            loadingDialog.dismissDialog();
            return;
        }
        if (TextUtils.isEmpty(second_name)) {
            alert.showErrorDialog(message);
            loadingDialog.dismissDialog();
            return;
        }
        if (TextUtils.isEmpty(last_name)) {
            alert.showErrorDialog(message);
            loadingDialog.dismissDialog();
            return;

        }
//        if (TextUtils.isEmpty(phone_number)) {
//            alert.showErrorDialog(message);
//            loadingDialog.dismissDialog();
//            return;
//
//        }
//        String ageText = bDay.getText().toString();

        if (TextUtils.isEmpty(age)) {
            alert.showErrorDialog(message);
            loadingDialog.dismissDialog();
            return;

        }

//        if (length <= 8 || phone_number.startsWith("0") ||phone_number.startsWith("3")||phone_number.startsWith("4")
//                |phone_number.startsWith("5")|phone_number.startsWith("6") |phone_number.startsWith("7")
//                |phone_number.startsWith("8") ||phone_number.startsWith("2") || phone_number.contains("#") || phone_number.contains(")")
//                || phone_number.contains("(") || phone_number.contains("*") || phone_number.contains(",")
//                || phone_number.contains(";") || phone_number.contains("-") || phone_number.contains("N")
//                || phone_number.contains("+") || phone_number.contains("/") || phone_number.contains(".")
//                || phone_number.contains(" ")) {
//            alert.showErrorDialog(getResources().getString(R.string.length_phone));
//            loadingDialog.dismissDialog();
//            return;
//        }

        Person person = new Person(first_name, second_name, last_name, gender, age, local_id);
        // progressDoalog.show();
        loadingDialog.startLoadingDialog(false);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(TOKEN, "token");
        Call<Person> call = apiService.getUserRegi(token,person);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                if (response.isSuccessful()) {
                    //progressDoalog.dismiss();
                    loadingDialog.dismissDialog();

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
