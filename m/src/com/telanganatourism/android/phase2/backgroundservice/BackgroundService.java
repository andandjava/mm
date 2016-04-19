package com.telanganatourism.android.phase2.backgroundservice;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.telanganatourism.android.phase2.Constants;
import com.telanganatourism.android.phase2.ServiceCalls1;
import com.telanganatourism.android.phase2.SplashScreen;
import com.telanganatourism.android.phase2.WebServiceCalls;
import com.telanganatourism.android.phase2.database.helper.BaseDatabase;
import com.telanganatourism.android.phase2.database.helper.TtHelper;
import com.telanganatourism.android.phase2.util.Utility;

public class BackgroundService extends Service {

	TtHelper dbbHelper;
	BaseDatabase baseDatabase;
	boolean dbFlag = false;

	ArrayList<String> destinationsDetailsArray;

	Utility utility;

	int iterator, language_id;

	public static String Base_url = "http://ttourdev.etisbew.net/WebServices/";

	public BackgroundService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		// Toast.makeText(this, "Service was Created",
		// Toast.LENGTH_LONG).show();

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// Perform your long running operations here.
		setAlarm();
	}

	@Override
	public void onDestroy() {
		// Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	}

	public void setAlarm() {

		// dbbHelper.close();
		// baseDatabase.close();

		utility = new Utility(BackgroundService.this);

//		if (utility.IsNetConnected(BackgroundService.this)) {

//			for (language_id = 1; language_id <= 4; language_id++) {

				for (iterator = 1; iterator <= 10; iterator++) {
					new DestinationCategoryTask("DestinationCategoryList",
							iterator, String.valueOf(1)).execute();

//					 new AccomodationTask("Accomodation_Content",
//					 String.valueOf(iterator)).execute();
					
					// new SlideImagesJSONParse(iterator).execute();

				}
//				 new CulturesTask().execute();
//				 new TourPackagesCategoryTask().execute();
//			}
			// new DestinationTask().execute();
			// new AccomodationTask().execute();
			//
			// new CulturesTask().execute();
			// new TourPackagesCategoryTask().execute();
//		} else {
//
//			Toast.makeText(BackgroundService.this, "Network not available",
//					Toast.LENGTH_LONG).show();
//		}

		// long currentTimeMillis = System.currentTimeMillis();
		// long nextUpdateTimeMillis = currentTimeMillis +
		// Constants.updatingTime
		// * DateUtils.MINUTE_IN_MILLIS;
		//
		// Intent serviceIntent = new Intent(this, BackgroundService.class);
		// PendingIntent pi = PendingIntent.getService(this, 131313,
		// serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		//
		// AlarmManager am = (AlarmManager)
		// getSystemService(Context.ALARM_SERVICE);
		// am.set(AlarmManager.RTC_WAKEUP, nextUpdateTimeMillis, pi);

	}
	
	class DestinationCategoryTask extends AsyncTask<String, Void, String> {

		String tblName = "", language_id;
		int locationId;

		public DestinationCategoryTask(String tableName, int locId,
				String lang_id) {
			// TODO Auto-generated constructor stub
			this.tblName = tableName;
			this.locationId = locId;
			this.language_id = lang_id;

		}
		
		@Override
		protected void onPreExecute() {

			super.onPreExecute();


		}

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getDestinationCategoriesList/" + locationId + "/"
					+ language_id);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			

			if (null == result || result.length() == 0) {

				Toast.makeText(BackgroundService.this, "No data found from Server!!!",
						Toast.LENGTH_LONG).show();

			} else {

				try {

					 dbbHelper = new TtHelper(BackgroundService.this);
					 dbbHelper.openDataBase();
					//
					// baseDatabase = new BaseDatabase(BackgroundService.this);
					// baseDatabase.openDataBase();
					//
					// // if (baseDatabase.display("DestinationCategoryList") ==
					// 0)
					// // {
					// //
					// //
					
					
					 dbbHelper.insertDestinationCategory1(String.valueOf(locationId),
					 result, language_id);

					// Toast.makeText(getActivity(), language_id, 200).show();
					// // } else {
					// //
					// dbbHelper.updateDestinationCategory(String.valueOf(locationId),
					// result);
					// //
					// // }
					//
					// if (baseDatabase.checkCategoryId(tblName,
					// String.valueOf(locationId)) == 0) {
					//
					// dbbHelper.insertDestinationCategory(
					// String.valueOf(locationId), result);
					// } else {
					//
					// dbbHelper.updateDestinationCategory(
					// String.valueOf(locationId), result);
					//
					// }

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj
							.getJSONArray("SubCategory");

					for (int i = 0; i < jsonArray.length(); i++) {

						new DestinationTask("Destination_Content",
								"getDestinationsList", jsonArray
										.getJSONObject(i).get("id").toString()
										.trim(), String.valueOf(locationId),
								language_id).execute();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					Toast.makeText(BackgroundService.this, "Categories exception",
							Toast.LENGTH_LONG).show();
				}

				// dbbHelper.close();
				// baseDatabase.close();


			}

		}
	}

	class DestinationTask extends AsyncTask<String, Void, String> {

		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				locationId = "", language_id;

		public DestinationTask(String tableName, String methodName,
				String moduleId, String locId, String lang_id) {
			// TODO Auto-generated constructor stub
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
			this.locationId = locId;
			this.language_id = lang_id;
		}
		
		@Override
		protected void onPreExecute() {

			super.onPreExecute();


		}

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getDestinationsList/" + locationId + "/" + moduleStr
					+ "/" + language_id);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {

				Toast.makeText(BackgroundService.this, "No data found from Server!!!",
						Toast.LENGTH_LONG).show();

			} else {

				try {

					// if (baseDatabase.display("Destination_Content") == 0) {
					//
					// dbbHelper.insertDestinationContent("1", "Destination",
					// result);
					// Toast.makeText(BackgroundService.this,
					// "A row is inserted", Toast.LENGTH_LONG).show();
					// } else {
					// dbFlag = true;
					// dbbHelper.updateDestinationContent("Destination",
					// result);
					//
					// Toast.makeText(BackgroundService.this,
					// "A row is updated", Toast.LENGTH_LONG).show();
					//
					// }

					// if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
					// locationId) == 0) {
					//
					
					 dbbHelper.insertDestinationContent(locationId, moduleStr,
					 result, language_id);

					// dbbHelper.insertShoppingContent(tableNameStr,
					// locationId, moduleStr, result, language_id);
					// } else {
					//
					// dbbHelper.updateDetailsContent(tableNameStr, moduleStr,
					// result, locationId, language_id);
					// //
					// Toast.makeText(getActivity(), moduleStr, 200).show();
					//
					// }

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj
							.getJSONArray("Attractions");

					// destinationsDetailsArray = new ArrayList<String>();

					for (int i = 0; i < jsonArray.length(); i++) {

						// destinationsDetailsArray.add(jsonArray.getJSONObject(i)
						// .get("id").toString().trim());
						new DestinationDetails(locationId,
								"getDestinationsDetail", jsonArray
										.getJSONObject(i).get("id").toString()
										.trim(), language_id, moduleStr)
								.execute();
					}

					// dbbHelper.close();
					// baseDatabase.close();

				} catch (Exception e1) {
					e1.printStackTrace();

					Toast.makeText(BackgroundService.this,
							"Destinations List exception", Toast.LENGTH_LONG)
							.show();
				}

			}

		}
	}

	class DestinationDetails extends AsyncTask<String, Void, String> {
		
		String categoryStr = "", module1Str = "", tableNameStr = "",
				methodNameStr = "", location1Id = "", language_id = "";

		public DestinationDetails(String locId, String methodName,
				String moduleId, String lang_id, String cat_id) {
			// TODO Auto-generated constructor stub
			this.location1Id = locId;
			this.module1Str = moduleId;
			this.methodNameStr = methodName;
			this.language_id = lang_id;
			this.categoryStr = cat_id;
		}
		
		

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getDestinationsDetail/" + location1Id + "/" + module1Str + "/17.430186/78.405196/"
					+ language_id);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (null == result || result.length() == 0) {
				Toast.makeText(BackgroundService.this, "No data found from Server!!!",
						Toast.LENGTH_LONG).show();
			} else {

				try {

					// Toast.makeText(BackgroundService.this,
					// "Check Id " +
					// baseDatabase.checkModuleId("DestinationDetailContent",
					// moduleStr), Toast.LENGTH_LONG).show();
					// if (baseDatabase
					// .checkModuleId(tableNameStr, moduleStr, locationId) == 0)
					// {

					// if(dbbHelper.myDataBase.isOpen())
					// {
					// Toast.makeText(getActivity(), "open", 200).show();
					// }else
					// {
					// Toast.makeText(getActivity(), "close", 200).show();
					// }

					// dbbHelper.insertDetailsContent1("Shop_Online_Details",location1Id,
					// module1Str, result, language_id);
					
					 dbbHelper.insertDestinationDetailsContent(location1Id,
					 module1Str, result, language_id);

//					dbbHelper.updateDestinationDetailsContent(module1Str,
//							result, location1Id);

//					Toast.makeText(BackgroundService.this, module1Str, Toast.LENGTH_LONG)
//							.show();

					// } else {
					//
					// dbbHelper.updateDetailsContent(tableNameStr, moduleStr,
					// result, locationId);
					//
					// }
				} catch (Exception e1) {
					e1.printStackTrace();
					Toast.makeText(BackgroundService.this, "exception",
							Toast.LENGTH_LONG).show();
				}


			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}
	}

