package com.jparkie.givesmehope.presenters;

import com.jparkie.givesmehope.BuildConfig;
import com.jparkie.givesmehope.data.services.GMHService;
import com.jparkie.givesmehope.views.SubmitView;
import com.squareup.okhttp.Response;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubmitPresenterImpl implements SubmitPresenter {
    public static final String TAG = SubmitPresenterImpl.class.getSimpleName();

    private SubmitView mSubmitView;

    private GMHService mGMHService;
    private Subscription mPostSubmitStorySubscription;

    public SubmitPresenterImpl(SubmitView submitView, GMHService gmhService) {
        this.mSubmitView = submitView;
        this.mGMHService = gmhService;
    }

    @Override
    public void onSubmit() {
        String submitName = mSubmitView.getNameEditText();
        String submitLocation = mSubmitView.getLocationEditText();
        String submitTitle = mSubmitView.getTitleEditText();
        String submitStory = mSubmitView.getStoryEditText();
        String submitCategory = mSubmitView.getCategorySpinnerSelection();

        if (submitTitle.length() <= 0) {
            mSubmitView.toastSubmitErrorTitle();
            return;
        }
        if (submitStory.length() <= 10) {
            mSubmitView.toastSubmitErrorStory();
            return;
        }

        submitStory(submitName, submitLocation, submitTitle, submitStory, submitCategory);
    }

    @Override
    public void onReset() {
        mSubmitView.resetSubmitNameEditText();
        mSubmitView.resetSubmitLocationEditText();
        mSubmitView.resetSubmitTitleEditText();
        mSubmitView.resetSubmitStoryEditText();
        mSubmitView.resetSubmitCategorySpinner();
    }

    @Override
    public void onDestroy() {
        releaseSubscriptions();
    }

    private void submitStory(String name, String location, String title, String story, String category) {
        if (mPostSubmitStorySubscription != null) {
            mPostSubmitStorySubscription.unsubscribe();
            mPostSubmitStorySubscription = null;
        }

        mPostSubmitStorySubscription = mGMHService
                .postSubmitStory(name, location, title, story, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onCompleted() {
                        mSubmitView.toastSubmitSuccess();

                        mSubmitView.closeDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) {
                            e.printStackTrace();
                        }

                        mSubmitView.toastSubmitFailure();

                        mSubmitView.closeDialog();
                    }

                    @Override
                    public void onNext(Response response) {
                        // Do Nothing.
                    }
                });
    }

    private void releaseSubscriptions() {
        if (mPostSubmitStorySubscription != null) {
            mPostSubmitStorySubscription.unsubscribe();
            mPostSubmitStorySubscription = null;
        }
    }
}
