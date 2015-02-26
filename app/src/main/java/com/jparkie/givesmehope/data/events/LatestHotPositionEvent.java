package com.jparkie.givesmehope.data.events;

public class LatestHotPositionEvent {
    public static final String TAG = LatestHotPositionEvent.class.getSimpleName();

    private int mPosition;

    public LatestHotPositionEvent(int position) {
        this.mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }
}
