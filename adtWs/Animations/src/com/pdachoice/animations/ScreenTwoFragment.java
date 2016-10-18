package com.pdachoice.animations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.pdachoice.animations.R;

public class ScreenTwoFragment extends Fragment implements OnClickListener{
  private View contentView;
  private View nextButton;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screentwo_fragment, container,
        false);
    
    nextButton = contentView.findViewById(R.id.buttonNext);
    nextButton.setOnClickListener(this); // Delegate button OnClick events 

    ((MainActivity)getActivity()).applyViewAnimation(contentView);
//  ((MainActivity)getActivity()).applyPropertyAnimation(contentView);

    return contentView;
  }
    
  @Override
  public void onClick(View v) {
     ScreenThreeFragment frag = new ScreenThreeFragment();
     ((MainActivity) getActivity()).pushViewController(frag, true);
  }

}