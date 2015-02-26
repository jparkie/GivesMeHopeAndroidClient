package com.jparkie.givesmehope.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Anthology implements Parcelable {
    public static final String TAG = Anthology.class.getSimpleName();

    public static final String PARCELABLE_KEY =  TAG + ":" + "ParcelableKey";

    private List<Story> mStories;

    private String mNextPageUrl;

    public Anthology() {}

    private Anthology(List<Story> stories, String nextPageUrl) {
        this.mStories = stories;

        this.mNextPageUrl = nextPageUrl;
    }

    public List<Story> getStories() {
        return this.mStories;
    }

    public void setStories(List<Story> stories) {
        mStories = stories;
    }

    public String getNextPageUrl() {
        return mNextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        mNextPageUrl = nextPageUrl;
    }

    public static final Creator<Anthology> CREATOR = new Creator<Anthology>() {
        @Override
        public Anthology createFromParcel(Parcel inputParcel) {
            return new Anthology(inputParcel);
        }

        @Override
        public Anthology[] newArray(int size) {
            return new Anthology[size];
        }
    };

    private Anthology(Parcel inputParcel) {
        this(
                new ArrayList<Story>(),   // Stories.
                inputParcel.readString()  // Next Page Url.
        );

        inputParcel.readTypedList(mStories, Story.CREATOR);
    }

    @Override
    public String toString() {
        return String.format(TAG + ": Next Page Url: %s.", mNextPageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel outputParcel, int flags) {
        outputParcel.writeTypedList(mStories);

        outputParcel.writeString(mNextPageUrl);
    }

    public static Anthology getParcelable(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return savedInstanceState.getParcelable(PARCELABLE_KEY);
        } else {
            throw new IllegalArgumentException(TAG + ": \'getParcelable\' Method has null argument: savedInstanceState.");
        }
    }

    public static void putParcelable(Bundle savedInstanceState, Anthology anthology) {
        if (savedInstanceState != null && anthology != null) {
            savedInstanceState.putParcelable(PARCELABLE_KEY, anthology);
        }
    }
}
