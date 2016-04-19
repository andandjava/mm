package com.telanganatourism.android.phase2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.telanganatourism.android.phase2.util.Utility;

public class MyreservationsFragment extends Fragment implements OnClickListener {
	
	private GestureDetector gestureDetector;
//	Utility utility;
	ListView listView1,listView2;
	TextView txtMsg,tv_hotels,tv_packages,tv_sno,tv_Date,tv_hotel;
	Dialog dialog;
	List<ItemBean> arrayOfList,arrayOfList1;
	
	String reservationNo1="",strExpCheckinDate1="",strExpCheckinTime1="",
		strExpCheckoutDate1="",	strExpCheckoutTime1="",strNoDays1="",
		strRoomTypeName1="",strNoOfRooms1="",strNoOfPaxAdult1="",
		strNoOfPaxChild1="",strTariffAmount1="",strTaxAmount1="",
		strstaxes1="",strTotalRoomTariff1=""	,strUnitName1="";	
	
	//for packages:
	String strP_reservationNo="",strP_serviceName="",strP_packageName="",
			strP_seatNo="",	strP_vehicleType="",strP_journeyDate="",
			strP_journeyTime="",strP_adultSeats="",strP_childSeats="",
			strP_boarding="",strP_totalFare="";
	
	RelativeLayout rr_packages,	rr_hotels;
	
	int txtMsgHotelsVisibilityFlag = 0, txtMsgPackageVisibilityFlag = 0;
	
	public MyreservationsFragment() {
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		gestureDetector = new GestureDetector(new SwipeGestureDetector());

		View rootView = inflater.inflate(R.layout.myreservations, container,
				false);
//		utility = new Utility(getActivity());
		arrayOfList = new ArrayList<ItemBean>();
		arrayOfList1 = new ArrayList<ItemBean>();
		
		listView1=(ListView) rootView.findViewById(R.id.listView1);
		listView2=(ListView) rootView.findViewById(R.id.listView2);
		
		txtMsg=(TextView) rootView.findViewById(R.id.txtMsg);
		tv_hotels=(TextView) rootView.findViewById(R.id.tv_hotels);
		tv_packages=(TextView) rootView.findViewById(R.id.tv_packages);
		
		 tv_sno = (TextView)rootView.findViewById(R.id.tv_sno);
		 tv_Date = (TextView)rootView.findViewById(R.id.tv_Date);
		 tv_hotel = (TextView)rootView.findViewById(R.id.tv_hotel);
		 
		 rr_packages = (RelativeLayout)rootView.findViewById(R.id.rr_packages);
		 rr_hotels = (RelativeLayout)rootView.findViewById(R.id.rr_hotels);
		
		tv_sno.setTypeface(Constants.ProximaNova_Regular);
		tv_Date.setTypeface(Constants.ProximaNova_Regular);
		tv_hotel.setTypeface(Constants.ProximaNova_Regular);
		tv_hotels.setTypeface(Constants.ProximaNova_Regular);
		tv_packages.setTypeface(Constants.ProximaNova_Regular);
		
		listView1.setVisibility(View.VISIBLE);
		listView2.setVisibility(View.GONE);
		tv_hotel.setText("HOTEL NAME");
		tv_hotels.setTextColor(Color.parseColor("#cf8837"));
		tv_packages.setTextColor(Color.parseColor("#838383"));
//		tv_hotels.setBackgroundResource(R.drawable.borderbg_bold);
//		tv_packages.setBackgroundResource(R.drawable.borderbg_shade);
		rr_packages.setBackgroundResource(R.drawable.grey1);
		 rr_hotels.setBackgroundResource(R.drawable.grey_blue);

		
		if (Utility.checkInternetConnection(getActivity())) {
			new MyHotelsReservationTask().execute();
		} else {
			Utility.showAlertNoInternetService(getActivity());
		}
		
		tv_hotels.setOnClickListener(this);
		tv_packages.setOnClickListener(this);

		MainActivity.txt_logo.setVisibility(View.GONE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setText(getResources().getString(
				R.string.reservation_sidemenu));

		MainActivity.txt_title.setTextColor(Color.parseColor("#000000"));

		MainActivity.txt_filter.setVisibility(View.GONE);

		MainActivity.txt_location.setVisibility(View.GONE);

		MainActivity.txt_search.setVisibility(View.GONE);

		MainActivity.txt_Edit.setVisibility(View.GONE);

		MainActivity.btn_menu.setBackgroundResource(R.drawable.menu_green);

		MainActivity.headerLayout.setBackgroundColor(Color
				.parseColor("#ffffff"));
		
		
		
		listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				ItemBean itemsObj = (ItemBean) arrayOfList.get(arg2); 
				
				reservationNo1 = itemsObj.getReservationNo();
				strExpCheckinDate1=itemsObj.getStrExpCheckinDate();
				strExpCheckinTime1=itemsObj.getStrExpCheckinTime();
				
				strExpCheckoutDate1=itemsObj.getStrExpCheckoutDate();
				strExpCheckoutTime1=itemsObj.getStrExpCheckoutTime();
				strNoDays1=itemsObj.getStrNoDays();
				
				strRoomTypeName1=itemsObj.getStrRoomTypeName();
				strNoOfRooms1=itemsObj.getStrNoOfRooms();
				strNoOfPaxAdult1=itemsObj.getStrNoOfPaxAdult();
				
				strNoOfPaxChild1=itemsObj.getStrNoOfPaxChild();
				strTariffAmount1=itemsObj.getStrTariffAmount();
				strTaxAmount1=itemsObj.getStrTaxAmount();
				
				strstaxes1=itemsObj.getStrstaxes();
				strTotalRoomTariff1=itemsObj.getStrTotalRoomTariff();
				strUnitName1=	itemsObj.getStrUnitName();			
				showHotelReservationsDetails();

			}
		});
		
		listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				ItemBean itemsObj1 = (ItemBean) arrayOfList1.get(arg2); 
				
				
				
				strP_reservationNo = itemsObj1.getStrPreservationNo();
				strP_serviceName=itemsObj1.getStrPserviceName();
				strP_packageName=itemsObj1.getStrPackageName();
				
				strP_seatNo=itemsObj1.getStrPseatNo();
				strP_vehicleType=itemsObj1.getStrPvehicleType();
				strP_journeyDate=itemsObj1.getStrPjourneyDate();
				
				strP_journeyTime=itemsObj1.getStrPjourneyTime();
				strP_adultSeats=itemsObj1.getStrPadultSeats();
				strP_childSeats=itemsObj1.getStrPchildSeats();
				
				strP_boarding=itemsObj1.getStrPboarding();
				strP_totalFare=itemsObj1.getStrPtotalFare();
				showPackagesReservationsDetails();
