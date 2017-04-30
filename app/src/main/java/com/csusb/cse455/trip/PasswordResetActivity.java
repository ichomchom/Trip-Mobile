package com.csusb.cse455.trip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

    //Regular expression for Email
    public static String emailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

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


        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(recoveryEmail);


        // Valid email flag.
        boolean valid = false;

        //region Check if Email is valid or not
        if (TextUtils.isEmpty(recoveryEmail)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if(!matcher.matches()){
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }
        else
        {
            valid = true;
        }
        //endregion

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
}
