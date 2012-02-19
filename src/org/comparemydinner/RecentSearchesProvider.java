package org.comparemydinner;

import android.content.SearchRecentSuggestionsProvider;

public class RecentSearchesProvider extends SearchRecentSuggestionsProvider {

  public final static String AUTHORITY = "org.comparemydinner.RecentSearchesProvider";

  public final static int MODE = DATABASE_MODE_QUERIES;

  public RecentSearchesProvider() {
    setupSuggestions(AUTHORITY, MODE);
  }

}
