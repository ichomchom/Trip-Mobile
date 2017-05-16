package com.csusb.cse455.trip.model;

import java.util.ArrayList;
import java.util.List;

// Route containing the checkpoints for the trip.
public class Route {
    // List of checkpoints.
    private List<Checkpoint> mCheckpoints;

    // Default constructor.
    public Route() {
        mCheckpoints = new ArrayList<>();
    }

    // Sets list of checkpoints.
    public void setCheckpoints(List<Checkpoint> checkpoints) {
        mCheckpoints = checkpoints;
    }

    // Gets list of check points.
    public List<Checkpoint> getCheckpoints() { return mCheckpoints; }

    // Gets a checkpoint by ID.
    public Checkpoint getCheckpointById(String id) {
        Checkpoint checkpoint = null;
        for (Checkpoint temp : mCheckpoints) {
            if (temp.getId().equals(id)) {
                checkpoint = temp;
                break;
            }
        }
        return checkpoint;
    }

    // Gets a checkpoint by list position.
    public Checkpoint getCheckpointByListPosition(int position) {
        Checkpoint checkpoint = null;
        if (position < mCheckpoints.size()) {
            checkpoint = mCheckpoints.get(position);
        }
        return checkpoint;
    }

    // Gets a checkpoint by route position.
    public Checkpoint getCheckpointByRoutePosition(int position) {
        Checkpoint checkpoint = null;
        for (Checkpoint temp : mCheckpoints) {
            if (temp.getPosition() == position) {
                checkpoint = temp;
                break;
            }
        }
        return checkpoint;
    }

    // Adds a checkpoint.
    public void addCheckpoint(Checkpoint checkpoint) {
        mCheckpoints.add(checkpoint);
    }

    // Adds a checkpoint at specific position.
    public void addCheckpoint(int position, Checkpoint checkpoint) {
        mCheckpoints.add(position, checkpoint);
    }

    // Removes a checkpoint by ID.
    public boolean removeCheckpointById(String id) {
        Checkpoint checkpoint = null;
        for (Checkpoint temp : mCheckpoints) {
            if (temp.getId().equals(id)) {
                checkpoint = temp;
                break;
            }
        }
        if (checkpoint != null) {
            mCheckpoints.remove(checkpoint);
            return true;
        }
        return false;
    }

    // Removes a checkpoint by reference.
    public boolean removeCheckpointByReference(Checkpoint checkpoint) {
        if (mCheckpoints.contains(checkpoint)) {
            mCheckpoints.remove(checkpoint);
            return true;
        }
        return false;
    }


    // Removes a checkpoint by list position.
    public boolean removeCheckpointByListPosition(int position) {
        if (position < mCheckpoints.size()) {
            mCheckpoints.remove(position);
            return true;
        }
        return false;
    }

    // Removes a checkpoint by route position.
    public boolean removeCheckpointByRoutePosition(int position) {
        Checkpoint checkpoint = null;
        for (Checkpoint temp : mCheckpoints) {
            if (temp.getPosition() == position) {
                checkpoint = temp;
                break;
            }
        }
        if (checkpoint != null) {
            mCheckpoints.remove(checkpoint);
            return true;
        }
        return false;
    }

    // Returns route size.
    public int size() {
        return mCheckpoints.size();
    }
}
