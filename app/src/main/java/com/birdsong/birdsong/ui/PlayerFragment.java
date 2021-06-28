package com.birdsong.birdsong.ui;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.birdsong.birdsong.ViewModel.MainViewModel;
import com.birdsong.birdsong.R;

import java.io.IOException;

public class PlayerFragment extends Fragment {

    private MainViewModel viewModel;

    private boolean isPlaying;
    private boolean songSet;

    TextView songTextView;

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

        // set play button image
        ImageButton playButton = (ImageButton)view.findViewById(R.id.playButton);
        playButton.setImageResource(R.drawable.play_button_play_foreground);

        ImageButton addButton = (ImageButton)view.findViewById(R.id.addButton);
        ImageButton playlistButton = (ImageButton)view.findViewById(R.id.playlistButton);

        // set click handlers
        addButton.setOnClickListener(v -> {
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

        playButton.setOnClickListener(v -> {
//
            if (mediaPlayer == null) {
                return;
            }

            if (isPlaying) {
                mediaPlayer.stop();
                //mediaPlayer.prepareAsync();
                playButton.setImageResource(R.drawable.play_button_play_foreground);
                viewModel.setIsPlaying(false);
            }

            else {
                if (!songSet) {

                    try {

                        mediaPlayer.setDataSource("https://ks.imslp.net/files/imglnks/usimg/7/74/IMSLP294242-PMLP04230-dso20120512-004-beethoven-piano-concerto-no1-mvtIII-rondo-allegro-scherzando.mp3");
                        mediaPlayer.prepareAsync();

                        mediaPlayer.setOnPreparedListener(player -> {
                            player.start();
                            playButton.setImageResource(R.drawable.play_button_pause_foreground);
                            viewModel.setSongSet(true);
                            viewModel.setIsPlaying(true);
                        });

                    } catch (IOException e) {
                        Toast.makeText(view.getContext(), "Error fetching mp3", Toast.LENGTH_SHORT).show();
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

        return view;
    }
}