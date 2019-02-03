package com.nanodegree.velaphi.popularmoviesstage1.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nanodegree.velaphi.popularmoviesstage1.database.FavoriteMovieDatabase;
import com.nanodegree.velaphi.popularmoviesstage1.models.Movie;
import com.nanodegree.velaphi.popularmoviesstage1.models.PopularMoviesResponse;
import com.nanodegree.velaphi.popularmoviesstage1.remote.AppClient;
import com.nanodegree.velaphi.popularmoviesstage1.remote.RequestInterface;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesRepositoryIml implements PopularMoviesRepository {

    @Inject
    FavoriteMovieDatabase favoriteMovieDatabase;

    public PopularMoviesRepositoryIml(FavoriteMovieDatabase favoriteMovieDatabase) {
        this.favoriteMovieDatabase = favoriteMovieDatabase;
    }

    @Override
    public void getTopRatedMovies(@NonNull final RepositoryCallback repositoryCallback) {
        RequestInterface requestInterface = AppClient.getApi();

        requestInterface.getTopRated().enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                repositoryCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });
    }

    @Override
    public void getPopularMovies(@NonNull final RepositoryCallback repositoryCallback) {
        RequestInterface requestInterface = AppClient.getApi();

        requestInterface.getPopular().enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                repositoryCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });
    }


    @Override
    public LiveData<List<Movie>>getFavouriteMovies() {
        return favoriteMovieDatabase.getFavoriteMovieDao().getFavoriteMovies();
    }
}
