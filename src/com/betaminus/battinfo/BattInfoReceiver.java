package com.betaminus.battinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class BattInfoReceiver extends BroadcastReceiver {
	@Override
	public final void onReceive(Context context, Intent intent) {
        int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -999);
        Log.d(BattInfoPlugin.LOG_TAG, "Got battery info change");
        Log.d(BattInfoPlugin.LOG_TAG, "EXTRA_TEMPERATURE: " + String.valueOf(temp));
        BatteryManager bm = (BatteryManager)context.getSystemService(Context.BATTERY_SERVICE);
        long current = bm.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        Log.d(BattInfoPlugin.LOG_TAG, "BATTERY_PROPERTY_CURRENT_NOW: " + String.valueOf(current));
        Log.d(BattInfoPlugin.LOG_TAG, "BATTERY_PROPERTY_CURRENT_AVERAGE: " + String.valueOf(bm.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE)));
        // Sometimes this doesn't return anything (seems to be mainly when charging state changes, which
        // triggers the broadcast but doesn't return anything for temperature. In those cases, just
        // leave the previous value there.
        if (temp != -999)
        	BattInfoPlugin.tempChanged(temp, context);
    	BattInfoPlugin.currentChanged(current, context);
	}
}
