package org.comparemydinner;

import android.app.Application;
import android.content.Context;

public class CompareMyDinnerApplication extends Application {

  public static final String NAME = "CompareMyDinner";

  public static final int VERSION = 1;

  private static CompareMyDinnerApplication instance;

  public CompareMyDinnerApplication() {
    instance = this;
  }

  public static Context getContext() {
    if (instance == null) {
      instance = new CompareMyDinnerApplication();
    }
    return instance;
  }

}
