package com.telanganatourism.android.phase2;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.flurry.android.FlurryAgent;
import com.telanganatourism.android.phase2.adapter.NavDrawerListAdapter;
import com.telanganatourism.android.phase2.model.NavDrawerItem;
import com.telanganatourism.android.phase2.util.Utility;

public class SubFragmentActivity extends Activity implements
		SearchView.OnQueryTextListener, OnClickListener {

	private SearchView mSearchView;
	int navFlag;
	private DrawerLayout mDrawerLayout;
	// private ListView mDrawerList;
	// static LinearLayout mDrawerList1;
	private ActionBarDrawerToggle mDrawerToggle;
	Boolean flg = false, flg1 = false;
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	// private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons, navMenuIcons1;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private ArrayList<NavDrawerItem> navDrawerItems1;
	private NavDrawerListAdapter adapter, adapter1;

	static String[] stay = { "Category", "Location", "Distance" };

	public static String changeColorStr = "#ffffff", changeTitle;

	public static TextView txt_logo, txt_logo1, txt_title, txt_filter,
			txt_search, txt_location, txt_back_search, txt_Edit,
			selectLocation, divinetxt, heritagetxt, naturetxt, wildtxt,
			journeytxt, boatingtxt, hydTxt, khammTxt, mbnrTxt, nalgTxt,
			warngalTxt, adilabadTxt, nizamabadTxt, karimnagarTxt, medakTxt,
			rangareddyTxt;

	public static Button btn_menu, searchBtn, submitBtn;

	public static RelativeLayout headerLayout, mDrawerList2;

	public static LinearLayout searchLayout, categoryLayout, locationLayout;

	ToggleButton toggleButton;

	boolean divineFlag = false, heritageFlag = false, natureFlag = false,
			wildFlag = false, journeyFlag = false, boatingFlag = false;
	public static String searchJsonString;
	ListView dialog_ListView;

	public static AutoCompleteTextView searchEdtTxt;

	public static Button menuBtn;

//	Utility utility;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Constants.selectLanguage.equalsIgnoreCase("3")) {
			setContentView(R.layout.arabic_activity_main);
		} else {
			setContentView(R.layout.activity_main);
		}

//		utility = new Utility(SubFragmentActivity.this);

		toggleButton = (ToggleButton) findViewById(R.id.togglebutton);
		initLayout();

		if ((SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Track, ""))
				.equalsIgnoreCase("true")) {
			toggleButton.setChecked(true);
		} else {
			toggleButton.setChecked(false);
		}

