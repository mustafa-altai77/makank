package com.example.makank.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makank.R;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class GridFragment extends Fragment {
    private  List<Home> grid_list = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridAdapter Gadapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grid, container, false);
        recyclerView = v.findViewById(R.id.grid_recycler);
        grid_list.clear();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        grid_list.add(new Home(1,getResources().getString(R.string.contact_screen),R.drawable.communication));
        grid_list.add(new Home(2,getResources().getString(R.string.notification_screen),R.drawable.notification));
        grid_list.add(new Home(3,getResources().getString(R.string.testing_screen),R.drawable.qualification));
        grid_list.add(new Home(4,getResources().getString(R.string.hospital),R.drawable.ic_hospital));
        grid_list.add(new Home(5,getResources().getString(R.string.volunteer_screen),R.drawable.volunteer));
        grid_list.add(new Home(6,getResources().getString(R.string.tips_screen),R.drawable.socialcare));
         Gadapter = new GridAdapter(grid_list,getContext());
        recyclerView.setAdapter(Gadapter);

        return v;

    }



}
