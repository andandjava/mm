package com.telanganatourism.android.phase2.backgroundservice;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.telanganatourism.android.phase2.Constants;
import com.telanganatourism.android.phase2.GPSTracker;
import com.telanganatourism.android.phase2.ServiceCalls1;
import com.telanganatourism.android.phase2.SplashScreen;
import com.telanganatourism.android.phase2.database.helper.BaseDatabase;
import com.telanganatourism.android.phase2.database.helper.TtHelper;
import com.telanganatourism.android.phase2.util.Utility;

public class TrackingService extends Service {

	TtHelper dbbHelper;
	BaseDatabase baseDatabase;

	ArrayList<String> destinationsDetailsArray;

//	Utility utility;

	int iterator;
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 5; // 1 minute
	public static String Base_url = "http://ttourdev.etisbew.net/WebServices/";
	public static String strIMEINo;
	// http://.....logUserTrack/user_id/imea_number/latitude/longitude/sessionID
	// http://172.16.0.49/telangana_tourism/WebServices/logUserTrack/
	public TrackingService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// SplashScreen.pref = getSharedPreferences("telangana_tourism",
		// MODE_PRIVATE);
		// editor = pref.edit();
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		strIMEINo = telephonyManager.getDeviceId();
		if ((SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Track, ""))
				.equalsIgnoreCase("true")) {

			setTrackingService();
		}

	}

	@Override
	public void onDestroy() {
//		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
		super.onDestroy();
	}

	public void setTrackingService() {

		// dbbHelper.close();
		// baseDatabase.close();
		GPSTracker gps = new GPSTracker(getBaseContext()) {
		};
		if (gps.canGetLocation()) {
			Constants.latitude = gps.getLatitude();
			Constants.longitude = gps.getLongitude();

		}
//		utility = new Utility(TrackingService.this);

		if (Utility.checkInternetConnection(TrackingService.this)) {

			new TrackingServiceTask().execute();

		} else {
			Toast.makeText(TrackingService.this, "Network not available",
					Toast.LENGTH_LONG).show();
		}
 
		long currentTimeMillis = System.currentTimeMillis();
		long nextUpdateTimeMillis = currentTimeMillis + 3
				* DateUtils.MINUTE_IN_MILLIS;

		Intent serviceIntent = new Intent(this, TrackingService.class);
		PendingIntent pi = PendingIntent.getService(this, 131313,
				serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, nextUpdateTimeMillis, pi);
		
		/*AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, MIN_TIME_BW_UPDATES, pi);*/
		

		// if((SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Track,
		// "")).equalsIgnoreCase("true"))
		// {
		//
		//
		//
		// /*AlarmManager
		// am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		// Intent intent = new Intent(context,
		// AlarmManagerBroadcastReceiver.class);
		// intent.putExtra(ONE_TIME, Boolean.TRUE);
		// PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		// am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1000 *
		// 60 * 5), pi);*/
		// }
	}

	class TrackingServiceTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(Base_url
					+ "logUserTrack/"
					+ SplashScreen.pref.getString(SplashScreen.Key_GET_USER_ID,
							"")
					+ "/"+strIMEINo+"/"
					+ Constants.latitude
					+ "/"
					+ Constants.longitude
					+ "/"
					+ SplashScreen.pref.getString(
							SplashScreen.Key_GET_USER_Session, ""));
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			/*Toast.makeText(
					TrackingService.this,
					""
							+ SplashScreen.pref.getString(
									SplashScreen.Key_GET_USER_Session, ""),
					Toast.LENGTH_LONG).show();*/

			if (null == result || result.length() == 0) {
				Toast.makeText(TrackingService.this,
						"No data found from Server!!!", Toast.LENGTH_LONG)
						.show();

			} else {
				
				try {
					JSONObject jsonObject = new JSONObject(result);
					SplashScreen.editor.putString(SplashScreen.Key_GET_USER_Session, jsonObject.optString("session"));
					SplashScreen.editor.commit();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

}
