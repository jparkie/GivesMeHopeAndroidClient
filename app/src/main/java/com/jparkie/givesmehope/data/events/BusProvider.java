package com.jparkie.givesmehope.data.events;

import com.squareup.otto.Bus;

public final class BusProvider {
    public static final String TAG = BusProvider.class.getSimpleName();

    private static Bus sInstance;

    private BusProvider() {
        throw new AssertionError(TAG + ": Cannot be initialized.");
    }

    public static Bus getInstance() {
        if (sInstance == null) {
            sInstance = new Bus();
        }

        return sInstance;
    }
}
