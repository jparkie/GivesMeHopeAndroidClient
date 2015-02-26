package com.jparkie.givesmehope.presenters;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jparkie.givesmehope.BuildConfig;
import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.data.events.BusProvider;
import com.jparkie.givesmehope.data.events.LatestHotPositionEvent;
import com.jparkie.givesmehope.data.factories.DefaultFactory;
import com.jparkie.givesmehope.data.factories.InitialFactory;
import com.jparkie.givesmehope.data.services.GMHService;
import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.modules.ApiModule;
import com.jparkie.givesmehope.views.HotView;
import com.jparkie.givesmehope.views.adapters.HotAdapter;
import com.jparkie.givesmehope.views.fragments.DisclaimerFragment;
import com.jparkie.givesmehope.views.fragments.SubmitFragment;
import com.squareup.otto.Subscribe;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HotPresenterImpl implements HotPresenter {
    public static final String TAG = HotPresenterImpl.class.getSimpleName();

    private HotView mHotView;
    private HotAdapter mHotAdapter;

    private GMHService mGMHService;
    private Subscription mGetHotAnthologySubscription;
    private static Observable<Anthology> mGetHotAnthologyObservable;
    private Observer<Anthology> mGetHotAnthologyObserver = new Observer<Anthology>() {
        @Override
        public void onCompleted() {
            mGetHotAnthologyObservable = null;

            mIsLoading = false;

            mHotView.hideHotProgressBar();
            mHotView.hideHotSwipeRefreshLayout();
            mHotView.hideNetworkingErrorImageView();
        }

        @Override
        public void onError(Throwable e) {
            mGetHotAnthologyObservable = null;

            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }

            mIsLoading = false;

            mHotView.hideHotProgressBar();
            mHotView.hideHotSwipeRefreshLayout();
            if (mHotAdapter.getItemCount() == 0) {
                mHotView.showNetworkingErrorImageView();
            }
        }

        @Override
        public void onNext(Anthology anthology) {
            mHotAnthology.setNextPageUrl(anthology.getNextPageUrl());

            if (mHotAdapter != null) {
                mHotAdapter.addStories(anthology.getStories());

                mHotAnthology.setStories(mHotAdapter.getStories());
            }
        }
    };

    private Anthology mHotAnthology;

    private boolean mIsLoading;

    public HotPresenterImpl(HotView hotView, GMHService gmhService) {
        this.mHotView = hotView;

        this.mGMHService = gmhService;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mHotView.initializeHotSwipeRefreshLayout();
        mHotView.initializeHotNetworkingErrorImageView();
        mHotView.initializeHotRecyclerView();

        mHotView.hideHotSwipeRefreshLayout();
        mHotView.hideNetworkingErrorImageView();

        if (savedInstanceState == null) {
            mHotView.showHotProgressBar();

            initializeInstanceState();

            pullHotAnthologyFromNetwork();
        } else {
            restoreInstanceState(savedInstanceState);

            pullHotAnthologyFromCache();
        }
    }

    @Override
    public void onResume() {
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void onLatestHotPositionReceived(LatestHotPositionEvent event) {
        int position = event.getPosition();
        if (conditionPullNextAnthology(position)) {
            pullHotAnthologyFromNetwork();
        }
    }

    @Override
    public void onPause() {
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Anthology.putParcelable(outState, mHotAnthology);
    }

    @Override
    public void onDestroy() {
        releaseSubscriptions();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.hot, menu);
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
        refreshHotAnthology();
    }

    private void onOptionsSubmit() {
        if (((ActionBarActivity)mHotView.getContext()).getSupportFragmentManager().findFragmentByTag(SubmitFragment.TAG) == null) {
            SubmitFragment submitFragment = new SubmitFragment();
            submitFragment.show(((ActionBarActivity) mHotView.getContext()).getSupportFragmentManager(), SubmitFragment.TAG);
        }
    }

    private void onOptionsRefresh() {
        if (!mHotView.isHotSwipeRefreshLayoutRefreshing()) {
            mHotView.showHotSwipeRefreshLayout();

            refreshHotAnthology();
        }
    }

    private void onOptionsToTop() {
        mHotView.scrollToTop();
    }

    private void onOptionsDisclaimer() {
        if (((ActionBarActivity)mHotView.getContext()).getSupportFragmentManager().findFragmentByTag(DisclaimerFragment.TAG) == null) {
            DisclaimerFragment disclaimerFragment = new DisclaimerFragment();
            disclaimerFragment.show(((ActionBarActivity) mHotView.getContext()).getSupportFragmentManager(), DisclaimerFragment.TAG);
        }
    }

    private void initializeInstanceState() {
        mHotAnthology = InitialFactory.Anthology.constructInitialHotInstance();

        mHotAdapter = new HotAdapter(mHotView.getContext(), mHotAnthology.getStories());
        mHotView.setAdapterForRecyclerView(mHotAdapter);
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        mHotAnthology = Anthology.getParcelable(savedInstanceState);

        mHotAdapter = new HotAdapter(mHotView.getContext(), mHotAnthology.getStories());
        mHotView.setAdapterForRecyclerView(mHotAdapter);
    }

    private void pullHotAnthologyFromNetwork() {
        if (mGetHotAnthologySubscription != null) {
            mGetHotAnthologySubscription.unsubscribe();
            mGetHotAnthologySubscription = null;
        }

        mIsLoading = true;

        mGetHotAnthologyObservable = mGMHService
                .getHotAnthology(mHotAnthology.getNextPageUrl())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();

        mGetHotAnthologySubscription = mGetHotAnthologyObservable
                .subscribe(mGetHotAnthologyObserver);
    }

    private void pullHotAnthologyFromCache() {
        if (mGetHotAnthologySubscription != null) {
            mGetHotAnthologySubscription.unsubscribe();
            mGetHotAnthologySubscription = null;
        }

        if (mGetHotAnthologyObservable != null) {
            mIsLoading = true;

            mGetHotAnthologySubscription = mGetHotAnthologyObservable
                    .subscribe(mGetHotAnthologyObserver);
        }
    }

    private void refreshHotAnthology() {
        initializeInstanceState();
        releaseSubscriptions();
        pullHotAnthologyFromNetwork();
    }

    private boolean conditionPullNextAnthology(int position) {
        return !mIsLoading
                && mHotAnthology != null
                && !mHotAnthology.getNextPageUrl().equals(DefaultFactory.Anthology.EMPTY_FIELD_NEXT_PAGE_URL)
                && position > mHotAdapter.getItemCount() - ApiModule.PULL_TOLERANCE;
    }

    private void releaseSubscriptions() {
        if (mGetHotAnthologySubscription != null) {
            mGetHotAnthologySubscription.unsubscribe();
            mGetHotAnthologySubscription = null;
        }
    }
}
