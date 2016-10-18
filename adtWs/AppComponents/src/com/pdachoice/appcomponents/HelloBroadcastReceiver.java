package com.pdachoice.appcomponents;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

// extends BroadcastReceiver
public class HelloBroadcastReceiver extends BroadcastReceiver {

  private static final String tag = "HelloReceiver";
  static String AUTHORITY = "com.pdachoice.appcomponents.HelloContentProvider";
  static String PATH = "DummyDbHelper"; // optional

  @Override
  // BroadcastReceiver lifecycle entry point
  public void onReceive(Context context, Intent intent) {
    String hello = intent.getExtras().getString("name");
    Log.w(tag, "onReceive: " + hello);

    insertIntoDummyProvider(context);
    retrieveFromDummyProvider(context);
  }

  private void insertIntoDummyProvider(Context context) {
    ContentResolver resolver = context.getContentResolver();

    String s = String.format("content://%s/%s", AUTHORITY, PATH);
    Uri uri = Uri.parse(s);

    ContentValues values = new ContentValues();
    values.put(DummyDbHelper.COLUMN_NAME, "dummy test data");
    resolver.insert(uri, values);
  }

  private void retrieveFromDummyProvider(Context context) {
    ContentResolver resolver = context.getContentResolver();

    String s = String.format("content://%s", AUTHORITY);
    Uri uri = Uri.parse(s);
    Cursor cursor = resolver.query(uri, null, null, null, null);

    // TODO do something more meaningful
    cursor.moveToLast();
    String key = cursor.getString(0);
    String value = cursor.getString(1);
    String mimeType = resolver.getType(uri);
    String txt = String.format("%s-%s \n(MIME: %s)", key, value, mimeType); 
    
    Toast.makeText(context, txt, Toast.LENGTH_LONG).show();
  }
}
