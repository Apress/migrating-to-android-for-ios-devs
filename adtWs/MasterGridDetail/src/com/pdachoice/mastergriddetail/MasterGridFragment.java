package com.pdachoice.mastergriddetail;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
//import android.widget.ArrayAdapter;

public class MasterGridFragment extends Fragment implements OnItemClickListener {

  MasterGridAdapter mGridAdapter;
  private GridView gridview;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true); // enable Option Menu.

    // setup ListView data source
    mGridAdapter = new MasterGridAdapter(getActivity());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    gridview = (GridView) inflater.inflate(R.layout.mastergrid_fragment,
        container, false);

    // set data source
    gridview.setAdapter(mGridAdapter);
    
    // set GridView item click listener
    gridview.setOnItemClickListener(this);

    return gridview;
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
      mGridAdapter.add(Calendar.getInstance().getTime());
      mGridAdapter.notifyDataSetChanged();
    } else {
      // other menu item
    }
    return true;
  }

  // callback method when GridView item is selected.
  @Override
  public void onItemClick(AdapterView<?> l, View v, int position, long id) {
    Fragment detailFragment = new DetailFragment();

    Bundle parms = new Bundle();
    Date timestamp = mGridAdapter.getItem(position);
    parms.putString("ts", timestamp.toString());
    detailFragment.setArguments(parms); // pass data to other Fragment

    ((MainActivity) getActivity()).pushViewController(detailFragment, true);
  }

  @Override
  public void onResume() {
    super.onResume();
    getActivity().setTitle("Master List"); // or Detail
  }

}
