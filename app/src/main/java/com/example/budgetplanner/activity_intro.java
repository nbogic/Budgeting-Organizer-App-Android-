package com.example.budgetplanner;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
/**
 * Followed by a splash screen, this class is the landing screen after the user launches the application. In this screen, the user can choose different methods to get access to the app (sign in, create, guest mode).
 */

public class activity_intro extends AppCompatActivity  implements View.OnClickListener {

    /**
     * btnLoginAccount - the user would click on this button to log into the application with a existing account in the file system
     * btnCreateAccount - the user would click on this button to create a new account to login with
     * btnGuestAccount - the user would click on this button in the case they do not create an account, or have a existing one
     */
    private Button btnLoginAccount;
    private Button btnCreateAccount;
    private Button btnGuestAccount;

    /**
     * vvBackground - a VideoView that will be used as the activity's background
     */
    VideoView vvBackground;

    /**
     * onClick - listen for button clicks and perform the appropriate function
     * ID.sign_in - takes the user to the sign in screen
     * ID.create = takes the user to the account creation screen
     * ID.guest = takes the user to the home screen
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.sign_in:
                intent = new Intent(this, activity_account_login.class);
                startActivity(intent);
                break;
            case R.id.create:
                intent = new Intent(this, activity_account_create.class);
                startActivity(intent);
                break;
            case R.id.guest:
                intent = new Intent(this, activity_home.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intro);

        /**
         * findViewById - distribute IDs to the corresponding views
         */

        btnLoginAccount = findViewById(R.id.sign_in);
        btnLoginAccount.setOnClickListener(this);

        btnCreateAccount = findViewById(R.id.create);
        btnCreateAccount.setOnClickListener(this);

        btnGuestAccount = findViewById(R.id.guest);
        btnGuestAccount.setOnClickListener(this);

        vvBackground = findViewById(R.id.videoView);

        loadBackground();

        /**
         * Function call (loadBackground) - load the background for this activity
         */
    }

    /**
     * loadBackground - enables video capability for the background of this layout, requires at least one video file in the project's storage
     * The background loops and resumes when the user returns to the activity.
     * @param "None"
     * @return: void
     */
    public void loadBackground() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.placeholder_background);
        vvBackground.setVideoURI(uri);
        vvBackground.start();
        vvBackground.requestFocus();

        vvBackground.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                /**
                 * setLooping - let the background loop continuously to prevent noticeable stops while the video is streaming
                 */
                mediaPlayer.setLooping(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        vvBackground.start();

    }
}



