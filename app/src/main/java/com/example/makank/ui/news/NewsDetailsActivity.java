package com.example.makank.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makank.R;
import com.example.makank.data.model.News;

public class NewsDetailsActivity extends AppCompatActivity {
    TextView text, description, datePublisher;
    ImageView image;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        image = findViewById(R.id.image_details);
        text = findViewById(R.id.title_details);
        description = findViewById(R.id.desc_details);
        final News model = getIntent().getParcelableExtra("object");
        String tit = model.getTitle();
        String txt = model.getText();

        text.setText(tit);
        description.setText(txt);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        text.setTypeface(typeface);
        description.setTypeface(typeface);
        text.setTypeface(typeface);
       // datePublisher.setTypeface(typeface);
//        Glide.with(getApplicationContext()).load("http://tatbiqati.com/public/images/"
//       +model.getImage()).into(image);
    }
}
