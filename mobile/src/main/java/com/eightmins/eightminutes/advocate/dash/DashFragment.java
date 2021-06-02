package com.eightmins.eightminutes.advocate.dash;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eightmins.eightminutes.MainApplication;
import com.eightmins.eightminutes.R.id;
import com.eightmins.eightminutes.R.layout;
import com.eightmins.eightminutes.login.User;
import com.parse.ParseUser;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icepick;

public class DashFragment extends Fragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @Bind(id.progress_bar) ProgressBar progressBar;
  @Bind(id.progress_text) TextView progressText;
  @Bind(id.dash_recycler_view) RecyclerView recyclerView;
  private List<Dash> dashes = new ArrayList<>(1);
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  public DashFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment DashFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static DashFragment newInstance(String param1, String param2) {
    DashFragment fragment = new DashFragment();
    Bundle args = new Bundle();
    args.putString(DashFragment.ARG_PARAM1, param1);
    args.putString(DashFragment.ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
    refWatcher.watch(this);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Icepick.saveInstanceState(this, outState);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(DashFragment.ARG_PARAM1);
      mParam2 = getArguments().getString(DashFragment.ARG_PARAM2);
    }

    Icepick.restoreInstanceState(this, savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(layout.fragment_dash, container, false);
    ButterKnife.bind(this, view);
    load();

    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(new DashAdapter(dashes));

    return view;
  }

  public void load() {
    showProgress();
    User user = (User)ParseUser.getCurrentUser();

    if (user != null) {
      dashes = new ArrayList<>(3);
      dashes.add(new Dash("Progress", user.getInstalled() + " Completed"));
      dashes.add(new Dash("Team", user.getMembers() + " Members"));
      dashes.add(new Dash("Earnings", "Rs " + user.getEarnings()));
    }
    hideProgress();
  }


  // TODO: Rename method, update argument and hook method into UI event
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
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  protected void hideProgress() {
    progressBar.setIndeterminate(false);
    progressBar.setVisibility(View.INVISIBLE);
    progressText.setVisibility(View.INVISIBLE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  protected void showProgress() {
    progressBar.setIndeterminate(true);
    progressBar.setVisibility(View.VISIBLE);
    progressText.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.INVISIBLE);
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
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
}
