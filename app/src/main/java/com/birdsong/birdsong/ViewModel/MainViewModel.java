package com.birdsong.birdsong.ViewModel;

import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.birdsong.birdsong.Player;
import com.birdsong.birdsong.models.Playlist;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainViewModel extends ViewModel {

    Player mediaPlayer;

    public final MutableLiveData<String> songTitle = new MutableLiveData<String>();
    public final MutableLiveData<Boolean> songSet = new MutableLiveData<Boolean>(false);
    public final MutableLiveData<Boolean> isPlaying = new MutableLiveData<Boolean>(false);
    public final MutableLiveData<Playlist> playlist = new MutableLiveData<Playlist>();

    public LiveData<String> getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String title) {
        this.songTitle.setValue(title);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer.getMediaPlayer();
    }

    public void setMediaPlayer(MediaPlayer player) {
        if (player == null) {
            mediaPlayer = new Player(player);
        }
    }

    public void setSongSet(Boolean bool) {
        this.songSet.setValue(bool);
    }

    public LiveData<Boolean> getSongSet() {
        return songSet;
    }

    public void setIsPlaying(boolean bool) {
        this.isPlaying.setValue(new Boolean(bool));
    }

    public LiveData<Boolean> getIsPlaying() {
        return isPlaying;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist.setValue(playlist);
    }

    public LiveData<Playlist> getPlaylist() {
        return playlist;
    }

    @Override
    public void onCleared() {
        super.onCleared();
    }

}
