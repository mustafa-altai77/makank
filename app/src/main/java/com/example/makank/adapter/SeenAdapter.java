package com.example.makank.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
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
import com.example.makank.data.model.Pivot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

        holder.Fneme.setText(seenListFiltered.get(position).getFirst_name() + " " + seenListFiltered.get(position).getSecond_name() + " " + seenListFiltered.get(position).getLast_name());
        holder.Pid.setText(seenListFiltered.get(position).getId());
//        holder.date.setText(seenListFiltered.get(position).getPivots().getUpdated_at());
        holder.stat.setText(seenListFiltered.get(position).getStatus());
        String created = seenListFiltered.get(position).getPivots().getCreated_at();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputTime = new SimpleDateFormat("HH:mm:ss");
        Date d =null ;

        try {

        d = sdf.parse(created);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = output.format(d);
        String formattedTime = outputTime.format(d);
        holder.date.setText(formattedDate);
        holder.time.setText(formattedTime);


        if (model.getStatus().equals("3")) {
            holder.stat.setText(context.getResources().getString(R.string.healthy_case));
            holder.image.setImageResource(R.color.green);
        } else if (model.getStatus().equals("2")) {
            holder.stat.setText(context.getResources().getString(R.string.suspicious_case));
            holder.image.setImageResource(R.color.yellow);
        } else if (model.getStatus().equals("1")) {
            holder.image.setImageResource(R.color.colorAccent);
            holder.stat.setText(context.getResources().getString(R.string.sufferer_case));
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
        TextView Fneme, Pid, stat, date, time;
        CircleImageView image;
        Typeface typeface;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Fneme = itemView.findViewById(R.id.full_name);
            Pid = itemView.findViewById(R.id.person_id);
            date = itemView.findViewById(R.id.dateComm);
            stat = itemView.findViewById(R.id.status_txt);
            time = itemView.findViewById(R.id.date_seen);

            image = itemView.findViewById(R.id.status_mg);


            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
            Fneme.setTypeface(typeface);
            Pid.setTypeface(typeface);
            date.setTypeface(typeface);
            stat.setTypeface(typeface);
            time.setTypeface(typeface);

        }
    }
}