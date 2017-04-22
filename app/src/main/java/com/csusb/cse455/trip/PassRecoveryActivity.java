package com.csusb.cse455.trip;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PassRecoveryActivity extends AppCompatActivity {
    // Firebase Authentication instance.
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_recovery);

        // Create a new Firebase Authentication instance.
        mAuth = FirebaseAuth.getInstance();

        // Get the email entered by a user.
        final TextView email = (TextView) findViewById(R.id.recoveryEmail);

        // On click, try reset.
        findViewById(R.id.recoveryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(email.getText().toString());
            }
        });
    }

    // Validates email format.
    private void validate(String email) {
        // TODO: Check if email is of right format here.
        boolean valid = true;

        // If valid, attempt to reset.
        if (valid) {
            tryReset(email);
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
        Toast.makeText(PassRecoveryActivity.this,
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
}
