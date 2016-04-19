package com.telanganatourism.android.phase2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.android.sdk.EBSPayment;
import com.ebs.android.sdk.PaymentDetailActivity;
import com.ebs.android.sdk.PaymentRequest;
import com.ebs.android.sdk.Config.Currency;
import com.ebs.android.sdk.Config.Encryption;
import com.ebs.android.sdk.Config.Mode;
import com.telanganatourism.android.phase2.HotelBookingActivity.GetRoomTypes;
import com.telanganatourism.android.phase2.util.Utility;

public class PackageTouristDetails extends Activity implements OnCheckedChangeListener {
	
	ArrayList<HashMap<String, String>> custom_post_parameters;
	
	Pattern pattern1 = Pattern
			.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

	EditText nameEdt, lastNameEdt, homeNoEdt, streetEdt, cityEdt, stateEdt,
			countryEdt, pinCodeEdt, emailEdt, phoneEdt;
	EditText nameEdt1, age1, nameEdt2, age2, nameEdt3, age3, nameEdt4, age4,
			nameEdt5, age5, nameEdt6, age6;

	TextView tv_firstname1, tv_lastname1, tv_user1, tv_user2, tv_user3,
			tv_user4, tv_user5, tv_user6, tv_user1_age, tv_user2_age,
			tv_user3_age, tv_user4_age, tv_user5_age, tv_user6_age,
			tv_user1_Gtxt, tv_user2_Gtxt, tv_user3_Gtxt, tv_user4_Gtxt,
			tv_user5_Gtxt, tv_user6_Gtxt, titleTxt, textView2, textView3,
			textView4;

	// for title
	TextView tv_apptext, tv_titletxt1, tv_titletxt2, tv_titletxt3,
			tv_titletxt4, tv_titletxt5, tv_titletxt6;

	Button confirmBtn;
	// for title
	RadioGroup radioGroup_1, radioGroup_2, radioGroup_3, radioGroup_4,
			radioGroup_5, radioGroup_6, radioGroup_7;
	
	private RadioButton radioTitleButton, radioTitleButton2, radioTitleButton3,
			radioTitleButton4, radioTitleButton5, radioTitleButton6,
			radioTitleButton7;
	
	private RadioButton radioGenderButton1, radioGenderButton2,
	radioGenderButton3, radioGenderButton4, radioGenderButton5,
	radioGenderButton6;
	public static String StrPackID;
	
	int selectedId1, selectedId2, selectedId3, selectedId4, selectedId5,
			selectedId6, selectedId7;
	String StrTitle = "", StrTitle1 = "", strChildTitle = "";

	RadioButton mrRdBtn, mrRdBtn1, mrRdBtn2, mrRdBtn3, mrRdBtn4, mrRdBtn5,
			mrRdBtn6, mrsRdBtn, mrsRdBtn1, mrsRdBtn2, mrsRdBtn3, mrsRdBtn4,
			mrsRdBtn5, mrsRdBtn6;

	// for gender
	RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5,
			radioGroup6;
	
	int sId1, sId2, sId3, sId4, sId5, sId6;
	String StrAdultGender = "", strChildGender = "";

	String strGender1, strGender2, strGender3, strGender4, strGender5,
			strGender6;

	// for userName with ~
	String strAdultName, strChildName = "", strAdultAge, strChildAge = "",
			strAdultSeatNo, strChildSeatNo = "", strAdultTarrif,
			strChildTarrif="";

	RadioButton user1_maleRdBtn, user1_femaleRdBtn, user2_maleRdBtn,
			user2_femaleRdBtn, user3_maleRdBtn, user3_femaleRdBtn,
			user4_maleRdBtn, user4_femaleRdBtn, user5_maleRdBtn,
			user5_femaleRdBtn, user6_maleRdBtn, user6_femaleRdBtn;

	LinearLayout layout1, layout2, layout3, layout4, layout5, layout6;
//	Utility utility;

	// Spinner gender1, gender2, gender3, gender4, gender5, gender6;
	HttpResponse response;
	private static final int ACC_ID = 17582; // Provided by EBS
	private static final String SECRET_KEY = "f835a539cd5868fe9594e3c5315056f6"; // Provided
	private static final int GROUP_ID = 1; // Provided by EBS
	private static String HOST_NAME = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.package_tourist_details);
		HOST_NAME = getResources().getString(R.string.hostname);
