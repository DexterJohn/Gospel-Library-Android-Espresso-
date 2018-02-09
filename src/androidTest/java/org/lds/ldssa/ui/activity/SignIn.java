package org.lds.ldssa.ui.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.ldssa.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignIn {

    @Rule
    public ActivityTestRule<StartupActivity> mActivityTestRule = new ActivityTestRule<>(StartupActivity.class);

    @Test
    public void signIn() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.skip), withText("Skip"),
                        childAtPosition(
                                allOf(withId(R.id.bottomContainer),
                                        childAtPosition(
                                                withId(R.id.bottom),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appIntroViewPager = onView(
                allOf(withId(R.id.view_pager),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appIntroViewPager.perform(swipeLeft());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.usernameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("s"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.usernameEditText), withText("s"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), replaceText("sm"));

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.usernameEditText), withText("sm"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.usernameEditText), withText("sm"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0)));
        textInputEditText4.perform(scrollTo(), replaceText("smi"));

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.usernameEditText), withText("smi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.usernameEditText), withText("smi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0)));
        textInputEditText6.perform(scrollTo(), replaceText("smith"));

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0)));
        textInputEditText8.perform(scrollTo(), replaceText("smith."));

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0)));
        textInputEditText10.perform(scrollTo(), replaceText("smith.c"));

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith.c"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText11.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith.c"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0)));
        textInputEditText12.perform(scrollTo(), replaceText("smith.ca"));

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith.ca"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText13.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText14 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith.ca"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0)));
        textInputEditText14.perform(scrollTo(), replaceText("smith.cas"));

        ViewInteraction textInputEditText15 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith.cas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText15.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText16 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith.cas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0)));
        textInputEditText16.perform(scrollTo(), replaceText("smith.casey"));

        ViewInteraction textInputEditText17 = onView(
                allOf(withId(R.id.usernameEditText), withText("smith.casey"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText17.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText18 = onView(
                allOf(withId(R.id.passwordEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0)));
        textInputEditText18.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText19 = onView(
                allOf(withId(R.id.passwordEditText), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0)));
        textInputEditText19.perform(scrollTo(), replaceText("1n"));

        ViewInteraction textInputEditText20 = onView(
                allOf(withId(R.id.passwordEditText), withText("1n"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText20.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText21 = onView(
                allOf(withId(R.id.passwordEditText), withText("1n"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0)));
        textInputEditText21.perform(scrollTo(), replaceText("1nep"));

        ViewInteraction textInputEditText22 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nep"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText22.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText23 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nep"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0)));
        textInputEditText23.perform(scrollTo(), replaceText("1nephie"));

        ViewInteraction textInputEditText24 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nephie"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText24.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText25 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nephie"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0)));
        textInputEditText25.perform(scrollTo(), replaceText("1nephi"));

        ViewInteraction textInputEditText26 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nephi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText26.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(80000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText27 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nephi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0)));
        textInputEditText27.perform(scrollTo(), replaceText("1nephi3"));

        ViewInteraction textInputEditText28 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nephi3"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText28.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText29 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nephi3"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0)));
        textInputEditText29.perform(scrollTo(), replaceText("1nephi3:"));

        ViewInteraction textInputEditText30 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nephi3:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText30.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText31 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nephi3:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0)));
        textInputEditText31.perform(scrollTo(), replaceText("1nephi3:7"));

        ViewInteraction textInputEditText32 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nephi3:7"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText32.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText33 = onView(
                allOf(withId(R.id.passwordEditText), withText("1nephi3:7"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0)));
        textInputEditText33.perform(pressImeActionButton());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
