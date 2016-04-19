package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.flurry.android.FlurryAgent;
import com.telanganatourism.android.phase2.SettingsActivity.TrackingServiceTask;
import com.telanganatourism.android.phase2.util.Utility;

public class SettingsFragment extends Fragment {

	Button submitBtn;
	LinearLayout lay;
	View view;
	LayoutInflater inflateer = null;
	int addValue = 0;

	ViewGroup containerView;

	ToggleButton toggleButton;

	EditText userNameedt, userPhoneedt, contact1, contact2, contact3, phone1,
			phone2, phone3;

	public static SharedPreferences pref;
	public static Editor editor;
	public static String Key_GET_USER_ID = "USER_ID";

	JSONObject res;
	JSONArray ja;
	HttpResponse response1;
	LinearLayout linearLayout1, linearLayout2;

//	Utility utility;

	String currentLanguage = "";

	Configuration config;

	TextView changeFontSizeTxt, selectLanguageTxt;

	TextView radioEnglish, radioTelugu, radioHindi, radioUrdu, radioSexButton;

	TextView radioSmall, radioNormal, radioLarge,tv_select_font;

	int fontChaning = 0;

	TextView trackTxt, langTxt, t1Txt, t2Txt, t3Txt, t4Txt;

	public SettingsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.settings, container, false);

		SplashScreen.name = SplashScreen.pref1.getString(
				SplashScreen.Key_GET_LANGUAGE_ID, "");

		if (SplashScreen.pref1.getString(SplashScreen.Key_GET_LANGUAGE_ID, "")
				.length() > 0) {

			Constants.selectLanguage = SplashScreen.pref1.getString(
					SplashScreen.Key_GET_LANGUAGE_ID, "");
		} else {
			Constants.selectLanguage = "1";
		}
		// Constants.selectLanguage =
		// SplashScreen.pref1.getString(SplashScreen.Key_GET_LANGUAGE_ID, "");
		// Toast.makeText(getApplicationContext(), name,
		// Toast.LENGTH_SHORT).show();
		


		changeFontSizeTxt = (TextView) rootView
				.findViewById(R.id.changeFontSize);

		selectLanguageTxt = (TextView) rootView
				.findViewById(R.id.selectLanguageBtn);

		SplashScreen.name = SplashScreen.pref1.getString(
				SplashScreen.Key_GET_LANGUAGE_ID, "");

		if (SplashScreen.name.length() > 0) {
			if (SplashScreen.name.equals("1")) {
				// radioEnglish.setCompoundDrawablesWithIntrinsicBounds(0, 0,
				// R.drawable.check_selected, 0);
				selectLanguageTxt.setText("English");
			} else if (SplashScreen.name.equals("2")) {
				// radioTelugu.setCompoundDrawablesWithIntrinsicBounds(0, 0,
				// R.drawable.check_selected, 0);
				selectLanguageTxt.setText("Telugu");
			}

			else if (SplashScreen.name.equals("3")) {
				// radioUrdu.setCompoundDrawablesWithIntrinsicBounds(0, 0,
				// R.drawable.check_selected, 0);
				selectLanguageTxt.setText("Urdu");
			}

			else if (SplashScreen.name.equals("4")) {
				// radioHindi.setCompoundDrawablesWithIntrinsicBounds(0, 0,
				// R.drawable.check_selected, 0);
				selectLanguageTxt.setText("Hindi");
			}
		}
		
		

		selectLanguageTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// custom dialog
				final Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.select_language_dialog);

				// set the custom dialog components - text, image and
				// button
				radioEnglish = (TextView) dialog.findViewById(R.id.engTxt);
				radioTelugu = (TextView) dialog.findViewById(R.id.teluTxt);
				radioHindi = (TextView) dialog.findViewById(R.id.hindiTxt);
				radioUrdu = (TextView) dialog.findViewById(R.id.urduTxt);
				radioEnglish.setTypeface(Constants.ProximaNova_Regular);
				radioTelugu.setTypeface(Constants.ProximaNova_Regular);
				radioHindi.setTypeface(Constants.ProximaNova_Regular);
				radioUrdu.setTypeface(Constants.ProximaNova_Regular);

				SplashScreen.name = SplashScreen.pref1.getString(
						SplashScreen.Key_GET_LANGUAGE_ID, "");
				if (SplashScreen.name.length() > 0) {
					if (SplashScreen.name.equals("1")) {
						radioEnglish.setCompoundDrawablesWithIntrinsicBounds(0,
								0, R.drawable.check_selected, 0);
						selectLanguageTxt.setText("English");
					} else if (SplashScreen.name.equals("2")) {
						radioTelugu.setCompoundDrawablesWithIntrinsicBounds(0,
								0, R.drawable.check_selected, 0);
						selectLanguageTxt.setText("Telugu");
					}

					else if (SplashScreen.name.equals("3")) {
						radioUrdu.setCompoundDrawablesWithIntrinsicBounds(0, 0,
								R.drawable.check_selected, 0);
						selectLanguageTxt.setText("Urdu");
					}

					else if (SplashScreen.name.equals("4")) {
						radioHindi.setCompoundDrawablesWithIntrinsicBounds(0,
								0, R.drawable.check_selected, 0);
						selectLanguageTxt.setText("Hindi");
					}
				}

				radioEnglish.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String languageToLoad = "en";
						Locale locale = new Locale(languageToLoad);
						Locale.setDefault(locale);
						Configuration config = new Configuration();
						config.locale = locale;
						getActivity().getBaseContext().getResources()
								.updateConfiguration(config, null);

						Constants.ProximaNova_Regular = Typeface
								.createFromAsset(getActivity().getAssets(),
										"ProximaNova-Regular.otf");

						SplashScreen.flg = "1";
						SplashScreen.editor1.putString(
								SplashScreen.Key_GET_LANGUAGE_ID, ""
										+ SplashScreen.flg);
						SplashScreen.editor1.commit();
						selectLanguageTxt.setText("English");
						Constants.selectLanguage = SplashScreen.pref1
								.getString(SplashScreen.Key_GET_LANGUAGE_ID, "");
						dialog.dismiss();
						
						MainActivity.homeBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.myreservations.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.destinationBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.accomodatonBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.eventsBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.shopBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.packagesBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.cultureBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.favroitesBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.weatherBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.settingBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.contactBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.emergencyBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.feedbackBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.trackingTxt.setTypeface(Constants.ProximaNova_Regular);
						
						MainActivity.homeBtn.setText(R.string.home_sidemenu);
						MainActivity.myreservations.setText(R.string.reservation_sidemenu);
						MainActivity.destinationBtn.setText(R.string.destination_sidemenu);
						MainActivity.accomodatonBtn.setText(R.string.accomodation_sidemenu);
						MainActivity.eventsBtn.setText(R.string.events_sidemenu);
						MainActivity.shopBtn.setText(R.string.shopping_sidemenu);
						MainActivity.packagesBtn.setText(R.string.packages_sidemenu);
						MainActivity.cultureBtn.setText(R.string.culture_sidemenu);
						MainActivity.favroitesBtn.setText(R.string.favorites_sidemenu);
						MainActivity.weatherBtn.setText(R.string.weather_sidemenu);
						MainActivity.settingBtn.setText(R.string.settings_sidemenu);
						MainActivity.contactBtn.setText(R.string.contactus_sidemenu);
						MainActivity.emergencyBtn.setText(R.string.emergency_sidemenu);
						MainActivity.feedbackBtn.setText(R.string.feed_back_sidemenu);
						MainActivity.trackingTxt.setText(R.string.setting_tracking);
						
						MainActivity.hydTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.khammTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.mbnrTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.nlgTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.warngalTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.adilabadTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.nizamabadTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.karimnagarTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.medakTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.rangareddyTxt.setTypeface(Constants.ProximaNova_Regular);
						
						MainActivity.hydTxt.setText(R.string.location_hyderabad);
						MainActivity.khammTxt.setText(R.string.location_khammam);
						MainActivity.mbnrTxt.setText(R.string.location_mbnr);
						MainActivity.nlgTxt.setText(R.string.location_nalgonda);
						MainActivity.warngalTxt.setText(R.string.location_warangal);
						MainActivity.adilabadTxt.setText(R.string.location_adilabad);
						MainActivity.nizamabadTxt.setText(R.string.location_nizamabad);
						MainActivity.karimnagarTxt.setText(R.string.location_karimnagar);
						MainActivity.medakTxt.setText(R.string.location_medak);
						MainActivity.rangareddyTxt.setText(R.string.location_rangareddy);
						
