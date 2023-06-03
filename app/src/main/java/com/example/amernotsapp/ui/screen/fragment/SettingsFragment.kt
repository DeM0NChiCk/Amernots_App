package com.example.amernotsapp.ui.screen.fragment

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.amernotsapp.R
import com.example.amernotsapp.ui.screen.activity.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        findPreference<ListPreference>("theme")?.setOnPreferenceChangeListener{ _, newsValue ->
            if (newsValue is String) MainActivity.setTheme(newsValue)
            true
        }
    }
}