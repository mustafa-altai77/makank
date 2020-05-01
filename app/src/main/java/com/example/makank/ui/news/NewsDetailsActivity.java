package com.example.makank.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makank.R;
import com.example.makank.data.model.News;

public class NewsDetailsActivity extends AppCompatActivity {
    TextView text,description;
    ImageView image;
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
//        Glide.with(getApplicationContext()).load("http://tatbiqati.com/public/images/"
//       +model.getImage()).into(image);
       }
}
