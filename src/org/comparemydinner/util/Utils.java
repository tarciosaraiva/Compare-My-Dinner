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
package org.comparemydinner.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.comparemydinner.R;
import org.comparemydinner.activity.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Utils {

  public static final int PROGRESS_DIALOG = 1;

  public static final int MENU_ABOUT = 0;

  public static final int MENU_SETTINGS = 1;

  public static final int MENU_NEW_SEARCH = 2;

  public static final int MENU_NEW_COMPARISON = 3;

  public static final int MENU_FEEDBACK = 4;

  public static final String EMPTY = "";

  public static final String TEMP_FOOD_FILE = "foods_file";

  public static void goHome(final Context context, final long recipeId, final String recipeName) {
    final Intent intent = new Intent(context, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    if (recipeId > -1) {
      intent.putExtra("recipeId", recipeId);
      intent.putExtra("recipeName", recipeName);
    }

    context.startActivity(intent);
  }

  public static Dialog getProgressDialog(final int dialogId, final String msg,
      final Activity activity) {
    Dialog dialog = null;

    switch (dialogId) {
      case PROGRESS_DIALOG:
        dialog = ProgressDialog.show(activity, "", msg, true);
        break;
      default:
        break;
    }

    return dialog;
  }

  public static void showAbout(final Activity activity) {
    // Get the package name
    String heading = activity.getResources().getText(R.string.app_name) + "\n";

    try {
      final PackageInfo pi = activity.getPackageManager().getPackageInfo(activity.getPackageName(),
          0);
      heading += "v" + pi.versionName + "\n\n";
    } catch (final NameNotFoundException e) {
      e.printStackTrace();
    }

    // Build alert dialog
    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
    dialogBuilder.setTitle(heading);
    final View aboutView = activity.getLayoutInflater().inflate(R.layout.dialog_about, null);
    dialogBuilder.setView(aboutView);
    dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

      @Override
      public void onClick(final DialogInterface dialog, final int whichButton) {
        dialog.dismiss();
      }

    });

    dialogBuilder.setCancelable(false);
    dialogBuilder.setIcon(R.drawable.logo);
    dialogBuilder.show();
  }

  public static String buildStr(final String... args) {
    if (null == args) {
      return "";
    }

    final StringBuffer strBuff = new StringBuffer();

    for (final String str : args) {
      strBuff.append(str);
    }

    return strBuff.toString();
  }

  public static void invokeEmail(final Context context) {
    final Intent emailIntent = new Intent(Intent.ACTION_SEND);
    emailIntent.setType("text/plain");
    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "comparemydinner@gmail.com" });
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Compare My Dinner - Feedback");
    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
    context.startActivity(Intent.createChooser(emailIntent, "Send feedback"));
  }

  public static void cleanTempFile(final String tag, final Activity activity) {
    try {
      final FileOutputStream fos = activity.openFileOutput(TEMP_FOOD_FILE, Context.MODE_PRIVATE);
      fos.write(EMPTY.getBytes());
      fos.close();
    } catch (final IOException e) {
      Log.e(tag, Utils.buildStr("Could not handle the file: ", e.getMessage()));
    }
  }

  public static void configureAttributionButton(final Button button, final Activity activity) {
    button.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(final View v) {
        final Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://platform.fatsecret.com"));
        activity.startActivity(i);
      }

    });
  }

}
