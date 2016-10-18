package com.pdachoice.pagebypage;

import java.text.DateFormatSymbols;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MonthFragment extends Fragment {
  private View contentView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.monthview_fragment, container, false);

    int month = this.getArguments().getInt(MonthViewPagerAdapter.MONTH);

    String monthName = new DateFormatSymbols().getMonths()[month];
    TextView label = (TextView) contentView.findViewById(R.id.textViewLabel);
    label.setText(monthName);

    return contentView;
  }
}