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

  public String getCalorieMeasure() {
    return mPreferences.getString("cals_measure", "kcal");
  }

}
