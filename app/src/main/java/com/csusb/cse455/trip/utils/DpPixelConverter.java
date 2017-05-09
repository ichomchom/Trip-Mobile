package com.csusb.cse455.trip.utils;

import android.content.Context;

// Handles conversion between dp and pixels.
public class DpPixelConverter {
    // Converts dp to pixels.
    public static float convertDpToPixel(float dp, final Context context){
        return dp * context.getResources().getDisplayMetrics().density;
    }

    // Converts pixels to dp.
    public static float convertPixelsToDp(float px, final Context context){
        return px / context.getResources().getDisplayMetrics().density;
    }
}
