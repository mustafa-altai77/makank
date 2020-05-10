package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.makank.R;

public class AboutMakanak extends AppCompatActivity {
RelativeLayout phonePress,emailPress,sitePress;
TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutmakanak);
        phonePress=findViewById(R.id.phoneNum);
        emailPress=findViewById(R.id.email);
        sitePress=findViewById(R.id.site);

        t1=findViewById(R.id.phoneNum1);
        t2=findViewById(R.id.phoneNum2);
        t3=findViewById(R.id.email1);
        t4=findViewById(R.id.email2);
        t5=findViewById(R.id.site1);
        t6=findViewById(R.id.site2);
        t7=findViewById(R.id.version1);
        t8=findViewById(R.id.version2);
        t9=findViewById(R.id.policy);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        t3.setTypeface(typeface);
        t4.setTypeface(typeface);
        t5.setTypeface(typeface);
        t6.setTypeface(typeface);
        t7.setTypeface(typeface);
        t8.setTypeface(typeface);
        t9.setTypeface(typeface);
        phonePress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+249903940244"));
                startActivity(intent);
            }
        });
        sitePress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri= "http://www.primaryKeysd.com";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        emailPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "info@primarykeysd.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, "Makank"));
            }
        });
}
}
