package com.example.pillsreminder.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pillsreminder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlertDialogFragment extends DialogFragment {

    public static String ID_MESSAGE = "ID_MESSAGE";
    public static String ID_DATABASE = "ID_DATABASE";
    private OnAlertDialogInteractionInterface mListener;
    private String message;
    private String database;


    public AlertDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        Bundle bundle = getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (bundle != null && activity != null) {
            message = bundle.getString(ID_MESSAGE);
            database = bundle.getString(ID_DATABASE);

            builder.setMessage(message).setPositiveButton(R.string.button_next, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onDialogPositiveClick(AlertDialogFragment.this, database);
                }
            }).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onDialogNegativeClick(AlertDialogFragment.this);
                }
            });
        } else {
            builder.setMessage("Error, cannot remove item").setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onDialogNegativeClick(AlertDialogFragment.this);
                }
            });

        }
        return builder.create();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnAlertDialogInteractionInterface) {
            mListener = (OnAlertDialogInteractionInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListPainFragmentInteractionListener");
        }
    }




    public interface OnAlertDialogInteractionInterface {

        void onDialogPositiveClick(DialogFragment dialogFragment, String database);

        void onDialogNegativeClick(DialogFragment dialogFragment);
    }





}
