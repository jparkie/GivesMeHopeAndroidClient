package com.jparkie.givesmehope.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.data.events.BusProvider;
import com.jparkie.givesmehope.data.events.LatestTrendingPositionEvent;
import com.jparkie.givesmehope.models.Story;
import com.jparkie.givesmehope.views.adapters.viewholders.TrendingViewHolder;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingViewHolder> {
    public static final String TAG = TrendingAdapter.class.getSimpleName();

    private List<Story> mStories;

    public TrendingAdapter(List<Story> stories) {
        this.mStories = stories;
    }

    @Override
    public TrendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrendingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(TrendingViewHolder holder, int position) {
        BusProvider.getInstance().post(new LatestTrendingPositionEvent(position));

        holder.bindStoryToView(mStories.get(position));
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    public List<Story> getStories() {
        return mStories;
    }

    public void addStories(List<Story> newStories) {
        int currentSize = mStories.size();
        int amountInserted = newStories.size();

        mStories.addAll(newStories);

        notifyItemRangeInserted(currentSize, amountInserted);
    }
}
