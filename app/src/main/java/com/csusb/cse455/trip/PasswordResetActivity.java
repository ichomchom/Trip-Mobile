package com.csusb.cse455.trip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasswordResetActivity extends AppCompatActivity {
    // Firebase Authentication instance.
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_recovery);

        // Create a new Firebase Authentication instance.
        mAuth = FirebaseAuth.getInstance();

        // Get the email entered by a user.
     //   final TextView email = (TextView) findViewById(R.id.recoveryEmail);

        // On click, try reset.
        findViewById(R.id.recoveryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // validate(email.getText().toString());
                validate();
            }
        });
    }

    // Validates email format.
    private void validate() {
        //Get email Input
        final TextView email = (TextView) findViewById(R.id.recoveryEmail);

        View focusView = null;
        boolean cancel = false;

        //Convert email to string
        String recoveryEmail = email.getText().toString();

        // Valid email flag.
        boolean valid = false;

        // Check if Email is valid or not
        if (!isEmailFormatValid(recoveryEmail)) {
            email.setError(getString(R.string.invalidEmailFormat));
            focusView = email;
            cancel = true;
        } else {
            valid = true;
        }

        // If valid, attempt to reset.
        if (valid) {
            tryReset(recoveryEmail);
        }
    }

    /*
    Attempts to reset the password for the provided email address.  We do not check if
    attempt was successful or failed.  If the user gets the email, they'll know it worked.
    This is done so that hackers may not attempt to scan through a list of emails to check
    which ones were successful, meaning they exist.
    */
    private void tryReset(String email) {
        mAuth.sendPasswordResetEmail(email);
        Toast.makeText(PasswordResetActivity.this,
                "An email with password reset instructions was sent.",
                Toast.LENGTH_LONG).show();
        // Transition to the login screen.
        transitionToLogin();
    }

    // Transition to the LoginActivity screen.
    private void transitionToLogin()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Checks if email is formatted properly.
    private boolean isEmailFormatValid(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
