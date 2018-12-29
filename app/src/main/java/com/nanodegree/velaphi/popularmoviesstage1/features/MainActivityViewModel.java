package com.nanodegree.velaphi.popularmoviesstage1.features;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nanodegree.velaphi.popularmoviesstage1.models.PopularMoviesResponse;
import com.nanodegree.velaphi.popularmoviesstage1.repository.PopularMoviesRepository;
import com.nanodegree.velaphi.popularmoviesstage1.repository.PopularMoviesRepositoryIml;

public class MainActivityViewModel extends ViewModel {

    private PopularMoviesRepository popularMoviesRepository;

     final MutableLiveData<PopularMoviesResponse> popularMoviesResponseMutableLiveData = new MutableLiveData<>();
     final MutableLiveData<Throwable> errorStatus = new MutableLiveData<>();

    void retrievePopularMovies(){

        popularMoviesRepository = new PopularMoviesRepositoryIml();

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

        popularMoviesRepository = new PopularMoviesRepositoryIml();

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

}
