package com.nanodegree.velaphi.popularmovies.features;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nanodegree.velaphi.popularmovies.R;
import com.nanodegree.velaphi.popularmovies.features.movieDetails.MovieDetailActivity;
import com.nanodegree.velaphi.popularmovies.models.Movie;


import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{

    private List<Movie> movieList;
    private Context context;

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View movieItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item_layout, viewGroup, false);
        return new MovieViewHolder(movieItem, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        final Movie movie = movieList.get(position);
        movieViewHolder.movieNameTextView.setText(movie.getTitle());

        RequestOptions options = new RequestOptions()
                .error(R.drawable.movie)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(context.getString(R.string.image_base_url) + movie.getPosterPath())
                .apply(options)
                .into(movieViewHolder.movieImageView);

        movieViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("movie", movie);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (movieList != null)
            return movieList.size();
        else
            return 0;
    }

    public  void setItem(List<Movie>movieList){
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        Context context;
        ImageView movieImageView;
        TextView movieNameTextView;

        public MovieViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            movieImageView = itemView.findViewById(R.id.image_view_movie_poster);
            movieNameTextView = itemView.findViewById(R.id.textView_movie_title);
        }
    }
}
