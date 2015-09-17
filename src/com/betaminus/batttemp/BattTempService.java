package com.betaminus.batttemp;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class BattTempService extends Service {
	public void onCreate() {
		super.onCreate();
		Log.i(BattTempPlugin.LOG_TAG, "Creating service and subscribing to battery notifications");
		BattTempPlugin.current_state = new BattTempReceiver();
		this.registerReceiver(BattTempPlugin.current_state, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	public void onDestroy() {
		super.onDestroy();
		Log.i(BattTempPlugin.LOG_TAG, "Stopping service");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
