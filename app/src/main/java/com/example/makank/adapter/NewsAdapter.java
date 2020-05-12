package com.example.makank.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.makank.R;
import com.example.makank.data.model.News;
import com.example.makank.ui.news.NewsDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> implements Filterable {

    private List<News> newsList;
    private List<News> newsListFiltered;
    private Context context;

    public void setMovieList(Context context, final List<News> movieList) {
        this.context = context;
        if (this.newsList == null) {
            this.newsList = movieList;
            this.newsListFiltered = movieList;
            notifyItemChanged(0, newsListFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return NewsAdapter.this.newsList.size();
                }

                @Override
                public int getNewListSize() {
                    return movieList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return NewsAdapter.this.newsList.get(oldItemPosition).getTitle() == movieList.get(newItemPosition).getTitle();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    News newNew = NewsAdapter.this.newsList.get(oldItemPosition);

                    News oldNews = movieList.get(newItemPosition);

                    return newNew.getTitle() == oldNews.getTitle();
                }
            });
            this.newsList = movieList;
            this.newsListFiltered = movieList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.MyViewHolder holder, int position) {
        final News model = newsList.get(position);
        holder.title.setText(newsListFiltered.get(position).getTitle());
        holder.discription.setText(newsListFiltered.get(position).getText());
        // holder.datePublisher.setText(newsListFiltered.get(position).getText());
        String created = newsListFiltered.get(position).getCreated_at();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date past = null;
        try {
            past = format.parse(newsListFiltered.get(position).getCreated_at());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {

            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if (seconds < 60) {
                holder.datePublisher.setText("قبل " + "" + seconds + " ثانية");
            } else if (minutes < 60) {
                holder.datePublisher.setText("قبل " + "" + minutes + " دقيقة");
            } else if (hours < 24) {
                holder.datePublisher.setText("قبل " + "" + hours + " ساعة");
            } else if (days == 2) {
                holder.datePublisher.setText("قبل " + "" + days + " يومان");
            } else if (days >= 31) {
                holder.datePublisher.setText("قبل شهر ");
                if (days>=62)holder.datePublisher.setText("قبل شهرين ");
                if (days>=93)holder.datePublisher.setText("قبل 3 أشهر ");
                if (days>=124)holder.datePublisher.setText("قبل 4 أشهر ");
                if (days>=155)holder.datePublisher.setText("قبل 5 أشهر ");
                if (days>=186)holder.datePublisher.setText("قبل 6 أشهر ");
                if (days>=217)holder.datePublisher.setText("قبل 7 أشهر ");
                if (days>=248)holder.datePublisher.setText("قبل 8 أشهر ");
                if (days>=279)holder.datePublisher.setText("قبل 9 أشهر ");
                if (days>=310)holder.datePublisher.setText("قبل 10 أشهر ");
                if (days>=341)holder.datePublisher.setText("قبل 11 شهر ");
                if (days>=342) holder.datePublisher.setText("قبل سنة");
            } else {
                holder.datePublisher.setText("قبل " + "" + days + " أيام");
            }
        } catch (Exception j) {
            j.printStackTrace();
        }
        //holder.datePublisher.setText(formattedDate);
        Glide.with(context).load("http://primarykeysd.com/makank/Anti_Covid19/public/news/" + model.getImage()).into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("object", model);
                context.startActivity(intent);
            }
        });
        holder.shareImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String shareBody = newsList.get(position).getText();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Makank");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_using)));
            }
        });
    holder.datePublisher.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onClick(View v) {
            String created = newsListFiltered.get(position).getCreated_at();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputTime = new SimpleDateFormat("HH:mm:ss");
            Date d = null;
            try {
                d = sdf.parse(created);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Typeface   typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
            SpannableString efr = new SpannableString(output.format(d));
            efr.setSpan(new TypefaceSpan(typeface),0,efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Toast toast=Toast.makeText(context,efr,Toast.LENGTH_SHORT);
            View view=toast.getView();
            view.setBackgroundColor(Color.RED);
            TextView text=(TextView) view.findViewById(android.R.id.message);
            text.setShadowLayer(0,0,0,Color.TRANSPARENT);
            text.setTextColor(Color.WHITE);
            text.setTextSize(Integer.valueOf(18));
            toast.show();
        }
    });
    }



    @Override
    public int getItemCount() {

        if (newsList != null) {
            return newsListFiltered.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    newsListFiltered = newsList;
                } else {
                    List<News> filteredList = new ArrayList<>();
                    for (News movie : newsList) {
                        if (movie.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    newsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = newsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                newsListFiltered = (ArrayList<News>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, discription, datePublisher;
        ImageView image, shareImage;
        CardView cardView;
        Typeface typeface;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            discription = itemView.findViewById(R.id.news_desc);
            image = itemView.findViewById(R.id.image_news);
            cardView = itemView.findViewById(R.id.news_card);
            shareImage = itemView.findViewById(R.id.img);
            datePublisher = itemView.findViewById(R.id.dateNew);
            //  datePublisher = itemView.findViewById(R.id.dateNew);

            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
            title.setTypeface(typeface);
            discription.setTypeface(typeface);
            datePublisher.setTypeface(typeface);
            // datePublisher.setTypeface(typeface);


        }
    }

}