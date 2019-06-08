package com.modnsolutions.theatre.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.modnsolutions.theatre.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<JSONObject> mReviews;

    public ReviewsAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recyclerview_reviews, parent,
                false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject review = mReviews.get(position);
            holder.reviewContent.setText(review.getString("content"));
            holder.reviewAuthor.setText(review.getString("author"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mReviews == null) return 0;
        return mReviews.size();
    }

    public void setReviews(List<JSONObject> reviews) {
        if (mReviews == null) mReviews = reviews;
        else mReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView reviewContent;
        private TextView reviewAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewContent = itemView.findViewById(R.id.review_content);
            reviewAuthor = itemView.findViewById(R.id.review_author);
        }
    }
}
