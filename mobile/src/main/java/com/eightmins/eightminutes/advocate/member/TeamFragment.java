package com.eightmins.eightminutes.advocate.member;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eightmins.eightminutes.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamFragment extends Fragment {

  @Bind(R.id.team_recycler_view) RecyclerView recyclerView;
  private TeamAdapter adapter;
  private List<Team> teams;

  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment TeamFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static TeamFragment newInstance(String param1, String param2) {
    TeamFragment fragment = new TeamFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }  public TeamFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }

    // TODO dummy data
    teams = new ArrayList<>(10);
    teams.add(new Team(R.mipmap.ic_menu, "Member 1", "Member One Description", 2, 4, 6, 10000));
    teams.add(new Team(R.mipmap.ic_menu, "Member 2", "Member Two Description", 2, 4, 6, 10000));
    teams.add(new Team(R.mipmap.ic_menu, "Member 3", "Member Three Description", 2, 4, 6, 10000));
    teams.add(new Team(R.mipmap.ic_menu, "Member 4", "Member Four Description", 2, 4, 6, 10000));
    teams.add(new Team(R.mipmap.ic_menu, "Member 5", "Member Five Description", 2, 4, 6, 10000));
    teams.add(new Team(R.mipmap.ic_menu, "Member 6", "Member Six Description", 2, 4, 6, 10000));
    teams.add(new Team(R.mipmap.ic_menu, "Member 7", "Member Seven Description", 2, 4, 6, 10000));
    teams.add(new Team(R.mipmap.ic_menu, "Member 8", "Member Eight Description", 2, 4, 6, 10000));
    teams.add(new Team(R.mipmap.ic_menu, "Member 9", "Member Nine Description", 2, 4, 6, 10000));
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_team, container, false);

    ButterKnife.bind(this, view);
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(new TeamAdapter(teams));

    return view;
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
      throw new RuntimeException(context.toString()
          + " must implement OnFragmentInteractionListener");
    }
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
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
}
