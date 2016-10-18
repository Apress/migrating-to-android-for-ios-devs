package com.pdachoice.hybridapp;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient {

   private Activity activity;
  // private ProgressBar progressBar;
  //
  // public MyWebViewClient(Context activity, ProgressBar progressBar) {
  // // TODO Auto-generated constructor stub
  // this.activity = activity;
  // this.progressBar = progressBar;
  // }

  public MyWebViewClient(Activity activity) {
    super();
    this.activity = activity;
  }

  @Override
  public void onReceivedError(WebView view, int errorCode, String description,
      String failingUrl) {
    // TODO: make the same toast
    Toast.makeText(activity, "Error!" + description, Toast.LENGTH_SHORT)
        .show();
  }

  // @Override
  // public void onPageStarted(WebView view, String url, Bitmap favicon) {
  // progressBar.setVisibility(View.VISIBLE);
  // }
  //
  // @Override
  // public void onPageFinished(WebView view, String url) {
  // progressBar.setVisibility(View.INVISIBLE);
  // }
}
