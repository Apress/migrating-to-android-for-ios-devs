package com.pdachoice.mastergriddetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pdachoice.mastergriddetail.R;

public class DetailFragment extends Fragment {
  private View contentView;
  private TextView textViewTimestamp;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.detail_fragment, container, false);

    Bundle args = this.getArguments();
    String ts = args.getString("ts");
    textViewTimestamp = (TextView) contentView
        .findViewById(R.id.textViewTimestamp);
    textViewTimestamp.setText(ts);

    return contentView;
  }

  @Override
  public void onResume() {
    super.onResume();
    getActivity().setTitle("Item Detail"); // or Master List
  }

}
