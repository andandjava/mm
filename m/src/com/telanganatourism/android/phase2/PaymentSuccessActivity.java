package com.telanganatourism.android.phase2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telanganatourism.android.phase2.util.Utility;

public class PaymentSuccessActivity extends Activity {
	
	String strResponse;
	String PaymentId;
	String AccountId;
	String MerchantRefNo;
	String Amount;
	String DateCreated;
	String Description;
	String Mode;
	String IsFlagged;
	String BillingName;
	String BillingAddress;
	String BillingCity;
	String BillingState;
	String BillingPostalCode;
	String BillingCountry;
	String BillingPhone;
	String BillingEmail;
	String DeliveryName;
	String DeliveryAddress;
	String DeliveryCity;
	String DeliveryState;
	String DeliveryPostalCode;
	String DeliveryCountry;
	String DeliveryPhone;
	String PaymentStatus;
	String PaymentMode;
	String SecureHash;
	String strResponsecode;
	String strTransactionId;
	//for Packages
	TextView tv_hotel_name,package_title_txt,
	package_type_txt,available_txt,tv_no_of_seats,tv_seat_numbers,tv_Boarding_place,
	tv_Boarding_date,grandTotal_Result,tv_value_adults,tv_value_children;
	RelativeLayout btn_payment_success,rr_package_layout;
	
	//for Hotels
	TextView tv_hotel_name1,tv_ref_id,tv_value_checkin,tv_value_checkout,nightStayValTxt,
	tv_value_adults1,tv_value_children1,tv_value_rtype,grandTotal_Result1, tv_no_ofrooms;
	RelativeLayout rr_hotels_layout;
	
	TextView tv_text_thanks, tv_text_thanks1,tv_text_checkin,tv_text_checkout,nightTxt,tv_text_occupants1,tv_txt_rtype,
	textView_ta1,textView_inc1,tv_text_emailbutom1,tv_back,tv_text_occupants,textView_ta,
	textView_inc,tv_text_emailbutom;
	
	//for failed
	TextView tv_text_thanks2,tv_txt1,tv_txt2,tv_txt4,tv_txt3,tv_txt5;
	RelativeLayout rr_failed_layout;
	
//	Utility utility;
	HttpResponse response;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.payment_success_packages);
		
