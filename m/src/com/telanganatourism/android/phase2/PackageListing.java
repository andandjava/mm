package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.telanganatourism.android.phase2.objects.ItemsObj;
import com.telanganatourism.android.phase2.util.Utility;

public class PackageListing extends Activity {

	ServicePackageListAdapter adapter;
	public ArrayList<PackageListModel> CustomListViewValuesArr = new ArrayList<PackageListModel>();
	public static String pack_title, pack_type, pack_no_of_adults,
			pack_no_of_child, pack_total_no_of_persons, pack_selected_date,
			boarding_pont = "", service_Code, city_Code, from_city, to_city, packagesAvailability, packagePeriod;

	ListView listView;
	
	TextView titleTxt;

	// ArrayList<String> fromCityCodeArray = new ArrayList<String>();
	// ArrayList<String> fromCityNameArray = new ArrayList<String>();
	// ArrayList<String> toCityCodeArray = new ArrayList<String>();
	// ArrayList<String> toCityNameArray = new ArrayList<String>();
	// ArrayList<String> serviceScheduleArray = new ArrayList<String>();
	// ArrayList<String> serviceCodeArray = new ArrayList<String>();
	// ArrayList<String> serviceNameArray = new ArrayList<String>();
	// ArrayList<String> packageNameArray = new ArrayList<String>();
	// ArrayList<String> vehicleTypeArray = new ArrayList<String>();
	// ArrayList<String> daysArray = new ArrayList<String>();
	// ArrayList<String> arrivalTimeArray = new ArrayList<String>();
	// ArrayList<String> derartureTimeArray = new ArrayList<String>();
	// ArrayList<String> returnDepTimeArray = new ArrayList<String>();
	// ArrayList<String> returnArrTimeArray = new ArrayList<String>();
	
//	Utility utility;
	
	public static int adultVal = 0;
	public static int childVal = 0;
	public static int personsVal = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.packages_listing);
		
		Constants.ProximaNova_Bold = Typeface.createFromAsset(
				getApplicationContext().getAssets(),
				"ProximaNova-Bold.otf");

		Constants.pack_list_item_array = new ArrayList<ItemsObj>();

		listView = (ListView) findViewById(R.id.packagesListview);
		
		titleTxt = (TextView)findViewById(R.id.event_title);
		
		titleTxt.setTypeface(Constants.ProximaNova_Regular);
		
		titleTxt.setText(DetailScreen1.tit);
		
//		utility = new Utility(PackageListing.this);

		if(Utility.checkInternetConnection(PackageListing.this)){
			new GetPackagesServicesList().execute();
		}else{
			Utility.showAlertNoInternetService(PackageListing.this);
		}
		
