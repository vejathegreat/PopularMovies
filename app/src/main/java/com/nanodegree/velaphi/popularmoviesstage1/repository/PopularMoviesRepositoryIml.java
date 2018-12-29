package com.nanodegree.velaphi.popularmoviesstage1.repository;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.nanodegree.velaphi.popularmoviesstage1.models.PopularMoviesResponse;
import com.nanodegree.velaphi.popularmoviesstage1.remote.AppClient;
import com.nanodegree.velaphi.popularmoviesstage1.remote.RequestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesRepositoryIml implements PopularMoviesRepository {

    @Override
    public void getTopRatedMovies(@NonNull final RepositoryCallback repositoryCallback) {
        RequestInterface requestInterface = AppClient.getApi();

        final MutableLiveData<PopularMoviesResponse> data = new MutableLiveData<>();

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
        final MutableLiveData<PopularMoviesResponse> data = new MutableLiveData<>();

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
}
