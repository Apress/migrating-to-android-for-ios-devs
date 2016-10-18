package com.pdachoice.usesystemapps;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

//extend IntentService which is a subclass of Service
public class HelloService extends IntentService {
  private static final String tag = "HelloService";

  public HelloService() {
    super("HelloService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Log.d("HelloService", "onHandleIntent");
    // executed in background thread, not UI thread
    String fname = intent.getExtras().getString("fname");
    String lname = intent.getExtras().getString("lname");
//    sendGlobalBroadcast(fname, lname);
    sendLocalBroadcast(fname, lname);
  }
  
  private void sendGlobalBroadcast(String fname, String lname) {
    // create a new Implicit Intent and set the action intent-filter
    Intent broadcastIntent = new Intent();
    broadcastIntent.setAction("com.pdachoice.usesystemapps.Hello");

    // bundle "Extra" data to send to broadcast receivers
    broadcastIntent.putExtra("name", String.format("Hi, %s %s!", fname, lname));
    this.sendBroadcast(broadcastIntent);

    // specify permission
//    String permission = "com.pdachoice.usesystemapps.HELLO_PERMISSION";
//    this.sendBroadcast(broadcastIntent, permission);
  }
  
  private void sendLocalBroadcast(String fname, String lname) {
    Intent broadcastIntent = new Intent();
    broadcastIntent.setAction("com.pdachoice.usesystemapps.Hello");

    // bundle "Extra" data to send to broadcast receivers
    broadcastIntent.putExtra("name", String.format("Hi, %s %s!", fname, lname));
    
    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
    manager.sendBroadcast(broadcastIntent);
  }
  
}
