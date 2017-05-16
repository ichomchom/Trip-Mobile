package com.csusb.cse455.trip.ui;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.adapter.FixedTabsPagerAdapter;
import com.csusb.cse455.trip.adapter.FragmentTab;
import com.csusb.cse455.trip.adapter.OnFragmentInteractionListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;

// An activity that lets the user create a new trip.
public class NewTripActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnTouchListener,
        OnFragmentInteractionListener<Object> {

    // Tag used for logging.
    private static final String TAG = NewTripActivity.class.getSimpleName();

    // Sliding panel components.
    View mSlidingPanel;
    View mDraggablePanel;
    Button mDraggablePanelGrip;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    ViewGroup.LayoutParams params;
    int mInitHeight;
    float mInitPos;
    float mMinHeight;
    float mTabHeight;
    float mExtendedHeight;

    // Google map.
    private GoogleMap mMap;
    // Google map camera position.
    private CameraPosition mCameraPosition;
    // The entry point to Google Play services, used by the Places API and Fused Location Provider.
    private GoogleApiClient mGoogleApiClient;

    // A default location (CSUSB) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(34.181259, -117.321494);
    private static final int DEFAULT_ZOOM = 15;

    // Permission request code.
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    // Permission granted flag.
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    // Sets up camera position and last known location (if saved), view, and Google API Client.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_new_trip);

        // Enable back button on action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Build the Play services client for use by the Fused Location Provider and the Places API.
        // addApi() method requests the Google Places API and the Fused Location Provider.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();

        // Get minimum height for the sliding panel.
        mMinHeight = getResources().getDimension(R.dimen.map_sliding_panel_min_height);
        // Get the tab height for the sliding panel.
        mTabHeight = getResources().getDimension(R.dimen.map_sliding_panel_tab_height);
        // Get default height for the sliding pnale.
        mExtendedHeight = getResources().getDimension(R.dimen.map_sliding_panel_extended_height);

        // Get all the main panels from the sliding panel group.
        mSlidingPanel = findViewById(R.id.sliding_panel);
        mDraggablePanel = findViewById(R.id.draggable_panel);
        mDraggablePanelGrip =  (Button) findViewById(R.id.draggable_panel_grip);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        // Set the touch listener on the draggable panel and its grip.
        mDraggablePanel.setOnTouchListener(this);
        mDraggablePanelGrip.setOnTouchListener(this);

        // Create tab fragments.
        ArrayList<FragmentTab> fragmentTabs = new ArrayList<>();
        fragmentTabs.add(new FragmentTab(NewTripRouteFragment.class, "Route"));
        fragmentTabs.add(new FragmentTab(NewTripSubscribersFragment.class, "Subscribers"));

        // Hook up tab fragments with the adapter and set the adapter in view pager.
        FixedTabsPagerAdapter pagerAdapter = new FixedTabsPagerAdapter(
                getSupportFragmentManager(), fragmentTabs);
        mViewPager.setAdapter(pagerAdapter);
    }

    //Handle Back button to go back to My Trips view.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    // Handle touch events.
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Get id.
        int id = v.getId();

        // If draggable panel or its grip, calculate sliding panel's height based on movement.
        if (id == R.id.draggable_panel || id == R.id.draggable_panel_grip) {
            if(params == null){
                params = mSlidingPanel.getLayoutParams();
            }
            switch (event.getActionMasked()) {
                case ACTION_DOWN:
                    // Get initial state.
                    mInitHeight = mSlidingPanel.getHeight();
                    mInitPos = event.getRawY();
                    break;
                case ACTION_MOVE:
                    // Perform sliding.
                    float dPos = mInitPos - event.getRawY();
                    float newHeight = mInitHeight + dPos;
                    if (newHeight < mMinHeight) {
                        newHeight = mMinHeight;
                    }
                    params.height = Math.round(newHeight);
                    // Refresh layout.
                    mSlidingPanel.requestLayout();
                    break;
            }
        }
        return false;
    }

    // Saves the state of the map when the activity is paused.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    // Builds the map when the Google Play services client is successfully connected.
    @Override
    public void onConnected(Bundle connectionHint) {
        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Handles failure to connect to the Google Play services client.
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        // Log error.
        Log.d(TAG, "Play services connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    // Handles suspension of the connection to the Google Play services client.
    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "Play services connection suspended.");
    }


    // Manipulates the map when it's available. This callback is triggered when the map is
    // ready to be used.
    @Override
    public void onMapReady(GoogleMap map) {
        // Assign the Google map.
        mMap = map;
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }

    // Gets the current location of the device, and positions the map's camera.
    private void getDeviceLocation() {
        // Request location permission, so that we can get the location of the
        // device. The result of the permission request is handled by a callback,
        // onRequestPermissionsResult.
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        // Get the best and most recent location of the device, which may be null in rare
        // cases when a location is not available.
        if (mLocationPermissionGranted) {
            mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        }

        // Set the map's camera position to the current location of the device.
        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        } else if (mLastKnownLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        } else {
            Log.d(TAG, "Current location is null. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    // Handles the result of the request for location permissions.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    // Updates the map's UI settings based on whether the user has granted location permission.
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        // Request location permission, so that we can get the location of the
        // device. The result of the permission request is handled by a callback,
        // onRequestPermissionsResult.
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }

    // Handles interaction with child fragments.
    @Override
    public void onFragmentInteraction(String tag, Object data) {
        switch (tag) {
            case "Locations":
                break;
            case "Subscribers":
                break;
            default:
                break;
        }

    }
}
