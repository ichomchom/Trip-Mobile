package com.csusb.cse455.trip.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csusb.cse455.trip.R;

// My Trip details fragment.
public class MyTripDetailsFragment extends Fragment {
    // Fragment initialization parameters.
    private static final String EXTRA_ID = "EXTRA_ID";
    private static final String EXTRA_LABEL = "EXTRA_LABEL";
    private static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";

    // Inflates view with the specified layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_my_trip_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the bundle passed from the parent.
        Bundle extras = this.getArguments();

        // If not null, set bindings.
        if (extras != null)
        {
            // Get UI elements.
            TextView id = (TextView) view.findViewById(R.id.item_id);
            TextView label = (TextView) view.findViewById(R.id.item_label);
            TextView description = (TextView) view.findViewById(R.id.item_description);

            // Set data.
            id.setText(extras.getString(EXTRA_ID));
            label.setText(extras.getString(EXTRA_LABEL));
            description.setText(extras.getString(EXTRA_DESCRIPTION));
        }
    }
}
