package com.eightmins.eightminutes.advocate.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eightmins.eightminutes.BuildConfig;
import com.eightmins.eightminutes.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason;
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.android.youtube.player.YouTubeThumbnailView.OnInitializedListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nabhilax on 23/01/16.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
  private final List<Video> videos;
  private Context context;

  public VideoAdapter(Context context, List<Video> videos) {
    this.context = context;
    this.videos = videos;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    holder.bind(position);
  }



  @Override
  public int getItemCount() {
    return videos.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.youtube_title) TextView title;
    @Bind(R.id.youtube_description) TextView description;
    @Bind(R.id.youtube_layout) RelativeLayout relativeLayout;
    @Bind(R.id.youtube_thumbnail) YouTubeThumbnailView youTubeThumbnailView;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    @OnClick(R.id.youtube_play)
    public void onClick(View v) {

      Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, BuildConfig.YOUTUBE_DATA_KEY, videos.get(getLayoutPosition()).getUrl());
      context.startActivity(intent);
    }

    public void bind(final int position) {
      title.setText(videos.get(position).getName());
      description.setText(videos.get(position).getDescription());

      final OnThumbnailLoadedListener onThumbnailLoadedListener = new OnThumbnailLoadedListener(){
        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
          youTubeThumbnailView.setVisibility(View.VISIBLE);
          relativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, ErrorReason errorReason) {
          //write something for failure
        }
      };

      youTubeThumbnailView.initialize(BuildConfig.YOUTUBE_DATA_KEY, new OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

          youTubeThumbnailLoader.setVideo(videos.get(position).getUrl());
          youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
        }

        @Override
        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
          //write something for failure
        }
      });
    }
  }
}
