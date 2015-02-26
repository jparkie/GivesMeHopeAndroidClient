package com.jparkie.givesmehope.modules;

import com.jparkie.givesmehope.data.services.GMHService;
import com.jparkie.givesmehope.presenters.TrendingPresenter;
import com.jparkie.givesmehope.presenters.TrendingPresenterImpl;
import com.jparkie.givesmehope.views.TrendingView;
import com.jparkie.givesmehope.views.fragments.TrendingFragment;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = TrendingFragment.class,
        addsTo = GMHModule.class,
        complete = false
)
public final class TrendingModule {
    public static final String TAG = TrendingModule.class.getSimpleName();

    private TrendingView mTrendingView;

    public TrendingModule(TrendingView trendingView) {
        this.mTrendingView = trendingView;
    }

    @Provides
    public TrendingView provideView() {
        return mTrendingView;
    }

    @Provides
    public TrendingPresenter providePresenter(TrendingView trendingView, GMHService gmhService) {
        return new TrendingPresenterImpl(trendingView, gmhService);
    }
}
