package com.lcsmobileapps.rssteam.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.lcsmobileapps.rssteam.service.FeedMonitor;
import com.lcsmobileapps.rssteam.util.AlarmController;

public class RefreshReceiver extends WakefulBroadcastReceiver {
    public RefreshReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Leandro", "Recebi");

        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            boolean isChecked = sharedPreferences.getBoolean("auto_refresh",false);

            if (isChecked) {
                AlarmController.configureAlarm(context);
            }
            return;
        }
        Intent serviceIntent = new Intent(context, FeedMonitor.class);

        startWakefulService(context, serviceIntent);

    }
}
