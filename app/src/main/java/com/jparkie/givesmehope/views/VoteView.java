package com.jparkie.givesmehope.views;

import com.jparkie.givesmehope.views.base.BaseContextView;

public interface VoteView extends BaseContextView {
    public void initializeVoteSwipeRefreshLayout();

    public void initializeVoteNetworkingErrorImageView();

    public void setVoteImageView(String imageUrl);

    public void setVoteFooterTextView(String footerText);

    public void showVoteSwipeRefreshLayout();

    public void hideVoteSwipeRefreshLayout();

    public boolean isVoteSwipeRefreshLayoutRefreshing();

    public void showVoteCardView();

    public void hideVoteCardView();

    public void showVoteProgressBar();

    public void hideVoteProgressBar();

    public void showNetworkingErrorImageView();

    public void hideNetworkingErrorImageView();

    public void toastVoteSuccess();

    public void toastVoteFailure();
}
