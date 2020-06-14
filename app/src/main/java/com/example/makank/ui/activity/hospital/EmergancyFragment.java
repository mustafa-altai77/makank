package com.example.makank.ui.activity.hospital;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.adapter.EmergancyAdapter;
import com.example.makank.data.model.Hospital;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmergancyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private EmergancyAdapter emergancyAdapter;
    private List<Hospital> hospitalsList;
    private RecyclerView recyclerView;
   // private TextView notfound;
    LoadingDialog loadingDialog;
    Alert alert;
    SwipeRefreshLayout refreshLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alert = new Alert(getActivity());
        loadingDialog = new LoadingDialog(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_emergancy, container, false);
        recyclerView = view.findViewById(R.id.emer_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emergancyAdapter = new EmergancyAdapter();
        recyclerView.setAdapter(emergancyAdapter);
//        notfound = view.findViewById(R.id.not_fond);
//        notfound.setVisibility(View.GONE);
        refreshLayout = view.findViewById(R.id.srEme);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.color);
        loadingDialog.startLoadingDialog();
        refreshLayout.setRefreshing(true);
        fetchData();
        return view;
    }
    public void fetchData()
    {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<Hospital>> call = apiService.getHospitals();
        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse
                    (Call<List<Hospital>> call, Response<List<Hospital>> response) {
                refreshLayout.setRefreshing(false);
                loadingDialog.dismissDialog();
                hospitalsList = response.body();
                if (!hospitalsList.isEmpty()) {
                    Log.d("TAG", "Response = " + hospitalsList);
                    emergancyAdapter.setHospitalsList(getContext(), hospitalsList);
                } //else
                   // notfound.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                loadingDialog.dismissDialog();
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }

    @Override
    public void onRefresh() {
        fetchData();
        refreshLayout.setRefreshing(false);
    }
}
