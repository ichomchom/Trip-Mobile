package com.csusb.cse455.trip.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.adapter.LocationsDataAdapter;
import com.csusb.cse455.trip.adapter.OnLocationCardClickCallback;
import com.csusb.cse455.trip.data.MockDataSource;
import com.csusb.cse455.trip.model.Location;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

// Locations fragment, which handles the display of Location cards.
public class LocationsFragment extends Fragment implements OnLocationCardClickCallback {
    // Fragment initialization parameters.
    private static final String EXTRA_LABEL = "EXTRA_LABEL";
    private static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    private static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    private static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";

    // Main activity.
    private MainActivity mMainActivity;
    // Data adapter.
    private LocationsDataAdapter mAdapter;
    // Data list.
    private ArrayList<Location> mListData;
    //Progress Dialog instance.
    private ProgressDialog newLocationDialog = null ;
    StorageReference mStorageRef;
    //Async Task
    MyAsyncTask myAsyncTask;

    // Required empty public constructor.
    public LocationsFragment() { }

    // Inflates view with the specified layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_locations, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the main activity.
        mMainActivity = (MainActivity) getActivity();

        // Get data.
        // TODO: Change to real data.
        mListData = (ArrayList<Location>) MockDataSource.getFavoriteLocationsList(30);
        Collections.sort(mListData, new Comparator<Location>() {
            @Override
            public int compare(Location l1, Location l2) {
                return l1.getLabel().compareToIgnoreCase(l2.getLabel());
            }
        });

        // Initialize adapter.
        if (mListData != null) {
            mAdapter = new LocationsDataAdapter(mListData, getActivity().getBaseContext());
        }

        // Setup Recycler view.
        RecyclerView mRecView = (RecyclerView) view.findViewById(R.id.items_list);
        mRecView.setHasFixedSize(false);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mRecView.setAdapter(mAdapter);
        mAdapter.setCardClickCallback(this);

        //Instance newTripDialog
        newLocationDialog = new ProgressDialog(getActivity());


        // Set up item touch helper.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mRecView);

        // Setup floating action button click listener.
        FloatingActionButton addItem = (FloatingActionButton) view.findViewById(R.id.btn_add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set Progress Dialog for Adding New Location
                newLocationDialog.setIndeterminate(true);
                newLocationDialog.setTitle("Adding New Location");
                newLocationDialog.setMessage("Please wait...");
                newLocationDialog.show();
                addNewLocation();
                takeScreenShot();
            }
        });
    }

    // Creates callback for touch operations.
    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            // Allows rearrangement of items.
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Do nothing.
            }
        };
    }

    //Dismiss Progress Dialog for Adding New Location when return to activity
    @Override
    public void onResume() {
        super.onResume();
        if(newLocationDialog!= null) {
            newLocationDialog.dismiss();
        }
    }

    // Opens a new location creation view.
    private void addNewLocation() {
        Intent intent = new Intent(getActivity(), NewLocationActivity.class);
        getActivity().startActivity(intent);
    }

    // Moves an item within the list.
    private void moveItem(int oldPos, int newPos) {
        Location item = mListData.get(oldPos);
        if (item != null) {
            mListData.remove(oldPos);
            mListData.add(newPos, item);
            mAdapter.notifyItemMoved(oldPos, newPos);
        }
    }

    // Handles on click event by showing details.
    @Override
    public void onViewActionClick(int position) {
        // Get an item form the given position.
        Location item = mListData.get(position);

        //Instance Async Task
        myAsyncTask = new MyAsyncTask();

        // Create a bundle.
        Bundle extras = new Bundle();
        extras.putString(EXTRA_LABEL, item.getLabel());
        extras.putString(EXTRA_DESCRIPTION, item.getDescription());
        extras.putDouble(EXTRA_LATITUDE, item.getLatitude());
        extras.putDouble(EXTRA_LONGITUDE, item.getLongitude());

        // Create a new details fragment.
        Fragment newFragment = new LocationDetailsFragment();

        // Pass bundle.
        newFragment.setArguments(extras);

        // Load the new fragment.
        mMainActivity.loadFragment(this.getId(), "LOCATION_DETAILS_FRAGMENT", newFragment,
                true, "Location Details");

        //Execute Async Task
        myAsyncTask.execute();
    }

    // Handles click on SHARE action.
    @Override
    public void onShareActionClick(int position) {
        // Get an item from the given position.

    }

    //Set up MyAsyncTask for Progress Dialog

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        boolean running;
        ProgressDialog progressDialog;
        @Override
        protected Void doInBackground(Void... params) {
            int i = 10;
            while(running){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i-- == 0){
                    running = false;
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;
            progressDialog = ProgressDialog.show(getActivity(),"","Please wait...",true,false);
            progressDialog.setCanceledOnTouchOutside(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }
    private void takeScreenShot(){
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        try {
            String mPath = Environment.getExternalStorageDirectory().toString() + "/PICTURES/Screenshots/" + now + ".jpg";
            View screenView = getActivity().getWindow().getDecorView().getRootView();
            screenView.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
            screenView.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,outputStream);
            outputStream.flush();
            outputStream.close();

            // Uri file = Uri.fromFile(imageFile);
//            Uri file = Uri.fromFile(new File(mPath));
            InputStream inputStream = new FileInputStream(new File(mPath));
            //Get Storage instance
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            mStorageRef = firebaseStorage.getReferenceFromUrl("gs://trip-e1c18.appspot.com/");

            StorageReference imageStoreage = mStorageRef.child("Locations/" + "file");//file.getLastPathSegment());
            //   UploadTask uploadTask = imageStoreage.putFile(file);
            UploadTask uploadTask = imageStoreage.putStream(inputStream);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("mdas","fail");

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Log.d("mysete","suc");
                }
            });

            //    openScreenshot(imageFile);

        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}
