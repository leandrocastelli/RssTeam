package com.lcsmobileapps.rssteam.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.lcsmobileapps.rssteam.receiver.RefreshReceiver;

/**
 * Created by Leandro on 8/27/2015.
 */
public class AlarmController {


    public static void configureAlarm( Context ctx) {
        String temp = PreferenceManager.getDefaultSharedPreferences(ctx).getString("refresh_period","1800000");
        long period = Long.parseLong(temp);

        Intent intent = new Intent(ctx, RefreshReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        cancelAlarms(ctx, pendingIntent);
        AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, period, period,pendingIntent);

    }

    public static void cancelAlarms(Context ctx) {
        Intent intent = new Intent(ctx, RefreshReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        cancelAlarms(ctx, pendingIntent);

    }

    private static void cancelAlarms(Context ctx, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
