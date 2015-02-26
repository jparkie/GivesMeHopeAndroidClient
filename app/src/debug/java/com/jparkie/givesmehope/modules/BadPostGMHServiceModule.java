package com.jparkie.givesmehope.modules;

import com.jparkie.givesmehope.data.interpreters.GMHInterpreter;
import com.jparkie.givesmehope.data.interpreters.MockGMHInterpreterImpl;
import com.jparkie.givesmehope.data.services.BadGetGMHServiceImpl;
import com.jparkie.givesmehope.data.services.BadPostGMHServiceImpl;
import com.jparkie.givesmehope.data.services.GMHService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        addsTo = GMHModule.class,
        library = true,
        overrides = true
)
public final class BadPostGMHServiceModule {
    public static final String TAG = BadPostGMHServiceModule.class.getSimpleName();

    @Provides
    @Singleton
    public GMHInterpreter provideGMHInterpreter() {
        return new MockGMHInterpreterImpl();
    }

    @Provides
    @Singleton
    public GMHService provideGMHService(GMHInterpreter interpreter) {
        return new BadPostGMHServiceImpl(interpreter);
    }
}