//		utility = new Utility(PackageTouristDetails.this);
		/*
		 * List<String> list = new ArrayList<String>(); list.add("M");
		 * list.add("F");
		 */

		RelativeLayout menu_layout = (RelativeLayout) findViewById(R.id.menuLayout);

		menu_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// startActivity(new Intent(HotelBookingConfirm.this,
				// HotelBookingActivity.class));
				finish();
			}
		});

		nameEdt = (EditText) findViewById(R.id.firstNameEdt);
		lastNameEdt = (EditText) findViewById(R.id.lastNameEdt);
		homeNoEdt = (EditText) findViewById(R.id.homeNoEdt);
		streetEdt = (EditText) findViewById(R.id.streetEdt);
		cityEdt = (EditText) findViewById(R.id.cityEdt);
		stateEdt = (EditText) findViewById(R.id.stateEdt);
		countryEdt = (EditText) findViewById(R.id.countryEdt);
		pinCodeEdt = (EditText) findViewById(R.id.pinCodeEdt);
		emailEdt = (EditText) findViewById(R.id.emailEdt);
		phoneEdt = (EditText) findViewById(R.id.phoneedt);

		nameEdt1 = (EditText) findViewById(R.id.nameEdt1);
		age1 = (EditText) findViewById(R.id.ageEdt1);
		nameEdt2 = (EditText) findViewById(R.id.nameEdt2);
		age2 = (EditText) findViewById(R.id.ageEdt2);
		nameEdt3 = (EditText) findViewById(R.id.nameEdt3);
		age3 = (EditText) findViewById(R.id.ageEdt3);
		nameEdt4 = (EditText) findViewById(R.id.nameEdt4);
		age4 = (EditText) findViewById(R.id.ageEdt4);
		nameEdt5 = (EditText) findViewById(R.id.nameEdt5);
		age5 = (EditText) findViewById(R.id.ageEdt5);
		nameEdt6 = (EditText) findViewById(R.id.nameEdt6);
		age6 = (EditText) findViewById(R.id.ageEdt6);

		tv_firstname1 = (TextView) findViewById(R.id.tv_firstname1);
		tv_lastname1 = (TextView) findViewById(R.id.tv_lastname1);

		tv_user1 = (TextView) findViewById(R.id.tv_user1);
		tv_user2 = (TextView) findViewById(R.id.tv_user2);
		tv_user3 = (TextView) findViewById(R.id.tv_user3);
		tv_user4 = (TextView) findViewById(R.id.tv_user4);
		tv_user5 = (TextView) findViewById(R.id.tv_user5);
		tv_user6 = (TextView) findViewById(R.id.tv_user6);

		tv_user1_age = (TextView) findViewById(R.id.tv_user1_age);
		tv_user2_age = (TextView) findViewById(R.id.tv_user2_age);
		tv_user3_age = (TextView) findViewById(R.id.tv_user3_age);
		tv_user4_age = (TextView) findViewById(R.id.tv_user4_age);
		tv_user5_age = (TextView) findViewById(R.id.tv_user5_age);
		tv_user6_age = (TextView) findViewById(R.id.tv_user6_age);

		tv_user1_Gtxt = (TextView) findViewById(R.id.tv_user1_Gtxt);
		tv_user2_Gtxt = (TextView) findViewById(R.id.tv_user2_Gtxt);
		tv_user3_Gtxt = (TextView) findViewById(R.id.tv_user3_Gtxt);
		tv_user4_Gtxt = (TextView) findViewById(R.id.tv_user4_Gtxt);
		tv_user5_Gtxt = (TextView) findViewById(R.id.tv_user5_Gtxt);
		tv_user6_Gtxt = (TextView) findViewById(R.id.tv_user6_Gtxt);

		titleTxt = (TextView) findViewById(R.id.event_title);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		textView4 = (TextView) findViewById(R.id.textView4);

		tv_apptext = (TextView) findViewById(R.id.tv_apptext);
		tv_titletxt1 = (TextView) findViewById(R.id.tv_titletxt1);
		tv_titletxt2 = (TextView) findViewById(R.id.tv_titletxt2);
		tv_titletxt3 = (TextView) findViewById(R.id.tv_titletxt3);
		tv_titletxt4 = (TextView) findViewById(R.id.tv_titletxt4);
		tv_titletxt5 = (TextView) findViewById(R.id.tv_titletxt5);
		tv_titletxt6 = (TextView) findViewById(R.id.tv_titletxt6);

		user1_maleRdBtn = (RadioButton) findViewById(R.id.user1_maleRdBtn);
		user2_maleRdBtn = (RadioButton) findViewById(R.id.user2_maleRdBtn);
		user3_maleRdBtn = (RadioButton) findViewById(R.id.user3_maleRdBtn);
		user4_maleRdBtn = (RadioButton) findViewById(R.id.user4_maleRdBtn);
		user5_maleRdBtn = (RadioButton) findViewById(R.id.user5_maleRdBtn);
		user6_maleRdBtn = (RadioButton) findViewById(R.id.user6_maleRdBtn);

		user1_femaleRdBtn = (RadioButton) findViewById(R.id.user1_femaleRdBtn);
		user2_femaleRdBtn = (RadioButton) findViewById(R.id.user2_femaleRdBtn);
		user3_femaleRdBtn = (RadioButton) findViewById(R.id.user3_femaleRdBtn);
		user4_femaleRdBtn = (RadioButton) findViewById(R.id.user4_femaleRdBtn);
		user5_femaleRdBtn = (RadioButton) findViewById(R.id.user5_femaleRdBtn);
		user6_femaleRdBtn = (RadioButton) findViewById(R.id.user6_femaleRdBtn);

		mrRdBtn = (RadioButton) findViewById(R.id.mrRdBtn);
		mrRdBtn1 = (RadioButton) findViewById(R.id.mrRdBtn1);
		mrRdBtn2 = (RadioButton) findViewById(R.id.mrRdBtn2);
		mrRdBtn3 = (RadioButton) findViewById(R.id.mrRdBtn3);
		mrRdBtn4 = (RadioButton) findViewById(R.id.mrRdBtn4);
		mrRdBtn5 = (RadioButton) findViewById(R.id.mrRdBtn5);
		mrRdBtn6 = (RadioButton) findViewById(R.id.mrRdBtn6);

		mrsRdBtn = (RadioButton) findViewById(R.id.mrsRdBtn);
		mrsRdBtn1 = (RadioButton) findViewById(R.id.mrsRdBtn1);
		mrsRdBtn2 = (RadioButton) findViewById(R.id.mrsRdBtn2);
		mrsRdBtn3 = (RadioButton) findViewById(R.id.mrsRdBtn3);
		mrsRdBtn4 = (RadioButton) findViewById(R.id.mrsRdBtn4);
		mrsRdBtn5 = (RadioButton) findViewById(R.id.mrsRdBtn5);
		mrsRdBtn6 = (RadioButton) findViewById(R.id.mrsRdBtn6);

		confirmBtn = (Button) findViewById(R.id.confirmBtn);

		layout1 = (LinearLayout) findViewById(R.id.tourist1);
		layout2 = (LinearLayout) findViewById(R.id.tourist2);
		layout3 = (LinearLayout) findViewById(R.id.tourist3);
		layout4 = (LinearLayout) findViewById(R.id.tourist4);
		layout5 = (LinearLayout) findViewById(R.id.tourist5);
		layout6 = (LinearLayout) findViewById(R.id.tourist6);

		
		//for title radioGroup 
		radioGroup_1 = (RadioGroup) findViewById(R.id.radioGroup_1);
		radioGroup_2 = (RadioGroup) findViewById(R.id.radioGroup_2);
		radioGroup_3 = (RadioGroup) findViewById(R.id.radioGroup_3);
		radioGroup_4 = (RadioGroup) findViewById(R.id.radioGroup_4);
		radioGroup_5 = (RadioGroup) findViewById(R.id.radioGroup_5);
		radioGroup_6 = (RadioGroup) findViewById(R.id.radioGroup_6);
		radioGroup_7 = (RadioGroup) findViewById(R.id.radioGroup_7);

		//for gender radioGroup
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
		radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
		radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);
		radioGroup5 = (RadioGroup) findViewById(R.id.radioGroup5);
		radioGroup6 = (RadioGroup) findViewById(R.id.radioGroup6);

		nameEdt.setTypeface(Constants.ProximaNova_Regular);
		lastNameEdt.setTypeface(Constants.ProximaNova_Regular);
		homeNoEdt.setTypeface(Constants.ProximaNova_Regular);
		streetEdt.setTypeface(Constants.ProximaNova_Regular);
		cityEdt.setTypeface(Constants.ProximaNova_Regular);
		stateEdt.setTypeface(Constants.ProximaNova_Regular);
		countryEdt.setTypeface(Constants.ProximaNova_Regular);
		pinCodeEdt.setTypeface(Constants.ProximaNova_Regular);
		emailEdt.setTypeface(Constants.ProximaNova_Regular);
		phoneEdt.setTypeface(Constants.ProximaNova_Regular);

		nameEdt1.setTypeface(Constants.ProximaNova_Regular);
		age1.setTypeface(Constants.ProximaNova_Regular);
		nameEdt2.setTypeface(Constants.ProximaNova_Regular);
		age2.setTypeface(Constants.ProximaNova_Regular);
		nameEdt3.setTypeface(Constants.ProximaNova_Regular);
		age3.setTypeface(Constants.ProximaNova_Regular);
		nameEdt4.setTypeface(Constants.ProximaNova_Regular);
		age4.setTypeface(Constants.ProximaNova_Regular);
		nameEdt5.setTypeface(Constants.ProximaNova_Regular);
		age5.setTypeface(Constants.ProximaNova_Regular);
		nameEdt6.setTypeface(Constants.ProximaNova_Regular);
		age6.setTypeface(Constants.ProximaNova_Regular);

		tv_firstname1.setTypeface(Constants.ProximaNova_Regular);
		tv_lastname1.setTypeface(Constants.ProximaNova_Regular);

		tv_user1.setTypeface(Constants.ProximaNova_Regular);
		tv_user2.setTypeface(Constants.ProximaNova_Regular);
		tv_user3.setTypeface(Constants.ProximaNova_Regular);
		tv_user4.setTypeface(Constants.ProximaNova_Regular);
		tv_user5.setTypeface(Constants.ProximaNova_Regular);
		tv_user6.setTypeface(Constants.ProximaNova_Regular);

		tv_user1_age.setTypeface(Constants.ProximaNova_Regular);
		tv_user2_age.setTypeface(Constants.ProximaNova_Regular);
		tv_user3_age.setTypeface(Constants.ProximaNova_Regular);
		tv_user4_age.setTypeface(Constants.ProximaNova_Regular);
		tv_user5_age.setTypeface(Constants.ProximaNova_Regular);
		tv_user6_age.setTypeface(Constants.ProximaNova_Regular);

		tv_user1_Gtxt.setTypeface(Constants.ProximaNova_Regular);
		tv_user2_Gtxt.setTypeface(Constants.ProximaNova_Regular);
		tv_user3_Gtxt.setTypeface(Constants.ProximaNova_Regular);
		tv_user4_Gtxt.setTypeface(Constants.ProximaNova_Regular);
		tv_user5_Gtxt.setTypeface(Constants.ProximaNova_Regular);
		tv_user6_Gtxt.setTypeface(Constants.ProximaNova_Regular);

		titleTxt.setTypeface(Constants.ProximaNova_Regular);
		textView2.setTypeface(Constants.ProximaNova_Regular);
		textView3.setTypeface(Constants.ProximaNova_Regular);
		textView4.setTypeface(Constants.ProximaNova_Regular);

		user1_maleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		user2_maleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		user3_maleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		user4_maleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		user5_maleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		user6_maleRdBtn.setTypeface(Constants.ProximaNova_Regular);

		user1_femaleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		user2_femaleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		user3_femaleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		user4_femaleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		user5_femaleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		user6_femaleRdBtn.setTypeface(Constants.ProximaNova_Regular);

		confirmBtn.setTypeface(Constants.ProximaNova_Regular);

		tv_apptext.setTypeface(Constants.ProximaNova_Regular);
		tv_titletxt1.setTypeface(Constants.ProximaNova_Regular);
		tv_titletxt2.setTypeface(Constants.ProximaNova_Regular);
		tv_titletxt3.setTypeface(Constants.ProximaNova_Regular);
		tv_titletxt4.setTypeface(Constants.ProximaNova_Regular);
		tv_titletxt5.setTypeface(Constants.ProximaNova_Regular);
		tv_titletxt6.setTypeface(Constants.ProximaNova_Regular);

		mrRdBtn.setTypeface(Constants.ProximaNova_Regular);
		mrRdBtn1.setTypeface(Constants.ProximaNova_Regular);
		mrRdBtn2.setTypeface(Constants.ProximaNova_Regular);
		mrRdBtn3.setTypeface(Constants.ProximaNova_Regular);
		mrRdBtn4.setTypeface(Constants.ProximaNova_Regular);
		mrRdBtn5.setTypeface(Constants.ProximaNova_Regular);
		mrRdBtn6.setTypeface(Constants.ProximaNova_Regular);

		mrsRdBtn.setTypeface(Constants.ProximaNova_Regular);
		mrsRdBtn1.setTypeface(Constants.ProximaNova_Regular);
		mrsRdBtn2.setTypeface(Constants.ProximaNova_Regular);
		mrsRdBtn3.setTypeface(Constants.ProximaNova_Regular);
		mrsRdBtn4.setTypeface(Constants.ProximaNova_Regular);
		mrsRdBtn5.setTypeface(Constants.ProximaNova_Regular);
		mrsRdBtn6.setTypeface(Constants.ProximaNova_Regular);

		titleTxt.setText(DetailScreen1.tit);
		countryEdt.setText("IND");
		countryEdt.setTextColor(Color.BLACK);
		countryEdt.setEnabled(false);
		
		//for title radioGroup 
