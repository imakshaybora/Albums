package com.akshay.albums.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshay.albums.R;
import com.akshay.albums.model.Album;

import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumViewolder> {

    private List<Album> dataList;

    public AlbumListAdapter(List<Album> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public AlbumViewolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.album_list_item, viewGroup, false);
        return new AlbumViewolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewolder forecastViewolder, int i) {
        forecastViewolder.txtAlbumTitle.setText(dataList.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class AlbumViewolder extends RecyclerView.ViewHolder {

        TextView txtAlbumTitle;

        AlbumViewolder(View itemView) {
            super(itemView);
            txtAlbumTitle = itemView.findViewById(R.id.album_name);
        }
    }

    public void addAlbums(List<Album> albumList) {
        this.dataList = albumList;
    }
}