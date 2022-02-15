package com.example.alarmclockreminder;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        switch (sp.getString("theme", "ThemeOrange")){
            case "ThemeOrange":
                setTheme(R.style.ThemeOrange);
                break;
            case "ThemeRed":
                setTheme(R.style.ThemeRed);
                break;
            case "ThemePurple":
                setTheme(R.style.ThemePurple);
                break;
            case "ThemeTeal":
                setTheme(R.style.ThemeTeal);
                break;
            case "ThemeGreen":
                setTheme(R.style.ThemeGreen);
                break;
            case "ThemeYellow":
                setTheme(R.style.ThemeYellow);
                break;
        }
        this.setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

}