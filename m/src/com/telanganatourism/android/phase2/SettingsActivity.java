package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.flurry.android.FlurryAgent;
import com.telanganatourism.android.phase2.SeatSelection.GetBoardIngonward;
import com.telanganatourism.android.phase2.SeatSelection.GetBoardingReturnjourney;
import com.telanganatourism.android.phase2.util.Utility;

public class SettingsActivity extends Activity {

	RadioButton engRadioButton, teluRadioButton, hindiRadioButton,
			urduRadioButton;

	Button submitBtn, gotitBtn;
	Intent intent;
	ListView listView;
	ArrayAdapter<String> listAdapter;
	LinearLayout lay;
	View view;
	LayoutInflater inflater = null;


	int addValue = 0;
	RelativeLayout hintLayout;

	ToggleButton toggleButton;

	JSONObject res, finalResp; 
	JSONArray ja;
	HttpResponse response1;

	boolean LogFlag = false;

	LinearLayout linearLayout1, linearLayout2;

	EditText userNameedt, userPhoneedt, contact1, contact2, contact3, phone1,
			phone2, phone3;

	String uid = "", sid = "";

//	Utility utility;
	TextView changeFontSizeTxt, selectLanguageTxt;

	TextView radioEnglish, radioTelugu, radioHindi, radioUrdu, radioSexButton;

//	int fontChaning = 0;
	
	TextView trackTxt, langTxt, t1Txt, t2Txt, t3Txt, t4Txt;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings);

		
		SplashScreen.name = SplashScreen.pref1.getString(SplashScreen.Key_GET_LANGUAGE_ID, "");

		if(SplashScreen.pref1.getString(SplashScreen.Key_GET_LANGUAGE_ID, "").length() > 0){
			
			Constants.selectLanguage = SplashScreen.pref1.getString(SplashScreen.Key_GET_LANGUAGE_ID, "");	
		}else{
			Constants.selectLanguage = "1";
		}
		
		// Toast.makeText(getApplicationContext(), name,
		// Toast.LENGTH_SHORT).show();

		changeFontSizeTxt = (TextView)findViewById(R.id.changeFontSize);
		
		TextView telanganaLogo = (TextView)findViewById(R.id.textView3);
		
		selectLanguageTxt = (TextView) findViewById(R.id.selectLanguageBtn);
		
		telanganaLogo.setVisibility(View.VISIBLE);
		changeFontSizeTxt.setVisibility(View.GONE);

		SplashScreen.name = SplashScreen.pref1.getString(SplashScreen.Key_GET_LANGUAGE_ID, "");
		if (SplashScreen.name.length() > 0) {
			if (SplashScreen.name.equals("1")) {
//				radioEnglish.setCompoundDrawablesWithIntrinsicBounds(0, 0,
//						R.drawable.check_selected, 0);
				selectLanguageTxt.setText("English");
			} else if (SplashScreen.name.equals("2")) {
//				radioTelugu.setCompoundDrawablesWithIntrinsicBounds(0, 0,
//						R.drawable.check_selected, 0);
				selectLanguageTxt.setText("Telugu");
			}

			else if (SplashScreen.name.equals("3")) {
//				radioUrdu.setCompoundDrawablesWithIntrinsicBounds(0, 0,
//						R.drawable.check_selected, 0);
				selectLanguageTxt.setText("Urdu");
			}

			else if (SplashScreen.name.equals("4")) {
//				radioHindi.setCompoundDrawablesWithIntrinsicBounds(0, 0,
//						R.drawable.check_selected, 0);
				selectLanguageTxt.setText("Hindi");
			}
		}

		selectLanguageTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// custom dialog
				final Dialog dialog = new Dialog(SettingsActivity.this);
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

				SplashScreen.name = SplashScreen.pref1.getString(SplashScreen.Key_GET_LANGUAGE_ID, "");
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
						
						String languageToLoad  = "en";
						Locale locale = new Locale(languageToLoad);
						Locale.setDefault(locale);
						Configuration config = new Configuration();
						config.locale = locale;
						getBaseContext().getResources().updateConfiguration(config, null);
						
						Constants.ProximaNova_Regular = Typeface.createFromAsset(
								getAssets(), "ProximaNova-Regular.otf");
						
						SplashScreen.flg = "1";
						SplashScreen.editor1.putString(SplashScreen.Key_GET_LANGUAGE_ID, "" + SplashScreen.flg);
						SplashScreen.editor1.commit();
						selectLanguageTxt.setText("English");
						Constants.selectLanguage = SplashScreen.pref1.getString(
								SplashScreen.Key_GET_LANGUAGE_ID, "");
						dialog.dismiss();
						startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
					}
				});

				radioTelugu.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String languageToLoad  = "te";
						Locale locale = new Locale(languageToLoad);
						Locale.setDefault(locale);
						Configuration config = new Configuration();
						config.locale = locale;
						getBaseContext().getResources().updateConfiguration(config, null);
						
						Constants.ProximaNova_Regular = Typeface.createFromAsset(
								getAssets(), "gautami.ttf");
						
						SplashScreen.flg = "2";
						SplashScreen.editor1.putString(SplashScreen.Key_GET_LANGUAGE_ID, "" + SplashScreen.flg);
						SplashScreen.editor1.commit();
						selectLanguageTxt.setText("Telugu");
						Constants.selectLanguage = SplashScreen.pref1.getString(
								SplashScreen.Key_GET_LANGUAGE_ID, "");
						dialog.dismiss();
						startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
					}
				});

				radioHindi.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						
						String languageToLoad  = "hi";
						Locale locale = new Locale(languageToLoad);
						Locale.setDefault(locale);
						Configuration config = new Configuration();
						config.locale = locale;
						getBaseContext().getResources().updateConfiguration(config, null);
						
						Constants.ProximaNova_Regular = Typeface.createFromAsset(
								getAssets(), "ProximaNova-Regular.otf");
			            
						SplashScreen.flg = "4";
						SplashScreen.editor1.putString(SplashScreen.Key_GET_LANGUAGE_ID, "" + SplashScreen.flg);
						SplashScreen.editor1.commit();
						selectLanguageTxt.setText("Hindi");
						Constants.selectLanguage = SplashScreen.pref1.getString(
								SplashScreen.Key_GET_LANGUAGE_ID, "");
						dialog.dismiss();
						startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
						
						
						

					}
				});

				radioUrdu.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String languageToLoad  = "ur";
						Locale locale = new Locale(languageToLoad);
						Locale.setDefault(locale);
						Configuration config = new Configuration();
						config.locale = locale;
						getBaseContext().getResources().updateConfiguration(config, null);
						
						Constants.ProximaNova_Regular = Typeface.createFromAsset(
								getAssets(), "asunaskh.ttf");
						
						SplashScreen.flg = "3";
						SplashScreen.editor1.putString(SplashScreen.Key_GET_LANGUAGE_ID, "" + SplashScreen.flg);
						SplashScreen.editor1.commit();
						selectLanguageTxt.setText("Urdu");
						Constants.selectLanguage = SplashScreen.pref1.getString(
								SplashScreen.Key_GET_LANGUAGE_ID, "");
						dialog.dismiss();
						startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
					}
				});

				dialog.show();
			}
		});

