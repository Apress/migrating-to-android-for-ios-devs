package com.pdachoice.masterdetail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MasterListFragment extends ListFragment {

  ArrayAdapter<Date> mListAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true); // enable Option Menu.

    // setup ListView data source
    mListAdapter = new ArrayAdapter<Date>(getActivity(),
        android.R.layout.simple_list_item_1, new ArrayList<Date>());
    this.setListAdapter(mListAdapter);
  }

  // render option menu from resource file, menu.xml
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.main, menu);
  }

  // callback method when menu items are selected.
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_add) {
      mListAdapter.add(Calendar.getInstance().getTime());
      mListAdapter.notifyDataSetChanged();
    } else {
      // other menu item
    }
    return true;
  }

  // callback method when list item is selected.
  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    Fragment detailFragment = new DetailFragment();

    Bundle parms = new Bundle();
    Date timestamp = mListAdapter.getItem(position);
    parms.putString("ts", timestamp.toString());
    detailFragment.setArguments(parms); // pass data to other Fragment

    ((MainActivity) getActivity()).pushViewController(detailFragment, true);
  }
  
  @Override
  public void onResume() {
     super.onResume();
     getActivity().setTitle("Master List");  // or Detail
  }

}
