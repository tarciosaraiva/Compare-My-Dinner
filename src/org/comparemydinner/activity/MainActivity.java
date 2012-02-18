package org.comparemydinner.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

  private static final String TAG = "MainActivity";

  private Button foodOne;

  private Button foodTwo;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // TODO fix, this is horrible
    if (savedInstanceState != null) {
      if (savedInstanceState.getBundle("recipe") != null) {
        Toast.makeText(getApplication(), "There's a recipe!", Toast.LENGTH_SHORT).show();
      }
    }

    Log.d(TAG, "Assigning buttons from XML view");

    foodOne = (Button) findViewById(R.id.button1);
    foodTwo = (Button) findViewById(R.id.button2);
    foodOne.setOnClickListener(this);
    foodTwo.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    Log.d(TAG, "Requesting search activity");
    onSearchRequested();
  }

}