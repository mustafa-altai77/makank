package com.example.makank.ui.statistic;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Statistc;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatisticFragment extends Fragment {
    Statistc statistcs;
    TextView case_red, case_yellow, caseNew_daeth, tx1, tx2, tx3, tx4, tx5, tx6, tx7, newRecover, newRed, totalDeath, totalRecover;
    Typeface typeface;
    LoadingDialog loadingDialog;
    Alert alert;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        case_red = view.findViewById(R.id.case_count_red);
        case_yellow = view.findViewById(R.id.case_count_yellow);
        caseNew_daeth = view.findViewById(R.id.case_new_death);

        newRecover = view.findViewById(R.id.new_recover);
        newRed = view.findViewById(R.id.newRed);
        totalDeath = view.findViewById(R.id.case_count_death);
        totalRecover = view.findViewById(R.id.total_case_recover);
        tx1 = view.findViewById(R.id.txt11);
        tx2 = view.findViewById(R.id.txt22);
        tx3 = view.findViewById(R.id.txt33);
        tx4 = view.findViewById(R.id.txt44);
        tx5 = view.findViewById(R.id.txt55);
        tx6 = view.findViewById(R.id.txt66);
        tx7 = view.findViewById(R.id.txt77);

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Hacen-Algeria.ttf");
        case_red.setTypeface(typeface);
        case_yellow.setTypeface(typeface);
        caseNew_daeth.setTypeface(typeface);
        newRecover.setTypeface(typeface);
        newRed.setTypeface(typeface);
        totalDeath.setTypeface(typeface);
        totalRecover.setTypeface(typeface);
        tx1.setTypeface(typeface);
        tx2.setTypeface(typeface);
        tx3.setTypeface(typeface);
        tx4.setTypeface(typeface);
        tx5.setTypeface(typeface);
        tx6.setTypeface(typeface);
        tx7.setTypeface(typeface);

 /*       final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
//        progressDoalog.setTitle("PzrogressDialog bar example");
        progressDoalog.show();
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);*/

        alert = new Alert(getActivity());
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Statistc> call = apiService.getCases();
        call.enqueue(new Callback<Statistc>() {
            @Override
            public void onResponse(Call<Statistc> call, Response<Statistc> response) {
                loadingDialog.dismissDialog();
                //progressDoalog.dismiss();
//                if (!response.isSuccessful()) {
//                  statistcs = new ArrayList<>();
                if(response.body() != null) {
                    statistcs = response.body();
//                for (int i = 0; i < statistcs.size(); i++) {

                    newRed.setText(statistcs.getNew_sure_cases());
                    case_yellow.setText(statistcs.getSuspected_cases()+"");
                    caseNew_daeth.setText(statistcs.getNew_Deaths()+"");
                    newRecover.setText(statistcs.getRecovery_cases());
                    totalRecover.setText(statistcs.getRecovery_cases());
                    totalDeath.setText(statistcs.getSum_Deaths());
                    case_red.setText(statistcs.getSum_cases());



                    //  }
                }
            }

            @Override
            public void onFailure(Call<Statistc> call, Throwable t) {
                //   progressDoalog.dismiss();
                loadingDialog.dismissDialog();

                //Toast.makeText(getContext(), "" + t, Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    private void fetchCases() {

    }
}
