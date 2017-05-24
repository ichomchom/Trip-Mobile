package com.csusb.cse455.trip.data;

import com.csusb.cse455.trip.model.Contact;
import com.csusb.cse455.trip.model.Location;
import com.csusb.cse455.trip.model.Notification;
import com.csusb.cse455.trip.model.Subscription;
import com.csusb.cse455.trip.model.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Provides mock data for testing purposes.
public class MockDataSource {
    // Keeps static counts of number of items -- good for getting fresh items during testing.
    private static int mMyTripCount = 0;
    private static int mSubscriptionCount = 0;
    private static int mLocationCount = 0;
    private static int mContactCount = 0;
    private static int mNotificationCount = 0;

    // Sample first names.
    private static String[] mFirstNames = { "Nadene", "Emilee", "Octavio", "Deanne", "Benita",
            "Stasia", "Terence", "Shawna", "Joshua", "Herbert", "Suzy", "Danyel", "Denae",
            "Donnell", "Tiffaney", "Helga", "Donette", "Londa", "Quentin", "Margurite", "Mireya",
            "Jaleesa", "Genny", "Christoper", "Tu", "Erich", "Alan", "Eleanora", "Wendie", "Adan" };

    // Sample last names.
    private static String[] mLastNames = { "Arnhold", "Clemente", "Boss", "Monroy", "Roby",
            "Valentino", "Barge", "Pekar", "Kibbe", "Mitchell", "Purdy", "Mcguffie", "Ballas",
            "Madeiros", "Petrus", "Keesler", "Gasser", "Walworth", "Munday", "Heintz", "Reddick",
            "Igoe", "Periera", "Schaar", "Priester", "Fludd", "Landes", "Schrom", "Lucca",
            "Jacobus" };

    // Constants.
    // Minimum latitude.
    private static final double minLat = 32.97;
    // Maximum latitude.
    private static final double maxLat = 34.81;
    // Minimum longitude.
    private static final double minLng = -119.11;
    // Maximum longitude.
    private static final double maxLng = -115.21;

    // Returns a specified number of Trip data items as a list.
    public static List<Trip> getMyTripsList(int count) {
        // Create a new list.
        List<Trip> data = new ArrayList<>();
        mMyTripCount = 0;

        // Populate list with random data.
        for (int i = 0; i < count; i++) {
            mMyTripCount++;
            Trip item = new Trip();
            item.setId(String.format("Trip #%d ID.", mMyTripCount));
            item.setLabel(String.format("Trip #%d label.", mMyTripCount));
            item.setDescription(String.format("Trip #%d description.", mMyTripCount));
            data.add(item);
        }

        // Return the list.
        return data;
    }

    // Returns a single randomly generated Trip data item.
    public static Trip getMyTrip() {
        mMyTripCount++;

        // Generate a new trip.
        Trip item = new Trip();
        item.setId(String.format("Trip #%d ID.", mMyTripCount));
        item.setLabel(String.format("Trip #%d label.", mMyTripCount));
        item.setDescription(String.format("Trip #%d description.", mMyTripCount));

        // Return new Trip.
        return item;
    }

    // Returns a specified number of Subscription data items as a list.
    public static List<Subscription> getSubscriptionsList(int count) {
        // Create a new list.
        List<Subscription> data = new ArrayList<>();
        mSubscriptionCount = 0;

        // Populate list with random data.
        for (int i = 0; i < count; i++) {
            mSubscriptionCount++;
            Subscription item = new Subscription();
            item.setId(String.format("Subscription #%d ID.", mSubscriptionCount));
            item.setLabel(String.format("Subscription #%d label.", mSubscriptionCount));
            item.setDescription(String.format("Subscription #%d description.", mSubscriptionCount));
            data.add(item);
        }

        // Return the list.
        return data;
    }

    // Returns a single randomly generated Subscription data item.
    public static Subscription getSubscription() {
        mSubscriptionCount++;

        // Generate a new subscription.
        Subscription item = new Subscription();
        item.setId(String.format("Subscription #%d ID.", mSubscriptionCount));
        item.setLabel(String.format("Subscription #%d label.", mSubscriptionCount));
        item.setDescription(String.format("Subscription #%d description.", mSubscriptionCount));

        // Return new subscription.
        return item;
    }