//		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(final CompoundButton toggleButton,
//					boolean isChecked) {
//
//				if (isChecked) {
//
//					// custom dialog
//					final Dialog dialog = new Dialog(SubFragmentActivity.this);
//					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//					dialog.setContentView(R.layout.dialog);
//
//					// set the custom dialog components - text, image and button
//
//					Button yesBtn = (Button) dialog.findViewById(R.id.btn_yes);
//					// if button is clicked, close the custom dialog
//					yesBtn.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							dialog.dismiss();
//							SplashScreen.editor.putString(
//									SplashScreen.Key_GET_USER_Track, "true");
//							SplashScreen.editor.commit();
//							new TrackingServiceTask().execute();
//						}
//					});
//
//					Button noBtn = (Button) dialog.findViewById(R.id.btn_no);
//					// if button is clicked, close the custom dialog
//					noBtn.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							dialog.dismiss();
//							toggleButton.setChecked(false);
//							SplashScreen.editor.putString(
//									SplashScreen.Key_GET_USER_Session, "");
//							SplashScreen.editor.putString(
//									SplashScreen.Key_GET_USER_Track, "false");
//							SplashScreen.editor.commit();
//
//							stopService(new Intent(
//									SubFragmentActivity.this,
//									com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));
//						}
//					});
//
//					dialog.show();
//				} else {
//
//					// String strUserFlag =
//					// SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Track,
//					// "");
//					// if(strUserFlag.equalsIgnoreCase("true"))
//					// {
//					//
//					// }
//					SplashScreen.editor.putString(
//							SplashScreen.Key_GET_USER_Session, "");
//					SplashScreen.editor.putString(
//							SplashScreen.Key_GET_USER_Track, "false");
//					SplashScreen.editor.commit();
//
//					stopService(new Intent(
//							SubFragmentActivity.this,
//							com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));
//				}
//			}
//		});
		
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

					stopService(new Intent(
							SubFragmentActivity.this,
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
					
					if (Utility.checkInternetConnection(SubFragmentActivity.this)) {
						new SettingsTaskTask().execute();
					} else {
						Utility.showAlertNoInternetService(SubFragmentActivity.this);
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

		searchEdtTxt = (AutoCompleteTextView) mCustomView
				.findViewById(R.id.editText1);

		txt_logo = (TextView) mCustomView.findViewById(R.id.txt_logo);

		txt_logo1 = (TextView) mCustomView.findViewById(R.id.txt_logo1);

		txt_title = (TextView) mCustomView.findViewById(R.id.txt_title);
		
		txt_title.setTypeface(Constants.ProximaNova_Regular);

		txt_filter = (TextView) mCustomView.findViewById(R.id.filterBtn);

		txt_filter.setVisibility(View.GONE);

		txt_Edit = (TextView) mCustomView.findViewById(R.id.editBtn);

		txt_Edit.setVisibility(View.GONE);

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
				txt_filter.setVisibility(View.VISIBLE);
				txt_location.setVisibility(View.GONE);
				btn_menu.setVisibility(View.VISIBLE);
			}
		});

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
			displayView(0);
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

	private void initLayout() {
		// TODO Auto-generated method stub
		Button homeBtn = (Button) findViewById(R.id.btn_home);
		Button destinationBtn = (Button) findViewById(R.id.btn_destination);
		Button accomodatonBtn = (Button) findViewById(R.id.btn_accommodation);
		Button eventsBtn = (Button) findViewById(R.id.btn_events);
		Button shopBtn = (Button) findViewById(R.id.btn_shopping);
		Button packagesBtn = (Button) findViewById(R.id.btn_packages);
		Button cultureBtn = (Button) findViewById(R.id.btn_culture);
		Button favroitesBtn = (Button) findViewById(R.id.btn_favroites);
		Button weatherBtn = (Button) findViewById(R.id.btn_weather);
		Button settingBtn = (Button) findViewById(R.id.btn_settings);
		Button contactBtn = (Button) findViewById(R.id.btn_contact);
		Button emergencyBtn = (Button) findViewById(R.id.btn_emergency);
		Button feedbackBtn = (Button) findViewById(R.id.btn_feedback);
		Button trackingTxt = (Button) findViewById(R.id.btn_tracking);

		TextView networkTxt = (TextView) findViewById(R.id.networkTxt);
		TextView networkTxt1 = (TextView) findViewById(R.id.networkTxt1);
		TextView networkTxt2 = (TextView) findViewById(R.id.networkTxt2);

		if (Utility.checkInternetConnection(SubFragmentActivity.this)) {
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

		hydTxt = (TextView) findViewById(R.id.hydTxt);
		khammTxt = (TextView) findViewById(R.id.khammamTxt);
		mbnrTxt = (TextView) findViewById(R.id.mbnrTxt);
		nalgTxt = (TextView) findViewById(R.id.nalgondaTxt);
		warngalTxt = (TextView) findViewById(R.id.warangalTxt);
		adilabadTxt = (TextView) findViewById(R.id.adilabadTxt);
		nizamabadTxt = (TextView) findViewById(R.id.nizambadTxt);
		karimnagarTxt = (TextView) findViewById(R.id.karimnagrTxt);
		medakTxt = (TextView) findViewById(R.id.medakTxt);
		rangareddyTxt = (TextView) findViewById(R.id.rangareddyTxt);

		searchBtn = (Button) findViewById(R.id.searchBtn);
		submitBtn = (Button) findViewById(R.id.submitBtn);
		
		Button fakeButton = (Button)findViewById(R.id.button2);

		categoryLayout.setVisibility(View.VISIBLE);
		locationLayout.setVisibility(View.GONE);
		searchBtn.setVisibility(View.VISIBLE);
		submitBtn.setVisibility(View.GONE);
		fakeButton.setVisibility(View.VISIBLE);

		homeBtn.setTypeface(Constants.ProximaNova_Regular);
		destinationBtn.setTypeface(Constants.ProximaNova_Regular);
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
		selectLocation.setTypeface(Constants.ProximaNova_Regular);

		divinetxt.setTypeface(Constants.ProximaNova_Regular);
		heritagetxt.setTypeface(Constants.ProximaNova_Regular);
		naturetxt.setTypeface(Constants.ProximaNova_Regular);
		wildtxt.setTypeface(Constants.ProximaNova_Regular);
		journeytxt.setTypeface(Constants.ProximaNova_Regular);
		boatingtxt.setTypeface(Constants.ProximaNova_Regular);

		hydTxt.setTypeface(Constants.ProximaNova_Regular);
		khammTxt.setTypeface(Constants.ProximaNova_Regular);
		mbnrTxt.setTypeface(Constants.ProximaNova_Regular);
		nalgTxt.setTypeface(Constants.ProximaNova_Regular);
		warngalTxt.setTypeface(Constants.ProximaNova_Regular);
		adilabadTxt.setTypeface(Constants.ProximaNova_Regular);
		nizamabadTxt.setTypeface(Constants.ProximaNova_Regular);
		karimnagarTxt.setTypeface(Constants.ProximaNova_Regular);
		medakTxt.setTypeface(Constants.ProximaNova_Regular);
		rangareddyTxt.setTypeface(Constants.ProximaNova_Regular);

		homeBtn.setOnClickListener(this);
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
		emergencyBtn.setOnClickListener(this);
		feedbackBtn.setOnClickListener(this);
		selectLocation.setOnClickListener(this);

		searchBtn.setOnClickListener(this);

		divinetxt.setOnClickListener(this);
		heritagetxt.setOnClickListener(this);
		naturetxt.setOnClickListener(this);
		wildtxt.setOnClickListener(this);
		journeytxt.setOnClickListener(this);
		boatingtxt.setOnClickListener(this);

		hydTxt.setOnClickListener(this);
		khammTxt.setOnClickListener(this);
		mbnrTxt.setOnClickListener(this);
		nalgTxt.setOnClickListener(this);
		warngalTxt.setOnClickListener(this);
		adilabadTxt.setOnClickListener(this);
		nizamabadTxt.setOnClickListener(this);
		karimnagarTxt.setOnClickListener(this);
		medakTxt.setOnClickListener(this);
		rangareddyTxt.setOnClickListener(this);
		
		fakeButton.setOnClickListener(this);

		if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID, "")
				.equalsIgnoreCase("3")) {
			homeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			destinationBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			accomodatonBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			eventsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			shopBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			cultureBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			favroitesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			weatherBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			settingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			contactBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			emergencyBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			feedbackBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			trackingTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

			divinetxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			heritagetxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			naturetxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			wildtxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			journeytxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			boatingtxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			selectLocation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")) {

			homeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			destinationBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			accomodatonBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			eventsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			shopBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			cultureBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			favroitesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			weatherBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			settingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			contactBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			emergencyBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			feedbackBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			trackingTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

			divinetxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			heritagetxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			naturetxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			wildtxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			journeytxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			boatingtxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			selectLocation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {

			homeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			destinationBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			accomodatonBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			eventsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			shopBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			cultureBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			favroitesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			weatherBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			settingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			contactBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			emergencyBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			feedbackBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			trackingTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

			divinetxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			heritagetxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			naturetxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			wildtxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			journeytxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			boatingtxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			selectLocation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		}else{
			
		}
	}

	static TextView[] txtName, txtLine;

	// public static void getRightMenuList(Context context, String[] stay) {
	//
	// LayoutParams linLayoutParam = new LayoutParams(
	// LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	//
	// LayoutParams linLayoutParam_line = new LayoutParams(
	// LayoutParams.MATCH_PARENT, 1);
	//
	// // mDrawerList1.removeAllViews();
	// txtName = new TextView[stay.length];
	// txtLine = new TextView[stay.length];
	//
	// for (int j = 0; j < stay.length; j++) {
	//
	// txtName[j] = new TextView(context);
	// txtName[j].setLayoutParams(linLayoutParam);
	// txtName[j].setText(stay[j]);
	// txtName[j].setTextSize(18);
	// txtName[j].setPadding(10, 10, 10, 10);
	// txtName[j].setGravity(Gravity.CENTER_VERTICAL);
	// // mDrawerList1.addView(txtName[j]);
	//
	// txtLine[j] = new TextView(context);
	// txtLine[j].setLayoutParams(linLayoutParam_line);
	// txtLine[j].setBackgroundColor(Color.BLACK);
	// // mDrawerList1.addView(txtLine[j]);
	// }
	// }

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
			fragment = new SubListFragment();
			break;
		// case 1:
		// SubFragmentActivity.changeTitle = "Destination";
		// SubFragmentActivity.changeColorStr = "#2a9595";
		// SettingsActivity.cdt_id = 0;
		// fragment = new DestinationFragment();
		// break;
		// case 2:
		// SettingsActivity.cdt_id = 1;
		// SubFragmentActivity.changeColorStr = "#e6912d";
		//
		// SubFragmentActivity.changeTitle = "Accomodation";
		// fragment = new WhereToShopFragment();
		// break;
		// case 3:
		// SettingsActivity.cdt_id = 2;
		// SubFragmentActivity.changeColorStr = "#af4141";
		//
		// SubFragmentActivity.changeTitle = "Events";
		// fragment = new WhereToShopFragment();
		// break;
		// case 4:
		// // SettingsActivity.cdt_id = 3;
		// // fragment = new WhereToShopFragment();
		// break;
		// case 5:
		// SettingsActivity.cdt_id = 3;
		// SubFragmentActivity.changeColorStr = "#9e5dd0";
		//
		// SubFragmentActivity.changeTitle = "Tour Packages";
		// fragment = new PackagesFragment();
		// break;
		//
		// case 6:
		// SettingsActivity.cdt_id = 4;
		// SubFragmentActivity.changeColorStr = "#79af52";
		//
		// SubFragmentActivity.changeTitle = "Culture";
		// fragment = new WhereToShopFragment();
		// break;
		// case 7:
		// fragment = new WhereToShopFragment();
		// break;
		// case 8:
		// fragment = new WeatherFragment();
		// break;
		//
		// case 10:
		// fragment = new ContactUsFragment();
		// break;
		// case 11:
		// fragment = new EmergencyFragment();
		// break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Fragment fragment = null;

		switch (v.getId()) {

		case R.id.btn_home:
			// fragment = new HomeFragment();
			// startActivity(new Intent(SubFragmentActivity.this,
			// MainActivity.class));
			// finish();

			SplashScreen.slide_menu_id = 0;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_destination:
			/*
			 * SubFragmentActivity.changeTitle = "Destination";
			 * SubFragmentActivity.changeColorStr = "#2a9595";
			 * SettingsActivity.cdt_id = 0; fragment = new
			 * DestinationFragment();
			 */
			SplashScreen.slide_menu_id = 1;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_accommodation:
			// SettingsActivity.cdt_id = 1;
			// SubFragmentActivity.changeColorStr = "#e6912d";
			//
			// SubFragmentActivity.changeTitle = "Accomodation";
			// fragment = new WhereToShopFragment();
			Constants.sublistFlag = 3;
			SplashScreen.slide_menu_id = 2;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_events:
			// SettingsActivity.cdt_id = 2;
			// SubFragmentActivity.changeColorStr = "#af4141";
			//
			// SubFragmentActivity.changeTitle = "Events";
			// fragment = new WhereToShopFragment();
			Constants.sublistFlag = 4;
			SplashScreen.slide_menu_id = 3;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_packages:
			// SettingsActivity.cdt_id = 3;
			// SubFragmentActivity.changeColorStr = "#9e5dd0";
			//
			// SubFragmentActivity.changeTitle = "Tour Packages";
			// fragment = new PackagesFragment();

			SplashScreen.slide_menu_id = 5;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();

			break;

		case R.id.btn_culture:
			// SettingsActivity.cdt_id = 4;
			// SubFragmentActivity.changeColorStr = "#79af52";
			//
			// SubFragmentActivity.changeTitle = "Culture";
			// fragment = new WhereToShopFragment();
			Constants.sublistFlag = 5;
			SplashScreen.slide_menu_id = 6;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_favroites:
			// fragment = new WhereToShopFragment();
			MainActivity.changeColorStr = "#ffffff";

			MainActivity.changeTitle = getResources().getString(
					R.string.favorites_sidemenu);
			SplashScreen.slide_menu_id = 7;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_weather:
			// fragment = new WeatherFragment();
			SplashScreen.slide_menu_id = 8;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_settings:
			SubFragmentActivity.changeColorStr = "#ffffff";

			SubFragmentActivity.changeTitle = getResources().getString(
					R.string.settings_sidemenu);
			// fragment = new SettingsFragment();
			SplashScreen.slide_menu_id = 4;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_contact:
			// fragment = new ContactUsFragment();
			SplashScreen.slide_menu_id = 10;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_shopping:
			// fragment = new ContactUsFragment();
			SplashScreen.slide_menu_id = 12;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_emergency:
			// fragment = new EmergencyFragment();
			SplashScreen.slide_menu_id = 11;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.btn_feedback:
			SplashScreen.slide_menu_id = 9;
			startActivity(new Intent(SubFragmentActivity.this,
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;

		case R.id.selectLocation_txt:

			final Dialog dialog = new Dialog(SubFragmentActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.locationslist);

			// Prepare ListView in dialog
			dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					SubFragmentActivity.this, R.layout.custom_row_list,
					MainActivity.whrdo);

			dialog_ListView.setAdapter(adapter);

			dialog_ListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					selectLocation.setText(MainActivity.whrdo[arg2]);
					dialog.dismiss();
				}
			});

			dialog.show();
			break;

		case R.id.divineDestionationTxt:

