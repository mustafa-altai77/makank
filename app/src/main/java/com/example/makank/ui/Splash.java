package com.example.makank.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.ui.activity.Areas;
import com.example.makank.ui.activity.HomeActivity;
import com.example.makank.ui.activity.PhoneNumberActivity;
import com.example.makank.ui.activity.StateActivity;

import java.util.Locale;

public class Splash extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView imageView1,imageView2;
    Configuration config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView1 =  findViewById(R.id.imageView);
        imageView2 =  findViewById(R.id.imageView2);

        imageView1.setAnimation(topAnim);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imageView1.setAnimation(topAnim);
        imageView2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkConnection();
            }
        },1500);

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
              //  Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(SharedPref.getInstance(this).HasToken()){
                startActivity(new Intent(this, Areas.class));
                finish();
            }
           else {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
        }else{
            Alert alert=new Alert(this);
            alert.showWarningDialog();

            checkConnection();
        }
    }
}