//		utility = new Utility(PaymentSuccessActivity.this);
		
		//for
		tv_text_thanks=(TextView)findViewById(R.id.tv_text_thanks);
		tv_text_thanks1=(TextView)findViewById(R.id.tv_text_thanks1);
		tv_text_checkin=(TextView)findViewById(R.id.tv_text_checkin);
		tv_text_checkout=(TextView)findViewById(R.id.tv_text_checkout);
		nightTxt=(TextView)findViewById(R.id.nightTxt);
		tv_text_occupants1=(TextView)findViewById(R.id.tv_text_occupants1);
		tv_txt_rtype=(TextView)findViewById(R.id.tv_txt_rtype);
		textView_ta=(TextView)findViewById(R.id.textView_ta);
		textView_ta1=(TextView)findViewById(R.id.textView_ta1);
		textView_inc1=(TextView)findViewById(R.id.textView_inc1);
		textView_inc=(TextView)findViewById(R.id.textView_inc);
		tv_text_emailbutom1=(TextView)findViewById(R.id.tv_text_emailbutom1);
		tv_back=(TextView)findViewById(R.id.tv_back);
		tv_text_occupants=(TextView)findViewById(R.id.tv_text_occupants);
		tv_text_emailbutom=(TextView)findViewById(R.id.tv_text_emailbutom);
				
		
		tv_text_thanks.setTypeface(Constants.ProximaNova_Regular);
		tv_text_thanks1.setTypeface(Constants.ProximaNova_Regular);
		tv_text_checkin.setTypeface(Constants.ProximaNova_Regular);
		tv_text_checkout.setTypeface(Constants.ProximaNova_Regular);
		nightTxt.setTypeface(Constants.ProximaNova_Regular);
		tv_text_occupants1.setTypeface(Constants.ProximaNova_Regular);
		tv_txt_rtype.setTypeface(Constants.ProximaNova_Regular);
		textView_ta.setTypeface(Constants.ProximaNova_Regular);
		textView_ta1.setTypeface(Constants.ProximaNova_Regular);
		textView_inc1.setTypeface(Constants.ProximaNova_Regular);
		textView_inc.setTypeface(Constants.ProximaNova_Regular);
		tv_text_emailbutom1.setTypeface(Constants.ProximaNova_Regular);
		tv_back.setTypeface(Constants.ProximaNova_Regular);
		tv_text_occupants.setTypeface(Constants.ProximaNova_Regular);
		tv_text_emailbutom.setTypeface(Constants.ProximaNova_Regular);
		
		//for Packages
		tv_hotel_name=(TextView)findViewById(R.id.tv_hotel_name);
		package_title_txt=(TextView)findViewById(R.id.package_title_txt);
		package_type_txt=(TextView)findViewById(R.id.package_type_txt);
		available_txt=(TextView)findViewById(R.id.available_txt);
		tv_no_of_seats=(TextView)findViewById(R.id.tv_no_of_seats);
		tv_seat_numbers=(TextView)findViewById(R.id.tv_seat_numbers);
		tv_Boarding_place=(TextView)findViewById(R.id.tv_Boarding_place);
		tv_Boarding_date=(TextView)findViewById(R.id.tv_Boarding_date);
		grandTotal_Result=(TextView)findViewById(R.id.grandTotal_Result);
		tv_value_adults=(TextView)findViewById(R.id.tv_value_adults);
		tv_value_children=(TextView)findViewById(R.id.tv_value_children);
		rr_package_layout=(RelativeLayout)findViewById(R.id.rr_package_layout);
		
		tv_hotel_name.setTypeface(Constants.ProximaNova_Regular);
		package_title_txt.setTypeface(Constants.ProximaNova_Regular);
		package_type_txt.setTypeface(Constants.ProximaNova_Regular);
		available_txt.setTypeface(Constants.ProximaNova_Regular);
		tv_no_of_seats.setTypeface(Constants.ProximaNova_Regular);
		tv_seat_numbers.setTypeface(Constants.ProximaNova_Regular);
		tv_Boarding_place.setTypeface(Constants.ProximaNova_Regular);
		tv_Boarding_date.setTypeface(Constants.ProximaNova_Regular);
		grandTotal_Result.setTypeface(Constants.ProximaNova_Regular);
		tv_value_adults.setTypeface(Constants.ProximaNova_Regular);
		tv_value_children.setTypeface(Constants.ProximaNova_Regular);
		

		//for Hotels
		tv_hotel_name1=(TextView)findViewById(R.id.tv_hotel_name1);
		tv_ref_id=(TextView)findViewById(R.id.tv_ref_id);
		tv_value_checkin=(TextView)findViewById(R.id.tv_value_checkin);
		tv_value_checkout=(TextView)findViewById(R.id.tv_value_checkout);
		nightStayValTxt=(TextView)findViewById(R.id.nightStayValTxt);
		tv_value_adults1=(TextView)findViewById(R.id.tv_value_adults1);
		tv_value_children1=(TextView)findViewById(R.id.tv_value_children1);
		tv_value_rtype=(TextView)findViewById(R.id.tv_value_rtype);
		grandTotal_Result1=(TextView)findViewById(R.id.grandTotal_Result1);
		rr_hotels_layout=(RelativeLayout)findViewById(R.id.rr_hotels_layout);
		tv_no_ofrooms=(TextView)findViewById(R.id.tv_roomnumberValue);
		
		
		tv_hotel_name1.setTypeface(Constants.ProximaNova_Regular);
		tv_ref_id.setTypeface(Constants.ProximaNova_Regular);
		tv_value_checkin.setTypeface(Constants.ProximaNova_Regular);
		tv_value_checkout.setTypeface(Constants.ProximaNova_Regular);
		nightStayValTxt.setTypeface(Constants.ProximaNova_Regular);
		tv_value_adults1.setTypeface(Constants.ProximaNova_Regular);
		tv_value_children1.setTypeface(Constants.ProximaNova_Regular);
		tv_value_rtype.setTypeface(Constants.ProximaNova_Regular);
		grandTotal_Result1.setTypeface(Constants.ProximaNova_Regular);
		tv_no_ofrooms.setTypeface(Constants.ProximaNova_Regular);
		
		//for failed
		tv_text_thanks2=(TextView)findViewById(R.id.tv_text_thanks2);
		tv_txt1=(TextView)findViewById(R.id.tv_txt1);
		tv_txt2=(TextView)findViewById(R.id.tv_txt2);
		tv_txt3=(TextView)findViewById(R.id.tv_txt3);
		tv_txt4=(TextView)findViewById(R.id.tv_txt4);
		tv_txt5=(TextView)findViewById(R.id.tv_txt5);
		rr_failed_layout=(RelativeLayout)findViewById(R.id.rr_failed_layout);
		
		tv_text_thanks2.setTypeface(Constants.ProximaNova_Regular);
		tv_txt1.setTypeface(Constants.ProximaNova_Regular);
		tv_txt2.setTypeface(Constants.ProximaNova_Regular);
		tv_txt3.setTypeface(Constants.ProximaNova_Regular);
		tv_txt4.setTypeface(Constants.ProximaNova_Regular);
		tv_txt5.setTypeface(Constants.ProximaNova_Regular);
		
		
		Intent intent = getIntent();
		strResponse = intent.getStringExtra("payment_id");
		System.out.println("strResponse" + " " + strResponse);
		getJsonReport();
		
		
		
		
		if(PaymentStatus.equalsIgnoreCase("Authorized")){
			
			if(Constants.ebs_flg==0){
				
				rr_hotels_layout.setVisibility(View.VISIBLE);
				rr_package_layout.setVisibility(View.GONE);
				rr_failed_layout.setVisibility(View.GONE);
				
				//for Hotels
				tv_hotel_name1.setText("Your booking for "+DetailScreen1.tit+"  Successfully done!");
				tv_ref_id.setText("Ref ID : "+HotelBookingConfirm.StrID);
				tv_value_checkin.setText(Constants.CheckInDateStr);
				tv_value_checkout.setText(Constants.CheckOutDateStr);
				nightStayValTxt.setText(HotelBookingActivity.night_stay_str);
				tv_value_adults1.setText("ADULTS"+"\n"+HotelBookingConfirm.strTotalAdults);
				tv_value_children1.setText("CHILDREN"+"\n"+HotelBookingConfirm.strTotalChilds);
				grandTotal_Result1.setText("RS : "+HotelBookingConfirm.totalAmount);
				tv_value_rtype.setText(HotelBookingActivity.strRoomTypeName);
				tv_no_ofrooms.setText(HotelBookingActivity.StrIndRNum);
				
				/*for (int i = 0; i < HotelBookingActivity.selectedRoomsArray.size(); i++) {
//					tv_value_rtype.setText(new ArrayList<String>(
//							HotelBookingActivity.newRoomTypeHash.values()).get(i));
					
					
//					System.out.println("roomtypename"+HotelBookingConfirm.roomTypeNames.replaceFirst(",", ""));
					tv_no_ofrooms.setText(new ArrayList<String>(
							HotelBookingActivity.selectedRoomsArray.values()).get(i));
//					tv_roomnumberValue.setText(new ArrayList<String>(
//							HotelBookingActivity.selectedRoomsArray.values()).get(i));
					
				}
//				System.out.println("roomtypename"+HotelBookingConfirm.roomTypeNames.replaceFirst(",", ""));
			*/}else{
				rr_package_layout.setVisibility(View.VISIBLE);
				rr_hotels_layout.setVisibility(View.GONE);
				rr_failed_layout.setVisibility(View.GONE);
				
				//for Packages
				tv_hotel_name.setText("Your booking for "+PackageListing.pack_title.replace(",", " ")+" Package has Successfully done!");
				package_title_txt.setText(PackageListing.pack_title.replace(",", " "));
				package_type_txt.setText(PackageListing.pack_type);
				available_txt.setText("Duration : " + PackageListing.packagePeriod
						+ " Days");
				if(Package_Booking.no_of_people.equalsIgnoreCase("1"))
				{
					tv_no_of_seats.setText(Package_Booking.no_of_people+"seat");
				}
				else 
				{
					tv_no_of_seats.setText(Package_Booking.no_of_people+"seats");
				}
				tv_seat_numbers.setText(Package_Booking.seatNos1);
				tv_Boarding_place.setText("Boarding Point : "+SeatSelection.StrBoardingPoint);
				tv_Boarding_date.setText("Boarding Date and Time :"+Package_Booking.str_date);
				grandTotal_Result.setText(Package_Booking.str_total_price);
				tv_value_adults.setText("ADULTS(12+YRS) " +"\n"+PackageListing.adultVal);
				tv_value_children.setText("CHILDREN(0-12)"+"\n"+PackageListing.childVal);
			}
			if(strResponsecode.equalsIgnoreCase("0")){
				
				if (Utility.checkInternetConnection(PaymentSuccessActivity.this)) {
					new PostReservationData().execute();
				} else {
					Utility.showAlertNoInternetService(PaymentSuccessActivity.this);
				}
				
				}
		}else
		{
			rr_failed_layout.setVisibility(View.VISIBLE);
			rr_hotels_layout.setVisibility(View.GONE);
			rr_package_layout.setVisibility(View.GONE);
		}
		btn_payment_success = (RelativeLayout) findViewById(R.id.btn_payment_success);
		btn_payment_success.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PaymentSuccessActivity.this.finish();
				 startActivity(new Intent(
				 PaymentSuccessActivity.this,
				 MainActivity.class));
			}
		});

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
	}
	

	private void getJsonReport() {
//		String response = strResponse;
		
		
		

		JSONObject jObject;
		try {
			jObject = new JSONObject(strResponse.toString());
			PaymentId = jObject.getString("PaymentId");
			AccountId = jObject.getString("AccountId");
			MerchantRefNo = jObject.getString("MerchantRefNo");
			Amount = jObject.getString("Amount");
			DateCreated = jObject.getString("DateCreated");
			Description = jObject.getString("Description");
			Mode = jObject.getString("Mode");
			IsFlagged = jObject.getString("IsFlagged");
			BillingName = jObject.getString("BillingName");
			BillingAddress = jObject
					.getString("BillingAddress");
			BillingCity = jObject.getString("BillingCity");
			BillingState = jObject.getString("BillingState");
			BillingPostalCode = jObject
					.getString("BillingPostalCode");
			BillingCountry = jObject
					.getString("BillingCountry");
			BillingPhone = jObject.getString("BillingPhone");
			BillingEmail = jObject.getString("BillingEmail");
			DeliveryName = jObject.getString("DeliveryName");
			if(Constants.ebs_flg==0){
				DeliveryAddress = HotelBookingConfirm.StrID;
			}else{
				DeliveryAddress = PackageTouristDetails.StrPackID;
			}

			
			
			DeliveryCity = jObject.getString("DeliveryCity");
			DeliveryState = jObject.getString("DeliveryState");
			DeliveryPostalCode = jObject
					.getString("DeliveryPostalCode");
			DeliveryCountry = jObject
					.getString("DeliveryCountry");
			DeliveryPhone = jObject.getString("DeliveryPhone");
			PaymentStatus = jObject.getString("PaymentStatus");
			PaymentMode = jObject.getString("PaymentMode");
			SecureHash = jObject.getString("SecureHash");
			strTransactionId =jObject.getString("TransactionId");
			strResponsecode=jObject.getString("ResponseCode");
			System.out.println("paymentid_rsp" + PaymentId+":"+PaymentStatus);
			System.out.println("AccountId" + AccountId+"MerchantRefNo"+MerchantRefNo);
			
			System.out.println("Amount" + Amount+"DateCreated"+DateCreated);
			System.out.println("Description" + Description+"Mode"+Mode);
			System.out.println("IsFlagged" + IsFlagged);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private class PostReservationData extends AsyncTask<String, String, String> {

		ProgressDialog myProgressDialog;
		String re = ""; 

		protected void onPreExecute() {
			super.onPreExecute();
			myProgressDialog = new ProgressDialog(PaymentSuccessActivity.this);
			myProgressDialog.setMessage("Loading...");
			myProgressDialog.setCancelable(false);
			myProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			try {

				HttpClient client = new DefaultHttpClient();
				String str="";
				if(Constants.ebs_flg==0){
					str=Constants.Base_url1
					+ "hotelpgresponsenew.jsp";
				}else{
					str=Constants.Base_url1
							+ "tourpgresponsenew.jsp";
				}
				
				HttpPost post= new HttpPost(str);
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();

				
				pairs.add(new BasicNameValuePair("ResponseCode",
						strResponsecode));
				pairs.add(new BasicNameValuePair("ResponseMessage",
						strResponsecode));
				
				if(DateCreated.length()<=10){
				pairs.add(new BasicNameValuePair("DateCreated", DateCreated+" 00:00:00"));
				}
				else
				{
					pairs.add(new BasicNameValuePair("DateCreated", DateCreated));	
				}
				pairs.add(new BasicNameValuePair("PaymentID",PaymentId));
				
				
				pairs.add(new BasicNameValuePair("MerchantRefNo",MerchantRefNo));
				pairs.add(new BasicNameValuePair("Amount",Amount));
				pairs.add(new BasicNameValuePair("Mode",Mode));

				pairs.add(new BasicNameValuePair("BillingName",BillingName ));
				pairs.add(new BasicNameValuePair("BillingAddress",BillingAddress ));
				pairs.add(new BasicNameValuePair("BillingCity",	BillingCity));
				
				pairs.add(new BasicNameValuePair("BillingState",	BillingState));
				pairs.add(new BasicNameValuePair("BillingPostalCode", BillingPostalCode)); 
				pairs.add(new BasicNameValuePair("BillingCountry", "IND"));
				
				pairs.add(new BasicNameValuePair("BillingPhone", BillingPhone));
				pairs.add(new BasicNameValuePair("BillingEmail", BillingEmail));
				pairs.add(new BasicNameValuePair("DeliveryName",DeliveryName)); 

				pairs.add(new BasicNameValuePair("DeliveryAddress", DeliveryAddress));
				pairs.add(new BasicNameValuePair("DeliveryCity",DeliveryCity));
				pairs.add(new BasicNameValuePair("DeliveryState", DeliveryState));
				
				pairs.add(new BasicNameValuePair("DeliveryPostalCode", DeliveryPostalCode));
				pairs.add(new BasicNameValuePair("DeliveryCountry", DeliveryCountry));
				pairs.add(new BasicNameValuePair("DeliveryPhone",DeliveryPhone));
				
				pairs.add(new BasicNameValuePair("IsFlagged",IsFlagged));
				pairs.add(new BasicNameValuePair("TransactionID", strTransactionId));
				pairs.add(new BasicNameValuePair("SecureHash",SecureHash));
				pairs.add(new BasicNameValuePair("AccountID", AccountId ));
				
				System.out.println("" +
						"" + pairs);

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

			try {
				if (myProgressDialog.isShowing())
					myProgressDialog.dismiss();
				System.out.println("results" + result);
				
				/*try {
					JSONArray json = new JSONArray(result);

					if (result.contains("response")) {
						JSONObject e = json.getJSONObject(0);

						StrID = e.getString("response");
						System.out.println("StrID" + StrID);

						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								HotelBookingConfirm.this);
						altDialog
								.setMessage("Please preserve this tracking ID "
										+ StrID); // here
						altDialog.setNeutralButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										Constants.ebs_flg=0;
										callEbsKit();
									}
								});
						altDialog.show();
					} else {
						Toast.makeText(getApplicationContext(), "error",
								Toast.LENGTH_SHORT).show();
					}

				} catch (Exception e) {
					// TODO: handle exception
				}*/
			} catch (Exception e) {

			}
		}
	}

}