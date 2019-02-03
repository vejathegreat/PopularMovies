package com.nanodegree.velaphi.popularmoviesstage1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nanodegree.velaphi.popularmoviesstage1.models.Movie;

@Database(entities = {Movie.class}, version = 1,exportSchema = false)
public abstract class FavoriteMovieDatabase extends RoomDatabase {
    public abstract FavoriteMovieDao getFavoriteMovieDao();

}
