package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.flurry.android.FlurryAgent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.telanganatourism.android.phase2.database.helper.TtHelper;
import com.telanganatourism.android.phase2.util.Utility;

//import android.widget.Toast;

@SuppressWarnings("deprecation")
@SuppressLint("SetJavaScriptEnabled")
public class DetailScreen1 extends Activity implements
		SearchView.OnQueryTextListener {

	boolean favFlag = false;
	TtHelper dbbHelper;
	Cursor constantCursor = null;
	Utility utils;
	String image_path;
	public static String tit, abt, lat, lng, id, address, dis;
	JSONObject res;

	private SearchView mSearchView;
	Button aboutBtn, aboutBtn2, aboutBtn3, placeBtn, placeStayBtn, packagesBtn,
			gettingBtn, readMoreBtn, readMoreBtn2, readMoreBtn3, facilitiesBtn,
			roomBtn, getDirectionsBtn, getDirectionsBtn2, getDirectionsBtn3,
			favButton, buyticketBtn;
	LinearLayout aboutLayout, aboutLayout1, aboutLayout2, facilitiesLay,
			roomLay, seeAlsoLay;
	static LinearLayout placesLayout, placesStayLayout, seeAlsoLayout,
			packagesLay, gettingLayout;

	// DropDownAnimation aboutusAnimation, placesAnimation, placesStayAnimation;
	boolean clickFlag = false;
	String[] namesArray = { "Charminar", "Golkonda Fort", "Hussain Sagar",
			"Birla Temple" };
	ArrayList<SeeAlsoItems> seeAlsoList, packagesList, accomodationList,
			attractionsList, attractionsImagesList;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private ViewFlipper mViewFlipper;

	// Dots variables
	private LinearLayout mDotsLayout;
	static TextView mDotsText[];
	private int mDotsCount;
	Runnable runnable;
	private final Handler handler = new Handler();
	Gallery g;
	TextView titleTxt, aboutTxt, aboutTxt1, aboutTxt2, facilitiesTxt, roomTxt,
			byBusTxt, byTrainTxt, byCarTxt;

	SeeAlsoItems AccomodationItem;
	ArrayList<SeeAlsoItems> toursList;

	String responseString;
	TouchImageView imgflag;

	// int fontChaning = 0;
	TextView seealsoTxt;

	ArrayList<String> roomTypeArray1 = new ArrayList<String>();
	ArrayList<String> roomCostArray1 = new ArrayList<String>();

	@SuppressWarnings("deprecation")
	@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		if (Constants.selectLanguage.equalsIgnoreCase("3")) {
			setContentView(R.layout.arabic_details_screen1);
		} else {
			setContentView(R.layout.details_screen1);
		}

		// ActionBar mActionBar = getActionBar();
		//
		// mActionBar.hide();

		utils = new Utility(DetailScreen1.this);

		if (Constants.sublistFlag == 3) {
			if(Utility.checkInternetConnection(DetailScreen1.this)){
				new GetHotelsPrice().execute();
			}else{
				Utility.showAlertNoInternetService(DetailScreen1.this);
			}
			
		}
		favButton = (Button) findViewById(R.id.favBtn);

		// ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
		//
		// scrollView.fullScroll(View.FOCUS_DOWN);

		try {
			dbbHelper = new TtHelper(DetailScreen1.this);
			dbbHelper.openDataBase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String selectQuery = "SELECT * FROM  Favorites_Content where MaduleUniqueId="
				+ Constants.id;

		constantCursor = dbbHelper.getReadableDatabase().rawQuery(selectQuery,
				null);
		startManagingCursor(constantCursor);

		if (constantCursor.getCount() > 0) {
			favFlag = true;
			favButton.setBackgroundResource(R.drawable.favorites_select);
		} else {
			favFlag = false;
			favButton.setBackgroundResource(R.drawable.favorites_unselect);
		}

		favButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					DecimalFormat df = new DecimalFormat("#.#");
					dis = df.format(utils.distance(Constants.latitude,
							Constants.longitude, Double.parseDouble(lat),
							Double.parseDouble(lng), 'K'));

					if (favFlag) {
						favButton
								.setBackgroundResource(R.drawable.favorites_unselect);
						String deleteQuery = "DELETE FROM  Favorites_Content where MaduleUniqueId='"
								+ Constants.id + "'";
						SQLiteDatabase database = dbbHelper
								.getWritableDatabase();
						Log.d("query", deleteQuery);
						database.execSQL(deleteQuery);
						favFlag = false;
					} else {

						favButton
								.setBackgroundResource(R.drawable.favorites_select);
						String qry = "insert into Favorites_Content (MaduleUniqueId,ModuleUnicName,Image_Path,Address,navFlag,Distence)"
								+ "values('"
								+ id
								+ "', '"
								+ tit.replace("'", "")
								+ "', '"
								+ image_path
								+ "', '"
								+ address.replace("'", "")
								+ "', '"
								+ Constants.sublistFlag + "', '" + dis + "') ";
						constantCursor = dbbHelper.getReadableDatabase()
								.rawQuery(qry, null);
						startManagingCursor(constantCursor);
						// Toast.makeText(
						// getApplicationContext(),
						// tit
						// +
						// " was added successfully to your list of favorites",
						// Toast.LENGTH_LONG).show();

						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								DetailScreen1.this);
						// altDialog.setTitle("Alert");
						altDialog.setMessage(tit
								+ " "
								+ getResources().getString(
										R.string.details_favourites_msg)); // here
						// add
						// your
						// message
						altDialog.setNeutralButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								});
						altDialog.show();
						favFlag = true;
						if (constantCursor.getCount() > 0) {
							favFlag = true;

						}

						
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		RelativeLayout headerLayout = (RelativeLayout) findViewById(R.id.topLayout);

		headerLayout.setBackgroundColor(Color
				.parseColor(MainActivity.changeColorStr));
		titleTxt = (TextView) findViewById(R.id.text_intro);
		titleTxt.setTypeface(Constants.ProximaNova_Regular);

		// titleTxt.setText(Constants.event_title);

		Button backBtn = (Button) findViewById(R.id.button1);

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		aboutLayout = (LinearLayout) findViewById(R.id.aboutLay);

		aboutLayout1 = (LinearLayout) findViewById(R.id.aboutLay2);

		aboutLayout2 = (LinearLayout) findViewById(R.id.aboutLay3);

		seeAlsoLay = (LinearLayout) findViewById(R.id.seealsoLay);

		facilitiesLay = (LinearLayout) findViewById(R.id.facilitiesLay);

		roomLay = (LinearLayout) findViewById(R.id.roomsLay);

		placesLayout = (LinearLayout) findViewById(R.id.palcesLay);

		packagesLay = (LinearLayout) findViewById(R.id.packagesLay);

		placesStayLayout = (LinearLayout) findViewById(R.id.palcestayLay);

		gettingLayout = (LinearLayout) findViewById(R.id.gettinghereLay);

		seeAlsoLayout = (LinearLayout) findViewById(R.id.seeAlsoLay);

		aboutBtn = (Button) findViewById(R.id.aboutusBtn);
		aboutBtn.setTypeface(Constants.ProximaNova_Regular);

		aboutBtn2 = (Button) findViewById(R.id.aboutusBtn2);
		aboutBtn2.setTypeface(Constants.ProximaNova_Regular);

		aboutBtn3 = (Button) findViewById(R.id.aboutusBtn3);
		aboutBtn3.setTypeface(Constants.ProximaNova_Regular);

		facilitiesBtn = (Button) findViewById(R.id.facilitiesBtn);
		facilitiesBtn.setTypeface(Constants.ProximaNova_Regular);

		roomBtn = (Button) findViewById(R.id.roomsBtn);
		roomBtn.setTypeface(Constants.ProximaNova_Regular);
		roomBtn.setText("Rooms");

		readMoreBtn = (Button) findViewById(R.id.readMoreBtn);
		readMoreBtn.setTypeface(Constants.ProximaNova_Regular);
		readMoreBtn2 = (Button) findViewById(R.id.readMoreBtn2);
		readMoreBtn2.setTypeface(Constants.ProximaNova_Regular);
		readMoreBtn3 = (Button) findViewById(R.id.readMoreBtn3);
		readMoreBtn3.setTypeface(Constants.ProximaNova_Regular);
		getDirectionsBtn3 = (Button) findViewById(R.id.getdirectionsBtn3);
		getDirectionsBtn3.setTypeface(Constants.ProximaNova_Regular);

		byBusTxt = (TextView) findViewById(R.id.byBusTxt);
		byBusTxt.setTypeface(Constants.ProximaNova_Regular);
		byTrainTxt = (TextView) findViewById(R.id.byTrainTxt);
		byTrainTxt.setTypeface(Constants.ProximaNova_Regular);
		byCarTxt = (TextView) findViewById(R.id.byCarTxt);
		byCarTxt.setTypeface(Constants.ProximaNova_Regular);

		getDirectionsBtn = (Button) findViewById(R.id.getdirectionsBtn);
		getDirectionsBtn.setTypeface(Constants.ProximaNova_Regular);
		getDirectionsBtn2 = (Button) findViewById(R.id.getdirectionsBtn2);
		getDirectionsBtn2.setTypeface(Constants.ProximaNova_Regular);
		getDirectionsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Intent intent = new
				// Intent(android.content.Intent.ACTION_VIEW,
				// Uri.parse("http://maps.google.com/maps?saddr="
				// + Constants.latitude + ","
				// + Constants.longitude + "&daddr=" + lat + ","
				// + lng));
				// intent.setClassName("com.google.android.apps.maps",
				// "com.google.android.maps.MapsActivity");
				// startActivity(intent);

				// final Dialog dialog = new Dialog(DetailScreen1.this,
				// android.R.style.Theme_Translucent_NoTitleBar);
				// // android.R.style.Theme_Translucent_NoTitleBar);
				// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// dialog.setContentView(R.layout.map_dialog);
				//
				// RelativeLayout headerLayout = (RelativeLayout) dialog
				// .findViewById(R.id.headerLayout);
				//
				// headerLayout.setBackgroundColor(Color
				// .parseColor(MainActivity.changeColorStr));
				//
				// TextView titleTxt1 = (TextView) dialog
				// .findViewById(R.id.tit_txt);
				//
				// titleTxt1.setText(URLDecoder.decode(tit));
				//
				// titleTxt1.setTypeface(Constants.ProximaNova_Regular);
				//
				// WebView webview = (WebView)
				// dialog.findViewById(R.id.webView1);
				// webview.setWebViewClient(new WebViewClient());
				// webview.getSettings().setJavaScriptEnabled(true);
				//
				// // if(accomodationList.get(v.getId()).acclat.length() ==
				// // 0 || accomodationList.get(v.getId()).acclng.length() == 0
				// // ||
				// //
				// // TextUtils.isEmpty(accomodationList.get(v.getId()).acclat)
				// // ||
				// // TextUtils.isEmpty(accomodationList.get(v.getId()).acclng){
				// //
				// // }else{
				//
				// // String languageToLoad = "en";
				// // Locale locale = new Locale(languageToLoad);
				// // Locale.setDefault(locale);
				// // Configuration config = new Configuration();
				// // config.locale = locale;
				// // DetailScreen1.this.getBaseContext().getResources()
				// // .updateConfiguration(config, null);
				//
				// // String uri = String.format(Locale.ENGLISH,
				// "http://maps.google.com/maps?" + "saddr="
				// // + Constants.latitude + "," + Constants.longitude
				// // + "&daddr=" + lat + "," + lng);
				//
				// webview.loadUrl("http://maps.google.com/maps?" + "saddr="
				// + Constants.latitude + "," + Constants.longitude
				// + "&daddr=" + lat + "," + lng);
				//
				// // }
				//
				// Button closeBtN = (Button)
				// dialog.findViewById(R.id.closeBtn);
				// closeBtN.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// dialog.dismiss();
				// }
				// });
				//
				// dialog.show();

				String uri = String.format(Locale.ENGLISH,
						"http://maps.google.com/maps?" + "saddr="
								+ Constants.latitude + ","
								+ Constants.longitude + "&daddr=" + lat + ","
								+ lng);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				intent.setClassName("com.google.android.apps.maps",
						"com.google.android.maps.MapsActivity");
				try {
					startActivity(intent);
				} catch (ActivityNotFoundException ex) {
					try {
						Intent unrestrictedIntent = new Intent(
								Intent.ACTION_VIEW, Uri.parse(uri));
						startActivity(unrestrictedIntent);
					} catch (ActivityNotFoundException innerEx) {
						Toast.makeText(DetailScreen1.this,
								"Please install a maps application",
								Toast.LENGTH_LONG).show();
					}
				}

			}
		});

		getDirectionsBtn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Intent intent = new
				// Intent(android.content.Intent.ACTION_VIEW,
				// Uri.parse("http://maps.google.com/maps?saddr="
				// + Constants.latitude + ","
				// + Constants.longitude + "&daddr=" + lat + ","
				// + lng));
				// intent.setClassName("com.google.android.apps.maps",
				// "com.google.android.maps.MapsActivity");
				// startActivity(intent);

				final Dialog dialog = new Dialog(DetailScreen1.this,
						android.R.style.Theme_Translucent_NoTitleBar);
				// android.R.style.Theme_Translucent_NoTitleBar);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.map_dialog);

				RelativeLayout headerLayout = (RelativeLayout) dialog
						.findViewById(R.id.headerLayout);

				headerLayout.setBackgroundColor(Color
						.parseColor(MainActivity.changeColorStr));

				TextView titleTxt1 = (TextView) dialog
						.findViewById(R.id.tit_txt);

				titleTxt1.setText(URLDecoder.decode(tit));
				titleTxt1.setTypeface(Constants.ProximaNova_Regular);

				WebView webview = (WebView) dialog.findViewById(R.id.webView1);
				webview.setWebViewClient(new WebViewClient());
				webview.getSettings().setJavaScriptEnabled(true);

				// if(accomodationList.get(v.getId()).acclat.length() ==
				// 0 || accomodationList.get(v.getId()).acclng.length() == 0
				// ||
				//
				// TextUtils.isEmpty(accomodationList.get(v.getId()).acclat)
				// ||
				// TextUtils.isEmpty(accomodationList.get(v.getId()).acclng){
				//
				// }else{

				webview.loadUrl("http://maps.google.com/maps?" + "saddr="
						+ Constants.latitude + "," + Constants.longitude
						+ "&daddr=" + lat + "," + lng);

				// }

				Button closeBtN = (Button) dialog.findViewById(R.id.closeBtn);
				closeBtN.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

				dialog.show();

			}
		});

		aboutTxt = (TextView) findViewById(R.id.aboutTxt);
		aboutTxt.setTypeface(Constants.ProximaNova_Regular);
		aboutTxt1 = (TextView) findViewById(R.id.TextView02);
		aboutTxt1.setTypeface(Constants.ProximaNova_Regular);
		aboutTxt2 = (TextView) findViewById(R.id.TextView03);
		aboutTxt2.setTypeface(Constants.ProximaNova_Regular);

		facilitiesTxt = (TextView) findViewById(R.id.facilitiesTxt);
		facilitiesTxt.setTypeface(Constants.ProximaNova_Regular);

		roomTxt = (TextView) findViewById(R.id.roomTxt);
		roomTxt.setTypeface(Constants.ProximaNova_Regular);

		LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.layout1);
		LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.layout2);
		LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.layout3);
		// LinearLayout bookLayout = (LinearLayout) findViewById(R.id.bookLay);

		buyticketBtn = (Button) findViewById(R.id.buyticket);
		buyticketBtn.setTypeface(Constants.ProximaNova_Regular);

		if (Constants.cdt_id == 0) {
			linearLayout1.setVisibility(View.VISIBLE);
			linearLayout2.setVisibility(View.GONE);
			linearLayout3.setVisibility(View.GONE);
		} else if (Constants.cdt_id == 1) {
			linearLayout1.setVisibility(View.GONE);
			linearLayout2.setVisibility(View.VISIBLE);
			linearLayout3.setVisibility(View.GONE);
		} else if (Constants.cdt_id == 2) {
			linearLayout1.setVisibility(View.GONE);
			linearLayout2.setVisibility(View.GONE);
			linearLayout3.setVisibility(View.VISIBLE);
		} else {
			linearLayout1.setVisibility(View.GONE);
			linearLayout2.setVisibility(View.GONE);
			linearLayout3.setVisibility(View.VISIBLE);
		}

		// aboutTxt.setText(Constants.description);
		// aboutTxt1.setText(Constants.description);
		// aboutTxt2.setText(Constants.description);

		readMoreBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent n = new Intent(DetailScreen1.this,
						ReadmoreActivity.class);
				startActivity(n);
				overridePendingTransition(R.anim.trans_left_in,
						R.anim.trans_left_out);
			}
		});

		readMoreBtn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent n = new Intent(DetailScreen1.this,
						ReadmoreActivity.class);
				startActivity(n);
				overridePendingTransition(R.anim.trans_left_in,
						R.anim.trans_left_out);
			}
		});

		g = (Gallery) findViewById(R.id.gallery);

		g.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(DetailScreen1.this, "" + arg2,
				// Toast.LENGTH_SHORT).show();

				SeeAlsoItems seeAlsoItems = (SeeAlsoItems) seeAlsoList
						.get(arg2);

				Constants.id = seeAlsoItems.id;
				startActivity(new Intent(getApplicationContext(),
						DetailScreen1.class));
				finish();
			}
		});

		final LinearLayout.LayoutParams btnLayoutParam = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 100);

		btnLayoutParam.setMargins(0, 0, 0, 1);

		final LinearLayout.LayoutParams btnLayoutParam1 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 100);

		btnLayoutParam1.setMargins(0, 0, 0, 1);

		final LinearLayout.LayoutParams btnLayoutParam2 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 100);

		btnLayoutParam2.setMargins(0, 0, 0, 1);

		final LinearLayout.LayoutParams btnLayoutParam3 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 100);

		btnLayoutParam3.setMargins(0, 0, 0, 1);

		placeBtn = (Button) findViewById(R.id.placesBtn);
		placeBtn.setTypeface(Constants.ProximaNova_Regular);

		placeStayBtn = (Button) findViewById(R.id.placetosatyBtn);
		placeStayBtn.setTypeface(Constants.ProximaNova_Regular);

		packagesBtn = (Button) findViewById(R.id.packagesBtn);
		packagesBtn.setTypeface(Constants.ProximaNova_Regular);

		gettingBtn = (Button) findViewById(R.id.gettinghereBtn);
		gettingBtn.setTypeface(Constants.ProximaNova_Regular);

		// aboutBtn.setText("About " + Constants.event_title);

		// aboutBtn2.setText("About " + Constants.event_title);
		//
		// aboutBtn3.setText("About " + Constants.event_title);

		aboutBtn.setLayoutParams(btnLayoutParam);
		aboutBtn2.setLayoutParams(btnLayoutParam);

		aboutBtn.setTextColor(Color.parseColor("#CC5C50"));
		aboutBtn2.setTextColor(Color.parseColor("#CC5C50"));
		aboutBtn3.setTextColor(Color.parseColor("#CC5C50"));
		// aboutBtn3.setLayoutParams(btnLayoutParam);

		placeBtn.setLayoutParams(btnLayoutParam1);

		placeStayBtn.setLayoutParams(btnLayoutParam2);

		gettingBtn.setLayoutParams(btnLayoutParam3);

		// aboutusAnimation = new DropDownAnimation(aboutLayout);
		// gettingAnimation = new DropDownAnimation(gettingLayout);

		// http://172.16.0.49/telangana_tourism/WebServices/getDestinationsDetail/1/1

		aboutBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				placesStayLayout.setVisibility(View.GONE);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);

					placesLayout.setVisibility(View.GONE);
					placeBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
					gettingLayout.setVisibility(View.GONE);
					gettingBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
				} else {
					placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);

					placesLayout.setVisibility(View.GONE);
					placeBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
					gettingLayout.setVisibility(View.GONE);
					gettingBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
				}

				aboutBtn.setTextColor(Color.parseColor("#CC5C50"));
				placeBtn.setTextColor(Color.parseColor("#383838"));
				placeStayBtn.setTextColor(Color.parseColor("#383838"));
				gettingBtn.setTextColor(Color.parseColor("#383838"));

				btnLayoutParam1.setMargins(0, 0, 0, 1);
				btnLayoutParam2.setMargins(0, 0, 0, 1);
				btnLayoutParam3.setMargins(0, 0, 0, 1);

				if (aboutLayout.isShown()) {
					// gettingLayout.clearAnimation();

					aboutBtn.setTextColor(Color.parseColor("#383838"));
					btnLayoutParam.setMargins(0, 0, 0, 1);

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						aboutBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null,
								null, null);
					} else {
						aboutBtn.setCompoundDrawablesWithIntrinsicBounds(null,
								null, getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null);
					}

					aboutLayout.setVisibility(View.GONE);
				} else {

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						aboutBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null,
								null, null);
					} else {
						aboutBtn.setCompoundDrawablesWithIntrinsicBounds(null,
								null, getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null);
					}

					btnLayoutParam.setMargins(0, 0, 0, 0);
					aboutLayout.setVisibility(View.VISIBLE);
				}

				aboutBtn.setLayoutParams(btnLayoutParam);
			}
		});

		aboutBtn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				facilitiesLay.setVisibility(View.GONE);

				roomLay.setVisibility(View.GONE);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					facilitiesBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);

					roomBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
				} else {
					facilitiesBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);

					roomBtn.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null);
				}

				aboutBtn2.setTextColor(Color.parseColor("#CC5C50"));
				facilitiesBtn.setTextColor(Color.parseColor("#383838"));
				roomBtn.setTextColor(Color.parseColor("#383838"));

				btnLayoutParam1.setMargins(0, 0, 0, 1);
				btnLayoutParam2.setMargins(0, 0, 0, 1);
				btnLayoutParam3.setMargins(0, 0, 0, 1);

				if (aboutLayout1.isShown()) {
					// gettingLayout.clearAnimation();

					aboutBtn2.setTextColor(Color.parseColor("#383838"));
					btnLayoutParam.setMargins(0, 0, 0, 1);

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						aboutBtn2.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null,
								null, null);
					} else {
						aboutBtn2.setCompoundDrawablesWithIntrinsicBounds(null,
								null, getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null);
					}

					aboutLayout1.setVisibility(View.GONE);
				} else {

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						aboutBtn2.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null,
								null, null);
					} else {
						aboutBtn2.setCompoundDrawablesWithIntrinsicBounds(null,
								null, getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null);
					}

					btnLayoutParam.setMargins(0, 0, 0, 0);
					aboutLayout1.setVisibility(View.VISIBLE);
				}

				aboutBtn2.setLayoutParams(btnLayoutParam);
			}
		});

		// aboutBtn3.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		//
		// aboutBtn3.setTextColor(Color.parseColor("#CC5C50"));
		//
		// if (aboutLayout2.isShown()) {
		// // gettingLayout.clearAnimation();
		//
		// aboutBtn3.setTextColor(Color.parseColor("#383838"));
		// btnLayoutParam.setMargins(0, 0, 0, 1);
		// aboutBtn3.setCompoundDrawablesWithIntrinsicBounds(null,
		// null, getApplicationContext().getResources()
		// .getDrawable(R.drawable.more), null);
		// aboutLayout2.setVisibility(View.GONE);
		// } else {
		//
		// aboutBtn3.setCompoundDrawablesWithIntrinsicBounds(null,
		// null, getApplicationContext().getResources()
		// .getDrawable(R.drawable.less), null);
		//
		// btnLayoutParam.setMargins(0, 0, 0, 0);
		// aboutLayout2.setVisibility(View.VISIBLE);
		// }
		//
		// aboutBtn3.setLayoutParams(btnLayoutParam);
		// }
		// });

		placeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				packagesLay.setVisibility(View.GONE);
				placesStayLayout.setVisibility(View.GONE);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);

					aboutLayout.setVisibility(View.GONE);
					aboutBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
					gettingLayout.setVisibility(View.GONE);
					gettingBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
				} else {
					placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);

					aboutLayout.setVisibility(View.GONE);
					aboutBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
					gettingLayout.setVisibility(View.GONE);
					gettingBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
				}

				aboutBtn.setTextColor(Color.parseColor("#383838"));
				placeBtn.setTextColor(Color.parseColor("#CC5C50"));
				placeStayBtn.setTextColor(Color.parseColor("#383838"));
				gettingBtn.setTextColor(Color.parseColor("#383838"));

				btnLayoutParam.setMargins(0, 0, 0, 1);
				btnLayoutParam2.setMargins(0, 0, 0, 1);
				btnLayoutParam3.setMargins(0, 0, 0, 1);

				if (placesLayout.isShown()) {
					placeBtn.setTextColor(Color.parseColor("#383838"));

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						placeBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null,
								null, null);
					} else {
						placeBtn.setCompoundDrawablesWithIntrinsicBounds(null,
								null, getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null);
					}

					btnLayoutParam1.setMargins(0, 0, 0, 1);
					placesLayout.setVisibility(View.GONE);
				} else {

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						placeBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null,
								null, null);
					} else {
						placeBtn.setCompoundDrawablesWithIntrinsicBounds(null,
								null, getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null);
					}

					btnLayoutParam1.setMargins(0, 0, 0, 0);
					placesLayout.setVisibility(View.VISIBLE);
				}
				placeBtn.setLayoutParams(btnLayoutParam1);
			}
		});

		packagesBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// packagesLay.setVisibility(View.GONE);
				placesStayLayout.setVisibility(View.GONE);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);

					aboutLayout.setVisibility(View.GONE);
					aboutBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
					gettingLayout.setVisibility(View.GONE);
					gettingBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
				} else {
					placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);

					aboutLayout.setVisibility(View.GONE);
					aboutBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
					gettingLayout.setVisibility(View.GONE);
					gettingBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
				}

				aboutBtn.setTextColor(Color.parseColor("#383838"));
				placeStayBtn.setTextColor(Color.parseColor("#383838"));
				gettingBtn.setTextColor(Color.parseColor("#383838"));

				btnLayoutParam.setMargins(0, 0, 0, 1);
				btnLayoutParam2.setMargins(0, 0, 0, 1);
				btnLayoutParam3.setMargins(0, 0, 0, 1);

				if (packagesLay.isShown()) {
					packagesBtn.setTextColor(Color.parseColor("#383838"));

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						packagesBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null,
								null, null);
					} else {
						packagesBtn.setCompoundDrawablesWithIntrinsicBounds(
								null, null,
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null);
					}

					btnLayoutParam1.setMargins(0, 0, 0, 1);
					packagesLay.setVisibility(View.GONE);
				} else {
					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						packagesBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null,
								null, null);
					} else {
						packagesBtn.setCompoundDrawablesWithIntrinsicBounds(
								null, null,
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null);
					}

					btnLayoutParam1.setMargins(0, 0, 0, 0);
					packagesLay.setVisibility(View.VISIBLE);
				}
				packagesBtn.setLayoutParams(btnLayoutParam1);
			}
		});

		placeStayBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				packagesLay.setVisibility(View.GONE);
				placesLayout.setVisibility(View.GONE);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					placeBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);

					aboutLayout.setVisibility(View.GONE);
					aboutBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
					gettingLayout.setVisibility(View.GONE);
					gettingBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
				} else {
					placeBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);

					aboutLayout.setVisibility(View.GONE);
					aboutBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
					gettingLayout.setVisibility(View.GONE);
					gettingBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
				}

				aboutBtn.setTextColor(Color.parseColor("#383838"));
				placeBtn.setTextColor(Color.parseColor("#383838"));
				placeStayBtn.setTextColor(Color.parseColor("#CC5C50"));
				gettingBtn.setTextColor(Color.parseColor("#383838"));

				btnLayoutParam.setMargins(0, 0, 0, 1);
				btnLayoutParam1.setMargins(0, 0, 0, 1);
				btnLayoutParam2.setMargins(0, 0, 0, 1);

				if (placesStayLayout.isShown()) {
					placeStayBtn.setTextColor(Color.parseColor("#383838"));
					btnLayoutParam2.setMargins(0, 0, 0, 1);
					placesStayLayout.setVisibility(View.GONE);
					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null,
								null, null);
					} else {
						placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(
								null, null,
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null);
					}

				} else {

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null,
								null, null);
					} else {
						placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(
								null, null,
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null);
					}

					btnLayoutParam2.setMargins(0, 0, 0, 0);
					placesStayLayout.setVisibility(View.VISIBLE);
				}

				placeStayBtn.setLayoutParams(btnLayoutParam2);

			}
		});

		gettingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnLayoutParam.setMargins(0, 0, 0, 1);
				btnLayoutParam1.setMargins(0, 0, 0, 1);
				btnLayoutParam2.setMargins(0, 0, 0, 1);

				aboutBtn.setTextColor(Color.parseColor("#383838"));
				placeBtn.setTextColor(Color.parseColor("#383838"));
				placeStayBtn.setTextColor(Color.parseColor("#383838"));
				gettingBtn.setTextColor(Color.parseColor("#CC5C50"));

				packagesLay.setVisibility(View.GONE);
				placesLayout.setVisibility(View.GONE);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {

					placeBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);

					aboutLayout.setVisibility(View.GONE);
					aboutBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
					placesStayLayout.setVisibility(View.GONE);
					placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
				} else {
					placeBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);

					aboutLayout.setVisibility(View.GONE);
					aboutBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
					placesStayLayout.setVisibility(View.GONE);
					placeStayBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
				}

				if (gettingLayout.isShown()) {

					gettingBtn.setTextColor(Color.parseColor("#383838"));

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						gettingBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null,
								null, null);

					} else {
						gettingBtn.setCompoundDrawablesWithIntrinsicBounds(
								null, null,
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null);
					}

					btnLayoutParam3.setMargins(0, 0, 0, 1);
					gettingLayout.setVisibility(View.GONE);
				} else {
					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						gettingBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null,
								null, null);
					} else {
						gettingBtn.setCompoundDrawablesWithIntrinsicBounds(
								null, null,
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null);
					}

					btnLayoutParam3.setMargins(0, 0, 0, 1);
					gettingLayout.setVisibility(View.VISIBLE);
				}

				gettingBtn.setLayoutParams(btnLayoutParam3);

			}
		});

		facilitiesBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				aboutLayout1.setVisibility(View.GONE);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					aboutBtn2.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);

					roomLay.setVisibility(View.GONE);

					roomBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
				} else {
					aboutBtn2.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);

					roomLay.setVisibility(View.GONE);

					roomBtn.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null);
				}

				aboutBtn2.setTextColor(Color.parseColor("#CC5C50"));
				facilitiesBtn.setTextColor(Color.parseColor("#383838"));
				roomBtn.setTextColor(Color.parseColor("#383838"));

				if (facilitiesLay.isShown()) {
					// gettingLayout.clearAnimation();

					facilitiesBtn.setTextColor(Color.parseColor("#383838"));
					btnLayoutParam.setMargins(0, 0, 0, 1);
					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						facilitiesBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null,
								null, null);
					} else {
						facilitiesBtn.setCompoundDrawablesWithIntrinsicBounds(
								null, null,
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null);
					}

					facilitiesLay.setVisibility(View.GONE);
				} else {

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						facilitiesBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null,
								null, null);
					} else {
						facilitiesBtn.setCompoundDrawablesWithIntrinsicBounds(
								null, null,
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null);
					}

					btnLayoutParam.setMargins(0, 0, 0, 0);
					facilitiesLay.setVisibility(View.VISIBLE);
				}

				facilitiesBtn.setLayoutParams(btnLayoutParam);
			}
		});

		roomBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				aboutLayout1.setVisibility(View.GONE);
				aboutBtn2.setTextColor(Color.parseColor("#383838"));

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					aboutBtn2.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);

					facilitiesLay.setVisibility(View.GONE);

					facilitiesBtn.setCompoundDrawablesWithIntrinsicBounds(
							getApplicationContext().getResources().getDrawable(
									R.drawable.more), null, null, null);
				} else {
					aboutBtn2.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);

					facilitiesLay.setVisibility(View.GONE);

					facilitiesBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
				}

				if (roomLay.isShown()) {
					// gettingLayout.clearAnimation();

					roomBtn.setTextColor(Color.parseColor("#383838"));
					btnLayoutParam.setMargins(0, 0, 0, 1);

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						roomBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null,
								null, null);
					} else {
						roomBtn.setCompoundDrawablesWithIntrinsicBounds(null,
								null, getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null);
					}

					roomLay.setVisibility(View.GONE);
				} else {
					roomBtn.setTextColor(Color.parseColor("#CC5C50"));

					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
						roomBtn.setCompoundDrawablesWithIntrinsicBounds(
								getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null,
								null, null);
					} else {
						roomBtn.setCompoundDrawablesWithIntrinsicBounds(null,
								null, getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null);
					}

					btnLayoutParam.setMargins(0, 1, 0, 0);
					roomLay.setVisibility(View.VISIBLE);
				}
				roomBtn.setLayoutParams(btnLayoutParam);
			}
		});

		mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);

		final GestureDetector detector = new GestureDetector(
				new MyGestureDetector());
		mViewFlipper.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(final View view, final MotionEvent event) {

				System.out.println("touch detected ");
				detector.onTouchEvent(event);
				return true;
			}
		});

		mDotsLayout = (LinearLayout) findViewById(R.id.image_count);

		// getDots();

		for (int i = 0; i < mDotsCount; i++) {

			mDotsText[i] = new TextView(DetailScreen1.this);
			mDotsText[i].setText(".");
			mDotsText[i].setTextSize(45);
			mDotsText[i].setTypeface(null, Typeface.BOLD);
			mDotsText[i].setTextColor(android.graphics.Color.GRAY);
			mDotsLayout.addView(mDotsText[i]);
			mDotsText[i].setId(i);

			mDotsText[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					mViewFlipper.setDisplayedChild(v.getId());

					for (int i = 0; i < mDotsCount; i++) {
						mDotsText[i].setTextColor(Color.GRAY);
					}

					mDotsText[v.getId()].setTextColor(Color.WHITE);
					// gallery.setSelection(v.getId());
				}
			});

			mDotsText[mViewFlipper.getDisplayedChild()]
					.setTextColor(Color.WHITE);
		}

		runnable = new Runnable() {
			public void run() {

				for (int j = 0; j < mDotsCount; j++) {
					mDotsText[j].setTextColor(Color.GRAY);
				}

				System.out.println("current view "
						+ mViewFlipper.getDisplayedChild());
				mDotsText[mViewFlipper.getDisplayedChild()]
						.setTextColor(Color.CYAN);

				handler.postDelayed(runnable, 0);
			}
		};

		// if (ServiceCalls1.isNetworkAvailable(DetailScreen1.this)) {

		if (Constants.sublistFlag == 3) {
			seeAlsoLay.setVisibility(View.VISIBLE);
			favButton.setVisibility(View.VISIBLE);

			// new AccomodationAsyTask().execute();

			try {
				dbbHelper = new TtHelper(DetailScreen1.this);
				dbbHelper.openDataBase();

				String select = " SELECT * FROM AccomodationDetailContent WHERE ModuleUniqueName="
						+ Constants.id;
				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
						select, null);

				startManagingCursor(constantCursor);

				if (constantCursor.getCount() > 0) {
					if (constantCursor.moveToFirst()) {
						// do {

						responseString = constantCursor
								.getString(constantCursor
										.getColumnIndex("ResponseContent"));

						// } while (constantCursor.moveToNext());

					}

				}

				accomodationJsonParsing(responseString);

				// dbbHelper.close();

				// dbbHelper.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (Constants.sublistFlag == 1) {
			seeAlsoLay.setVisibility(View.VISIBLE);
			favButton.setVisibility(View.VISIBLE);
			
			new DestinationDetailsTask().execute();
			
//			try {
//				dbbHelper = new TtHelper(DetailScreen1.this);
//				dbbHelper.openDataBase();
//
//				String select;
//				if (Constants.sublistFlag == 6) {
//					select = " SELECT * FROM Shop_Online_Details WHERE ModuleUniqueName="
//							+ Constants.id
//							+ " AND language_id="
//							+ Constants.selectLanguage;
//				} else {
//					select = " SELECT * FROM Destinations_Detail_Content WHERE ModuleUniqueName="
//							+ Constants.id;
//				}
//
//				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
//						select, null);
//
//				startManagingCursor(constantCursor);
//
//				if (constantCursor.getCount() > 0) {
//					if (constantCursor.moveToFirst()) {
//						// do {
//
//						responseString = constantCursor
//								.getString(constantCursor
//										.getColumnIndex("ResponseContent"));
//
//						// } while (constantCursor.moveToNext());
//
//					}
//
//				}
//
//				destinationsJsonParsing(responseString);
//				// dbbHelper.close();
//
//				// dbbHelper.close();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			
		} else if (Constants.sublistFlag == 5) {
			seeAlsoLay.setVisibility(View.GONE);
			favButton.setVisibility(View.GONE);
			new CultureDetailsTask().execute();
		} else if (Constants.sublistFlag == 2) {
			seeAlsoLay.setVisibility(View.GONE);
			favButton.setVisibility(View.GONE);
			linearLayout2.setVisibility(View.VISIBLE);
			facilitiesBtn.setVisibility(View.GONE);
			roomBtn.setVisibility(View.VISIBLE);

			readMoreBtn2.setVisibility(View.GONE);
			getDirectionsBtn2.setVisibility(View.GONE);
			aboutBtn3.setVisibility(View.GONE);
			buyticketBtn.setVisibility(View.VISIBLE);
			new PackagesTask().execute();
			
//			try {
//				dbbHelper = new TtHelper(DetailScreen1.this);
//				dbbHelper.openDataBase();
//
//				String select = " SELECT * FROM TourPackagesDetailContent WHERE ModuleUniqueName="
//						+ Constants.id
//						+ " AND language_id="
//						+ Constants.selectLanguage;
//				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
//						select, null);
//
//				startManagingCursor(constantCursor);
//
//				if (constantCursor.getCount() > 0) {
//					if (constantCursor.moveToFirst()) {
//						// do {
//
//						responseString = constantCursor
//								.getString(constantCursor
//										.getColumnIndex("ResponseContent"));
//
//						// } while (constantCursor.moveToNext());
//
//					}
//
//				}
//				
//				packagesJsonParsing(responseString);
//
//				// dbbHelper.close();
//
//				// dbbHelper.close();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		} else if (Constants.sublistFlag == 4) {
			seeAlsoLay.setVisibility(View.VISIBLE);
			favButton.setVisibility(View.GONE);
			new EventsTask().execute();
		} else if (Constants.sublistFlag == 6) {
			seeAlsoLay.setVisibility(View.VISIBLE);
			favButton.setVisibility(View.VISIBLE);
			new DestinationDetailsTask().execute();
			
//			try {
//				dbbHelper = new TtHelper(DetailScreen1.this);
//				dbbHelper.openDataBase();
//
//				String select;
//				if (Constants.sublistFlag == 6) {
//					select = " SELECT * FROM Shop_Online_Details WHERE ModuleUniqueName="
//							+ Constants.id
//							+ " AND language_id="
//							+ Constants.selectLanguage;
//				} else {
//					select = " SELECT * FROM Destinations_Detail_Content WHERE ModuleUniqueName="
//							+ Constants.id;
//				}
//
//				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
//						select, null);
//
//				startManagingCursor(constantCursor);
//
//				if (constantCursor.getCount() > 0) {
//					if (constantCursor.moveToFirst()) {
//						// do {
//
//						responseString = constantCursor
//								.getString(constantCursor
//										.getColumnIndex("ResponseContent"));
//
//						// } while (constantCursor.moveToNext());
//
//					}
//
//				}
//
//				destinationsJsonParsing(responseString);
//				// dbbHelper.close();
//
//				// dbbHelper.close();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		}
		// }

		// http://www.happeninghyderabad.com/xmlevents/TTEvent/83/22960/1

		// new FavoritesAsyTask().execute();

		if (Constants.sublistFlag == 3) {
			favButton.setVisibility(View.VISIBLE);
			facilitiesBtn.setVisibility(View.VISIBLE);
			roomBtn.setVisibility(View.VISIBLE);
		}

		// TextView tvSmallFont = (TextView) findViewById(R.id.smallFontTxt);
		// TextView tvBigFont = (TextView) findViewById(R.id.bigFontTxt);

		seealsoTxt = (TextView) findViewById(R.id.seealsoTxt);
		seealsoTxt.setTypeface(Constants.ProximaNova_Regular);

		if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID, "")
				.equalsIgnoreCase("1")) {
			aboutBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			aboutBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			aboutBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			aboutTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			aboutTxt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			aboutTxt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			readMoreBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			readMoreBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			readMoreBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			getDirectionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			getDirectionsBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			getDirectionsBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			placeStayBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			gettingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			seealsoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			facilitiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			facilitiesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			roomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			roomTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			buyticketBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
			aboutBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			aboutBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			aboutBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			aboutTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			aboutTxt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			aboutTxt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			readMoreBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			readMoreBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			readMoreBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			getDirectionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			getDirectionsBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			getDirectionsBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			placeStayBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			gettingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			seealsoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			facilitiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			facilitiesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			roomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			roomTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			buyticketBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
			aboutBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			aboutBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			aboutBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			aboutTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			aboutTxt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			aboutTxt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			readMoreBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			readMoreBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			readMoreBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			getDirectionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			getDirectionsBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			getDirectionsBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			placeStayBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			gettingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			seealsoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
			facilitiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			facilitiesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			roomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			roomTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			buyticketBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		}

		// tvSmallFont.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (fontChaning == 0) {
		// if (Constants.sublistFlag == 1) {
		// getAccomodationList(getApplicationContext());
		// getPackagesList(getApplicationContext());
		// }
		// aboutBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// aboutBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// aboutBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// aboutTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// aboutTxt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// aboutTxt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// readMoreBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// readMoreBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// readMoreBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// getDirectionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// getDirectionsBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// getDirectionsBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// placeStayBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// gettingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// seealsoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// facilitiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// facilitiesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// roomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// roomTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// buyticketBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// } else {
		// fontChaning--;
		// if (fontChaning > 0) {
		// if (Constants.sublistFlag == 1) {
		// getAccomodationList(getApplicationContext());
		// getPackagesList(getApplicationContext());
		// }
		// if (fontChaning == 1) {
		// aboutBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// aboutBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// aboutBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// aboutTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// aboutTxt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// aboutTxt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// readMoreBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// readMoreBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// readMoreBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// getDirectionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// getDirectionsBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// getDirectionsBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// placeStayBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// gettingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// seealsoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// facilitiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// facilitiesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// roomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// roomTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// buyticketBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// } else if (fontChaning == 2) {
		// aboutBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// aboutBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// aboutBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// aboutTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// aboutTxt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// aboutTxt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// readMoreBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// readMoreBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// readMoreBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// getDirectionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// getDirectionsBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// getDirectionsBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// placeStayBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// gettingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// seealsoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// facilitiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// facilitiesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// roomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// roomTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// buyticketBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// } else if (fontChaning == 3) {
		// aboutBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// aboutBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// aboutBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// aboutTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// aboutTxt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// aboutTxt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// readMoreBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// readMoreBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// readMoreBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// getDirectionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// getDirectionsBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// getDirectionsBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// placeStayBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// gettingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// seealsoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
		// facilitiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// facilitiesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// roomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// roomTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// buyticketBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// }
		// // Toast.makeText(getApplicationContext(),
		// // "" + fontChaning, Toast.LENGTH_LONG).show();
		// } else {
		// // Toast.makeText(getApplicationContext(),
		// // "Else : " + fontChaning, Toast.LENGTH_LONG)
		// // .show();
		// }
		// }
		//
		// }
		// });
		//
		// tvBigFont.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// if (fontChaning == 3) {
		//
		// } else {
		// fontChaning++;
		//
		// if (fontChaning <= 3) {
		//
		// if (Constants.sublistFlag == 1) {
		// getAccomodationList(getApplicationContext());
		// getPackagesList(getApplicationContext());
		// }
		//
		// if (fontChaning == 1) {
		// aboutBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// aboutBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// aboutBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// aboutTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// aboutTxt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// aboutTxt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// readMoreBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// readMoreBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// readMoreBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// getDirectionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// getDirectionsBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// getDirectionsBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// placeStayBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// gettingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// seealsoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// facilitiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// facilitiesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// roomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// roomTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// buyticketBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// } else if (fontChaning == 2) {
		// aboutBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// aboutBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// aboutBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// aboutTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// aboutTxt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// aboutTxt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// readMoreBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// readMoreBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// readMoreBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// getDirectionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// getDirectionsBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// getDirectionsBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// placeStayBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// gettingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// seealsoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// facilitiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// facilitiesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// roomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// roomTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// buyticketBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// } else if (fontChaning == 3) {
		// aboutBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// aboutBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// aboutBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// aboutTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// aboutTxt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// aboutTxt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// readMoreBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// readMoreBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// readMoreBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// getDirectionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// getDirectionsBtn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// getDirectionsBtn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// placeStayBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// gettingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// seealsoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
		// facilitiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// facilitiesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// roomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// roomTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// buyticketBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// }
		//
		// // Toast.makeText(getApplicationContext(),
		// // "" + fontChaning, Toast.LENGTH_LONG).show();
		// } else {
		// // Toast.makeText(getApplicationContext(),
		// // "Else : " + fontChaning, Toast.LENGTH_LONG)
		// // .show();
		// }
		// }
		//
		// }
		// });
	}

	// private void getDots() {
	//
	// // mDotsCount=mDotsCount;
	// System.out.println(" on create dots count "
	// + mViewFlipper.getChildCount() + " dots num " + mDotsCount);
	// mDotsText = new TextView[mViewFlipper.getChildCount()];
	//
	// // here we set the dots
	// // j=0;
	// mDotsLayout.removeAllViews();
	// for (int i = 0; i < mViewFlipper.getChildCount(); i++) {
	// // j++;
	// mDotsText[i] = new TextView(DetailScreen1.this);
	// mDotsText[i].setText(".");
	// mDotsText[i].setTextSize(45);
	// mDotsText[i].setTypeface(null, Typeface.BOLD);
	// // mDotsText[i].setBackgroundResource(R.drawable.round_dots);
	// // mDotsText[i].setTextColor(android.graphics.Color.RED);
	// mDotsLayout.addView(mDotsText[i]);
	// mDotsText[i].setId(i);
	//
	// /*
	// * mDotsText[i].setOnClickListener(new OnClickListener() { // int
	// * j=mDotsText[i].getId();
	// *
	// * @Override public void onClick(View v) {
	// * viewPager.setCurrentItem(v.getId()); for (int i = 0; i <
	// * mDotsCount; i++) { mDotsText[i].setTextColor(Color.GRAY); }
	// * mDotsText[v.getId()].setTextColor(Color.BLUE); //
	// * gallery.setSelection(v.getId()); } });
	// */
	//
	// // mDotsText[viewPager.getCurrentItem()].setTextColor(Color.BLUE);
	// }
	// }

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {

				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

					mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(
							DetailScreen1.this, R.anim.left_in1));
					mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
							DetailScreen1.this, R.anim.left_out1));
					mViewFlipper.showNext();

					for (int i = 0; i < mDotsCount; i++) {
						mDotsText[i].setTextColor(Color.GRAY);
					}
					// mDotsText[mViewFlipper.getDisplayedChild()]
					// .setTextColor(Color.WHITE);

					return true;
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(
							DetailScreen1.this, R.anim.right_in1));
					mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
							DetailScreen1.this, R.anim.right_out1));
					mViewFlipper.showPrevious();

					for (int i = 0; i < mDotsCount; i++) {
						mDotsText[i].setTextColor(Color.GRAY);
					}
					// mDotsText[mViewFlipper.getDisplayedChild()]
					// .setTextColor(Color.WHITE);

					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return false;
		}
	}

	public class ImageAdapter extends BaseAdapter {

		int mGalleryItemBackground;
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
			TypedArray a = c.obtainStyledAttributes(R.styleable.Gallery1);
			mGalleryItemBackground = a.getResourceId(
					R.styleable.Gallery1_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			return seeAlsoList.size();
		}

		public SeeAlsoItems getItem(int position) {
			return seeAlsoList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("deprecation")
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView image = new ImageView(mContext);
			SeeAlsoItems item = seeAlsoList.get(position);
			if (!TextUtils.isEmpty(item.icon)) {

				// if (item.icon.contains("http:")) {

				// if (utils.IsNetConnected(getApplicationContext())) {
				// Picasso.with(mContext)
				// .load(item.icon.replace("\'", "")
				// .replace("%20", "").replace(" ", "")
				// .trim()).error(R.drawable.default_img)
				// .into(image);
				//
				// } else {

				// if (Constants.sublistFlag == 4) {
				if(item.icon.contains("http")){
					Picasso.with(mContext)
					.load(item.icon.replace("\'", "").replace("%20", "")
							.replace(" ", "").trim())
					.error(R.drawable.default_img).into(image);
				}else{
					Picasso.with(mContext)
					.load(SplashScreen.Base_url2+item.icon.replace("\'", "").replace("%20", "")
							.replace(" ", "").trim())
					.error(R.drawable.default_img).into(image);
				}
				
//				

				// ImageLoader imageLoader = ImageLoader.getInstance();
				// DisplayImageOptions options = new
				// DisplayImageOptions.Builder()
				// .cacheInMemory(true).cacheOnDisc(true)
				// .resetViewBeforeLoading(true)
				// .showImageForEmptyUri(R.drawable.default_img)
				// .showImageOnFail(R.drawable.default_img)
				// .showImageOnLoading(R.drawable.default_img).build();
				//
				// // download and display image from url
				// imageLoader.displayImage(
				// item.icon.replace("\'", "").replace("%20", "")
				// .replace(" ", "").trim(), image, options);
				// }else{
				// try {
				// String url = item.icon.replace("\'", "");
				//
				// String replacdUrl = url.replace(
				// "http://ttourdev.etisbew.net/images/", "");
				// // get input stream
				// InputStream ims = getAssets().open(
				// replacdUrl.trim());
				// // load image as Drawable
				// Drawable d = Drawable.createFromStream(ims, null);
				// // set image to ImageView
				// image.setImageDrawable(d);
				// } catch (IOException ex) {
				// image.setImageResource(R.drawable.default_img);
				// }
				// }

				// }

				// } else {
				// try {
				// // get input stream
				//
				// String str = item.icon.replace("\'", "");
				// String str1 = str.replace(
				// "http://ttourdev.etisbew.net/images/", "");
				// InputStream ims = getApplicationContext().getAssets()
				// .open(str1.replace("%20", "").replace(" ", "")
				// .trim());
				// // load image as Drawable
				// Drawable d = Drawable.createFromStream(ims, null);
				// // set image to ImageView
				// image.setImageDrawable(d);
				// } catch (IOException ex) {
				// image.setImageResource(R.drawable.default_img);
				// }
				// }
			}
			image.setLayoutParams(new Gallery.LayoutParams(300, 200));
			image.setScaleType(ImageView.ScaleType.FIT_XY);

			return image;
		}
	}

	public class ImageAdapter1 extends BaseAdapter {

		int mGalleryItemBackground;
		private Context mContext;

		public ImageAdapter1(Context c) {
			mContext = c;
			TypedArray a = c.obtainStyledAttributes(R.styleable.Gallery1);
			mGalleryItemBackground = a.getResourceId(
					R.styleable.Gallery1_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			return WhereToShopFragment.img.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("deprecation")
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

			i.setImageResource(WhereToShopFragment.img.get(position));
			i.setLayoutParams(new Gallery.LayoutParams(300, 200));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			i.setBackgroundResource(mGalleryItemBackground);

			return i;
		}
	}

	static TextView[] txtName, txtLine, gettxtLine, btnName, btnLine;
	static ImageView[] imageView;
	static LinearLayout[] rootLayout, layoutLL, layoutLLButton, imgLayout;
	SeeAlsoItems packagesItem;

	@SuppressWarnings("deprecation")
	public void getPackagesList(final Context context) {

		LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 2f);
		linLayoutParam.gravity = Gravity.CENTER_VERTICAL;
		packagesLay.removeAllViews();
		txtName = new TextView[packagesList.size()];
		txtLine = new TextView[packagesList.size()];
		btnName = new TextView[packagesList.size()];
		btnLine = new TextView[packagesList.size()];
		imageView = new ImageView[packagesList.size()];
		rootLayout = new LinearLayout[packagesList.size()];
		layoutLL = new LinearLayout[packagesList.size()];
		imgLayout = new LinearLayout[packagesList.size()];
		layoutLLButton = new LinearLayout[packagesList.size()];
		gettxtLine = new TextView[packagesList.size()];

		for (int j = 0; j < packagesList.size(); j++) {

			packagesItem = packagesList.get(j);

			rootLayout[j] = new LinearLayout(context);
			rootLayout[j].setOrientation(LinearLayout.HORIZONTAL);

			linLayoutParam.setMargins(0, 10, 0, 0);
			rootLayout[j].setLayoutParams(linLayoutParam);
			rootLayout[j].setBackgroundColor(Color.WHITE);
			rootLayout[j].setPadding(10, 10, 10, 10);

			LinearLayout.LayoutParams sublinLayoutParam = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			sublinLayoutParam.weight = (float) .5;
			sublinLayoutParam.gravity = Gravity.CENTER_VERTICAL;
			sublinLayoutParam.setMargins(15, 10, 0, 5);
			layoutLL[j] = new LinearLayout(context);
			layoutLL[j].setLayoutParams(sublinLayoutParam);
			layoutLL[j].setOrientation(LinearLayout.VERTICAL);

			LinearLayout.LayoutParams sublinLayoutParam0 = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			sublinLayoutParam0.weight = (float) 1.5;
			sublinLayoutParam0.gravity = Gravity.CENTER_VERTICAL;
			// sublinLayoutParam0.setMargins(10, 0, 0, 0);
			imgLayout[j] = new LinearLayout(context);
			imgLayout[j].setLayoutParams(sublinLayoutParam0);
			// imgLayout[j].setPadding(0, 5, 0, 0);
			imgLayout[j].setOrientation(LinearLayout.VERTICAL);
			imgLayout[j].setGravity(Gravity.CENTER_VERTICAL);

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, 170);
			layoutParams.setMargins(5, 5, 5, 5);
			layoutParams.gravity = Gravity.CENTER_VERTICAL;

			imageView[j] = new ImageView(context);
			imageView[j].setLayoutParams(layoutParams);
			imageView[j].setScaleType(ScaleType.FIT_XY);
			// imageView[j].setBackgroundResource(R.drawable.tour1);
			imageView[j].setId(j);

			// Picasso.with(context)
			// .load(packagesItem.icon.replace("\'", "")
			// .replace("%20", "").replace(" ", "").trim())
			// .error(R.drawable.default_img).into(imageView[j]);

			if (packagesItem.icon.length() > 0) {

				// if (packagesItem.icon.contains("http")) {
				//
				// if (utils.IsNetConnected(getApplicationContext())) {
				if(packagesItem.icon.contains("http")){
				Picasso.with(getApplicationContext())
						.load(packagesItem.icon.replace("\'", "%20").trim())
						.noFade().error(R.drawable.default_img)
						.into(imageView[j]);
				}else{
					Picasso.with(getApplicationContext())
					.load(SplashScreen.Base_url2+packagesItem.icon.replace("\'", "%20").trim())
					.noFade().error(R.drawable.default_img)
					.into(imageView[j]);
				}
//				

				// } else {
				// try {
				// // get input stream
				//
				// String str = packagesItem.icon.replace("\'", "");
				// String str1 = str.replace(
				// "http://ttourdev.etisbew.net/images/", "");
				// InputStream ims = getApplicationContext()
				// .getAssets().open(str1.trim());
				// // load image as Drawable
				// Drawable d = Drawable.createFromStream(ims, null);
				// // set image to ImageView
				// imageView[j].setImageDrawable(d);
				// } catch (IOException ex) {
				//
				// }
				// }
				// } else {
				// try {
				// // get input stream
				//
				// String str = packagesItem.icon.replace("\'", "");
				// String str1 = str.replace(
				// "http://ttourdev.etisbew.net/images/", "");
				// InputStream ims = getApplicationContext().getAssets()
				// .open(str1.trim());
				// // load image as Drawable
				// Drawable d = Drawable.createFromStream(ims, null);
				// // set image to ImageView
				// imageView[j].setImageDrawable(d);
				// } catch (IOException ex) {
				//
				// }
				// }
			}

			imageView[j].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// startActivity(new Intent(getApplicationContext(),
					// SubDetailScreen.class));
				}
			});

			txtName[j] = new TextView(context);
			txtName[j].setTypeface(Constants.ProximaNova_Regular);
			txtName[j].setText(Html.fromHtml("<b>"
					+ URLDecoder.decode(packagesItem.title) + "</b>"));
			txtName[j].setTypeface(Constants.ProximaNova_Regular);

			txtName[j].setTextColor(Color.parseColor("#CC5C50"));
			// txtName[j].setTextSize(18);

			// txtName[j].setPadding(5, 0, 5, 5);
			txtName[j].setGravity(Gravity.CENTER_VERTICAL);
			txtName[j].setId(j);

			layoutLL[j].addView(txtName[j]);

			txtLine[j] = new TextView(context);
			txtLine[j].setTypeface(Constants.ProximaNova_Regular);
			txtLine[j].setText(URLDecoder.decode(packagesItem.description));
			txtLine[j].setTypeface(Constants.ProximaNova_Regular);
			// txtName[j].setTextSize(14);

			txtLine[j].setTextColor(Color.parseColor("#848283"));
			txtLine[j].setGravity(Gravity.CENTER_VERTICAL);
			txtLine[j].setSingleLine(false);
			txtLine[j].setEllipsize(TruncateAt.END);
			int n = 1; // the exact number of lines you want to display
			txtLine[j].setLines(1);
			// txtLine[j].setLayoutParams(sublinLayoutParam);
			txtLine[j].setId(j);

			layoutLL[j].addView(txtLine[j]);

			LinearLayout.LayoutParams sublinLayoutParam1 = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			sublinLayoutParam1.setMargins(0, 20, 0, 0);
			sublinLayoutParam1.gravity = Gravity.BOTTOM;
			layoutLLButton[j] = new LinearLayout(context);
			layoutLLButton[j].setLayoutParams(sublinLayoutParam1);
			layoutLLButton[j].setGravity(Gravity.BOTTOM);
			layoutLLButton[j].setOrientation(LinearLayout.HORIZONTAL);

			LinearLayout.LayoutParams sublinLayoutParam2 = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			sublinLayoutParam2.setMargins(30, 0, 0, 0);
			// sublinLayoutParam2.weight = 1;

			btnName[j] = new TextView(context);
			btnName[j].setTypeface(Constants.ProximaNova_Regular);
			// btnName[j].setLayoutParams(sublinLayoutParam2);
			btnName[j].setText(getResources().getString(
					R.string.details_readmore));
			btnName[j].setBackgroundResource(R.drawable.rounded_rect3);
			// btnName[j].setTextSize(14);

			btnName[j].setTextColor(Color.WHITE);
			btnName[j].setGravity(Gravity.CENTER_VERTICAL
					| Gravity.CENTER_HORIZONTAL);
			btnName[j].setTag(packagesItem.description);
			btnName[j].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					abt = v.getTag().toString();

					Intent n = new Intent(DetailScreen1.this,
							ReadmoreActivity.class);
					startActivity(n);
					overridePendingTransition(R.anim.trans_left_in,
							R.anim.trans_left_out);
				}
			});

			layoutLLButton[j].addView(btnName[j]);

			btnLine[j] = new TextView(context);
			btnLine[j].setTypeface(Constants.ProximaNova_Regular);
			btnLine[j].setText(getResources().getString(
					R.string.details_book_now));
			btnLine[j].setSingleLine(true);
			btnLine[j].setEllipsize(TextUtils.TruncateAt.END);
			// btnLine[j].setTextSize(14);

			if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID,
					"").equalsIgnoreCase("1")) {
				txtName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				txtLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				btnName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				btnLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			} else if (SplashScreen.fontpref.getString(
					SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
				txtName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				txtLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				btnName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				btnLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			} else if (SplashScreen.fontpref.getString(
					SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
				txtName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				txtLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				btnName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				btnLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			}
			btnLine[j].setTextColor(Color.WHITE);
			btnLine[j].setBackgroundResource(R.drawable.blue_btn);
			btnLine[j].setGravity(Gravity.CENTER_VERTICAL
					| Gravity.CENTER_HORIZONTAL);
			btnLine[j].setLayoutParams(sublinLayoutParam2);
			btnLine[j].setId(j);

			if (TextUtils.isEmpty(packagesItem.packageUrl)
					|| packagesItem.packageUrl.length() == 0) {
				btnLine[j].setVisibility(View.GONE);
			} else {
				btnLine[j].setVisibility(View.VISIBLE);
			}

			btnLine[j].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// package_url
					String url = packagesItem.packageUrl;
					if (!url.startsWith("http://")
							&& !url.startsWith("https://"))
						url = "http://" + url;
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(packagesItem.packageUrl));
					startActivity(browserIntent);
				}
			});

			layoutLLButton[j].addView(btnLine[j]);

			gettxtLine[j] = new TextView(context);
			gettxtLine[j].setTypeface(Constants.ProximaNova_Regular);
			gettxtLine[j].setBackgroundColor(Color.parseColor("#939393"));
			gettxtLine[j].setGravity(Gravity.CENTER_VERTICAL);
			gettxtLine[j].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, 2));
			gettxtLine[j].setId(j);

			layoutLL[j].addView(layoutLLButton[j]);
			imgLayout[j].addView(imageView[j]);
			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				layoutLL[j].setPadding(0, 0, 6, 0);
				rootLayout[j].addView(layoutLL[j]);
				rootLayout[j].addView(imgLayout[j]);

			} else {
				rootLayout[j].addView(imgLayout[j]);
				rootLayout[j].addView(layoutLL[j]);
			}

			packagesLay.addView(rootLayout[j]);
		}
	}

	public void getAccomodationList(final Context context) {

		LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 2f);
		linLayoutParam.gravity = Gravity.CENTER_VERTICAL;
		placesStayLayout.removeAllViews();
		txtName = new TextView[accomodationList.size()];
		txtLine = new TextView[accomodationList.size()];
		btnName = new TextView[accomodationList.size()];
		btnLine = new TextView[accomodationList.size()];
		imageView = new ImageView[accomodationList.size()];
		rootLayout = new LinearLayout[accomodationList.size()];
		layoutLL = new LinearLayout[accomodationList.size()];
		imgLayout = new LinearLayout[accomodationList.size()];
		layoutLLButton = new LinearLayout[accomodationList.size()];
		gettxtLine = new TextView[accomodationList.size()];

		for (int j = 0; j < accomodationList.size(); j++) {
			AccomodationItem = accomodationList.get(j);

			rootLayout[j] = new LinearLayout(context);
			rootLayout[j].setOrientation(LinearLayout.HORIZONTAL);
			linLayoutParam.setMargins(0, 10, 0, 0);
			rootLayout[j].setLayoutParams(linLayoutParam);
			rootLayout[j].setBackgroundColor(Color.WHITE);
			rootLayout[j].setPadding(10, 10, 10, 10);

			LinearLayout.LayoutParams sublinLayoutParam = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			sublinLayoutParam.weight = (float) .5;
			sublinLayoutParam.gravity = Gravity.CENTER_VERTICAL;
			sublinLayoutParam.setMargins(15, 0, 0, 5);
			layoutLL[j] = new LinearLayout(context);
			layoutLL[j].setLayoutParams(sublinLayoutParam);
			layoutLL[j].setOrientation(LinearLayout.VERTICAL);

			LinearLayout.LayoutParams sublinLayoutParam0 = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			sublinLayoutParam0.weight = (float) 1.5;
			sublinLayoutParam0.gravity = Gravity.CENTER_VERTICAL;
			// sublinLayoutParam0.setMargins(10, 0, 0, 0);
			imgLayout[j] = new LinearLayout(context);
			imgLayout[j].setLayoutParams(sublinLayoutParam0);
			// imgLayout[j].setPadding(0, 5, 0, 0);
			imgLayout[j].setOrientation(LinearLayout.VERTICAL);
			imgLayout[j].setGravity(Gravity.CENTER_VERTICAL);

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					200, 150);
			// sublinLayoutParam0.setMargins(0,5, 0, 0);
			layoutParams.gravity = Gravity.CENTER_VERTICAL;

			imageView[j] = new ImageView(context);
			imageView[j].setLayoutParams(layoutParams);
			// imageView[j].setBackgroundResource(R.drawable.ls4);
			imageView[j].setScaleType(ScaleType.FIT_XY);
			// Picasso.with(context)
			// .load(AccomodationItem.icon.replace("\'", "")
			// .replace("%20", "").replace(" ", "").trim())
			// .error(R.drawable.default_img).into(imageView[j]);
 
			// try {
			// // get input stream
			//
			// String str = AccomodationItem.icon.replace("\'", "");
			// // String str1 = str.replace(
			// // "http://ttourdev.etisbew.net/images/", "");
			// InputStream ims = getApplicationContext().getAssets().open(
			// str.trim());
			// // load image as Drawable
			// Drawable d = Drawable.createFromStream(ims, null);
			// // set image to ImageView
			// imageView[j].setImageDrawable(d);
			// } catch (IOException ex) {
			//
			// }

			if (AccomodationItem.icon.length() > 0) {

				// if (AccomodationItem.icon.contains("http")) {
				//
				// if (utils.IsNetConnected(getApplicationContext())) {
				if(AccomodationItem.icon.contains("http")){
				Picasso.with(getApplicationContext())
						.load(AccomodationItem.icon.replace("\'", "%20").trim())
						.noFade().error(R.drawable.default_img)
						.into(imageView[j]);
				}else{
					Picasso.with(getApplicationContext())
					.load(SplashScreen.Base_url2+AccomodationItem.icon.replace("\'", "%20").trim())
					.noFade().error(R.drawable.default_img)
					.into(imageView[j]);
				}
//				Picasso.with(getApplicationContext())
//				.load(SplashScreen.Base_url2+AccomodationItem.icon.replace("\'", "%20").trim())
//				.noFade().error(R.drawable.default_img)
//				.into(imageView[j]);

				// } else {
				// try {
				// // get input stream
				//
				// String str = AccomodationItem.icon
				// .replace("\'", "");
				// String str1 = str.replace(
				// "http://ttourdev.etisbew.net/images/", "");
				// InputStream ims = getApplicationContext()
				// .getAssets().open(str1.trim());
				// // load image as Drawable
				// Drawable d = Drawable.createFromStream(ims, null);
				// // set image to ImageView
				// imageView[j].setImageDrawable(d);
				// } catch (IOException ex) {
				//
				// }
				// }
				// } else {
				// try {
				// // get input stream
				//
				// String str = packagesItem.icon.replace("\'", "");
				// String str1 = str.replace(
				// "http://ttourdev.etisbew.net/images/", "");
				// InputStream ims = getApplicationContext().getAssets()
				// .open(str1.trim());
				// // load image as Drawable
				// Drawable d = Drawable.createFromStream(ims, null);
				// // set image to ImageView
				// imageView[j].setImageDrawable(d);
				// } catch (IOException ex) {
				//
				// }
				// }
			}

			txtName[j] = new TextView(context);
			txtName[j].setTypeface(Constants.ProximaNova_Regular);
			// txtName[j].setText("Hotel Marriott");
			txtName[j].setText(Html.fromHtml("<b>"
					+ URLDecoder.decode(AccomodationItem.title) + "</b>"));
			txtName[j].setTypeface(Constants.ProximaNova_Regular);
			txtName[j].setTextColor(Color.parseColor("#CC5C50"));
			// txtName[j].setTextSize(16);

			// txtName[j].setPadding(5, 0, 5, 5);
			txtName[j].setGravity(Gravity.CENTER_VERTICAL);
			txtName[j].setId(j);
			txtName[j].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// Toast.makeText(context, "first text " + v.getId(),
					// Toast.LENGTH_SHORT).show();
				}
			});
			layoutLL[j].addView(txtName[j]);

			txtLine[j] = new TextView(context);
			txtLine[j].setTypeface(Constants.ProximaNova_Regular);
			txtLine[j].setText(URLDecoder.decode(AccomodationItem.address));
			// txtLine[j].setPadding(5, 0, 5, 5);
			txtLine[j].setTypeface(Constants.ProximaNova_Regular);
			// txtName[j].setTextSize(14);

			txtLine[j].setTextColor(Color.parseColor("#848283"));
			txtLine[j].setGravity(Gravity.CENTER_VERTICAL);
			txtLine[j].setSingleLine(false);
			txtLine[j].setEllipsize(TruncateAt.END);
			int n = 1; // the exact number of lines you want to display
			txtLine[j].setLines(n);
			// txtLine[j].setLayoutParams(sublinLayoutParam);
			txtLine[j].setId(j);
			txtLine[j].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// Toast.makeText(context, "second text " + v.getId(),
					// Toast.LENGTH_SHORT).show();
				}
			});

			layoutLL[j].addView(txtLine[j]);

			LinearLayout.LayoutParams sublinLayoutParam1 = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			sublinLayoutParam1.setMargins(0, 20, 0, 0);
			layoutLLButton[j] = new LinearLayout(context);
			layoutLLButton[j].setLayoutParams(sublinLayoutParam1);
			layoutLLButton[j].setOrientation(LinearLayout.HORIZONTAL);

			LinearLayout.LayoutParams sublinLayoutParam2 = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			sublinLayoutParam2.setMargins(30, 0, 0, 0);
			// sublinLayoutParam2.weight = 1;

			btnName[j] = new TextView(context);
			btnName[j].setTypeface(Constants.ProximaNova_Regular);
			// btnName[j].setLayoutParams(sublinLayoutParam2);
			btnName[j].setText(getResources().getString(
					R.string.details_book_online));
			btnName[j].setSingleLine(true);
			btnName[j].setEllipsize(TextUtils.TruncateAt.END);
			btnName[j].setBackgroundResource(R.drawable.rounded_rect4);
			// btnName[j].setTextSize(14);

			btnName[j].setTextColor(Color.WHITE);
			// btnName[j].setPadding(10, 5, 5, 10);
			btnName[j].setGravity(Gravity.CENTER_VERTICAL
					| Gravity.CENTER_HORIZONTAL);
			btnName[j].setId(j);
			if (TextUtils.isEmpty(AccomodationItem.bookUrl)
					|| AccomodationItem.bookUrl.length() == 0) {
				btnName[j].setVisibility(View.GONE);
			} else {
				btnName[j].setVisibility(View.VISIBLE);
			}

			btnName[j].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Toast.makeText(context, "first button " + v.getId(),
					// Toast.LENGTH_SHORT).show();
					String url = AccomodationItem.bookUrl;
					if (!url.startsWith("http://")
							&& !url.startsWith("https://"))
						url = "http://" + url;
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(AccomodationItem.bookUrl));
					startActivity(browserIntent);
				}
			});

			layoutLLButton[j].addView(btnName[j]);

			btnLine[j] = new TextView(context);
			btnLine[j].setTypeface(Constants.ProximaNova_Regular);
			btnLine[j].setText(getResources().getString(
					R.string.details_getdirections));
			// btnLine[j].setTextSize(14);

			if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID,
					"").equalsIgnoreCase("1")) {
				txtName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				txtLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				btnName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				btnLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			} else if (SplashScreen.fontpref.getString(
					SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
				txtName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				txtLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				btnName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				btnLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			} else if (SplashScreen.fontpref.getString(
					SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
				txtName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				txtLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				btnName[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				btnLine[j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			}
			btnLine[j].setSingleLine(true);
			btnLine[j].setEllipsize(TextUtils.TruncateAt.END);
			btnLine[j].setTextColor(Color.WHITE);
			btnLine[j].setBackgroundResource(R.drawable.blue_btn);
			btnLine[j].setGravity(Gravity.CENTER_VERTICAL
					| Gravity.CENTER_HORIZONTAL);
			btnLine[j].setLayoutParams(sublinLayoutParam2);
			btnLine[j].setId(j);

			if (AccomodationItem.acclat.length() == 0
					|| AccomodationItem.acclng.length() == 0
					|| TextUtils.isEmpty(AccomodationItem.acclat)
					|| TextUtils.isEmpty(AccomodationItem.acclng)) {

				btnLine[j].setVisibility(View.GONE);

			} else {
				btnLine[j].setVisibility(View.VISIBLE);

			}

			btnLine[j].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Toast.makeText(context, "second button " + v.getId(),
					// Toast.LENGTH_SHORT).show();

					// Intent intent = new Intent(
					// android.content.Intent.ACTION_VIEW,
					// Uri.parse("http://maps.google.com/maps?saddr="
					// + Constants.latitude + ","
					// + Constants.longitude + "&daddr="
					// + accomodationList.get(v.getId()).acclat
					// + ","
					// + accomodationList.get(v.getId()).acclng));
					// intent.setClassName("com.google.android.apps.maps",
					// "com.google.android.maps.MapsActivity");
					// startActivity(intent);

					final Dialog dialog = new Dialog(DetailScreen1.this,
							android.R.style.Theme_Translucent_NoTitleBar);
					// android.R.style.Theme_Translucent_NoTitleBar);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.map_dialog);

					RelativeLayout headerLayout = (RelativeLayout) dialog
							.findViewById(R.id.headerLayout);

					headerLayout.setBackgroundColor(Color
							.parseColor(MainActivity.changeColorStr));

					TextView titleTxt1 = (TextView) dialog
							.findViewById(R.id.tit_txt);
					titleTxt1.setTypeface(Constants.ProximaNova_Regular);

					titleTxt1.setText(URLDecoder.decode(accomodationList.get(v
							.getId()).title));
					titleTxt1.setTypeface(Constants.ProximaNova_Regular);

					WebView webview = (WebView) dialog
							.findViewById(R.id.webView1);
					webview.setWebViewClient(new WebViewClient());
					webview.getSettings().setJavaScriptEnabled(true);

					// if(accomodationList.get(v.getId()).acclat.length() ==
					// 0 || accomodationList.get(v.getId()).acclng.length() == 0
					// ||
					//
					// TextUtils.isEmpty(accomodationList.get(v.getId()).acclat)
					// ||
					// TextUtils.isEmpty(accomodationList.get(v.getId()).acclng){
					//
					// }else{

					webview.loadUrl("http://maps.google.com/maps?" + "saddr="
							+ Constants.latitude + "," + Constants.longitude
							+ "&daddr="
							+ accomodationList.get(v.getId()).acclat + ","
							+ accomodationList.get(v.getId()).acclng);

					// }

					Button closeBtN = (Button) dialog
							.findViewById(R.id.closeBtn);
					closeBtN.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					dialog.show();

				}
			});

			layoutLLButton[j].addView(btnLine[j]);

			// LinearLayout.LayoutParams sublinLayoutParam1 = new
			// LinearLayout.LayoutParams(
			// LayoutParams.FILL_PARENT, 2);
			// sublinLayoutParam1.setMargins(0, 0, 0, 5);
			gettxtLine[j] = new TextView(context);
			gettxtLine[j].setTypeface(Constants.ProximaNova_Regular);
			gettxtLine[j].setBackgroundColor(Color.parseColor("#939393"));
			gettxtLine[j].setGravity(Gravity.CENTER_VERTICAL);
			gettxtLine[j].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, 2));
			gettxtLine[j].setId(j);

			layoutLL[j].addView(layoutLLButton[j]);
			imgLayout[j].addView(imageView[j]);
			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				layoutLL[j].setPadding(0, 0, 6, 0);
				rootLayout[j].addView(layoutLL[j]);
				rootLayout[j].addView(imgLayout[j]);

			} else {
				rootLayout[j].addView(imgLayout[j]);
				rootLayout[j].addView(layoutLL[j]);
			}

			// rootLayout[j].addView(layoutLLButton[j]);
			placesStayLayout.addView(rootLayout[j]);
		}
	}

	private class DestinationDetailsTask extends
			AsyncTask<String, Void, String> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			pDialog = new ProgressDialog(DetailScreen1.this);
