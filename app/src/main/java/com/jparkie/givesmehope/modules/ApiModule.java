package com.jparkie.givesmehope.modules;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.jparkie.givesmehope.BuildConfig;
import com.jparkie.givesmehope.data.interpreters.GMHInterpreter;
import com.jparkie.givesmehope.data.interpreters.GMHInterpreterImpl;
import com.jparkie.givesmehope.data.services.GMHService;
import com.jparkie.givesmehope.data.services.GMHServiceImpl;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        library = true
)
public final class ApiModule {
    public static final String TAG = ApiModule.class.getSimpleName();

    public static final String API_URL = "http://mobile.givesmehope.com";
    public static final String INITIAL_TRENDING_PAGE_URL = "http://mobile.givesmehope.com/page/1/";
    public static final String INITIAL_HOT_PAGE_URL = "http://mobile.givesmehope.com/bestof/month/";
    public static final String SUBMIT_URL = "http://mobile.givesmehope.com/submit_new.php";
    public static final String VOTE_URL = "http://mobile.givesmehope.com/moderate";
    public static final String VOTE_ACTION_URL = "http://mobile.givesmehope.com/script/post_action.php";

    public static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    public static final int PULL_TOLERANCE = 10;

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Application application) {
        return createOkHttpClient(application);
    }

    @Provides
    @Singleton
    public GMHInterpreter provideGMHInterpreter() {
        return new GMHInterpreterImpl();
    }

    @Provides
    @Singleton
    public GMHService provideGMHService(OkHttpClient client, GMHInterpreter interpreter) {
        return new GMHServiceImpl(client, interpreter);
    }

    private static OkHttpClient createOkHttpClient(Application application) {
        final OkHttpClient temporaryClient = new OkHttpClient();

        try {
            File cacheDirectory = application.getCacheDir();
            Cache cache = new Cache(cacheDirectory, DISK_CACHE_SIZE);
            temporaryClient.setCache(cache);
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "Unable to initialize OkHttpClient with disk cache.");
            }
        }

        if (BuildConfig.DEBUG) {
            temporaryClient.networkInterceptors().add(new StethoInterceptor());
        }

        return temporaryClient;
    }
}
