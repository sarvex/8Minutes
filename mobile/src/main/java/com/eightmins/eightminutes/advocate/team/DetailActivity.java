package com.eightmins.eightminutes.advocate.team;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eightmins.eightminutes.R;
import com.eightmins.eightminutes.R.layout;
import com.mikepenz.iconics.context.IconicsContextWrapper;

public class DetailActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(layout.activity_member_detail);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
  }
}
