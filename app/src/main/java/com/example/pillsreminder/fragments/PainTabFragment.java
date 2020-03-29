package com.example.pillsreminder.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pillsreminder.R;
import com.example.pillsreminder.activities.NewPillActivity;
import com.example.pillsreminder.entities.Pain;
import com.example.pillsreminder.viewAdapter.PainListAdapter;
import com.example.pillsreminder.viewModels.PainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PainTabFragment extends Fragment {

    public static final CharSequence title = "Pain";
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private PainViewModel painViewModel;

    public PainTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pain_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        RecyclerView recyclerView = getActivity().findViewById(R.id.pain_recyclerview);
        final PainListAdapter adapter =  new PainListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        painViewModel = ViewModelProviders.of(this).get(PainViewModel.class);
        painViewModel.getAllPains().observe(getActivity(), new Observer<List<Pain>>() {
            @Override
            public void onChanged(List<Pain> pains) {
                adapter.setAllPains(pains);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_new_pain);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewPillActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Pain pain = new Pain(Integer.valueOf(data.getStringExtra(NewPillActivity.EXTRA_REPLY)));
            painViewModel.insertItem(pain);
        } else {
            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
        }
    }

}
