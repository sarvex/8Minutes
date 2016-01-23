package com.eightmins.eightminutes.advocate.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eightmins.eightminutes.BuildConfig;
import com.eightmins.eightminutes.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.android.youtube.player.YouTubeThumbnailView.OnInitializedListener;

import java.util.List;

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
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false));  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    holder.bind(position);
  }



  @Override
  public int getItemCount() {
    return videos.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected RelativeLayout relativeLayout;
    YouTubeThumbnailView youTubeThumbnailView;
    protected ImageView playButton;

    public ViewHolder(View itemView) {
      super(itemView);
      playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
      playButton.setOnClickListener(this);
      relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
      youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
    }

    @Override
    public void onClick(View v) {

      Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, BuildConfig.YOUTUBE_DATA_KEY, videos.get(getLayoutPosition()).getUrl());
      context.startActivity(intent);
    }

    public void bind(final int position) {
      final OnThumbnailLoadedListener onThumbnailLoadedListener = new OnThumbnailLoadedListener(){
        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
          youTubeThumbnailView.setVisibility(View.VISIBLE);
          relativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
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
