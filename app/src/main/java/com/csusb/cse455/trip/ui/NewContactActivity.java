package com.csusb.cse455.trip.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.csusb.cse455.trip.R;

// An activity that lets the user add a new contact.
public class NewContactActivity extends AppCompatActivity {
    // Tag used for logging.
    private static final String TAG = NewContactActivity.class.getSimpleName();

    // Handles the necessary initialization during creation.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Super propagation call.
        super.onCreate(savedInstanceState);

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_new_contact);

        // Enable back button on action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //Handle Back button to go back to Locations view.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
