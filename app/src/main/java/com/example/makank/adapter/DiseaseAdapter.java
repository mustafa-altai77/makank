package com.example.makank.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makank.R;
import com.example.makank.data.model.Disease;

import java.util.ArrayList;
import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder> {
    private Context context;
    private List<Disease> diseases;


    @NonNull
    @Override
    public DiseaseAdapter.DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view =  LayoutInflater.from(context).inflate(R.layout.disease_item, parent, false);
        return new DiseaseViewHolder(view);
    }

    public DiseaseAdapter(Context context, List<Disease> diseases) {
        this.context = context;
        this.diseases = diseases;
    }
    public void setDiseases(List<Disease> diseases) {
        this.diseases = new ArrayList<>();
        this.diseases = diseases;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull DiseaseAdapter.DiseaseViewHolder holder, int position) {
        holder.bind(diseases.get(position));
    }

    @Override
    public int getItemCount() {
        if(diseases != null){
            return diseases.size();
        } else {
            return 0;
        }
    }

    public class DiseaseViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        Typeface typeface;
        public DiseaseViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.disease_name);
            imageView = itemView.findViewById(R.id.image_disease);
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
            textView.setTypeface(typeface);
        }

        void bind(final Disease disease) {
            imageView.setVisibility(disease.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(disease.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    disease.setChecked(!disease.isChecked());
                    imageView.setVisibility(disease.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public List<Disease> getAll() {
        return diseases;
    }

    public List<Disease> getSelected() {
        List<Disease> selected = new ArrayList<>();
        for (int i = 0; i < diseases.size(); i++) {
            if (diseases.get(i).isChecked()) {
                selected.add(diseases.get(i));
            }
        }
        return selected;
    }

}
