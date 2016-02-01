package com.eightmins.eightminutes.advocate.video;

import android.R.string;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eightmins.eightminutes.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  protected List<Video> videos = new ArrayList<>(1);
  @Bind(R.id.progress_bar) ProgressBar progressBar;
  @Bind(R.id.progress_text) TextView progressText;
  @Bind(R.id.video_recycler_view)

  RecyclerView recyclerView;
  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  public VideoFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment VideoFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static VideoFragment newInstance(String param1, String param2) {
    VideoFragment fragment = new VideoFragment();
    Bundle args = new Bundle();
    args.putString(VideoFragment.ARG_PARAM1, param1);
    args.putString(VideoFragment.ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(VideoFragment.ARG_PARAM1);
      mParam2 = getArguments().getString(VideoFragment.ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_video, container, false);
    ButterKnife.bind(this, view);
    load();

    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(new VideoAdapter(getContext(), videos));

    return view;
  }

  public void load() {
    showProgress();
    ParseQuery<Video> query = ParseQuery.getQuery("Video");
    query.findInBackground(new FindCallback<Video>() {
      @Override
      public void done(List<Video> objects, ParseException exception) {
        hideProgress();
        if (exception == null) {
          if (objects == null) {
            new Builder(getActivity()).setTitle(R.string.error_title).setMessage("Unable to Load Videos").setPositiveButton(string.ok, null).create().show();
          } else {
            videos = new ArrayList<>(objects);
            VideoAdapter videoAdapter = new VideoAdapter(getContext(), videos);
            recyclerView.setAdapter(videoAdapter);
            videoAdapter.notifyDataSetChanged();
          }
        } else {
          new Builder(getActivity()).setTitle(R.string.error_title).setMessage(exception.getMessage()).setPositiveButton(string.ok, null).create().show();
        }
      }
    });
  }

  protected void showProgress() {
    progressBar.setIndeterminate(true);
    progressBar.setVisibility(View.VISIBLE);
    progressText.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.INVISIBLE);
  }

  protected void hideProgress() {
    progressBar.setIndeterminate(false);
    progressBar.setVisibility(View.INVISIBLE);
    progressText.setVisibility(View.INVISIBLE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri uri);
  }
}
