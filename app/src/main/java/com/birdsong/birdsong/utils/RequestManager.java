package com.birdsong.birdsong.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

import com.birdsong.birdsong.models.Playlist;
import com.birdsong.birdsong.models.PlaylistItem;

public class RequestManager {

    private String server_url;

    public RequestManager() {

    }

    public Playlist fetchPlaylist() {

        Playlist playlist = new Playlist();
        return playlist;

    }

//    public PlaylistItem fetchSong() {

        //https get request

//        PlaylistItem song = new PlaylistItem();
//        song.setUrl("");
//
//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//
//        byte [] data = mediaMetadataRetriever.getEmbeddedPicture();
//
//        // convert the byte array to a bitmap
//        if(data != null)
//        {
//            //Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//            //song.setAlbumArt(bitmap);
//        }
//        else
//        {
//
//        }
//
//        coverart.setAdjustViewBounds(true);
//        coverart.setLayoutParams(new LinearLayout.LayoutParams(500, 500));

//    }

}
