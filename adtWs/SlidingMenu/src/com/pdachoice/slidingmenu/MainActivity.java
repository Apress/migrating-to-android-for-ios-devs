package com.pdachoice.slidingmenu;

import java.util.Arrays;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements
    OnItemClickListener {

  private static final List<String> items = Arrays.asList("1st view",
      "2nd view", "3rd view");

  private View contentView;
  private ListView leftDrawer;
  private ArrayAdapter<String> mListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // setup the content view.
    setContentView(R.layout.activity_main);

    contentView = findViewById(R.id.drawer_layout);

    leftDrawer = (ListView) contentView.findViewById(R.id.leftDrawer);

    mListAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, items);

    leftDrawer.setAdapter(mListAdapter);
    leftDrawer.setOnItemClickListener(this);

    pushViewController(new ScreenOneFragment(), false);
    
    // enable App Icon as Up button
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);    
  }

  
  // callback for the App Icon Nav Up button
  @Override
  public boolean onSupportNavigateUp () {
    Log.d("", "onSupportNavigateUp");
    if(((DrawerLayout) contentView).isDrawerOpen(leftDrawer)) {
      ((DrawerLayout) contentView).closeDrawer(leftDrawer);
    } else {
      ((DrawerLayout) contentView).openDrawer(leftDrawer);
    }
    return true;
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
      transaction.addToBackStack(null);
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
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    // do the needful, for example:
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      // Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
      // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onItemClick(AdapterView<?> lv, View li, int pos, long id) {

    // Close the the sliding menu, and set the title.
    ((DrawerLayout) contentView).closeDrawer(leftDrawer);
//    getSupportActionBar().setTitle(items.get(pos));
    setTitle(items.get(pos));

    // Navigate to the selected Fragment.
    FragmentManager manager = getSupportFragmentManager();
    Fragment toFragment = null;
    switch (pos) {
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
    case 2:
      toFragment = manager.findFragmentByTag(ScreenThreeFragment.class
          .getSimpleName());
      if (toFragment == null) {
        toFragment = new ScreenThreeFragment();
      }
      break;
    }

    pushViewController(toFragment, true);

  }
}
