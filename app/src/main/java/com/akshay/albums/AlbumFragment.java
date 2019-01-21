package com.akshay.albums;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akshay.albums.adapter.AlbumListAdapter;
import com.akshay.albums.model.Album;
import com.akshay.albums.viewmodel.AlbumListViewModel;

import java.util.ArrayList;
import java.util.List;


public class AlbumFragment extends Fragment {
    private AlbumListAdapter albumListAdapter;
    private ImageView imageView;
    private TextView mNodataTextView;
    private static final float ROTATE_FROM = 0.0f;
    private static final float ROTATE_TO = 360.0f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_fragment,
                container, false);

        RecyclerView recyclerView = view.findViewById(R.id.albumlist);
        imageView = view.findViewById(R.id.ic_loader);
        mNodataTextView = view.findViewById(R.id.no_data_tv);
        albumListAdapter = new AlbumListAdapter(new ArrayList<Album>());
        startLoadingAnimation();
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) recyclerLayoutManager).getOrientation());
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(albumListAdapter);
        AlbumListViewModel albumListViewModel = ViewModelProviders.of(this).get(AlbumListViewModel.class);
        albumListViewModel.getAlbumList().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable List<Album> albumList) {
                if (albumList != null) {
                    albumListAdapter.addAlbums(albumList);
                    albumListAdapter.notifyDataSetChanged();
                    stopLoadingAnimation();
                    if (albumList.size() == 0) {
                        mNodataTextView.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "There is no data.. Please connect to " +
                                "network..", Toast.LENGTH_LONG).show();
                    }
                } else {
                    stopLoadingAnimation();
                    Toast.makeText(getActivity(), "OOPS issue at our end", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        return view;

    }

    private void startLoadingAnimation() {
        imageView.setVisibility(View.VISIBLE);
        RotateAnimation rotateAnimation = new RotateAnimation(ROTATE_FROM, ROTATE_TO, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(rotateAnimation);
    }

    private void stopLoadingAnimation() {
        imageView.clearAnimation();
        imageView.setVisibility(View.GONE);
    }

}
