package com.csusb.cse455.trip;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class PassRecoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_recovery);

        //Set up Notification

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        final Button recoveryBtn = (Button) findViewById(R.id.recoveryBtn);

        mBuilder.setContentTitle("Password Reset!");
        mBuilder.setContentText("A link has been sent to your email to reset the password.");

        final Intent resultIntent = new Intent(this, PassRecoveryActivity.class);

        //click the button show notification email has been sent

        recoveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
