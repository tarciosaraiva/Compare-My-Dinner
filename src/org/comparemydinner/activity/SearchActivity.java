package org.comparemydinner.activity;

import static android.content.Intent.ACTION_SEARCH;

import org.comparemydinner.model.JSONResponse;
import org.comparemydinner.model.Recipe;
import org.comparemydinner.model.Recipes;
import org.comparemydinner.service.SearchRecipeService;
import org.json.JSONArray;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

public class SearchActivity extends ListActivity {

  private static final String TAG = "SearchActivity";

  private static final int PROGRESS_DIALOG = 1;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search);

    Log.d(TAG, "onCreate");

    handleIntent(this.getIntent());
  }

  @Override
  protected void onNewIntent(final Intent intent) {
    Log.d(TAG, "onNewIntent");

    setIntent(intent);
    handleIntent(intent);
  }

  private void handleIntent(final Intent intent) {
    if (ACTION_SEARCH.equals(intent.getAction())) {
      final String query = intent.getStringExtra(SearchManager.QUERY);
      new SearchProgressTask().execute(query);
    }
  }

  private void processDisplayFor(final Recipes recipes) {
    setListAdapter(new ListingArrayAdapter(this, R.layout.list_item, recipes.getRecipe()));
  }

  @Override
  protected Dialog onCreateDialog(final int id) {
    Dialog dialog = null;

    switch (id) {
      case PROGRESS_DIALOG:
        dialog = ProgressDialog.show(SearchActivity.this, "", "Querying...", true);
        break;
      default:

        break;
    }

    return dialog;
  }

  // class for listing
  class ListingArrayAdapter extends ArrayAdapter<Recipe> {

    public ListingArrayAdapter(final Context context, final int textViewResourceId,
        final Recipe[] objects) {
      super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
      return super.getView(position, convertView, parent);
    }

  }

  // class for searching
  class SearchProgressTask extends AsyncTask<String, JSONArray, JSONResponse> {

    @Override
    protected JSONResponse doInBackground(String... params) {
      return doSearch(params[0]);
    }

    private JSONResponse doSearch(final String query) {
      String jsonMsg = null;
      JSONResponse response = null;
      jsonMsg = new SearchRecipeService().execute(query);

      try {
        response = new Gson().fromJson(jsonMsg, JSONResponse.class);
      } catch (Exception e) {
        Log.e(TAG, e.getMessage());
      }

      return response;
    }

    @Override
    protected void onPreExecute() {
      showDialog(PROGRESS_DIALOG);
    }

    @Override
    protected void onPostExecute(final JSONResponse result) {
      removeDialog(PROGRESS_DIALOG);

      if (null != result) {
        processDisplayFor(result.getRecipes());
      } else {
        Toast.makeText(getApplication(), "Sorry, could not obtain results!", Toast.LENGTH_SHORT)
            .show();
      }
    }

  }

}