//						startActivity(new Intent(getActivity(),
//								MainActivity.class));
						
						HomeFragment fragment = new HomeFragment();

						if (fragment != null) {
//							FragmentManager fragmentManager = getFragmentManager();
//							fragmentManager.beginTransaction()
//									.replace(R.id.frame_container, fragment).commit();
							
							final FragmentTransaction ft = getFragmentManager()
									.beginTransaction();
							ft.replace(R.id.frame_container, fragment,
									getResources().getString(R.string.home_sidemenu));
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
				});

				radioTelugu.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String languageToLoad = "te";
						Locale locale = new Locale(languageToLoad);
						Locale.setDefault(locale);
						Configuration config = new Configuration();
						config.locale = locale;
						getActivity().getBaseContext().getResources()
								.updateConfiguration(config, null);

						Constants.ProximaNova_Regular = Typeface
								.createFromAsset(getActivity().getAssets(),
										"gautami.ttf");

						SplashScreen.flg = "2";
						SplashScreen.editor1.putString(
								SplashScreen.Key_GET_LANGUAGE_ID, ""
										+ SplashScreen.flg);
						SplashScreen.editor1.commit();
						selectLanguageTxt.setText("Telugu");
						Constants.selectLanguage = SplashScreen.pref1
								.getString(SplashScreen.Key_GET_LANGUAGE_ID, "");
						dialog.dismiss();
