package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.telanganatourism.android.phase2.database.helper.TtHelper;
import com.telanganatourism.android.phase2.util.Utility;

public class SplashScreen extends Activity {

	Intent i;

	public static int hintVal = 0;
	public static int slide_menu_id = 0;
	TtHelper dbbHelper;

//	Utility utility;

	public static SharedPreferences pref;
	public static Editor editor;
	public static String user_id;
	public static String Key_GET_USER_ID = "USER_ID";
	public static String Key_GET_USER_NAME = "USER_NAME";
	public static String Key_GET_USER_PHONE_NO = "USER_PHONE_NO";
	public static String Key_GET_USER_Session = "session";
	public static String Key_GET_USER_Track = "false";
	public static String Key_GET_USER_RATE_TRACK = "false";

	public static String varvalue = "1";

	// public static String Base_url =
	// "http://172.16.0.49/telangana_tourism/WebServices/";

	public static String Base_url = "http://ttourdev.etisbew.net/WebServices/";
	public static String Base_url2 = "http://ttourdev.etisbew.net/images/";

	public static String strIMEINo;

	Cursor constantCursor = null;

	String strStoredTime;

	public static String flg = "1", exitAppFlag = "1";
	public static String Key_GET_LANGUAGE_ID = "LANGUAGE_ID";

	public static String Key_GET_FONT_ID = "FONT_ID";

	public static String Key_GET_SUBMIT_ID = "track_submit_id";
	public static SharedPreferences pref1, fontpref;
	public static Editor editor1, fonteditor;
	public static String name = "1", font = "1";

	ArrayList<String> getMethodName = new ArrayList<String>();

	int iterator;
	

	public static SharedPreferences hotel_pref;
	public static Editor hotel_editor;
	public static String GET_HOTELS_IDS = "hotelId";
	public static String GET_PACKAGE_IDS = "packageId";
	
	private int year;
	private int month;
	private int day;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activitiy_splash);

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc(true).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		ImageLoaderConfiguration imageloader_config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache())
				.discCacheSize(100 * 1024 * 1024).build();

		ImageLoader.getInstance().init(imageloader_config);

