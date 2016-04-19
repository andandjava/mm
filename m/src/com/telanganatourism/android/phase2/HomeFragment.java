package com.telanganatourism.android.phase2;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.flurry.android.FlurryAgent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.telanganatourism.android.phase2.database.helper.TtHelper;
import com.telanganatourism.android.phase2.util.Utility;

public class HomeFragment extends Fragment {

	// static String[] locations = { "Hyderabad", "Khammam", "Mahabubnaga",
	// "Nalgonda", "Warangal", "Adilabad", "Nizamabad", "Karimnagar", "Medak",
	// "Rangareddy" };

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private ViewFlipper mViewFlipper;
	private int mDotsCount;
	private LinearLayout mDotsLayout;
	TextView mDotsText[];

	Runnable runnable;
	private final Handler handler = new Handler();

	Utility utility;

	int i;
	ImageView image;
	JSONObject jsonObject;
	JSONArray resultjsonArray;
	TtHelper dbbHelper;
	Cursor constantCursor = null;

	String responseString;

	int iterator, language_id;

	RelativeLayout rootlayout;

	int fontChaning = 0;

	TextView textView1, textView2, textView3, textView4, textView5, textView6,
			textView7, textView8;

	public HomeFragment() {
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		utility = new Utility(getActivity());

		try {
			dbbHelper = new TtHelper(getActivity());
			dbbHelper.openDataBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(tp);
		}

		View v = inflater.inflate(R.layout.dashboard, container, false);

		MainActivity.txt_logo.setVisibility(View.VISIBLE);

		MainActivity.txt_logo1.setVisibility(View.GONE);

		MainActivity.txt_title.setVisibility(View.GONE);

		MainActivity.btn_menu.setBackgroundResource(R.drawable.menu_green);

		MainActivity.txt_filter.setVisibility(View.GONE);

		MainActivity.txt_location.setVisibility(View.VISIBLE);

		MainActivity.txt_search.setVisibility(View.GONE);

		MainActivity.txt_location
				.setBackgroundResource(R.drawable.location_icon);

		MainActivity.txt_search.setBackgroundResource(R.drawable.search_icon);

		MainActivity.headerLayout.setBackgroundColor(Color
				.parseColor("#ffffff"));

		MainActivity.searchLayout.setBackgroundColor(Color
				.parseColor("#ffffff"));

		MainActivity.searchLayout.setVisibility(View.GONE);

		MainActivity.txt_Edit.setVisibility(View.GONE);

		// MainActivity.homeBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.destinationBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.accomodatonBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.eventsBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.shopBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.packagesBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.cultureBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.favroitesBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.weatherBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.settingBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.contactBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.emergencyBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.feedbackBtn.setTypeface(Constants.ProximaNova_Regular);
		// MainActivity.trackingTxt.setTypeface(Constants.ProximaNova_Regular);
		//
		// MainActivity.homeBtn.setText(R.string.home_sidemenu);
		// MainActivity.destinationBtn.setText(R.string.destination_sidemenu);
		// MainActivity.accomodatonBtn.setText(R.string.accomodation_sidemenu);
		// MainActivity.eventsBtn.setText(R.string.events_sidemenu);
		// MainActivity.shopBtn.setText(R.string.shopping_sidemenu);
		// MainActivity.packagesBtn.setText(R.string.packages_sidemenu);
		// MainActivity.cultureBtn.setText(R.string.culture_sidemenu);
		// MainActivity.favroitesBtn.setText(R.string.favorites_sidemenu);
		// MainActivity.weatherBtn.setText(R.string.weather_sidemenu);
		// MainActivity.settingBtn.setText(R.string.settings_sidemenu);
		// MainActivity.contactBtn.setText(R.string.contactus_sidemenu);
		// MainActivity.emergencyBtn.setText(R.string.emergency_sidemenu);
		// MainActivity.feedbackBtn.setText(R.string.feed_back_sidemenu);
		// MainActivity.trackingTxt.setText(R.string.setting_tracking);

		String[] splitedStr = Constants.locationCode.split(",");
		/* print substrings */
		ArrayList<String> aListNumbers = new ArrayList<String>(
				Arrays.asList(splitedStr));

		if ((SplashScreen.pref.getString(SplashScreen.Key_GET_USER_Track, ""))
				.equalsIgnoreCase("true")) {
			MainActivity.toggleButton.setChecked(true);
		} else {
			MainActivity.toggleButton.setChecked(false);
		}

		for (int i = 0; i < aListNumbers.size(); i++) {
			if (aListNumbers.get(i).equalsIgnoreCase("0")) {
				MainActivity.hydFlag = true;
				MainActivity.warangalFlag = true;
				MainActivity.karimnagarFlag = true;
				MainActivity.khamammFlag = true;
				MainActivity.medakFlag = true;
				MainActivity.nalgondaFlag = true;
				MainActivity.nizambadFlag = true;
				MainActivity.adilabadFlag = true;
				MainActivity.mbnrFlag = true;
				MainActivity.rangareddyFlag = true;

				MainActivity.hydTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getActivity().getResources().getDrawable(
								R.drawable.check_selected), null);

				MainActivity.warngalTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity().getResources().getDrawable(
										R.drawable.check_selected), null);

				MainActivity.karimnagarTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity().getResources().getDrawable(
										R.drawable.check_selected), null);

