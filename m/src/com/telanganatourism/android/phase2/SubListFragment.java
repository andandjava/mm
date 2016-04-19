package com.telanganatourism.android.phase2;

import java.io.IOException;
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
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.flurry.android.FlurryAgent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.telanganatourism.android.phase2.database.helper.TtHelper;
import com.telanganatourism.android.phase2.objects.ItemsObj;
import com.telanganatourism.android.phase2.util.Utility;

public class SubListFragment extends Fragment {
	ArrayList<Integer> list;
//	EventBean event;
	ArrayList<EventsVariables> events_upcoming;
	ArrayList<Integer> id_list;
	AQuery androidAQuery;
	SubListAdapter adapter;
	SearchListAdapter searchadapter;
	// CustomAdapter adapter1;
	ListView lv, searc_ListView;
	
//	int fontChaning = 0;

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
	public static ArrayList<String> listing_data = new ArrayList<String>();

	public SubListFragment() {

	}

	int upcomingeventsLength = 0;

	JSONArray jsonArray;

	private final Handler handler = new Handler();
	private Drawable oldBackground = null;

	TtHelper dbbHelper;
	Cursor constantCursor = null;

	String responseString;

//	Utility utility;

	ArrayList<ItemsObj> search_items_array = new ArrayList<ItemsObj>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_whats_hot,
				container, false);

//		utility = new Utility(getActivity());
		MainActivity.detailNavFlag = 2;

		lv = (ListView) rootView.findViewById(R.id.listView1);

		searc_ListView = (ListView) rootView.findViewById(R.id.search_listView);

		lv.setEmptyView(rootView.findViewById(android.R.id.empty));

		SubFragmentActivity.txt_logo.setVisibility(View.GONE);

		SubFragmentActivity.txt_logo1.setVisibility(View.VISIBLE);

		SubFragmentActivity.txt_title.setVisibility(View.VISIBLE);

		SubFragmentActivity.txt_title.setText(MainActivity.changeTitle);
		
		SubFragmentActivity.txt_title.setTextColor(Color.parseColor("#ffffff"));

		SubFragmentActivity.txt_filter.setVisibility(View.VISIBLE);

		SubFragmentActivity.txt_location.setVisibility(View.GONE);

		SubFragmentActivity.txt_search.setVisibility(View.VISIBLE);

		SubFragmentActivity.txt_Edit.setVisibility(View.GONE);

		if (Constants.sublistFlag == 1) {

			SubFragmentActivity.txt_filter.setVisibility(View.VISIBLE);
		} else if (Constants.sublistFlag == 2) {

			SubFragmentActivity.txt_filter.setVisibility(View.GONE);
			SubFragmentActivity.txt_search.setVisibility(View.GONE);
		}

		SubFragmentActivity.txt_filter
				.setBackgroundResource(R.drawable.filter_icon_white);

		SubFragmentActivity.txt_location
				.setBackgroundResource(R.drawable.location_icon_white);

		SubFragmentActivity.txt_search
				.setBackgroundResource(R.drawable.search_icon_white);

		SubFragmentActivity.btn_menu.setBackgroundResource(R.drawable.menu);

		SubFragmentActivity.headerLayout.setBackgroundColor(Color
				.parseColor(MainActivity.changeColorStr));

		SubFragmentActivity.searchLayout.setBackgroundColor(Color
				.parseColor(MainActivity.changeColorStr));

		SubFragmentActivity.searchLayout.setVisibility(View.GONE);

		SubFragmentActivity.searchEdtTxt.setTextColor(Color.WHITE);
		
		SubFragmentActivity.txt_search.setVisibility(View.GONE);
		