//		utility = new Utility(SplashScreen.this);

		pref = getApplicationContext().getSharedPreferences(
				"telangana_tourism", MODE_PRIVATE);
		editor = pref.edit();

		pref1 = getApplicationContext().getSharedPreferences("sam",
				MODE_PRIVATE);
		editor1 = pref1.edit();

		fontpref = getApplicationContext().getSharedPreferences("font",
				MODE_PRIVATE);
		fonteditor = fontpref.edit();
		
		hotel_pref = getApplicationContext().getSharedPreferences("hotel_ids",
				MODE_PRIVATE);
		hotel_editor = hotel_pref.edit();
		
		if(fontpref.getString(Key_GET_FONT_ID, "").length() == 0){
			fonteditor.putString(
					Key_GET_FONT_ID, "1");
			fonteditor.commit();
		}
		
		if(pref.getString(SplashScreen.Key_GET_USER_Track, "").length() == 0){
			editor.putString(
					Key_GET_USER_Track, "false");
			editor.commit();
		}
		
		if(pref1.getString(Key_GET_LANGUAGE_ID, "").length() == 0){
			editor1.putString(
					Key_GET_LANGUAGE_ID, "1");
			editor1.commit();
		}
		

		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		strIMEINo = telephonyManager.getDeviceId();

		try {
			dbbHelper = new TtHelper(SplashScreen.this);
			dbbHelper.createDataBase();
			dbbHelper.openDataBase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		Date today = c.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		Constants.currentDate = formatter.format(today);

		if (Utility.checkInternetConnection(SplashScreen.this)) {
			new GetHotels().execute();
			new GetPackages().execute();
		} else {
			Utility.showAlertNoInternetService(SplashScreen.this);
		}
		
		
		
//		try {
//			JSONArray json = new JSONArray(loadJSONFromAsset());
//
//			StringBuilder stringBuilder = new StringBuilder();
//			stringBuilder.setLength(0);
//
//			for (int i = 0; i < json.length(); i++) {
//				HashMap<String, String> map = new HashMap<String, String>();
//				JSONObject e = json.getJSONObject(i);
//				map.put("unitCode",
//						"Unit Code: " + e.getString("unitCode"));
//
//				stringBuilder.append("," + e.getString("unitCode"));
//			}
//
////			Toast.makeText(
////					SplashScreen.this,
////					"" + stringBuilder.toString().replaceFirst(",", ""),
////					Toast.LENGTH_LONG).show();
//			SplashScreen.hotel_editor.putString(
//					SplashScreen.GET_HOTELS_IDS,
//					""
//							+ stringBuilder.toString()
//									.replaceFirst(",", "").toString());
//			SplashScreen.hotel_editor.commit();
//
//
//		} catch (Exception e) {
//		}
//		
//		try {
//			JSONArray json = new JSONArray(loadJSONFromAsset1());
//
//			StringBuilder stringBuilder1 = new StringBuilder();
//			stringBuilder1.setLength(0);
//
//			for (int i = 0; i < json.length(); i++) {
//				HashMap<String, String> map = new HashMap<String, String>();
//				JSONObject e = json.getJSONObject(i);
//				map.put("cityCode",
//						"City Code: " + e.getString("cityCode"));
//
//				stringBuilder1.append("," + e.getString("cityCode"));
//			}
//
////			Toast.makeText(
////					SplashScreen.this,
////					"" + stringBuilder.toString().replaceFirst(",", ""),
////					Toast.LENGTH_LONG).show();
//			SplashScreen.hotel_editor.putString(
//					SplashScreen.GET_PACKAGE_IDS,
//					""
//							+ stringBuilder1.toString()
//									.replaceFirst(",", "").toString());
//			SplashScreen.hotel_editor.commit();
//
//
//		} catch (Exception e) {
//		}

		// Updating Service
		if (Utility.checkInternetConnection(SplashScreen.this)) {
			new UpdateServices().execute();
		} else {
			Utility.showAlertNoInternetService(SplashScreen.this);
		}
		
		
		
		

		if (pref1.getString(Key_GET_LANGUAGE_ID, "").equalsIgnoreCase("1")) {
			String languageToLoad = "en";
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			this.getBaseContext().getResources()
					.updateConfiguration(config, null);

			Constants.ProximaNova_Regular = Typeface.createFromAsset(
					getApplicationContext().getAssets(),
					"ProximaNova-Regular.otf");

		} else if (pref1.getString(Key_GET_LANGUAGE_ID, "").equalsIgnoreCase(
				"2")) {
			String languageToLoad = "te";
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			this.getBaseContext().getResources()
					.updateConfiguration(config, null);

			Constants.ProximaNova_Regular = Typeface.createFromAsset(
					getApplicationContext().getAssets(), "gautami.ttf");

		} else if (pref1.getString(Key_GET_LANGUAGE_ID, "").equalsIgnoreCase(
				"3")) {
			String languageToLoad = "ur";
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			this.getBaseContext().getResources()
					.updateConfiguration(config, null);

			Constants.ProximaNova_Regular = Typeface.createFromAsset(
					getApplicationContext().getAssets(), "asunaskh.ttf");

		} else if (pref1.getString(Key_GET_LANGUAGE_ID, "").equalsIgnoreCase(
				"4")) {
			String languageToLoad = "hi";
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			this.getBaseContext().getResources()
					.updateConfiguration(config, null);

			Constants.ProximaNova_Regular = Typeface.createFromAsset(
					getApplicationContext().getAssets(),
					"ProximaNova-Regular.otf");
		} else {
			String languageToLoad = "en";
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			this.getBaseContext().getResources()
					.updateConfiguration(config, null);

			Constants.ProximaNova_Regular = Typeface.createFromAsset(
					getApplicationContext().getAssets(),
					"ProximaNova-Regular.otf");
		}

		// Background Service Call
		if (Utility.checkInternetConnection(SplashScreen.this)) {
			// Start the service

			// String select = " SELECT * FROM timestamp";
			// constantCursor = dbbHelper.getReadableDatabase().rawQuery(select,
			// null);
			//
			// startManagingCursor(constantCursor);
			//
			// if (constantCursor.getCount() > 0) {
			// if (constantCursor.moveToFirst()) {
			// // do {
			//
			// strStoredTime = constantCursor.getString(constantCursor
			// .getColumnIndex("StoredTime"));
			//
			//
			// // } while (constantCursor.moveToNext());
			//
			// }
			//
			// }
			//
			// Calendar c = Calendar.getInstance();
			// SimpleDateFormat df = new SimpleDateFormat(
			// "MM/dd/yyyy HH:mm:ss");
			// String currentDate = df.format(c.getTime());
			//
			// Constants.strStoredTime = currentDate;
			//
			// // HH converts hour in 24 hours format (0-23), day calculation
			// SimpleDateFormat format = new SimpleDateFormat(
			// "MM/dd/yyyy HH:mm:ss");
			//
			// Date d1 = null;
			// Date d2 = null;
			//
			// try {
			// d1 = format.parse(strStoredTime);
			// d2 = format.parse(currentDate);
			//
			// // in milliseconds
			// long diff = d2.getTime() - d1.getTime();
			//
			// long diffDays = diff / (24 * 60 * 60 * 1000);
			//
			// WifiManager wifi = (WifiManager)
			// getSystemService(Context.WIFI_SERVICE);
			//
			// // wifi is enabled
			//
			// if (diffDays > 5) {
			// Toast.makeText(getApplicationContext(), "If "+diffDays,
			// Toast.LENGTH_LONG).show();
			// startService(new Intent(
			// this,
			// com.telanganatourism.android.backgroundservice.BackgroundService.class));
			// }
			// else {
			// // Toast.makeText(getApplicationContext(), "Else "+diffDays,
			// Toast.LENGTH_LONG).show();
			// if(isMyServiceRunning()){
			// stopService(new Intent(
			// this,
			// com.telanganatourism.android.backgroundservice.BackgroundService.class));
			// }
			// }
			//
			// } catch (Exception e) {
			// e.printStackTrace();
			// }

		} else {
			// utility.showAlertNoInternetService(SplashScreen.this);
		}

		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (waited < 3000) {
						sleep(100);
						waited += 100;
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					// finish();

					// i = new Intent(getApplicationContext(),
					// SettingsActivity.class);
					// startActivity(i);
					// finish();

					String name = pref.getString(Key_GET_USER_ID, "");

					if (pref1.getString(Key_GET_LANGUAGE_ID, "").length() > 0) {

						Constants.selectLanguage = pref1.getString(
								Key_GET_LANGUAGE_ID, "");
					} else {
						Constants.selectLanguage = "1";
					}

					if (name.length() > 0) {
						// Constants.selectLanguage = "1";
						SplashScreen.user_id = name;
						Intent i = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(i);
						finish();
					} else {
						// Constants.selectLanguage = "1";
						Intent i = new Intent(getApplicationContext(),
								SettingsActivity.class);// Login
						startActivity(i);
						finish();
					}
				}
			}
		};
		splashThread.start();
	}

	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (com.telanganatourism.android.phase2.backgroundservice.BackgroundService.class
					.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	class GetHotels extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+Constants.Base_url1+"hotels.jsp");
			return ServiceCalls1
					.getJSONString(Constants.Base_url1+"hotels.jsp");
		}

		@Override
		protected void onProgressUpdate(Integer... a) {
			super.onProgressUpdate(a);
			System.out.println("time" + a);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
//			Log.e("Result : ", result);
			
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			if (null == result || result.length() == 0) {
				showDialogMsg(SplashScreen.this, "No hotels data found from web!!!");
			} else {

				try {
					JSONArray json = new JSONArray(result);

					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.setLength(0);

					for (int i = 0; i < json.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						JSONObject e = json.getJSONObject(i);
						map.put("unitCode",
								"Unit Code: " + e.getString("unitCode"));

						stringBuilder.append("," + e.getString("unitCode"));
					}

//					Toast.makeText(
//							SplashScreen.this,
//							"" + stringBuilder.toString().replaceFirst(",", ""),
//							Toast.LENGTH_LONG).show();
					SplashScreen.hotel_editor.putString(
							SplashScreen.GET_HOTELS_IDS,
							""
									+ stringBuilder.toString()
											.replaceFirst(",", "").toString());
					SplashScreen.hotel_editor.commit();

//					ArrayList<String> lists = new ArrayList<String>();
//					/*
//					 * given string will be split by the argument delimiter
//					 * provided.
//					 */
//					String[] temp = SplashScreen.hotel_pref.getString(
//							SplashScreen.GET_HOTELS_IDS, "").split(",");
//					/* print substrings */
//					lists.clear();
//					for (int i = 0; i < temp.length; i++) {
//						System.out.println(temp[i]);
//						lists.add(temp[i]);
//					}
//
//					if (lists.contains("106023")) {
//						Toast.makeText(SplashScreen.this, "found",
//								Toast.LENGTH_LONG).show();
//					} else {
//						Toast.makeText(SplashScreen.this, "not found element",
//								Toast.LENGTH_LONG).show();
//					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
	
	class GetPackages extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+Constants.Base_url1+"packagelocations.jsp");
			return ServiceCalls1
					.getJSONString(Constants.Base_url1+"packagelocations.jsp");
		}

		@Override
		protected void onProgressUpdate(Integer... a) {
			super.onProgressUpdate(a);
//			System.out.println("time" + a);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
//			Log.e("Result : ", result);
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			if (null == result || result.length() == 0) {
				showDialogMsg(SplashScreen.this, "No package data found from web!!!");
			} else {

				try {
					JSONArray json = new JSONArray(result);

					StringBuilder stringBuilder1 = new StringBuilder();
					stringBuilder1.setLength(0);

					for (int i = 0; i < json.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						JSONObject e = json.getJSONObject(i);
						map.put("cityCode",
								"City Code: " + e.getString("cityCode"));

						stringBuilder1.append("," + e.getString("cityCode"));
					}

//					Toast.makeText(
//							SplashScreen.this,
//							"" + stringBuilder.toString().replaceFirst(",", ""),
//							Toast.LENGTH_LONG).show();
					SplashScreen.hotel_editor.putString(
							SplashScreen.GET_PACKAGE_IDS,
							""
									+ stringBuilder1.toString()
											.replaceFirst(",", "").toString());
					SplashScreen.hotel_editor.commit();


				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
	
	class UpdateServices extends AsyncTask<String, Void, String> {

		SimpleDateFormat dateFormat;
		Date date1;

		@Override
		protected String doInBackground(String... params) {

			dateFormat = new SimpleDateFormat("yyyy-MM-dd.HH@mm@ss");
			date1 = new Date();
			System.out.println("" + dateFormat.format(date1));
			System.out.println("url is"+SplashScreen.Base_url
					+ "getUpdatedServices/" + ""
					+ dateFormat.format(date1).replace(" ", "%20"));

			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getUpdatedServices/" + ""
					+ dateFormat.format(date1).replace(" ", "%20"));
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {

				Toast.makeText(SplashScreen.this,
						"No data found from Server!!!", Toast.LENGTH_LONG)
						.show();

			} else {

				// result =
				// "{\"ServiceNames\":[{\"name\":\"getDestinationCategoriesList\"},{\"name\":\"getDestinationsList\"},{\"name\":\"getDestinationsDetail\"}]}";
				try {
					try {

						if (result.contains("ServiceNames")) {

							Constants.update_service_flag = true;
							JSONObject jsonObject = new JSONObject(result);

							JSONArray jsonArray = jsonObject
									.getJSONArray("ServiceNames");

							for (int i = 0; i < jsonArray.length(); i++) {
								String str = jsonArray.getJSONObject(i)
										.getString("name").toString();

								if (str.contains("getDest")) {
									Constants.destinations_flag = true;
									dbbHelper.updateStoredTime(
											"Destination_timestamp",
											dateFormat.format(date1));
								} else if (str.contains("getAccom")) {
									Constants.accomodation_flag = true;
									dbbHelper.updateStoredTime(
											"Accomodation_timestamp",
											dateFormat.format(date1));
								} else if (str.contains("getCult")) {
									Constants.culture_flag = true;
									dbbHelper.updateStoredTime(
											"Culture_timestamp",
											dateFormat.format(date1));
								} else if (str.contains("getPack")) {
									Constants.packages_flag = true;
									dbbHelper.updateStoredTime(
											"Tours_timestamp",
											dateFormat.format(date1));
								}
							}

							if (Constants.destinations_flag == true) {

								Constants.destinations_flag = false;
								// for (language = 1; language <= 4; language++)
								// {

								String deleteQuery = "DELETE FROM  DestinationCategoryList where language_id='"
										+ "1" + "'";

								String deleteQuery1 = "DELETE FROM  Destination_Content where language_id='"
										+ "1" + "'";

								String deleteQuery2 = "DELETE FROM  Destinations_Detail_Content where language_id='"
										+ "1" + "'";

								// SQLiteDatabase database = dbbHelper
								// .getWritableDatabase();
								//
								//
								// Log.d("query", deleteQuery);
								// database.execSQL(deleteQuery);

								dbbHelper.deleteAll(deleteQuery);
								dbbHelper.deleteAll(deleteQuery1);
								dbbHelper.deleteAll(deleteQuery2);

								startService(new Intent(
										SplashScreen.this,
										com.telanganatourism.android.phase2.backgroundservice.BackgroundService.class));
								// for (iterator = 1; iterator <= 10;
								// iterator++) {
								// new
								// DestinationCategoryTask("DestinationCategoryList",
								// iterator, String.valueOf(1)).execute();
								//
								// }
								// }
							}
						} else {
							Constants.update_service_flag = false;
							Constants.destinations_flag = false;
							Constants.accomodation_flag = false;
							Constants.culture_flag = false;
							Constants.packages_flag = false;
							// Toast.makeText(getApplicationContext(),
							// "No Services to update",
							// Toast.LENGTH_LONG).show();

							stopService(new Intent(
									SplashScreen.this,
									com.telanganatourism.android.phase2.backgroundservice.BackgroundService.class));
						}

					} catch (Exception e) {
						// TODO: handle exception
						Log.e("Error ", "" + e);
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	// class DestinationCategoryTask extends AsyncTask<String, Void, String> {
	//
	// private ProgressDialog progressDialog;
	//
	// String tblName = "", language_id;
	// int locationId;
	//
	// public DestinationCategoryTask(String tableName, int locId,
	// String lang_id) {
	// // TODO Auto-generated constructor stub
	// this.tblName = tableName;
	// this.locationId = locId;
	// this.language_id = lang_id;
	//
	// }
	//
	// @Override
	// protected void onPreExecute() {
	//
	// super.onPreExecute();
	// progressDialog = new ProgressDialog(SplashScreen.this);
	// progressDialog.setMessage("Loading ...");
	// progressDialog.setIndeterminate(false);
	// progressDialog.setCancelable(false);
	// progressDialog.show();
	//
	// }
	//
	// @Override
	// protected String doInBackground(String... params) {
	// return ServiceCalls1.getJSONString(SplashScreen.Base_url
	// + "getDestinationCategoriesList/" + locationId + "/"
	// + language_id);
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// super.onPostExecute(result);
	//
	//
	//
	// if (null == result || result.length() == 0) {
	//
	// Toast.makeText(SplashScreen.this, "No data found from Server!!!",
	// Toast.LENGTH_LONG).show();
	//
	// } else {
	//
	// try {
	//
	// // dbbHelper = new TtHelper(BackgroundService.this);
	// // dbbHelper.openDataBase();
	// //
	// // baseDatabase = new BaseDatabase(BackgroundService.this);
	// // baseDatabase.openDataBase();
	// //
	// // // if (baseDatabase.display("DestinationCategoryList") ==
	// // 0)
	// // // {
	// // //
	// // //
	//
	//
	// dbbHelper.insertDestinationCategory1(String.valueOf(locationId),
	// result, language_id);
	//
	// // Toast.makeText(getActivity(), language_id, 200).show();
	// // // } else {
	// // //
	// // dbbHelper.updateDestinationCategory(String.valueOf(locationId),
	// // result);
	// // //
	// // // }
	// //
	// // if (baseDatabase.checkCategoryId(tblName,
	// // String.valueOf(locationId)) == 0) {
	// //
	// // dbbHelper.insertDestinationCategory(
	// // String.valueOf(locationId), result);
	// // } else {
	// //
	// // dbbHelper.updateDestinationCategory(
	// // String.valueOf(locationId), result);
	// //
	// // }
	//
	// JSONObject jsonResultObj = new JSONObject(result);
	//
	// JSONArray jsonArray = jsonResultObj
	// .getJSONArray("SubCategory");
	//
	// for (int i = 0; i < jsonArray.length(); i++) {
	//
	// new DestinationTask("Destination_Content",
	// "getDestinationsList", jsonArray
	// .getJSONObject(i).get("id").toString()
	// .trim(), String.valueOf(locationId),
	// language_id).execute();
	// }
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// Toast.makeText(SplashScreen.this, "Categories exception",
	// Toast.LENGTH_LONG).show();
	// }
	//
	// // dbbHelper.close();
	// // baseDatabase.close();
	//
	// if (null != progressDialog && progressDialog.isShowing()) {
	// progressDialog.dismiss();
	// }
	//
	// }
	//
	// }
	// }
	//
	// class DestinationTask extends AsyncTask<String, Void, String> {
	//
	// ProgressDialog progressDialog;
	//
	// String moduleStr = "", tableNameStr = "", methodNameStr = "",
	// locationId = "", language_id;
	//
	// public DestinationTask(String tableName, String methodName,
	// String moduleId, String locId, String lang_id) {
	// // TODO Auto-generated constructor stub
	// this.moduleStr = moduleId;
	// this.tableNameStr = tableName;
	// this.methodNameStr = methodName;
	// this.locationId = locId;
	// this.language_id = lang_id;
	// }
	//
	// @Override
	// protected void onPreExecute() {
	//
	// super.onPreExecute();
	// progressDialog = new ProgressDialog(SplashScreen.this);
	// progressDialog.setMessage("Loading ...");
	// progressDialog.setIndeterminate(false);
	// progressDialog.setCancelable(false);
	// progressDialog.show();
	//
	// }
	//
	// @Override
	// protected String doInBackground(String... params) {
	// return ServiceCalls1.getJSONString(SplashScreen.Base_url
	// + "getDestinationsList/" + locationId + "/" + moduleStr
	// + "/" + language_id);
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// super.onPostExecute(result);
	//
	// if (null == result || result.length() == 0) {
	//
	// Toast.makeText(SplashScreen.this, "No data found from Server!!!",
	// Toast.LENGTH_LONG).show();
	//
	// } else {
	//
	// try {
	//
	// // if (baseDatabase.display("Destination_Content") == 0) {
	// //
	// // dbbHelper.insertDestinationContent("1", "Destination",
	// // result);
	// // Toast.makeText(BackgroundService.this,
	// // "A row is inserted", Toast.LENGTH_LONG).show();
	// // } else {
	// // dbFlag = true;
	// // dbbHelper.updateDestinationContent("Destination",
	// // result);
	// //
	// // Toast.makeText(BackgroundService.this,
	// // "A row is updated", Toast.LENGTH_LONG).show();
	// //
	// // }
	//
	// // if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
	// // locationId) == 0) {
	// //
	//
	// dbbHelper.insertDestinationContent(locationId, moduleStr,
	// result, language_id);
	//
	// // dbbHelper.insertShoppingContent(tableNameStr,
	// // locationId, moduleStr, result, language_id);
	// // } else {
	// //
	// // dbbHelper.updateDetailsContent(tableNameStr, moduleStr,
	// // result, locationId, language_id);
	// // //
	// // Toast.makeText(getActivity(), moduleStr, 200).show();
	// //
	// // }
	//
	// JSONObject jsonResultObj = new JSONObject(result);
	//
	// JSONArray jsonArray = jsonResultObj
	// .getJSONArray("Attractions");
	//
	// // destinationsDetailsArray = new ArrayList<String>();
	//
	// for (int i = 0; i < jsonArray.length(); i++) {
	//
	// // destinationsDetailsArray.add(jsonArray.getJSONObject(i)
	// // .get("id").toString().trim());
	// new DestinationDetails(locationId,
	// "getDestinationsDetail", jsonArray
	// .getJSONObject(i).get("id").toString()
	// .trim(), language_id, moduleStr)
	// .execute();
	// }
	//
	// // dbbHelper.close();
	// // baseDatabase.close();
	//
	// } catch (Exception e1) {
	// e1.printStackTrace();
	//
	// Toast.makeText(SplashScreen.this,
	// "Destinations List exception", Toast.LENGTH_LONG)
	// .show();
	// }
	//
	// if (null != progressDialog && progressDialog.isShowing()) {
	// progressDialog.dismiss();
	// }
	//
	// }
	//
	// }
	// }
	//
	// class DestinationDetails extends AsyncTask<String, Void, String> {
	//
	// ProgressDialog progressDialog;
	//
	// String categoryStr = "", module1Str = "", tableNameStr = "",
	// methodNameStr = "", location1Id = "", language_id = "";
	//
	// public DestinationDetails(String locId, String methodName,
	// String moduleId, String lang_id, String cat_id) {
	// // TODO Auto-generated constructor stub
	// this.location1Id = locId;
	// this.module1Str = moduleId;
	// this.methodNameStr = methodName;
	// this.language_id = lang_id;
	// this.categoryStr = cat_id;
	// }
	//
	//
	//
	// @Override
	// protected String doInBackground(String... params) {
	// return ServiceCalls1.getJSONString(SplashScreen.Base_url
	// + "getDestinationsDetail/" + location1Id + "/" + module1Str +
	// "/17.430186/78.405196/"
	// + language_id);
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// super.onPostExecute(result);
	// if (null == result || result.length() == 0) {
	// Toast.makeText(SplashScreen.this, "No data found from Server!!!",
	// Toast.LENGTH_LONG).show();
	// } else {
	//
	// try {
	//
	// // Toast.makeText(BackgroundService.this,
	// // "Check Id " +
	// // baseDatabase.checkModuleId("DestinationDetailContent",
	// // moduleStr), Toast.LENGTH_LONG).show();
	// // if (baseDatabase
	// // .checkModuleId(tableNameStr, moduleStr, locationId) == 0)
	// // {
	//
	// // if(dbbHelper.myDataBase.isOpen())
	// // {
	// // Toast.makeText(getActivity(), "open", 200).show();
	// // }else
	// // {
	// // Toast.makeText(getActivity(), "close", 200).show();
	// // }
	//
	// // dbbHelper.insertDetailsContent1("Shop_Online_Details",location1Id,
	// // module1Str, result, language_id);
	//
	// dbbHelper.insertDestinationDetailsContent(location1Id,
	// module1Str, result, language_id);
	//
	// // dbbHelper.updateDestinationDetailsContent(module1Str,
	// // result, location1Id);
	//
	// Toast.makeText(SplashScreen.this, module1Str, Toast.LENGTH_LONG)
	// .show();
	//
	// // } else {
	// //
	// // dbbHelper.updateDetailsContent(tableNameStr, moduleStr,
	// // result, locationId);
	// //
	// // }
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// Toast.makeText(SplashScreen.this, "exception",
	// Toast.LENGTH_LONG).show();
	// }
	//
	// if (null != progressDialog && progressDialog.isShowing()) {
	// progressDialog.dismiss();
	// }
	//
	// }
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// progressDialog = new ProgressDialog(SplashScreen.this);
	// progressDialog.setMessage("Loading ...");
	// progressDialog.setIndeterminate(false);
	// progressDialog.setCancelable(false);
	// progressDialog.show();
	//
	// }
	// }
	
	public String loadJSONFromAsset() {
		String json = null;
		try {
			InputStream is = getApplicationContext().getAssets().open(
					"jsonresponse.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}
	
	public String loadJSONFromAsset1() {
		String json = null;
		try {
			InputStream is = getApplicationContext().getAssets().open(
					"packagestype.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(SplashScreen.this, "6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(SplashScreen.this);
	}
	public void showDialogMsg(Context con, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