				MainActivity.khammTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getActivity().getResources().getDrawable(
								R.drawable.check_selected), null);

				MainActivity.medakTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getActivity().getResources().getDrawable(
								R.drawable.check_selected), null);

				MainActivity.nlgTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getActivity().getResources().getDrawable(
								R.drawable.check_selected), null);

				MainActivity.nizamabadTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity().getResources().getDrawable(
										R.drawable.check_selected), null);

				MainActivity.adilabadTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity().getResources().getDrawable(
										R.drawable.check_selected), null);

				MainActivity.mbnrTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getActivity().getResources().getDrawable(
								R.drawable.check_selected), null);

				MainActivity.rangareddyTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity().getResources().getDrawable(
										R.drawable.check_selected), null);

			} else if (aListNumbers.get(i).equalsIgnoreCase("1")) {
				MainActivity.hydFlag = true;
				// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				// MainActivity.hydTxt.setCompoundDrawablesWithIntrinsicBounds(
				// getActivity().getResources().getDrawable(
				// R.drawable.check_selected),
				// null,
				// null, null);
				// } else {
				MainActivity.hydTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getActivity().getResources().getDrawable(
								R.drawable.check_selected), null);
				// }

			} else if (aListNumbers.get(i).equalsIgnoreCase("2")) {
				MainActivity.warangalFlag = true;

				// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				// MainActivity.warngalTxt
				// .setCompoundDrawablesWithIntrinsicBounds(
				// getActivity().getResources().getDrawable(
				// R.drawable.check_selected),
				// null,
				// null, null);
				// } else {
				MainActivity.warngalTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity().getResources().getDrawable(
										R.drawable.check_selected), null);
				// }

			} else if (aListNumbers.get(i).equalsIgnoreCase("3")) {
				MainActivity.karimnagarFlag = true;
				// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				// MainActivity.karimnagarTxt
				// .setCompoundDrawablesWithIntrinsicBounds(
				// getActivity().getResources().getDrawable(
				// R.drawable.check_selected),
				// null,
				// null, null);
				// } else {
				MainActivity.karimnagarTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity().getResources().getDrawable(
										R.drawable.check_selected), null);
				// }

			} else if (aListNumbers.get(i).equalsIgnoreCase("4")) {
				MainActivity.khamammFlag = true;

				// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				// MainActivity.khammTxt.setCompoundDrawablesWithIntrinsicBounds(
				// getActivity().getResources().getDrawable(
				// R.drawable.check_selected),
				// null,
				// null, null);
				// } else {
				MainActivity.khammTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getActivity().getResources().getDrawable(
								R.drawable.check_selected), null);
				// }

			} else if (aListNumbers.get(i).equalsIgnoreCase("5")) {
				MainActivity.medakFlag = true;
				// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				// MainActivity.medakTxt.setCompoundDrawablesWithIntrinsicBounds(
				// getActivity().getResources().getDrawable(
				// R.drawable.check_selected),
				// null,
				// null, null);
				// } else {
				MainActivity.medakTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getActivity().getResources().getDrawable(
								R.drawable.check_selected), null);
				// }

			} else if (aListNumbers.get(i).equalsIgnoreCase("6")) {
				MainActivity.nalgondaFlag = true;
				// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				// MainActivity.nlgTxt.setCompoundDrawablesWithIntrinsicBounds(
				// getActivity().getResources().getDrawable(
				// R.drawable.check_selected),
				// null,
				// null, null);
				// } else {
				MainActivity.nlgTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getActivity().getResources().getDrawable(
								R.drawable.check_selected), null);
				// }

			} else if (aListNumbers.get(i).equalsIgnoreCase("7")) {
				MainActivity.nizambadFlag = true;
				// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				// MainActivity.nizamabadTxt
				// .setCompoundDrawablesWithIntrinsicBounds(
				// getActivity().getResources().getDrawable(
				// R.drawable.check_selected),
				// null,
				// null, null);
				// } else {
				MainActivity.nizamabadTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity().getResources().getDrawable(
										R.drawable.check_selected), null);
				// }

			} else if (aListNumbers.get(i).equalsIgnoreCase("8")) {
				MainActivity.adilabadFlag = true;
				// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				// MainActivity.adilabadTxt
				// .setCompoundDrawablesWithIntrinsicBounds(
				// getActivity().getResources().getDrawable(
				// R.drawable.check_selected),
				// null,
				// null, null);
				// } else {
				MainActivity.adilabadTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity().getResources().getDrawable(
										R.drawable.check_selected), null);
				// }

			} else if (aListNumbers.get(i).equalsIgnoreCase("9")) {
				MainActivity.mbnrFlag = true;
				// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				// MainActivity.mbnrTxt.setCompoundDrawablesWithIntrinsicBounds(
				// getActivity().getResources().getDrawable(
				// R.drawable.check_selected),
				// null,
				// null, null);
				// } else {
				MainActivity.mbnrTxt.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getActivity().getResources().getDrawable(
								R.drawable.check_selected), null);
				// }

			} else if (aListNumbers.get(i).equalsIgnoreCase("10")) {
				MainActivity.rangareddyFlag = true;
				// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				// MainActivity.rangareddyTxt
				// .setCompoundDrawablesWithIntrinsicBounds(
				// getActivity().getResources().getDrawable(
				// R.drawable.check_selected),
				// null,
				// null, null);
				// } else {
				MainActivity.rangareddyTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity().getResources().getDrawable(
										R.drawable.check_selected), null);
				// }

			}
		}

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
				MainActivity.txt_filter.setVisibility(View.VISIBLE);
				MainActivity.txt_location.setVisibility(View.VISIBLE);
				MainActivity.btn_menu.setVisibility(View.VISIBLE);
			}
		});

		textView1 = (TextView) v.findViewById(R.id.txt_attr);
		textView2 = (TextView) v.findViewById(R.id.txt_hotel);
		textView3 = (TextView) v.findViewById(R.id.txt_packages);
		textView4 = (TextView) v.findViewById(R.id.txt_shop);
		textView5 = (TextView) v.findViewById(R.id.txt_events);
		textView6 = (TextView) v.findViewById(R.id.txt_culture);
		textView7 = (TextView) v.findViewById(R.id.txt_weather);
		textView8 = (TextView) v.findViewById(R.id.txt_emergency);

		textView1.setTypeface(Constants.ProximaNova_Regular);
		textView2.setTypeface(Constants.ProximaNova_Regular);
		textView3.setTypeface(Constants.ProximaNova_Regular);
		textView4.setTypeface(Constants.ProximaNova_Regular);
		textView5.setTypeface(Constants.ProximaNova_Regular);
		textView6.setTypeface(Constants.ProximaNova_Regular);
		textView7.setTypeface(Constants.ProximaNova_Regular);
		textView8.setTypeface(Constants.ProximaNova_Regular);

		// TextView tvSmallFont = (TextView) v.findViewById(R.id.smallFontTxt);
		// TextView tvBigFont = (TextView) v.findViewById(R.id.bigFontTxt);
		//
		// tvSmallFont.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (fontChaning == 0) {
		// textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		// } else {
		// fontChaning--;
		// if (fontChaning > 0) {
		//
		// if (fontChaning == 1) {
		// textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// } else if (fontChaning == 2) {
		// textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// } else if (fontChaning == 3) {
		// textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
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

		if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID, "")
				.equalsIgnoreCase("1")) {

			textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {

			textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			textView2.setSingleLine(true);
			textView2.setEllipsize(TruncateAt.END);
			textView2.setLines(1);
			textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			textView6.setSingleLine(true);
			textView6.setEllipsize(TruncateAt.END);
			textView6.setLines(1);
			textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {

			textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		}
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

		// MainActivity.getRightMenuList(getActivity(), locations);

		getActivity().getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#ffffff")));

		RelativeLayout attrImage = (RelativeLayout) v
				.findViewById(R.id.attaractionsLayout);

		RelativeLayout hotelImage = (RelativeLayout) v
				.findViewById(R.id.hotelLayout);

		RelativeLayout packagesImage = (RelativeLayout) v
				.findViewById(R.id.packagesLayout);

		RelativeLayout shopImage = (RelativeLayout) v
				.findViewById(R.id.shopLayout);

		RelativeLayout eventsImage = (RelativeLayout) v
				.findViewById(R.id.eventsLayout);

		RelativeLayout cultureImage = (RelativeLayout) v
				.findViewById(R.id.cultureLayout);

		RelativeLayout weatherImage = (RelativeLayout) v
				.findViewById(R.id.weatherLayout);

		RelativeLayout emergencyImage = (RelativeLayout) v
				.findViewById(R.id.emergencyLayout);

		MainActivity.allTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "0";
				Constants.cityId = "90883135";

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.hydFlag) {
						MainActivity.hydFlag = false;
						MainActivity.warangalFlag = false;
						MainActivity.karimnagarFlag = false;
						MainActivity.khamammFlag = false;
						MainActivity.medakFlag = false;
						MainActivity.nalgondaFlag = false;
						MainActivity.nizambadFlag = false;
						MainActivity.adilabadFlag = false;
						MainActivity.mbnrFlag = false;
						MainActivity.rangareddyFlag = false;
						
						MainActivity.allTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								getActivity()
										.getResources()
										.getDrawable(
												R.drawable.check_unselect),
								null, null, null);

						MainActivity.hydTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
						MainActivity.warngalTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
						MainActivity.karimnagarTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
						MainActivity.khammTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
						MainActivity.medakTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
						MainActivity.nlgTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
						MainActivity.nizamabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
						MainActivity.adilabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
						MainActivity.mbnrTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
						MainActivity.rangareddyTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						
						MainActivity.hydFlag = true;
						MainActivity.warangalFlag = true;
						MainActivity.karimnagarFlag = true;
						MainActivity.khamammFlag = true;
						MainActivity.medakFlag = true;
						MainActivity.nalgondaFlag = true;
						MainActivity.nizambadFlag = true;
						MainActivity.adilabadFlag = true;
						MainActivity.mbnrFlag = true;
						MainActivity.rangareddyFlag = true;
						
						MainActivity.allTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								getActivity()
										.getResources()
										.getDrawable(
												R.drawable.check_selected),
								null, null, null);
						
						MainActivity.hydTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
						MainActivity.warngalTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
						MainActivity.karimnagarTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
						MainActivity.khammTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
						MainActivity.medakTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
						MainActivity.nlgTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
						MainActivity.nizamabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
						MainActivity.adilabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
						MainActivity.mbnrTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
						MainActivity.rangareddyTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.allFlag) {
						MainActivity.allFlag = false;
						MainActivity.hydFlag = false;
						MainActivity.warangalFlag = false;
						MainActivity.karimnagarFlag = false;
						MainActivity.khamammFlag = false;
						MainActivity.medakFlag = false;
						MainActivity.nalgondaFlag = false;
						MainActivity.nizambadFlag = false;
						MainActivity.adilabadFlag = false;
						MainActivity.mbnrFlag = false;
						MainActivity.rangareddyFlag = false;
						
						MainActivity.allTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity()
										.getResources()
										.getDrawable(
												R.drawable.check_unselect),
								null);

						MainActivity.hydTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);

						MainActivity.warngalTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);

						MainActivity.karimnagarTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);

						MainActivity.khammTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);

						MainActivity.medakTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);

						MainActivity.nlgTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);

						MainActivity.nizamabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);

						MainActivity.adilabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);

						MainActivity.mbnrTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);

						MainActivity.rangareddyTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.allFlag = true;
						MainActivity.hydFlag = true;
						MainActivity.warangalFlag = true;
						MainActivity.karimnagarFlag = true;
						MainActivity.khamammFlag = true;
						MainActivity.medakFlag = true;
						MainActivity.nalgondaFlag = true;
						MainActivity.nizambadFlag = true;
						MainActivity.adilabadFlag = true;
						MainActivity.mbnrFlag = true;
						MainActivity.rangareddyFlag = true;
						
						MainActivity.allTxt
						.setCompoundDrawablesWithIntrinsicBounds(
								null,
								null,
								getActivity()
										.getResources()
										.getDrawable(
												R.drawable.check_selected),
								null);

						MainActivity.hydTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);

						MainActivity.warngalTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);

						MainActivity.karimnagarTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);

						MainActivity.khammTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);

						MainActivity.medakTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);

						MainActivity.nlgTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);

						MainActivity.nizamabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);

						MainActivity.adilabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);

						MainActivity.mbnrTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);

						MainActivity.rangareddyTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

				// HomeFragment fragment = new HomeFragment();
				//
				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				//
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);
			}
		});

		MainActivity.hydTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "1";
				Constants.cityId = "90883135";

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.hydFlag) {
						MainActivity.hydFlag = false;
						MainActivity.hydTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						MainActivity.hydFlag = true;
						MainActivity.hydTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.hydFlag) {
						MainActivity.hydFlag = false;
						MainActivity.hydTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.hydFlag = true;
						MainActivity.hydTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

				// HomeFragment fragment = new HomeFragment();
				//
				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				//
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);
			}
		});

		MainActivity.warngalTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "2";
				Constants.cityId = "90882603";
				// HomeFragment fragment = new HomeFragment();
				//
				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.warangalFlag) {
						MainActivity.warangalFlag = false;
						MainActivity.warngalTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						MainActivity.warangalFlag = true;
						MainActivity.warngalTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.warangalFlag) {
						MainActivity.warangalFlag = false;
						MainActivity.warngalTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.warangalFlag = true;
						MainActivity.warngalTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

			}
		});

		MainActivity.karimnagarTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "3";
				Constants.cityId = "2295185";
				// HomeFragment fragment = new HomeFragment();
				//
				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.karimnagarFlag) {
						MainActivity.karimnagarFlag = false;
						MainActivity.karimnagarTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						MainActivity.karimnagarFlag = true;
						MainActivity.karimnagarTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.karimnagarFlag) {
						MainActivity.karimnagarFlag = false;
						MainActivity.karimnagarTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.karimnagarFlag = true;
						MainActivity.karimnagarTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

			}
		});

		MainActivity.khammTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "4";
				Constants.cityId = "2295229";
				// HomeFragment fragment = new HomeFragment();
				//
				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.khamammFlag) {
						MainActivity.khamammFlag = false;
						MainActivity.khammTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						MainActivity.khamammFlag = true;
						MainActivity.khammTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.khamammFlag) {
						MainActivity.khamammFlag = false;
						MainActivity.khammTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.khamammFlag = true;
						MainActivity.khammTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

			}
		});

		MainActivity.medakTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "5";
				Constants.cityId = "90882271";
				// HomeFragment fragment = new HomeFragment();
				//
				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.medakFlag) {
						MainActivity.medakFlag = false;
						MainActivity.medakTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						MainActivity.medakFlag = true;
						MainActivity.medakTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.medakFlag) {
						MainActivity.medakFlag = false;
						MainActivity.medakTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.medakFlag = true;
						MainActivity.medakTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

			}
		});

		MainActivity.nlgTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "6";
				Constants.cityId = "90886279";
				// HomeFragment fragment = new HomeFragment();
				//
				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.nalgondaFlag) {
						MainActivity.nalgondaFlag = false;
						MainActivity.nlgTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						MainActivity.nalgondaFlag = true;
						MainActivity.nlgTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.nalgondaFlag) {
						MainActivity.nalgondaFlag = false;
						MainActivity.nlgTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.nalgondaFlag = true;
						MainActivity.nlgTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

			}
		});

		MainActivity.nizamabadTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "7";
				Constants.cityId = "90887467";
				HomeFragment fragment = new HomeFragment();

				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.nizambadFlag) {
						MainActivity.nizambadFlag = false;
						MainActivity.nizamabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						MainActivity.nizambadFlag = true;
						MainActivity.nizamabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.nizambadFlag) {
						MainActivity.nizambadFlag = false;
						MainActivity.nizamabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.nizambadFlag = true;
						MainActivity.nizamabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

			}
		});

		MainActivity.adilabadTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "8";
				Constants.cityId = "90882225";
				// HomeFragment fragment = new HomeFragment();
				//
				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.adilabadFlag) {
						MainActivity.adilabadFlag = false;
						MainActivity.adilabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						MainActivity.adilabadFlag = true;
						MainActivity.adilabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.adilabadFlag) {
						MainActivity.adilabadFlag = false;
						MainActivity.adilabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.adilabadFlag = true;
						MainActivity.adilabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

			}
		});

		MainActivity.mbnrTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "9";
				Constants.cityId = "2281586";
				// HomeFragment fragment = new HomeFragment();
				//
				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.mbnrFlag) {
						MainActivity.mbnrFlag = false;
						MainActivity.mbnrTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						MainActivity.mbnrFlag = true;
						MainActivity.mbnrTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.mbnrFlag) {
						MainActivity.mbnrFlag = false;
						MainActivity.mbnrTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.mbnrFlag = true;
						MainActivity.mbnrTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

			}
		});

		MainActivity.rangareddyTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationId = "10";
				Constants.cityId = "90891709";
				// HomeFragment fragment = new HomeFragment();
				//
				// if (fragment != null) {
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, fragment).commit();
				//
				// } else {
				// // error in creating fragment
				// Log.e("MainActivity", "Error in creating fragment");
				// }
				// MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);

				if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					if (MainActivity.rangareddyFlag) {
						MainActivity.rangareddyFlag = false;
						MainActivity.rangareddyTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null, null, null);
					} else {
						MainActivity.rangareddyFlag = true;
						MainActivity.rangareddyTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null, null, null);
					}
				} else {
					if (MainActivity.rangareddyFlag) {
						MainActivity.rangareddyFlag = false;
						MainActivity.rangareddyTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_unselect),
										null);
					} else {
						MainActivity.rangareddyFlag = true;
						MainActivity.rangareddyTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
					}
				}

			}
		});

		MainActivity.submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.locationCode = "";

				if (MainActivity.hydFlag) {
					Constants.locationCode = Constants.locationCode + ",1";
				}
				if (MainActivity.warangalFlag) {
					Constants.locationCode = Constants.locationCode + ",2";
				}
				if (MainActivity.karimnagarFlag) {
					Constants.locationCode = Constants.locationCode + ",3";
				}
				if (MainActivity.khamammFlag) {
					Constants.locationCode = Constants.locationCode + ",4";
				}
				if (MainActivity.medakFlag) {
					Constants.locationCode = Constants.locationCode + ",5";
				}
				if (MainActivity.nalgondaFlag) {
					Constants.locationCode = Constants.locationCode + ",6";
				}
				if (MainActivity.nizambadFlag) {
					Constants.locationCode = Constants.locationCode + ",7";
				}
				if (MainActivity.adilabadFlag) {
					Constants.locationCode = Constants.locationCode + ",8";
				}
				if (MainActivity.mbnrFlag) {
					Constants.locationCode = Constants.locationCode + ",9";
				}
				if (MainActivity.rangareddyFlag) {
					Constants.locationCode = Constants.locationCode + ",10";
				}
				Constants.locationCode = Constants.locationCode.replaceFirst(
						",", "");

				// System.out.println("search code " + Constants.searchCode);

				// mDrawerLayout.closeDrawer(Gravity.RIGHT);

				if (TextUtils.isEmpty(Constants.locationCode)) {
					Toast.makeText(getActivity(),
							"Please Select atleast one location",
							Toast.LENGTH_LONG).show();
					
				} else {

					HomeFragment fragment = new HomeFragment();

					if (fragment != null) {
						FragmentManager fragmentManager = getFragmentManager();
						fragmentManager.beginTransaction()
								.replace(R.id.frame_container, fragment)
								.commit();

					} else {
						// error in creating fragment
						Log.e("MainActivity", "Error in creating fragment");
					}
					MainActivity.mDrawerLayout.closeDrawer(Gravity.RIGHT);

					// startActivity(new Intent(getActivity(),
					// MainActivity.class));
				}

			}
		});

		attrImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Constants.cdt_id = 0;

				MainActivity.changeTitle = getResources().getString(
						R.string.destination_sidemenu);
				MainActivity.changeColorStr = "#2a9595";
				final FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_container, new DestinationFragment(),
						getResources().getString(R.string.destination_sidemenu))
						.addToBackStack(
								getResources().getString(
										R.string.destination_sidemenu))
						.commit();
				// ft.addToBackStack(
				// getResources().getString(R.string.destination_sidemenu))
				// .commit();

			}
		});

		hotelImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Constants.cdt_id = 1;
				Constants.sublistFlag = 3;
				MainActivity.changeColorStr = "#e6912d";

				MainActivity.changeTitle = getResources().getString(
						R.string.accomodation_sidemenu);
				final FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(
						R.id.frame_container,
						new WhereToShopFragment(),
						getResources()
								.getString(R.string.accomodation_sidemenu))
						.addToBackStack(
								getResources().getString(
										R.string.accomodation_sidemenu))
						.commit();
				// ft.addToBackStack(
				// getResources()
				// .getString(R.string.accomodation_sidemenu))
				// .commit();

			}
		});

		packagesImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.cdt_id = 3;
				MainActivity.changeColorStr = "#9e5dd0";
				MainActivity.changeTitle = getResources().getString(
						R.string.packages_sidemenu);

				final FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_container, new PackagesFragment(),
						getResources().getString(R.string.packages_sidemenu))
						.addToBackStack(
								getResources().getString(
										R.string.packages_sidemenu)).commit();
				// ft.addToBackStack(
				// getResources().getString(R.string.packages_sidemenu))
				// .commit();
			}
		});

		shopImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.cdt_id = 0;
				MainActivity.changeColorStr = "#2a9595";

				MainActivity.changeTitle = getResources().getString(
						R.string.shopping_sidemenu);
				Constants.sublistFlag = 6;
				final FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_container, new WhereToShopFragment(),
						getResources().getString(R.string.shopping_sidemenu))
						.addToBackStack(
								getResources().getString(
										R.string.shopping_sidemenu)).commit();
				// ft.addToBackStack(
				// getResources().getString(R.string.shopping_sidemenu))
				// .commit();
			}
		});

		eventsImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.cdt_id = 2;
				Constants.sublistFlag = 4;
				MainActivity.changeColorStr = "#af4141";

				MainActivity.changeTitle = getResources().getString(
						R.string.events_sidemenu);
				final FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_container, new WhereToShopFragment(),
						getResources().getString(R.string.events_sidemenu))
						.addToBackStack(
								getResources().getString(
										R.string.events_sidemenu)).commit();
				// ft.addToBackStack(
				// getResources().getString(R.string.events_sidemenu))
				// .commit();
			}
		});

		cultureImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub3
				Constants.cdt_id = 4;
				MainActivity.changeColorStr = "#79af52";

				MainActivity.changeTitle = getResources().getString(
						R.string.culture_sidemenu);
				Constants.sublistFlag = 5;
				final FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_container, new WhereToShopFragment(),
						getResources().getString(R.string.culture_sidemenu))
						.addToBackStack(
								getResources().getString(
										R.string.culture_sidemenu)).commit();
				// ft.addToBackStack(
				// getResources().getString(R.string.culture_sidemenu))
				// .commit();
			}
		});

		weatherImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub3
				final FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_container, new WeatherFragment(),
						"NewFragmentTag");
				ft.addToBackStack(null).commit();
			}
		});

		emergencyImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub3
				MainActivity.changeColorStr = "#ffffff";
				MainActivity.changeTitle = getResources().getString(
						R.string.emergency_sidemenu);
				final FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_container, new EmergencyFragment(),
						getResources().getString(R.string.emergency_sidemenu))
						.addToBackStack(
								getResources().getString(
										R.string.emergency_sidemenu)).commit();
				// ft.addToBackStack(
				// getResources().getString(R.string.emergency_sidemenu))
				// .commit();
			}
		});

		mViewFlipper = (ViewFlipper) v.findViewById(R.id.view_flipper);

		// if (utility.IsNetConnected(getActivity())) {

		// for (language_id = 1; language_id <= 4; language_id++) {
		// for (iterator = 1; iterator <= 10; iterator++) {

		new SlideImagesJSONParse(Constants.locationCode,
				Constants.selectLanguage).execute();

		// new SlideImagesJSONParse(iterator).execute();

		// }
		// }
		// mViewFlipper.startFlipping();
		// } else {
		// mViewFlipper.stopFlipping();
		// RelativeLayout rootlayout = new RelativeLayout(
		// getActivity());
		// RelativeLayout.LayoutParams relativeParams = new
		// RelativeLayout.LayoutParams(
		// LayoutParams.FILL_PARENT,
		// LayoutParams.FILL_PARENT);
		// rootlayout.setLayoutParams(relativeParams);
		// image = new ImageView(getActivity());
		// TextView txt_img_name = new TextView(getActivity());
		//
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
		// Picasso.with(getActivity())
		// .load(R.drawable.default_img).error(R.drawable.default_img)
		// .into(image);
		// // txt_img_name.setText(jsonObject.getString("title"));
		// txt_img_name.setSingleLine(true);
		//
		// rootlayout.addView(txt_img_name);
		// mViewFlipper.addView(rootlayout);
		//
		// }
		final GestureDetector detector = new GestureDetector(
				new MyGestureDetector());
		mViewFlipper.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(final View view, final MotionEvent event) {

				System.out.println("touch detected ");
				detector.onTouchEvent(event);
				return false;
			}
		});

		mDotsLayout = (LinearLayout) v.findViewById(R.id.image_count);
		mDotsCount = mViewFlipper.getChildCount();
		mDotsText = new TextView[mDotsCount];
		// here we set the dots

		for (int i = 0; i < mDotsCount; i++) {

			mDotsText[i] = new TextView(getActivity());
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

		return v;
	}

	private class SlideImagesJSONParse extends
			AsyncTask<String, String, String> {
		// ProgressDialog pDialog;

		String locationId = "", languageId = "";

		public SlideImagesJSONParse(String locId, String langid) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;
			this.languageId = langid;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new ProgressDialog(getActivity());
			// pDialog.setMessage("Loading Please wait...");
			// pDialog.setIndeterminate(false);
			// pDialog.setCancelable(false);
			// pDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... args) {
			System.out.println("url is"+Constants.Base_url
					+ "getSlideImages/" + Constants.locationCode + "/"
					+ languageId);
			return WebServiceCalls.getJSONString(Constants.Base_url
					+ "getSlideImages/" + Constants.locationCode + "/"
					+ languageId);

			// try {
			// dbbHelper = new TtHelper(getActivity());
			// dbbHelper.openDataBase();
			//
			// String[] splitedStr = Constants.locationCode.split(",");
			//
			// // String[] strValues = strNumbers.split(",");
			//
			// ArrayList<String> aListNumbers = new ArrayList<String>(
			// Arrays.asList(splitedStr));
			//
			// for (int i = 0; i < aListNumbers.size(); i++) {
			// String select = " SELECT * FROM slide_images WHERE location_id="
			// + aListNumbers.get(i)
			// + " AND language_id="
			// + Constants.selectLanguage;
			// constantCursor = dbbHelper.getReadableDatabase().rawQuery(
			// select, null);
			//
			// getActivity().startManagingCursor(constantCursor);
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
			// }
			//
			// // dbbHelper.close();
			//
			// // dbbHelper.close();
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			// return responseString;

			/*
			 * try { dbbHelper = new TtHelper(getActivity());
			 * dbbHelper.openDataBase();
			 * 
			 * String select = " SELECT * FROM slide_images WHERE location_id="
			 * + Constants.locationId; constantCursor =
			 * dbbHelper.getReadableDatabase().rawQuery( select, null);
			 * 
			 * getActivity().startManagingCursor(constantCursor);
			 * 
			 * if (constantCursor.getCount() > 0) { if
			 * (constantCursor.moveToFirst()) { // do {
			 * 
			 * responseString = constantCursor .getString(constantCursor
			 * .getColumnIndex("ResponseContent"));
			 * 
			 * // } while (constantCursor.moveToNext());
			 * 
			 * }
			 * 
			 * }
			 * 
			 * // dbbHelper.close();
			 * 
			 * // dbbHelper.close(); } catch (IOException e1) { // TODO
			 * Auto-generated catch block e1.printStackTrace(); }
			 * 
			 * return responseString;
			 */
		}

		@Override
		protected void onPostExecute(String jsonResult) {
			// pDialog.dismiss();
			System.out.println(" result " + jsonResult);

			try {

				// dbbHelper.insertSlideImagesContent(languageId, locationId,
				// jsonResult);
				//
				// Toast.makeText(getActivity(), locationId, 200).show();

				// if(utility.IsNetConnected(getActivity())){

				JSONObject jsonObjMain = new JSONObject(jsonResult);
				if (jsonObjMain.has("WhatToSeePlace")) {
					// Creating JSONArray from JSONObject

					resultjsonArray = jsonObjMain
							.getJSONArray("WhatToSeePlace");

					for (i = 0; i < resultjsonArray.length(); i++) {

						final int imgTagId = i;
						jsonObject = resultjsonArray.getJSONObject(i);
						System.out.println("ids are :"
								+ jsonObject.getString("id"));
						System.out.println("titles are :"
								+ jsonObject.getString("title"));
						System.out.println("image paths are :"
								+ jsonObject.getString("image_path"));
						rootlayout = new RelativeLayout(getActivity());
						RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT);
						rootlayout.setLayoutParams(relativeParams);
						rootlayout.setBackgroundColor(Color
								.parseColor("#ffffff"));
						image = new ImageView(getActivity());
						TextView txt_img_name = new TextView(getActivity());

						image.setTag(i);
						image.setId(i);
						image.setLayoutParams(relativeParams);
						image.setScaleType(ImageView.ScaleType.CENTER_CROP);

						rootlayout.addView(image);

						RelativeLayout.LayoutParams relativeParams1 = new RelativeLayout.LayoutParams(
								LayoutParams.FILL_PARENT, 60);
						relativeParams1
								.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
						txt_img_name.setLayoutParams(relativeParams1);
						txt_img_name.setGravity(Gravity.CENTER);
						txt_img_name.setTypeface(Constants.ProximaNova_Regular);
						// txt_img_name.setTextSize(18);

						if (SplashScreen.fontpref.getString(
								SplashScreen.Key_GET_FONT_ID, "")
								.equalsIgnoreCase("1")) {
							txt_img_name.setTextSize(
									TypedValue.COMPLEX_UNIT_SP, 18);
						} else if (SplashScreen.fontpref.getString(
								SplashScreen.Key_GET_FONT_ID, "")
								.equalsIgnoreCase("2")) {
							txt_img_name.setTextSize(
									TypedValue.COMPLEX_UNIT_SP, 16);
						} else if (SplashScreen.fontpref.getString(
								SplashScreen.Key_GET_FONT_ID, "")
								.equalsIgnoreCase("3")) {
							txt_img_name.setTextSize(
									TypedValue.COMPLEX_UNIT_SP, 20);
						}
						txt_img_name.setTextColor(Color.WHITE);
						txt_img_name.setBackgroundColor(Color
								.parseColor("#99000000"));

						// if
						// (jsonObject.getString("image_path").contains("http"))
						// {
						// Picasso.with(getActivity())
						// .load(jsonObject.getString("image_path"))
						// .error(R.drawable.default_img).into(image);

						ImageLoader imageLoader = ImageLoader.getInstance();
						DisplayImageOptions options = new DisplayImageOptions.Builder()
								.cacheInMemory(true).cacheOnDisc(true)
								.resetViewBeforeLoading(true)
								.showImageForEmptyUri(R.drawable.default_img)
								.showImageOnFail(R.drawable.default_img)
								.showImageOnLoading(R.drawable.default_img)
								.build();

						// download and display image from url
						imageLoader.displayImage(
								jsonObject.getString("image_path"), image,
								options);

						// } else {
						// try {
						// // Locale defloc = Locale.getDefault();
						// // .toLowerCase(defloc)
						// // get input stream
						// InputStream ims = getActivity().getAssets()
						// .open(jsonObject
						// .getString("image_path")
						// .toString());
						// // load image as Drawable
						// Drawable d = Drawable.createFromStream(ims,
						// null);
						// // set image to ImageView
						// image.setImageDrawable(d);
						// } catch (IOException ex) {
						// // return;
						// // Toast.makeText(getActivity(), ""+ex,
						// // Toast.LENGTH_LONG).show();
						// }
						// }

						// try {
						// String url = jsonObject.getString("image_path")
						// .replace("\'", "");
						//
						// String replacdUrl = url.replace(
						// "http://ttourdev.etisbew.net/images/", "");
						// // get input stream
						// InputStream ims = getActivity().getAssets().open(
						// replacdUrl.trim());
						// // load image as Drawable
						// Drawable d = Drawable.createFromStream(ims, null);
						// // set image to ImageView
						// image.setImageDrawable(d);
						// } catch (IOException ex) {
						// image.setBackgroundResource(R.drawable.default_img);
						// }
						txt_img_name.setText(jsonObject.getString("title"));
						txt_img_name.setSingleLine(true);

						rootlayout.addView(txt_img_name);
						mViewFlipper.addView(rootlayout);

						// mViewFlipper.setOnTouchListener(new OnTouchListener()
						// {
						//
						// @Override
						// public boolean onTouch(View v, MotionEvent event) {
						// // TODO Auto-generated method stub
						// Toast.makeText(getActivity(), ""+v.getTag(),
						// Toast.LENGTH_SHORT).show();
						// return false;
						// }
						// });
						rootlayout.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								try {
									jsonObject = resultjsonArray
											.getJSONObject(imgTagId);

									System.out.println("ids are :"
											+ jsonObject.getString("id"));
									// Toast.makeText(getActivity(),
									// ""+jsonObject.getString("id"),
									// Toast.LENGTH_SHORT).show();
									Constants.sublistFlag = 1;
									Constants.cdt_id = 0;
									Constants.id = jsonObject.getString("id");
									// MainActivity.changeTitle=jsonObject.getString("title");
									MainActivity.changeColorStr = "#2a9595";
									startActivity(new Intent(getActivity(),
											DetailScreen1.class));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});

					}

					// if (pDialog.isShowing() && pDialog != null) {
					// pDialog.dismiss();
					// }
				}

				mViewFlipper.startFlipping();
				// }else{
				// RelativeLayout rootlayout = new RelativeLayout(
				// getActivity());
				// RelativeLayout.LayoutParams relativeParams = new
				// RelativeLayout.LayoutParams(
				// LayoutParams.FILL_PARENT,
				// LayoutParams.FILL_PARENT);
				// rootlayout.setLayoutParams(relativeParams);
				// image = new ImageView(getActivity());
				// TextView txt_img_name = new TextView(getActivity());
				//
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
				// // if (jsonObject.getString("image_path").contains("http")) {
				// // Picasso.with(getActivity())
				// // .load(jsonObject.getString("image_path"))
				// // .error(R.drawable.default_img).into(image);
				//
				// image.setBackgroundResource(R.drawable.default_img);
				// // } else {
				// // try {
				// // // Locale defloc = Locale.getDefault();
				// // // .toLowerCase(defloc)
				// // // get input stream
				// // InputStream ims = getActivity().getAssets()
				// // .open(jsonObject
				// // .getString("image_path")
				// // .toString());
				// // // load image as Drawable
				// // Drawable d = Drawable.createFromStream(ims,
				// // null);
				// // // set image to ImageView
				// // image.setImageDrawable(d);
				// // } catch (IOException ex) {
				// // // return;
				// // // Toast.makeText(getActivity(), ""+ex,
				// // // Toast.LENGTH_LONG).show();
				// // }
				// // }
				// txt_img_name.setText("");
				// txt_img_name.setSingleLine(true);
				//
				// rootlayout.addView(txt_img_name);
				// mViewFlipper.addView(rootlayout);
				//
				//
				// }

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				// if (pDialog.isShowing() && pDialog != null) {
				// pDialog.dismiss();
				// }
			}
		}
	}

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {

				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

					mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(
							getActivity(), R.anim.left_in1));
					mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
							getActivity(), R.anim.left_out1));
					mViewFlipper.showNext();

					for (int i = 0; i < mDotsCount; i++) {
						mDotsText[i].setTextColor(Color.GRAY);
					}
					mDotsText[mViewFlipper.getDisplayedChild()]
							.setTextColor(Color.WHITE);
					return true;
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(
							getActivity(), R.anim.right_in1));
					mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
							getActivity(), R.anim.right_out1));
					mViewFlipper.showPrevious();

					for (int i = 0; i < mDotsCount; i++) {
						mDotsText[i].setTextColor(Color.GRAY);
					}
					mDotsText[mViewFlipper.getDisplayedChild()]
							.setTextColor(Color.WHITE);

					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return false;
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
		SlideImagesJSONParse imagesJSONParse = new SlideImagesJSONParse(
				Constants.locationCode, Constants.selectLanguage);
		imagesJSONParse.cancel(true);
	}
}
