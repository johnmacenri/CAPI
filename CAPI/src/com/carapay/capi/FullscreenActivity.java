package com.carapay.capi;

import static com.carapay.capi.AppRegistry.SENDER_ID;
import static com.carapay.capi.AppRegistry.TIME_FORMAT;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.carapay.capi.R;
import com.google.android.gcm.GCMRegistrar;

public class FullscreenActivity extends Activity {

	private static final String TAG = "FullscreenActivity";

	private static TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AppRegistry.registerActivity("FullscreenActivity", this);

		setContentView(R.layout.activity_fullscreen);
		textView = (TextView) findViewById(R.id.fullscreen_content);
		appendText("Starting Application");

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (!regId.equals("")) {
			appendText("Already registered. Send regId to Carapay");
			try {
				APIClient client = new APIClient();
				client.execute(regId);
				String regResponse = client.get();
				appendText("Decrytion Key from Carapay is " + regResponse);
			} catch (Exception e) {
				appendText(e.getLocalizedMessage());
			}
		} else {
			appendText("Not Registered. Calling GCM to get new regId");
			GCMRegistrar.register(this, SENDER_ID);
		}

	}

	public void appendTextUI(final String msg) {
		runOnUiThread(new Runnable() {
			public void run() {
				appendText(msg);
			}
		});
	}

	private void appendText(final String msg) {
		Log.v(TAG, msg);
		textView.setText(textView.getText() + getTimeNow() + " - " + msg + "\n");
	}

	@SuppressLint("SimpleDateFormat")
	private static String getTimeNow() {
		return new SimpleDateFormat(TIME_FORMAT).format(new Date());
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

}
