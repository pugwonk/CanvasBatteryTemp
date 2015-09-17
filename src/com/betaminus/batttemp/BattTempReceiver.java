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
        int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -999);
        // Sometimes this doesn't return anything (seems to be mainly when charging state changes, which
        // triggers the broadcast but doesn't return anything for temperature. In those cases, just
        // leave the previous value there.
        if (temp != -999)
        	BattTempPlugin.stateChanged(temp, context);
	}
}