//			pDialog.setMessage("Loading please wait...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {

			// return ServiceCalls1.getJSONString(Constants.Base_url
			// + "getDestinationsDetail/" + Constants.locationId + "/"
			// + Constants.id + "/" + Constants.latitude + "/"+
			// Constants.longitude);

			try {
				dbbHelper = new TtHelper(DetailScreen1.this);
				dbbHelper.openDataBase();

				String select;
				if (Constants.sublistFlag == 6) {
					select = " SELECT * FROM Shop_Online_Details WHERE ModuleUniqueName="
							+ Constants.id
							+ " AND language_id="
							+ Constants.selectLanguage;
				} else {
					select = " SELECT * FROM Destinations_Detail_Content WHERE ModuleUniqueName="
							+ Constants.id;
				}

				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
						select, null);

				startManagingCursor(constantCursor);

				if (constantCursor.getCount() > 0) {
					if (constantCursor.moveToFirst()) {
						// do {

						responseString = constantCursor
								.getString(constantCursor
										.getColumnIndex("ResponseContent"));

						// } while (constantCursor.moveToNext());

					}

				}

				// dbbHelper.close();

				// dbbHelper.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return responseString;

		}

		@Override
		protected void onPostExecute(String jsonResult) {
			System.out.println(" result " + jsonResult);

			try {

				JSONObject jsonObjMain = new JSONObject(jsonResult);
				mViewFlipper.removeAllViews();

				if (jsonObjMain.has("message")) {
//					if (pDialog.isShowing() && pDialog != null) {
//						pDialog.dismiss();
//					}
				} else {

					if (jsonObjMain.has("SeeAlso")) {
						// Creating JSONArray from JSONObject

						JSONArray resultjsonArray = jsonObjMain
								.getJSONArray("SeeAlso");

						seeAlsoList = new ArrayList<SeeAlsoItems>();
						packagesList = new ArrayList<SeeAlsoItems>();
						accomodationList = new ArrayList<SeeAlsoItems>();
						attractionsList = new ArrayList<SeeAlsoItems>();
						attractionsImagesList = new ArrayList<SeeAlsoItems>();

						for (int i = 0; i < resultjsonArray.length(); i++) {

							JSONObject jsonObject = resultjsonArray
									.getJSONObject(i);

							System.out.println("ids are :"
									+ jsonObject.getString("id"));
							// System.out.println("image paths are :"
							// + jsonObject.getString("image_path"));

							System.out.println("titles are :"
									+ jsonObject.getString("title"));
							SeeAlsoItems seeAlsoItems = new SeeAlsoItems();

							try {
								if (jsonObject.has("image_path")) {

									// jsonObject.optJSONArray("image_path");

									// Object json = new JSONTokener(jsonObject
									// .getJSONArray("image_path").get(0)
									// .toString()).nextValue();

									// if (json instanceof JSONObject)
									// //you have an object
									// else
									if (jsonObject.optJSONObject("image_path") == null)
									// you have an array
									{
										seeAlsoItems.icon = jsonObject
												.getJSONArray("image_path")
												.get(0).toString();
										seeAlsoItems.id = jsonObject
												.getString("id");
										seeAlsoList.add(seeAlsoItems);
									} else {
										jsonObject.optString("image_path");
									}
									// if(!TextUtils.isEmpty(jsonObject
									// .getJSONArray("image_path")
									// .get(0).toString())){
									//
									// }else{
									// if (jsonObject.getJSONArray("image_path")
									// .length() > 0) {
									//
									//
									// }
									// }

								}

							} catch (JSONException je) {
								// TODO: handle exception
								je.printStackTrace();
							}

						}
					}
					if (jsonObjMain.has("Package")) {
						JSONArray jsonPackagesArray = jsonObjMain
								.getJSONArray("Package");

						if (jsonPackagesArray.length() == 0) {
							packagesBtn.setVisibility(View.GONE);
						} else {
//							packagesBtn.setVisibility(View.VISIBLE);
							packagesBtn.setVisibility(View.GONE);
							
						}

						for (int j = 0; j < jsonPackagesArray.length(); j++) {

							JSONObject jsonPackagesObject = jsonPackagesArray
									.getJSONObject(j);
							SeeAlsoItems packagesItems = new SeeAlsoItems();

							System.out.println("packages ids are :"
									+ jsonPackagesObject.getString("id"));
							packagesItems.id = jsonPackagesObject
									.getString("id");
							System.out.println("packages description are :"
									+ jsonPackagesObject
											.getString("description"));
							packagesItems.description = jsonPackagesObject
									.getString("description");
							abt = packagesItems.description.trim();
							tit = packagesItems.title;
							System.out.println("packages titles are :"
									+ jsonPackagesObject.getString("title"));
							packagesItems.title = jsonPackagesObject
									.getString("title");
							System.out.println("packages image paths are :"
									+ jsonPackagesObject.getString("image"));
							packagesItems.icon = jsonPackagesObject
									.getString("image");
							packagesItems.packageUrl = jsonPackagesObject
									.getString("package_url");

							packagesList.add(packagesItems);
						}
					} else {
						packagesBtn.setVisibility(View.GONE);
					}

					if (jsonObjMain.has("Accomodation")) {
						JSONArray jsonAccomodationArray = jsonObjMain
								.getJSONArray("Accomodation");

						if (jsonAccomodationArray.length() == 0) {
							placeStayBtn.setVisibility(View.GONE);
						} else {
//							placeStayBtn.setVisibility(View.VISIBLE);
							placeStayBtn.setVisibility(View.GONE);
						}

						for (int j = 0; j < jsonAccomodationArray.length(); j++) {

							JSONObject jsonAccomodationObject = jsonAccomodationArray
									.getJSONObject(j);
							SeeAlsoItems AccomodationItems = new SeeAlsoItems();

							System.out.println("Accomodation ids are :"
									+ jsonAccomodationObject.getString("id"));
							AccomodationItems.id = jsonAccomodationObject
									.getString("id");
							AccomodationItems.title = jsonAccomodationObject
									.getString("title");
							AccomodationItems.address = jsonAccomodationObject
									.getString("address");
							System.out.println("Accomodation image paths are :"
									+ jsonAccomodationObject
											.getString("image_path"));
							// AccomodationItems.icon = jsonAccomodationObject
							// .getString("image_path");

							try {
								if (jsonAccomodationObject.getJSONArray(
										"image_path").length() > 0) {
									AccomodationItems.icon = jsonAccomodationObject
											.getJSONArray("image_path").get(0)
											.toString();
								}

							} catch (JSONException je) {
								// TODO: handle exception
								je.printStackTrace();
							}

							System.out.println("Accomodation latitude are :"
									+ jsonAccomodationObject
											.getString("latitude"));
							System.out.println("Accomodation longitude are :"
									+ jsonAccomodationObject
											.getString("longitude"));

							AccomodationItems.acclat = jsonAccomodationObject
									.getString("latitude");

							AccomodationItems.acclng = jsonAccomodationObject
									.getString("longitude");

							if (jsonAccomodationObject.has("book_url")) {
								System.out
										.println("Accomodation book_url are :"
												+ jsonAccomodationObject
														.getString("book_url"));
								AccomodationItems.bookUrl = jsonAccomodationObject
										.getString("book_url");
							}
							accomodationList.add(AccomodationItems);
						}
					} else {

						placeStayBtn.setVisibility(View.GONE);

					}

					if (jsonObjMain.has("Attractions")) {

						JSONObject jsonObject = jsonObjMain
								.getJSONObject("Attractions");

						SeeAlsoItems AttractionsItems = new SeeAlsoItems();

						System.out.println("Attractions ids are :"
								+ jsonObject.getString("id"));
						AttractionsItems.id = jsonObject.getString("id");
						AttractionsItems.title = jsonObject.getString("title");
						AttractionsItems.address = jsonObject
								.getString("address");
						AttractionsItems.about = jsonObject.getString("about")
								.toString().trim();

						// Favroties

						tit = jsonObject.getString("title");
						lat = jsonObject.getString("latitude");
						lng = jsonObject.getString("longitude");
						id = jsonObject.getString("id");
						address = jsonObject.getString("address");
						abt = AttractionsItems.about;

						try {
							if (jsonObject.getJSONArray("image_path").length() > 0) {
								image_path = jsonObject
										.getJSONArray("image_path").get(0)
										.toString();
							}

						} catch (JSONException je) {
							// TODO: handle exception
							je.printStackTrace();
						}

						// Favourites End

						attractionsList.add(AttractionsItems);

						System.out.println("Attractions latitude are :"
								+ jsonObject.getString("latitude"));
						System.out.println("Attractions longitude are :"
								+ jsonObject.getString("longitude"));

						System.out.println("Attractions by_bus are :"
								+ jsonObject.getString("by_bus"));

						System.out.println("Attractions by_train are :"
								+ jsonObject.getString("by_train"));
						System.out.println("Attractions by_air are :"
								+ jsonObject.getString("by_air"));

						JSONArray jsonAttrImagesArray = jsonObject
								.getJSONArray("image_path");

						titleTxt.setText(URLDecoder
								.decode(AttractionsItems.title));
						titleTxt.setTypeface(Constants.ProximaNova_Regular);

						if (AttractionsItems.about.equalsIgnoreCase("null")
								|| TextUtils.isEmpty(AttractionsItems.about)) {
							aboutLayout.setVisibility(View.GONE);
							aboutBtn.setVisibility(View.GONE);
						} else {
							aboutLayout.setVisibility(View.VISIBLE);
							aboutBtn.setVisibility(View.VISIBLE);
							aboutTxt.setVisibility(View.VISIBLE);
							if (Constants.selectLanguage.equalsIgnoreCase("1")) {
								aboutBtn.setText("About "
										+ URLDecoder
												.decode(AttractionsItems.title));
							} else {
								aboutBtn.setText(URLDecoder
										.decode(AttractionsItems.title)
										+ " "
										+ getResources().getString(
												R.string.details_about));
							}

							aboutBtn.setTypeface(Constants.ProximaNova_Regular);
							aboutTxt.setText(Html.fromHtml(URLDecoder.decode(
									AttractionsItems.about).replace("&nbsp;",
									" ")));
							aboutTxt.setTypeface(Constants.ProximaNova_Regular);
						}

						if (TextUtils.isEmpty(jsonObject.getString("by_bus"))
								&& TextUtils.isEmpty(jsonObject
										.getString("by_train"))
								&& TextUtils.isEmpty(jsonObject
										.getString("by_air"))) {
							gettingBtn.setVisibility(View.GONE);
						} else {
							gettingBtn.setVisibility(View.VISIBLE);
							if (!TextUtils.isEmpty(jsonObject
									.getString("by_bus"))) {
								byBusTxt.setText(URLDecoder.decode(jsonObject
										.getString("by_bus")));
								byBusTxt.setTypeface(Constants.ProximaNova_Regular);
							}
							if (!TextUtils.isEmpty(jsonObject
									.getString("by_train"))) {
								byTrainTxt.setText(URLDecoder.decode(jsonObject
										.getString("by_train")));
								byTrainTxt
										.setTypeface(Constants.ProximaNova_Regular);
							}
							if (!TextUtils.isEmpty(jsonObject
									.getString("by_air"))) {
								byCarTxt.setText(URLDecoder.decode(jsonObject
										.getString("by_air")));
								byCarTxt.setTypeface(Constants.ProximaNova_Regular);
							}
						}
						for (int j = 0; j < jsonAttrImagesArray.length(); j++) {

							SeeAlsoItems attractionImages = new SeeAlsoItems();
							attractionImages.icon = jsonAttrImagesArray
									.getString(j);
							// attractionsImagesList.add(attractionImages);

							settingImageToViewFlipper(j, attractionImages.icon,
									AttractionsItems.title);
						}

						getPackagesList(DetailScreen1.this);

						getAccomodationList(DetailScreen1.this);

						if (seeAlsoList.size() == 0) {
							seeAlsoLay.setVisibility(View.GONE);
						} else {
							seeAlsoLay.setVisibility(View.VISIBLE);
							g.setAdapter(new ImageAdapter(DetailScreen1.this));

							// g.setSelection(1, true);

							if (seeAlsoList.size() == 1) {
								g.setSelection(0, true);
							} else {
								g.setSelection(1, true);
							}
						}

						if (packagesList.size() == 0) {
							// packagesLay.setVisibility(View.GONE);
							packagesBtn.setVisibility(View.GONE);
						} else {
							// packagesLay.setVisibility(View.VISIBLE);
//							packagesBtn.setVisibility(View.VISIBLE);
							packagesBtn.setVisibility(View.GONE);
						}
						// if (MainActivity.detailNavFlag == 1) {
						// g.setAdapter(new ImageAdapter1(DetailScreen1.this));
						// } else if (MainActivity.detailNavFlag == 2) {
						// g.setAdapter(new ImageAdapter(DetailScreen1.this));
						// }

						if (pDialog.isShowing() && pDialog != null) {
							pDialog.dismiss();
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
//				if (pDialog.isShowing() && pDialog != null) {
//					pDialog.dismiss();
//				}
			}
		}
	}

	private class PackagesTask extends AsyncTask<String, Void, String> {
//		private ProgressDialog pDialog;
		String url;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			pDialog = new ProgressDialog(DetailScreen1.this);
//			pDialog.setMessage("Loading please wait...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
			// http://172.16.0.49/telangana_tourism/WebServices/getPackageDetails/52
			// Log.e("tag",
			// "http://172.16.0.49/telangana_tourism/WebServices/getPackageDetails/"
			// + Constants.id);
		}

		@Override
		protected String doInBackground(String... args) {

			// return ServiceCalls1.getJSONString(Constants.Base_url
			// + "getPackageDetails/" + Constants.id);

			try {
				dbbHelper = new TtHelper(DetailScreen1.this);
				dbbHelper.openDataBase();

				String select = " SELECT * FROM TourPackagesDetailContent WHERE ModuleUniqueName="
						+ Constants.id
						+ " AND language_id="
						+ Constants.selectLanguage;
				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
						select, null);
				System.out.println("url string "+select);

				startManagingCursor(constantCursor);

				if (constantCursor.getCount() > 0) {
					if (constantCursor.moveToFirst()) {
						// do {

						responseString = constantCursor
								.getString(constantCursor
										.getColumnIndex("ResponseContent"));

						// } while (constantCursor.moveToNext());

					}

				}

				// dbbHelper.close();

				// dbbHelper.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return responseString;
		}

		@Override
		protected void onPostExecute(String jsonResult) {
			System.out.println(" result " + jsonResult);

//			if (pDialog.isShowing() && pDialog != null) {
//				pDialog.dismiss();
//			}
			try {

				JSONObject jsonObjMain = new JSONObject(jsonResult);
				mViewFlipper.removeAllViews();
				toursList = new ArrayList<SeeAlsoItems>();
				
				if(jsonObjMain.has("TourType")){
					JSONArray jsonPackagesArray = jsonObjMain
							.getJSONArray("TourType");// ("Package");
					String str = "";
					for (int j = 0; j < jsonPackagesArray.length(); j++) {

						JSONObject jsonPackagesObject = jsonPackagesArray
								.getJSONObject(j);
						SeeAlsoItems touresItems = new SeeAlsoItems();
						touresItems.tour_type = jsonPackagesObject
								.getString("tour_type");
						touresItems.fare_type = jsonPackagesObject
								.getString("fare_type");
						touresItems.cost = jsonPackagesObject.getString("cost");
						toursList.add(touresItems);

						String fareType = "";

						// if(URLDecoder.decode(touresItems.fare_type).equalsIgnoreCase("null")){
						// fareType = "Adult";
						// }else{
						// fareType = URLDecoder.decode(touresItems.fare_type);
						// }
						str = str
								+ "<p><font size='3' color='#939393'><b>Tickets ("
								+ URLDecoder.decode(touresItems.tour_type)
								+ ") : </b></font><font size='3' color='#939393'> "
								+ URLDecoder.decode(touresItems.fare_type).replace(
										"null", "Adult")
								+ "&nbsp; &nbsp; </b></font> <font size='3' color='red'><b>Rs. "
								+ URLDecoder.decode(touresItems.cost)
								+ "</b></font></p>";
						System.out.println(" string for tour type " + str);
					}
				}

				

				// if (str.length() > 0) {
				// roomBtn.setVisibility(View.VISIBLE);
				// roomBtn.setText(getResources().getString(
				// R.string.details_tickets));
				// roomBtn.setTypeface(Constants.ProximaNova_Regular);
				// roomTxt.setText(Html.fromHtml("<html lang='en-US'><body>"
				// + str + "</body></html"));
				// roomTxt.setTypeface(Constants.ProximaNova_Regular);
				// } else {
				roomBtn.setVisibility(View.GONE);
				// }

				if (jsonObjMain.has("Package")) {
					JSONObject jsonPackagesObject = jsonObjMain
							.getJSONObject("Package");

					// SeeAlsoItems packagesItems = new SeeAlsoItems();

					System.out.println("packages description are :"
							+ jsonPackagesObject.getString("description"));

					tit = jsonPackagesObject.getString("title");
					abt = jsonPackagesObject.getString("description").trim();
					url = jsonPackagesObject.getString("package_url");

//					if (TextUtils.isEmpty(url) || url.length() == 0) {
//						buyticketBtn.setVisibility(View.GONE);
//					} else {
//						buyticketBtn.setVisibility(View.VISIBLE);
//						buyticketBtn.setOnClickListener(new OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								// if (!url.startsWith("http://")
//								// && !url.startsWith("https://"))
//								// url = "http://" + url;
//								// Intent browserIntent = new Intent(
//								// Intent.ACTION_VIEW, Uri.parse(url));
//								// startActivity(browserIntent);
//
//								ArrayList<String> lists = new ArrayList<String>();
//								/*
//								 * given string will be split by the argument
//								 * delimiter provided.
//								 */
//
//								if (SplashScreen.hotel_pref.getString(
//										SplashScreen.GET_PACKAGE_IDS, "")
//										.length() > 0) {
//									String[] temp = SplashScreen.hotel_pref
//											.getString(
//													SplashScreen.GET_PACKAGE_IDS,
//													"").split(",");
//									/* print substrings */
//									lists.clear();
//									for (int i = 0; i < temp.length; i++) {
//										System.out.println(temp[i]);
//										lists.add(temp[i]);
//									}
//
//									if (lists.contains(Constants.new_pack_id)) {
//
//										// Toast.makeText(DetailScreen1.this,
//										// "found",
//										// Toast.LENGTH_LONG).show();
//
//										startActivity(new Intent(
//												DetailScreen1.this,
//												PackageListing.class));
//
//										// finish();
//
//									} else {
//
//										AlertDialog.Builder altDialog = new AlertDialog.Builder(
//												DetailScreen1.this);
//										altDialog
//												.setMessage("This service is presently not available"); // here
//										// add
//										// your
//										// message
//										altDialog
//												.setNeutralButton(
//														"OK",
//														new DialogInterface.OnClickListener() {
//															@Override
//															public void onClick(
//																	DialogInterface dialog,
//																	int which) {
//																dialog.dismiss();
//															}
//														});
//										altDialog.show();
//									}
//								}
//
//								//
//								// finish();
//							}
//						});
//					}
					
					ArrayList<String> lists = new ArrayList<String>();
					/*
					 * given string will be split by the argument
					 * delimiter provided.
					 */

					if (SplashScreen.hotel_pref.getString(
							SplashScreen.GET_PACKAGE_IDS, "")
							.length() > 0) {
						String[] temp = SplashScreen.hotel_pref
								.getString(
										SplashScreen.GET_PACKAGE_IDS,
										"").split(",");
						/* print substrings */
						lists.clear();
						for (int i = 0; i < temp.length; i++) {
							System.out.println(temp[i]);
							lists.add(temp[i]);
						}

						if (lists.contains(Constants.new_pack_id)) {

							// Toast.makeText(DetailScreen1.this,
							// "found",
							// Toast.LENGTH_LONG).show();

							buyticketBtn.setVisibility(View.VISIBLE);
							buyticketBtn.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									startActivity(new Intent(
											DetailScreen1.this,
											PackageListing.class));
								}
							});
							

							// finish();

						} else {
							buyticketBtn.setVisibility(View.GONE);
//							AlertDialog.Builder altDialog = new AlertDialog.Builder(
//									DetailScreen1.this);
//							altDialog
//									.setMessage("This service is presently not available"); // here
//							// add
//							// your
//							// message
//							altDialog
//									.setNeutralButton(
//											"OK",
//											new DialogInterface.OnClickListener() {
//												@Override
//												public void onClick(
//														DialogInterface dialog,
//														int which) {
//													dialog.dismiss();
//												}
//											});
//							altDialog.show();
						}
					}

					titleTxt.setText(URLDecoder.decode(tit));
					titleTxt.setTypeface(Constants.ProximaNova_Regular);
					if (TextUtils.isEmpty(abt) || abt.trim().length() == 0
							|| abt.equalsIgnoreCase("null")) {
						aboutLayout1.setVisibility(View.GONE);
						aboutBtn2.setVisibility(View.GONE);
						roomLay.setVisibility(View.VISIBLE);
						roomBtn.setCompoundDrawablesWithIntrinsicBounds(null,
								null, getApplicationContext().getResources()
										.getDrawable(R.drawable.less), null);
					} else {
						roomLay.setVisibility(View.GONE);
						roomBtn.setCompoundDrawablesWithIntrinsicBounds(null,
								null, getApplicationContext().getResources()
										.getDrawable(R.drawable.more), null);
						aboutLayout1.setVisibility(View.VISIBLE);
						if (Constants.selectLanguage.equalsIgnoreCase("1")) {
							aboutBtn2
									.setText("About " + URLDecoder.decode(tit));
						} else {
							aboutBtn2.setText(URLDecoder.decode(tit)
									+ " "
									+ getResources().getString(
											R.string.details_about));
						}

						aboutBtn2.setTypeface(Constants.ProximaNova_Regular);
						aboutTxt1.setText(Html.fromHtml(URLDecoder.decode(abt)
								.replace("&nbsp;", " ")));
						aboutTxt1.setTypeface(Constants.ProximaNova_Regular);
					}

					settingImageToViewFlipper(0,
							jsonPackagesObject.getString("image"), tit);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
//				if (pDialog.isShowing() && pDialog != null) {
//					pDialog.dismiss();
//				}
			}
		}
	}

	SeeAlsoItems eventsItems;

	private class EventsTask extends AsyncTask<String, Void, String> {
		private ProgressDialog pDialog;
		String url;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailScreen1.this);
			pDialog.setMessage("Loading please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

			// Log.e("tag",
			// "http://172.16.0.49/telangana_tourism/WebServices/getCultureDetails/"
			// + Constants.id);
		}

		@Override
		protected String doInBackground(String... args) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getEventDetails/" + Constants.id + "/"
					+ Constants.selectLanguage);

			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getEventDetails/" + Constants.id + "/"
					+ Constants.selectLanguage);

		}

		@Override
		protected void onPostExecute(String jsonResult) {
			System.out.println(" result " + jsonResult);

			try {
				if (jsonResult != null) {
					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (jsonObjMain.has("EventsList")) {
						JSONArray jsonPackagesArray = jsonObjMain
								.getJSONArray("EventsList");

						for (int j = 0; j < jsonPackagesArray.length(); j++) {

							JSONObject jsonObject = jsonPackagesArray
									.getJSONObject(j);
							eventsItems = new SeeAlsoItems();

							System.out.println("eventsItems description are :"
									+ jsonObject.getString("description"));
							eventsItems.description = jsonObject.getString(
									"description").replace("\r\n", "<br>");

							System.out.println("eventsItems titles are :"
									+ jsonObject.getString("title"));
							eventsItems.title = jsonObject.getString("title");
							// System.out.println("eventsItems image paths are :"
							// + jsonObject.getString("image_path"));
							eventsItems.icon = jsonObject
									.getString("image_path");
							// packagesList.add(packagesItems);

							tit = jsonObject.getString("title");
							abt = jsonObject.getString("description").trim();

							eventsItems.acclat = jsonObject
									.getString("latitude");
							eventsItems.acclng = jsonObject
									.getString("longitude");

							// http://www.eventsnow.com/events/details/art-attack

							if (jsonObject.has("buy_url")) {
								url = jsonObject.getString("buy_url");
								if (TextUtils.isEmpty(url)
										|| TextUtils.isEmpty(url)
										|| url.toString().length() == 0
										|| url.toString().length() == 0) {
									buyticketBtn.setVisibility(View.GONE);
								} else {
									buyticketBtn.setVisibility(View.VISIBLE);
									buyticketBtn
											.setText(getResources()
													.getString(
															R.string.details_buy_tickets));
									buyticketBtn
											.setTypeface(Constants.ProximaNova_Regular);

									buyticketBtn
											.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View v) {
													// TODO Auto-generated
													// method stub
													if (!url.startsWith("http://")
															&& !url.startsWith("https://"))
														url = "http://" + url;
													Intent browserIntent = new Intent(
															Intent.ACTION_VIEW,
															Uri.parse(url));
													startActivity(browserIntent);
												}
											});
								}
							} else {
								buyticketBtn.setVisibility(View.GONE);
							}

							if (TextUtils.isEmpty(eventsItems.acclat)
									|| TextUtils.isEmpty(eventsItems.acclng)
									|| eventsItems.acclat.toString().length() == 0
									|| eventsItems.acclng.toString().length() == 0) {
								getDirectionsBtn3.setVisibility(View.GONE);
							} else {
								getDirectionsBtn3.setVisibility(View.VISIBLE);

								getDirectionsBtn3
										.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												// TODO Auto-generated method

												final Dialog dialog = new Dialog(
														DetailScreen1.this,
														android.R.style.Theme_Translucent_NoTitleBar);
												// android.R.style.Theme_Translucent_NoTitleBar);
												dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
												dialog.setContentView(R.layout.map_dialog);

												RelativeLayout headerLayout = (RelativeLayout) dialog
														.findViewById(R.id.headerLayout);

												headerLayout
														.setBackgroundColor(Color
																.parseColor(MainActivity.changeColorStr));

												TextView titleTxt1 = (TextView) dialog
														.findViewById(R.id.tit_txt);

												titleTxt1.setText(URLDecoder
														.decode(tit));
												titleTxt1
														.setTypeface(Constants.ProximaNova_Regular);

												WebView webview = (WebView) dialog
														.findViewById(R.id.webView1);
												webview.setWebViewClient(new WebViewClient());
												webview.getSettings()
														.setJavaScriptEnabled(
																true);

												// if(accomodationList.get(v.getId()).acclat.length()
												// ==
												// 0 ||
												// accomodationList.get(v.getId()).acclng.length()
												// == 0
												// ||
												//
												// TextUtils.isEmpty(accomodationList.get(v.getId()).acclat)
												// ||
												// TextUtils.isEmpty(accomodationList.get(v.getId()).acclng){
												//
												// }else{

												webview.loadUrl("http://maps.google.com/maps?"
														+ "saddr="
														+ Constants.latitude
														+ ","
														+ Constants.longitude
														+ "&daddr="
														+ eventsItems.acclat
														+ ","
														+ eventsItems.acclng);

												// }

												Button closeBtN = (Button) dialog
														.findViewById(R.id.closeBtn);
												closeBtN.setOnClickListener(new OnClickListener() {

													@Override
													public void onClick(View v) {
														// TODO Auto-generated
														// method stub
														dialog.dismiss();
													}
												});

												dialog.show();

											}
										});
							}
							titleTxt.setText(eventsItems.title);

							if (eventsItems.description
									.equalsIgnoreCase("null")
									|| TextUtils
											.isEmpty(eventsItems.description)) {
								aboutLayout2.setVisibility(View.GONE);
								aboutBtn3.setVisibility(View.GONE);
							} else {
								aboutLayout2.setVisibility(View.VISIBLE);
								if (Constants.selectLanguage
										.equalsIgnoreCase("1")) {
									aboutBtn3.setText("About "
											+ eventsItems.title);
								} else {
									aboutBtn3.setText(URLDecoder
											.decode(eventsItems.title)
											+ " "
											+ getResources().getString(
													R.string.details_about));
								}

								aboutTxt2
										.setText(Html
												.fromHtml(eventsItems.description
														.replace("&nbsp;", " ")
														.replace("\r\n\r\n",
																"<br><br>")));
							}

							RelativeLayout rootlayout = new RelativeLayout(
									DetailScreen1.this);
							RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
									LayoutParams.FILL_PARENT,
									LayoutParams.FILL_PARENT);
							relativeParams
									.addRule(RelativeLayout.CENTER_VERTICAL);
							rootlayout.setGravity(Gravity.CENTER_VERTICAL);
							rootlayout.setLayoutParams(relativeParams);
							RelativeLayout.LayoutParams imageRelativeParams = new RelativeLayout.LayoutParams(
									LayoutParams.FILL_PARENT, 360);
							ImageView image = new ImageView(DetailScreen1.this);
							image.setTag(j);
							image.setId(j);
							image.setLayoutParams(imageRelativeParams);
							image.setScaleType(ImageView.ScaleType.FIT_XY);
							rootlayout.addView(image);

							
							
							if(eventsItems.icon.contains("http"))
							{
								Picasso.with(DetailScreen1.this)
								.load(eventsItems.icon.replace("\'", "")
										.replace("%20", "")
										.replace(" ", "").trim())
								.error(R.drawable.default_img).into(image);	
							}else{
								Picasso.with(DetailScreen1.this)
								.load(SplashScreen.Base_url2+eventsItems.icon.replace("\'", "")
										.replace("%20", "")
										.replace(" ", "").trim())
								.error(R.drawable.default_img).into(image);
							}
