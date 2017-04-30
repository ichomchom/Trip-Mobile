package com.csusb.cse455.trip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    // Used for logging.
    private static final String TAG = RegisterActivity.class.getSimpleName();

    // Firebase Authentication instance.
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get Firebase Authentication instance.
        mAuth = FirebaseAuth.getInstance();

        // Hook up callback method for registration button's OnClickListener.
        findViewById(R.id.regBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verify information.
                verify();
            }
        });
    }

    // Verifies registration information.  If valid, attempts to register by calling
    // tryRegister(...).
    protected void verify() {
        // Get the fields.
        final EditText regFirstName = (EditText) findViewById(R.id.regFirstName);
        final EditText regLastName = (EditText) findViewById(R.id.regLastName);
        final AutoCompleteTextView regEmail = (AutoCompleteTextView) findViewById(R.id.regEmail);
        final EditText regPassword = (EditText) findViewById(R.id.regPassword);
        final EditText regRePassword = (EditText) findViewById(R.id.regRePassword);

        View focusView = null;
        boolean cancel = false;

        //region Convert the inputs to String
        String email = regEmail.getText().toString();
        String fName = regFirstName.getText().toString();
        String lName = regLastName.getText().toString();
        String pass1 = regPassword.getText().toString();
        String pass2 = regRePassword.getText().toString();
        //endregion

        // Valid formatting flags.
        boolean validEmailFormat = false;
        boolean validFirstNameFormat = false;
        boolean validLastNameFormat = false;
        boolean validPasswordFormat = false;

        //region Check First and Last Name if Empty
        if (TextUtils.isEmpty(fName)) {
            regFirstName.setError(getString(R.string.error_field_required));
            focusView = regFirstName;
            cancel = true;
        } else {
            validFirstNameFormat = true;
        }

        if (TextUtils.isEmpty(lName)) {
            regLastName.setError(getString(R.string.error_field_required));
            focusView = regLastName;
            cancel = true;
        } else {
            validLastNameFormat = true;
        }
        //endregion

        //region Check if password matches itself and if format is valid.
        if (!pass1.equals(pass2)) {
            regPassword.setError(getString(R.string.error_password_not_match));
            regRePassword.setError(getString(R.string.error_password_not_match));
            focusView = regPassword;
            cancel = true;
        } else if (!isPasswordFormatValid(pass1)) {
            regPassword.setError("Invalid password length. It must be a minimum of " +
                    LoginActivity.PASSWORD_LENGTH + " characters long.");
            regRePassword.setError("Invalid password length. It must be a minimum of " +
                    LoginActivity.PASSWORD_LENGTH + " characters long.");
            focusView = regPassword;
            cancel = true;
        } else {
            validPasswordFormat = true;
        }
        //endregion

        //region Check if email format is valid.
        if (!isEmailFormatValid(email)) {
            regEmail.setError(getString(R.string.invalidEmailFormat));
            focusView = regEmail;
            cancel = true;
        } else {
            validEmailFormat = true;
        }
        //endregion

        // If valid, try registering.
        if (validEmailFormat && validPasswordFormat && validFirstNameFormat &&
                validLastNameFormat) {
            tryRegister(email, pass1,fName, lName);
        }
    }

    // Checks if email is formatted properly.
    private boolean isEmailFormatValid(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    // Checks if password is formatted properly.
    private boolean isPasswordFormatValid(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= LoginActivity.PASSWORD_LENGTH;
    }

    // Attempts to register a user.  If successful, sends out email verification.  If not,
    // notifies the user.
    protected void tryRegister(final String email, final String password,
                               final String fName, final String lName) {
        // Attempt to create a user with email and password.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If successful, lets the user know, stores additional information provided,
                        // sends out verification email and transitions to the login screen.
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Success.  " +
                                    "An email address verification was sent.",
                                    Toast.LENGTH_LONG).show();
                            // If successful store additional information.
                            storeInfo(email, fName, lName);
                            // Send out verification email.
                            verifyEmail();
                            // Transition to the login screen.
                            transitionToLogin();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this,
                                    "Could not register using provided information.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // Store additional information provided in the registration form.
    private void storeInfo(final String email, final String fName, final String lName)
    {
        // Get the database instance.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        // Get a reference to the users tree with a unique key for the new user.
        DatabaseReference dbRef = db.getReference("users").push();
        // Store information.
        dbRef.child("email").setValue(email);
        dbRef.child("first_name").setValue(fName);
        dbRef.child("last_name").setValue(lName);

    }

    // Sends out a verification email to the newly registered user.
    private void verifyEmail() {
        try {
            mAuth.getCurrentUser().sendEmailVerification();
        }
        catch (java.lang.NullPointerException e) {
            Log.v(TAG, "verifyUserWithEmail:failure", e);
        }
    }

    // Transition to the LoginActivity screen.
    private void transitionToLogin()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
