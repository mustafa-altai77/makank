package com.example.makank.ui.statistic;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.R;
import com.example.makank.data.ApiClient;
import com.example.makank.data.ApiInterface;
import com.example.makank.data.State;
import com.example.makank.data.Statistc;
import com.example.makank.ui.activity.PlaceActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatisticFragment extends Fragment {
      Statistc statistcs;
    TextView case_red ,case_yellow,case_daeth;

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
        case_daeth = view.findViewById(R.id.case_count_death);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
//        progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.show();
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Statistc> call = apiService.getCases();
        call.enqueue(new Callback<Statistc>() {
            @Override
            public void onResponse(Call<Statistc> call, Response<Statistc> response) {

                progressDoalog.dismiss();
//                if (!response.isSuccessful()) {
//                  statistcs = new ArrayList<>();
                statistcs =  response.body();
//                for (int i = 0; i < statistcs.size(); i++) {

                    case_red.setText(Integer.toString(statistcs.getRed()));
                    case_yellow.setText(Integer.toString(statistcs.getYellow()));
                    case_daeth.setText(Integer.toString(statistcs.getDeath()));

              //  }
            }
            @Override
            public void onFailure(Call<Statistc>call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(getContext(), "" + t, Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    private void fetchCases(){

    }
}
