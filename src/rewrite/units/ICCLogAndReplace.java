package rewrite.units;

import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.Intent;

/* * 
 * This rewriting is used to redirect and monitor the intent, 
 * which is widely used in Android inter-component communications.
 * */

public class ICCLogAndReplace {


	public static void implicitToExplicitIntent(Intent intent) {
		
		// String deviceId ="867979022278631";
		String deviceId = "35549906050200";
		String fakeID = "12345678910";

		String stest = "";

		Bundle bundle = intent.getExtras();

		for (String key : bundle.keySet()) {
			
			Object value = bundle.get(key);
			Log.d("rewrite-1", String.format("%s %s (%s)", key, value.toString(), value.getClass().getName()));
			stest = value.toString();
			if (stest.contains(deviceId)) {
				Log.e("rewrite", stest + " is block based on user's command");
				Log.e("rewrite", "We return a fake DeviceId 12345678910 to not affect the ICFG");
				intent.removeExtra(key);
				intent.putExtra(key, fakeID);

			}
		}
		
		return;
	}

	
	// This is used to log the intent 
	public static void PrintIntent(Intent intent) {
		if (intent == null) {
			return;
		}
		String out = intent.toString();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			extras.size();
			out += "\n" + extras.toString();
		}
		if (intent.getAction() != null)
			out += "\nAction = " + intent.getAction();
		if (intent.getType() != null)
			out += "\nType = " + intent.getType();
		if (intent.getData() != null)
			out += "\nData = " + intent.getData();
		if (intent.getPackage() != null)
			out += "\nPackage = " + intent.getPackage();
		if (intent.getDataString() != null)
			out += "\nDataString = " + intent.getDataString();

		Log.e("[rewrite]Log intent", out);
	}

	// redierct the intent
	public static void IntentRedirectBroadcast(Intent intent) throws IOException {
		
		PrintIntent(intent);
		// change the intent to an explicit intent
		Intent t = (Intent) intent.clone();
		intent.setAction("icc.receiver.proxy");
		intent.putExtra("clone", t);
		Log.e("[rewrite]redirect-bro", "we redirect the intent");
	}


	// redierct the intent
	public static void IntentRedirectService(Intent intent) throws IOException {
		
		PrintIntent(intent);
		// change the intent to an explicit intent
		Intent t = (Intent) intent.clone();
		intent.setAction("icc.service.proxy");
		intent.putExtra("clone", t);
		Log.e("[rewrite]redirect-ser", "we redirect the intent");
	}

	
	
	//this is the package name and class name for the Proxy intent
	public final static String pkg = "example.test.iccreceiver";
	public final static String cln = "example.test.iccreceiver.MainActivity";
	
	// redierct the intent
	public static void IntentRedirect(Intent intent) throws IOException {
		PrintIntent(intent);
		// change the intent to an explicit intent
		Intent t = (Intent) intent.clone();
		intent.setClassName(pkg, cln);
		intent.putExtra("clone", t);
		Log.e("[rewrite]redirect", "we redirect the intent");
	}
   	
	// receiver com.example.ketian.collusionreceive
	// class name com.example.ketian.collusionreceive.MainActivity
	public static void generateExplicitIntent(String s, Context context) {
		Intent i = new Intent();
		i.setClassName(pkg, cln);
		i.putExtra("data", s);
		context.startActivity(i);
	}

	// receiver com.example.ketian.collusionreceive
	// class name com.example.ketian.collusionreceive.MainActivity
	public static void LogData(Object ob) {
		String i = "rewrite:log";
		Log.e(i, String.valueOf(ob));
	}
}
