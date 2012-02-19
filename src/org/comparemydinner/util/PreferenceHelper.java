package org.comparemydinner.util;

import org.comparemydinner.CompareMyDinnerApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {

  private final SharedPreferences mPreferences;

  private final Context mContext;

  public PreferenceHelper() {
    mContext = CompareMyDinnerApplication.getContext();
    mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
  }

  public boolean canShowCalories() {
    return mPreferences.getBoolean("show_cals", Boolean.TRUE);
  }

  public boolean canShowCarbs() {
    return mPreferences.getBoolean("show_carbs", Boolean.TRUE);
  }

  public boolean canShowProtein() {
    return mPreferences.getBoolean("show_protein", Boolean.TRUE);
  }

  public boolean canShowFat() {
    return mPreferences.getBoolean("show_fat", Boolean.TRUE);
  }

  public boolean canShowCholesterol() {
    return mPreferences.getBoolean("show_chol", Boolean.TRUE);
  }

  public boolean canShowTransFat() {
    return mPreferences.getBoolean("show_trans_fat", Boolean.FALSE);
  }

  public boolean canShowSaturatedFat() {
    return mPreferences.getBoolean("show_sat_fat", Boolean.FALSE);
  }

  public boolean canShowSodium() {
    return mPreferences.getBoolean("show_sodium", Boolean.FALSE);
  }

  public boolean canShowPotassium() {
    return mPreferences.getBoolean("show_potassium", Boolean.FALSE);
  }

  public boolean canShowFibre() {
    return mPreferences.getBoolean("show_fibre", Boolean.FALSE);
  }

  public boolean canShowSugar() {
    return mPreferences.getBoolean("show_sugar", Boolean.FALSE);
  }

  public boolean canShowCalcium() {
    return mPreferences.getBoolean("show_calcium", Boolean.FALSE);
  }

  public boolean canShowIron() {
    return mPreferences.getBoolean("show_iron", Boolean.FALSE);
  }

}
