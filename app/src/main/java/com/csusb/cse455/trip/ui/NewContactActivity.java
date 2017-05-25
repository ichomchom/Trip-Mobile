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

// An activity that lets the user add a new contact.
public class NewContactActivity extends AppCompatActivity {
    // Tag used for logging.
    private static final String TAG = NewContactActivity.class.getSimpleName();

    // Firebase Authentication instance.
    private FirebaseAuth mAuth;

    // Contact email view.
    TextView mEmailView;

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

        // Get Firebase Authentication instance.
        mAuth = FirebaseAuth.getInstance();

        // Get contact email view.
        mEmailView = (TextView) findViewById(R.id.contact_email);

        // Attach the button click listener.
        this.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close soft keyboard.
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // Validate format.
                if (Format.checkEmailFormat(mEmailView)) {
                    // Get current user.
                    FirebaseUser user = mAuth.getCurrentUser();
                    // Get the contact email.
                    String contactEmail = mEmailView.getText().toString();

                    // Check that it's current user's email.
                    if (user != null && user.getEmail() != null &&
                            !user.getEmail().equals(contactEmail)) {
                        // Add contact.
                        FirebaseDb.addContactByEmail(user, contactEmail);
                        Toast.makeText(v.getContext(), "Added new contact.",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(v.getContext(), "You can't add yourself as a contact.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
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
