package org.comparemydinner.activity;

import static org.comparemydinner.util.Utils.PROGRESS_DIALOG;

import org.comparemydinner.model.Food;
import org.comparemydinner.model.JSONRecipeResponse;
import org.comparemydinner.service.GetFoodService;
import org.comparemydinner.task.BaseAsyncTask;
import org.comparemydinner.util.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends Activity implements OnClickListener {

  private static final String TAG = "MainActivity";

  private Button foodOne, foodTwo, compare;

  private TableRow foodCompareRow;

  private long foodOneId, foodTwoId;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Log.d(TAG, "Assigning buttons from XML view");

    foodOne = (Button) findViewById(R.id.button1);

    foodTwo = (Button) findViewById(R.id.button2);
    compare = (Button) findViewById(R.id.compareBtn);
    foodCompareRow = (TableRow) findViewById(R.id.foodCompareRow);

    foodOne.setOnClickListener(this);
    foodTwo.setOnClickListener(this);

    // implement a different listener for comparison
    compare.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        final Intent intent = new Intent(MainActivity.this, CompareActivity.class);
        intent.putExtra("recipeOne", foodOneId);
        intent.putExtra("recipeTwo", foodTwoId);

        startActivity(intent);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();

    long recipeId = getIntent().getLongExtra("recipeId", -1);

    Log.d(TAG, "Recipe ID: " + recipeId);

    if (recipeId > -1) {
      new GetRecipeProgressTask(this, PROGRESS_DIALOG).execute(String.valueOf(recipeId));
    }
  }

  @Override
  protected Dialog onCreateDialog(final int id) {
    return Utils.getProgressDialog(id, MainActivity.this);
  }

  @Override
  public void onClick(View v) {
    Log.d(TAG, "Requesting search activity");

    onSearchRequested();
  }

  // sub class to get the recipe
  class GetRecipeProgressTask extends BaseAsyncTask<JSONRecipeResponse> {

    public GetRecipeProgressTask(Activity activity, int dialogId) {
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
        Food food = result.getRecipe();

        if (food != null) {

          if (foodOneId <= 0) {
            foodOne.setEnabled(Boolean.FALSE);
            foodOne.setText(food.getFood_name());
            foodOneId = food.getFood_id();

            getIntent().putExtra("foodone", foodOneId);
          } else {
            foodTwo.setEnabled(Boolean.FALSE);
            foodTwo.setText(food.getFood_name());
            foodTwoId = food.getFood_id();

            getIntent().getExtras().putLong("foodtwo", foodTwoId);

            foodCompareRow.setVisibility(View.VISIBLE);
          }
        }

      } else {
        Toast.makeText(getApplication(), "Sorry, could not obtain results!", Toast.LENGTH_SHORT)
            .show();
      }
    }

  }

}