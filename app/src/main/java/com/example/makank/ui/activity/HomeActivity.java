package com.example.makank.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.R;
import com.example.makank.ui.Splash;
import com.example.makank.ui.contact.ContactFragment;
import com.example.makank.ui.home.Call_Isolation;
import com.example.makank.ui.home.CustomTypefaceSpan;
import com.example.makank.ui.home.GridFragment;
import com.example.makank.ui.news.NewsFragment;
import com.example.makank.ui.profile.ProfileFragment;
import com.example.makank.ui.statistic.StatisticFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.mCtx;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    BottomNavigationView bottomNavigationView;
    private ActionBar toolbar;
    Typeface typeface;
    Toolbar toolbar1;
    TextView name, state;
    NavigationView navigationView;
    Alert alert;

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.id_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.getLayoutDirection();
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //toolbar = getSupportActionBar();
        toolbar1 = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar1);
        // typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        loadFragment(new GridFragment());
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_view2);
        Menu m = navigationView.getMenu();
        Menu me = bottomNavigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        for (int i = 0; i < me.size(); i++) {
            MenuItem mi = me.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                alert = new Alert(HomeActivity.this);
                // int id = menuItem.getItemId();
                switch (menuItem.getItemId()) {
                    /*case R.id.share_nav:
                        Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
                        break;*/
                    case R.id.qr_code:
                        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        final String token = sharedPreferences.getString(TOKEN, "token");
                        if (token.equals("token")) {
                            alert.showRegisterDialog();
                        } else {
                            Intent in = new Intent(getApplicationContext(), QrCodeActivity.class);
                            startActivity(in);
                        }
                        break;
                    case R.id.inst:
                        Intent iin = new Intent(getApplicationContext(), InstructionsActivity.class);
                        startActivity(iin);
                        break;
                    case R.id.about_nav:
                        Intent intent = new Intent(getApplicationContext(), About.class);
                        startActivity(intent);
                        break;
                    case R.id.rol_nav:
                        String uri = "http://www.primaryKeysd.com/covid/term.html";
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(i);
                        break;

                    case R.id.face_book:
                        String urii = "https://www.facebook.com/primarykeysd";
                        Intent iy = new Intent(Intent.ACTION_VIEW, Uri.parse(urii));
                        startActivity(iy);
                        break;
                   /* case R.id.tips:
                        Intent inten = new Intent(getApplicationContext(), Steper.class);
                        startActivity(inten);
                        break;*/
                    case R.id.waba:
                        Intent inr = new Intent(getApplicationContext(), Call_Isolation.class);
                        startActivity(inr);
                        break;
                    default:
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(TOKEN, "token");
            alert = new Alert(HomeActivity.this);
            switch (item.getItemId()) {
                case R.id.navigationHome:
//                    toolbar.setTitle("الرئسية");
                    fragment = new GridFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigationMyContact:
//                    toolbar.setTitle("التواصل");
                    if (token.equals("token")) {
                        alert.showRegisterDialog();
                    } else {
                        fragment = new ContactFragment();
                        loadFragment(fragment);
                    }
                    return true;
                case R.id.navigationNews:
//                    toolbar.setTitle("الاخبار");
                    fragment = new NewsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigationMyProfile:
//                    toolbar.setTitle("الحساب");
                    if (token.equals("token")) {
                        alert.showRegisterDialog();
                    } else {
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                    }
                    return true;
                case R.id.navigationStatistic:
//                    toolbar.setTitle("الاحصائيات");
                    fragment = new StatisticFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_home, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Hacen-Algeria.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


}