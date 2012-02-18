package org.comparemydinner.activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import org.comparemydinner.util.PreferenceHelper;
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

  private static final String TAG = "MainActivity";

  private static final int MENU_ABOUT = 0;

  private static final int MENU_SETTINGS = 1;

  private Button foodOne, foodTwo, compare;

  private TableRow foodCompareRow;

  private long foodOneId, foodTwoId;

  private PreferenceHelper mPreferenceHelper;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    if (savedInstanceState != null) {
      foodOneId = savedInstanceState.getLong("foodOneId");
      foodTwoId = savedInstanceState.getLong("foodTwoId");
      Log.d(TAG, "RESTORING state - foodone: " + foodOneId + " || foodTwo: " + foodTwoId);
    }

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
        try {
          FileOutputStream fos = openFileOutput("foods_file", Context.MODE_PRIVATE);
          fos.write("".getBytes());
          fos.close();
        } catch (IOException e) {
          // TODO
        }

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
      // new GetRecipeProgressTask(this,
      // PROGRESS_DIALOG).execute(String.valueOf(recipeId));
      try {

        FileOutputStream fos = openFileOutput("foods_file", Context.MODE_APPEND);
        fos.write(String.valueOf(intentFoodId).getBytes());
        fos.write(String.valueOf("-").getBytes());
        fos.write(String.valueOf(intentFoodName).getBytes());
        fos.write(String.valueOf("#").getBytes());
        fos.close();

        FileInputStream fis = openFileInput("foods_file");

        StringBuffer strContent = new StringBuffer("");
        int ch;

        while ((ch = fis.read()) != -1) {
          strContent.append((char) ch);
        }

        fis.close();

        StringTokenizer strToken = new StringTokenizer(strContent.toString(), "#");
        int count = 0;

        while (strToken.hasMoreTokens()) {
          String str = strToken.nextToken();

          String foodId = str.substring(0, str.indexOf("-"));
          String foodName = str.substring(str.indexOf("-") + 1);

          if (count == 0) {
            foodOne.setEnabled(Boolean.FALSE);
            foodOne.setText(foodName);
            foodOneId = Long.valueOf(foodId);
          } else {
            foodTwo.setEnabled(Boolean.FALSE);
            foodTwo.setText(foodName);
            foodTwoId = Long.valueOf(foodId);

            foodCompareRow.setVisibility(View.VISIBLE);
          }

          count++;

        }

      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    super.onResume();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);

    menu.add(0, MENU_ABOUT, 0, R.string.menu_about).setIcon(android.R.drawable.ic_menu_help);
    menu.add(0, MENU_SETTINGS, 0, R.string.menu_settings).setIcon(
        android.R.drawable.ic_menu_preferences);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case MENU_ABOUT:
        Utils.showAbout(this, mPreferenceHelper);
        return true;
      case MENU_SETTINGS:
        startActivity(new Intent(this, SettingsActivity.class));
        return true;
    }
    return false;

  }

  @Override
  public void onClick(View v) {
    Log.d(TAG, "Requesting search activity");

    onSearchRequested();
  }

}