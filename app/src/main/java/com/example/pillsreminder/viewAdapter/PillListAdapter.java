package com.example.pillsreminder.viewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pillsreminder.R;
import com.example.pillsreminder.fragments.PillTabFragment;
import com.example.pillsreminder.helpers.CalendarHelpers;
import com.example.pillsreminder.room.drug.Drug;
import com.example.pillsreminder.room.pill.Pill;

import java.util.List;

public class PillListAdapter extends RecyclerView.Adapter<PillListAdapter.PillViewHolder> {

    private List<Pill> mPills;
    private List<Drug> mDrugs;
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
//            holder.textView_date.setText(CalendarHelpers.calendarToDateString(current.getDate()));
            Drug drug = mDrugs.stream().filter(d -> d.getId() == current.getDrugType_id()).findAny().orElse(null);
            String drug_name = (drug != null) ? drug.getName() : "Indéfini";
            @SuppressLint("DefaultLocale") String desc = String.format("%d x %s", current.getId(), drug_name);

            SpannableStringBuilder str = new SpannableStringBuilder()
                    .append(String.valueOf(current.getQuantity()) + " x ",
                            new RelativeSizeSpan(0.75f), Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    .append(drug_name, new StyleSpan(Typeface.BOLD), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            holder.textView_description.setText(str, TextView.BufferType.SPANNABLE);
            holder.textView_date.setText(CalendarHelpers.calendarToDateString(current.getDate()));
            holder.textView_time.setText(CalendarHelpers.calendarToTimeString(current.getDate()));

            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onListClickPillDelete(mPills.get(position));
                }
            });
            holder.imageIcon.setImageResource(R.drawable.pill_red);

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

    public void setDrugs(List<Drug> mDrugs) {
        this.mDrugs = mDrugs;
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
        private TextView textView_description;
        private ImageView imageDelete;
        private ImageView imageIcon;
        public PillViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView_date = itemView.findViewById(R.id.textView_date_pill_item);
            this.textView_time = itemView.findViewById(R.id.textView_time_pill_item);
            this.textView_description = itemView.findViewById(R.id.textView_description_pill);
            this.imageDelete = itemView.findViewById(R.id.imageDelete);
            this.imageIcon = itemView.findViewById(R.id.imageIcon_pill_item);

        }
    }
}
