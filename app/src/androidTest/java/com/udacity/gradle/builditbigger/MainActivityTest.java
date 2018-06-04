package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        //Click getJoke button
        onView(withId(R.id.tellJoke_btn)).perform(click());
        //Check the joke
        onView(withId(R.id.joke_tv)).check(matches(ViewMatchers.withText(Matchers.not(Matchers.startsWith("Error: ")))));
    }



    //*************Helper*methods******************
    public Activity getCurrentActivity() {
        final Activity[] currentActivity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                currentActivity[0] = (Activity) ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED).toArray()[0];
            }
        });
        return currentActivity[0];
    }
    //**************End*helper*methods******************
}