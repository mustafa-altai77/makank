package com.example.makank.ui.news;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
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

import com.example.makank.R;
import com.example.makank.data.ApiClient;
import com.example.makank.data.ApiInterface;
import com.example.makank.data.News;
import com.example.makank.ui.activity.DiseaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragment extends Fragment {
    SearchView searchView;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<News> newsList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView =  view.findViewById(R.id.recycler_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter = new NewsAdapter();
        recyclerView.setAdapter(newsAdapter);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
        progressDoalog.show();
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<News>> call = apiService.getNews();

        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                progressDoalog.dismiss();
                newsList = response.body();
                Log.d("TAG","Response = "+newsList);
                newsAdapter.setMovieList(getContext(),newsList);
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                progressDoalog.dismiss();
                Log.d("TAG","Response = "+t.toString());
            }
        });
        return view;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item, menu);

        SearchManager searchManager2 = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager2
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                newsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                newsAdapter.getFilter().filter(query);
                return false;
            }
        });
    }
}
