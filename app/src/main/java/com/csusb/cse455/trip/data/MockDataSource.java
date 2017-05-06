package com.csusb.cse455.trip.data;

import com.csusb.cse455.trip.model.Trip;

import java.util.ArrayList;
import java.util.List;

// Provides mock data for testing purposes.
public class MockDataSource {
    // Keeps static counts of number of items.
    private static int mMyTripCount = 0;

    // Returns a specified number of Trip data items as a list.
    public static List<Trip> getMyTripsList(int count) {
        // Create a new list.
        List<Trip> data = new ArrayList<>();

        // Populate it with random data.
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
        Trip item = new Trip();
        item.setId(String.format("Trip ID #%d ID.", mMyTripCount));
        item.setLabel(String.format("Trip ID #%d label.", mMyTripCount));
        item.setDescription(String.format("Trip ID #%d description.", mMyTripCount));
        return item;
    }
}
