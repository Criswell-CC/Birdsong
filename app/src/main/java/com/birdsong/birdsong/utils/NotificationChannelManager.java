package com.birdsong.birdsong.utils;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.birdsong.birdsong.R;

public class NotificationChannelManager {

    private Context context;

    public NotificationChannelManager(Context context) {
        this.context = context;
    }

    public void createNotificationChannels() {
        createDefaultNotificationChannel();
    }

    private void createDefaultNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //name is what user sees in app settings
            CharSequence name = context.getString(R.string.default_channel_name);
            String description = context.getString(R.string.default_channel_description);

            int importance = NotificationManager.IMPORTANCE_MAX;
            @SuppressLint("WrongConstant") NotificationChannel default_channel = new NotificationChannel(context.getString(R.string.default_channel_id), name, importance);
            default_channel.setDescription(description);

            name = context.getString(R.string.songshare_channel_name);
            description = context.getString(R.string.songshare_channel_description);

            @SuppressLint("WrongConstant") NotificationChannel songshare_channel = new NotificationChannel(context.getString(R.string.songshare_channel_id), name, importance);
            songshare_channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(default_channel);
            notificationManager.createNotificationChannel(songshare_channel);

        }
    }

    //song share channel

}
