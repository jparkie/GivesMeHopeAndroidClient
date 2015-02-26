package com.jparkie.givesmehope.modules;

import com.jparkie.givesmehope.data.services.GMHService;
import com.jparkie.givesmehope.presenters.VotePresenter;
import com.jparkie.givesmehope.presenters.VotePresenterImpl;
import com.jparkie.givesmehope.views.VoteView;
import com.jparkie.givesmehope.views.fragments.VoteFragment;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = VoteFragment.class,
        addsTo = GMHModule.class,
        complete = false
)
public final class VoteModule {
    public static final String TAG = VoteModule.class.getSimpleName();

    private VoteView mVoteView;

    public VoteModule(VoteView voteView) {
        this.mVoteView = voteView;
    }

    @Provides
    public VoteView provideView() {
        return mVoteView;
    }

    @Provides
    public VotePresenter providePresenter(VoteView voteView, GMHService gmhService) {
        return new VotePresenterImpl(voteView, gmhService);
    }
}
