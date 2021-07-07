package com.birdsong.birdsong.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.birdsong.birdsong.ViewModel.MainViewModel;
import com.birdsong.birdsong.R;
import com.birdsong.birdsong.services.MessagingService;
//import com.birdsong.birdsong.services.MessagingService;

import java.io.IOException;
import java.net.URL;

public class PlayerFragment extends Fragment {

    private MainViewModel viewModel;

    private boolean isPlaying;
    private boolean songSet;

    TextView songTextView;
    TextView artistTextView;


    public PlayerFragment() {

    }

    public static PlayerFragment newInstance(String param1, String param2) {
        PlayerFragment fragment = new PlayerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.player_fragment, container, false);
        initializeUI(view);
        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initializeUI(View view) {

        //click listener for album art if needed
//        ImageView albumArt = (ImageView)view.findViewById(R.id.albumArt);
//        albumArt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        // set play button image
        ImageButton playButton = (ImageButton)view.findViewById(R.id.playButton);
        playButton.setImageResource(R.drawable.play_button_play_foreground);

        ImageButton addButton = (ImageButton)view.findViewById(R.id.addButton);
        ImageButton playlistButton = (ImageButton)view.findViewById(R.id.playlistButton);

        // set click handlers
        addButton.setOnClickListener(v -> {

            MessagingService.sendMessageTest(getActivity());

            //add to playlist data struct
            Toast.makeText(getActivity().getBaseContext(), "Song added to playlist", Toast.LENGTH_SHORT).show();
        });

        playlistButton.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.playlistFragment);

        });

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        MediaPlayer mediaPlayer = viewModel.getMediaPlayer();

        songTextView = (TextView)view.findViewById(R.id.songTitle);

        final Observer<String> songTitleObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newSongTitle) {
                // Update the UI, in this case, a TextView.
                songTextView.setText(newSongTitle);
            }
        };

        viewModel.getSongTitle().observe(getViewLifecycleOwner(), songTitleObserver);
        viewModel.getIsPlaying().observe(getViewLifecycleOwner(), isPlaying -> {
            this.isPlaying = isPlaying.booleanValue();
        });
        viewModel.getSongSet().observe(getViewLifecycleOwner(), songSet -> {
            this.songSet = songSet.booleanValue();
        });

        initializePlayButton(playButton, mediaPlayer);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initializePlayButton(ImageButton playButton, MediaPlayer mediaPlayer) {
        playButton.setOnClickListener(v -> {

            if (mediaPlayer == null) {
                return;
            }

            if (isPlaying) {
                mediaPlayer.stop();
                //needed?
                //mediaPlayer.prepareAsync();
                playButton.setImageResource(R.drawable.play_button_play_foreground);
                viewModel.setIsPlaying(false);
            }

            else {
                if (!songSet) {

                    try {

                        //hardcoded test url
                        mediaPlayer.setDataSource(getResources().getString(R.string.song_src));
                        mediaPlayer.prepareAsync();

                        mediaPlayer.setOnPreparedListener(player -> {
                            player.start();
                            playButton.setImageResource(R.drawable.play_button_pause_foreground);
                            viewModel.setSongSet(true);
                            viewModel.setIsPlaying(true);
                        });

                    } catch (IOException e) {
                        Toast.makeText(v.getContext(), "Error fetching mp3", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                //resume playing
                else {
                    mediaPlayer.start();
                    playButton.setImageResource(R.drawable.play_button_pause_foreground);
                    viewModel.setIsPlaying(true);
                }
            }
        });
    }
}