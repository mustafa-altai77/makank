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
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.makank.R;
import com.example.makank.adapter.DiseaseAdapter;
import com.example.makank.adapter.StateAdapter;
import com.example.makank.data.model.State;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

public class StateActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    SearchView searchView;
    private List<State> states;
    private StateAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        //   toolbar = findViewById(R.id.toolbar_custom);
        //  setActionBar(toolbar);
        this.recyclerView = findViewById(R.id.state_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new StateAdapter(this, states);
        recyclerView.setAdapter(adapter);
        states = new ArrayList<>();
        fetchState();
    }

    private void fetchState() {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(StateActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
//        progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.show();
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<State>> call = apiService.getState();
        call.enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(Call<List<State>> call, Response<List<State>> response) {

                progressDoalog.dismiss();
//                if (!response.isSuccessful()) {

                states = (ArrayList<State>) response.body();
                adapter.setStates(states);
            }

            @Override
            public void onFailure(Call<List<State>> call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(StateActivity.this, "" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_two, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search2)
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
