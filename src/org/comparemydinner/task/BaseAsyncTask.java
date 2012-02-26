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
package org.comparemydinner.task;

import android.app.Activity;
import android.os.AsyncTask;

public abstract class BaseAsyncTask<Result> extends AsyncTask<String, Void, Result> {

  private final Activity activity;

  private final int dialogId;

  public BaseAsyncTask(final Activity activity, final int dialogId) {
    this.activity = activity;
    this.dialogId = dialogId;
  }

  @Override
  protected Result doInBackground(final String... params) {
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
