package com.csusb.cse455.trip.utils;

import android.app.Activity;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Firebase utilities.
public class Firebase {
    // Sends out an email verification to the specified user and displays a toast
    // in the invoking activity.
    public static void sendEmailVerification(Activity activity, FirebaseUser user) {
        Toast.makeText(activity, "Success! An email address verification was sent.",
                Toast.LENGTH_LONG).show();
        user.sendEmailVerification();
    }

    // Stores general account information.
    public static void storeGeneralAccountInformation(
            final String email, final String firstName, final String lastName) {
        // Get the database instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        // Get a reference to the users tree with a unique key for the new user.
        DatabaseReference dbRef = db.getReference("users").push();
        // Store information.
        dbRef.child("email").setValue(email);
        dbRef.child("first_name").setValue(firstName);
        dbRef.child("last_name").setValue(lastName);
    }
}
