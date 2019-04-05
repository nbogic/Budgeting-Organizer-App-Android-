package com.example.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class activity_splash extends AppCompatActivity {

    /* splash screen activity */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);

        /*
        duration of the splash screen before the app transitions the intro activity is outlined here
         */

        /* current graphics need to be updated */

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), activity_intro.class);
                startActivity(intent);
                finish();
            }}, 2000);

    }
}
