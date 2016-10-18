package com.pdachoice.tabbedapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class MainActivity extends ActionBarActivity implements TabListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // setup the content view.
    setContentView(R.layout.activity_main);

    // enable the Navigation Tabs in ActionBar
    ActionBar actionBar = getSupportActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    actionBar.setDisplayShowTitleEnabled(false);
    actionBar.setDisplayShowHomeEnabled(false);

    // create and add First tab, with label, icon and listener
    Tab tab1 = actionBar.newTab().setText("First")
        .setIcon(android.R.drawable.ic_dialog_alert).setTabListener(this);
    actionBar.addTab(tab1);

    // create and add Second tab, with label, icon and listener
    Tab tab2 = actionBar.newTab().setText("Second")
        .setIcon(android.R.drawable.ic_dialog_info).setTabListener(this);
    actionBar.addTab(tab2);
  }

  // to be called when you want to show the toFragments in Activity
  void pushViewController(Fragment toFragment, boolean addToStack) {
    // 1: Create a FragmentTransaction from FragmentManager via activity
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();

    // 2: tug in this toFragment into Activity ViewGroup
    transaction.replace(R.id.fragment_container, toFragment, toFragment
        .getClass().getSimpleName());

    if (addToStack) {
      // 3: add the transaction to the back stack so we can pop it out later
      
      // if fragment nont in back stack, add it.
      if(manager.findFragmentByTag(toFragment.getClass().getSimpleName()) == null) 
      {
        transaction.addToBackStack(null);        
      }
    }

    // 4: commit the transaction.
    transaction.commit();    
  }

  // Go back to previous screen.
  void popViewController() {
    FragmentManager manager = getSupportFragmentManager();
    manager.popBackStack();
  }

  @Override
  public void onBackPressed() {
//    super.onBackPressed();
    // this is the back button callback.
    // prevent back event to make tab navigation strictly
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    // do the needful, for example:
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      // Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
      // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
    }
  }

  // ActionBar.TabListener implementations
  @Override
  public void onTabSelected(Tab tab, FragmentTransaction arg1) {

    Log.d("", "onTabSelected: " + tab.getText());
    int position = tab.getPosition();

    FragmentManager manager = getSupportFragmentManager();
    Fragment toFragment = null;
    switch (position) {
    case 0:
      toFragment = manager.findFragmentByTag(ScreenOneFragment.class
          .getSimpleName());
      
      if (toFragment == null) {
        toFragment = new ScreenOneFragment();
      }
      break;
    case 1:
      toFragment = manager.findFragmentByTag(ScreenTwoFragment.class
          .getSimpleName());
      if (toFragment == null) {
        toFragment = new ScreenTwoFragment();
      }
      break;
    }

    pushViewController(toFragment, true);
  }

  @Override
  public void onTabReselected(Tab t, FragmentTransaction arg1) {
    // do nothing
  }

  @Override
  public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
    // do nothing
  }
}
