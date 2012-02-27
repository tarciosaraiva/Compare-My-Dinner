/*
  Copyright 2012 Tarcio Saraiva

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.comparemydinner.activity;

import static android.content.Intent.ACTION_SEARCH;
import static org.comparemydinner.util.Utils.MENU_ABOUT;
import static org.comparemydinner.util.Utils.MENU_FEEDBACK;
import static org.comparemydinner.util.Utils.MENU_NEW_SEARCH;
import static org.comparemydinner.util.Utils.PROGRESS_DIALOG;

import org.comparemydinner.R;
import org.comparemydinner.RecentSearchesProvider;
import org.comparemydinner.model.Food;
import org.comparemydinner.model.Foods;
import org.comparemydinner.model.JSONSearchResponse;
import org.comparemydinner.service.SearchFoodService;
import org.comparemydinner.task.BaseAsyncTask;
import org.comparemydinner.util.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class SearchListActivity extends ListActivity implements OnItemClickListener {

  private static final String TAG = "SearchActivity";

  private Button attributionBtn;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search);

    attributionBtn = (Button) findViewById(R.id.attributionBtn);
    Utils.configureAttributionButton(attributionBtn, this);

    handleIntent(getIntent());

    final ListView lv = getListView();
    lv.setTextFilterEnabled(true);
    lv.setOnItemClickListener(this);
  }

  @Override
  protected void onNewIntent(final Intent intent) {
    setIntent(intent);
    handleIntent(intent);
  }

  private void handleIntent(final Intent intent) {
    if (ACTION_SEARCH.equals(intent.getAction())) {

      final String query = intent.getStringExtra(SearchManager.QUERY);

      Log.d(TAG, Utils.buildStr("Searching for food [", query, "]"));

      final SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
          RecentSearchesProvider.AUTHORITY, RecentSearchesProvider.MODE);
      suggestions.saveRecentQuery(query, null);

      new SearchProgressTask(this, PROGRESS_DIALOG).execute(query);
    }
  }

  private void processDisplayFor(final Foods foods) {
    final String[] columns = { "_id", "name", "descr" };
    final int[] viewsToBind = { R.id.food_id, R.id.food_name, R.id.food_description };

    final MatrixCursor cursor = new MatrixCursor(columns);

    for (final Food food : foods.getFood()) {
      cursor.addRow(food.getColumnValuesForCursor());
    }

    setListAdapter(new SimpleCursorAdapter(this, R.layout.list_item, cursor, columns, viewsToBind));
  }

  @Override
  protected Dialog onCreateDialog(final int id) {
    return Utils.getProgressDialog(id, "Searching...", SearchListActivity.this);
  }

  @Override
  public void onItemClick(final AdapterView<?> parent, final View view, final int position,
      final long id) {
    Utils.goHome(SearchListActivity.this, id, ((TextView) ((LinearLayout) view).getChildAt(1))
        .getText().toString());
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {
    super.onCreateOptionsMenu(menu);

    menu.add(0, MENU_ABOUT, 0, R.string.menu_about).setIcon(android.R.drawable.ic_menu_help);
    menu.add(0, MENU_NEW_SEARCH, 0, R.string.menu_new_search).setIcon(
        android.R.drawable.ic_menu_search);
    menu.add(0, MENU_FEEDBACK, 0, R.string.menu_feedback).setIcon(android.R.drawable.ic_menu_send);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case MENU_ABOUT:
        Utils.showAbout(this);
        return true;
      case MENU_NEW_SEARCH:
        onSearchRequested();
        return true;
      case MENU_FEEDBACK:
        Utils.invokeEmail(this);
        return true;
    }
    return false;

  }

  // class for searching
  // 1) params 2) progress 3) results
  class SearchProgressTask extends BaseAsyncTask<JSONSearchResponse> {

    public SearchProgressTask(final Activity activity, final int dialogId) {
      super(activity, dialogId);
    }

    @Override
    protected JSONSearchResponse doSearch(final String query) {
      JSONSearchResponse response = null;
      final String jsonMsg = new SearchFoodService().execute(query);

      Log.d(TAG, Utils.buildStr("Response: [", jsonMsg, "]"));

      try {
        response = new Gson().fromJson(jsonMsg, JSONSearchResponse.class);
      } catch (final Exception e) {
        response = new JSONSearchResponse();
        Log.e(TAG, e.getMessage());
      }

      return response;
    }

    @Override
    protected void postProcessAfterDialogRemoval(final JSONSearchResponse result) {
      if (null != result.getFoods() && result.getFoods().getTotal_results() > 0) {
        processDisplayFor(result.getFoods());
      } else {
        Toast.makeText(getApplication(), "Sorry, could not obtain results!", Toast.LENGTH_SHORT)
            .show();
        Utils.goHome(SearchListActivity.this, -1, null);
      }
    }

  }

}
