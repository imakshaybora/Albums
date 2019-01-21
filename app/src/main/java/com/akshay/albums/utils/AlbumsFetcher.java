package com.akshay.albums.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import com.akshay.albums.db.AppDatabase;
import com.akshay.albums.model.Album;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class AlbumsFetcher {

    private AppDatabase appDatabase;
    private MutableLiveData<List<Album>> albumList;
    private static final String GET_ALL_ALBUMS = "GET_ALL_ALBUMS";
    private static final String SET_ALL_ALBUMS = "SET_ALL_ALBUMS";

    public AlbumsFetcher(AppDatabase appDatabase, LiveData<List<Album>> listMutableLiveData) {
        this.appDatabase = appDatabase;
        albumList = (MutableLiveData<List<Album>>) listMutableLiveData;
    }

    public void getAlbums() {
        GetAlbumList albumListService = RetrofitInstance.getRetrofitInstance().create
                (GetAlbumList.class);
        Call<List<Album>> call = albumListService.getAlbumdata();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()) {
                    List<Album> albumListResponse = response.body();
                    Collections.sort(albumListResponse);
                    albumList.setValue(albumListResponse);
                    new AlbumFetchAsyncTask(albumListResponse).execute(SET_ALL_ALBUMS);
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
//                albumList.setValue(null);
                new AlbumFetchAsyncTask(null).execute(GET_ALL_ALBUMS);
            }
        });
    }

    private class AlbumFetchAsyncTask extends AsyncTask<String, Void, Void> {

        private List<Album> tempList;

        public AlbumFetchAsyncTask(List<Album> albumList) {
            tempList = albumList;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String operation = strings[0];
            if (operation.equals(GET_ALL_ALBUMS)) {
                List<Album> dbAlbumList = appDatabase.albumListModel().getAllAlbums();
                Collections.sort(dbAlbumList);
                albumList.postValue(dbAlbumList);
            } else if (operation.equals(SET_ALL_ALBUMS)) {
                appDatabase.albumListModel().insertAllAlbums(tempList);
            }
            return null;
        }
    }
}