//						startActivity(new Intent(getActivity(),
//								MainActivity.class));
						
						MainActivity.homeBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.myreservations.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.destinationBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.accomodatonBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.eventsBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.shopBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.packagesBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.cultureBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.favroitesBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.weatherBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.settingBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.contactBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.emergencyBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.feedbackBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.trackingTxt.setTypeface(Constants.ProximaNova_Regular);
						
						MainActivity.homeBtn.setText(R.string.home_sidemenu);
						MainActivity.myreservations.setText(R.string.reservation_sidemenu);
						MainActivity.destinationBtn.setText(R.string.destination_sidemenu);
						MainActivity.accomodatonBtn.setText(R.string.accomodation_sidemenu);
						MainActivity.eventsBtn.setText(R.string.events_sidemenu);
						MainActivity.shopBtn.setText(R.string.shopping_sidemenu);
						MainActivity.packagesBtn.setText(R.string.packages_sidemenu);
						MainActivity.cultureBtn.setText(R.string.culture_sidemenu);
						MainActivity.favroitesBtn.setText(R.string.favorites_sidemenu);
						MainActivity.weatherBtn.setText(R.string.weather_sidemenu);
						MainActivity.settingBtn.setText(R.string.settings_sidemenu);
						MainActivity.contactBtn.setText(R.string.contactus_sidemenu);
						MainActivity.emergencyBtn.setText(R.string.emergency_sidemenu);
						MainActivity.feedbackBtn.setText(R.string.feed_back_sidemenu);
						MainActivity.trackingTxt.setText(R.string.setting_tracking);
						
						MainActivity.hydTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.khammTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.mbnrTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.nlgTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.warngalTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.adilabadTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.nizamabadTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.karimnagarTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.medakTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.rangareddyTxt.setTypeface(Constants.ProximaNova_Regular);
						
						MainActivity.hydTxt.setText(R.string.location_hyderabad);
						MainActivity.khammTxt.setText(R.string.location_khammam);
						MainActivity.mbnrTxt.setText(R.string.location_mbnr);
						MainActivity.nlgTxt.setText(R.string.location_nalgonda);
						MainActivity.warngalTxt.setText(R.string.location_warangal);
						MainActivity.adilabadTxt.setText(R.string.location_adilabad);
						MainActivity.nizamabadTxt.setText(R.string.location_nizamabad);
						MainActivity.karimnagarTxt.setText(R.string.location_karimnagar);
						MainActivity.medakTxt.setText(R.string.location_medak);
						MainActivity.rangareddyTxt.setText(R.string.location_rangareddy);
						
						HomeFragment fragment = new HomeFragment();

						if (fragment != null) {
//							FragmentManager fragmentManager = getFragmentManager();
//							fragmentManager.beginTransaction()
//									.replace(R.id.frame_container, fragment).commit();
							
							final FragmentTransaction ft = getFragmentManager()
									.beginTransaction();
							ft.replace(R.id.frame_container, fragment,
									getResources().getString(R.string.home_sidemenu));
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
				});

				radioHindi.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String languageToLoad = "hi";
						Locale locale = new Locale(languageToLoad);
						Locale.setDefault(locale);
						Configuration config = new Configuration();
						config.locale = locale;
						getActivity().getBaseContext().getResources()
								.updateConfiguration(config, null);

						Constants.ProximaNova_Regular = Typeface
								.createFromAsset(getActivity().getAssets(),
										"ProximaNova-Regular.otf");

						SplashScreen.flg = "4";
						SplashScreen.editor1.putString(
								SplashScreen.Key_GET_LANGUAGE_ID, ""
										+ SplashScreen.flg);
						SplashScreen.editor1.commit();
						selectLanguageTxt.setText("Hindi");
						Constants.selectLanguage = SplashScreen.pref1
								.getString(SplashScreen.Key_GET_LANGUAGE_ID, "");
						dialog.dismiss();
						
						MainActivity.homeBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.myreservations.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.destinationBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.accomodatonBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.eventsBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.shopBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.packagesBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.cultureBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.favroitesBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.weatherBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.settingBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.contactBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.emergencyBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.feedbackBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.trackingTxt.setTypeface(Constants.ProximaNova_Regular);
						
						MainActivity.homeBtn.setText(R.string.home_sidemenu);
						MainActivity.myreservations.setText(R.string.reservation_sidemenu);
						MainActivity.destinationBtn.setText(R.string.destination_sidemenu);
						MainActivity.accomodatonBtn.setText(R.string.accomodation_sidemenu);
						MainActivity.eventsBtn.setText(R.string.events_sidemenu);
						MainActivity.shopBtn.setText(R.string.shopping_sidemenu);
						MainActivity.packagesBtn.setText(R.string.packages_sidemenu);
						MainActivity.cultureBtn.setText(R.string.culture_sidemenu);
						MainActivity.favroitesBtn.setText(R.string.favorites_sidemenu);
						MainActivity.weatherBtn.setText(R.string.weather_sidemenu);
						MainActivity.settingBtn.setText(R.string.settings_sidemenu);
						MainActivity.contactBtn.setText(R.string.contactus_sidemenu);
						MainActivity.emergencyBtn.setText(R.string.emergency_sidemenu);
						MainActivity.feedbackBtn.setText(R.string.feed_back_sidemenu);
						MainActivity.trackingTxt.setText(R.string.setting_tracking);
						
						MainActivity.hydTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.khammTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.mbnrTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.nlgTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.warngalTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.adilabadTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.nizamabadTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.karimnagarTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.medakTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.rangareddyTxt.setTypeface(Constants.ProximaNova_Regular);
						
						MainActivity.hydTxt.setText(R.string.location_hyderabad);
						MainActivity.khammTxt.setText(R.string.location_khammam);
						MainActivity.mbnrTxt.setText(R.string.location_mbnr);
						MainActivity.nlgTxt.setText(R.string.location_nalgonda);
						MainActivity.warngalTxt.setText(R.string.location_warangal);
						MainActivity.adilabadTxt.setText(R.string.location_adilabad);
						MainActivity.nizamabadTxt.setText(R.string.location_nizamabad);
						MainActivity.karimnagarTxt.setText(R.string.location_karimnagar);
						MainActivity.medakTxt.setText(R.string.location_medak);
						MainActivity.rangareddyTxt.setText(R.string.location_rangareddy);
						
//						startActivity(new Intent(getActivity(),
//								MainActivity.class));
						
						HomeFragment fragment = new HomeFragment();

						if (fragment != null) {
//							FragmentManager fragmentManager = getFragmentManager();
//							fragmentManager.beginTransaction()
//									.replace(R.id.frame_container, fragment).commit();
							
							final FragmentTransaction ft = getFragmentManager()
									.beginTransaction();
							ft.replace(R.id.frame_container, fragment,
									getResources().getString(R.string.home_sidemenu));
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
				});

				radioUrdu.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String languageToLoad = "ur";
						Locale locale = new Locale(languageToLoad);
						Locale.setDefault(locale);
						Configuration config = new Configuration();
						config.locale = locale;
						getActivity().getBaseContext().getResources()
								.updateConfiguration(config, null);

						Constants.ProximaNova_Regular = Typeface
								.createFromAsset(getActivity().getAssets(),
										"asunaskh.ttf");

						SplashScreen.flg = "3";
						SplashScreen.editor1.putString(
								SplashScreen.Key_GET_LANGUAGE_ID, ""
										+ SplashScreen.flg);
						SplashScreen.editor1.commit();
						selectLanguageTxt.setText("Urdu");
						Constants.selectLanguage = SplashScreen.pref1
								.getString(SplashScreen.Key_GET_LANGUAGE_ID, "");
						dialog.dismiss();
