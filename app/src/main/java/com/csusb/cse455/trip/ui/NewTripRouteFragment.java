package com.csusb.cse455.trip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.adapter.OnNewTripRouteItemClickCallback;
import com.csusb.cse455.trip.adapter.RouteDataAdapter;
import com.csusb.cse455.trip.model.Checkpoint;
import com.csusb.cse455.trip.model.Route;

// Allows addition of new locations to new trip's route.
public class NewTripRouteFragment extends Fragment
        implements OnNewTripRouteItemClickCallback {
    // Fragment initialization parameters.
    private static final String EXTRA_ID = "EXTRA_ID";
    private static final String EXTRA_LABEL = "EXTRA_LABEL";
    private static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";

    // Main activity.
    private NewTripActivity mNewTripActivity;
    // Route data adapter.
    private RouteDataAdapter mAdapter;
    // Route data.
    private Route mRoute;

    // Required empty public constructor.
    public NewTripRouteFragment() { }

    // Inflates view with the specified layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_new_trip_route, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the main activity.
        mNewTripActivity = (NewTripActivity) getActivity();

        // Create a new route.
        mRoute = new Route();

        // Initialize adapter.
        if (mRoute!= null) {
            mAdapter = new RouteDataAdapter(mRoute, getActivity().getBaseContext());
        }

        // Setup Recycler view.
        RecyclerView mRecView = (RecyclerView) view.findViewById(R.id.items_list);
        mRecView.setHasFixedSize(false);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mRecView.setAdapter(mAdapter);
        mAdapter.setItemClickCallback(this);

        // Set up item touch helper.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mRecView);
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

    // Opens a new location creation view.
    private void addNewLocation() {
        Intent intent = new Intent(getActivity(), NewLocationActivity.class);
        getActivity().startActivity(intent);
    }

    // Moves an item within the list.
    private void moveItem(int oldPos, int newPos) {
        Checkpoint item = mRoute.getCheckpointByListPosition(oldPos);
        if (item != null) {
            mRoute.removeCheckpointByListPosition(oldPos);
            mRoute.addCheckpoint(newPos, item);
            mAdapter.notifyItemMoved(oldPos, newPos);
        }
    }

    // Handles on click event for an item.
    @Override
    public void onViewActionClick(int position) {
        // TODO: Implement desired logic.
    }
}