//							Picasso.with(DetailScreen1.this)
//							.load(SplashScreen.Base_url2+eventsItems.icon.replace("\'", "")
//									.replace("%20", "")
//									.replace(" ", "").trim())
//							.error(R.drawable.default_img).into(image);
							image.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									final Dialog dialog = new Dialog(
											DetailScreen1.this,
											android.R.style.Theme_Translucent_NoTitleBar);
									// android.R.style.Theme_Translucent_NoTitleBar);
									dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
									dialog.setContentView(R.layout.touch_img);
									imgflag = (TouchImageView) dialog
											.findViewById(R.id.view_image);
									RelativeLayout headerLayouT = (RelativeLayout) dialog
											.findViewById(R.id.headerLayout);

									headerLayouT.setBackgroundColor(Color
											.parseColor(MainActivity.changeColorStr));
									TextView title = (TextView) dialog
											.findViewById(R.id.tit_txt);
									title.setText("" + URLDecoder.decode(tit));
									title.setTypeface(Constants.ProximaNova_Regular);

									/*
									 * )); titleTxt = (TextView)
									 * findViewById(R.id.text_intro);
									 * titleTxt.setTypeface
									 * (Constants.ProximaNova_Regular);
									 */
if(eventsItems.icon.contains("http")){
									Picasso.with(DetailScreen1.this)
											.load(eventsItems.icon
													.replace("\'", "")
													.replace("%20", "")
													.replace(" ", "").trim())
											.error(R.drawable.default_img)
											.into(imgflag);
}else{
	Picasso.with(DetailScreen1.this)
	.load(SplashScreen.Base_url2+eventsItems.icon
			.replace("\'", "")
			.replace("%20", "")
			.replace(" ", "").trim())
	.error(R.drawable.default_img)
	.into(imgflag);
}
									Button closeBtN = (Button) dialog
											.findViewById(R.id.closeBtn);
									closeBtN.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											dialog.dismiss();
										}
									});
									dialog.show();

								}
							});

							// try
							// {
							// // get input stream
							//
							// String str = eventsItems.icon.replace("\'", "");
							// String str1 =
							// str.replace("http://ttourdev.etisbew.net/images/",
							// "");
							// InputStream ims =
							// getApplicationContext().getAssets().open(str1.replace("%20",
							// "")
							// .replace(" ", "").trim());
							// // load image as Drawable
							// Drawable d = Drawable.createFromStream(ims,
							// null);
							// // set image to ImageView
							// image.setImageDrawable(d);
							// }
							// catch(IOException ex)
							// {
							//
							// }

							// rootlayout.addView(txt_img_name);
							mViewFlipper.addView(rootlayout);

						}
					}

					if (jsonObjMain.has("SeeAlso")) {

						JSONArray resultjsonArray = jsonObjMain
								.getJSONArray("SeeAlso");

						seeAlsoList = new ArrayList<SeeAlsoItems>();

						for (int i = 0; i < resultjsonArray.length(); i++) {

							JSONObject jsonObject = resultjsonArray
									.getJSONObject(i);

							System.out.println("ids are :"
									+ jsonObject.getString("id"));
							System.out.println("image paths are :"
									+ jsonObject.getString("image_path"));

							SeeAlsoItems seeAlsoItems = new SeeAlsoItems();

							seeAlsoItems.icon = jsonObject
									.getString("image_path");

							seeAlsoItems.id = jsonObject.getString("id");
							seeAlsoList.add(seeAlsoItems);
							// android.text.Html.fromHtml(instruction).toString()
						}
					}
					if (seeAlsoList.size() == 0) {
						seeAlsoLay.setVisibility(View.GONE);
					} else {
						seeAlsoLay.setVisibility(View.VISIBLE);
						g.setAdapter(new ImageAdapter(DetailScreen1.this));

						// g.setSelection(1, true);
						if (seeAlsoList.size() == 1) {
							g.setSelection(0, true);
						} else {
							g.setSelection(1, true);
						}
					}

					if (pDialog.isShowing() && pDialog != null) {
						pDialog.dismiss();
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				if (pDialog.isShowing() && pDialog != null) {
					pDialog.dismiss();
				}
			}
		}
	}

	private class CultureDetailsTask extends AsyncTask<String, Void, String> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailScreen1.this);
			pDialog.setMessage("Loading please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

			// Log.e("tag",
			// "http://172.16.0.49/telangana_tourism/WebServices/getCultureDetails/"
			// + Constants.id);
		}

		@Override
		protected String doInBackground(String... args) {

			// return ServiceCalls1.getJSONString(Constants.Base_url
			// + "getCultureDetails/" + Constants.id);

			try {
				dbbHelper = new TtHelper(DetailScreen1.this);
				dbbHelper.openDataBase();

				String select = " SELECT * FROM CultureDeatilContent WHERE ModuleUniqueName="
						+ Constants.id
						+ " AND language_id="
						+ Constants.selectLanguage;
				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
						select, null);

				startManagingCursor(constantCursor);

				if (constantCursor.getCount() > 0) {
					if (constantCursor.moveToFirst()) {
						// do {

						responseString = constantCursor
								.getString(constantCursor
										.getColumnIndex("ResponseContent"));

						// } while (constantCursor.moveToNext());

					}

				}

				// dbbHelper.close();

				// dbbHelper.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return responseString;

		}

		@Override
		protected void onPostExecute(String jsonResult) {
			System.out.println(" result " + jsonResult);

			try {
				if (jsonResult != null) {
					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (jsonObjMain.has("Culture")) {
						JSONArray jsonPackagesArray = jsonObjMain
								.getJSONArray("Culture");

						for (int j = 0; j < jsonPackagesArray.length(); j++) {

							JSONObject jsonPackagesObject = jsonPackagesArray
									.getJSONObject(j);
							final SeeAlsoItems packagesItems = new SeeAlsoItems();

							System.out.println("Culture ids are :"
									+ jsonPackagesObject.getString("id"));
							packagesItems.id = jsonPackagesObject
									.getString("id");
							System.out.println("Culture description are :"
									+ jsonPackagesObject
											.getString("description"));
							packagesItems.description = jsonPackagesObject
									.getString("description");

							System.out.println("Culture titles are :"
									+ jsonPackagesObject.getString("title"));
							packagesItems.title = jsonPackagesObject
									.getString("title");
							System.out.println("Culture image paths are :"
									+ jsonPackagesObject
											.getString("image_path"));
							packagesItems.icon = jsonPackagesObject
									.getString("image_path");

							tit = jsonPackagesObject.getString("title");
							abt = jsonPackagesObject.getString("description")
									.trim();

							titleTxt.setText(URLDecoder
									.decode(packagesItems.title));
							titleTxt.setTypeface(Constants.ProximaNova_Regular);

							if (packagesItems.description
									.equalsIgnoreCase("null")
									|| TextUtils
											.isEmpty(packagesItems.description)) {
								aboutLayout2.setVisibility(View.GONE);
								aboutBtn3.setVisibility(View.GONE);

							} else {
								aboutLayout2.setVisibility(View.VISIBLE);
								aboutTxt2.setText(Html.fromHtml(URLDecoder
										.decode(packagesItems.description)
										.replace("&nbsp;", " ")
										.replace("\r\n\r\n", "<br><br>")));
								aboutTxt2
										.setTypeface(Constants.ProximaNova_Regular);
								if (Constants.selectLanguage
										.equalsIgnoreCase("1")) {
									aboutBtn3
											.setText("About "
													+ URLDecoder
															.decode(packagesItems.title));
								} else {
									aboutBtn3.setText(URLDecoder
											.decode(packagesItems.title)
											+ " "
											+ getResources().getString(
													R.string.details_about));
								}

								aboutBtn3
										.setTypeface(Constants.ProximaNova_Regular);

							}

							RelativeLayout rootlayout = new RelativeLayout(
									DetailScreen1.this);
							RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
									LayoutParams.FILL_PARENT,
									LayoutParams.FILL_PARENT);
							relativeParams
									.addRule(RelativeLayout.CENTER_VERTICAL);
							rootlayout.setGravity(Gravity.CENTER_VERTICAL);
							rootlayout.setLayoutParams(relativeParams);
							RelativeLayout.LayoutParams imageRelativeParams = new RelativeLayout.LayoutParams(
									LayoutParams.FILL_PARENT, 360);
							ImageView image = new ImageView(DetailScreen1.this);

							image.setTag(j);
							image.setId(j);
							image.setLayoutParams(imageRelativeParams);
							image.setScaleType(ImageView.ScaleType.FIT_XY);

							rootlayout.addView(image);
							if (packagesItems.icon.length() > 0) {
								// if (packagesItems.icon.contains("http")) {
								String url=packagesItems.icon
										.replace("\'", "")
										.replace("%20", "")
										.replace(" ", "").trim();
//								String url=SplashScreen.Base_url2+packagesItems.icon
//										.replace("\'", "")
//										.replace("%20", "")
//										.replace(" ", "").trim();
								System.out.println("url of image"+url);
								Picasso.with(DetailScreen1.this)
										.load(url)
										.error(R.drawable.default_img)
										.into(image);

								// ImageLoader imageLoader = ImageLoader
								// .getInstance();
								// DisplayImageOptions options = new
								// DisplayImageOptions.Builder()
								// .cacheInMemory(true)
								// .cacheOnDisc(true)
								// .resetViewBeforeLoading(true)
								// .showImageForEmptyUri(
								// R.drawable.default_img)
								// .showImageOnFail(R.drawable.default_img)
								// .showImageOnLoading(
								// R.drawable.default_img).build();
								//
								// // download and display image from url
								// imageLoader.displayImage(packagesItems.icon
								// .replace("\'", "").replace("%20", "")
								// .replace(" ", "").trim(), image,
								// options);

								image.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub

										final Dialog dialog = new Dialog(
												DetailScreen1.this,
												android.R.style.Theme_Translucent_NoTitleBar);
										// android.R.style.Theme_Translucent_NoTitleBar);
										dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
										dialog.setContentView(R.layout.touch_img);
										imgflag = (TouchImageView) dialog
												.findViewById(R.id.view_image);
										RelativeLayout headerLayouT = (RelativeLayout) dialog
												.findViewById(R.id.headerLayout);

										headerLayouT.setBackgroundColor(Color
												.parseColor(MainActivity.changeColorStr));
										TextView title = (TextView) dialog
												.findViewById(R.id.tit_txt);
										title.setText(""
												+ URLDecoder
														.decode(packagesItems.title));
										title.setTypeface(Constants.ProximaNova_Regular);

										/*
										 * )); titleTxt = (TextView)
										 * findViewById(R.id.text_intro);
										 * titleTxt.setTypeface(Constants.
										 * ProximaNova_Regular);
										 */
if(packagesItems.icon.contains("http")){
	
	Picasso.with(DetailScreen1.this)
	.load(packagesItems.icon
			.replace("\'", "")
			.replace("%20", "")
			.replace(" ", "")
			.trim())
	.error(R.drawable.default_img)
	.into(imgflag);
}else{
	Picasso.with(DetailScreen1.this)
	.load(SplashScreen.Base_url2+packagesItems.icon
			.replace("\'", "")
			.replace("%20", "")
			.replace(" ", "")
			.trim())
	.error(R.drawable.default_img)
	.into(imgflag);
}
										

										Button closeBtN = (Button) dialog
												.findViewById(R.id.closeBtn);
										closeBtN.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												// TODO Auto-generated method
												// stub
												dialog.dismiss();
											}
										});
										dialog.show();

									}
								});

								// } else {
								// try {
								// // get input stream
								//
								// String str = packagesItems.icon
								// .replace("\'", "");
								// String str1 = str
								// .replace(
								// "http://ttourdev.etisbew.net/images/",
								// "");
								// InputStream ims = getApplicationContext()
								// .getAssets().open(
								// str1.replace("%20", "")
								// .replace(" ",
								// "")
								// .trim());
								// // load image as Drawable
								// Drawable d = Drawable.createFromStream(
								// ims, null);
								// // set image to ImageView
								// image.setImageDrawable(d);
								// } catch (IOException ex) {
								//
								// }
								// }
							}
 
							// rootlayout.addView(txt_img_name);
							mViewFlipper.addView(rootlayout);

						}
						g.setAdapter(new ImageAdapter(DetailScreen1.this));
						g.setSelection(1, true);
					}

					// if (jsonObjMain.has("Accomodation")) {
					// JSONArray jsonAccomodationArray = jsonObjMain
					// .getJSONArray("Accomodation");
					//
					// for (int j = 0; j < jsonAccomodationArray.length(); j++)
					// {
					//
					// JSONObject jsonAccomodationObject = jsonAccomodationArray
					// .getJSONObject(j);
					// SeeAlsoItems AccomodationItems = new SeeAlsoItems();
					//
					// System.out.println("Accomodation ids are :"
					// + jsonAccomodationObject.getString("id"));
					// AccomodationItems.id = jsonAccomodationObject
					// .getString("id");
					// AccomodationItems.title = jsonAccomodationObject
					// .getString("title");
					// AccomodationItems.address = jsonAccomodationObject
					// .getString("address");
					// System.out.println("Accomodation image paths are :"
					// + jsonAccomodationObject
					// .getString("image_path"));
					// // AccomodationItems.icon = jsonAccomodationObject
					// // .getString("image_path");
					//
					// AccomodationItems.icon = jsonAccomodationObject
					// .getJSONArray("image_path").get(0).toString();
					//
					// System.out.println("Accomodation latitude are :"
					// + jsonAccomodationObject.getString("latitude"));
					// System.out
					// .println("Accomodation longitude are :"
					// + jsonAccomodationObject
					// .getString("longitude"));
					//
					// AccomodationItems.acclat = jsonAccomodationObject
					// .getString("latitude");
					//
					// AccomodationItems.acclng = jsonAccomodationObject
					// .getString("longitude");
					//
					// System.out.println("Accomodation book_url are :"
					// + jsonAccomodationObject.getString("book_url"));
					//
					// accomodationList.add(AccomodationItems);
					// }
					// }

					// if (jsonObjMain.has("Attractions")) {
					//
					// JSONObject jsonObject = jsonObjMain
					// .getJSONObject("Attractions");
					//
					// SeeAlsoItems AttractionsItems = new SeeAlsoItems();
					//
					// System.out.println("Attractions ids are :"
					// + jsonObject.getString("id"));
					// AttractionsItems.id = jsonObject.getString("id");
					// AttractionsItems.title = jsonObject.getString("title");
					// AttractionsItems.address =
					// jsonObject.getString("address");
					// AttractionsItems.about = jsonObject.getString("about");
					//
					// // Favroties
					//
					// tit = jsonObject.getString("title");
					// lat = jsonObject.getString("latitude");
					// lng = jsonObject.getString("longitude");
					// id = jsonObject.getString("id");
					// address = jsonObject.getString("address");
					//
					// image_path = jsonObject.getJSONArray("image_path").get(0)
					// .toString();
					//
					// // Favourites End
					//
					// attractionsList.add(AttractionsItems);
					//
					// System.out.println("Attractions latitude are :"
					// + jsonObject.getString("latitude"));
					// System.out.println("Attractions longitude are :"
					// + jsonObject.getString("longitude"));
					//
					// System.out.println("Attractions by_bus are :"
					// + jsonObject.getString("by_bus"));
					//
					// System.out.println("Attractions by_train are :"
					// + jsonObject.getString("by_train"));
					// System.out.println("Attractions by_air are :"
					// + jsonObject.getString("by_air"));
					//
					// JSONArray jsonAttrImagesArray = jsonObject
					// .getJSONArray("image_path");
					//
					// titleTxt.setText(AttractionsItems.title);
					// aboutBtn.setText("About " + AttractionsItems.title);
					// aboutTxt.setText(AttractionsItems.about);
					//
					// byBusTxt.setText(jsonObject.getString("by_bus"));
					// byTrainTxt.setText(jsonObject.getString("by_train"));
					// byCarTxt.setText(jsonObject.getString("by_air"));
					//
					// for (int j = 0; j < jsonAttrImagesArray.length(); j++) {
					//
					// SeeAlsoItems attractionImages = new SeeAlsoItems();
					// attractionImages.icon = jsonAttrImagesArray
					// .getString(j);
					// // attractionsImagesList.add(attractionImages);
					//
					// RelativeLayout rootlayout = new RelativeLayout(
					// DetailScreen1.this);
					// RelativeLayout.LayoutParams relativeParams = new
					// RelativeLayout.LayoutParams(
					// LayoutParams.FILL_PARENT,
					// LayoutParams.FILL_PARENT);
					// relativeParams.addRule(RelativeLayout.CENTER_VERTICAL);
					// rootlayout.setGravity(Gravity.CENTER_VERTICAL);
					// rootlayout.setLayoutParams(relativeParams);
					// ImageView image = new ImageView(DetailScreen1.this);
					// TextView txt_img_name = new TextView(DetailScreen1.this);
					//
					// image.setTag(j);
					// image.setId(j);
					// image.setLayoutParams(relativeParams);
					// image.setScaleType(ImageView.ScaleType.CENTER_CROP);
					//
					// rootlayout.addView(image);
					//
					// RelativeLayout.LayoutParams relativeParams1 = new
					// RelativeLayout.LayoutParams(
					// LayoutParams.FILL_PARENT, 60);
					// relativeParams1
					// .addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					// txt_img_name.setLayoutParams(relativeParams1);
					// txt_img_name.setGravity(Gravity.CENTER);
					// txt_img_name.setTextSize(18);
					// txt_img_name.setTextColor(Color.WHITE);
					// txt_img_name.setBackgroundColor(Color
					// .parseColor("#99000000"));
					//
					// Picasso.with(DetailScreen1.this)
					// .load(jsonAttrImagesArray.getString(j))
					// .error(R.drawable.default_img).into(image);
					// txt_img_name.setText(AttractionsItems.title);
					//
					// rootlayout.addView(txt_img_name);
					// mViewFlipper.addView(rootlayout);
					//
					// }
					//
					// getPackagesList(DetailScreen1.this);
					// getAccomodationList(DetailScreen1.this);
					//
					// g.setAdapter(new ImageAdapter(DetailScreen1.this));
					// if (MainActivity.detailNavFlag == 1) {
					// g.setAdapter(new ImageAdapter1(DetailScreen1.this));
					// } else if (MainActivity.detailNavFlag == 2) {
					// g.setAdapter(new ImageAdapter(DetailScreen1.this));
					// }

					if (pDialog.isShowing() && pDialog != null) {
						pDialog.dismiss();
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				if (pDialog.isShowing() && pDialog != null) {
					pDialog.dismiss();
				}
			}
		}
	}

	static TextView[] seeTxtName;
	static ImageView[] seeImageView;
	static LinearLayout[] seeRootLayout;

	public void getSeeAlso(final Context context) {

		LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		seeAlsoLayout.removeAllViews();
		seeTxtName = new TextView[10];

		seeImageView = new ImageView[10];
		seeRootLayout = new LinearLayout[10];

		for (int j = 0; j < 4; j++) {

			seeRootLayout[j] = new LinearLayout(context);
			seeRootLayout[j].setPadding(5, 5, 5, 5);
			seeRootLayout[j].setOrientation(LinearLayout.HORIZONTAL);
			seeRootLayout[j].setLayoutParams(linLayoutParam);
			seeRootLayout[j].setGravity(Gravity.CENTER_VERTICAL);

			LinearLayout.LayoutParams sublinLayoutParam = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					150, 90);

			seeImageView[j] = new ImageView(context);
			seeImageView[j].setLayoutParams(layoutParams);
			seeImageView[j].setBackgroundResource(R.drawable.default_img);

			seeImageView[j].setPadding(5, 5, 5, 5);

			seeTxtName[j] = new TextView(context);
			seeTxtName[j].setLayoutParams(linLayoutParam);
			seeTxtName[j].setText(URLDecoder.decode(namesArray[j]));
			seeTxtName[j].setTypeface(Constants.ProximaNova_Regular);
			seeTxtName[j].setTextSize(18);
			seeTxtName[j].setPadding(5, 5, 5, 5);
			seeTxtName[j].setGravity(Gravity.CENTER_VERTICAL);
			seeTxtName[j].setId(j);
			seeRootLayout[j].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});
			seeRootLayout[j].setId(j);
			seeRootLayout[j].addView(seeImageView[j]);
			seeRootLayout[j].addView(seeTxtName[j]);
			seeAlsoLayout.addView(seeRootLayout[j]);
		}
	}

