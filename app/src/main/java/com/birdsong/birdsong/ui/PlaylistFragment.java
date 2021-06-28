package com.birdsong.birdsong.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdsong.birdsong.R;

public class PlaylistFragment extends Fragment {

    public PlaylistFragment() {

    }

    public static PlaylistFragment newInstance(String param1, String param2) {
        PlaylistFragment fragment = new PlaylistFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.playlist_fragment, container, false);



        //PlaylistAdapter adapter = new PlaylistAdapter(playlist);
        //RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.playlist);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //recyclerView.setAdapter(adapter);

        return view;
    }
}