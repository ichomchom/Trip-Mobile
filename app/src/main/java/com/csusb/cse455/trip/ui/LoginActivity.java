package com.csusb.cse455.trip.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.csusb.cse455.trip.R;
import com.csusb.cse455.trip.utils.FirebaseUtil;
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

    //Async Task
    MyAsyncTask myAsyncTask;

    // Handles initialization during view creation.
    @Override
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
                    //execute Async Task for Progress Dialog
                        myAsyncTask = new MyAsyncTask();
                        myAsyncTask.execute();

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

    // Handles initialization during view startup.
    @Override
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



    // Overrides the default action on back button being pressed by redirecting to device's home.
    @Override
    public void onBackPressed() {
        finish();
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
        // Get current user.
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        // Go to the main activity if verified.
        if (currentUser != null && currentUser.isEmailVerified()) {
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
                                    FirebaseUtil.sendEmailVerification(LoginActivity.this,
                                            mAuth.getCurrentUser());
                                }
                    })
                    .show();
        }
    }

    //Set up MyAsyncTask for Progress Dialog

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        boolean running;
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {
            int i = 10;
            while(running){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i-- == 0){
                    running = false;
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;
            progressDialog = ProgressDialog.show(LoginActivity.this,"","Authenticating...",true,false);
            progressDialog.setCanceledOnTouchOutside(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

    }

}
