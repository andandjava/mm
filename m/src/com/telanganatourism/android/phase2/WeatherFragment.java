package com.telanganatourism.android.phase2;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.telanganatourism.android.phase2.SubFragmentActivity.SettingsTaskTask;
import com.telanganatourism.android.phase2.util.Utility;

public class WeatherFragment extends Fragment {

	public WeatherFragment() {
	}

	String str_mesur = "c";
	int year;
	TextView ctTxt, fhTxt, cityTxt;
	TextView txt_min, txt_max, txt_humidity, day1, day2, low2, day3, low3,
			day4, low4, day5, low5, day6, low6, day7, low7;
	TextView tvmin, tvmax;
	ImageView txt_we, weather_icon1, weather_icon2, weather_icon3,
			weather_icon4, weather_icon5, weather_icon6;

//	Utility utility;
	ArrayList<String> txt = new ArrayList<String>();
	View rootView;

	// int fontChaning = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (Constants.selectLanguage.equalsIgnoreCase("3")) {
			rootView = inflater.inflate(R.layout.arabic_weather, container,
					false);
		} else {
			rootView = inflater.inflate(R.layout.weather, container, false);
		}
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// getActivity().getActionBar().setTitle("Weather");
		// getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActivity().getActionBar().setBackgroundDrawable(
		// new ColorDrawable(Color.parseColor("#F2F2F2")));
		// MainActivity.getRightMenuList(getActivity(),MainActivity.stay);

