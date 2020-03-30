package com.example.pillsreminder.viewAdapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pillsreminder.R;
import com.example.pillsreminder.entities.Pain;
import com.example.pillsreminder.helpers.CalendarHelpers;

import java.util.List;

public class PainListAdapter extends RecyclerView.Adapter<PainListAdapter.PainViewHolder> {

    private List<Pain> allPains;
    private LayoutInflater mInflater;

    @NonNull
    @Override
    public PainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_pill_item, parent, false);
        return new PainViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PainViewHolder holder, int position) {
        if (allPains != null) {
            Pain current = allPains.get(position);
            holder.textView.setText(CalendarHelpers.calendarToString(current.getDate()));
        } else {
            holder.textView.setText("No pain.");
        }
    }

    @Override
    public int getItemCount() {
        if (allPains != null)
            return allPains.size();
        else
            return 0;
    }

    public void setAllPains(List<Pain> pains) {
        allPains = pains;
        notifyDataSetChanged();
    }

    // Constructor
    public PainListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public class PainViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        public PainViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textView = itemView.findViewById(R.id.textView_pill);
        }
    }
}
