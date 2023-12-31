package com.example.makank.ui.home;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makank.Alert;
import com.example.makank.R;
import com.example.makank.data.model.Person;
import com.example.makank.ui.activity.HospitalsActivity;
import com.example.makank.ui.activity.Steper;
import com.example.makank.ui.activity.SendNotifActivity;
import com.example.makank.ui.activity.ContactActivity;
import com.example.makank.ui.Test.TestActivity;
import com.example.makank.ui.activity.VolunteerActivity;

import java.util.List;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.TOKEN;
import static com.example.makank.SharedPref.mCtx;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CategoryViewHolider> {
    private Context context;
    private List<Home> items;
    Alert alert;

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
        alert = new Alert((Activity) context);
        final Home item = items.get(position);
        holder.categoryText.setText(item.getName());
        holder.categoryImage.setImageResource(item.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(TOKEN, "token");

            @Override
            public void onClick(View v) {

                if (item.getId() == 1) {
                    if (token.equals("token")) {
                        alert.showRegisterDialog();
                    } else {
                        Intent intent = new Intent(context, ContactActivity.class);
                        context.startActivity(intent);
                    }
                } else if (item.getId() == 5) {
                    if (token.equals("token")) {
                        alert.showRegisterDialog();
                    } else {
                        Intent intent = new Intent(context, VolunteerActivity.class);
                        context.startActivity(intent);
                    }
                } else if (item.getId() == 2) {
                    if (token.equals("token")) {
                        alert.showRegisterDialog();
                    } else {
                        Intent intent = new Intent(context, SendNotifActivity.class);
                        context.startActivity(intent);
                    }
                } else if (item.getId() == 6) {
                    Intent intent = new Intent(context, Steper.class);
                    context.startActivity(intent);
                }
                if (item.getId() == 3) {
                    Intent intent = new Intent(context, TestActivity.class);
                    context.startActivity(intent);
                }
                if (item.getId() == 4) {
                    Intent inten = new Intent(context, HospitalsActivity.class);
                   context.startActivity(inten);
                    /*Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
                    SpannableString efr = new SpannableString(context.getResources().getString(R.string.soon));
                    //TODO:
                    //cause an exception
                    //efr.setSpan(new TypefaceSpan(typeface),0,efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Toast toast = Toast.makeText(context, efr, Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.TRANSPARENT);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
                    text.setTextColor(Color.RED);
                    text.setTextSize(Integer.valueOf(20));
                    if (text != null) text.setGravity(Gravity.CENTER);
                    toast.show();*/
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
