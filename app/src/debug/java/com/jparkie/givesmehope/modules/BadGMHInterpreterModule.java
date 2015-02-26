package com.jparkie.givesmehope.modules;

import com.jparkie.givesmehope.data.interpreters.BadGMHInterpreterImpl;
import com.jparkie.givesmehope.data.interpreters.GMHInterpreter;
import com.jparkie.givesmehope.data.services.DummyGMHServiceImpl;
import com.jparkie.givesmehope.data.services.GMHService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        addsTo = GMHModule.class,
        library = true,
        overrides = true
)
public final class BadGMHInterpreterModule {
    public static final String TAG = MockGMHInterpreterModule.class.getSimpleName();

    @Provides
    @Singleton
    public GMHInterpreter provideGMHInterpreter() {
        return new BadGMHInterpreterImpl();
    }

    @Provides
    @Singleton
    public GMHService provideGMHService(GMHInterpreter interpreter) {
        return new DummyGMHServiceImpl(interpreter);
    }
}
