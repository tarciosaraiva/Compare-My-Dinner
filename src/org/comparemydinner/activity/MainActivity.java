package org.comparemydinner.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

  private static final String TAG = "MainActivity";

  private Button foodOne;

  private Button foodTwo;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    foodOne = (Button) findViewById(R.id.button1);
    foodTwo = (Button) findViewById(R.id.button2);
    foodOne.setOnClickListener(this);
    foodTwo.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    onSearchRequested();
  }
}