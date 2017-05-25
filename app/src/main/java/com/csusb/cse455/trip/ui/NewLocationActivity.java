package com.csusb.cse455.trip.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.data.FirebaseDb;
import com.csusb.cse455.trip.utils.Format;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// An activity that lets the user add a new location.
public class NewLocationActivity extends AppCompatActivity {
    // Tag used for logging.
    private static final String TAG = NewSubscriptionActivity.class.getSimpleName();

    // Firebase Authentication instance.
    private FirebaseAuth mAuth;

    // Location label view.
    TextView mLocationLabel;
    // Location description view.
    TextView mLocationDescription;
    // Location latitude view.
    TextView mLocationLatitude;
    // Location longitude view.
    TextView mLocationLongitude;

    // Handles the necessary initialization during creation.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Super propagation call.
        super.onCreate(savedInstanceState);

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_new_location);

        // Get Firebase Authentication instance.
        mAuth = FirebaseAuth.getInstance();

        // Get location label view.
        mLocationLabel = (TextView) findViewById(R.id.location_label);
        // Get location description view.
        mLocationDescription = (TextView) findViewById(R.id.location_description);
        // Get location latitude view.
        mLocationLatitude = (TextView) findViewById(R.id.location_latitude);
        // Get location longitude view.
        mLocationLongitude = (TextView) findViewById(R.id.location_longitude);

        // Enable back button on action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Attach the button click listener.
        this.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close soft keyboard.
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    //Handle Back button to go back to Locations view.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
