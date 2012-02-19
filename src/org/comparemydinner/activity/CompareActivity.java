package org.comparemydinner.activity;

import static org.comparemydinner.util.Utils.MENU_FEEDBACK;
import static org.comparemydinner.util.Utils.MENU_NEW_COMPARISON;
import static org.comparemydinner.util.Utils.PROGRESS_DIALOG;

import org.comparemydinner.R;
import org.comparemydinner.model.Food;
import org.comparemydinner.model.JSONRecipeResponse;
import org.comparemydinner.model.Serving;
import org.comparemydinner.service.GetFoodService;
import org.comparemydinner.task.BaseAsyncTask;
import org.comparemydinner.util.PreferenceHelper;
import org.comparemydinner.util.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
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

  private static final String CALCIUM = "Calcium";

  private static final String FIBRE = "Fibre";

  private static final String SAT_FAT = "Sat. Fat";

  private static final String TRANS_FAT = "Trans Fat";

  private static final String SODIUM = "Sodium";

  private static final String POTASSIUM = "Potassium";

  private static final String SUGAR = "Sugar";

  private static final String IRON = "Iron";

  private long foodOne, foodTwo;

  private PreferenceHelper prefHelper;

  private LinearLayout leftColumn;

  private LinearLayout rightColumn;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.comparison);

    prefHelper = new PreferenceHelper();

    // populate the elements
    leftColumn = (LinearLayout) findViewById(R.id.leftColumn);
    rightColumn = (LinearLayout) findViewById(R.id.rightColumn);

    foodOne = getIntent().getLongExtra("foodOne", -1);
    foodTwo = getIntent().getLongExtra("foodTwo", -1);

    new GetRecipesProgressTask(this, PROGRESS_DIALOG).execute(String.valueOf(foodOne));
    new GetRecipesProgressTask(this, PROGRESS_DIALOG).execute(String.valueOf(foodTwo));
  }

  private void processFood(final Food food) {
    Serving serving = food.getServings().getServingList().get(0);

    String measuringUnit = serving.getMetric_serving_unit();
    boolean addTofirstColumn = true;

    if (food.getFood_id() != foodOne) {
      addTofirstColumn = false;
    }

    createTextView(food.getFood_name(), R.layout.food_name_view, addTofirstColumn);
    createTextView(serving.getServing_description(), R.layout.food_serving_view, addTofirstColumn);

    if (prefHelper.canShowCalories()) {
      createTextView(CALS + "\n" + String.valueOf(serving.getCalories()) + " kcal",
          R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowCarbs()) {
      createTextView(
          CARBS + "\n" + String.valueOf(serving.getCarbohydrate()) + " " + measuringUnit,
          R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowFat()) {
      createTextView(FAT + "\n" + String.valueOf(serving.getFat()) + " " + measuringUnit,
          R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowSaturatedFat()) {
      createTextView(SAT_FAT + "\n" + String.valueOf(serving.getSaturated_fat()) + " "
          + measuringUnit, R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowTransFat()) {
      createTextView(TRANS_FAT + "\n" + String.valueOf(serving.getTrans_fat()) + " "
          + measuringUnit, R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowCholesterol()) {
      createTextView(CHOL + "\n" + String.valueOf(serving.getCholesterol()) + " " + measuringUnit,
          R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowProtein()) {
      createTextView(PROT + "\n" + String.valueOf(serving.getProtein()) + " " + measuringUnit,
          R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowCalcium()) {
      createTextView(CALCIUM + "\n" + String.valueOf(serving.getCalcium()) + " " + measuringUnit,
          R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowFibre()) {
      createTextView(FIBRE + "\n" + String.valueOf(serving.getFiber()) + " " + measuringUnit,
          R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowIron()) {
      createTextView(IRON + "\n" + String.valueOf(serving.getIron()) + " " + measuringUnit,
          R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowPotassium()) {
      createTextView(POTASSIUM + "\n" + String.valueOf(serving.getPotassium()) + " "
          + measuringUnit, R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowSodium()) {
      createTextView(SODIUM + "\n" + String.valueOf(serving.getSodium()) + " " + measuringUnit,
          R.layout.nutrient_info_view, addTofirstColumn);
    }

    if (prefHelper.canShowSugar()) {
      createTextView(SUGAR + "\n" + String.valueOf(serving.getSugar()) + " " + measuringUnit,
          R.layout.nutrient_info_view, addTofirstColumn);
    }
  }

  private void createTextView(String text, int viewToInflate, boolean addTofirstColumn) {
    TextView textView = (TextView) getLayoutInflater().inflate(viewToInflate, null);
    textView.setText(text);

    if (addTofirstColumn) {
      leftColumn.addView(textView);
    } else {
      rightColumn.addView(textView);
    }
  }

  @Override
  protected Dialog onCreateDialog(final int id) {
    return Utils.getProgressDialog(id, "Fetching food data", CompareActivity.this);
  }

  // sub class to get the recipe
  class GetRecipesProgressTask extends BaseAsyncTask<JSONRecipeResponse> {

    public GetRecipesProgressTask(Activity activity, int dialogId) {
      super(activity, dialogId);
    }

    @Override
    protected JSONRecipeResponse doSearch(final String query) {
      JSONRecipeResponse response = null;

      Log.d(TAG, Utils.buildStr("Getting food id [", query, "]"));

      String jsonMsg = new GetFoodService().execute(query);

      try {
        if (jsonMsg.indexOf("[") < 0) {
          jsonMsg = jsonMsg.replace("\"serving\":", "\"serving\": [");
          jsonMsg = jsonMsg.replace("} } }}", "}] } }}");
        }
        response = new Gson().fromJson(jsonMsg, JSONRecipeResponse.class);
      } catch (Exception e) {
        Log.e(TAG, e.getMessage());
        response = new JSONRecipeResponse();
      }

      return response;
    }

    @Override
    protected void postProcessAfterDialogRemoval(final JSONRecipeResponse result) {
      if (null != result.getFood()) {
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

    menu.add(0, MENU_NEW_COMPARISON, 0, R.string.menu_new_comparison).setIcon(
        android.R.drawable.ic_menu_rotate);
    menu.add(0, MENU_FEEDBACK, 0, R.string.menu_feedback).setIcon(android.R.drawable.ic_menu_send);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case MENU_NEW_COMPARISON:
        Utils.goHome(getApplicationContext(), -1, null);
        return true;
      case MENU_FEEDBACK:
        Utils.invokeEmail(this);
        return true;
    }

    return false;

  }
}
