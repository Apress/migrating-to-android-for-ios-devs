package com.pdachoice.hybridapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ScreenOneFragment extends Fragment {
  private View contentView;
  private WebView mWebView;

  @SuppressLint("SetJavaScriptEnabled")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screenone_fragment, container,
        false);

    setHasOptionsMenu(true); // need to enable it explicitly

    // WebView
    mWebView = (WebView) contentView.findViewById(R.id.webView);
    mWebView.loadUrl("http://pdachoice.com/me/webview/index.html");

    WebSettings webSettings = mWebView.getSettings();
    webSettings.setJavaScriptEnabled(true);

    WebChromeClient webChromeClient = new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int progress) {
        getActivity().setProgress(progress * 100);
      }
    };

    mWebView.setWebChromeClient(webChromeClient);

    // WebViewClient webViewClient = new WebViewClient() {
    // @Override
    // public void onReceivedError(WebView view, int errorCode,
    // String description, String failingUrl) {
    // Toast.makeText(getActivity(), "Error!" + description,
    // Toast.LENGTH_SHORT).show();
    // }
    // };
    // mWebView.setWebViewClient(webViewClient);

    WebViewClient webViewClient = new MyWebViewClient(getActivity());
    mWebView.setWebViewClient(webViewClient);

    mWebView.addJavascriptInterface(new Object() {
      @JavascriptInterface
      public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
      }
    }, "HybridApp");

    return contentView;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Log.d("MainActivity", "onOptionsItemSelected: " + item.getItemId());

    switch (item.getItemId()) {
    case R.id.actionBack:
      doGoBack();
      break;
    case R.id.actionFwd:
      doGoForward();
      break;
    case R.id.actionHome:
      injectJsCode("showPage('pgHome')");
      break;
    case R.id.actionMe:
      injectJsCode("showPage('pgme')");
      break;
    default:
      break;
    }

    return super.onOptionsItemSelected(item);
  }

  private void injectJsCode(String jsCode) {
    mWebView.loadUrl("javascript:" + jsCode);
  }

  // Go Back to previous page or reload
  private void doGoBack() {
    if (mWebView.canGoBack()) {
      mWebView.goBack();
    } else {
      mWebView.reload();
    }
  }

  // Go Forward or reload
  private void doGoForward() {
    if (mWebView.canGoForward()) {
      mWebView.goForward();
    } else {
      mWebView.reload();
    }
  }

}