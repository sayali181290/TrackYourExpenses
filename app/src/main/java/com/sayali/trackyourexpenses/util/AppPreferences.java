package com.sayali.trackyourexpenses.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static final String APP_SETTINGS = "TrackYourExpenses";


    private AppPreferences() {}

    private static SharedPreferences getSharedPreferences(Context mContext) {
        return mContext.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public static String getString(Context mContext, String key) {
        return getSharedPreferences(mContext).getString(key , null);
    }

    public static void setString(Context mContext, String value, String key) {
        final SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.putString(key , value);
        editor.apply();
    }

    public static Boolean getBoolean(Context mContext, String key) {
        return getSharedPreferences(mContext).getBoolean(key , false);
    }

    public static void setBoolean(Context mContext,String key, Boolean value) {
        final SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.putBoolean(key , value);
        editor.apply();
    }


}
