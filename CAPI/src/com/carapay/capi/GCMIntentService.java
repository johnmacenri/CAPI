package com.carapay.capi;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	private void appendText(String msg) {
		FullscreenActivity mainScreen = (FullscreenActivity)AppRegistry.getActivity("FullscreenActivity");
		if (mainScreen != null) {
			mainScreen.appendTextUI(msg);
		}
	}
	
	@Override
	protected void onError(Context context, String errorId) {
		appendText("An error occurred. ID=" + errorId);
	}

	@TargetApi(16)
	@Override
	protected void onMessage(Context context, Intent intent) {
		String msg = "SharedSecret received from GCM. Value is " + intent.getStringExtra("SharedSecret");
		appendText(msg);
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		appendText("Registration ID received from GCM " + regId.substring(0,30) + "...");
		try {
			APIClient client = new APIClient();
			client.execute(regId);			
			String regResponse = client.get();
			appendText("Decrytion Key from Carapay is " + regResponse);
		} catch (Exception e) {
			appendText(e.getLocalizedMessage());
		} 		
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		appendText("Unregistration has succeeded for regId " + regId.substring(0,30) + "...");
	}
	
}