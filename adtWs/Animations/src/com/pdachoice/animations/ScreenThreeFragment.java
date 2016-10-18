package com.pdachoice.animations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pdachoice.animations.R;

public class ScreenThreeFragment extends Fragment {
  private View contentView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screenthree_fragment, container,
        false);
    
    ((MainActivity)getActivity()).applyViewAnimation(contentView);
//  ((MainActivity)getActivity()).applyPropertyAnimation(contentView);

    return contentView;
  }
}