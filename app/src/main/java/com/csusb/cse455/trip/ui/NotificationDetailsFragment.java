package com.csusb.cse455.trip.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csusb.cse455.trip.R;

// Notification details fragment.
public class NotificationDetailsFragment extends Fragment {
    // Fragment initialization parameters.
    private static final String EXTRA_ID = "EXTRA_ID";
    private static final String EXTRA_TITLE = "EXTRA_TITLE";
    private static final String EXTRA_SOURCE = "EXTRA_SOURCE";
    private static final String EXTRA_DATE = "EXTRA_DATE";
    private static final String EXTRA_TIME = "EXTRA_TIME";
    private static final String EXTRA_CONTENT = "EXTRA_CONTENT";

    // Inflate the layout for this fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_notification_details, container, false);
    }

    // Handles bindings after view has been created.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the bundle passed from the parent.
        Bundle extras = this.getArguments();

        // If not null, set bindings.
        if (extras != null)
        {
            // Get UI elements.
            //TextView id = (TextView) view.findViewById(R.id.item_id);
            TextView title = (TextView) view.findViewById(R.id.item_title);
            TextView source = (TextView) view.findViewById(R.id.item_source);
            TextView date = (TextView) view.findViewById(R.id.item_date);
            TextView time = (TextView) view.findViewById(R.id.item_time);
            TextView content = (TextView) view.findViewById(R.id.item_content);

            // Set data.
            //id.setText(extras.getString(EXTRA_ID));
            title.setText(extras.getString(EXTRA_TITLE));
            source.setText(extras.getString(EXTRA_SOURCE));
            date.setText(extras.getString(EXTRA_DATE));
            time.setText(extras.getString(EXTRA_TIME));
            content.setText(extras.getString(EXTRA_CONTENT));
        }
    }
}
