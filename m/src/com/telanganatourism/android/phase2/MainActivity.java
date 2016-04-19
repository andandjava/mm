package com.telanganatourism.android.phase2;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.flurry.android.FlurryAgent;
import com.telanganatourism.android.phase2.model.NavDrawerItem;
import com.telanganatourism.android.phase2.objects.ItemsObj;
import com.telanganatourism.android.phase2.util.Utility;

public class MainActivity extends FragmentActivity implements
		SearchView.OnQueryTextListener, OnClickListener {

	int backStackCount;
	private SearchView mSearchView;
	int navFlag;
	public static DrawerLayout mDrawerLayout;
	// private ListView mDrawerList;
	// static LinearLayout mDrawerList1;
	private ActionBarDrawerToggle mDrawerToggle;
	Boolean flg = false, flg1 = false;
	// nav drawer title
	private CharSequence mDrawerTitle;

	public static String tag = "Home";

	ListView dialog_ListView;

	// used to store app title
	// private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons, navMenuIcons1;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private ArrayList<NavDrawerItem> navDrawerItems1;
	// private NavDrawerListAdapter adapter, adapter1;

	// static String[] stay = { "Category", "Location", "Distance" };

	public static String[] whrdo = { "Hyderabad", "Khammam", "Mahabubnagar",
			"Nalgonda", "Warangal", "Adilabad", "Nizamabad", "Karimnagar",
			"Medak", "Rangareddy" };

	public static String changeColorStr = "#ffffff", changeTitle;

	public static TextView txt_logo, txt_logo1, txt_title, txt_filter,
			txt_search, txt_location, txt_back_search, txt_Edit,
			selectLocation, divinetxt, heritagetxt, naturetxt, wildtxt,
			journeytxt, boatingtxt;
	public static TextView allTxt, hydTxt, khammTxt, mbnrTxt, nlgTxt, warngalTxt,
			adilabadTxt, nizamabadTxt, karimnagarTxt, medakTxt, rangareddyTxt,selectLocTxt;
	public static Button btn_menu, searchBtn, menuBtn, submitBtn;

	public static RelativeLayout headerLayout, mDrawerList2;

	public static LinearLayout searchLayout, categoryLayout, locationLayout;

	public static ToggleButton toggleButton;

	boolean divineFlag = false, heritageFlag = false, natureFlag = false,
			wildFlag = false, journeyFlag = false, boatingFlag = false;

	public static ArrayList<ItemsObj> dest_items_array;

	public static int detailNavFlag = 0, searchLocCode = 1;
	public static String searchJsonString = "";

//	Utility utility;

	String currentLanguage = "";

	Configuration config;

	public static boolean allFlag = false, hydFlag = false, karimnagarFlag = false,
			khamammFlag = false, medakFlag = false, mbnrFlag = false,
			nizambadFlag = false, adilabadFlag = false, warangalFlag = false,
			rangareddyFlag = false, nalgondaFlag = false;

	public static SharedPreferences pref;
	public static Editor editor;
	public static String Key_GET_USER_ID = "USER_ID";

	// public static int exitVariable = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
		// setContentView(R.layout.arabic_activity_main);
		// } else {
		setContentView(R.layout.activity_main);
		// }

		pref = getApplicationContext().getSharedPreferences(
				"telangana_tourism", getApplicationContext().MODE_PRIVATE);
		editor = pref.edit();

		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(tp);
		}

		// if(pref.getString(SplashScreen.Key_GET_USER_RATE_TRACK,
		// "").equalsIgnoreCase("false") ){
		//
		// final Dialog dialog = new Dialog(MainActivity.this);
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// dialog.setContentView(R.layout.rate_dialog);
		//
		// // set the custom dialog components - text, image and
		// // button
		//
		// Button noThanks_btn = (Button) dialog
		// .findViewById(R.id.btn_nothanks);
		// // if button is clicked, close the custom dialog
		// noThanks_btn.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// SplashScreen.editor.putString(
		// SplashScreen.Key_GET_USER_RATE_TRACK, "true");
		// SplashScreen.editor.commit();
		// dialog.dismiss();
		//
		// }
		// });
		//
		// Button btn_remindlater = (Button) dialog
		// .findViewById(R.id.btn_remindlater);
		// // if button is clicked, close the custom dialog
		// btn_remindlater.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// SplashScreen.editor.putString(
		// SplashScreen.Key_GET_USER_RATE_TRACK, "true");
		// SplashScreen.editor.commit();
		// dialog.dismiss();
		//
		// }
		// });
		//
		// Button btn_ratenow = (Button) dialog
		// .findViewById(R.id.btn_ratenow);
		// // if button is clicked, close the custom dialog
		// btn_ratenow.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// SplashScreen.editor.putString(
		// SplashScreen.Key_GET_USER_RATE_TRACK, "true");
		// SplashScreen.editor.commit();
		// dialog.dismiss();
		// }
		// });
		//
		// dialog.show();
		// }

		// currentLanguage = "hi";
		// Locale locale = new Locale(currentLanguage);
		// Locale.setDefault(locale);
		//
		// /**
		// * Print the current language
		// */
		// System.out.println("My current language: "
		// + Locale.getDefault());
		//
		//
		// config = new Configuration();
		// config.locale = locale;
		// getBaseContext().getResources().updateConfiguration(config,
		// getBaseContext().getResources().getDisplayMetrics());

		toggleButton = (ToggleButton) findViewById(R.id.togglebutton);
