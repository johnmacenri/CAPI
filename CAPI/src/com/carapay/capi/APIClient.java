package com.carapay.capi;

import static com.carapay.capi.AppRegistry.CARAPAY_URL;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

public class APIClient extends AsyncTask<String, Void, String>{

	private static final String TAG = "APIClient";

	@Override
	protected String doInBackground(String... params) {
		return registerWithCarapay(params[0]);
	}	
	
	private HttpParams getHttpParams() {

		HttpParams htpp = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(htpp, 30000);
		HttpConnectionParams.setSoTimeout(htpp, 50000);
		return htpp;
	}
	
	private String registerWithCarapay(String regId) {
				
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("udak", AppRegistry.getUdak()));
		params.add(new BasicNameValuePair("regId", regId));
		
		try {
			HttpClient httpclient = new DefaultHttpClient(getHttpParams());
			HttpPost httppost = new HttpPost(CARAPAY_URL);
			httppost.setEntity(new UrlEncodedFormEntity(params));
			ResponseHandler<String> handler = new BasicResponseHandler();
			return httpclient.execute(httppost, handler);
		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);			
			return e.getLocalizedMessage();
		}	
	}

}
