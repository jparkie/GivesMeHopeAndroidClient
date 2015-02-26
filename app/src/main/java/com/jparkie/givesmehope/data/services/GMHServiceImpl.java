package com.jparkie.givesmehope.data.services;

import com.jparkie.givesmehope.data.interpreters.GMHInterpreter;
import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.models.Story;
import com.jparkie.givesmehope.modules.ApiModule;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.net.URLEncoder;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public final class GMHServiceImpl implements GMHService {
    public static final String TAG = GMHServiceImpl.class.getSimpleName();

    private OkHttpClient mClient;
    private GMHInterpreter mInterpreter;

    public GMHServiceImpl(OkHttpClient client, GMHInterpreter interpreter) {
        this.mClient = client;
        this.mInterpreter = interpreter;
    }

    @Override
    public Observable<Anthology> getHotAnthology(String requestUrl) {
        return getResponse(requestUrl)
                .flatMap(new Func1<Response, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response response) {
                        return mapResponseToHtml(response);
                    }
                })
                .flatMap(new Func1<String, Observable<Anthology>>() {
                    @Override
                    public Observable<Anthology> call(final String unparsedHtml) {
                        return Observable.create(new Observable.OnSubscribe<Anthology>() {
                            @Override
                            public void call(Subscriber<? super Anthology> subscriber) {
                                try {
                                    if (!subscriber.isUnsubscribed()) {
                                        subscriber.onNext(mInterpreter.interpretHotAnthologyFromString(unparsedHtml));
                                    }
                                    subscriber.onCompleted();
                                } catch (Throwable e) {
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public Observable<Anthology> getTrendingAnthology(String requestUrl) {
        return getResponse(requestUrl)
                .flatMap(new Func1<Response, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response response) {
                        return mapResponseToHtml(response);
                    }
                })
                .flatMap(new Func1<String, Observable<Anthology>>() {
                    @Override
                    public Observable<Anthology> call(final String unparsedHtml) {
                        return Observable.create(new Observable.OnSubscribe<Anthology>() {
                            @Override
                            public void call(Subscriber<? super Anthology> subscriber) {
                                try {
                                    if (!subscriber.isUnsubscribed()) {
                                        subscriber.onNext(mInterpreter.interpretTrendingAnthologyFromString(unparsedHtml));
                                    }
                                    subscriber.onCompleted();
                                } catch (Throwable e) {
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public Observable<Story> getVoteStory(String requestUrl) {
        return getResponse(requestUrl)
                .flatMap(new Func1<Response, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response response) {
                        return mapResponseToHtml(response);
                    }
                })
                .flatMap(new Func1<String, Observable<Story>>() {
                    @Override
                    public Observable<Story> call(final String unparsedHtml) {
                        return Observable.create(new Observable.OnSubscribe<Story>() {
                            @Override
                            public void call(Subscriber<? super Story> subscriber) {
                                try {
                                    if (!subscriber.isUnsubscribed()) {
                                        subscriber.onNext(mInterpreter.interpretVoteStoryFromString(unparsedHtml));
                                    }
                                    subscriber.onCompleted();
                                } catch (Throwable e) {
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public Observable<Response> postSubmitStory(final String submitName, final String submitLocation, final String submitTitle, final String submitStory, final String submitCategory) {
        return Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    final MediaType CONSTANT_MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
                    final String CONSTANT_CHAR_SET = "UTF-8";
                    final String CONSTANT_FORM_STORY_SUBMIT = "story_submit";
                    final String CONSTANT_FORM_USERNAME = "username";
                    final String CONSTANT_FORM_LOCATION = "location";
                    final String CONSTANT_FORM_TITLE = "title";
                    final String CONSTANT_FORM_CATEGORY = "category";
                    final String CONSTANT_FORM_CONTENT = "content";
                    final String CONSTANT_FORM_SEND = "send";
                    final String CONSTANT_FORM_SUBMITTED = "submitted";
                    final String CONSTANT_FORM_CALLER = "caller";

                    StringBuilder submissionContent = new StringBuilder();
                    submissionContent.append(CONSTANT_FORM_STORY_SUBMIT + "=" + URLEncoder.encode("1", CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_USERNAME + "=" + URLEncoder.encode(submitName, CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_LOCATION + "=" + URLEncoder.encode(submitLocation, CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_TITLE + "=" + URLEncoder.encode(submitTitle, CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_CATEGORY + "=" + URLEncoder.encode(submitCategory, CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_CONTENT + "=" + URLEncoder.encode(submitStory, CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_SEND + "=" + URLEncoder.encode("Submit", CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_SUBMITTED + "=" + URLEncoder.encode("1", CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_CALLER + "=" + URLEncoder.encode("builder.js", CONSTANT_CHAR_SET));

                    RequestBody body = RequestBody.create(CONSTANT_MEDIA_TYPE, submissionContent.toString());
                    Request request = new Request.Builder()
                            .url(ApiModule.SUBMIT_URL)
                            .post(body)
                            .build();

                    subscriber.onNext(mClient.newCall(request).execute());
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<Response> postVoteStoryDown(final String postId) {
        return Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    final MediaType CONSTANT_MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
                    final String CONSTANT_CHAR_SET = "UTF-8";
                    final String CONSTANT_FORM_ACTION = "action";
                    final String CONSTANT_FORM_POST_ID = "post_id";
                    final String CONSTANT_FORM_LIST = "list";
                    final String CONSTANT_FORM_DECISION = "decision";

                    StringBuilder submissionContent = new StringBuilder();
                    submissionContent.append(CONSTANT_FORM_ACTION + "=" + URLEncoder.encode("vote", CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_POST_ID + "=" + URLEncoder.encode(postId, CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_LIST + "=" + URLEncoder.encode("0", CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_DECISION + "=" + URLEncoder.encode("no", CONSTANT_CHAR_SET));

                    RequestBody body = RequestBody.create(CONSTANT_MEDIA_TYPE, submissionContent.toString());
                    Request request = new Request.Builder()
                            .url(ApiModule.VOTE_ACTION_URL)
                            .post(body)
                            .build();

                    subscriber.onNext(mClient.newCall(request).execute());
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<Response> postVoteStoryUp(final String postId) {
        return Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    final MediaType CONSTANT_MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
                    final String CONSTANT_CHAR_SET = "UTF-8";
                    final String CONSTANT_FORM_ACTION = "action";
                    final String CONSTANT_FORM_POST_ID = "post_id";
                    final String CONSTANT_FORM_LIST = "list";
                    final String CONSTANT_FORM_DECISION = "decision";

                    StringBuilder submissionContent = new StringBuilder();
                    submissionContent.append(CONSTANT_FORM_ACTION + "=" + URLEncoder.encode("vote", CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_POST_ID + "=" + URLEncoder.encode(postId, CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_LIST + "=" + URLEncoder.encode("0", CONSTANT_CHAR_SET));
                    submissionContent.append("&" + CONSTANT_FORM_DECISION + "=" + URLEncoder.encode("yes", CONSTANT_CHAR_SET));

                    RequestBody body = RequestBody.create(CONSTANT_MEDIA_TYPE, submissionContent.toString());
                    Request request = new Request.Builder()
                            .url(ApiModule.VOTE_ACTION_URL)
                            .post(body)
                            .build();

                    subscriber.onNext(mClient.newCall(request).execute());
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observable<Response> getResponse(final String requestUrl) {
        return Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    Request request = new Request.Builder()
                            .url(requestUrl)
                            .build();

                    subscriber.onNext(mClient.newCall(request).execute());
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observable<String> mapResponseToHtml(final Response response) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(response.body().string());
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
