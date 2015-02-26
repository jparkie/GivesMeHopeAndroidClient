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
import com.jparkie.givesmehope.modules.HotModule;
import com.jparkie.givesmehope.presenters.HotPresenter;
import com.jparkie.givesmehope.views.HotView;
import com.jparkie.givesmehope.views.fragments.base.BaseFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HotFragment extends BaseFragment implements HotView {
    public static final String TAG = HotFragment.class.getSimpleName();

    @Inject
    HotPresenter mHotPresenter;

    @InjectView(R.id.hotSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.hotRecyclerView)
    RecyclerView mRecyclerView;
    @InjectView(R.id.hotProgressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.hotNetworkingErrorImageView)
    ImageView mNetworkingErrorImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hotView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_hot, container, false);

        ButterKnife.inject(this, hotView);

        return hotView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHotPresenter.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        mHotPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mHotPresenter.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mHotPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mHotPresenter.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mHotPresenter.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mHotPresenter.onOptionsItemSelected(item);
    }

    // BaseFragment:

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new HotModule(this)
        );
    }

    // HotView:

    @Override
    public void initializeHotSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accentColor));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHotPresenter.onRefresh();
            }
        });
    }

    @Override
    public void initializeHotRecyclerView() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initializeHotNetworkingErrorImageView() {
        mNetworkingErrorImageView.setColorFilter(getResources().getColor(R.color.accentColor), PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void setAdapterForRecyclerView(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showHotSwipeRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideHotSwipeRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean isHotSwipeRefreshLayoutRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    @Override
    public void showHotProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHotProgressBar() {
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
