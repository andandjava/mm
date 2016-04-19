package com.telanganatourism.android.phase2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.flurry.android.FlurryAgent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.telanganatourism.android.phase2.database.helper.BaseDatabase;
import com.telanganatourism.android.phase2.database.helper.TtHelper;
import com.telanganatourism.android.phase2.objects.ItemsObj;
import com.telanganatourism.android.phase2.util.Utility;

public class WhereToShopFragment extends Fragment {
	ArrayList<Integer> list;
	// EventBean event;
	ArrayList<EventsVariables> events_upcoming;
	ArrayList<Integer> id_list;
	AQuery androidAQuery;
	DestinationListAdapter adapter;
	// CustomAdapter adapter1;
	ListView lv;

	public static ArrayList<String> hotelIdArray = new ArrayList<String>();
	public static ArrayList<String> hotelNameArray = new ArrayList<String>();
	public static ArrayList<String> hotelRoomTypesArray = new ArrayList<String>();

	public static int position;

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

//	Utility utility;

	public WhereToShopFragment() {

	}

	int upcomingeventsLength = 0;

	JSONArray jsonArray;

	private final Handler handler = new Handler();
	private Drawable oldBackground = null;

	TtHelper dbbHelper;
	BaseDatabase baseDatabase;
	Cursor constantCursor = null;

	String responseString, responseStr;

	int iterator, language_id;

	TextView txtMsg;

	// int fontChaning = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_whats_hot,
				container, false);