//						startActivity(new Intent(getActivity(),
//								MainActivity.class));
						
						MainActivity.homeBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.myreservations.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.destinationBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.accomodatonBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.eventsBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.shopBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.packagesBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.cultureBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.favroitesBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.weatherBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.settingBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.contactBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.emergencyBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.feedbackBtn.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.trackingTxt.setTypeface(Constants.ProximaNova_Regular);
						
						MainActivity.homeBtn.setText(R.string.home_sidemenu);
						MainActivity.myreservations.setText(R.string.reservation_sidemenu);
						MainActivity.destinationBtn.setText(R.string.destination_sidemenu);
						MainActivity.accomodatonBtn.setText(R.string.accomodation_sidemenu);
						MainActivity.eventsBtn.setText(R.string.events_sidemenu);
						MainActivity.shopBtn.setText(R.string.shopping_sidemenu);
						MainActivity.packagesBtn.setText(R.string.packages_sidemenu);
						MainActivity.cultureBtn.setText(R.string.culture_sidemenu);
						MainActivity.favroitesBtn.setText(R.string.favorites_sidemenu);
						MainActivity.weatherBtn.setText(R.string.weather_sidemenu);
						MainActivity.settingBtn.setText(R.string.settings_sidemenu);
						MainActivity.contactBtn.setText(R.string.contactus_sidemenu);
						MainActivity.emergencyBtn.setText(R.string.emergency_sidemenu);
						MainActivity.feedbackBtn.setText(R.string.feed_back_sidemenu);
						MainActivity.trackingTxt.setText(R.string.setting_tracking);
						
						MainActivity.hydTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.khammTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.mbnrTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.nlgTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.warngalTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.adilabadTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.nizamabadTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.karimnagarTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.medakTxt.setTypeface(Constants.ProximaNova_Regular);
						MainActivity.rangareddyTxt.setTypeface(Constants.ProximaNova_Regular);
						
						MainActivity.hydTxt.setText(R.string.location_hyderabad);
						MainActivity.khammTxt.setText(R.string.location_khammam);
						MainActivity.mbnrTxt.setText(R.string.location_mbnr);
						MainActivity.nlgTxt.setText(R.string.location_nalgonda);
						MainActivity.warngalTxt.setText(R.string.location_warangal);
						MainActivity.adilabadTxt.setText(R.string.location_adilabad);
						MainActivity.nizamabadTxt.setText(R.string.location_nizamabad);
						MainActivity.karimnagarTxt.setText(R.string.location_karimnagar);
						MainActivity.medakTxt.setText(R.string.location_medak);
						MainActivity.rangareddyTxt.setText(R.string.location_rangareddy);
						
						HomeFragment fragment = new HomeFragment();

						if (fragment != null) {
//							FragmentManager fragmentManager = getFragmentManager();
//							fragmentManager.beginTransaction()
//									.replace(R.id.frame_container, fragment).commit();
							
							final FragmentTransaction ft = getFragmentManager()
									.beginTransaction();
							ft.replace(R.id.frame_container, fragment,
									getResources().getString(R.string.home_sidemenu));
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
				});

				dialog.show();
			}
		});

		changeFontSizeTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.select_font_dialog);

				radioSmall = (TextView) dialog.findViewById(R.id.smallTxt);
				radioNormal = (TextView) dialog.findViewById(R.id.mediumTxt);
				radioLarge = (TextView) dialog.findViewById(R.id.largeTxt);
				tv_select_font = (TextView) dialog.findViewById(R.id.tv_select_font);
				
				radioSmall.setTypeface(Constants.ProximaNova_Regular);
				radioNormal.setTypeface(Constants.ProximaNova_Regular);
				radioLarge.setTypeface(Constants.ProximaNova_Regular);
				tv_select_font.setTypeface(Constants.ProximaNova_Regular);
				SplashScreen.font = SplashScreen.fontpref.getString(
						SplashScreen.Key_GET_FONT_ID, "");

				if (SplashScreen.font.length() > 0) {
					if (SplashScreen.font.equals("1")) {
						radioNormal.setCompoundDrawablesWithIntrinsicBounds(0,
								0, R.drawable.check_selected, 0);
					}

					else if (SplashScreen.font.equals("2")) {
						radioSmall.setCompoundDrawablesWithIntrinsicBounds(0,
								0, R.drawable.check_selected, 0);
					}

					else if (SplashScreen.font.equals("3")) {
						radioLarge.setCompoundDrawablesWithIntrinsicBounds(0,
								0, R.drawable.check_selected, 0);
					}
				}

				radioSmall.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SplashScreen.fonteditor.putString(
								SplashScreen.Key_GET_FONT_ID, "2");
						SplashScreen.fonteditor.commit();
						
						Constants.selectedFont = SplashScreen.fontpref
								.getString(SplashScreen.Key_GET_FONT_ID, "");

//						final FragmentTransaction ft = getFragmentManager()
//								.beginTransaction();
//						ft.replace(R.id.frame_container,
//								new SettingsFragment(), "NewFragmentTag");
//						ft.addToBackStack(null).commit();
						
						final FragmentTransaction ft = getFragmentManager()
								.beginTransaction();
						ft.replace(R.id.frame_container, new SettingsFragment(),
								getResources().getString(R.string.settings_sidemenu)).commit();
