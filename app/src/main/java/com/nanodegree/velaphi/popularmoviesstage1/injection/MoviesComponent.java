package com.nanodegree.velaphi.popularmoviesstage1.injection;

import com.nanodegree.velaphi.popularmoviesstage1.features.MainActivityViewModel;
import com.nanodegree.velaphi.popularmoviesstage1.features.movieDetails.MovieDetailsViewModel;

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
