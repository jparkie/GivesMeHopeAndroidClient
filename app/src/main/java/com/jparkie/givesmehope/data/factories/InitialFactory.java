package com.jparkie.givesmehope.data.factories;

import com.jparkie.givesmehope.models.Story;

import java.util.ArrayList;

public final class InitialFactory {
    public static final String TAG = InitialFactory.class.getSimpleName();

    private InitialFactory() {
        throw new AssertionError(TAG + ": Cannot be initialized.");
    }

    public static final class Anthology {
        public static final String TAG = Anthology.class.getSimpleName();

        public static final String INITIAL_FIELD_CURRENT_TRENDING_PAGE_URL = "http://mobile.givesmehope.com/page/1/";
        public static final String INITIAL_FIELD_CURRENT_HOT_PAGE_URL = "http://mobile.givesmehope.com/bestof/month/";

        private Anthology() {
            throw new AssertionError(TAG + ": Cannot be initialized.");
        }

        public static com.jparkie.givesmehope.models.Anthology constructInitialTrendingInstance() {
            final com.jparkie.givesmehope.models.Anthology temporaryInstance = DefaultFactory.Anthology.constructDefaultInstance();

            temporaryInstance.setStories(new ArrayList<Story>());

            temporaryInstance.setNextPageUrl(INITIAL_FIELD_CURRENT_TRENDING_PAGE_URL);

            return temporaryInstance;
        }

        public static com.jparkie.givesmehope.models.Anthology constructInitialHotInstance() {
            final com.jparkie.givesmehope.models.Anthology temporaryInstance = DefaultFactory.Anthology.constructDefaultInstance();

            temporaryInstance.setStories(new ArrayList<Story>());

            temporaryInstance.setNextPageUrl(INITIAL_FIELD_CURRENT_HOT_PAGE_URL);

            return temporaryInstance;
        }
    }
}
