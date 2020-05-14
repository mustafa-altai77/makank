package com.example.makank.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.makank.R;
import com.example.makank.SliderAdapter;

import java.time.Instant;

public class Steper extends AppCompatActivity {
    ViewPager mys;
    LinearLayout mylay;
    SliderAdapter sliderAdapter;
    TextView[] mDots;
    Button next, back;
    int currentPage;
    Typeface typeface;
    TextView instr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steper);
        mylay = findViewById(R.id.mlayout);
        mys = findViewById(R.id.slideView);
        instr = findViewById(R.id.onClicked);
        sliderAdapter = new SliderAdapter(this);
        mys.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mys.addOnPageChangeListener(viewListener);

        next = findViewById(R.id.nextBtn);
        back = findViewById(R.id.backBtn);

        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        next.setTypeface(typeface);
        back.setTypeface(typeface);
        instr.setTypeface(typeface);
        instr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Steper.this,InstructionsActivity.class);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mys.setCurrentItem(currentPage + 1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mys.setCurrentItem(currentPage - 1);
            }
        });
    }

    void addDotsIndicator(int position) {
        mDots = new TextView[5];
        mylay.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.color));

            mylay.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.total1));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            currentPage = i;
            if (currentPage == 0) {
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.VISIBLE);

                next.setText(getResources().getString(R.string.next));
                back.setText("");
            } else if (i == mDots.length - 1) {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                next.setText("");
                back.setText(getResources().getString(R.string.back));
            } else {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setText(getResources().getString(R.string.next));
                back.setText(getResources().getString(R.string.back));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
