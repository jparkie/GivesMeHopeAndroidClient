package com.jparkie.givesmehope;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import com.jparkie.givesmehope.utils.ActivityRule;
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
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTestWithNoDebugModules {
    public static final String TAG = MainActivityTestWithNoDebugModules.class.getSimpleName();

    @Rule
    public final ActivityRule<MainActivity> mActivityRule = new ActivityRule<>(MainActivity.class);

    private MainActivity mMainActivity;

    @Before
    public void setUp() {
        mMainActivity = mActivityRule.get();
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
    public void testMainViewPager() {
        onView(withId(R.id.mainViewPager)).check(matches(CustomViewMatchers.withViewPagerCurrentItem(Matchers.equalTo(0))));
        onView(withId(R.id.mainToolbar)).check(matches(CustomViewMatchers.withToolbarTitle(Matchers.equalTo(InstrumentationRegistry.getTargetContext().getString(R.string.fragment_title_hot)))));

        onView(withId(R.id.mainViewPager)).perform(swipeLeft());
        onView(withId(R.id.mainViewPager)).check(matches(CustomViewMatchers.withViewPagerCurrentItem(Matchers.equalTo(1))));
        onView(withId(R.id.mainToolbar)).check(matches(CustomViewMatchers.withToolbarTitle(Matchers.equalTo(InstrumentationRegistry.getTargetContext().getString(R.string.fragment_title_trending)))));

        onView(withId(R.id.mainViewPager)).perform(swipeLeft());
        onView(withId(R.id.mainViewPager)).check(matches(CustomViewMatchers.withViewPagerCurrentItem(Matchers.equalTo(2))));
        onView(withId(R.id.mainToolbar)).check(matches(CustomViewMatchers.withToolbarTitle(Matchers.equalTo(InstrumentationRegistry.getTargetContext().getString(R.string.fragment_title_vote)))));
    }

    @Test
    public void testDisclaimerDialogFragment() {
        openActionBarOverflowOrOptionsMenu(mActivityRule.instrumentation().getTargetContext());

        onView(withText(R.string.action_disclaimer)).perform(click());

        onView(withText(R.string.disclaimer_dialog_title)).check(matches(isDisplayed()));
    }

    @Test
    public void testSubmitDialogFragmentShow() {
        onView(withId(R.id.action_submit)).perform(click());

        onView(withId(R.id.submitScrollView)).check(matches(isDisplayed()));
    }

    @Test
    public void testSubmitDialogFragmentReset() {
        onView(withId(R.id.action_submit)).perform(click());

        onView(withId(R.id.submitNameEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitLocationEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitTitleEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitStoryEditText)).perform(typeText("Lorem ipsum dolor..."));

        onView(withText(R.string.submit_dialog_button_reset)).perform(click());

        onView(withId(R.id.submitNameEditText)).check(matches(withText(Matchers.equalToIgnoringWhiteSpace(""))));
        onView(withId(R.id.submitLocationEditText)).check(matches(withText(Matchers.equalToIgnoringWhiteSpace(""))));
        onView(withId(R.id.submitTitleEditText)).check(matches(withText(Matchers.equalToIgnoringWhiteSpace(""))));
        onView(withId(R.id.submitStoryEditText)).check(matches(withText(Matchers.equalToIgnoringWhiteSpace(""))));
    }

    @Test
    public void testSubmitDialogFragmentTitleError() {
        onView(withId(R.id.action_submit)).perform(click());

        onView(withId(R.id.submitNameEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitLocationEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitStoryEditText)).perform(typeText("Lorem ipsum dolor..."));

        onView(isRoot()).perform(new CloseKeyboardAction());
        onView(allOf(isAssignableFrom(Button.class), withText(R.string.submit_dialog_button_submit))).perform(click());

        SubmitFragment submitFragment = (SubmitFragment)mMainActivity.getSupportFragmentManager().findFragmentByTag(SubmitFragment.TAG);
        onView(withText(R.string.toast_submit_error_title)).inRoot(withDecorView(not(is(submitFragment.getDialog().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void testSubmitDialogFragmentStoryError() {
        onView(withId(R.id.action_submit)).perform(click());

        onView(withId(R.id.submitNameEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitLocationEditText)).perform(typeText("Lorem ipsum dolor..."));
        onView(withId(R.id.submitTitleEditText)).perform(typeText("Lorem ipsum dolor..."));

        onView(isRoot()).perform(new CloseKeyboardAction());
        onView(allOf(isAssignableFrom(Button.class), withText(R.string.submit_dialog_button_submit))).perform(click());

        SubmitFragment submitFragment = (SubmitFragment)mMainActivity.getSupportFragmentManager().findFragmentByTag(SubmitFragment.TAG);
        onView(withText(R.string.toast_submit_error_story)).inRoot(withDecorView(not(is(submitFragment.getDialog().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
