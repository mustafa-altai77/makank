package com.example.makank.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.makank.R;
import com.example.makank.data.model.News;
import com.example.makank.ui.news.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

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
        Glide.with(context).load("http://primarykeysd.com/makank/Anti_Covid19/public/images/public/"+model.getImage()).into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("object",model);
                context.startActivity(intent);
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
        TextView title,discription;
        ImageView image;
        CardView cardView;
        @SuppressLint("WrongViewCast")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            discription = itemView.findViewById(R.id.news_desc);
            image = itemView.findViewById(R.id.image_news);
            cardView = itemView.findViewById(R.id.news_card);

        }
    }
}