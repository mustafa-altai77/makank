package com.example.makank.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.makank.R;

import java.util.Locale;

public class Language extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        showLanguageDialog();
        loadLocale();
    }

    private void showLanguageDialog() {
        final String[] listItems = {"Arabic", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Language.this);
        builder.setTitle(getResources().getString(R.string.choose_language));
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("ar");
                    recreate();
                } else if (which == 1) {
                    setLocale("en");
                    recreate();
                }
                dialog.dismiss();
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                startActivity(i);
                finish();
            }
        });
        AlertDialog mDialog = builder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.commit();//this.finish();
        //Intent refresh = new Intent(this, Call_Isolation.class);
        // startActivity(refresh);


    }

    public void loadLocale() {
        SharedPreferences pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language = pref.getString("My_lang", "");
        setLocale(language);

    }
}
