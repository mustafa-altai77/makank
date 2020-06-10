package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.makank.R;

import com.example.makank.adapter.TabViewPagerAdapter;
import com.example.makank.ui.activity.hospital.EmergancyFragment;
import com.example.makank.ui.activity.hospital.QuarantineFragment;
import com.google.android.material.tabs.TabLayout;

public class HospitalsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager firstViewPager;

    SearchView searchView;
    Toolbar toolbar;
    Typeface typeface;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_hospitals);
//        recyclerView = findViewById(R.id.recycler_hospital);

        info = findViewById(R.id.infoInsert);
        //define btn
        firstViewPager = findViewById(R.id.viewpager_content);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(firstViewPager);
        setupViewPager(firstViewPager);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        info.setTypeface(typeface);


    }
    private void setupViewPager(ViewPager viewPager) {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new QuarantineFragment(), getResources().getString(R.string.quarantin));
        adapter.addFragment(new EmergancyFragment(), getResources().getString(R.string.emergancy));
        viewPager.setAdapter(adapter);
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Hacen-Algeria.ttf");

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(typeface, Typeface.NORMAL);
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setupViewPager(firstViewPager);

    }

}
