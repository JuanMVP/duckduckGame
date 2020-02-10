package com.example.duckduckhunt;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.duckduckhunt.models.User;


import java.util.List;


public class MyUserRecyclerViewAdapter extends RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder> {

    private final List<User> mValues;


    public MyUserRecyclerViewAdapter(List<User> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        int posicion = position +1;
        holder.textViewPosicion.setText(posicion + "º");
        holder.textViewDucksRanking.setText(String.valueOf(mValues.get(position).getDucks()));
        holder.textViewUsernameRanking.setText(mValues.get(posicion).getName());


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textViewPosicion;
        public final TextView textViewDucksRanking;
        public final TextView textViewUsernameRanking;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textViewPosicion =  view.findViewById(R.id.tvPosicionRanking);
            textViewDucksRanking =  view.findViewById(R.id.tvDucksRanking);
            textViewUsernameRanking = view.findViewById(R.id.tvUsernameRanking);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewUsernameRanking.getText() + "'";
        }
    }
}
