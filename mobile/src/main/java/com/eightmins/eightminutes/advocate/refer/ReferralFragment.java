package com.eightmins.eightminutes.advocate.refer;

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

import com.eightmins.eightminutes.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReferralFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReferralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReferralFragment extends Fragment {

  @Bind(R.id.referral_recycler_view) RecyclerView recyclerView;
  private ReferralAdapter adapter;
  private List<Referral> referrals;

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
   * @return A new instance of fragment ReferralFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ReferralFragment newInstance(String param1, String param2) {
    ReferralFragment fragment = new ReferralFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }  public ReferralFragment() {
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
    referrals = new ArrayList<>(10);
    referrals.add(new Referral(R.mipmap.ic_menu, "Referral 1", "Referral One Description", R.mipmap.ic_done));
    referrals.add(new Referral(R.mipmap.ic_menu, "Referral 2", "Referral Two Description", R.mipmap.ic_done));
    referrals.add(new Referral(R.mipmap.ic_menu, "Referral 3", "Referral Three Description", R.mipmap.ic_done));
    referrals.add(new Referral(R.mipmap.ic_menu, "Referral 4", "Referral Four Description", R.mipmap.ic_done));
    referrals.add(new Referral(R.mipmap.ic_menu, "Referral 5", "Referral Five Description", R.mipmap.ic_done));
    referrals.add(new Referral(R.mipmap.ic_menu, "Referral 6", "Referral Six Description", R.mipmap.ic_done));
    referrals.add(new Referral(R.mipmap.ic_menu, "Referral 7", "Referral Seven Description", R.mipmap.ic_done));
    referrals.add(new Referral(R.mipmap.ic_menu, "Referral 8", "Referral Eight Description", R.mipmap.ic_done));
    referrals.add(new Referral(R.mipmap.ic_menu, "Referral 9", "Referral Nine Description", R.mipmap.ic_done));
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_referral, container, false);

    ButterKnife.bind(this, view);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(new ReferralAdapter(referrals));

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
