package com.example.makank.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.makank.R;
import com.example.makank.data.model.State;
import com.example.makank.ui.activity.CityActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {
    private Context context;
    private List<State> states;


    @NonNull
    @Override
    public StateAdapter.StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.state_item, parent, false);
        return new StateViewHolder(view);
    }


    public StateAdapter(Context context, List<State> states) {
        this.context = context;
        this.states = states;
    }
    public void setStates(List<State> states) {
        this.states = new ArrayList<>();
        this.states = states;
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
