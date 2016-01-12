package com.eightmins.eightminutes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.eightmins.eightminutes.R.id;
import com.eightmins.eightminutes.R.layout;
import com.eightmins.eightminutes.login.LoginActivity;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  private final boolean searchBoxShown = false;
  @Bind(id.drawer_layout)
  DrawerLayout drawerLayout;
  @Bind(id.toolbar)
  Toolbar toolbar;
  @Bind(id.viewpager)
  ViewPager viewPager;
  @Bind(id.tabs)
  TabLayout tabLayout;
  @Bind(id.fab)
  FloatingActionButton floatingActionButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(layout.activity_main);
    ButterKnife.bind(this);

    this.setSupportActionBar(this.toolbar);

    ActionBar actionBar = this.getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    this.drawerLayout = (DrawerLayout) this.findViewById(id.drawer_layout);
    NavigationView navigationView = (NavigationView) this.findViewById(id.nav_view);
    if (navigationView != null) {
      navigationView.setNavigationItemSelectedListener(
          new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
              menuItem.setChecked(true);
              MainActivity.this.drawerLayout.closeDrawers();
              return true;
            }
          });
    }

    if (this.viewPager != null) {
      PagerAdapter adapter = new PagerAdapter(this.getSupportFragmentManager());
//      adapter.addFragment(new StintFragment(), "Offered");
//      adapter.addFragment(new StintFragment(), "Accepted");
//      adapter.addFragment(new StintFragment(), "Completed");
//      adapter.addFragment(new StintFragment(), "Approved");
      this.viewPager.setAdapter(adapter);
      this.viewPager.setCurrentItem(1);
    }

    this.tabLayout.setupWithViewPager(this.viewPager);
    this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    this.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    ParseAnalytics.trackAppOpenedInBackground(this.getIntent());
    if (ParseUser.getCurrentUser() == null) {
      this.toLoginActivity();
    }
  }

  private void toLoginActivity() {
    this.startActivity(new Intent(this, LoginActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    this.getMenuInflater().inflate(R.menu.menu_main, menu);
    MenuItem searchItem = menu.findItem(id.action_search);

    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

    SearchView searchView = null;
    if (searchItem != null) {
      searchView = (SearchView) searchItem.getActionView();
    }
    if (searchView != null) {
      searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    boolean result = false;

    switch (id) {
      case R.id.action_logout:
        ParseUser.logOutInBackground();
        this.toLoginActivity();
        result = true;
        break;
      case R.id.action_settings:
        result = true;
        break;
      case android.R.id.home:
        this.drawerLayout.openDrawer(GravityCompat.START);
        result = true;
      default:
        break;
    }

    return result || super.onOptionsItemSelected(item);
  }

  @OnClick(id.fab)
  public void onFloatingActionButtonClicked(View view) {
    Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
  }
}
