package com.csusb.cse455.trip.utils;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Formatting and format validation utilities.
public class Format {
    // Minimum number of characters in a password.
    public static final int PASSWORD_LENGTH = 6;

    // Validates email format.
    public static boolean checkEmailFormat(TextView emailView) {
        // Get the string from view.
        final String email = emailView.getText().toString();

        // Check if email format is valid.  If not, return false.
        if (!isEmailFormatValid(email)) {
            emailView.setError("Invalid email address format.");
            emailView.requestFocus();
            return false;
        }
        // Otherwise, return true.
        return true;
    }

    // Checks if email is formatted properly.
    public static boolean isEmailFormatValid(String email) {
        // First check if it's empty.  Return false if it is.
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            // Otherwise, confirm it matches an accepted email format.  Return the resulting
            // boolean value.
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    // Validates password format of a newly created password, which required the original
    // password and its retype for confirmation.
    public static boolean checkNewPasswordFormat(EditText passwordView, EditText rePasswordView) {
        final String password = passwordView.getText().toString();
        final String rePassword = rePasswordView.getText().toString();

        // First, check if formatting is valid.
        if (!checkPasswordFormat(passwordView))
        {
            return false;
        }
        // Then check if password and its retype copy are equal.
        if (!password.equals(rePassword)) {
            rePasswordView.setError("Passwords do not match.");
            rePasswordView.requestFocus();
            return false;
        }
        return true;
    }

    // Validates password format.
    public static boolean checkPasswordFormat(EditText passwordView) {
        // Get the string from view.
        final String password = passwordView.getText().toString();

        // Check if password format is valid.  If not, return false.
        if (!isPasswordFormatValid(password)) {
            passwordView.setError("Password is too short.  Minimum length is " +
                    PASSWORD_LENGTH + ".");
            passwordView.requestFocus();
            return false;
        }
        // Otherwise, return true.
        return true;
    }

    // Checks if password is formatted properly.
    public static boolean isPasswordFormatValid(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= PASSWORD_LENGTH;
    }

    // Checks if a generic text view is empty.  Returns true if it is; otherwise, false.
    public static boolean isTextViewEmpty(TextView view) {
        // If the field is empty, return true.
        if (TextUtils.isEmpty(view.getText().toString())) {
            view.setError("This field is required.");
            view.requestFocus();
            return true;
        }
        // Otherwise, return false.
        return false;
    }

    // Checks if latitude is valid.
    public static boolean isValidLatitude(TextView view) {
        String lat = view.getText().toString();
        String p = "[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)";
        Pattern pattern = Pattern.compile(p);
        Matcher m = pattern.matcher(lat);
        if (!m.matches()) {
            view.setError("Invalid latitude value.");
            view.requestFocus();
            return false;
        }
        return true;
    }

    // Checks if longitude is valid.
    public static boolean isValidLongitude(TextView view) {
        String lng = view.getText().toString();
        String p = "[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)";
        Pattern pattern = Pattern.compile(p);
        Matcher m = pattern.matcher(lng);
        if (!m.matches()) {
            view.setError("Invalid longitude value.");
            view.requestFocus();
            return false;
        }
        return true;
    }

}
