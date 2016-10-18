package com.pdachoice.rentalroi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

public class EditTextViewFragment extends Fragment {
  private View contentView;

  public int tag;
  public String header;
  public String text;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.edittextview_fragment, container,
        false);
    setHasOptionsMenu(true); // enable Option Menu.

    tfEditText = (EditText) contentView.findViewById(R.id.tfEditText);

    // this.tfEditText.getText = this.src.text;
    this.tfEditText.setText(this.text);
    
    return contentView;
  }

  @Override
  public void onResume() {
    super.onResume();
    ((MainActivity) getActivity()).slideIn(contentView, MainActivity.SLIDE_UP); // or SLIDE_LEFT

    viewDidAppear(true);
    getActivity().setTitle(header);
    
    EasyTracker tracker = EasyTracker.getInstance(getActivity());
    tracker.set(Fields.SCREEN_NAME, this.getClass().getSimpleName());
    tracker.send(MapBuilder.createAppView().build());
  }
  
  @Override
  public void onPause() {
    super.onPause();
    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(tfEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.edittextview_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    doSave(null);
    return true;
  }

  // from .h
  // @interface EditTextViewController : UIViewController <EditTextDelegate>
  public EditText tfEditText;

  public EditTextViewControllerDelegate delegate;
  // inner interface
  interface EditTextViewControllerDelegate {
    public void textEditControllerDidFinishEditText(
        EditTextViewFragment controller, String text);

    public void textEditControllerDidCancel(EditTextViewFragment controller);
  }

  // TODO: from EditTextViewController.m
  //
  // SlEditTextViewController.m
  // RentalROI
  //
  // Created by Sean on 1/28/14.
  // Copyright (c) 2014 PdaChoice. All rights reserved.
  //

  private void viewDidLoad() {
    // [super viewDidLoad];
    // Do any additional setup after loading the view.

    // [[NSNotificationCenter defaultCenter] addObserver:this
    // selector:@selector(keyboardAppeared:)
    // name:UIKeyboardDidShowNotification
    // object:nil];
    // this.navBar.topItem.title = this.header;
  }

  private void viewDidAppear(boolean animated) {
    // [super viewDidAppear:animated];
    // this.tfEditText.becomeFirstResponder();
    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(tfEditText, InputMethodManager.SHOW_IMPLICIT);    
    tfEditText.selectAll();
  }

  // private void keyboardAppeared: (NSNotification*)notification {
  // // SLLOG;
  // NSDictionary* keyboardInfo = [notification userInfo];
  // NSValue* keyboardFrameBegin = [keyboardInfo
  // valueForKey:UIKeyboardFrameBeginUserInfoKey];
  // CGRect keyboardFrameBeginRect = [keyboardFrameBegin CGRectValue];
  //
  // CGFloat keyboardH = MIN(keyboardFrameBeginRect.size.width,
  // keyboardFrameBeginRect.size.height);
  //
  // CGRect screenRect = [[UIScreen mainScreen] bounds];
  //
  // UIInterfaceOrientation orientation = [UIApplication
  // sharedApplication].statusBarOrientation;
  //
  // CGFloat screenHeight;
  // if (UIInterfaceOrientationIsLandscape(orientation)) {
  // screenHeight = screenRect.size.width;
  // } else {
  // screenHeight = screenRect.size.height;
  // }
  //
  // CGFloat h = screenHeight - keyboardH;
  //
  // CGRect tfRect = this.tfEditText.frame;
  //
  // [UIView animateWithDuration:0.1 animations:^(void) {
  // CGRect newRect = CGRectMake(tfRect.origin.x, h/2, tfRect.size.width,
  // tfRect.size.height);
  // this.tfEditText.frame = newRect;
  // [this.tfEditText selectAll:this];
  // }];
  // }

  private void didReceiveMemoryWarning() {
    // [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
  }

  public void doCancel(Object sender) {
    ((MainActivity)getActivity()).popViewController();
    if(delegate != null) {
      this.delegate.textEditControllerDidCancel(this);
    }
  }

  public void doSave(Object sender) {
    String returnText = this.tfEditText.getText().toString();
    if(delegate != null) {
      this.delegate.textEditControllerDidFinishEditText(this, returnText);
    }
  }

}