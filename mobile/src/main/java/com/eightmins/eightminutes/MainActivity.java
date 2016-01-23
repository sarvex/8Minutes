package com.eightmins.eightminutes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.eightmins.eightminutes.advocate.MemberActivity;
import com.eightmins.eightminutes.advocate.ReferActivity;
import com.eightmins.eightminutes.advocate.dash.DashFragment;
import com.eightmins.eightminutes.advocate.member.TeamFragment;
import com.eightmins.eightminutes.advocate.refer.ReferralFragment;
import com.eightmins.eightminutes.advocate.video.VideoFragment;
import com.eightmins.eightminutes.login.LoginActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ReferralFragment.OnFragmentInteractionListener,
    TeamFragment.OnFragmentInteractionListener, VideoFragment.OnFragmentInteractionListener,
    DashFragment.OnFragmentInteractionListener {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.viewpager) ViewPager viewPager;
  @Bind(R.id.tabs) TabLayout tabLayout;

  private AccountHeader accountHeader;
  private Drawer drawer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setupToolbar();

    setupDrawer();

    setupViewPager();

    setupCollapsingToolbar();

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
    if (ParseUser.getCurrentUser() == null) {
      toLoginActivity();
    }
  }

  private void setupCollapsingToolbar() {
    final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

    collapsingToolbar.setTitleEnabled(false);
    collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.primary));
    collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.primary_dark));
  }

  private void setupViewPager() {
    if (viewPager != null) {
      PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
      adapter.addFragment(new DashFragment(), "Home");
      adapter.addFragment(new ReferralFragment(), "Referrals");
      adapter.addFragment(new TeamFragment(), "Team");
      adapter.addFragment(new VideoFragment(), "Videos");
      viewPager.setAdapter(adapter);
    }

    tabLayout.setupWithViewPager(viewPager);
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
    }
  }

  private void setupDrawer() {

    String username = "";
    String email = "";

    if (ParseUser.getCurrentUser() != null) {
      username = ParseUser.getCurrentUser().getUsername();
      email = ParseUser.getCurrentUser().getEmail();
    }

    accountHeader = new AccountHeaderBuilder()
        .withActivity(this)
        .withHeaderBackground(R.drawable.header)
        .addProfiles(new ProfileDrawerItem().withName(username).withEmail(email))
        .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
          @Override
          public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
            return false;
          }
        })
        .build();

    drawer = new DrawerBuilder()
        .withActivity(this)
        .withToolbar(toolbar)
        .withAccountHeader(accountHeader)
        .withHeader(R.layout.header)
        .addDrawerItems(
            new PrimaryDrawerItem().withName("Order T-Shirts").withIcon(GoogleMaterial.Icon.gmd_local_florist),
            new PrimaryDrawerItem().withName("Profile").withIcon(GoogleMaterial.Icon.gmd_person),
            new PrimaryDrawerItem().withName("Settings").withIcon(GoogleMaterial.Icon.gmd_settings),
            new PrimaryDrawerItem().withName("Logout").withIcon(GoogleMaterial.Icon.gmd_android)
        )
        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

          @Override
          public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            if (drawerItem != null) {
              if (drawerItem instanceof Nameable) {
                toolbar.setTitle(((Nameable) drawerItem).getName().getText(MainActivity.this));
              }
              if (onFilterChangedListener != null) {
                onFilterChangedListener.onFilterChanged(drawerItem.getIdentifier());
              }
            }

            return false;
          }
        })
        .build();

    drawer.getRecyclerView().setVerticalScrollBarEnabled(false);
  }

  private OnFilterChangedListener onFilterChangedListener;

  public void setOnFilterChangedListener(OnFilterChangedListener onFilterChangedListener) {
    this.onFilterChangedListener = onFilterChangedListener;
  }


  private void toLoginActivity() {
    startActivity(new Intent(this, LoginActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    boolean result = false;

    switch (id) {
      case R.id.action_settings:
        result = true;
        break;
      default:
        break;
    }

    return result || super.onOptionsItemSelected(item);
  }

  @OnClick(R.id.add_button)
  public void onAddButtonClicked(View view) {
    switch (viewPager.getCurrentItem()) {
      case 1:
        startActivity(new Intent(this, ReferActivity.class));
        break;
      case 2:
        startActivity(new Intent(this, MemberActivity.class));
        break;
      default:
        break;
    }

  }

  @Override
  public void onFragmentInteraction(Uri uri) {

  }

  public interface OnFilterChangedListener {
    public void onFilterChanged(int filter);
  }
}
