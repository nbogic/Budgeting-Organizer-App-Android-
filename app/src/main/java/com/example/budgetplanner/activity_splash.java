package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
/**
 * The purpose of this class is to create a brief splash screen before the application reaches the main screens
 */

public class activity_splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
/**
 * Create a new timer to decide how long the splash image will appear for
 * Delay: 2000
 */
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                /**
                 * Create a new intent to transition the user to the welcome screen (activity_intro)
                 */
                Intent intent = new Intent(getApplicationContext(), activity_intro.class);
                startActivity(intent);
                finish();
            }}, 2000);

    }
}
