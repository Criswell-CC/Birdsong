
package com.birdsong.birdsong.ui;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.birdsong.birdsong.utils.RequestManager;
import com.birdsong.birdsong.R;
import com.birdsong.birdsong.ViewModel.MainViewModel;
import com.birdsong.birdsong.utils.NotificationChannelManager;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private MediaPlayer mediaPlayer;
    private MediaSessionCompat mediaSession;

    private NotificationChannelManager notificationChannelManager = new NotificationChannelManager(this);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mediaSession = new MediaSessionCompat(this, "mainSession");

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.setMediaPlayer(this.mediaPlayer);

        viewModel.setPlaylist(new RequestManager().fetchPlaylist());

        notificationChannelManager.createNotificationChannels();
        
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
        //cancel any async tasks
//        if (runningTask != null)
//            runningTask.cancel(true);
    }
}