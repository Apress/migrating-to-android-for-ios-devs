package com.pdachoice.usesystemapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DummyDbHelper extends SQLiteOpenHelper {

  private static final String DB_NAME = "DummyDbHelper.db";
  private static final int DB_VERSION = 1;

  static final String TABLE_NAME = "DummyDbHelper";
  static final String COLUMN_PKEY = "uid";
  static final String COLUMN_NAME = "name";

  public DummyDbHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION); // create SQLite database
  }

  @Override
  public void onCreate(SQLiteDatabase db) { // 2. create table(s)
    String sql = String.format(
        "create table %s (%s INTEGER PRIMARY KEY, %s TEXT);", TABLE_NAME,
        COLUMN_PKEY, COLUMN_NAME);
    db.execSQL(sql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.d("DummyDbHelper", "onUpgrade");
    // For table schema changes.

    // you can choose to drop old table if you don't need to preserve old data
    String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
    db.execSQL(sql);
    onCreate(db);

    // Or, those typical data migration code ...
  }
}