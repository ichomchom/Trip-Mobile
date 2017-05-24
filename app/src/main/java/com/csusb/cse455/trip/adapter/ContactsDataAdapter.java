package com.csusb.cse455.trip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.model.Contact;
import com.csusb.cse455.trip.model.Notification;

import java.util.List;

// Contacts data adapter.
public class ContactsDataAdapter extends RecyclerView.Adapter<ContactsDataAdapter.DataViewHolder> {
    // Data.
    private List<Contact> mListData;

    // Layout inflater for recycler view.
    private LayoutInflater mInflater;

    // Main activity communication interface.
    private OnContactItemClickCallback mItemClickCallback;

    // Constructor.
    public ContactsDataAdapter(List<Contact> listData, Context context) {
        mInflater = LayoutInflater.from(context);
        mListData = listData;
    }

    // Sets item click callback.
    public void setItemClickCallback(final OnContactItemClickCallback callback) {
        mItemClickCallback = callback;
    }

    // Inflates view.
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_contact, parent, false);
        return new DataViewHolder(view);
    }

    // Binds data.
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Contact item = mListData.get(position);
        holder.mFirstName.setText(item.getFirstName());
        holder.mLastName.setText(item.getLastName());
        holder.mEmail.setText(item.getEmail());
        String fName = item.getFirstName();
        if (fName != null && fName.length() > 0) {
            holder.mThumb.setText(fName.substring(0,1));
        } else {
            holder.mThumb.setText("");
        }
    }

    // Returns the number of itmes.
    @Override
    public int getItemCount() {
        return mListData.size();
    }

    // Data view holder for Location data adapter.
    class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // UI components.
        private TextView mFirstName;
        private TextView mLastName;
        private TextView mEmail;
        private TextView mThumb;

        // Constructor.
        private DataViewHolder(View v) {
            // Super propagation.
            super(v);

            // Initialize components.
            mFirstName = (TextView) v.findViewById(R.id.item_first_name);
            mLastName = (TextView) v.findViewById(R.id.item_last_name);
            mEmail = (TextView) v.findViewById(R.id.item_email);
            mThumb = (TextView) v.findViewById(R.id.item_contact_thumb);

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
