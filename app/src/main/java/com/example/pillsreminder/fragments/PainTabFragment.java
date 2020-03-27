package com.example.pillsreminder.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pillsreminder.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PainTabFragment extends Fragment {

    public static final CharSequence title = "Pain";
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    public PainTabFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pain_tab, container, false);
    }

}
