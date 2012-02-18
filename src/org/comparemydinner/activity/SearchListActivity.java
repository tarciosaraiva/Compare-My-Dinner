package org.comparemydinner.activity;

import static android.content.Intent.ACTION_SEARCH;

import org.comparemydinner.model.JSONRecipeResponse;
import org.comparemydinner.model.JSONSearchResponse;
import org.comparemydinner.model.Recipe;
import org.comparemydinner.model.Recipes;
import org.comparemydinner.service.GetRecipeService;
import org.comparemydinner.service.SearchRecipeService;
import org.comparemydinner.task.BaseAsyncTask;
import org.comparemydinner.util.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

public class SearchListActivity extends ListActivity implements OnItemClickListener {

  private static final String TAG = "SearchActivity";

  private static final int PROGRESS_DIALOG = 1;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search);

    handleIntent(getIntent());

    ListView lv = getListView();
    lv.setTextFilterEnabled(true);
    lv.setOnItemClickListener(this);
  }

  @Override
  protected void onNewIntent(final Intent intent) {
    Log.d(TAG, "onNewIntent");

    setIntent(intent);
    handleIntent(intent);
  }

  private void handleIntent(final Intent intent) {
    if (ACTION_SEARCH.equals(intent.getAction())) {
      Log.d(TAG, "Searching for food");
      final String query = intent.getStringExtra(SearchManager.QUERY);
      new SearchProgressTask(this, PROGRESS_DIALOG).execute(query);
    }
  }

  private void processDisplayFor(final Recipes recipes) {
    String[] columns = { "_id", "name", "descr" };
    int[] viewsToBind = { R.id.food_id, R.id.food_name, R.id.food_description };

    MatrixCursor cursor = new MatrixCursor(columns);

    for (Recipe recipe : recipes.getRecipe()) {
      cursor.addRow(recipe.getColumnValuesForCursor());
    }

    setListAdapter(new SimpleCursorAdapter(this, R.layout.list_item, cursor, columns, viewsToBind));
  }

  @Override
  protected Dialog onCreateDialog(final int id) {
    Dialog dialog = null;

    switch (id) {
      case PROGRESS_DIALOG:
        dialog = ProgressDialog.show(SearchListActivity.this, "", "Querying...", true);
        break;
      default:
        break;
    }

    return dialog;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    new GetRecipeProgressTask(this, PROGRESS_DIALOG).execute(String.valueOf(id));
  }

  private void processClickFor(final Recipe recipe) {
    Utils.goHome(SearchListActivity.this, recipe);
  }

  // class for searching
  // 1) params 2) progress 3) results
  class SearchProgressTask extends BaseAsyncTask<JSONSearchResponse> {

    public SearchProgressTask(Activity activity, int dialogId) {
      super(activity, dialogId);
    }

    @Override
    protected JSONSearchResponse doSearch(final String query) {
      JSONSearchResponse response = null;
      final String jsonMsg = new SearchRecipeService().execute(query);

      try {
        response = new Gson().fromJson(jsonMsg, JSONSearchResponse.class);
      } catch (Exception e) {
        Log.e(TAG, e.getMessage());
      }

      return response;
    }

    @Override
    protected void postProcessAfterDialogRemoval(final JSONSearchResponse result) {
      if (null != result) {
        processDisplayFor(result.getRecipes());
      } else {
        Toast.makeText(getApplication(), "Sorry, could not obtain results!", Toast.LENGTH_SHORT)
            .show();
      }
    }

  }

  // sub class to get the recipe
  class GetRecipeProgressTask extends BaseAsyncTask<JSONRecipeResponse> {

    public GetRecipeProgressTask(Activity activity, int dialogId) {
      super(activity, dialogId);
    }

    @Override
    protected JSONRecipeResponse doSearch(final String query) {
      JSONRecipeResponse response = null;
      final String jsonMsg = new GetRecipeService().execute(query);

      try {
        response = new Gson().fromJson(jsonMsg, JSONRecipeResponse.class);
      } catch (Exception e) {
        Log.e(TAG, e.getMessage());
      }

      return response;
    }

    @Override
    protected void postProcessAfterDialogRemoval(final JSONRecipeResponse result) {
      if (null != result) {
        processClickFor(result.getRecipe());
      } else {
        Toast.makeText(getApplication(), "Sorry, could not obtain results!", Toast.LENGTH_SHORT)
            .show();
      }
    }

  }
}
