package com.csusb.cse455.trip.utils;

import android.app.Activity;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;

// Firebase utilities.
public class FirebaseUtil {
    // Sends out an email verification to the specified user and displays a toast
    // in the invoking activity.
    public static void sendEmailVerification(Activity activity, FirebaseUser user) {
        Toast.makeText(activity, "Success! An email address verification was sent.",
                Toast.LENGTH_LONG).show();
        user.sendEmailVerification();
    }
}
