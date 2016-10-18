package com.pdachoice.usesystemapps;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ScreenOneFragment extends Fragment {
  private View contentView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screenone_fragment, container,
        false);

    setHasOptionsMenu(true);

    return contentView;
  }
  

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Log.d(">>>onOptionsItemSelected", item.getTitle().toString());
    switch (item.getItemId()) {
    case R.id.actionPhone:
      usePhone();
      break;
    case R.id.actionSms:
      useSms();
      break;
    case R.id.actionEmail:
      useEmail();
      break;
    case R.id.actionMedia:
      useMediaPlayer();
      break;
    default:
      break;
    }

    return super.onOptionsItemSelected(item);
  }

  private void usePhone() {
    Intent intent = new Intent(Intent.ACTION_DIAL); // or, try ACTION_CALL
    String uri = "tel:12345";
    intent.setData(Uri.parse(uri));
    getActivity().startActivity(intent);
  }

  private void useSms() {
    try {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setType("vnd.android-dir/mms-sms");
      intent.putExtra("sms_body", "Hello World!");
      intent.putExtra("address", "0123456789");
      startActivity(intent);
    } catch (ActivityNotFoundException e) {
      // some KitKat devices only have Google Hangouts without the
      // SMS messaging app. The following intent is more generic

      Intent sendIntent = new Intent(Intent.ACTION_SEND);
      sendIntent.setType("text/plain");
      sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello World!");

      startActivity(sendIntent);
    }
  }

  private void useEmail() {
    Intent intent = new Intent(Intent.ACTION_SENDTO);
    Uri uri = Uri.parse("mailto:someone@gmail.com");
    intent.setData(uri);
    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
    intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
    startActivity(Intent.createChooser(intent, "Choose a Mail client:"));
  }

  private void useMediaPlayer() {
    Uri uri = Uri.parse("http://www.pdachoice.com/me/sample_mpeg4.mp4");
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setDataAndType(uri, "video/mp4");
    startActivity(intent);
  }

  void useHelloService() {
    ComponentName component = new ComponentName("com.pdachoice.usesystemapps",
        "com.pdachoice.usesystemapps.HelloService");

    Intent intent = new Intent();
    intent.setComponent(component);

    intent.putExtra("fname", "Mobile");
    intent.putExtra("lname", "Developer");

    // getActivity().startService(intent);
    // Create PendingIntent to wrap the original intent with extra parms
    try {
      PendingIntent pendingIntent = PendingIntent.getService(getActivity(), 0,
          intent, PendingIntent.FLAG_UPDATE_CURRENT);

      pendingIntent.send();
    } catch (CanceledException e) {
      e.printStackTrace();
    }
  }

}