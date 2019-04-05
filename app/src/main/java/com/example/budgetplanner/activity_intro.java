package com.example.budgetplanner;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class activity_intro extends AppCompatActivity {
    VideoView background_intro_video;
    private static int time = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intro);

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



