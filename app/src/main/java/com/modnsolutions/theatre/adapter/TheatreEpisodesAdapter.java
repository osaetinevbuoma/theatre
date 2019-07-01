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
import com.modnsolutions.theatre.db.entity.EpisodeEntity;
import com.modnsolutions.theatre.utils.Utilities;

import java.text.ParseException;
import java.util.List;

public class TheatreEpisodesAdapter extends RecyclerView.Adapter<TheatreEpisodesAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<EpisodeEntity> mEpisodes;

    public TheatreEpisodesAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recyclerview_episodes, parent,
                false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            EpisodeEntity episode = mEpisodes.get(position);

            Glide.with(mContext)
                    .load(BuildConfig.IMAGE_BASE_URL + "/w500" +
                            episode.getStillPath())
                    .placeholder(new ColorDrawable(mContext.getResources().getColor(
                            R.color.colorPrimaryLight)))
                    .centerCrop()
                    .into(holder.episodeStillImage);
            holder.episodeStillImage.setContentDescription(episode.getName());

            holder.episodeNumber.setText(String.valueOf(episode.getEpisodeNumber()));
            holder.episodeAirDate.setText(Utilities.formatDate(episode.getAirDate()));
            holder.episodeName.setText(episode.getName());
            holder.episodeOverview.setText(episode.getOverview());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mEpisodes == null) return 0;
        return mEpisodes.size();
    }

    public void setEpisodes(List<EpisodeEntity> episodes) {
        if (mEpisodes == null) mEpisodes = episodes;
        else mEpisodes.addAll(episodes);
        notifyDataSetChanged();
    }

    public void removeAll() {
        if (mEpisodes != null) {
            mEpisodes.clear();
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView episodeStillImage;
        private TextView episodeNumber;
        private TextView episodeAirDate;
        private TextView episodeName;
        private TextView episodeOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            episodeStillImage = itemView.findViewById(R.id.episode_still_image);
            episodeNumber = itemView.findViewById(R.id.episode_number);
            episodeAirDate = itemView.findViewById(R.id.episode_air_date);
            episodeName = itemView.findViewById(R.id.episode_name);
            episodeOverview = itemView.findViewById(R.id.episode_overview);
        }
    }
}
