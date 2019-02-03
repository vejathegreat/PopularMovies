package com.nanodegree.velaphi.popularmoviesstage1.remote;

import com.nanodegree.velaphi.popularmoviesstage1.models.PopularMoviesResponse;
import com.nanodegree.velaphi.popularmoviesstage1.models.ReviewResponse;
import com.nanodegree.velaphi.popularmoviesstage1.models.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestInterface {

    @GET("movie/popular")
    Call<PopularMoviesResponse> getPopular();

    @GET("movie/top_rated")
    Call<PopularMoviesResponse>getTopRated();

    @GET("movie/{movie_id}/videos")
    Call<TrailersResponse> getTrailers(@Path("movie_id") Integer id);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getReviews(@Path("movie_id") Integer id);
}
