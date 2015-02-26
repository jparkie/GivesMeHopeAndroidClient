package com.jparkie.givesmehope.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jparkie.givesmehope.R;
import com.jparkie.givesmehope.data.events.BusProvider;
import com.jparkie.givesmehope.data.events.LatestHotPositionEvent;
import com.jparkie.givesmehope.models.Story;
import com.jparkie.givesmehope.views.adapters.viewholders.HotViewHolder;

import java.util.List;

public class HotAdapter extends RecyclerView.Adapter<HotViewHolder> {
    public static final String TAG = HotAdapter.class.getSimpleName();

    private Context mContext;
    private List<Story> mStories;

    public HotAdapter(Context context, List<Story> stories) {
        this.mContext = context;
        this.mStories = stories;
    }

    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(HotViewHolder holder, int position) {
        BusProvider.getInstance().post(new LatestHotPositionEvent(position));

        holder.bindStoryToView(mContext, mStories.get(position));
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
