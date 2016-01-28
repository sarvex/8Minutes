package com.eightmins.eightminutes.advocate.team;

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
 * {@link MembersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MembersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MembersFragment extends Fragment {

  @Bind(R.id.team_recycler_view) RecyclerView recyclerView;
  private MemberAdapter adapter;
  private List<Member> members;

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
   * @return A new instance of fragment MembersFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static MembersFragment newInstance(String param1, String param2) {
    MembersFragment fragment = new MembersFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }  public MembersFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
    load();
  }

  private void load() {
    // TODO dummy data
    members = new ArrayList<>(10);
    Member member1 = new Member();
    member1.load(R.mipmap.ic_account_circle, "Member 1", 11, 2, 4, 6, 10000);
    members.add(member1);
    Member member2 = new Member();
    member2.load(R.mipmap.ic_account_circle, "Member 1", 11, 2, 4, 6, 10000);
    members.add(member2);
    Member member3 = new Member();
    member3.load(R.mipmap.ic_account_circle, "Member 1", 11, 2, 4, 6, 10000);
    members.add(member3);
    Member member4 = new Member();
    member4.load(R.mipmap.ic_account_circle, "Member 1", 11, 2, 4, 6, 10000);
    members.add(member4);
    Member member5 = new Member();
    member5.load(R.mipmap.ic_account_circle, "Member 1", 11, 2, 4, 6, 10000);
    members.add(member5);
    Member member6 = new Member();
    member6.load(R.mipmap.ic_account_circle, "Member 1", 11, 2, 4, 6, 10000);
    members.add(member6);
    Member member7 = new Member();
    member7.load(R.mipmap.ic_account_circle, "Member 1", 11, 2, 4, 6, 10000);
    members.add(member7);
    Member member8 = new Member();
    member8.load(R.mipmap.ic_account_circle, "Member 1", 11, 2, 4, 6, 10000);
    members.add(member8);
    Member member9 = new Member();
    member9.load(R.mipmap.ic_account_circle, "Member 1", 11, 2, 4, 6, 10000);
    members.add(member9);
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
    recyclerView.setAdapter(new MemberAdapter(members));

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
