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
import com.modnsolutions.theatre.fragment.SearchMoviesFragment;
import com.modnsolutions.theatre.fragment.SearchTVShowsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SearchTVShowsAdapter extends RecyclerView.Adapter<SearchTVShowsAdapter.ViewHolder> {
    private List<JSONObject> mResults;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private SearchTVShowsFragment.OnSearchTVShowsFragmentInteractionListener mListener;

    public SearchTVShowsAdapter(Context context, SearchTVShowsFragment
            .OnSearchTVShowsFragmentInteractionListener listener) {
        mContext = context;
        mListener = listener;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recyclerview_search_tv_shows, parent,
                false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject result = mResults.get(position);

            Glide.with(mContext)
                    .load(BuildConfig.IMAGE_BASE_URL + "/w154" +
                            result.getString("poster_path"))
                    .placeholder(new ColorDrawable(mContext.getResources().getColor(
                            R.color.colorPrimaryLight)))
                    .fitCenter()
                    .into(holder.poster);
            holder.poster.setContentDescription(result.getString("name"));

            holder.title.setText(result.getString("name"));
            holder.overview.setText(result.getString("overview"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mResults == null) return 0;
        return mResults.size();
    }

    public void setResults(List<JSONObject> results) {
        if (mResults == null) mResults = results;
        else mResults.addAll(results);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView poster;
        private TextView title;
        private TextView overview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);
            overview = itemView.findViewById(R.id.overview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        JSONObject tvShow = mResults.get(getAdapterPosition());
                        mListener.onDisplayTVShowDetails(tvShow.getInt("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
