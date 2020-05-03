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
import com.example.makank.adapter.LocalAdapter;
import com.example.makank.data.model.Local;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

public class LocalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Local> locals;
    private LocalAdapter adapter;
    String city_id;
    String city_name;
    TextView txtCityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        //this.txtCityName = findViewById(R.id.txt_city_name);
        this.recyclerView = findViewById(R.id.local_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter = new LocalAdapter(this, locals);
        recyclerView.setAdapter(adapter);
        locals = new ArrayList<>();

        city_id =   getIntent().getStringExtra("city_id");
        city_name =   getIntent().getStringExtra("city_name");
        //txtCityName.setText(city_name);
        fetchLocal(city_id);

    }


    private void fetchLocal(String id){
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(LocalActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
        progressDoalog.show();
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<Local>> call = apiService.getLocal(id);
        call.enqueue(new Callback<List<Local>>() {
            @Override
            public void onResponse(Call<List<Local>> call, Response<List<Local>> response) {

                progressDoalog.dismiss();
//                if (!response.isSuccessful()) {

                locals = (ArrayList<Local>) response.body();
                adapter.setLocals(locals);
            }
            @Override
            public void onFailure(Call<List<Local>>call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(LocalActivity.this, "غير متصل بالشبكة" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
