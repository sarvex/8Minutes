package com.eightmins.eightminutes.advocate.dash;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eightmins.eightminutes.R.id;
import com.eightmins.eightminutes.R.layout;
import com.eightmins.eightminutes.advocate.dash.DashAdapter.ViewHolder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nabhilax on 20/01/16.
 */
public class DashAdapter extends Adapter<ViewHolder> {

  private final List<Dash> dashes;

  public DashAdapter(List<Dash> dashes) {
    this.dashes = dashes;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout.item_dash, parent, false));

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
    @Bind(id.dash_title) TextView title;
    @Bind(id.dash_description) TextView description;

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