//		utility = new Utility(SettingsActivity.this);

		GPSTracker gps = new GPSTracker(getBaseContext()) {
		};
		if (gps.canGetLocation()) {
			Constants.latitude = gps.getLatitude();
			Constants.longitude = gps.getLongitude();

		}

		Constants.ProximaNova_Bold = Typeface.createFromAsset(this.getAssets(),
				"ProximaNova-Bold.otf");
		Constants.ProximaNova_Light = Typeface.createFromAsset(
				this.getAssets(), "ProximaNova-Light.otf");
		Constants.ProximaNova_Regular = Typeface.createFromAsset(
				this.getAssets(), "ProximaNova-Regular.otf");

		userNameedt = (EditText) findViewById(R.id.userName);
		userPhoneedt = (EditText) findViewById(R.id.userPhoneNo);

		contact1 = (EditText) findViewById(R.id.contact1);
		phone1 = (EditText) findViewById(R.id.phoneNo1);

		contact2 = (EditText) findViewById(R.id.contact2);
		phone2 = (EditText) findViewById(R.id.phoneNo2);

		contact3 = (EditText) findViewById(R.id.contact3);
		phone3 = (EditText) findViewById(R.id.phoneNo3);

		toggleButton = (ToggleButton) findViewById(R.id.togglebutton);

		if (Utility.checkInternetConnection(SettingsActivity.this)) {
			toggleButton.setEnabled(true);
		} else {
			toggleButton.setEnabled(false);
			// utility.showAlertNoInternetService(SettingsActivity.this);
		}
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(final CompoundButton toggleButton,
					boolean isChecked) {

				if (Utility.checkInternetConnection(SettingsActivity.this)) {
					if (isChecked) {
						// custom dialog
						final Dialog dialog = new Dialog(SettingsActivity.this);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.dialog);

						// set the custom dialog components - text, image and
						// button

						Button yesBtn = (Button) dialog
								.findViewById(R.id.btn_yes);
						// if button is clicked, close the custom dialog
						yesBtn.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								LogFlag = true;
								// new TrackingServiceTask().execute();
							}
						});

						Button noBtn = (Button) dialog
								.findViewById(R.id.btn_no);
						// if button is clicked, close the custom dialog
						noBtn.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								LogFlag = false;
								toggleButton.setChecked(false);
								stopService(new Intent(
										SettingsActivity.this,
										com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));
							}
						});

						dialog.show();
					} else {
						LogFlag = false;

						SplashScreen.editor.putString(
								SplashScreen.Key_GET_USER_Session, "");
						SplashScreen.editor.putString(
								SplashScreen.Key_GET_USER_Track, "" + LogFlag);
						SplashScreen.editor.commit();

						stopService(new Intent(
								SettingsActivity.this,
								com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));
					}

				} else {
					// utility.showAlertNoInternetService(SettingsActivity.this);
				}
			}
		});

		// engRadioButton = (RadioButton) findViewById(R.id.engRadioBtn);
		// teluRadioButton = (RadioButton) findViewById(R.id.teluRadioBtn);
		// hindiRadioButton = (RadioButton) findViewById(R.id.hindiRadioBtn);
		// urduRadioButton = (RadioButton) findViewById(R.id.urduRadioBtn);

		hintLayout = (RelativeLayout) findViewById(R.id.hint_layout);

		// if (SplashScreen.hintVal == 0) {
		// hintLayout.setVisibility(View.VISIBLE);
		// } else {
		// hintLayout.setVisibility(View.GONE);
		// }

		// gotitBtn = (Button) findViewById(R.id.gotitBtn);

		submitBtn = (Button) findViewById(R.id.submitBtn);

		// gotitBtn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// SplashScreen.hintVal = 1;
		// hintLayout.setVisibility(View.GONE);
		// }
		// });

		Button addBtn = (Button) findViewById(R.id.addlayouBtn);
		
		addBtn.setTypeface(Constants.ProximaNova_Regular);

		linearLayout1 = (LinearLayout) findViewById(R.id.laycon2);

		linearLayout2 = (LinearLayout) findViewById(R.id.laycon3);

		lay = (LinearLayout) findViewById(R.id.lay);

		inflater = (LayoutInflater) getApplicationContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

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

		// engRadioButton.setOnClickListener(this);
		// teluRadioButton.setOnClickListener(this);
		// hindiRadioButton.setOnClickListener(this);
		// urduRadioButton.setOnClickListener(this);

		// submitBtn.setOnClickListener(SettingsActivity.this);

		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Utility.checkInternetConnection(SettingsActivity.this)) {
					try {

						if (userNameedt.getText().toString().length() == 0
								|| userPhoneedt.getText().toString().length() == 0) {

							Toast.makeText(getApplicationContext(),
									"Your Personal Details are Mandatory",
									Toast.LENGTH_LONG).show();

						} else {
							if (userPhoneedt.getText().toString()
									.length() < 10) {
								Toast.makeText(getApplicationContext(),
										"Please enter valid 10 digit phone number",
										Toast.LENGTH_LONG).show();
							} else {

//								finalResp = new JSONObject();
								res = new JSONObject();
								JSONObject obj = new JSONObject();

								obj.put("name", userNameedt.getText()
										.toString());
								obj.put("phone", userPhoneedt.getText()
										.toString());
								obj.put("imea_number", SplashScreen.strIMEINo);

								// JSONArray creation

								if (addValue == 0) {
									JSONObject jo = new JSONObject();
									jo.put("name", contact1.getText()
											.toString());
									jo.put("phone", phone1.getText().toString());

									ja = new JSONArray();
									ja.put(jo);

								} else if (addValue == 1) {
									JSONObject jo = new JSONObject();
									jo.put("name", contact1.getText()
											.toString());
									jo.put("phone", phone1.getText().toString());

									JSONObject jo1 = new JSONObject();
									jo1.put("name", contact2.getText()
											.toString());
									jo1.put("phone", phone2.getText()
											.toString());

									ja = new JSONArray();
									ja.put(jo);
									ja.put(jo1);
								} else if (addValue == 2) {
									JSONObject jo = new JSONObject();
									jo.put("name", contact1.getText()
											.toString());
									jo.put("phone", phone1.getText().toString());

									JSONObject jo1 = new JSONObject();
									jo1.put("name", contact2.getText()
											.toString());
									jo1.put("phone", phone2.getText()
											.toString());

									JSONObject jo2 = new JSONObject();
									jo2.put("name", contact3.getText()
											.toString());
									jo2.put("phone", phone3.getText()
											.toString());

									ja = new JSONArray();
									ja.put(jo);
									ja.put(jo1);
									ja.put(jo2);
								}

								res.put("User", obj);
								res.put("EmergencyContact", ja);
//								finalResp.put("contact_details", res);
								// Toast.makeText(getApplicationContext(), "" +
								// res,
								// Toast.LENGTH_LONG)
								// .show();
								
								if (Utility.checkInternetConnection(SettingsActivity.this)) {
									new PostCall().execute();
								} else {
									Utility.showAlertNoInternetService(SettingsActivity.this);
								}

								
							}
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Utility.showAlertNoInternetService(SettingsActivity.this);
				}

			}
		});
		TextView button = (TextView) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(SettingsActivity.this);
				dialog.setContentView(R.layout.custom);
				dialog.setTitle("Selcet a Language");

				listView = (ListView) dialog.findViewById(R.id.listView1);

				// Create and populate a List of planet names.
				String[] languages = getResources().getStringArray(
						R.array.language_items);
				ArrayList<String> languagesList = new ArrayList<String>();
				languagesList.addAll(Arrays.asList(languages));

				listAdapter = new ArrayAdapter<String>(SettingsActivity.this,
						android.R.layout.simple_list_item_1, languagesList);

				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						startActivity(new Intent(SettingsActivity.this,
								MainActivity.class));
					}
				});
				listView.setAdapter(listAdapter);

				dialog.show();
			}
		});
		
