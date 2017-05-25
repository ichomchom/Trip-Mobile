package com.csusb.cse455.trip.data;

import com.csusb.cse455.trip.model.Location;
import com.csusb.cse455.trip.model.User;
import com.google.firebase.auth.FirebaseUser;
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

    // Adds contact by email to the current user.
    public static void addContactByEmail(FirebaseUser user, String contactEmail) {
        // Get the database instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        // Get a user's tree reference.
        DatabaseReference dbRef = db.getReference("contacts");

        // Replace periods with commas, because periods are not allowed.
        contactEmail = contactEmail.replace('.', ',');

        // Store information.
        dbRef.child(user.getUid()).child(contactEmail).setValue(true);
    }

    // Adds new subscription.
    public static void addSubscription(FirebaseUser user, String contactEmail, String label) {
        // Get the database instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        // Get a user's tree reference.
        DatabaseReference dbRef = db.getReference("subscriptions");

        // Replace periods with commas, because periods are not allowed.
        contactEmail = contactEmail.replace('.', ',');

        // Store information.
        dbRef.child(user.getUid()).child(contactEmail).child(label).setValue(true);
    }

    // Adds new location.
    public static void addLocation(FirebaseUser user, Location location) {
        // Get the database instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        // Get a user's tree reference.
        DatabaseReference dbRef = db.getReference("locations");

        // Store information.
        DatabaseReference child = dbRef.child(user.getUid()).child(location.getLabel());
        child.child("description").setValue(location.getDescription());
        child.child("latitude").setValue(location.getLatitude());
        child.child("longitude").setValue(location.getLongitude());
    }
}
