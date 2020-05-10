package com.example.makank.ui.contact;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.adapter.SeenAdapter;
import com.example.makank.data.model.Member;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;


public class ContactFragment extends Fragment {
    SearchView searchView;
    private RecyclerView recyclerView;
    private SeenAdapter seenAdapter;
    private List<Member> memberList;
    TextView member;
    LoadingDialog loadingDialog;
    Alert alert;
    LinearLayout layout;
    Typeface typeface;

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
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        recyclerView = view.findViewById(R.id.recycler_contact);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        seenAdapter = new SeenAdapter();
        recyclerView.setAdapter(seenAdapter);
        member = view.findViewById(R.id.mermberInC);
        layout = view.findViewById(R.id.layoutSeen);
        layout.setVisibility(View.INVISIBLE);
        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Hacen-Algeria.ttf");
        member.setTypeface(typeface);


        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String my_id = sharedPreferences.getString(USER_ID, "id");

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<Member>> call = apiService.getMyseen(my_id);
        loadingDialog.startLoadingDialog();

        call.enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if (response.isSuccessful()) {
                    loadingDialog.dismissDialog();
                    memberList = response.body();
                    layout.setVisibility(View.VISIBLE);
                    seenAdapter.setMovieList(getContext(), memberList);
                }
//                Toast.makeText(getContext(), ""+memberList.size(), Toast.LENGTH_SHORT).show();
            member.setText("الأشخاص الذين تم التواصل معهم : "+memberList.size());
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                loadingDialog.dismissDialog();
                layout.setVisibility(View.INVISIBLE);
                alert.showAlertError("تــأكد من إتصالك بالإنترنت");
                //Toast.makeText(getContext(), "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item, menu);

        SearchManager searchSeen = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchSeen
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                seenAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                seenAdapter.getFilter().filter(query);
                return false;
            }
        });

    }

}
