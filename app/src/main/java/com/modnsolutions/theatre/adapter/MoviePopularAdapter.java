package com.modnsolutions.theatre.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.AsyncPagedListDiffer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.modnsolutions.theatre.BuildConfig;
import com.modnsolutions.theatre.MovieDetailActivity;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.db.entity.movie.MovieNowPlayingEntity;
import com.modnsolutions.theatre.db.entity.movie.MoviePopularEntity;
import com.modnsolutions.theatre.fragment.MovieInfoFragment;

public class MoviePopularAdapter extends RecyclerView.Adapter<MoviePopularAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final AsyncPagedListDiffer<MoviePopularEntity> mDiffer = new AsyncPagedListDiffer(
            this, DIFF_CALLBACK);

    public MoviePopularAdapter(Context context) {
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
        MoviePopularEntity movie = mDiffer.getItem(position);

        Glide.with(mContext)
                .load(BuildConfig.IMAGE_BASE_URL + "/w154" +
                        movie.getPosterPath())
                .placeholder(new ColorDrawable(mContext.getResources().getColor(
                        R.color.colorPrimaryLight)))
                .fitCenter()
                .into(holder.moviePoster);
        holder.moviePoster.setContentDescription(movie.getTitle());

        String title;
        if (movie.getTitle().length() >= 20) {
            title = movie.getTitle().substring(0, 20) + "...";
        } else {
            title = movie.getTitle();
        }
        holder.movieTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return mDiffer.getItemCount();
    }

    public void submitList(PagedList<MoviePopularEntity> pagedList) {
        mDiffer.submitList(pagedList);
    }

    public static final DiffUtil.ItemCallback<MoviePopularEntity> DIFF_CALLBACK = new DiffUtil
            .ItemCallback<MoviePopularEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull MoviePopularEntity oldItem,
                                       @NonNull MoviePopularEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MoviePopularEntity oldItem,
                                          @NonNull MoviePopularEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView moviePoster;
        private TextView movieTitle;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.movie_poster);
            movieTitle = itemView.findViewById(R.id.movie_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoviePopularEntity movie = mDiffer.getItem(getAdapterPosition());
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra(MovieInfoFragment.MOVIE_ID_INTENT, movie.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
