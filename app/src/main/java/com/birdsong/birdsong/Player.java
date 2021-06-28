package com.birdsong.birdsong;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Player {

    private MediaPlayer mediaPlayer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Player(MediaPlayer player) {
        mediaPlayer = player;
        initializePlayer();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initializePlayer() {

        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
        );

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    };

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
