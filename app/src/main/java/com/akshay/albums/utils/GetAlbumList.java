package com.akshay.albums.utils;


import com.akshay.albums.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetAlbumList {
    @GET("albums")
    Call<List<Album>> getAlbumdata();
}