//		TextView tvSmallFont = (TextView) rootView.findViewById(R.id.smallFontTxt);
//		TextView tvBigFont = (TextView) rootView.findViewById(R.id.bigFontTxt);
//		
//		tvSmallFont.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (fontChaning == 0) {
//					adapter.notifyDataSetChanged();
//					SubFragmentActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//				} else {
//					fontChaning--;
//					if (fontChaning > 0) {
//
//						if (fontChaning == 1) {
//							adapter.notifyDataSetChanged();
//							SubFragmentActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//						} else if (fontChaning == 2) {
//							adapter.notifyDataSetChanged();
//							SubFragmentActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//						} else if (fontChaning == 3) {
//							adapter.notifyDataSetChanged();
//							SubFragmentActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//						}
						
						if(SplashScreen.fontpref
								.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")){
//							adapter.notifyDataSetChanged();
							SubFragmentActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
						}else if(SplashScreen.fontpref
								.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")){
//							adapter.notifyDataSetChanged();
							SubFragmentActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
						}else if(SplashScreen.fontpref
								.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")){
//							adapter.notifyDataSetChanged();
							SubFragmentActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
						}
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
//							adapter.notifyDataSetChanged();
//							SubFragmentActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//						} else if (fontChaning == 2) {
//							adapter.notifyDataSetChanged();
//							SubFragmentActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//						} else if (fontChaning == 3) {
//							adapter.notifyDataSetChanged();
//							SubFragmentActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
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

		SubFragmentActivity.txt_search
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SubFragmentActivity.searchEdtTxt.requestFocus();
						SubFragmentActivity.searchLayout
								.setVisibility(View.VISIBLE);
						SubFragmentActivity.txt_location
								.setVisibility(View.GONE);
						SubFragmentActivity.btn_menu
								.setVisibility(View.VISIBLE);
						SubFragmentActivity.menuBtn.setVisibility(View.GONE);
						InputMethodManager imm = (InputMethodManager) getActivity()
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
					}
				});
		
		

		SubFragmentActivity.txt_back_search
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						searc_ListView.setVisibility(View.GONE);
						lv.setVisibility(View.VISIBLE);

						SubFragmentActivity.searchLayout
								.setVisibility(View.GONE);
						SubFragmentActivity.txt_filter
								.setVisibility(View.VISIBLE);
						SubFragmentActivity.txt_location
								.setVisibility(View.GONE);
						SubFragmentActivity.btn_menu
								.setVisibility(View.VISIBLE);
						SubFragmentActivity.menuBtn.setVisibility(View.VISIBLE);

						SubFragmentActivity.searchEdtTxt.setText("");

						InputMethodManager inputManager = (InputMethodManager) getActivity()
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						// inputManager.toggleSoftInput(0, 0);
						inputManager.hideSoftInputFromWindow(
								SubFragmentActivity.searchEdtTxt
										.getWindowToken(), 0);
					}
				});

		SubFragmentActivity.searchEdtTxt
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		SubFragmentActivity.searchEdtTxt
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						InputMethodManager inputManager = (InputMethodManager) getActivity()
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.toggleSoftInput(0, 0);

						searc_ListView.setVisibility(View.VISIBLE);
						lv.setVisibility(View.GONE);

						search_items_array.clear();
						for (int i = 0; i < MainActivity.dest_items_array
								.size(); i++) {
							ItemsObj objItem = (ItemsObj) MainActivity.dest_items_array
									.get(i);
							try {
								if (objItem
										.getDest_title()
										.toString()
										.trim()
										.equalsIgnoreCase(
												SubFragmentActivity.searchEdtTxt
														.getText().toString())) {
									try {

										ItemsObj objItem1 = new ItemsObj();

										objItem1.setSearch_id(objItem
												.getDest_id().toString().trim());
										objItem1.setSearch_title(objItem
												.getDest_title().toString()
												.trim());
										objItem1.setSearch_addr(objItem
												.getDest_addr().toString()
												.trim());
										objItem1.setSearch_imgPath(objItem
												.getDest_imgPath().toString()
												.trim());
										objItem1.setSearch_latt(objItem
												.getDest_latt().toString()
												.trim());
										objItem1.setSearch_lng(objItem
												.getDest_lng().toString()
												.trim());

										search_items_array.add(objItem1);
									} catch (Exception e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
									}
								}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

						if (Constants.selectLanguage.equalsIgnoreCase("3")) {
							searchadapter = new SearchListAdapter(
									getActivity(), R.layout.arbic_item_layout,
									search_items_array);
						} else {
							searchadapter = new SearchListAdapter(
									getActivity(), R.layout.item_layout,
									search_items_array);
						}

						searchadapter.notifyDataSetChanged();
						searc_ListView.setAdapter(searchadapter);

					}
				});

		// SubFragmentActivity.searchEdtTxt.addTextChangedListener(new
		// TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before, int
		// count) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		// SubFragmentActivity.searchEdtTxt.setOnEditorActionListener(new
		// OnEditorActionListener() {
		// @Override
		// public boolean onEditorAction(TextView v, int actionId,
		// KeyEvent event) {
		// boolean handled = false;
		// if (actionId == EditorInfo.IME_ACTION_DONE ||
		// ( (event.isShiftPressed()==false) &&
		// (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
		// (event.getAction() == KeyEvent.ACTION_DOWN ) ) ) {
		// // TODO do something
		// /*itemId = R.id.searchText;
		// if (callback != null)
		// callback.onSlideMenuItemClick(itemId, textMessage
		// .getText().toString());
		// hide(); */
		// // Toast.makeText(getActivity(), edtSearch.getText().toString(),
		// Toast.LENGTH_LONG).show();
		//
		// if(SubFragmentActivity.searchEdtTxt.getText().toString().length()>0)
		// {
		// Locale defloc = Locale.getDefault();
		// Toast.makeText(getActivity(),
		// ""+SubFragmentActivity.searchEdtTxt.getText().toString().toLowerCase(defloc),
		// Toast.LENGTH_LONG).show();
		// String val
		// =SubFragmentActivity.searchEdtTxt.getText().toString().toLowerCase(defloc);
		// InputMethodManager inputManager = (InputMethodManager)
		// getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		// inputManager.toggleSoftInput(0, 0);
		// searc_ListView.setVisibility(View.VISIBLE);
		// lv.setVisibility(View.GONE);
		//
		// search_items_array.clear();
		// for (int i = 0; i < MainActivity.dest_items_array.size(); i++) {
		// ItemsObj objItem = new ItemsObj();
		// try {
		// if (objItem.getDest_title()
		// .toString().trim().contains(val))
		// {
		// try {
		//
		// ItemsObj objItem1 = new ItemsObj();
		//
		// objItem1.setSearch_id(objItem.getDest_id()
		// .toString().trim());
		// objItem1.setSearch_title(objItem.getDest_title()
		// .toString().trim());
		// objItem1.setSearch_addr(objItem.getDest_addr()
		// .toString().trim());
		// objItem1.setSearch_imgPath(objItem.getDest_imgPath()
		// .toString().trim());
		// objItem1.setSearch_latt(objItem.getDest_latt()
		// .toString().trim());
		// objItem1.setSearch_lng(objItem.getDest_lng()
		// .toString().trim());
		//
		//
		// search_items_array.add(objItem1);
		// } catch (Exception e) {
		// // TODO Auto-generated catch
		// // block
		// e.printStackTrace();
		// }
		// }
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		//
		//
		// }
		//
		// searchadapter = new SearchListAdapter(getActivity(),
		// R.layout.item_layout, search_items_array);
		// searchadapter.notifyDataSetChanged();
		// searc_ListView.setAdapter(searchadapter);
		//
		//
		// /*Boolean status = util.IsNetConnected(getActivity());
		// if (status) {
		// new BrandsListingSearch().execute();
		//
		// } else {
		// util.showAlertNoInternetService(getActivity());
		// }*/
		// }
		// else
		// {
		// AlertDialog.Builder altDialog = new
		// AlertDialog.Builder(getActivity());
		// altDialog.setTitle("Alert");
		// altDialog
		// .setMessage(" please enter search key");
		// // add
		// // your
		// // message
		// altDialog.setNeutralButton("OK", new
		// DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		// }
		// });
		// altDialog.show();
		// }
		//
		//
		//
		//
		// handled = true;
		// }
		// return handled;
		// }
		// });

		titles.clear();
		loc.clear();
		img.clear();
		dec.clear();

		// SubFragmentActivity.getRightMenuList(getActivity(), whrdo);

		list = new ArrayList<Integer>();
		id_list = new ArrayList<Integer>();
