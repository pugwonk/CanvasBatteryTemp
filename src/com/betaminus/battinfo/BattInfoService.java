package com.betaminus.battinfo;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class BattInfoService extends Service {
	public void onCreate() {
		super.onCreate();
		Log.i(BattInfoPlugin.LOG_TAG, "Creating service and subscribing to battery notifications");
		BattInfoPlugin.current_state = new BattInfoReceiver();
		this.registerReceiver(BattInfoPlugin.current_state, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	public void onDestroy() {
		super.onDestroy();
		Log.i(BattInfoPlugin.LOG_TAG, "Stopping service");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
