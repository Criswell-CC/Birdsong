package com.example.birdsong;

import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private boolean songSet = false;
    private boolean playing = false;

    private MediaPlayer mediaPlayer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        );

        ImageButton playButton = (ImageButton)findViewById(R.id.playButton);
        playButton.setImageResource(R.drawable.play_button_play_foreground);

        playButton.setOnClickListener(v -> {

            if (playing) {
                mediaPlayer.stop();
                playButton.setImageResource(R.drawable.play_button_play_foreground);
                playing = false;
            }

            else {
                if (!songSet) {
                    try {

                        //test with sound file in assets folder --> audio will be fetched from api later
                        AssetFileDescriptor descriptor = getApplicationContext().getAssets().openFd("audio/test.mp3");
                        mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                        descriptor.close();
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        playButton.setImageResource(R.drawable.play_button_pause_foreground);
                        songSet = true;
                        playing = true;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    mediaPlayer.start();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();

    }
}