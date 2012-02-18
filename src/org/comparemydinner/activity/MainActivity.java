package org.comparemydinner.activity;

import static org.comparemydinner.util.Utils.PROGRESS_DIALOG;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.comparemydinner.model.JSONRecipeResponse;
import org.comparemydinner.model.Recipe;
import org.comparemydinner.service.GetRecipeService;
import org.comparemydinner.task.BaseAsyncTask;
import org.comparemydinner.util.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends Activity implements OnClickListener {

  private static final String TAG = "MainActivity";

  private Button foodOne;

  private Button foodTwo;

  private ImageView foodImageOne;

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
    foodImageOne = (ImageView) findViewById(R.id.foodImageOne);

    foodTwo = (Button) findViewById(R.id.button2);

    foodOne.setOnClickListener(this);
    foodTwo.setOnClickListener(this);
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
      final String jsonMsg = new GetRecipeService().execute(query);

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
        Recipe recipe = result.getRecipe();

        if (recipe.getRecipe_image() != null) {
          foodOne.setVisibility(View.GONE);
          foodImageOne.setVisibility(View.VISIBLE);

          try {
            URL imageUrl = new URL(recipe.getRecipe_image());
            InputStream content = (InputStream) imageUrl.getContent();
            Drawable d = Drawable.createFromStream(content, "src");
            foodImageOne.setImageDrawable(d);
          } catch (IOException e) {
            Log.e(TAG, e.getMessage());
          }
        } else {
          foodOne.setEnabled(Boolean.FALSE);
          foodOne.setText(recipe.getRecipe_name());
        }
      } else {
        Toast.makeText(getApplication(), "Sorry, could not obtain results!", Toast.LENGTH_SHORT)
            .show();
      }
    }

  }

}