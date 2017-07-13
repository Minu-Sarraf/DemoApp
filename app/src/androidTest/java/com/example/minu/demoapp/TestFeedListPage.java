package com.example.minu.demoapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.minu.demoapp.Activity.FeedActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by minu on 7/13/2017.
 */
@RunWith(AndroidJUnit4.class)

public class TestFeedListPage {
    @Rule
    public ActivityTestRule<FeedActivity> mLoginActivityTestRule = new
            ActivityTestRule<FeedActivity>(FeedActivity.class);
    @Test
    public void feedListPage() {
        onView(withId(R.id.my_recycler_view)).check((matches(isDisplayed())));
    }
}
