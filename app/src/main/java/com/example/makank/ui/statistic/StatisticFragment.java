package com.example.makank.ui.statistic;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.adapter.StatistcAdapter;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Statistc;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatisticFragment extends Fragment{// implements SwipeRefreshLayout.OnRefreshListener {
    private List<Statistc> statistcs;
    private RecyclerView recyclerView;
    private StatistcAdapter statistcAdapter;
    LinearLayout case_view,case_view2;
    TextView sumCase, sumRecov, sumDeath, sum_active, t1, t2, t3, t4, t1New, t2New, t3New,new_sumCase,new_sumRecov,new_sumDeath;
    Typeface typeface;
    LoadingDialog loadingDialog;
    Alert alert;
     int total = 0;
    int total2 = 0;
    int total3 = 0;
    int new_total =0;
    int new_total2 =0;
    int new_total3 =0;
    //SwipeRefreshLayout refreshLayout;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        recyclerView = view.findViewById(R.id.recycler_static);
        case_view = view.findViewById(R.id.casesView);
        case_view2 = view.findViewById(R.id.NewcasesView);
        sumCase = view.findViewById(R.id.sum_cases);
        sumRecov = view.findViewById(R.id.sum_recova);
        sumDeath = view.findViewById(R.id.sum_death);
        sum_active = view.findViewById(R.id.sumActive);

        new_sumCase = view.findViewById(R.id.Newsum_cases);
        new_sumRecov = view.findViewById(R.id.Newsum_recova);
        new_sumDeath = view.findViewById(R.id.Newsum_death);
        t1 = view.findViewById(R.id.txtS1);
        t2 = view.findViewById(R.id.txtS2);
        t3 = view.findViewById(R.id.txtS3);
        t4 = view.findViewById(R.id.txtS4);

        t1New = view.findViewById(R.id.NewtxtS1);
        t2New = view.findViewById(R.id.NewtxtS2);
        t3New = view.findViewById(R.id.NewtxtS3);

        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Hacen-Algeria.ttf");
        sumCase.setTypeface(typeface);
        sumRecov.setTypeface(typeface);
        sumDeath.setTypeface(typeface);
        sum_active.setTypeface(typeface);
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        t3.setTypeface(typeface);
        t4.setTypeface(typeface);
        t1New.setTypeface(typeface);
        t2New.setTypeface(typeface);
        t3New.setTypeface(typeface);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        statistcAdapter = new StatistcAdapter(statistcs, getContext());
        recyclerView.setAdapter(statistcAdapter);
        case_view.setVisibility(View.GONE);
        case_view2.setVisibility(View.GONE);
        alert = new Alert(getActivity());
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();

        //refreshLayout = view.findViewById(R.id.srStatic);
       // refreshLayout.setOnRefreshListener(this);
       // refreshLayout.setColorSchemeResources(R.color.color);
        //refreshLayout.setRefreshing(true);
        fetchData();
        return view;
    }

    public void fetchData() {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<Statistc>> call = apiService.getCases();
        call.enqueue(new Callback<List<Statistc>>() {
            @Override
            public void onResponse(Call<List<Statistc>> call, Response<List<Statistc>> response) {
               // refreshLayout.setRefreshing(false);
                loadingDialog.dismissDialog();
                if (response.body() != null) {
                    statistcs = response.body();

                    for (int i = 0; i < statistcs.size(); i++) {
                        total += Integer.parseInt(statistcs.get(i).getCases_count());
                        sumCase.setText(Integer.toString(total));
                    }


                    for (int i = 0; i < statistcs.size(); i++) {
                        total2 += Integer.parseInt(statistcs.get(i).getRecovery_cases());
                        sumRecov.setText(Integer.toString(total2));

                    }

                    for (int i = 0; i < statistcs.size(); i++) {
                        total3 += Integer.parseInt(statistcs.get(i).getNew_Deaths());
                        sumDeath.setText(Integer.toString(total3));
                    }
                    int total4 = total - (total3 + total2);
                    sum_active.setText(Integer.toString(total4));
                    statistcAdapter.setCases(statistcs);
                    case_view.setVisibility(View.VISIBLE);
                    case_view2.setVisibility(View.VISIBLE);


                    for (int i = 0; i < statistcs.size(); i++) {
                        new_total += Integer.parseInt(statistcs.get(i).getLatest_cases().getNew_sure_cases());
                        new_sumCase.setText(Integer.toString(new_total));
                    }


                    for (int i = 0; i < statistcs.size(); i++) {
                        new_total2 += Integer.parseInt(statistcs.get(i).getLatest_cases().getRecovery_cases());
                        new_sumRecov.setText(Integer.toString(new_total2));

                    }

                    for (int i = 0; i < statistcs.size(); i++) {
                        new_total3 += Integer.parseInt(statistcs.get(i).getLatest_cases().getNew_Deaths());
                        new_sumDeath.setText(Integer.toString(new_total3));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Statistc>> call, Throwable t) {
               // refreshLayout.setRefreshing(false);
                loadingDialog.dismissDialog();
            }
        });
    }

   /* @Override
    public void onRefresh() {
        fetchData();
        refreshLayout.setRefreshing(false);
    }*/
}
