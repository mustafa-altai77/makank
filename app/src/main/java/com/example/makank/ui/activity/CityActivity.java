package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.R;
import com.example.makank.adapter.CityAdapter;
import com.example.makank.adapter.DiseaseAdapter;
import com.example.makank.adapter.StateAdapter;
import com.example.makank.data.model.City;
import com.example.makank.data.model.Disease;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<City> cities;
    private CityAdapter adapter;
    String state_id;
    String state_name;
    TextView txtStateName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        //this.txtStateName = findViewById(R.id.txt_state_name);
        this.recyclerView = findViewById(R.id.city_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter = new CityAdapter(this, cities);
        recyclerView.setAdapter(adapter);
        cities = new ArrayList<>();

        state_id = getIntent().getStringExtra("state_id");
        state_name =   getIntent().getStringExtra("state_name");
        //txtStateName.setText(state_name);
        fetchCity(state_id);


    }

    private void fetchCity(String id){
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(CityActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
//        progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.show();
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<City>> call = apiService.getCity(id);
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                progressDoalog.dismiss();
//                if (!response.isSuccessful()) {
                cities = (ArrayList<City>) response.body();

                adapter.setCities(cities);

            }
            @Override
            public void onFailure(Call<List<City>>call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(CityActivity.this, "" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
