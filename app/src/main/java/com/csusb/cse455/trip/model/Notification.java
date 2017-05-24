package com.csusb.cse455.trip.model;

// Notification model.
public class Notification {
    // Notification ID.
    private String id;
    // Date of notification.
    private String date;
    // Time of notification.
    private String time;
    // Title of notification (what the user sees in a list).
    private String title;
    // Source of notification (who it came from: system, user, admin, etc.).
    private String source;
    // Content of the notification.
    private String content;

    // Default constructor.
    public Notification() {}

    // Overloaded constructor.
    public Notification(String id, String date, String time, String title,
                        String source, String content) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.title = title;
        this.source = source;
        this.content = content;
    }

    // Get notification id.
    public String getId() { return id; }

    // Set notification id.
    public void setId(String id) { this.id = id; }

    // Get notification date.
    public String getDate() { return date; }

    // Set notification date.
    public void setDate(String date) { this.date = date;}

    // Get notification time.
    public String getTime() { return time; }

    // Set notification time.
    public void setTime(String time) { this.time = time; }

    // Get notification title.
    public String getTitle() { return title; }

    // Set notification title.
    public void setTitle(String title) { this.title = title; }

    // Get notification source.
    public String getSource() { return source; }

    // Set notification source.
    public void setSource(String source) { this.source = source; }

    // Get notification content.
    public String getContent() { return content; }

    // Set notification content.
    public void setContent(String content) { this.content = content; }
}
