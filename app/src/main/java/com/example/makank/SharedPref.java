package com.example.makank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.makank.ui.activity.HomeActivity;

public class SharedPref {
    //Storage File
    public static final String SHARED_PREF_NAME = "larntech";

    //Username
    public static final String USER_ID = "id";
    public static final String F_NAME = "first_name";
    public static final String S_NAME = "second_name";
    public static final String L_NAME = "last_name";
    public static final String PHONE = "phone";
    public static final String GENDER = "gender";
    public static final String STATUS = "status";
    public static final String AGE = "age";

    public static final String USER_LOCAL = "local";

    public static SharedPref mInstance;

    public static Context mCtx;


    public SharedPref(Context context) {
        mCtx = context;
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
        editor.putString(USER_LOCAL, local);
        editor.commit();
    }
    //method to store user data
    public void storeUserID(String id,String f_name,String s_name,String l_name,String phone,String gender,String status,String age) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, id);
        editor.putString(F_NAME, f_name);
        editor.putString(S_NAME, s_name);
        editor.putString(L_NAME, l_name);
        editor.putString(PHONE, phone);
        editor.putString(GENDER, gender);
        editor.putString(STATUS, status);
        editor.putString(AGE, age);
        editor.commit();
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
