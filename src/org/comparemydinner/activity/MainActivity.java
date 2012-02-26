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

import static org.comparemydinner.util.Utils.EMPTY;
import static org.comparemydinner.util.Utils.MENU_ABOUT;
import static org.comparemydinner.util.Utils.MENU_FEEDBACK;
import static org.comparemydinner.util.Utils.MENU_SETTINGS;
import static org.comparemydinner.util.Utils.TEMP_FOOD_FILE;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import org.comparemydinner.R;
import org.comparemydinner.util.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;

public class MainActivity extends Activity implements OnClickListener {

  private static final String HASH = "#";

  private static final String DASH = "-";

  private static final String TAG = "MainActivity";

  private Button foodOne, foodTwo, compare;

  private TableRow foodCompareRow;

  private long foodOneId, foodTwoId;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Log.d(TAG, "Assigning buttons from view");

    foodOne = (Button) findViewById(R.id.button1);
    foodTwo = (Button) findViewById(R.id.button2);
    compare = (Button) findViewById(R.id.compareBtn);
    foodCompareRow = (TableRow) findViewById(R.id.foodCompareRow);

    foodOne.setOnClickListener(this);
    foodTwo.setOnClickListener(this);

    // implement a different listener for comparison
    compare.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(final View v) {
        cleanTempFile();

        final Intent intent = new Intent(MainActivity.this, CompareActivity.class);
        intent.putExtra("foodOne", foodOneId);
        intent.putExtra("foodTwo", foodTwoId);

        startActivity(intent);
      }

    });
  }

  @Override
  protected void onResume() {
    final long intentFoodId = getIntent().getLongExtra("recipeId", -1);
    final String intentFoodName = getIntent().getStringExtra("recipeName");

    Log.d(TAG, "Recipe ID: " + intentFoodId);
    Log.d(TAG, "Recipe Name: " + intentFoodName);

    if (intentFoodId > -1) {

      try {

        final FileOutputStream fos = openFileOutput(TEMP_FOOD_FILE, Context.MODE_APPEND);
        fos.write(String.valueOf(intentFoodId).getBytes());
        fos.write(String.valueOf(DASH).getBytes());
        fos.write(String.valueOf(intentFoodName).getBytes());
        fos.write(String.valueOf(HASH).getBytes());
        fos.close();

        final FileInputStream fis = openFileInput(TEMP_FOOD_FILE);

        final StringBuffer strContent = new StringBuffer(EMPTY);
        int ch;

        while ((ch = fis.read()) != -1) {
          strContent.append((char) ch);
        }

        fis.close();

        final StringTokenizer strToken = new StringTokenizer(strContent.toString(), HASH);
        int count = 0;

        while (strToken.hasMoreTokens()) {
          final String str = strToken.nextToken();

          final String foodId = str.substring(0, str.indexOf(DASH));
          final String foodName = str.substring(str.indexOf(DASH) + 1);

          if (count == 0) {
            disableSearchButtonAndModifyText(foodOne, foodName);
            foodOneId = Long.valueOf(foodId);
          } else {
            disableSearchButtonAndModifyText(foodTwo, foodName);
            foodTwoId = Long.valueOf(foodId);

            foodCompareRow.setVisibility(View.VISIBLE);
          }

          count++;
        }

      } catch (final IOException e) {
        Log.e(TAG, Utils.buildStr("Problem processing selection from search: ", e.getMessage()));
      }
    }

    super.onResume();
  }

  private void disableSearchButtonAndModifyText(final Button button, final String foodName) {
    button.setEnabled(Boolean.FALSE);
    button.setText(foodName);
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {
    super.onCreateOptionsMenu(menu);

    menu.add(0, MENU_ABOUT, 0, R.string.menu_about).setIcon(android.R.drawable.ic_menu_help);
    menu.add(0, MENU_SETTINGS, 0, R.string.menu_preferences).setIcon(
        android.R.drawable.ic_menu_preferences);
    menu.add(0, MENU_FEEDBACK, 0, R.string.menu_feedback).setIcon(android.R.drawable.ic_menu_send);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case MENU_ABOUT:
        Utils.showAbout(this);
        return true;
      case MENU_SETTINGS:
        startActivity(new Intent(this, PreferencesActivity.class));
        return true;
      case MENU_FEEDBACK:
        Utils.invokeEmail(this);
        return true;
    }

    return false;

  }

  @Override
  public void onClick(final View v) {
    Log.d(TAG, "Requesting search activity");
    onSearchRequested();
  }

  private void cleanTempFile() {
    try {
      final FileOutputStream fos = openFileOutput(TEMP_FOOD_FILE, Context.MODE_PRIVATE);
      fos.write(EMPTY.getBytes());
      fos.close();
    } catch (final IOException e) {
      Log.e(TAG, Utils.buildStr("Could not handle the file: ", e.getMessage()));
    }
  }

}