//				radioGroup_1.setOnCheckedChangeListener(this);
				radioGroup_2.setOnCheckedChangeListener(this);
				radioGroup_3.setOnCheckedChangeListener(this);
				radioGroup_4.setOnCheckedChangeListener(this);
				radioGroup_5.setOnCheckedChangeListener(this);
				radioGroup_6.setOnCheckedChangeListener(this);
				radioGroup_7.setOnCheckedChangeListener(this);

				//for gender radioGroup
				radioGroup1.setOnCheckedChangeListener(this);
				radioGroup2.setOnCheckedChangeListener(this);
				radioGroup3.setOnCheckedChangeListener(this);
				radioGroup4.setOnCheckedChangeListener(this);
				radioGroup5.setOnCheckedChangeListener(this);
				radioGroup6.setOnCheckedChangeListener(this);
		
		
		
		
		

		/*
		 * gender1 = (Spinner) findViewById(R.id.genderSpnr1); gender2 =
		 * (Spinner) findViewById(R.id.genderSpnr2); gender3 = (Spinner)
		 * findViewById(R.id.genderSpnr3); gender4 = (Spinner)
		 * findViewById(R.id.genderSpnr4); gender5 = (Spinner)
		 * findViewById(R.id.genderSpnr5); gender6 = (Spinner)
		 * findViewById(R.id.genderSpnr6);
		 * 
		 * ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_spinner_item, list);
		 * 
		 * ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_spinner_item, list);
		 * 
		 * ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_spinner_item, list);
		 * 
		 * ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_spinner_item, list);
		 * 
		 * ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_spinner_item, list);
		 * 
		 * ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_spinner_item, list);
		 * 
		 * dataAdapter
		 * .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
		 * );
		 * 
		 * dataAdapter1
		 * .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
		 * );
		 * 
		 * dataAdapter2
		 * .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
		 * );
		 * 
		 * dataAdapter3
		 * .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
		 * );
		 * 
		 * dataAdapter4
		 * .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
		 * );
		 * 
		 * dataAdapter5
		 * .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
		 * );
		 */

		/*
		 * gender1.setAdapter(dataAdapter); gender2.setAdapter(dataAdapter1);
		 * gender3.setAdapter(dataAdapter2); gender4.setAdapter(dataAdapter3);
		 * gender5.setAdapter(dataAdapter4); gender6.setAdapter(dataAdapter5);
		 */

		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Utility.checkInternetConnection(PackageTouristDetails.this)) {
					if (nameEdt.getText().toString().trim().length() == 0) {
						Toast.makeText(PackageTouristDetails.this,
								"Please enter name", Toast.LENGTH_LONG).show();
					} else {
						if (lastNameEdt.getText().toString().trim().length() == 0) {
							Toast.makeText(PackageTouristDetails.this,
									"Please enter last name", Toast.LENGTH_LONG)
									.show();
						} else {

							if (homeNoEdt.getText().toString().trim().length() == 0) {
								Toast.makeText(PackageTouristDetails.this,
										"Please enter house number",
										Toast.LENGTH_LONG).show();
							} else {

								if (streetEdt.getText().toString().trim()
										.length() == 0) {
									Toast.makeText(PackageTouristDetails.this,
											"Please enter street name",
											Toast.LENGTH_LONG).show();
								} else {
									if (cityEdt.getText().toString().trim()
											.length() == 0) {
										Toast.makeText(
												PackageTouristDetails.this,
												"Please enter city name",
												Toast.LENGTH_LONG).show();
									} else {
										if (stateEdt.getText().toString()
												.trim().length() == 0) {
											Toast.makeText(
													PackageTouristDetails.this,
													"Please enter state name",
													Toast.LENGTH_LONG).show();
										} else {
											if (pinCodeEdt.getText().toString()
													.trim().length() == 0) {
												Toast.makeText(
														PackageTouristDetails.this,
														"Please enter 6 digit valid pin code number",
														Toast.LENGTH_LONG)
														.show();
											} else {
												if (pinCodeEdt.getText()
														.length() < 6) {
													Toast.makeText(
															PackageTouristDetails.this,
															"Please enter 6 digit valid pin code number",
															Toast.LENGTH_LONG)
															.show();
												} else {
													if (phoneEdt.getText()
															.toString().trim()
															.length() == 0) {
														Toast.makeText(
																PackageTouristDetails.this,
																"Please enter 10 digit valid phone number",
																Toast.LENGTH_LONG)
																.show();
													} else {
														if (emailEdt.getText()
																.toString()
																.trim()
																.length() == 0) {
															Toast.makeText(
																	PackageTouristDetails.this,
																	"Please enter email id",
																	Toast.LENGTH_LONG)
																	.show();
														} else {
															if (phoneEdt
																	.getText()
																	.length() < 10) {
																Toast.makeText(
																		PackageTouristDetails.this,
																		"Please enter 10 digit valid phone number",
																		Toast.LENGTH_LONG)
																		.show();
															}

															else {
																Matcher matcher1 = pattern1
																		.matcher(emailEdt
																				.getText()
																				.toString()
																				.trim());

																if (matcher1
																		.matches()) {

																	Constants.paymentNav = "2";

																	if (PackageListing.adultVal == 1) {
																		strAdultTarrif = Package_Booking.adultTariff;
																	} else if (PackageListing.adultVal == 2) {
																		strAdultTarrif = Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff;
																	} else if (PackageListing.adultVal == 3) {
																		strAdultTarrif = Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff;
																	} else if (PackageListing.adultVal == 4) {
																		strAdultTarrif = Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff;
																	} else if (PackageListing.adultVal == 5) {
																		strAdultTarrif = Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff;
																	} else if (PackageListing.adultVal == 6) {
																		strAdultTarrif = Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff
																				+ "~"
																				+ Package_Booking.adultTariff;
																	}

																	if (PackageListing.childVal == 1) {
																		strChildTarrif = Package_Booking.childTariff;
																	} else if (PackageListing.childVal == 2) {
																		strChildTarrif = Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff;
																	} else if (PackageListing.childVal == 3) {
																		strChildTarrif = Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff;
																	} else if (PackageListing.childVal == 4) {
																		strChildTarrif = Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff;
																	} else if (PackageListing.childVal == 5) {
																		strChildTarrif = Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff;
																	} else if (PackageListing.childVal == 6) {
																		strChildTarrif = Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff
																				+ "~"
																				+ Package_Booking.childTariff;
																	}

																	selectedId1 = radioGroup_1
																			.getCheckedRadioButtonId();

																	radioTitleButton = (RadioButton) findViewById(selectedId1);

																	if (radioTitleButton
																			.getText()
																			.toString()
																			.equals("Mr.")) {
																		
																		StrTitle = "Mr.";
																	} else {
																		StrTitle = "Mrs.";
																	}

																	if (Package_Booking.no_of_people
																			.equalsIgnoreCase("1")) {

																		selectedId2 = radioGroup_2
																				.getCheckedRadioButtonId();

																		radioTitleButton2 = (RadioButton) findViewById(selectedId2);
																		if (radioTitleButton2
																				.getText()
																				.toString()
																				.equals("Mr.")) {
																			
//																			user1_maleRdBtn.setChecked(true);
																			StrTitle1 = "Mr.";
																		} else {
//																			user1_femaleRdBtn.setChecked(true);
																			StrTitle1 = "Mrs.";
																		}

//																		StrTitle1 = radioTitleButton2
//																				.getText()
//																				.toString();

																		sId1 = radioGroup1
																				.getCheckedRadioButtonId();
																		radioGenderButton1 = (RadioButton) findViewById(sId1);

																		if (radioGenderButton1
																				.getText()
																				.toString()
																				.equals("Male")) {
																			
																			StrAdultGender = "M";
//																			mrRdBtn1.setChecked(true);
																			
																		} else {
																			StrAdultGender = "F";
//																			mrsRdBtn1.setChecked(true);
																		}

																		strAdultName = nameEdt1
																				.getText()
																				.toString();
																		strAdultAge = age1
																				.getText()
																				.toString();

																		strAdultSeatNo = Package_Booking.seatNos1;

																	} else if (Package_Booking.no_of_people
																			.equalsIgnoreCase("2")) {

																		if (PackageListing.adultVal == 2) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString();

																			strAdultSeatNo = Package_Booking.seatNos1
																					.replace(
																							",",
																							"~");

																		} else {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString();
																			strChildTitle = radioTitleButton3
																					.getText()
																					.toString();

																			StrAdultGender = strGender1;
																			strChildGender = strGender2;

																			strAdultName = nameEdt1
																					.getText()
																					.toString();
																			strAdultAge = age1
																					.getText()
																					.toString();

																			strChildName = nameEdt2
																					.getText()
																					.toString();
																			strChildAge = age2
																					.getText()
																					.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");

																			strAdultSeatNo = splitStr[0];
																			strChildSeatNo = splitStr[1];

																		}

																	} else if (Package_Booking.no_of_people
																			.equalsIgnoreCase("3")) {

																		if (PackageListing.adultVal == 3) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2
																					+ "~"
																					+ strGender3;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString()
																					+ "~"
																					+ age3.getText()
																							.toString();

																			strAdultSeatNo = Package_Booking.seatNos1
																					.replace(
																							",",
																							"~");

																		} else if (PackageListing.adultVal == 2) {

																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString();

																			strChildTitle = radioTitleButton4
																					.getText()
																					.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2;

																			strChildGender = strGender3;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString();

																			strChildName = nameEdt3
																					.getText()
																					.toString();
																			strChildAge = age3
																					.getText()
																					.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");

																			strAdultSeatNo = splitStr[0]
																					+ "~"
																					+ splitStr[1];
																			strChildSeatNo = splitStr[2];

																		} else {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString();

																			strChildTitle = radioTitleButton3
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			StrAdultGender = strGender1;
																			strChildGender = strGender2
																					+ "~"
																					+ strGender3;

																			strAdultName = nameEdt1
																					.getText()
																					.toString();
																			strAdultAge = age1
																					.getText()
																					.toString();

																			strChildName = nameEdt2
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString();
																			strChildAge = age2
																					.getText()
																					.toString()
																					+ "~"
																					+ age3.getText()
																							.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");

																			strAdultSeatNo = splitStr[0];
																			strChildSeatNo = splitStr[1]
																					+ "~"
																					+ splitStr[2];

																		}

																	} else if (Package_Booking.no_of_people
																			.equalsIgnoreCase("4")) {

																		if (PackageListing.adultVal == 4) {

																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2
																					+ "~"
																					+ strGender3
																					+ "~"
																					+ strGender4;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString()
																					+ "~"
																					+ age3.getText()
																							.toString()
																					+ "~"
																					+ age4.getText()
																							.toString();

																			strAdultSeatNo = Package_Booking.seatNos1
																					.replace(
																							",",
																							"~");

																		} else if (PackageListing.adultVal == 3) {

																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString();

																			strChildTitle = radioTitleButton5
																					.getText()
																					.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2
																					+ "~"
																					+ strGender3;
																			strChildGender = strGender4;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString()
																					+ "~"
																					+ age3.getText()
																							.toString();

																			strChildName = nameEdt4
																					.getText()
																					.toString();
																			strChildAge = age4
																					.getText()
																					.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0]
																					+ "~"
																					+ splitStr[1]
																					+ "~"
																					+ splitStr[2];
																			strChildSeatNo = splitStr[3];

																		} else if (PackageListing.adultVal == 2) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString();

																			strChildTitle = radioTitleButton4
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2;
																			strChildGender = strGender3
																					+ "~"
																					+ strGender4;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString();

																			strChildName = nameEdt3
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString();
																			strChildAge = age3
																					.getText()
																					.toString()
																					+ "~"
																					+ age4.getText()
																							.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0]
																					+ "~"
																					+ splitStr[1];
																			strChildSeatNo = splitStr[2]
																					+ "~"
																					+ splitStr[3];
																		} else if (PackageListing.adultVal == 1) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString();

																			strChildTitle = radioTitleButton3
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			StrAdultGender = strGender1;
																			strChildGender = strGender2
																					+ "~"
																					+ strGender3
																					+ "~"
																					+ strGender4;

																			strAdultName = nameEdt1
																					.getText()
																					.toString();
																			strAdultAge = age1
																					.getText()
																					.toString();

																			strChildName = nameEdt2
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString();
																			strChildAge = age2
																					.getText()
																					.toString()
																					+ "~"
																					+ age3.getText()
																							.toString()
																					+ "~"
																					+ age4.getText()
																							.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0];
																			strChildSeatNo = splitStr[1]
																					+ "~"
																					+ splitStr[2]
																					+ "~"
																					+ splitStr[3];
																		}

																	} else if (Package_Booking.no_of_people
																			.equalsIgnoreCase("5")) {

																		if (PackageListing.adultVal == 5) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton6
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2
																					+ "~"
																					+ strGender3
																					+ "~"
																					+ strGender4
																					+ "~"
																					+ strGender5;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt5
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString()
																					+ "~"
																					+ age3.getText()
																							.toString()
																					+ "~"
																					+ age4.getText()
																							.toString()
																					+ "~"
																					+ age5.getText()
																							.toString();

																			strAdultSeatNo = Package_Booking.seatNos1
																					.replace(
																							",",
																							"~");

																		} else if (PackageListing.adultVal == 4) {

																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString();

																			strChildTitle = radioTitleButton6
																					.getText()
																					.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2
																					+ "~"
																					+ strGender3
																					+ "~"
																					+ strGender4;
																			strChildGender = strGender5;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString()
																					+ "~"
																					+ age3.getText()
																							.toString()
																					+ "~"
																					+ age4.getText()
																							.toString();

																			strChildName = nameEdt5
																					.getText()
																					.toString();
																			strChildAge = age5
																					.getText()
																					.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0]
																					+ "~"
																					+ splitStr[1]
																					+ "~"
																					+ splitStr[2]
																					+ "~"
																					+ splitStr[3];
																			strChildSeatNo = splitStr[4];

																		} else if (PackageListing.adultVal == 3) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString();

																			strChildTitle = radioTitleButton5
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton6
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2
																					+ "~"
																					+ strGender3;
																			strChildGender = strGender4
																					+ "~"
																					+ strGender5;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString()
																					+ "~"
																					+ age3.getText()
																							.toString();

																			strChildName = nameEdt4
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt5
																							.getText()
																							.toString();
																			strChildAge = age4
																					.getText()
																					.toString()
																					+ "~"
																					+ age5.getText()
																							.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0]
																					+ "~"
																					+ splitStr[1]
																					+ "~"
																					+ splitStr[2];
																			strChildSeatNo = splitStr[3]
																					+ "~"
																					+ splitStr[4];

																		} else if (PackageListing.adultVal == 2) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString();

																			strChildTitle = radioTitleButton4
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton6
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2;
																			strChildGender = strGender3
																					+ "~"
																					+ strGender4
																					+ "~"
																					+ strGender5;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString();

																			strChildName = nameEdt3
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt5
																							.getText()
																							.toString();
																			strChildAge = age3
																					.getText()
																					.toString()
																					+ "~"
																					+ age4.getText()
																							.toString()
																					+ "~"
																					+ age5.getText()
																							.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0]
																					+ "~"
																					+ splitStr[1];
																			strChildSeatNo = splitStr[2]
																					+ "~"
																					+ splitStr[3]
																					+ "~"
																					+ splitStr[4];

																		} else if (PackageListing.adultVal == 1) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString();

																			strChildTitle = radioTitleButton3
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton6
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			StrAdultGender = strGender1;
																			strChildGender = strGender2
																					+ "~"
																					+ strGender3
																					+ "~"
																					+ strGender4
																					+ "~"
																					+ strGender5;

																			strAdultName = nameEdt1
																					.getText()
																					.toString();
																			strAdultAge = age1
																					.getText()
																					.toString();

																			strChildName = nameEdt2
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt5
																							.getText()
																							.toString();
																			strChildAge = age2
																					.getText()
																					.toString()
																					+ "~"
																					+ age3.getText()
																							.toString()
																					+ "~"
																					+ age4.getText()
																							.toString()
																					+ "~"
																					+ age5.getText()
																							.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0];
																			strChildSeatNo = splitStr[1]
																					+ "~"
																					+ splitStr[2]
																					+ "~"
																					+ splitStr[3]
																					+ "~"
																					+ splitStr[4];

																		}

																	} else if (Package_Booking.no_of_people
																			.equalsIgnoreCase("6")) {

																		if (PackageListing.adultVal == 6) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			selectedId7 = radioGroup_7
																					.getCheckedRadioButtonId();

																			radioTitleButton7 = (RadioButton) findViewById(selectedId7);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton6
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton7
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			sId6 = radioGroup6
																					.getCheckedRadioButtonId();
																			radioGenderButton6 = (RadioButton) findViewById(sId6);
																			if (radioGenderButton6
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender6 = "M";
																			} else {
																				strGender6 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2
																					+ "~"
																					+ strGender3
																					+ "~"
																					+ strGender4
																					+ "~"
																					+ strGender5
																					+ "~"
																					+ strGender6;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt5
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt6
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString()
																					+ "~"
																					+ age3.getText()
																							.toString()
																					+ "~"
																					+ age4.getText()
																							.toString()
																					+ "~"
																					+ age5.getText()
																							.toString()
																					+ "~"
																					+ age6.getText()
																							.toString();

																			strAdultSeatNo = Package_Booking.seatNos1
																					.replace(
																							",",
																							"~");

																		} else if (PackageListing.adultVal == 5) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			selectedId7 = radioGroup_7
																					.getCheckedRadioButtonId();

																			radioTitleButton7 = (RadioButton) findViewById(selectedId7);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton6
																							.getText()
																							.toString();

																			strChildTitle = radioTitleButton7
																					.getText()
																					.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			sId6 = radioGroup6
																					.getCheckedRadioButtonId();
																			radioGenderButton6 = (RadioButton) findViewById(sId6);
																			if (radioGenderButton6
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender6 = "M";
																			} else {
																				strGender6 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2
																					+ "~"
																					+ strGender3
																					+ "~"
																					+ strGender4
																					+ "~"
																					+ strGender5;
																			strChildGender = strGender6;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt5
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString()
																					+ "~"
																					+ age3.getText()
																							.toString()
																					+ "~"
																					+ age4.getText()
																							.toString()
																					+ "~"
																					+ age5.getText()
																							.toString();

																			strChildName = nameEdt6
																					.getText()
																					.toString();
																			strChildAge = age6
																					.getText()
																					.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0]
																					+ "~"
																					+ splitStr[1]
																					+ "~"
																					+ splitStr[2]
																					+ "~"
																					+ splitStr[3]
																					+ "~"
																					+ splitStr[4];
																			strChildSeatNo = splitStr[5];

																		} else if (PackageListing.adultVal == 4) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			selectedId7 = radioGroup_7
																					.getCheckedRadioButtonId();

																			radioTitleButton7 = (RadioButton) findViewById(selectedId7);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString();

																			strChildTitle = radioTitleButton6
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton7
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			sId6 = radioGroup6
																					.getCheckedRadioButtonId();
																			radioGenderButton6 = (RadioButton) findViewById(sId6);
																			if (radioGenderButton6
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender6 = "M";
																			} else {
																				strGender6 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2
																					+ "~"
																					+ strGender3
																					+ "~"
																					+ strGender4;
																			strChildGender = strGender5
																					+ "~"
																					+ strGender6;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString()
																					+ "~"
																					+ age3.getText()
																							.toString()
																					+ "~"
																					+ age4.getText()
																							.toString();

																			strChildName = nameEdt5
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt6
																							.getText()
																							.toString();
																			strChildAge = age5
																					.getText()
																					.toString()
																					+ "~"
																					+ age6.getText()
																							.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0]
																					+ "~"
																					+ splitStr[1]
																					+ "~"
																					+ splitStr[2]
																					+ "~"
																					+ splitStr[3];
																			strChildSeatNo = splitStr[4]
																					+ "~"
																					+ splitStr[5];

																		} else if (PackageListing.adultVal == 3) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			selectedId7 = radioGroup_7
																					.getCheckedRadioButtonId();

																			radioTitleButton7 = (RadioButton) findViewById(selectedId7);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString();

																			strChildTitle = radioTitleButton5
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton6
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton7
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			sId6 = radioGroup6
																					.getCheckedRadioButtonId();
																			radioGenderButton6 = (RadioButton) findViewById(sId6);
																			if (radioGenderButton6
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender6 = "M";
																			} else {
																				strGender6 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2
																					+ "~"
																					+ strGender3;
																			strChildGender = strGender4
																					+ "~"
																					+ strGender5
																					+ "~"
																					+ strGender6;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString()
																					+ "~"
																					+ age3.getText()
																							.toString();

																			strChildName = nameEdt4
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt5
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt6
																							.getText()
																							.toString();
																			strChildAge = age4
																					.getText()
																					.toString()
																					+ "~"
																					+ age5.getText()
																							.toString()
																					+ "~"
																					+ age6.getText()
																							.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0]
																					+ "~"
																					+ splitStr[1]
																					+ "~"
																					+ splitStr[2];
																			strChildSeatNo = splitStr[3]
																					+ "~"
																					+ splitStr[4]
																					+ "~"
																					+ splitStr[5];

																		} else if (PackageListing.adultVal == 2) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			selectedId7 = radioGroup_7
																					.getCheckedRadioButtonId();

																			radioTitleButton7 = (RadioButton) findViewById(selectedId7);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton3
																							.getText()
																							.toString();

																			strChildTitle = radioTitleButton4
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton6
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton7
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			sId6 = radioGroup6
																					.getCheckedRadioButtonId();
																			radioGenderButton6 = (RadioButton) findViewById(sId6);
																			if (radioGenderButton6
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender6 = "M";
																			} else {
																				strGender6 = "F";
																			}

																			StrAdultGender = strGender1
																					+ "~"
																					+ strGender2;
																			strChildGender = strGender3
																					+ "~"
																					+ strGender4
																					+ "~"
																					+ strGender5
																					+ "~"
																					+ strGender6;

																			strAdultName = nameEdt1
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt2
																							.getText()
																							.toString();
																			strAdultAge = age1
																					.getText()
																					.toString()
																					+ "~"
																					+ age2.getText()
																							.toString();

																			strChildName = nameEdt3
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt5
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt6
																							.getText()
																							.toString();
																			strChildAge = age3
																					.getText()
																					.toString()
																					+ "~"
																					+ age4.getText()
																							.toString()
																					+ "~"
																					+ age5.getText()
																							.toString()
																					+ "~"
																					+ age6.getText()
																							.toString();

																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0]
																					+ "~"
																					+ splitStr[1];
																			strChildSeatNo = splitStr[2]
																					+ "~"
																					+ splitStr[3]
																					+ "~"
																					+ splitStr[4]
																					+ "~"
																					+ splitStr[5];

																		} else if (PackageListing.adultVal == 1) {
																			selectedId2 = radioGroup_2
																					.getCheckedRadioButtonId();

																			radioTitleButton2 = (RadioButton) findViewById(selectedId2);

																			selectedId3 = radioGroup_3
																					.getCheckedRadioButtonId();

																			radioTitleButton3 = (RadioButton) findViewById(selectedId3);

																			selectedId4 = radioGroup_4
																					.getCheckedRadioButtonId();

																			radioTitleButton4 = (RadioButton) findViewById(selectedId4);

																			selectedId5 = radioGroup_5
																					.getCheckedRadioButtonId();

																			radioTitleButton5 = (RadioButton) findViewById(selectedId5);

																			selectedId6 = radioGroup_6
																					.getCheckedRadioButtonId();

																			radioTitleButton6 = (RadioButton) findViewById(selectedId6);

																			selectedId7 = radioGroup_7
																					.getCheckedRadioButtonId();

																			radioTitleButton7 = (RadioButton) findViewById(selectedId7);

																			StrTitle1 = radioTitleButton2
																					.getText()
																					.toString();

																			strChildTitle = radioTitleButton3
																					.getText()
																					.toString()
																					+ "~"
																					+ radioTitleButton4
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton5
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton6
																							.getText()
																							.toString()
																					+ "~"
																					+ radioTitleButton7
																							.getText()
																							.toString();

																			sId1 = radioGroup1
																					.getCheckedRadioButtonId();
																			radioGenderButton1 = (RadioButton) findViewById(sId1);
																			if (radioGenderButton1
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender1 = "M";
																			} else {
																				strGender1 = "F";
																			}

																			sId2 = radioGroup2
																					.getCheckedRadioButtonId();
																			radioGenderButton2 = (RadioButton) findViewById(sId2);
																			if (radioGenderButton2
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender2 = "M";
																			} else {
																				strGender2 = "F";
																			}

																			sId3 = radioGroup3
																					.getCheckedRadioButtonId();
																			radioGenderButton3 = (RadioButton) findViewById(sId3);
																			if (radioGenderButton3
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender3 = "M";
																			} else {
																				strGender3 = "F";
																			}

																			sId4 = radioGroup4
																					.getCheckedRadioButtonId();
																			radioGenderButton4 = (RadioButton) findViewById(sId4);
																			if (radioGenderButton4
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender4 = "M";
																			} else {
																				strGender4 = "F";
																			}

																			sId5 = radioGroup5
																					.getCheckedRadioButtonId();
																			radioGenderButton5 = (RadioButton) findViewById(sId5);
																			if (radioGenderButton5
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender5 = "M";
																			} else {
																				strGender5 = "F";
																			}

																			sId6 = radioGroup6
																					.getCheckedRadioButtonId();
																			radioGenderButton6 = (RadioButton) findViewById(sId6);
																			if (radioGenderButton6
																					.getText()
																					.toString()
																					.equals("Male")) {
																				strGender6 = "M";
																			} else {
																				strGender6 = "F";
																			}

																			StrAdultGender = strGender1;
																			strChildGender = strGender2
																					+ "~"
																					+ strGender3
																					+ "~"
																					+ strGender4
																					+ "~"
																					+ strGender5
																					+ "~"
																					+ strGender6;

																			strAdultName = nameEdt1
																					.getText()
																					.toString();
																			strAdultAge = age1
																					.getText()
																					.toString();

																			strChildName = nameEdt2
																					.getText()
																					.toString()
																					+ "~"
																					+ nameEdt3
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt4
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt5
																							.getText()
																							.toString()
																					+ "~"
																					+ nameEdt6
																							.getText()
																							.toString();
																			strChildAge = age2
																					.getText()
																					.toString()
																					+ "~"
																					+ age3.getText()
																							.toString()
																					+ "~"
																					+ age4.getText()
																							.toString()
																					+ "~"
																					+ age5.getText()
																							.toString()
																					+ "~"
																					+ age6.getText()
																							.toString();
																			String splitStr[] = Package_Booking.seatNos1
																					.split(",");
																			strAdultSeatNo = splitStr[0];
																			strChildSeatNo = splitStr[1]
																					+ "~"
																					+ splitStr[2]
																					+ "~"
																					+ splitStr[3]
																					+ "~"
																					+ splitStr[4]
																					+ "~"
																					+ splitStr[5];
																		}

																	}

																	if (Package_Booking.no_of_people
																			.equalsIgnoreCase("1")) {

																		if (age1.getText()
																				.toString()
																				.trim()
																				.length() == 0
																				|| nameEdt1
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0) {
																			Toast.makeText(
																					PackageTouristDetails.this,
																					"Please fill all the tourist details.",
																					Toast.LENGTH_LONG)
																					.show();
																		} else {

																			if (Integer
																					.parseInt(age1
																							.getText()
																							.toString()) <= 12) {
																				Toast.makeText(
																						PackageTouristDetails.this,
																						"Please enter valid age for Adult.",
																						Toast.LENGTH_LONG)
																						.show();
																			} else {
																				if (Utility
																						.checkInternetConnection(PackageTouristDetails.this)) {
																					new PostPackagedata()
																							.execute();
																				} else {
																					Utility.showAlertNoInternetService(PackageTouristDetails.this);
																				}
																			}
																		}

																	} else if (Package_Booking.no_of_people
																			.equalsIgnoreCase("2")) {

																		if (nameEdt1
																				.getText()
																				.toString()
																				.trim()
																				.length() == 0
																				|| nameEdt2
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age1.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age2.getText()
																						.toString()
																						.trim()
																						.length() == 0) {
																			Toast.makeText(
																					PackageTouristDetails.this,
																					"Please fill all the tourist details.",
																					Toast.LENGTH_LONG)
																					.show();
																		} else {

																			if (PackageListing.adultVal == 2) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {
																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 1) {
																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else if (Integer
																						.parseInt(age2
																								.getText()
																								.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}
																			}
																		}

																	} else if (Package_Booking.no_of_people
																			.equalsIgnoreCase("3")) {

																		/*
																		 * if(
																		 * nameEdt1
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0 ||
																		 * nameEdt2
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * nameEdt3
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age1
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age2
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age3
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0) {
																		 * Toast
																		 * .
																		 * makeText
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this,
																		 * "Please fill all the tourist details."
																		 * ,
																		 * Toast
																		 * .
																		 * LENGTH_LONG
																		 * )
																		 * .show
																		 * ();
																		 * }else
																		 * { if
																		 * (
																		 * utility
																		 * .
																		 * IsNetConnected
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this)
																		 * ) {
																		 * new
																		 * PostPackagedata
																		 * (
																		 * ).execute
																		 * (); }
																		 * else
																		 * {
																		 * utility
																		 * .
																		 * showAlertNoInternetService
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this)
																		 * ; } }
																		 */
																		if (nameEdt1
																				.getText()
																				.toString()
																				.trim()
																				.length() == 0
																				|| nameEdt2
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| nameEdt3
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age1.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age2.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age3.getText()
																						.toString()
																						.trim()
																						.length() == 0) {
																			Toast.makeText(
																					PackageTouristDetails.this,
																					"Please fill all the tourist details.",
																					Toast.LENGTH_LONG)
																					.show();
																		} else {

																			if (PackageListing.adultVal == 3) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {
																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 2) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else if (Integer
																						.parseInt(age3
																								.getText()
																								.toString()) > 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else {
																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 1) {
																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else if (Integer
																						.parseInt(age2
																								.getText()
																								.toString()) > 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}
																			}
																		}

																	} else if (Package_Booking.no_of_people
																			.equalsIgnoreCase("4")) {

																		/*
																		 * if(
																		 * nameEdt1
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0 ||
																		 * nameEdt2
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * nameEdt3
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * nameEdt4
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age1
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age2
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age3
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age4
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0) {
																		 * Toast
																		 * .
																		 * makeText
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this,
																		 * "Please fill all the tourist details."
																		 * ,
																		 * Toast
																		 * .
																		 * LENGTH_LONG
																		 * )
																		 * .show
																		 * ();
																		 * }else
																		 * { if
																		 * (
																		 * utility
																		 * .
																		 * IsNetConnected
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this)
																		 * ) {
																		 * new
																		 * PostPackagedata
																		 * (
																		 * ).execute
																		 * (); }
																		 * else
																		 * {
																		 * utility
																		 * .
																		 * showAlertNoInternetService
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this)
																		 * ; } }
																		 */
																		if (nameEdt1
																				.getText()
																				.toString()
																				.trim()
																				.length() == 0
																				|| nameEdt2
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| nameEdt3
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| nameEdt4
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age1.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age2.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age3.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age4.getText()
																						.toString()
																						.trim()
																						.length() == 0) {
																			Toast.makeText(
																					PackageTouristDetails.this,
																					"Please fill all the tourist details.",
																					Toast.LENGTH_LONG)
																					.show();
																		} else {

																			if (PackageListing.adultVal == 4) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {
																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 3) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else if (Integer
																						.parseInt(age4
																								.getText()
																								.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 2) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else if (Integer
																						.parseInt(age3
																								.getText()
																								.toString()) > 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) > 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else {
																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 1) {
																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else if (Integer
																						.parseInt(age2
																								.getText()
																								.toString()) > 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) > 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}
																			}
																		}

																	} else if (Package_Booking.no_of_people
																			.equalsIgnoreCase("5")) {

																		/*
																		 * if(
																		 * nameEdt1
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0 ||
																		 * nameEdt2
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * nameEdt3
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * nameEdt4
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * nameEdt5
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age1
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age2
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age3
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age4
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age5
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0) {
																		 * Toast
																		 * .
																		 * makeText
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this,
																		 * "Please fill all the tourist details."
																		 * ,
																		 * Toast
																		 * .
																		 * LENGTH_LONG
																		 * )
																		 * .show
																		 * ();
																		 * }else
																		 * { if
																		 * (
																		 * utility
																		 * .
																		 * IsNetConnected
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this)
																		 * ) {
																		 * new
																		 * PostPackagedata
																		 * (
																		 * ).execute
																		 * (); }
																		 * else
																		 * {
																		 * utility
																		 * .
																		 * showAlertNoInternetService
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this)
																		 * ; } }
																		 */

																		if (nameEdt1
																				.getText()
																				.toString()
																				.trim()
																				.length() == 0
																				|| nameEdt2
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| nameEdt3
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| nameEdt4
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| nameEdt5
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age1.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age2.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age3.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age4.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age5.getText()
																						.toString()
																						.trim()
																						.length() == 0) {
																			Toast.makeText(
																					PackageTouristDetails.this,
																					"Please fill all the tourist details.",
																					Toast.LENGTH_LONG)
																					.show();
																		} else {

																			if (PackageListing.adultVal == 5) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age5
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {
																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 4) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else if (Integer
																						.parseInt(age5
																								.getText()
																								.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 3) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else if (Integer
																						.parseInt(age4
																								.getText()
																								.toString()) > 12
																						|| Integer
																								.parseInt(age5
																										.getText()
																										.toString()) > 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else {
																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 2) {
																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else if (Integer
																						.parseInt(age3
																								.getText()
																								.toString()) > 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) > 12
																						|| Integer
																								.parseInt(age5
																										.getText()
																										.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}
																			} else if (PackageListing.adultVal == 1) {
																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else if (Integer
																						.parseInt(age2
																								.getText()
																								.toString()) > 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) > 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) > 12
																						|| Integer
																								.parseInt(age5
																										.getText()
																										.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}
																			}
																		}

																	} else if (Package_Booking.no_of_people
																			.equalsIgnoreCase("6")) {

																		/*
																		 * if(
																		 * nameEdt1
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0 ||
																		 * nameEdt2
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * nameEdt3
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * nameEdt4
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * nameEdt5
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * nameEdt6
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0 ||
																		 * age1
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age2
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age3
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age4
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age5
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0||
																		 * age6
																		 * .
																		 * getText
																		 * ().
																		 * toString
																		 * (
																		 * ).trim
																		 * (
																		 * ).length
																		 * () ==
																		 * 0) {
																		 * Toast
																		 * .
																		 * makeText
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this,
																		 * "Please fill all the tourist details."
																		 * ,
																		 * Toast
																		 * .
																		 * LENGTH_LONG
																		 * )
																		 * .show
																		 * ();
																		 * }else
																		 * { if
																		 * (
																		 * utility
																		 * .
																		 * IsNetConnected
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this)
																		 * ) {
																		 * new
																		 * PostPackagedata
																		 * (
																		 * ).execute
																		 * (); }
																		 * else
																		 * {
																		 * utility
																		 * .
																		 * showAlertNoInternetService
																		 * (
																		 * PackageTouristDetails
																		 * .
																		 * this)
																		 * ; } }
																		 */
																		if (nameEdt1
																				.getText()
																				.toString()
																				.trim()
																				.length() == 0
																				|| nameEdt2
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| nameEdt3
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| nameEdt4
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| nameEdt5
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| nameEdt6
																						.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age1.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age2.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age3.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age4.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age5.getText()
																						.toString()
																						.trim()
																						.length() == 0
																				|| age6.getText()
																						.toString()
																						.trim()
																						.length() == 0) {
																			Toast.makeText(
																					PackageTouristDetails.this,
																					"Please fill all the tourist details.",
																					Toast.LENGTH_LONG)
																					.show();
																		} else {

																			if (PackageListing.adultVal == 6) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age5
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age6
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {
																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 5) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age5
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else if (Integer
																						.parseInt(age6
																								.getText()
																								.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 4) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) <= 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else if (Integer
																						.parseInt(age5
																								.getText()
																								.toString()) > 12
																						|| Integer
																								.parseInt(age6
																										.getText()
																										.toString()) > 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else {
																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}

																			} else if (PackageListing.adultVal == 3) {
																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) <= 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else if (Integer
																						.parseInt(age4
																								.getText()
																								.toString()) > 12
																						|| Integer
																								.parseInt(age5
																										.getText()
																										.toString()) > 12
																						|| Integer
																								.parseInt(age6
																										.getText()
																										.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}
																			} else if (PackageListing.adultVal == 2) {
																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12
																						|| Integer
																								.parseInt(age2
																										.getText()
																										.toString()) <= 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else if (Integer
																						.parseInt(age3
																								.getText()
																								.toString()) > 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) > 12
																						|| Integer
																								.parseInt(age5
																										.getText()
																										.toString()) > 12
																						|| Integer
																								.parseInt(age6
																										.getText()
																										.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata()
																								.execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}
																			} else if (PackageListing.adultVal == 1) {

																				if (Integer
																						.parseInt(age1
																								.getText()
																								.toString()) <= 12) {

																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for adults.",
																							Toast.LENGTH_LONG)
																							.show();

																				} else if (Integer
																						.parseInt(age2
																								.getText()
																								.toString()) > 12
																						|| Integer
																								.parseInt(age3
																										.getText()
																										.toString()) > 12
																						|| Integer
																								.parseInt(age4
																										.getText()
																										.toString()) > 12
																						|| Integer
																								.parseInt(age5
																										.getText()
																										.toString()) > 12
																						|| Integer
																								.parseInt(age6
																										.getText()
																										.toString()) > 12) {
																					Toast.makeText(
																							PackageTouristDetails.this,
																							"Please enter valid age for Child.",
																							Toast.LENGTH_LONG)
																							.show();
																				} else {

																					if (Utility
																							.checkInternetConnection(PackageTouristDetails.this)) {
																						new PostPackagedata().execute();
																					} else {
																						Utility.showAlertNoInternetService(PackageTouristDetails.this);
																					}
																				}
																			}
																		}
																	}
																	// startActivity(new
																	// Intent(PackageTouristDetails.this,
																	// PaymentScreen.class));
																} else {
																	Toast.makeText(
																			PackageTouristDetails.this,
																			"Please enter valid email",
																			Toast.LENGTH_LONG)
																			.show();
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}

				} else {
					Utility.showAlertNoInternetService(PackageTouristDetails.this);
				}

			}
		});

		nameEdt.setText(SplashScreen.pref.getString(
				SplashScreen.Key_GET_USER_NAME, ""));
		nameEdt1.setText(SplashScreen.pref.getString(
				SplashScreen.Key_GET_USER_NAME, ""));
		phoneEdt.setText(SplashScreen.pref.getString(
				SplashScreen.Key_GET_USER_PHONE_NO, ""));

		if (Package_Booking.no_of_people.equalsIgnoreCase("1")) {
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.GONE);
			layout3.setVisibility(View.GONE);
			layout4.setVisibility(View.GONE);
			layout5.setVisibility(View.GONE);
			layout6.setVisibility(View.GONE);
		} else if (Package_Booking.no_of_people.equalsIgnoreCase("2")) {
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.VISIBLE);
			layout3.setVisibility(View.GONE);
			layout4.setVisibility(View.GONE);
			layout5.setVisibility(View.GONE);
			layout6.setVisibility(View.GONE);
		} else if (Package_Booking.no_of_people.equalsIgnoreCase("3")) {
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.VISIBLE);
			layout3.setVisibility(View.VISIBLE);
			layout4.setVisibility(View.GONE);
			layout5.setVisibility(View.GONE);
			layout6.setVisibility(View.GONE);
		} else if (Package_Booking.no_of_people.equalsIgnoreCase("4")) {
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.VISIBLE);
			layout3.setVisibility(View.VISIBLE);
			layout4.setVisibility(View.VISIBLE);
			layout5.setVisibility(View.GONE);
			layout6.setVisibility(View.GONE);
		} else if (Package_Booking.no_of_people.equalsIgnoreCase("5")) {
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.VISIBLE);
			layout3.setVisibility(View.VISIBLE);
			layout4.setVisibility(View.VISIBLE);
			layout5.setVisibility(View.VISIBLE);
			layout6.setVisibility(View.GONE);
		} else if (Package_Booking.no_of_people.equalsIgnoreCase("6")) {
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.VISIBLE);
			layout3.setVisibility(View.VISIBLE);
			layout4.setVisibility(View.VISIBLE);
			layout5.setVisibility(View.VISIBLE);
			layout6.setVisibility(View.VISIBLE);
		}
	}

	private class PostPackagedata extends AsyncTask<String, String, String> {

		ProgressDialog myProgressDialog;
		String re = "";

		protected void onPreExecute() {
			super.onPreExecute();
			myProgressDialog = new ProgressDialog(PackageTouristDetails.this);
			myProgressDialog.setMessage("Loading...");
			myProgressDialog.setCancelable(false);
			myProgressDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... arg0) {
			// Creating service handler class instance

			try {

				HttpClient client = new DefaultHttpClient();
				// HttpPost post = new HttpPost(Home.url);
				HttpPost post = new HttpPost(Constants.Base_url1
						+ "postpackagetempdata.jsp");
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("adult", String
						.valueOf(PackageListing.adultVal)));
				pairs.add(new BasicNameValuePair("child", String
						.valueOf(PackageListing.childVal)));
				pairs.add(new BasicNameValuePair("jdate",
						Package_Booking.str_date));
				pairs.add(new BasicNameValuePair("serviceid",
						PackageListing.service_Code));
				pairs.add(new BasicNameValuePair("pkgtypeid",
						Package_Booking.packTypeCode));
				pairs.add(new BasicNameValuePair("depttime",
						SeatSelection.departureTime));
				pairs.add(new BasicNameValuePair("totfare",
						Package_Booking.str_total_price.replace(",", "")));

				pairs.add(new BasicNameValuePair("title", StrTitle1));
				pairs.add(new BasicNameValuePair("name", strAdultName));
				pairs.add(new BasicNameValuePair("sex", StrAdultGender));
				pairs.add(new BasicNameValuePair("age", strAdultAge));
				pairs.add(new BasicNameValuePair("seats", strAdultSeatNo));

				System.out.println("strChildTitle " + strChildTitle);
				if (!TextUtils.isEmpty(strChildTitle)) {
					pairs.add(new BasicNameValuePair("ctitle", strChildTitle));
					pairs.add(new BasicNameValuePair("cname", strChildName));
					
					pairs.add(new BasicNameValuePair("csex", strChildGender));
					pairs.add(new BasicNameValuePair("cage", strChildAge));
					pairs.add(new BasicNameValuePair("cseats", strChildSeatNo));
					pairs.add(new BasicNameValuePair("camt", strChildTarrif));
					pairs.add(new BasicNameValuePair("cfare",Package_Booking.childTariff));
				}
				else
				{
					pairs.add(new BasicNameValuePair("ctitle", "0"));
					pairs.add(new BasicNameValuePair("cname", "0"));
					pairs.add(new BasicNameValuePair("csex", "0"));
					pairs.add(new BasicNameValuePair("cage", "0"));
					pairs.add(new BasicNameValuePair("cseats", "0"));
					pairs.add(new BasicNameValuePair("camt", "0"));
					pairs.add(new BasicNameValuePair("cfare","0"));
				}

				pairs.add(new BasicNameValuePair("atitle", StrTitle));
				pairs.add(new BasicNameValuePair("afirstnm", nameEdt.getText()
						.toString()));
				pairs.add(new BasicNameValuePair("alastnm", lastNameEdt
						.getText().toString()));
				pairs.add(new BasicNameValuePair("house", homeNoEdt.getText()
						.toString()));
				pairs.add(new BasicNameValuePair("street", streetEdt.getText()
						.toString()));
				pairs.add(new BasicNameValuePair("city", cityEdt.getText()
						.toString()));
				pairs.add(new BasicNameValuePair("state", stateEdt.getText()
						.toString()));
				pairs.add(new BasicNameValuePair("country", "205"));
				pairs.add(new BasicNameValuePair("pin", pinCodeEdt.getText()
						.toString()));
				pairs.add(new BasicNameValuePair("email", emailEdt.getText()
						.toString()));
				pairs.add(new BasicNameValuePair("mobile", phoneEdt.getText()
						.toString()));
				
				
				
//				pairs.add(new BasicNameValuePair("boarding", "12"));
//				pairs.add(new BasicNameValuePair("alightng",
//						PackageListing.city_Code));
				
				pairs.add(new BasicNameValuePair("boarding", SeatSelection.StrBoardingPointCode));
				pairs.add(new BasicNameValuePair("alightng",
						SeatSelection.StrBoardingReturnPointCode));
				
				
//				pairs.add(new BasicNameValuePair("boarding", SeatSelection.StrBoardingPoint));
//				pairs.add(new BasicNameValuePair("alightng",SeatSelection.StrBoardingReturnPoint));
				
				pairs.add(new BasicNameValuePair("aamt", strAdultTarrif));
				pairs.add(new BasicNameValuePair("afare",
						Package_Booking.adultTariff));
				
				pairs.add(new BasicNameValuePair("seatno",
						Package_Booking.seatNos1));
				pairs.add(new BasicNameValuePair("returnseatno",
						Package_Booking.seatNos2));

				pairs.add(new BasicNameValuePair("boardngtm",
						SeatSelection.departureTime));
				pairs.add(new BasicNameValuePair("alighttm",
						SeatSelection.departureReturnTime));
				pairs.add(new BasicNameValuePair("totamt",
						Package_Booking.str_total_price.replace(",", "")));
				pairs.add(new BasicNameValuePair("ifare",
						Package_Booking.infantTarrif));
				if (PackageListing.personsVal == 1) {
					pairs.add(new BasicNameValuePair("singleextrafare",
							Package_Booking.singleTariff));
				} else {
					pairs.add(new BasicNameValuePair("singleextrafare", "0.00"));
				}
				pairs.add(new BasicNameValuePair("sname",
						PackageListing.pack_title.replace(",", " ")));
				
				System.out.println("params1" + pairs);

				post.setEntity(new UrlEncodedFormEntity(pairs));
				response = client.execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					re = EntityUtils.toString(entity, HTTP.UTF_8).trim();
					if (re == null || re.equalsIgnoreCase("null")) {
						re = "";
					}
				}
				System.out.println("response" + re);

			} catch (Exception e) {
				Log.e("exception in doinbackground", "" + e.getMessage());
			}
			return re;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println("results" + result);
			try {
				if (myProgressDialog.isShowing())
					myProgressDialog.dismiss();
				System.out.println("results" + result);
				try {
					JSONArray json = new JSONArray(result);

					if (result.contains("response")) {
						JSONObject e = json.getJSONObject(0);

						StrPackID = e.getString("response");
						System.out.println("StrID" + StrPackID);

						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								PackageTouristDetails.this);
						altDialog
								.setMessage("Please preserve this tracking ID "
										+ StrPackID); // here
						altDialog.setNeutralButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										Constants.ebs_flg=1;
										callEbsKit();
									}
								});
						altDialog.show();
					} else if (result.contains("error")) {
						JSONObject e = json.getJSONObject(0);

						String strEr = e.getString("error");
						Toast.makeText(getApplicationContext(),
								"error" + strEr, Toast.LENGTH_SHORT).show();
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			} catch (Exception e) {

			}
		}
	}
	
	
	private void callEbsKit() {
		/**
		 * Set Parameters Before Initializing the EBS Gateway, All mandatory
		 * values must be provided
		 */

		/** Payment Amount Details */
		// Total Amount

		PaymentRequest.getInstance().setTransactionAmount(
				String.format("%.2f", Double.valueOf(Package_Booking.str_total_price.replace(",", ""))));

		/** Mandatory */

		PaymentRequest.getInstance().setAccountId(ACC_ID);
		PaymentRequest.getInstance().setSecureKey(SECRET_KEY);

		// Reference No
		PaymentRequest.getInstance().setReferenceNo(PackageListing.service_Code);
		/** Mandatory */

		// Email Id
		PaymentRequest.getInstance().setBillingEmail(emailEdt.getText().toString());
		/** Mandatory */

		/**
		 * Set failure id as 1 to display amount and reference number on failed
		 * transaction page. set 0 to disable
		 */
		PaymentRequest.getInstance().setFailureid("0");
		/** Mandatory */

		// Currency
		PaymentRequest.getInstance().setCurrency("INR");
		/** Mandatory */

		/** Optional */
		// Your Reference No or Order Id for this transaction
		PaymentRequest.getInstance().setTransactionDescription(
				"Test Transaction");

		/** Billing Details */
		PaymentRequest.getInstance().setBillingName(nameEdt.getText().toString());
		/** Optional */
		PaymentRequest.getInstance().setBillingAddress(streetEdt.getText().toString());
		/** Optional */
		PaymentRequest.getInstance().setBillingCity(cityEdt.getText().toString());
		/** Optional */
		PaymentRequest.getInstance().setBillingPostalCode(pinCodeEdt.getText().toString());
		/** Optional */
		PaymentRequest.getInstance().setBillingState("Telangana");
		/** Optional */
			PaymentRequest.getInstance().setBillingCountry("IND");
		// ** Optional */
		PaymentRequest.getInstance().setBillingPhone(phoneEdt.getText().toString());
		/** Optional */
		/** set custom message for failed transaction */

		PaymentRequest.getInstance().setFailuremessage(
				getResources().getString(R.string.payment_failure_message));
		/** Optional */
		/** Shipping Details */
		PaymentRequest.getInstance().setShippingName(nameEdt.getText().toString());
		/** Optional */
		PaymentRequest.getInstance().setShippingAddress(StrPackID);
		/** Optional */
		PaymentRequest.getInstance().setShippingCity(cityEdt.getText().toString());
		/** Optional */
		PaymentRequest.getInstance().setShippingPostalCode("500029");
		/** Optional */
		PaymentRequest.getInstance().setShippingState("Telangana");
		/** Optional */
		PaymentRequest.getInstance().setShippingCountry("IND");
		/** Optional */
		PaymentRequest.getInstance().setShippingEmail("test@testmail.com");
		/** Optional */
		PaymentRequest.getInstance().setShippingPhone("01234567890");
		/** Optional */
		/* enable log by setting 1 and disable by setting 0 */
		PaymentRequest.getInstance().setLogEnabled("1");  

		/**
		 * Initialise parameters for dyanmic values sending from merchant custom
		 * values from merchant
		 */

		custom_post_parameters = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashpostvalues = new HashMap<String, String>();
		hashpostvalues.put("account_details", "saving");
		hashpostvalues.put("merchant_type", "gold");
		custom_post_parameters.add(hashpostvalues);

		PaymentRequest.getInstance()
				.setCustomPostValues(custom_post_parameters);
		/** Optional-Set dyanamic values */

		// PaymentRequest.getInstance().setFailuremessage(getResources().getString(R.string.payment_failure_message));

		EBSPayment.getInstance().init(PackageTouristDetails.this, ACC_ID, SECRET_KEY,
				Mode.ENV_TEST, Encryption.ALGORITHM_MD5, HOST_NAME);   
		
		
		
		
//		EBSPayment.getInstance().init(context, accId, secretkey, environment, algorithm, host_name);
	}

