package com.jparkie.givesmehope;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import com.jparkie.givesmehope.modules.BadPostGMHServiceModule;
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
public class MainActivityTestWithBadPostGMHServiceModule {
    public static final String TAG = MainActivityTestWithBadPostGMHServiceModule.class.getSimpleName();

    @Rule
    public final ActivityRule<MainActivity> mActivityRule = new ActivityRule<>(MainActivity.class);

    private MainActivity mMainActivity;

    @Before
    public void setUp() {
        prepareTestWithBadPostGMHServiceModule();

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
    public void testSubmitDialogFragmentSubmitFailure() {
        onView(withId(R.id.action_submit)).perform(click());

        onView(withId(R.id.submitNameEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitLocationEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitTitleEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitStoryEditText)).perform(typeText("Lorem ipsum dolor..."));

        onView(isRoot()).perform(new CloseKeyboardAction());
        onView(allOf(isAssignableFrom(Button.class), withText(R.string.submit_dialog_button_submit))).perform(click());

        onView(withText(R.string.toast_submit_failure)).inRoot(withDecorView(not(is(mMainActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void testVoteDownFailure() {
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft());
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft());

        onView(withId(R.id.voteDownButton)).perform(click());

        onView(withText(R.string.toast_vote_failure)).inRoot(withDecorView(not(is(mMainActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void testVoteUpFailure() {
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft());
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft());

        onView(withId(R.id.voteUpButton)).perform(click());

        onView(withText(R.string.toast_vote_failure)).inRoot(withDecorView(not(is(mMainActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    private void prepareTestWithBadPostGMHServiceModule() {
        GMHApplication currentApplication = GMHApplication.getApplication(InstrumentationRegistry.getTargetContext());
        currentApplication.initializeObjectGraph();
        currentApplication.setObjectGraph(
                new BadPostGMHServiceModule()
        );
    }
}
