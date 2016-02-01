package com.eightmins.eightminutes.advocate.team;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eightmins.eightminutes.R;
import com.eightmins.eightminutes.R.id;
import com.eightmins.eightminutes.R.layout;
import com.eightmins.eightminutes.advocate.team.MemberAdapter.ViewHolder;
import com.eightmins.eightminutes.login.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nabhilax on 14/01/16.
 */
public class MemberAdapter extends Adapter<ViewHolder> {
  private final List<User> users;

  public MemberAdapter(List<User> users) {
    this.users = users;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout.item_member, parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(users.get(position));
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(id.name) TextView name;
    @Bind(id.members) TextView members;
    @Bind(id.members_label) TextView membersLabel;
    @Bind(id.installed) TextView installed;
    @Bind(id.installed_label) TextView installedLabel;
    @Bind(id.progress) TextView progress;
    @Bind(id.progress_label) TextView progressLabel;
    @Bind(id.pending) TextView pending;
    @Bind(id.pending_label) TextView pendingLabel;
    @Bind(id.earnings) TextView earnings;

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
      earnings.setText("Rs. " + user.getEarnings());
    }
  }
}
