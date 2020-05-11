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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.adapter.DiseaseAdapter;
import com.example.makank.adapter.StateAdapter;
import com.example.makank.data.model.State;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StateActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    SearchView searchView;
    private List<State> states;
    private StateAdapter adapter;
    LoadingDialog loadingDialog;
    Alert alert;
    EditText editText;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        //   toolbar = findViewById(R.id.toolbar_custom);
        //  setActionBar(toolbar);
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);
        this.recyclerView = findViewById(R.id.state_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new StateAdapter(this, states);
        recyclerView.setAdapter(adapter);
        states = new ArrayList<>();
        fetchState();
        editText = findViewById(R.id.maare);

        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        editText.setTypeface(typeface);

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

    private void fetchState() {
        loadingDialog.startLoadingDialog();

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<State>> call = apiService.getState();
        call.enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(Call<List<State>> call, Response<List<State>> response) {

                loadingDialog.dismissDialog();
//                if (!response.isSuccessful()) {

                states = response.body();
                adapter.setStates(states);
            }

            @Override
            public void onFailure(Call<List<State>> call, Throwable t) {
                loadingDialog.dismissDialog();

                Toast.makeText(StateActivity.this, "" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

}