package com.example.minu.demoapp;

import android.content.ComponentName;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.minu.demoapp.Activity.FeedActivity;
import com.example.minu.demoapp.Activity.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
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
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void triggerIntentTest() {
        onView(withId(R.id.login)).check(matches(notNullValue()));
        String email = "minusarraf96@gmail.com";
        String password = "bex432505";
        onView(withId(R.id.userEmail)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.userPassword)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), FeedActivity.class)));

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}

