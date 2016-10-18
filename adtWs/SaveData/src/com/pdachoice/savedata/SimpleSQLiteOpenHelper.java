package com.pdachoice.savedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SimpleSQLiteOpenHelper extends SQLiteOpenHelper {

  private static final String DB_NAME = "simple.db";
  private static final int DB_VERSION = 1;

  static final String TABLE_NAME = "person";
  static final String COLUMN_PKEY = "uid";
  static final String COLUMN_NAME = "name";

  public SimpleSQLiteOpenHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION); // create SQLite database
  }

  @Override
  public void onCreate(SQLiteDatabase db) { // 2. create table(s)
    Log.d("SimpleSQLiteOpenHelper", "onCreate");
    
    String sql = String.format(
        "create table %s (%s INTEGER PRIMARY KEY, %s TEXT);", TABLE_NAME,
        COLUMN_PKEY, COLUMN_NAME);
    db.execSQL(sql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.d("SimpleSQLiteOpenHelper", "onUpgrade");
    // For table schema changes.

    // you can choose to drop old table if you don't need to preserve old data
    String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
    db.execSQL(sql);
    onCreate(db);

    // Or, those typical data migration code ...
  }

  public void createRecord(String data) {
    Log.d("SimpleSQLiteOpenHelper", "createRecord");
    SQLiteDatabase db = this.getWritableDatabase();

    // Create a new map of values, where column names are the keys
    ContentValues values = new ContentValues();
    values.put(SimpleSQLiteOpenHelper.COLUMN_NAME, data);

    // Insert the new row, returning the primary key value of the new row
    long rowId = db.insert(SimpleSQLiteOpenHelper.TABLE_NAME, "N/A", values);
    Log.d("rowId: ", "" + rowId);
  }

  public String retrieveMostRecentNameColumn() {
    Log.d("SimpleSQLiteOpenHelper", "retrieveMostRecentNameColumn");
    SQLiteDatabase db = this.getWritableDatabase();

    String[] columns = null; // all columns, same as {"*"}
    String whereClause = null; // for all records
    String[] whereArgs = null; // to replace an ? in whereClause 
    String groupBy = null, having = null, orderBy = null;

    Cursor cursor = db.query(TABLE_NAME, columns, whereClause, whereArgs,
        groupBy, having, orderBy);

    boolean notEmpty = cursor.moveToLast();
    String result = null;
    if(notEmpty == true) {
      int columnIndex = cursor.getColumnIndex(SimpleSQLiteOpenHelper.COLUMN_NAME);
      result = cursor.getString(columnIndex);
    }
    
    return result;
  }

  public void deleteRecord(String name) {
    Log.d("SimpleSQLiteOpenHelper", "deleteRecord");

    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(SimpleSQLiteOpenHelper.TABLE_NAME, SimpleSQLiteOpenHelper.COLUMN_NAME + "=?", new String[] { name });

  }
}