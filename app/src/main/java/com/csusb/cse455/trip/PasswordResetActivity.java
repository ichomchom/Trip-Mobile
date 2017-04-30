package com.csusb.cse455.trip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

// Password reset screen, which allows a user to request a password reset email
// using a properly formatted email address.  If the email address exists in the
// system, the user will get reset instructions.
public class PasswordResetActivity extends AppCompatActivity {
    // Firebase Authentication instance.
    private FirebaseAuth mAuth;

    @Override
    // Handles initialization during view creation.
    protected void onCreate(Bundle savedInstanceState) {
        // Super propagation.
        super.onCreate(savedInstanceState);
        // Set content view layout.
        setContentView(R.layout.activity_pass_recovery);

        // Get a new Firebase Authentication instance.
        mAuth = FirebaseAuth.getInstance();

        // Get UI references.
        final TextView emailView = (TextView) findViewById(R.id.recoveryEmail);
        final Button resetButton = (Button) findViewById(R.id.recoveryBtn);

        // Set on click listener for the reset button.
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If format is valid, attempt to reset.
                if (checkEmailFormat(emailView)) {
                    tryReset(emailView.getText().toString());
                    // Finish this Activity.
                    finish();
                }
            }
        });
    }

    // Validates email format.
    private boolean checkEmailFormat(TextView emailView) {
        // Get the string from view.
        final String email = emailView.getText().toString();

        // Check if email format is valid.  If not, return false.
        if (!isEmailFormatValid(email)) {
            emailView.setError(getString(R.string.invalidEmailFormat));
            emailView.requestFocus();
            return false;
        }
        // Otherwise, return true.
        return true;
    }

    // Checks if email is formatted properly.
    private boolean isEmailFormatValid(String email) {
        // First check if it's empty.  Return false if it is.
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            // Otherwise, confirm it matches an accepted email format.  Return the resulting
            // boolean value.
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    // Attempts to reset the password for the provided email address.  We do not check if
    // attempt was successful or failed.  If the user gets the email, they'll know it worked.
    private void tryReset(String email) {
        if (mAuth != null) {
            mAuth.sendPasswordResetEmail(email);
            Toast.makeText(PasswordResetActivity.this,
                    "An email with password reset instructions was sent.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
