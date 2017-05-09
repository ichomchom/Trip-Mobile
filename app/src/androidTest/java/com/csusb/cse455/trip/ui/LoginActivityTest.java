package com.csusb.cse455.trip.ui;

import com.csusb.cse455.trip.R;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)

@LargeTest
public class LoginActivityTest {

    private void launchActivityWithIntent()
    {
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
    }

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new IntentsTestRule<>(LoginActivity.class, true, false);


    @Test
    public void LoginAttempt() throws Exception {

        launchActivityWithIntent();

        onView(withId(R.id.logEmail)).perform(typeText("name@email.com"));

        onView(withId(R.id.logPassword)).perform(typeText("Password"));

        onView(withId(R.id.logBtn)).perform(click());

        // Include Toast check for being unable to login

    }

    @Test
    public void DoubleAtSymbolLoginFailure() throws Exception {

        launchActivityWithIntent();

        onView(withId(R.id.logEmail)).perform(typeText("name@@email.com"));

        onView(withId(R.id.logPassword)).perform(typeText("Password"));

        onView(withId(R.id.logBtn)).perform(click());

        onView(withId(R.id.logEmail)).check(matches(hasErrorText("Invalid email address format.")));

    }

}