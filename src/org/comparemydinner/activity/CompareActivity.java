package org.comparemydinner.activity;

import static org.comparemydinner.util.Utils.PROGRESS_DIALOG;

import org.comparemydinner.model.Food;
import org.comparemydinner.model.JSONRecipeResponse;
import org.comparemydinner.service.GetFoodService;
import org.comparemydinner.task.BaseAsyncTask;
import org.comparemydinner.util.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

public class CompareActivity extends Activity {

  private static final String TAG = "CompareActivity";

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    final long foodOne = getIntent().getLongExtra("foodOne", -1);
    final long foodTwo = getIntent().getLongExtra("foodTwo", -1);

    new GetRecipesProgressTask(this, PROGRESS_DIALOG).execute(String.valueOf(foodOne));
    new GetRecipesProgressTask(this, PROGRESS_DIALOG).execute(String.valueOf(foodTwo));
  }

  @Override
  protected Dialog onCreateDialog(final int id) {
    return Utils.getProgressDialog(id, CompareActivity.this);
  }

  // sub class to get the recipe
  class GetRecipesProgressTask extends BaseAsyncTask<JSONRecipeResponse> {

    public GetRecipesProgressTask(Activity activity, int dialogId) {
      super(activity, dialogId);
    }

    @Override
    protected JSONRecipeResponse doSearch(final String query) {
      JSONRecipeResponse response = null;
      final String jsonMsg = new GetFoodService().execute(query);

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
        Food food = result.getFood();

        if (food != null) {
        }

      } else {
        Toast.makeText(getApplication(), "Sorry, could not obtain results!", Toast.LENGTH_SHORT)
            .show();
      }
    }
  }

}
