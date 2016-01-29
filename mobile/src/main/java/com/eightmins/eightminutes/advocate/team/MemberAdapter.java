package com.eightmins.eightminutes.advocate.team;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eightmins.eightminutes.R;
import com.eightmins.eightminutes.login.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nabhilax on 14/01/16.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
  private final List<User> members;

  public MemberAdapter(List<User> members) {
    this.members = members;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(members.get(position));
  }

  @Override
  public int getItemCount() {
    return members.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.team_name) TextView name;
    @Bind(R.id.team_members) TextView members;
    @Bind(R.id.team_members_label) TextView membersLabel;
    @Bind(R.id.team_installed) TextView installed;
    @Bind(R.id.team_installed_label) TextView installedLabel;
    @Bind(R.id.team_progress) TextView progress;
    @Bind(R.id.team_progress_label) TextView progressLabel;
    @Bind(R.id.team_pending) TextView pending;
    @Bind(R.id.team_pending_label) TextView pendingLabel;
    @Bind(R.id.team_earnings) TextView earnings;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    public void bind(User user) {
      name.setText(user.getName());
      members.setText(String.valueOf(user.getMembers()));
      membersLabel.setText(" Members");
      installed.setText(String.valueOf(user.getInstalled()));
      installedLabel.setText(" Installed");
      progress.setText(String.valueOf(user.getInstalled()));
      progressLabel.setText(" Progress");
      pending.setText(String.valueOf(user.getInstalled()));
      pendingLabel.setText(" Pending");
      earnings.setText("Rs. " + String.valueOf(user.getEarnings()));
    }
  }
}
