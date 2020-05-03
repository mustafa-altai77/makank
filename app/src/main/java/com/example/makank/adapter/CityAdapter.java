package com.example.makank.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.makank.R;
import com.example.makank.data.model.City;
import com.example.makank.ui.activity.LocalActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    private Context context;
    private List<City> cities;


    @NonNull
    @Override
    public CityAdapter.CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.city_item, parent, false);
        return new CityAdapter.CityViewHolder(view);
    }


    public CityAdapter(Context context, List<City> Cities) {
        this.context = context;
        this.cities = Cities;
    }
    public void setCities(List<City> cities) {
        this.cities = new ArrayList<>();
        this.cities = cities;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull CityAdapter.CityViewHolder holder, int position) {
        holder.bind(cities.get(position));
    }

    @Override
    public int getItemCount() {
        if(cities != null){
            return cities.size();
        } else {
            return 0;
        }
    }

    public  class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCityID;
        private TextView txtCityName;
        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCityID = itemView.findViewById(R.id.txt_city_id);
            txtCityName = itemView.findViewById(R.id.txt_city_name);
        }

        void bind(final City City) {
            txtCityID.setText(City.getId());
            txtCityName.setText(City.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView txtCityID =view.findViewById(R.id.txt_city_id);
                    TextView txtCityName =view.findViewById(R.id.txt_city_name);
                    String CityID = txtCityID.getText().toString();
                    String CityName = txtCityName.getText().toString();

                    Intent i = new Intent(context, LocalActivity.class);
                    i.putExtra("city_id",CityID);
                    i.putExtra("city_name",CityName);
                    context.startActivity(i);

                }
            });
        }
    }

    public List<City> getAll() {
        return cities;
    }

/*    public List<City> getSelected() {
        List<City> selected = new ArrayList<>();
        for (int i = 0; i < City.size(); i++) {
            if (City.get(i).isChecked()) {
                selected.add(City.get(i));
            }
        }
        return selected;
    }*/

}
