package com.csusb.cse455.trip.data;

import com.csusb.cse455.trip.model.Location;
import com.csusb.cse455.trip.model.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Provides mock data for testing purposes.
public class MockDataSource {
    // Keeps static counts of number of items -- good for getting fresh items during testing.
    private static int mMyTripCount = 0;
    private static int mLocationCount = 0;

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

        // Generate new trip.
        Trip item = new Trip();
        item.setId(String.format("Trip ID #%d ID.", mMyTripCount));
        item.setLabel(String.format("Trip ID #%d label.", mMyTripCount));
        item.setDescription(String.format("Trip ID #%d description.", mMyTripCount));

        // Return new Trip.
        return item;
    }

    // Returns a specified number of favorite location data items as a list.
    public static List<Location> getFavoriteLocationsList(int count) {
        // Create a new list.
        List<Location> data = new ArrayList<>();

        // Random latitude, longitude generation.
        Random r = new Random();

        // Populate list with random data.
        for (int i = 0; i < count; i++) {
            mLocationCount++;
            Location item = new Location();
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

        // Generate new location.
        Location item = new Location();
        item.setLabel(String.format("Location #%d Label.", mLocationCount));
        item.setDescription(String.format("Location #%d description.", mLocationCount));
        item.setLatitude(minLat + (maxLat - minLat) * r.nextDouble());
        item.setLongitude(minLng + (maxLng - minLng) * r.nextDouble());
        item.setFavorite(true);

        // Return new location.
        return item;
    }
}