//				showHotelReservationsDetails();

			}
		});

		return rootView;
	}
	protected void showHotelReservationsDetails() {
		// TODO Auto-generated method stub
		

			dialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar); //				 android.R.style.Theme_Translucent_NoTitleBar););
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.myreservations_row);
			

			TextView tv_ref_id = (TextView) dialog.findViewById(R.id.tv_ref_id);
			TextView tv_value_checkin = (TextView) dialog.findViewById(R.id.tv_value_checkin);
			TextView tv_value_checkout = (TextView) dialog.findViewById(R.id.tv_value_checkout);
			TextView nightStayValTxt = (TextView) dialog.findViewById(R.id.nightStayValTxt);
			
			TextView tv_value_adults1 = (TextView) dialog.findViewById(R.id.tv_value_adults1);
			TextView tv_value_children1 = (TextView) dialog.findViewById(R.id.tv_value_children1);
			TextView tv_value_rtype = (TextView) dialog.findViewById(R.id.tv_value_rtype);
			
			TextView roomtariff = (TextView) dialog.findViewById(R.id.roomtariff);
			TextView luxTaxTxt = (TextView) dialog.findViewById(R.id.luxTaxTxt);
			TextView serviceTaxtxt = (TextView) dialog.findViewById(R.id.serviceTaxtxt);
			TextView grandTotal_Result = (TextView) dialog.findViewById(R.id.grandTotal_Result);
			TextView roomsTxt = (TextView) dialog.findViewById(R.id.roomsTxt);
			TextView roomsValTxt = (TextView) dialog.findViewById(R.id.roomsValTxt);
			
			TextView tv_text_checkin = (TextView) dialog.findViewById(R.id.tv_text_checkin);
			TextView tv_text_checkout = (TextView) dialog.findViewById(R.id.tv_text_checkout);
			TextView nightTxt = (TextView) dialog.findViewById(R.id.nightTxt);
			TextView tv_text_occupants1 = (TextView) dialog.findViewById(R.id.tv_text_occupants1);
			TextView tv_txt_rtype = (TextView) dialog.findViewById(R.id.tv_txt_rtype);
			TextView textView_ta1 = (TextView) dialog.findViewById(R.id.textView_ta1);
			TextView textView_inc1 = (TextView) dialog.findViewById(R.id.textView_inc1);
			TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
			TextView tv_summary = (TextView) dialog.findViewById(R.id.tv_summary);
			TextView tv_txt_rtypecost = (TextView) dialog.findViewById(R.id.tv_txt_rtypecost);
			
			Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
			
			//
			tv_ref_id.setTypeface(Constants.ProximaNova_Regular);
			tv_value_checkin.setTypeface(Constants.ProximaNova_Regular);
			tv_value_checkout.setTypeface(Constants.ProximaNova_Regular);
			nightStayValTxt.setTypeface(Constants.ProximaNova_Regular);
			tv_value_adults1.setTypeface(Constants.ProximaNova_Regular);
			tv_value_children1.setTypeface(Constants.ProximaNova_Regular);
			tv_value_rtype.setTypeface(Constants.ProximaNova_Regular);
			roomtariff.setTypeface(Constants.ProximaNova_Regular);
			luxTaxTxt.setTypeface(Constants.ProximaNova_Regular);
			serviceTaxtxt.setTypeface(Constants.ProximaNova_Regular);
			grandTotal_Result.setTypeface(Constants.ProximaNova_Regular);
			roomsTxt.setTypeface(Constants.ProximaNova_Regular);
			roomsValTxt.setTypeface(Constants.ProximaNova_Regular);
			tv_title.setTypeface(Constants.ProximaNova_Regular);
			tv_txt_rtypecost.setTypeface(Constants.ProximaNova_Regular);
			
			 tv_text_checkin.setTypeface(Constants.ProximaNova_Regular);
			 tv_text_checkout.setTypeface(Constants.ProximaNova_Regular);
			 nightTxt.setTypeface(Constants.ProximaNova_Regular);
			 tv_text_occupants1.setTypeface(Constants.ProximaNova_Regular);
			 tv_txt_rtype.setTypeface(Constants.ProximaNova_Regular);
			 textView_ta1.setTypeface(Constants.ProximaNova_Regular);
			 textView_inc1.setTypeface(Constants.ProximaNova_Regular);
			 tv_summary.setTypeface(Constants.ProximaNova_Regular);
			
			 
			 
			 
			 
			    tv_ref_id.setText("Res No:"+reservationNo1);
				tv_value_checkin.setText(strExpCheckinDate1+"  "+strExpCheckinTime1);
				tv_value_checkout.setText(strExpCheckoutDate1+"  "+strExpCheckoutTime1);
				nightStayValTxt.setText(strNoDays1);
