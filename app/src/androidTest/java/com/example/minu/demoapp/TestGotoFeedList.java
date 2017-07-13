package com.example.minu.demoapp;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.minu.demoapp.Activity.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by minu on 7/13/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TestGotoFeedList {

    @Rule
    public IntentsTestRule<LoginActivity> mActivityRule = new IntentsTestRule<>(LoginActivity.class);
   /* @Before
    public void init(){
        mActivityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }*/
    @Test
    public void triggerIntentTest() {
        // check that the button is there
        onView(withId(R.id.login)).check(matches(notNullValue()));
        onView(withId(R.id.login)).perform(click());
        intended(toPackage("com.example.minu.demoapp.Activity.FeedActivity"));

    }

}

