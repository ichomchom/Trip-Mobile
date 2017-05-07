package com.csusb.cse455.trip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.model.Trip;
import java.util.List;


// MyTrips data adapter.
public class MyTripsDataAdapter extends RecyclerView.Adapter<MyTripsDataAdapter.DataViewHolder> {
    // My trips data.
    private List<Trip> mListData;

    // Layout inflater for recycler view.
    private LayoutInflater mInflater;

    // Main activity communication interface.
    private OnMyTripCardClickCallback mCardClickCallback;

    // Constructor.
    public MyTripsDataAdapter(List<Trip> listData, Context context) {
        mInflater = LayoutInflater.from(context);
        mListData = listData;
    }

    // Sets item click callback.
    public void setCardClickCallback(final OnMyTripCardClickCallback callback) {
        mCardClickCallback = callback;
    }

    // Inflates view.
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_my_trip, parent, false);
        return new DataViewHolder(view);
    }

    // Binds data.
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Trip item = mListData.get(position);
        holder.mLabel.setText(item.getLabel());
        holder.mDescription.setText(item.getDescription());
        //String imgSource = item.getImageSource();
        //ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        //imgLoader.DisplayImage(imgSource, R.drawable.trip_snapshot_placeholder, holder.mSnapshot);
    }

    // Returns the number of itmes.
    @Override
    public int getItemCount() {
        return mListData.size();
    }


    // Data view holder for MyTrips data adapter.
    class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // UI components.
        private TextView mLabel;
        private TextView mDescription;
        private ImageView mSnapshot;
        private TextView mViewAction;
        private TextView mShareAction;

        // Constructor.
        private DataViewHolder(View v) {
            // Super propagation.
            super(v);

            // Initialize components.
            mLabel = (TextView) v.findViewById(R.id.card_my_trip_label);
            mDescription = (TextView) v.findViewById(R.id.card_my_trip_description);
            mSnapshot = (ImageView) v.findViewById(R.id.card_my_trip_snapshot);
            mViewAction = (TextView) v.findViewById(R.id.card_my_trip_action_view);
            mShareAction = (TextView) v.findViewById(R.id.card_my_trip_action_share);

            // Set click listeners.
            mViewAction.setOnClickListener(this);
            mShareAction.setOnClickListener(this);
        }

        // On click event handler.
        @Override
        public void onClick(View v) {
            // Standard null check.
            if (v == null)
            {
                return;
            }

            // Get id.
            int id = v.getId();

            // Take action based on ID.
            if (id == R.id.card_my_trip_action_view) {
                mCardClickCallback.onViewActionClick(getAdapterPosition());
            } else if (id == R.id.card_my_trip_action_share) {
                mCardClickCallback.onShareActionClick(getAdapterPosition());
            }
        }
    }
}
