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
import com.modnsolutions.theatre.TVShowsDetailsActivity;
import com.modnsolutions.theatre.fragment.TVShowInfoFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<JSONObject> mTVShows = new LinkedList<>();

    public TVShowAdapter(Context context) {
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
        JSONObject tvShow = mTVShows.get(position);

        try {
            Glide.with(mContext)
                    .load(BuildConfig.IMAGE_BASE_URL + "/w154" +
                            tvShow.getString("poster_path"))
                    .placeholder(new ColorDrawable(mContext.getResources().getColor(
                            R.color.colorPrimaryLight)))
                    .fitCenter()
                    .into(holder.tvShowPoster);
            holder.tvShowPoster.setContentDescription(tvShow.getString("name"));

            String name;
            if (tvShow.getString("name").length() >= 20) {
                name = tvShow.getString("name").substring(0, 20) + "...";
            } else {
                name = tvShow.getString("name");
            }
            holder.tvShowOriginalName.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mTVShows.size();
    }

    public void setTVShows(List<JSONObject> tvShows) {
        if (mTVShows.size() == 0) mTVShows = tvShows;
        else mTVShows.addAll(tvShows);
    }

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
                    try {
                        JSONObject tvShow = mTVShows.get(getAdapterPosition());
                        Intent intent = new Intent(mContext, TVShowsDetailsActivity.class);
                        intent.putExtra(TVShowInfoFragment.TV_SHOW_EXTRA, tvShow.getInt("id"));
                        mContext.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
