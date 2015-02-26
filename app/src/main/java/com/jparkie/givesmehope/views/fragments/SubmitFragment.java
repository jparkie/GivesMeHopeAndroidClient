package com.jparkie.givesmehope.views.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.modules.SubmitModule;
import com.jparkie.givesmehope.presenters.SubmitPresenter;
import com.jparkie.givesmehope.views.SubmitView;
import com.jparkie.givesmehope.views.fragments.base.BaseDialogFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SubmitFragment extends BaseDialogFragment implements SubmitView {
    public static final String TAG = SubmitFragment.class.getSimpleName();

    @Inject
    SubmitPresenter mSubmitPresenter;

    @InjectView(R.id.submitNameEditText)
    EditText mSubmitNameEditText;
    @InjectView(R.id.submitLocationEditText)
    EditText mSubmitLocationEditText;
    @InjectView(R.id.submitTitleEditText)
    EditText mSubmitTitleEditText;
    @InjectView(R.id.submitStoryEditText)
    EditText mSubmitStoryEditText;
    @InjectView(R.id.submitCategorySpinner)
    Spinner mSubmitCategorySpinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View submitView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_submit, null, false);

        ButterKnife.inject(this, submitView);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(submitView)
                .setPositiveButton(R.string.submit_dialog_button_submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        // Do Nothing.
                    }
                })
                .setNegativeButton(R.string.submit_dialog_button_reset, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        // Do Nothing.
                    }
                });

        return dialogBuilder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        AlertDialog currentDialog = (AlertDialog)getDialog();
        if (currentDialog != null) {
            Button submitButton = currentDialog.getButton(Dialog.BUTTON_POSITIVE);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSubmitPresenter.onSubmit();
                }
            });
            Button resetButton = currentDialog.getButton(Dialog.BUTTON_NEGATIVE);
            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSubmitPresenter.onReset();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSubmitPresenter.onDestroy();
    }

    // BaseDialogFragment:

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new SubmitModule(this)
        );
    }

    // SubmitView:

    @Override
    public void closeDialog() {
        SubmitFragment.this.getDialog().cancel();
    }

    @Override
    public String getNameEditText() {
        return mSubmitNameEditText.getEditableText().toString();
    }

    @Override
    public String getLocationEditText() {
        return mSubmitLocationEditText.getEditableText().toString();
    }

    @Override
    public String getTitleEditText() {
        return mSubmitTitleEditText.getEditableText().toString();
    }

    @Override
    public String getStoryEditText() {
        return mSubmitStoryEditText.getEditableText().toString();
    }

    @Override
    public String getCategorySpinnerSelection() {
        TypedArray submitCategoryValues = getResources().obtainTypedArray(R.array.submit_dialog_category_entry_values);

        return submitCategoryValues.getString(mSubmitCategorySpinner.getSelectedItemPosition());
    }

    @Override
    public void resetSubmitNameEditText() {
        mSubmitNameEditText.getEditableText().clear();
    }

    @Override
    public void resetSubmitLocationEditText() {
        mSubmitLocationEditText.getEditableText().clear();
    }

    @Override
    public void resetSubmitTitleEditText() {
        mSubmitTitleEditText.getEditableText().clear();
    }

    @Override
    public void resetSubmitStoryEditText() {
        mSubmitStoryEditText.getEditableText().clear();
    }

    @Override
    public void resetSubmitCategorySpinner() {
        mSubmitCategorySpinner.setSelection(0);
    }

    @Override
    public void toastSubmitErrorTitle() {
        Toast.makeText(getActivity(), R.string.toast_submit_error_title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toastSubmitErrorStory() {
        Toast.makeText(getActivity(), R.string.toast_submit_error_story, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toastSubmitSuccess() {
        Toast.makeText(getActivity(), R.string.toast_submit_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toastSubmitFailure() {
        Toast.makeText(getActivity(), R.string.toast_submit_failure, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
