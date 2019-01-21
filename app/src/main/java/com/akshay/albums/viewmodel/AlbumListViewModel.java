package com.akshay.albums.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.akshay.albums.db.AppDatabase;
import com.akshay.albums.model.Album;
import com.akshay.albums.utils.AlbumsFetcher;

import java.util.List;

public class AlbumListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Album>> albumList;
    private AppDatabase appDatabase;
    private AlbumsFetcher albumsFetcher;

    public AlbumListViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public LiveData<List<Album>> getAlbumList() {
        if (albumList == null) {
            albumList = new MutableLiveData<>();
            albumsFetcher = new AlbumsFetcher(appDatabase, albumList);
            albumsFetcher.getAlbums();
        }
        return albumList;
    }

}
