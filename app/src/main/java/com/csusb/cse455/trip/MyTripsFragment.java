package com.csusb.cse455.trip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyTripsFragment extends Fragment {
    // UI references.
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Required empty public constructor.
    public MyTripsFragment() { }

    @Override
    // onCreateView event handler.
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_my_trips, container, false);
    }

    @Override
    // onCreate event handler.
    public void onActivityCreated(Bundle savedInstanceState) {
        // Propagate to super.
        super.onActivityCreated(savedInstanceState);

        // Get view.
        View view = getView();

        // Return if view is null.
        if (view == null) {
            return;
        }

        // Initialize members.
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_trips_recycler_view);

        // Use a linear layout manager.
        //mLayoutManager = new LinearLayoutManager(this);

    }
}
