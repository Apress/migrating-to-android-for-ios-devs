package com.pdachoice.appcomponents;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class HelloContentProvider extends ContentProvider {

  private DummyDbHelper dbHelper;

  @Override
  public boolean onCreate() {
    // lifecycle callback. Typically, init a dbHelper instance
    dbHelper = new DummyDbHelper(getContext());
    return true;
  }

  @Override
  public String getType(Uri uri) {
    return ContentResolver.CURSOR_ITEM_BASE_TYPE; // MIME type for one row
  }

  @Override
  public Cursor query(Uri uri, String[] columns, String selection,
      String[] selectionArgs, String sortOrder) {

    return dbHelper.getReadableDatabase().query(DummyDbHelper.TABLE_NAME,
        columns, selection, selectionArgs, null, null, null);
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    long rowid = dbHelper.getWritableDatabase().insert(
        DummyDbHelper.TABLE_NAME, null, values);

    // content://com.authority/tablename/rowid
    String rowUri = String.format("content://%s/%s/%d",
        "com.pdachoice.appcomponents.HelloContentProvider",
        DummyDbHelper.TABLE_NAME, rowid);

    return Uri.parse(rowUri);
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    int count = dbHelper.getWritableDatabase().delete(DummyDbHelper.TABLE_NAME,
        selection, selectionArgs);

    return count;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
      String[] selectionArgs) {
    int count = dbHelper.getWritableDatabase().update(DummyDbHelper.TABLE_NAME,
        values, selection, selectionArgs);

    return count;
  }
}
