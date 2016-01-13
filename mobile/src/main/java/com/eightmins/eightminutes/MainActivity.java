package com.eightmins.eightminutes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.eightmins.eightminutes.login.LoginActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  private final boolean searchBoxShown = false;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.viewpager) ViewPager viewPager;
  @Bind(R.id.tabs) TabLayout tabLayout;
  @Bind(R.id.fab) FloatingActionButton floatingActionButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    setupDrawer();

    if (viewPager != null) {
      PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
//      adapter.addFragment(new StintFragment(), "Offered");
//      adapter.addFragment(new StintFragment(), "Accepted");
//      adapter.addFragment(new StintFragment(), "Completed");
//      adapter.addFragment(new StintFragment(), "Approved");
      viewPager.setAdapter(adapter);
//      viewPager.setCurrentItem(1);
    }

    tabLayout.setupWithViewPager(viewPager);
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
    if (ParseUser.getCurrentUser() == null) {
      toLoginActivity();
    }
  }

//  private OnFilterChangedListener onFilterChangedListener;

//  public void setOnFilterChangedListener(OnFilterChangedListener onFilterChangedListener) {
//    this.onFilterChangedListener = onFilterChangedListener;
//  }

  private void setupDrawer () {
    AccountHeader accountHeader = new AccountHeaderBuilder()
        .withActivity(this)
        .withHeaderBackground(R.drawable.header)
        .addProfiles(
            new ProfileDrawerItem().withName(ParseUser.getCurrentUser().getUsername()).withEmail(ParseUser.getCurrentUser().getEmail())
        )
        .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
          @Override
          public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
            return false;
          }
        })
        .build();

    Drawer drawer = new DrawerBuilder()
        .withActivity(this)
        .withToolbar(toolbar)
        .withAccountHeader(accountHeader)

        .withHeader(R.layout.header)
//        .addDrawerItems(
//            new PrimaryDrawerItem().withName(R.string.category_all).withIdentifier(Category.ALL.id).withIcon(GoogleMaterial.Icon.gmd_landscape),
//            new PrimaryDrawerItem().withName(R.string.category_featured).withIdentifier(Category.FEATURED.id).withIcon(GoogleMaterial.Icon.gmd_grade),
//            new SectionDrawerItem().withName(R.string.category_section_categories),
//            new PrimaryDrawerItem().withName(R.string.category_buildings).withIdentifier(Category.BUILDINGS.id).withIcon(GoogleMaterial.Icon.gmd_location_city),
//            new PrimaryDrawerItem().withName(R.string.category_food).withIdentifier(Category.FOOD.id).withIcon(GoogleMaterial.Icon.gmd_local_bar),
//            new PrimaryDrawerItem().withName(R.string.category_nature).withIdentifier(Category.NATURE.id).withIcon(GoogleMaterial.Icon.gmd_local_florist),
//            new PrimaryDrawerItem().withName(R.string.category_objects).withIdentifier(Category.OBJECTS.id).withIcon(GoogleMaterial.Icon.gmd_style),
//            new PrimaryDrawerItem().withName(R.string.category_people).withIdentifier(Category.PEOPLE.id).withIcon(GoogleMaterial.Icon.gmd_person),
//            new PrimaryDrawerItem().withName(R.string.category_technology).withIdentifier(Category.TECHNOLOGY.id).withIcon(GoogleMaterial.Icon.gmd_local_see)
//        )
//        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//
//          @Override
//          public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//            if (drawerItem != null) {
//              if (drawerItem instanceof Nameable) {
//                toolbar.setTitle(((Nameable) drawerItem).getName().getText(MainActivity.this));
//              }
//              if (onFilterChangedListener != null) {
//                onFilterChangedListener.onFilterChanged(drawerItem.getIdentifier());
//              }
//            }
//
//            return false;
//          }
//        })
        .build();

    drawer.getRecyclerView().setVerticalScrollBarEnabled(false);
  }

  private void toLoginActivity() {
    startActivity(new Intent(this, LoginActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    MenuItem searchItem = menu.findItem(R.id.action_search);

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
        toLoginActivity();
        result = true;
        break;
      case R.id.action_settings:
        result = true;
        break;
      default:
        break;
    }

    return result || super.onOptionsItemSelected(item);
  }

  @OnClick(R.id.fab)
  public void onFloatingActionButtonClicked(View view) {
    Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
  }
}
