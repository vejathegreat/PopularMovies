package com.nanodegree.velaphi.popularmoviesstage1.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nanodegree.velaphi.popularmoviesstage1.models.Movie;
import com.nanodegree.velaphi.popularmoviesstage1.models.PopularMoviesResponse;

import java.util.List;


public interface PopularMoviesRepository {
    interface RepositoryCallback{
        void onSuccess(PopularMoviesResponse response);
        void onFailure(Throwable t);
    }

    void getTopRatedMovies(@NonNull RepositoryCallback repositoryCallback);
    void getPopularMovies(@NonNull  RepositoryCallback repositoryCallback);

    LiveData<List<Movie>> getFavouriteMovies();
}
