package com.jparkie.givesmehope.views.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.models.Story;
import com.jparkie.givesmehope.views.TrendingView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TrendingViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = TrendingView.class.getSimpleName();

    @InjectView(R.id.textTitleTextView)
    TextView mTitleTextView;
    @InjectView(R.id.textStoryTextView)
    TextView mStoryTextView;
    @InjectView(R.id.textFooterTextView)
    TextView mFooterTextView;

    public TrendingViewHolder(View itemView) {
        super(itemView);

        ButterKnife.inject(this, itemView);
    }

    public void bindStoryToView(Story story) {
        mTitleTextView.setText(story.getTitle());
        mStoryTextView.setText(story.getStory());
        mFooterTextView.setText(story.getFooter());
    }
}
