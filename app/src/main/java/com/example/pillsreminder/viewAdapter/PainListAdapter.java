package com.example.pillsreminder.viewAdapter;

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
import com.example.pillsreminder.entities.Pain;
import com.example.pillsreminder.fragments.PainTabFragment;
import com.example.pillsreminder.helpers.CalendarHelpers;

import java.util.List;

public class PainListAdapter extends RecyclerView.Adapter<PainListAdapter.PainViewHolder> {

    private List<Pain> allPains;
    private LayoutInflater mInflater;
    private final PainTabFragment.OnPainFragmentInteractionListener painItemListener;

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

            SpannableStringBuilder str = new SpannableStringBuilder()
                    .append("Douleur : ", new RelativeSizeSpan(0.75f),
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    .append(current.getPainLevel().getFrenchName(), new StyleSpan(Typeface.BOLD),
                            Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            holder.textView_date.setText(CalendarHelpers.calendarToDateString(current.getDate()));
            holder.textView_time.setText(CalendarHelpers.calendarToTimeString(current.getDate()));
            holder.textView_description.setText(str);
            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    painItemListener.onListClickPainDelete(allPains.get(position));
                }
            });
        } else {
            holder.textView_date.setText("-");
            holder.textView_time.setText("-");
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
    public PainListAdapter(Context context, PainTabFragment.OnPainFragmentInteractionListener painItemListener) {
        mInflater = LayoutInflater.from(context);
        this.painItemListener = painItemListener;
    }

    public class PainViewHolder extends RecyclerView.ViewHolder{

        private TextView textView_date;
        private TextView textView_time;
        private TextView textView_description;
        private ImageView imageDelete;

        public PainViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView_date = itemView.findViewById(R.id.textView_date_pill_item);
            this.textView_time = itemView.findViewById(R.id.textView_time_pill_item);
            this.textView_description = itemView.findViewById(R.id.textView_description_pill);
            this.imageDelete = itemView.findViewById(R.id.imageDelete);
        }
    }
}
