package com.eightmins.eightminutes;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.eightmins.eightminutes.advocate.AddMemberActivity;
import com.eightmins.eightminutes.advocate.AddReferralActivity;
import com.eightmins.eightminutes.advocate.dash.DashFragment;
import com.eightmins.eightminutes.advocate.member.TeamFragment;
import com.eightmins.eightminutes.advocate.refer.ReferralFragment;
import com.eightmins.eightminutes.advocate.video.VideoFragment;
import com.eightmins.eightminutes.login.LoginActivity;
import com.eightmins.eightminutes.login.ProfileActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.parse.LogOutCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
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
  @Bind(R.id.add_button) FloatingActionButton addButton;

  private AccountHeader accountHeader;
  private Drawer drawer;
  private ShareActionProvider shareActionProvider;
  private ProgressDialog progress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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
    collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.primary));
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
    tabLayout.setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager) {
      @Override
      public void onTabSelected(Tab tab) {
        super.onTabSelected(tab);
        switch (tab.getText().toString()) {
          case "Home":
            addButton.setVisibility(View.INVISIBLE);
            break;
          case "Referrals":
            addButton.setVisibility(View.VISIBLE);
            break;
          case "Team":
            addButton.setVisibility(View.VISIBLE);
            break;
          case "Videos":
            addButton.setVisibility(View.INVISIBLE);
            break;
        }
      }

      @Override
      public void onTabUnselected(Tab tab) {
        super.onTabUnselected(tab);
      }

      @Override
      public void onTabReselected(Tab tab) {
        super.onTabReselected(tab);
      }
    });
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
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
            new PrimaryDrawerItem().withName("Order T-Shirts").withIcon(Icon.gmd_local_florist),
            new PrimaryDrawerItem().withName("Profile").withIcon(Icon.gmd_person)
            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
              @Override
              public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                toProfileActivity();
                return true;
              }
            }),
            new PrimaryDrawerItem().withName("Settings").withIcon(Icon.gmd_settings),
            new PrimaryDrawerItem().withName("Logout").withIcon(Icon.gmd_android)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                  @Override
                  public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                    if (ParseUser.getCurrentUser() != null) {
                      showProgressBar();
                      ParseUser.logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                          hideProgressBar();
                          if (e == null) {
                            toLoginActivity();
                          }
                        }
                      });
                    }
                    return true;
                  }
                }))
        .build();

    drawer.getRecyclerView().setVerticalScrollBarEnabled(false);
  }

  protected void hideProgressBar() {
    if ((progress != null) && progress.isShowing()) {
      progress.dismiss();
    }
  }

  protected void showProgressBar() {
    progress = new ProgressDialog(this);
    progress.setMessage("Logging in...");
    progress.setIndeterminate(true);
    progress.setProgress(0);
    progress.show();
  }

  private void toProfileActivity() {
    startActivity(new Intent(this, ProfileActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  private OnFilterChangedListener onFilterChangedListener;

  public void setOnFilterChangedListener(OnFilterChangedListener onFilterChangedListener) {
    this.onFilterChangedListener = onFilterChangedListener;
  }


  protected void toLoginActivity() {
    startActivity(new Intent(this, LoginActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);

    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView = null;
    if (searchItem != null) {
      searchView = (SearchView) searchItem.getActionView();
    }
    if (searchView != null) {
      searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    MenuItem shareItem = menu.findItem(R.id.action_share);
    shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
    shareActionProvider.setShareIntent(doShare());
    return super.onCreateOptionsMenu(menu);
  }

  private Intent doShare() {
    return null;
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
        startActivity(new Intent(this, AddReferralActivity.class));
        break;
      case 2:
        startActivity(new Intent(this, AddMemberActivity.class));
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
