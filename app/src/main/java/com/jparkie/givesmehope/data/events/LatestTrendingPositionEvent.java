package com.jparkie.givesmehope.data.events;

public class LatestTrendingPositionEvent {
    public static final String TAG = LatestTrendingPositionEvent.class.getSimpleName();

    private int mPosition;

    public LatestTrendingPositionEvent(int position) {
        this.mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }
}
