package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.flurry.android.FlurryAgent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.telanganatourism.android.phase2.database.helper.TtHelper;
import com.telanganatourism.android.phase2.objects.ItemsObj;
import com.telanganatourism.android.phase2.util.Utility;

public class PackagesFragment extends Fragment {
	ArrayList<Integer> list;
//	EventBean event;
	ArrayList<EventsVariables> events_upcoming;
	ArrayList<Integer> id_list;
	AQuery androidAQuery;
	PackagesListAdapter adapter;
	ListView lv;

	TtHelper dbbHelper;
	Cursor constantCursor = null;

	String responseString;
	
	Utility utility;

	// static String[] whrdo = { "Hyderabad", "Khammam", "Mahabubnaga",
	// "Nalgonda", "Warangal", "Adilabad", "Nizamabad", "Karimnagar", "Medak",
	// "Rangareddy" };

	/*
	 * static String[] titles = { "Charminar", "Birla Mandir",
	 * "Falaknuma Palace","Golconda fortg" }; static String[] loc = {
	 * "Hyderabad, Telangana", "Hyderabad, Telangana",
	 * "Hyderabad, Telangana","Hyderabad, Telangana" }; static Integer[] img =
	 * {R
	 * .drawable.charminar,R.drawable.birlamandir,R.drawable.falaknuma,R.drawable
	 * .golconda_fort };
	 */
	// static String[] titles[];
	// static String[] loc[] ;
	// static Integer[] img[] ;

	public static ArrayList<String> titles = new ArrayList<String>();
	public static ArrayList<String> loc = new ArrayList<String>();
	public static ArrayList<Integer> img = new ArrayList<Integer>();
	public static ArrayList<String> dec = new ArrayList<String>();

	public PackagesFragment() {

	}

	int upcomingeventsLength = 0;

	JSONArray jsonArray;

	int iterator;
	
//	int fontChaning = 0;

	private final Handler handler = new Handler();
	private Drawable oldBackground = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_whats_hot,
				container, false);
		
		utility = new Utility(getActivity());
		
		try {
			dbbHelper = new TtHelper(getActivity());
			dbbHelper.openDataBase();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		


		Constants.dest_items_array1 = new ArrayList<ItemsObj>();

		// changeColor(Color.parseColor("#ffffff"));

		MainActivity.txt_logo.setVisibility(View.GONE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setTextColor(Color.parseColor("#ffffff"));

		MainActivity.txt_title.setText(MainActivity.changeTitle);
		
		MainActivity.txt_title.setTypeface(Constants.ProximaNova_Regular);

		MainActivity.txt_filter.setVisibility(View.GONE);

		MainActivity.txt_location.setVisibility(View.GONE);

		MainActivity.txt_search.setVisibility(View.GONE);

		MainActivity.txt_Edit.setVisibility(View.GONE);

		MainActivity.txt_filter
				.setBackgroundResource(R.drawable.filter_icon_white);

		MainActivity.txt_location
				.setBackgroundResource(R.drawable.location_icon_white);

		MainActivity.txt_search
				.setBackgroundResource(R.drawable.search_icon_white);

		MainActivity.btn_menu.setBackgroundResource(R.drawable.menu);

		MainActivity.headerLayout.setBackgroundColor(Color
				.parseColor(MainActivity.changeColorStr));

		MainActivity.searchLayout.setBackgroundColor(Color
				.parseColor(MainActivity.changeColorStr));

		MainActivity.searchLayout.setVisibility(View.GONE);
		
//		TextView tvSmallFont = (TextView) rootView.findViewById(R.id.smallFontTxt);
//		TextView tvBigFont = (TextView) rootView.findViewById(R.id.bigFontTxt);
//		
//		tvSmallFont.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (fontChaning == 0) {
//					adapter.notifyDataSetChanged();
//					MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
					
					if(SplashScreen.fontpref
							.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")){
						MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
					}else if(SplashScreen.fontpref
							.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")){
						MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
					}else if(SplashScreen.fontpref
							.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")){
						MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
					}
//				} else {
//					fontChaning--;
//					if (fontChaning > 0) {
//
//						if (fontChaning == 1) {
//							adapter.notifyDataSetChanged();
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//						} else if (fontChaning == 2) {
//							adapter.notifyDataSetChanged();
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//						} else if (fontChaning == 3) {
//							adapter.notifyDataSetChanged();
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//						}
////						Toast.makeText(getApplicationContext(),
////								"" + fontChaning, Toast.LENGTH_LONG).show();
//					} else {
////						Toast.makeText(getApplicationContext(),
////								"Else : " + fontChaning, Toast.LENGTH_LONG)
////								.show();
//					}
//				}
//
//			}
//		});
//
//		tvBigFont.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				if (fontChaning == 3) {
//
//				} else {
//					fontChaning++;
//
//					if (fontChaning <= 3) {
//
//						if (fontChaning == 1) {
//							adapter.notifyDataSetChanged();
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//						} else if (fontChaning == 2) {
//							adapter.notifyDataSetChanged();
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//						} else if (fontChaning == 3) {
//							adapter.notifyDataSetChanged();
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//						}
//
////						Toast.makeText(getApplicationContext(),
////								"" + fontChaning, Toast.LENGTH_LONG).show();
//					} else {
////						Toast.makeText(getApplicationContext(),
////								"Else : " + fontChaning, Toast.LENGTH_LONG)
////								.show();
//					}
//				}
//
//			}
//		});

		MainActivity.txt_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.searchLayout.setVisibility(View.VISIBLE);
				MainActivity.txt_filter.setVisibility(View.GONE);
				MainActivity.txt_location.setVisibility(View.GONE);
				MainActivity.btn_menu.setVisibility(View.VISIBLE);
			}
		});

		MainActivity.txt_back_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.searchLayout.setVisibility(View.GONE);
				MainActivity.txt_location.setVisibility(View.VISIBLE);
				MainActivity.btn_menu.setVisibility(View.VISIBLE);
			}
		});

		titles.clear();
		loc.clear();
		img.clear();
		dec.clear();

		// MainActivity.getRightMenuList(getActivity(), whrdo);

		list = new ArrayList<Integer>();
		id_list = new ArrayList<Integer>();
