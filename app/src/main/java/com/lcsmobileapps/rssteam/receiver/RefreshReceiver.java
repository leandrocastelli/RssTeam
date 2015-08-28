package com.lcsmobileapps.rssteam.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.lcsmobileapps.rssteam.util.AlarmController;

public class RefreshReceiver extends BroadcastReceiver {
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


    }
}
