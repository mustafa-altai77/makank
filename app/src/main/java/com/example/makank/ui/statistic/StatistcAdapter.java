package com.example.makank.ui.statistic;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makank.R;
import com.example.makank.data.Statistc;
import com.example.makank.ui.activity.ContactActivity;
import com.example.makank.ui.activity.SendNotifActivity;
import com.example.makank.ui.activity.VolunteerActivity;
import com.example.makank.ui.home.Home;

import java.util.List;

public class StatistcAdapter extends RecyclerView.Adapter<StatistcAdapter.CategoryViewHolider> {
        private Context context;
        private List<Statistc> statistcs;

        public StatistcAdapter(List<Statistc> statistcs, Context context) {
            this.context = context;
            this.statistcs = statistcs;
        }

        @NonNull
        @Override
        public CategoryViewHolider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.group_item,parent,false);

            return new CategoryViewHolider(view);
        }

        @SuppressLint("ResourceType")
        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolider holder, final int position) {
            final Statistc item = statistcs.get(position);
//            holder.case_count.setText(item.g());
//            holder.categoryImage.setImageResource(item.statistcs);

        }

        @Override
        public int getItemCount() {
            return statistcs.size();
        }

        public class CategoryViewHolider extends RecyclerView.ViewHolder{
            ImageView categoryImage ;
            TextView case_count ,case_txt;
            CardView cardView;
            @SuppressLint("ResourceAsColor")
            public CategoryViewHolider(@NonNull View itemView) {
                super(itemView);
               // categoryImage = itemView.findViewById(R.id.grid_image);
                case_count = itemView.findViewById(R.id.case_count_red);
                case_txt = itemView.findViewById(R.id.case_name_yellow);
                //cardView = itemView.findViewById(R.id.card_grid);
            }
        }
}
