package com.csusb.cse455.trip;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Used for logging.
    private static final String TAG = LoginActivity.class.getSimpleName();

    // Firebase Authentication instance.
    private FirebaseAuth mAuth;

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and verified, and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null || !currentUser.isEmailVerified())
        {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new Firebase Authentication instance.
        mAuth = FirebaseAuth.getInstance();


        //region Set dashboard as main Fragment
        DashboardFragment dashboardFragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.relativelayout_for_fragment,dashboardFragment,dashboardFragment.getTag()).commit();
        //endregion



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
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
                mAuth.signOut();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //region Link Fragments, and Activities to Navigation View
        if (id == R.id.nav_dashboard) {
            Toast.makeText(this,"Dashboard",Toast.LENGTH_SHORT).show();
            setTitle("Dashboard");

            DashboardFragment dashboardFragment = new DashboardFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayout_for_fragment,dashboardFragment,dashboardFragment.getTag()).commit();

        } else if (id == R.id.nav_notifications) {
            Toast.makeText(this,"Notifications",Toast.LENGTH_SHORT).show();
            setTitle("Notifications");

            NotificationsFragment notificationsFragment = new NotificationsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayout_for_fragment,notificationsFragment,notificationsFragment.getTag()).commit();

        } else if (id == R.id.nav_contacts) {
            Toast.makeText(this,"Contacts",Toast.LENGTH_SHORT).show();
            setTitle("Contacts");

            ContactsFragment contactsFragment = new ContactsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayout_for_fragment,contactsFragment,contactsFragment.getTag()).commit();


        } else if (id == R.id.nav_locations) {
            Toast.makeText(this,"Locations",Toast.LENGTH_SHORT).show();
            setTitle("Locations");

            LocationsFragment locationsFragment = new LocationsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayout_for_fragment,locationsFragment,locationsFragment.getTag()).commit();


        } else if (id == R.id.nav_mytrips) {
            Toast.makeText(this,"My Trips",Toast.LENGTH_SHORT).show();
            setTitle("My Trips");

            MyTripsFragment myTripsFragment = new MyTripsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayout_for_fragment,myTripsFragment,myTripsFragment.getTag()).commit();


        } else if (id == R.id.nav_subscriptions) {
            Toast.makeText(this,"Subscriptions",Toast.LENGTH_SHORT).show();
            setTitle("Subscriptions");

            SubscriptionsFragment subscriptionsFragment = new SubscriptionsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayout_for_fragment, subscriptionsFragment, subscriptionsFragment.getTag()).commit();


        } else if (id == R.id.nav_settings) {
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
            setTitle("Settings");


        } else if (id == R.id.nav_contactus)
        {
            Toast.makeText(this,"Contact Us",Toast.LENGTH_SHORT).show();
            setTitle("Contact Us");

            ContactUsFragment contactUsFragment = new ContactUsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayout_for_fragment,contactUsFragment,contactUsFragment.getTag()).commit();


        }
        //endregion

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
