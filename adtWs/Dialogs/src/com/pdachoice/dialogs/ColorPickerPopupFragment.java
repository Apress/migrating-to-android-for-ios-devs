package com.pdachoice.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ColorPickerPopupFragment extends DialogFragment implements
    OnItemClickListener {

  private ArrayAdapter<String> mListAdapter;
  private ListView mListview;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mListAdapter = new ArrayAdapter<String>(getActivity(),
        android.R.layout.simple_list_item_1, new String[] { "Red", "Green",
            "Blue" });
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    LayoutInflater inflater = getActivity().getLayoutInflater();

    // set list item Adapter that supplies list view items
    mListview = (ListView) inflater.inflate(R.layout.colorpickerpopup_fragment,
        null);
    mListview.setAdapter(mListAdapter);
    mListview.setOnItemClickListener(this);

    // Same as previous Alert, the framework use Builder design pattern
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    // AlertDialog title and icon.
    builder.setTitle("Pick a color");
    builder.setIcon(android.R.drawable.ic_menu_directions);

    // AlertDialog custom layout in AlertDialog content area
    builder.setView(mListview);

    // Create the AlertDialog instance and return it.
    AlertDialog dialog = builder.create();
    return dialog;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    Log.d("onClick", "position: " + position + "id: " + id);

    Toast.makeText(getActivity(), mListAdapter.getItem(position),
        Toast.LENGTH_LONG).show();
    getDialog().dismiss();
  }

}