//				try {
//					
//					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//					String dateInString = strExpCheckinDate1;
//					
//					String dateInString1 = strExpCheckoutDate1;
//					
//					Date date = formatter.parse(dateInString);
//					Date date1 = formatter.parse(dateInString1);
//					
//					nightStayValTxt.setText(String.valueOf(daysBetween(date, date1)));
//					
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				
				tv_value_adults1.setText("ADULTS"+"\n"+"     "+strNoOfPaxAdult1);
				tv_value_children1.setText("CHILDREN"+"\n"+"     "+strNoOfPaxChild1);
				tv_value_rtype.setText(strRoomTypeName1);
				
				roomtariff.setText("Tariff : "+strTariffAmount1);
				
				
               double luxery_splitValue = 0.00;
				
				if(strTaxAmount1.contains(",")){
					String[] lux_part = strTaxAmount1.split(", ");
					for (int i = 0; i < lux_part.length; i++) {
						
						String part1 = lux_part[i];
						
						luxery_splitValue = Double.valueOf(part1)+luxery_splitValue;
						
					}
				}else{
					luxery_splitValue = Double.valueOf(strTaxAmount1);
				}
				
				
				
				
				luxTaxTxt.setText("Luxury Tax : "+luxery_splitValue);
				
              double service_splitValue = 0.00;
				if(strstaxes1.contains(",")){
					String[] ser_part = strstaxes1.split(", ");
					for (int i = 0; i < ser_part.length; i++) {
						
						String part1 = ser_part[i];
						
						service_splitValue = Double.valueOf(part1)+service_splitValue;
						
					}
				}else{
					service_splitValue = Double.valueOf(strstaxes1);
				}
				serviceTaxtxt.setText("Service Tax : "+service_splitValue);
				
				
				double splitValue = 0.00;
				if(strTotalRoomTariff1.contains(",")){
					String[] part = strTotalRoomTariff1.split(", ");
					for (int i = 0; i < part.length; i++) {
						String part1 = part[i];
						splitValue = Double.valueOf(part1)+splitValue;
					}
				}else{
					splitValue = Double.valueOf(strTotalRoomTariff1);
				}
				
				grandTotal_Result.setText("Rs. "+splitValue);
				roomsValTxt.setText(strNoOfRooms1);
				tv_title.setText(strUnitName1);
			 

				btnClose.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				

				dialog.show();
			
		
	}