//			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//				if (divineFlag) {
//					divineFlag = false;
//					divinetxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_unselect), null, null,
//							null);
//				} else {
//					divineFlag = true;
//					divinetxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_selected), null, null,
//							null);
//				}
//			} else {
				if (divineFlag) {
					divineFlag = false;
					divinetxt.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.check_unselect),
							null);
				} else {
					divineFlag = true;
					divinetxt.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.check_selected),
							null);
				}
//			}

			break;

		case R.id.heritageTxt:

//			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//				if (heritageFlag) {
//					heritageFlag = false;
//					heritagetxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_unselect), null, null,
//							null);
//				} else {
//					heritageFlag = true;
//					heritagetxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_selected), null, null,
//							null);
//				}
//			} else {
				if (heritageFlag) {
					heritageFlag = false;
					heritagetxt.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.check_unselect),
							null);
				} else {
					heritageFlag = true;
					heritagetxt.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.check_selected),
							null);
				}
//			}

			break;

		case R.id.natureTxt:

//			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//				if (natureFlag) {
//					natureFlag = false;
//					naturetxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_unselect), null, null,
//							null);
//				} else {
//					natureFlag = true;
//					naturetxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_selected), null, null,
//							null);
//				}
//			} else {
				if (natureFlag) {
					natureFlag = false;
					naturetxt.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.check_unselect),
							null);
				} else {
					natureFlag = true;
					naturetxt.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.check_selected),
							null);
				}
