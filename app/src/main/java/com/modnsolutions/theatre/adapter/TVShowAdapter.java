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

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<JSONObject> mTVShows;

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
        try {
            JSONObject tvShow = mTVShows.get(position);

            Glide.with(mContext)
                    .load(BuildConfig.IMAGE_BASE_URL + "/w154" +
                            tvShow.getString("poster_path"))
                    .placeholder(new ColorDrawable(mContext.getResources().getColor(
                            R.color.colorPrimaryLight)))
                    .fitCenter()
                    .into(holder.tvShowPoster);
            holder.tvShowPoster.setContentDescription(tvShow.getString("original_name"));

            String originalName;
            if (tvShow.getString("original_name").length() >= 20) {
                originalName = tvShow.getString("original_name").substring(0, 20) + "...";
            } else {
                originalName = tvShow.getString("original_name");
            }
            holder.tvShowOriginalName.setText(originalName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mTVShows == null) return 0;
        return mTVShows.size();
    }

    public void setTVShows(List<JSONObject> tvShows) {
        if (mTVShows == null) mTVShows = tvShows;
        else mTVShows.addAll(tvShows);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView tvShowPoster;
        private TextView tvShowOriginalName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvShowPoster = itemView.findViewById(R.id.tv_show_poster);
            tvShowOriginalName = itemView.findViewById(R.id.tv_show_original_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Do something
                }
            });
        }
    }
}
