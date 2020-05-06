package com.example.makank.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.ui.contact.ContactFragment;
import com.example.makank.ui.home.GridFragment;
import com.example.makank.ui.news.NewsFragment;
import com.example.makank.ui.profile.ProfileFragment;
import com.example.makank.ui.statistic.StatisticFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    BottomNavigationView bottomNavigationView;
    private ActionBar toolbar;
    Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.id_drawer);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.getLayoutDirection();
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = getSupportActionBar();

        //typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        loadFragment(new GridFragment());
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view2);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id==R.id.share_nav){
                    Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.about_nav){
                    Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.rol_nav){
                    Toast.makeText(getApplicationContext(), "Role", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationHome:
//                    toolbar.setTitle("الرئسية");
                    fragment = new GridFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigationMyContact:
//                    toolbar.setTitle("التواصل");
                    fragment = new ContactFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigationNews:
//                    toolbar.setTitle("الاخبار");
                    fragment = new NewsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigationMyProfile:
//                    toolbar.setTitle("الحساب");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
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
        transaction.replace(R.id.frame_home,fragment);
        transaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}
