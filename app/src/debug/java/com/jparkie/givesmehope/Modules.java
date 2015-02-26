package com.jparkie.givesmehope;

import com.jparkie.givesmehope.modules.GMHModule;

import java.util.Arrays;
import java.util.List;

public final class Modules {
    public static final String TAG = Modules.class.getSimpleName();

    private Modules() {
        throw new AssertionError(TAG + ": Cannot be initialized.");
    }

    public static List<Object> getModules(GMHApplication application) {
        return Arrays.<Object>asList(
                new GMHModule(application),
                new DebugGMHModule()
        );
    }
}
