package com.example.deedeehan.daylight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by deedeehan on 2/6/16.
 */
public class LocationDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface LocationDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String type, int numStars, String comment);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    LocationDialogListener mListener;
    public double latitude;
    public double longitude;
    public String type;
    public String comment;
    int numStars;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocationDialogFragment () {
        return;
    }

    @SuppressLint("ValidFragment")
    public LocationDialogFragment (LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }

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

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_comment, null);
        EditText mTypeText = (EditText)view.findViewById(R.id.type);
        EditText mCommentText = (EditText)view.findViewById(R.id.comment);
        RatingBar mRatingBar = (RatingBar)view.findViewById(R.id.ratingBar);

        final String type = mTypeText.getText().toString();
        final String comment = mCommentText.getText().toString();
        final int numStars = mRatingBar.getNumStars();

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("New Comment")
                .setView(inflater.inflate(R.layout.dialog_comment, null))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(LocationDialogFragment.this, type, numStars, comment);
                    }
                });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogNegativeClick(LocationDialogFragment.this);
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    // This function is used to save data when the screen is rotated
    // ie from vertical to horizontal
    // Currently, this app does not support changes in screen orientation
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
