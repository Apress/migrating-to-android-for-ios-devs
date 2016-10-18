package com.pdachoice.mobilesearch;

import android.content.SearchRecentSuggestionsProvider;

public class MyProvider extends SearchRecentSuggestionsProvider {
   public final static String AUTHORITY = MyProvider.class.getName();
   public final static int MODE = DATABASE_MODE_QUERIES;

   public MyProvider() {
       setupSuggestions(AUTHORITY, MODE);
   }
}
