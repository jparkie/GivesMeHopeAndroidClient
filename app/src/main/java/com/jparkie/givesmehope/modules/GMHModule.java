package com.jparkie.givesmehope.modules;

import android.app.Application;

import com.jparkie.givesmehope.GMHApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
                ApiModule.class
        },
        injects = {
                GMHApplication.class
        }
)
public final class GMHModule {
    public static final String TAG = GMHModule.class.getSimpleName();

    private final GMHApplication mApplication;

    public GMHModule(GMHApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }
}
