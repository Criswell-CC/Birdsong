package com.birdsong.birdsong.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.media.session.MediaControllerCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import com.birdsong.birdsong.R;
import com.birdsong.birdsong.ui.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    private MediaControllerCompat controller;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        NotificationCompat.Builder notificationBuilder;

/*       custom notifications triggered when app in foreground or when remote notification lacks
         a "notification" entry and only has a "data" key
         custom notification should, therefore, be set from data fields and not from notification fields*/
        if (remoteMessage.getData().get("type").equals("songshare")) {

            //fetch album art to be displayed in notification player
/*
                        Bitmap albumArt;

                        try {

                            URL url = new URL("https://img.youtube.com/vi/" + remoteMessage.getData().get("thumbnailUrl") + "/default.jpg");

                                run task for this
                            albumArt = BitmapFactory.decodeStream(url.openStream());

                        }

                        //set to default album art if exception
                        catch (IOException e) {

                            albumArt = BitmapFactory.decodeResource(getResources(), R.mipmap.sample_album_foreground);

                        }*/

            notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.songshare_channel_id) )
                    .setSmallIcon(R.drawable.ic_stat_bird)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("body"))
                    .addAction(R.drawable.play_button_play_foreground, "Play", null)
                    .addAction(R.drawable.add_button_foreground, "Add", null)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            //.setMediaSession(session)
                    );
        }

        else {

            notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.default_channel_id))
                    .setSmallIcon(R.drawable.ic_stat_bird)
                    .setContentTitle("Default")
                    .setContentText(remoteMessage.getData().get("body"));
        }

            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(1, notificationBuilder.build());
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        System.out.println("Token.........: " + token);

        //store token in shared prefs
        getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit().putString("fcm_token", token).apply();

        //TO-DO: store token in back-end to enable intra-device communication (i.e., sharing)
        //http request in async task

    }

    public static void sendMessageTest(FragmentActivity activity) {

        Intent activityIntent = new Intent(activity.getBaseContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(activity,
                0, activityIntent, 0);
        Bitmap picture = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.sample_album);
        Notification notification = new NotificationCompat.Builder(activity.getBaseContext(), activity.getString(R.string.default_channel_id))
                .setSmallIcon(R.drawable.ic_stat_bird)
                .setContentTitle("Test")
                .setContentText("Test message body")
                .setLargeIcon(picture)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(picture)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity.getBaseContext());
        notificationManager.notify(1, notification);
    }

}
