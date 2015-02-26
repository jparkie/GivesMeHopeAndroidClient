package com.jparkie.givesmehope;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import com.jparkie.givesmehope.data.factories.DefaultFactory;
import com.jparkie.givesmehope.modules.MockGMHServiceModule;
import com.jparkie.givesmehope.utils.ActivityRule;
import com.jparkie.givesmehope.utils.CustomViewActions;
import com.jparkie.givesmehope.utils.CustomViewMatchers;
import com.jparkie.givesmehope.views.activties.MainActivity;
import com.jparkie.givesmehope.views.fragments.SubmitFragment;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTestWithMockGMHServiceModule {
    public static final String TAG = MainActivityTestWithMockGMHServiceModule.class.getSimpleName();

    @Rule
    public final ActivityRule<MainActivity> mActivityRule = new ActivityRule<>(MainActivity.class);

    private MainActivity mMainActivity;

    @Before
    public void setUp() {
        prepareTestWithMockGMHServiceModule();

        mMainActivity = mActivityRule.get();

        onView(isRoot()).perform(CustomViewActions.waitFor(2500));
    }

    @After
    public void tearDown() {
        mMainActivity = null;
    }

    @Test
    public void checkPreconditions() {
        assertNotNull(mMainActivity);
    }

    @Test
    public void testProgressBarViewIsInvisible() {
        onView(withId(R.id.hotProgressBar)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)));
        onView(withId(R.id.trendingProgressBar)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)));
        onView(withId(R.id.voteProgressBar)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)));
    }

    @Test
    public void testNetworkingErrorImageViewIsInvisible() {
        onView(withId(R.id.hotNetworkingErrorImageView)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)));
        onView(withId(R.id.trendingNetworkingErrorImageView)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)));
        onView(withId(R.id.voteNetworkingErrorImageView)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)));
    }

    @Test
    public void testHotRecyclerView() {
        onView(withId(R.id.hotRecyclerView)).check(matches(CustomViewMatchers.withChildCount(Matchers.greaterThan(0))));
        onView(withId(R.id.hotRecyclerView)).check(matches(CustomViewMatchers.withRecyclerViewAdapterItemCount(Matchers.lessThanOrEqualTo(10))));
    }

    @Test
    public void testHotRecyclerViewLoading() {
        onView(withId(R.id.hotRecyclerView)).perform(swipeUp());
        onView(withId(R.id.hotRecyclerView)).perform(swipeUp());

        onView(isRoot()).perform(CustomViewActions.waitFor(2500));

        onView(withId(R.id.hotRecyclerView)).check(matches(CustomViewMatchers.withRecyclerViewAdapterItemCount(Matchers.greaterThan(10))));
    }

    @Test
    public void testHotRecyclerViewRefreshing() {
        onView(withId(R.id.hotRecyclerView)).perform(swipeDown());

        onView(isRoot()).perform(CustomViewActions.waitFor(2500));

        onView(withId(R.id.hotRecyclerView)).check(matches(CustomViewMatchers.withRecyclerViewAdapterItemCount(Matchers.lessThanOrEqualTo(10))));
    }

    @Test
    public void testTrendingRecyclerView() {
        onView(withId(R.id.trendingRecyclerView)).check(matches(CustomViewMatchers.withChildCount(Matchers.greaterThan(0))));
        onView(withId(R.id.trendingRecyclerView)).check(matches(CustomViewMatchers.withRecyclerViewAdapterItemCount(Matchers.lessThanOrEqualTo(10))));
    }

    @Test
    public void testTrendingRecyclerViewLoading() {
        onView(withId(R.id.mainViewPager)).perform(swipeLeft());

        onView(withId(R.id.trendingRecyclerView)).perform(swipeUp());
        onView(withId(R.id.trendingRecyclerView)).perform(swipeUp());

        onView(isRoot()).perform(CustomViewActions.waitFor(2500));

        onView(withId(R.id.trendingRecyclerView)).check(matches(CustomViewMatchers.withRecyclerViewAdapterItemCount(Matchers.greaterThan(10))));
    }

    @Test
    public void testTrendingRecyclerViewRefreshing() {
        onView(withId(R.id.mainViewPager)).perform(swipeLeft());

        onView(withId(R.id.trendingRecyclerView)).perform(swipeDown());

        onView(isRoot()).perform(CustomViewActions.waitFor(2500));

        onView(withId(R.id.trendingRecyclerView)).check(matches(CustomViewMatchers.withRecyclerViewAdapterItemCount(Matchers.lessThanOrEqualTo(10))));
    }

    @Test
    public void testVoteCardView() {
        onView(withId(R.id.voteImageView)).check(matches(Matchers.not(CustomViewMatchers.withDrawable(R.mipmap.gmh_loading_story))));
        onView(withId(R.id.voteImageView)).check(matches(Matchers.not(CustomViewMatchers.withDrawable(R.mipmap.gmh_no_story))));
        onView(withId(R.id.voteFooterTextView)).check(matches(Matchers.not(withText(DefaultFactory.Story.EMPTY_FIELD_FOOTER))));
    }

    @Test
    public void testVoteDownSuccess() {
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft());
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft());

        onView(withId(R.id.voteDownButton)).perform(click());

        onView(withText(R.string.toast_vote_success)).inRoot(withDecorView(not(is(mMainActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void testVoteUpSuccess() {
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft());
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft());

        onView(withId(R.id.voteUpButton)).perform(click());

        onView(withText(R.string.toast_vote_success)).inRoot(withDecorView(not(is(mMainActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void testSubmitDialogFragmentSubmitSuccess() {
        onView(withId(R.id.action_submit)).perform(click());

        onView(withId(R.id.submitNameEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitLocationEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitTitleEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitStoryEditText)).perform(typeText("Lorem ipsum dolor..."));

        onView(isRoot()).perform(new CloseKeyboardAction());
        onView(allOf(isAssignableFrom(Button.class), withText(R.string.submit_dialog_button_submit))).perform(click());

        onView(withText(R.string.toast_submit_success)).inRoot(withDecorView(not(is(mMainActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    private void prepareTestWithMockGMHServiceModule() {
        GMHApplication currentApplication = GMHApplication.getApplication(InstrumentationRegistry.getTargetContext());
        currentApplication.initializeObjectGraph();
        currentApplication.setObjectGraph(
                new MockGMHServiceModule()
        );
    }
}
