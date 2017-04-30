package com.csusb.cse455.trip.utils;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import com.csusb.cse455.trip.R;

// Handles formatting and format validation.
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

    // Validates password format.
    public static boolean checkPasswordFormat(EditText passwordView) {
        // Get the string from view.
        final String password = passwordView.getText().toString();

        // Check if password format is valid.  If not, return false.
        if (!isPasswordFormatValid(password)) {
            passwordView.setError("Password is too short.  Minimum length is" + PASSWORD_LENGTH);
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

}
