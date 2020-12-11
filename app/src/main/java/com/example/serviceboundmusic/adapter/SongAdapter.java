package com.example.serviceboundmusic.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serviceboundmusic.R;
import com.example.serviceboundmusic.model.Song;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHoler> {

    private List<Song> songList;
    private Activity activity;

    public SongAdapter(List<Song> songList, Activity activity) {
        this.songList = songList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SongViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        return new SongViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHoler holder, int position) {
        Song song = songList.get(position);
        holder.textView.setText(song.getTen());
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class SongViewHoler extends RecyclerView.ViewHolder  {

        private TextView textView;
        public SongViewHoler(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.songtv);
        }
    }
}
