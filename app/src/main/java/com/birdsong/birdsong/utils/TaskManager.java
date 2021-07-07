package com.birdsong.birdsong.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.birdsong.birdsong.R;

import java.io.IOException;
import java.net.URL;

public class TaskManager {

    Context context;


    public TaskManager(Context context) {
        this.context = context;
    }

    private class GetThumbnailTask extends AsyncTask<URL, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(URL... url) {

            Bitmap albumArt;

            try {
                albumArt = BitmapFactory.decodeStream(url[0].openStream());
            }

            //invalid url
            catch (IOException ex) {

                albumArt = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sample_album_foreground);

            }

            return albumArt;
        }
    }
}
