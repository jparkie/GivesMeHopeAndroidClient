package com.jparkie.givesmehope.views.fragments.base;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.jparkie.givesmehope.GMHApplication;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public abstract class BaseDialogFragment extends DialogFragment {
    public static final String TAG = BaseDialogFragment.class.getSimpleName();

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mObjectGraph = GMHApplication.getApplication(getActivity()).buildScopedObjectGraph(getModules().toArray());
        mObjectGraph.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mObjectGraph = null;
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                // Empty.
        );
    }
}
