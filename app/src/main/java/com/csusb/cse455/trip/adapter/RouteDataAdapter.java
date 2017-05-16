package com.csusb.cse455.trip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.model.Checkpoint;
import com.csusb.cse455.trip.model.Route;

import java.util.List;

// Location data adapter.
public class RouteDataAdapter extends RecyclerView.Adapter<RouteDataAdapter.DataViewHolder> {
    // Route data.
    private Route mRoute;

    // Layout inflater for recycler view.
    private LayoutInflater mInflater;

    // Main activity communication interface.
    private OnNewTripRouteItemClickCallback mItemClickCallback;

    // Constructor.
    public RouteDataAdapter(Route route, Context context) {
        mInflater = LayoutInflater.from(context);
        mRoute = route;
    }

    // Sets item click callback.
    public void setItemClickCallback(final OnNewTripRouteItemClickCallback callback) {
        mItemClickCallback = callback;
    }

    // Inflates view.
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_route_checkpoint, parent, false);
        return new DataViewHolder(view);
    }

    // Binds data.
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Checkpoint item = mRoute.getCheckpointByListPosition(position);
        holder.mLabel.setText(item.getLabel());
        holder.mDescription.setText(item.getDescription());
    }

    // Returns the number of itmes.
    @Override
    public int getItemCount() {
        return mRoute.size();
    }


    // Data view holder for Location data adapter.
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
            mLabel = (TextView) v.findViewById(R.id.card_label);
            mDescription = (TextView) v.findViewById(R.id.card_description);
            mSnapshot = (ImageView) v.findViewById(R.id.card_snapshot);
            mViewAction = (TextView) v.findViewById(R.id.card_action_view);
            mShareAction = (TextView) v.findViewById(R.id.card_action_share);

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
            if (id == R.id.card_action_view) {
                mItemClickCallback.onViewActionClick(getAdapterPosition());
            }
        }
    }
}
