package com.example.makank.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makank.R;
import com.example.makank.data.model.Statistc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatistcAdapter extends RecyclerView.Adapter<StatistcAdapter.CategoryViewHolider> {
        private Context context;
        private List<Statistc> statistcs;
    public StatistcAdapter(List<Statistc> statistcs, Context context) {
        this.context = context;
        this.statistcs = statistcs;
    }
    public void setCases(List<Statistc> statistcs) {
        this.statistcs =  new ArrayList<>();
            this.statistcs =  statistcs;
        notifyDataSetChanged();

    }
        @NonNull
        @Override
        public CategoryViewHolider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistic_item, parent, false);
            return new CategoryViewHolider(view);
        }

        @SuppressLint("ResourceType")
        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolider holder, final int position) {
            holder.stateName.setText(statistcs.get(position).getName());
            holder.case_txt.setText(statistcs.get(position).getCases_count());
            holder.recovery.setText(statistcs.get(position).getRecovery_cases());
            holder.deathes.setText(statistcs.get(position).getNew_Deaths());
//            for (int i = 0; i < statistcs.size(); i++)
//                holder.sum.setText(i+statistcs.get(position).getCases_count());
//

            String created = statistcs.get(position).getLatest_cases().getCreated_at();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputTime = new SimpleDateFormat("HH:mm:ss");
            Date d = null;

            try {

                d = sdf.parse(created);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = output.format(d);
            String formattedTime = outputTime.format(d);
            holder.date.setText(formattedDate);

        }

        @Override
        public int getItemCount() {
            if (statistcs != null) {
                return statistcs.size();
            } else {
                return 0;
            }        }

        public class CategoryViewHolider extends RecyclerView.ViewHolder{
            ImageView categoryImage ;
            TextView stateName,case_txt,deathes,recovery,date,sum;
            CardView cardView;
            @SuppressLint("ResourceAsColor")
            public CategoryViewHolider(@NonNull View itemView) {
                super(itemView);
               // categoryImage = itemView.findViewById(R.id.grid_image);
                case_txt = itemView.findViewById(R.id.cases_count);
                stateName = itemView.findViewById(R.id.state_name);
                recovery = itemView.findViewById(R.id.recovery_count);
                deathes = itemView.findViewById(R.id.death_count);
                date = itemView.findViewById(R.id.dateComm);
                sum = itemView.findViewById(R.id.sum_cases);


                //cardView = itemView.findViewById(R.id.card_grid);
            }
        }
}
