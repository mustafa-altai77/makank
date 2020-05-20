package com.example.makank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.makank.ui.activity.HomeActivity;

public class SharedPref {
    //Storage File
    public static final String SHARED_PREF_NAME = "larntech";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    //Username
    public static final String USER_ID = "id";
    public static final String F_NAME = "first_name";
    public static final String S_NAME = "second_name";
    public static final String L_NAME = "last_name";
    public static final String QRCODE = "qr_cod";

    public static final String PHONE = "phone";
    public static final String GENDER = "gender";
    public static final String STATUS = "status";
    public static final String AGE = "age";
    public static final String TOKEN = "token";

    public static final String USER_LOCAL = "local";

    public static SharedPref mInstance;

    public static Context mCtx;


    public SharedPref(Context context) {
        mCtx = context;
    }


    public void setFirstTimeLaunch(boolean isFirstTime) {

    }


    public static synchronized SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref(context);
        }
        return mInstance;
    }

    public void storeUserLocal(String local) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
        editor.putString(USER_LOCAL, local);
        editor.commit();    }
    //method to store user data
    public void storeUserID(String id,String f_name,String s_name,String l_name,String qr_cod,String gender,String age,String status) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_ID, id);
        editor.putString(F_NAME, f_name);
        editor.putString(S_NAME, s_name);
        editor.putString(L_NAME, l_name);
        editor.putString(QRCODE, qr_cod);
        editor.putString(GENDER, gender);
        editor.putString(AGE, age);
        editor.putString(STATUS, status);
        editor.commit();
       // Toast.makeText(mCtx, ""+USER_ID, Toast.LENGTH_SHORT).show();
    }
    public void storeNumber(String phone) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE, phone);
        editor.commit();
        // Toast.makeText(mCtx, ""+USER_ID, Toast.LENGTH_SHORT).show();
    }
    public void storeToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.commit();
        // Toast.makeText(mCtx, ""+USER_ID, Toast.LENGTH_SHORT).show();
    }
    //check if user is logged in
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID, null) != null;
    }


    //find logged in user
    public String LoggedInUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID, null);

    }


    //Logout user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, HomeActivity.class));
    }

}
