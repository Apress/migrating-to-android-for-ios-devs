package com.pdachoice.pagebypage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// Use framework class to simplify PagerAdapter impl
public class MonthViewPagerAdapter extends FragmentPagerAdapter {

  // Java constants: public static final ...
  public static final String MONTH = "month";
  private static final int MonthCount = 12;

  // Must have at least one constructor if parent has non-default constructor
  public MonthViewPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  // Required method for using FragmentPagerAdapter
  @Override
  public int getCount() {
    return MonthCount;
  }

  // Required method for using FragmentPagerAdapter
  @Override
  public Fragment getItem(int position) {
    Fragment f = new MonthFragment();
    Bundle args = new Bundle();
    args.putInt(MONTH, position);
    f.setArguments(args);
    return f;
  }
}