//		utility = new Utility(MainActivity.this);

		GPSTracker gps = new GPSTracker(getBaseContext()) {
		};
		if (gps.canGetLocation()) {
			Constants.latitude = gps.getLatitude();
			Constants.longitude = gps.getLongitude();

		}
		dest_items_array = new ArrayList<ItemsObj>();

		initLayout();

		if ((SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Track, ""))
				.equalsIgnoreCase("true")) {
			toggleButton.setChecked(true);
		} else {
			toggleButton.setChecked(false);
		}

		toggleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ((SplashScreen.pref.getString(
						SplashScreen.Key_GET_USER_Track, ""))
						.equalsIgnoreCase("true")) {

					// String strUserFlag =
					// SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Track,
					// "");
					// if(strUserFlag.equalsIgnoreCase("true"))
					// {
					//
					// }
					SplashScreen.editor.putString(
							SplashScreen.Key_GET_USER_Session, "");
					SplashScreen.editor.putString(
							SplashScreen.Key_GET_USER_Track, "false");
					SplashScreen.editor.commit();
					
					if(tag.equalsIgnoreCase(getResources().getString(
							R.string.settings_sidemenu))){
						getSettingsFragment();
						FragmentManager fragmentManager = getFragmentManager();
						SettingsFragment fragment = new SettingsFragment();
						fragmentManager
								.beginTransaction()
								.replace(
										R.id.frame_container,
										fragment)
								.commit();
						
//						mDrawerLayout.closeDrawer(mDrawerList2);
					}

					stopService(new Intent(
							MainActivity.this,
							com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));

					// if (Constants.toggleFlag == true) {
					//
					// SettingsFragment fragment = new SettingsFragment();
					// if (fragment != null) {
					//
					// FragmentManager fragmentManager = getFragmentManager();
					// fragmentManager
					// .beginTransaction()
					// .replace(R.id.frame_container, fragment,
					// tag).addToBackStack(tag).commit();
					//
					// mDrawerLayout.closeDrawer(mDrawerList2);
					// } else {
					// // error in creating fragment
					// Log.e("MainActivity", "Error in creating fragment");
					// }
					// } else {
					mDrawerLayout.closeDrawer(mDrawerList2);
					// }
					toggleButton.setChecked(false);

				} else {
					
					if(Utility.checkInternetConnection(MainActivity.this)){
						new SettingsTaskTask().execute();
					}else{
						Utility.showAlertNoInternetService(MainActivity.this);
					}

					
//					SplashScreen.editor.putString(
//							SplashScreen.Key_GET_USER_Session, "");
//					SplashScreen.editor.putString(
//							SplashScreen.Key_GET_USER_Track, "true");
//					SplashScreen.editor.commit();
//					toggleButton.setChecked(true);

				}
			}
		});

			// toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener()
		// {
		// @Override
		// public void onCheckedChanged(final CompoundButton toggleButton,
		// boolean isChecked) {
		//
		// if (isChecked) {
		//
		// new SettingsTaskTask().execute();
		//
		// // // custom dialog
		// // final Dialog dialog = new Dialog(MainActivity.this);
		// // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// // dialog.setContentView(R.layout.dialog);
		// //
		// // // set the custom dialog components - text, image and
		// // button
		// //
		// // Button yesBtn = (Button)
		// // dialog.findViewById(R.id.btn_yes);
		// // // if button is clicked, close the custom dialog
		// // yesBtn.setOnClickListener(new OnClickListener() {
		// // @Override
		// // public void onClick(View v) {
		// // dialog.dismiss();
		// // toggleButton.setChecked(true);
		// // SplashScreen.editor.putString(
		// // SplashScreen.Key_GET_USER_Track, "true");
		// // SplashScreen.editor.commit();
		// // new TrackingServiceTask().execute();
		// //
		// // if (Constants.toggleFlag == true) {
		// //
		// // SettingsFragment fragment = new SettingsFragment();
		// // if (fragment != null) {
		// //
		// // FragmentManager fragmentManager = getFragmentManager();
		// // fragmentManager
		// // .beginTransaction()
		// // .replace(R.id.frame_container,
		// // fragment, tag)
		// // .addToBackStack(tag).commit();
		// //
		// // mDrawerLayout.closeDrawer(mDrawerList2);
		// // } else {
		// // // error in creating fragment
		// // Log.e("MainActivity",
		// // "Error in creating fragment");
		// // }
		// // }else{
		// // mDrawerLayout.closeDrawer(mDrawerList2);
		// // }
		// // }
		// // });
		// //
		// // Button noBtn = (Button) dialog.findViewById(R.id.btn_no);
		// // // if button is clicked, close the custom dialog
		// // noBtn.setOnClickListener(new OnClickListener() {
		// // @Override
		// // public void onClick(View v) {
		// // dialog.dismiss();
		// // toggleButton.setChecked(false);
		// // SplashScreen.editor.putString(
		// // SplashScreen.Key_GET_USER_Session, "");
		// // SplashScreen.editor.putString(
		// // SplashScreen.Key_GET_USER_Track, "false");
		// // SplashScreen.editor.commit();
		// //
		// // stopService(new Intent(
		// // MainActivity.this,
		// //
		// com.telanganatourism.android.backgroundservice.TrackingService.class));
		// // }
		// // });
		// //
		// // dialog.show();
		//
		// } else {
		//
		// // String strUserFlag =
		// // SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Track,
		// // "");
		// // if(strUserFlag.equalsIgnoreCase("true"))
		// // {
		// //
		// // }
		// SplashScreen.editor.putString(
		// SplashScreen.Key_GET_USER_Session, "");
		// SplashScreen.editor.putString(
		// SplashScreen.Key_GET_USER_Track, "false");
		// SplashScreen.editor.commit();
		//
		// stopService(new Intent(
		// MainActivity.this,
		// com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));
		//
		// if (Constants.toggleFlag == true) {
		//
		// SettingsFragment fragment = new SettingsFragment();
		// if (fragment != null) {
		//
		// FragmentManager fragmentManager = getFragmentManager();
		// fragmentManager
		// .beginTransaction()
		// .replace(R.id.frame_container, fragment,
		// tag).addToBackStack(tag).commit();
		//
		// mDrawerLayout.closeDrawer(mDrawerList2);
		// } else {
		// // error in creating fragment
		// Log.e("MainActivity", "Error in creating fragment");
		// }
		// } else {
		// mDrawerLayout.closeDrawer(mDrawerList2);
		// }
		// }
		// }
		// });			

		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		navMenuIcons1 = getResources().obtainTypedArray(
				R.array.nav_drawer_icons1);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList2 = (RelativeLayout) findViewById(R.id.left_drawer1);
		// mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// mDrawerList1 = (LinearLayout) findViewById(R.id.right_drawer);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		navDrawerItems1 = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(0, -1),
				navMenuTitles[0]));
		// What to see
		navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(1, -1),
				navMenuTitles[1]));
		// what to do
		navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(2, -1),
				navMenuTitles[2]));
		// Where to stay
		navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(3, -1),
				navMenuTitles[3]));
		// where to eat
		navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(4, -1),
				navMenuTitles[4]));
		// where to shop
		navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(5, -1),
				navMenuTitles[5]));
		// shop online
		navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(6, -1),
				navMenuTitles[6]));
		// articles
		navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(7, -1),
				navMenuTitles[7]));
		// culture
		navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(8, -1),
				navMenuTitles[8]));
		// favorite
		navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(9, -1),
				navMenuTitles[9]));
		// weather
		navDrawerItems.add(new NavDrawerItem(
				navMenuIcons.getResourceId(10, -1), navMenuTitles[10]));
		// contact
		navDrawerItems.add(new NavDrawerItem(
				navMenuIcons.getResourceId(11, -1), navMenuTitles[11]));
		// // settings
		// navDrawerItems.add(new NavDrawerItem(
		// navMenuIcons.getResourceId(12, -1), navMenuTitles[12]));
		// // locations
		// navDrawerItems.add(new NavDrawerItem(
		// navMenuIcons.getResourceId(13, -1), navMenuTitles[13]));
		// // emergency
		// navDrawerItems.add(new NavDrawerItem(
		// navMenuIcons.getResourceId(14, -1), navMenuTitles[14]));
		// // tour
		// navDrawerItems.add(new NavDrawerItem(
		// navMenuIcons.getResourceId(15, -1), navMenuTitles[15]));
		// // tour
		// navDrawerItems.add(new NavDrawerItem(
		// navMenuIcons.getResourceId(16, -1), navMenuTitles[16]));

		// locations
		navDrawerItems1.add(new NavDrawerItem(navMenuIcons1
				.getResourceId(0, -1), "Category"));
		// emergency
		navDrawerItems1.add(new NavDrawerItem(navMenuIcons1
				.getResourceId(1, -1), "Location"));
		// tour
		navDrawerItems1.add(new NavDrawerItem(navMenuIcons1
				.getResourceId(2, -1), "Distance"));

		// Recycle the typed array
		navMenuIcons.recycle();
		navMenuIcons1.recycle();

		// /////////////////////////////////////////

		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));

		ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setHomeButtonEnabled(false);
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setDisplayShowCustomEnabled(true);

		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.custom_actionbar1, null);

		txt_logo = (TextView) mCustomView.findViewById(R.id.txt_logo);

		txt_logo1 = (TextView) mCustomView.findViewById(R.id.txt_logo1);

		txt_logo1.setVisibility(View.GONE);

		txt_title = (TextView) mCustomView.findViewById(R.id.txt_title);

		txt_Edit = (TextView) mCustomView.findViewById(R.id.editBtn);

		txt_Edit.setVisibility(View.GONE);

		txt_filter = (TextView) mCustomView.findViewById(R.id.filterBtn);

		txt_filter.setVisibility(View.GONE);

		txt_search = (TextView) mCustomView.findViewById(R.id.searchBtn);

		txt_location = (TextView) mCustomView.findViewById(R.id.locationBtn);

		txt_back_search = (TextView) mCustomView.findViewById(R.id.back_search);

		btn_menu = (Button) mCustomView.findViewById(R.id.menuBtn);

		headerLayout = (RelativeLayout) mCustomView
				.findViewById(R.id.headerLayout);

		btn_menu.setBackgroundResource(R.drawable.menu_green);

		searchLayout = (LinearLayout) mCustomView
				.findViewById(R.id.searchLayout);

		searchLayout.setBackgroundColor(Color.parseColor("#ffffff"));

		txt_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchLayout.setVisibility(View.VISIBLE);
				txt_filter.setVisibility(View.GONE);
				txt_location.setVisibility(View.GONE);
				btn_menu.setVisibility(View.GONE);
			}
		});

		txt_back_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchLayout.setVisibility(View.GONE);
				txt_filter.setVisibility(View.GONE);
				txt_location.setVisibility(View.VISIBLE);
				btn_menu.setVisibility(View.VISIBLE);
			}
		});

		// txt_location.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
		// mDrawerLayout.closeDrawer(Gravity.RIGHT);
		// mDrawerLayout.closeDrawer(Gravity.LEFT);
		// } else {
		// mDrawerLayout.openDrawer(Gravity.RIGHT);
		// mDrawerLayout.closeDrawer(Gravity.LEFT);
		// }
		// }
		// });

		menuBtn = (Button) mCustomView.findViewById(R.id.button1);

		menuBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (flg1) {
					mDrawerLayout.openDrawer(mDrawerList2);
					mDrawerLayout.closeDrawer(Gravity.RIGHT);
					flg1 = false;
				} else {
					mDrawerLayout.openDrawer(mDrawerList2);
					mDrawerLayout.closeDrawer(Gravity.RIGHT);
					flg1 = true;
				}

			}
		});

		txt_location.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
					mDrawerLayout.closeDrawer(Gravity.RIGHT);
					mDrawerLayout.closeDrawer(Gravity.LEFT);
				} else {
					mDrawerLayout.openDrawer(Gravity.RIGHT);
					mDrawerLayout.closeDrawer(Gravity.LEFT);
				}

			}
		});

		txt_filter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
					mDrawerLayout.closeDrawer(Gravity.RIGHT);
					mDrawerLayout.closeDrawer(Gravity.LEFT);
				} else {
					mDrawerLayout.openDrawer(Gravity.RIGHT);
					mDrawerLayout.closeDrawer(Gravity.LEFT);
				}

			}
		});

		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);

		// ///////////////////////////////////////

		// mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		// // mDrawerList1.setOnItemClickListener(new SlideMenuClickListener());
		//
		// // setting the nav drawer list adapter
		// adapter = new NavDrawerListAdapter(getApplicationContext(),
		// navDrawerItems);
		//
		// adapter1 = new NavDrawerListAdapter(getApplicationContext(),
		// navDrawerItems1);
		// mDrawerList.setAdapter(adapter);
		// mDrawerList1.setAdapter(adapter1);

		// enabling action bar app icon and behaving it as toggle button

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				0, // nav drawer open - description for accessibility
				0 // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				// TODO Auto-generated method stub
				super.onDrawerStateChanged(newState);
				if (navFlag == 0) {
					if (newState == DrawerLayout.STATE_DRAGGING) {
						if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
							mDrawerLayout.closeDrawer(Gravity.RIGHT);
							Log.v("TAG", "Drawer opened by dragging");
						} else {
							mDrawerLayout.closeDrawer(Gravity.RIGHT);
						}
					}
				}
			}

			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				if (item != null && item.getItemId() == android.R.id.home) {
					mDrawerLayout.closeDrawer(Gravity.RIGHT);
					if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
					} else {
						mDrawerLayout.openDrawer(Gravity.LEFT);
					}
				}
				return false;
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(SplashScreen.slide_menu_id);
		}
	}

	@TargetApi(11)
	static public <T> void executeAsyncTask(AsyncTask<T, ?, ?> task,
			T... params) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		} else {
			task.execute(params);
		}
	}

	class SettingsTaskTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getUserEmergencyContacts/"
					+ pref.getString(Key_GET_USER_ID, ""));
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getUserEmergencyContacts/"
					+ pref.getString(Key_GET_USER_ID, ""));
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {

				Toast.makeText(getApplicationContext(),
						"No data found from Server!!!", Toast.LENGTH_LONG)
						.show();

			} else {

				try {
					JSONObject jsonObject = new JSONObject(result);

					JSONArray jsonArray = jsonObject
							.getJSONArray("EmergencyContact");

					if (jsonArray.length() == 1) {
						if (jsonArray.getJSONObject(0).getString("name")
								.equalsIgnoreCase("null")) {

							MainActivity.changeColorStr = "#ffffff";

							MainActivity.changeTitle = getResources()
									.getString(R.string.settings_sidemenu);
							
							AlertDialog.Builder altDialog = new AlertDialog.Builder(
									MainActivity.this);
							altDialog
									.setMessage("Please submit your contact details before you start tracking"); // here
																													// add
																													// your
																													// message
							altDialog.setNeutralButton("OK",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											
											
											dialog.dismiss();
											
											SettingsFragment fragment = new SettingsFragment();
											FragmentManager fragmentManager = getFragmentManager();
											fragmentManager.beginTransaction()
													.replace(R.id.frame_container, fragment)
													.addToBackStack(tag).commit();
											toggleButton.setChecked(false);
											// update selected item and title, then close the
											// drawer
											// mDrawerList.setItemChecked(position, true);
											// mDrawerList.setSelection(position);
											// setTitle(navMenuTitles[position]);
											mDrawerLayout.closeDrawer(mDrawerList2);
										}
									});
							altDialog.show();
							

							
						} else {
							// custom dialog
							final Dialog dialog = new Dialog(MainActivity.this);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.dialog);

							// set the custom dialog components - text, image
							// and button

							Button yesBtn = (Button) dialog
									.findViewById(R.id.btn_yes);
							// if button is clicked, close the custom dialog
							yesBtn.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									dialog.dismiss();
									SplashScreen.editor.putString(
											SplashScreen.Key_GET_USER_Track,
											"true");
									SplashScreen.editor.commit();
									
									new TrackingServiceTask().execute();

									
									if(tag.equalsIgnoreCase(getResources().getString(
											R.string.settings_sidemenu))){
										getSettingsFragment();
										FragmentManager fragmentManager = getFragmentManager();
										SettingsFragment fragment = new SettingsFragment();
										fragmentManager
												.beginTransaction()
												.replace(
														R.id.frame_container,
														fragment)
												.commit();
										
//										mDrawerLayout.closeDrawer(mDrawerList2);
									}
								}
							});

							Button noBtn = (Button) dialog
									.findViewById(R.id.btn_no);
							// if button is clicked, close the custom dialog
							noBtn.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									dialog.dismiss();
									toggleButton.setChecked(false);
									SplashScreen.editor.putString(
											SplashScreen.Key_GET_USER_Session,
											"");
									SplashScreen.editor.putString(
											SplashScreen.Key_GET_USER_Track,
											"false");
									SplashScreen.editor.commit();
									
									if(tag.equalsIgnoreCase(getResources().getString(
											R.string.settings_sidemenu))){
										getSettingsFragment();
										FragmentManager fragmentManager = getFragmentManager();
										SettingsFragment fragment = new SettingsFragment();
										fragmentManager
												.beginTransaction()
												.replace(
														R.id.frame_container,
														fragment)
												.commit();
										
//										mDrawerLayout.closeDrawer(mDrawerList2);
									}

									stopService(new Intent(
											MainActivity.this,
											com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));
									
									
								}
							});

							dialog.show();
						}

					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		}
	}

	public static Button homeBtn, destinationBtn, accomodatonBtn, eventsBtn,
			shopBtn, packagesBtn, cultureBtn, favroitesBtn, weatherBtn,
			settingBtn, contactBtn, emergencyBtn, feedbackBtn, trackingTxt,myreservations, privacyPolicyBtn, cancellationPolicyBtn;

	private void initLayout() {
		// TODO Auto-generated method stub
		homeBtn = (Button) findViewById(R.id.btn_home);
		myreservations=(Button) findViewById(R.id.btn_reservations);
		destinationBtn = (Button) findViewById(R.id.btn_destination);
		accomodatonBtn = (Button) findViewById(R.id.btn_accommodation);
		eventsBtn = (Button) findViewById(R.id.btn_events);
		shopBtn = (Button) findViewById(R.id.btn_shopping);
		packagesBtn = (Button) findViewById(R.id.btn_packages);
		cultureBtn = (Button) findViewById(R.id.btn_culture);
		favroitesBtn = (Button) findViewById(R.id.btn_favroites);
		weatherBtn = (Button) findViewById(R.id.btn_weather);
		settingBtn = (Button) findViewById(R.id.btn_settings);
		contactBtn = (Button) findViewById(R.id.btn_contact);
		emergencyBtn = (Button) findViewById(R.id.btn_emergency);
		feedbackBtn = (Button) findViewById(R.id.btn_feedback);
		trackingTxt = (Button) findViewById(R.id.btn_tracking);
		privacyPolicyBtn = (Button) findViewById(R.id.btn_privacy_policy);
		cancellationPolicyBtn = (Button) findViewById(R.id.btn_cancellation_refund_policy);

		TextView networkTxt = (TextView) findViewById(R.id.networkTxt);
		TextView networkTxt1 = (TextView) findViewById(R.id.networkTxt1);
		TextView networkTxt2 = (TextView) findViewById(R.id.networkTxt2);

		if (Utility.checkInternetConnection(MainActivity.this)) {
			eventsBtn.setTextColor(Color.parseColor("#000000"));
			weatherBtn.setTextColor(Color.parseColor("#000000"));
			trackingTxt.setTextColor(Color.parseColor("#000000"));
			networkTxt.setVisibility(View.GONE);
			networkTxt1.setVisibility(View.GONE);
			networkTxt2.setVisibility(View.GONE);
			eventsBtn.setEnabled(true);
			weatherBtn.setEnabled(true);
			toggleButton.setEnabled(true);
			toggleButton.setVisibility(View.VISIBLE);
		} else {
			eventsBtn.setTextColor(Color.parseColor("#E0E0E0"));
			weatherBtn.setTextColor(Color.parseColor("#E0E0E0"));
			trackingTxt.setTextColor(Color.parseColor("#E0E0E0"));
			networkTxt.setVisibility(View.VISIBLE);
			networkTxt1.setVisibility(View.VISIBLE);
			networkTxt2.setVisibility(View.VISIBLE);
			eventsBtn.setEnabled(false);
			weatherBtn.setEnabled(false);
			toggleButton.setEnabled(false);
			toggleButton.setVisibility(View.GONE);
		}

		categoryLayout = (LinearLayout) findViewById(R.id.layout);
		locationLayout = (LinearLayout) findViewById(R.id.locationLayout);

		selectLocation = (TextView) findViewById(R.id.selectCatTxt);
		divinetxt = (TextView) findViewById(R.id.divineDestionationTxt);
		heritagetxt = (TextView) findViewById(R.id.heritageTxt);
		naturetxt = (TextView) findViewById(R.id.natureTxt);
		wildtxt = (TextView) findViewById(R.id.wildlifeTxt);
		journeytxt = (TextView) findViewById(R.id.journeyTxt);
		boatingtxt = (TextView) findViewById(R.id.boatingTxt);

		allTxt = (TextView) findViewById(R.id.allTxt);
		hydTxt = (TextView) findViewById(R.id.hydTxt);
		khammTxt = (TextView) findViewById(R.id.khammamTxt);
		mbnrTxt = (TextView) findViewById(R.id.mbnrTxt);
		nlgTxt = (TextView) findViewById(R.id.nalgondaTxt);
		warngalTxt = (TextView) findViewById(R.id.warangalTxt);
		adilabadTxt = (TextView) findViewById(R.id.adilabadTxt);
		nizamabadTxt = (TextView) findViewById(R.id.nizambadTxt);
		karimnagarTxt = (TextView) findViewById(R.id.karimnagrTxt);
		medakTxt = (TextView) findViewById(R.id.medakTxt);
		rangareddyTxt = (TextView) findViewById(R.id.rangareddyTxt);
		selectLocTxt = (TextView) findViewById(R.id.selectLocTxt);

		Button fakeBtn = (Button) findViewById(R.id.button1);
		Button fakeButton = (Button) findViewById(R.id.button2);

		searchBtn = (Button) findViewById(R.id.searchBtn);

		submitBtn = (Button) findViewById(R.id.submitBtn);

		categoryLayout.setVisibility(View.GONE);
		locationLayout.setVisibility(View.VISIBLE);
		searchBtn.setVisibility(View.GONE);
		fakeButton.setVisibility(View.VISIBLE);

		homeBtn.setTypeface(Constants.ProximaNova_Regular);
		destinationBtn.setTypeface(Constants.ProximaNova_Regular);
		myreservations.setTypeface(Constants.ProximaNova_Regular);
		accomodatonBtn.setTypeface(Constants.ProximaNova_Regular);
		eventsBtn.setTypeface(Constants.ProximaNova_Regular);
		shopBtn.setTypeface(Constants.ProximaNova_Regular);
		packagesBtn.setTypeface(Constants.ProximaNova_Regular);
		cultureBtn.setTypeface(Constants.ProximaNova_Regular);
		favroitesBtn.setTypeface(Constants.ProximaNova_Regular);
		weatherBtn.setTypeface(Constants.ProximaNova_Regular);
		settingBtn.setTypeface(Constants.ProximaNova_Regular);
		contactBtn.setTypeface(Constants.ProximaNova_Regular);
		emergencyBtn.setTypeface(Constants.ProximaNova_Regular);
		feedbackBtn.setTypeface(Constants.ProximaNova_Regular);
		trackingTxt.setTypeface(Constants.ProximaNova_Regular);
		selectLocation.setTypeface(Constants.ProximaNova_Regular);
		privacyPolicyBtn.setTypeface(Constants.ProximaNova_Regular);
		cancellationPolicyBtn.setTypeface(Constants.ProximaNova_Regular);

		divinetxt.setTypeface(Constants.ProximaNova_Regular);
		heritagetxt.setTypeface(Constants.ProximaNova_Regular);
		naturetxt.setTypeface(Constants.ProximaNova_Regular);
		wildtxt.setTypeface(Constants.ProximaNova_Regular);
		journeytxt.setTypeface(Constants.ProximaNova_Regular);
		boatingtxt.setTypeface(Constants.ProximaNova_Regular);

		allTxt.setTypeface(Constants.ProximaNova_Regular);
		hydTxt.setTypeface(Constants.ProximaNova_Regular);
		khammTxt.setTypeface(Constants.ProximaNova_Regular);
		mbnrTxt.setTypeface(Constants.ProximaNova_Regular);
		nlgTxt.setTypeface(Constants.ProximaNova_Regular);
		warngalTxt.setTypeface(Constants.ProximaNova_Regular);
		adilabadTxt.setTypeface(Constants.ProximaNova_Regular);
		nizamabadTxt.setTypeface(Constants.ProximaNova_Regular);
		karimnagarTxt.setTypeface(Constants.ProximaNova_Regular);
		medakTxt.setTypeface(Constants.ProximaNova_Regular);
		rangareddyTxt.setTypeface(Constants.ProximaNova_Regular);

		submitBtn.setTypeface(Constants.ProximaNova_Regular);
		selectLocTxt.setTypeface(Constants.ProximaNova_Regular);
		// if(SplashScreen.flg.equalsIgnoreCase("1")){
		// if (Constants.locationId.equalsIgnoreCase("1")) {
		// hydTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// } else if (Constants.locationId.equalsIgnoreCase("2")) {
		// warngalTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// } else if (Constants.locationId.equalsIgnoreCase("3")) {
		// karimnagarTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// } else if (Constants.locationId.equalsIgnoreCase("4")) {
		// khammTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// } else if (Constants.locationId.equalsIgnoreCase("5")) {
		// medakTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// } else if (Constants.locationId.equalsIgnoreCase("6")) {
		// nlgTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// } else if (Constants.locationId.equalsIgnoreCase("7")) {
		// nizamabadTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// } else if (Constants.locationId.equalsIgnoreCase("8")) {
		// adilabadTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// } else if (Constants.locationId.equalsIgnoreCase("9")) {
		// mbnrTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// } else if (Constants.locationId.equalsIgnoreCase("10")) {
		// rangareddyTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// }else{
		// hydTxt.setCompoundDrawablesWithIntrinsicBounds(
		// null,
		// null,
		// getApplicationContext().getResources().getDrawable(
		// R.drawable.check_unselect), null);
		// }
		// }else if(SplashScreen.flg.equalsIgnoreCase("2")){
		//
		// if (Constants.locationId.equalsIgnoreCase("11")) {
		// hydTxt.setChecked(true);
		// } else if (Constants.locationId.equalsIgnoreCase("12")) {
		// warngalTxt.setChecked(true);
		// } else if (Constants.locationId.equalsIgnoreCase("13")) {
		// karimnagarTxt.setChecked(true);
		// } else if (Constants.locationId.equalsIgnoreCase("14")) {
		// khammTxt.setChecked(true);
		// } else if (Constants.locationId.equalsIgnoreCase("15")) {
		// medakTxt.setChecked(true);
		// } else if (Constants.locationId.equalsIgnoreCase("16")) {
		// nlgTxt.setChecked(true);
		// } else if (Constants.locationId.equalsIgnoreCase("17")) {
		// nizamabadTxt.setChecked(true);
		// } else if (Constants.locationId.equalsIgnoreCase("18")) {
		// adilabadTxt.setChecked(true);
		// } else if (Constants.locationId.equalsIgnoreCase("19")) {
		// mbnrTxt.setChecked(true);
		// } else if (Constants.locationId.equalsIgnoreCase("20")) {
		// rangareddyTxt.setChecked(true);
		// }else{
		// hydTxt.setChecked(true);
		// }
		// }

		homeBtn.setOnClickListener(this);
		myreservations.setOnClickListener(this);
		destinationBtn.setOnClickListener(this);
		accomodatonBtn.setOnClickListener(this);
		eventsBtn.setOnClickListener(this);
		shopBtn.setOnClickListener(this);
		packagesBtn.setOnClickListener(this);
		cultureBtn.setOnClickListener(this);
		favroitesBtn.setOnClickListener(this);
		weatherBtn.setOnClickListener(this);
		settingBtn.setOnClickListener(this);
		contactBtn.setOnClickListener(this);
		feedbackBtn.setOnClickListener(this);
		emergencyBtn.setOnClickListener(this);
		selectLocation.setOnClickListener(this);
		privacyPolicyBtn.setOnClickListener(this);
		cancellationPolicyBtn.setOnClickListener(this);

		submitBtn.setOnClickListener(this);

		divinetxt.setOnClickListener(this);
		heritagetxt.setOnClickListener(this);
		naturetxt.setOnClickListener(this);
		wildtxt.setOnClickListener(this);
		journeytxt.setOnClickListener(this);
		boatingtxt.setOnClickListener(this);

		allTxt.setOnClickListener(this);
		hydTxt.setOnClickListener(this);
		khammTxt.setOnClickListener(this);
		mbnrTxt.setOnClickListener(this);
		nlgTxt.setOnClickListener(this);
		warngalTxt.setOnClickListener(this);
		adilabadTxt.setOnClickListener(this);
		nizamabadTxt.setOnClickListener(this);
		karimnagarTxt.setOnClickListener(this);
		medakTxt.setOnClickListener(this);
		rangareddyTxt.setOnClickListener(this);

		fakeBtn.setOnClickListener(this);
	}

	static TextView[] txtName, txtLine;

	public static void getRightMenuList(Context context, String[] stay) {

		LayoutParams linLayoutParam = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		LayoutParams linLayoutParam_line = new LayoutParams(
				LayoutParams.MATCH_PARENT, 1);

		// mDrawerList1.removeAllViews();
		txtName = new TextView[stay.length];
		txtLine = new TextView[stay.length];

		for (int j = 0; j < stay.length; j++) {

			txtName[j] = new TextView(context);
			txtName[j].setLayoutParams(linLayoutParam);
			txtName[j].setText(stay[j]);
			txtName[j].setTextSize(18);
			txtName[j].setPadding(10, 10, 10, 10);
			txtName[j].setGravity(Gravity.CENTER_VERTICAL);
			// mDrawerList1.addView(txtName[j]);

			txtLine[j] = new TextView(context);
			txtLine[j].setLayoutParams(linLayoutParam_line);
			txtLine[j].setBackgroundColor(Color.BLACK);
			// mDrawerList1.addView(txtLine[j]);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
			// if (position == 11) {
			// startActivity(new Intent(getApplicationContext(),
			// Weather.class));
			// }else
			if (position == 9) {
				startActivity(new Intent(getApplicationContext(),
						SettingsActivity.class));
			}
			if (position == 0) {
				navFlag = 0;
			} else if (position == 8 || position == 10 || position == 11) {
				navFlag = 2;
			} else {
				navFlag = 1;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		// if (navFlag == 1) {
		// menu.findItem(R.id.action_filter).setVisible(true);
		//
		// } else if (navFlag == 2) {
		// menu.findItem(R.id.action_filter).setVisible(false);
		// menu.findItem(R.id.action_location).setVisible(false);
		// menu.findItem(R.id.action_search).setVisible(false);
		// } else {
		// menu.findItem(R.id.action_filter).setVisible(false);
		// }
		//
		// MenuItem searchItem = menu.findItem(R.id.action_search);
		// mSearchView = (SearchView) searchItem.getActionView();
		// mSearchView.setQueryHint("Search Here");
		// setupSearchView(searchItem);
		// invalidateOptionsMenu();

		return true;
	}

	@SuppressLint("NewApi")
	private void setupSearchView(MenuItem searchItem) {

		if (isAlwaysExpanded()) {
			mSearchView.setIconifiedByDefault(false);
			// getActionBar().setIcon(android.R.color.transparent);
		} else {
			searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			// getActionBar().setIcon(R.drawable.telanganatourismlogo);
		}

		searchItem
				.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

					@Override
					public boolean onMenuItemActionExpand(MenuItem item) {
						// Do whatever you need
						getActionBar().setIcon(android.R.color.transparent);
						return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
					}

					@Override
					public boolean onMenuItemActionCollapse(MenuItem item) {
						// Do whatever you need
						getActionBar().setIcon(R.drawable.telanganatourismlogo);

						return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
					}
				});

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (searchManager != null) {
			List<SearchableInfo> searchables = searchManager
					.getSearchablesInGlobalSearch();

			SearchableInfo info = searchManager
					.getSearchableInfo(getComponentName());
			for (SearchableInfo inf : searchables) {
				if (inf.getSuggestAuthority() != null
						&& inf.getSuggestAuthority().startsWith("applications")) {
					info = inf;
				}
			}

			int id = mSearchView.getContext().getResources()
					.getIdentifier("android:id/search_src_text", null, null);
			TextView textView = (TextView) mSearchView.findViewById(id);
			textView.setTextColor(Color.BLACK);
			mSearchView.setSearchableInfo(info);
		}

		mSearchView.setOnQueryTextListener(this);
	}

	public boolean onQueryTextChange(String newText) {
		// mStatusView.setText("Query = " + newText);
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		// mStatusView.setText("Query = " + query + " : submitted");
		return false;
	}

	public boolean onClose() {
		// mStatusView.setText("Closed!");
		return false;
	}

	protected boolean isAlwaysExpanded() {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {

		// case R.id.action_filter:
		// if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
		// mDrawerLayout.closeDrawer(Gravity.RIGHT);
		// mDrawerLayout.closeDrawer(Gravity.LEFT);
		// } else {
		// mDrawerLayout.openDrawer(Gravity.RIGHT);
		// mDrawerLayout.closeDrawer(Gravity.LEFT);
		// }
		// return true;
		//
		// case R.id.action_location:
		// if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
		// mDrawerLayout.closeDrawer(Gravity.RIGHT);
		// mDrawerLayout.closeDrawer(Gravity.LEFT);
		// } else {
		// mDrawerLayout.openDrawer(Gravity.RIGHT);
		// mDrawerLayout.closeDrawer(Gravity.LEFT);
		// }
		// return true;
		case android.R.id.home:
			// do your action here.

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList2);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			getHomeFragment();
			fragment = new HomeFragment();
			break;
		case 13:
			getMyreservationsFragment();
			fragment = new MyreservationsFragment();
			break;
		case 14:
			getPrivacyPolicyFragment();
			fragment = new PrivacyPolicyFragment();
			break;
		case 15:
			getCancellationPolicyFragment();
			fragment = new CancellationRefundPolicyFragment();
			break;
		case 1:

			getDestinationFragment();
			fragment = new DestinationFragment();
			break;
		case 2:

			getAccomadationFragment();
			fragment = new WhereToShopFragment();
			break;
		case 3:
			getEventsFragment();
			fragment = new WhereToShopFragment();
			break;
		case 4:
			getSettingsFragment();
			fragment = new SettingsFragment();
			break;
		case 5:
			getPackagesFragment();
			fragment = new PackagesFragment();
			break;

		case 12:
			getShopFragment();
			fragment = new WhereToShopFragment();
		case 6:
			getCultureFragment();
			fragment = new WhereToShopFragment();
			break;
		case 7:
			getFavroutiesFragment();
			fragment = new FavoritesFragment();
			break;
		case 8:
			getWeatherFragment();
			fragment = new WeatherFragment();
			break;

		case 10:
			getContactUsFragment();
			fragment = new ContactUsFragment();
			break;
		case 11:
			getEmergencyFragment();
			fragment = new EmergencyFragment();
			break;

		case 9:
			getFeedbackFragment();
			fragment = new FeedBackFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment)
					.addToBackStack(tag).commit();
			// update selected item and title, then close the drawer
			// mDrawerList.setItemChecked(position, true);
			// mDrawerList.setSelection(position);
			// setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList2);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	public Fragment getHomeFragment() {

		tag = "Home";

		return new HomeFragment();
	}
	
	
	private void getMyreservationsFragment() {

		MainActivity.changeTitle = getResources().getString(
				R.string.reservation_sidemenu);
		MainActivity.changeColorStr = "#2a9595";
//		Constants.cdt_id = 0;
		tag = getResources().getString(R.string.reservation_sidemenu);
		// return new DestinationFragment();
	}
	
	private void getPrivacyPolicyFragment() {

		MainActivity.changeTitle = getResources().getString(
				R.string.privacy_policy_sidemenu);
		MainActivity.changeColorStr = "#2a9595";
//		Constants.cdt_id = 0;
		tag = getResources().getString(R.string.privacy_policy_sidemenu);
		// return new DestinationFragment();
	}
	
	private void getCancellationPolicyFragment() {

		MainActivity.changeTitle = getResources().getString(
				R.string.cancellation_policy_sidemenu);
		MainActivity.changeColorStr = "#2a9595";
//		Constants.cdt_id = 0;
		tag = getResources().getString(R.string.cancellation_policy_sidemenu);
		// return new DestinationFragment();
	}

	private void getDestinationFragment() {

		MainActivity.changeTitle = getResources().getString(
				R.string.destination_sidemenu);
		MainActivity.changeColorStr = "#2a9595";
		Constants.cdt_id = 0;
		tag = getResources().getString(R.string.destination_sidemenu);
		// return new DestinationFragment();
	}

	private void getAccomadationFragment() {

		Constants.cdt_id = 1;
		MainActivity.changeColorStr = "#e6912d";
		Constants.sublistFlag = 3;
		MainActivity.changeTitle = getResources().getString(
				R.string.accomodation_sidemenu);
		tag = getResources().getString(R.string.accomodation_sidemenu);
		// return new WhereToShopFragment();
	}

	private void getEventsFragment() {
		Constants.cdt_id = 2;
		MainActivity.changeColorStr = "#af4141";

		MainActivity.changeTitle = getResources().getString(
				R.string.events_sidemenu);
		Constants.sublistFlag = 4;
		tag = getResources().getString(R.string.events_sidemenu);
		// return new WhereToShopFragment();
	}

	private void getPackagesFragment() {

		Constants.cdt_id = 3;
		MainActivity.changeColorStr = "#9e5dd0";

		MainActivity.changeTitle = getResources().getString(
				R.string.packages_sidemenu);
		// fragment = new PackagesFragment();
		tag = getResources().getString(R.string.packages_sidemenu);
		// return new PackagesFragment();
	}

	private void getShopFragment() {

		Constants.cdt_id = 0;
		MainActivity.changeColorStr = "#2a9595";

		MainActivity.changeTitle = getResources().getString(
				R.string.shopping_sidemenu);
		Constants.sublistFlag = 6;
		// Constants.cdt_id = 4;
		// MainActivity.changeColorStr = "#2a9595";
		//
		// MainActivity.changeTitle =
		// getResources().getString(R.string.shopping_sidemenu);
		// fragment = new PackagesFragment();
		tag = getResources().getString(R.string.shopping_sidemenu);
		// return new PackagesFragment();
	}

	private void getCultureFragment() {
		Constants.sublistFlag = 5;

		Constants.cdt_id = 4;
		MainActivity.changeColorStr = "#79af52";

		MainActivity.changeTitle = getResources().getString(
				R.string.culture_sidemenu);
		tag = getResources().getString(R.string.culture_sidemenu);
	}

	public void getFavroutiesFragment() {
		MainActivity.changeColorStr = "#ffffff";
		MainActivity.changeTitle = getResources().getString(
				R.string.favorites_sidemenu);
		tag = getResources().getString(R.string.favorites_sidemenu);
	}

	private void getWeatherFragment() {
		MainActivity.changeColorStr = "#ffffff";
		MainActivity.changeTitle = getResources().getString(
				R.string.weather_sidemenu);
		tag = getResources().getString(R.string.weather_sidemenu);
	}

	private void getSettingsFragment() {
		MainActivity.changeColorStr = "#ffffff";
		MainActivity.changeTitle = getResources().getString(
				R.string.settings_sidemenu);
		tag = getResources().getString(R.string.settings_sidemenu);
	}

	private void getContactUsFragment() {
		MainActivity.changeColorStr = "#ffffff";
		MainActivity.changeTitle = getResources().getString(
				R.string.contactus_sidemenu);
		tag = getResources().getString(R.string.contactus_sidemenu);
	}

	private void getFeedbackFragment() {
		MainActivity.changeColorStr = "#ffffff";
		MainActivity.changeTitle = getResources().getString(
				R.string.feed_back_sidemenu);
		tag = getResources().getString(R.string.feed_back_sidemenu);
	}

	private void getEmergencyFragment() {
		MainActivity.changeColorStr = "#ffffff";
		MainActivity.changeTitle = getResources().getString(
				R.string.emergency_sidemenu);
		tag = getResources().getString(R.string.emergency_sidemenu);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Fragment fragment = null;

		switch (v.getId()) {

		case R.id.btn_home:
			Constants.toggleFlag = false;
			getHomeFragment();
			fragment = new HomeFragment();
			break;
			
			
		case R.id.btn_reservations:
			Constants.toggleFlag = false;
//			if (TextUtils.isEmpty(Constants.locationCode)) {
//				Toast.makeText(getApplicationContext(),
//						"Please Select atleast one location", Toast.LENGTH_LONG)
//						.show();
//			} else {
				getMyreservationsFragment();
				fragment = new MyreservationsFragment();
//			}

			break;
			
		case R.id.btn_privacy_policy:
			Constants.toggleFlag = false;
//			if (TextUtils.isEmpty(Constants.locationCode)) {
//				Toast.makeText(getApplicationContext(),
//						"Please Select atleast one location", Toast.LENGTH_LONG)
//						.show();
//			} else {
				getPrivacyPolicyFragment();
				fragment = new PrivacyPolicyFragment();
//			}

			break;
			
		case R.id.btn_cancellation_refund_policy:
			Constants.toggleFlag = false;
//			if (TextUtils.isEmpty(Constants.locationCode)) {
//				Toast.makeText(getApplicationContext(),
//						"Please Select atleast one location", Toast.LENGTH_LONG)
//						.show();
//			} else {
				getCancellationPolicyFragment();
				fragment = new CancellationRefundPolicyFragment();
//			}

			break;

		case R.id.btn_destination:
			Constants.toggleFlag = false;
			if (TextUtils.isEmpty(Constants.locationCode)) {
				Toast.makeText(getApplicationContext(),
						"Please Select atleast one location", Toast.LENGTH_LONG)
						.show();
			} else {
				getDestinationFragment();
				fragment = new DestinationFragment();
			}

			break;

		case R.id.btn_accommodation:
			Constants.toggleFlag = false;
			// SettingsActivity.cdt_id = 1;
			// Constants.sublistFlag = 3;
			// MainActivity.changeColorStr = "#e6912d";
			// MainActivity.changeTitle =
			// getResources().getString(R.string.accomodation_sidemenu);
			if (TextUtils.isEmpty(Constants.locationCode)) {
				Toast.makeText(getApplicationContext(),
						"Please Select atleast one location", Toast.LENGTH_LONG)
						.show();
			} else {
				getAccomadationFragment();

				fragment = new WhereToShopFragment();
			}

			break;

		case R.id.btn_events:
			Constants.toggleFlag = false;
			// SettingsActivity.cdt_id = 2;
			// MainActivity.changeColorStr = "#af4141";
			//
			// MainActivity.changeTitle =
			// getResources().getString(R.string.events_sidemenu);
			// Constants.sublistFlag = 4;
			getEventsFragment();
			fragment = new WhereToShopFragment();
			break;

		case R.id.btn_shopping:
			Constants.toggleFlag = false;
			// SettingsActivity.cdt_id = 0;
			// MainActivity.changeColorStr = "#2a9595";
			//
			// MainActivity.changeTitle =
			// getResources().getString(R.string.shopping_sidemenu);
			// Constants.sublistFlag = 6;
			getShopFragment();
			fragment = new WhereToShopFragment();
			break;

		case R.id.btn_packages:
			Constants.toggleFlag = false;
			// SettingsActivity.cdt_id = 3;
			// MainActivity.changeColorStr = "#9e5dd0";
			//
			// MainActivity.changeTitle =
			// getResources().getString(R.string.packages_sidemenu);
			getPackagesFragment();
			fragment = new PackagesFragment();
			break;

		case R.id.btn_culture:
			Constants.toggleFlag = false;
			// SettingsActivity.cdt_id = 4;
			// Constants.sublistFlag = 5;
			// MainActivity.changeColorStr = "#79af52";
			//
			// MainActivity.changeTitle =
			// getResources().getString(R.string.culture_sidemenu);
			getCultureFragment();
			fragment = new WhereToShopFragment();
			break;

		case R.id.btn_favroites:
			Constants.toggleFlag = false;
			// MainActivity.changeColorStr = "#ffffff";
			//
			// MainActivity.changeTitle =
			// getResources().getString(R.string.favorites_sidemenu);
			getFavroutiesFragment();
			fragment = new FavoritesFragment();
			break;

		case R.id.btn_weather:
			Constants.toggleFlag = false;
			getWeatherFragment();
			fragment = new WeatherFragment();
			break;

		case R.id.btn_settings:
			Constants.toggleFlag = true;
			MainActivity.changeColorStr = "#ffffff";

			MainActivity.changeTitle = getResources().getString(
					R.string.settings_sidemenu);

			getSettingsFragment();

			fragment = new SettingsFragment();
			break;

		case R.id.btn_contact:
			Constants.toggleFlag = false;
			getContactUsFragment();
			fragment = new ContactUsFragment();
			break;

		case R.id.btn_feedback:
			Constants.toggleFlag = false;
			getFeedbackFragment();
			fragment = new FeedBackFragment();
			break;

		case R.id.btn_emergency:
			Constants.toggleFlag = false;
			getEmergencyFragment();
			fragment = new EmergencyFragment();
			break;

		case R.id.selectLocation_txt:

			final Dialog dialog = new Dialog(MainActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.locationslist);

			// Prepare ListView in dialog
			dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					MainActivity.this, R.layout.custom_row_list, whrdo);

			dialog_ListView.setAdapter(adapter);

			dialog_ListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					selectLocation.setText(whrdo[arg2]);
					dialog.dismiss();
				}
			});

			dialog.show();
			break;

		case R.id.divineDestionationTxt:

			if (divineFlag) {
				divineFlag = false;
				// divinetxt.setBackgroundResource(R.drawable.unselect);
				divinetxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_unselect, 0);
			} else {
				divineFlag = true;
				divinetxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_selected, 0);
			}
			break;

		case R.id.heritageTxt:

			if (heritageFlag) {
				heritageFlag = false;
				heritagetxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_unselect, 0);
			} else {
				heritageFlag = true;
				heritagetxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_selected, 0);
			}
			break;

		case R.id.natureTxt:
			if (natureFlag) {
				natureFlag = false;
				naturetxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_unselect, 0);
			} else {
				natureFlag = true;
				naturetxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_selected, 0);
			}
			break;

		case R.id.wildlifeTxt:
			if (wildFlag) {
				wildFlag = false;
				wildtxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_unselect, 0);
			} else {
				wildFlag = true;
				wildtxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_selected, 0);
			}
			break;

		case R.id.journeyTxt:
			if (journeyFlag) {
				journeyFlag = false;
				journeytxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_unselect, 0);
			} else {
				journeyFlag = true;
				journeytxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_selected, 0);
			}
			break;

		case R.id.boatingTxt:
			if (boatingFlag) {
				boatingFlag = false;
				boatingtxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_unselect, 0);
			} else {
				boatingFlag = true;
				boatingtxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.check_selected, 0);
			}
			break;

		case R.id.hydTxt:

			Constants.locationId = "1";

			Constants.cityId = "90883135";

			// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
			// if (hydFlag) {
			// hydFlag = false;
			// hydTxt.setCompoundDrawablesWithIntrinsicBounds(
			// getApplicationContext().getResources().getDrawable(
			// R.drawable.check_unselect), null, null,
			// null);
			// } else {
			// hydFlag = true;
			// hydTxt.setCompoundDrawablesWithIntrinsicBounds(
			// getApplicationContext().getResources().getDrawable(
			// R.drawable.check_unselect), null, null,
			// null);
			// }
			// } else {
			if (hydFlag) {
				hydFlag = false;
				hydTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			} else {
				hydFlag = true;
				hydTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			}
			// }

			// hydTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.check_selected, 0);
			// mDrawerLayout.closeDrawer(Gravity.RIGHT);

			// new FilterLocMenuAync().execute();
			break;

		case R.id.khammamTxt:
			Constants.locationId = "4";

			Constants.cityId = "2295229";
			if (khamammFlag) {
				khamammFlag = false;
				khammTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			} else {
				khamammFlag = true;
				khammTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			}
			// khammTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.check_selected, 0);
			// mDrawerLayout.closeDrawer(Gravity.RIGHT);
			// new FilterLocMenuAync().execute();
			break;

		case R.id.mbnrTxt:
			Constants.locationId = "9";
			Constants.cityId = "2281586";

			if (mbnrFlag) {
				mbnrFlag = false;
				mbnrTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			} else {
				mbnrFlag = true;
				mbnrTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			}
			// mbnrTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.check_selected, 0);
			// mDrawerLayout.closeDrawer(Gravity.RIGHT);
			// new FilterLocMenuAync().execute();
			break;

		case R.id.nalgondaTxt:
			Constants.locationId = "6";
			Constants.cityId = "90886279";

			if (nalgondaFlag) {
				nalgondaFlag = false;
				nlgTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			} else {
				nalgondaFlag = true;
				nlgTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			}
			// nalgTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.check_selected, 0);
			// mDrawerLayout.closeDrawer(Gravity.RIGHT);
			// new FilterLocMenuAync().execute();
			break;

		case R.id.warangalTxt:
			Constants.locationId = "2";

			Constants.cityId = "90882603";

			if (warangalFlag) {
				warangalFlag = false;
				warngalTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			} else {
				warangalFlag = true;
				warngalTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			}
			// warngalTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.check_selected, 0);
			// mDrawerLayout.closeDrawer(Gravity.RIGHT);
			// new FilterLocMenuAync().execute();
			break;

		case R.id.adilabadTxt:
			Constants.locationId = "8";
			Constants.cityId = "90882225";

			if (adilabadFlag) {
				adilabadFlag = false;
				adilabadTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			} else {
				adilabadFlag = true;
				adilabadTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			}
			// adilabadTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.check_selected, 0);
			// mDrawerLayout.closeDrawer(Gravity.RIGHT);
			// new FilterLocMenuAync().execute();
			break;

		case R.id.nizambadTxt:
			Constants.locationId = "7";
			Constants.cityId = "90887467";

			if (nizambadFlag) {
				nizambadFlag = false;
				nizamabadTxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, getApplicationContext().getResources()
								.getDrawable(R.drawable.check_unselect), null);
			} else {
				nizambadFlag = true;
				nizamabadTxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, getApplicationContext().getResources()
								.getDrawable(R.drawable.check_unselect), null);
			}
			// nizamabadTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.check_selected, 0);
			// mDrawerLayout.closeDrawer(Gravity.RIGHT);
			// new FilterLocMenuAync().execute();
			break;

		case R.id.karimnagrTxt:
			Constants.locationId = "3";
			Constants.cityId = "2295185";

			if (karimnagarFlag) {
				karimnagarFlag = false;
				karimnagarTxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, getApplicationContext().getResources()
								.getDrawable(R.drawable.check_unselect), null);
			} else {
				karimnagarFlag = true;
				karimnagarTxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, getApplicationContext().getResources()
								.getDrawable(R.drawable.check_unselect), null);
			}
			// karimnagarTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.check_selected, 0);
			// mDrawerLayout.closeDrawer(Gravity.RIGHT);
			// new FilterLocMenuAync().execute();
			break;

		case R.id.medakTxt:
			Constants.locationId = "5";
			Constants.cityId = "90882271";

			if (medakFlag) {
				medakFlag = false;
				medakTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			} else {
				medakFlag = true;
				medakTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getApplicationContext().getResources().getDrawable(
								R.drawable.check_unselect), null);
			}
			// medakTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.check_selected, 0);
			// mDrawerLayout.closeDrawer(Gravity.RIGHT);
			// new FilterLocMenuAync().execute();
			break;

		case R.id.rangareddyTxt:
			Constants.locationId = "10";
			Constants.cityId = "90891709";

			if (rangareddyFlag) {
				rangareddyFlag = false;
				rangareddyTxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, getApplicationContext().getResources()
								.getDrawable(R.drawable.check_unselect), null);
			} else {
				rangareddyFlag = true;
				rangareddyTxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, getApplicationContext().getResources()
								.getDrawable(R.drawable.check_unselect), null);
			}
			// rangareddyTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.check_selected, 0);
			// mDrawerLayout.closeDrawer(Gravity.RIGHT);
			// new FilterLocMenuAync().execute();
			break;

		case R.id.button1:

			break;

		// case R.id.submitBtn:
		//
		// Constants.locationCode = "";
		//
		// if (hydFlag) {
		// Constants.locationCode = Constants.locationCode + ",1";
		// }
		// if (warangalFlag) {
		// Constants.locationCode = Constants.locationCode + ",2";
		// }
		// if (karimnagarFlag) {
		// Constants.locationCode = Constants.locationCode + ",3";
		// }
		// if (khamammFlag) {
		// Constants.locationCode = Constants.locationCode + ",4";
		// }
		// if (medakFlag) {
		// Constants.locationCode = Constants.locationCode + ",5";
		// }
		// if (nalgondaFlag) {
		// Constants.locationCode = Constants.locationCode + ",6";
		// }
		// if (nizambadFlag) {
		// Constants.locationCode = Constants.locationCode + ",7";
		// }
		// if (adilabadFlag) {
		// Constants.locationCode = Constants.locationCode + ",8";
		// }
		// if (mbnrFlag) {
		// Constants.locationCode = Constants.locationCode + ",9";
		// }
		// if (rangareddyFlag) {
		// Constants.locationCode = Constants.locationCode + ",10";
		// }
		// Constants.locationCode = Constants.locationCode.replaceFirst(",",
		// "");
		//
		// // System.out.println("search code " + Constants.searchCode);
		//
		//
		// // mDrawerLayout.closeDrawer(Gravity.RIGHT);
		//
		// Toast.makeText(getApplicationContext(), Constants.locationCode,
		// 200).show();
		//
		// // if (utility.IsNetConnected(SubFragmentActivity.this)) {
		// // new FilterMenuAync().execute();
		// // } else {
		// // utility.showAlertNoInternetService(SubFragmentActivity.this);
		// // }
		//
		// // http: //
		// // 172.16.0.49/telangana_tourism/WebServices/getFilters/1/1,2
		//
		// break;

		default:
			break;
		}

		if (fragment != null) {
			if (tag.equalsIgnoreCase(getCurrentFragment())) {
				// if(getCurrentFragment()){
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment, tag).commit();
			} else {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment, tag)
						.addToBackStack(tag).commit();
			}

			mDrawerLayout.closeDrawer(mDrawerList2);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	private String getCurrentFragment() {
		FragmentManager fragmentManager = getFragmentManager();
		String fragmentTag = fragmentManager.getBackStackEntryAt(
				fragmentManager.getBackStackEntryCount() - 1).getName();
		Fragment currentFragment = getFragmentManager().findFragmentByTag(
				fragmentTag);
		System.out.println("current fragment tag " + fragmentTag);
		return fragmentTag;
	}

	class TrackingServiceTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+Constants.Base_url
					+ "logUserTrack/"
					+ SplashScreen.pref.getString(SplashScreen.Key_GET_USER_ID,
							"")
					+ "/"
					+ SplashScreen.strIMEINo
					+ "/"
					+ Constants.latitude
					+ "/"
					+ Constants.longitude
					+ "/"
					+ SplashScreen.pref.getString(
							SplashScreen.Key_GET_USER_Session, ""));
			return ServiceCalls1.getJSONString(Constants.Base_url
					+ "logUserTrack/"
					+ SplashScreen.pref.getString(SplashScreen.Key_GET_USER_ID,
							"")
					+ "/"
					+ SplashScreen.strIMEINo
					+ "/"
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

			if (null == result || result.length() == 0) {
				Toast.makeText(MainActivity.this,
						"No data found from Server!!!", Toast.LENGTH_LONG)
						.show();

			} else {

				try {
					try {
						JSONObject jsonObject = new JSONObject(result);

						SplashScreen.editor.putString(
								SplashScreen.Key_GET_USER_Session,
								jsonObject.optString("session"));
						SplashScreen.editor
								.putString(
										SplashScreen.Key_GET_USER_Track,
										""
												+ SplashScreen.pref
														.getString(
																SplashScreen.Key_GET_USER_Track,
																""));
						SplashScreen.editor.commit();

						startService(new Intent(
								MainActivity.this,
								com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));

						// Toast.makeText(getApplicationContext(), "session"
						// +SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Session,
						// ""), Toast.LENGTH_SHORT).show();

					} catch (Exception e) {
						// TODO: handle exception
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	// private class FilterLocMenuAync extends AsyncTask<String, Void, String> {
	// private ProgressDialog pDialog;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// pDialog = new ProgressDialog(MainActivity.this);
	// pDialog.setMessage("Loading please wait...");
	// pDialog.setIndeterminate(false);
	// pDialog.setCancelable(true);
	// pDialog.show();
	//
	// Log.e("tag",
	// "http://172.16.0.49/telangana_tourism/WebServices/getFilters/1/"
	// + searchLocCode);
	// }
	//
	// @Override
	// protected String doInBackground(String... args) {
	//
	// return ServiceCalls1
	// .getJSONString("http://172.16.0.49/telangana_tourism/WebServices/getFilters/1/"
	// + searchLocCode);
	// }
	//
	// @Override
	// protected void onPostExecute(String jsonResult) {
	//
	// searchJsonString = jsonResult;
	// System.out.println(" result " + jsonResult);
	//
	// DestinationFragment fragment = new DestinationFragment();
	// if (fragment != null) {
	// FragmentManager fragmentManager = getFragmentManager();
	// fragmentManager.beginTransaction()
	// .replace(R.id.frame_container, fragment).commit();
	// // update selected item and title, then close the drawer
	// // mDrawerList.setItemChecked(position, true);
	// // mDrawerList.setSelection(position);
	// // setTitle(navMenuTitles[position]);
	// } else {
	// // error in creating fragment
	// Log.e("MainActivity", "Error in creating fragment");
	// }
	// if (pDialog.isShowing() && pDialog != null) {
	// pDialog.dismiss();
	// }
	// }
	// }

	// @Override
	// public void setTitle(CharSequence title) {
	// mTitle = title;\
	// getActionBar().setTitle(mTitle);
	// }

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() >= 0) {
	// onBackPressed();
	// }
	// return super.onKeyDown(keyCode, event);
	// }

	@Override
	public void onBackPressed() {

		// FragmentManager fm = getFragmentManager();
		// for (int entry = 0; entry < fm.getBackStackEntryCount(); entry++) {
		// String ide = fm.getBackStackEntryAt(entry).getName();
		// Log.i("TAG", "Found fragment: " + ide);
		// }
		FragmentManager fragmentManager = this.getFragmentManager();
		System.out
				.println(" count " + fragmentManager.getBackStackEntryCount());

		backStackCount = fragmentManager.getBackStackEntryCount();
		if (backStackCount <= 1) {
			Log.v(" home ", "" + fragmentManager.getBackStackEntryCount());
			// if (TextUtils.isEmpty(getCurrentFragment())) {
			// fragmentManager
			// .beginTransaction()
			// .replace(R.id.frame_container, new HomeFragment(),
			// "Home").addToBackStack("Home").commit();
			// } else if (getCurrentFragment().equalsIgnoreCase("Home")) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(
					MainActivity.this);
			dialog.setMessage("Are you sure do you want to exit. ?");
			dialog.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface paramDialogInterface,
								int paramInt) {
							// TODO Auto-generated method stub
							
							
							
							
							SplashScreen.editor.putString(
									SplashScreen.Key_GET_USER_Session, "");
							SplashScreen.editor.putString(
									SplashScreen.Key_GET_USER_Track, "false");
							SplashScreen.editor.commit();
							
							SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Track, "");

							getApplicationContext()
									.stopService(
											new Intent(
													getApplicationContext(),
													com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));
							
//							System.exit(0);
							
//							if(SplashScreen.exitAppFlag.equalsIgnoreCase("2")){
//								MainActivity.this.finish();
//								moveTaskToBack(true);
//							}else{
								System.exit(0);
//							}
							
//							System.exit(0);
							
							// finish();
						}
					});

			dialog.setNegativeButton("No",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int paramInt) {
							dialog.dismiss();
							// TODO Auto-generated method stub
						}
					});
			AlertDialog alert = dialog.create();
			alert.show();
			// } else {
			// getFragmentManager().popBackStack();
			// fragmentManager.beginTransaction()
			// .replace(R.id.frame_container, new HomeFragment(), tag)
			// .addToBackStack("Home").commit();
			// }
		} else {

			getFragmentManager().popBackStack();
			String tag = "";
			tag = fragmentManager.getBackStackEntryAt(
					fragmentManager.getBackStackEntryCount() - 2).getName();
			Log.d("This is your Top Fragment name: ", "" + tag);

			if (tag.equalsIgnoreCase(getResources().getString(
					R.string.destination_sidemenu))) {
				getDestinationFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.reservation_sidemenu))) {
				getMyreservationsFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.accomodation_sidemenu))) {
				getAccomadationFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.events_sidemenu))) {
				getEventsFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.shopping_sidemenu))) {
				getShopFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.packages_sidemenu))) {
				getPackagesFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.culture_sidemenu))) {
				getCultureFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.favorites_sidemenu))) {
				getFavroutiesFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.weather_sidemenu))) {
				getWeatherFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.settings_sidemenu))) {
				getSettingsFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.contactus_sidemenu))) {
				getContactUsFragment();
			} else if (tag.equalsIgnoreCase(getResources().getString(
					R.string.emergency_sidemenu))) {
				getEmergencyFragment();
			} else if (tag.equalsIgnoreCase("Home")) {

				backStackCount = fragmentManager.getBackStackEntryCount();
				for (int i = 0; i < backStackCount; i++) {

					// Get the back stack fragment id.
					int backStackId = fragmentManager.getBackStackEntryAt(i)
							.getId();

					fragmentManager.popBackStack(backStackId,
							FragmentManager.POP_BACK_STACK_INCLUSIVE);

				} /* end of for */
				HomeFragment fragment = new HomeFragment();

				if (fragment != null) {
					// FragmentManager fragmentManager = getFragmentManager();
					// fragmentManager.beginTransaction()
					// .replace(R.id.frame_container, fragment).commit();

					final FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					ft.replace(R.id.frame_container, fragment, getResources()
							.getString(R.string.home_sidemenu));
					ft.addToBackStack(
							getResources().getString(R.string.home_sidemenu))
							.commit();
					// update selected item and title, then close the drawer
					// mDrawerList.setItemChecked(position, true);
					// mDrawerList.setSelection(position);
					// setTitle(navMenuTitles[position]);
					// mDrawerLayout.closeDrawer(mDrawerList2);
				} else {
					// error in creating fragment
					Log.e("MainActivity", "Error in creating fragment");
				}
				// fragmentsCount = 0;

			} else if (tag.equalsIgnoreCase("होम")) {

				backStackCount = fragmentManager.getBackStackEntryCount();
				for (int i = 0; i < backStackCount; i++) {

					// Get the back stack fragment id.
					int backStackId = fragmentManager.getBackStackEntryAt(i)
							.getId();

					fragmentManager.popBackStack(backStackId,
							FragmentManager.POP_BACK_STACK_INCLUSIVE);

				} /* end of for */
				HomeFragment fragment = new HomeFragment();

				if (fragment != null) {
					// FragmentManager fragmentManager = getFragmentManager();
					// fragmentManager.beginTransaction()
					// .replace(R.id.frame_container, fragment).commit();

					final FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					ft.replace(R.id.frame_container, fragment, getResources()
							.getString(R.string.home_sidemenu));
					ft.addToBackStack(
							getResources().getString(R.string.home_sidemenu))
							.commit();
					// update selected item and title, then close the drawer
					// mDrawerList.setItemChecked(position, true);
					// mDrawerList.setSelection(position);
					// setTitle(navMenuTitles[position]);
					// mDrawerLayout.closeDrawer(mDrawerList2);
				} else {
					// error in creating fragment
					Log.e("MainActivity", "Error in creating fragment");
				}

			} else if (tag.equalsIgnoreCase("హోమ్")) {

				backStackCount = fragmentManager.getBackStackEntryCount();
				for (int i = 0; i < backStackCount; i++) {

					// Get the back stack fragment id.
					int backStackId = fragmentManager.getBackStackEntryAt(i)
							.getId();

					fragmentManager.popBackStack(backStackId,
							FragmentManager.POP_BACK_STACK_INCLUSIVE);

				} /* end of for */

				HomeFragment fragment = new HomeFragment();

				if (fragment != null) {
					// FragmentManager fragmentManager = getFragmentManager();
					// fragmentManager.beginTransaction()
					// .replace(R.id.frame_container, fragment).commit();

					final FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					ft.replace(R.id.frame_container, fragment, getResources()
							.getString(R.string.home_sidemenu));
					ft.addToBackStack(
							getResources().getString(R.string.home_sidemenu))
							.commit();
					// update selected item and title, then close the drawer
					// mDrawerList.setItemChecked(position, true);
					// mDrawerList.setSelection(position);
					// setTitle(navMenuTitles[position]);
					// mDrawerLayout.closeDrawer(mDrawerList2);
				} else {
					// error in creating fragment
					Log.e("MainActivity", "Error in creating fragment");
				}
			} else if (tag.equalsIgnoreCase("ہوم")) {

				backStackCount = fragmentManager.getBackStackEntryCount();
				for (int i = 0; i < backStackCount; i++) {

					// Get the back stack fragment id.
					int backStackId = fragmentManager.getBackStackEntryAt(i)
							.getId();

					fragmentManager.popBackStack(backStackId,
							FragmentManager.POP_BACK_STACK_INCLUSIVE);

				} /* end of for */

				HomeFragment fragment = new HomeFragment();

				if (fragment != null) {
					// FragmentManager fragmentManager = getFragmentManager();
					// fragmentManager.beginTransaction()
					// .replace(R.id.frame_container, fragment).commit();

					final FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					ft.replace(R.id.frame_container, fragment, getResources()
							.getString(R.string.home_sidemenu));
					ft.addToBackStack(
							getResources().getString(R.string.home_sidemenu))
							.commit();
					// update selected item and title, then close the drawer
					// mDrawerList.setItemChecked(position, true);
					// mDrawerList.setSelection(position);
					// setTitle(navMenuTitles[position]);
					// mDrawerLayout.closeDrawer(mDrawerList2);
				} else {
					// error in creating fragment
					Log.e("MainActivity", "Error in creating fragment");
				}
			}
		}
		return;
	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event)
	// {
	// if (keyCode == KeyEvent.KEYCODE_BACK)
	// {
	// if (getFragmentManager().getBackStackEntryCount() == 0)
	// {
	// this.finish();
	// return false;
	// }
	// else
	// {
	// getFragmentManager().popBackStack();
	// removeCurrentFragment();
	//
	// return false;
	// }
	//
	//
	//
	// }
	//
	// return super.onKeyDown(keyCode, event);
	// }
	//
	//
	// public void removeCurrentFragment()
	// {
	// FragmentTransaction transaction =
	// getFragmentManager().beginTransaction();
	//
	// Fragment currentFrag =
	// getFragmentManager().findFragmentById(R.id.frame_container);
	//
	//
	// String fragName = "NONE";
	//
	// if (currentFrag!=null)
	// fragName = currentFrag.getClass().getSimpleName();
	//
	//
	// if (currentFrag != null)
	// transaction.remove(currentFrag);
	//
	// transaction.commit();
	//
	// }

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(MainActivity.this, "6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(MainActivity.this);
	}

}
