package com.pdachoice.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;

public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // setup the content view.
    setContentView(R.layout.activity_main);

    // b: Adding the first fragment to the navigation stack.
    pushViewController(new ScreenOneFragment(), false);
  }

  // to be called when you want to show the toFragments in Activity
  void pushViewController(Fragment toFragment, boolean addToStack) {
    // 1: Create a FragmentTransaction from FragmentManager via activity
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();

    // 2: tug in this toFragment into Activity ViewGroup
    transaction.replace(R.id.fragment_container, toFragment, toFragment
        .getClass().getSimpleName());

    if (addToStack) {
      // 3: add the transaction to the back stack so we can pop it out later
      transaction.addToBackStack(null);
    }

    // 4: commit the transaction.
    transaction.commit();
  }

  // Go back to previous screen.
  void popViewController() {
    FragmentManager manager = getSupportFragmentManager();
    manager.popBackStack();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    // do the needful, for example:
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      // Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
      // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
    }
  }

  void applyViewAnimation(View view) {
    AnimationSet screentransition = new AnimationSet(true);

    Animation fadeOut = new AlphaAnimation(0, 1);
    Animation rotate = new RotateAnimation(0f, 360f,
        Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);

    screentransition.setInterpolator(new BounceInterpolator());
    screentransition.addAnimation(fadeOut);
    screentransition.addAnimation(rotate);

    screentransition.setDuration(3000);

    // Animation screentransition = AnimationUtils.loadAnimation(this,
    // R.anim.screentransition);

    view.startAnimation(screentransition);
  }

  void applyPropertyAnimation(View view) {

    AnimatorSet screentransition = new AnimatorSet();
    screentransition.setInterpolator(new BounceInterpolator());
    screentransition.setDuration(2000).start();

    ValueAnimator alphaA = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
    ValueAnimator rotateA = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
    screentransition.play(alphaA).with(rotateA);

    // Animator screentransition = AnimatorInflater.loadAnimator(this,
    // R.animator.screentransition);

    screentransition.setTarget(view);
    screentransition.start();
  }

  void applyViewPropertyAnimation(View view) {
    ViewPropertyAnimator animator = view.animate();
    animator.setDuration(2000).setInterpolator(new BounceInterpolator())
        .alpha(1).rotation(360);
  }

}