//	String[] listHead = { "By Train", "By Bus", "By Car" };

	static Button[] herebtnName, herebtnLine;
	static LinearLayout[] hererootLayout, herelayoutLLButton;

	public void getttingHere(final Context context) {

		LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 2f);
		// placesLayout.removeAllViews();
		// placesStayLayout.removeAllViews();
		gettingLayout.removeAllViews();
		herebtnName = new Button[3];
		herebtnLine = new Button[3];
		hererootLayout = new LinearLayout[3];

		herelayoutLLButton = new LinearLayout[3];

		for (int j = 0; j < 3; j++) {

			hererootLayout[j] = new LinearLayout(context);
			hererootLayout[j].setOrientation(LinearLayout.HORIZONTAL);
			hererootLayout[j].setLayoutParams(linLayoutParam);

			hererootLayout[j].setPadding(5, 5, 5, 5);

			LinearLayout.LayoutParams sublinLayoutParam = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			sublinLayoutParam.weight = (float) .7;

			LinearLayout.LayoutParams sublinLayoutParam1 = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			sublinLayoutParam1.weight = (float) 1.3;

			herelayoutLLButton[j] = new LinearLayout(context);
			herelayoutLLButton[j].setLayoutParams(sublinLayoutParam1);
			herelayoutLLButton[j].setOrientation(LinearLayout.VERTICAL);

			LinearLayout.LayoutParams sublinLayoutParam2 = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			sublinLayoutParam2.weight = 1;

			herebtnName[j] = new Button(context);
			herebtnName[j].setLayoutParams(sublinLayoutParam2);
			herebtnName[j].setText(" frist button" + j);
			herebtnName[j].setTextSize(18);
			herebtnName[j].setPadding(5, 5, 5, 5);
			herebtnName[j].setGravity(Gravity.CENTER_VERTICAL);
			herebtnName[j].setId(j);
			herebtnName[j].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});

			herelayoutLLButton[j].addView(herebtnName[j]);

			herebtnLine[j] = new Button(context);
			herebtnLine[j].setText("second button" + j);
			herebtnLine[j].setGravity(Gravity.CENTER_VERTICAL);
			herebtnLine[j].setLayoutParams(sublinLayoutParam2);
			herebtnLine[j].setId(j);
			herebtnLine[j].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});

			herelayoutLLButton[j].addView(herebtnLine[j]);

			// rootLayout[j].addView(imageView[j]);
			// rootLayout[j].addView(layoutLL[j]);
			hererootLayout[j].addView(herelayoutLLButton[j]);
			gettingLayout.addView(hererootLayout[j]);
		}
	}

	// class AccomodationAsyTask extends AsyncTask<String, Void, String> {
	// private ProgressDialog pDialog;
	// String url;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// pDialog = new ProgressDialog(DetailScreen1.this);
	// pDialog.setMessage("Loading please wait...");
	// pDialog.setIndeterminate(false);
	// pDialog.setCancelable(true);
	// pDialog.show();
	// }
	//
	// @Override
	// protected String doInBackground(String... params) {
	// // return ServiceCalls1.getJSONString(Constants.Base_url
	// // + "getAccomodationDetails/" + Constants.id);
	//
	// try {
	// dbbHelper = new TtHelper(DetailScreen1.this);
	// dbbHelper.openDataBase();
	//
	// String select =
	// " SELECT * FROM AccomodationDetailContent WHERE ModuleUniqueName="
	// + Constants.id;
	// constantCursor = dbbHelper.getReadableDatabase().rawQuery(
	// select, null);
	//
	// startManagingCursor(constantCursor);
	//
	// if (constantCursor.getCount() > 0) {
	// if (constantCursor.moveToFirst()) {
	// // do {
	//
	// responseString = constantCursor
	// .getString(constantCursor
	// .getColumnIndex("ResponseContent"));
	//
	// // } while (constantCursor.moveToNext());
	//
	// }
	//
	// }
	//
	// // dbbHelper.close();
	//
	// // dbbHelper.close();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// return responseString;
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// super.onPostExecute(result);
	//
	// if (null == result || result.length() == 0) {
	// Toast.makeText(DetailScreen1.this,
	// "No data found from Server!!!", Toast.LENGTH_LONG)
	// .show();
	//
	// } else {
	//
	// System.out.println(" result " + result);
	//
	// try {
	//
	// JSONObject jsonObjMain = new JSONObject(result);
	// mViewFlipper.removeAllViews();
	//
	// if (jsonObjMain.has("Room")) {
	// JSONArray jsonPackagesArray = jsonObjMain
	// .getJSONArray("Room");
	// String str = "";
	// for (int j = 0; j < jsonPackagesArray.length(); j++) {
	//
	// JSONObject jsonPackagesObject = jsonPackagesArray
	// .getJSONObject(j);
	// SeeAlsoItems packagesItems = new SeeAlsoItems();
	//
	// System.out.println("packages ids are :"
	// + jsonPackagesObject
	// .getString("room_type_id"));
	// packagesItems.room_type_id = jsonPackagesObject
	// .getString("room_type_id");
	// System.out.println("packages description are :"
	// + jsonPackagesObject.getString("nos"));
	// packagesItems.nos = jsonPackagesObject
	// .getString("nos");
	//
	// System.out.println("packages titles are :"
	// + jsonPackagesObject.getString("cost"));
	// packagesItems.cost = jsonPackagesObject
	// .getString("cost");
	//
	//
	//
	// }
	//
	// for (int i = 0; i < roomTypeArray.size(); i++) {
	// str = str
	// + "<p><font size='3' color='#939393'><b>Rooms ("
	// + roomTypeArray.get(i)
	// + ") : </b></font> <font size='3' color='red'><b>Rs. "
	// + roomCostArray.get(i)
	// + "/-</b></font></p>";
	// }
	//
	// if (str.length() > 0) {
	// roomBtn.setVisibility(View.VISIBLE);
	// roomBtn.setText(getResources().getString(
	// R.string.details_rooms));
	// roomBtn.setTypeface(Constants.ProximaNova_Regular);
	// roomTxt.setText(Html
	// .fromHtml("<html lang='en-US'><body>" + str
	// + "</body></html"));
	// roomTxt.setTypeface(Constants.ProximaNova_Regular);
	// } else {
	// roomBtn.setVisibility(View.GONE);
	// }
	// }
	//
	// if (jsonObjMain.has("SeeAlso")) {
	//
	// JSONArray resultjsonArray = jsonObjMain
	// .getJSONArray("SeeAlso");
	//
	// seeAlsoList = new ArrayList<SeeAlsoItems>();
	//
	// for (int i = 0; i < resultjsonArray.length(); i++) {
	//
	// JSONObject jsonObject = resultjsonArray
	// .getJSONObject(i);
	//
	// System.out.println("ids are :"
	// + jsonObject.getString("id"));
	// System.out.println("image paths are :"
	// + jsonObject.getString("image_path"));
	//
	// SeeAlsoItems seeAlsoItems = new SeeAlsoItems();
	//
	// try {
	// if (jsonObject.getJSONArray("image_path")
	// .length() > 0) {
	// seeAlsoItems.icon = jsonObject
	// .getJSONArray("image_path").get(0)
	// .toString();
	// }
	//
	// } catch (JSONException je) {
	// // TODO: handle exception
	// je.printStackTrace();
	// }
	//
	// seeAlsoItems.id = jsonObject.getString("id");
	// seeAlsoList.add(seeAlsoItems);
	//
	// }
	// }
	// if (jsonObjMain.has("StayPlace")) {
	//
	// JSONObject jsonObject = jsonObjMain
	// .getJSONObject("StayPlace");
	//
	// SeeAlsoItems AttractionsItems = new SeeAlsoItems();
	//
	// System.out.println("Attractions ids are :"
	// + jsonObject.getString("description"));
	// AttractionsItems.id = jsonObject.getString("id");
	// AttractionsItems.title = jsonObject.getString("title");
	// AttractionsItems.address = jsonObject
	// .getString("address");
	// AttractionsItems.description = jsonObject
	// .getString("description");
	//
	// // Favroties
	//
	// tit = jsonObject.getString("title");
	// lat = jsonObject.getString("latitude");
	// lng = jsonObject.getString("longitude");
	// id = jsonObject.getString("id");
	// address = jsonObject.getString("address");
	// abt = jsonObject.getString("description").trim();
	//
	// try {
	// if (jsonObject.getJSONArray("image_path").length() > 0) {
	// image_path = jsonObject
	// .getJSONArray("image_path").get(0)
	// .toString();
	// }
	//
	// } catch (JSONException je) {
	// // TODO: handle exception
	// je.printStackTrace();
	// }
	//
	// // Favourites End
	// // attractionsList.add(AttractionsItems);
	//
	// System.out.println("Attractions latitude are :"
	// + jsonObject.getString("latitude"));
	// System.out.println("Attractions longitude are :"
	// + jsonObject.getString("longitude"));
	//
	// // System.out.println("Attractions by_bus are :"
	// // + jsonObject.getString("by_bus"));
	// // System.out.println("Attractions by_train are :"
	// // + jsonObject.getString("by_train"));
	// // System.out.println("Attractions by_air are :"
	// // + jsonObject.getString("by_air"));
	//
	// JSONArray jsonAttrImagesArray = jsonObject
	// .getJSONArray("image_path");
	//
	// titleTxt.setText(URLDecoder
	// .decode(AttractionsItems.title));
	// titleTxt.setTypeface(Constants.ProximaNova_Regular);
	//
	// if (TextUtils.isEmpty(AttractionsItems.description)
	// || AttractionsItems.description.length() == 0) {
	// aboutBtn2.setVisibility(View.GONE);
	// aboutLayout1.setVisibility(View.GONE);
	// } else {
	// aboutLayout1.setVisibility(View.VISIBLE);
	//
	// if (Constants.selectLanguage.equalsIgnoreCase("1")) {
	// aboutBtn2
	// .setText("About "
	// + URLDecoder
	// .decode(AttractionsItems.title));
	// } else {
	// aboutBtn2.setText(URLDecoder
	// .decode(AttractionsItems.title)
	// + " "
	// + getResources().getString(
	// R.string.details_about));
	// }
	//
	// aboutBtn2
	// .setTypeface(Constants.ProximaNova_Regular);
	// aboutTxt1.setText(Html.fromHtml(URLDecoder.decode(
	// AttractionsItems.description).replace(
	// "&nbsp;", " ")));
	// aboutTxt1
	// .setTypeface(Constants.ProximaNova_Regular);
	// aboutTxt1.setEllipsize(TextUtils.TruncateAt.END);
	// aboutTxt1.setLines(4);
	// }
	// if (TextUtils.isEmpty(jsonObject
	// .getString("facilities"))
	// || jsonObject.getString("facilities").length() == 0) {
	// facilitiesBtn.setVisibility(View.GONE);
	// } else {
	// facilitiesTxt
	// .setText(Html.fromHtml("<html lang='en-US'><body><ul>"
	// + // <li>1. Open Air Auditorium 1600
	// // persons
	// // capacity.</li><br><br>" +
	// URLDecoder
	// .decode(jsonObject
	// .getString(
	// "facilities")
	// .replace("\r\n",
	// "<br><br>"))
	// + "<br></ul></body></html"));
	// }
	//
	// url = jsonObject.getString("buy_url");
	//
	// if (TextUtils.isEmpty(url) || url.length() == 0) {
	// buyticketBtn.setVisibility(View.GONE);
	// } else {
	// buyticketBtn.setVisibility(View.VISIBLE);
	// buyticketBtn
	// .setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// // if (!url.startsWith("http://")
	// // && !url.startsWith("https://"))
	// // url = "http://" + url;
	// // Intent browserIntent = new
	// // Intent(
	// // Intent.ACTION_VIEW, Uri
	// // .parse(url));
	// // startActivity(browserIntent);
	//
	// ArrayList<String> lists = new ArrayList<String>();
	// /*
	// * given string will be split by the argument delimiter
	// * provided.
	// */
	//
	// if(SplashScreen.hotel_pref.getString(
	// SplashScreen.GET_HOTELS_IDS, "").length() > 0){
	// String[] temp = SplashScreen.hotel_pref.getString(
	// SplashScreen.GET_HOTELS_IDS, "").split(",");
	// /* print substrings */
	// lists.clear();
	// for (int i = 0; i < temp.length; i++) {
	// System.out.println(temp[i]);
	// lists.add(temp[i]);
	// }
	//
	// if (lists.contains(Constants.new_id)) {
	//
	// // Toast.makeText(DetailScreen1.this, "found",
	// // Toast.LENGTH_LONG).show();
	//
	// startActivity(new Intent(
	// DetailScreen1.this,
	// HotelBookingActivity.class));
	//
	// // finish();
	//
	// } else {
	//
	//
	// AlertDialog.Builder altDialog = new AlertDialog.Builder(
	// DetailScreen1.this);
	// altDialog
	// .setMessage("This service is presently not available"); // here
	// // add
	// // your
	// // message
	// altDialog
	// .setNeutralButton(
	// "OK",
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(
	// DialogInterface dialog,
	// int which) {
	// dialog.dismiss();
	// }
	// });
	// altDialog.show();
	// }
	// }
	//
	//
	//
	// }
	// });
	// }
	//
	// // byBusTxt.setText(jsonObject.getString("by_bus"));
	// // byTrainTxt.setText(jsonObject.getString("by_train"));
	// // byCarTxt.setText(jsonObject.getString("by_air"));
	//
	// for (int j = 0; j < jsonAttrImagesArray.length(); j++) {
	//
	// SeeAlsoItems attractionImages = new SeeAlsoItems();
	// attractionImages.icon = jsonAttrImagesArray
	// .getString(j);
	// // attractionsImagesList.add(attractionImages);
	//
	// settingImageToViewFlipper(j,
	// jsonAttrImagesArray.getString(j),
	// AttractionsItems.title);
	//
	// }
	//
	// // getPackagesList(DetailScreen1.this);
	// // getAccomodationList(DetailScreen1.this);
	//
	// if (seeAlsoList.size() == 0) {
	// seeAlsoLay.setVisibility(View.GONE);
	// } else {
	// seeAlsoLay.setVisibility(View.VISIBLE);
	// g.setAdapter(new ImageAdapter(DetailScreen1.this));
	//
	// if (seeAlsoList.size() == 1) {
	// g.setSelection(0, true);
	// } else {
	// g.setSelection(1, true);
	// }
	//
	// }
	// // if (MainActivity.detailNavFlag == 1) {
	// // g.setAdapter(new ImageAdapter1(DetailScreen1.this));
	// // } else if (MainActivity.detailNavFlag == 2) {
	// // g.setAdapter(new ImageAdapter(DetailScreen1.this));
	// // }
	//
	// if (pDialog.isShowing() && pDialog != null) {
	// pDialog.dismiss();
	// }
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// if (pDialog.isShowing() && pDialog != null) {
	// pDialog.dismiss();
	// }
	// }
	//
	// }
	//
	// }
	// }

	public void settingImageToViewFlipper(int position, final String imagepath,
			final String title) {

		RelativeLayout rootlayout = new RelativeLayout(DetailScreen1.this);
		RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		relativeParams.addRule(RelativeLayout.CENTER_VERTICAL);
		rootlayout.setGravity(Gravity.CENTER_VERTICAL);
		rootlayout.setLayoutParams(relativeParams);
		ImageView image = new ImageView(DetailScreen1.this);
		TextView txt_img_name = new TextView(DetailScreen1.this);
		RelativeLayout.LayoutParams imageRelativeParams = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 360);
		image.setTag(position);
		image.setId(position);
		image.setLayoutParams(imageRelativeParams);
		image.setScaleType(ImageView.ScaleType.FIT_XY);
		rootlayout.addView(image);

		RelativeLayout.LayoutParams relativeParams1 = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 80);
		relativeParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		txt_img_name.setLayoutParams(relativeParams1);
		txt_img_name.setGravity(Gravity.CENTER);
		// txt_img_name.setTextSize(18);
		if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID, "")
				.equalsIgnoreCase("1")) {
			txt_img_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
			txt_img_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
			txt_img_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		}
		txt_img_name.setTextColor(Color.WHITE);
		txt_img_name.setBackgroundColor(Color.parseColor("#99000000"));

		if (imagepath.length() > 0) {
			// String u = "http://ttourdev.etisbew.net/images/";
			// if (imagepath.contains("http")) {
			// String uu=u+imagepath;

			// if (utils.IsNetConnected(getApplicationContext())) {
			// Picasso.with(DetailScreen1.this)
			// .load(imagepath.replace("\'", "").replace("%20", "")
			// .replace(" ", "").trim())
			// .error(R.drawable.default_img).into(image);

			ImageLoader imageLoader = ImageLoader.getInstance();
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisc(true)
					.resetViewBeforeLoading(true)
					.showImageForEmptyUri(R.drawable.default_img)
					.showImageOnFail(R.drawable.default_img)
					.showImageOnLoading(R.drawable.default_img).build();

			// download and display image from url
			imageLoader.displayImage(
					imagepath.replace("\'", "").replace("%20", "")
							.replace(" ", "").trim(), image, options);
//			imageLoader.displayImage(
//					SplashScreen.Base_url2+imagepath.replace("\'", "").replace("%20", "")
//							.replace(" ", "").trim(), image, options);

			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					final Dialog dialog = new Dialog(DetailScreen1.this,
							android.R.style.Theme_Translucent_NoTitleBar);
					// android.R.style.Theme_Translucent_NoTitleBar);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.touch_img);
					imgflag = (TouchImageView) dialog
							.findViewById(R.id.view_image);
					RelativeLayout headerLayouT = (RelativeLayout) dialog
							.findViewById(R.id.headerLayout);

					headerLayouT.setBackgroundColor(Color
							.parseColor(MainActivity.changeColorStr));
					TextView titles = (TextView) dialog
							.findViewById(R.id.tit_txt);
					titles.setText("" + URLDecoder.decode(title));
					titles.setTypeface(Constants.ProximaNova_Regular);

					/*
					 * )); titleTxt = (TextView) findViewById(R.id.text_intro);
					 * titleTxt.setTypeface(Constants.ProximaNova_Regular);
					 */
if(imagepath.contains("http")){
					Picasso.with(DetailScreen1.this)
							.load(imagepath.replace("\'", "")
									.replace("%20", "").replace(" ", "").trim())
							.error(R.drawable.default_img).into(imgflag);
}else{
	Picasso.with(DetailScreen1.this)
	.load(SplashScreen.Base_url2+imagepath.replace("\'", "")
			.replace("%20", "").replace(" ", "").trim())
	.error(R.drawable.default_img).into(imgflag);
}
					


					Button closeBtN = (Button) dialog
							.findViewById(R.id.closeBtn);
					closeBtN.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();

				}
			});
			//
			// } else {
			// try {
			// String url = imagepath.replace("\'", "");
			//
			// String replacdUrl = url.replace(
			// "http://ttourdev.etisbew.net/images/", "");
			// // get input stream
			// InputStream ims = getAssets().open(replacdUrl.trim());
			// // load image as Drawable
			// Drawable d = Drawable.createFromStream(ims, null);
			// // set image to ImageView
			// image.setImageDrawable(d);
			// } catch (IOException ex) {
			// image.setImageResource(R.drawable.default_img);
			// }
			// }
			// } else {
			// try {
			// // get input stream
			//
			// String str = imagepath.replace("\'", "");
			// String str1 = str.replace(
			// "http://ttourdev.etisbew.net/images/", "");
			// InputStream ims = getApplicationContext().getAssets().open(
			// str1.replace("%20", "").replace(" ", "").trim());
			// // load image as Drawable
			// Drawable d = Drawable.createFromStream(ims, null);
			// // set image to ImageView
			// image.setImageDrawable(d);
			// } catch (IOException ex) {
			// image.setImageResource(R.drawable.default_img);
			// }
			// }
		}
		//

		txt_img_name.setText(URLDecoder.decode(title));
		txt_img_name.setTypeface(Constants.ProximaNova_Regular);

		rootlayout.addView(txt_img_name);
		mViewFlipper.addView(rootlayout);
	}
	
	private void destinationsJsonParsing(String responseString1){
		try {

			JSONObject jsonObjMain = new JSONObject(responseString1);
			mViewFlipper.removeAllViews();

			if (jsonObjMain.has("message")) {
				
			} else {

				if (jsonObjMain.has("SeeAlso")) {
					// Creating JSONArray from JSONObject

					JSONArray resultjsonArray = jsonObjMain
							.getJSONArray("SeeAlso");

					seeAlsoList = new ArrayList<SeeAlsoItems>();
					packagesList = new ArrayList<SeeAlsoItems>();
					accomodationList = new ArrayList<SeeAlsoItems>();
					attractionsList = new ArrayList<SeeAlsoItems>();
					attractionsImagesList = new ArrayList<SeeAlsoItems>();

					for (int i = 0; i < resultjsonArray.length(); i++) {

						JSONObject jsonObject = resultjsonArray
								.getJSONObject(i);

						System.out.println("ids are :"
								+ jsonObject.getString("id"));
						// System.out.println("image paths are :"
						// + jsonObject.getString("image_path"));

						System.out.println("titles are :"
								+ jsonObject.getString("title"));
						SeeAlsoItems seeAlsoItems = new SeeAlsoItems();

						try {
							if (jsonObject.has("image_path")) {

								// jsonObject.optJSONArray("image_path");

								// Object json = new JSONTokener(jsonObject
								// .getJSONArray("image_path").get(0)
								// .toString()).nextValue();

								// if (json instanceof JSONObject)
								// //you have an object
								// else
								if (jsonObject.optJSONObject("image_path") == null)
								// you have an array
								{
									seeAlsoItems.icon = jsonObject
											.getJSONArray("image_path")
											.get(0).toString();
									seeAlsoItems.id = jsonObject
											.getString("id");
									seeAlsoList.add(seeAlsoItems);
								} else {
									jsonObject.optString("image_path");
								}
								// if(!TextUtils.isEmpty(jsonObject
								// .getJSONArray("image_path")
								// .get(0).toString())){
								//
								// }else{
								// if (jsonObject.getJSONArray("image_path")
								// .length() > 0) {
								//
								//
								// }
								// }

							}

						} catch (JSONException je) {
							// TODO: handle exception
							je.printStackTrace();
						}

					}
				}
				if (jsonObjMain.has("Package")) {
					JSONArray jsonPackagesArray = jsonObjMain
							.getJSONArray("Package");

					if (jsonPackagesArray.length() == 0) {
						packagesBtn.setVisibility(View.GONE);
					} else {
//						packagesBtn.setVisibility(View.VISIBLE);
						packagesBtn.setVisibility(View.GONE);
					}

					for (int j = 0; j < jsonPackagesArray.length(); j++) {

						JSONObject jsonPackagesObject = jsonPackagesArray
								.getJSONObject(j);
						SeeAlsoItems packagesItems = new SeeAlsoItems();

						System.out.println("packages ids are :"
								+ jsonPackagesObject.getString("id"));
						packagesItems.id = jsonPackagesObject
								.getString("id");
						System.out.println("packages description are :"
								+ jsonPackagesObject
										.getString("description"));
						packagesItems.description = jsonPackagesObject
								.getString("description");
						abt = packagesItems.description.trim();
						tit = packagesItems.title;
						System.out.println("packages titles are :"
								+ jsonPackagesObject.getString("title"));
						packagesItems.title = jsonPackagesObject
								.getString("title");
						System.out.println("packages image paths are :"
								+ jsonPackagesObject.getString("image"));
						packagesItems.icon = jsonPackagesObject
								.getString("image");
						packagesItems.packageUrl = jsonPackagesObject
								.getString("package_url");

						packagesList.add(packagesItems);
					}
				} else {
					packagesBtn.setVisibility(View.GONE);
				}

				if (jsonObjMain.has("Accomodation")) {
					JSONArray jsonAccomodationArray = jsonObjMain
							.getJSONArray("Accomodation");

					if (jsonAccomodationArray.length() == 0) {
						placeStayBtn.setVisibility(View.GONE);
					} else {
//						placeStayBtn.setVisibility(View.VISIBLE);
						placeStayBtn.setVisibility(View.GONE);
					}

					for (int j = 0; j < jsonAccomodationArray.length(); j++) {

						JSONObject jsonAccomodationObject = jsonAccomodationArray
								.getJSONObject(j);
						SeeAlsoItems AccomodationItems = new SeeAlsoItems();

						System.out.println("Accomodation ids are :"
								+ jsonAccomodationObject.getString("id"));
						AccomodationItems.id = jsonAccomodationObject
								.getString("id");
						AccomodationItems.title = jsonAccomodationObject
								.getString("title");
						AccomodationItems.address = jsonAccomodationObject
								.getString("address");
						System.out.println("Accomodation image paths are :"
								+ jsonAccomodationObject
										.getString("image_path"));
						// AccomodationItems.icon = jsonAccomodationObject
						// .getString("image_path");

						try {
							if (jsonAccomodationObject.getJSONArray(
									"image_path").length() > 0) {
								AccomodationItems.icon = jsonAccomodationObject
										.getJSONArray("image_path").get(0)
										.toString();
							}

						} catch (JSONException je) {
							// TODO: handle exception
							je.printStackTrace();
						}

						System.out.println("Accomodation latitude are :"
								+ jsonAccomodationObject
										.getString("latitude"));
						System.out.println("Accomodation longitude are :"
								+ jsonAccomodationObject
										.getString("longitude"));

						AccomodationItems.acclat = jsonAccomodationObject
								.getString("latitude");

						AccomodationItems.acclng = jsonAccomodationObject
								.getString("longitude");

						if (jsonAccomodationObject.has("book_url")) {
							System.out
									.println("Accomodation book_url are :"
											+ jsonAccomodationObject
													.getString("book_url"));
							AccomodationItems.bookUrl = jsonAccomodationObject
									.getString("book_url");
						}
						accomodationList.add(AccomodationItems);
					}
				} else {

					placeStayBtn.setVisibility(View.GONE);

				}

				if (jsonObjMain.has("Attractions")) {

					JSONObject jsonObject = jsonObjMain
							.getJSONObject("Attractions");

					SeeAlsoItems AttractionsItems = new SeeAlsoItems();

					System.out.println("Attractions ids are :"
							+ jsonObject.getString("id"));
					AttractionsItems.id = jsonObject.getString("id");
					AttractionsItems.title = jsonObject.getString("title");
					AttractionsItems.address = jsonObject
							.getString("address");
					AttractionsItems.about = jsonObject.getString("about")
							.toString().trim();

					// Favroties

					tit = jsonObject.getString("title");
					lat = jsonObject.getString("latitude");
					lng = jsonObject.getString("longitude");
					id = jsonObject.getString("id");
					address = jsonObject.getString("address");
					abt = AttractionsItems.about;

					try {
						if (jsonObject.getJSONArray("image_path").length() > 0) {
							image_path = jsonObject
									.getJSONArray("image_path").get(0)
									.toString();
						}

					} catch (JSONException je) {
						// TODO: handle exception
						je.printStackTrace();
					}

					// Favourites End

					attractionsList.add(AttractionsItems);

					System.out.println("Attractions latitude are :"
							+ jsonObject.getString("latitude"));
					System.out.println("Attractions longitude are :"
							+ jsonObject.getString("longitude"));

					System.out.println("Attractions by_bus are :"
							+ jsonObject.getString("by_bus"));

					System.out.println("Attractions by_train are :"
							+ jsonObject.getString("by_train"));
					System.out.println("Attractions by_air are :"
							+ jsonObject.getString("by_air"));

					JSONArray jsonAttrImagesArray = jsonObject
							.getJSONArray("image_path");

					titleTxt.setText(URLDecoder
							.decode(AttractionsItems.title));
					titleTxt.setTypeface(Constants.ProximaNova_Regular);

					if (AttractionsItems.about.equalsIgnoreCase("null")
							|| TextUtils.isEmpty(AttractionsItems.about)) {
						aboutLayout.setVisibility(View.GONE);
						aboutBtn.setVisibility(View.GONE);
					} else {
						aboutLayout.setVisibility(View.VISIBLE);
						aboutBtn.setVisibility(View.VISIBLE);
						aboutTxt.setVisibility(View.VISIBLE);
						if (Constants.selectLanguage.equalsIgnoreCase("1")) {
							aboutBtn.setText("About "
									+ URLDecoder
											.decode(AttractionsItems.title));
						} else {
							aboutBtn.setText(URLDecoder
									.decode(AttractionsItems.title)
									+ " "
									+ getResources().getString(
											R.string.details_about));
						}

						aboutBtn.setTypeface(Constants.ProximaNova_Regular);
						aboutTxt.setText(Html.fromHtml(URLDecoder.decode(
								AttractionsItems.about).replace("&nbsp;",
								" ")));
						aboutTxt.setTypeface(Constants.ProximaNova_Regular);
					}

					if (TextUtils.isEmpty(jsonObject.getString("by_bus"))
							&& TextUtils.isEmpty(jsonObject
									.getString("by_train"))
							&& TextUtils.isEmpty(jsonObject
									.getString("by_air"))) {
						gettingBtn.setVisibility(View.GONE);
					} else {
						gettingBtn.setVisibility(View.VISIBLE);
						if (!TextUtils.isEmpty(jsonObject
								.getString("by_bus"))) {
							byBusTxt.setText(URLDecoder.decode(jsonObject
									.getString("by_bus")));
							byBusTxt.setTypeface(Constants.ProximaNova_Regular);
						}
						if (!TextUtils.isEmpty(jsonObject
								.getString("by_train"))) {
							byTrainTxt.setText(URLDecoder.decode(jsonObject
									.getString("by_train")));
							byTrainTxt
									.setTypeface(Constants.ProximaNova_Regular);
						}
						if (!TextUtils.isEmpty(jsonObject
								.getString("by_air"))) {
							byCarTxt.setText(URLDecoder.decode(jsonObject
									.getString("by_air")));
							byCarTxt.setTypeface(Constants.ProximaNova_Regular);
						}
					}
					for (int j = 0; j < jsonAttrImagesArray.length(); j++) {

						SeeAlsoItems attractionImages = new SeeAlsoItems();
						attractionImages.icon = jsonAttrImagesArray
								.getString(j);
						// attractionsImagesList.add(attractionImages);

						settingImageToViewFlipper(j, attractionImages.icon,
								AttractionsItems.title);
					}

					getPackagesList(DetailScreen1.this);

					getAccomodationList(DetailScreen1.this);

					if (seeAlsoList.size() == 0) {
						seeAlsoLay.setVisibility(View.GONE);
					} else {
						seeAlsoLay.setVisibility(View.VISIBLE);
						g.setAdapter(new ImageAdapter(DetailScreen1.this));

						// g.setSelection(1, true);

						if (seeAlsoList.size() == 1) {
							g.setSelection(0, true);
						} else {
							g.setSelection(1, true);
						}
					}

					if (packagesList.size() == 0) {
						// packagesLay.setVisibility(View.GONE);
						packagesBtn.setVisibility(View.GONE);
					} else {
						// packagesLay.setVisibility(View.VISIBLE);
//						packagesBtn.setVisibility(View.VISIBLE);
						packagesBtn.setVisibility(View.GONE);
					}
					// if (MainActivity.detailNavFlag == 1) {
					// g.setAdapter(new ImageAdapter1(DetailScreen1.this));
					// } else if (MainActivity.detailNavFlag == 2) {
					// g.setAdapter(new ImageAdapter(DetailScreen1.this));
					// }

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	}

	private void accomodationJsonParsing(String responseString2) {
		// TODO Auto-generated method stub
		String url;

		try {

			JSONObject jsonObjMain = new JSONObject(responseString2);
			mViewFlipper.removeAllViews();

			if (jsonObjMain.has("Room")) {
				JSONArray jsonPackagesArray = jsonObjMain.getJSONArray("Room");
				String str = "";
				for (int j = 0; j < jsonPackagesArray.length(); j++) {

					JSONObject jsonPackagesObject = jsonPackagesArray
							.getJSONObject(j);
					SeeAlsoItems packagesItems = new SeeAlsoItems();

					System.out.println("packages ids are :"
							+ jsonPackagesObject.getString("room_type_id"));
					packagesItems.room_type_id = jsonPackagesObject
							.getString("room_type_id");
					System.out.println("packages description are :"
							+ jsonPackagesObject.getString("nos"));
					packagesItems.nos = jsonPackagesObject.getString("nos");

					System.out.println("packages titles are :"
							+ jsonPackagesObject.getString("cost"));
					packagesItems.cost = jsonPackagesObject.getString("cost");

				}

				if(roomTypeArray1.size() == 0){
					
					
					roomBtn.setVisibility(View.GONE);
				}else{
					for (int i = 0; i < roomTypeArray1.size(); i++) {
						str = str
								+ "<p><font size='3' color='#939393'><b>Rooms ("
								+ roomTypeArray1.get(i)
								+ ") : </b></font> <font size='3' color='red'><b>Rs. "
								+ roomCostArray1.get(i) + "/-</b></font></p>";
					}

					if (str.length() > 0) {
						roomBtn.setVisibility(View.VISIBLE);
						roomBtn.setText(getResources().getString(
								R.string.details_rooms));
						roomBtn.setTypeface(Constants.ProximaNova_Regular);
						roomTxt.setText(Html.fromHtml("<html lang='en-US'><body>"
								+ str + "</body></html"));
						roomTxt.setTypeface(Constants.ProximaNova_Regular);
					} else {
						roomBtn.setVisibility(View.GONE);
					}
				}
				
			}

			if (jsonObjMain.has("SeeAlso")) {

				JSONArray resultjsonArray = jsonObjMain.getJSONArray("SeeAlso");

				seeAlsoList = new ArrayList<SeeAlsoItems>();

				for (int i = 0; i < resultjsonArray.length(); i++) {

					JSONObject jsonObject = resultjsonArray.getJSONObject(i);

					System.out
							.println("ids are :" + jsonObject.getString("id"));
					System.out.println("image paths are :"
							+ jsonObject.getString("image_path"));

					SeeAlsoItems seeAlsoItems = new SeeAlsoItems();

					try {
						if (jsonObject.getJSONArray("image_path").length() > 0) {
							seeAlsoItems.icon = jsonObject
									.getJSONArray("image_path").get(0)
									.toString();
						}

					} catch (JSONException je) {
						// TODO: handle exception
						je.printStackTrace();
					}

					seeAlsoItems.id = jsonObject.getString("id");
					seeAlsoList.add(seeAlsoItems);

				}
			}
			if (jsonObjMain.has("StayPlace")) {

				JSONObject jsonObject = jsonObjMain.getJSONObject("StayPlace");

				SeeAlsoItems AttractionsItems = new SeeAlsoItems();

				System.out.println("Attractions ids are :"
						+ jsonObject.getString("description"));
				AttractionsItems.id = jsonObject.getString("id");
				AttractionsItems.title = jsonObject.getString("title");
				AttractionsItems.address = jsonObject.getString("address");
				AttractionsItems.description = jsonObject
						.getString("description");

				// Favroties

				tit = jsonObject.getString("title");
				lat = jsonObject.getString("latitude");
				lng = jsonObject.getString("longitude");
				id = jsonObject.getString("id");
				address = jsonObject.getString("address");
				abt = jsonObject.getString("description").trim();

				try {
					if (jsonObject.getJSONArray("image_path").length() > 0) {
						image_path = jsonObject.getJSONArray("image_path")
								.get(0).toString();
					}

				} catch (JSONException je) {
					// TODO: handle exception
					je.printStackTrace();
				}

				// Favourites End
				// attractionsList.add(AttractionsItems);

				System.out.println("Attractions latitude are :"
						+ jsonObject.getString("latitude"));
				System.out.println("Attractions longitude are :"
						+ jsonObject.getString("longitude"));

				// System.out.println("Attractions by_bus are :"
				// + jsonObject.getString("by_bus"));
				// System.out.println("Attractions by_train are :"
				// + jsonObject.getString("by_train"));
				// System.out.println("Attractions by_air are :"
				// + jsonObject.getString("by_air"));

				JSONArray jsonAttrImagesArray = jsonObject
						.getJSONArray("image_path");

				titleTxt.setText(URLDecoder.decode(AttractionsItems.title));
				titleTxt.setTypeface(Constants.ProximaNova_Regular);

				if (TextUtils.isEmpty(AttractionsItems.description)
						|| AttractionsItems.description.length() == 0) {
					aboutBtn2.setVisibility(View.GONE);
					aboutLayout1.setVisibility(View.GONE);
				} else {
					aboutLayout1.setVisibility(View.VISIBLE);

					if (Constants.selectLanguage.equalsIgnoreCase("1")) {
						aboutBtn2.setText("About "
								+ URLDecoder.decode(AttractionsItems.title));
					} else {
						aboutBtn2.setText(URLDecoder
								.decode(AttractionsItems.title)
								+ " "
								+ getResources().getString(
										R.string.details_about));
					}

					aboutBtn2.setTypeface(Constants.ProximaNova_Regular);
					aboutTxt1.setText(Html.fromHtml(URLDecoder.decode(
							AttractionsItems.description)
							.replace("&nbsp;", " ")));
					aboutTxt1.setTypeface(Constants.ProximaNova_Regular);
					aboutTxt1.setEllipsize(TextUtils.TruncateAt.END);
					aboutTxt1.setLines(4);
				}
				if (TextUtils.isEmpty(jsonObject.getString("facilities"))
						|| jsonObject.getString("facilities").length() == 0) {
					facilitiesBtn.setVisibility(View.GONE);
				} else {

					if (jsonObject.getString("facilities").equalsIgnoreCase(
							"null")) {
						facilitiesBtn.setVisibility(View.GONE);
					} else {
						facilitiesBtn.setVisibility(View.VISIBLE);
						facilitiesTxt.setText(Html
								.fromHtml("<html lang='en-US'><body><ul>"
										+ // <li>1. Open Air Auditorium 1600
											// persons
											// capacity.</li><br><br>" +
										URLDecoder.decode(jsonObject.getString(
												"facilities").replace("\r\n",
												"<br><br>"))
										+ "<br></ul></body></html"));
					}

				}

				url = jsonObject.getString("buy_url");

				ArrayList<String> lists = new ArrayList<String>();
				/*
				 * given string will be split by the argument delimiter
				 * provided.
				 */

				if (SplashScreen.hotel_pref.getString(
						SplashScreen.GET_HOTELS_IDS, "").length() > 0) {
					String[] temp = SplashScreen.hotel_pref.getString(
							SplashScreen.GET_HOTELS_IDS, "").split(",");
					/* print substrings */
					lists.clear();
					for (int i = 0; i < temp.length; i++) {
						System.out.println(temp[i]);
						lists.add(temp[i]);
					}

					if (lists.contains(Constants.new_id)) {

						// Toast.makeText(DetailScreen1.this, "found",
						// Toast.LENGTH_LONG).show();

						buyticketBtn.setVisibility(View.VISIBLE);
						buyticketBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								startActivity(new Intent(DetailScreen1.this,
										HotelBookingActivity.class));

							}

						});

					} else {
						buyticketBtn.setVisibility(View.GONE);

						// AlertDialog.Builder altDialog = new
						// AlertDialog.Builder(
						// DetailScreen1.this);
						// altDialog
						// .setMessage("This service is presently not available");
						// // here
						// // add
						// // your
						// // message
						// altDialog
						// .setNeutralButton(
						// "OK",
						// new DialogInterface.OnClickListener() {
						// @Override
						// public void onClick(
						// DialogInterface dialog,
						// int which) {
						// dialog.dismiss();
						// }
						// });
						// altDialog.show();
					}
				}

				// if (TextUtils.isEmpty(url) || url.length() == 0) {
				// buyticketBtn.setVisibility(View.GONE);
				// } else {
				// buyticketBtn.setVisibility(View.VISIBLE);
				// buyticketBtn
				// .setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// // if (!url.startsWith("http://")
				// // && !url.startsWith("https://"))
				// // url = "http://" + url;
				// // Intent browserIntent = new
				// // Intent(
				// // Intent.ACTION_VIEW, Uri
				// // .parse(url));
				// // startActivity(browserIntent);
				//
				// ArrayList<String> lists = new ArrayList<String>();
				// /*
				// * given string will be split by the argument delimiter
				// * provided.
				// */
				//
				// if(SplashScreen.hotel_pref.getString(
				// SplashScreen.GET_HOTELS_IDS, "").length() > 0){
				// String[] temp = SplashScreen.hotel_pref.getString(
				// SplashScreen.GET_HOTELS_IDS, "").split(",");
				// /* print substrings */
				// lists.clear();
				// for (int i = 0; i < temp.length; i++) {
				// System.out.println(temp[i]);
				// lists.add(temp[i]);
				// }
				//
				// if (lists.contains(Constants.new_id)) {
				//
				// // Toast.makeText(DetailScreen1.this, "found",
				// // Toast.LENGTH_LONG).show();
				//
				// startActivity(new Intent(
				// DetailScreen1.this,
				// HotelBookingActivity.class));
				//
				// // finish();
				//
				// } else {
				//
				//
				// AlertDialog.Builder altDialog = new AlertDialog.Builder(
				// DetailScreen1.this);
				// altDialog
				// .setMessage("This service is presently not available"); //
				// here
				// // add
				// // your
				// // message
				// altDialog
				// .setNeutralButton(
				// "OK",
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(
				// DialogInterface dialog,
				// int which) {
				// dialog.dismiss();
				// }
				// });
				// altDialog.show();
				// }
				// }
				//
				//
				//
				// }
				// });
				// }

				// byBusTxt.setText(jsonObject.getString("by_bus"));
				// byTrainTxt.setText(jsonObject.getString("by_train"));
				// byCarTxt.setText(jsonObject.getString("by_air"));

				for (int j = 0; j < jsonAttrImagesArray.length(); j++) {

					SeeAlsoItems attractionImages = new SeeAlsoItems();
					attractionImages.icon = jsonAttrImagesArray.getString(j);
					// attractionsImagesList.add(attractionImages);

					settingImageToViewFlipper(j,
							jsonAttrImagesArray.getString(j),
							AttractionsItems.title);

				}

				// getPackagesList(DetailScreen1.this);
				// getAccomodationList(DetailScreen1.this);

				if (seeAlsoList.size() == 0) {
					seeAlsoLay.setVisibility(View.GONE);
				} else {
					seeAlsoLay.setVisibility(View.VISIBLE);
					g.setAdapter(new ImageAdapter(DetailScreen1.this));

					if (seeAlsoList.size() == 1) {
						g.setSelection(0, true);
					} else {
						g.setSelection(1, true);
					}

				}
				// if (MainActivity.detailNavFlag == 1) {
				// g.setAdapter(new ImageAdapter1(DetailScreen1.this));
				// } else if (MainActivity.detailNavFlag == 2) {
				// g.setAdapter(new ImageAdapter(DetailScreen1.this));
				// }

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
	}
	
	private void packagesJsonParsing(String responseString2) {
		
		String url;
		try {

			JSONObject jsonObjMain = new JSONObject(responseString2);
			mViewFlipper.removeAllViews();
			toursList = new ArrayList<SeeAlsoItems>();

			JSONArray jsonPackagesArray = jsonObjMain
					.getJSONArray("TourType");// ("Package");
			String str = "";
			for (int j = 0; j < jsonPackagesArray.length(); j++) {

				JSONObject jsonPackagesObject = jsonPackagesArray
						.getJSONObject(j);
				SeeAlsoItems touresItems = new SeeAlsoItems();
				touresItems.tour_type = jsonPackagesObject
						.getString("tour_type");
				touresItems.fare_type = jsonPackagesObject
						.getString("fare_type");
				touresItems.cost = jsonPackagesObject.getString("cost");
				toursList.add(touresItems);

				String fareType = "";

				// if(URLDecoder.decode(touresItems.fare_type).equalsIgnoreCase("null")){
				// fareType = "Adult";
				// }else{
				// fareType = URLDecoder.decode(touresItems.fare_type);
				// }
				str = str
						+ "<p><font size='3' color='#939393'><b>Tickets ("
						+ URLDecoder.decode(touresItems.tour_type)
						+ ") : </b></font><font size='3' color='#939393'> "
						+ URLDecoder.decode(touresItems.fare_type).replace(
								"null", "Adult")
						+ "&nbsp; &nbsp; </b></font> <font size='3' color='red'><b>Rs. "
						+ URLDecoder.decode(touresItems.cost)
						+ "</b></font></p>";
				System.out.println(" string for tour type " + str);
			}

			// if (str.length() > 0) {
			// roomBtn.setVisibility(View.VISIBLE);
			// roomBtn.setText(getResources().getString(
			// R.string.details_tickets));
			// roomBtn.setTypeface(Constants.ProximaNova_Regular);
			// roomTxt.setText(Html.fromHtml("<html lang='en-US'><body>"
			// + str + "</body></html"));
			// roomTxt.setTypeface(Constants.ProximaNova_Regular);
			// } else {
			roomBtn.setVisibility(View.GONE);
			// }

			if (jsonObjMain.has("Package")) {
				JSONObject jsonPackagesObject = jsonObjMain
						.getJSONObject("Package");

				// SeeAlsoItems packagesItems = new SeeAlsoItems();

				System.out.println("packages description are :"
						+ jsonPackagesObject.getString("description"));

				tit = jsonPackagesObject.getString("title");
				abt = jsonPackagesObject.getString("description").trim();
				url = jsonPackagesObject.getString("package_url");

//				if (TextUtils.isEmpty(url) || url.length() == 0) {
//					buyticketBtn.setVisibility(View.GONE);
//				} else {
//					buyticketBtn.setVisibility(View.VISIBLE);
//					buyticketBtn.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							// if (!url.startsWith("http://")
//							// && !url.startsWith("https://"))
//							// url = "http://" + url;
//							// Intent browserIntent = new Intent(
//							// Intent.ACTION_VIEW, Uri.parse(url));
//							// startActivity(browserIntent);
//
//							ArrayList<String> lists = new ArrayList<String>();
//							/*
//							 * given string will be split by the argument
//							 * delimiter provided.
//							 */
//
//							if (SplashScreen.hotel_pref.getString(
//									SplashScreen.GET_PACKAGE_IDS, "")
//									.length() > 0) {
//								String[] temp = SplashScreen.hotel_pref
//										.getString(
//												SplashScreen.GET_PACKAGE_IDS,
//												"").split(",");
//								/* print substrings */
//								lists.clear();
//								for (int i = 0; i < temp.length; i++) {
//									System.out.println(temp[i]);
//									lists.add(temp[i]);
//								}
//
//								if (lists.contains(Constants.new_pack_id)) {
//
//									// Toast.makeText(DetailScreen1.this,
//									// "found",
//									// Toast.LENGTH_LONG).show();
//
//									startActivity(new Intent(
//											DetailScreen1.this,
//											PackageListing.class));
//
//									// finish();
//
//								} else {
//
//									AlertDialog.Builder altDialog = new AlertDialog.Builder(
//											DetailScreen1.this);
//									altDialog
//											.setMessage("This service is presently not available"); // here
//									// add
//									// your
//									// message
//									altDialog
//											.setNeutralButton(
//													"OK",
//													new DialogInterface.OnClickListener() {
//														@Override
//														public void onClick(
//																DialogInterface dialog,
//																int which) {
//															dialog.dismiss();
//														}
//													});
//									altDialog.show();
//								}
//							}
//
//							//
//							// finish();
//						}
//					});
//				}
				
				ArrayList<String> lists = new ArrayList<String>();
				/*
				 * given string will be split by the argument
				 * delimiter provided.
				 */

				if (SplashScreen.hotel_pref.getString(
						SplashScreen.GET_PACKAGE_IDS, "")
						.length() > 0) {
					String[] temp = SplashScreen.hotel_pref
							.getString(
									SplashScreen.GET_PACKAGE_IDS,
									"").split(",");
					/* print substrings */
					lists.clear();
					for (int i = 0; i < temp.length; i++) {
						System.out.println(temp[i]);
						lists.add(temp[i]);
					}

					if (lists.contains(Constants.new_pack_id)) {

						// Toast.makeText(DetailScreen1.this,
						// "found",
						// Toast.LENGTH_LONG).show();

						buyticketBtn.setVisibility(View.VISIBLE);
						buyticketBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								startActivity(new Intent(
										DetailScreen1.this,
										PackageListing.class));
							}
						});
						

						// finish();

					} else {
						buyticketBtn.setVisibility(View.GONE);
//						AlertDialog.Builder altDialog = new AlertDialog.Builder(
//								DetailScreen1.this);
//						altDialog
//								.setMessage("This service is presently not available"); // here
//						// add
//						// your
//						// message
//						altDialog
//								.setNeutralButton(
//										"OK",
//										new DialogInterface.OnClickListener() {
//											@Override
//											public void onClick(
//													DialogInterface dialog,
//													int which) {
//												dialog.dismiss();
//											}
//										});
//						altDialog.show();
					}
				}

				titleTxt.setText(URLDecoder.decode(tit));
				titleTxt.setTypeface(Constants.ProximaNova_Regular);
				if (TextUtils.isEmpty(abt) || abt.trim().length() == 0
						|| abt.equalsIgnoreCase("null")) {
					aboutLayout1.setVisibility(View.GONE);
					aboutBtn2.setVisibility(View.GONE);
					roomLay.setVisibility(View.VISIBLE);
					roomBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.less), null);
				} else {
					roomLay.setVisibility(View.GONE);
					roomBtn.setCompoundDrawablesWithIntrinsicBounds(null,
							null, getApplicationContext().getResources()
									.getDrawable(R.drawable.more), null);
					aboutLayout1.setVisibility(View.VISIBLE);
					if (Constants.selectLanguage.equalsIgnoreCase("1")) {
						aboutBtn2
								.setText("About " + URLDecoder.decode(tit));
					} else {
						aboutBtn2.setText(URLDecoder.decode(tit)
								+ " "
								+ getResources().getString(
										R.string.details_about));
					}

					aboutBtn2.setTypeface(Constants.ProximaNova_Regular);
					aboutTxt1.setText(Html.fromHtml(URLDecoder.decode(abt)
							.replace("&nbsp;", " ")));
					aboutTxt1.setTypeface(Constants.ProximaNova_Regular);
				}

				settingImageToViewFlipper(0,
						jsonPackagesObject.getString("image"), tit);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
	}
	
	

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		int itemId = item.getItemId();
		switch (itemId) {
		case android.R.id.home:
			// do your action here.

			finish();
			break;

		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		// menu.findItem(R.id.action_filter).setVisible(false);
		// menu.findItem(R.id.action_location).setVisible(true);
		// // menu.findItem(R.id.action_search).setVisible(false);
		//
		// MenuItem searchItem = menu.findItem(R.id.action_search)
		// .setVisible(true).setIcon(R.drawable.search_icon);
		// mSearchView = (SearchView) searchItem.getActionView();
		//
		// // int searchImgId = getResources().getIdentifier(
		// // "android:id/search_button", null, null);
		// // ImageView v = (ImageView) mSearchView.findViewById(searchImgId);
		// // v.setImageResource(R.drawable.search_icon);
		// setupSearchView(searchItem);
		//
		// invalidateOptionsMenu();
		return true;
	}

	@SuppressLint("NewApi")
	private void setupSearchView(MenuItem searchItem) {

		if (isAlwaysExpanded()) {
			mSearchView.setIconifiedByDefault(false);
		} else {
			searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}

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

	class GetHotelsPrice extends AsyncTask<String, Void, String> {

		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailScreen1.this);
			pDialog.setMessage("Loading please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}
		
		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+Constants.Base_url1
					+ "hoteltypes.jsp?code=" + Constants.new_id + "&dt="
					+ Constants.currentDate);
			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "hoteltypes.jsp?code=" + Constants.new_id + "&dt="
					+ Constants.currentDate);
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
					JSONArray json = new JSONArray(result);

					for (int i = 0; i < json.length(); i++) {
						JSONObject e = json.getJSONObject(i);

						// stringBuilder.append(","+e.getString("unitCode"));
						// map.put("unitName", "Unit Name: " +
						// e.getString("unitName"));

						roomTypeArray1.add(e.getString("roomTypeName")
								.toString());
						roomCostArray1
								.add(e.getString("roomTariff").toString());

						// mylist.add(map);
					}

					accomodationJsonParsing(responseString);

					// Toast.makeText(MainActivity.this,
					// ""+stringBuilder.toString().replaceFirst(",", ""),
					// Toast.LENGTH_LONG).show();

				} catch (Exception e) {
					// TODO: handle exception
					Log.e("Tag", "" + e);
					
					if (pDialog.isShowing() && pDialog != null) {
						pDialog.dismiss();
					}
				}
				
				if (pDialog.isShowing() && pDialog != null) {
					pDialog.dismiss();
				}

			}

		}
	}

	class GetRoomTypesz extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailScreen1.this);
			pDialog.setMessage("Loading please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {

			 System.out.println("url is"+Constants.Base_url1
						+ "hoteltypes.jsp?code=" + Constants.new_id + "&dt="
						+ Constants.currentDate);

			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "hoteltypes.jsp?code=" + Constants.new_id + "&dt="
					+ Constants.currentDate);

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			Log.e("Result : ", result);
			if (null == result || result.length() == 0) {
				if (pDialog.isShowing() && pDialog != null) {
					pDialog.dismiss();
				}
			} else {

				try {
					JSONArray json = new JSONArray(result);

					for (int i = 0; i < json.length(); i++) {
						JSONObject e = json.getJSONObject(i);

						// stringBuilder.append(","+e.getString("unitCode"));
						// map.put("unitName", "Unit Name: " +
						// e.getString("unitName"));

						roomTypeArray1.add(e.getString("roomTypeName")
								.toString());
						roomCostArray1
								.add(e.getString("roomTariff").toString());

						// mylist.add(map);
					}

					// Toast.makeText(MainActivity.this,
					// ""+stringBuilder.toString().replaceFirst(",", ""),
					// Toast.LENGTH_LONG).show();
					
					if (pDialog.isShowing() && pDialog != null) {
						pDialog.dismiss();
					}

				} catch (Exception e) {
					// TODO: handle exception
					Log.e("Tag", "" + e);
					if (pDialog.isShowing() && pDialog != null) {
						pDialog.dismiss();
					}
				}
				
				if (pDialog.isShowing() && pDialog != null) {
					pDialog.dismiss();
				}

			}
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, "6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}

}
