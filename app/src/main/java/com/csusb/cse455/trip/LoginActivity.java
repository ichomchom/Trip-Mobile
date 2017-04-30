package com.csusb.cse455.trip;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.csusb.cse455.trip.utils.Format;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// A login screen that offers login via email and password.
public class LoginActivity extends AppCompatActivity  {
    // Firebase Authentication instance.
    private FirebaseAuth mAuth;

    @Override
    // Handles initialization during view creation.
    protected void onCreate(Bundle savedInstanceState) {
        // Super propagation.
        super.onCreate(savedInstanceState);
        // Set content view layout.
        setContentView(R.layout.activity_login);

        // Create a new Firebase Authentication instance.
        mAuth = FirebaseAuth.getInstance();

        // Get UI references.
        final TextView emailView = (TextView) findViewById(R.id.logEmail);
        final EditText passwordView = (EditText) findViewById(R.id.logPassword);
        final Button loginButton = (Button) findViewById(R.id.logBtn);

        // Register links.
        final TextView registerLink = (TextView) findViewById(R.id.logRegister);
        final TextView passResetLink = (TextView) findViewById(R.id.logResetPass);

        // Set on click listener for the login button.
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // If login information formatting is valid...
                if (Format.checkEmailFormat(emailView) && Format.checkPasswordFormat(passwordView)) {
                    // Attempt to login.
                    tryLogin(emailView.getText().toString(), passwordView.getText().toString());
                }
            }
        });

        // Set on click listener for the registration link.
        registerLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new registration activity.
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        // Set on click listener for the password reset link.
        passResetLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new password reset activity.
                Intent resetIntent = new Intent(LoginActivity.this, PasswordResetActivity.class);
                LoginActivity.this.startActivity(resetIntent);
            }
        });
    }

    @Override
    // Handles initialization during view startup.
    protected void onStart() {
        // Super propagation.
        super.onStart();
        // Get current user.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // If there is a signed in and authenticated user, then transition to the main activity.
        if (currentUser != null && currentUser.isEmailVerified())
        {
            transitionToMain();
        }
    }

    @Override
    // Overrides the default action on back button being pressed by redirecting to device's home.
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
    }

    // Attempts to login using provided credentials.  Return true if successful; otherwise, false.
    private void tryLogin(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // If credentials are verified, attempt to transition to main.
                            transitionToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this,
                                    "Unable to login.  Verify your information.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // Transition to main activity (user email must be verified).
    private void transitionToMain()
    {
        // Go to the main activity if verified.
        if (mAuth.getCurrentUser().isEmailVerified()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
        } else {
            // Otherwise, let the user know and see if they'd like to re-attempt verification.
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Unable to login.")
                    .setMessage("You must first verify your email address. " +
                            "Please check your inbox and spam folders.")
                    .setCancelable(true)
                    .setPositiveButton("SEND NEW VERIFICATION",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    verifyEmail();
                                }
                    })
                    .show();
        }
    }

    // Sends out a verification email to the newly registered user.
    private void verifyEmail() {
        Toast.makeText(LoginActivity.this, "An email address verification was sent.",
                Toast.LENGTH_LONG).show();
        mAuth.getCurrentUser().sendEmailVerification();
    }
}
