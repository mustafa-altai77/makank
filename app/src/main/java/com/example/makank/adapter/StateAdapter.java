package com.example.makank.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.makank.R;
import com.example.makank.data.model.State;
import com.example.makank.ui.activity.CityActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.StateViewHolder> implements Filterable {
    private Context context;
    private List<State> states;
    private List<State> allStates;

    @NonNull
    @Override
    public StateAdapter.StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.state_item, parent, false);
        return new StateViewHolder(view);
    }


    public StateAdapter(Context context, List<State> states) {
        this.context = context;
        this.states = states;
        this.allStates = states;
    }
    public void setStates( List<State> states) {
        this.states = states;
        this.allStates = states;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull StateAdapter.StateViewHolder holder, int position) {
        holder.bind(states.get(position));
    }

    @Override
    public int getItemCount() {
        if(states != null){
            return states.size();
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
                    states = allStates;
                } else {
                    List<State> filteredList = new ArrayList<>();
                    for (State state : allStates) {
                        if (state.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(state);
                        }
                    }
                    states = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = states;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                states = (ArrayList<State>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public  class StateViewHolder extends RecyclerView.ViewHolder {
        private TextView txtStateID;
        private TextView txtStateName;
        public StateViewHolder(@NonNull View itemView) {
            super(itemView);
            txtStateID = itemView.findViewById(R.id.txt_state_id);
            txtStateName = itemView.findViewById(R.id.txt_state_name);
        }

        void bind(final State state) {
            txtStateID.setText(state.getId());
            txtStateName.setText(state.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView txtStateID =view.findViewById(R.id.txt_state_id);
                    TextView txtStateName =view.findViewById(R.id.txt_state_name);
                    String stateID = txtStateID.getText().toString();
                    String stateName = txtStateName.getText().toString();

                    Intent i = new Intent(context, CityActivity.class);
                    i.putExtra("state_id",stateID);
                    i.putExtra("state_name",stateName);
                    context.startActivity(i);
                }
            });
        }
    }

    public List<State> getAll() {
        return states;
    }

/*    public List<State> getSelected() {
        List<State> selected = new ArrayList<>();
        for (int i = 0; i < state.size(); i++) {
            if (state.get(i).isChecked()) {
                selected.add(State.get(i));
            }
        }
        return selected;
    }*/

}