//	private void callEbsKit() {
//		/**
//		 * Set Parameters Before Initializing the EBS Gateway, All mandatory
//		 * values must be provided
//		 */
//		/** Payment Amount Details */
//		
//		PaymentRequest.getInstance().setTransactionAmount(
//				String.format("%.2f", Double.valueOf(Package_Booking.str_total_price.replace(",", ""))));
////		 PaymentRequest.getInstance().setTransactionAmount(Double.valueOf(Package_Booking.str_total_price.replace(",", "")));
//		 PaymentRequest.getInstance().setShippingName(nameEdt.getText().toString());
//		 PaymentRequest.getInstance().setShippingCity(cityEdt.getText().toString());
//		/** Mandatory */
//		 PaymentRequest.getInstance().setBillingName(nameEdt.getText().toString());
//		 /** Mandatory */
//		 PaymentRequest.getInstance().setBillingAddress(streetEdt.getText().toString());
//		 /** Mandatory */
//		 PaymentRequest.getInstance().setBillingCity(cityEdt.getText().toString());
//		 /** Mandatory */
//		 PaymentRequest.getInstance().setBillingState("Telangana");
//		 /** Mandatory */
//		 PaymentRequest.getInstance().setBillingPostalCode(pinCodeEdt.getText().toString());
//		 /** Mandatory */
//		 PaymentRequest.getInstance().setBillingCountry("India");
//		 /** Mandatory */
//		PaymentRequest.getInstance().setReferenceNo(PackageListing.service_Code);
//		/** Mandatory */
//		 PaymentRequest.getInstance().setBillingEmail(emailEdt.getText().toString());
//		 /** Mandatory */
//		 PaymentRequest.getInstance().setBillingPhone(
//		 phoneEdt.getText().toString());
//		 PaymentRequest.getInstance().setShippingAddress(StrPackID);
//		/** Mandatory */
//		 PaymentRequest.getInstance().setTransactionDescription("Test Transaction");
//		 /** Mandatory */
//		 // "http://202.65.147.152:8080/tgtdc/etg/pgresponse.jsp");
//		 PaymentRequest.getInstance().setReturnUrl(Constants.Base_url1+"tourpgresponse.jsp");
//		 /** Mandatory */
////		 PaymentRequest.getInstance().setReturnUrl(RETURN_URL);/**Mandatory*/
//		 PaymentRequest.getInstance().setGroupId(GROUP_ID);
//		 /** Mandatory */
//		 /** Billing Details */
//		
////		 /** Optional */
////		 PaymentRequest.getInstance().setBillingCountry("IND");
//		 
//		 /** Optional */
//		 /** Shipping Details */
//		 // PaymentRequest.getInstance().setShippingName("Ganesh");
//		 // /** Optional */
//		 //
////		 PaymentRequest.getInstance().setShippingAddress("North Mada Street");
//		 // /** Optional */
//		 // PaymentRequest.getInstance().setShippingCity("Chennai");
//		 // /** Optional */
//		 // PaymentRequest.getInstance().setShippingPostalCode("600019");
//		 // /** Optional */
//		 // PaymentRequest.getInstance().setShippingState("Tamilnadu");
//		 // /** Optional */
//		 // PaymentRequest.getInstance().setShippingCountry("IND");
//		 // /** Optional */
//		 // PaymentRequest.getInstance().setShippingEmail("ganesh@test.com");
//		 // /** Optional */
//		 // PaymentRequest.getInstance().setShippingPhone("04424578545");
//		 /** Optional */
//		 // PaymentRequest.getInstance().setPaymentMode(1);/**Optional*/
//
//		/** Initializing the EBS Gateway */
//			EBSPayment.getInstance().init(PackageTouristDetails.this, ACC_ID,
//					SECRET_KEY,  Mode.ENV_TEST,
//					Encryption.ALGORITHM_MD5,HOST_NAME);
//		System.out.println("payment details"
//				+ EBSPayment.getInstance().ebspayment);
//		PaymentRequest.getInstance();
//	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch(group.getId()) {
        case R.id.radioGroup_2:
        	RadioButton radiotitleButton1 = (RadioButton) findViewById(checkedId);
			String str1 = radiotitleButton1.getText().toString();
			if (str1.contains("Mr.")) {
				user1_maleRdBtn.setChecked(true);
			} else {
				user1_femaleRdBtn.setChecked(true);
			}
          break;
        case R.id.radioGroup_3:
        	RadioButton radiotitleButton2 = (RadioButton) findViewById(checkedId);
			String str2 = radiotitleButton2.getText().toString();
			if (str2.contains("Mr.")) {
				user2_maleRdBtn.setChecked(true);
			} else {
				user2_femaleRdBtn.setChecked(true);
			}
          break;
          case R.id.radioGroup_4:
        	RadioButton radiotitleButton3 = (RadioButton) findViewById(checkedId);
			String str3 = radiotitleButton3.getText().toString();
			if (str3.contains("Mr.")) {
				user3_maleRdBtn.setChecked(true);
			} else {
				user3_femaleRdBtn.setChecked(true);
			}
          break;
          case R.id.radioGroup_5:
        	RadioButton radiotitleButton4 = (RadioButton) findViewById(checkedId);
			String str4 = radiotitleButton4.getText().toString();
			if (str4.contains("Mr.")) {
				user4_maleRdBtn.setChecked(true);
			} else {
				user4_femaleRdBtn.setChecked(true);
			}
          break;
          case R.id.radioGroup_6:
        	RadioButton radiotitleButton5 = (RadioButton) findViewById(checkedId);
			String str5 = radiotitleButton5.getText().toString();
			if (str5.contains("Mr.")) {
				user5_maleRdBtn.setChecked(true);
			} else {
				user5_femaleRdBtn.setChecked(true);
			}
          break;
          case R.id.radioGroup_7:
        	RadioButton radiotitleButton6 = (RadioButton) findViewById(checkedId);
			String str6 = radiotitleButton6.getText().toString();
			if (str6.contains("Mr.")) {
				user6_maleRdBtn.setChecked(true);
			} else {
				user6_femaleRdBtn.setChecked(true);
			}
          break;
          case R.id.radioGroup1:
        	RadioButton radiogenderButton1 = (RadioButton) findViewById(checkedId);
			String strG1 = radiogenderButton1.getText().toString();
			if (strG1.contains("Male")) {
				mrRdBtn1.setChecked(true);
			} else {
				mrsRdBtn1.setChecked(true);
			}
          break;
          case R.id.radioGroup2:
        	  RadioButton radiogenderButton2 = (RadioButton) findViewById(checkedId);
  			String strG2 = radiogenderButton2.getText().toString();
  			if (strG2.contains("Male")) {
  				mrRdBtn2.setChecked(true);
  			} else {
  				mrsRdBtn2.setChecked(true);
  			}
          break;
          case R.id.radioGroup3:
        	  RadioButton radiogenderButton3 = (RadioButton) findViewById(checkedId);
  			String strG3 = radiogenderButton3.getText().toString();
  			if (strG3.contains("Male")) {
  				mrRdBtn3.setChecked(true);
  			} else {
  				mrsRdBtn3.setChecked(true);
  			}
          break;
          case R.id.radioGroup4:
        	  RadioButton radiogenderButton4 = (RadioButton) findViewById(checkedId);
  			String strG4 = radiogenderButton4.getText().toString();
  			if (strG4.contains("Male")) {
  				mrRdBtn4.setChecked(true);
  			} else {
  				mrsRdBtn4.setChecked(true);
  			}
          break;
          case R.id.radioGroup5:
        	  RadioButton radiogenderButton5 = (RadioButton) findViewById(checkedId);
  			String strG5 = radiogenderButton5.getText().toString();
  			if (strG5.contains("Male")) {
  				mrRdBtn5.setChecked(true);
  			} else {
  				mrsRdBtn5.setChecked(true);
  			}
          break;
          case R.id.radioGroup6:
        	  RadioButton radiogenderButton6 = (RadioButton) findViewById(checkedId);
  			String strG6 = radiogenderButton6.getText().toString();
  			if (strG6.contains("Male")) {
  				mrRdBtn6.setChecked(true);
  			} else {
  				mrsRdBtn6.setChecked(true);
  			}
          break;
      }
	}
}
