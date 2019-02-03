package com.nanodegree.velaphi.popularmovies.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nanodegree.velaphi.popularmovies.models.Movie;
import com.nanodegree.velaphi.popularmovies.models.ReviewResponse;
import com.nanodegree.velaphi.popularmovies.models.TrailersResponse;

import io.reactivex.Completable;

public interface MovieDetailsRepository {

    interface ReviewsRepositoryCallback{
        void onSuccess(ReviewResponse reviewResponse);
        void onFailure(Throwable t);
    }

    interface TrailersRepositoryCallback{
        void onSuccess(TrailersResponse trailersResponse);
        void onFailure(Throwable t);
    }

    void getTrailers(Integer movieID, @NonNull MovieDetailsRepository.TrailersRepositoryCallback repositoryCallback);

    void getReviews(Integer movieID, @NonNull MovieDetailsRepository.ReviewsRepositoryCallback repositoryCallback);

    Completable insertFavMovies(Movie favoriteMovie);
    Completable removeFavMovies(Integer movieId);
    LiveData<Movie> isFavorite(Integer movieId);
}
