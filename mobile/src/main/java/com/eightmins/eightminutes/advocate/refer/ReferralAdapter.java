package com.eightmins.eightminutes.advocate.refer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eightmins.eightminutes.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nabhilax on 14/01/16.
 */
public class ReferralAdapter extends RecyclerView.Adapter<ReferralAdapter.ViewHolder>{
  private final List<Referral> referrals;

  public ReferralAdapter(List<Referral> referrals) {
    this.referrals = referrals;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.referral_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bindStint(referrals.get(position));
  }

  @Override
  public int getItemCount() {
    return referrals.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.referral_photo) ImageView photo;
    @Bind(R.id.referral_name) TextView name;
    @Bind(R.id.referral_description) TextView description;
    @Bind(R.id.referral_status) ImageView status;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    public void bindStint(Referral referral) {
      photo.setImageResource(referral.getImage());
      name.setText(referral.getName());
      description.setText(referral.getDescription());
      status.setImageResource(referral.getStatus());
    }
  }

}
