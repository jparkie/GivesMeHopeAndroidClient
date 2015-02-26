package com.jparkie.givesmehope.presenters;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jparkie.givesmehope.BuildConfig;
import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.data.events.BusProvider;
import com.jparkie.givesmehope.data.events.LatestTrendingPositionEvent;
import com.jparkie.givesmehope.data.factories.DefaultFactory;
import com.jparkie.givesmehope.data.factories.InitialFactory;
import com.jparkie.givesmehope.data.services.GMHService;
import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.modules.ApiModule;
import com.jparkie.givesmehope.views.TrendingView;
import com.jparkie.givesmehope.views.adapters.TrendingAdapter;
import com.jparkie.givesmehope.views.fragments.DisclaimerFragment;
import com.jparkie.givesmehope.views.fragments.SubmitFragment;
import com.squareup.otto.Subscribe;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TrendingPresenterImpl implements TrendingPresenter {
    public static final String TAG = TrendingPresenterImpl.class.getSimpleName();

    private TrendingView mTrendingView;
    private TrendingAdapter mTrendingAdapter;

    private GMHService mGMHService;
    private Subscription mGetTrendingAnthologySubscription;
    private static Observable<Anthology> mGetTrendingAnthologyObservable;
    private Observer<Anthology> mGetTrendingAnthologyObserver = new Observer<Anthology>() {
        @Override
        public void onCompleted() {
            mGetTrendingAnthologyObservable = null;

            mIsLoading = false;

            mTrendingView.hideTrendingProgressBar();
            mTrendingView.hideTrendingSwipeRefreshLayout();
            mTrendingView.hideNetworkingErrorImageView();
        }

        @Override
        public void onError(Throwable e) {
            mGetTrendingAnthologyObservable = null;

            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }

            mIsLoading = false;

            mTrendingView.hideTrendingProgressBar();
            mTrendingView.hideTrendingSwipeRefreshLayout();
            if (mTrendingAdapter.getItemCount() == 0) {
                mTrendingView.showNetworkingErrorImageView();
            }
        }

        @Override
        public void onNext(Anthology anthology) {
            mTrendingAnthology.setNextPageUrl(anthology.getNextPageUrl());

            if (mTrendingAdapter != null) {
                mTrendingAdapter.addStories(anthology.getStories());

                mTrendingAnthology.setStories(mTrendingAdapter.getStories());
            }
        }
    };

    private Anthology mTrendingAnthology;

    private boolean mIsLoading;

    public TrendingPresenterImpl(TrendingView trendingView, GMHService gmhService) {
        this.mTrendingView = trendingView;

        this.mGMHService = gmhService;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mTrendingView.initializeTrendingSwipeRefreshLayout();
        mTrendingView.initializeTrendingNetworkingErrorImageView();
        mTrendingView.initializeTrendingRecyclerView();

        mTrendingView.hideTrendingSwipeRefreshLayout();
        mTrendingView.hideNetworkingErrorImageView();

        if (savedInstanceState == null) {
            mTrendingView.showTrendingProgressBar();

            initializeInstanceState();

            pullTrendingAnthologyFromNetwork();
        } else {
            restoreInstanceState(savedInstanceState);

            pullTrendingAnthologyFromCache();
        }
    }

    @Override
    public void onResume() {
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void onLatestTrendingPositionReceived(LatestTrendingPositionEvent event) {
        int position = event.getPosition();
        if (conditionPullNextAnthology(position)) {
            pullTrendingAnthologyFromNetwork();
        }
    }

    @Override
    public void onPause() {
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Anthology.putParcelable(outState, mTrendingAnthology);
    }

    @Override
    public void onDestroy() {
        releaseSubscriptions();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.trending, menu);
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
            case R.id.action_to_top:
                onOptionsToTop();
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
        refreshTrendingAnthology();
    }

    private void onOptionsSubmit() {
        if (((ActionBarActivity) mTrendingView.getContext()).getSupportFragmentManager().findFragmentByTag(SubmitFragment.TAG) == null) {
            SubmitFragment submitFragment = new SubmitFragment();
            submitFragment.show(((ActionBarActivity) mTrendingView.getContext()).getSupportFragmentManager(), SubmitFragment.TAG);
        }
    }

    private void onOptionsRefresh() {
        if (!mTrendingView.isTrendingSwipeRefreshLayoutRefreshing()) {
            mTrendingView.showTrendingSwipeRefreshLayout();

            refreshTrendingAnthology();
        }
    }

    private void onOptionsToTop() {
        mTrendingView.scrollToTop();
    }

    private void onOptionsDisclaimer() {
        if (((ActionBarActivity) mTrendingView.getContext()).getSupportFragmentManager().findFragmentByTag(DisclaimerFragment.TAG) == null) {
            DisclaimerFragment disclaimerFragment = new DisclaimerFragment();
            disclaimerFragment.show(((ActionBarActivity) mTrendingView.getContext()).getSupportFragmentManager(), DisclaimerFragment.TAG);
        }
    }

    private void initializeInstanceState() {
        mTrendingAnthology = InitialFactory.Anthology.constructInitialTrendingInstance();

        mTrendingAdapter = new TrendingAdapter(mTrendingAnthology.getStories());
        mTrendingView.setAdapterForRecyclerView(mTrendingAdapter);
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        mTrendingAnthology = Anthology.getParcelable(savedInstanceState);

        mTrendingAdapter = new TrendingAdapter(mTrendingAnthology.getStories());
        mTrendingView.setAdapterForRecyclerView(mTrendingAdapter);
    }

    private void pullTrendingAnthologyFromNetwork() {
        if (mGetTrendingAnthologySubscription != null) {
            mGetTrendingAnthologySubscription.unsubscribe();
            mGetTrendingAnthologySubscription = null;
        }

        mIsLoading = true;

        mGetTrendingAnthologyObservable = mGMHService
                .getTrendingAnthology(mTrendingAnthology.getNextPageUrl())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();

        mGetTrendingAnthologySubscription = mGetTrendingAnthologyObservable
                .subscribe(mGetTrendingAnthologyObserver);
    }

    private void pullTrendingAnthologyFromCache() {
        if (mGetTrendingAnthologySubscription != null) {
            mGetTrendingAnthologySubscription.unsubscribe();
            mGetTrendingAnthologySubscription = null;
        }

        if (mGetTrendingAnthologyObservable != null) {
            mIsLoading = true;

            mGetTrendingAnthologySubscription = mGetTrendingAnthologyObservable
                    .subscribe(mGetTrendingAnthologyObserver);
        }
    }

    private void refreshTrendingAnthology() {
        initializeInstanceState();
        releaseSubscriptions();
        pullTrendingAnthologyFromNetwork();
    }

    private boolean conditionPullNextAnthology(int position) {
        return !mIsLoading
                && mTrendingAnthology != null
                && !mTrendingAnthology.getNextPageUrl().equals(DefaultFactory.Anthology.EMPTY_FIELD_NEXT_PAGE_URL)
                && position > mTrendingAdapter.getItemCount() - ApiModule.PULL_TOLERANCE;
    }

    private void releaseSubscriptions() {
        if (mGetTrendingAnthologySubscription != null) {
            mGetTrendingAnthologySubscription.unsubscribe();
            mGetTrendingAnthologySubscription = null;
        }
    }
}
