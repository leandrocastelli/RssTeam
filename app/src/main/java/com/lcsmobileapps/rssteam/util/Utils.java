package com.lcsmobileapps.rssteam.util;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Leandro on 8/14/2015.
 */
public class Utils {

    public static Date convertToDate (String temp) {
        temp = temp.replace('h',':');
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy-HH:mm");
        try {
            return simpleDateFormat.parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String convertToString (Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy-HH:mm");
        return simpleDateFormat.format(date);
    }
}
