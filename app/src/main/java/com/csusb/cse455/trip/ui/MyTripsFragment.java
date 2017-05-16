package com.csusb.cse455.trip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.adapter.OnMyTripCardClickCallback;
import com.csusb.cse455.trip.adapter.MyTripsDataAdapter;
import com.csusb.cse455.trip.data.MockDataSource;
import com.csusb.cse455.trip.model.Trip;
import java.util.ArrayList;

// My Trips fragment, which handles the display of My Trip cards.
public class MyTripsFragment extends Fragment implements OnMyTripCardClickCallback {
    // Fragment initialization parameters.
    private static final String EXTRA_ID = "EXTRA_ID";
    private static final String EXTRA_LABEL = "EXTRA_LABEL";
    private static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";

    // Main activity.
    private MainActivity mMainActivity;
    // Data adapter.
    private MyTripsDataAdapter mAdapter;
    // Data list.
    private ArrayList<Trip> mListData;

    // Required empty public constructor.
    public MyTripsFragment() { }

    // Inflates view with the specified layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_my_trips, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the main activity.
        mMainActivity = (MainActivity) getActivity();

        // Get data.
        // TODO: Change to real data.
        mListData = (ArrayList<Trip>) MockDataSource.getMyTripsList(30);

        // Initialize adapter.
        if (mListData != null) {
            mAdapter = new MyTripsDataAdapter(mListData, getActivity().getBaseContext());
        }

        // Setup Recycler view.
        RecyclerView mRecView = (RecyclerView) view.findViewById(R.id.items_list);
        mRecView.setHasFixedSize(false);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mRecView.setAdapter(mAdapter);
        mAdapter.setCardClickCallback(this);

        // Set up item touch helper.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mRecView);

        // Setup floating action button click listener.
        FloatingActionButton addItem = (FloatingActionButton) view.findViewById(R.id.btn_add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTrip();
            }
        });
    }

    // Creates callback for touch operations.
    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            // Allows rearrangement of cards.
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

    // Opens a new trip creation view.
    private void addNewTrip() {
        Intent intent = new Intent(getActivity(), NewTripActivity.class);
        getActivity().startActivity(intent);
    }

    // Moves an item within the list.
    private void moveItem(int oldPos, int newPos) {
        Trip item = mListData.get(oldPos);
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
        Trip item = mListData.get(position);

        // Create a bundle.
        Bundle extras = new Bundle();
        extras.putString(EXTRA_ID, item.getId());
        extras.putString(EXTRA_LABEL, item.getLabel());
        extras.putString(EXTRA_DESCRIPTION, item.getDescription());

        // Create a new details fragment.
        Fragment newFragment = new MyTripDetailsFragment();

        // Pass bundle.
        newFragment.setArguments(extras);

        // Load the new fragment.
        mMainActivity.loadFragment(this.getId(), "MY_TRIP_DETAILS_FRAGMENT", newFragment,
                true, "My Trip Details");
    }

    // Handles click on SHARE action.
    @Override
    public void onShareActionClick(int position) {
        // Get an item from the given position.

    }
}
