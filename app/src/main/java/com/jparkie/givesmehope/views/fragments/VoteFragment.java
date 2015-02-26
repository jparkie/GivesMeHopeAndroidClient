package com.jparkie.givesmehope.views.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.modules.VoteModule;
import com.jparkie.givesmehope.presenters.VotePresenter;
import com.jparkie.givesmehope.views.VoteView;
import com.jparkie.givesmehope.views.fragments.base.BaseFragment;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VoteFragment extends BaseFragment implements VoteView {
    public static final String TAG = VoteFragment.class.getSimpleName();

    @Inject
    VotePresenter mVotePresenter;

    @InjectView(R.id.voteSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.voteCardView)
    CardView mCardView;
    @InjectView(R.id.voteImageView)
    ImageView mImageView;
    @InjectView(R.id.voteFooterTextView)
    TextView mFooterTextView;
    @InjectView(R.id.voteDownButton)
    FloatingActionButton mVoteDownButton;
    @InjectView(R.id.voteUpButton)
    FloatingActionButton mVoteUpButton;
    @InjectView(R.id.voteProgressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.voteNetworkingErrorImageView)
    ImageView mNetworkingErrorImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View voteView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_vote, container, false);

        ButterKnife.inject(this, voteView);

        return voteView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mVotePresenter.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mVotePresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mVotePresenter.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mVotePresenter.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mVotePresenter.onOptionsItemSelected(item);
    }

    @OnClick(R.id.voteDownButton)
    public void onVoteDown() {
        mVotePresenter.onVoteDown();
    }

    @OnClick(R.id.voteUpButton)
    public void onVoteUp() {
        mVotePresenter.onVoteUp();
    }

    // BaseFragment:

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new VoteModule(this)
        );
    }

    // VoteView:

    @Override
    public void initializeVoteSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accentColor));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mVotePresenter.onRefresh();
            }
        });
    }

    @Override
    public void initializeVoteNetworkingErrorImageView() {
        mNetworkingErrorImageView.setColorFilter(getResources().getColor(R.color.accentColor), PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void setVoteImageView(String imageUrl) {
        Picasso.with(getActivity())
                .load(imageUrl)
                .placeholder(R.mipmap.gmh_loading_story)
                .error(R.mipmap.gmh_no_story)
                .into(mImageView);
    }

    @Override
    public void setVoteFooterTextView(String footerText) {
        mFooterTextView.setText(footerText);
    }

    @Override
    public void showVoteSwipeRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideVoteSwipeRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean isVoteSwipeRefreshLayoutRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    @Override
    public void showVoteCardView() {
        mCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideVoteCardView() {
        mCardView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showVoteProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideVoteProgressBar() {
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
    public void toastVoteSuccess() {
        Toast.makeText(getActivity(), R.string.toast_vote_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toastVoteFailure() {
        Toast.makeText(getActivity(), R.string.toast_vote_failure, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
