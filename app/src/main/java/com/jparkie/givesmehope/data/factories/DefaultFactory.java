package com.jparkie.givesmehope.data.factories;

import java.util.ArrayList;

public final class DefaultFactory {
    public static final String TAG = DefaultFactory.class.getSimpleName();

    private DefaultFactory() {
        throw new AssertionError(TAG + ": Cannot be initialized.");
    }

    public static final class Anthology {
        public static final String TAG = Anthology.class.getSimpleName();

        public static final String EMPTY_FIELD_NEXT_PAGE_URL = "No Next Page URL";

        private Anthology() {
            throw new AssertionError(TAG + ": Cannot be initialized.");
        }

        public static com.jparkie.givesmehope.models.Anthology constructDefaultInstance() {
            final com.jparkie.givesmehope.models.Anthology temporaryInstance = new com.jparkie.givesmehope.models.Anthology();

            temporaryInstance.setStories(new ArrayList<com.jparkie.givesmehope.models.Story>());

            temporaryInstance.setNextPageUrl(EMPTY_FIELD_NEXT_PAGE_URL);

            return temporaryInstance;
        }
    }

    public static final class Story {
        public static final String TAG = Story.class.getSimpleName();

        public static final String EMPTY_FIELD_URL = "No URL";
        public static final String EMPTY_FIELD_POST_ID = "No Post Id";

        public static final String EMPTY_FIELD_TITLE = "No Title";
        public static final String EMPTY_FIELD_CATEGORY = "No Category";
        public static final String EMPTY_FIELD_STORY = "No Story";

        public static final String EMPTY_FIELD_FOOTER = "No Footer";

        public static final String EMPTY_FIELD_IMAGE_URL = "No Image Url";

        private Story() {
            throw new AssertionError(TAG + ": Cannot be initialized.");
        }

        public static com.jparkie.givesmehope.models.Story constructDefaultInstance() {
            final com.jparkie.givesmehope.models.Story temporaryInstance = new com.jparkie.givesmehope.models.Story();

            temporaryInstance.setUrl(EMPTY_FIELD_URL);
            temporaryInstance.setPostId(EMPTY_FIELD_POST_ID);

            temporaryInstance.setTitle(EMPTY_FIELD_TITLE);
            temporaryInstance.setCategory(EMPTY_FIELD_CATEGORY);
            temporaryInstance.setStory(EMPTY_FIELD_STORY);

            temporaryInstance.setFooter(EMPTY_FIELD_FOOTER);

            temporaryInstance.setImageUrl(EMPTY_FIELD_IMAGE_URL);

            return temporaryInstance;
        }
    }
}
