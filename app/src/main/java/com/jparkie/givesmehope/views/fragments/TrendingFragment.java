package com.jparkie.givesmehope.views.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.modules.TrendingModule;
import com.jparkie.givesmehope.presenters.TrendingPresenter;
import com.jparkie.givesmehope.views.TrendingView;
import com.jparkie.givesmehope.views.fragments.base.BaseFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TrendingFragment extends BaseFragment implements TrendingView {
    public static final String TAG = TrendingFragment.class.getSimpleName();

    @Inject
    TrendingPresenter mTrendingPresenter;

    @InjectView(R.id.trendingSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.trendingRecyclerView)
    RecyclerView mRecyclerView;
    @InjectView(R.id.trendingProgressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.trendingNetworkingErrorImageView)
    ImageView mNetworkingErrorImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View trendingView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_trending, container, false);

        ButterKnife.inject(this, trendingView);

        return trendingView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTrendingPresenter.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        mTrendingPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mTrendingPresenter.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mTrendingPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mTrendingPresenter.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mTrendingPresenter.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mTrendingPresenter.onOptionsItemSelected(item);
    }

    // BaseFragment:

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new TrendingModule(this)
        );
    }

    // TrendingView:

    @Override
    public void initializeTrendingSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accentColor));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTrendingPresenter.onRefresh();
            }
        });
    }

    @Override
    public void initializeTrendingRecyclerView() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initializeTrendingNetworkingErrorImageView() {
        mNetworkingErrorImageView.setColorFilter(getResources().getColor(R.color.accentColor), PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void setAdapterForRecyclerView(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showTrendingSwipeRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideTrendingSwipeRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean isTrendingSwipeRefreshLayoutRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    @Override
    public void showTrendingProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTrendingProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNetworkingErrorImageView() {
        mNetworkingErrorImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNetworkingErrorImageView() {
        mNetworkingErrorImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }
}