//		event = (EventBean) getActivity().getApplicationContext();
		androidAQuery = new AQuery(getActivity());
		events_upcoming = new ArrayList<EventsVariables>();
		lv = (ListView) rootView.findViewById(R.id.listView1);
		lv.setEmptyView(rootView.findViewById(android.R.id.empty));
		list.clear();
		id_list.clear();

		// // new UpComingEvents().execute();
		// // calendar_dates.clear();
		//
		// titles.add("DAILY TOURS");
		// titles.add("WEEKEND TOURS");
		// titles.add("INTER STATE TOURS");
		// titles.add("ON DEMAND TOURS");
		//
		// // loc.add("Hyderabad, Telangana");
		// // loc.add("Hyderabad, Telangana");
		// // loc.add("Hyderabad, Telangana");
		// // loc.add("Hyderabad, Telangana");
		// //
		// img.add(R.drawable.daily_tours);
		// img.add(R.drawable.weekly_tours);
		// img.add(R.drawable.inter_state_tours);
		// img.add(R.drawable.on_demand_tours);
		// //
		// //
		// dec.add("The Charminar, built in 1591 CE, is a monument and mosque located in Hyderabad, Telangana, India. The landmark has become a global icon of Hyderabad, listed among the most recognized structures of India. The Charminar is situated on the east bank of Musi river. To the west lies the Laad Bazaar, and to the southwest lies the richly ornamented granite Makkah Masjid. The English name is a translation and combination of the Urdu words Char and Minar, translating to 'Four Towers the eponymous towers are ornate minarets attached and supported by four grand arches. Some of the popular myths that are recorded in accord with the monument's architectural appearance are as follows.");
		// //
		// dec.add("Birla Mandir is a Hindu temple, built on a 280 feet (85 m) high hillock called Naubath Pahad on a 13 acres (53,000 m2) plot. The construction took 10 years and was constructed in 1976 by Swami Ranganathananda of Ramakrishna Mission. The temple was constructed by Birla Foundation, which has also constructed several similar temples across India");
		// //
		// dec.add("Falaknuma Palace is one of the finest palaces in Hyderabad, Telangana, India. It belonged to Paigah Hyderabad State, and it was later owned by the Nizams.[1] It is on a 32-acre (13 ha) area in Falaknuma, 5 km from Charminar. It was built by Nawab Vikar-ul-Umra, prime minister of Hyderabad and the uncle and brother-in-law of the Nizam VI, Nawab Mir Mahboob Ali Khan Bahadur.[2] Falak-numa means 'Like the Sky' or 'Mirror of the Sky' in Urdu");
		// //
		// dec.add("Golconda, also known as Golkonda or Golla konda ('shepherd's hill'), a ruined fort of Southern India and capital of medieval Golconda Sultanate (c.1518�1687), is situated 11 km west of Hyderabad. It is also a mandal of Hyderabad District. The region is known for the mines that have produced some of the world's most famous gems, including the Hope Diamond and the Nassak ");
		//
		// for (int j = 0; j < titles.size(); j++) {
		//
		// EventsVariables eventsBean = new EventsVariables();
		//
		// eventsBean.title = titles.get(j);
		// // eventsBean.venue = loc.get(j);
		//
		// // eventsBean.title = "Charminar";
		//
		// // eventsBean.startDate = jsonObject1
		// // .getString("StartDate");
		// // eventsBean.endDate = jsonObject1
		// // .getString("EndDate");
		// // eventsBean.venue ="Hyderabad, Telangana";
		// // eventsBean.category = jsonObject1
		// // .getString("Category");
		// // eventsBean.thumbnail = jsonObject1
		// // .getString("Thumbnail");
		// events_upcoming.add(eventsBean);
		// event.setObject(events_upcoming);
		//
		// }
		//
		// adapter = new CustomAdapter(getActivity(), events_upcoming);
		// adapter.notifyDataSetChanged();
		// lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				// EventsVariables eve1 = events_upcoming.get(arg2);
				// Constants.imgs = img.get(arg2);
				// Constants.event_title = eve1.title;
				// Constants.description = dec.get(arg2);
				// startActivity(new Intent(getActivity(),
				// DetailScreen1.class));

				// if(arg2 == 0){
				// final FragmentTransaction ft = getFragmentManager()
				// .beginTransaction();
				// ft.replace(R.id.frame_container, new TourPackagesFragment(),
				// "NewFragmentTag");
				// ft.commit();
				// }else{
				// final FragmentTransaction ft = getFragmentManager()
				// .beginTransaction();
				// ft.replace(R.id.frame_container, new WhereToShopFragment(),
				// "NewFragmentTag");
				// ft.commit();
				// }
				Constants.sublistFlag = 2;

				ItemsObj itemsObj = (ItemsObj) Constants.dest_items_array1
						.get(arg2);
				Constants.destination_cat_id = itemsObj.getDest_cat_id();
				MainActivity.changeTitle = itemsObj.getDest_cat_title();
				startActivity(new Intent(getActivity(),
						SubFragmentActivity.class));

			}
		});

		try {


			String select = " SELECT * FROM TourPackagesCategoryList WHERE language_id="
					+ Constants.selectLanguage;
			constantCursor = dbbHelper.getReadableDatabase().rawQuery(select,
					null);

			getActivity().startManagingCursor(constantCursor);

			if (constantCursor.getCount() > 0) {
				if (constantCursor.moveToFirst()) {
					// do {

					responseString = constantCursor.getString(constantCursor
							.getColumnIndex("ResponseContent"));

					jsonParsing(responseString);

					// } while (constantCursor.moveToNext());

				}

			}

			// dbbHelper.close();

			// dbbHelper.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		

		// Checking the Packages Updating flag whether to update
		if(Constants.packages_flag == true){
			
			Constants.packages_flag = false;
			
			// Updating the Services in the background
			getActivity()
			.startService(
					new Intent(
							getActivity(),
							com.telanganatourism.android.phase2.backgroundservice.UpdatingServices.class));
			
		}

		return rootView;
	}

	class TourPackagesCategoryTask extends AsyncTask<String, Void, String> {

		String languageId;

		public TourPackagesCategoryTask(String langid) {
			// TODO Auto-generated constructor stub
			this.languageId = langid;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is_PackagesFragment"+SplashScreen.Base_url
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

					// dbbHelper = new TtHelper(BackgroundService.this);
					// dbbHelper.openDataBase();
					//
					// baseDatabase = new BaseDatabase(BackgroundService.this);
					// baseDatabase.openDataBase();

					// if (baseDatabase.display("TourPackagesCategoryList") ==
					// 0) {

					dbbHelper
							.insertTouPackagesCategory("1", result, languageId);

					// } else {
					// dbbHelper.updateTourPackagesCategory("1", result);
					//
					// }

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
			System.out.println("url is_PackagesFragment"+SplashScreen.Base_url
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

					// if (baseDatabase
					// .checkModuleId(tableNameStr, moduleStr, "1") == 0) {

					dbbHelper.insertTouPackages("1", moduleStr, result,
							language_id);
					// } else {
					//
					// dbbHelper.updateTourPackagesContent(moduleStr, result,
					// language_id);
					//
					// }

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj.getJSONArray("Package");

					for (int i = 0; i < jsonArray.length(); i++) {

						// destinationsDetailsArray.add(object)
						new PackagesDetailsTask("1",
								"TourPackagesDetailContent",
								"getPackageDetails", jsonArray.getJSONObject(i)
										.get("id").toString().trim(),
								language_id).execute();
					}

					// dbbHelper.close();
					// baseDatabase.close();

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
			System.out.println("url is_PackagesFragment"+SplashScreen.Base_url
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

					dbbHelper.insertPackageDetailsContent(
							"TourPackagesDetailContent", "1", moduleStr,
							result, language_id);
					// dbbHelper.updatePackageDetailsContent(moduleStr, result,
					// language_id);

					Toast.makeText(getActivity(), moduleStr, Toast.LENGTH_SHORT)
							.show();

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

	private void jsonParsing(String result) {
		// TODO Auto-generated method stub
		try {

			Constants.dest_items_array1.clear();
			JSONObject jsonResultObj = new JSONObject(result);

			JSONArray jsonArray = jsonResultObj.getJSONArray("PackageType");

			for (int i = 0; i < jsonArray.length(); i++) {

				ItemsObj objItem = new ItemsObj();
				objItem.setDest_cat_id(jsonArray.getJSONObject(i).get("id")
						.toString().trim());
				objItem.setDest_cat_title(jsonArray.getJSONObject(i)
						.get("title").toString().trim());
				objItem.setDest_cat_imgPath(jsonArray.getJSONObject(i)
						.get("image").toString().trim());

				Constants.dest_items_array1.add(objItem);
			}

			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				adapter = new PackagesListAdapter(getActivity(),
						R.layout.arbic_item_layout, Constants.dest_items_array1);
			} else {
				adapter = new PackagesListAdapter(getActivity(),
						R.layout.item_layout, Constants.dest_items_array1);
			}

			adapter.notifyDataSetChanged();
			lv.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void changeColor(int newColor) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(
					R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {
					colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getActivity().getActionBar().setBackgroundDrawable(ld);
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] {
						oldBackground, ld });

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {

					getActivity().getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}

			oldBackground = ld;

			getActivity().getActionBar().setDisplayShowTitleEnabled(false);
			getActivity().getActionBar().setDisplayShowTitleEnabled(true);

		}

	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@SuppressLint("NewApi")
		@Override
		public void invalidateDrawable(Drawable who) {
			getActivity().getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	public void refresh() {
		adapter = new PackagesListAdapter(getActivity(), R.layout.item_layout,
				Constants.dest_items_array1);
		adapter.notifyDataSetChanged();
		lv.setAdapter(adapter);

	}

	public class PackagesListAdapter extends ArrayAdapter<ItemsObj> {

		private Activity activity;
		private List<ItemsObj> items;
		private ItemsObj objBean;
		@SuppressWarnings("unused")
		private int row;
		Bitmap bimg;
		ViewHolder holder;
		String strQty = "0";
		AQuery androidAQuery = new AQuery(getContext());

		Utility utils = new Utility(getContext());

		public PackagesListAdapter(Activity act, int resource,
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
			TextView category, distancee, locationTxt, event_desc;
			ImageView iv;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			LayoutInflater inflator = activity.getLayoutInflater();
			objBean = items.get(position);
			if (convertView == null) {
				holder = new ViewHolder();

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					convertView = inflator.inflate(R.layout.arbic_item_layout,
							null);
				} else {
					convertView = inflator.inflate(R.layout.item_layout, null);
				}

				holder.category = (TextView) convertView
						.findViewById(R.id.category);
				holder.distancee = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.locationTxt = (TextView) convertView
						.findViewById(R.id.locationTxt);
				// holder.event_time = (TextView)
				// convertView.findViewById(R.id.event_time);
				holder.event_desc = (TextView) convertView
						.findViewById(R.id.event_desc);
				holder.iv = (ImageView) convertView
						.findViewById(R.id.item_icon);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// DecimalFormat df = new DecimalFormat("#.#");
			//
			// holder.distancee.setText(df.format(utils.distance(
			// Double.parseDouble("17.3998"),
			// Double.parseDouble("78.4766"),
			// Double.parseDouble(objBean.getDest_latt()),
			// Double.parseDouble(objBean.getDest_lng()), 'K'))
			// + " KMS");
			holder.distancee.setVisibility(View.GONE);

			holder.locationTxt.setVisibility(View.GONE);
			holder.category.setText(objBean.getDest_cat_title());
			
			holder.category.setTypeface(Constants.ProximaNova_Regular);
			holder.locationTxt.setTypeface(Constants.ProximaNova_Regular);
			// holder.locationTxt.setText(objBean.getDest_addr());
			
//			if (fontChaning == 1) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
//			} else if (fontChaning == 2) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
//			} else if (fontChaning == 3) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
//			} else if (fontChaning == 0) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
//			}
			
			if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
			}else if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			}else if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
			}

			// holder.txtName.setText(objBean.getStr_title());
			// holder.txtDetails.setText("Product: "+objBean.getStr_titl1().replace("...",
			// "")+" ...");
			//
			if (objBean.getDest_cat_imgPath().length() > 0) {

//				if (objBean.getDest_cat_imgPath().contains("http")) {
					
					
//					if (utility.IsNetConnected(getActivity())) {
//						Picasso.with(getActivity())
//						.load(objBean.getDest_cat_imgPath()
//								.replace("\'", "%20").trim()).noFade()
//						.error(R.drawable.default_img).into(holder.iv);
						
						ImageLoader imageLoader = ImageLoader.getInstance();
						DisplayImageOptions options = new DisplayImageOptions.Builder()
								.cacheInMemory(true).cacheOnDisc(true)
								.resetViewBeforeLoading(true)
								.showImageForEmptyUri(R.drawable.default_img)
								.showImageOnFail(R.drawable.default_img)
								.showImageOnLoading(R.drawable.default_img).build();

						// download and display image from url
						imageLoader.displayImage(
								objBean.getDest_cat_imgPath()
								.replace("\'", "%20").trim(), holder.iv, options);
//					
//					} else {
//						try { 
//							String url= objBean.getDest_cat_imgPath().replace("\'", "");
//							
//							String replacdUrl = url.replace("http://ttourdev.etisbew.net/images/", "");
//							// get input stream
//							InputStream ims = getActivity().getAssets().open(
//									replacdUrl.trim());
//							// load image as Drawable
//							Drawable d = Drawable.createFromStream(ims, null);
//							// set image to ImageView
//							holder.iv.setImageDrawable(d);
//						} catch (IOException ex) {
//
//						}
//					}
//				} else {
//					try {
//						// get input stream
//						InputStream ims = getActivity().getAssets().open(
//								objBean.getDest_cat_imgPath());
//						// load image as Drawable
//						Drawable d = Drawable.createFromStream(ims, null);
//						// set image to ImageView
//						holder.iv.setImageDrawable(d);
//					} catch (IOException ex) {
//
//					}
//				}
			}

			return convertView;
		}

	}

	public class CustomAdapter extends BaseAdapter {

		ArrayList<EventsVariables> list;

		LayoutInflater mInflater;

		public CustomAdapter(Context context,
				ArrayList<EventsVariables> events_upcoming) {
			mInflater = LayoutInflater.from(context);

			this.list = events_upcoming;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			// int pos = list.get(position);
			// System.out.println("list size" + list.size() + list + pos);
			UserHolder1 holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_layout1, parent,
						false);
				holder = new UserHolder1();

				holder.category = (TextView) convertView
						.findViewById(R.id.category);
				holder.event_title = (TextView) convertView
						.findViewById(R.id.event_title);
				holder.locationTxt = (TextView) convertView
						.findViewById(R.id.locationTxt);
				// holder.event_time = (TextView)
				// convertView.findViewById(R.id.event_time);
				holder.event_desc = (TextView) convertView
						.findViewById(R.id.event_desc);
				holder.iv = (ImageView) convertView
						.findViewById(R.id.item_icon);

				convertView.setTag(holder);
			} else {
				holder = (UserHolder1) convertView.getTag();
			}
			// holder.event_title.setTypeface(event.getTextBold());
			// holder.event_date.setTypeface(event.getTextBold());
			// // holder.event_time.setTypeface(event.getTextNormal());
			// holder.event_desc.setTypeface(event.getTextNormal());

			EventsVariables eve1 = events_upcoming.get(position);

			// holder.category.setText(eve1.category);
			holder.category.setText(titles.get(position));
			System.out.println("eve category title" + eve1.category
					+ eve1.title);
			// holder.event_time.setText(eve1.endDate.replace("T", " "));
			Utility util = new Utility(getActivity());
			// holder.event_date.setText(util.dateConvert(eve1.startDate,
			// eve1.endDate));
			holder.locationTxt.setText(eve1.venue);
			
//			if (fontChaning == 1) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
//			} else if (fontChaning == 2) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
//			} else if (fontChaning == 3) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
//			} else if (fontChaning == 0) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
//			}
			
			if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
			}else if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			}else if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
			}
			// androidAQuery.id(holder.iv).image(eve1.thumbnail, true, true,
			// AQuery.FADE_IN, position, null, 0, 0);

			holder.iv.setImageResource(img.get(position));
			return convertView;
		}

		class UserHolder1 {
			TextView category;
			TextView event_title;
			TextView locationTxt;
			TextView event_time;
			TextView event_desc;
			ImageView iv;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(getActivity(), "6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(getActivity());
	}

}