//						ft.addToBackStack(
//								getResources().getString(R.string.settings_sidemenu))
//								.commit();

						dialog.dismiss();
					}
				});

				radioNormal.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SplashScreen.fonteditor.putString(
								SplashScreen.Key_GET_FONT_ID, "1");
						SplashScreen.fonteditor.commit();
						
						Constants.selectedFont = SplashScreen.fontpref
								.getString(SplashScreen.Key_GET_FONT_ID, "");

						final FragmentTransaction ft = getFragmentManager()
								.beginTransaction();
						ft.replace(R.id.frame_container, new SettingsFragment(),
								getResources().getString(R.string.settings_sidemenu)).commit();
//						ft.addToBackStack(
//								getResources().getString(R.string.settings_sidemenu))
//								.commit();

						dialog.dismiss();
					}
				});

				radioLarge.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SplashScreen.fonteditor.putString(
								SplashScreen.Key_GET_FONT_ID, "3");
						SplashScreen.fonteditor.commit();
						
						Constants.selectedFont = SplashScreen.fontpref
								.getString(SplashScreen.Key_GET_FONT_ID, "");

						final FragmentTransaction ft = getFragmentManager()
								.beginTransaction();
						ft.replace(R.id.frame_container, new SettingsFragment(),
								getResources().getString(R.string.settings_sidemenu)).commit();
//						ft.addToBackStack(
//								getResources().getString(R.string.settings_sidemenu))
//								.commit();

						dialog.dismiss();
					}
				});

				dialog.show();
			}
		});

		containerView = container;

