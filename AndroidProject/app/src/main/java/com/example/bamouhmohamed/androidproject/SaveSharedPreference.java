package com.example.bamouhmohamed.androidproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by younes on 14/04/2018.
 */

public class SaveSharedPreference
{
    static final String PREF_USER_NAME= "username";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }
    public static void setType(Context ctx, String type)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("type", type);
        editor.commit();
    }
    public static void setAccessToken(Context ctx, String accessToken)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("access_token", accessToken);
        editor.commit();
    }
    public static void setId_accomp(Context ctx, String id_accomp){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("id_accomp", id_accomp);
        editor.commit();
    }


    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
    public static String getType(Context ctx)
    {
        return getSharedPreferences(ctx).getString("type", "");
    }
    public static String getAccessToken(Context ctx){
        return getSharedPreferences(ctx).getString("access_token", "");
    }
    public static String getId_accomp(Context ctx){
        return getSharedPreferences(ctx).getString("id_accomp", "");
    }
    public static void createSharedPref(Context ctx,String param,String paramValue){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(param, paramValue);
        editor.commit();
    }
    public static String getSharedPref(Context ctx,String param){
        return getSharedPreferences(ctx).getString(param, "");
    }
}