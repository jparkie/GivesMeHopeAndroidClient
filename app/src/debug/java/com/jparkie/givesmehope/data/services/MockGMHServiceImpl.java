package com.jparkie.givesmehope.data.services;

import android.content.Context;
import android.content.res.AssetManager;

import com.jparkie.givesmehope.data.interpreters.GMHInterpreter;
import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.models.Story;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MockGMHServiceImpl implements GMHService {
    public static final String TAG = MockGMHServiceImpl.class.getSimpleName();

    private Context mContext;
    private GMHInterpreter mInterpreter;

    public MockGMHServiceImpl(Context context, GMHInterpreter interpreter) {
        this.mContext = context;
        this.mInterpreter = interpreter;
    }

    @Override
    public Observable<Anthology> getHotAnthology(String requestUrl) {
        return getStringFromHtml("Hot.html")
                .flatMap(new Func1<String, Observable<Anthology>>() {
                    @Override
                    public Observable<Anthology> call(String unparsedHtml) {
                        return Observable.just(mInterpreter.interpretHotAnthologyFromString(unparsedHtml));
                    }
                });
    }

    @Override
    public Observable<Anthology> getTrendingAnthology(String requestUrl) {
        return getStringFromHtml("Trending.html")
                .flatMap(new Func1<String, Observable<Anthology>>() {
                    @Override
                    public Observable<Anthology> call(String unparsedHtml) {
                        return Observable.just(mInterpreter.interpretTrendingAnthologyFromString(unparsedHtml));
                    }
                });
    }

    @Override
    public Observable<Story> getVoteStory(String requestUrl) {
        return getStringFromHtml("Vote.html")
                .flatMap(new Func1<String, Observable<Story>>() {
                    @Override
                    public Observable<Story> call(String unparsedHtml) {
                        return Observable.just(mInterpreter.interpretVoteStoryFromString(unparsedHtml));
                    }
                });
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

    private Observable<String> getStringFromHtml(final String fileName) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    AssetManager assetManager = mContext.getAssets();

                    InputStream inputStream = null;
                    BufferedReader bufferedReader = null;
                    StringBuilder stringBuilder = new StringBuilder();

                    try {
                        inputStream = assetManager.open("mock/html/" + fileName);
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                        String currentLine = null;
                        while ((currentLine = bufferedReader.readLine()) != null) {
                            stringBuilder.append(currentLine);
                        }
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                    }

                    subscriber.onNext(stringBuilder.toString());
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
