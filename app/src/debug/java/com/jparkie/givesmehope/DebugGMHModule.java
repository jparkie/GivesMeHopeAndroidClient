package com.jparkie.givesmehope;

import com.jparkie.givesmehope.modules.GMHModule;

import dagger.Module;

@Module(
        addsTo = GMHModule.class,
        includes = {
                // BadGMHInterpreterModule.class,
                // BadGetGMHServiceModule.class,
                // BadPostGMHServiceModule.class,
                // MockGMHInterpreterModule.class,
                // MockGMHServiceModule.class,
        },
        overrides = true
)
public final class DebugGMHModule {
    public static final String TAG = DebugGMHModule.class.getSimpleName();
}
