package org.comparemydinner.activity;

import static org.comparemydinner.util.Utils.PROGRESS_DIALOG;

import org.comparemydinner.model.Food;
import org.comparemydinner.model.JSONRecipeResponse;
import org.comparemydinner.model.Serving;
import org.comparemydinner.service.GetFoodService;
import org.comparemydinner.task.BaseAsyncTask;
import org.comparemydinner.util.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class CompareActivity extends Activity {

  private static final String TAG = "CompareActivity";

  private static final String CALS = "Cals";

  private static final String FAT = "Fat";

  private static final String CARBS = "Carbs";

  private static final String CHOL = "Cholesterol";

  private static final String PROT = "Protein";

  private static final int MENU_NEW = 0;

  private long foodOne, foodTwo;

  private TextView calsOne, calsTwo;

  private TextView carbsOne, carbsTwo;

  private TextView fatOne, fatTwo;

  private TextView cholOne, cholTwo;

  private TextView protOne, protTwo;

  private TextView servingSizeOne, servingSizeTwo;

  private TextView foodNameOne, foodNameTwo;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.comparison);

    // populate the elements
    calsOne = (TextView) findViewById(R.id.cals1);
    calsTwo = (TextView) findViewById(R.id.cals2);
    carbsOne = (TextView) findViewById(R.id.carbs1);
    carbsTwo = (TextView) findViewById(R.id.carbs2);
    fatOne = (TextView) findViewById(R.id.fat1);
    fatTwo = (TextView) findViewById(R.id.fat2);
    cholOne = (TextView) findViewById(R.id.chol1);
    cholTwo = (TextView) findViewById(R.id.chol2);
    protOne = (TextView) findViewById(R.id.prot1);
    protTwo = (TextView) findViewById(R.id.prot2);

    servingSizeOne = (TextView) findViewById(R.id.serving_descr1);
    servingSizeTwo = (TextView) findViewById(R.id.serving_descr2);

    foodNameOne = (TextView) findViewById(R.id.food1);
    foodNameTwo = (TextView) findViewById(R.id.food2);

    foodOne = getIntent().getLongExtra("foodOne", -1);
    foodTwo = getIntent().getLongExtra("foodTwo", -1);

    new GetRecipesProgressTask(this, PROGRESS_DIALOG).execute(String.valueOf(foodOne));
    new GetRecipesProgressTask(this, PROGRESS_DIALOG).execute(String.valueOf(foodTwo));
  }

  private void processFood(final Food food) {
    Serving serving = food.getServings().getServing()[0];

    float calories = serving.getCalories();
    float carbs = serving.getCarbohydrate();
    float fat = serving.getFat();
    float cholesterol = serving.getCholesterol();
    float protein = serving.getProtein();

    String measuringUnit = serving.getMetric_serving_unit();

    if (food.getFood_id() == foodOne) {
      calsOne.setText(CALS + "\n" + String.valueOf(calories) + " kcal");
      carbsOne.setText(CARBS + "\n" + String.valueOf(carbs) + " " + measuringUnit);
      fatOne.setText(FAT + "\n" + String.valueOf(fat) + " " + measuringUnit);
      cholOne.setText(CHOL + "\n" + String.valueOf(cholesterol) + " " + measuringUnit);
      protOne.setText(PROT + "\n" + String.valueOf(protein) + " " + measuringUnit);

      foodNameOne.setText(food.getFood_name());
      servingSizeOne.setText(serving.getServing_description());
    } else {
      calsTwo.setText(CALS + "\n" + String.valueOf(calories) + " kcal");
      carbsTwo.setText(CARBS + "\n" + String.valueOf(carbs) + " " + measuringUnit);
      fatTwo.setText(FAT + "\n" + String.valueOf(fat) + " " + measuringUnit);
      cholTwo.setText(CHOL + "\n" + String.valueOf(cholesterol) + " " + measuringUnit);
      protTwo.setText(PROT + "\n" + String.valueOf(protein) + " " + measuringUnit);

      foodNameTwo.setText(food.getFood_name());
      servingSizeTwo.setText(serving.getServing_description());
    }
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
        processFood(result.getFood());
      } else {
        Toast.makeText(getApplication(), "Sorry, could not obtain results!", Toast.LENGTH_SHORT)
            .show();
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);

    menu.add(0, MENU_NEW, 0, R.string.menu_new_comparison).setIcon(
        android.R.drawable.ic_menu_search);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case MENU_NEW:
        Utils.goHome(getApplicationContext(), -1, null);
        return true;
    }

    return false;

  }
}
