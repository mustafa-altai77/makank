package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.makank.R;

public class About extends AppCompatActivity {
    TextView t1, t2, t3, t4, copyR;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        // getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        t1 = findViewById(R.id.id1);
        t2 = findViewById(R.id.id2);
        t3 = findViewById(R.id.id3);
        t4 = findViewById(R.id.id4);
        copyR = findViewById(R.id.copyRight);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        t3.setTypeface(typeface);
        copyR.setTypeface(typeface);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+249118135130"));
                startActivity(intent);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://www.primaryKeysd.com";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@primarykeysd.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, "Makank"));
            }
        });
    }
}