//		utility = new Utility(getActivity());
		MainActivity.detailNavFlag = 1;

		MainActivity.txt_location.setVisibility(View.VISIBLE);
		lv = (ListView) rootView.findViewById(R.id.listView1);

		txtMsg = (TextView) rootView.findViewById(R.id.txtMsg);
		txtMsg.setText(getResources().getString(R.string.need_active_network));
		// lv.setEmptyView(rootView.findViewById(android.R.id.empty));
		try {
			dbbHelper = new TtHelper(getActivity());
			dbbHelper.openDataBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// new GetHotelsId(Constants.locationCode, Constants.selectLanguage);

		StringBuilder buf = new StringBuilder();
		InputStream json;
		try {
			json = getActivity().getAssets().open("contents.json");
			BufferedReader in = new BufferedReader(new InputStreamReader(json,
					"UTF-8"));
			String str;

			while ((str = in.readLine()) != null) {
				buf.append(str);
			}

			responseStr = buf.toString();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setting location default value with 1
		if (Constants.locationCode.isEmpty()
				|| TextUtils.isEmpty(Constants.locationCode)) {
			Constants.locationCode = "1";
		}

		if (Constants.sublistFlag == 3) {

			String[] splitedStr = Constants.locationCode.split(",");

			// String[] strValues = strNumbers.split(",");

			ArrayList<String> aListNumbers = new ArrayList<String>(
					Arrays.asList(splitedStr));

			for (int i = 0; i < aListNumbers.size(); i++) {
				if (aListNumbers.get(i).equalsIgnoreCase("1")) {
					MainActivity.hydFlag = true;
					// if (Constants.selectLanguage.equalsIgnoreCase("3")) {
					// MainActivity.hydTxt.setCompoundDrawablesWithIntrinsicBounds(
					// getActivity().getResources().getDrawable(
					// R.drawable.check_selected),
					// null,
					// null, null);
					// } else {
					MainActivity.hydTxt
							.setCompoundDrawablesWithIntrinsicBounds(
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
					MainActivity.khammTxt
							.setCompoundDrawablesWithIntrinsicBounds(
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
					MainActivity.medakTxt
							.setCompoundDrawablesWithIntrinsicBounds(
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
					MainActivity.nlgTxt
							.setCompoundDrawablesWithIntrinsicBounds(
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
					MainActivity.mbnrTxt
							.setCompoundDrawablesWithIntrinsicBounds(
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

			MainActivity.dest_items_array.clear();
			for (int i = 0; i < aListNumbers.size(); i++) {
				try {

					String select = " SELECT * FROM Accomodation_Content WHERE id="
							+ aListNumbers.get(i)
							+ " AND language_id="
							+ Constants.selectLanguage;
					constantCursor = dbbHelper.getReadableDatabase().rawQuery(
							select, null);

					getActivity().startManagingCursor(constantCursor);

					if (constantCursor.getCount() > 0) {
						if (constantCursor.moveToFirst()) {
							do {

								responseString = constantCursor
										.getString(constantCursor
												.getColumnIndex("ResponseContent"));

								jsonParsing(responseString);

							} while (constantCursor.moveToNext());

						}

					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		} else if (Constants.sublistFlag == 4) {

			// jsonEventsParsing(responseStr);
			MainActivity.txt_location.setVisibility(View.GONE);
			if (Utility.checkInternetConnection(getActivity())) {
				txtMsg.setVisibility(View.GONE);
				if (Utility.checkInternetConnection(getActivity())) {
					new EventsTask().execute();
				} else {
					Utility.showAlertNoInternetService(getActivity());
				}
				
				
			} else {
				txtMsg.setVisibility(View.VISIBLE);
			}
		} else if (Constants.sublistFlag == 5) {

			try {

				MainActivity.txt_location.setVisibility(View.GONE);
				String select = " SELECT * FROM Culture_Content WHERE language_id="
						+ Constants.selectLanguage;
				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
						select, null);

				getActivity().startManagingCursor(constantCursor);

				if (constantCursor.getCount() > 0) {
					if (constantCursor.moveToFirst()) {
						do {

							responseString = constantCursor
									.getString(constantCursor
											.getColumnIndex("ResponseContent"));

							jsonCultureParsing(responseString);

						} while (constantCursor.moveToNext());

					}

				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (Constants.sublistFlag == 6) {
			try {

				MainActivity.txt_location.setVisibility(View.GONE);
				String select = " SELECT * FROM Shop_Online WHERE language_id="
						+ Constants.selectLanguage;
				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
						select, null);

				getActivity().startManagingCursor(constantCursor);

				if (constantCursor.getCount() > 0) {
					if (constantCursor.moveToFirst()) {
						do {

							responseString = constantCursor
									.getString(constantCursor
											.getColumnIndex("ResponseContent"));

							jsonShoppingParsing(responseString);

						} while (constantCursor.moveToNext());

					}

				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// for (iterator = 2; iterator <= 4; iterator++) {
			//
			// new CulturesTask(String.valueOf(iterator)).execute();
			//
			// // new SlideImagesJSONParse(iterator).execute();
			//
			// }
		}

		MainActivity.txt_logo.setVisibility(View.GONE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setText(MainActivity.changeTitle);

		MainActivity.txt_title.setTypeface(Constants.ProximaNova_Regular);

		MainActivity.txt_title.setTextColor(Color.parseColor("#ffffff"));

		MainActivity.txt_filter.setVisibility(View.GONE);

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
		// adapter.notifyDataSetChanged();
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// } else {
		// fontChaning--;
		// if (fontChaning > 0) {
		//
		// if (fontChaning == 1) {
		// adapter.notifyDataSetChanged();
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// } else if (fontChaning == 2) {
		// adapter.notifyDataSetChanged();
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// } else if (fontChaning == 3) {
		// adapter.notifyDataSetChanged();
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
		// }

		if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID, "")
				.equalsIgnoreCase("1")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		}
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
		// adapter.notifyDataSetChanged();
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// } else if (fontChaning == 2) {
		// adapter.notifyDataSetChanged();
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// } else if (fontChaning == 3) {
		// adapter.notifyDataSetChanged();
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
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

		MainActivity.txt_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.searchLayout.setVisibility(View.VISIBLE);
				MainActivity.txt_location.setVisibility(View.GONE);
				MainActivity.btn_menu.setVisibility(View.VISIBLE);
			}
		});

		MainActivity.txt_back_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.searchLayout.setVisibility(View.GONE);
				MainActivity.txt_filter.setVisibility(View.GONE);
				MainActivity.txt_location.setVisibility(View.VISIBLE);
				MainActivity.btn_menu.setVisibility(View.VISIBLE);
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

					WhereToShopFragment fragment = new WhereToShopFragment();

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
				}

			}
		});

		titles.clear();
		loc.clear();
		img.clear();
		dec.clear();

		// MainActivity.getRightMenuList(getActivity(), whrdo);

		list = new ArrayList<Integer>();
		id_list = new ArrayList<Integer>();
		// event = (EventBean) getActivity().getApplicationContext();
		androidAQuery = new AQuery(getActivity());
		events_upcoming = new ArrayList<EventsVariables>();
		list.clear();
		id_list.clear();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				position = arg2;
				ItemsObj itemsObj = MainActivity.dest_items_array.get(arg2);
				Constants.id = itemsObj.getDest_id();
				Constants.new_id = itemsObj.getDestnew_id();
				

				// if(Constants.id.equalsIgnoreCase("0")){
				// AlertDialog.Builder altDialog = new AlertDialog.Builder(
				// getActivity());
				// altDialog
				// .setMessage("Inactive Hotel"); // here
				// // add
				// // your
				// // message
				// altDialog.setNeutralButton("OK",
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(
				// DialogInterface dialog,
				// int which) {
				// dialog.dismiss();
				// }
				// });
				// altDialog.show();
				// }else{
				startActivity(new Intent(getActivity(), DetailScreen1.class));
				// }

			}
		});

		if (Constants.sublistFlag == 3) {
			// Checking the Accomodation Updating flag value whether to update
			if (Constants.accomodation_flag == true) {

				Constants.accomodation_flag = false;

				// Updating the service in the background
				getActivity()
				.startService(
						new Intent(
								getActivity(),
								com.telanganatourism.android.phase2.backgroundservice.UpdatingServices.class));
				
				
			}
		}else if(Constants.sublistFlag == 5){
			
			//Checking the Culture Updating flag value whether to update
			if (Constants.culture_flag == true) {

				Constants.culture_flag = false;

				// Updating the service in the background
				getActivity()
				.startService(
						new Intent(
								getActivity(),
								com.telanganatourism.android.phase2.backgroundservice.UpdatingServices.class));
			}
		}

		return rootView;
	}

	class AccomodationTask extends AsyncTask<String, Void, String> {

		String tableNameStr = "", locationId = "", languageId = "";

		public AccomodationTask(String tableName, String locId, String langid) {
			// TODO Auto-generated constructor stub
			this.tableNameStr = tableName;
			this.locationId = locId;
			this.languageId = langid;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getAccomodationsList/" + locationId + "/" + languageId);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getAccomodationsList/" + locationId + "/" + languageId);
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

					// if (baseDatabase.display("Accomodation_Content") == 0) {
					//
					// dbbHelper.insertAccomodationContent("1",
					// "Accomodation", result);
					//
					// } else {
					//
					// dbbHelper.updateAccomodationContent("Accomodation",
					// result);
					//
					// }

					// if (baseDatabase.checkCategoryId(tableNameStr,
					// String.valueOf(locationId)) == 0) {

					dbbHelper.insertAccomodationContent(languageId, locationId,
							"Accomodation", result);
					//
					// Toast.makeText(getActivity(), locationId,
					// Toast.LENGTH_SHORT).show();
					// } else {
					//
					// dbbHelper.updateAccomodationContent("Accomodation",
					// result, locationId);
					//
					// }

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj
							.getJSONArray("stayplace");

					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.setLength(0);

					for (int i = 0; i < jsonArray.length(); i++) {

						stringBuilder.append(","
								+ jsonArray.getJSONObject(i).get("new_id")
										.toString());

						new AccomodationsDetailsTask(locationId,
								"AccomodationDetailContent",
								"getAccomodationDetails", jsonArray
										.getJSONObject(i).get("id").toString()
										.trim(), languageId).execute();
					}

					SplashScreen.hotel_editor.putString(
							SplashScreen.GET_HOTELS_IDS,
							""
									+ stringBuilder.toString()
											.replaceFirst(",", "").toString());
					SplashScreen.hotel_editor.commit();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	class AccomodationsDetailsTask extends AsyncTask<String, Void, String> {
		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				locationId = "", languageId = "";

		public AccomodationsDetailsTask(String locId, String tableName,
				String methodName, String moduleId, String lang_id) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
			this.languageId = lang_id;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + languageId);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + languageId);
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

					// Toast.makeText(BackgroundService.this,
					// "Check Id " +
					// baseDatabase.checkModuleId("DestinationDetailContent",
					// moduleStr), Toast.LENGTH_LONG).show();
					// if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
					// locationId) == 0) {
					//
					dbbHelper.insertAccomodationDetailsContent(tableNameStr,
							locationId, moduleStr, result, languageId);
					//
					// } else {

					// dbbHelper.updateAccomodationDetailsContent(tableNameStr,
					// moduleStr, result, languageId);

					Toast.makeText(getActivity(), moduleStr, Toast.LENGTH_SHORT)
							.show();

					// }
				} catch (Exception e1) {
					e1.printStackTrace();
					Toast.makeText(getActivity(), "" + e1, Toast.LENGTH_SHORT)
							.show();
				}

			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	class CulturesTask extends AsyncTask<String, Void, String> {

		String languageId;

		public CulturesTask(String langid) {
			// TODO Auto-generated constructor stub
			this.languageId = langid;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getCultureList/" + languageId);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getCultureList/" + languageId);
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

					// if (baseDatabase.display("Culture_Content") == 0) {

					dbbHelper.insertCultureContent("1", "Culture", result);

					// } else {
					//
					// dbbHelper.updateCultureContent("Culture", result,
					// languageId);
					//
					// }

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj.getJSONArray("Culture");

					for (int i = 0; i < jsonArray.length(); i++) {

						new CulturDetailsTask("1", "CultureDeatilContent",
								"getCultureDetails", jsonArray.getJSONObject(i)
										.get("id").toString().trim(),
								languageId).execute();
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	class CulturDetailsTask extends AsyncTask<String, Void, String> {
		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				locationId = "", languageId = "";

		public CulturDetailsTask(String locId, String tableName,
				String methodName, String moduleId, String lang_id) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
			this.languageId = lang_id;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + languageId);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ methodNameStr + "/" + moduleStr + "/" + languageId);
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

					dbbHelper.insertCultureDetailsContent(tableNameStr,
							locationId, moduleStr, result, languageId);

					// Toast.makeText(getActivity(), languageId,
					// Toast.LENGTH_SHORT).show();

					// } else {
					//
					// dbbHelper.updateCultureDetailsContent(tableNameStr,
					// moduleStr, result, locationId);
					//
					// }
				} catch (Exception e1) {
					e1.printStackTrace();
					Toast.makeText(getActivity(), "" + e1, Toast.LENGTH_SHORT)
							.show();
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

			JSONObject jsonResultObj = new JSONObject(result);

			JSONArray jsonArray = jsonResultObj.getJSONArray("stayplace");

			for (int i = 0; i < jsonArray.length(); i++) {

				ItemsObj objItem = new ItemsObj();
				objItem.setDest_id(jsonArray.getJSONObject(i).get("id")
						.toString().trim());
				objItem.setDestnew_id(jsonArray.getJSONObject(i).get("new_id")
						.toString().trim());
				objItem.setDest_title(jsonArray.getJSONObject(i).get("title")
						.toString().trim());
				objItem.setDest_addr(jsonArray.getJSONObject(i).get("address")
						.toString().trim());
				try {
					if (jsonArray.getJSONObject(i).getJSONArray("image_path")
							.length() > 0) {
						objItem.setDest_imgPath(jsonArray.getJSONObject(i)
								.getJSONArray("image_path").get(0).toString());
					}
				} catch (JSONException e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				objItem.setDest_latt(jsonArray.getJSONObject(i).get("latitude")
						.toString().trim());
				objItem.setDest_lng(jsonArray.getJSONObject(i).get("longitude")
						.toString().trim());

				if (jsonArray.getJSONObject(i).get("new_id").toString().trim()
						.equalsIgnoreCase("0")) {

				} else {
					MainActivity.dest_items_array.add(objItem);
				}

			}

			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				adapter = new DestinationListAdapter(getActivity(),
						R.layout.arbic_item_layout,
						MainActivity.dest_items_array);
			} else {
				adapter = new DestinationListAdapter(getActivity(),
						R.layout.item_layout, MainActivity.dest_items_array);
			}

			adapter.notifyDataSetChanged();
			lv.setAdapter(adapter);

			JSONObject jsonObject = new JSONObject(loadJSONFromAsset());

			JSONArray hotelsArray = jsonObject.getJSONArray("hoteltypes");

			hotelIdArray.clear();
			hotelNameArray.clear();
			hotelRoomTypesArray.clear();
			for (int i = 0; i < hotelsArray.length(); i++) {
				Log.e("Result : ",
						""
								+ hotelsArray.getJSONObject(i).getJSONArray(
										"room_types"));

				hotelIdArray.add(hotelsArray.getJSONObject(i).getString(
						"unit_code"));
				hotelNameArray.add(hotelsArray.getJSONObject(i).getString(
						"unit_name"));
				hotelRoomTypesArray.add(hotelsArray.getJSONObject(i)
						.getJSONArray("room_types").toString());
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String loadJSONFromAsset() {
		String json = null;
		try {
			InputStream is = getActivity().getAssets().open("hoteltypes.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}

	private void jsonEventsParsing(String result) {
		// TODO Auto-generated method stub
		try {

			MainActivity.dest_items_array.clear();
			JSONObject jsonResultObj = new JSONObject(result);

			JSONArray jsonArray = jsonResultObj.getJSONArray("EventsList");

			for (int i = 0; i < jsonArray.length(); i++) {

				ItemsObj objItem = new ItemsObj();
				objItem.setDest_id(jsonArray.getJSONObject(i).get("id")
						.toString().trim());
				objItem.setDest_title(jsonArray.getJSONObject(i).get("title")
						.toString().trim());
				objItem.setDest_addr(jsonArray.getJSONObject(i).get("address")
						.toString().trim());

				objItem.setDest_imgPath(jsonArray.getJSONObject(i)
						.get("image_path").toString().trim());
				objItem.setDest_latt(jsonArray.getJSONObject(i).get("latitude")
						.toString().trim());
				objItem.setDest_lng(jsonArray.getJSONObject(i).get("longitude")
						.toString().trim());

				MainActivity.dest_items_array.add(objItem);
			}

			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				adapter = new DestinationListAdapter(getActivity(),
						R.layout.arbic_item_layout,
						MainActivity.dest_items_array);
			} else {
				adapter = new DestinationListAdapter(getActivity(),
						R.layout.item_layout, MainActivity.dest_items_array);
			}

			adapter.notifyDataSetChanged();
			lv.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void jsonCultureParsing(String result) {
		// TODO Auto-generated method stub
		try {

			MainActivity.dest_items_array.clear();
			JSONObject jsonResultObj = new JSONObject(result);

			JSONArray jsonArray = jsonResultObj.getJSONArray("Culture");

			for (int i = 0; i < jsonArray.length(); i++) {

				ItemsObj objItem = new ItemsObj();
				objItem.setDest_id(jsonArray.getJSONObject(i).get("id")
						.toString().trim());
				objItem.setDest_title(jsonArray.getJSONObject(i).get("title")
						.toString().trim());
				// objItem.setDest_addr(jsonArray.getJSONObject(i).get("address")
				// .toString().trim());
				objItem.setDest_imgPath(jsonArray.getJSONObject(i)
						.get("image_path").toString().trim());
				// objItem.setDest_latt(jsonArray.getJSONObject(i).get("latitude")
				// .toString().trim());
				// objItem.setDest_lng(jsonArray.getJSONObject(i).get("longitude")
				// .toString().trim());

				MainActivity.dest_items_array.add(objItem);
			}

			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				adapter = new DestinationListAdapter(getActivity(),
						R.layout.arbic_item_layout,
						MainActivity.dest_items_array);
			} else {
				adapter = new DestinationListAdapter(getActivity(),
						R.layout.item_layout, MainActivity.dest_items_array);
			}

			adapter.notifyDataSetChanged();
			lv.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void jsonShoppingParsing(String result) {
		// TODO Auto-generated method stub
		try {

			MainActivity.dest_items_array.clear();
			JSONObject jsonResultObj = new JSONObject(result);

			JSONArray jsonArray = jsonResultObj.getJSONArray("Attractions");

			for (int i = 0; i < jsonArray.length(); i++) {

				ItemsObj objItem = new ItemsObj();
				objItem.setDest_id(jsonArray.getJSONObject(i).get("id")
						.toString().trim());
				objItem.setDest_title(jsonArray.getJSONObject(i).get("title")
						.toString().trim());
				// listing_data.add(jsonArray.getJSONObject(i).get("title")
				// .toString().trim());
				objItem.setDest_addr(jsonArray.getJSONObject(i).get("address")
						.toString().trim());
				objItem.setDest_imgPath(jsonArray.getJSONObject(i)
						.get("image_path").toString().trim());
				objItem.setDest_latt(jsonArray.getJSONObject(i).get("latitude")
						.toString().trim());
				objItem.setDest_lng(jsonArray.getJSONObject(i).get("longitude")
						.toString().trim());

				MainActivity.dest_items_array.add(objItem);
			}

			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				adapter = new DestinationListAdapter(getActivity(),
						R.layout.arbic_item_layout,
						MainActivity.dest_items_array);
			} else {
				adapter = new DestinationListAdapter(getActivity(),
						R.layout.item_layout, MainActivity.dest_items_array);
			}

			adapter.notifyDataSetChanged();
			lv.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// class DestinatioTask extends AsyncTask<String, Void, String> {
	//
	// ProgressDialog pDialog;
	//
	// @Override
	// protected String doInBackground(String... params) {
	//
	// return ServiceCalls1
	// .getJSONString("http://172.16.0.49/telangana_tourism/WebServices/getDestinations");
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// super.onPostExecute(result);
	//
	// if (null == result || result.length() == 0) {
	// if (null != pDialog && pDialog.isShowing()) {
	// pDialog.dismiss();
	// }
	// Toast.makeText(getActivity(), "No data found from Server!!!",
	// Toast.LENGTH_LONG).show();
	//
	// } else {
	// try {
	// // AppConstant.strEvntsRes = result;
	// // parseData(result);
	//
	// // Toast.makeText(getActivity(), result,
	// // Toast.LENGTH_LONG).show();
	//
	// if (null != pDialog && pDialog.isShowing()) {
	// pDialog.dismiss();
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// }
	//
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	//
	// pDialog = new ProgressDialog(getActivity());
	// pDialog.setMessage("Loading...");
	// pDialog.setCancelable(false);
	// pDialog.show();
	// }
	// }

	class EventsTask extends AsyncTask<String, Void, String> {
		ProgressDialog myProgressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			myProgressDialog = new ProgressDialog(getActivity());
			myProgressDialog.setMessage("please wait ...");
			myProgressDialog.setCancelable(false);
			myProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getEventsList/" + Constants.selectLanguage);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getEventsList/" + Constants.selectLanguage);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {
				Toast.makeText(getActivity(), "No data found from Server!!!",
						Toast.LENGTH_LONG).show();

			} else {

				try {

					jsonEventsParsing(result);
					if (myProgressDialog.isShowing())
						myProgressDialog.dismiss();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	public class DestinationListAdapter extends ArrayAdapter<ItemsObj> {

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

		public DestinationListAdapter(Activity act, int resource,
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

			DecimalFormat df = new DecimalFormat("#.#");

			if (Utility.checkInternetConnection(getActivity())) {
				if (Constants.sublistFlag == 3) {
					holder.distancee.setText(df.format(utils.distance(
							Constants.latitude, Constants.longitude,
							Double.parseDouble(objBean.getDest_latt()),
							Double.parseDouble(objBean.getDest_lng()), 'K'))
							+ " KMS");

					holder.locationTxt.setText(URLDecoder.decode(objBean
							.getDest_addr()));
					holder.locationTxt
							.setTypeface(Constants.ProximaNova_Regular);
				} else if (Constants.sublistFlag == 4) {
					holder.distancee.setText(df.format(utils.distance(
							Constants.latitude, Constants.longitude,
							Double.parseDouble(objBean.getDest_latt()),
							Double.parseDouble(objBean.getDest_lng()), 'K'))
							+ " KMS");

					holder.locationTxt.setText(URLDecoder.decode(objBean
							.getDest_addr()));
					holder.locationTxt
							.setTypeface(Constants.ProximaNova_Regular);
				} else if (Constants.sublistFlag == 5) {
					holder.distancee.setText("");
					holder.locationTxt.setText("");
				} else if (Constants.sublistFlag == 6) {
					holder.distancee.setText(df.format(utils.distance(
							Constants.latitude, Constants.longitude,
							Double.parseDouble(objBean.getDest_latt()),
							Double.parseDouble(objBean.getDest_lng()), 'K'))
							+ " KMS");

					holder.locationTxt.setText(URLDecoder.decode(objBean
							.getDest_addr()));
					holder.locationTxt
							.setTypeface(Constants.ProximaNova_Regular);
				}

			} else {
				if (Constants.sublistFlag == 5) {
					holder.locationTxt.setText("");
					// holder.locationTxt.setTypeface(Constants.ProximaNova_Regular);
				} else {
					holder.locationTxt.setText(URLDecoder.decode(objBean
							.getDest_addr()));
					holder.locationTxt
							.setTypeface(Constants.ProximaNova_Regular);
				}

				holder.distancee.setVisibility(View.GONE);
			}

			holder.category.setText(URLDecoder.decode(objBean.getDest_title()));
			holder.category.setTypeface(Constants.ProximaNova_Regular);
			holder.distancee.setTypeface(Constants.ProximaNova_Regular);
			// if (fontChaning == 1) {
			// holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
			// holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			// holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			// } else if (fontChaning == 2) {
			// holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
			// holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			// holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			// } else if (fontChaning == 3) {
			// holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
			// holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			// holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			// } else if (fontChaning == 0) {
			// holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			// holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			// holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			// }

			if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID,
					"").equalsIgnoreCase("1")) {
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			} else if (SplashScreen.fontpref.getString(
					SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			} else if (SplashScreen.fontpref.getString(
					SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			}

			// holder.txtName.setText(objBean.getStr_title());
			// holder.txtDetails.setText("Product: "+objBean.getStr_titl1().replace("...",
			// "")+" ...");
			//
			if (objBean.getDest_imgPath().length() > 0) {
				// androidAQuery.id(holder.iv).image(objBean.getDest_imgPath(),
				// true, true);

				// if (objBean.getDest_imgPath().contains("http")) {

				// if (utility.IsNetConnected(getActivity())) {
				// Picasso.with(getActivity())
				// .load(objBean.getDest_imgPath()
				// .replace("\'", "%20").trim()).noFade()
				// .error(R.drawable.default_img).into(holder.iv);
				//
				// } else {

				// if (Constants.sublistFlag == 4) {
				// Picasso.with(getActivity())
				// .load(objBean.getDest_imgPath()
				// .replace("\'", "%20").trim()).noFade()
				// .error(R.drawable.default_img).into(holder.iv);

				ImageLoader imageLoader = ImageLoader.getInstance();
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.cacheInMemory(true).cacheOnDisc(true)
						.resetViewBeforeLoading(true)
						.showImageForEmptyUri(R.drawable.default_img)
						.showImageOnFail(R.drawable.default_img)
						.showImageOnLoading(R.drawable.default_img).build();

				// download and display image from url
				imageLoader.displayImage(
						objBean.getDest_imgPath().replace("\'", "%20").trim(),
						holder.iv, options);
				// } else {
				// try {
				// String url = objBean.getDest_imgPath().replace(
				// "\'", "");
				//
				// String replacdUrl = url.replace(
				// "http://ttourdev.etisbew.net/images/", "");
				// // get input stream
				// InputStream ims = getActivity().getAssets().open(
				// replacdUrl.trim());
				// // load image as Drawable
				// Drawable d = Drawable.createFromStream(ims, null);
				// // set image to ImageView
				// holder.iv.setImageDrawable(d);
				// } catch (IOException ex) {
				//
				// }
				// }
				//
				// }
				// } else {
				// try {
				// // get input stream
				//
				// String url = objBean.getDest_imgPath()
				// .replace("\'", "");
				//
				// String replacdUrl = url.replace(
				// "http://ttourdev.etisbew.net/images/", "");
				//
				// InputStream ims = getActivity().getAssets().open(
				// replacdUrl.trim());
				// // load image as Drawable
				// Drawable d = Drawable.createFromStream(ims, null);
				// // set image to ImageView
				// holder.iv.setImageDrawable(d);
				// } catch (IOException ex) {
				//
				// }
				// }
				/*
				 * if (Constants.sublistFlag == 4) { Picasso.with(getActivity())
				 * .load(objBean.getDest_imgPath().replace("\'", "%20")
				 * .trim()).noFade().error(R.drawable.default_img)
				 * .into(holder.iv); }else{ try { // get input stream
				 * InputStream ims =
				 * getActivity().getAssets().open(objBean.getDest_imgPath()); //
				 * load image as Drawable Drawable d =
				 * Drawable.createFromStream(ims, null); // set image to
				 * ImageView holder.iv.setImageDrawable(d); } catch(IOException
				 * ex) {
				 * 
				 * } }
				 */

			}

			return convertView;
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
		adapter = new DestinationListAdapter(getActivity(),
				R.layout.item_layout, MainActivity.dest_items_array);
		adapter.notifyDataSetChanged();

		lv.setAdapter(adapter);

	}

	class GetHotelsId extends AsyncTask<String, Void, String> {

		String tableNameStr = "", locationId = "", languageId = "";

		public GetHotelsId(String locId, String langid) {
			// TODO Auto-generated constructor stub
			this.locationId = locId;
			this.languageId = langid;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getAccomodationsList/" + locationId + "/" + languageId);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getAccomodationsList/" + locationId + "/" + languageId);
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

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj
							.getJSONArray("stayplace");

					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.setLength(0);

					for (int i = 0; i < jsonArray.length(); i++) {

						stringBuilder.append(","
								+ jsonArray.getJSONObject(i).get("new_id")
										.toString());

					}

					Toast.makeText(
							getActivity(),
							"" + stringBuilder.toString().replaceFirst(",", ""),
							Toast.LENGTH_LONG).show();

					SplashScreen.hotel_editor.putString(
							SplashScreen.GET_HOTELS_IDS,
							""
									+ stringBuilder.toString()
											.replaceFirst(",", "").toString());
					SplashScreen.hotel_editor.commit();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	}

	// public class CustomAdapter extends BaseAdapter {
	//
	// ArrayList<EventsVariables> list;
	//
	// LayoutInflater mInflater;
	//
	// public CustomAdapter(Context context,
	// ArrayList<EventsVariables> events_upcoming) {
	// mInflater = LayoutInflater.from(context);
	//
	// this.list = events_upcoming;
	//
	// }
	//
	// @Override
	// public int getCount() {
	// return list.size();
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// return position;
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// @Override
	// public View getView(final int position, View convertView,
	// ViewGroup parent) {
	//
	// // int pos = list.get(position);
	// // System.out.println("list size" + list.size() + list + pos);
	// UserHolder1 holder = null;
	// if (convertView == null) {
	// convertView = mInflater.inflate(R.layout.item_layout, parent,
	// false);
	// holder = new UserHolder1();
	//
	// holder.category = (TextView) convertView
	// .findViewById(R.id.category);
	// holder.event_title = (TextView) convertView
	// .findViewById(R.id.event_title);
	// holder.locationTxt = (TextView) convertView
	// .findViewById(R.id.locationTxt);
	// // holder.event_time = (TextView)
	// // convertView.findViewById(R.id.event_time);
	// holder.event_desc = (TextView) convertView
	// .findViewById(R.id.event_desc);
	// holder.iv = (ImageView) convertView
	// .findViewById(R.id.item_icon);
	//
	// convertView.setTag(holder);
	// } else {
	// holder = (UserHolder1) convertView.getTag();
	// }
	// // holder.event_title.setTypeface(event.getTextBold());
	// // holder.event_date.setTypeface(event.getTextBold());
	// // // holder.event_time.setTypeface(event.getTextNormal());
	// // holder.event_desc.setTypeface(event.getTextNormal());
	//
	// EventsVariables eve1 = events_upcoming.get(position);
	//
	// // holder.category.setText(eve1.category);
	// holder.category.setText(titles.get(position));
	// System.out.println("eve category title" + eve1.category
	// + eve1.title);
	// // holder.event_time.setText(eve1.endDate.replace("T", " "));
	// Utility util = new Utility(getActivity());
	// // holder.event_date.setText(util.dateConvert(eve1.startDate,
	// // eve1.endDate));
	// holder.locationTxt.setText(eve1.venue);
	// // androidAQuery.id(holder.iv).image(eve1.thumbnail, true, true,
	// // AQuery.FADE_IN, position, null, 0, 0);
	//
	// holder.iv.setImageResource(img.get(position));
	// return convertView;
	// }
	//
	// class UserHolder1 {
	// TextView category;
	// TextView event_title;
	// TextView locationTxt;
	// TextView event_time;
	// TextView event_desc;
	// ImageView iv;
	// }
	// }

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
