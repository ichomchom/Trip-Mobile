package com.csusb.cse455.trip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.model.Notification;

import java.util.List;

// Notification data adapter.
public class NotificationsDataAdapter extends RecyclerView.Adapter<NotificationsDataAdapter.DataViewHolder> {
    // Data.
    private List<Notification> mListData;

    // Layout inflater for recycler view.
    private LayoutInflater mInflater;

    // Main activity communication interface.
    private OnNotificationItemClickCallback mItemClickCallback;

    // Constructor.
    public NotificationsDataAdapter(List<Notification> listData, Context context) {
        mInflater = LayoutInflater.from(context);
        mListData = listData;
    }

    // Sets item click callback.
    public void setItemClickCallback(final OnNotificationItemClickCallback callback) {
        mItemClickCallback = callback;
    }

    // Inflates view.
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_notification, parent, false);
        return new DataViewHolder(view);
    }

    // Binds data.
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Notification item = mListData.get(position);
        holder.mTitle.setText(item.getTitle());
        holder.mSource.setText(item.getSource());
        holder.mContent.setText(item.getContent());
        holder.mDate.setText(item.getDate());
        holder.mTime.setText(item.getTime());
    }

    // Returns the number of itmes.
    @Override
    public int getItemCount() {
        return mListData.size();
    }

    // Data view holder for Location data adapter.
    class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // UI components.
        private TextView mTitle;
        private TextView mSource;
        private TextView mContent;
        private TextView mDate;
        private TextView mTime;

        // Constructor.
        private DataViewHolder(View v) {
            // Super propagation.
            super(v);

            // Initialize components.
            mTitle = (TextView) v.findViewById(R.id.item_title);
            mSource = (TextView) v.findViewById(R.id.item_source);
            mContent = (TextView) v.findViewById(R.id.item_content);
            mDate = (TextView) v.findViewById(R.id.item_date);
            mTime = (TextView) v.findViewById(R.id.item_time);

            // Set click listeners.
            v.findViewById(R.id.item).setOnClickListener(this);
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
            if (id == R.id.item) {
                mItemClickCallback.onViewActionClick(getAdapterPosition());
            }
        }
    }
}
