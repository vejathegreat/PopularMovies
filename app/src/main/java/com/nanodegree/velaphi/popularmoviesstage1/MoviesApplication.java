package com.nanodegree.velaphi.popularmoviesstage1;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.nanodegree.velaphi.popularmoviesstage1.injection.DaggerMoviesComponent;
import com.nanodegree.velaphi.popularmoviesstage1.injection.MoviesComponent;
import com.nanodegree.velaphi.popularmoviesstage1.injection.MoviesModule;

public class MoviesApplication extends Application {

    private MoviesComponent moviesComponent = createMoviesComponent();

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
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