//	public int daysBetween(Date d1, Date d2) {
//		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
//	}
	
	protected void showPackagesReservationsDetails() {
		// TODO Auto-generated method stub
		

			dialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar); //				 android.R.style.Theme_Translucent_NoTitleBar););
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.myreservations_row_packages);
			

			TextView tv_res_id = (TextView) dialog.findViewById(R.id.tv_ref_id);
			TextView tv_packagename = (TextView) dialog.findViewById(R.id.tv_title);
			TextView tv_serviceName = (TextView) dialog.findViewById(R.id.tv_serviceName);
			TextView tv_vehicletype = (TextView) dialog.findViewById(R.id.tv_vehicletype);
			TextView tvjourneydateTime = (TextView) dialog.findViewById(R.id.tvjourneydateTime);
			TextView tv_seat_numbers = (TextView) dialog.findViewById(R.id.tv_seat_numbers);
			TextView tv_Pvalue_adults1 = (TextView) dialog.findViewById(R.id.tv_value_adults1);
			TextView tv_Pvalue_children1 = (TextView) dialog.findViewById(R.id.tv_value_children1);
			TextView tv_Boarding_place = (TextView) dialog.findViewById(R.id.tv_Boarding_place);
			TextView PgrandTotal_Result = (TextView) dialog.findViewById(R.id.grandTotal_Result);
			TextView tv_text_occupants1=(TextView) dialog.findViewById(R.id.tv_text_occupants1);
//			TextView serviceTaxtxt = (TextView) dialog.findViewById(R.id.serviceTaxtxt);
//			TextView grandTotal_Result = (TextView) dialog.findViewById(R.id.grandTotal_Result);
//			TextView roomsTxt = (TextView) dialog.findViewById(R.id.roomsTxt);
//			TextView roomsValTxt = (TextView) dialog.findViewById(R.id.roomsValTxt);
//			
//			TextView tv_text_checkin = (TextView) dialog.findViewById(R.id.tv_text_checkin);
//			TextView tv_text_checkout = (TextView) dialog.findViewById(R.id.tv_text_checkout);
//			TextView nightTxt = (TextView) dialog.findViewById(R.id.nightTxt);
//			TextView tv_text_occupants1 = (TextView) dialog.findViewById(R.id.tv_text_occupants1);
//			TextView tv_txt_rtype = (TextView) dialog.findViewById(R.id.tv_txt_rtype);
//			TextView textView_ta1 = (TextView) dialog.findViewById(R.id.textView_ta1);
//			TextView textView_inc1 = (TextView) dialog.findViewById(R.id.textView_inc1);
			TextView tv_summary=(TextView) dialog.findViewById(R.id.tv_summary);
			TextView textView_ta1=(TextView) dialog.findViewById(R.id.textView_ta1);
			TextView textView_inc1=(TextView) dialog.findViewById(R.id.textView_inc1);
			TextView tv_no_of_seats=(TextView) dialog.findViewById(R.id.tv_no_of_seats);
			Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
						//
			tv_res_id.setTypeface(Constants.ProximaNova_Regular);
			tv_packagename.setTypeface(Constants.ProximaNova_Regular);
			tv_serviceName.setTypeface(Constants.ProximaNova_Regular);
			tv_vehicletype.setTypeface(Constants.ProximaNova_Regular);
			tvjourneydateTime.setTypeface(Constants.ProximaNova_Regular);
			tv_seat_numbers.setTypeface(Constants.ProximaNova_Regular);
			tv_Pvalue_adults1.setTypeface(Constants.ProximaNova_Regular);
			tv_Pvalue_children1.setTypeface(Constants.ProximaNova_Regular);
			tv_Boarding_place.setTypeface(Constants.ProximaNova_Regular);
			PgrandTotal_Result.setTypeface(Constants.ProximaNova_Regular);
			tv_summary.setTypeface(Constants.ProximaNova_Regular);
			tv_text_occupants1.setTypeface(Constants.ProximaNova_Regular);
			textView_ta1.setTypeface(Constants.ProximaNova_Regular);
			textView_inc1.setTypeface(Constants.ProximaNova_Regular);
			tv_no_of_seats.setTypeface(Constants.ProximaNova_Regular);
