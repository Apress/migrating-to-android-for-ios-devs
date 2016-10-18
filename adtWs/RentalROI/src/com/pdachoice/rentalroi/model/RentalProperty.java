package com.pdachoice.rentalroi.model;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class RentalProperty implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 2931547086803719756L;
  
  public double purchasePrice;
  public double loanAmt;
  public double interestRate;
  public int numOfTerms;
  public double escrow;
  public double extra;
  public double expenses;
  public double rent;

  public static final String KEY_AMO_SAVED = "KEY_AMO_SAVED";
  public static final String KEY_PROPERTY = "KEY_PROPERTY";
  private static RentalProperty _sharedInstance = null;

  private RentalProperty() {
    super();
    this.interestRate = 5.0f;
    this.numOfTerms = 30;

  }

  public static RentalProperty sharedInstance() {
    if (_sharedInstance == null) {
      _sharedInstance = new RentalProperty();
    }
    return _sharedInstance;
  }

  public String getAmortizationPersistentKey() {    
    String amortizationPersistentKey = String.format("%.2f-%.2f-%d-%.2f",
        this.loanAmt, this.interestRate, this.numOfTerms, this.extra);
    return amortizationPersistentKey;
  }

  public JSONArray getSavedAmortization(Context activity) {
//  NSUserDefaults* userDefaults = [NSUserDefaults standardUserDefaults];
//  NSString* savedKey =[userDefaults stringForKey: KEY_AMO_SAVED];
//  NSString* amortizationPersistentKey = [self getAmortizationPersistentKey];
//  if (savedKey.length > 0 && [savedKey isEqualToString:amortizationPersistentKey]) {
//     return [userDefaults arrayForKey:savedKey];
//  } else {
//     return nil;
//  }
    
    String savedKey = retrieveSharedPref(KEY_AMO_SAVED, activity);
    String amortizationPersistentKey = this.getAmortizationPersistentKey();
    if(savedKey.length() > 0 && savedKey.equals(amortizationPersistentKey)) {
      String jsonArrayString = retrieveSharedPref(savedKey, activity);
      try {
        return new JSONArray(jsonArrayString);
      } catch (JSONException e) {
        return null;
      }
    } else {
       return null;
    }  
  }

  public void saveAmortization(String data, Context activity) {
//  // only save one, remove prev first
//  NSUserDefaults* userDefaults = [NSUserDefaults standardUserDefaults];
//  NSString* amortizationPersistentKey = [self getAmortizationPersistentKey];
//  [userDefaults setObject:amortizationPersistentKey forKey:KEY_AMO_SAVED];
//  [userDefaults setObject:jsonOjb forKey:amortizationPersistentKey];
//  [userDefaults synchronize];

    String amortizationPersistentKey = this.getAmortizationPersistentKey();
    saveSharedPref(KEY_AMO_SAVED, amortizationPersistentKey, activity);
    saveSharedPref(amortizationPersistentKey, data, activity);
  }

  public boolean load(Context activity) {
//  NSUserDefaults* userDefaults = [NSUserDefaults standardUserDefaults];
//  NSDictionary* jo = [userDefaults objectForKey:KEY_PROPERTY];
//  if (jo == nil) {
//     return NO;
//  }
//  
//  self.purchasePrice = [[jo objectForKey:@"purchasePrice"] doubleValue];
//  self.loanAmt = [[jo objectForKey:@"loanAmt"] doubleValue];
//  self.interestRate = [[jo objectForKey:@"interestRate"] doubleValue];
//  self.numOfTerms = [[jo objectForKey:@"numOfTerms"] intValue];
//  self.escrow = [[jo objectForKey:@"escrow"] doubleValue];
//  self.extra = [[jo objectForKey:@"extra"] doubleValue];
//  self.expenses = [[jo objectForKey:@"expenses"] doubleValue];
//  self.rent = [[jo objectForKey:@"rent"] doubleValue];
//  return YES;
    
    String jostr = retrieveSharedPref(KEY_PROPERTY, activity);
    if(jostr == null) {
      return false;
    }
    
    try {
      JSONObject jo = new JSONObject(jostr);
      this.purchasePrice = jo.getDouble("purchasePrice");
      this.loanAmt = jo.getDouble("loanAmt");
      this.interestRate = jo.getDouble("interestRate");
      this.numOfTerms = jo.getInt("numOfTerms");
      this.escrow = jo.getDouble("escrow");
      this.extra = jo.getDouble("extra");
      this.expenses = jo.getDouble("expenses");
      this.rent = jo.getDouble("rent");
      return true;
    } catch (JSONException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void save(Context activity) {
//  NSDictionary* jo =  @{@"purchasePrice" : [NSNumber numberWithDouble:self.purchasePrice],
//                        @"loanAmt" : [NSNumber numberWithDouble:self.loanAmt],
//                        @"interestRate" : [NSNumber numberWithDouble:self.interestRate],
//                        @"numOfTerms" : [NSNumber numberWithDouble:self.numOfTerms],
//                        @"escrow" : [NSNumber numberWithDouble:self.escrow],
//                        @"extra" : [NSNumber numberWithDouble:self.extra],
//                        @"expenses" : [NSNumber numberWithDouble:self.expenses],
//                        @"rent" : [NSNumber numberWithDouble:self.rent]
//                        };
//  
//  NSUserDefaults* userDefaults = [NSUserDefaults standardUserDefaults];
//  [userDefaults setObject:jo forKey:KEY_PROPERTY];
//  [userDefaults synchronize];
    JSONObject jo = new JSONObject();
    
    try {
      jo.put("purchasePrice", purchasePrice);
      jo.put("loanAmt", loanAmt);
      jo.put("interestRate", interestRate);
      jo.put("numOfTerms", numOfTerms);
      jo.put("escrow", escrow);
      jo.put("extra", extra);
      jo.put("expenses", expenses);
      jo.put("rent", rent);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    this.saveSharedPref(KEY_PROPERTY, jo.toString(), activity);
  }

  ///////// SharedPreferences usage /////////////////
  private static final String PREFS_NAME = "MyPrefs";
  private static final int MODE = Context.MODE_PRIVATE; // MODE_WORLD_WRITEABLE

  public void saveSharedPref(String key, String data, Context activity) {
    // get a handle to SharedPreferences object from Context, i.e., Activity
    SharedPreferences sharedPrefs = activity.getSharedPreferences(
        PREFS_NAME, MODE);
    // We need an Editor object to make preference changes.
    SharedPreferences.Editor editor = sharedPrefs.edit();
    // changes are cached in Editor first.
    editor.putString(key, data);
    // Commit all the changes from Editor all at once.
    editor.commit();
  }

  public String retrieveSharedPref(String key, Context activity) {
    // get a handle to SharedPreferences object from Context.
    SharedPreferences sharedPrefs = activity.getSharedPreferences(
        PREFS_NAME, MODE);
    // use appropriate API to get the value by key.
    String data = sharedPrefs.getString(key, "");
    return data;
  }

  public void deleteSharedPref(String key, Context activity) {
    SharedPreferences sharedPrefs = activity.getSharedPreferences(
        PREFS_NAME, MODE);

    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.remove(key);
    editor.commit();
  }
  
}
