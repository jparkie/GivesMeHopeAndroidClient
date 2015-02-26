package com.jparkie.givesmehope.utils;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import android.support.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CustomViewMatchers {
    // https://github.com/xrigau/droidcon-android-espresso/blob/master/app/src/instrumentTest/java/com/xrigau/droidcon/espresso/helper/EspressoTestsMatchers.java
    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    // https://github.com/xrigau/droidcon-android-espresso/blob/master/app/src/instrumentTest/java/com/xrigau/droidcon/espresso/helper/EspressoTestsMatchers.java
    public static Matcher<View> withChildCount(final Matcher<Integer> numberMatcher) {
        return new BoundedMatcher<View, ViewGroup>(ViewGroup.class) {
            @Override
            protected boolean matchesSafely(ViewGroup viewGroup) {
                return numberMatcher.matches(viewGroup.getChildCount());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("number of child views ");
                numberMatcher.describeTo(description);
            }
        };
    }

    public static Matcher<View> withToolbarTitle(final Matcher<String> stringMatcher) {
        return new BoundedMatcher<View, Toolbar>(Toolbar.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("current item ");
                stringMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(Toolbar toolbar) {
                return stringMatcher.matches(toolbar.getTitle());
            }
        };
    }

    public static Matcher<View> withViewPagerCurrentItem(final Matcher<Integer> numberMatcher) {
        return new BoundedMatcher<View, ViewPager>(ViewPager.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("current item ");
                numberMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(ViewPager viewPager) {
                return numberMatcher.matches(viewPager.getCurrentItem());
            }
        };
    }

    public static Matcher<View> withRecyclerViewAdapterItemCount(final Matcher<Integer> numberMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("number of adapter items ");
                numberMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                if (recyclerView.getAdapter() != null) {
                    return numberMatcher.matches(recyclerView.getAdapter().getItemCount());
                }

                return false;
            }
        };
    }
}