//			roomsTxt.setTypeface(Constants.ProximaNova_Regular);
//			roomsValTxt.setTypeface(Constants.ProximaNova_Regular);
//			tv_title.setTypeface(Constants.ProximaNova_Regular);
			
			
//			 tv_text_checkin.setTypeface(Constants.ProximaNova_Regular);
//			 tv_text_checkout.setTypeface(Constants.ProximaNova_Regular);
//			 nightTxt.setTypeface(Constants.ProximaNova_Regular);
//			 tv_text_occupants1.setTypeface(Constants.ProximaNova_Regular);
//			 tv_txt_rtype.setTypeface(Constants.ProximaNova_Regular);
//			 textView_ta1.setTypeface(Constants.ProximaNova_Regular);
//			 textView_inc1.setTypeface(Constants.ProximaNova_Regular);
			
//			strP_reservationNo = itemsObj1.getStrPreservationNo();
//			strP_serviceName=itemsObj1.getStrPserviceName();
//			strP_packageName=itemsObj1.getStrPackageName();
//			
//			strP_seatNo=itemsObj1.getStrPseatNo();
//			strP_vehicleType=itemsObj1.getStrPvehicleType();
//			strP_journeyDate=itemsObj1.getStrPjourneyDate();
//			
//			strP_journeyTime=itemsObj1.getStrPjourneyTime();
//			strP_adultSeats=itemsObj1.getStrPadultSeats();
//			strP_childSeats=itemsObj1.getStrPchildSeats();
//			
//			strP_boarding=itemsObj1.getStrPboarding();
//			strP_totalFare=itemsObj1.getStrPtotalFare();
			 
			   tv_packagename.setText(strP_packageName);
			   tv_res_id.setText("Res No:"+strP_reservationNo);
			   tv_Pvalue_adults1.setText("ADULTS"+"\n"+"     "+strP_adultSeats);
			   tv_Pvalue_children1.setText("CHILDREN"+"\n"+"     "+strP_childSeats);
			   tv_serviceName.setText(strP_serviceName);
			   tv_vehicletype.setText(strP_vehicleType);
			   tvjourneydateTime.setText("JOURNEY DATE : "+strP_journeyDate+"  TIME : "+strP_journeyTime);
			   PgrandTotal_Result.setText("Rs. "+strP_totalFare);
			   tv_Boarding_place.setText("BOARDING :"+strP_boarding);
			   tv_seat_numbers.setText(strP_seatNo);
			   
//				tv_value_checkin.setText(strExpCheckinDate1+"  "+strExpCheckinTime1);
//				tv_value_checkout.setText(strExpCheckoutDate1+"  "+strExpCheckoutTime1);
//				nightStayValTxt.setText(strNoDays1);
//				
//				tv_value_adults1.setTypeface(Constants.ProximaNova_Regular);
//				tv_value_children1.setTypeface(Constants.ProximaNova_Regular);
//				
//				
//				tv_value_rtype.setText(strRoomTypeName1);
//				
//				roomtariff.setText("Tariff : "+strTariffAmount1);
//				luxTaxTxt.setText("Luxury Tax : "+strTaxAmount1);
//				serviceTaxtxt.setText("Service Tax : "+strstaxes1);
//				grandTotal_Result.setText("Rs. "+strTotalRoomTariff1);
//				roomsValTxt.setText(strNoOfRooms1);
				
			 

				btnClose.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				

				dialog.show();
			
		
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
//			makeAcall("04024745243");
		}
		
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
		private class MyHotelsReservationTask extends AsyncTask<String, Void, String> {

			ProgressDialog pDialog;

			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage("Loading...");
				pDialog.setCancelable(false);
				pDialog.show();
			}
