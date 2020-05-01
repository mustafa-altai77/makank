package com.example.makank.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makank.R;
import com.example.makank.data.model.Member;

import java.util.ArrayList;
import java.util.List;

public class SeenAdapter extends RecyclerView.Adapter<SeenAdapter.MyViewHolder> implements Filterable {

    private List<Member> seenList;
    private List<Member> seenListFiltered;
    private Context context;

    public void setMovieList(Context context, final List<Member> movieList) {
        this.context = context;
        if (this.seenList == null) {
            this.seenList = movieList;
            this.seenListFiltered = movieList;
            notifyItemChanged(0, seenListFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return SeenAdapter.this.seenList.size();
                }

                @Override
                public int getNewListSize() {
                    return movieList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return SeenAdapter.this.seenList.get(oldItemPosition).getFirst_name() == movieList.get(newItemPosition).getFirst_name();

                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    Member newNew = SeenAdapter.this.seenList.get(oldItemPosition);

                    Member oldNews = movieList.get(newItemPosition);

                    return newNew.getFirst_name() == oldNews.getFirst_name();
                }
            });
            this.seenList = movieList;
            this.seenListFiltered = movieList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public SeenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SeenAdapter.MyViewHolder holder, int position) {
        final Member model = seenList.get(position);
        holder.Fneme.setText(seenListFiltered.get(position).getFirst_name());
        holder.Sname.setText(seenListFiltered.get(position).getSecond_name());
        holder.Lname.setText(seenListFiltered.get(position).getLast_name());
        holder.Pid.setText(seenListFiltered.get(position).getId());
        holder.data.setText(seenListFiltered.get(position).getUpdated_at());
        holder.stat.setText(seenListFiltered.get(position).getStatus());

        if (model.getStatus().equals("3")) {
            holder.stat.setText("سليم");
            holder.image.setImageResource(R.color.green);
        } else if (model.getStatus().equals("2")) {
            holder.stat.setText("مخالط");
            holder.image.setImageResource(R.color.yellow);
        } else if (model.getStatus().equals("1")) {
            holder.stat.setText("مصاب");
            holder.image.setImageResource(R.color.colorAccent);
        }
    }

    @Override
    public int getItemCount() {

        if (seenList != null) {
            return seenListFiltered.size();
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
                    seenListFiltered = seenList;
                } else {
                    List<Member> filteredList = new ArrayList<>();
                    for (Member member : seenList) {
                        if (member.getFirst_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(member);
                        }
                    }
                    seenListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = seenListFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                seenListFiltered = (ArrayList<Member>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Fneme, Sname, Lname, Pid, stat, data, time;
        ImageView image;
        @SuppressLint("WrongViewCast")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Fneme = itemView.findViewById(R.id.f_nam);
            Sname = itemView.findViewById(R.id.s_nam);
            Lname = itemView.findViewById(R.id.l_nam);
            Pid = itemView.findViewById(R.id.peson_id);
            //time = itemView.findViewById(R.id.time_seen);
            data = itemView.findViewById(R.id.date_seen);
            stat = itemView.findViewById(R.id.status_txt);
            image = itemView.findViewById(R.id.status_mg);
        }
    }
}