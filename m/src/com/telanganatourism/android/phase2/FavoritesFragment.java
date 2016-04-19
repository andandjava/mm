package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.telanganatourism.android.phase2.database.helper.TtHelper;
import com.telanganatourism.android.phase2.objects.ItemsObj;
import com.telanganatourism.android.phase2.util.Utility;

public class FavoritesFragment extends Fragment {
	ArrayList<Integer> list;
//	EventBean event;
	ArrayList<EventsVariables> events_upcoming;
	ArrayList<Integer> id_list;
//	AQuery androidAQuery;
	CustomAdapter adapter;
	ListView lv;
	
	TtHelper dbbHelper;
	Cursor constantCursor = null;
	FavoritesListAdapter favoritesListAdapter ;
	String responseString;
	public static ArrayList<ItemsObj> Favorites_items_array;                               
	boolean sideMenuFlag = false,btnFlag = false;

	int editFlag = 0;
	
	Utility utility;
	
//	static String[] whrdo = { "Hyderabad", "Khammam", "Mahabubnaga", "Nalgonda", "Warangal", "Adilabad", "Nizamabad", "Karimnagar", "Medak", "Rangareddy" };

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

	public FavoritesFragment() {

	}

	int upcomingeventsLength = 0;

	JSONArray jsonArray;
	
	TextView txtMsg;
	

	private final Handler handler = new Handler();
	private Drawable oldBackground = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		utility = new Utility(getActivity());
		View rootView = inflater.inflate(R.layout.fragment_whats_hot,
				container, false);
		Favorites_items_array = new ArrayList<ItemsObj>();
		// changeColor(Color.parseColor("#ffffff"));

		MainActivity.txt_logo.setVisibility(View.GONE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);
		
		MainActivity.txt_title.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setTextColor(Color.parseColor("#000000"));
		
		MainActivity.txt_title.setText(MainActivity.changeTitle);

		MainActivity.txt_Edit.setVisibility(View.VISIBLE);
		
		MainActivity.txt_Edit.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (editFlag == 0) {
					if (Favorites_items_array.size() > 0) {
						editFlag = 1;
						favoritesListAdapter.notifyDataSetChanged();
						favoritesListAdapter.notifyDataSetInvalidated();
						

					}
				} else {
					if (Favorites_items_array.size() > 0) {
						editFlag = 0;
 
						favoritesListAdapter.notifyDataSetChanged();
						favoritesListAdapter.notifyDataSetInvalidated();

					}
				}
			}
		});
		
		MainActivity.txt_filter.setVisibility(View.GONE);
		
		MainActivity.txt_location.setVisibility(View.GONE);
		
		MainActivity.txt_search.setVisibility(View.GONE);
		
		MainActivity.txt_filter
				.setBackgroundResource(R.drawable.filter_icon_white);

		MainActivity.txt_location
				.setBackgroundResource(R.drawable.location_icon_white);

		MainActivity.txt_search
				.setBackgroundResource(R.drawable.search_icon_white);

		MainActivity.btn_menu.setBackgroundResource(R.drawable.menu_green);

		MainActivity.headerLayout.setBackgroundColor(Color
				.parseColor(MainActivity.changeColorStr));
		
		MainActivity.searchLayout.setBackgroundColor(Color
				.parseColor(MainActivity.changeColorStr));
		
		MainActivity.searchLayout.setVisibility(View.GONE);
		
		if(SplashScreen.fontpref
				.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")){
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		}else if(SplashScreen.fontpref
				.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")){
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		}else if(SplashScreen.fontpref
				.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")){
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
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
				MainActivity.txt_location.setVisibility(View.VISIBLE);
				MainActivity.btn_menu.setVisibility(View.VISIBLE);
			}
		});

		titles.clear();
		loc.clear();
		img.clear();
		dec.clear();

//		MainActivity.getRightMenuList(getActivity(), whrdo);
		
		txtMsg = (TextView)rootView.findViewById(R.id.txtMsg);

		list = new ArrayList<Integer>();
		id_list = new ArrayList<Integer>();
