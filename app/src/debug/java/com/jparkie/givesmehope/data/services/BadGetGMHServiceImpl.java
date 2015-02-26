package com.jparkie.givesmehope.data.services;

import android.accounts.NetworkErrorException;

import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.models.Story;
import com.squareup.okhttp.Response;

import rx.Observable;

public class BadGetGMHServiceImpl implements GMHService {
    public static final String TAG = BadGetGMHServiceImpl.class.getSimpleName();

    public BadGetGMHServiceImpl() {}

    @Override
    public Observable<Anthology> getHotAnthology(String requestUrl) {
        return Observable.error(new NetworkErrorException(TAG + ": getHotAnthology() - Simulated Bad Network Request."));
    }

    @Override
    public Observable<Anthology> getTrendingAnthology(String requestUrl) {
        return Observable.error(new NetworkErrorException(TAG + ": getTrendingAnthology() - Simulated Bad Network Request."));
    }

    @Override
    public Observable<Story> getVoteStory(String requestUrl) {
        return Observable.error(new NetworkErrorException(TAG + ": getVoteStory() - Simulated Bad Network Request."));
    }

    @Override
    public Observable<Response> postSubmitStory(String submitName, String submitLocation, String submitTitle, String submitStory, String submitCategory) {
        return Observable.empty();
    }

    @Override
    public Observable<Response> postVoteStoryDown(String postId) {
        return Observable.empty();
    }

    @Override
    public Observable<Response> postVoteStoryUp(String postId) {
        return Observable.empty();
    }
}
