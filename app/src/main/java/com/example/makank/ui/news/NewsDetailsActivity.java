package com.example.makank.ui.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.makank.R;
import com.example.makank.data.model.News;

public class NewsDetailsActivity extends AppCompatActivity {
    TextView text,description,title;
    ImageView image;
    Typeface typeface;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
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

        String tex=String.valueOf(Html.fromHtml(
                "<![CDATA[<body style=\"text-align:justify\">"
                +txt+ "</body>]]>"
        ));

        text.setText(tit);
        description.setText(txt);//Html.fromHtml(tex));
        Glide.with(getApplicationContext()).load("http://primarykeysd.com/makank/Anti_Covid19/public/news/"
       +model.getImage()).into(image);
       }
}
