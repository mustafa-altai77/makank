package com.example.makank.adapter;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.makank.R;
import com.example.makank.data.model.Local;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder> implements Filterable {
    private Context context;
    private List<Local> locals;
    private List<Local> allLocals;
    View.OnClickListener listener;


    @NonNull
    @Override
    public LocalAdapter.LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.local_item, parent, false);
        return new LocalAdapter.LocalViewHolder(view);
    }


    public LocalAdapter(Context context, List<Local> Locals, View.OnClickListener listener) {
        this.context = context;
        this.locals = Locals;
        this.allLocals = Locals;
        this.listener = listener;
    }
    public void setLocals(List<Local> Locals) {
        this.locals = new ArrayList<>();
        this.locals = Locals;
        this.allLocals = Locals;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull LocalAdapter.LocalViewHolder holder, int position) {
        holder.bind(locals.get(position));
    }

    @Override
    public int getItemCount() {
        if(locals != null){
            return locals.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    locals = allLocals;
                } else {
                    List<Local> filteredList = new ArrayList<>();
                    for (Local Local : allLocals) {
                        if (Local.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(Local);
                        }
                    }
                    locals = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = locals;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                locals = (ArrayList<Local>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public  class LocalViewHolder extends RecyclerView.ViewHolder {
        private TextView txtLocalID;
        private TextView txtLocalName;
        Typeface typeface;
        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLocalID = itemView.findViewById(R.id.txt_local_id);
            txtLocalName = itemView.findViewById(R.id.txt_local_name);
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen-Algeria.ttf");
            txtLocalName.setTypeface(typeface);
            txtLocalName.setTextColor(context.getResources().getColor(R.color.color));

        }

        void bind(final Local Local) {
            txtLocalID.setText(Local.getId());
            txtLocalName.setText(Local.getName());

            itemView.setOnClickListener(listener);
     /*       itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView txtLocalID =view.findViewById(R.id.txt_local_id);
                    TextView txtLocalName =view.findViewById(R.id.txt_local_name);
                    String LocalID = txtLocalID.getText().toString();
                    String LocalName = txtLocalName.getText().toString();

                    Intent i = new Intent(context, RegisterActivity.class);
                    i.putExtra("local_id",LocalID);
                    i.putExtra("local_name",LocalName);
                    context.startActivity(i);
                }
            });*/
        }
    }

    public List<Local> getAll() {
        return locals;
    }

/*    public List<Local> getSelected() {
        List<Local> selected = new ArrayList<>();
        for (int i = 0; i < Local.size(); i++) {
            if (Local.get(i).isChecked()) {
                selected.add(Local.get(i));
            }
        }
        return selected;
    }*/

}
