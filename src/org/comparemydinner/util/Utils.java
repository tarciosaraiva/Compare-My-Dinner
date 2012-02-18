package org.comparemydinner.util;

import org.comparemydinner.activity.MainActivity;
import org.comparemydinner.activity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;

public class Utils {

  public static final int PROGRESS_DIALOG = 1;

  public static void goHome(Context context, long recipeId, String recipeName) {
    final Intent intent = new Intent(context, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    if (recipeId > -1) {
      intent.putExtra("recipeId", recipeId);
      intent.putExtra("recipeName", recipeName);
    }

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

  public static void showAbout(final Activity activity, final PreferenceHelper prefHelper) {
    // Get the package name
    String heading = activity.getResources().getText(R.string.app_name) + "\n";

    try {
      PackageInfo pi = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
      heading += "v" + pi.versionName + "\n\n";
    } catch (NameNotFoundException e) {
      e.printStackTrace();
    }

    // Build alert dialog
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
    dialogBuilder.setTitle(heading);
    View aboutView = activity.getLayoutInflater().inflate(R.layout.dialog_about, null);
    dialogBuilder.setView(aboutView);
    dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int whichButton) {
        dialog.dismiss();
      }

    });

    dialogBuilder.setCancelable(false);
    dialogBuilder.setIcon(R.drawable.logo);
    dialogBuilder.show();
  }
}