//		utility = new Utility(getActivity());

		Calendar now = Calendar.getInstance();
		//
		year = now.get(Calendar.YEAR);

		MainActivity.txt_logo.setVisibility(View.GONE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setText(getResources().getString(
				R.string.weather_sidemenu));

		MainActivity.txt_title.setTypeface(Constants.ProximaNova_Regular);

		MainActivity.txt_title.setTextColor(Color.parseColor("#000000"));

		MainActivity.txt_filter.setVisibility(View.GONE);

		MainActivity.txt_location.setVisibility(View.GONE);

		MainActivity.txt_search.setVisibility(View.GONE);

		MainActivity.txt_Edit.setVisibility(View.GONE);

		MainActivity.btn_menu.setBackgroundResource(R.drawable.menu_green);

		MainActivity.headerLayout.setBackgroundColor(Color
				.parseColor("#ffffff"));

		cityTxt = (TextView) rootView.findViewById(R.id.cityTxt);

		cityTxt.setTypeface(Constants.ProximaNova_Regular);

		// if(Constants.locationId.equalsIgnoreCase("1")){
		// cityTxt.setText("City : "+getResources().getString(R.string.location_hyderabad));
		// }else if(Constants.locationId.equalsIgnoreCase("2")){
		// cityTxt.setText("City : "+getResources().getString(R.string.location_warangal));
		// }else if(Constants.locationId.equalsIgnoreCase("3")){
		// cityTxt.setText("City : "+getResources().getString(R.string.location_karimnagar));
		// }else if(Constants.locationId.equalsIgnoreCase("4")){
		// cityTxt.setText("City : "+getResources().getString(R.string.location_khammam));
		// }else if(Constants.locationId.equalsIgnoreCase("5")){
		// cityTxt.setText("City : "+getResources().getString(R.string.location_medak));
		// }else if(Constants.locationId.equalsIgnoreCase("6")){
		// cityTxt.setText("City : "+getResources().getString(R.string.location_nalgonda));
		// }else if(Constants.locationId.equalsIgnoreCase("7")){
		// cityTxt.setText("City : "+getResources().getString(R.string.location_nizamabad));
		// }else if(Constants.locationId.equalsIgnoreCase("8")){
		// cityTxt.setText("City : "+getResources().getString(R.string.location_adilabad));
		// }else if(Constants.locationId.equalsIgnoreCase("9")){
		// cityTxt.setText("City : "+getResources().getString(R.string.location_mbnr));
		// }else if(Constants.locationId.equalsIgnoreCase("10")){
		// cityTxt.setText("City : "+getResources().getString(R.string.location_rangareddy));
		// }

		Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

		List<Address> addresses = null;

		try {
			addresses = geocoder.getFromLocation(Constants.latitude,
					Constants.longitude, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);

			if (address.toString().equalsIgnoreCase("Hyderabad")) {
				Constants.cityId = "90883135";
				cityTxt.setText("City : "
						+ getResources().getString(R.string.location_hyderabad));
			} else if (address.toString().equalsIgnoreCase("Khammam")) {
				Constants.cityId = "2295229";
				cityTxt.setText("City : "
						+ getResources().getString(R.string.location_khammam));
			} else if (address.toString().equalsIgnoreCase("Mahabubnagar")) {
				Constants.cityId = "2281586";
				cityTxt.setText("City : "
						+ getResources().getString(R.string.location_mbnr));
			} else if (address.toString().equalsIgnoreCase("Nalgonda")) {
				Constants.cityId = "90886279";
				cityTxt.setText("City : "
						+ getResources().getString(R.string.location_nalgonda));
			} else if (address.toString().equalsIgnoreCase("Warangal")) {
				Constants.cityId = "90882603";
				cityTxt.setText("City : "
						+ getResources().getString(R.string.location_warangal));
			} else if (address.toString().equalsIgnoreCase("Adilabad")) {
				Constants.cityId = "90882225";
				cityTxt.setText("City : "
						+ getResources().getString(R.string.location_adilabad));
			} else if (address.toString().equalsIgnoreCase("Nizamabad")) {
				Constants.cityId = "90887467";
				cityTxt.setText("City : "
						+ getResources().getString(R.string.location_nizamabad));
			} else if (address.toString().equalsIgnoreCase("Karimnagar")) {
				Constants.cityId = "2295185";
				cityTxt.setText("City : "
						+ getResources()
								.getString(R.string.location_karimnagar));
			} else if (address.toString().equalsIgnoreCase("Medak")) {
				Constants.cityId = "90882271";
				cityTxt.setText("City : "
						+ getResources().getString(R.string.location_medak));
			} else if (address.toString().equalsIgnoreCase("Rangareddy")) {
				Constants.cityId = "90891709";
				cityTxt.setText("City : "
						+ getResources()
								.getString(R.string.location_rangareddy));
			} else {
				Constants.cityId = "90883135";
				cityTxt.setText("City : "
						+ getResources().getString(R.string.location_hyderabad));
			}

			// Toast.makeText(getActivity(), ""+address.getLocality(),
			// Toast.LENGTH_LONG).show();
			// Appconstants.ltDo.name = address.getThoroughfare();
			// Appconstants.strCityName = address.getLocality();
			//
			// Appconstants.LATTITUDE =String.valueOf(""+lat);
			// Appconstants.LANGITUDE=String.valueOf(""+lng);
		}

		txt_min = (TextView) rootView.findViewById(R.id.txt_min);
		txt_max = (TextView) rootView.findViewById(R.id.txt_max);
		txt_humidity = (TextView) rootView.findViewById(R.id.txt_humidity);

		day1 = (TextView) rootView.findViewById(R.id.day1);

		day2 = (TextView) rootView.findViewById(R.id.day2);
		low2 = (TextView) rootView.findViewById(R.id.low2);

		day3 = (TextView) rootView.findViewById(R.id.day3);
		low3 = (TextView) rootView.findViewById(R.id.low3);

		day4 = (TextView) rootView.findViewById(R.id.day4);
		low4 = (TextView) rootView.findViewById(R.id.low4);

		day5 = (TextView) rootView.findViewById(R.id.day5);
		low5 = (TextView) rootView.findViewById(R.id.low5);

		day6 = (TextView) rootView.findViewById(R.id.day6);
		low6 = (TextView) rootView.findViewById(R.id.low6);

		day7 = (TextView) rootView.findViewById(R.id.day7);
		low7 = (TextView) rootView.findViewById(R.id.low7);

		txt_we = (ImageView) rootView.findViewById(R.id.txt_we);
		weather_icon1 = (ImageView) rootView.findViewById(R.id.weather_icon1);
		weather_icon2 = (ImageView) rootView.findViewById(R.id.weather_icon2);
		weather_icon3 = (ImageView) rootView.findViewById(R.id.weather_icon3);
		weather_icon4 = (ImageView) rootView.findViewById(R.id.weather_icon4);
		weather_icon5 = (ImageView) rootView.findViewById(R.id.weather_icon5);
		weather_icon6 = (ImageView) rootView.findViewById(R.id.weather_icon6);

		ctTxt = (TextView) rootView.findViewById(R.id.cnTxt);
		fhTxt = (TextView) rootView.findViewById(R.id.fhTxt);

		txt_min.setTypeface(Constants.ProximaNova_Regular);
		txt_max.setTypeface(Constants.ProximaNova_Regular);
		txt_humidity.setTypeface(Constants.ProximaNova_Regular);
		day1.setTypeface(Constants.ProximaNova_Regular);
		day2.setTypeface(Constants.ProximaNova_Regular);
		low2.setTypeface(Constants.ProximaNova_Regular);
		day3.setTypeface(Constants.ProximaNova_Regular);
		low3.setTypeface(Constants.ProximaNova_Regular);
		day4.setTypeface(Constants.ProximaNova_Regular);
		low4.setTypeface(Constants.ProximaNova_Regular);
		day5.setTypeface(Constants.ProximaNova_Regular);
		low5.setTypeface(Constants.ProximaNova_Regular);
		day6.setTypeface(Constants.ProximaNova_Regular);
		low6.setTypeface(Constants.ProximaNova_Regular);
		day7.setTypeface(Constants.ProximaNova_Regular);
		low7.setTypeface(Constants.ProximaNova_Regular);
		ctTxt.setTypeface(Constants.ProximaNova_Regular);
		fhTxt.setTypeface(Constants.ProximaNova_Regular);

		ctTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				fhTxt.setBackgroundResource(R.drawable.right_grey);
				fhTxt.setTextColor(Color.parseColor("#5A5C5C"));

				ctTxt.setBackgroundResource(R.drawable.left_white);
				ctTxt.setTextColor(Color.parseColor("#088A29"));
				txt.clear();
				str_mesur = "c";
				
				if (Utility.checkInternetConnection(getActivity())) {
					new WeatherAsyTask().execute();
				} else {
					Utility.showAlertNoInternetService(getActivity());
				}


				

			}
		});
		fhTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fhTxt.setBackgroundResource(R.drawable.right_white);
				fhTxt.setTextColor(Color.parseColor("#088A29"));

				ctTxt.setBackgroundResource(R.drawable.left_grey);
				ctTxt.setTextColor(Color.parseColor("#5A5C5C"));
				txt.clear();
				str_mesur = "f";
				if (Utility.checkInternetConnection(getActivity())) {
					new WeatherAsyTask().execute();
				} else {
					Utility.showAlertNoInternetService(getActivity());
				}
			}
		});

		txt.clear();

		if (Utility.checkInternetConnection(getActivity())) {
			new WeatherAsyTask().execute();
		} else {
			Utility.showAlertNoInternetService(getActivity());
		}

		// TextView tvSmallFont = (TextView)
		// rootView.findViewById(R.id.smallFontTxt);
		// TextView tvBigFont = (TextView)
		// rootView.findViewById(R.id.bigFontTxt);

		tvmin = (TextView) rootView.findViewById(R.id.high1);
		tvmax = (TextView) rootView.findViewById(R.id.max1);

		// tvSmallFont.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (fontChaning == 0) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// cityTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// ctTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		// fhTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		// tvmin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		// tvmax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		// txt_min.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		// txt_max.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		// txt_humidity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// day3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// day4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// day5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// day6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// day7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// low2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// low3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// low4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// low5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// low6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// low7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

		if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID, "")
				.equalsIgnoreCase("1")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			cityTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			ctTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
			fhTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
			tvmin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			tvmax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			txt_min.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
			txt_max.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
			txt_humidity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			day1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			day2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			day3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			day4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			day5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			day6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			day7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			low2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			low3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			low4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			low5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			low6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			low7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			cityTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			ctTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
			fhTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
			tvmin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
			tvmax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
			txt_min.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
			txt_max.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
			txt_humidity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			day1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			day2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			day3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			day4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			day5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			day6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			day7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			low2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			low3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			low4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			low5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			low6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			low7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
			cityTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
			ctTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
			fhTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
			tvmin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tvmax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			txt_min.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
			txt_max.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
			txt_humidity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			day1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			day2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			day3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			day4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			day5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			day6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			day7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			low2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			low3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			low4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			low5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			low6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			low7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		}
		// } else {
		// fontChaning--;
		// if (fontChaning > 0) {
		//
		// if (fontChaning == 1) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// cityTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// ctTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
		// fhTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
		// tvmin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// tvmax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// txt_min.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
		// txt_max.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
		// txt_humidity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// } else if (fontChaning == 2) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// cityTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// ctTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 29);
		// fhTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 29);
		// tvmin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// tvmax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// txt_min.setTextSize(TypedValue.COMPLEX_UNIT_SP, 29);
		// txt_max.setTextSize(TypedValue.COMPLEX_UNIT_SP, 29);
		// txt_humidity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// } else if (fontChaning == 3) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
		// cityTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
		// ctTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31);
		// fhTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31);
		// tvmin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// tvmax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// txt_min.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31);
		// txt_max.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31);
		// txt_humidity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// day1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// day2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
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
		// cityTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// ctTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
		// fhTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
		// tvmin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// tvmax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// txt_min.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
		// txt_max.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
		// txt_humidity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// day7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// low7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// } else if (fontChaning == 2) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// cityTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// ctTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 29);
		// fhTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 29);
		// tvmin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// tvmax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// txt_min.setTextSize(TypedValue.COMPLEX_UNIT_SP, 29);
		// txt_max.setTextSize(TypedValue.COMPLEX_UNIT_SP, 29);
		// txt_humidity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// day7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// low7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// } else if (fontChaning == 3) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
		// cityTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
		// ctTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31);
		// fhTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31);
		// tvmin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// tvmax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// txt_min.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31);
		// txt_max.setTextSize(TypedValue.COMPLEX_UNIT_SP, 31);
		// txt_humidity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// day1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// day2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// day7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// low7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
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

		return rootView;
	}

	private String QueryYahooWeather(String str_mesur) {

		String qResult = "";
		String queryString = "http://weather.yahooapis.com/forecastrss?w="
				+ Constants.cityId + "&u=" + str_mesur + "+&d=7";
       System.out.println("temperature"+queryString);
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(queryString);

		try {
			HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

			if (httpEntity != null) {
				InputStream inputStream = httpEntity.getContent();
				Reader in = new InputStreamReader(inputStream);
				BufferedReader bufferedreader = new BufferedReader(in);
				StringBuilder stringBuilder = new StringBuilder();

				String stringReadLine = null;

				while ((stringReadLine = bufferedreader.readLine()) != null) {
					stringBuilder.append(stringReadLine + "\n");
				}

				qResult = stringBuilder.toString();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
					.show();
		}

		return qResult;
	}

	private void parseWeather(Document srcDoc) {

		Node humidity = srcDoc.getElementsByTagName("yweather:atmosphere")
				.item(0);

		txt_humidity.setText("Humidity "
				+ humidity.getAttributes().getNamedItem("humidity")
						.getNodeValue().toString() + "%");

		Node dayNode0 = srcDoc.getElementsByTagName("yweather:forecast")
				.item(0);

		day1.setText(dayNode0.getAttributes().getNamedItem("day")
				.getNodeValue().toString()
				+ " - "
				+ dayNode0.getAttributes().getNamedItem("date").getNodeValue()
						.toString().replace("" + year, ""));

		txt_min.setText(dayNode0.getAttributes().getNamedItem("low")
				.getNodeValue().toString()
				+ " ° " + str_mesur);

		txt_max.setText(dayNode0.getAttributes().getNamedItem("high")
				.getNodeValue().toString()
				+ " ° " + str_mesur);

		txt.add(dayNode0.getAttributes().getNamedItem("text").getNodeValue()
				.toString());

		Node dayNode1 = srcDoc.getElementsByTagName("yweather:forecast")
				.item(1);

		txt.add(dayNode1.getAttributes().getNamedItem("text").getNodeValue()
				.toString());

		day2.setText(dayNode1.getAttributes().getNamedItem("day")
				.getNodeValue().toString()
				+ " - "
				+ dayNode1.getAttributes().getNamedItem("date").getNodeValue()
						.toString().replace("" + year, ""));

		low2.setText(dayNode1.getAttributes().getNamedItem("low")
				.getNodeValue().toString()
				+ " ° "
				+ str_mesur
				+ " - "
				+ dayNode1.getAttributes().getNamedItem("high").getNodeValue()
						.toString() + " ° " + str_mesur);

		Node dayNode2 = srcDoc.getElementsByTagName("yweather:forecast")
				.item(2);

		txt.add(dayNode2.getAttributes().getNamedItem("text").getNodeValue()
				.toString());

		day3.setText(dayNode2.getAttributes().getNamedItem("day")
				.getNodeValue().toString()
				+ " - "
				+ dayNode2.getAttributes().getNamedItem("date").getNodeValue()
						.toString().replace("" + year, ""));

		low3.setText(dayNode2.getAttributes().getNamedItem("low")
				.getNodeValue().toString()
				+ " ° "
				+ str_mesur
				+ " - "
				+ dayNode2.getAttributes().getNamedItem("high").getNodeValue()
						.toString() + " ° " + str_mesur);

		Node dayNode3 = srcDoc.getElementsByTagName("yweather:forecast")
				.item(3);
		txt.add(dayNode3.getAttributes().getNamedItem("text").getNodeValue()
				.toString());

		day4.setText(dayNode3.getAttributes().getNamedItem("day")
				.getNodeValue().toString()
				+ " - "
				+ dayNode3.getAttributes().getNamedItem("date").getNodeValue()
						.toString().replace("" + year, ""));

		low4.setText(dayNode3.getAttributes().getNamedItem("low")
				.getNodeValue().toString()
				+ " ° "
				+ str_mesur
				+ " - "
				+ dayNode3.getAttributes().getNamedItem("high").getNodeValue()
						.toString() + " ° " + str_mesur);

		Node dayNode4 = srcDoc.getElementsByTagName("yweather:forecast")
				.item(4);

		txt.add(dayNode4.getAttributes().getNamedItem("text").getNodeValue()
				.toString());

		day5.setText(dayNode4.getAttributes().getNamedItem("day")
				.getNodeValue().toString()
				+ " - "
				+ dayNode4.getAttributes().getNamedItem("date").getNodeValue()
						.toString().replace("" + year, ""));

		low5.setText(dayNode4.getAttributes().getNamedItem("low")
				.getNodeValue().toString()
				+ " ° "
				+ str_mesur
				+ " - "
				+ dayNode4.getAttributes().getNamedItem("high").getNodeValue()
						.toString() + " ° " + str_mesur);

		Node dayNode5 = srcDoc.getElementsByTagName("yweather:forecast")
				.item(5);

		txt.add(dayNode5.getAttributes().getNamedItem("text").getNodeValue()
				.toString());

		day6.setText(dayNode5.getAttributes().getNamedItem("day")
				.getNodeValue().toString()
				+ " - "
				+ dayNode5.getAttributes().getNamedItem("date").getNodeValue()
						.toString().replace("" + year, ""));

		low6.setText(dayNode5.getAttributes().getNamedItem("low")
				.getNodeValue().toString()
				+ " ° "
				+ str_mesur
				+ " - "
				+ dayNode5.getAttributes().getNamedItem("high").getNodeValue()
						.toString() + " ° " + str_mesur);

		Node dayNode6 = srcDoc.getElementsByTagName("yweather:forecast")
				.item(6);

		txt.add(dayNode6.getAttributes().getNamedItem("text").getNodeValue()
				.toString());

		day7.setText(dayNode6.getAttributes().getNamedItem("day")
				.getNodeValue().toString()
				+ " - "
				+ dayNode6.getAttributes().getNamedItem("date").getNodeValue()
						.toString().replace("" + year, ""));

		low7.setText(dayNode6.getAttributes().getNamedItem("low")
				.getNodeValue().toString()
				+ " ° "
				+ str_mesur
				+ " - "
				+ dayNode6.getAttributes().getNamedItem("high").getNodeValue()
						.toString() + " ° " + str_mesur);
	}

	private Document convertStringToDocument(String src) {
		Document dest = null;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser;

		try {
			parser = dbFactory.newDocumentBuilder();
			dest = parser.parse(new ByteArrayInputStream(src.getBytes()));
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
			Toast.makeText(getActivity(), e1.toString(), Toast.LENGTH_LONG)
					.show();
		} catch (SAXException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
					.show();
		}

		return dest;
	}

	private class WeatherAsyTask extends AsyncTask<String, Void, String> {
		private ProgressDialog pDialog1;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog1 = new ProgressDialog(getActivity());
			pDialog1.setMessage("please wait...");
			pDialog1.setIndeterminate(false);
			pDialog1.setCancelable(false);
			pDialog1.show();
		}

		@Override
		protected String doInBackground(String... args) {
			return "Done!!!";
		}

		@Override
		protected void onPostExecute(String jsonResult) {
			try {
				String weatherString = QueryYahooWeather(str_mesur);
				Document weatherDoc = convertStringToDocument(weatherString);
				parseWeather(weatherDoc);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			pDialog1.dismiss();
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
