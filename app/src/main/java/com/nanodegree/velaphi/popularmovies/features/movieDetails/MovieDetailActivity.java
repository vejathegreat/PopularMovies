package com.nanodegree.velaphi.popularmovies.features.movieDetails;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nanodegree.velaphi.popularmovies.MoviesApplication;
import com.nanodegree.velaphi.popularmovies.R;
import com.nanodegree.velaphi.popularmovies.injection.MoviesFactory;
import com.nanodegree.velaphi.popularmovies.models.Movie;
import com.nanodegree.velaphi.popularmovies.models.Review;
import com.nanodegree.velaphi.popularmovies.models.Trailer;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView backdropImageView;
    ImageView posterImageView;
    TextView releaseDateTextView;
    TextView averageVoteTextView;
    TextView plotTextView;
    RecyclerView trailersRecyclerView;
    RecyclerView reviewsRecyclerView;
    MovieDetailsViewModel movieDetailsViewModel;
    TextView reviewsHeaderTextView;
    TextView trailersHeaderTextView;
    Movie movie;
    List<Review>reviews;
    List<Trailer>trailers;
    FloatingActionButton favoriteFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        setUpView();

        Intent i = getIntent();
        movie = Objects.requireNonNull(i.getExtras()).getParcelable("movie");

        if(movie !=  null){
            setToolbar(movie.getTitle());
            populateMovieDetails();
            setupViewModel();
            setupFavorites();
            movieDetailsViewModel.retrieveReviews(movie.getId());
            movieDetailsViewModel.retrieveTrailers(movie.getId());
        }
    }



    private void setupFavorites() {
        MoviesApplication application = (MoviesApplication) this.getApplication();
        movieDetailsViewModel = ViewModelProviders.of(this, new MoviesFactory(application)).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.retrieveIsFavorite(movie.getId());
        favoriteFab.setOnClickListener(v -> movieDetailsViewModel.toggleFab(movie));
        movieDetailsViewModel.isFavourite.observe(this, movie -> {
        if(movie != null){
          updateFab(true);
        }else{
            updateFab(false);
        }
        });
    }

    private void populateMovieDetails() {
            populateImageView(backdropImageView, movie.getBackdropPath());
            populateImageView(posterImageView,movie.getPosterPath());
            releaseDateTextView.setText(movie.getReleaseDate());
            averageVoteTextView.setText(String.format(Locale.US,"%.0f / 10", movie.getVoteAverage()));
            plotTextView.setText(movie.getOverview());
    }

    private void setupViewModel() {
        MoviesApplication application = (MoviesApplication) this.getApplication();
        movieDetailsViewModel = ViewModelProviders.of(this, new MoviesFactory(application)).get(MovieDetailsViewModel.class);
        observeReviews();
        observeTrailers();
    }


    private void updateFab(Boolean isFavorite) {
        if(isFavorite){
            favoriteFab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_filled));
        }else{
            favoriteFab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
        }

    }

    private void observeTrailers() {
        movieDetailsViewModel.trailersResponseMutableLiveData.observe(this, trailersResponse -> {
            if(trailersResponse != null) {
                if(!trailersResponse.getTrailers().isEmpty()) {
                    trailers = trailersResponse.getTrailers();
                    displayTrailers(trailers);
                }
            }
        });
    }

    private void observeReviews() {
        movieDetailsViewModel.reviewResponseResponseMutableLiveData.observe(this, reviewResponse -> {
            if(reviewResponse != null)
                if(!reviewResponse.getReviews().isEmpty()){
                    reviews = reviewResponse.getReviews();
                    displayReview(reviews);
                }
            });
    }

    private void displayTrailers(List<Trailer> trailerList) {
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TrailersAdapter trailersAdapter = new TrailersAdapter(trailerList, this);
        trailersRecyclerView.setHasFixedSize(true);
        trailersRecyclerView.setAdapter(trailersAdapter);
        trailersHeaderTextView.setVisibility(View.VISIBLE);
        trailersRecyclerView.setVisibility(View.VISIBLE);
    }

    private void displayReview(List<Review> reviewList) {
        LinearLayoutManager layoutManager =  new LinearLayoutManager(this);
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviewList, this);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setAdapter(reviewsAdapter);
        reviewsHeaderTextView.setVisibility(View.VISIBLE);
        reviewsRecyclerView.setVisibility(View.VISIBLE);
    }

    private void setToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpView() {
        backdropImageView = findViewById(R.id.backdrop_imageView);
        posterImageView = findViewById(R.id.poster_imageView);
        releaseDateTextView = findViewById(R.id.release_date_textView);
        averageVoteTextView = findViewById(R.id.rating_textView);
        plotTextView = findViewById(R.id.plot_synopsis_textView);
        reviewsRecyclerView = findViewById(R.id.reviews_recyclerview);
        trailersRecyclerView = findViewById(R.id.trailers_recyclerview);
        reviewsHeaderTextView = findViewById(R.id.review_header_textView);
        trailersHeaderTextView = findViewById(R.id.trailer_header_textview);
        favoriteFab = findViewById(R.id.favorite);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void populateImageView(ImageView imageView, String url) {

        RequestOptions options = new RequestOptions()
                .error(R.drawable.movie)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(this)
                .load(this.getString(R.string.image_base_url) + url)
                .apply(options)
                .into(imageView);
    }
}
