package com.jparkie.givesmehope.presenters;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jparkie.givesmehope.BuildConfig;
import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.data.factories.DefaultFactory;
import com.jparkie.givesmehope.data.services.GMHService;
import com.jparkie.givesmehope.models.Story;
import com.jparkie.givesmehope.modules.ApiModule;
import com.jparkie.givesmehope.views.VoteView;
import com.jparkie.givesmehope.views.fragments.DisclaimerFragment;
import com.jparkie.givesmehope.views.fragments.SubmitFragment;
import com.squareup.okhttp.Response;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class VotePresenterImpl implements VotePresenter {
    public static final String TAG = VotePresenterImpl.class.getSimpleName();

    private VoteView mVoteView;

    private GMHService mGMHService;
    private Subscription mGetVoteStorySubscription;
    private Subscription mPostVoteStoryDownSubscription;
    private Subscription mPostVoteStoryUpSubscription;
    private static Observable<Story> mGetVoteStoryObservable;
    private Observer<Story> mGetVoteStoryObserver = new Observer<Story>() {
        @Override
        public void onCompleted() {
            mGetVoteStoryObservable = null;

            mVoteView.setVoteImageView(mVoteStory.getImageUrl());
            mVoteView.setVoteFooterTextView(mVoteStory.getFooter());

            mVoteView.showVoteCardView();
            mVoteView.hideVoteProgressBar();
            mVoteView.hideVoteSwipeRefreshLayout();
            mVoteView.hideNetworkingErrorImageView();
        }

        @Override
        public void onError(Throwable e) {
            mGetVoteStoryObservable = null;

            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }

            mVoteView.hideVoteCardView();
            mVoteView.hideVoteProgressBar();
            mVoteView.hideVoteSwipeRefreshLayout();
            mVoteView.showNetworkingErrorImageView();
        }

        @Override
        public void onNext(Story story) {
            mVoteStory = story;
        }
    };

    private Story mVoteStory;

    private boolean mIsVoting;

    public VotePresenterImpl(VoteView voteView, GMHService gmhService) {
        mVoteView = voteView;

        mGMHService = gmhService;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mVoteView.initializeVoteSwipeRefreshLayout();
        mVoteView.initializeVoteNetworkingErrorImageView();

        mVoteView.hideVoteSwipeRefreshLayout();
        mVoteView.hideVoteCardView();
        mVoteView.hideNetworkingErrorImageView();

        if (savedInstanceState == null) {
            mVoteView.showVoteProgressBar();

            pullVoteStoryFromNetwork();
        } else {
            restoreInstanceState(savedInstanceState);

            pullVoteStoryFromCache();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Story.putParcelable(outState, mVoteStory);
    }

    @Override
    public void onDestroy() {
        releaseSubscriptions();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.vote, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                onOptionsSubmit();
                return true;
            case R.id.action_refresh:
                onOptionsRefresh();
                return true;
            case R.id.action_disclaimer:
                onOptionsDisclaimer();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onRefresh() {
        refreshVoteStory();
    }

    @Override
    public void onVoteDown() {
        if (!mIsVoting && mVoteStory != null && !mVoteStory.getPostId().equals(DefaultFactory.Story.EMPTY_FIELD_POST_ID)) {
            if (mPostVoteStoryDownSubscription != null) {
                mPostVoteStoryDownSubscription.unsubscribe();
                mPostVoteStoryDownSubscription = null;
            }

            mIsVoting = true;

            mPostVoteStoryDownSubscription = mGMHService
                    .postVoteStoryDown(mVoteStory.getPostId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnTerminate(new Action0() {
                        @Override
                        public void call() {
                            mIsVoting = false;
                        }
                    })
                    .subscribe(new Observer<Response>() {
                        @Override
                        public void onCompleted() {
                            onOptionsRefresh();

                            mVoteView.toastVoteSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (BuildConfig.DEBUG) {
                                e.printStackTrace();
                            }

                            mVoteView.toastVoteFailure();
                        }

                        @Override
                        public void onNext(Response response) {
                            // Do Nothing.
                        }
                    });
        }
    }

    @Override
    public void onVoteUp() {
        if (!mIsVoting && mVoteStory != null && !mVoteStory.getPostId().equals(DefaultFactory.Story.EMPTY_FIELD_POST_ID)) {
            if (mPostVoteStoryUpSubscription != null) {
                mPostVoteStoryUpSubscription.unsubscribe();
                mPostVoteStoryUpSubscription = null;
            }

            mIsVoting = true;

            mPostVoteStoryUpSubscription = mGMHService
                    .postVoteStoryUp(mVoteStory.getPostId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnTerminate(new Action0() {
                        @Override
                        public void call() {
                            mIsVoting = false;
                        }
                    })
                    .subscribe(new Observer<Response>() {
                        @Override
                        public void onCompleted() {
                            onOptionsRefresh();

                            mVoteView.toastVoteSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (BuildConfig.DEBUG) {
                                e.printStackTrace();
                            }

                            mVoteView.toastVoteFailure();
                        }

                        @Override
                        public void onNext(Response response) {
                            // Do Nothing.
                        }
                    });
        }
    }

    private void onOptionsSubmit() {
        if (((ActionBarActivity) mVoteView.getContext()).getSupportFragmentManager().findFragmentByTag(SubmitFragment.TAG) == null) {
            SubmitFragment submitFragment = new SubmitFragment();
            submitFragment.show(((ActionBarActivity) mVoteView.getContext()).getSupportFragmentManager(), SubmitFragment.TAG);
        }
    }

    private void onOptionsRefresh() {
        if (!mVoteView.isVoteSwipeRefreshLayoutRefreshing()) {
            mVoteView.showVoteSwipeRefreshLayout();

            refreshVoteStory();
        }
    }

    private void onOptionsDisclaimer() {
        if (((ActionBarActivity) mVoteView.getContext()).getSupportFragmentManager().findFragmentByTag(DisclaimerFragment.TAG) == null) {
            DisclaimerFragment disclaimerFragment = new DisclaimerFragment();
            disclaimerFragment.show(((ActionBarActivity) mVoteView.getContext()).getSupportFragmentManager(), DisclaimerFragment.TAG);
        }
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        mVoteStory = Story.getParcelable(savedInstanceState);

        if (mVoteStory != null) {
            mVoteView.setVoteImageView(mVoteStory.getImageUrl());
            mVoteView.setVoteFooterTextView(mVoteStory.getFooter());

            mVoteView.showVoteCardView();
        }
    }

    private void pullVoteStoryFromNetwork() {
        if (mGetVoteStorySubscription != null) {
            mGetVoteStorySubscription.unsubscribe();
            mGetVoteStorySubscription = null;
        }

        mGetVoteStoryObservable = mGMHService
                .getVoteStory(ApiModule.VOTE_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();

        mGetVoteStorySubscription = mGetVoteStoryObservable
                .subscribe(mGetVoteStoryObserver);
    }

    private void pullVoteStoryFromCache() {
        if (mGetVoteStorySubscription != null) {
            mGetVoteStorySubscription.unsubscribe();
            mGetVoteStorySubscription = null;
        }

        if (mGetVoteStoryObservable != null) {
            mGetVoteStorySubscription = mGetVoteStoryObservable
                    .subscribe(mGetVoteStoryObserver);
        }
    }

    private void refreshVoteStory() {
        pullVoteStoryFromNetwork();
    }

    private void releaseSubscriptions() {
        if (mGetVoteStorySubscription != null) {
            mGetVoteStorySubscription.unsubscribe();
            mGetVoteStorySubscription = null;
        }
        if (mPostVoteStoryDownSubscription != null) {
            mPostVoteStoryDownSubscription.unsubscribe();
            mPostVoteStoryDownSubscription = null;
        }
        if (mPostVoteStoryUpSubscription != null) {
            mPostVoteStoryUpSubscription.unsubscribe();
            mPostVoteStoryUpSubscription = null;
        }
    }
}