//		titleTxt.setText(text)
		

		// try {
		// JSONArray json = new JSONArray(loadJSONFromAsset());
		//
		// for (int i = 0; i < json.length(); i++) {
		// HashMap<String, String> map = new HashMap<String, String>();
		// JSONObject e = json.getJSONObject(i);
		//
		// map.put("serviceName",
		// "Service Name  : " + e.getString("serviceName"));
		// map.put("vehicleType",
		// "Vehicle Type:" + e.getString("vehicleType"));
		// map.put("fromCityCode",
		// "fromCityCode: " + e.getString("fromCityCode"));
		//
		// // stringBuilder.append(","+e.getString("unitCode"));
		// // map.put("unitName", "Unit Name: " +
		// // e.getString("unitName"));
		//
		// fromCityCodeArray.add(e.getString("fromCityCode")
		// .toString());
		// fromCityNameArray.add(e.getString("fromCityName")
		// .toString());
		// toCityCodeArray.add(e.getString("toCityCode")
		// .toString());
		// toCityNameArray.add(e.getString("toCityName")
		// .toString());
		// serviceScheduleArray.add(e.getString("serviceSchedule")
		// .toString());
		// serviceCodeArray.add(e.getString("serviceCode")
		// .toString());
		// serviceNameArray.add(e.getString("serviceName")
		// .toString());
		// packageNameArray.add(e.getString("packageName")
		// .toString());
		// vehicleTypeArray.add(e.getString("vehicleType")
		// .toString());
		// daysArray.add(e.getString("days").toString());
		// arrivalTimeArray.add(e.getString("arrivalTime")
		// .toString());
		// derartureTimeArray.add(e.getString("derartureTime")
		// .toString());
		// returnDepTimeArray.add(e.getString("returnDepTime")
		// .toString());
		// returnArrTimeArray.add(e.getString("returnArrTime")
		// .toString());
		//
		// // mylist.add(map);
		// }
		//
		// // Toast.makeText(MainActivity.this,
		// // ""+stringBuilder.toString().replaceFirst(",", ""),
		// // Toast.LENGTH_LONG).show();
		//
		// } catch (Exception e) {
		// // TODO: handle exception
		// Log.e("Tag", "" + e);
		// }

		// Resources res =getResources();
		//
		//
		// for (int i = 0; i < fromCityCodeArray.size(); i++) {
		//
		// PackageListModel sched = new PackageListModel();
		// sched.setPackageName(serviceNameArray.get(i));
		// sched.setPackageType(vehicleTypeArray.get(i));
		// sched.setPackageAvailability(daysArray.get(i));
		//
		// CustomListViewValuesArr.add(sched);
		// }
		//
		//
		// PackageListModel sched1 = new PackageListModel();
		// sched1.setPackageName("73 HYD Tirupathi Benz/Volvo");
		// sched1.setPackageType("A/C 45");
		// sched1.setPackageAvailability("FRI  SAT");
		//
		// PackageListModel sched2 = new PackageListModel();
		// sched2.setPackageName("74 HYD Tirupathi Volvo II");
		// sched2.setPackageType("A/C 45");
		// sched2.setPackageAvailability("FRI  SAT  SUN");
		//
		// CustomListViewValuesArr.add(sched);
		// CustomListViewValuesArr.add(sched1);
		// CustomListViewValuesArr.add(sched2);
		//
		// adapter = new CustomAdapter(PackageListing.this,
		// CustomListViewValuesArr, res);
		// listView.setAdapter(adapter);

		// fromCityCodeArray.clear();
		// fromCityNameArray.clear();
		// toCityCodeArray.clear();
		// toCityNameArray.clear();
		// serviceScheduleArray.clear();
		// serviceCodeArray.clear();
		// serviceNameArray.clear();
		// packageNameArray.clear();
		// vehicleTypeArray.clear();
		// daysArray.clear();

		RelativeLayout menu_layout = (RelativeLayout) findViewById(R.id.menuLayout);

		menu_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				startActivity(new Intent(PackageListing.this,
//						DetailScreen1.class));
				finish();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// PackageListModel listModel = (PackageListModel)
				// CustomListViewValuesArr
				// .get(arg2);
				//
				// pack_title = listModel.getPackageName();
				// pack_type = listModel.getPackageType();
				
				adultVal = 0;
				childVal = 0;
				personsVal = 0;

				ItemsObj itemsObj = Constants.pack_list_item_array.get(arg2);
				pack_title = itemsObj.getServiceName();
				pack_type = itemsObj.getVehicleType();
				service_Code = itemsObj.getServiceCode();
				city_Code = itemsObj.getToCityCode();
				from_city = itemsObj.getFromCityName();
				to_city = itemsObj.getToCityName();
				packagesAvailability = itemsObj.getDays(); 
				packagePeriod = itemsObj.getPackagePeriod();
				
				if(Utility.checkInternetConnection(PackageListing.this)){
					new ServiceValidation().execute();
				}else{
					Utility.showAlertNoInternetService(PackageListing.this);
				}

				

			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			startActivity(new Intent(PackageListing.this, DetailScreen1.class));
			finish();

		}
		return true;
	}

	class GetPackagesServicesList extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PackageListing.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// return "result";
         System.out.println("url is"+Constants.Base_url1+"services.jsp?tcc="
							+ Constants.new_pack_id);
			return ServiceCalls1
					.getJSONString(Constants.Base_url1+"services.jsp?tcc="
							+ Constants.new_pack_id);

		}

		@Override
		protected void onProgressUpdate(Integer... a) {
			super.onProgressUpdate(a);

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			Log.e("Result : ", result);
			
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {

			} else {

				try {
					JSONArray json = new JSONArray(result);

					for (int i = 0; i < json.length(); i++) {
						// HashMap<String, String> map = new HashMap<String,
						// String>();
						JSONObject e = json.getJSONObject(i);

						// map.put("serviceName",
						// "Service Name  : " + e.getString("serviceName"));
						// map.put("vehicleType",
						// "Vehicle Type:" + e.getString("vehicleType"));
						// map.put("fromCityCode",
						// "fromCityCode: " + e.getString("fromCityCode"));

						// stringBuilder.append(","+e.getString("unitCode"));
						// map.put("unitName", "Unit Name: " +
						// e.getString("unitName"));

						ItemsObj objItem = new ItemsObj();
						objItem.setServiceName(e.getString("serviceName")
								.toString());
						objItem.setVehicleType(e.getString("vehicleType")
								.toString());
						objItem.setDays(e.getString("days").toString());
						objItem.setPackagePeriod(e.getString("packagePeriod").toString());
						objItem.setServiceCode(e.getString("serviceCode")
								.toString());
						objItem.setToCityCode(e.getString("toCityCode")
								.toString());
						objItem.setFromCityName(e.getString("fromCityName")
								.toString());
						objItem.setToCityName(e.getString("toCityName")
								.toString());

						Constants.pack_list_item_array.add(objItem);

						// fromCityCodeArray.add(e.getString("fromCityCode")
						// .toString());
						// fromCityNameArray.add(e.getString("fromCityName")
						// .toString());
						// toCityCodeArray.add(e.getString("toCityCode")
						// .toString());
						// toCityNameArray.add(e.getString("toCityName")
						// .toString());
						// serviceScheduleArray.add(e.getString("serviceSchedule")
						// .toString());
						// serviceCodeArray.add(e.getString("serviceCode")
						// .toString());
						// serviceNameArray.add(e.getString("serviceName")
						// .toString());
						// packageNameArray.add(e.getString("packageName")
						// .toString());
						// vehicleTypeArray.add(e.getString("vehicleType")
						// .toString());
						// daysArray.add(e.getString("days").toString());
						// arrivalTimeArray.add(e.getString("arrivalTime")
						// .toString());
						// derartureTimeArray.add(e.getString("derartureTime")
						// .toString());
						// returnDepTimeArray.add(e.getString("returnDepTime")
						// .toString());
						// returnArrTimeArray.add(e.getString("returnArrTime")
						// .toString());

						// mylist.add(map);
					}

					adapter = new ServicePackageListAdapter(
							PackageListing.this, R.layout.packages_list_row,
							Constants.pack_list_item_array);

					adapter.notifyDataSetChanged();
					listView.setAdapter(adapter);

					// Toast.makeText(MainActivity.this,
					// ""+stringBuilder.toString().replaceFirst(",", ""),
					// Toast.LENGTH_LONG).show();

				} catch (Exception e) {
					// TODO: handle exception
					Log.e("Tag", "" + e);
				}
				
				if(pDialog.isShowing()){
					pDialog.dismiss();
				}

			}
		}
	}

	class ServiceValidation extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {

			 System.out.println("url is"+Constants.Base_url1+"servicevalidation.jsp?dt="+Constants.currentDate+"&sc="
						+ service_Code);

			return ServiceCalls1
					.getJSONString(Constants.Base_url1+"servicevalidation.jsp?dt="+Constants.currentDate+"&sc="
							+ service_Code);

			// return "result";

		}

		@Override
		protected void onProgressUpdate(Integer... a) {
			super.onProgressUpdate(a);
			// System.out.println("time" + a);

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			Log.e("Result : ", result);
			if (null == result || result.length() == 0) {

			} else {

				try {
					JSONArray json = new JSONArray(result);

					for (int i = 0; i < json.length(); i++) {
						JSONObject e = json.getJSONObject(i);

						if (e.get("response").toString().equalsIgnoreCase("1")) {

//							Toast.makeText(getApplicationContext(),
//									e.getString("response").toString(), 200)
//									.show();
							startActivity(new Intent(PackageListing.this,
									Package_Booking.class));
//							finish();
						} else {

						}

					}

				} catch (Exception e) {
					// TODO: handle exception
					Log.e("Tag", "" + e);
				}

			}
		}
	}

	public class ServicePackageListAdapter extends ArrayAdapter<ItemsObj> {

		private Activity activity;
		private List<ItemsObj> items;
		private ItemsObj objBean;
		private int row;
		ViewHolder holder;
		String strQty = "0";

		Utility utils = new Utility(getContext());

		public ServicePackageListAdapter(Activity act, int resource,
				List<ItemsObj> arrayList) {
			super(act, resource, arrayList);
			this.activity = act;
			this.row = resource;
			this.items = arrayList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size();
		}

		@Override
		public ItemsObj getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public class ViewHolder {

			public TextView package_name;
			public TextView package_type;
			public TextView package_availability;

		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			LayoutInflater inflator = activity.getLayoutInflater();
			objBean = items.get(position);
			if (convertView == null) {
				holder = new ViewHolder();

				convertView = inflator
						.inflate(R.layout.packages_list_row, null);

				holder = new ViewHolder();
				holder.package_name = (TextView) convertView
						.findViewById(R.id.package_title_txt);
				holder.package_name.setTypeface(Constants.ProximaNova_Bold);
				holder.package_type = (TextView) convertView
						.findViewById(R.id.package_type_txt);
				holder.package_type.setTypeface(Constants.ProximaNova_Bold);
				holder.package_availability = (TextView) convertView
						.findViewById(R.id.available_txt);
				holder.package_availability.setTypeface(Constants.ProximaNova_Bold);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.package_name.setText(objBean.getServiceName());
			holder.package_type.setText(objBean.getVehicleType());
			holder.package_availability.setText(objBean.getDays());

			return convertView;
		}

	}

	/*********
	 * Adapter class extends with BaseAdapter and implements with
	 * OnClickListener
	 ************/
	public class CustomAdapter extends BaseAdapter {

		/*********** Declare Used Variables *********/
		private Activity activity;
		private ArrayList data;
		public LayoutInflater inflater = null;
		public Resources res;
		PackageListModel tempValues = null;
		int i = 0;

		/************* CustomAdapter Constructor *****************/
		public CustomAdapter(Activity a, ArrayList d, Resources resLocal) {

			/********** Take passed values **********/
			activity = a;
			data = d;
			res = resLocal;

			/*********** Layout inflator to call external xml layout () ***********/
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		/******** What is the size of Passed Arraylist Size ************/
		public int getCount() {

			if (data.size() <= 0)
				return 1;
			return data.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		/******
		 * Depends upon data size called for each row , Create each ListView row
		 *****/
		public View getView(int position, View convertView, ViewGroup parent) {

			View vi = convertView;
			ViewHolder holder;

			if (convertView == null) {

				/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
				vi = inflater.inflate(R.layout.packages_list_row, null);

				/****** View Holder Object to contain tabitem.xml file elements ******/

				holder = new ViewHolder();
				holder.package_name = (TextView) vi
						.findViewById(R.id.package_title_txt);
				holder.package_type = (TextView) vi
						.findViewById(R.id.package_type_txt);
				holder.package_availability = (TextView) vi
						.findViewById(R.id.available_txt);

				/************ Set holder with LayoutInflater ************/
				vi.setTag(holder);
			} else
				holder = (ViewHolder) vi.getTag();

			if (data.size() <= 0) {
				// holder.text.setText("No Data");

			} else {
				/***** Get each Model object from Arraylist ********/
				tempValues = null;
				tempValues = (PackageListModel) data.get(position);

				/************ Set Model values in Holder elements ***********/

				holder.package_name.setText(tempValues.getPackageName());
				holder.package_type.setText(tempValues.getPackageType());
				holder.package_availability.setText(tempValues
						.getPackageAvailability());

				/******** Set Item Click Listner for LayoutInflater for each row *******/

			}
			return vi;
		}

		// @Override
		// public void onClick(View v) {
		// Log.v("CustomAdapter", "=====Row button clicked=====");
		// }
		//
		// /********* Called when Item click in ListView ************/
		// private class OnItemClickListener implements OnClickListener {
		// private int mPosition;
		//
		// OnItemClickListener(int position) {
		// mPosition = position;
		// }
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// CustomListViewAndroidExample sct = (CustomListViewAndroidExample)
		// activity;
		//
		// /****
		// * Call onItemClick Method inside CustomListViewAndroidExample
		// * Class ( See Below )
		// ****/
		//
		// sct.onItemClick(mPosition);
		// }
		// }
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView package_name;
		public TextView package_type;
		public TextView package_availability;

	}

	public String loadJSONFromAsset() {
		String json = null;
		try {
			InputStream is = getApplicationContext().getAssets().open(
					"packagelist.json");
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
					"serviceValidation.json");
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
}
