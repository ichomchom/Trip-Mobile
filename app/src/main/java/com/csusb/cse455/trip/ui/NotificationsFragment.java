package com.csusb.cse455.trip.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.adapter.NotificationsDataAdapter;
import com.csusb.cse455.trip.adapter.OnNotificationItemClickCallback;
import com.csusb.cse455.trip.data.MockDataSource;
import com.csusb.cse455.trip.model.Notification;
import java.util.ArrayList;

// Notifications fragment, which handles the display of Notification items.
public class NotificationsFragment extends Fragment implements OnNotificationItemClickCallback {
    // Fragment initialization parameters.
    private static final String EXTRA_ID = "EXTRA_ID";
    private static final String EXTRA_TITLE = "EXTRA_TITLE";
    private static final String EXTRA_SOURCE = "EXTRA_SOURCE";
    private static final String EXTRA_DATE = "EXTRA_DATE";
    private static final String EXTRA_TIME = "EXTRA_TIME";
    private static final String EXTRA_CONTENT = "EXTRA_CONTENT";

    // Main activity.
    private MainActivity mMainActivity;
    // Data adapter.
    private NotificationsDataAdapter mAdapter;
    // Data list.
    private ArrayList<Notification> mListData;
    //Async Task
    MyAsyncTask myAsyncTask;

    // Required empty public constructor.
    public NotificationsFragment() { }

    // Inflates view with the specified layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the main activity.
        mMainActivity = (MainActivity) getActivity();

        // Get data.
        // TODO: Change to real data.
        mListData = (ArrayList<Notification>) MockDataSource.getNotificationsList(30);

        // Initialize adapter.
        if (mListData != null) {
            mAdapter = new NotificationsDataAdapter(mListData, getActivity().getBaseContext());
        }

        // Setup Recycler view.
        RecyclerView mRecView = (RecyclerView) view.findViewById(R.id.items_list);
        mRecView.setHasFixedSize(false);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mRecView.setAdapter(mAdapter);
        mAdapter.setItemClickCallback(this);

        // Set up item touch helper.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mRecView);
    }

    // Creates callback for touch operations.
    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            // Allows rearrangement of cards.
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Do nothing.
            }
        };
    }

    // Moves an item within the list.
    private void moveItem(int oldPos, int newPos) {
        Notification item = mListData.get(oldPos);
        if (item != null) {
            mListData.remove(oldPos);
            mListData.add(newPos, item);
            mAdapter.notifyItemMoved(oldPos, newPos);
        }
    }

    // Handles on click event by showing details.
    @Override
    public void onViewActionClick(int position) {
        // Get an item form the given position.
        Notification item = mListData.get(position);

        // Create a bundle.
        Bundle extras = new Bundle();
        extras.putString(EXTRA_ID, item.getId());
        extras.putString(EXTRA_TITLE, item.getTitle());
        extras.putString(EXTRA_SOURCE, item.getSource());
        extras.putString(EXTRA_DATE, item.getDate());
        extras.putString(EXTRA_TIME, item.getTime());
        extras.putString(EXTRA_CONTENT, item.getContent());

        // Create a new details fragment.
        Fragment newFragment = new NotificationDetailsFragment();

        // Pass bundle.
        newFragment.setArguments(extras);

        // Load the new fragment.
        mMainActivity.loadFragment(this.getId(), "NOTIFICATION_DETAILS_FRAGMENT", newFragment,
                true, "Notification Details");
        //show Progress Dialog
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    //Set up MyAsyncTask for Progress Dialog
    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        boolean running;
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {
            int i = 10;
            while(running){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i-- == 0){
                    running = false;
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;
            progressDialog = ProgressDialog.show(getActivity(),"","Please wait...",true,false);
            progressDialog.setCanceledOnTouchOutside(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

    }
}
