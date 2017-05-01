package com.csusb.cse455.trip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.csusb.cse455.trip.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // Firebase Authentication instance.
    private FirebaseAuth mAuth;

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

        // Create a new Firebase Authentication instance.
        mAuth = FirebaseAuth.getInstance();

        // Set dashboard as main Fragment
        DashboardFragment dashboardFragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content_frame, dashboardFragment,
                dashboardFragment.getTag()).commit();

        // Set the support action bar.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the toggle listener on the drawer menu.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set the navigation item selected listener for the navigation view.
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Get the navigation header.
        View navHeader = navigationView.getHeaderView(0);
        // Set the email in the header.
        TextView navHeaderEmail = (TextView) navHeader.findViewById(R.id.navHeaderEmail);
        navHeaderEmail.setText(mAuth.getCurrentUser().getEmail());

        // Get the logout link.
        final TextView logoutLink = (TextView) navHeader.findViewById(R.id.navHeaderLogout);
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

    // onOptionsItemSelected event handler.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Action bar clicks handling.
        int id = item.getItemId();

        // TODO: add more setting items, such as logout.

        // Get the selected item.
        if (id == R.id.action_settings) {
            return true;
        }

        // Return selected item.
        return super.onOptionsItemSelected(item);
    }

    // onNavigationItemSelected event handler.
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Fragment for a selected item.
        Fragment fragment = null;

        // Instantiate an appropriate fragment and set title.
        if (id == R.id.nav_dashboard) {
            setTitle("Dashboard");
            fragment = new DashboardFragment();
        } else if (id == R.id.nav_notifications) {
            setTitle("Notifications");
            fragment = new NotificationsFragment();
        } else if (id == R.id.nav_contacts) {
            setTitle("Contacts");
            fragment = new ContactsFragment();
        } else if (id == R.id.nav_locations) {
            setTitle("Locations");
            fragment = new LocationsFragment();
        } else if (id == R.id.nav_mytrips) {
            setTitle("My Trips");
            fragment = new MyTripsFragment();
        } else if (id == R.id.nav_subscriptions) {
            setTitle("Subscriptions");
            fragment = new SubscriptionsFragment();
        } else if (id == R.id.nav_settings) {
            setTitle("Settings");
            //TODO: fragment = new SettingsFragment(); <-- Should this be Activity or Fragment?
        } else if (id == R.id.nav_contactus) {
            setTitle("Contact Us");
            fragment = new ContactUsFragment();
        }

        // If fragment is not null, replace content frame with it.
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.main_content_frame, fragment).commit();
        }

        // Close drawer menu.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
