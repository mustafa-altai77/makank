package com.example.makank;

import android.os.Bundle;

import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Steper extends AppCompatActivity {
    ViewPager mys;
    LinearLayout mylay;
    SliderAdapter sliderAdapter;
    TextView[] mDots;
    TextView next, back;
    int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steper);
        mylay =  findViewById(R.id.mlayout);
        mys = findViewById(R.id.slidview);

        sliderAdapter = new SliderAdapter(this);
        mys.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mys.addOnPageChangeListener(viewListener);

        next =  findViewById(R.id.nextBtn);
         back = findViewById(R.id.backBtn);



       next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mys.setCurrentItem(currentPage +1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mys.setCurrentItem(currentPage -1);
            }
        });
    }

    void addDotsIndicator(int position) {
        mDots = new TextView[4];
        mylay.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.white));

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

            currentPage=i;
            if (currentPage==0)
            {
               next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.VISIBLE);

                next.setText("Next");
                back.setText("");
            }else if(i==mDots.length -1)
            {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                next.setText("Finish");
                back.setText("Back");
            }
            else
            {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setText("Next");
                back.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
