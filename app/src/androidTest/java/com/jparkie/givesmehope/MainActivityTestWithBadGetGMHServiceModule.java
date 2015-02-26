package com.jparkie.givesmehope;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import com.jparkie.givesmehope.modules.BadGetGMHServiceModule;
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
public class MainActivityTestWithBadGetGMHServiceModule {
    public static final String TAG = MainActivityTestWithBadGetGMHServiceModule.class.getSimpleName();

    @Rule
    public final ActivityRule<MainActivity> mActivityRule = new ActivityRule<>(MainActivity.class);

    private MainActivity mMainActivity;

    @Before
    public void setUp() {
        prepareTestWithBadGetGMHServiceModule();

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
    public void testNetworkingErrorImageViewIsVisible() {
        onView(withId(R.id.hotNetworkingErrorImageView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.trendingNetworkingErrorImageView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.voteNetworkingErrorImageView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
    }

    @Test
    public void testHotRecyclerView() {
        onView(withId(R.id.hotRecyclerView)).check(matches(CustomViewMatchers.withChildCount(Matchers.lessThanOrEqualTo(0))));
    }

    @Test
    public void testTrendingRecyclerView() {
        onView(withId(R.id.trendingRecyclerView)).check(matches(CustomViewMatchers.withChildCount(Matchers.lessThanOrEqualTo(0))));
    }

    @Test
    public void testVoteCardView() {
        onView(withId(R.id.voteCardView)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)));
    }

    private void prepareTestWithBadGetGMHServiceModule() {
        GMHApplication currentApplication = GMHApplication.getApplication(InstrumentationRegistry.getTargetContext());
        currentApplication.initializeObjectGraph();
        currentApplication.setObjectGraph(
                new BadGetGMHServiceModule()
        );
    }
}
