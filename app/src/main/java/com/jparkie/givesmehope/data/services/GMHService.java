package com.jparkie.givesmehope.data.services;

import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.models.Story;
import com.squareup.okhttp.Response;

import rx.Observable;

public interface GMHService {
    public Observable<Anthology> getHotAnthology(String requestUrl);

    public Observable<Anthology> getTrendingAnthology(String requestUrl);

    public Observable<Story> getVoteStory(String requestUrl);

    public Observable<Response> postSubmitStory(String submitName, String submitLocation, String submitTitle, String submitStory, String submitCategory);

    public Observable<Response> postVoteStoryDown(String postId);

    public Observable<Response> postVoteStoryUp(String postId);
}
