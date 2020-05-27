package com.example.makank.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.makank.Alert;
import com.example.makank.R;

public class Areas extends AppCompatActivity {
    Button button, button2, button3, goRegister;
    String stateID, stateName, cityID, cityName, localID, localName;
    Typeface typeface;
    Toolbar toolbar;
    TextView infoPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);
       // getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
       // this.setFinishOnTouchOutside(false);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        button = findViewById(R.id.btnState);
        button2 = findViewById(R.id.btnCity);
        button3 = findViewById(R.id.btnLocal);
        goRegister = findViewById(R.id.gotoRegister);
        infoPlace=findViewById(R.id.info_place);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        button.setTypeface(typeface);
        button2.setTypeface(typeface);
        button3.setTypeface(typeface);
        button2.setEnabled(false);
        button3.setEnabled(false);
        goRegister.setTypeface(typeface);
        infoPlace.setTypeface(typeface);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Areas.this, StateActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Areas.this, CityActivity.class);
                i.putExtra("state_id", stateID);
                i.putExtra("state_name", stateName);
                startActivityForResult(i, 2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Areas.this, LocalActivity.class);
                i.putExtra("city_id", cityID);
                i.putExtra("city_name", cityName);
                startActivityForResult(i, 3);
            }
        });

        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (localID==null) {
                    Alert alert = new Alert(Areas.this);
                    alert.showErrorDialog(getResources().getString(R.string.messageLocal));

                } else {
                  /*  Intent intent = new Intent("finish");
                    sendBroadcast(intent);
                    finish();*/
                    Intent i = new Intent(Areas.this, RegisterActivity.class);
                    i.putExtra("local_id", localID);
                    i.putExtra("local_name", localName);
                    startActivity(i);
                    finish();

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            stateID = data.getStringExtra("state_id");
            stateName = data.getStringExtra("state_name");
            button.setText(stateName);
            button2.setText(getResources().getString(R.string.chooseCity));
            button3.setText(getResources().getString(R.string.chooseLocal));
            button2.setEnabled(true);
        }
        if (requestCode == 2) {
            cityID = data.getStringExtra("city_id");
            cityName = data.getStringExtra("city_name");
            button2.setText(cityName);
            localName = null;
            localID = null;
            button3.setText(getResources().getString(R.string.chooseLocal));
            button3.setEnabled(true);
        }
        if (requestCode == 3) {
            localID = data.getStringExtra("local_id");
            localName = data.getStringExtra("local_name");
            button3.setText(localName);

            //Toast.makeText(this, ""+stateName+""+cityName+""+localName, Toast.LENGTH_SHORT).show();
        }
    }

}
