package com.pdachoice.mastergriddetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MasterGridAdapter extends BaseAdapter {

  private List<Date> timestamps;
  private Activity activity;

  public MasterGridAdapter(Activity caller) {
    this.timestamps = new ArrayList<Date>();
    this.activity = caller;
  }

  // number of items in the data set
  @Override
  public int getCount() {
    return timestamps.size();
  }

  // the data item with the position in the data set
  @Override
  public Date getItem(int position) {
    return timestamps.get(position);
  }

  // the row id with the position
  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup paren) {

    if (view == null) { // check null first before recycled object.
      view = activity.getLayoutInflater()
          .inflate(R.layout.date_grid_cell, null);
    }

    // manipulate the view widgets and display data
    if (position % 2 == 0) {
      view.setBackgroundColor(Color.argb(32, 0, 128, 128));
    } else {
      view.setBackgroundColor(Color.argb(0, 0, 0, 0));
    }

    Date ts = timestamps.get(position);

    TextView textViewMonth = (TextView) view.findViewById(R.id.textViewMonth);
    textViewMonth.setText(DateFormat.format("MMMM yyyy", ts));

    TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);
    textViewDate.setText(DateFormat.format("EEEE, MMMM dd", ts));

    TextView textViewTime = (TextView) view.findViewById(R.id.textViewTime);
    textViewTime.setText(DateFormat.format("h:mmaa", ts));

    return view;
  }

  public void add(Date time) {
    timestamps.add(time);
  }

}
