package com.nanodegree.velaphi.popularmoviesstage1.features.movieDetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nanodegree.velaphi.popularmoviesstage1.injection.MoviesComponent;
import com.nanodegree.velaphi.popularmoviesstage1.models.Movie;
import com.nanodegree.velaphi.popularmoviesstage1.models.ReviewResponse;
import com.nanodegree.velaphi.popularmoviesstage1.models.TrailersResponse;
import com.nanodegree.velaphi.popularmoviesstage1.repository.MovieDetailsRepository;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MovieDetailsViewModel extends ViewModel implements MoviesComponent.Injectable {

    @Inject
    MovieDetailsRepository movieDetailsRepository;

    @Override
    public void inject(MoviesComponent moviesComponent) {
        moviesComponent.inject(this);
    }

    final MutableLiveData<TrailersResponse> trailersResponseMutableLiveData = new MutableLiveData<>();
    final MutableLiveData<ReviewResponse> reviewResponseResponseMutableLiveData = new MutableLiveData<>();
    LiveData<Movie> isFavourite = new MutableLiveData<>();

    final MutableLiveData<Throwable> reviewErrorStatus = new MutableLiveData<>();
    final MutableLiveData<Throwable> trailerErrorStatus = new MutableLiveData<>();

    void retrieveTrailers(Integer movieId) {
        movieDetailsRepository.getTrailers(movieId, new MovieDetailsRepository.TrailersRepositoryCallback() {
            @Override
            public void onSuccess(TrailersResponse trailersResponse) {
                trailersResponseMutableLiveData.setValue(trailersResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                trailerErrorStatus.setValue(t);
            }
        });
    }

    void retrieveReviews(Integer movieId) {
        movieDetailsRepository.getReviews(movieId, new MovieDetailsRepository.ReviewsRepositoryCallback() {
            @Override
            public void onSuccess(ReviewResponse reviewResponse) {
                reviewResponseResponseMutableLiveData.setValue(reviewResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                reviewErrorStatus.setValue(t);
            }
        });
    }

    private void addToFavoriteMovies(Movie movie){
        movieDetailsRepository.insertFavMovies(movie).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - deleted event");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("OnError - deleted event: ", e);
                    }
                });
    }


    private void removeFromFavoriteMovies(Integer movieId){
        movieDetailsRepository.removeFavMovies(movieId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - deleted event");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("OnError - deleted event: ", e);
                    }
                });
    }

    void retrieveIsFavorite(Integer movieId){
        isFavourite = movieDetailsRepository.isFavorite(movieId);
    }

    void toggleFab(Movie movie){
        if(isFavourite.getValue() != null){
            removeFromFavoriteMovies(movie.getId());
        }else{
            addToFavoriteMovies(movie);
        }
    }

}
