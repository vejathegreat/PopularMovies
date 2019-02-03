package com.nanodegree.velaphi.popularmovies.injection;

import com.nanodegree.velaphi.popularmovies.features.MainActivityViewModel;
import com.nanodegree.velaphi.popularmovies.features.movieDetails.MovieDetailsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MoviesModule.class})
public interface MoviesComponent {

    void inject(MainActivityViewModel mainActivityViewModel);
    void inject(MovieDetailsViewModel movieDetailsViewModel);

    interface Injectable {
        void inject(MoviesComponent moviesComponent);
    }
}
