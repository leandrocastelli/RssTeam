package com.lcsmobileapps.rssteam.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Leandro on 8/14/2015.
 */
public class Utils {
    private static final String PREF_NAME = "TeamPref";
    //  RSS_DATE_FORMAT is used to format the Date from RSS
    public static String RSS_DATE_FORMAT = "dd/MM/yy-HH:mm";
    //      DB_DATE_FORMAT is the SQLITE3 Pattern to store Date
    public static String DB_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date convertToDate(String temp) {

        //Work-Around check if came from rss or DB
        SimpleDateFormat simpleDateFormat;
        if (temp.contains("h")) {
            temp = temp.replace('h', ':');
            simpleDateFormat = new SimpleDateFormat(RSS_DATE_FORMAT);
        } else {
            simpleDateFormat = new SimpleDateFormat(DB_DATE_FORMAT);
        }
        temp = temp.replace('h', ':');

        try {
            return simpleDateFormat.parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertToString(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String getPrefTeamName(Context ctx) {
        SharedPreferences preferences = ctx.getSharedPreferences(PREF_NAME, 0);

        return preferences.getString("teamPref", "");
    }

    public static void setPrefTeamName(Context ctx, String teamName) {
        SharedPreferences preferences = ctx.getSharedPreferences(PREF_NAME, 0);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("teamPref",teamName);
        editor.commit();


    }

}