//			}

			break;

		case R.id.wildlifeTxt:

//			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//				if (wildFlag) {
//					wildFlag = false;
//					wildtxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_unselect), null, null,
//							null);
//				} else {
//					wildFlag = true;
//					wildtxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_selected), null, null,
//							null);
//				}
//			} else {
				if (wildFlag) {
					wildFlag = false;
					wildtxt.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							getApplicationContext().getResources().getDrawable(
									R.drawable.check_unselect), null);
				} else {
					wildFlag = true;
					wildtxt.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							getApplicationContext().getResources().getDrawable(
									R.drawable.check_selected), null);
				}
//			}

			break;

		case R.id.journeyTxt:

//			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//				if (journeyFlag) {
//					journeyFlag = false;
//					journeytxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_unselect), null, null,
//							null);
//				} else {
//					journeyFlag = true;
//					journeytxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_selected), null, null,
//							null);
//				}
//			} else {
				if (journeyFlag) {
					journeyFlag = false;
					journeytxt.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.check_unselect),
							null);
				} else {
					journeyFlag = true;
					journeytxt.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.check_selected),
							null);
				}
//			}

			break;

		case R.id.boatingTxt:

//			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//				if (boatingFlag) {
//					boatingFlag = false;
//					boatingtxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_unselect), null, null,
//							null);
//				} else {
//					boatingFlag = true;
//					boatingtxt.setCompoundDrawablesWithIntrinsicBounds(
//							getApplicationContext().getResources().getDrawable(
//									R.drawable.check_selected), null, null,
//							null);
//				}
//			} else {
				if (boatingFlag) {
					boatingFlag = false;
					boatingtxt.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.check_unselect),
							null);
				} else {
					boatingFlag = true;
					boatingtxt.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.check_selected),
							null);
				}
