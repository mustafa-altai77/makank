package com.example.makank.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makank.R;
import com.example.makank.data.model.Member;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        holder.Fullneme.setText(newsListFiltered.get(position).getFirst_name()+" "+newsListFiltered.get(position).getSecond_name()+" "+newsListFiltered.get(position).getLast_name());
       //holder.Pid.setText(newsListFiltered.get(position).getUser().getPhone_number());
        holder.stat.setText(newsListFiltered.get(position).getStatus());

        if (model.getStatus().equals("3")) {
            holder.stat.setText(context.getResources().getString(R.string.healthy_case));
            holder.image.setImageResource(R.color.green);
        }else if (model.getStatus().equals("2")) {
            holder.stat.setText(context.getResources().getString(R.string.suspicious_case));
            holder.image.setImageResource(R.color.yellow);
        }
        else if(model.getStatus().equals("1")) {
            holder.stat.setText(context.getResources().getString(R.string.sufferer_case));
          holder.image.setImageResource(R.color.colorAccent);
//
//        Glide.with(context).load(newsList.get(position).getImage()).apply(RequestOptions.centerCropTransform()).into(holder.image);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = newsListFiltered.get(position).getUser().getPhone_number();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                context.startActivity(intent);
            }
        });

      /*  holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = newsListFiltered.get(position).getUser().getPhone_number();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                context.startActivity(intent);
            }
        });*/
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
        TextView Fullneme,Pid,stat;
        CircleImageView image;
        ImageView imageView;
        CardView cardView;
        Typeface typeface;
        @SuppressLint("WrongViewCast")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Fullneme = itemView.findViewById(R.id.personInGroup);
            //Pid = itemView.findViewById(R.id.idPerson);
            stat = itemView.findViewById(R.id.statusofPerson);
            image = itemView.findViewById(R.id.status_mgG);
            imageView = itemView.findViewById(R.id.phoneSeen);
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
            Fullneme.setTypeface(typeface);
//            Pid.setTypeface(typeface);
            stat.setTypeface(typeface);
        }
    }
}