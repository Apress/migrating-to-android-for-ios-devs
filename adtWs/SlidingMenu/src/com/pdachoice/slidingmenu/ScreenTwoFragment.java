package com.pdachoice.slidingmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ScreenTwoFragment extends Fragment implements OnClickListener{
  private View contentView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screentwo_fragment, container,
        false);
    
    return contentView;
  }
  
  @Override
  public void onClick(View v) {
     ScreenThreeFragment frag = new ScreenThreeFragment();
     ((MainActivity) getActivity()).pushViewController(frag, true);
  }

}