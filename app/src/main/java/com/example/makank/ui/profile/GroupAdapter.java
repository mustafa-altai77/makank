package com.example.makank.ui.profile;

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
import com.bumptech.glide.request.RequestOptions;
import com.example.makank.R;
import com.example.makank.data.Member;
import com.example.makank.data.News;
import com.example.makank.ui.news.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> implements Filterable {

    private List<Member> newsList;
    private List<Member> newsListFiltered;
    private Context context;

    public void setMovieList(Context context, final List<Member> movieList) {
        this.context = context;
        if (this.newsList == null) {
            this.newsList = movieList;
            this.newsListFiltered = movieList;
            notifyItemChanged(0, newsListFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return GroupAdapter.this.newsList.size();
                }

                @Override
                public int getNewListSize() {
                    return movieList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return GroupAdapter.this.newsList.get(oldItemPosition).getFirst_name() == movieList.get(newItemPosition).getFirst_name();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    Member newNew = GroupAdapter.this.newsList.get(oldItemPosition);

                    Member oldNews = movieList.get(newItemPosition);

                    return newNew.getFirst_name() == oldNews.getFirst_name();
                }
            });
            this.newsList = movieList;
            this.newsListFiltered = movieList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public GroupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(GroupAdapter.MyViewHolder holder, int position) {
        final Member model = newsList.get(position);
        holder.Fneme.setText(newsListFiltered.get(position).getFirst_name());
        holder.Sname.setText(newsListFiltered.get(position).getSecond_name());
        holder.Lname.setText(newsListFiltered.get(position).getLast_name());
        holder.Pid.setText(newsListFiltered.get(position).getId());
        holder.stat.setText(newsListFiltered.get(position).getStatus());

        if (model.getStatus().equals("3")) {
            holder.stat.setText("سليم");
            holder.image.setImageResource(R.color.green);
        }else if (model.getStatus().equals("2")) {
            holder.stat.setText("مخالط");
            holder.image.setImageResource(R.color.yellow);
        }
        else if(model.getStatus().equals("1")) {
            holder.stat.setText("مصاب");
            holder.image.setImageResource(R.color.colorAccent);
//
//        Glide.with(context).load(newsList.get(position).getImage()).apply(RequestOptions.centerCropTransform()).into(holder.image);
        }
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
                    List<Member> filteredList = new ArrayList<>();
                    for (Member movie : newsList) {
                        if (movie.getFirst_name().toLowerCase().contains(charString.toLowerCase())) {
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
                newsListFiltered = (ArrayList<Member>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Fneme,Sname,Lname,Pid,stat;
        ImageView image;
        CardView cardView;
        @SuppressLint("WrongViewCast")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Fneme = itemView.findViewById(R.id.f_nam);
            Sname = itemView.findViewById(R.id.s_nam);
            Lname = itemView.findViewById(R.id.l_nam);
            Pid = itemView.findViewById(R.id.peson_id);
            stat = itemView.findViewById(R.id.status_txt);
            image = itemView.findViewById(R.id.status_mg);


        }
    }
}