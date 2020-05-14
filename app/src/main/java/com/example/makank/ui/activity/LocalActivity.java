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
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.adapter.LocalAdapter;
import com.example.makank.data.model.Local;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    SearchView searchView;
    private List<Local> locals;
    private LocalAdapter adapter;
    String city_id;
    String city_name;
    LoadingDialog loadingDialog;
    Alert alert;
    EditText editText;
    Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);
        this.recyclerView = findViewById(R.id.city_recycler);
        this.recyclerView = findViewById(R.id.local_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter = new LocalAdapter(this, locals);
        recyclerView.setAdapter(adapter);
        locals = new ArrayList<>();
        editText = findViewById(R.id.maare);

        city_id =   getIntent().getStringExtra("city_id");
        city_name =   getIntent().getStringExtra("city_name");
        //txtCityName.setText(city_name);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        editText.setTypeface(typeface);

        fetchLocal(city_id);
        editText = findViewById(R.id.maare);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = editText.getText().toString().toLowerCase(Locale.getDefault());
                adapter.getFilter().filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fetchLocal(String id){
        loadingDialog.startLoadingDialog();

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<Local>> call = apiService.getLocal(id);
        call.enqueue(new Callback<List<Local>>() {
            @Override
            public void onResponse(Call<List<Local>> call, Response<List<Local>> response) {

                loadingDialog.dismissDialog();
//                if (!response.isSuccessful()) {

                locals =  response.body();
                adapter.setLocals(locals);
            }
            @Override
            public void onFailure(Call<List<Local>>call, Throwable t) {
                loadingDialog.dismissDialog();

               alert.showWarningDialog();
            }
        });
    }

}
