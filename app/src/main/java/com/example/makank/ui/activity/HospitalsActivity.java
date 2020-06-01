package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;

import com.example.makank.adapter.HospitalAdapter;
import com.example.makank.adapter.NewsAdapter;
import com.example.makank.data.model.Hospital;
import com.example.makank.data.model.News;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class HospitalsActivity extends AppCompatActivity {
    private List<Hospital> hospitalsList;
    private RecyclerView recyclerView;
    private TextView notfound,isCorona;
    private HospitalAdapter hospitalAdapter;
    LoadingDialog loadingDialog;
    Alert alert;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        recyclerView =  findViewById(R.id.recycler_hospital);
        notfound = findViewById(R.id.not_fond);
        notfound.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hospitalAdapter = new HospitalAdapter();
        recyclerView.setAdapter(hospitalAdapter);
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<Hospital>> call = apiService.getHospitals();
        loadingDialog.startLoadingDialog();
        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse
                    (Call<List<Hospital>> call, Response<List<Hospital>> response) {
                // progressDoalog.dismiss();
                loadingDialog.dismissDialog();
                hospitalsList = response.body();
                if (!hospitalsList.isEmpty()) {

                    Log.d("TAG", "Response = " + hospitalsList);
                    hospitalAdapter.setHospitalsList(getApplicationContext(), hospitalsList);
                } else
                    notfound.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t) {
                //    progressDoalog.dismiss();
                loadingDialog.dismissDialog();
                Log.d("TAG", "Response = " + t.toString());
            }
        });

    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item, menu);

        SearchManager searchManager2 = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager2
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hospitalAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                hospitalAdapter.getFilter().filter(query);
                return false;
            }
        });
    }
}