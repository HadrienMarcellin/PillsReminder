package com.example.pillsreminder.viewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pillsreminder.R;
import com.example.pillsreminder.entities.Pill;
import com.example.pillsreminder.fragments.PillTabFragment;
import com.example.pillsreminder.helpers.CalendarHelpers;

import java.util.List;

public class PillListAdapter extends RecyclerView.Adapter<PillListAdapter.PillViewHolder> {

    private List<Pill> mPills;
    private LayoutInflater mInflater;
    private final PillTabFragment.OnPillFragmentInteractionListener mListener;


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
            holder.textView_date.setText(CalendarHelpers.calendarToDateString(current.getDate()));
            holder.textView_time.setText(CalendarHelpers.calendarToTimeString(current.getDate()));

            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onListClickPillDelete(mPills.get(position));
                }
            });
        } else {
            holder.textView_date.setText("-");
            holder.textView_time.setText("-");
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
    public PillListAdapter(Context context, PillTabFragment.OnPillFragmentInteractionListener mListener) {
        mInflater = LayoutInflater.from(context);
        this.mListener = mListener;
    }

    public class PillViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_date;
        private TextView textView_time;
        private ImageView imageDelete;
        public PillViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView_date = itemView.findViewById(R.id.textView_date_pill_item);
            this.textView_time = itemView.findViewById(R.id.textView_time_pill_item);
            this.imageDelete = itemView.findViewById(R.id.imageDelete);

        }
    }
}
