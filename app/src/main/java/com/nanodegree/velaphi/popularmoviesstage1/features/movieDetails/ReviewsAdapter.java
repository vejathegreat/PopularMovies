package com.nanodegree.velaphi.popularmoviesstage1.features.movieDetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nanodegree.velaphi.popularmoviesstage1.R;
import com.nanodegree.velaphi.popularmoviesstage1.models.Review;

import java.util.List;

public class ReviewsAdapter  extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>{
    private List<Review> reviewsList;
    private Context context;
    private Boolean isClicked = false;

    public ReviewsAdapter(List<Review> reviewsList, Context context) {
        this.reviewsList = reviewsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View trailerItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item_layout, viewGroup, false);
        return new ReviewsAdapter.ReviewViewHolder(trailerItem,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewViewHolder trailerViewHolder, int position) {
        final Review review = reviewsList.get(position);
        trailerViewHolder.reviewerNameTextView.setText(review.getAuthor());
        trailerViewHolder.reviewContentTextView.setText(review.getContent());

        trailerViewHolder.seeMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked){
                    trailerViewHolder.reviewContentTextView.setMaxLines(10);
                    isClicked = false;
                    trailerViewHolder.seeMoreButton.setText(R.string.see_more);
                }else{
                    trailerViewHolder.reviewContentTextView.setMaxLines(Integer.MAX_VALUE);
                    isClicked = true;
                    trailerViewHolder.seeMoreButton.setText(R.string.see_less);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        Context context;
        TextView reviewerNameTextView;
        TextView reviewContentTextView;
        TextView seeMoreButton;

        ReviewViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            reviewContentTextView = itemView.findViewById(R.id.review_content_textView);
            reviewerNameTextView = itemView.findViewById(R.id.reviewer_textView);
            seeMoreButton = itemView.findViewById(R.id.see_more);

        }
    }
}
