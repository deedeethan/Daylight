package com.example.deedeehan.daylight;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by deedeehan on 2/6/16.
 */
public class LocationDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Context context = getActivity();
            final EditText input = new EditText(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                   // .setTitle(context.getString(""))
                    .setView(input)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });
            // probably need to change this to return something because the handleTaps() function
            // adds a new marker by default to the map
            builder.setNegativeButton(R.string.cancel, null);

            AlertDialog dialog = builder.create();
            return dialog;
        }
}
