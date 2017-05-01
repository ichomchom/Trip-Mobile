package com.csusb.cse455.trip.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csusb.cse455.trip.R;

// RecyclerView data adapter for Trip data.
public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    // Data set.
    private String[] mDataset;

    // Sets up data item view.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Text view.
        public TextView mTextView;

        // Constructor.
        public ViewHolder(TextView view) {
            // Propagate to super.
            super(view);
            // Initialize text view.
            mTextView = view;
        }
    }

    // Constructor using a predefined data set.
    public TripAdapter(String[] dataSet) {
        // Initialize data set.
        mDataset = dataSet;
    }

    // Create new views (invoked by the layout manager).
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view.
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager).
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get element from the data set at this position and replace the contents of the view
        // with that element.
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of the dataset (invoked by the layout manager).
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
