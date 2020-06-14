package com.example.makank.ui.profile;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.adapter.GroupAdapter;
import com.example.makank.data.model.Member;
import com.example.makank.ui.activity.AddGroupActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;

public class GroupFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ImageView imageView;
    SearchView searchView;
    private TextView notfound;
    LoadingDialog loadingDialog;
    ArrayList<String> group_id;
    ArrayList<String> member_name;
    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private ArrayList<Member> members;
    Alert alert;
    Typeface font;
    SwipeRefreshLayout refreshLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alert = new Alert(getActivity());
        loadingDialog = new LoadingDialog(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        members = new ArrayList<>();
        group_id = new ArrayList<>();
        member_name = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        imageView = view.findViewById(R.id.add_member);
        notfound = view.findViewById(R.id.not_fond);
        notfound.setVisibility(View.GONE);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Hacen-Algeria.ttf");
        notfound.setTypeface(font);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddGroupActivity.class);
                startActivity(i);
            }
        });

        recyclerView = view.findViewById(R.id.member_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        groupAdapter = new GroupAdapter();
        refreshLayout = view.findViewById(R.id.srGroup);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.color);
        loadingDialog.startLoadingDialog();
        refreshLayout.setRefreshing(true);
        recyclerView.setAdapter(groupAdapter);
        getGroupMemeber();
        // loadingDialog.startLoadingDialog();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        members = new ArrayList<>();
    }

    private void getGroupMemeber() {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String my_id = sharedPreferences.getString(USER_ID, "id");
        final String token = sharedPreferences.getString(TOKEN, "token");
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<Member>> call = apiService.getMygroup(token,my_id);
        try {


            call.enqueue(new Callback<List<Member>>() {
                @Override
                public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                    refreshLayout.setRefreshing(false);
                    loadingDialog.dismissDialog();
                    imageView.setVisibility(View.VISIBLE);

                    if (response.isSuccessful()) {
                        //loadingDialog.dismissDialog();
                        members = new ArrayList<>();

                        if (response.code() == 200) {
                            members = (ArrayList<Member>) response.body();
                            if (!members.isEmpty()) {

                                for (int i = 0; i < members.size(); i++) {
                                    String g = members.get(i).getGroupID();
                                    member_name.add(members.get(i).getFirst_name());
                                    if (!my_id.equals(g)) {
                                        imageView.setVisibility(View.GONE);
                                    }
                                }
                                groupAdapter.setMovieList(getContext(), members);
                            } else
                                notfound.setVisibility(View.VISIBLE);
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<Member>> call, Throwable t) {
                    refreshLayout.setRefreshing(false);
                    loadingDialog.dismissDialog();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        EditText searchEditText = searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Hacen-Algeria.ttf");
        searchEditText.setTypeface(font);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                groupAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                groupAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @Override
    public void onRefresh() {
        getGroupMemeber();
        refreshLayout.setRefreshing(false);
    }
}
