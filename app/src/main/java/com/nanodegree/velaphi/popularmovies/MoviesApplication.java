package com.nanodegree.velaphi.popularmovies;

import android.app.Application;

import com.nanodegree.velaphi.popularmovies.injection.DaggerMoviesComponent;
import com.nanodegree.velaphi.popularmovies.injection.MoviesComponent;
import com.nanodegree.velaphi.popularmovies.injection.MoviesModule;

public class MoviesApplication extends Application {

    private MoviesComponent moviesComponent = createMoviesComponent();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected MoviesComponent createMoviesComponent() {
        return DaggerMoviesComponent.builder()
                .moviesModule(new MoviesModule(this))
                .build();
    }

    public MoviesComponent getMoviesComponent() {
        return moviesComponent;
    }
}