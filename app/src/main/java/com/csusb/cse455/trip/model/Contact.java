package com.csusb.cse455.trip.model;

// Contact information.
public class Contact extends User {
    private String id;
    private String email;

    // Default constructor.
    public Contact() {
        this("", "");
    }

    // Overloaded constructor.
    public Contact(String id, String email) {
        this(id, email, "", "");
    }

    // Overloaded constructor.
    public Contact(String id, String email, String fName, String lName) {
        super(fName, lName);
        this.id = id;
        this.email = email;
    }

    // Gets contact's ID.
    public String getId() { return id; }

    // Sets contact's ID.
    public void setId(String id) { this.id = id; }

    // Gets contact's email.
    public String getEmail() { return email; }

    // Sets contact's email.
    public void setEmail(String email) { this.email = email; }
}
