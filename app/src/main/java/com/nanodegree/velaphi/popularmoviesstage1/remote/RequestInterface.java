package com.nanodegree.velaphi.popularmoviesstage1.remote;

import com.nanodegree.velaphi.popularmoviesstage1.models.PopularMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("movie/popular")
    Call<PopularMoviesResponse> getPopular();

    @GET("movie/top_rated")
    Call<PopularMoviesResponse>getTopRated();
}
