package org.comparemydinner.activity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity implements
    OnSharedPreferenceChangeListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.prefs);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

  }

}