//		event = (EventBean) getActivity().getApplicationContext();
		androidAQuery = new AQuery(getActivity());
		events_upcoming = new ArrayList<EventsVariables>();
		list.clear();
		id_list.clear();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				ItemsObj item = (ItemsObj) MainActivity.dest_items_array
						.get(arg2);

				// Constants.imges = item.getDest_imgPath();
				// Constants.event_title = item.getDest_title();
				// Constants.description = item.getDest_addr();
				Constants.id = item.getDest_id();
				Constants.new_pack_id = item.getCity_lookup();
				startActivity(new Intent(getActivity(), DetailScreen1.class));
			}
		});

		searc_ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				ItemsObj item = (ItemsObj) search_items_array.get(arg2);

				// Constants.imges = item.getDest_imgPath();
				// Constants.event_title = item.getDest_title();
				// Constants.description = item.getDest_addr();
				Constants.id = item.getSearch_id();
				startActivity(new Intent(getActivity(), DetailScreen1.class));

				searc_ListView.setVisibility(View.GONE);
				lv.setVisibility(View.VISIBLE);

				SubFragmentActivity.searchLayout.setVisibility(View.GONE);
				SubFragmentActivity.txt_filter.setVisibility(View.VISIBLE);
				SubFragmentActivity.txt_location.setVisibility(View.GONE);
				SubFragmentActivity.btn_menu.setVisibility(View.VISIBLE);
				SubFragmentActivity.menuBtn.setVisibility(View.VISIBLE);

				SubFragmentActivity.searchEdtTxt.setText("");
			}
		});

		if (Constants.sublistFlag == 1) {
			try {
				
				listing_data.clear();
				MainActivity.dest_items_array.clear();
				
				if (Constants.searchCode.equalsIgnoreCase("0")) {
					dbbHelper = new TtHelper(getActivity());
					dbbHelper.openDataBase();
					
					
					
					String[] splitedStr = Constants.locationCode.split(",");
					
					ArrayList<String> aListNumbers = new ArrayList<String>(Arrays.asList(splitedStr));
					
					for (int i = 0; i <aListNumbers.size(); i++) {
						String select = "SELECT * FROM Destination_Content WHERE ModuleUniqueName="
								+ Constants.destination_cat_id
								+ " AND id="
								+ aListNumbers.get(i)
								+ " AND language_id="
								+ Constants.selectLanguage;
						constantCursor = dbbHelper.getReadableDatabase().rawQuery(
								select, null);

						getActivity().startManagingCursor(constantCursor);

						if (constantCursor.getCount() > 0) {
							if (constantCursor.moveToFirst()) {

								responseString = constantCursor
										.getString(constantCursor
												.getColumnIndex("ResponseContent"));

								jsonParsing(responseString);

							}
						}
					}

					
				} else {
					jsonParsing(SubFragmentActivity.searchJsonString);
				}
				// dbbHelper.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (Constants.sublistFlag == 2) {
			try {
				dbbHelper = new TtHelper(getActivity());
				dbbHelper.openDataBase();

				String select = "SELECT * FROM TourPackagesContent WHERE ModuleUniqueName="
						+ Constants.destination_cat_id
						+ " AND language_id="
						+ Constants.selectLanguage;
				constantCursor = dbbHelper.getReadableDatabase().rawQuery(
						select, null);

				getActivity().startManagingCursor(constantCursor);

				if (constantCursor.getCount() > 0) {
					if (constantCursor.moveToFirst()) {

						responseString = constantCursor
								.getString(constantCursor
										.getColumnIndex("ResponseContent"));

						jsonParsingPackages(responseString);

					}

				}
				// dbbHelper.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.select_dialog_item, listing_data);

		SubFragmentActivity.searchEdtTxt.setThreshold(1);

		SubFragmentActivity.searchEdtTxt.setAdapter(adapter);
		return rootView;
	}

	private void jsonParsing(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonResultObj = new JSONObject(result);

			JSONArray jsonArray = jsonResultObj.getJSONArray("Attractions");

			for (int i = 0; i < jsonArray.length(); i++) {

				ItemsObj objItem = new ItemsObj();
				objItem.setDest_id(jsonArray.getJSONObject(i).get("id")
						.toString().trim());
				objItem.setDest_title(jsonArray.getJSONObject(i).get("title")
						.toString().trim());
				listing_data.add(jsonArray.getJSONObject(i).get("title")
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
				adapter = new SubListAdapter(getActivity(),
						R.layout.arbic_item_layout,
						MainActivity.dest_items_array);
			} else {
				adapter = new SubListAdapter(getActivity(),
						R.layout.item_layout, MainActivity.dest_items_array);
			}
			adapter.notifyDataSetChanged();
			lv.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void jsonParsingPackages(String result) {
		// TODO Auto-generated method stub
		try {
			listing_data.clear();
			MainActivity.dest_items_array.clear();
			JSONObject jsonResultObj = new JSONObject(result);

			JSONArray jsonArray = jsonResultObj.getJSONArray("Package");

			for (int i = 0; i < jsonArray.length(); i++) {

				ItemsObj objItem = new ItemsObj();
				objItem.setDest_id(jsonArray.getJSONObject(i).get("id")
						.toString().trim());				
				objItem.setCity_lookup(jsonArray.getJSONObject(i).get("city_lookup")
						.toString().trim());
				objItem.setDest_title(jsonArray.getJSONObject(i).get("title")
						.toString().trim());
				listing_data.add(jsonArray.getJSONObject(i).get("title")
						.toString().trim());
				objItem.setDest_start_time(jsonArray.getJSONObject(i)
						.get("start_time").toString().trim());
				objItem.setDest_end_time(jsonArray.getJSONObject(i)
						.get("end_time").toString().trim());
				objItem.setDest_imgPath(jsonArray.getJSONObject(i).get("image")
						.toString().trim());

				if(jsonArray.getJSONObject(i).get("city_lookup")
						.toString().trim().equalsIgnoreCase("0")){
					
				}else{
					MainActivity.dest_items_array.add(objItem);
				}
				
			}

			adapter = new SubListAdapter(getActivity(), R.layout.item_layout,
					MainActivity.dest_items_array);
			adapter.notifyDataSetChanged();
			lv.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		adapter = new SubListAdapter(getActivity(), R.layout.item_layout,
				MainActivity.dest_items_array);
		adapter.notifyDataSetChanged();
		lv.setAdapter(adapter);

	}

	public class SubListAdapter extends ArrayAdapter<ItemsObj> {

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

		public SubListAdapter(Activity act, int resource,
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
				if (Constants.sublistFlag == 1) {
					holder.distancee.setText(df.format(utils.distance(Constants.latitude,
							Constants.longitude,
							Double.parseDouble(objBean.getDest_latt()),
							Double.parseDouble(objBean.getDest_lng()), 'K'))
							+ " KMS");
				} else if (Constants.sublistFlag == 2) {
					holder.distancee.setText("");
				}
			} else {
				holder.distancee.setVisibility(View.GONE);
			}

			// if(Constants.selectLanguage.equalsIgnoreCase("4")){
			holder.category.setText(URLDecoder.decode(objBean.getDest_title()));
			// }else{
			// holder.category.setText(objBean.getDest_title());
			// }

			holder.locationTxt
					.setText(objBean.getDest_addr());

			holder.category.setTypeface(Constants.ProximaNova_Regular);
			holder.locationTxt.setTypeface(Constants.ProximaNova_Regular);
			
//			if (fontChaning == 1) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
//				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//			} else if (fontChaning == 2) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
//				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//			} else if (fontChaning == 3) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
//				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//			} else if (fontChaning == 0) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//			}
			
			if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			}else if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			}else if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
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
//				if (objBean.getDest_imgPath().contains("http")) {

					
//					if (utility.IsNetConnected(getActivity())) {
//						Picasso.with(getActivity())
//						.load(objBean.getDest_imgPath().replace("\'", "")
//								.replace("%20", "").replace(" ", "").trim())
//						.noFade().error(R.drawable.default_img)
//						.into(holder.iv);
						
						ImageLoader imageLoader = ImageLoader.getInstance();
						DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
										.cacheOnDisc(true).resetViewBeforeLoading(true)
										.showImageForEmptyUri(R.drawable.default_img)
										.showImageOnFail(R.drawable.default_img)
										.showImageOnLoading(R.drawable.default_img).build();
			

						//download and display image from url
						imageLoader.displayImage(objBean.getDest_imgPath().replace("\'", "")
								.replace("%20", "").replace(" ", "").trim(), holder.iv, options);
						
//					
//					} else {
//						try { 
//							String url= objBean.getDest_imgPath().replace("\'", "");
//							
//							String replacdUrl = url.replace("http://ttourdev.etisbew.net/images/", "");
//							// get input stream
//							InputStream ims = getActivity().getAssets().open(
//									replacdUrl.trim());
//							// load image as Drawable
//							Drawable d = Drawable.createFromStream(ims, null);
//							// set image to ImageView
//							holder.iv.setImageDrawable(d);
//						} catch (IOException ex) {
//
//						}
//					}
//				} else {
//					try {
//						// get input stream
//						InputStream ims = getActivity().getAssets().open(
//								objBean.getDest_imgPath());
//						// load image as Drawable
//						Drawable d = Drawable.createFromStream(ims, null);
//						// set image to ImageView
//						holder.iv.setImageDrawable(d);
//					} catch (IOException ex) {
//
//					}
//				}
			}
			return convertView;
		}

	}

	public class SearchListAdapter extends ArrayAdapter<ItemsObj> {

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

		public SearchListAdapter(Activity act, int resource,
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
				if (Constants.sublistFlag == 1) {
					holder.distancee.setText(df.format(utils.distance(
							Constants.latitude, Constants.longitude,
							Double.parseDouble(objBean.getSearch_latt()),
							Double.parseDouble(objBean.getSearch_lng()), 'K'))
							+ " KMS");
				} else if (Constants.sublistFlag == 2) {
					holder.distancee.setText("");
				}
			} else {
				holder.distancee.setVisibility(View.GONE);
			}

			holder.category.setText(objBean.getSearch_title());
			holder.locationTxt.setText(objBean.getSearch_addr());

			holder.category.setTypeface(Constants.ProximaNova_Regular);
			holder.locationTxt.setTypeface(Constants.ProximaNova_Regular);
			
//			if (fontChaning == 1) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
//				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//			} else if (fontChaning == 2) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
//				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//			} else if (fontChaning == 3) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
//				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//			} else if (fontChaning == 0) {
//				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//			}
			
			if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			}else if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			}else if(SplashScreen.fontpref
					.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")){
				holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
				holder.distancee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				holder.locationTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			}

			// holder.txtName.setText(objBean.getStr_title());
			// holder.txtDetails.setText("Product: "+objBean.getStr_titl1().replace("...",
			// "")+" ...");

			if (objBean.getSearch_imgPath().length() > 0) {
				// androidAQuery.id(holder.iv).image(objBean.getDest_imgPath(),
				// true, true);
//				if (objBean.getSearch_imgPath().contains("http")) {

				
				ImageLoader imageLoader = ImageLoader.getInstance();
				DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
								.cacheOnDisc(true).resetViewBeforeLoading(true)
								.showImageForEmptyUri(R.drawable.default_img)
								.showImageOnFail(R.drawable.default_img)
								.showImageOnLoading(R.drawable.default_img).build();
	

				//download and display image from url
				imageLoader.displayImage(objBean.getSearch_imgPath().replace("\'", "")
						.replace("%20", "").replace(" ", "").trim(), holder.iv, options);
					
//					if (utility.IsNetConnected(getActivity())) {
//						Picasso.with(getActivity())
//						.load(objBean.getSearch_imgPath().replace("\'", "")
//								.replace("%20", "").replace(" ", "").trim())
//						.noFade().error(R.drawable.default_img)
//						.into(holder.iv);
//					
//					} else {
//						try { 
//							String url= objBean.getSearch_imgPath().replace("\'", "");
//							
//							String replacdUrl = url.replace("http://ttourdev.etisbew.net/images/", "");
//							// get input stream
//							InputStream ims = getActivity().getAssets().open(
//									replacdUrl.trim());
//							// load image as Drawable
//							Drawable d = Drawable.createFromStream(ims, null);
//							// set image to ImageView
//							holder.iv.setImageDrawable(d);
//						} catch (IOException ex) {
//
//						}
//					}
//				} else {
//					try {
//						// get input stream
//						InputStream ims = getActivity().getAssets().open(
//								objBean.getSearch_imgPath());
//						// load image as Drawable
//						Drawable d = Drawable.createFromStream(ims, null);
//						// set image to ImageView
//						holder.iv.setImageDrawable(d);
//					} catch (IOException ex) {
//
//					}
//				}
			}

			return convertView;
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
