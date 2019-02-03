package com.nanodegree.velaphi.popularmoviesstage1.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.nanodegree.velaphi.popularmoviesstage1.MoviesApplication;

public class MoviesFactory extends ViewModelProvider.NewInstanceFactory{

    private MoviesApplication application;

    public MoviesFactory(MoviesApplication application) {
        this.application = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if (t instanceof MoviesComponent.Injectable) {
            ((MoviesComponent.Injectable) t).inject(application.getMoviesComponent());
        }
        return t;
    }
}