    // Returns a specified number of favorite location data items as a list.
    public static List<Location> getFavoriteLocationsList(int count) {
        // Create a new list.
        List<Location> data = new ArrayList<>();
        mLocationCount = 0;

        // Random latitude, longitude generation.
        Random r = new Random();

        // Populate list with random data.
        for (int i = 0; i < count; i++) {
            mLocationCount++;
            Location item = new Location();
            item.setId(String.format("Location #%d ID.", mLocationCount));
            item.setLabel(String.format("Location #%d Label.", mLocationCount));
            item.setDescription(String.format("Location #%d description.", mLocationCount));
            item.setLatitude(minLat + (maxLat - minLat) * r.nextDouble());
            item.setLongitude(minLng + (maxLng - minLng) * r.nextDouble());
            item.setFavorite(true);
            data.add(item);
        }

        // Return the list.
        return data;
    }

    // Returns a single favorite location.
    public static Location getFavoriteLocation() {
        mLocationCount++;

        // Random latitude, longitude generation.
        Random r = new Random();

        // Generate a new location.
        Location item = new Location();
        item.setId(String.format("Location #%d ID.", mLocationCount));
        item.setLabel(String.format("Location #%d Label.", mLocationCount));
        item.setDescription(String.format("Location #%d description.", mLocationCount));
        item.setLatitude(minLat + (maxLat - minLat) * r.nextDouble());
        item.setLongitude(minLng + (maxLng - minLng) * r.nextDouble());
        item.setFavorite(true);

        // Return new location.
        return item;
    }

    // Returns a specified number of contact data items as a list.
    public static List<Contact> getContactsList(int count) {
        // Create a new list.
        List<Contact> data = new ArrayList<>();
        mContactCount = 0;

        // Populate list with random data.
        for (int i = 0; i < count; i++) {
            mContactCount++;
            Contact item = new Contact();
            item.setId(String.format("Contact #%d ID.", mContactCount));
            item.setFirstName(mLastNames[i % mLastNames.length]);
            item.setLastName(mFirstNames[i % mFirstNames.length]);
            item.setEmail(item.getFirstName() + item.getLastName() + "@email.com");
            data.add(item);
        }

        // Return the list.
        return data;
    }

    // Returns a single contact.
    public static Contact getContact() {
        mContactCount++;

        // Generate a new contact.
        Contact item = new Contact();
        item.setId(String.format("Contact #%d ID.", mContactCount));
        item.setEmail(String.format("Contact #%d Email.", mContactCount));
        item.setFirstName(String.format("Contact #%d First Name.", mContactCount));
        item.setLastName(String.format("Contact #%d Last Name.", mContactCount));

        // Return new location.
        return item;
    }

    // Returns a specified number of notification data items as a list.
    public static List<Notification> getNotificationsList(int count) {
        // Create a new list.
        List<Notification> data = new ArrayList();
        mNotificationCount = 0;

        // Populate list with random data.
        for (int i = 0; i < count; i++) {
            mNotificationCount++;
            Notification item = new Notification();
            item.setId(String.format("Notification #%d ID.", mNotificationCount));
            item.setDate("05/24/2017");
            item.setTime("02:10 PM");
            item.setTitle(String.format("Notification #%d Title.", mNotificationCount));
            item.setSource("Source");
            item.setContent(String.format("Notification #%d Content.", mNotificationCount));
            data.add(item);
        }

        // Return the list.
        return data;
    }

    // Returns a single notification.
    public static Notification getNotification() {
        mNotificationCount++;

        // Generate a new notification.
        Notification item = new Notification();
        item.setId(String.format("Notification #%d ID.", mNotificationCount));
        item.setDate("05/24/2017");
        item.setTime("02:10 PM");
        item.setTitle(String.format("Notification #%d Title.", mNotificationCount));
        item.setSource("Source");
        item.setContent(String.format("Notification #%d Content.", mNotificationCount));

        // Return new notification.
        return item;
    }
}
