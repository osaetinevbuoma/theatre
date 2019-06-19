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
import com.modnsolutions.theatre.db.entity.tvshow.TVShowPopularEntity;
import com.modnsolutions.theatre.fragment.MovieInfoFragment;
import com.modnsolutions.theatre.fragment.TVShowInfoFragment;

public class TVShowPopularAdapter extends RecyclerView.Adapter<TVShowPopularAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final AsyncPagedListDiffer<TVShowPopularEntity> mDiffer = new AsyncPagedListDiffer(
            this, DIFF_CALLBACK);

    public TVShowPopularAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recyclerview_tv_shows, parent,
                false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TVShowPopularEntity tvShow = mDiffer.getItem(position);

        Glide.with(mContext)
                .load(BuildConfig.IMAGE_BASE_URL + "/w154" +
                        tvShow.getPosterPath())
                .placeholder(new ColorDrawable(mContext.getResources().getColor(
                        R.color.colorPrimaryLight)))
                .fitCenter()
                .into(holder.tvShowPoster);
        holder.tvShowPoster.setContentDescription(tvShow.getName());

        String name;
        if (tvShow.getName().length() >= 20) {
            name = tvShow.getName().substring(0, 20) + "...";
        } else {
            name = tvShow.getName();
        }
        holder.tvShowOriginalName.setText(name);
    }

    @Override
    public int getItemCount() {
        return mDiffer.getItemCount();
    }

    public void submitList(PagedList<TVShowPopularEntity> pagedList) {
        mDiffer.submitList(pagedList);
    }

    public static final DiffUtil.ItemCallback<TVShowPopularEntity> DIFF_CALLBACK = new DiffUtil
            .ItemCallback<TVShowPopularEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TVShowPopularEntity oldItem,
                                       @NonNull TVShowPopularEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TVShowPopularEntity oldItem,
                                          @NonNull TVShowPopularEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView tvShowPoster;
        private TextView tvShowOriginalName;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvShowPoster = itemView.findViewById(R.id.tv_show_poster);
            tvShowOriginalName = itemView.findViewById(R.id.tv_show_original_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TVShowPopularEntity tvShow = mDiffer.getItem(getAdapterPosition());
                    Intent intent = new Intent(mContext, TVShowsDetailsActivity.class);
                    intent.putExtra(TVShowInfoFragment.TV_SHOW_EXTRA, tvShow.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
