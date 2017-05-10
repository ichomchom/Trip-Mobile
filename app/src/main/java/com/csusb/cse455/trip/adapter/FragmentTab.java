package com.csusb.cse455.trip.adapter;

// Holds information for a fragment tab view.  This is used in classes such as
// FixedTabsPagerAdapter.
public class FragmentTab {
    // View's title.
    private String mTitle;
    // View's class.
    private Class<?> mClass;

    // Constructor accepting class and title.
    public FragmentTab(Class<?> cls, String title) {
        mTitle = title;
        mClass = cls;
    }

    // Returns view's title.
    public String getTitle() {
        return mTitle;
    }

    // Sets view's title.
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    // Returns view's class.
    public Class<?> getFragmentClass() {
        return mClass;
    }

    // Sets view's class.
    public void setFragmentClass(Class<?> cls) {
        this.mClass = cls;
    }
}
