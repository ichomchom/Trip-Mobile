package com.csusb.cse455.trip.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.adapter.ItemClickCallback;
import com.csusb.cse455.trip.adapter.MyTripsDataAdapter;
import com.csusb.cse455.trip.data.MockDataSource;
import com.csusb.cse455.trip.model.MyTripItem;

import java.util.ArrayList;

public class MyTripsFragment extends Fragment implements ItemClickCallback {
    // Fragment initialization parameters.
    private static final String EXTRA_ID = "EXTRA_ID";
    private static final String EXTRA_LABEL = "EXTRA_LABEL";
    private static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";

    // Recycler view.
    private RecyclerView mRecView;
    // Data adapter.
    private MyTripsDataAdapter mAdapter;
    // Data list.
    private ArrayList mListData;

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

        // Get data.
        // TODO: Change to real data.
        mListData = (ArrayList) MockDataSource.getMyTripItemsList(30);
        if (mListData == null) {
            mListData = new ArrayList();
        }

        // Initialize adapter.
        mAdapter = new MyTripsDataAdapter(mListData, getActivity().getBaseContext());

        // Setup Recycler view.
        mRecView = (RecyclerView) view.findViewById(R.id.items_list);
        mRecView.setHasFixedSize(false);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mRecView.setAdapter(mAdapter);
        mAdapter.setItemClickCallback(this);

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
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        deleteItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;
    }

    // Opens a new trip creation view.
    private void addNewTrip() {
        // Create a new details fragment.
        Fragment newFragment = new NewTripFragment();

        // Create a fragment transaction.
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace this fragment with the new fragment.  Push old fragment onto the stack.
        transaction.replace(this.getId(), newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack("NEW_TRIP");

        // Commit transaction.
        transaction.commit();

        // Set new title.
        setNewTitle("New Trip");
    }

    // Moves an item within the list.
    private void moveItem(int oldPos, int newPos) {
        MyTripItem item = (MyTripItem) mListData.get(oldPos);
        mListData.remove(oldPos);
        mListData.add(newPos, item);
        mAdapter.notifyItemMoved(oldPos, newPos);
    }

    // Deletes an item from the list.
    private void deleteItem(int position) {
        mListData.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    // Handles on click event by showing details.
    @Override
    public void onItemClick(int position) {
        // Get an item form the given position.
        MyTripItem item = (MyTripItem) mListData.get(position);

        // Create a bundle.
        Bundle extras = new Bundle();
        extras.putString(EXTRA_ID, item.getId());
        extras.putString(EXTRA_LABEL, item.getLabel());
        extras.putString(EXTRA_DESCRIPTION, item.getDescription());

        // Create a new details fragment.
        Fragment newFragment = new MyTripDetailsFragment();

        // Pass bundle.
        newFragment.setArguments(extras);

        // Create a fragment transaction.
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace this fragment with the new fragment.  Push old fragment onto the stack.
        transaction.replace(this.getId(), newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack("MY_TRIP_DETAILS");

        // Commit transaction.
        transaction.commit();

        // Set new title.
        setNewTitle("My Trip Details");
    }

    private void setNewTitle(String title) {
        // Add existing title to the title stack.
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.pushTitle(mainActivity.getTitle().toString());

        // Change title.
        mainActivity.setTitle(title);
    }
}
