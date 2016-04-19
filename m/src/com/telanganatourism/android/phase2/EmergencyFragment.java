package com.telanganatourism.android.phase2;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.telanganatourism.android.phase2.util.Utility;

public class EmergencyFragment extends Fragment implements OnClickListener,
		OnTouchListener {

	TextView titleTxt, addressTxt, phoneNoTxt,txt3,txt33,txt36;
	
	

	String latVal, lngVal, phoneNo, emergencyType;
	LinearLayout health_emergency_lay, police_emergency_lay, root_layout;
	LayoutInflater layoutInflater, layoutInflater1, layoutInflater3;
	View view, view1, view3;
	
	TextView btn_call, btn_direction, btn_call1, btn_direction1;
	ArrayList<String> police_no = new ArrayList<String>();
	ArrayList<String> healthcare_no = new ArrayList<String>();
	int i = 0, j = 0;

//	Utility utility;
	TextView title_1_pho, tourism_toll_free_txt;
	// railway_enquiry_pho1, railway_enquiry_pho2,
	// electricity_complaint_title_1_pho, traffic_help_1_pho,
	// apollo_ambulance_1_pho, osmania_general_hospital_pho1,
	// osmania_general_hospital_pho2, gandhi_hospital_1_pho,
	// government_chest_hospital_pho1, government_chest_hospital_pho2,
	// government_chest_hospital_pho3, government_chest_hospital_pho4,
	// government_ent_hospital_pho12, government_ent_hospital_pho22,
	// crime_stopper_1_pho, child_line_txt, women_protection_cell_txt;

	TextView txtPolice, txtAmbulance, txtFire;

	private GestureDetector gestureDetector;

	ArrayList<String> emergency_id, emergency_title, emergency_phone;

	// int fontChaning = 0;

	public EmergencyFragment() {
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		gestureDetector = new GestureDetector(new SwipeGestureDetector());

		View rootView = inflater.inflate(R.layout.emergency_contact, container,
				false);
		health_emergency_lay = (LinearLayout) rootView
				.findViewById(R.id.health_emergency_lay);
		root_layout = (LinearLayout) rootView.findViewById(R.id.root_layout);
		police_emergency_lay = (LinearLayout) rootView
				.findViewById(R.id.police_emergency_lay);

//		utility = new Utility(getActivity());

		// getActivity().getActionBar().setTitle("Emergency Contacts");
		// getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActivity().getActionBar().setBackgroundDrawable(
		// new ColorDrawable(Color.parseColor("#F2F2F2")));

		txtPolice = (TextView) rootView.findViewById(R.id.txtPolice);
		txtAmbulance = (TextView) rootView.findViewById(R.id.txtAmbulance);
		txtFire = (TextView) rootView.findViewById(R.id.txtFire);
		
		txt3 = (TextView) rootView.findViewById(R.id.txt3);
		txt33 = (TextView) rootView.findViewById(R.id.txt33);
		txt36 = (TextView) rootView.findViewById(R.id.txt36);
		
		txtPolice.setTypeface(Constants.ProximaNova_Regular);
		txtAmbulance.setTypeface(Constants.ProximaNova_Regular);
		txtFire.setTypeface(Constants.ProximaNova_Regular);
		
		txt3.setTypeface(Constants.ProximaNova_Regular);
		txt33.setTypeface(Constants.ProximaNova_Regular);
		txt36.setTypeface(Constants.ProximaNova_Regular);
		
		txtPolice.setTypeface(Constants.ProximaNova_Regular);
		
		
		tourism_toll_free_txt = (TextView) rootView.findViewById(R.id.title_1);
		title_1_pho = (TextView) rootView.findViewById(R.id.tourism_department_phno);
		
		tourism_toll_free_txt.setTypeface(Constants.ProximaNova_Regular);
		title_1_pho.setTypeface(Constants.ProximaNova_Regular);
		// railway_enquiry_pho1 = (TextView) rootView
		// .findViewById(R.id.railway_enquiry_pho1);
		// railway_enquiry_pho2 = (TextView) rootView
		// .findViewById(R.id.railway_enquiry_pho2);
		// electricity_complaint_title_1_pho = (TextView) rootView
		// .findViewById(R.id.electricity_complaint_title_1_pho);
		// traffic_help_1_pho = (TextView) rootView
		// .findViewById(R.id.traffic_help_1_pho);
		// apollo_ambulance_1_pho = (TextView) rootView
		// .findViewById(R.id.apollo_ambulance_1_pho);
		// osmania_general_hospital_pho1 = (TextView) rootView
		// .findViewById(R.id.osmania_general_hospital_pho1);
		// osmania_general_hospital_pho2 = (TextView) rootView
		// .findViewById(R.id.osmania_general_hospital_pho2);
		// gandhi_hospital_1_pho = (TextView) rootView
		// .findViewById(R.id.gandhi_hospital_1_pho);
		// government_chest_hospital_pho1 = (TextView) rootView
		// .findViewById(R.id.government_chest_hospital_pho1);
		// government_chest_hospital_pho2 = (TextView) rootView
		// .findViewById(R.id.government_chest_hospital_pho2);
		// government_chest_hospital_pho3 = (TextView) rootView
		// .findViewById(R.id.government_chest_hospital_pho3);
		// government_chest_hospital_pho4 = (TextView) rootView
		// .findViewById(R.id.government_chest_hospital_pho4);
		// government_ent_hospital_pho12 = (TextView) rootView
		// .findViewById(R.id.government_ent_hospital_pho12);
		// government_ent_hospital_pho22 = (TextView) rootView
		// .findViewById(R.id.government_ent_hospital_pho22);
		// crime_stopper_1_pho = (TextView) rootView
		// .findViewById(R.id.crime_stopper_1_pho);
		// child_line_txt = (TextView)
		// rootView.findViewById(R.id.child_line_txt);
		// women_protection_cell_txt = (TextView) rootView
		// .findViewById(R.id.women_protection_cell_txt);

		title_1_pho.setOnClickListener(this);
		// railway_enquiry_pho1.setOnClickListener(this);
		// railway_enquiry_pho2.setOnClickListener(this);
		// electricity_complaint_title_1_pho.setOnClickListener(this);
		// traffic_help_1_pho.setOnClickListener(this);
		// apollo_ambulance_1_pho.setOnClickListener(this);
		// osmania_general_hospital_pho1.setOnClickListener(this);
		// osmania_general_hospital_pho2.setOnClickListener(this);
		// gandhi_hospital_1_pho.setOnClickListener(this);
		// government_chest_hospital_pho1.setOnClickListener(this);
		// government_chest_hospital_pho2.setOnClickListener(this);
		// government_chest_hospital_pho3.setOnClickListener(this);
		// government_chest_hospital_pho4.setOnClickListener(this);
		// government_ent_hospital_pho12.setOnClickListener(this);
		// government_ent_hospital_pho22.setOnClickListener(this);
		// crime_stopper_1_pho.setOnClickListener(this);
		// child_line_txt.setOnClickListener(this);
		// women_protection_cell_txt.setOnClickListener(this);

		TextView policeBtn = (TextView) rootView.findViewById(R.id.policeBtn);
		TextView ambulanceBtn = (TextView) rootView	.findViewById(R.id.ambulanceBtn);
		TextView fireBtn = (TextView) rootView.findViewById(R.id.fireBtn);
		
		

		policeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:100"));
				startActivity(callIntent);
			}
		});

		ambulanceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:108"));
				startActivity(callIntent);
			}
		});

		fireBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:101"));
				startActivity(callIntent);
			}
		});

		txtPolice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:100"));
				startActivity(callIntent);
			}
		});

		txtAmbulance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:108"));
				startActivity(callIntent);
			}
		});

		txtFire.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:101"));
				startActivity(callIntent);
			}
		});
		
		if(Utility.checkInternetConnection(getActivity())){
			new EmergencyTask().execute();
		}else{
			Utility.showAlertNoInternetService(getActivity());
		}
		
		/*
		 * TextView callBtn = (TextView)rootView.findViewById(R.id.button4);
		 * 
		 * callBtn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent callIntent = new Intent(Intent.ACTION_CALL);
		 * callIntent.setData(Uri.parse("tel:"+phoneNo));
		 * startActivity(callIntent); } });
		 * 
		 * 
		 * 
		 * TextView getDirectionBtn =
		 * (TextView)rootView.findViewById(R.id.button5);
		 * 
		 * getDirectionBtn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub final Dialog dialog = new Dialog(getActivity(),
		 * android.R.style.Theme_Translucent_NoTitleBar); //
		 * android.R.style.Theme_Translucent_NoTitleBar);
		 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * dialog.setContentView(R.layout.map_dialog);
		 * 
		 * WebView webview = (WebView) dialog.findViewById(R.id.webView1);
		 * webview.setWebViewClient(new WebViewClient());
		 * webview.getSettings().setJavaScriptEnabled(true);
		 * webview.loadUrl("http://maps.google.com/maps?" +
		 * "saddr=17.3998,78.4766" + "&daddr="+latVal+","+lngVal);
		 * 
		 * Button closeBtN = (Button) dialog.findViewById(R.id.closeBtn);
		 * closeBtN.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub dialog.dismiss(); } });
		 * 
		 * dialog.show(); } });
		 */
		police_no.clear();
		healthcare_no.clear();

		// if(utility.IsNetConnected(getActivity())){
		// new EmergencyTask().execute();
		// }else{
		// utility.showAlertNoInternetService(getActivity());
		// }

		MainActivity.txt_logo.setVisibility(View.GONE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setText(getResources().getString(
				R.string.emergency_sidemenu));

		MainActivity.txt_title.setTextColor(Color.parseColor("#000000"));

		MainActivity.txt_filter.setVisibility(View.GONE);

		MainActivity.txt_location.setVisibility(View.GONE);

		MainActivity.txt_search.setVisibility(View.GONE);

		MainActivity.txt_Edit.setVisibility(View.GONE);

		MainActivity.btn_menu.setBackgroundResource(R.drawable.menu_green);

		MainActivity.headerLayout.setBackgroundColor(Color
				.parseColor("#ffffff"));
		// MainActivity.getRightMenuList(getActivity(),MainActivity.stay);

		// TextView tvSmallFont = (TextView)
		// rootView.findViewById(R.id.smallFontTxt);
		// TextView tvBigFont = (TextView)
		// rootView.findViewById(R.id.bigFontTxt);
		//
		// tvSmallFont.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (fontChaning == 0) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// txtFire.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// txtAmbulance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// txtPolice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// tourism_toll_free_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// title_1_pho.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// new EmergencyTask().execute();
		//
		if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID, "")
				.equalsIgnoreCase("1")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			txtFire.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			txtAmbulance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			txtPolice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			tourism_toll_free_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
			title_1_pho.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			txtFire.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			txtAmbulance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			txtPolice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			tourism_toll_free_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			title_1_pho.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
			txtFire.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			txtAmbulance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			txtPolice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			tourism_toll_free_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
			title_1_pho.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		}
		// } else {
		// fontChaning--;
		// if (fontChaning > 0) {
		//
		// if (fontChaning == 1) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// txtFire.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// txtAmbulance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// txtPolice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// tourism_toll_free_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// title_1_pho.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// new EmergencyTask().execute();
		// } else if (fontChaning == 2) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// txtFire.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// txtAmbulance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// txtPolice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// tourism_toll_free_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
		// title_1_pho.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// new EmergencyTask().execute();
		// } else if (fontChaning == 3) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
		// txtFire.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// txtAmbulance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// txtPolice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// tourism_toll_free_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
		// title_1_pho.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// new EmergencyTask().execute();
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
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// txtFire.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// txtAmbulance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// txtPolice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// tourism_toll_free_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// title_1_pho.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// new EmergencyTask().execute();
		// } else if (fontChaning == 2) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// txtFire.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// txtAmbulance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// txtPolice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// tourism_toll_free_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
		// title_1_pho.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// new EmergencyTask().execute();
		// } else if (fontChaning == 3) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
		// txtFire.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// txtAmbulance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// txtPolice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// tourism_toll_free_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
		// title_1_pho.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// new EmergencyTask().execute();
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

		title_1_pho.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		});

		return rootView;
	}

	class EmergencyTask extends AsyncTask<String, Void, String> {

		TextView phone_txt;

		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getEmergencyTransportTypes/" + Constants.locationCode
					+ "/" + Constants.selectLanguage);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getEmergencyTransportTypes/" + Constants.locationCode
					+ "/" + Constants.selectLanguage);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {

				// Toast.makeText(getActivity(), "No data found from Server!!!",
				// Toast.LENGTH_LONG).show();
				if (pDialog.isShowing() && pDialog != null) {
					pDialog.dismiss();
				}
			} else {

				try {

					if (result.contains("message")) {
						if (pDialog.isShowing() && pDialog != null) {
							pDialog.dismiss();
						}
					} else {
						JSONObject jsonResultObj = new JSONObject(result);

						JSONArray jsonArray = jsonResultObj
								.getJSONArray("emergencycontacts");

						emergency_id = new ArrayList<String>();
						emergency_title = new ArrayList<String>();
						emergency_phone = new ArrayList<String>();
						layoutInflater3 = getActivity().getLayoutInflater();
						health_emergency_lay.removeAllViews();
						for (int i = 0; i < jsonArray.length(); i++) {
							emergency_id.add(jsonArray.getJSONObject(i)
									.get("id").toString().trim());
							emergency_title.add(jsonArray.getJSONObject(i)
									.get("title").toString().trim());
							emergency_phone.add(jsonArray.getJSONObject(i)
									.get("phone").toString().trim());

							view3 = layoutInflater3.inflate(
									R.layout.emergency_row_layout,
									health_emergency_lay, false);

							TextView title_txt = (TextView) view3
									.findViewById(R.id.title_1);
							
							phone_txt = (TextView) view3
									.findViewById(R.id.title_1_pho);
							title_txt.setTypeface(Constants.ProximaNova_Regular);
							phone_txt.setTypeface(Constants.ProximaNova_Regular);
							
							title_txt.setText(jsonArray.getJSONObject(i)
									.get("title").toString().trim());

							phone_txt.setText(jsonArray.getJSONObject(i)
									.get("phone").toString().trim());

							phone_txt.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									makeAcall(phone_txt.getText().toString());
								}
							});

							// if(fontChaning == 1){
							// title_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							// 19);
							// phone_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							// 16);
							// }else if(fontChaning == 2){
							// title_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							// 21);
							// phone_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							// 18);
							// }else if(fontChaning == 3){
							// title_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							// 23);
							// phone_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							// 20);
							// }else if(fontChaning == 0){
							// title_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							// 17);
							// phone_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							// 14);
							// }

							if (SplashScreen.fontpref.getString(
									SplashScreen.Key_GET_FONT_ID, "")
									.equalsIgnoreCase("1")) {
								title_txt.setTextSize(
										TypedValue.COMPLEX_UNIT_SP, 17);
								phone_txt.setTextSize(
										TypedValue.COMPLEX_UNIT_SP, 14);
							} else if (SplashScreen.fontpref.getString(
									SplashScreen.Key_GET_FONT_ID, "")
									.equalsIgnoreCase("2")) {
								title_txt.setTextSize(
										TypedValue.COMPLEX_UNIT_SP, 15);
								phone_txt.setTextSize(
										TypedValue.COMPLEX_UNIT_SP, 12);
							} else if (SplashScreen.fontpref.getString(
									SplashScreen.Key_GET_FONT_ID, "")
									.equalsIgnoreCase("3")) {
								title_txt.setTextSize(
										TypedValue.COMPLEX_UNIT_SP, 19);
								phone_txt.setTextSize(
										TypedValue.COMPLEX_UNIT_SP, 16);
							}

							health_emergency_lay.addView(view3);

						}

						if (pDialog.isShowing() && pDialog != null) {
							pDialog.dismiss();
						}
					}

				} catch (Exception e1) {
					e1.printStackTrace();
					if (pDialog.isShowing() && pDialog != null) {
						pDialog.dismiss();
					}
				}

				// dbbHelper.close();
				// baseDatabase.close();

			}

		}
	}

	private void makeAcall(String string) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + string));
		startActivity(callIntent);

	}

	// Private class for gestures
	private class SwipeGestureDetector extends SimpleOnGestureListener {
		// Swipe properties, you can change it to make the swipe
		// longer or shorter and speed
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 200;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				float diffAbs = Math.abs(e1.getY() - e2.getY());
				float diff = e1.getX() - e2.getX();

				if (diffAbs > SWIPE_MAX_OFF_PATH)
					return false;

				// Left swipe
				if (diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					onLeftSwipe();

					// Right swipe
				} else if (-diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					onRightSwipe();
				}
			} catch (Exception e) {
				Log.e("YourActivity", "Error on gestures");
			}
			return false;
		}
	}

	private void onLeftSwipe() {
		// Do something
	}

	private void onRightSwipe() {
		// Do something
		makeAcall("04024745243");
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.tourism_department_phno:

			makeAcall("1800-425-46464");
			break;

		// case R.id.railway_enquiry_pho1:
		// makeAcall("131");
		// break;
		//
		// case R.id.railway_enquiry_pho2:
		// makeAcall("135");
		// break;
		//
		// case R.id.electricity_complaint_title_1_pho:
		// makeAcall("1912");
		// break;
		//
		// case R.id.traffic_help_1_pho:
		// makeAcall("1073");
		// break;
		//
		// case R.id.crime_stopper_1_pho:
		// makeAcall("1090");
		// break;
		//
		// case R.id.apollo_ambulance_1_pho:
		// makeAcall("1066");
		// break;
		//
		// case R.id.osmania_general_hospital_pho1:
		// makeAcall("04023538846");
		// break;
		//
		// case R.id.osmania_general_hospital_pho2:
		// makeAcall("04024600146");
		// break;
		//
		// case R.id.gandhi_hospital_1_pho:
		// makeAcall("04027505566");
		// break;
		//
		// case R.id.government_chest_hospital_pho1:
		// makeAcall("04023814421");
		// break;
		//
		// case R.id.government_chest_hospital_pho2:
		// makeAcall("04023814422");
		// break;
		//
		// case R.id.government_chest_hospital_pho3:
		// makeAcall("04023814423");
		//
		// break;
		//
		// case R.id.government_chest_hospital_pho4:
		// makeAcall("04023814424");
		//
		// break;
		//
		// case R.id.government_ent_hospital_pho12:
		// makeAcall("04024740245");
		//
		// break;
		//
		// case R.id.government_ent_hospital_pho22:
		// makeAcall("04024742329​​​​");
		// break;
		//
		// case R.id.women_protection_cell_txt:
		// makeAcall("04023240663​​​​");
		// break;
		//
		// case R.id.child_line_txt:
		// makeAcall("1098​​​​");
		// break;

		default:
			break;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
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
