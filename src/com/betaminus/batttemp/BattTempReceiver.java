package com.betaminus.batttemp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class BattTempReceiver extends BroadcastReceiver {
	@Override
	public final void onReceive(Context context, Intent intent) {
        Log.d(BattTempPlugin.LOG_TAG, "Got battery temperature change");
        BattTempPlugin.stateChanged(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0), context);
	}
}
