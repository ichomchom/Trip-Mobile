package com.csusb.cse455.trip.utils;

import android.app.Activity;
import android.widget.Toast;
import com.csusb.cse455.trip.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Firebase utilities.
public class FirebaseUtil {
    // Sends out an email verification to the specified user and displays a toast
    // in the invoking activity.
    public static void sendEmailVerification(Activity activity, FirebaseUser user) {
        Toast.makeText(activity, "Success! An email address verification was sent.",
                Toast.LENGTH_LONG).show();
        user.sendEmailVerification();
    }

    // Updated user's account information.  If existing account information doesn't exist,
    // stores as new.
    public static void updateUser(String uId, User user) {
        // Get the database instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        // Get a reference to the users tree reference..
        DatabaseReference dbRef = db.getReference("users");
        // Store information.
        dbRef.child(uId).setValue(user);
    }

    // Returns user database reference by specified user id.
    public static DatabaseReference getUserDatabaseReference (String uId) {
        // Get the database instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        // Get a reference to the users tree.
        DatabaseReference dbRef = db.getReference("users");

        // Return database reference.
        return dbRef.child(uId);
    }
}
