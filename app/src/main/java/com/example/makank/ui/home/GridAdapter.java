package com.example.makank.ui.home;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makank.R;
import com.example.makank.ui.activity.Steper;
import com.example.makank.ui.activity.SendNotifActivity;
import com.example.makank.ui.activity.ContactActivity;
import com.example.makank.ui.Test.TestActivity;
import com.example.makank.ui.activity.VolunteerActivity;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CategoryViewHolider> {
    private Context context;
    private List<Home> items;

    public GridAdapter(List<Home> items, Context context) {
        this.context = context;
        this.items = items;

    }

    @NonNull
    @Override
    public CategoryViewHolider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_item, parent, false);

        return new CategoryViewHolider(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolider holder, final int position) {
        final Home item = items.get(position);
        holder.categoryText.setText(item.getName());
        holder.categoryImage.setImageResource(item.image);
        holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getId() == 1) {
                    Intent intent = new Intent(context, ContactActivity.class);
                    context.startActivity(intent);
                } else if (item.getId() == 5) {
                    Intent intent = new Intent(context, VolunteerActivity.class);
                    context.startActivity(intent);
                } else if (item.getId() == 2) {
                    Intent intent = new Intent(context, SendNotifActivity.class);
                    context.startActivity(intent);
                } else if (item.getId() == 6) {
                    Intent intent = new Intent(context, Steper.class);
                    context.startActivity(intent);
                }
                if (item.getId() == 3) {
                    Intent intent = new Intent(context, TestActivity.class);
                    context.startActivity(intent);
                }
                return;
            }

        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CategoryViewHolider extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryText;
        CardView cardView;
        Typeface typeface;

        @SuppressLint("ResourceAsColor")
        public CategoryViewHolider(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.grid_image);
            categoryText = itemView.findViewById(R.id.grid_text);
            categoryText = itemView.findViewById(R.id.grid_text);
            cardView = itemView.findViewById(R.id.card_grid);
            categoryImage.setColorFilter(Color.argb(255, 255, 0, 0));
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
            categoryText.setTypeface(typeface);
        }
    }
}
