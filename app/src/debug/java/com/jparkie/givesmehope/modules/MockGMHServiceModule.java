package com.jparkie.givesmehope.modules;

import android.app.Application;

import com.jparkie.givesmehope.data.interpreters.GMHInterpreter;
import com.jparkie.givesmehope.data.interpreters.GMHInterpreterImpl;
import com.jparkie.givesmehope.data.services.GMHService;
import com.jparkie.givesmehope.data.services.MockGMHServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        addsTo = GMHModule.class,
        complete = false,
        library = true,
        overrides = true
)
public final class MockGMHServiceModule {
    public static final String TAG = MockGMHServiceModule.class.getSimpleName();

    @Provides
    @Singleton
    public GMHInterpreter provideGMHInterpreter() {
        return new GMHInterpreterImpl();
    }

    @Provides
    @Singleton
    public GMHService provideGMHService(Application application, GMHInterpreter interpreter) {
        return new MockGMHServiceImpl(application, interpreter);
    }
}
