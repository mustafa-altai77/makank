package com.example.makank.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makank.Alert;
import com.example.makank.R;
import com.example.makank.data.model.Hospital;

import java.util.List;

public class EmergancyAdapter extends RecyclerView.Adapter<EmergancyAdapter.HospitalViewHolder> {
    private List<Hospital> hospitalsList;
    private List<Hospital> hospitalsListFiltered;
    // TextView isCorona;
    CardView cardView;
    Alert alert;
    Typeface typeface;

    private Context context;

    public EmergancyAdapter() {
        this.context = context;
        this.hospitalsList = hospitalsList;
    }

    public void setHospitalsList(Context context, List<Hospital> hospitalsList) {
        this.context = context;
        if (this.hospitalsList == null) {
            this.hospitalsList = hospitalsList;
            this.hospitalsListFiltered = hospitalsList;
            notifyItemChanged(0, hospitalsListFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return EmergancyAdapter.this.hospitalsList.size();
                }

                @Override
                public int getNewListSize() {
                    return hospitalsList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return EmergancyAdapter.this.hospitalsList.get(oldItemPosition).getName() == hospitalsList.get(newItemPosition).getName();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    Hospital hospital = EmergancyAdapter.this.hospitalsList.get(oldItemPosition);

                    Hospital hospital1 = hospitalsList.get(newItemPosition);

                    return hospital.getName() == hospital1.getName();
                }
            });
            this.hospitalsList = hospitalsList;
            this.hospitalsListFiltered = hospitalsList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public EmergancyAdapter.HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_item, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmergancyAdapter.HospitalViewHolder holder, int position) {
        final Hospital model = hospitalsList.get(position);
        holder.hospital_name.setText(hospitalsListFiltered.get(position).getName());
        holder.adress.setText(hospitalsListFiltered.get(position).getDesc_address());
        // holder.bad.setText(hospitalsListFiltered.get(position).getBed_count());
        //  holder.isCorona.setVisibility(View.GONE);
        holder.isCorona.setVisibility(View.GONE);
        holder.bed_num.setVisibility(View.GONE);
        holder.imageView.setVisibility(View.GONE);
        holder.bad.setVisibility(View.GONE);

        if (model.getIs_corona().equals("1")) {

            holder.moreM.setVisibility(View.VISIBLE);

            holder.cardView.setVisibility(View.GONE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String details = hospitalsListFiltered.get(position).getDetails();
                /*if (details == null) {
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
                    Toast toast = Toast.makeText(context, context.getResources().getString(R.string.detailsHospital), Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.BLACK);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
                    text.setTextColor(Color.RED);
                    text.setTextSize(Integer.valueOf(20));
                    text.setTypeface(typeface);
                    if (text != null) text.setGravity(Gravity.CENTER);
                    toast.show();
                } else {*/
                    alert = new Alert((Activity) context);
                    alert.showHospitalDialog(details);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (hospitalsList != null) {
            return hospitalsListFiltered.size();
        } else {
            return 0;
        }
    }

    public class HospitalViewHolder extends RecyclerView.ViewHolder {
        TextView hospital_name, adress, bad, bed_num, isCorona, moreM;
        ImageView imageView;
        LinearLayout linearLayout;
        CardView cardView;
        RelativeLayout relativeLayout;
        Typeface typeface;

        public HospitalViewHolder(View view) {
            super(view);
            hospital_name = itemView.findViewById(R.id.hospital_name);
            adress = itemView.findViewById(R.id.hospital_address);
            isCorona = itemView.findViewById(R.id.isCorona);
            bad = itemView.findViewById(R.id.bad_count);
            linearLayout = itemView.findViewById(R.id.linear_left);
            relativeLayout = itemView.findViewById(R.id.relative_center);
            bed_num = itemView.findViewById(R.id.number_bed);
            cardView = itemView.findViewById(R.id.card);
            imageView = itemView.findViewById(R.id.img);
            moreM = itemView.findViewById(R.id.more);
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
            hospital_name.setTypeface(typeface);
            isCorona.setTypeface(typeface);
            bad.setTypeface(typeface);
            adress.setTypeface(typeface);
            bed_num.setTypeface(typeface);
            moreM.setTypeface(typeface);

        }
    }

}
