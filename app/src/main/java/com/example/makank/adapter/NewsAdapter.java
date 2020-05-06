package com.example.makank.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(created);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = output.format(d);
        holder.datePublisher.setText(formattedDate);
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