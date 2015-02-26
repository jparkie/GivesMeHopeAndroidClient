package com.jparkie.givesmehope.views.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.models.Story;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HotViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = HotViewHolder.class.getSimpleName();

    @InjectView(R.id.imageImageView)
    ImageView mImageView;
    @InjectView(R.id.imageFooterTextView)
    TextView mFooterTextView;

    public HotViewHolder(View itemView) {
        super(itemView);

        ButterKnife.inject(this, itemView);
    }

    public void bindStoryToView(Context context, Story story) {
        Picasso.with(context)
                .load(story.getImageUrl())
                .placeholder(R.mipmap.gmh_loading_story)
                .error(R.mipmap.gmh_no_story)
                .into(mImageView);

        mFooterTextView.setText(story.getFooter());
    }
}
