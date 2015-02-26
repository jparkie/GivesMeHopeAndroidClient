package com.jparkie.givesmehope.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Story implements Parcelable {
    public static final String TAG = Story.class.getSimpleName();

    public static final String PARCELABLE_KEY = TAG + ":" + "ParcelableKey";

    private String mUrl;
    private String mPostId;

    private String mTitle;
    private String mCategory;
    private String mStory;

    private String mFooter;

    private String mImageUrl;

    public Story() {}

    private Story(String url, String postId, String title, String category, String story, String footer, String imageUrl) {
        this.mUrl = url;
        this.mPostId = postId;

        this.mTitle = title;
        this.mCategory = category;
        this.mStory = story;

        this.mFooter = footer;

        this.mImageUrl = imageUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getPostId() {
        return mPostId;
    }

    public void setPostId(String postId) {
        mPostId = postId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getStory() {
        return mStory;
    }

    public void setStory(String story) {
        mStory = story;
    }

    public String getFooter() {
        return mFooter;
    }

    public void setFooter(String footer) {
        mFooter = footer;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return String.format(
                TAG + ": %s of %s \n" +
                        "Title: %s... \n" +
                        "Category: %s... \n" +
                        "Story: %s...",
                mUrl, mPostId, mTitle, mCategory, mStory
        );
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel inputParcel) {
            return new Story(inputParcel);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    private Story(Parcel inputParcel) {
        this(
                inputParcel.readString(), // Url.
                inputParcel.readString(), // Post Id.
                inputParcel.readString(), // Title.
                inputParcel.readString(), // Category.
                inputParcel.readString(), // Story.
                inputParcel.readString(), // Footer.
                inputParcel.readString()  // Image Url
        );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel outputParcel, int flags) {
        outputParcel.writeString(mUrl);
        outputParcel.writeString(mPostId);

        outputParcel.writeString(mTitle);
        outputParcel.writeString(mCategory);
        outputParcel.writeString(mStory);

        outputParcel.writeString(mFooter);

        outputParcel.writeString(mImageUrl);
    }

    public static Story getParcelable(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return savedInstanceState.getParcelable(PARCELABLE_KEY);
        } else {
            throw new IllegalArgumentException(TAG + ": \'getParcelable\' Method has null argument: savedInstanceState.");
        }
    }

    public static void putParcelable(Bundle savedInstanceState, Story story) {
        if (savedInstanceState != null && story != null) {
            savedInstanceState.putParcelable(PARCELABLE_KEY, story);
        }
    }
}
