package com.example.manager.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private final SharedPreferences prefs;

    public Session(Context context) {
        prefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        //prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void set(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public String get(String key) {
        return prefs.getString(key, "");
    }
}
