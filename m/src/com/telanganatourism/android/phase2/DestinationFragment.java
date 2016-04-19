package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
import com.telanganatourism.android.phase2.database.helper.TtHelper;
import com.telanganatourism.android.phase2.objects.ItemsObj;
import com.telanganatourism.android.phase2.util.Utility;

@SuppressWarnings("unused")
public class DestinationFragment extends Fragment {
	ArrayList<Integer> list;
	// EventBean event;
	ArrayList<EventsVariables> events_upcoming;
	ArrayList<Integer> id_list;
	AQuery androidAQuery;
	DestinationListAdapter adapter;
	ListView lv;

	List<String> ids = new ArrayList<String>();
	List<String> tit = new ArrayList<String>();
	List<String> img = new ArrayList<String>();

	int fontChaning = 0;

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

//	int iterator, language;

	public DestinationFragment() {

	}

	int upcomingeventsLength = 0;

	JSONArray jsonArray;

	private final Handler handler = new Handler();
	private Drawable oldBackground = null;

	TtHelper dbbHelper;
	Cursor constantCursor = null;

	String responseString;

	Utility utility;

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_whats_hot,
				container, false);

		utility = new Utility(getActivity());

		Constants.dest_items_array1 = new ArrayList<ItemsObj>();
		Constants.dest_items_array2 = new ArrayList<ItemsObj>();

		lv = (ListView) rootView.findViewById(R.id.listView1);
		lv.setEmptyView(rootView.findViewById(android.R.id.empty));

		try {
			dbbHelper = new TtHelper(getActivity());
			dbbHelper.openDataBase();

			// if (Constants.destinations_flag == true) {

			// Constants.destinations_flag = false;
			// for (language = 1; language <= 4; language++) {

			// String deleteQuery =
			// "DELETE FROM  DestinationCategoryList where language_id='"
			// + "4" + "'";
			//
			// String deleteQuery1 =
			// "DELETE FROM  Destination_Content where language_id='"
			// + "4" + "'";
			//
			// String deleteQuery2 =
			// "DELETE FROM  Destinations_Detail_Content where language_id='"
			// + "4" + "'";
			//
			// SQLiteDatabase database = dbbHelper
			// .getWritableDatabase();
			//
			//
			// Log.d("query", deleteQuery);
			// database.execSQL(deleteQuery);

			// dbbHelper.deleteAll(deleteQuery);
			// dbbHelper.deleteAll(deleteQuery1);
			// dbbHelper.deleteAll(deleteQuery2);

			// for (iterator = 1; iterator <= 10; iterator++) {
			// new DestinationCategoryTask("DestinationCategoryList",
			// iterator, String.valueOf(4)).execute();
			//
			// }
			// }
			// }

			Constants.dest_items_array1.clear();
			ids.clear();
			tit.clear();
			img.clear();
			
			// Setting location default value with 1
			if(Constants.locationCode.isEmpty() || TextUtils.isEmpty(Constants.locationCode)){
				Constants.locationCode = "1";
			}

			String[] splitedStr = Constants.locationCode.split(",");
			/* print substrings */
			ArrayList<String> aListNumbers = new ArrayList<String>(
					Arrays.asList(splitedStr));

			for (int i = 0; i < aListNumbers.size(); i++) {
				if (aListNumbers.get(i).equalsIgnoreCase("1")) {
					MainActivity.hydFlag = true;
//					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//						MainActivity.hydTxt
//								.setCompoundDrawablesWithIntrinsicBounds(
//										getActivity()
//												.getResources()
//												.getDrawable(
//														R.drawable.check_selected),
//										null, null, null);
//					} else {
						MainActivity.hydTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
//					}

				} else if (aListNumbers.get(i).equalsIgnoreCase("2")) {
					MainActivity.warangalFlag = true;

//					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//						MainActivity.warngalTxt
//								.setCompoundDrawablesWithIntrinsicBounds(
//										getActivity()
//												.getResources()
//												.getDrawable(
//														R.drawable.check_selected),
//										null, null, null);
//					} else {
						MainActivity.warngalTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
//					}

				} else if (aListNumbers.get(i).equalsIgnoreCase("3")) {
					MainActivity.karimnagarFlag = true;
//					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//						MainActivity.karimnagarTxt
//								.setCompoundDrawablesWithIntrinsicBounds(
//										getActivity()
//												.getResources()
//												.getDrawable(
//														R.drawable.check_selected),
//										null, null, null);
//					} else {
						MainActivity.karimnagarTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
//					}

				} else if (aListNumbers.get(i).equalsIgnoreCase("4")) {
					MainActivity.khamammFlag = true;

//					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//						MainActivity.khammTxt
//								.setCompoundDrawablesWithIntrinsicBounds(
//										getActivity()
//												.getResources()
//												.getDrawable(
//														R.drawable.check_selected),
//										null, null, null);
//					} else {
						MainActivity.khammTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
//					}

				} else if (aListNumbers.get(i).equalsIgnoreCase("5")) {
					MainActivity.medakFlag = true;
//					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//						MainActivity.medakTxt
//								.setCompoundDrawablesWithIntrinsicBounds(
//										getActivity()
//												.getResources()
//												.getDrawable(
//														R.drawable.check_selected),
//										null, null, null);
//					} else {
						MainActivity.medakTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
//					}

				} else if (aListNumbers.get(i).equalsIgnoreCase("6")) {
					MainActivity.nalgondaFlag = true;
//					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//						MainActivity.nlgTxt
//								.setCompoundDrawablesWithIntrinsicBounds(
//										getActivity()
//												.getResources()
//												.getDrawable(
//														R.drawable.check_selected),
//										null, null, null);
//					} else {
						MainActivity.nlgTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
//					}

				} else if (aListNumbers.get(i).equalsIgnoreCase("7")) {
					MainActivity.nizambadFlag = true;
//					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//						MainActivity.nizamabadTxt
//								.setCompoundDrawablesWithIntrinsicBounds(
//										getActivity()
//												.getResources()
//												.getDrawable(
//														R.drawable.check_selected),
//										null, null, null);
//					} else {
						MainActivity.nizamabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
//					}

				} else if (aListNumbers.get(i).equalsIgnoreCase("8")) {
					MainActivity.adilabadFlag = true;
//					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//						MainActivity.adilabadTxt
//								.setCompoundDrawablesWithIntrinsicBounds(
//										getActivity()
//												.getResources()
//												.getDrawable(
//														R.drawable.check_selected),
//										null, null, null);
//					} else {
						MainActivity.adilabadTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
//					}

				} else if (aListNumbers.get(i).equalsIgnoreCase("9")) {
					MainActivity.mbnrFlag = true;
//					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//						MainActivity.mbnrTxt
//								.setCompoundDrawablesWithIntrinsicBounds(
//										getActivity()
//												.getResources()
//												.getDrawable(
//														R.drawable.check_selected),
//										null, null, null);
//					} else {
						MainActivity.mbnrTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
//					}

				} else if (aListNumbers.get(i).equalsIgnoreCase("10")) {
					MainActivity.rangareddyFlag = true;
//					if (Constants.selectLanguage.equalsIgnoreCase("3")) {
//						MainActivity.rangareddyTxt
//								.setCompoundDrawablesWithIntrinsicBounds(
//										getActivity()
//												.getResources()
//												.getDrawable(
//														R.drawable.check_selected),
//										null, null, null);
//					} else {
						MainActivity.rangareddyTxt
								.setCompoundDrawablesWithIntrinsicBounds(
										null,
										null,
										getActivity()
												.getResources()
												.getDrawable(
														R.drawable.check_selected),
										null);
//					}

				}
			}

			for (int i = 0; i < aListNumbers.size(); i++) {
				String select = " SELECT * FROM DestinationCategoryList WHERE id="
						+ aListNumbers.get(i)
						+ " AND language_id="
						+ Constants.selectLanguage;
				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
						select, null);

				getActivity().startManagingCursor(constantCursor);

				if (constantCursor.getCount() > 0) {
					if (constantCursor.moveToFirst()) {
						// do {

						responseString = constantCursor
								.getString(constantCursor
										.getColumnIndex("ResponseContent"));

						jsonParsing(responseString);

						// } while (constantCursor.moveToNext());

					}

				}
			}

			LinkedHashSet<String> Lnk_ids = new LinkedHashSet<String>();
			LinkedHashSet<String> Lnk_tit = new LinkedHashSet<String>();
			LinkedHashSet<String> Lnk_img = new LinkedHashSet<String>();

			Lnk_ids.addAll(ids);
			Lnk_tit.addAll(tit);
			Lnk_img.addAll(img);

			// Removing ArrayList elements
			ids.clear();
			tit.clear();
			img.clear();

			// Adding LinkedHashSet elements to the ArrayList
			ids.addAll(Lnk_ids);
			tit.addAll(Lnk_tit);
			img.addAll(Lnk_img);

			for (int j = 0; j < ids.size(); j++) {
				ItemsObj objItem = new ItemsObj();
				objItem.setDest_cat_id(ids.get(j).toString().trim());
				objItem.setDest_cat_title(tit.get(j).toString().trim());
				objItem.setDest_cat_imgPath(img.get(j).toString().trim());

				Constants.dest_items_array1.add(objItem);
			}

			if (Constants.selectLanguage.equalsIgnoreCase("3")) {
				adapter = new DestinationListAdapter(getActivity(),
						R.layout.arbic_item_layout, Constants.dest_items_array1);
			} else {
				adapter = new DestinationListAdapter(getActivity(),
						R.layout.item_layout, Constants.dest_items_array1);
			}

			adapter.notifyDataSetChanged();
			lv.setAdapter(adapter);

			// dbbHelper.close();

			// dbbHelper.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Shopping
		// for (language = 1; language <= 4; language++) {
		//
		// new DestinationTask("Shop_Online",
		// "getDestinationsList", "7", "1", String.valueOf(language))
		// .execute();
		//
		// }

		MainActivity.txt_logo.setVisibility(View.GONE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setTextColor(Color.parseColor("#ffffff"));

		MainActivity.txt_title.setText(MainActivity.changeTitle);
		
		MainActivity.txt_title.setTypeface(Constants.ProximaNova_Regular);

		MainActivity.txt_filter.setVisibility(View.GONE);

		MainActivity.txt_location.setVisibility(View.VISIBLE);

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

		if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID, "")
				.equalsIgnoreCase("1")) {
			// adapter.notifyDataSetChanged();
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
			// adapter.notifyDataSetChanged();
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
			// adapter.notifyDataSetChanged();
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

					DestinationFragment fragment = new DestinationFragment();

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

		// titles.clear();
		// loc.clear();
		// img.clear();
		// dec.clear();

		// MainActivity.getRightMenuList(getActivity(), whrdo);

		list = new ArrayList<Integer>();
		id_list = new ArrayList<Integer>();
		// event = (EventBean) getActivity().getApplicationContext();
		androidAQuery = new AQuery(getActivity());
		events_upcoming = new ArrayList<EventsVariables>();
		list.clear();
		id_list.clear();

		// new UpComingEvents().execute();
		// calendar_dates.clear();

		// titles.add("DIVINE DESTINATIONS");
		// titles.add("HERITAGE SPOTS");
		// titles.add("NATURE DISCOVERY");
		// titles.add("WILD LIFE");
		//
		// // loc.add("Hyderabad, Telangana");
		// // loc.add("Hyderabad, Telangana");
		// // loc.add("Hyderabad, Telangana");
		// // loc.add("Hyderabad, Telangana");
		// //
		// img.add(R.drawable.divinedest);
		// img.add(R.drawable.heritage);
		// img.add(R.drawable.nature);
		// img.add(R.drawable.wild);
		// //
		// //
		// dec.add("The Charminar, built in 1591 CE, is a monument and mosque located in Hyderabad, Telangana, India. The landmark has become a global icon of Hyderabad, listed among the most recognized structures of India. The Charminar is situated on the east bank of Musi river. To the west lies the Laad Bazaar, and to the southwest lies the richly ornamented granite Makkah Masjid. The English name is a translation and combination of the Urdu words Char and Minar, translating to 'Four Towers the eponymous towers are ornate minarets attached and supported by four grand arches. Some of the popular myths that are recorded in accord with the monument's architectural appearance are as follows.");
		// //
		// dec.add("Birla Mandir is a Hindu temple, built on a 280 feet (85 m) high hillock called Naubath Pahad on a 13 acres (53,000 m2) plot. The construction took 10 years and was constructed in 1976 by Swami Ranganathananda of Ramakrishna Mission. The temple was constructed by Birla Foundation, which has also constructed several similar temples across India");
		// //
		// dec.add("Falaknuma Palace is one of the finest palaces in Hyderabad, Telangana, India. It belonged to Paigah Hyderabad State, and it was later owned by the Nizams.[1] It is on a 32-acre (13 ha) area in Falaknuma, 5 km from Charminar. It was built by Nawab Vikar-ul-Umra, prime minister of Hyderabad and the uncle and brother-in-law of the Nizam VI, Nawab Mir Mahboob Ali Khan Bahadur.[2] Falak-numa means 'Like the Sky' or 'Mirror of the Sky' in Urdu");
		// //
		// dec.add("Golconda, also known as Golkonda or Golla konda ('shepherd's hill'), a ruined fort of Southern India and capital of medieval Golconda Sultanate (c.1518–1687), is situated 11 km west of Hyderabad. It is also a mandal of Hyderabad District. The region is known for the mines that have produced some of the world's most famous gems, including the Hope Diamond and the Nassak ");
		//
		// for (int j = 0; j < titles.size(); j++) {
		//
		// EventsVariables eventsBean = new EventsVariables();
		//
		// eventsBean.title = titles.get(j);
		// // eventsBean.venue = loc.get(j);
		//
		// // eventsBean.title = "Charminar";
		//
		// // eventsBean.startDate = jsonObject1
		// // .getString("StartDate");
		// // eventsBean.endDate = jsonObject1
		// // .getString("EndDate");
		// // eventsBean.venue ="Hyderabad, Telangana";
		// // eventsBean.category = jsonObject1
		// // .getString("Category");
		// // eventsBean.thumbnail = jsonObject1
		// // .getString("Thumbnail");
		// events_upcoming.add(eventsBean);
		// event.setObject(events_upcoming);
		//
		// }

		// adapter = new CustomAdapter(getActivity(), events_upcoming);
		// adapter.notifyDataSetChanged();
		// lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				// EventsVariables eve1 = events_upcoming.get(arg2);
				// Constants.imgs = img.get(arg2);
				// Constants.event_title = eve1.title;
				// Constants.description = dec.get(arg2);
				// startActivity(new Intent(getActivity(),
				// DetailScreen1.class));

				// if(arg2 == 0){
				// final FragmentTransaction ft = getFragmentManager()
				// .beginTransaction();
				// ft.replace(R.id.frame_container, new
				// DestinationSubFragment(),
				// "NewFragmentTag");
				// ft.commit();
				// }else{
				// final FragmentTransaction ft = getFragmentManager()
				// .beginTransaction();
				// ft.replace(R.id.frame_container, new WhereToShopFragment(),
				// "NewFragmentTag");
				// ft.commit();
				// }
				Constants.searchCode = "0";
				Constants.sublistFlag = 1;
				ItemsObj itemsObj = (ItemsObj) Constants.dest_items_array1
						.get(arg2);
				Constants.destination_cat_id = itemsObj.getDest_cat_id();
				MainActivity.changeTitle = itemsObj.getDest_cat_title();
				startActivity(new Intent(getActivity(),
						SubFragmentActivity.class));

			}
		});

		return rootView;
	}

	private void jsonParsing(String result) {
		// TODO Auto-generated method stub
		try {

			JSONObject jsonResultObj = new JSONObject(result);

			JSONArray jsonArray = jsonResultObj.getJSONArray("SubCategory");

			for (int i = 0; i < jsonArray.length(); i++) {

				/*
				 * ItemsObj objItem = new ItemsObj();
				 * objItem.setDest_cat_id(jsonArray.getJSONObject(i).get("id")
				 * .toString().trim());
				 * objItem.setDest_cat_title(jsonArray.getJSONObject(i)
				 * .get("title").toString().trim());
				 * objItem.setDest_cat_imgPath(jsonArray.getJSONObject(i)
				 * .get("image").toString().trim());
				 * 
				 * Constants.dest_items_array1.add(objItem);
				 */

				ids.add(jsonArray.getJSONObject(i).get("id").toString().trim());
				tit.add(jsonArray.getJSONObject(i).get("title").toString()
						.trim());
				img.add(jsonArray.getJSONObject(i).get("image").toString()
						.trim());
			}

			// // Converting ArrayList to HashSet to remove duplicates
			// HashSet<ItemsObj> listToSet = new
			// HashSet<ItemsObj>(Constants.dest_items_array1);
			//
			// // Creating Arraylist without duplicate values
			// List<ItemsObj> listWithoutDuplicates = new ArrayList<ItemsObj>(
			// listToSet);
			//
			// // Collections.sort(listWithoutDuplicates);
			// // Constants.dest_items_array2.clear();
			// // ********** Value after sorting **************
			// for (ItemsObj str : listWithoutDuplicates) {
			//
			// Constants.dest_items_array2.add(str);
			// }
			//
			//
			// for (int i = 0; i < Constants.dest_items_array2.size(); i++) {
			// ItemsObj objItem = new ItemsObj();
			// objItem.setDest_cat_id(jsonArray.getJSONObject(i).get("id")
			// .toString().trim());
			// objItem.setDest_cat_title(jsonArray.getJSONObject(i)
			// .get("title").toString().trim());
			// objItem.setDest_cat_imgPath(jsonArray.getJSONObject(i)
			// .get("image").toString().trim());
			//
			// Constants.dest_items_array2.add(objItem);
			// }

			/*
			 * if(Constants.selectLanguage.equalsIgnoreCase("3")){
			 * 
			 * 
			 * adapter = new DestinationListAdapter(getActivity(),
			 * R.layout.arbic_item_layout, Constants.dest_items_array1); }else{
			 * 
			 * 
			 * 
			 * adapter = new DestinationListAdapter(getActivity(),
			 * R.layout.item_layout, Constants.dest_items_array1); }
			 * 
			 * adapter.notifyDataSetChanged(); lv.setAdapter(adapter);
			 */

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static <T> void removeDuplicates(ArrayList<T> list) {
		int size = list.size();
		int out = 0;
		{
			final Set<T> encountered = new HashSet<T>();
			for (int in = 0; in < size; in++) {
				final T t = list.get(in);
				final boolean first = encountered.add(t);
				if (first) {
					list.set(out++, t);
				}
			}
		}
		while (out < size) {
			list.remove(--size);
		}
	}

	class DestinationCategoryTask extends AsyncTask<String, Void, String> {

		// private ProgressDialog progressDialog;

		String tblName = "", language_id;
		int locationId;

		public DestinationCategoryTask(String tableName, int locId,
				String lang_id) {
			// TODO Auto-generated constructor stub
			this.tblName = tableName;
			this.locationId = locId;
			this.language_id = lang_id;

		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			// progressDialog = new ProgressDialog(getActivity());
			// progressDialog.setMessage("Loading ...");
			// progressDialog.setIndeterminate(false);
			// progressDialog.setCancelable(false);
			// progressDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			
			System.out.println("url is"+SplashScreen.Base_url
					+ "getDestinationCategoriesList/" + locationId + "/"
					+ language_id);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getDestinationCategoriesList/" + locationId + "/"
					+ language_id);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {

				Toast.makeText(getActivity(), "No data found from Server!!!",
						Toast.LENGTH_LONG).show();

			} else {

				try {

					// dbbHelper = new TtHelper(BackgroundService.this);
					// dbbHelper.openDataBase();
					//
					// baseDatabase = new BaseDatabase(BackgroundService.this);
					// baseDatabase.openDataBase();
					//
					// // if (baseDatabase.display("DestinationCategoryList") ==
					// 0)
					// // {
					// //
					// //

					dbbHelper.insertDestinationCategory1(
							String.valueOf(locationId), result, language_id);

					// Toast.makeText(getActivity(), language_id, 200).show();
					// // } else {
					// //
					// dbbHelper.updateDestinationCategory(String.valueOf(locationId),
					// result);
					// //
					// // }
					//
					// if (baseDatabase.checkCategoryId(tblName,
					// String.valueOf(locationId)) == 0) {
					//
					// dbbHelper.insertDestinationCategory(
					// String.valueOf(locationId), result);
					// } else {
					//
					// dbbHelper.updateDestinationCategory(
					// String.valueOf(locationId), result);
					//
					// }

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj
							.getJSONArray("SubCategory");

					for (int i = 0; i < jsonArray.length(); i++) {

						new DestinationTask("Destination_Content",
								"getDestinationsList", jsonArray
										.getJSONObject(i).get("id").toString()
										.trim(), String.valueOf(locationId),
								language_id).execute();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					Toast.makeText(getActivity(), "Categories exception",
							Toast.LENGTH_LONG).show();
				}

				// dbbHelper.close();
				// baseDatabase.close();

				// if (null != progressDialog && progressDialog.isShowing()) {
				// progressDialog.dismiss();
				// }

			}

		}
	}

	class DestinationTask extends AsyncTask<String, Void, String> {

		// ProgressDialog progressDialog;

		String moduleStr = "", tableNameStr = "", methodNameStr = "",
				locationId = "", language_id;

		public DestinationTask(String tableName, String methodName,
				String moduleId, String locId, String lang_id) {
			// TODO Auto-generated constructor stub
			this.moduleStr = moduleId;
			this.tableNameStr = tableName;
			this.methodNameStr = methodName;
			this.locationId = locId;
			this.language_id = lang_id;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			// progressDialog = new ProgressDialog(getActivity());
			// progressDialog.setMessage("Loading ...");
			// progressDialog.setIndeterminate(false);
			// progressDialog.setCancelable(false);
			// progressDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getDestinationsList/" + locationId + "/" + moduleStr
					+ "/" + language_id);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getDestinationsList/" + locationId + "/" + moduleStr
					+ "/" + language_id);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {

				Toast.makeText(getActivity(), "No data found from Server!!!",
						Toast.LENGTH_LONG).show();

			} else {

				try {

					// if (baseDatabase.display("Destination_Content") == 0) {
					//
					// dbbHelper.insertDestinationContent("1", "Destination",
					// result);
					// Toast.makeText(BackgroundService.this,
					// "A row is inserted", Toast.LENGTH_LONG).show();
					// } else {
					// dbFlag = true;
					// dbbHelper.updateDestinationContent("Destination",
					// result);
					//
					// Toast.makeText(BackgroundService.this,
					// "A row is updated", Toast.LENGTH_LONG).show();
					//
					// }

					// if (baseDatabase.checkModuleId(tableNameStr, moduleStr,
					// locationId) == 0) {
					//

					dbbHelper.insertDestinationContent(locationId, moduleStr,
							result, language_id);

					// dbbHelper.insertShoppingContent(tableNameStr,
					// locationId, moduleStr, result, language_id);
					// } else {
					//
					// dbbHelper.updateDetailsContent(tableNameStr, moduleStr,
					// result, locationId, language_id);
					// //
					// Toast.makeText(getActivity(), moduleStr, 200).show();
					//
					// }

					JSONObject jsonResultObj = new JSONObject(result);

					JSONArray jsonArray = jsonResultObj
							.getJSONArray("Attractions");

					// destinationsDetailsArray = new ArrayList<String>();

					for (int i = 0; i < jsonArray.length(); i++) {

						// destinationsDetailsArray.add(jsonArray.getJSONObject(i)
						// .get("id").toString().trim());
						new DestinationDetails(locationId,
								"getDestinationsDetail", jsonArray
										.getJSONObject(i).get("id").toString()
										.trim(), language_id, moduleStr)
								.execute();
					}

					// dbbHelper.close();
					// baseDatabase.close();

				} catch (Exception e1) {
					e1.printStackTrace();

					Toast.makeText(getActivity(),
							"Destinations List exception", Toast.LENGTH_LONG)
							.show();
				}

				// if (null != progressDialog && progressDialog.isShowing()) {
				// progressDialog.dismiss();
				// }

			}

		}
	}

	class DestinationDetails extends AsyncTask<String, Void, String> {

		// ProgressDialog progressDialog;

		String categoryStr = "", module1Str = "", tableNameStr = "",
				methodNameStr = "", location1Id = "", language_id = "";

		public DestinationDetails(String locId, String methodName,
				String moduleId, String lang_id, String cat_id) {
			// TODO Auto-generated constructor stub
			this.location1Id = locId;
			this.module1Str = moduleId;
			this.methodNameStr = methodName;
			this.language_id = lang_id;
			this.categoryStr = cat_id;
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is"+SplashScreen.Base_url
					+ "getDestinationsDetail/" + location1Id + "/" + module1Str
					+ "/17.430186/78.405196/" + language_id);
			return ServiceCalls1.getJSONString(SplashScreen.Base_url
					+ "getDestinationsDetail/" + location1Id + "/" + module1Str
					+ "/17.430186/78.405196/" + language_id);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (null == result || result.length() == 0) {
				Toast.makeText(getActivity(), "No data found from Server!!!",
						Toast.LENGTH_LONG).show();
			} else {

				try {

					// Toast.makeText(BackgroundService.this,
					// "Check Id " +
					// baseDatabase.checkModuleId("DestinationDetailContent",
					// moduleStr), Toast.LENGTH_LONG).show();
					// if (baseDatabase
					// .checkModuleId(tableNameStr, moduleStr, locationId) == 0)
					// {

					// if(dbbHelper.myDataBase.isOpen())
					// {
					// Toast.makeText(getActivity(), "open", 200).show();
					// }else
					// {
					// Toast.makeText(getActivity(), "close", 200).show();
					// }

					// dbbHelper.insertDetailsContent1("Shop_Online_Details",location1Id,
					// module1Str, result, language_id);

					dbbHelper.insertDestinationDetailsContent(location1Id,
							module1Str, result, language_id);

					// dbbHelper.updateDestinationDetailsContent(module1Str,
					// result, location1Id);

					Toast.makeText(getActivity(), module1Str, Toast.LENGTH_LONG)
							.show();

					// } else {
					//
					// dbbHelper.updateDetailsContent(tableNameStr, moduleStr,
					// result, locationId);
					//
					// }
				} catch (Exception e1) {
					e1.printStackTrace();
					Toast.makeText(getActivity(), "exception",
							Toast.LENGTH_LONG).show();
				}

				// if (null != progressDialog && progressDialog.isShowing()) {
				// progressDialog.dismiss();
				// }

			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// progressDialog = new ProgressDialog(getActivity());
			// progressDialog.setMessage("Loading ...");
			// progressDialog.setIndeterminate(false);
			// progressDialog.setCancelable(false);
			// progressDialog.show();

		}
	}

	public class DestinationListAdapter extends ArrayAdapter<ItemsObj> {

		private Activity activity;
		private List<ItemsObj> items;
		private ItemsObj objBean;
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

		@SuppressLint("InflateParams")
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

			// DecimalFormat df = new DecimalFormat("#.#");
			//
			// holder.distancee.setText(df.format(utils.distance(
			// Double.parseDouble("17.3998"),
			// Double.parseDouble("78.4766"),
			// Double.parseDouble(objBean.getDest_latt()),
			// Double.parseDouble(objBean.getDest_lng()), 'K'))
			// + " KMS");
			holder.distancee.setVisibility(View.GONE);

			holder.locationTxt.setVisibility(View.GONE);
			holder.category.setText(objBean.getDest_cat_title());
			holder.category.setTypeface(Constants.ProximaNova_Regular);
			holder.locationTxt.setTypeface(Constants.ProximaNova_Regular);
			holder.distancee.setTypeface(Constants.ProximaNova_Regular);
			holder.event_desc.setTypeface(Constants.ProximaNova_Regular);
			if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID,
					"").equalsIgnoreCase("3")) {
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
			} else if (SplashScreen.fontpref.getString(
					SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			} else if (SplashScreen.fontpref.getString(
					SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")) {
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
			}

			// holder.locationTxt.setText(objBean.getDest_addr());

			// holder.txtName.setText(objBean.getStr_title());
			// holder.txtDetails.setText("Product: "+objBean.getStr_titl1().replace("...",
			// "")+" ...");
			//
			if (objBean.getDest_cat_imgPath().length() > 0) {

				// androidAQuery.id(holder.iv).image(
				// objBean.getDest_cat_imgPath(), true, true);
				// if (objBean.getDest_cat_imgPath().contains("http")) {

				// if (utility.IsNetConnected(getActivity())) {
				// Picasso.with(getActivity())
				// .load(objBean.getDest_cat_imgPath()
				// .replace("\'", "%20").trim()).noFade()
				// .error(R.drawable.default_img).into(holder.iv);

				// ImageLoader.getInstance().displayImage(objBean.getDest_cat_imgPath()
				// .replace("\'", "%20").trim(), holder.iv);

				ImageLoader imageLoader = ImageLoader.getInstance();
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.cacheInMemory(true).cacheOnDisc(true)
						.resetViewBeforeLoading(true)
						.showImageForEmptyUri(R.drawable.default_img)
						.showImageOnFail(R.drawable.default_img)
						.showImageOnLoading(R.drawable.default_img).build();

				// download and display image from url
				imageLoader.displayImage(
						objBean.getDest_cat_imgPath().replace("\'", "%20")
								.trim(), holder.iv, options);
				//
				// } else {
				// try {
				// String url = objBean.getDest_cat_imgPath().replace(
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
				// } else {
				// try {
				// // get input stream
				// InputStream ims = getActivity().getAssets().open(
				// objBean.getDest_cat_imgPath());
				// // load image as Drawable
				// Drawable d = Drawable.createFromStream(ims, null);
				// // set image to ImageView
				// holder.iv.setImageDrawable(d);
				// } catch (IOException ex) {
				//
				// }
				// }
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
		// adapter = new CustomAdapter(getActivity(), events_upcoming);
		// adapter.notifyDataSetChanged();
		//
		// lv.setAdapter(adapter);

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
	// convertView = mInflater.inflate(R.layout.item_layout1, parent,
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

	// class UpComingEvents extends AsyncTask<String, Void, String> {
	//
	// int index = 1;
	// ProgressDialog myProgressDialog;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// myProgressDialog = new ProgressDialog(getActivity());
	// myProgressDialog.setMessage("Loading...");
	// myProgressDialog.show();
	//
	// myProgressDialog.setCanceledOnTouchOutside(false);
	//
	// }
	//
	// @Override
	// protected String doInBackground(String... params) {
	//
	// return WebServiceCalls
	// .getJSONString("http://www.happeninghyderabad.com/webservices/FilteredEvents/null/22960/sd.xml");
	//
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// super.onPostExecute(result);
	//
	// if (null == result || result.length() == 0) {
	//
	// Toast.makeText(getActivity(),
	// "Could not connect to server, please try again later.",
	// Toast.LENGTH_LONG).show();
	// if (myProgressDialog.isShowing())
	// myProgressDialog.dismiss();
	// } else {
	//
	// try {
	//
	// JSONObject jsonObj = XML.toJSONObject(result.trim());
	//
	// if (jsonObj.toString() != null) {
	//
	// JSONObject jsonObj1 = new JSONObject(jsonObj.toString());
	// JSONObject jsonObj3 = null, jsonsubObj = null;
	// int childrenArrayLength = 1;
	//
	// if (jsonObj1.toString().contains("EventsList\":\"\"")) {
	//
	// } else {
	//
	// JSONObject jsonObj12 = jsonObj1
	// .getJSONObject("ns0:Response");
	//
	// if (jsonObj1.toString().contains(
	// "EventListResponse\":\"\"")) {
	// childrenArrayLength = -1;
	// } else {
	// jsonObj3 = jsonObj12
	// .getJSONObject("EventListResponse");
	// childrenArrayLength = 1;
	// }
	//
	// for (int i = 0; i < childrenArrayLength; i++) {
	// JSONObject jsonObject;
	// jsonObject = jsonObj3
	// .getJSONObject("EventsList");
	// CharSequence cs1 = "\"Event\":{\"";
	// Boolean objArray = jsonObject.toString()
	// .contains(cs1);
	// if (objArray) {
	// jsonsubObj = jsonObject
	// .getJSONObject("Event");
	// upcomingeventsLength = 1;
	// } else {
	// jsonArray = jsonObject
	// .getJSONArray("Event");
	// upcomingeventsLength = jsonArray.length();
	// }
	//
	// JSONObject jsonObject1 = null;
	// if (objArray) {
	// jsonObject1 = jsonsubObj;
	// } else {
	// jsonObject1 = jsonArray.getJSONObject(0);
	// }
	//
	// for (int j = 0; j < upcomingeventsLength; j++) {
	//
	// if (objArray) {
	// jsonObject1 = jsonsubObj;
	// } else {
	// jsonObject1 = jsonArray
	// .getJSONObject(j);
	// }
	//
	// EventsVariables eventsBean = new EventsVariables();
	//
	// eventsBean.title = jsonObject1
	// .getString("Title");
	// eventsBean.startDate = jsonObject1
	// .getString("StartDate");
	// eventsBean.endDate = jsonObject1
	// .getString("EndDate");
	// eventsBean.venue = jsonObject1
	// .getString("Venu");
	// eventsBean.category = jsonObject1
	// .getString("Category");
	// eventsBean.thumbnail = jsonObject1
	// .getString("Thumbnail");
	// events_upcoming.add(eventsBean);
	// event.setObject(events_upcoming);
	//
	// }
	// }
	// }
	// }
	//
	// adapter = new CustomAdapter(getActivity(), events_upcoming);
	// adapter.notifyDataSetChanged();
	// lv.setAdapter(adapter);
	// // refresh();
	// if (myProgressDialog != null
	// && myProgressDialog.isShowing()) {
	// myProgressDialog.dismiss();
	// }
	// } catch (NoSuchMethodError nr) {
	// if (myProgressDialog != null
	// && myProgressDialog.isShowing()) {
	// myProgressDialog.dismiss();
	// }
	// Toast.makeText(
	// getActivity(),
	// "Could not connect to server, please try again later.",
	// Toast.LENGTH_LONG).show();
	// nr.printStackTrace();
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// if (myProgressDialog != null
	// && myProgressDialog.isShowing()) {
	// myProgressDialog.dismiss();
	// }
	// e.printStackTrace();
	// }
	//
	// }
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
