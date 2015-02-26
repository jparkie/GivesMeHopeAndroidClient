package com.jparkie.givesmehope.data.services;

import android.accounts.NetworkErrorException;

import com.jparkie.givesmehope.data.interpreters.GMHInterpreter;
import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.models.Story;
import com.squareup.okhttp.Response;

import rx.Observable;
import rx.Subscriber;

public class BadPostGMHServiceImpl implements GMHService {
    public static final String TAG = BadPostGMHServiceImpl.class.getSimpleName();

    private GMHInterpreter mInterpreter;

    public BadPostGMHServiceImpl(GMHInterpreter interpreter) {
        this.mInterpreter = interpreter;
    }

    @Override
    public Observable<Anthology> getHotAnthology(String requestUrl) {
        return Observable.create(new Observable.OnSubscribe<Anthology>() {
            @Override
            public void call(Subscriber<? super Anthology> subscriber) {
                try {
                    subscriber.onNext(mInterpreter.interpretHotAnthologyFromString(null));
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<Anthology> getTrendingAnthology(String requestUrl) {
        return Observable.create(new Observable.OnSubscribe<Anthology>() {
            @Override
            public void call(Subscriber<? super Anthology> subscriber) {
                try {
                    subscriber.onNext(mInterpreter.interpretHotAnthologyFromString(null));
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<Story> getVoteStory(String requestUrl) {
        return Observable.create(new Observable.OnSubscribe<Story>() {
            @Override
            public void call(Subscriber<? super Story> subscriber) {
                try {
                    subscriber.onNext(mInterpreter.interpretVoteStoryFromString(null));
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<Response> postSubmitStory(String submitName, String submitLocation, String submitTitle, String submitStory, String submitCategory) {
        return Observable.error(new NetworkErrorException(TAG + ": postSubmitStory() - Simulated Bad Network Request."));
    }

    @Override
    public Observable<Response> postVoteStoryDown(String postId) {
        return Observable.error(new NetworkErrorException(TAG + ": postVoteStoryDown() - Simulated Bad Network Request."));
    }

    @Override
    public Observable<Response> postVoteStoryUp(String postId) {
        return Observable.error(new NetworkErrorException(TAG + ": postVoteStoryUp() - Simulated Bad Network Request."));
    }
}
