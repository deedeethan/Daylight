package com.example.deedeehan.daylight;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by deedeehan on 2/6/16.
 */
public class LocationDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface LocationDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    LocationDialogListener mListener;
    public String type;
    public String comment;
    public int numStars;
    public double latitude;
    public double longitude;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (LocationDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement LocationDialogListener");

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        final EditText type = new EditText(context);
        final EditText comment = new EditText(context);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("New Comment")
                .setView(inflater.inflate(R.layout.dialog_comment, null))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(LocationDialogFragment.this);
                    }
                });
        // probably need to change this to return something because the handleTaps() function
        // adds a new marker by default to the map
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogNegativeClick(LocationDialogFragment.this);
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("rating", numStars);
        outState.putString("type", type);
        outState.putString("comment", comment);
        outState.putDouble("latitude", latitude);
        outState.putDouble("longitude", longitude);
        super.onSaveInstanceState(outState);
    }
}
