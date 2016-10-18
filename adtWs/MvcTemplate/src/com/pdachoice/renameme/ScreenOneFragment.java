package com.pdachoice.renameme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

public class ScreenOneFragment extends Fragment implements OnClickListener {
  private View contentView;
  private View nextButton;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screenone_fragment, container,
        false);
    
    nextButton = contentView.findViewById(R.id.buttonNext);
    nextButton.setOnClickListener(this); // Delegate button OnClick events 
    return contentView;
  }

  @Override
  public void onResume() {
      super.onResume();

      EasyTracker tracker =  EasyTracker.getInstance(getActivity());
      tracker.set(Fields.SCREEN_NAME, this.getClass().getSimpleName());
      tracker.send(MapBuilder.createAppView().build());
  }
  @Override
  public void onClick(View v) {
    ((MainActivity)getActivity()).pushViewController(new ScreenTwoFragment(), true);
    
  }
  
  // provide the interface implementation
}