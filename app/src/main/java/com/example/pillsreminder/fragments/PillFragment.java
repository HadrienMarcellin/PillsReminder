package com.example.pillsreminder.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.pillsreminder.R;
import com.example.pillsreminder.room.pill.Pill;

/**
 * A simple {@link Fragment} subclass.
 */
public class PillFragment extends Fragment {


    private Pill pill;

    public PillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pill_item, container, false);
    }
}
