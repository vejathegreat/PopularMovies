package com.nanodegree.velaphi.popularmovies.features;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nanodegree.velaphi.popularmovies.injection.MoviesComponent;
import com.nanodegree.velaphi.popularmovies.models.Movie;
import com.nanodegree.velaphi.popularmovies.models.PopularMoviesResponse;
import com.nanodegree.velaphi.popularmovies.repository.PopularMoviesRepository;

import java.util.List;

import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel implements MoviesComponent.Injectable {

    @Inject
    PopularMoviesRepository popularMoviesRepository;

    @Override
    public void inject(MoviesComponent moviesComponent) {
        moviesComponent.inject(this);
    }

     final MutableLiveData<PopularMoviesResponse> popularMoviesResponseMutableLiveData = new MutableLiveData<>();
     LiveData<List<Movie>> favMovieListMutableLiveData = new MutableLiveData<>();
     final MutableLiveData<Throwable> errorStatus = new MutableLiveData<>();

    void retrievePopularMovies(){
        popularMoviesRepository.getPopularMovies(new PopularMoviesRepository.RepositoryCallback() {
            @Override
            public void onSuccess(PopularMoviesResponse response) {
                popularMoviesResponseMutableLiveData.setValue(response);
            }

            @Override
            public void onFailure(Throwable t) {
                errorStatus.setValue(t);
            }
        });
    }

    void retrieveTopRatedMovies(){
        popularMoviesRepository.getTopRatedMovies(new PopularMoviesRepository.RepositoryCallback() {
            @Override
            public void onSuccess(PopularMoviesResponse response) {
                popularMoviesResponseMutableLiveData.setValue(response);
            }

            @Override
            public void onFailure(Throwable t) {
                errorStatus.setValue(t);
            }
        });
    }

    void retrieveFavMovies(){
        favMovieListMutableLiveData =  popularMoviesRepository.getFavouriteMovies();
    }

}
