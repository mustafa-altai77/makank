package com.example.makank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by Mustafa on 4/29/2020.
 */

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //volunteer   virus   wash
    public int[] slide_images =
            {
                    R.drawable.four,
                    R.drawable.second,
                    R.drawable.three,
                    R.drawable.first1
            };

    @Override
   public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (LinearLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_img);
       // TextView slideDe = (TextView) view.findViewById(R.id.slide_des);

        slideImageView.setImageResource(slide_images[position]);
      //  slideDe.setText(slide_des[position]);

        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