//		TextView tvSmallFont = (TextView) findViewById(R.id.smallFontTxt);
//		TextView tvBigFont = (TextView) findViewById(R.id.bigFontTxt);
		
		trackTxt = (TextView) findViewById(R.id.textView4);
		langTxt = (TextView) findViewById(R.id.textView5);
		t1Txt = (TextView) findViewById(R.id.textView6);
		t2Txt = (TextView) findViewById(R.id.textView7);
		t3Txt = (TextView) findViewById(R.id.textView9);
		t4Txt = (TextView) findViewById(R.id.textView10);
		
		if (SplashScreen.fontpref
				.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
			
//			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
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
			
		} else if (SplashScreen.fontpref
				.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")) {
			
//			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
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
			
		} else if (SplashScreen.fontpref
				.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
			
//			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
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
		
//		tvSmallFont.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (fontChaning == 0) {
//					MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//					trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//					langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//					t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//					userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//					userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//					t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//					t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//					contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//					phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//					t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//					submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//					selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
//				} else {
//					fontChaning--;
//					if (fontChaning > 0) {
//
//						if (fontChaning == 1) {
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//							trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//							selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//						} else if (fontChaning == 2) {
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//							trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//							selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
//						} else if (fontChaning == 3) {
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//							trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//							selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
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
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//							trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//							submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//							selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//						} else if (fontChaning == 2) {
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//							trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//							submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//							selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
//						} else if (fontChaning == 3) {
//							MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//							trackTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							langTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							t1Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							userNameedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							userPhoneedt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							t2Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							t3Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							contact1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							phone1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							t4Txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//							submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//							selectLanguageTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
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

	}

	// private class LogUserTrack extends AsyncTask<String, Void, String> {
	//
	// private ProgressDialog progressDialog;
	//
	// @Override
	// protected void onPreExecute() {
	//
	// super.onPreExecute();
	// progressDialog = new ProgressDialog(SettingsActivity.this);
	// progressDialog.setMessage("Loading ...");
	// progressDialog.setIndeterminate(false);
	// progressDialog.setCancelable(false);
	// progressDialog.show();
	//
	// }
	//
	// @Override
	// protected String doInBackground(String... args) {
	// HttpClient httpclient = new DefaultHttpClient();
	// HttpPost httppost = new HttpPost(
	// "http://172.16.0.49/telangana_tourism/WebServices/logUserTrack/");
	// String result = null;
	// // http://172.16.0.49/telangana_tourism/WebServices/logUserTrack/
	// try {
	//
	// /*
	// * List<NameValuePair> nameValuePairs = new
	// * ArrayList<NameValuePair>( 2); nameValuePairs.add(new
	// * BasicNameValuePair("contact_details", res.toString()));
	// * httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	// */
	//
	// // Execute HTTP Post Request
	// HttpResponse response = httpclient.execute(httppost);
	// HttpEntity entity = response.getEntity();
	// result = EntityUtils.toString(entity, HTTP.UTF_8).trim();
	//
	// } catch (ClientProtocolException e) {
	// // TODO Auto-generated catch block
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// }
	// return result;
	//
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// super.onPostExecute(result);
	// System.out.println("result1" + result);
	//
	// if (null != progressDialog && progressDialog.isShowing()) {
	// progressDialog.dismiss();
	// }
	//
	// try {
	// JSONObject jsonObject = new JSONObject(result);
	//
	// // String strUserId = jsonObject.optString("UserId");
	//
	// SplashScreen.editor.putString(SplashScreen.Key_GET_USER_ID,
	// jsonObject.optString("UserId"));
	// SplashScreen.editor.putString(SplashScreen.Key_GET_USER_NAME,
	// userNameedt.getText().toString());
	// SplashScreen.editor.putString(SplashScreen.Key_GET_USER_PHONE_NO,
	// userPhoneedt.getText().toString());
	//
	// SplashScreen.editor.commit();
	// // Toast.makeText(getApplicationContext(), "userId" +strUserId,
	// // Toast.LENGTH_SHORT).show();
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// }
	//
	// }

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
				Toast.makeText(SettingsActivity.this,
						"No data found from Server!!!", Toast.LENGTH_LONG)
						.show();

			} else {

				try {
					try {
						JSONObject jsonObject = new JSONObject(result);

						SplashScreen.editor.putString(
								SplashScreen.Key_GET_USER_Session,
								jsonObject.optString("session"));
						SplashScreen.editor.putString(
								SplashScreen.Key_GET_USER_Track, "" + LogFlag);
						SplashScreen.editor.commit();

						startService(new Intent(
								SettingsActivity.this,
								com.telanganatourism.android.phase2.backgroundservice.TrackingService.class));

						Toast.makeText(
								getApplicationContext(),
								"session"
										+ SplashScreen.pref
												.getString(
														SplashScreen.Key_GET_USER_Session,
														""), Toast.LENGTH_SHORT)
								.show();
						startActivity(new Intent(SettingsActivity.this,
								MainActivity.class));

					} catch (Exception e) {
						// TODO: handle exception
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	private class PostCall extends AsyncTask<String, Void, String> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			progressDialog = new ProgressDialog(SettingsActivity.this);
			progressDialog.setMessage("Loading ...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... args) {
			String result = null;
			try {
				
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(SplashScreen.Base_url
						+ "addUserEmergencyContacts/");
				
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

			try {
				JSONObject jsonObject = new JSONObject(result);

				// String strUserId = jsonObject.optString("UserId");

				SplashScreen.editor.putString(SplashScreen.Key_GET_USER_ID,
						jsonObject.optString("UserId"));

				SplashScreen.editor.putString(SplashScreen.Key_GET_USER_NAME,
						userNameedt.getText().toString());
				SplashScreen.editor.putString(
						SplashScreen.Key_GET_USER_PHONE_NO, userPhoneedt
								.getText().toString());

				SplashScreen.editor.commit();
				// Toast.makeText(getApplicationContext(), "userId" +strUserId,
				// Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (LogFlag) {
				SplashScreen.editor.putString(SplashScreen.Key_GET_USER_Track,
						"true");
				SplashScreen.editor.commit();
				// Toast.makeText(getApplicationContext(), "Log on ",
				// 100).show();
				
				if (Utility.checkInternetConnection(SettingsActivity.this)) {
					new TrackingServiceTask().execute();
				} else {
					Utility.showAlertNoInternetService(SettingsActivity.this);
				}
				
			} else {
				// Toast.makeText(getApplicationContext(), "Log off ",
				// 100).show();
				SplashScreen.editor.putString(SplashScreen.Key_GET_USER_Track,
						"false");
				SplashScreen.editor.commit();
				startActivity(new Intent(SettingsActivity.this,
						MainActivity.class));
				finish();
			}

		}

	}
	
	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(SettingsActivity.this, "6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(SettingsActivity.this);
	}

}
