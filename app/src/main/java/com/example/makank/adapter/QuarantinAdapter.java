package com.example.makank.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
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

public class QuarantinAdapter extends RecyclerView.Adapter<QuarantinAdapter.HospitalViewHolder> {
    private List<Hospital> hospitalsList;
    private List<Hospital> hospitalsListFiltered;
    TextView isCorona;
    CardView cardView;
    Alert alert;
    private Context context;

    public QuarantinAdapter() {
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
                    return QuarantinAdapter.this.hospitalsList.size();
                }

                @Override
                public int getNewListSize() {
                    return hospitalsList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return QuarantinAdapter.this.hospitalsList.get(oldItemPosition).getName() == hospitalsList.get(newItemPosition).getName();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    Hospital hospital = QuarantinAdapter.this.hospitalsList.get(oldItemPosition);

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
    public QuarantinAdapter.HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_item, parent, false);
        return new QuarantinAdapter.HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuarantinAdapter.HospitalViewHolder holder, int position) {
        final Hospital model = hospitalsList.get(position);
        holder.hospital_name.setText(hospitalsListFiltered.get(position).getName());
        holder.adress.setText(hospitalsListFiltered.get(position).getDesc_address());
        holder.bad.setText(hospitalsListFiltered.get(position).getBed_count());
        isCorona.setVisibility(View.VISIBLE);
        holder.bad.setVisibility(View.VISIBLE);
        holder.bed_num.setVisibility(View.VISIBLE);
        holder.imageView.setVisibility(View.VISIBLE);
        holder.moreM.setVisibility(View.GONE);

        if (model.getIs_corona().equals("0")) {

            isCorona.setText(context.getResources().getString(R.string.hospital_azl));

            holder.cardView.setVisibility(View.GONE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String details = hospitalsListFiltered.get(position).getDetails();
                alert=new Alert((Activity) context);
                alert.showHospitalDialog(details);
                //Toast.makeText(context, details+"", Toast.LENGTH_SHORT).show();
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
        TextView hospital_name, adress, bad, bed_num,moreM;
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
            moreM=itemView.findViewById(R.id.more);
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
            hospital_name.setTypeface(typeface);
            isCorona.setTypeface(typeface);
            bad.setTypeface(typeface);
            adress.setTypeface(typeface);
            bed_num.setTypeface(typeface);

        }
    }

}