//		event = (EventBean) getActivity().getApplicationContext();
//		androidAQuery = new AQuery(getActivity());
		events_upcoming = new ArrayList<EventsVariables>();
		lv = (ListView) rootView.findViewById(R.id.listView1);
		lv.setEmptyView(rootView.findViewById(android.R.id.empty));
		list.clear();
		id_list.clear();

		
//		adapter = new CustomAdapter(getActivity(), events_upcoming);
//		adapter.notifyDataSetChanged();
//		lv.setAdapter(adapter);

		
		try {
			Favorites_items_array.clear();
			dbbHelper = new TtHelper(getActivity());
			dbbHelper.openDataBase();

			String select = " SELECT * FROM Favorites_Content";
			constantCursor = dbbHelper.getReadableDatabase().rawQuery(select,
					null);

			getActivity().startManagingCursor(constantCursor);

			if (constantCursor.getCount() > 0) {
				if (constantCursor.moveToFirst()) {
					do {

						/*responseString = constantCursor
								.getString(constantCursor
										.getColumnIndex("MaduleUniqueId"));*/
						txtMsg.setVisibility(View.GONE);
						ItemsObj objItem = new ItemsObj();
						
						objItem.setFavorites_id(constantCursor
								.getString(constantCursor
										.getColumnIndex("MaduleUniqueId")).trim());
						
						objItem.setFavorites_title(constantCursor
								.getString(constantCursor
										.getColumnIndex("ModuleUnicName")).trim());
						
						objItem.setFavorites_imgPath(constantCursor
								.getString(constantCursor
										.getColumnIndex("Image_Path")).trim());
						
						objItem.setFavorites_addr(constantCursor
								.getString(constantCursor
										.getColumnIndex("Address")).trim());
						
						objItem.setFavorites_Distence(constantCursor
								.getString(constantCursor
										.getColumnIndex("Distence")).trim());
						
						objItem.setFavorites_NavFlag(constantCursor
								.getString(constantCursor
										.getColumnIndex("navFlag")).trim());

						Favorites_items_array.add(objItem);
					} while (constantCursor.moveToNext());

				}

			}else{
				txtMsg.setVisibility(View.VISIBLE);

			}
			jsonParsing("");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
				
//				if(arg2 == 0){
//					final FragmentTransaction ft = getFragmentManager()
//							.beginTransaction();
//					ft.replace(R.id.frame_container, new TourPackagesFragment(),
//							"NewFragmentTag");
//					ft.commit();
//				}else{
//					final FragmentTransaction ft = getFragmentManager()
//							.beginTransaction();
//					ft.replace(R.id.frame_container, new WhereToShopFragment(),
//							"NewFragmentTag");
//					ft.commit();	
//				}
				
				MainActivity.changeColorStr = "#2a9595";
				ItemsObj itemsObj = (ItemsObj) Favorites_items_array
						.get(arg2);
				Constants.sublistFlag= Integer.parseInt(itemsObj.getFavorites_NavFlag());
				Constants.id = itemsObj.getFavorites_id();
				startActivity(new Intent(getActivity(), DetailScreen1.class));

				
			}
		});

		return rootView;
	}

	private void jsonParsing(String result) {
		// TODO Auto-generated method stub
		try {

			/*Favorites_items_array.clear();
			JSONObject jsonResultObj = new JSONObject(result);

			JSONArray jsonArray = jsonResultObj.getJSONArray("stayplace");

			for (int i = 0; i < Favorites_items_array.size(); i++) {

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

				Favorites_items_array.add(objItem);
			}*/
			/*adapter = new CustomAdapter(getActivity(), events_upcoming);
			adapter.notifyDataSetChanged();
			lv.setAdapter(adapter);*/
			
			
			if(Constants.selectLanguage.equalsIgnoreCase("3")){
				favoritesListAdapter = new FavoritesListAdapter(getActivity(),
						R.layout.arbic_item_layout, Favorites_items_array);
			}else{
				favoritesListAdapter = new FavoritesListAdapter(getActivity(),
						R.layout.item_layout, Favorites_items_array);
			}
			favoritesListAdapter.notifyDataSetChanged(); 
			lv.setAdapter(favoritesListAdapter);
 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public class FavoritesListAdapter extends ArrayAdapter<ItemsObj> {

		private Activity activity;
		private List<ItemsObj> items;
		private ItemsObj objBean;
		@SuppressWarnings("unused")
		private int row;
		Bitmap bimg;
		ViewHolder holder;
		String strQty = "0";
//		AQuery androidAQuery = new AQuery(getContext());

		Utility utils = new Utility(getContext());

		public FavoritesListAdapter(Activity act, int resource,
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
			Button removeBtn;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			LayoutInflater inflator = activity.getLayoutInflater();
			objBean = items.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				
				if(Constants.selectLanguage.equalsIgnoreCase("3")){
					convertView = inflator.inflate(R.layout.arbic_item_layout, null);
				}else{
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
				holder.removeBtn = (Button) convertView
						.findViewById(R.id.removeBtn); 
				
				

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			DecimalFormat df = new DecimalFormat("#.#");

			holder.distancee.setText(objBean.getFavorites_Distence()
					+ " KMS");

			holder.category.setText(URLDecoder.decode(objBean.getFavorites_title()));
			holder.locationTxt.setText(URLDecoder.decode(objBean.getFavorites_addr()));
			
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
 
//			String ddd = objBean.getFavorites_imgPath()
//					.toString().replace("[", "");
//			String ddd1 =ddd.replace("]", "").replace("\"", "");
//			String ddd2 =ddd1.replace("\"", "").trim();
////			Toast.makeText(getActivity(), ddd2.replace("\"", ""), Toast.LENGTH_LONG).show();
//			if(ddd2.replace("\"", "").contains(","))
//			{
//				String[] parts = ddd2.split(",");
////				androidAQuery.id(holder.iv).image(parts[0],
////						true, true);
//				if(parts[0].contains("http"))
//				{
//					Picasso.with(getActivity())
//					.load(parts[0].replace("\'", "%20")
//							.trim()).noFade().error(R.drawable.default_img)
//					.into(holder.iv);
//				}
//				else
//				{
//				try 
//				{
//				    // get input stream
//				    InputStream ims = getActivity().getAssets().open(parts[0]);
//				    // load image as Drawable
//				    Drawable d = Drawable.createFromStream(ims, null);
//				    // set image to ImageView
//				    holder.iv.setImageDrawable(d);
//				}
//				catch(IOException ex) 
//				{
//					
//				}
//				}
//			}
//			else
//			{
////				androidAQuery.id(holder.iv).image(ddd2,
////						true, true);
//				
//				try 
//				{
//				    // get input stream
//				    InputStream ims = getActivity().getAssets().open(ddd2);
//				    // load image as Drawable
//				    Drawable d = Drawable.createFromStream(ims, null);
//				    // set image to ImageView
//				    holder.iv.setImageDrawable(d);
//				}
//				catch(IOException ex) 
//				{
//					
//				}
//			}
			

//			if (objBean.getFavorites_imgPath().length() > 0) {
//				// androidAQuery.id(holder.iv).image(objBean.getDest_imgPath(),
//				// true, true);
//
//				if (objBean.getFavorites_imgPath().contains("http")) {
//					Picasso.with(getActivity())
//							.load(objBean.getFavorites_imgPath()
//									.replace("\'", "%20").trim()).noFade()
//							.error(R.drawable.default_img).into(holder.iv);
//				} else {
//					try {
//						// get input stream
//						InputStream ims = getActivity().getAssets().open(
//								objBean.getFavorites_imgPath());
//						// load image as Drawable
//						Drawable d = Drawable.createFromStream(ims, null);
//						// set image to ImageView
//						holder.iv.setImageDrawable(d);
//					} catch (IOException ex) {
//
//					}
//				}
//				/*
//				 * if (Constants.sublistFlag == 4) { Picasso.with(getActivity())
//				 * .load(objBean.getDest_imgPath().replace("\'", "%20")
//				 * .trim()).noFade().error(R.drawable.default_img)
//				 * .into(holder.iv); }else{ try { // get input stream
//				 * InputStream ims =
//				 * getActivity().getAssets().open(objBean.getDest_imgPath()); //
//				 * load image as Drawable Drawable d =
//				 * Drawable.createFromStream(ims, null); // set image to
//				 * ImageView holder.iv.setImageDrawable(d); } catch(IOException
//				 * ex) {
//				 * 
//				 * } }
//				 */
//
//			}
			
			if (objBean.getFavorites_imgPath().length() > 0) {
				// androidAQuery.id(holder.iv).image(objBean.getDest_imgPath(),
				// true, true);

//				if (objBean.getFavorites_imgPath().contains("http")) {
//
//					
//					if (utility.IsNetConnected(getActivity())) {
				// Picasso.with(getActivity())
				// .load(objBean.getFavorites_imgPath()
				// .replace("\'", "%20").trim()).noFade()
				// .error(R.drawable.default_img).into(holder.iv);
						
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
								objBean.getFavorites_imgPath()
								.replace("\'", "%20").trim(), holder.iv,
								options);
					
//					} else {
//						try { 
//							String url= objBean.getFavorites_imgPath().replace("\'", "");
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
//						
//						String url= objBean.getFavorites_imgPath().replace("\'", "");
//						
//						String replacdUrl = url.replace("http://ttourdev.etisbew.net/images/", "");
//						
//						InputStream ims = getActivity().getAssets().open(replacdUrl.trim());
//						// load image as Drawable
//						Drawable d = Drawable.createFromStream(ims, null);
//						// set image to ImageView
//						holder.iv.setImageDrawable(d);
//					} catch (IOException ex) {
//
//					}
//				}
//				/*
//				 * if (Constants.sublistFlag == 4) { Picasso.with(getActivity())
//				 * .load(objBean.getDest_imgPath().replace("\'", "%20")
//				 * .trim()).noFade().error(R.drawable.default_img)
//				 * .into(holder.iv); }else{ try { // get input stream
//				 * InputStream ims =
//				 * getActivity().getAssets().open(objBean.getDest_imgPath()); //
//				 * load image as Drawable Drawable d =
//				 * Drawable.createFromStream(ims, null); // set image to
//				 * ImageView holder.iv.setImageDrawable(d); } catch(IOException
//				 * ex) {
//				 * 
//				 * } }
//				 */
//
			}
			
			/*if (objBean.getDest_imgPath().length() > 0) {
				androidAQuery.id(holder.iv).image(objBean.getDest_imgPath(),
						true, true);
			}*/

			
			
			if (editFlag == 1) {
				holder.removeBtn.setVisibility(View.VISIBLE);
			} else if (editFlag == 0) {
				holder.removeBtn.setVisibility(View.GONE);
			}

			holder.removeBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if(!btnFlag)
					{
						btnFlag = true;
						try {

							ItemsObj itemsObj = (ItemsObj) Favorites_items_array
									.get(position);
							
							dbbHelper = new TtHelper(getActivity());
							dbbHelper.openDataBase();

//							String deleteQuery = "DELETE FROM  Favorites_Content where ModuleUnicName='"
//									+ objBean.getFavorites_title() + "'";

							SQLiteDatabase database = dbbHelper
									.getWritableDatabase();
							
							
//							Log.d("query", deleteQuery);
							
							Log.d("Item", objBean.getFavorites_title());
//							database.execSQL(deleteQuery);
							
							String deleteQuery = "DELETE FROM Favorites_Content where MaduleUniqueId='"+itemsObj.getFavorites_id()+"'";
							
							constantCursor= database.rawQuery(deleteQuery,null);

							getActivity().startManagingCursor(constantCursor);
							
							
							
//							String select = " SELECT * FROM Favorites_Content";
//							constantCursor = dbbHelper.getReadableDatabase().rawQuery(select,
//									null);
//
//							getActivity().startManagingCursor(constantCursor);
							
							if (constantCursor.getCount() > 0) {
								txtMsg.setVisibility(View.GONE);
							}else{
								txtMsg.setVisibility(View.VISIBLE);
							}

							
							
//							if (constantCursor.getCount() > 0) {
//								if (constantCursor.moveToFirst()) {
//									do {
//
//										/*responseString = constantCursor
//												.getString(constantCursor
//														.getColumnIndex("MaduleUniqueId"));*/
//										txtMsg.setVisibility(View.GONE);
//										ItemsObj objItem = new ItemsObj();
//										
//										objItem.setFavorites_id(constantCursor
//												.getString(constantCursor
//														.getColumnIndex("MaduleUniqueId")).trim());
//										
//										objItem.setFavorites_title(constantCursor
//												.getString(constantCursor
//														.getColumnIndex("ModuleUnicName")).trim());
//										
//										objItem.setFavorites_imgPath(constantCursor
//												.getString(constantCursor
//														.getColumnIndex("Image_Path")).trim());
//										
//										objItem.setFavorites_addr(constantCursor
//												.getString(constantCursor
//														.getColumnIndex("Address")).trim());
//										
//										objItem.setFavorites_Distence(constantCursor
//												.getString(constantCursor
//														.getColumnIndex("Distence")).trim());
//										
//										objItem.setFavorites_NavFlag(constantCursor
//												.getString(constantCursor
//														.getColumnIndex("navFlag")).trim());
//
//										Favorites_items_array.add(objItem);
//									} while (constantCursor.moveToNext());
//
//								}
//
//							}else{
//								txtMsg.setVisibility(View.VISIBLE);
//
//							}
							
							Favorites_items_array.remove(position);
							favoritesListAdapter.notifyDataSetInvalidated();
							favoritesListAdapter.notifyDataSetChanged();
							btnFlag = false;
						} catch (SQLException sqle) {

//							Toast.makeText(getApplicationContext(),
//									"Exception : " + sqle, Toast.LENGTH_LONG)
//									.show();

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			
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
		adapter = new CustomAdapter(getActivity(), events_upcoming);
		adapter.notifyDataSetChanged();

		lv.setAdapter(adapter);
 
	}

	public class CustomAdapter extends BaseAdapter {

		ArrayList<EventsVariables> list;

		LayoutInflater mInflater;

		public CustomAdapter(Context context,
				ArrayList<EventsVariables> events_upcoming) {
			mInflater = LayoutInflater.from(context);

			this.list = events_upcoming;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			// int pos = list.get(position);
			// System.out.println("list size" + list.size() + list + pos);
			UserHolder1 holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_layout1, parent,
						false);
				holder = new UserHolder1();

				holder.category = (TextView) convertView
						.findViewById(R.id.category);
				holder.event_title = (TextView) convertView
						.findViewById(R.id.event_title);
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
				holder = (UserHolder1) convertView.getTag();
			}
			// holder.event_title.setTypeface(event.getTextBold());
			// holder.event_date.setTypeface(event.getTextBold());
			// // holder.event_time.setTypeface(event.getTextNormal());
			// holder.event_desc.setTypeface(event.getTextNormal());

			EventsVariables eve1 = events_upcoming.get(position);

			// holder.category.setText(eve1.category);
			holder.category.setText(titles.get(position));
			System.out.println("eve category title" + eve1.category
					+ eve1.title);
			// holder.event_time.setText(eve1.endDate.replace("T", " "));
			Utility util = new Utility(getActivity());
			// holder.event_date.setText(util.dateConvert(eve1.startDate,
			// eve1.endDate));
			holder.locationTxt.setText(eve1.venue);
			// androidAQuery.id(holder.iv).image(eve1.thumbnail, true, true,
			// AQuery.FADE_IN, position, null, 0, 0);

			holder.iv.setImageResource(img.get(position));
			return convertView;
		}

		class UserHolder1 {
			TextView category;
			TextView event_title;
			TextView locationTxt;
			TextView event_time;
			TextView event_desc;
			ImageView iv;
		}
	}

//	class UpComingEvents extends AsyncTask<String, Void, String> {
//
//		int index = 1;
//		ProgressDialog myProgressDialog;
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			myProgressDialog = new ProgressDialog(getActivity());
//			myProgressDialog.setMessage("Loading...");
//			myProgressDialog.show();
//
//			myProgressDialog.setCanceledOnTouchOutside(false);
//
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//
//			return WebServiceCalls
//					.getJSONString("http://www.happeninghyderabad.com/webservices/FilteredEvents/null/22960/sd.xml");
//
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//
//			if (null == result || result.length() == 0) {
//
//				Toast.makeText(getActivity(),
//						"Could not connect to server, please try again later.",
//						Toast.LENGTH_LONG).show();
//				if (myProgressDialog.isShowing())
//					myProgressDialog.dismiss();
//			} else {
//
//				try {
//
//					JSONObject jsonObj = XML.toJSONObject(result.trim());
//
//					if (jsonObj.toString() != null) {
//
//						JSONObject jsonObj1 = new JSONObject(jsonObj.toString());
//						JSONObject jsonObj3 = null, jsonsubObj = null;
//						int childrenArrayLength = 1;
//
//						if (jsonObj1.toString().contains("EventsList\":\"\"")) {
//
//						} else {
//
//							JSONObject jsonObj12 = jsonObj1
//									.getJSONObject("ns0:Response");
//
//							if (jsonObj1.toString().contains(
//									"EventListResponse\":\"\"")) {
//								childrenArrayLength = -1;
//							} else {
//								jsonObj3 = jsonObj12
//										.getJSONObject("EventListResponse");
//								childrenArrayLength = 1;
//							}
//
//							for (int i = 0; i < childrenArrayLength; i++) {
//								JSONObject jsonObject;
//								jsonObject = jsonObj3
//										.getJSONObject("EventsList");
//								CharSequence cs1 = "\"Event\":{\"";
//								Boolean objArray = jsonObject.toString()
//										.contains(cs1);
//								if (objArray) {
//									jsonsubObj = jsonObject
//											.getJSONObject("Event");
//									upcomingeventsLength = 1;
//								} else {
//									jsonArray = jsonObject
//											.getJSONArray("Event");
//									upcomingeventsLength = jsonArray.length();
//								}
//
//								JSONObject jsonObject1 = null;
//								if (objArray) {
//									jsonObject1 = jsonsubObj;
//								} else {
//									jsonObject1 = jsonArray.getJSONObject(0);
//								}
//
//								for (int j = 0; j < upcomingeventsLength; j++) {
//
//									if (objArray) {
//										jsonObject1 = jsonsubObj;
//									} else {
//										jsonObject1 = jsonArray
//												.getJSONObject(j);
//									}
//
//									EventsVariables eventsBean = new EventsVariables();
//
//									eventsBean.title = jsonObject1
//											.getString("Title");
//									eventsBean.startDate = jsonObject1
//											.getString("StartDate");
//									eventsBean.endDate = jsonObject1
//											.getString("EndDate");
//									eventsBean.venue = jsonObject1
//											.getString("Venu");
//									eventsBean.category = jsonObject1
//											.getString("Category");
//									eventsBean.thumbnail = jsonObject1
//											.getString("Thumbnail");
//									events_upcoming.add(eventsBean);
//									event.setObject(events_upcoming);
//
//								}
//							}
//						}
//					}
//
//					adapter = new CustomAdapter(getActivity(), events_upcoming);
//					adapter.notifyDataSetChanged();
//					lv.setAdapter(adapter);
//					// refresh();
//					if (myProgressDialog != null
//							&& myProgressDialog.isShowing()) {
//						myProgressDialog.dismiss();
//					}
//				} catch (NoSuchMethodError nr) {
//					if (myProgressDialog != null
//							&& myProgressDialog.isShowing()) {
//						myProgressDialog.dismiss();
//					}
//					Toast.makeText(
//							getActivity(),
//							"Could not connect to server, please try again later.",
//							Toast.LENGTH_LONG).show();
//					nr.printStackTrace();
//
//				} catch (Exception e) {
//					// TODO: handle exception
//					if (myProgressDialog != null
//							&& myProgressDialog.isShowing()) {
//						myProgressDialog.dismiss();
//					}
//					e.printStackTrace();
//				}
//
//			}
//		}
//	}
	
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
