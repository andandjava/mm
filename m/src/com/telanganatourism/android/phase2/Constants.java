package com.telanganatourism.android.phase2;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.telanganatourism.android.phase2.objects.ItemsObj;

public class Constants {
	public static Context cont;

	public static String event_title = "Event Title";
	public static int imgs;
	public static String imges, id, new_id, new_pack_id,available_rooms_str;
	public static String description = "";
	public static int updatingTime = 2*24*60; 	//5*24*60
	public static String destination_cat_id = "",tag="home";
	public static ArrayList<ItemsObj> dest_items_array1, dest_items_array2, pack_list_item_array;
	public static int sublistFlag = 0;
	public static double latitude, longitude;
	public static String locationId = "1";
	public static String cityId = "90883135";	
	public static String searchCode = "", strStoredTime, locationCode = "1,";
	public static Typeface ProximaNova_Regular, ProximaNova_Light, ProximaNova_Bold;
	public static String selectLanguage = "1";
	public static String selectedFont = "1";
	static int package_date_flag = 0;
	public static boolean toggleFlag = false;
	 
	
	public static String paymentNav = "0";
	
	
	public static String currentDate;
	public static String CheckInDate;
	
	public static String CheckInDateStr;
	public static String CheckOutDateStr;

	public static int cdt_id = 0;
//	public static String Base_url = "http://172.16.0.49/telangana_tourism/WebServices/";
	public static String Base_url = "http://ttourdev.etisbew.net/WebServices/";
//	public static String Base_url1 = "http://172.16.3.30:8080/tgtdc/etg/";
//	public static String Base_url1 = "http://202.65.147.152:8080/tgtdc/etg/";
	public static String Base_url1 = "http://tstdc.in/tgtdc/etg/";
	public static boolean update_service_flag = false;
	public static boolean destinations_flag = false;
	public static boolean accomodation_flag = false;
	public static boolean packages_flag = false;
	public static boolean culture_flag = false;
	
	public static String day_string = "";
	public static String day_number = "";
	
	
	public static int date_flg=0;
	public static int selected_day;
	public static int selected_month;
	public static int selected_year;
	public static int ebs_flg=0;
	
	public static int RTypeAvailableRooms = 0;
	public static String max_allowed_rooms_str = "";
	public static String no_of_adults_str="";
	
	 public static Dialog dialog;
//	public static void showCustomDialog(String str) {
//
//		dialog = new Dialog(cont, android.R.style.Theme_Translucent);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//		dialog.setCancelable(true);
//		dialog.setContentView(R.layout.dialog1);
//
//		TextView textView = (TextView) dialog.findViewById(R.id.tv);
//		textView.setText(str);
//		Button btnSearch = (Button) dialog.findViewById(R.id.btnsearch);
//		// btnCancel = (Button) dialog.findViewById(R.id.btncancel);
//
//		btnSearch.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dialog.dismiss();
//			}
//		});
//		// btnCancel.setOnClickListener(this);
//
//		dialog.show();
//	}
	
}
