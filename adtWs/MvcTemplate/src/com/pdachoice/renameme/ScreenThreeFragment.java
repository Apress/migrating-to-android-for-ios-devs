package com.pdachoice.renameme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.pdachoice.renameme.R;

public class ScreenThreeFragment extends Fragment {
  private View contentView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screenthree_fragment, container,
        false);
    return contentView;
  }
  
  @Override
  public void onResume() {
      super.onResume();

      EasyTracker tracker =  EasyTracker.getInstance(getActivity());
      tracker.set(Fields.SCREEN_NAME, this.getClass().getSimpleName());
      tracker.send(MapBuilder.createAppView().build());
  }

}