package com.jparkie.givesmehope.views;

import com.jparkie.givesmehope.views.base.BaseContextView;

public interface SubmitView extends BaseContextView {
    public void closeDialog();

    public String getNameEditText();

    public String getLocationEditText();

    public String getTitleEditText();

    public String getStoryEditText();

    public String getCategorySpinnerSelection();

    public void resetSubmitNameEditText();

    public void resetSubmitLocationEditText();

    public void resetSubmitTitleEditText();

    public void resetSubmitStoryEditText();

    public void resetSubmitCategorySpinner();

    public void toastSubmitErrorTitle();

    public void toastSubmitErrorStory();

    public void toastSubmitSuccess();

    public void toastSubmitFailure();
}
