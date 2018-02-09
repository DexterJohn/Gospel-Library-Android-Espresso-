package org.lds.ldssa.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.test.espresso.ViewInteraction;
import android.test.suitebuilder.annotation.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import junit.framework.Assert;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lds.ldssa.R;
import org.lds.ldssa.model.database.catalog.language.Language;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import tools.fastlane.screengrab.Screengrab;
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy;
import tools.fastlane.screengrab.locale.LocaleTestRule;
import tools.fastlane.screengrab.locale.LocaleUtil;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 *
 * Before running, don't forget to uncomment the extra permissions in AndroidManifest.xml
 * Also, disable animations on the emulator under developer settings
 *
 * Pull screenshots from emulator to a folder on your desktop:
 * adb pull /storage/emulated/0/org.lds.ldssa.dev/screengrab ~/Desktop/testing
 *
 * Clear old screenshots from emulator:
 * adb shell rm -r /storage/emulated/0/org.lds.ldssa.dev/screengrab
 *
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TakeScreenShots {

    @Before
    public void grantPhonePermission() {
        // In M+, trying to call a number will trigger a runtime dialog. Make sure
        // the permission is granted before running this test.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + getTargetContext().getPackageName() + " android.permission.WRITE_EXTERNAL_STORAGE");
            getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + getTargetContext().getPackageName() + " android.permission.READ_EXTERNAL_STORAGE");
            getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + getTargetContext().getPackageName() + " android.permission.CHANGE_CONFIGURATION");
            getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + getTargetContext().getPackageName() + " android.permission.SET_ANIMATION_SCALE");
        }
    }

    @ClassRule
    public static final LocaleTestRule localeTestRule = new LocaleTestRule();
    @Rule
    public ActivityTestRule<StartupActivity> mActivityTestRule = new ActivityTestRule<>(StartupActivity.class);
    public ActivityTestRule<UriRouterActivity> uriRouterActivityTestRule = new ActivityTestRule<>(UriRouterActivity.class, true, false);




    @Test

    public void TakeScreenShotsForRelease() {

        sleep(5000);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Skip Tips
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

        sleep(1000);

        // Sign in

        ViewInteraction textInputUserName = onView(
                allOf(withId(R.id.usernameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_username_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputUserName.perform(replaceText("smith.casey"));
        textInputUserName.perform(closeSoftKeyboard());

        ViewInteraction textInputPassword = onView(
                allOf(withId(R.id.passwordEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ldsaccount_login_password_layout),
                                        0),
                                0)));
        textInputPassword.perform(replaceText("1nephi3:7"));
        textInputPassword.perform(pressImeActionButton());

        sleep(1000);

        Map<String, String> languageMap = new HashMap<>();
        languageMap.put("en", "eng"); // English
        languageMap.put("bg", "bul"); // Bulgarian
        languageMap.put("zh", "zhs"); // Chinese (Simplified)
        languageMap.put("zh", "zho"); // Chinese (Traditional)
        languageMap.put("hr", "hrv"); // Croatian
        languageMap.put("cs", "ces"); // Czech
        languageMap.put("da", "dan"); // Danish
        languageMap.put("nl", "nld"); // Dutch
        languageMap.put("et", "est"); // Estonian
        languageMap.put("fil", "tgl"); // Filipino (Tagalog)
        languageMap.put("fi", "fin"); // Finnish
        languageMap.put("fr", "fra"); // French
        languageMap.put("de", "deu"); // German
        languageMap.put("el", "ell"); // Greek – Book of Mormon not in app
        languageMap.put("hu", "hun"); // Hungarian
        languageMap.put("id", "ind"); // Indonesian
        languageMap.put("it", "ita"); // Italian
        languageMap.put("ja", "jpn"); // Japanese
        languageMap.put("ko", "kor"); // Korean
        languageMap.put("lv", "lav"); // Latvian
        languageMap.put("lt", "lit"); // Lithuanian
        languageMap.put("no", "nor"); // Norwegian
        languageMap.put("pl", "pol"); // Polish – Book of Mormon not in app
        languageMap.put("pt", "por"); // Portuguese
        languageMap.put("ro", "ron"); // Romanian
        languageMap.put("ru", "rus"); // Russian
        languageMap.put("es", "spa"); // Spanish
        languageMap.put("sw", "swa"); // Swahili
        languageMap.put("sv", "swe"); // Swedish
        languageMap.put("th", "tha"); // Thai
        languageMap.put("uk", "ukr"); // Ukrainian
        languageMap.put("vi", "vie"); // Vietnamese

        sleep(2500); // This allows time for permission granting to finish and for initial content to download

        Screengrab.setDefaultScreenshotStrategy(new UiAutomatorScreenshotStrategy());
        Intent intent = new Intent();

        // Download all of the books
//        for (String deviceLanguageCode : languageMap.keySet()) {
//
//            String contentLanguageCode = languageMap.get(deviceLanguageCode);
//            LocaleUtil.changeDeviceLocaleTo(new Locale(deviceLanguageCode));
//
//            intent.setData(Uri.parse("gospellibrary://content/scriptures/bofm/1-ne/8?lang=" + contentLanguageCode));
//            uriRouterActivityTestRule.launchActivity(intent);
//            try{ ViewInteraction mDButton = onView(
//                        allOf(withId(R.id.md_buttonDefaultPositive),
//                                isDisplayed()));
//                mDButton.perform(click());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            sleep(5000);
//
//
//            intent.setData(Uri.parse("gospellibrary://content/general-conference/2016/04/choices?lang=" + contentLanguageCode));
//            uriRouterActivityTestRule.launchActivity(intent);
//            try{ ViewInteraction mDButton = onView(
//                    allOf(withId(R.id.md_buttonDefaultPositive),
//                            isDisplayed()));
//                mDButton.perform(click());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            sleep(5000);
//
//
//        }

        for (String deviceLanguageCode : languageMap.keySet()) {

            String contentLanguageCode = languageMap.get(deviceLanguageCode);
            LocaleUtil.changeDeviceLocaleTo(new Locale(deviceLanguageCode));

            intent.setData(Uri.parse("gospellibrary://content/scriptures/bofm/1-ne/8?lang=" + contentLanguageCode));
            uriRouterActivityTestRule.launchActivity(intent);
            sleep(2500);
            ViewInteraction contentWebView = onView(
                    allOf(withId(R.id.contentWebView),
                            isDisplayed()));

            contentWebView.perform(longClick());
            sleep(2500);
            Screengrab.screenshot("1-highlight");

            intent.setData(Uri.parse("gospellibrary://content?lang=" + contentLanguageCode));
            uriRouterActivityTestRule.launchActivity(intent);
            sleep(2500);
            Screengrab.screenshot("2-library");

            intent.setData(Uri.parse("gospellibrary://content/general-conference/2016/04/choices?lang=" + contentLanguageCode));
            uriRouterActivityTestRule.launchActivity(intent);
            sleep(2500);
            Screengrab.screenshot("3-conference");

            intent.setData(Uri.parse("gospellibrary://content?lang=" + contentLanguageCode));
            uriRouterActivityTestRule.launchActivity(intent);
            sleep(6000);
            Screengrab.screenshot("4-notebooks");

            intent.setData(Uri.parse("gospellibrary://content/scriptures/bofm/1-ne/8?lang=" + contentLanguageCode + "#1"));
            uriRouterActivityTestRule.launchActivity(intent);
            sleep(6000);
            Screengrab.screenshot("5-sidebar");

        }

        // End by setting the device back to English
        LocaleUtil.changeDeviceLocaleTo(new Locale("en"));
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

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
