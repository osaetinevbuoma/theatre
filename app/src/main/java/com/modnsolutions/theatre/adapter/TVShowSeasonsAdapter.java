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
import com.modnsolutions.theatre.R;
import com.modnsolutions.theatre.TVShowEpisodesActivity;
import com.modnsolutions.theatre.fragment.TVShowInfoFragment;
import com.modnsolutions.theatre.fragment.TVShowSeasonsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TVShowSeasonsAdapter extends RecyclerView.Adapter<TVShowSeasonsAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<JSONObject> mSeasons;
    private int mTVShowID;
    private TVShowSeasonsFragment.OnTVShowSeasonsFragmentInteraction mListener;

    public TVShowSeasonsAdapter(Context context,
                                TVShowSeasonsFragment.OnTVShowSeasonsFragmentInteraction listener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
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
        try {
            JSONObject season = mSeasons.get(position);

            Glide.with(mContext)
                    .load(BuildConfig.IMAGE_BASE_URL + "/w154" +
                            season.getString("poster_path"))
                    .placeholder(new ColorDrawable(mContext.getResources().getColor(
                            R.color.colorPrimaryLight)))
                    .fitCenter()
                    .into(holder.mTVSeasonPoster);
            holder.mTVSeasonPoster.setContentDescription(season.getString("name"));

            holder.mTVSeasonName.setText(season.getString("name"));
            holder.mTVSeasonOverview.setText(season.getString("overview"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mSeasons == null) return 0;
        return mSeasons.size();
    }

    public void setSeasons(List<JSONObject> seasons) {
        if (mSeasons == null) mSeasons = seasons;
        else mSeasons.addAll(seasons);
        notifyDataSetChanged();
    }

    public void setTVShowID(int id) {
        mTVShowID = id;
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
                    try {
                        int position = getAdapterPosition();
                        mListener.onDisplaySeasonEpisodes(mTVShowID, mSeasons.get(position)
                                .getInt("season_number"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
