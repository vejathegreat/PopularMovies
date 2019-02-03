package com.nanodegree.velaphi.popularmovies.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nanodegree.velaphi.popularmovies.database.FavoriteMovieDatabase;
import com.nanodegree.velaphi.popularmovies.models.Movie;
import com.nanodegree.velaphi.popularmovies.models.ReviewResponse;
import com.nanodegree.velaphi.popularmovies.models.TrailersResponse;
import com.nanodegree.velaphi.popularmovies.remote.AppClient;
import com.nanodegree.velaphi.popularmovies.remote.RequestInterface;

import javax.inject.Inject;

import io.reactivex.Completable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepositoryImpl implements MovieDetailsRepository {

    @Inject
    FavoriteMovieDatabase favoriteMovieDatabase;

    public MovieDetailsRepositoryImpl(FavoriteMovieDatabase favoriteMovieDatabase) {
        this.favoriteMovieDatabase = favoriteMovieDatabase;
    }


    @Override
    public void getTrailers(Integer movieID, @NonNull final TrailersRepositoryCallback repositoryCallback) {
        RequestInterface requestInterface = AppClient.getApi();

        requestInterface.getTrailers(movieID).enqueue(new Callback<TrailersResponse>() {
            @Override
            public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
                repositoryCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<TrailersResponse> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });

    }

    @Override
    public void getReviews(Integer movieID, @NonNull final ReviewsRepositoryCallback repositoryCallback) {
        RequestInterface requestInterface = AppClient.getApi();

        requestInterface.getReviews(movieID).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                repositoryCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });
    }

    @Override
    public Completable insertFavMovies(Movie movie) {
        return  Completable.fromAction(()->favoriteMovieDatabase.getFavoriteMovieDao().insertMovie(movie));
    }


    @Override
    public Completable removeFavMovies(Integer movieId) {
        return  Completable.fromAction(()->favoriteMovieDatabase.getFavoriteMovieDao().removeMovie(movieId));
    }

    @Override
    public LiveData<Movie> isFavorite(Integer movieId) {
        return  favoriteMovieDatabase.getFavoriteMovieDao().isMovieFavourite(movieId);
    }

}
