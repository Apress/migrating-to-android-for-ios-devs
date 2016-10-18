package com.pdachoice.mobilesearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MasterListFragment extends ListFragment implements
    OnQueryTextListener {

  // ArrayAdapter<Date> mListAdapter;
  //
  // @Override
  // public void onCreate(Bundle savedInstanceState) {
  // super.onCreate(savedInstanceState);
  // setHasOptionsMenu(true); // enable Option Menu.
  //
  // // setup ListView data source
  // mListAdapter = new ArrayAdapter<Date>(getActivity(),
  // android.R.layout.simple_list_item_1, new ArrayList<Date>());
  // this.setListAdapter(mListAdapter);
  // }

  private static final String[] items = { "a", "ab", "abc", "abcd", "abcde",
      "abcdef", "abcdefg" };

  ArrayAdapter<String> myListAdapter;

  private SearchView searchView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    myListAdapter = new ArrayAdapter<String>(getActivity(),
        android.R.layout.simple_list_item_1, Arrays.asList(items));
    this.setListAdapter(myListAdapter);

    setHasOptionsMenu(true);
  }

  // render option menu from resource file, menu.xml
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.main, menu);

    MenuItem searchItem = menu.findItem(R.id.menuAction);
    searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

    searchView.setOnQueryTextListener(this);

    searchView.setOnCloseListener(new OnCloseListener() {
      @Override
      public boolean onClose() {
        Log.w("MasterListFragment", "onClose");

        myListAdapter = new ArrayAdapter<String>(getActivity(),
            android.R.layout.simple_list_item_1, Arrays.asList(items));
        setListAdapter(myListAdapter);
        return false;
      }
    });

    searchView.setIconifiedByDefault(true); // default to true.
    searchView.setQueryHint(getActivity().getResources().getText(
        R.string.search_hint));

    // searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH); // for API 14+
    // searchView.setInputType(InputType.TYPE_CLASS_TEXT); // for API 14+

    searchView.setQueryRefinementEnabled(true);
    SearchManager searchManager = (SearchManager) getActivity()
        .getSystemService(Context.SEARCH_SERVICE);
    SearchableInfo info = searchManager.getSearchableInfo(getActivity()
        .getComponentName());
    searchView.setSearchableInfo(info);

  }

  // callback method when menu items are selected.
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.delete_history) {
      SearchRecentSuggestions suggestions = new SearchRecentSuggestions(
          getActivity(), MyProvider.AUTHORITY, MyProvider.MODE);
      suggestions.clearHistory();
    }

    return true;
  }

  // callback method when list item is selected.
  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    Fragment detailFragment = new DetailFragment();

    Bundle parms = new Bundle();
    // Date timestamp = mListAdapter.getItem(position);

    parms.putString("ts", items[position]);
    detailFragment.setArguments(parms); // pass data to other Fragment

    ((MainActivity) getActivity()).pushViewController(detailFragment, true);
  }

  @Override
  public void onResume() {
    super.onResume();
    getActivity().setTitle("Master List"); // or Detail
  }

  void doSearch(String query) {
    Log.w("MasterListFragment", "doSearch: " + query);
    List<String> filteredList = new ArrayList<String>();
    if (query == null || query.length() == 0) {
      filteredList = Arrays.asList(items);
    } else {
      for (int i = 0; i < items.length; i++) {
        if (items[i].toLowerCase().contains(query.toLowerCase())) {
          filteredList.add(items[i]);
        }
      }
    }

    // reload List view with the filtered results
    myListAdapter = new ArrayAdapter<String>(getActivity(),
        android.R.layout.simple_list_item_1, filteredList);
    this.setListAdapter(myListAdapter);
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    Log.d("MasterListFragment", "onQueryTextSubmit: " + query);
    searchView.setQuery(query, false); // leave query text in SearchView

    InputMethodManager imm = (InputMethodManager) getActivity()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);//dismiss keyboard

    doSearch(query);

    // save the query string
    SearchRecentSuggestions suggestions = new SearchRecentSuggestions(
        getActivity(), MyProvider.AUTHORITY, MyProvider.MODE);
    suggestions.saveRecentQuery(query, null);

    // true if the query has been handled by the listener,
    // false to let the SearchView perform the default action.
    return false;
  }

  @Override
  public boolean onQueryTextChange(String query) {
    Log.d("MasterListFragment", "onQueryTextChange: " + query);
    doSearch(query);

    // true if the action was handled by the listener
    // false if the SearchView should perform the default action of showing
    // any suggestions if available,
    return false;
  }

}
