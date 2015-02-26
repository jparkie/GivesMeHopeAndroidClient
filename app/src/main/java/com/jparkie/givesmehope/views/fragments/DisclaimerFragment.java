package com.jparkie.givesmehope.views.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.jparkie.givesmehope.R;

public class DisclaimerFragment extends DialogFragment {
    public static final String TAG = DisclaimerFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View disclaimerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_disclaimer, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(disclaimerView)
                .setPositiveButton(R.string.disclaimer_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DisclaimerFragment.this.getDialog().cancel();
                    }
                });

        return dialogBuilder.create();
    }
}
