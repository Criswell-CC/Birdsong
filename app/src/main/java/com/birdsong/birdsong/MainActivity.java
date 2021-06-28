package com.birdsong.birdsong;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.birdsong.birdsong.ui.main.PlaylistActivity;

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

        ImageButton addButton = (ImageButton)findViewById(R.id.addButton);
        ImageButton playlistButton = (ImageButton)findViewById(R.id.playlistButton);

        addButton.setOnClickListener(v -> {
            //add to playlist data struct
            Toast.makeText(this, "Song added to playlist", Toast.LENGTH_SHORT).show();
        });

        playlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launch intent
                Intent playlistIntent = new Intent(view.getContext(), PlaylistActivity.class);
                view.getContext().startActivity(playlistIntent);
            }
        });


        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        ImageButton playButton = (ImageButton)findViewById(R.id.playButton);
        playButton.setImageResource(R.drawable.play_button_play_foreground);

        playButton.setOnClickListener(v -> {

            if (mediaPlayer == null) {
                return;
            }

            if (playing) {
                mediaPlayer.stop();
                //mediaPlayer.prepareAsync();
                playButton.setImageResource(R.drawable.play_button_play_foreground);
                playing = false;
            }

            else {
                if (!songSet) {

                    try {

                        mediaPlayer.setDataSource("https://ks.imslp.net/files/imglnks/usimg/7/74/IMSLP294242-PMLP04230-dso20120512-004-beethoven-piano-concerto-no1-mvtIII-rondo-allegro-scherzando.mp3");
                        mediaPlayer.prepareAsync();

                        mediaPlayer.setOnPreparedListener(player -> {
                            player.start();
                            playButton.setImageResource(R.drawable.play_button_pause_foreground);
                            songSet = true;
                            playing = true;
                        });

                    } catch (IOException e) {
                        Toast.makeText(this, "Error fetching mp3", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                //resume playing
                else {
                    mediaPlayer.start();
                    playButton.setImageResource(R.drawable.play_button_pause_foreground);
                    playing = true;
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