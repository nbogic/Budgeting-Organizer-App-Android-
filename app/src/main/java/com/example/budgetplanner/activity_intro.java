package com.example.budgetplanner;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class activity_intro extends AppCompatActivity  implements View.OnClickListener {

    private Button intro_sign_in;
    private Button intro_new;
    private Button intro_guest;

    VideoView background_intro_video;
    private static int time = 2000;

    /* implementation for buttons to respond to clicks */
    /* checks the id, sends the user to the corresponding activity*/

    @Override
    public void onClick(View view) {
        Intent intent;
        //Intent intent2;
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

        //testing
        System.out.println("Id" + view.getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intro);

        //linking buttons to their respective ids
        intro_sign_in = (Button) findViewById(R.id.sign_in);
        intro_sign_in.setOnClickListener(this);

        intro_new = (Button) findViewById(R.id.create);
        intro_new.setOnClickListener(this);

        intro_guest = (Button) findViewById(R.id.guest);
        intro_guest.setOnClickListener(this);

        /*allows video capability for the background of the intro layout to be displayed with a personalised video
        the background loops and resumes when the user returns to the activity
         */

        background_intro_video = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.placeholder_background);

        background_intro_video.setVideoURI(uri);
        background_intro_video.start();
        background_intro_video.requestFocus();

        background_intro_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        background_intro_video.start();

    }
}



