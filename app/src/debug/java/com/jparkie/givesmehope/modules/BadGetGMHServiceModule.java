package com.jparkie.givesmehope.modules;

import com.jparkie.givesmehope.data.services.BadGetGMHServiceImpl;
import com.jparkie.givesmehope.data.services.GMHService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        addsTo = GMHModule.class,
        library = true,
        overrides = true
)
public final class BadGetGMHServiceModule {
    public static final String TAG = BadGetGMHServiceModule.class.getSimpleName();

    @Provides
    @Singleton
    public GMHService provideGMHService() {
        return new BadGetGMHServiceImpl();
    }
}
