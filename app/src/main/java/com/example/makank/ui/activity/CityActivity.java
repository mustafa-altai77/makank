package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
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
    SearchView searchView;
    private List<City> cities;
    private CityAdapter adapter;
    String state_id;
    String state_name;
    TextView txtStateName;
    LoadingDialog loadingDialog;
    Alert alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        //this.txtStateName = findViewById(R.id.txt_state_name);
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);
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
        loadingDialog.startLoadingDialog();

//

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<City>> call = apiService.getCity(id);
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                loadingDialog.dismissDialog();
//                if (!response.isSuccessful()) {
                cities = (ArrayList<City>) response.body();

                adapter.setCities(cities);

            }
            @Override
            public void onFailure(Call<List<City>>call, Throwable t) {
                loadingDialog.dismissDialog();

                Toast.makeText(CityActivity.this, "" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_item, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
}