//		utility = new Utility(getActivity());

		Constants.ProximaNova_Bold = Typeface.createFromAsset(this
				.getActivity().getAssets(), "ProximaNova-Bold.otf");
		Constants.ProximaNova_Light = Typeface.createFromAsset(this
				.getActivity().getAssets(), "ProximaNova-Light.otf");
		Constants.ProximaNova_Regular = Typeface.createFromAsset(this
				.getActivity().getAssets(), "ProximaNova-Regular.otf");

		// MainActivity.getRightMenuList(getActivity(),MainActivity.stay);

		// RelativeLayout detailsLayout = (RelativeLayout)
		// rootView.findViewById(R.id.your_details);
		//
		// detailsLayout.setVisibility(View.GONE);

		pref = getActivity().getSharedPreferences("telangana_tourism",
				getActivity().MODE_PRIVATE);
		editor = pref.edit();

		userNameedt = (EditText) rootView.findViewById(R.id.userName);
		userPhoneedt = (EditText) rootView.findViewById(R.id.userPhoneNo);

		linearLayout1 = (LinearLayout) rootView.findViewById(R.id.laycon2);

		linearLayout2 = (LinearLayout) rootView.findViewById(R.id.laycon3);

		contact1 = (EditText) rootView.findViewById(R.id.contact1);
		phone1 = (EditText) rootView.findViewById(R.id.phoneNo1);

		contact2 = (EditText) rootView.findViewById(R.id.contact2);
		phone2 = (EditText) rootView.findViewById(R.id.phoneNo2);

		contact3 = (EditText) rootView.findViewById(R.id.contact3);
		phone3 = (EditText) rootView.findViewById(R.id.phoneNo3);

		toggleButton = (ToggleButton) rootView.findViewById(R.id.togglebutton);

		// Button urduBtn = (Button) rootView.findViewById(R.id.urduBtn);
		// Button hindiBtn = (Button) rootView.findViewById(R.id.hindiBtn);
		//
		// hindiBtn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
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
		// getActivity().getBaseContext().getResources().updateConfiguration(config,
		// getActivity().getBaseContext().getResources().getDisplayMetrics());
		// }
		// });

		if (Utility.checkInternetConnection(getActivity())) {
			toggleButton.setEnabled(true);
			
			if (Utility.checkInternetConnection(getActivity())) {
				new SettingsTaskTask().execute();
			} else {
				Utility.showAlertNoInternetService(getActivity());
			}
			
		} else {
			toggleButton.setEnabled(false);
			// utility.showAlertNoInternetService(getActivity());
		}

		if ((SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Track, ""))
				.equalsIgnoreCase("true")) {
			toggleButton.setChecked(true);

			MainActivity.toggleButton.setChecked(true);
		} else {
			toggleButton.setChecked(false);

			MainActivity.toggleButton.setChecked(false);
		}
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(final CompoundButton toggleButton,
					boolean isChecked) {

				if (Utility.checkInternetConnection(getActivity())) {
					if (isChecked) {

						// if(SplashScreen.pref.getString(SplashScreen.Key_GET_SUBMIT_ID,
						// "").length() > 0){
						if (phone1.getText().toString().length() < 10
								&& contact1.getText().toString().length() <= 0) {
							// Toast.makeText(getActivity(),
							// "Please enter emergency contact details",
							// Toast.LENGTH_LONG).show();
							AlertDialog.Builder altDialog = new AlertDialog.Builder(
									getActivity());
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
										}
									});
							altDialog.show();
							toggleButton.setChecked(false);
						} else {
							toggleButton.setChecked(true);
							// custom dialog
							final Dialog dialog = new Dialog(getActivity());
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.dialog);

							// set the custom dialog components - text, image
							// and
							// button

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
									if ((SplashScreen.pref
											.getString(
													SplashScreen.Key_GET_USER_Track,
													""))
											.equalsIgnoreCase("true")) {
										toggleButton.setChecked(true);

										MainActivity.toggleButton
												.setChecked(true);
									} else {
										toggleButton.setChecked(false);

										MainActivity.toggleButton
												.setChecked(false);
									}
									if (Utility.checkInternetConnection(getActivity())) {
										new TrackingServiceTask().execute();
									} else {
										Utility.showAlertNoInternetService(getActivity());
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

									if ((SplashScreen.pref
											.getString(
													SplashScreen.Key_GET_USER_Track,
													""))
											.equalsIgnoreCase("true")) {
										toggleButton.setChecked(true);

										MainActivity.toggleButton
												.setChecked(true);
									} else {
										toggleButton.setChecked(false);

										MainActivity.toggleButton
												.setChecked(false);
									}

									getActivity()
											.stopService(
													new Intent(
															getActivity(),
															com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));
								}
							});

							dialog.show();
						}
						// }else{
						// AlertDialog.Builder altDialog = new
						// AlertDialog.Builder(getActivity());
						// altDialog.setMessage("Please submit your contact details before you start tracking");
						// // here add your message
						// altDialog.setNeutralButton("OK", new
						// DialogInterface.OnClickListener() {
						// @Override
						// public void onClick(DialogInterface dialog, int
						// which) {
						// dialog.dismiss();
						// }
						// });
						// altDialog.show();
						// // Toast.makeText(getActivity(),
						// "Please submit before you on the tracking",
						// Toast.LENGTH_LONG).show();
						// }

					} else {
						SplashScreen.editor.putString(
								SplashScreen.Key_GET_USER_Session, "");
						SplashScreen.editor.putString(
								SplashScreen.Key_GET_USER_Track, "false");
						SplashScreen.editor.commit();

						if ((SplashScreen.pref.getString(
								SplashScreen.Key_GET_USER_Track, ""))
								.equalsIgnoreCase("true")) {
							toggleButton.setChecked(true);

							MainActivity.toggleButton.setChecked(true);
						} else {
							toggleButton.setChecked(false);

							MainActivity.toggleButton.setChecked(false);
						}

						getActivity()
								.stopService(
										new Intent(
												getActivity(),
												com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));
					}
				}

			}
		});

		MainActivity.txt_logo.setVisibility(View.GONE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setTextColor(Color.parseColor("#000000"));

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

		MainActivity.btn_menu.setBackgroundResource(R.drawable.menu_green);

		MainActivity.btn_menu.setVisibility(View.VISIBLE);

		MainActivity.txt_Edit.setVisibility(View.GONE);

		MainActivity.headerLayout.setBackgroundColor(Color
				.parseColor(MainActivity.changeColorStr));

		MainActivity.searchLayout.setBackgroundColor(Color
				.parseColor(MainActivity.changeColorStr));

		MainActivity.searchLayout.setVisibility(View.GONE);

		RelativeLayout headerLayout = (RelativeLayout) rootView
				.findViewById(R.id.menu_layout);

		headerLayout.setVisibility(View.GONE);

		RelativeLayout hintLayout = (RelativeLayout) rootView
				.findViewById(R.id.hint_layout);

		hintLayout.setVisibility(View.GONE);

		submitBtn = (Button) rootView.findViewById(R.id.submitBtn);

		Button addBtn = (Button) rootView.findViewById(R.id.addlayouBtn);
		
		addBtn.setTypeface(Constants.ProximaNova_Regular);

		lay = (LinearLayout) rootView.findViewById(R.id.lay);

		// inflater = (LayoutInflater) getActivity().getSystemService(
		// Context.LAYOUT_INFLATER_SERVICE);

		inflateer = getActivity().getLayoutInflater();
		lay.removeAllViews();

		addBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				addValue++;
				if (addValue == 1) {
					linearLayout1.setVisibility(View.VISIBLE);
				} else if (addValue == 2) {
					linearLayout2.setVisibility(View.VISIBLE);
				}

			}
		});

		// TextView tvSmallFont = (TextView) rootView
		// .findViewById(R.id.smallFontTxt);
		// TextView tvBigFont = (TextView)
		// rootView.findViewById(R.id.bigFontTxt);

		trackTxt = (TextView) rootView.findViewById(R.id.textView4);
		langTxt = (TextView) rootView.findViewById(R.id.textView5);
		t1Txt = (TextView) rootView.findViewById(R.id.textView6);
		t2Txt = (TextView) rootView.findViewById(R.id.textView7);
		t3Txt = (TextView) rootView.findViewById(R.id.textView9);
		t4Txt = (TextView) rootView.findViewById(R.id.textView10);
		
		if (SplashScreen.fontpref
				.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
			
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
			trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
			selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			changeFontSizeTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			
			MainActivity.homeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.myreservations.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.destinationBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.accomodatonBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.eventsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.shopBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.cultureBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.favroitesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.weatherBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.settingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.contactBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.emergencyBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.feedbackBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.trackingTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			
			MainActivity.hydTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.karimnagarTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.medakTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.adilabadTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.nizamabadTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.nizamabadTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.rangareddyTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.warngalTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.mbnrTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			MainActivity.khammTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			
		} else if (SplashScreen.fontpref
				.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")) {
			
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			changeFontSizeTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			
			MainActivity.homeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.myreservations.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.destinationBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.accomodatonBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.eventsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.shopBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.cultureBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.favroitesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.weatherBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.settingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.contactBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.emergencyBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.feedbackBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.trackingTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			
			MainActivity.hydTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.karimnagarTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.medakTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.adilabadTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.nizamabadTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.nizamabadTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.rangareddyTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.warngalTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.mbnrTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			MainActivity.khammTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			
		} else if (SplashScreen.fontpref
				.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
			
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			changeFontSizeTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			
			MainActivity.homeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.myreservations.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.destinationBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.accomodatonBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.eventsBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.shopBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.packagesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.cultureBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.favroitesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.weatherBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.settingBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.contactBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.emergencyBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.feedbackBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.trackingTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			
			MainActivity.hydTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.karimnagarTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.medakTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.adilabadTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.nizamabadTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.nizamabadTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.rangareddyTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.warngalTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.mbnrTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			MainActivity.khammTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			
		}
		
		trackTxt.setTypeface(Constants.ProximaNova_Regular);
		langTxt.setTypeface(Constants.ProximaNova_Regular);
		t1Txt.setTypeface(Constants.ProximaNova_Regular);
		t2Txt.setTypeface(Constants.ProximaNova_Regular);
		t3Txt.setTypeface(Constants.ProximaNova_Regular);
		t4Txt.setTypeface(Constants.ProximaNova_Regular);
		userNameedt.setTypeface(Constants.ProximaNova_Regular);
		userPhoneedt.setTypeface(Constants.ProximaNova_Regular);
		contact1.setTypeface(Constants.ProximaNova_Regular);
		phone1.setTypeface(Constants.ProximaNova_Regular);
		submitBtn.setTypeface(Constants.ProximaNova_Regular);
		selectLanguageTxt.setTypeface(Constants.ProximaNova_Regular);
		changeFontSizeTxt.setTypeface(Constants.ProximaNova_Regular);

		// tvSmallFont.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (fontChaning == 0) {
		// MainActivity.txt_title.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 20);
		// trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 13);
		// } else {
		// fontChaning--;
		// if (fontChaning > 0) {
		//
		// if (fontChaning == 1) {
		// MainActivity.txt_title.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 22);
		// trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 16);
		// userPhoneedt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 16);
		// t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 22);
		// selectLanguageTxt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 15);
		// } else if (fontChaning == 2) {
		// MainActivity.txt_title.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 24);
		// trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 18);
		// userPhoneedt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 18);
		// t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 24);
		// selectLanguageTxt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 17);
		// } else if (fontChaning == 3) {
		// MainActivity.txt_title.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 26);
		// trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 20);
		// userPhoneedt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 20);
		// t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 26);
		// selectLanguageTxt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 19);
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
		// if (fontChaning == 1) {
		// MainActivity.txt_title.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 22);
		// trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 16);
		// userPhoneedt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 16);
		// t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 22);
		// selectLanguageTxt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 15);
		// } else if (fontChaning == 2) {
		// MainActivity.txt_title.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 24);
		// trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 18);
		// userPhoneedt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 18);
		// t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 24);
		// selectLanguageTxt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 17);
		// } else if (fontChaning == 3) {
		// MainActivity.txt_title.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 26);
		// trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 20);
		// userPhoneedt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 20);
		// t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 26);
		// selectLanguageTxt.setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 19);
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

		// engRadioButton.setOnClickListener(this);
		// teluRadioButton.setOnClickListener(this);
		// hindiRadioButton.setOnClickListener(this);
		// urduRadioButton.setOnClickListener(this);

		// submitBtn.setOnClickListener(SettingsActivity.this);

		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Utility.checkInternetConnection(getActivity())) {
					// if(contact1.getText().toString().trim().length() > 0 &&
					// phone1.getText().toString().trim().length() > 0){
					//
					// }
					if (phone1.getText().toString().trim().length() < 10
							&& contact1.getText().toString().trim().length() > 0) {
						Toast.makeText(getActivity(),
								"Please enter valid 10 digit contact number",
								Toast.LENGTH_LONG).show();
					} else {
						try {

							// SplashScreen.editor.putString(
							// SplashScreen.Key_GET_SUBMIT_ID, "1");
							// SplashScreen.editor.commit();

							res = new JSONObject();
							JSONObject obj = new JSONObject();

							obj.put("name", userNameedt.getText().toString());
							obj.put("phone", userPhoneedt.getText().toString());
							obj.put("imea_number", SplashScreen.strIMEINo);

							// JSONArray creation

							if (addValue == 0) {
								JSONObject jo = new JSONObject();
								jo.put("name", contact1.getText().toString());
								jo.put("phone", phone1.getText().toString());

								ja = new JSONArray();
								ja.put(jo);

							} else if (addValue == 1) {
								JSONObject jo = new JSONObject();
								jo.put("name", contact1.getText().toString());
								jo.put("phone", phone1.getText().toString());
								JSONObject jo1 = new JSONObject();
								jo1.put("name", contact2.getText().toString());
								jo1.put("phone", phone2.getText().toString());

								ja = new JSONArray();
								ja.put(jo);
								ja.put(jo1);
							} else if (addValue == 2) {
								JSONObject jo = new JSONObject();
								jo.put("name", contact1.getText().toString());
								jo.put("phone", phone1.getText().toString());

								JSONObject jo1 = new JSONObject();
								jo1.put("name", contact2.getText().toString());
								jo1.put("phone", phone2.getText().toString());

								JSONObject jo2 = new JSONObject();
								jo2.put("name", contact3.getText().toString());
								jo2.put("phone", phone3.getText().toString());

								ja = new JSONArray();
								ja.put(jo);
								ja.put(jo1);
								ja.put(jo2);
							}

							res.put("User", obj);
							res.put("EmergencyContact", ja);
							// Toast.makeText(getActivity(), "" + res,
							// Toast.LENGTH_LONG).show();

							
							
							if (Utility.checkInternetConnection(getActivity())) {
								new PostCall().execute();
							} else {
								Utility.showAlertNoInternetService(getActivity());
							}

//							SplashScreen.editor.putString(
//									SplashScreen.Key_GET_USER_Track, "true");
//							SplashScreen.editor.commit();
							if ((SplashScreen.pref.getString(
									SplashScreen.Key_GET_USER_Track, ""))
									.equalsIgnoreCase("true")) {
								// toggleButton.setChecked(true);

								MainActivity.toggleButton.setChecked(true);
								toggleButton.setChecked(true);
							} else {
								// toggleButton.setChecked(false);

								MainActivity.toggleButton.setChecked(false);
								toggleButton.setChecked(false);
							}
//							new TrackingServiceTask().execute();

						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
				// startActivity(new Intent(getActivity(), MainActivity.class));

			}
		});
		// TextView button = (TextView) rootView.findViewById(R.id.button1);

		// button.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// final Dialog dialog = new Dialog(getActivity());
		// dialog.setContentView(R.layout.custom);
		// dialog.setTitle("Selcet a Language");
		//
		// listView = (ListView) dialog.findViewById(R.id.listView1);
		//
		// // Create and populate a List of planet names.
		// String[] languages = getResources().getStringArray(
		// R.array.language_items);
		// ArrayList<String> languagesList = new ArrayList<String>();
		// languagesList.addAll(Arrays.asList(languages));
		//
		// listAdapter = new ArrayAdapter<String>(SettingsActivity.this,
		// android.R.layout.simple_list_item_1, languagesList);
		//
		// listView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// startActivity(new Intent(SettingsActivity.this,
		// MainActivity.class));
		// }
		// });
		// listView.setAdapter(listAdapter);
		//
		// dialog.show();
		// }
		// });

		return rootView;
	}

	class TrackingServiceTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
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
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
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
				Toast.makeText(getActivity(), "No data found from Server!!!",
						Toast.LENGTH_LONG).show();

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

						// startService(new Intent(
						// getFragmentManager(),
						// com.telanganatourism.android.backgroundservice.TrackingService.class));

						// Toast.makeText(
						// getActivity(),
						// "session"
						// + SplashScreen.pref
						// .getString(
						// SplashScreen.Key_GET_USER_Session,
						// ""), Toast.LENGTH_SHORT)
						// .show();

					} catch (Exception e) {
						// TODO: handle exception
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

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

				Toast.makeText(getActivity(), "No data found from Server!!!",
						Toast.LENGTH_LONG).show();

			} else {

				try {
					JSONObject jsonObject = new JSONObject(result);

					JSONObject jsonObject2 = jsonObject.getJSONObject("User");

					userNameedt.setText(jsonObject2.optString("name"));
					userPhoneedt.setText(jsonObject2.optString("phone"));

					userNameedt.setEnabled(false);
					userPhoneedt.setEnabled(false);

					JSONArray jsonArray = jsonObject
							.getJSONArray("EmergencyContact");

					if (jsonArray.length() == 1) {
						if (jsonArray.getJSONObject(0).getString("name")
								.equalsIgnoreCase("null")) {
							contact1.setText("");
							phone1.setText("");
						} else {
							contact1.setText(jsonArray.getJSONObject(0)
									.getString("name"));
							phone1.setText(jsonArray.getJSONObject(0)
									.getString("email"));
						}

					} else if (jsonArray.length() == 2) {
						contact1.setText(jsonArray.getJSONObject(0).getString(
								"name"));
						phone1.setText(jsonArray.getJSONObject(0).getString(
								"email"));

						linearLayout1.setVisibility(View.VISIBLE);
						contact2.setText(jsonArray.getJSONObject(1).getString(
								"name"));
						phone2.setText(jsonArray.getJSONObject(1).getString(
								"email"));

					} else if (jsonArray.length() == 3) {
						contact1.setText(jsonArray.getJSONObject(0).getString(
								"name"));
						phone1.setText(jsonArray.getJSONObject(0).getString(
								"email"));

						contact2.setText(jsonArray.getJSONObject(1).getString(
								"name"));
						phone2.setText(jsonArray.getJSONObject(1).getString(
								"email"));

						linearLayout1.setVisibility(View.VISIBLE);
						linearLayout2.setVisibility(View.VISIBLE);
						contact3.setText(jsonArray.getJSONObject(2).getString(
								"name"));
						phone3.setText(jsonArray.getJSONObject(2).getString(
								"email"));
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		}
	}

	private class PostCall extends AsyncTask<String, Void, String> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("Loading ...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(SplashScreen.Base_url
					+ "addUserEmergencyContacts/"
					+ pref.getString(Key_GET_USER_ID, ""));
			String result = null;
			try {
				// Add your data
				/*
				 * child_name age gender image_path father_name comments
				 * date_created personNameEdt = (EditText)
				 * findViewById(R.id.personNameEdt); stallNameEdt = (EditText)
				 * findViewById(R.id.stallNameEdt); stallNumberEdt =
				 * (EditText)findViewById(R.id.stallNumberEdt); stallcomments
				 * =(EditText)findViewById(R.id.stallcomments);
				 */
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("contact_details",
						res.toString()));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, HTTP.UTF_8).trim();
				
				SplashScreen.editor.putString(
						SplashScreen.Key_GET_USER_Track,
						"true");
				SplashScreen.editor.commit();

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println("result1" + result);

			if (null != progressDialog && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}

			// Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

			// try {
			// JSONObject jsonObject = new JSONObject(result);
			//
			// // String strUserId = jsonObject.optString("UserId");
			//
			// SplashScreen.editor.putString(SplashScreen.Key_GET_USER_ID,
			// jsonObject.optString("UserId"));
			// SplashScreen.editor.commit();
			// // Toast.makeText(getApplicationContext(), "userId" +strUserId,
			// Toast.LENGTH_SHORT).show();
			// } catch (Exception e) {
			// // TODO: handle exception
			// }

			HomeFragment fragment = new HomeFragment();

			if (fragment != null) {
//				FragmentManager fragmentManager = getFragmentManager();
//				fragmentManager.beginTransaction()
//						.replace(R.id.frame_container, fragment).commit();
				
				final FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_container, fragment,
						getResources().getString(R.string.home_sidemenu));
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
