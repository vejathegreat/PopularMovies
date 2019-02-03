package com.nanodegree.velaphi.popularmoviesstage1.features.movieDetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nanodegree.velaphi.popularmoviesstage1.R;
import com.nanodegree.velaphi.popularmoviesstage1.models.Review;
import com.nanodegree.velaphi.popularmoviesstage1.models.Trailer;

import java.util.HashMap;
import java.util.List;

public class TrailersAdapter  extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder>{

    private List<Trailer> trailersList;
    private Context context;

    public TrailersAdapter(List<Trailer> trailersList, Context context) {
        this.trailersList = trailersList;
        this.context = context;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View trailerItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item_layout, viewGroup, false);
        return new TrailersAdapter.TrailerViewHolder(trailerItem,context);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int position) {
        final Trailer trailer =trailersList.get(position);
        trailerViewHolder.trailerNameTextView.setText(trailer.getName());
        String thumbnail_url = context.getResources().getString(R.string.thumbnail_url, trailer.getKey());

        RequestOptions options = new RequestOptions()
                .error(R.drawable.movie)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(thumbnail_url)
                .apply(options)
                .into(trailerViewHolder.movieThumbnailImageView);

        trailerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse("vnd.youtube:" + trailer.getKey()));
            context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder{

        Context context;
        ImageView movieThumbnailImageView;
        TextView trailerNameTextView;

        TrailerViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            movieThumbnailImageView = itemView.findViewById(R.id.movie_thumbnail);
            trailerNameTextView = itemView.findViewById(R.id.trailer_name);

        }
    }
}
