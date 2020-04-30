package com.example.makank.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.data.ApiClient;
import com.example.makank.data.ApiInterface;
import com.example.makank.data.City;
import com.example.makank.data.Local;
import com.example.makank.data.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceActivity extends AppCompatActivity {
     Button next;
     Spinner state_spin;
     Spinner city_spin;
     Spinner local_spin;
     String state;
     String city;
    String local;
    String na;


    ArrayAdapter<String> statAdapter ,cityAdapter ,localAdapter;
    private ArrayList<State> states;
    private ArrayList<City> cities;
    private ArrayList<Local> locals;

    ArrayList<String> state_name,state_id ,city_id,city_name,local_id,local_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        next = findViewById(R.id.login);
        state_spin = findViewById(R.id.state_spinner);
        city_spin = findViewById(R.id.city_spinner);
        local_spin = findViewById(R.id.local_spinner);

        states =new ArrayList<>();
        cities = new ArrayList<>();
        locals = new ArrayList<>();

        state_name = new ArrayList<>();
        state_id = new ArrayList<>();

        city_name = new ArrayList<>();
        city_id = new ArrayList<>();

        local_name = new ArrayList<>();
        local_id = new ArrayList<>();

        statAdapter = new ArrayAdapter<>(PlaceActivity .this, R.layout.support_simple_spinner_dropdown_item, state_name);
        state_spin.setAdapter(statAdapter);
        cityAdapter = new ArrayAdapter<>(PlaceActivity .this, R.layout.support_simple_spinner_dropdown_item, city_name);
        city_spin.setAdapter(cityAdapter);
        localAdapter = new ArrayAdapter<>(PlaceActivity .this, R.layout.support_simple_spinner_dropdown_item, local_name);
        local_spin.setAdapter(localAdapter);

        fetchState();

        state_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                 state =   state_id.get((int) id);
                fetchCity(state);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here
            }
//
        });

            city_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                city =   city_id.get((int) id);
                fetchLocal(city);
            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here

            }
//
        });

        local_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                local =   local_id.get((int) id);
                na = local_name.get(position);

            }

            @Override

            public void onNothingSelected(AdapterView<?> adapterView) {

                // DO Nothing here

            }
//
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PlaceActivity.this, RegisterActivity.class);
                intent.putExtra("local_id",local);
                intent.putExtra("local_name",na);

                startActivity(intent);
                finish();
            }
        });
    }

    private void fetchState(){
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(PlaceActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
//        progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.show();
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<State>> call = apiService.getState();
        call.enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(Call<List<State>> call, Response <List<State>>response) {

                progressDoalog.dismiss();
//                if (!response.isSuccessful()) {

                    states = (ArrayList<State>) response.body();

                for (int i = 0; i < states.size(); i++) {

                        state_name.add(states.get(i).getName());
                        state_id.add(states.get(i).getId());

                    }
                statAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<State>>call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(PlaceActivity.this, "" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchCity(String id){
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(PlaceActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
//        progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.show();
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<City>> call = apiService.getCity(id);
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response <List<City>>response) {

                progressDoalog.dismiss();
//                if (!response.isSuccessful()) {
                 city_name.clear();
                 city_id.clear();
                 cities = (ArrayList<City>) response.body();
                for (int i = 0; i < cities.size(); i++) {

                    city_name.add(cities.get(i).getName());
                    city_id.add(cities.get(i).getId());
                }

                cityAdapter.notifyDataSetChanged();

            }
            @Override
            public void onFailure(Call<List<City>>call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(PlaceActivity.this, "" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchLocal(String id){
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(PlaceActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
        progressDoalog.show();
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<Local>> call = apiService.getLocal(id);
        call.enqueue(new Callback<List<Local>>() {
            @Override
            public void onResponse(Call<List<Local>> call, Response <List<Local>>response) {

                progressDoalog.dismiss();
//                if (!response.isSuccessful()) {
                local_name.clear();
                local_id.clear();
                locals = (ArrayList<Local>) response.body();
                for (int i = 0; i < locals.size(); i++) {
                    local_name.add(locals.get(i).getName());
                    local_id.add(locals.get(i).getId());
                }

                localAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Local>>call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(PlaceActivity.this, "غير متصل بالشبكة" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}