//			9866786289
			@Override
			protected String doInBackground(String... params) {
				System.out.println("url is"+Constants.Base_url1
						+ "myreservations.jsp?r=htl&mobile="+SplashScreen.pref.getString(
								SplashScreen.Key_GET_USER_PHONE_NO, ""));
				return ServiceCalls1.getJSONString(Constants.Base_url1
						+ "myreservations.jsp?r=htl&mobile="+SplashScreen.pref.getString(
								SplashScreen.Key_GET_USER_PHONE_NO, ""));
				
//				return ServiceCalls1.getJSONString("http://tstdc.in/tgtdc/etg/myreservations.jsp?r=htl&mobile=9966966339");
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				
				if (null != pDialog && pDialog.isShowing()) {
					pDialog.dismiss();
				}

				if (null == result || result.length() == 0) {
					showDialogMsg(getActivity(), "No data found from web!!!");
				} else {
					
				try {
					
					JSONArray json = new JSONArray(result);
					System.out.println("result"+result);
					
//						JSONObject jObject = new JSONObject(result);
						if (json.length()==0) 
						{
							listView1.setVisibility(View.GONE);
							listView2.setVisibility(View.GONE);
							txtMsg.setVisibility(View.VISIBLE);
							
							txtMsgHotelsVisibilityFlag = 1;
						} else {
							
							txtMsgHotelsVisibilityFlag = 0;
//							JSONArray menuitemArray = jObject.getJSONArray("baby");
//							if(menuitemArray.length()>0){
//								babies_listview.setVisibility(View.VISIBLE);
//								tv_no_record1.setVisibility(View.GONE);
							
							for (int i = 0; i < json.length(); i++) {
								JSONObject e = json.getJSONObject(i);
								
								ItemBean objItem = new ItemBean();								
								
								SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
								Date date = new Date();
								System.out.println(sdf.format(date).toString());
								
								if(!TextUtils.isEmpty(e.getString("strExpCheckinDate"))){
									
									Date date2 = sdf.parse(sdf.format(date).toString());
						        	Date date1 = sdf.parse(e.getString("strExpCheckinDate"));

						        	System.out.println(sdf.format(date1));
						        	System.out.println(sdf.format(date2));
						        	
						        	if(date1.compareTo(date2)>=0){
						        		System.out.println("Date1 is after Date2");
						        		
						        		objItem.setReservationNo(e.getString("reservationNo"));					        	
										objItem.setStrExpCheckinDate(e.getString("strExpCheckinDate"));
										objItem.setStrExpCheckinTime(e.getString("strExpCheckinTime"));
										objItem.setStrExpCheckoutDate(e.getString("strExpCheckoutDate"));
										objItem.setStrExpCheckoutTime(e.getString("strExpCheckoutTime"));
										
										objItem.setStrNoDays(e.getString("strNoDays"));
										objItem.setStrRoomTypeName(e.getString("strRoomTypeName"));
										objItem.setStrNoOfRooms(e.getString("strNoOfRooms"));
										objItem.setStrNoOfPaxAdult(e.getString("strNoOfPaxAdult"));
										objItem.setStrNoOfPaxChild(e.getString("strNoOfPaxChild"));
										
										objItem.setStrTariffAmount(e.getString("strTariffAmount"));
										objItem.setStrTaxAmount(e.getString("strTaxAmount"));
										objItem.setStrstaxes(e.getString("strstaxes"));
										objItem.setStrTotalRoomTariff(e.getString("strTotalRoomTariff"));
										objItem.setStrUnitName(e.getString("strUnitName"));
										
										arrayOfList.add(objItem);
										
						        	}
								}
								
								
							}
							listView1.setAdapter(new CustomReservationsList(getActivity(),
									R.layout.myreservations_row1, arrayOfList));
						}
//						}
						

						if (pDialog.isShowing())
							pDialog.dismiss();
				} catch (Exception e) {
				}

			}
		}

	}
		
		private class MyPackagesReservationTask extends AsyncTask<String, Void, String> {

			ProgressDialog pDialog;

			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage("Loading...");
				pDialog.setCancelable(false);
				pDialog.show();
			}
