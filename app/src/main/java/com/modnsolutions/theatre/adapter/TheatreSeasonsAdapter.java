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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.modnsolutions.theatre.BuildConfig;
import com.modnsolutions.theatre.EpisodeActivity;
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.db.entity.SeasonEntity;
import com.modnsolutions.theatre.fragment.TVShowSeasonsFragment;
import com.modnsolutions.theatre.fragment.TheatreTVShowDetailsEpisodeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.modnsolutions.theatre.fragment.TheatreTVShowDetailsEpisodeFragment.SEASON_ID_INTENT;
import static com.modnsolutions.theatre.fragment.TheatreTVShowDetailsEpisodeFragment.SEASON_NUMBER_INTENT;
import static com.modnsolutions.theatre.fragment.TheatreTVShowDetailsEpisodeFragment.THEATRE_ID_INTENT;

public class TheatreSeasonsAdapter extends RecyclerView.Adapter<TheatreSeasonsAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<SeasonEntity> mSeasons;

    public TheatreSeasonsAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recyclerview_tv_shows_seasons, parent,
                false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SeasonEntity season = mSeasons.get(position);

        Glide.with(mContext)
                .load(BuildConfig.IMAGE_BASE_URL + "/w154" +
                        season.getPosterPath())
                .placeholder(new ColorDrawable(mContext.getResources().getColor(
                        R.color.colorPrimaryLight)))
                .fitCenter()
                .into(holder.mTVSeasonPoster);
        holder.mTVSeasonPoster.setContentDescription(season.getName());

        holder.mTVSeasonName.setText(season.getName());
        holder.mTVSeasonOverview.setText(season.getOverview());
    }

    @Override
    public int getItemCount() {
        if (mSeasons == null) return 0;
        return mSeasons.size();
    }

    public void setSeasons(List<SeasonEntity> seasons) {
        if (mSeasons == null) mSeasons = seasons;
        else mSeasons.addAll(seasons);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mTVSeasonPoster;
        private TextView mTVSeasonName;
        private TextView mTVSeasonOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTVSeasonPoster = itemView.findViewById(R.id.tv_show_season_poster);
            mTVSeasonName = itemView.findViewById(R.id.tv_show_season_name);
            mTVSeasonOverview = itemView.findViewById(R.id.tv_show_season_overview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(mContext, EpisodeActivity.class);
                    intent.putExtra(SEASON_ID_INTENT, mSeasons.get(position).getId());
                    intent.putExtra(THEATRE_ID_INTENT, mSeasons.get(position).getTheatreId());
                    intent.putExtra(SEASON_NUMBER_INTENT, mSeasons.get(position).getSeasonNumber());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
