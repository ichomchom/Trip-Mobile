package com.csusb.cse455.trip.ui;

import com.csusb.cse455.trip.R;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

// Added for Toast Message Testing
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


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
public class PasswordResetActivityTest {

    private void launchActivityWithIntent()
    {
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
    }

    @Rule
    public ActivityTestRule<PasswordResetActivity> mActivityRule = new IntentsTestRule<>(PasswordResetActivity.class, true, false);


    @Test
    public void PasswordResetScreenToLoginScreenTransistion() throws Exception {

        launchActivityWithIntent();

        onView(withId(R.id.regBackBtn)).perform(click());

    }


    @Test
    public void PasswordResetAttempt() throws Exception {

        launchActivityWithIntent();

        onView(withId(R.id.recoveryEmail)).perform(typeText("name@email.com"));

        closeSoftKeyboard();

        onView(withId(R.id.recoveryBtn)).perform(click());

    }

}