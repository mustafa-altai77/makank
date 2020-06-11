package com.example.makank.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        this.statistcs = new ArrayList<>();
        this.statistcs = statistcs;
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

        String newCases = statistcs.get(position).getLatest_cases().getNew_sure_cases();
        String newRecovery = statistcs.get(position).getLatest_cases().getRecovery_cases();
        String newDeath = statistcs.get(position).getLatest_cases().getNew_Deaths();

      //  Toast.makeText(context, "Cases " + newCases + "Recovery " + newRecovery + "Deaths " + newDeath + "", Toast.LENGTH_LONG).show();
        holder.case_txt1.setText(newCases);
        holder.recovery1.setText(newRecovery);
        holder.deathes1.setText(newDeath);
     /*   String created = statistcs.get(position).getLatest_cases().getCreated_at();
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
        holder.date.setText(formattedDate);*/
        /*if (num %2==0)
        {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        num++;*/
    }


    @Override
    public int getItemCount() {
        if (statistcs != null) {
            return statistcs.size();
        } else {
            return 0;
        }
    }

    public class CategoryViewHolider extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView stateName, case_txt, deathes, recovery, date, sum, newCases, newRecovery, newDeath, caseActive, recoveryTxt, deathTxt, txtCase, txtRec, txtDe, old_total, new_Cases, case_txt1, deathes1, recovery1;
        CardView cardView;
        Typeface typeface;
        LinearLayout layout;

        @SuppressLint("ResourceAsColor")
        public CategoryViewHolider(@NonNull View itemView) {
            super(itemView);
            // categoryImage = itemView.findViewById(R.id.grid_image);
            case_txt = itemView.findViewById(R.id.cases_count);
            stateName = itemView.findViewById(R.id.state_name);
            recovery = itemView.findViewById(R.id.recovery_count);
            deathes = itemView.findViewById(R.id.death_count);
            // date = itemView.findViewById(R.id.dateComm);
            sum = itemView.findViewById(R.id.sum_cases);
            caseActive = itemView.findViewById(R.id.s1);
            recoveryTxt = itemView.findViewById(R.id.s2);
            deathTxt = itemView.findViewById(R.id.s3);

            layout = itemView.findViewById(R.id.lColor);
            //New Cases per day
            case_txt1 = itemView.findViewById(R.id.cases_count1);
            recovery1 = itemView.findViewById(R.id.recovery_count1);
            deathes1 = itemView.findViewById(R.id.death_count1);

            //for font new cases
            txtCase = itemView.findViewById(R.id.s4);
            txtRec = itemView.findViewById(R.id.s5);
            txtDe = itemView.findViewById(R.id.s6);

            old_total = itemView.findViewById(R.id.newCase);
            new_Cases = itemView.findViewById(R.id.total);
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
            case_txt.setTypeface(typeface);
            stateName.setTypeface(typeface);
            recovery.setTypeface(typeface);
            deathes.setTypeface(typeface);
            //date.setTypeface(typeface);
//            sum.setTypeface(typeface);
            caseActive.setTypeface(typeface);
            recoveryTxt.setTypeface(typeface);
            deathTxt.setTypeface(typeface);

            case_txt1.setTypeface(typeface);
            recovery1.setTypeface(typeface);
            deathes1.setTypeface(typeface);

            txtCase.setTypeface(typeface);
            txtRec.setTypeface(typeface);
            txtDe.setTypeface(typeface);
            old_total.setTypeface(typeface);
            new_Cases.setTypeface(typeface);

            //cardView = itemView.findViewById(R.id.card_grid);
        }
    }
}
