package com.eightmins.eightminutes.advocate.dash;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eightmins.eightminutes.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nabhilax on 20/01/16.
 */
public class DashAdapter extends RecyclerView.Adapter<DashAdapter.ViewHolder> {

  public DashAdapter(List<Dash> dashes) {
    this.dashes = dashes;
  }

  private final List<Dash> dashes;

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dash_list_item, parent, false));

  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(dashes.get(position));
  }

  @Override
  public int getItemCount() {
    return dashes.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.dash_title) TextView title;
    @Bind(R.id.dash_description) TextView description;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    public void bind(Dash dash) {
      title.setText(dash.getTitle());
      description.setText(dash.getDescription());
    }
  }
}