//	class DestinationCategoryTask extends AsyncTask<String, Void, String> {
//
//		String tblName = "";
//		int locationId, lan_id;
//
//		public DestinationCategoryTask(String tableName, int locId) {
//			// TODO Auto-generated constructor stub
//			this.tblName = tableName;
//			this.locationId = locId;
//			// this.lan_id = language_id;
//
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			return ServiceCalls1.getJSONString(Base_url
//					+ "getDestinationCategoriesList/" + locationId);
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//
//			if (null == result || result.length() == 0) {
//
//				// Toast.makeText(BackgroundService.this,
//				// "No data found from Server!!!", Toast.LENGTH_LONG)
//				// .show();
//
//			} else {
//
//				try {
//
//					dbbHelper = new TtHelper(BackgroundService.this);
//					dbbHelper.openDataBase();
//
//					baseDatabase = new BaseDatabase(BackgroundService.this);
//					baseDatabase.openDataBase();
//
//					// if (baseDatabase.display("DestinationCategoryList") == 0)
//					// {
//					//
//					// dbbHelper.insertDestinationCategory(String.valueOf(locationId),
//					// result);
//					// } else {
//					// dbbHelper.updateDestinationCategory(String.valueOf(locationId),
//					// result);
//					//
//					// }
//
//					// Store the Updated time in timestamp
//					// dbbHelper.updateStoredTime(Constants.strStoredTime);
//
//					//
//					if (baseDatabase.checkCategoryId(tblName,
//							String.valueOf(locationId)) == 0) {
//
//						dbbHelper.insertDestinationCategory(
//								String.valueOf(locationId), result);
//					} else {
//
//						dbbHelper.updateDestinationCategory(
//								String.valueOf(locationId), result);
//
//					}
//
//					JSONObject jsonResultObj = new JSONObject(result);
//
//					JSONArray jsonArray = jsonResultObj
//							.getJSONArray("SubCategory");
//
//					for (int i = 0; i < jsonArray.length(); i++) {
//
//						new DestinationTask("Destination_Content",
//								"getDestinationsList", jsonArray
//										.getJSONObject(i).get("id").toString()
//										.trim(), String.valueOf(locationId))
//								.execute();
//					}
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//
//				// dbbHelper.close();
//				// baseDatabase.close();
//
//			}
//
//		}
//	}

	class TourPackagesCategoryTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(Base_url
					+ "getPackageCategoriesList");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {

				// Toast.makeText(BackgroundService.this,
				// "No data found from Server!!!", Toast.LENGTH_LONG)
				// .show();

			} else {

				try {

					// dbbHelper = new TtHelper(BackgroundService.this);
					// dbbHelper.openDataBase();
					//
					// baseDatabase = new BaseDatabase(BackgroundService.this);
					// baseDatabase.openDataBase();

					if (baseDatabase.display("TourPackagesCategoryList") == 0) {

//						dbbHelper.insertTouPackagesCategory("1", result);

					} else {
						dbbHelper.updateTourPackagesCategory("1", result);

					}

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj
							.getJSONArray("PackageType");

					for (int i = 0; i < jsonArray.length(); i++) {

						new TourPakagesTask("TourPackagesContent",
								"getPackagesList", jsonArray.getJSONObject(i)
										.get("id").toString().trim()).execute();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

//	class DestinationTask extends AsyncTask<String, Void, String> {
//
//		String moduleStr = "", tableNameStr = "", methodNameStr = "",
//				locationId = "", language_id = "";
//
//		public DestinationTask(String tableName, String methodName,
//				String moduleId, String locId) {
//			// TODO Auto-generated constructor stub
//			this.moduleStr = moduleId;
//			this.tableNameStr = tableName;
//			this.methodNameStr = methodName;
//			this.locationId = locId;
//			// this.language_id = languageid;
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			return ServiceCalls1.getJSONString(Base_url + methodNameStr + "/"
//					+ locationId + "/" + moduleStr);
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//
//			if (null == result || result.length() == 0) {
//
//				// Toast.makeText(BackgroundService.this,
//				// "No data found from Server!!!", Toast.LENGTH_LONG)
//				// .show();
//
//			} else {
//
//				try {
//
//					// if (baseDatabase.display("Destination_Content") == 0) {
//					//
//					// dbbHelper.insertDestinationContent("1", "Destination",
//					// result);
//					// Toast.makeText(BackgroundService.this,
//					// "A row is inserted", Toast.LENGTH_LONG).show();
//					// } else {
//					// dbFlag = true;
//					// dbbHelper.updateDestinationContent("Destination",
//					// result);
//					//
//					// Toast.makeText(BackgroundService.this,
//					// "A row is updated", Toast.LENGTH_LONG).show();
//					//
//					// }
//
//					if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
//							locationId) == 0) {
//
////						dbbHelper.insertDetailsContent(tableNameStr,
////								locationId, moduleStr, result);
//					} else {
//
////						dbbHelper.updateDetailsContent(tableNameStr, moduleStr,
////								result, locationId);
//
//					}
//
//					// JSONObject jsonResultObj = new JSONObject(result);
//					//
//					// JSONArray jsonArray = jsonResultObj
//					// .getJSONArray("Attractions");
//					//
//					// destinationsDetailsArray = new ArrayList<String>();
//					//
//					// for (int i = 0; i < jsonArray.length(); i++) {
//					//
//					// destinationsDetailsArray.add(jsonArray.getJSONObject(i)
//					// .get("id").toString().trim());
//					// new DestinationDetails(locationId,
//					// "DestinationDetailContent",
//					// "getDestinationsDetail", jsonArray
//					// .getJSONObject(i).get("id").toString()
//					// .trim()).execute();
//					// }
//
//					// dbbHelper.close();
//					// baseDatabase.close();
//
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//
//			}
//
//		}
//	}

	class TourPakagesTask extends AsyncTask<String, Void, String> {

		String moduleStr = "", tableNameStr = "", methodNameStr = "";

		public TourPakagesTask(String tableName, String methodName,
				String moduleId) {
			// TODO Auto-generated constructor stub
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
		}

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(Base_url + methodNameStr + "/"
					+ moduleStr);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {

				// Toast.makeText(BackgroundService.this,
				// "No data found from Server!!!", Toast.LENGTH_LONG)
				// .show();

			} else {

				try {

					// if (baseDatabase.display("Destination_Content") == 0) {
					//
					// dbbHelper.insertDestinationContent("1", "Destination",
					// result);
					// Toast.makeText(BackgroundService.this,
					// "A row is inserted", Toast.LENGTH_LONG).show();
					// } else {
					// dbFlag = true;
					// dbbHelper.updateDestinationContent("Destination",
					// result);
					//
					// Toast.makeText(BackgroundService.this,
					// "A row is updated", Toast.LENGTH_LONG).show();
					//
					// }

					if (baseDatabase
							.checkModuleId(tableNameStr, moduleStr, "1") == 0) {

//						dbbHelper.insertDetailsContent(tableNameStr, "1",
//								moduleStr, result);
					} else {

//						dbbHelper.updateDetailsContent(tableNameStr, moduleStr,
//								result, "1");

					}

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj.getJSONArray("Package");

					for (int i = 0; i < jsonArray.length(); i++) {

						// destinationsDetailsArray.add(object)
						new PackagesDetailsTask("1",
								"TourPackagesDetailContent",
								"getPackageDetails", jsonArray.getJSONObject(i)
										.get("id").toString().trim()).execute();
					}

					// dbbHelper.close();
					// baseDatabase.close();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	class AccomodationTask extends AsyncTask<String, Void, String> {

		String tableNameStr = "", locationId = "";

		public AccomodationTask(String tableName, String locId) {
			// TODO Auto-generated constructor stub
			this.tableNameStr = tableName;
			this.locationId = locId;
		}

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(Base_url
					+ "getAccomodationsList/" + locationId);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {
				// Toast.makeText(BackgroundService.this,
				// "No data found from Server!!!", Toast.LENGTH_LONG)
				// .show();

			} else {

				try {

					// if (baseDatabase.display("Accomodation_Content") == 0) {
					//
					// dbbHelper.insertAccomodationContent("1",
					// "Accomodation", result);
					//
					// } else {
					//
					// dbbHelper.updateAccomodationContent("Accomodation",
					// result);
					//
					// }

					if (baseDatabase.checkCategoryId(tableNameStr,
							String.valueOf(locationId)) == 0) {

//						dbbHelper.insertAccomodationContent(locationId,
//								"Accomodation", result);
					} else {

						dbbHelper.updateAccomodationContent("Accomodation",
								result, locationId);

					}

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj
							.getJSONArray("stayplace");

					for (int i = 0; i < jsonArray.length(); i++) {

						new AccomodationsDetailsTask(locationId,
								"AccomodationDetailContent",
								"getAccomodationDetails1", jsonArray
										.getJSONObject(i).get("id").toString()
										.trim()).execute();
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	class CulturesTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(Base_url + "getCultureList");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {
				// Toast.makeText(BackgroundService.this,
				// "No data found from Server!!!", Toast.LENGTH_LONG)
				// .show();

			} else {

				try {

					// if (baseDatabase.display("Culture_Content") == 0) {

					dbbHelper.insertCultureContent("1", "Culture", result);

					// } else {
					//
					// dbbHelper.updateCultureContent("Culture", result);
					//
					// }

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj.getJSONArray("Culture");

					for (int i = 0; i < jsonArray.length(); i++) {

						new CulturDetailsTask("1", "CultureDeatilContent",
								"getCultureDetails", jsonArray.getJSONObject(i)
										.get("id").toString().trim()).execute();
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

//	class DestinationDetails extends AsyncTask<String, Void, String> {
//		String moduleStr = "", tableNameStr = "", methodNameStr = "",
//				locationId = "";
//
//		public DestinationDetails(String locId, String tableName,
//				String methodName, String moduleId) {
//			// TODO Auto-generated constructor stub
//			this.locationId = locId;
//			this.moduleStr = moduleId;
//			this.tableNameStr = tableName;
//			this.methodNameStr = methodName;
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			return ServiceCalls1.getJSONString(Base_url + methodNameStr + "/"
//					+ locationId + "/" + moduleStr + "/17.430186/78.405196");
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//			if (null == result || result.length() == 0) {
//				// Toast.makeText(getApplicationContext(),
//				// "No data found from Server!!!", Toast.LENGTH_LONG)
//				// .show();
//			} else {
//
//				try {
//
//					// Toast.makeText(BackgroundService.this,"Check Id " +
//					// baseDatabase.checkModuleId("DestinationDetailContent",
//					// moduleStr), Toast.LENGTH_LONG).show();
//					if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
//							locationId) == 0) {
//
//////						dbbHelper.insertDetailsContent(tableNameStr,
////								locationId, moduleStr, result);
//
//					} else {
//
////						dbbHelper.updateDetailsContent(tableNameStr, moduleStr,
////								result, locationId);
//
//					}
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//
//			}
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//	}

	class AccomodationsDetailsTask extends AsyncTask<String, Void, String> {
		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				locationId = "";

		public AccomodationsDetailsTask(String locId, String tableName,
				String methodName, String moduleId) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
		}

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(Base_url + methodNameStr + "/"
					+ locationId + "/" + moduleStr);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (null == result || result.length() == 0) {
				// Toast.makeText(getApplicationContext(),
				// "No data found from Server!!!", Toast.LENGTH_LONG)
				// .show();
			} else {

				try {

					// Toast.makeText(BackgroundService.this,
					// "Check Id " +
					// baseDatabase.checkModuleId("DestinationDetailContent",
					// moduleStr), Toast.LENGTH_LONG).show();
					if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
							locationId) == 0) {

//						dbbHelper.insertAccomodationDetailsContent(
//								tableNameStr, locationId, moduleStr, result);

					} else {

						dbbHelper.updateAccomodationDetailsContent(
								tableNameStr, moduleStr, result, locationId);

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	class CulturDetailsTask extends AsyncTask<String, Void, String> {
		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				locationId = "";

		public CulturDetailsTask(String locId, String tableName,
				String methodName, String moduleId) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
		}

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(Base_url + methodNameStr + "/"
					+ moduleStr);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (null == result || result.length() == 0) {
				// Toast.makeText(getApplicationContext(),
				// "No data found from Server!!!", Toast.LENGTH_LONG)
				// .show();
			} else {

				try {

					// Toast.makeText(BackgroundService.this,"Check Id " +
					// baseDatabase.checkModuleId("DestinationDetailContent",
					// moduleStr), Toast.LENGTH_LONG).show();
					if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
							locationId) == 0) {

//						dbbHelper.insertCultureDetailsContent(tableNameStr,
//								locationId, moduleStr, result);

					} else {

						dbbHelper.updateCultureDetailsContent(tableNameStr,
								moduleStr, result, locationId);

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	class PackagesDetailsTask extends AsyncTask<String, Void, String> {
		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				locationId = "";

		public PackagesDetailsTask(String locId, String tableName,
				String methodName, String moduleId) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
		}

		@Override
		protected String doInBackground(String... params) {
			return ServiceCalls1.getJSONString(Base_url + methodNameStr + "/"
					+ moduleStr);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (null == result || result.length() == 0) {
				// Toast.makeText(getApplicationContext(),
				// "No data found from Server!!!", Toast.LENGTH_LONG)
				// .show();
			} else {

				try {

					// Toast.makeText(BackgroundService.this,"Check Id " +
					// baseDatabase.checkModuleId("DestinationDetailContent",
					// moduleStr), Toast.LENGTH_LONG).show();
					if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
							locationId) == 0) {

//						dbbHelper.insertCultureDetailsContent(tableNameStr,
//								locationId, moduleStr, result);

					} else {

						dbbHelper.updateCultureDetailsContent(tableNameStr,
								moduleStr, result, locationId);

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	private class SlideImagesJSONParse extends
			AsyncTask<String, String, String> {
		int locationId;

		public SlideImagesJSONParse(int locId) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... args) {
			return WebServiceCalls.getJSONString(Constants.Base_url
					+ "getSlideImages/" + locationId);
		}

		@Override
		protected void onPostExecute(String jsonResult) {
			// pDialog.dismiss();
			System.out.println(" result " + jsonResult);

			try {
				dbbHelper.insertSlideImageContent(String.valueOf(locationId),
						jsonResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