//			}

			break;
			
		case R.id.button2:

			break;

		case R.id.searchBtn:

			Constants.searchCode = "";

			if (divineFlag) {
				Constants.searchCode = Constants.searchCode + ",1";
			}
			if (heritageFlag) {
				Constants.searchCode = Constants.searchCode + ",2";
			}
			if (natureFlag) {
				Constants.searchCode = Constants.searchCode + ",3";
			}
			if (wildFlag) {
				Constants.searchCode = Constants.searchCode + ",4";
			}
			if (journeyFlag) {
				Constants.searchCode = Constants.searchCode + ",5";
			}
			if (boatingFlag) {
				Constants.searchCode = Constants.searchCode + ",6";
			}
			Constants.searchCode = Constants.searchCode.replaceFirst(",", "");

			System.out.println("search code " + Constants.searchCode);

			mDrawerLayout.closeDrawer(Gravity.RIGHT);

			if (Utility.checkInternetConnection(SubFragmentActivity.this)) {
				new FilterMenuAync().execute();
			} else {
				Utility.showAlertNoInternetService(SubFragmentActivity.this);
			}

			// http: //
			// 172.16.0.49/telangana_tourism/WebServices/getFilters/1/1,2

			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
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
	
	class SettingsTaskTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getUserEmergencyContacts/"
					+ MainActivity.pref.getString(MainActivity.Key_GET_USER_ID, ""));
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getUserEmergencyContacts/"
					+ MainActivity.pref.getString(MainActivity.Key_GET_USER_ID, ""));
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
									SubFragmentActivity.this);
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
													.addToBackStack(MainActivity.tag).commit();
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
							final Dialog dialog = new Dialog(SubFragmentActivity.this);
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
									
									if (Utility.checkInternetConnection(SubFragmentActivity.this)) {
										new TrackingServiceTask().execute();
									} else {
										Utility.showAlertNoInternetService(SubFragmentActivity.this);
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

									stopService(new Intent(
											SubFragmentActivity.this,
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
				Toast.makeText(SubFragmentActivity.this,
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
								SubFragmentActivity.this,
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

	private class FilterMenuAync extends AsyncTask<String, Void, String> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SubFragmentActivity.this);
			pDialog.setMessage("Loading please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

			// Log.e("tag",
			// "http://172.16.0.49/telangana_tourism/WebServices/getFilters/"+Constants.locationId+"/"
			// + Constants.searchCode);
		}

		@Override
		protected String doInBackground(String... args) {
			System.out.println("url is"+Constants.Base_url
					+ "getFilters/" + Constants.locationCode + "/"
					+ Constants.searchCode + "/" + Constants.selectLanguage);

			return ServiceCalls1.getJSONString(Constants.Base_url
					+ "getFilters/" + Constants.locationCode + "/"
					+ Constants.searchCode + "/" + Constants.selectLanguage);
		}

		@Override
		protected void onPostExecute(String jsonResult) {

			searchJsonString = jsonResult;
			System.out.println(" result " + jsonResult);

			SubListFragment fragment = new SubListFragment();
			if (fragment != null) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).commit();
				// update selected item and title, then close the drawer
				// mDrawerList.setItemChecked(position, true);
				// mDrawerList.setSelection(position);
				// setTitle(navMenuTitles[position]);
			} else {
				// error in creating fragment
				Log.e("MainActivity", "Error in creating fragment");
			}
			if (pDialog.isShowing() && pDialog != null) {
				pDialog.dismiss();
			}
		}
	}

	// @Override
	// public void setTitle(CharSequence title) {
	// mTitle = title;
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() >= 0) {
			onBackPressed();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		// Fragment fragment = null;
		// fragment = new HomeFragment();
		// if (fragment != null) {
		// FragmentManager fragmentManager = getFragmentManager();
		// fragmentManager.beginTransaction()
		// .replace(R.id.frame_container, fragment).commit();
		// // update selected item and title, then close the drawer
		// // mDrawerList.setItemChecked(position, true);
		// // mDrawerList.setSelection(position);
		// // setTitle(navMenuTitles[position]);
		// // mDrawerLayout.closeDrawer(mDrawerList2);
		// } else {
		// // error in creating fragment
		// Log.e("MainActivity", "Error in creating fragment");
		// }
		SubFragmentActivity.this.finish();
		return;
	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(SubFragmentActivity.this,
				"6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(SubFragmentActivity.this);
	}
}
