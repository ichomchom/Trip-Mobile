package com.csusb.cse455.trip.data;

import com.csusb.cse455.trip.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


// Firebase data access.
public class FirebaseDb {
    // Updated user's account information.  If existing account information doesn't exist,
    // stores as new.
    public static void createOrUpdateUser(String uId, User user) {
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
