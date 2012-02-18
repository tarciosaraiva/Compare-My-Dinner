package org.comparemydinner.util;

import org.comparemydinner.activity.MainActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class Utils {

  public static final int PROGRESS_DIALOG = 1;

  public static void goHome(Context context, long recipeId) {
    final Intent intent = new Intent(context, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("recipeId", recipeId);

    context.startActivity(intent);
  }

  public static Dialog getProgressDialog(int id, Activity activity) {
    Dialog dialog = null;

    switch (id) {
      case PROGRESS_DIALOG:
        dialog = ProgressDialog.show(activity, "", "Querying...", true);
        break;
      default:
        break;
    }

    return dialog;
  }

}
