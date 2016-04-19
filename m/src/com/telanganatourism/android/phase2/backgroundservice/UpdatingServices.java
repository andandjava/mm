package com.telanganatourism.android.phase2.backgroundservice;

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
import com.telanganatourism.android.phase2.database.helper.BaseDatabase;
import com.telanganatourism.android.phase2.database.helper.TtHelper;

public class UpdatingServices extends Service {

	int iterator, language_id;
	TtHelper dbbHelper;
	BaseDatabase baseDatabase;
	boolean dbFlag = false;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// Perform your long running operations here.
		setServicesOn();
	}

	private void setServicesOn() {
		// TODO Auto-generated method stub

		if (Constants.sublistFlag == 3) {

			// Accomdation Services
			for (language_id = 1; language_id <= 4; language_id++) {
				for (iterator = 1; iterator <= 10; iterator++) {

					new AccomodationTask("Accomodation_Content",
							String.valueOf(iterator),
							String.valueOf(language_id)).execute();

				}
			}

		} else if (Constants.sublistFlag == 5) {
			
			// Culture Services
			for (iterator = 1; iterator <= 4; iterator++) {

				new CulturesTask(String.valueOf(1)).execute();

			}
		} else {

			// Packages Services
			for (iterator = 1; iterator <= 4; iterator++) {

				new TourPackagesCategoryTask(String.valueOf(iterator))
						.execute();

			}
		}
	}

	class AccomodationTask extends AsyncTask<String, Void, String> {

		String tableNameStr = "", locationId = "", languageId = "";

		public AccomodationTask(String tableName, String locId, String langid) {
			// TODO Auto-generated constructor stub
			this.tableNameStr = tableName;
			this.locationId = locId;
			this.languageId = langid;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getAccomodationsList/" + locationId + "/" + languageId);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getAccomodationsList/" + locationId + "/" + languageId);
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

					dbbHelper = new TtHelper(UpdatingServices.this);
					dbbHelper.openDataBase();

					dbbHelper.updateAccomodationContent(languageId, result,
							locationId);

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj
							.getJSONArray("stayplace");

					// StringBuilder stringBuilder = new StringBuilder();
					// stringBuilder.setLength(0);

					for (int i = 0; i < jsonArray.length(); i++) {

						// stringBuilder.append(","
						// + jsonArray.getJSONObject(i).get("new_id")
						// .toString());

						new AccomodationsDetailsTask(locationId,
								"AccomodationDetailContent",
								"getAccomodationDetails", jsonArray
										.getJSONObject(i).get("id").toString()
										.trim(), languageId).execute();
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	class AccomodationsDetailsTask extends AsyncTask<String, Void, String> {
		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				locationId = "", languageId = "";

		public AccomodationsDetailsTask(String locId, String tableName,
				String methodName, String moduleId, String lang_id) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
			this.languageId = lang_id;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + languageId);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + languageId);
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

					dbbHelper.updateAccomodationDetailsContent(tableNameStr,
							moduleStr, result, languageId);

					Toast.makeText(getApplicationContext(), moduleStr,
							Toast.LENGTH_SHORT).show();

					// }
				} catch (Exception e1) {
					e1.printStackTrace();
					// Toast.makeText(getActivity(), "" + e1,
					// Toast.LENGTH_SHORT)
					// .show();
				}

			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	class TourPackagesCategoryTask extends AsyncTask<String, Void, String> {

		String languageId;

		public TourPackagesCategoryTask(String langid) {
			// TODO Auto-generated constructor stub
			this.languageId = langid;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getPackageCategoriesList");
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
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

					dbbHelper = new TtHelper(UpdatingServices.this);
					dbbHelper.openDataBase();

					dbbHelper.updateTourPackagesCategory(languageId, result);

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj
							.getJSONArray("PackageType");

					for (int i = 0; i < jsonArray.length(); i++) {

						new TourPakagesTask("TourPackagesContent",
								"getPackagesList", jsonArray.getJSONObject(i)
										.get("id").toString().trim(),
								languageId).execute();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	class TourPakagesTask extends AsyncTask<String, Void, String> {

		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				language_id = "";

		public TourPakagesTask(String tableName, String methodName,
				String moduleId, String lang_id) {
			// TODO Auto-generated constructor stub
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
			this.language_id = lang_id;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + language_id);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + language_id);
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

					dbbHelper.updateTourPackagesContent(moduleStr, result,
							language_id);

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj.getJSONArray("Package");

					for (int i = 0; i < jsonArray.length(); i++) {

						new PackagesDetailsTask("1",
								"TourPackagesDetailContent",
								"getPackageDetails", jsonArray.getJSONObject(i)
										.get("id").toString().trim(),
								language_id).execute();
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	class PackagesDetailsTask extends AsyncTask<String, Void, String> {
		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				locationId = "", language_id = "";

		public PackagesDetailsTask(String locId, String tableName,
				String methodName, String moduleId, String lang_id) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
			this.language_id = lang_id;
		}

		@Override
		protected String doInBackground(String... params) {
			
			System.out.println("url is"+SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + language_id);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + language_id);
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
					// if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
					// locationId) == 0) {

					// dbbHelper.insertPackageDetailsContent(
					// "TourPackagesDetailContent", "1", moduleStr,
					// result, language_id);
					dbbHelper.updatePackageDetailsContent(moduleStr, result,
							language_id);

					Toast.makeText(getApplicationContext(), moduleStr,
							Toast.LENGTH_SHORT).show();

					// } else {
					//
					// dbbHelper.updateCultureDetailsContent(tableNameStr,
					// moduleStr, result, locationId);
					//
					// }
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
	
	class CulturesTask extends AsyncTask<String, Void, String> {

		String languageId;

		public CulturesTask(String langid) {
			// TODO Auto-generated constructor stub
			this.languageId = langid;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getCultureList/" + languageId);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getCultureList/" + languageId);
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

//					dbbHelper.insertCultureContent("1", "Culture", result);

					// } else {
					//
					 dbbHelper.updateCultureContent(result, languageId);
					//
					// }

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj.getJSONArray("Culture");

					for (int i = 0; i < jsonArray.length(); i++) {

						new CulturDetailsTask("1", "CultureDeatilContent",
								"getCultureDetails", jsonArray.getJSONObject(i)
										.get("id").toString().trim(),
								languageId).execute();
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	class CulturDetailsTask extends AsyncTask<String, Void, String> {
		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				locationId = "", languageId = "";

		public CulturDetailsTask(String locId, String tableName,
				String methodName, String moduleId, String lang_id) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
			this.languageId = lang_id;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + languageId);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + languageId);
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
					// if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
					// locationId) == 0) {

//					dbbHelper.insertCultureDetailsContent(tableNameStr,
//							locationId, moduleStr, result, languageId);

					// Toast.makeText(getActivity(), languageId,
					// Toast.LENGTH_SHORT).show();

					// } else {
					//
					 dbbHelper.updateCultureDetailsContent(tableNameStr,
					 moduleStr, result, locationId);
					//
					// }
				} catch (Exception e1) {
					e1.printStackTrace();
					Toast.makeText(getApplicationContext(), "" + e1, Toast.LENGTH_SHORT)
							.show();
				}

			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
}
