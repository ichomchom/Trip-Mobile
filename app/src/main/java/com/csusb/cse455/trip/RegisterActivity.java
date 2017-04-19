package com.csusb.cse455.trip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //User register entries

        final EditText regFirstName = (EditText) findViewById(R.id.regFirstName);
        final EditText regLastName = (EditText) findViewById(R.id.regLastName);
        final EditText regEmail = (EditText) findViewById(R.id.regEmail);
        final EditText regPhoneNum = (EditText) findViewById(R.id.regPhoneNum);
        final EditText regPassword = (EditText) findViewById(R.id.regPassword);
        final EditText regRePassword = (EditText) findViewById(R.id.regRePassword);
        final Button regBtn = (Button) findViewById(R.id.regBtn);

        //Register Button

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(registerIntent);
            }
        });
    }
}
