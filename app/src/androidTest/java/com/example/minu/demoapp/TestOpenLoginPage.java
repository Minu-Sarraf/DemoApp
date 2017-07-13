package com.example.minu.demoapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.minu.demoapp.Activity.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestOpenLoginPage {
    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityTestRule = new
            ActivityTestRule<LoginActivity>(LoginActivity.class);
    @Before
    public void init(){
        mLoginActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }
    @Test
    public void opensLoginScreen() {

        //check if the login  screen is displayed by asserting that the first email edittext is displayed
        onView(withId(R.id.userEmail)).check(matches(allOf(isDescendantOfA(withId(R.id.login_layout)), isDisplayed())));
    }

    @Test
    public void enterLoginCredential() {

        String email = "minusarraf96@gmail.com";
        String password = "password";
        //type in email
        onView(withId(R.id.userEmail)).perform(typeText(email), closeSoftKeyboard());

        //type in password
        onView(withId(R.id.userPassword)).perform(typeText(password), closeSoftKeyboard());


    }
}
