package com.jparkie.givesmehope.views;

import android.support.v7.widget.RecyclerView;

import com.jparkie.givesmehope.views.base.BaseContextView;

public interface HotView extends BaseContextView {
    public void initializeHotSwipeRefreshLayout();

    public void initializeHotRecyclerView();

    public void initializeHotNetworkingErrorImageView();

    public void setAdapterForRecyclerView(RecyclerView.Adapter adapter);

    public void showHotSwipeRefreshLayout();

    public void hideHotSwipeRefreshLayout();

    public boolean isHotSwipeRefreshLayoutRefreshing();

    public void showHotProgressBar();

    public void hideHotProgressBar();

    public void showNetworkingErrorImageView();

    public void hideNetworkingErrorImageView();

    public void scrollToTop();
}
