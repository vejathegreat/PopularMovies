package com.nanodegree.velaphi.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nanodegree.velaphi.popularmovies.models.Movie;

@Database(entities = {Movie.class}, version = 1,exportSchema = false)
public abstract class FavoriteMovieDatabase extends RoomDatabase {
    public abstract FavoriteMovieDao getFavoriteMovieDao();

}
