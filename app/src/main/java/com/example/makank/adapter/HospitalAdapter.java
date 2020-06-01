package com.example.makank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.makank.R;
import com.example.makank.data.model.Hospital;
import com.example.makank.data.model.News;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {
    private List<Hospital> hospitalsList;
    private List<Hospital> hospitalsListFiltered;
    TextView isCorona;
    private Context context;

    public HospitalAdapter() {
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
                    return HospitalAdapter.this.hospitalsList.size();
                }

                @Override
                public int getNewListSize() {
                    return hospitalsList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return HospitalAdapter.this.hospitalsList.get(oldItemPosition).getName() == hospitalsList.get(newItemPosition).getName();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    Hospital newNew = HospitalAdapter.this.hospitalsList.get(oldItemPosition);

                    Hospital oldNews = hospitalsList.get(newItemPosition);

                    return newNew.getName() == oldNews.getName();
                }
            });
            this.hospitalsList = hospitalsList;
            this.hospitalsListFiltered = hospitalsList;
            result.dispatchUpdatesTo(this);
        }
    }
    @NonNull
    @Override
    public HospitalAdapter.HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_item, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalAdapter.HospitalViewHolder holder, int position) {
        final Hospital model = hospitalsList.get(position);
        holder.hospital_name.setText(hospitalsListFiltered.get(position).getName());
        holder.adress.setText(hospitalsListFiltered.get(position).getDesc_address());
        holder.bad.setText(hospitalsListFiltered.get(position).getBed_count());
        isCorona.setText(isCorona.getText());
        if (model.getIs_corona().equals("1")){
            isCorona.setVisibility(View.VISIBLE);

        }
        else
            isCorona.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        if (hospitalsList != null) {
            return hospitalsListFiltered.size();
        } else {
            return 0;
        }    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    hospitalsListFiltered = hospitalsList;
                } else {
                    List<Hospital> filteredList = new ArrayList<>();
                    for (Hospital hospital : hospitalsList) {
                        if (hospital.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(hospital);
                        }
                    }
                    hospitalsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = hospitalsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                hospitalsListFiltered = (ArrayList<Hospital>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }
    public class HospitalViewHolder extends RecyclerView.ViewHolder {
        TextView hospital_name, adress, bad;
        public HospitalViewHolder(View view) {
            super(view);
            hospital_name = itemView.findViewById(R.id.hospital_name);
            adress = itemView.findViewById(R.id.hospital_address);
            isCorona = itemView.findViewById(R.id.isCorona);
            bad = itemView.findViewById(R.id.bad_count);

        }
    }
}