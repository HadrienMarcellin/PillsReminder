package com.example.pillsreminder.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pillsreminder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodTabFragment extends Fragment {

    public final static CharSequence title = "Food";

    public FoodTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_tab, container, false);
    }
}
