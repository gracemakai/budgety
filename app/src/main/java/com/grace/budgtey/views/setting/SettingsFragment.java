package com.grace.budgtey.views.setting;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.grace.budgtey.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        findPreference("budget").setDefaultValue(20000);

    }
}