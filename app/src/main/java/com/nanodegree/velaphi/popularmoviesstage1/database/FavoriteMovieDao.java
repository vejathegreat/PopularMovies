package com.nanodegree.velaphi.popularmoviesstage1.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.nanodegree.velaphi.popularmoviesstage1.models.Movie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM  favorite_movies")
    LiveData<List<Movie>> getFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Query("delete from favorite_movies where id = :id")
    void removeMovie(Integer id);

    @Query("select * from favorite_movies where id = :id")
    LiveData<Movie> isMovieFavourite(Integer id);
}
