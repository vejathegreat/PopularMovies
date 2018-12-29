package com.nanodegree.velaphi.popularmoviesstage1.repository;

import android.support.annotation.NonNull;

import com.nanodegree.velaphi.popularmoviesstage1.models.PopularMoviesResponse;

public interface PopularMoviesRepository {
    interface RepositoryCallback{
        void onSuccess(PopularMoviesResponse response);
        void onFailure(Throwable t);
    }

    void getTopRatedMovies(@NonNull RepositoryCallback repositoryCallback);
    void getPopularMovies(@NonNull  RepositoryCallback repositoryCallback);
}
