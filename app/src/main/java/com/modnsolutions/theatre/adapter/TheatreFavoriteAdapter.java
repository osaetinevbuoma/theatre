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
import com.modnsolutions.theatre.TVShowsDetailsActivity;
import com.modnsolutions.theatre.TheatreMovieDetailsActivity;
import com.modnsolutions.theatre.TheatreTVShowDetailsActivity;
import com.modnsolutions.theatre.db.entity.TheatreEntity;
import com.modnsolutions.theatre.fragment.MovieInfoFragment;
import com.modnsolutions.theatre.fragment.TVShowInfoFragment;

import static com.modnsolutions.theatre.TheatreMovieDetailsActivity.NAVIGATION_FROM_FAVORITES;
import static com.modnsolutions.theatre.fragment.TheatreMovieDetailsFragment.THEATRE_MOVIE_ID_INTENT;
import static com.modnsolutions.theatre.fragment.TheatreTVShowDetailsFragment.THEATRE_TVSHOW_ID_INTENT;

public class TheatreFavoriteAdapter extends RecyclerView.Adapter<TheatreFavoriteAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final AsyncPagedListDiffer<TheatreEntity> mDiffer = new AsyncPagedListDiffer(
            this, DIFF_CALLBACK);
    private boolean isMovie;

    public TheatreFavoriteAdapter(Context context, boolean isMovie) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.isMovie = isMovie;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recyclerview_theatre, parent,
                false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheatreEntity entity = mDiffer.getItem(position);

        Glide.with(mContext)
                .load(BuildConfig.IMAGE_BASE_URL + "/w154" +
                        entity.getPosterPath())
                .placeholder(new ColorDrawable(mContext.getResources().getColor(
                        R.color.colorPrimaryLight)))
                .fitCenter()
                .into(holder.poster);
        holder.poster.setContentDescription(entity.getTitle());

        String title;
        if (entity.getTitle().length() >= 20) {
            title = entity.getTitle().substring(0, 20) + "...";
        } else {
            title = entity.getTitle();
        }
        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return mDiffer.getItemCount();
    }

    public void submitList(PagedList<TheatreEntity> pagedList) {
        mDiffer.submitList(pagedList);
    }

    public static final DiffUtil.ItemCallback<TheatreEntity> DIFF_CALLBACK = new DiffUtil
            .ItemCallback<TheatreEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TheatreEntity oldItem,
                                       @NonNull TheatreEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TheatreEntity oldItem,
                                          @NonNull TheatreEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView poster;
        private TextView title;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.theatre_poster);
            title = itemView.findViewById(R.id.theatre_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TheatreEntity entity = mDiffer.getItem(getAdapterPosition());

                    if (isMovie) {
                        Intent intent = new Intent(mContext, TheatreMovieDetailsActivity.class);
                        intent.putExtra(THEATRE_MOVIE_ID_INTENT, entity.getId());
                        intent.putExtra(NAVIGATION_FROM_FAVORITES, true);
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, TheatreTVShowDetailsActivity.class);
                        intent.putExtra(THEATRE_TVSHOW_ID_INTENT, entity.getId());
                        intent.putExtra(TheatreTVShowDetailsActivity.NAVIGATION_FROM_FAVORITES,
                                true);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
