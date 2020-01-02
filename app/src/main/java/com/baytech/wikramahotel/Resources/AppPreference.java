package com.baytech.wikramahotel.Resources;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private String KEY_DAILY = "daily";
    private String PREF_NAME = "UserPref";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public AppPreference(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public void setDaily(boolean status){
        editor.putBoolean(KEY_DAILY, status);
        editor.apply();
    }


    public boolean isDaily(){
        return preferences.getBoolean(KEY_DAILY, false);
    }
}
