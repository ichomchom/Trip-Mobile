package com.csusb.cse455.trip.model;

// User information.
public class User {
    private String firstName;
    private String lastName;

    // Default constructor.
    public User(){
        firstName = "";
        lastName = "";
    }

    // Overloaded constructor.
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Gets user's first name.
    public String getFirstName() {
        return firstName;
    }

    // Sets user's first name.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Gets user's last name.
    public String getLastName() {
        return lastName;
    }

    // Sets user's last name.
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
