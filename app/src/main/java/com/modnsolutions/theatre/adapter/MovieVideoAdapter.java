package com.modnsolutions.theatre.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.modnsolutions.theatre.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<JSONObject> mVideos = new LinkedList<>();

    public MovieVideoAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recyclerview_vidoes, parent,
                false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JSONObject video = mVideos.get(position);

        try {
            Glide.with(mContext)
                    .load(BuildConfig.YOUTUBE_IMAGE_URL + video.getString("key")
                            + "/0.jpg")
                    .placeholder(new ColorDrawable(mContext.getResources().getColor(
                            R.color.colorPrimaryLight)))
                    .fitCenter()
                    .into(holder.videoImage);
            holder.videoImage.setContentDescription(video.getString("name"));
            holder.videoName.setText(video.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public void setVideos(List<JSONObject> videos) {
        if (mVideos.size() == 0) mVideos = videos;
        else mVideos.addAll(videos);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView videoImage;
        private TextView videoName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            videoImage = itemView.findViewById(R.id.video_image);
            videoName = itemView.findViewById(R.id.video_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();

                        // Open trailer in youtube app or web browser.
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                                BuildConfig.YOUTUBE_WATCH_URL + mVideos.get(position)
                                        .getString("key")));
                        if (intent.resolveActivity(mContext.getPackageManager()) != null)
                            mContext.startActivity(intent);
                        else
                            Utilities.displayToast(mContext, "No app available to " +
                                    "display video.");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