//			9866786289
			@Override
			protected String doInBackground(String... params) {
				System.out.println("url is"+Constants.Base_url1
						+ "myreservations.jsp?r=wrs&mobile="+SplashScreen.pref.getString(
								SplashScreen.Key_GET_USER_PHONE_NO, ""));
				return ServiceCalls1.getJSONString(Constants.Base_url1
						+ "myreservations.jsp?r=wrs&mobile="+SplashScreen.pref.getString(
								SplashScreen.Key_GET_USER_PHONE_NO, ""));
				
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				
				if (null != pDialog && pDialog.isShowing()) {
					pDialog.dismiss();
				}

				if (null == result || result.length() == 0) {
					showDialogMsg(getActivity(), "No data found from web!!!");
				} else {
					
				try {
					
					JSONArray json = new JSONArray(result);
					System.out.println("result"+result);
					
						if (json.length()==0) 
						{
							listView1.setVisibility(View.GONE);
							listView2.setVisibility(View.GONE);
							txtMsg.setVisibility(View.VISIBLE);
							txtMsgPackageVisibilityFlag = 1;
						} else {
							
							txtMsgPackageVisibilityFlag = 0;
							for (int i = 0; i < json.length(); i++) {
								JSONObject e = json.getJSONObject(i);
								
								ItemBean objItem1 = new ItemBean();								
								
//								SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
								Date date = new Date();
								System.out.println(sdf1.format(date).toString());
								
								Date date2 = sdf1.parse(sdf1.format(date).toString());
					        	Date date1 = sdf1.parse(e.getString("journeyDate"));

					        	System.out.println(sdf1.format(date1));
					        	System.out.println(sdf1.format(date2));
					        	
					        	if(date1.compareTo(date2)>=0){
					        		System.out.println("Date1 is after Date2");
					        		
					        		objItem1.setStrPreservationNo(e.getString("reservationNo"));	
									objItem1.setStrPserviceName(e.getString("serviceName"));
									objItem1.setStrPackageName(e.getString("packageName"));
									
									objItem1.setStrPseatNo(e.getString("seatNo"));
									objItem1.setStrPvehicleType(e.getString("vehicleType"));
									objItem1.setStrPjourneyDate(e.getString("journeyDate"));
									objItem1.setStrPjourneyTime(e.getString("journeyTime"));
									
									objItem1.setStrPadultSeats(e.getString("adultSeats"));
									objItem1.setStrPchildSeats(e.getString("childSeats"));
									objItem1.setStrPboarding(e.getString("boarding"));
									objItem1.setStrPtotalFare(e.getString("totalFare"));
									
									arrayOfList1.add(objItem1);
									
					        	}
								
							}
							listView2.setAdapter(new CustomPackagesReservationsList(getActivity(),
									R.layout.myreservations_row1, arrayOfList1));
						}
						

						if (pDialog.isShowing())
							pDialog.dismiss();
				} catch (Exception e) {
				}

			}
		}

	}	
		public void showDialogMsg(Context con, String msg) {
			AlertDialog.Builder builder = new AlertDialog.Builder(con);
			builder.setMessage(msg).setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		}
		
		private class CustomReservationsList extends
		ArrayAdapter<ItemBean> {
			private Activity activity;
			private List<ItemBean> items;
			private ItemBean objBean;
			private int row;
			ViewHolder holder;
			Date date1, date2;

	public CustomReservationsList(Activity act, int resource,
			List<ItemBean> arrayList) {
	super(act, resource, arrayList);
	this.activity = act;
	this.row = resource;
	this.items = arrayList;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView,
			ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);
			holder = new ViewHolder();
			
			holder.tv_resNo1 = (TextView) view.findViewById(R.id.tv_resNo1);
//			if (holder.tv_sno1 != null) {
//				holder.tv_sno1.setTypeface(Constants
//						.getTf_lato_regular(activity));
//			}
			
			holder.tv_hotel1 = (TextView) view.findViewById(R.id.tv_hotel1);
			holder.tv_sno1 = (TextView) view.findViewById(R.id.tv_sno1);
			
			holder.tv_resNo1.setTypeface(Constants.ProximaNova_Regular);
			holder.tv_hotel1.setTypeface(Constants.ProximaNova_Regular);
			holder.tv_sno1.setTypeface(Constants.ProximaNova_Regular);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		if ((items == null) || ((position + 1) > items.size()))
			return view;

		objBean = items.get(position);

		if (holder.tv_resNo1 != null
				&& !TextUtils.isEmpty(objBean.getStrExpCheckinDate())) {
//			holder.txtReviewDate.setTypeface(Constants
//					.getTf_lato_regular(activity));
			holder.tv_resNo1.setText(objBean.getStrExpCheckinDate());
		}
		if (holder.tv_hotel1 != null
				&& !TextUtils.isEmpty(objBean.getStrUnitName())) {
//			holder.txtReviewDate.setTypeface(Constants
//					.getTf_lato_regular(activity));
			holder.tv_hotel1.setText(objBean.getStrUnitName());
		}
		int sno= position+1;
		holder.tv_sno1.setText(""+sno);
		
		return view;
	}
	public class ViewHolder {
		public TextView tv_sno1,tv_resNo1,tv_hotel1;

	}
	}
		
		private class CustomPackagesReservationsList extends
		ArrayAdapter<ItemBean> {
			private Activity activity;
			private List<ItemBean> items;
			private ItemBean objBean;
			private int row;
			ViewHolder holder;
			Date date1, date2;

	public CustomPackagesReservationsList(Activity act, int resource,
			List<ItemBean> arrayList) {
	super(act, resource, arrayList);
	this.activity = act;
	this.row = resource;
	this.items = arrayList;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView,
			ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);
			holder = new ViewHolder();
			
			holder.tv_pjourneydate = (TextView) view.findViewById(R.id.tv_resNo1);
			holder.tv_pservicename = (TextView) view.findViewById(R.id.tv_hotel1);
			holder.tv_psno = (TextView) view.findViewById(R.id.tv_sno1);
			
			holder.tv_pjourneydate.setTypeface(Constants.ProximaNova_Regular);
			holder.tv_pservicename.setTypeface(Constants.ProximaNova_Regular);
			holder.tv_psno.setTypeface(Constants.ProximaNova_Regular);
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		if ((items == null) || ((position + 1) > items.size()))
			return view;

		objBean = items.get(position);

		if (holder.tv_pjourneydate != null
				&& !TextUtils.isEmpty(objBean.getStrPjourneyDate())) {
			holder.tv_pjourneydate.setText(objBean.getStrPjourneyDate());
		}
		if (holder.tv_pservicename != null
				&& !TextUtils.isEmpty(objBean.getStrPserviceName())) {
			holder.tv_pservicename.setText(objBean.getStrPserviceName());
		}
		int sno= position+1;
		holder.tv_psno.setText(""+sno);
		
		return view;
	}
	public class ViewHolder {
		public TextView tv_psno,tv_pjourneydate,tv_pservicename;

	}
	}		

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			case R.id.tv_hotels:
				
				tv_hotel.setText("HOTEL NAME");
				tv_hotels.setTextColor(Color.parseColor("#cf8837"));
				tv_packages.setTextColor(Color.parseColor("#838383"));
