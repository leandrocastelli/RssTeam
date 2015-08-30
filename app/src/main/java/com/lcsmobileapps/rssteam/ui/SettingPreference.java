package com.lcsmobileapps.rssteam.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.lcsmobileapps.rssteam.R;
import com.lcsmobileapps.rssteam.util.AlarmController;
import com.lcsmobileapps.rssteam.util.Utils;

/**
 * Created by Leandro on 8/26/2015.
 */
public class SettingPreference extends PreferenceActivity {

    private ListPreference listPreference;
    private ListPreference refreshListPreference;
    private CheckBoxPreference autoRefresh;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        listPreference = (ListPreference)findPreference("team_pref");
        refreshListPreference = (ListPreference)findPreference("refresh_period");
        autoRefresh = (CheckBoxPreference)findPreference("auto_refresh");

        if (sharedPreferences.getString("refresh_period","0").equals("0")) {

            refreshListPreference.setValueIndex(0);
        }
        refreshListPreference.setSummary(refreshListPreference.getEntry());
        refreshListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                int index = refreshListPreference.findIndexOfValue((String)o);
                refreshListPreference.setSummary(getResources().getStringArray(R.array.refresh_items)[index]);
                if (autoRefresh.isChecked()) {
                    AlarmController.configureAlarm(getApplicationContext());
                }
                return true;
            }
        });
        listPreference.setSummary(Utils.getPrefTeamName(this));
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                preference.setSummary((String)o);
                return true;
            }
        });
        autoRefresh.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
               enableDisableRefresh((boolean)o);
                if ((boolean)o) {
                    AlarmController.configureAlarm(getApplicationContext());
                } else {
                    AlarmController.cancelAlarms(getApplicationContext());
                }
                return true;
            }
        });
        enableDisableRefresh(autoRefresh.isChecked());
    }

    private void enableDisableRefresh(boolean isChecked) {
        if(isChecked) {
            refreshListPreference.setEnabled(true);
            refreshListPreference.setSelectable(true);
        } else {
            refreshListPreference.setEnabled(false);
            refreshListPreference.setSelectable(false);
        }
    }


}
