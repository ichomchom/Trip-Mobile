package com.csusb.cse455.trip.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.csusb.cse455.trip.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;

// Location details fragment.
public class LocationDetailsFragment extends Fragment {
    // Fragment initialization parameters.
    private static final String EXTRA_LABEL = "EXTRA_LABEL";
    private static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    private static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    private static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";

    // Sliding panel components.
    View mSlidingPanel;
    View mDraggablePanel;
    Button mDraggablePanelGrip;
    ViewGroup.LayoutParams params;
    int mInitHeight;
    float mInitPos;
    float mMinHeight;

    // Google map.
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    // Google map camera position.
    private CameraPosition mCameraPosition;
    // Location marker.
    private MarkerOptions mMarkerOptions;
    private Marker mMarker;

    // A default location (CSUSB) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(34.181259, -117.321494);
    private static final int DEFAULT_ZOOM = 15;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Inflate the layout for this fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_location_details, container, false);
    }

    // Handles bindings after view has been created.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the bundle passed from the parent.
        final Bundle extras = this.getArguments();

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        // Attach map.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    // Assign the Google map.
                    mMap = map;
                    mMap.setPadding(0, 0, 0, 600);
                    // Initialize marker options.
                    mMarkerOptions = new MarkerOptions();
                    // Set title.
                    mMarkerOptions.title(EXTRA_LABEL);
                    // Set position.
                    LatLng latLng = new LatLng(
                            extras.getDouble(EXTRA_LATITUDE), extras.getDouble(EXTRA_LONGITUDE));
                    mMarkerOptions.position(latLng);
                    // Initialize marker.
                    mMarker = mMap.addMarker(mMarkerOptions);
                    // Move camera to marker location.
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                }
            });
        }

        // Get all the main panels from the sliding panel group.
        mSlidingPanel = getActivity().findViewById(R.id.sliding_panel);
        mDraggablePanel = getActivity().findViewById(R.id.draggable_panel);
        mDraggablePanelGrip =  (Button) getActivity().findViewById(R.id.draggable_panel_grip);

        // Set the touch listener on the draggable panel and its grip.
        mDraggablePanel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchHandler(v, event);
            }
        });
        mDraggablePanelGrip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchHandler(v, event);
            }
        });

        // Get minimum height for the sliding panel.
        mMinHeight = getResources().getDimension(R.dimen.map_sliding_panel_min_height);

        // If not null, set bindings.
        if (extras != null)
        {
            // Get UI elements.
            TextView label = (TextView) view.findViewById(R.id.location_label);
            TextView description = (TextView) view.findViewById(R.id.location_description);
            TextView latitude= (TextView) view.findViewById(R.id.location_latitude);
            TextView longitude = (TextView) view.findViewById(R.id.location_longitude);

            // Set data.
            label.setText(extras.getString(EXTRA_LABEL));
            description.setText(extras.getString(EXTRA_DESCRIPTION));
            latitude.setText(String.valueOf(extras.getDouble(EXTRA_LATITUDE)));
            longitude.setText(String.valueOf(extras.getDouble(EXTRA_LONGITUDE)));
        }
    }

    // OnTouch event handler.
    public boolean onTouchHandler(View v, MotionEvent event) {
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
}
