package org.comparemydinner.task;

import android.app.Activity;
import android.os.AsyncTask;

public abstract class BaseAsyncTask<Result> extends AsyncTask<String, Void, Result> {

  private Activity activity;

  private int dialogId;

  public BaseAsyncTask(Activity activity, int dialogId) {
    this.activity = activity;
    this.dialogId = dialogId;
  }

  @Override
  protected Result doInBackground(String... params) {
    return doSearch(params[0]);
  }

  @Override
  protected void onPreExecute() {
    activity.showDialog(dialogId);
  }

  @Override
  protected void onPostExecute(final Result result) {
    activity.removeDialog(dialogId);
    postProcessAfterDialogRemoval(result);
  }

  protected abstract void postProcessAfterDialogRemoval(final Result result);

  protected abstract Result doSearch(final String query);

}
