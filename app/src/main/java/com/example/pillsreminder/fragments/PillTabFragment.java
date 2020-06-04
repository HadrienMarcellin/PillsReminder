package com.example.pillsreminder.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pillsreminder.R;
import com.example.pillsreminder.activities.NewPillActivity;
import com.example.pillsreminder.room.drug.Drug;
import com.example.pillsreminder.room.pill.Pill;
import com.example.pillsreminder.viewAdapter.PillListAdapter;
import com.example.pillsreminder.viewModels.TreatmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PillTabFragment extends Fragment {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
//    private PillViewModel pillViewModel;
//    private DrugViewModel drugViewModel;
    private TreatmentViewModel treatmentViewModel;
    private OnPillFragmentInteractionListener mListener;

    public PillTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pill_tab, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {

//        drugViewModel = ViewModelProviders.of(this).get(DrugViewModel.class);
//        pillViewModel = ViewModelProviders.of(this).get(PillViewModel.class);
        treatmentViewModel = ViewModelProviders.of(this).get(TreatmentViewModel.class);

        RecyclerView recyclerView = getActivity().findViewById(R.id.pill_recyclerview);
        final PillListAdapter adapter = new PillListAdapter(getActivity(), mListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        treatmentViewModel.getAllPills().observe(getActivity(), new Observer<List<Pill>>() {
            @Override
            public void onChanged(List<Pill> pillEntities) {
                adapter.setPills(pillEntities);
            }
        });
        treatmentViewModel.getAllDrugs().observe(getActivity(), new Observer<List<Drug>>() {
            @Override
            public void onChanged(List<Drug> drugs) {
                adapter.setDrugs(drugs);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_new_pill);
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
        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
           // Pill pill = new Pill(data.getStringExtra(NewPillActivity.EXTRA_REPLY));
           // mPillViewModel.insertItem(pill);
        } else {
            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPillFragmentInteractionListener)
            mListener = (OnPillFragmentInteractionListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement OnPillFragmentInteractionListener.");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface provides a simple way to handle the delete button from any item in the list
     * displayed in the fragment.
     */
    public interface OnPillFragmentInteractionListener {
        /**
         * Use this method to remove a pill from the local database.
         * @param pill item to remove
         */
        void onListClickPillDelete(Pill pill);
    }
}
