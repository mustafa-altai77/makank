package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.data.model.Person;
import com.example.makank.data.model.SendNumber;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneNumberActivity extends AppCompatActivity {
    private TextInputLayout editTextMobile;
    LoadingDialog loadingDialog;
    Alert alert;
    Typeface typeface;
    Button button;
    TextView text_View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);
        setContentView(R.layout.activity_phone_number);
        editTextMobile = findViewById(R.id.editTextMobile);
        button= findViewById(R.id.buttonContinue);
        text_View=findViewById(R.id.textView);

        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        editTextMobile.setTypeface(typeface);
        editTextMobile.getEditText().setTypeface(typeface);
        button.setTypeface(typeface);
        text_View.setTypeface(typeface);

       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getEditText().getText().toString();
                if(mobile.isEmpty() || mobile.length() < 10){
                   editTextMobile.setErrorEnabled(true);
                   editTextMobile.setError(getResources().getString(R.string.error_phone_number));
                   editTextMobile.requestFocus();
                    SpannableString efr = new SpannableString(getResources().getString(R.string.error_phone_number));
                    Toast toast = Toast.makeText(PhoneNumberActivity.this,efr, Toast.LENGTH_SHORT);
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
             sendNumber(mobile);
            }
        });
    }
    private void sendNumber(String mobile) {
        loadingDialog.startLoadingDialog(false);
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<SendNumber> call = apiService.sendNum(mobile);
        call.enqueue(new Callback<SendNumber>() {
            @Override
            public void onResponse(Call<SendNumber> call, Response<SendNumber> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    //progressDoalog.dismiss();
//                    String mesag = response.body().getMessage();
              //      Toast.makeText(PhoneNumberActivity.this, ""+mobile, Toast.LENGTH_SHORT).show();
                    SharedPref.getInstance(PhoneNumberActivity.this).storeNumber(mobile);

                    Intent intent = new Intent(PhoneNumberActivity.this, VerifyCodeActivity.class);
                    intent.putExtra("mobile",mobile);
                    startActivity(intent);
                    finish();
                }
              //  else
                   // Toast.makeText(PhoneNumberActivity.this,response.errorBody()+ "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SendNumber> call, Throwable t) {
                loadingDialog.dismissDialog();

               // alert.showWarningDialog();
            }
        });
    }
}

