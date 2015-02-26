package com.jparkie.givesmehope.modules;

import com.jparkie.givesmehope.data.services.GMHService;
import com.jparkie.givesmehope.presenters.HotPresenter;
import com.jparkie.givesmehope.presenters.HotPresenterImpl;
import com.jparkie.givesmehope.views.HotView;
import com.jparkie.givesmehope.views.fragments.HotFragment;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = HotFragment.class,
        addsTo = GMHModule.class,
        complete = false
)
public final class HotModule {
    public static final String TAG = HotModule.class.getSimpleName();

    private HotView mHotView;

    public HotModule(HotView hotView) {
        this.mHotView = hotView;
    }

    @Provides
    public HotView provideView() {
        return mHotView;
    }

    @Provides
    public HotPresenter providePresenter(HotView hotView, GMHService gmhService) {
        return new HotPresenterImpl(hotView, gmhService);
    }
}
