package com.csusb.cse455.trip.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csusb.cse455.trip.R;

// Contact details fragment.
public class ContactDetailsFragment extends Fragment {
    // Fragment initialization parameters.
    private static final String EXTRA_FIRST_NAME= "EXTRA_FIRST_NAME";
    private static final String EXTRA_LAST_NAME = "EXTRA_LAST_NAME";
    private static final String EXTRA_EMAIL = "EXTRA_EMAIL";

    // Inflate the layout for this fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_contact_details, container, false);
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
            TextView firstName = (TextView) view.findViewById(R.id.item_first_name);
            TextView lastName = (TextView) view.findViewById(R.id.item_last_name);
            TextView email = (TextView) view.findViewById(R.id.item_email);

            // Set data.
            firstName.setText(extras.getString(EXTRA_FIRST_NAME));
            lastName.setText(extras.getString(EXTRA_LAST_NAME));
            email.setText(extras.getString(EXTRA_EMAIL));
        }
    }
}
