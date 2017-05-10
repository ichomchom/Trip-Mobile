package com.csusb.cse455.trip.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.data.FirebaseDb;
import com.csusb.cse455.trip.model.User;
import com.csusb.cse455.trip.utils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Stack;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // Firebase Authentication instance.
    private FirebaseAuth mAuth;

    // Used in conjunction with pushing and popping fragments from their stack.
    private Stack<String> mTitleStack;

    // Navigation view from the drawer menu.
    private NavigationView mNavigationView;

    // Push a title onto the stack.
    public void pushTitle(String title) {
        if (mTitleStack != null) {
            mTitleStack.push(title);
        }
    }

    // Pop the stack and return what was on it.
    public String popTitle() {
        // If not empty or null, return top.
        if (mTitleStack != null && !mTitleStack.empty())
        {
            return mTitleStack.pop();
        }
        // Making a choice not to return null here.  Just return an empty string.
        return "";
    }

    // onStart event handler.
    @Override
    public void onStart() {
        // Propagate to super.
        super.onStart();

        // Check if user is signed in (non-null) and verified, and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null || !currentUser.isEmailVerified())
        {
            finish();
        }
    }

    // onCreate event handler.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Propagate to super.
        super.onCreate(savedInstanceState);
        // Set content view layout.
        setContentView(R.layout.activity_main);

        // Initialize title stack.
        mTitleStack = new Stack<>();

        // Create a new Firebase Authentication instance.
        mAuth = FirebaseAuth.getInstance();

        // Load dashboard fragment, unless there's already a fragment in saved state.
        if (savedInstanceState == null) {
            loadFragment(R.id.main_content_frame, "DASHBOARD_FRAGMENT", new DashboardFragment(),
                    false, "Dashboard");
        }

        // Set the support action bar.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the toggle listener on the drawer menu.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set the navigation item selected listener for the navigation view.
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // Get the navigation header.
        View navHeader = mNavigationView.getHeaderView(0);

        // Get header text views.
        final TextView navHeaderEmail = (TextView) navHeader.findViewById(R.id.nav_header_email);
        final TextView navFirstName = (TextView) navHeader.findViewById(R.id.nav_header_first_name);
        final TextView navLastName = (TextView) navHeader.findViewById(R.id.nav_header_last_name);

        // Get Firebase user.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Set header email.
            navHeaderEmail.setText(currentUser.getEmail());

            // Get user information from the database.
            DatabaseReference dbRef = FirebaseDb.getUserDatabaseReference(currentUser.getUid());

            // Setup a value listener.
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get snapshot value.
                    User user = dataSnapshot.getValue(User.class);

                    // Set head first name and last name.
                    navFirstName.setText(user.getFirstName());
                    navLastName.setText(user.getLastName());
                }

                // Not implemented.
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

        // Get the logout link.
        final TextView logoutLink = (TextView) navHeader.findViewById(R.id.nav_header_sign_out);
        // Set logout callback to sign out and return to the login screen.
        logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    // Signs out the current user.
    private void signOut()
    {
        mAuth.signOut();
        finish();
    }

    // onBackPressed event handler.
    @Override
    public void onBackPressed() {
        // Get the drawer.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // If drawer is open, close it.
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        // Else, if there is an entry on fragment stack, pop it.
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            setTitle(popTitle());
        // Else, if current fragment is not dashboard, load it.
        } else if (getFragmentManager().findFragmentByTag("DASHBOARD_FRAGMENT") == null){
            mNavigationView.getMenu().performIdentifierAction(R.id.nav_dashboard, 0);
            mNavigationView.setCheckedItem(R.id.nav_dashboard);
        // Else, go to user's home (suspends application).
        } else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
        }
    }

    // onCreateOptionsMenu event handler.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Handles actions form the action menu.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item id.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // TODO: handle settings view transition here.
        } else if (id == R.id.action_sign_out) {
            signOut();
        }
        // Return selected item.
        return super.onOptionsItemSelected(item);
    }

    // Handles navigation actions from the drawer menu.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Get item id.
        int id = item.getItemId();

        // Fragment for a selected item.
        Fragment fragment = null;

        // Fragment tag.
        String tag = "";

        // Fragment title.
        String title = "";

        // Instantiate an appropriate fragment and set title.
        if (id == R.id.nav_dashboard) {
            title = "Dashboard";
            fragment = new DashboardFragment();
            tag = "DASHBOARD_FRAGMENT";
        } else if (id == R.id.nav_notifications) {
            title = "Notifications";
            fragment = new NotificationsFragment();
            tag = "NOTIFICATIONS_FRAGMENT";
        } else if (id == R.id.nav_contacts) {
            title = "Contacts";
            fragment = new ContactsFragment();
            tag = "CONTACTS_FRAGMENT";
        } else if (id == R.id.nav_locations) {
            title = "Locations";
            fragment = new LocationsFragment();
            tag = "LOCATIONS_FRAGMENT";
        } else if (id == R.id.nav_mytrips) {
            title = "My Trips";
            fragment = new MyTripsFragment();
            tag = "MY_TRIPS_FRAGMENT";
        } else if (id == R.id.nav_subscriptions) {
            title = "Subscriptions";
            fragment = new SubscriptionsFragment();
            tag = "SUBSCRIPTIONS_FRAGMENT";
        } else if (id == R.id.nav_settings) {
            title = "Settings";
            tag = "SETTINGS_FRAGMENT";
            //TODO: fragment = new SettingsFragment(); <-- Should this be Activity or Fragment?
        } else if (id == R.id.nav_contactus) {
            title = "Contact Us";
            fragment = new ContactUsFragment();
            tag = "CONTACT_US_FRAGMENT";
        }

        // If fragment is not null, replace content frame with it.
        if (fragment != null) {
            loadFragment(R.id.main_content_frame, tag, fragment, false, title);
        }

        // Close drawer menu.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Loads the specified fragment.  Allows use of stack.  If stack is used new title is
    // loaded and old title is pushed onto the stack.
    public void loadFragment(int id, String tag, Fragment fragment, boolean useStack,
                             String newTitle) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(id, fragment, tag);

        // Check if use of stack is requested. If it is, pushes fragment onto the stack with
        // the specified fragment tag.
        if (useStack) {
            transaction.addToBackStack(tag);
        }

        // Commit transaction.
        transaction.commit();

        // If new title is specified, use it.
        if (newTitle != null) {
            setNewTitle(newTitle);
        }
    }

    // Sets new title.
    private void setNewTitle(String title) {
        // Add existing title to the title stack.
        pushTitle(getTitle().toString());
        // Change title.
        setTitle(title);
    }
}
