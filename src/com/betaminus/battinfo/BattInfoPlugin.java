package com.betaminus.battinfo;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.betaminus.battinfo.R;
import com.pennas.pebblecanvas.plugin.PebbleCanvasPlugin;

public class BattInfoPlugin extends PebbleCanvasPlugin {
	public static final String LOG_TAG = "CANV_BATTTEMP";
	
	public static final int BATTTEMP_ID = 1; // Needs to be unique only within this plugin package
	
	private static final String[] MASKS = { "%C", "%F" };
	private static final int MASK_C = 0;
	private static final int MASK_F = 1;
	
	private static float currentTemp = -999;
	
	public static BattInfoReceiver current_state;
	
	// send plugin metadata to Canvas when requested
	@Override
	protected ArrayList<PluginDefinition> get_plugin_definitions(Context context) {
		Log.i(LOG_TAG, "get_plugin_definitions");
				
		// create a list of plugins provided by this app
		ArrayList<PluginDefinition> plugins = new ArrayList<PluginDefinition>();
		
		// now playing (text)
		TextPluginDefinition tplug = new TextPluginDefinition();
		tplug.id = BATTTEMP_ID;
		tplug.name = context.getString(R.string.plugin_name);
		tplug.format_mask_descriptions = new ArrayList<String>(Arrays.asList(context.getResources().getStringArray(R.array.format_mask_descs)));
		// populate example content for each field (optional) to be display in the format mask editor
		ArrayList<String> examples = new ArrayList<String>();
		examples.add("30�");
		examples.add("100�");
		tplug.format_mask_examples = examples;
		tplug.format_masks = new ArrayList<String>(Arrays.asList(MASKS));
		tplug.default_format_string = "%C";
		plugins.add(tplug);
		
		return plugins;
	}
	
	// send current text values to canvas when requested
	@Override
	protected String get_format_mask_value(int def_id, String format_mask, Context context, String param) {
		Log.i(LOG_TAG, "get_format_mask_value def_id = " + def_id + " format_mask = '" + format_mask + "'");
		format_mask = format_mask.replace("#", ""); // was coming back as '%C##', no idea why
		if (def_id == BATTTEMP_ID) {
			// Start service if it's not running. It'll only start once so can call multiple times
			Intent tickerService = new Intent(context, BattInfoService.class);
			context.startService(tickerService);
			if (currentTemp == -999)
				return "...";
			else {
				// which format to return current value for?
				if (format_mask.equals(MASKS[MASK_F])) {
					return String.valueOf((int)(currentTemp * 9/5 + 32)) + "�";
				} else if (format_mask.equals(MASKS[MASK_C])) {
					return String.valueOf((int)currentTemp) + "�";
				}
			}
		}
		Log.i(LOG_TAG, "no matching mask found");
		return null;
	}
	
	// send bitmap value to canvas when requested
	@Override
	protected Bitmap get_bitmap_value(int def_id, Context context, String param) {
		return null;
	}

	public static void stateChanged(int temp, Context context) {
		Log.i(LOG_TAG, "Retrieving and returning current battery temperature (" + String.valueOf(temp) + ")");
        currentTemp = temp/10;
        notify_canvas_updates_available(BATTTEMP_ID, context);
    }
}