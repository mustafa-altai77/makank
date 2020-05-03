package com.example.makank.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.ui.activity.HomeActivity;
import com.example.makank.ui.activity.PlaceActivity;
import com.example.makank.ui.activity.StateActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkConnection();
            }
        },500);
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    public void checkConnection(){
        if(isOnline()){
            if (SharedPref.getInstance(this).isLoggedIn()) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
            else {
                startActivity(new Intent(this, StateActivity.class));
                finish();
            }
        }else{
            Toast.makeText(Splash.this, "انت غير متصل بالانترنت", Toast.LENGTH_LONG).show();
            checkConnection();
        }
    }
}
