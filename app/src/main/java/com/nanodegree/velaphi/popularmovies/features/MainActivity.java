package com.nanodegree.velaphi.popularmovies.features;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.nanodegree.velaphi.popularmovies.MoviesApplication;
import com.nanodegree.velaphi.popularmovies.R;
import com.nanodegree.velaphi.popularmovies.injection.MoviesFactory;
import com.nanodegree.velaphi.popularmovies.models.Movie;

import java.util.List;


public class MainActivity extends AppCompatActivity{

    ProgressBar progressBar;
    MainActivityViewModel viewModel;
    TextView errorMessageTextView;
    TextView emptyFavoritesTextView;
    TextView sortCriteriaTextView;
    Button tryAgainButton;
    RecyclerView movieListRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MoviesAdapter moviesAdapter;
    Toolbar toolbar;
    MoviesApplication application;
    List<Movie> movies;
   Parcelable mListState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setToolBarTitle(getString(R.string.app_name));
        setupSpinner();
        setupView();
        setupViewModel();
        setRecyclerView();

    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        mListState = mLayoutManager.onSaveInstanceState();
        state.putParcelable("movies", mListState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        if(state != null)
            mListState = state.getParcelable("movies");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }
    private void setupView() {
        mLayoutManager = new GridLayoutManager(this, 2);
        progressBar = findViewById(R.id.progressBar_movies);
        errorMessageTextView = findViewById(R.id.error_textView);
        errorMessageTextView.setVisibility(View.GONE);
        tryAgainButton = findViewById(R.id.try_again_button);
        emptyFavoritesTextView = findViewById(R.id.errorFavTextView);
        sortCriteriaTextView = findViewById(R.id.criteria_selection_textview);

        tryAgainButton.setOnClickListener(v -> {
           finish();
           startActivity(getIntent());
        });

        tryAgainButton.setVisibility(View.GONE);
        movieListRecyclerView = findViewById(R.id.movie_list_recyclerView);
    }

    private void setupSpinner() {
        Spinner spinner = findViewById(R.id.spinner_sort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(!(selectedItem.equalsIgnoreCase(toolbar.getTitle().toString()))){
                    if(position != 0)
                        setToolBarTitle(parent.getItemAtPosition(position).toString());
                    if(selectedItem.equalsIgnoreCase(getString((R.string.favorites)))){
                         observeFavMovies();
                        viewModel.retrieveFavMovies();
                    }else if(selectedItem.equalsIgnoreCase(getString((R.string.popular)))){
                        viewModel.retrievePopularMovies();
                    }else if(selectedItem.equalsIgnoreCase(getString((R.string.rated)))){
                        viewModel.retrieveTopRatedMovies();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setToolBarTitle(String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

    }

    private void setupViewModel() {
        application = (MoviesApplication) this.getApplication();
        viewModel = ViewModelProviders.of(this, new MoviesFactory(application)).get(MainActivityViewModel.class);
        observeMovies();
        observeErrorMessage();
    }

    private void observeFavMovies() {
        viewModel.favMovieListMutableLiveData.observe(this, movies -> {
            if(movies != null && movies.size() >0){
                hidErrorMessage();
                this.movies = movies;
                moviesAdapter.setItem(movies);
            }else{
                this.movies.clear();
                moviesAdapter.setItem(movies);
                emptyFavoritesTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void observeErrorMessage() {
        viewModel.errorStatus.observe(this, throwable -> {
            if(throwable != null)
            {
                progressBar.setVisibility(View.GONE);
                errorMessageTextView.setVisibility(View.VISIBLE);
                tryAgainButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void observeMovies() {
        viewModel.popularMoviesResponseMutableLiveData.observe(this, popularMoviesResponse -> {
            if(popularMoviesResponse == null)
            {
                displayErrorMessage();
            }else{
                hidErrorMessage();
                movies = popularMoviesResponse.getMovieList();
                moviesAdapter.setItem(movies);
            }

        });
    }

    private void setRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        movieListRecyclerView.setLayoutManager(gridLayoutManager);
        moviesAdapter = new MoviesAdapter(this);
        moviesAdapter.setItem(movies);
        movieListRecyclerView.setAdapter(moviesAdapter);
    }


    private void displayErrorMessage() {
        progressBar.setVisibility(View.GONE);
        errorMessageTextView.setVisibility(View.VISIBLE);
        tryAgainButton.setVisibility(View.VISIBLE);
    }

    private void hidErrorMessage() {
        sortCriteriaTextView.setVisibility(View.GONE);
        emptyFavoritesTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorMessageTextView.setVisibility(View.GONE);
        tryAgainButton.setVisibility(View.GONE);
    }
}