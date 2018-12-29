package com.nanodegree.velaphi.popularmoviesstage1.features;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.nanodegree.velaphi.popularmoviesstage1.R;
import com.nanodegree.velaphi.popularmoviesstage1.models.PopularMoviesResponse;

public class MainActivity extends AppCompatActivity{

    ProgressBar progressBar;
    MainActivityViewModel viewModel;
    TextView errorMessageTextView;
    Button tryAgainButton;
    RecyclerView movieListRecyclerView;
    MoviesAdapter moviesAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setToolBarTitle(getString(R.string.popular));
        setupSpinner();
        setupView();
        setupViewModel();
        viewModel.retrievePopularMovies();
    }

    private void setupView() {
        progressBar = findViewById(R.id.progressBar_movies);
        errorMessageTextView = findViewById(R.id.error_textView);
        errorMessageTextView.setVisibility(View.GONE);
        tryAgainButton = findViewById(R.id.try_again_button);

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
               startActivity(getIntent());
            }
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
                    setToolBarTitle(parent.getItemAtPosition(position).toString());
                    if(selectedItem.equalsIgnoreCase(getString((R.string.popular)))){
                        viewModel.retrievePopularMovies();
                    }else{
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
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        observeMovies();
        observeErrorMessage();
    }

    private void observeErrorMessage() {
        viewModel.errorStatus.observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                if(throwable != null)
                {
                    progressBar.setVisibility(View.GONE);
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    tryAgainButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void observeMovies() {
        viewModel.popularMoviesResponseMutableLiveData.observe(this, new Observer<PopularMoviesResponse>() {
            @Override
            public void onChanged(@Nullable PopularMoviesResponse popularMoviesResponse) {
                if(popularMoviesResponse == null)
                {
                    displayErrorMessage();
                }else{
                    hidErrorMessage();
                    setRecyclerView(popularMoviesResponse);
                }

            }
        });
    }

    private void setRecyclerView(PopularMoviesResponse popularMoviesResponse) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        movieListRecyclerView.setLayoutManager(gridLayoutManager);
        moviesAdapter = new MoviesAdapter(popularMoviesResponse.getMovieList(), this);
        movieListRecyclerView.setAdapter(moviesAdapter);
    }


    private void displayErrorMessage() {
        progressBar.setVisibility(View.GONE);
        errorMessageTextView.setVisibility(View.VISIBLE);
        tryAgainButton.setVisibility(View.VISIBLE);
    }

    private void hidErrorMessage() {
        progressBar.setVisibility(View.GONE);
        errorMessageTextView.setVisibility(View.GONE);
        tryAgainButton.setVisibility(View.GONE);
    }
}