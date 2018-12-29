package com.nanodegree.velaphi.popularmoviesstage1.features;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nanodegree.velaphi.popularmoviesstage1.R;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView backdropImageView;
    ImageView posterImageView;
    TextView releaseDateTextView;
    TextView averageVoteTextView;
    TextView plotTextView;
    Toolbar toolbar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        setUpView();

        Intent intent = getIntent();
        Bundle moviesBundle = intent.getExtras();

        if(moviesBundle != null)
        {
            setToolBar((String) moviesBundle.get(this.getString(R.string.movie_title_key)));

            populateInageViewView(backdropImageView,(String) moviesBundle.get(this.getString(R.string.backdrop_url_key)));
            populateInageViewView(posterImageView,(String) moviesBundle.get(this.getString(R.string.poster_url_key)));
            releaseDateTextView.setText((String) moviesBundle.get(this.getString(R.string.release_date_key)));
            averageVoteTextView.setText(moviesBundle.get(this.getString(R.string.rating_key)).toString());
            plotTextView.setText((String)moviesBundle.get(this.getString(R.string.plot_key)));

        }
    }

    private void setToolBar(String title) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void setUpView() {
        backdropImageView = findViewById(R.id.backdrop_imageView);
        posterImageView = findViewById(R.id.poster_imageView);
        releaseDateTextView = findViewById(R.id.release_date_textView);
        averageVoteTextView = findViewById(R.id.rating_textView);
        plotTextView = findViewById(R.id.plot_textView);
    }

    private void populateInageViewView(ImageView imageView, String url) {

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
