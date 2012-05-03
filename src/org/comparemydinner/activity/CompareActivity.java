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

import static org.comparemydinner.util.Utils.MENU_FEEDBACK;
import static org.comparemydinner.util.Utils.MENU_NEW_COMPARISON;
import static org.comparemydinner.util.Utils.PROGRESS_DIALOG;

import org.comparemydinner.R;
import org.comparemydinner.model.Food;
import org.comparemydinner.model.JSONFoodResponse;
import org.comparemydinner.model.Serving;
import org.comparemydinner.service.GetFoodService;
import org.comparemydinner.util.PreferenceHelper;
import org.comparemydinner.util.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;

@EActivity(R.layout.comparison)
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

  private Button attributionBtn;

  private ProgressDialog dialog;

  @AfterViews
  void populateNutrients() {
    prefHelper = new PreferenceHelper();

    // populate the elements
    leftColumn = (LinearLayout) findViewById(R.id.leftColumn);
    rightColumn = (LinearLayout) findViewById(R.id.rightColumn);
    attributionBtn = (Button) findViewById(R.id.attributionBtn);
    Utils.configureAttributionButton(attributionBtn, this);

    foodOne = getIntent().getLongExtra("foodOne", -1);
    foodTwo = getIntent().getLongExtra("foodTwo", -1);

    final String[] foodIds = { String.valueOf(foodOne), String.valueOf(foodTwo) };

    dialog = (ProgressDialog) Utils.getProgressDialog(PROGRESS_DIALOG, "Loading foods..",
        CompareActivity.this);

    getFood(foodIds);
  }

  @Background
  void getFood(final String... foodIds) {

    for (final String food : foodIds) {
      doSearch(food);
    }

    cancelDialog();
  }

  @UiThread
  void cancelDialog() {
    dialog.dismiss();
  }

  @UiThread
  void processFood(final Food food) {
    final Serving serving = food.getServings().getServing().get(0);

    String measuringUnit = serving.getMetric_serving_unit();
    if (null == measuringUnit) {
      measuringUnit = "g";
    }

    boolean addTofirstColumn = true;

    if (food.getFood_id() != foodOne) {
      addTofirstColumn = false;
    }

    createTextView(food.getFood_name(), R.layout.food_name_view, addTofirstColumn);

    String servingSize = "";

    if (null == serving.getMetric_serving_unit()) {
      servingSize = serving.getServing_description();
    } else {
      servingSize = Utils.buildStr(String.valueOf(serving.getMetric_serving_amount()), " ",
          serving.getMetric_serving_unit());
    }

    createTextView(servingSize, R.layout.food_serving_view, addTofirstColumn);

    if (prefHelper.canShowCalories()) {
      final String calMeasure = prefHelper.getCalorieMeasure();

      float calories = serving.getCalories();

      if ("cal".equals(calMeasure)) {
        calories = calories / 10;
      } else if ("kj".equals(calMeasure)) {
        calories = (float) (calories * 4.184);
      }

      createTextView(CALS + "\n" + String.format("%8.0f", calories).trim() + " " + calMeasure,
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

  private void createTextView(final String text, final int viewToInflate,
      final boolean addTofirstColumn) {
    final TextView textView = (TextView) getLayoutInflater().inflate(viewToInflate, null);
    textView.setText(text);

    final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    lp.setMargins(10, 10, 10, 10);

    if (addTofirstColumn) {
      leftColumn.addView(textView, lp);
    } else {
      rightColumn.addView(textView, lp);
    }
  }

  @Override
  protected Dialog onCreateDialog(final int id) {
    return Utils.getProgressDialog(id, "Fetching food data", CompareActivity.this);
  }

  private JSONFoodResponse doSearch(final String query) {
    JSONFoodResponse response = null;

    Log.d(TAG, Utils.buildStr("Getting food id [", query, "]"));

    String jsonMsg = new GetFoodService().execute(query);

    try {
      final JSONObject object = (JSONObject) new JSONTokener(jsonMsg).nextValue();
      final JSONObject foodObject = object.getJSONObject("food");
      final JSONObject servingsObject = foodObject.getJSONObject("servings");

      JSONArray arrayOfServings = null;

      try {
        arrayOfServings = servingsObject.getJSONArray("serving");
      } catch (final Exception e) {
        Log.e(TAG, e.getMessage());
      }

      if (arrayOfServings == null) {
        jsonMsg = jsonMsg.replace("\"serving\":", "\"serving\": [");
        jsonMsg = jsonMsg.replace("} } }}", "}] } }}");
      }

      response = new Gson().fromJson(jsonMsg, JSONFoodResponse.class);

      if (response != null && response.getFood() != null) {
        processFood(response.getFood());
      }
    } catch (final Exception e) {
      Log.e(TAG, e.getMessage());
      response = new JSONFoodResponse();
    }

    return response;
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {
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
