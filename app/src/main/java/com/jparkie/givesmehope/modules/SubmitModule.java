package com.jparkie.givesmehope.modules;

import com.jparkie.givesmehope.data.services.GMHService;
import com.jparkie.givesmehope.presenters.SubmitPresenter;
import com.jparkie.givesmehope.presenters.SubmitPresenterImpl;
import com.jparkie.givesmehope.views.SubmitView;
import com.jparkie.givesmehope.views.fragments.SubmitFragment;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = SubmitFragment.class,
        addsTo = GMHModule.class,
        complete = false
)
public final class SubmitModule {
    public static final String TAG = SubmitModule.class.getSimpleName();

    private SubmitView mSubmitView;

    public SubmitModule(SubmitView submitView) {
        this.mSubmitView = submitView;
    }

    @Provides
    public SubmitView provideView() {
        return mSubmitView;
    }

    @Provides
    public SubmitPresenter providePresenter(SubmitView submitView, GMHService gmhService) {
        return new SubmitPresenterImpl(submitView, gmhService);
    }
}
