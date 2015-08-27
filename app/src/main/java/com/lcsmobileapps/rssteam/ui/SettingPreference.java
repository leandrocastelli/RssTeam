package com.lcsmobileapps.rssteam.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.lcsmobileapps.rssteam.R;

/**
 * Created by Leandro on 8/26/2015.
 */
public class SettingPreference extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
