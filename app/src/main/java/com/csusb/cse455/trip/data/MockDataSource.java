package com.csusb.cse455.trip.data;

import com.csusb.cse455.trip.model.MyTripItem;
import com.csusb.cse455.trip.utils.RandomString;
import java.util.ArrayList;
import java.util.List;

// Provides mock data for testing purposes.
public class MockDataSource {
    // Returns a specified number of randomly generated MyTripItem data items as a list.
    public static List<MyTripItem> getMyTripItemsList (int count) {
        // Initialize random string generators.
        RandomString id = new RandomString(15);
        RandomString label = new RandomString(10);
        RandomString description = new RandomString(30);

        // Create a new list.
        List<MyTripItem> data = new ArrayList<>();

        // Populate it with random data.
        for (int i = 0; i < count; i++) {
            MyTripItem item = new MyTripItem();
            item.setId(id.nextString());
            item.setLabel(label.nextString());
            item.setDescription(description.nextString());
            data.add(item);
        }

        // Return the list.
        return data;
    }

    // Returns a single randomly generated MyTripItem data item.
    public static MyTripItem getMyTripItem() {
        MyTripItem item = new MyTripItem();
        item.setId(new RandomString(15).nextString());
        item.setLabel(new RandomString(10).nextString());
        item.setDescription(new RandomString(30).nextString());
        return item;
    }
}
