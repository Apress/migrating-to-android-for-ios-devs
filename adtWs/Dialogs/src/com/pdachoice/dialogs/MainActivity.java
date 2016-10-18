package com.pdachoice.dialogs;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // setup the content view.
    setContentView(R.layout.activity_main);

    // b: Adding the first fragment to the navigation stack.
    pushViewController(new ScreenOneFragment(), false);
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

  public void setBackgroundColor(int position) {
    // TODO Auto-generated method stub

    ScreenOneFragment frag = (ScreenOneFragment) getSupportFragmentManager()
        .findFragmentByTag(ScreenOneFragment.class.getSimpleName());

    // "Red", "Green", "Blue"
    int color;
    switch (position) {
    case 0:
      color = Color.RED;
      break;
    case 1:
      color = Color.GREEN;
      break;
    default:
      color = Color.BLUE;
      break;
    }

    frag.contentView.setBackgroundColor(color);
  }
}