//				tv_hotels.setBackgroundResource(R.drawable.borderbg_bold);
//				tv_packages.setBackgroundResource(R.drawable.borderbg_shade);
				rr_packages.setBackgroundResource(R.drawable.grey1);
				 rr_hotels.setBackgroundResource(R.drawable.grey_blue);
				listView1.setVisibility(View.VISIBLE);
				listView2.setVisibility(View.GONE);
				
				if(txtMsgHotelsVisibilityFlag == 1){
					txtMsg.setVisibility(View.VISIBLE);
				}else{
					txtMsg.setVisibility(View.GONE);
				}
				
				
//				if (Utility.checkInternetConnection(getActivity())) {
//					new MyHotelsReservationTask().execute();
//				} else {
//					Utility.showAlertNoInternetService(getActivity());
//				}
				break;
				
			case R.id.tv_packages:
				arrayOfList1.clear();
				tv_hotel.setText("PACKAGE NAME");
				tv_hotels.setTextColor(Color.parseColor("#838383"));
				tv_packages.setTextColor(Color.parseColor("#cf8837"));
//				tv_packages.setBackgroundResource(R.drawable.borderbg_bold);
//				tv_hotels.setBackgroundResource(R.drawable.borderbg_shade);
				rr_packages.setBackgroundResource(R.drawable.grey_blue);
				 rr_hotels.setBackgroundResource(R.drawable.grey1);
				listView1.setVisibility(View.GONE);
				listView2.setVisibility(View.VISIBLE);
				if(txtMsgPackageVisibilityFlag == 1){
					txtMsg.setVisibility(View.VISIBLE);
				}else{
					txtMsg.setVisibility(View.GONE);
				}
				
				if (Utility.checkInternetConnection(getActivity())) {
					new MyPackagesReservationTask().execute();
				} else {
					Utility.showAlertNoInternetService(getActivity());
				}
				
				break;
				
			}
			
		}
		

}
