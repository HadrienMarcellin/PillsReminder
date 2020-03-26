package com.example.pillsreminder.viewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pillsreminder.R;
import com.example.pillsreminder.entities.Pill;

import java.util.List;

public class PillListAdapter extends RecyclerView.Adapter<PillListAdapter.PillViewHolder> {

    private List<Pill> mPills;
    private LayoutInflater mInflater;


    @NonNull
    @Override
    public PillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_pill_item, parent, false);
        return new PillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PillViewHolder holder, int position) {
        if (mPills != null)
        {
            Pill current = mPills.get(position);
            holder.pillTextView.setText(current.getDrug());
        } else {
            holder.pillTextView.setText("No Pill");
        }
    }

    @Override
    public int getItemCount() {
        if (mPills != null)
            return mPills.size();
        else return 0;
    }

    public void setPills(List<Pill> pillEntities) {
        mPills = pillEntities;
        notifyDataSetChanged();
    }

    // Constructor
    public PillListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public class PillViewHolder extends RecyclerView.ViewHolder{
        private TextView pillTextView;
        public PillViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pillTextView = itemView.findViewById(R.id.textView_pill);

        }
    }
}
