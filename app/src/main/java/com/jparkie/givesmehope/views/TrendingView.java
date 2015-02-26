package com.jparkie.givesmehope.views;

import android.support.v7.widget.RecyclerView;

import com.jparkie.givesmehope.views.base.BaseContextView;

public interface TrendingView extends BaseContextView {
    public void initializeTrendingSwipeRefreshLayout();

    public void initializeTrendingRecyclerView();

    public void initializeTrendingNetworkingErrorImageView();

    public void setAdapterForRecyclerView(RecyclerView.Adapter adapter);

    public void showTrendingSwipeRefreshLayout();

    public void hideTrendingSwipeRefreshLayout();

    public boolean isTrendingSwipeRefreshLayoutRefreshing();

    public void showTrendingProgressBar();

    public void hideTrendingProgressBar();

    public void showNetworkingErrorImageView();

    public void hideNetworkingErrorImageView();

    public void scrollToTop();
}
