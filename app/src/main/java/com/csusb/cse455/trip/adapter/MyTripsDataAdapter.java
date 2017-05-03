package com.csusb.cse455.trip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.model.MyTripItem;
import java.util.List;


// MyTrips data adapter.
public class MyTripsDataAdapter extends RecyclerView.Adapter<MyTripsDataAdapter.DataViewHolder> {
    // My trips data.
    private List<MyTripItem> mListData;

    // Layout inflater for recycler view.
    private LayoutInflater mInflater;

    // Main activity communication interface.
    private ItemClickCallback mItemClickCallback;

    // Constructor.
    public MyTripsDataAdapter(List<MyTripItem> listData, Context context) {
        mInflater = LayoutInflater.from(context);
        mListData = listData;
    }

    // Sets item click callback.
    public void setItemClickCallback(final ItemClickCallback callback) {
        mItemClickCallback = callback;
    }

    // Inflates view.
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_trip_list_item, parent, false);
        return new DataViewHolder(view);
    }

    // Binds data.
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        MyTripItem item = mListData.get(position);
        holder.mId.setText(item.getId());
        holder.mLabel.setText(item.getLabel());
        holder.mDescription.setText(item.getDescription());
    }

    // Returns the number of itmes.
    @Override
    public int getItemCount() {
        return mListData.size();
    }


    // Data view holder for MyTrips data adapter.
    class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // UI components.
        private TextView mId;
        private TextView mLabel;
        private TextView mDescription;
        private View mContainer;

        // Constructor.
        public DataViewHolder(View itemView) {
            // Super propagation.
            super(itemView);

            // Initialize components.
            mId = (TextView) itemView.findViewById(R.id.item_id);
            mLabel = (TextView) itemView.findViewById(R.id.item_label);
            mDescription = (TextView) itemView.findViewById(R.id.item_description);
            mContainer = itemView.findViewById(R.id.item_container);

            mContainer.setOnClickListener(this);
        }

        // On click event handler.
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.item_container) {
                mItemClickCallback.onItemClick(getAdapterPosition());
            }
        }
    }
}
