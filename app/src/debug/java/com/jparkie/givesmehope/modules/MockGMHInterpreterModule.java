package com.jparkie.givesmehope.modules;

import com.jparkie.givesmehope.data.interpreters.GMHInterpreter;
import com.jparkie.givesmehope.data.interpreters.MockGMHInterpreterImpl;
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
public final class MockGMHInterpreterModule {
    public static final String TAG = MockGMHInterpreterModule.class.getSimpleName();

    @Provides
    @Singleton
    public GMHInterpreter provideGMHInterpreter() {
        return new MockGMHInterpreterImpl();
    }

    @Provides
    @Singleton
    public GMHService provideGMHService(GMHInterpreter interpreter) {
        return new DummyGMHServiceImpl(interpreter);
    }
}
