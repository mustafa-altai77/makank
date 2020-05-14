package com.example.makank.ui.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.makank.R;
import com.example.makank.data.model.Local;

import java.util.Locale;

public class Call_Isolation extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5;
    LinearLayout layout, layout2;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_call_dialog);

        // WindowManager.LayoutParams params = getWindow().getAttributes();
        // getWindow().setLayout(400,700);
        //  getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        t1 = findViewById(R.id.textTitle);
        t2 = findViewById(R.id.id2);
        t3 = findViewById(R.id.id3);
        t4 = findViewById(R.id.id4);
        t5 = findViewById(R.id.id5);
        layout = findViewById(R.id.layout_kh);
        layout2 = findViewById(R.id.layout_su);


        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        t3.setTypeface(typeface);
        t4.setTypeface(typeface);
        t5.setTypeface(typeface);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:221"));
                startActivity(intent);
            }
        });


        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9090"));
             startActivity(intent);


            }
        });

    }

}