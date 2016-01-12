package com.eightmins.eightminutes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabhilax on 12/01/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
  private final List<Fragment> fragments = new ArrayList<>();
  private final List<String> titles = new ArrayList<>();

  public PagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public void addFragment(Fragment fragment, String title) {
    this.fragments.add(fragment);
    this.titles.add(title);
  }

  @Override
  public Fragment getItem(int position) {
    return this.fragments.get(position);
  }

  @Override
  public int getCount() {
    return this.fragments.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return this.titles.get(position);
  }
}
