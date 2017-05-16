package com.csusb.cse455.trip.model;

// Route check point.
public class Checkpoint extends Location {
    // Flag indicating whether the checkpoint has been visited on the current route.
    private boolean mVisited;

    // Default constructor.
    public Checkpoint() { super(); }

    // Overloaded constructor.
    public Checkpoint(boolean visited, int position, String label, String descr,
                      double lat, double lng, boolean fav) {
        super(position, label, descr, lat, lng, fav);
        mVisited = visited;
    }

    // Returns visited flag.
    public boolean getVisited() { return mVisited; }

    // Sets visited flag.
    public void setVisited(boolean visited) { this.mVisited = visited; }
}
