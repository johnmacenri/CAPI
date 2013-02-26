package com.carapay.capi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;

public class AppRegistry {

	public static final String SENDER_ID = "167387389854";
	public static final String TIME_FORMAT = "HH:mm:ss.SSS";
	public static final String CARAPAY_URL = "http://capi-jetty.herokuapp.com/register/device/android";

	private static Map<String, Activity> activities = new HashMap<String, Activity>();
	private static String udak  = UUID.randomUUID().toString();
	
	public static void registerActivity(String name, Activity activity) {
		activities.put(name, activity);
	}
	
	public static Activity getActivity(String name) {
		return activities.get(name);
	}
	
	public static String getUdak() {
		return udak;
	}
}
