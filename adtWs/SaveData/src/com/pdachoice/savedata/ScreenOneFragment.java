package com.pdachoice.savedata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ScreenOneFragment extends Fragment {
  private View contentView;
  private EditText editTextInput;

  private static final String PREFS_NAME = "MyPrefs";
  private static final int MODE = Context.MODE_PRIVATE; // MODE_WORLD_WRITEABLE
  private static final String KEY_NAME = "name";

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screenone_fragment, container,
        false);

    setHasOptionsMenu(true);

    editTextInput = (EditText) contentView.findViewById(R.id.editTextInput);
    String data = null;
    // data = retrieveSharedPref(KEY_NAME);
    // Person obj = (Person) retrieveFromFile();
    // if (obj != null) {
    //   data = obj.getName();
    // }

    // create the JSONObject from the saved JSON string
    // data = retrieveSharedPref(KEY_NAME);
    // try {
    //   JSONObject jo = new JSONObject(data);
    //   data = jo.getString(KEY_NAME); // JSONObject getters: getString, etc ...
    // } catch (JSONException e) {
    //   e.printStackTrace();
    // }
    
    SimpleSQLiteOpenHelper sqlHelper = new SimpleSQLiteOpenHelper(getActivity());
    data = sqlHelper.retrieveMostRecentNameColumn();

    this.editTextInput.setText(data);

    return contentView;
  }

  @Override
  public void onStop() {
    super.onStop();
    String data = editTextInput.getText().toString();
    if (data != null && data.length() > 0) {
      // saveSharedPref(KEY_NAME, data);
      // saveObjectToFile(new Person(data));

      // create JSONObject and use the generic setter, put(...)
      // try {
      //   JSONObject jo = new JSONObject();
      //   jo.put(KEY_NAME, data);
      //
      //   String jsonStr = jo.toString();
      //   saveSharedPref(KEY_NAME, jsonStr);
      //
      // } catch (JSONException e) {
      //   // TODO Auto-generated catch block
      // }
      
      SimpleSQLiteOpenHelper sqlHelper = new SimpleSQLiteOpenHelper(getActivity());
      sqlHelper.createRecord(data);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Log.d(">>>onOptionsItemSelected", item.getTitle().toString());
    // this.deleteSharedPref(KEY_NAME);
    // this.deleteObjectFromFile();
    SimpleSQLiteOpenHelper sqlHelper = new SimpleSQLiteOpenHelper(getActivity());
    sqlHelper.deleteRecord(KEY_NAME);
    editTextInput.setText(null);
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main, menu);
  }

  // /////// SharedPreferences usage ///////////////////
  private void saveSharedPref(String key, String data) {
    // get a handle to SharedPreferences object from Context, i.e., Activity
    SharedPreferences sharedPrefs = getActivity().getSharedPreferences(
        PREFS_NAME, MODE);
    // We need an Editor object to make preference changes.
    SharedPreferences.Editor editor = sharedPrefs.edit();
    // changes are cached in Editor first.
    editor.putString(key, data);
    // Commit all the changes from Editor all at once.
    editor.commit();
  }

  private String retrieveSharedPref(String key) {
    // get a handle to SharedPreferences object from Context.
    SharedPreferences sharedPrefs = getActivity().getSharedPreferences(
        PREFS_NAME, MODE);
    // use appropriate API to get the value by key.
    String data = sharedPrefs.getString(key, "");
    return data;
  }

  private void deleteSharedPref(String key) {
    SharedPreferences sharedPrefs = getActivity().getSharedPreferences(
        PREFS_NAME, MODE);

    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.remove(key);
    editor.commit();
  }

  // ///////////// File Storage ////////////////////////
  private static final String FILE_NAME = "person.ser";
  private void saveObjectToFile(Serializable obj) {

    // use File API to write to file
    ObjectOutputStream out = null;
    try {
      // use File API to write to file
      FileOutputStream fileOut = getActivity().openFileOutput(FILE_NAME, MODE);
      out = new ObjectOutputStream(fileOut);

      out.writeObject(obj);
      out.close();
      fileOut.close();
    } catch (IOException i) {
      i.printStackTrace();
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException i) {
          i.printStackTrace();
        }
      }
    }    
  }

  private Object retrieveFromFile() {
    Object obj = null;
    try {
      // use File API to read bytes from File
      FileInputStream fileIn = getActivity().openFileInput(FILE_NAME);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      obj = in.readObject();
      in.close();
      fileIn.close();
    } catch (IOException i) {
      Log.w("retrieveFromFile", "probably file not found, not saved yet.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return obj;
  }

  private void deleteObjectFromFile() {
    // simply delete the file, or write empty content
    File dir = getActivity().getFilesDir();
    File f = new File(dir, FILE_NAME);
    boolean b = f.delete();

    Log.d("File.delete: ", b ? "deleted" : "failed");
  }

}