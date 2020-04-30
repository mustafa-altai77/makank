package com.example.makank.ui.profile;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.makank.R;
import com.example.makank.data.ApiClient;
import com.example.makank.data.ApiInterface;
import com.example.makank.data.Member;
import com.example.makank.data.News;
import com.example.makank.ui.activity.AddGroupActivity;
import com.example.makank.ui.news.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;

public class GroupFragment extends Fragment {
    ImageView imageView;
    SearchView searchView;
    ArrayList<String> group_id;
    ArrayList<String> member_name;
    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private ArrayList<Member> members;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddGroupActivity.class);
                startActivity(i);
            }
        });

        recyclerView =  view.findViewById(R.id.member_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        groupAdapter = new GroupAdapter();

        recyclerView.setAdapter(groupAdapter);
        getGroupMemeber();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        members = new ArrayList<>();
    }
    private void getGroupMemeber(){
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String my_id = sharedPreferences.getString(USER_ID, "id");

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<Member>> call = apiService.getMygroup(my_id);
        //progressDoalog.show();
        call.enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                imageView.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                   /// progressDoalog.dismiss();
                    members = new ArrayList<>();

                    if (response.code()==200){
                        members = (ArrayList<Member>) response.body();
                        for (int i = 0; i < members.size(); i++) {
                         String g = members.get(i).getGroupID();
                            member_name.add(members.get(i).getFirst_name());
                            if (!my_id.equals(g)) {
                                imageView.setVisibility(View.GONE);
                                Toast.makeText(getContext(),g+ "", Toast.LENGTH_SHORT).show();
                            }
                            groupAdapter.setMovieList(getContext(),members);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
               // progressDoalog.dismiss();

                Toast.makeText(getContext(), "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
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
}
