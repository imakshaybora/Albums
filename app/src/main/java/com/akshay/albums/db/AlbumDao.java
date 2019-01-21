package com.akshay.albums.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.akshay.albums.model.Album;

import java.util.List;


@Dao
public interface AlbumDao {
    @Query("select * from Album")
    List<Album> getAllAlbums();

    @Query("select * from Album where id = :id")
    Album getAlbumbyId(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAlbum(Album album);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAlbums(List<Album> albums);

    @Delete
    void deleteAlbum(Album album);
}
