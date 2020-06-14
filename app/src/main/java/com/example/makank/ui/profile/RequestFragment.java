package com.example.makank.ui.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.adapter.RequestAdapter;
import com.example.makank.data.model.Request;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;

public class RequestFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private List<Request> requestList;
    private TextView notfound;
    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    Typeface font;
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
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        recyclerView = view.findViewById(R.id.recycler_request);
        notfound = view.findViewById(R.id.not_fond);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        requestAdapter = new RequestAdapter(requestList, getContext());
        recyclerView.setAdapter(requestAdapter);
        notfound.setVisibility(View.GONE);
       // loadingDialog.startLoadingDialog();
         font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Hacen-Algeria.ttf");
         notfound.setTypeface(font);

        refreshLayout = view.findViewById(R.id.srRequest);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.color);


        loadingDialog.startLoadingDialog();
        refreshLayout.setRefreshing(true);
        fetchData();

        return view;
    }
public void fetchData()
{
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    final String id = sharedPreferences.getString(USER_ID, "id");
    ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
    final String token = sharedPreferences.getString(TOKEN, "token");
    Call<List<Request>> call = apiService.getRequst(token,id);

    call.enqueue(new Callback<List<Request>>() {
        @Override
        public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
            //loadingDialog.dismissDialog();
            refreshLayout.setRefreshing(false);
            loadingDialog.dismissDialog();
            requestList = response.body();
            if (!requestList.isEmpty()) {
                Log.d("TAG", "Response = " + requestList);
                requestAdapter.setRequestList(requestList);
            }
            else
                notfound.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailure(Call<List<Request>> call, Throwable t) {
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
