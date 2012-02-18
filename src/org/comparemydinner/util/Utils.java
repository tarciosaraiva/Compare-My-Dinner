package org.comparemydinner.util;

import org.comparemydinner.activity.MainActivity;
import org.comparemydinner.model.Recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Utils {

  public static void goHome(Context context, Recipe recipe) {
    final Intent intent = new Intent(context, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    if (recipe != null) {
      Bundle recipeBundle = new Bundle();
      intent.putExtra("recipe", recipeBundle);
    }

    context.startActivity(intent);
  }
}
