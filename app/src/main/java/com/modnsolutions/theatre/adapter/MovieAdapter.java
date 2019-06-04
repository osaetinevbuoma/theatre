package com.modnsolutions.theatre.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.modnsolutions.theatre.BuildConfig;
import com.modnsolutions.theatre.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<JSONObject> mMovies;

    public MovieAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recyclerview_movie, parent,
                false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject movie = mMovies.get(position);

            Glide.with(mContext)
                    .load(BuildConfig.IMAGE_BASE_URL + "/w154" +
                            movie.getString("poster_path"))
                    .placeholder(new ColorDrawable(mContext.getResources().getColor(
                            R.color.colorPrimaryLight)))
                    .fitCenter()
                    .into(holder.moviePoster);
            holder.moviePoster.setContentDescription(movie.getString("title"));

            String title;
            if (movie.getString("title").length() >= 20) {
                title = movie.getString("title").substring(0, 20) + "...";
            } else {
                title = movie.getString("title");
            }
            holder.movieTitle.setText(title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) return 0;
        else return mMovies.size();
    }

    public void setMovies(List<JSONObject> movies) {
        if (mMovies == null) mMovies = movies;
        else mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView moviePoster;
        private TextView movieTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.movie_poster);
            movieTitle = itemView.findViewById(R.id.movie_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Do something
                }
            });
        }
    }
}
