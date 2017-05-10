package com.csusb.cse455.trip.adapter;

// Interface which allows host activity to handle fragment interaction.
public interface OnFragmentInteractionListener<T> {
    void onFragmentInteraction(String tag, T data);
}
