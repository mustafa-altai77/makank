package com.example.makank.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.makank.R;
import com.example.makank.data.model.News;

public class NewsDetailsActivity extends AppCompatActivity {
    TextView text,description,title;
    ImageView image;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        image = findViewById(R.id.image_details);
        text = findViewById(R.id.title_details);
        title = findViewById(R.id.detNew);
        description = findViewById(R.id.desc_details);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        text.setTypeface(typeface);
        description.setTypeface(typeface);
        title.setTypeface(typeface);
        final News model = getIntent().getParcelableExtra("object");
        String tit = model.getTitle();
        String txt = model.getText();

        text.setText(tit);
        description.setText(txt);
        Glide.with(getApplicationContext()).load("http://primarykeysd.com/makank/Anti_Covid19/public/news/"
       +model.getImage()).into(image);
       }
}
