package com.telanganatourism.android.phase2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.ebs.android.sdk.Config.Encryption;
//import com.ebs.android.sdk.Config.Mode;
//import com.ebs.android.sdk.EBSPayment;
//import com.ebs.android.sdk.PaymentRequest;
import com.ebs.android.sdk.Config.Encryption;
import com.ebs.android.sdk.Config.Mode;
import com.ebs.android.sdk.EBSPayment;
import com.ebs.android.sdk.PaymentRequest;
import com.flurry.android.FlurryAgent;
import com.telanganatourism.android.phase2.util.Utility;

public class HotelBookingConfirm extends Activity {

	ArrayList<HashMap<String, String>> custom_post_parameters;
	
	Pattern pattern1 = Pattern
			.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

	TextView dateTxt, dateTxt1, monthTxt, monthTxt1, dayTxt, dayTxt1,
			adultValue, childValue, nightStayValTxt, noOfpersons,
			selectRoomType, grandTotal_Result, serviceTaxtxt, luxTaxTxt,
			textView8, textView16, titleTxt, addressTxt, textView6, textView90,
			textV, text_night, text_room, text_rType;

	EditText nameEdt, phoneEdt, emailEdt, lastnameEdt, citieEdt, countrieEdt,
			pinCodeEdt;

	LayoutInflater inflater;
	LinearLayout inflateLayout;
	View view;
	RadioGroup radioGroup_1, radioGroup_sex;
	int selectedId, selectedId1;

	RadioButton maleRdBtn, femaleRdBtn, mrRdBtn, mrsRdBtn;
	private RadioButton radioSexButton, radioSexButton1;

//	Utility utility;

	String Str_sex, Str_title, BlockedRoomNum, BlockedRoomTypeCode,
			BlockedRoomTypeName, BlackedRoomTariff;
	double total_tariff = 0.00, total_tariff_forday = 0.00;

	// String totalNoofRooms = "";
	int TotalAdults = 0, TotalChilds = 0;
	String StrTAdults, StrTChilds;

	public static String StrID, totalAmount;

	private static final int ACC_ID = 17582; // Provided by EBS
	private static final String SECRET_KEY = "f835a539cd5868fe9594e3c5315056f6"; // Provided
	private static final int GROUP_ID = 1; // Provided by EBS
	HttpResponse response;

	StringBuilder stringBuilder, stringBuilderCost, stringBuilderroomtypename,
			stringBuilderRoomnumbers, stringBuilderluxTax,
			stringBuilderRoomTypeAndNum, stringBuilderTotalTax,
			stringBuilderRoomType;

	public static String roomTypeNames = "",strTotalAdults="",
			strTotalChilds="";
	String	strAdults = "", strChilds = "";

	int serviceTax1;// = (int) (totalPrice * (60.0f / 100.0f));

	int servTax;// = (int) (serviceTax1 * (14.0f / 100.0f));

	int luxTax;

	String strFinal = "";
	String strFinal1 = "";

	// String StrTempTrackID;
	private static String HOST_NAME = "";
	
	int gradTotalAmount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.confirm_booking);

		HOST_NAME = getResources().getString(R.string.hostname);
//		utility = new Utility(HotelBookingConfirm.this);

		inflateLayout = (LinearLayout) findViewById(R.id.dynamicLayout);

		inflater = LayoutInflater.from(HotelBookingConfirm.this);

		titleTxt = (TextView) findViewById(R.id.event_title);
		addressTxt = (TextView) findViewById(R.id.accAddressTxt);
		textView8 = (TextView) findViewById(R.id.textView8);
		textView16 = (TextView) findViewById(R.id.textView16);
		textView6 = (TextView) findViewById(R.id.textView6);
		textView90 = (TextView) findViewById(R.id.textView90);
		textV = (TextView) findViewById(R.id.textV);
		titleTxt.setText(DetailScreen1.tit);
		addressTxt.setText(DetailScreen1.address);

		TextView chkinTxt = (TextView) findViewById(R.id.chkinTxt);
		TextView chkoutTxt = (TextView) findViewById(R.id.chkoutTxt);

		TextView chkkinTxt = (TextView) findViewById(R.id.checkinTxt);
		TextView chkkoutTxt = (TextView) findViewById(R.id.checkoutTxt);

		Button confirmBookingBtn = (Button) findViewById(R.id.button1);

		dateTxt = (TextView) findViewById(R.id.textView3);
		dateTxt1 = (TextView) findViewById(R.id.textVie);

		monthTxt = (TextView) findViewById(R.id.monthTxt);
		monthTxt1 = (TextView) findViewById(R.id.monthTxt1);

		dayTxt = (TextView) findViewById(R.id.dayTxt);
		dayTxt1 = (TextView) findViewById(R.id.day1Txt);

		adultValue = (TextView) findViewById(R.id.adult_val);
		childValue = (TextView) findViewById(R.id.child_val);

		nightStayValTxt = (TextView) findViewById(R.id.textView5);
		noOfpersons = (TextView) findViewById(R.id.roomsResult_val);

		selectRoomType = (TextView) findViewById(R.id.selectRoomTypeBtn);

		luxTaxTxt = (TextView) findViewById(R.id.luxTaxTxt);
		serviceTaxtxt = (TextView) findViewById(R.id.serviceTaxtxt);
		TextView textView2 = (TextView) findViewById(R.id.textView2);

		grandTotal_Result = (TextView) findViewById(R.id.grandTotal_Result);

		nameEdt = (EditText) findViewById(R.id.nameEdt);
		phoneEdt = (EditText) findViewById(R.id.phoneEdt);
		pinCodeEdt = (EditText) findViewById(R.id.pinCodeEdt);
		emailEdt = (EditText) findViewById(R.id.emailEdt);
		lastnameEdt = (EditText) findViewById(R.id.lastnameEdt);
		citieEdt = (EditText) findViewById(R.id.citieEdt);
		countrieEdt = (EditText) findViewById(R.id.countrieEdt);

		radioGroup_1 = (RadioGroup) findViewById(R.id.radioGroup_1);
		radioGroup_sex = (RadioGroup) findViewById(R.id.radioGroup_sex);

		maleRdBtn = (RadioButton) findViewById(R.id.maleRdBtn);
		femaleRdBtn = (RadioButton) findViewById(R.id.femaleRdBtn);
		mrRdBtn = (RadioButton) findViewById(R.id.mrRdBtn);
		mrsRdBtn = (RadioButton) findViewById(R.id.mrsRdBtn);

		text_night = (TextView) findViewById(R.id.text_night);
		text_room = (TextView) findViewById(R.id.text_room);
		text_rType = (TextView) findViewById(R.id.text_rType);

		maleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		femaleRdBtn.setTypeface(Constants.ProximaNova_Regular);
		mrRdBtn.setTypeface(Constants.ProximaNova_Regular);
		mrsRdBtn.setTypeface(Constants.ProximaNova_Regular);
		textView2.setTypeface(Constants.ProximaNova_Regular);

		titleTxt.setTypeface(Constants.ProximaNova_Regular);
		addressTxt.setTypeface(Constants.ProximaNova_Regular);
		chkinTxt.setTypeface(Constants.ProximaNova_Regular);
		chkoutTxt.setTypeface(Constants.ProximaNova_Regular);
		chkkinTxt.setTypeface(Constants.ProximaNova_Regular);
		chkkoutTxt.setTypeface(Constants.ProximaNova_Regular);
		dateTxt.setTypeface(Constants.ProximaNova_Regular);
		dateTxt1.setTypeface(Constants.ProximaNova_Regular);
		monthTxt.setTypeface(Constants.ProximaNova_Regular);
		childValue.setTypeface(Constants.ProximaNova_Regular);
		monthTxt1.setTypeface(Constants.ProximaNova_Regular);
		dayTxt.setTypeface(Constants.ProximaNova_Regular);
		dayTxt1.setTypeface(Constants.ProximaNova_Regular);
		noOfpersons.setTypeface(Constants.ProximaNova_Regular);
		adultValue.setTypeface(Constants.ProximaNova_Regular);
		nightStayValTxt.setTypeface(Constants.ProximaNova_Regular);
		luxTaxTxt.setTypeface(Constants.ProximaNova_Regular);
		serviceTaxtxt.setTypeface(Constants.ProximaNova_Regular);
		grandTotal_Result.setTypeface(Constants.ProximaNova_Regular);
		nightStayValTxt.setTypeface(Constants.ProximaNova_Regular);
		selectRoomType.setTypeface(Constants.ProximaNova_Regular);
		textView8.setTypeface(Constants.ProximaNova_Regular);
		textView16.setTypeface(Constants.ProximaNova_Regular);
		textView6.setTypeface(Constants.ProximaNova_Regular);
		textView90.setTypeface(Constants.ProximaNova_Regular);
		textV.setTypeface(Constants.ProximaNova_Regular);
		nameEdt.setTypeface(Constants.ProximaNova_Regular);
		phoneEdt.setTypeface(Constants.ProximaNova_Regular);
		pinCodeEdt.setTypeface(Constants.ProximaNova_Regular);
		emailEdt.setTypeface(Constants.ProximaNova_Regular);
		lastnameEdt.setTypeface(Constants.ProximaNova_Regular);
		citieEdt.setTypeface(Constants.ProximaNova_Regular);
		countrieEdt.setTypeface(Constants.ProximaNova_Regular);

		text_night.setTypeface(Constants.ProximaNova_Regular);
		text_room.setTypeface(Constants.ProximaNova_Regular);
		text_rType.setTypeface(Constants.ProximaNova_Regular);
		countrieEdt.setKeyListener(null);
		phoneEdt.setKeyListener(null);
		countrieEdt.setText("India");

		// StrTempTrackID= HotelBookingActivity.TrackId.get(0);

		// Adding First room id's to the arrays
		// HotelBookingActivity.typeArrayId.add(0,
		// HotelBookingActivity.room_id_str);
		HotelBookingActivity.noOfRoomsArray.add(0,
				HotelBookingActivity.no_of_rooms_str);
		// HotelBookingActivity.dummyNoofRoomsArray.add(0,
		// HotelBookingActivity.dummy_no_of_rooms_str);
		// HotelBookingActivity.typecostArray.add(0,HotelBookingActivity.room_type_cost);

		// List<Value> values = new ArrayList<Value>(map.values());
		// For a list of keys:
		// List<Key> keys = new ArrayList<Key>(map.keySet());

		stringBuilderroomtypename = new StringBuilder();
		stringBuilderroomtypename.setLength(0);

		for (Map.Entry<Integer, String> entry : HotelBookingActivity.newRoomTypeHash
				.entrySet()) {
			String value = entry.getValue();
			stringBuilderroomtypename.append("," + value);
		}

		BlockedRoomTypeName = stringBuilderroomtypename.toString()
				.replaceFirst(",", "").toString();

		// room details formating
		// 09 - > Room type Code
		// 1 -> Number of Rooms
		// 3402.00 -> Total Amount
		// 2 -> Number of Adults
		// 0-> Number of Childs
		// A/C DOUBLE ROOM -> Room type name
		// 3000 -> Room Tariff
		// 150 -> Luxury tax

		System.out.println("Room type codes "
				+ HotelBookingActivity.StrIndRtypecode);

		System.out.println(" room and type "
				+ HotelBookingActivity.ReservedRoomAndTypeHash.toString());
		System.out.println("blocked_roomstypeName" + BlockedRoomTypeName);
		System.out.println(" adults "
				+ HotelBookingActivity.adultArray.toString());
		System.out.println("childs "
				+ HotelBookingActivity.childArray.toString());
//		System.out.println("StrIndRTariff "
//				+ HotelBookingActivity.StrIndRTariff.toString());

		System.out.println(" total " + "amount");
		System.out.println("StrIndRTariff "
				+ HotelBookingActivity.StrIndRTariff.toString());

		// string formation

		String[] selectedRoomCode = HotelBookingActivity.selectedRoomCodesStr
				.replaceFirst(",", "").split(",");
		List<String> values = Arrays.asList(selectedRoomCode);
		// vijay

		// String strfinalstr, - '09~1~3402.00~2~0~A/C DOUBLE
		// ROOM~3000~150$14~1~2268.00~2~0~NON A/C 3 BEDDED ROOM~2000~100'
		//
		// 09 - > Room type Code
		// 1 -> Number of Rooms
		// 3402.00 -> Total Amount
		// 2 -> Number of Adults
		// 0-> Number of Childs
		// A/C DOUBLE ROOM -> Room type name
		// 3000 -> Room Tariff
		// 150 -> Luxury tax

		// VIJAY CODE START HERE
		HashMap<Integer, String> room_id = new HashMap<Integer, String>();
		HashMap<Integer, Integer> room_quantity = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> room_total = new HashMap<Integer, Integer>();
		HashMap<Integer, String> room_adults = new HashMap<Integer, String>();// Room
		HashMap<Integer, String> room_childs = new HashMap<Integer, String>();// Room
		HashMap<Integer, String> room_type = new HashMap<Integer, String>();// Room
		HashMap<Integer, Integer> room_tariff = new HashMap<Integer, Integer>();// Room
		HashMap<Integer, Integer> room_tax = new HashMap<Integer, Integer>();// Room
		// VIJAY CODE END HERE

		System.out.println("values are" + values);
		ArrayList<String> roomCostArray = new ArrayList<String>(
				HotelBookingActivity.newRoomCostHash_new.values());

		for (int j = 0; j < HotelBookingActivity.selectedRoomsArray.size(); j++) {

			ArrayList<Integer> roomsArray = new ArrayList<Integer>(
					HotelBookingActivity.selectedRoomsArray.keySet());

			int index = roomsArray.get(j);

			if (HotelBookingActivity.adultArray.get(
					new ArrayList<Integer>(
							HotelBookingActivity.selectedRoomsArray.keySet())
							.get(j)).equalsIgnoreCase("0")) {

			} else {

				Double TotalAmount = Double.parseDouble(roomCostArray
						.get(index)) * (index + 1);

				serviceTax1 = (int) (TotalAmount * (60.0f / 100.0f));
				servTax = (int) (serviceTax1 * (14.0f / 100.0f));
				luxTax = (int) (TotalAmount * (5.0f / 100.0f));
				double includingServiceTaxTotal = 0.00;

				if (TotalAmount < 599) {
					includingServiceTaxTotal = (double) (TotalAmount + servTax);
				} else if (TotalAmount < 999) {
					includingServiceTaxTotal = (double) (TotalAmount + luxTax);
				} else {
					includingServiceTaxTotal = (double) (TotalAmount + servTax + luxTax);
				}

				int totamt = (int) includingServiceTaxTotal;
				
				gradTotalAmount = totamt;
				
				System.out.println("room num "
						+ HotelBookingActivity.roomIdArray.get(index));
				String adults = "0", childs = "0";

				if (HotelBookingActivity.adultArray.size() > 0) {
					adults = HotelBookingActivity.adultArray.get(index);
				}
				try {
					if (HotelBookingActivity.childArray.size() > 0) {
						childs = HotelBookingActivity.childArray.get(index);
					}
				} catch (Exception e) {

				}

				Integer value2 = Integer.parseInt(HotelBookingActivity.roomIdArray.get(index));

				// VIJAY CODE START HERE
				if (room_id.containsKey(value2)) {
					int quantity = room_quantity.get(value2);
					// int total =room_total.get(value2);
					int adults1 = Integer.parseInt(room_adults.get(value2));
					int childs1 = Integer.parseInt(room_childs.get(value2));
					// int tax1=room_tax.get(value2);

					int serviceTax1_1 = (int) ((int) Double
							.parseDouble(roomCostArray.get(index)) * (60.0f / 100.0f));

					int servTax_1 = (int) (serviceTax1_1 * (14.0f / 100.0f));

					int luxTax_1 = (int) ((int) Double
							.parseDouble(roomCostArray.get(index)) * (5.0f / 100.0f));

					double includingServiceTaxTotal1 = 0.00;

					if ((int) Double
							.parseDouble(HotelBookingActivity.newRoomCostHash_new
									.get(index)) < 599) {

						includingServiceTaxTotal1 = (double) ((int) Double
								.parseDouble(roomCostArray.get(index)) + servTax_1);
					} else if ((int) Double.parseDouble(roomCostArray
							.get(index)) < 999) {
						includingServiceTaxTotal1 = (double) ((int) Double
								.parseDouble(roomCostArray.get(index)) + luxTax_1);
					} else {
						includingServiceTaxTotal1 = (double) ((int) Double
								.parseDouble(roomCostArray.get(index))
								+ servTax_1 + luxTax_1);
					}

					int totamt1 = (int) includingServiceTaxTotal1;

					room_quantity.put(value2, (quantity + 1));
					// room_total.put(value2, (total+totamt));
					room_total.put(value2, (totamt1 + totamt1));

					room_adults.put(value2, ""
							+ (Integer.parseInt(adults) + adults1));
					room_childs.put(value2, ""
							+ (Integer.parseInt(childs) + childs1));
					// room_tax.put(value2, (tax1+luxTax));
					room_tax.put(value2, (luxTax_1 + luxTax_1));
				} else {
					int serviceTax1_1 = (int) ((int) Double
							.parseDouble(roomCostArray.get(index)) * (60.0f / 100.0f));

					int servTax_1 = (int) (serviceTax1_1 * (14.0f / 100.0f));

					int luxTax_1 = (int) ((int) Double
							.parseDouble(roomCostArray.get(index)) * (5.0f / 100.0f));

					double includingServiceTaxTotal1 = 0.00;

					if ((int) Double.parseDouble(roomCostArray.get(index)) < 599) {

						includingServiceTaxTotal1 = (double) ((int) Double
								.parseDouble(roomCostArray.get(index)) + servTax_1);
					} else if ((int) Double.parseDouble(roomCostArray
							.get(index)) < 999) {
						includingServiceTaxTotal1 = (double) ((int) Double
								.parseDouble(roomCostArray.get(index)) + luxTax_1);
					} else {
						includingServiceTaxTotal1 = (double) ((int) Double
								.parseDouble(roomCostArray.get(index))
								+ servTax_1 + luxTax_1);
					}
					int totamt1 = (int) includingServiceTaxTotal1;

					room_id.put(value2, "" + HotelBookingActivity.roomIdArray.get(index));
					room_quantity.put(value2, Integer.parseInt(HotelBookingActivity.selectedRoomsArray.get(index)));
					room_total.put(value2, totamt1);
					room_adults.put(value2, adults);
					room_childs.put(value2, childs);
					room_type.put(value2, "" + HotelBookingActivity.roomTypeArray.get(index));
//					room_type.put(value2, new ArrayList<String>(
//							HotelBookingActivity.newRoomTypeHash.values())
//							.get(j));
					room_tariff.put(value2,
							(int) Double.parseDouble(roomCostArray.get(index)));
					room_tax.put(value2, luxTax_1);

				}

				// VIJAY CODE END HERE
				// }

				List<Integer> selectedRoomsKeys = new ArrayList<Integer>(
						HotelBookingActivity.selectedRoomsArray.keySet());

				System.out.println("selected rooms "
						+ HotelBookingActivity.selectedRoomsArray.keySet());

				int key = selectedRoomsKeys.indexOf(index);

				roomTypeNames = roomTypeNames
						+ ","
						+ new ArrayList<String>(
								HotelBookingActivity.newRoomTypeHash.values())
								.get(j);

				strFinal = strFinal
						
						+ (index != 0 ? "$" : "")
						+ HotelBookingActivity.roomIdArray.get(index)
						+ "~"
						+ HotelBookingActivity.selectedRoomsArray.get(key)
						+ "~"
						+ totamt
						+ "~"
						+ adults
						+ "~"
						+ childs
						+ "~"
						+ new ArrayList<String>(
								HotelBookingActivity.newRoomTypeHash.values())
								.get(j)
						+ "~"
						+ (int) Double
								.parseDouble(HotelBookingActivity.roomCostArray
										.get(j)) + "~" + luxTax;

				// strFinal = strFinal.toString().substring(1);

				System.out.println(" final string " + strFinal);
			}
		}

		// VIJAY CODE START HERE
		Iterator<Integer> keySetIterator = room_id.keySet().iterator();
		int j1 = 0;
		// String strFinal1="";
		while (keySetIterator.hasNext()) {
			Integer key = keySetIterator.next();
//			strFinal1 = strFinal1 + (j1 != 0 ? "$" : "") + room_id.get(key)
//					+ "~" + room_quantity.get(key) + "~" + room_total.get(key)
//					+ "~" + room_adults.get(key) + "~" + room_childs.get(key)
//					+ "~" + room_type.get(key) + "~" + room_tariff.get(key)
//					+ "~" + room_tax.get(key);
			
			//Edited by Goutham
//			strFinal1 = strFinal1 + (j1 != 0 ? "$" : "") + room_id.get(key)
//					+ "~" + room_quantity.get(key) + "~" + room_total.get(key)
//					+ "~" + room_adults.get(key) + "~" + room_childs.get(key)
//					+ "~" + room_type.get(key) + "~" + room_tariff.get(key)
//					+ "~" + room_tax.get(key);
			
			strFinal1 = strFinal1 + (j1 != 0 ? "$" : "") + room_id.get(key)
					+ "~" + room_quantity.get(key) + "~" + gradTotalAmount
					+ "~" + room_adults.get(key) + "~" + room_childs.get(key)
					+ "~" + room_type.get(key) + "~" + room_tariff.get(key)
					+ "~" + room_tax.get(key);
			j1++;
		}
		System.out.println("strfinal11111:" + strFinal1);
		// VIJAY CODE END HERE

		// luxTax = (int) (totalPrice * (5.0f / 100.0f));
		// HotelBookingActivity.adultArray.add(HotelBookingActivity.no_of_adults);
		for (int i = 0; i < HotelBookingActivity.adultArray.size(); i++) {
			TotalAdults = TotalAdults
					+ Integer.parseInt(HotelBookingActivity.adultArray.get(i));
		}
		strTotalAdults=String.valueOf(TotalAdults);

		// HotelBookingActivity.childArray.add(HotelBookingActivity.no_of_child);
		for (int i = 0; i < HotelBookingActivity.childArray.size(); i++) {
			TotalChilds = TotalChilds
					+ Integer.parseInt(HotelBookingActivity.childArray.get(i));
		}
		strTotalChilds=String.valueOf(TotalChilds);
		stringBuilder = new StringBuilder();
		stringBuilder.setLength(0);

		for (Map.Entry<Integer, String> entry : HotelBookingActivity.newRoomIdsArray
				.entrySet()) {
			String value = entry.getValue();
			stringBuilder.append("~" + value);
		}

		stringBuilderRoomnumbers = new StringBuilder();
		stringBuilderRoomnumbers.setLength(0);
		for (int i = 0; i < HotelBookingActivity.ReservedRoom.size(); i++) {
			stringBuilderRoomnumbers.append("~"
					+ HotelBookingActivity.ReservedRoom.get(i));
		}

		BlockedRoomNum = stringBuilderRoomnumbers.toString()
				.replaceFirst("~", "").toString();

		System.out.println("blocked_rooms" + BlockedRoomNum);

		System.out.println(" blockedrmswithtype "
				+ HotelBookingActivity.StrIndRNum + "and  "
				+ HotelBookingActivity.StrIndRtypecode);

		// ReservedRoomType
		stringBuilderRoomType = new StringBuilder();
		stringBuilderRoomType.setLength(0);
		for (int i = 0; i < HotelBookingActivity.ReservedRoomType.size(); i++) {
			stringBuilderRoomType.append("~"
					+ HotelBookingActivity.ReservedRoomType.get(i));
		}

		BlockedRoomTypeCode = stringBuilderRoomType.toString()
				.replaceFirst("~", "").toString();
		System.out.println("blocked_roomtypecode" + BlockedRoomTypeCode);

		stringBuilderluxTax = new StringBuilder();
		stringBuilderluxTax.setLength(0);

		for (Map.Entry<Integer, String> entry : HotelBookingActivity.newRoomluxuryTaxHash
				.entrySet()) {
			String value = entry.getValue();
			stringBuilderluxTax.append("~" + value);
			// do stuff
		}

		stringBuilderTotalTax = new StringBuilder();
		stringBuilderTotalTax.setLength(0);

		for (Map.Entry<Integer, String> entry : HotelBookingActivity.newRoomTotalAmntHash
				.entrySet()) {
			String value = entry.getValue();
			stringBuilderTotalTax.append("~" + value);

			// do stuff
		}

		System.out.println("value_roomLuxtax"
				+ stringBuilderluxTax.toString().replaceFirst("~", "")
						.toString());
		System.out.println("value_roomtotalAmount"
				+ stringBuilderTotalTax.toString().replaceFirst("~", "")
						.toString());

		System.out.println("value_roomid"
				+ stringBuilder.toString().replaceFirst("~", "").toString());

		System.out.println("value_roomnumber"
				+ stringBuilderRoomnumbers.toString().replaceFirst("~", "")
						.toString());

		System.out.println("value_adilts"
				+ HotelBookingActivity.adultArray.toString() + "total"
				+ TotalAdults);
		System.out.println("value_child"
				+ HotelBookingActivity.childArray.toString() + "total"
				+ TotalChilds);
		System.out.println("value_totalrooms"
				+ HotelBookingActivity.noOfRoomsArray.size());

		for (int i = 0; i < HotelBookingActivity.selectedRoomsArray.size(); i++) {

			ArrayList<Integer> roomsArray = new ArrayList<Integer>(
					HotelBookingActivity.selectedRoomsArray.keySet());

			int index = roomsArray.get(i);

			if (HotelBookingActivity.adultArray.get((new ArrayList<Integer>(
					HotelBookingActivity.selectedRoomsArray.keySet()).get(i)))
					.equalsIgnoreCase("0")) {

			} else {
				view = inflater.inflate(R.layout.rooms_infate_layout,
						inflateLayout, false);

				TextView adultValueTxt = (TextView) view
						.findViewById(R.id.adult_val1);

				TextView childValueTxt = (TextView) view
						.findViewById(R.id.child_val1);

				TextView selectRoomTypeTxt = (TextView) view
						.findViewById(R.id.selectRoomTypeBtn1);
				
				TextView tv_roomnumberValue = (TextView) view
						.findViewById(R.id.tv_roomnumberValue);

				TextView totalResult = (TextView) view
						.findViewById(R.id.roomsResult_val1);

				adultValueTxt.setTypeface(Constants.ProximaNova_Regular);
				childValueTxt.setTypeface(Constants.ProximaNova_Regular);
				selectRoomTypeTxt.setTypeface(Constants.ProximaNova_Regular);
				totalResult.setTypeface(Constants.ProximaNova_Regular);
				tv_roomnumberValue.setTypeface(Constants.ProximaNova_Regular);
				adultValueTxt.setText("ADULTS : "
						+ HotelBookingActivity.adultArray.get(index));
				strAdults = strAdults + ","
						+ HotelBookingActivity.adultArray.get(index);
				childValueTxt.setText("CHILDREN : "
						+ HotelBookingActivity.childArray.get(index));
				strChilds = strChilds + ","
						+ HotelBookingActivity.childArray.get(index);

				System.out.println("adult_values " + strAdults);
				System.out.println("child_values " + strChilds);

				System.out.println("adult_value "
						+ HotelBookingActivity.adultArray.toString());
				System.out.println("child_value "
						+ HotelBookingActivity.childArray.toString());

				int totalVal = Integer.parseInt(HotelBookingActivity.adultArray
						.get(index))
						+ Integer.parseInt(HotelBookingActivity.childArray
								.get(index));

				// List<String> selectedRoomType = new ArrayList<String>(
				// HotelBookingActivity.typeArray.values());

//				selectRoomTypeTxt
//						.setText(new ArrayList<String>(
//								HotelBookingActivity.newRoomTypeHash.values()).get(i));
				selectRoomTypeTxt.setText(HotelBookingActivity.roomTypeArray.get(index));

				
//				tv_roomnumberValue.setText(Integer.parseInt(HotelBookingActivity.selectedRoomsArray.get(index)));
				tv_roomnumberValue.setText(new ArrayList<String>(
						HotelBookingActivity.selectedRoomsArray.values()).get(i));
				
//				Integer.parseInt(HotelBookingActivity.selectedRoomsArray.get(index))
				totalResult.setText("" + totalVal);

				inflateLayout.addView(view);
			}
		}

		// Setting the Values
		chkkinTxt.setText(HotelBookingActivity.strCheckinTime);
		chkkoutTxt.setText(HotelBookingActivity.strCheckoutTime);
		dateTxt.setText(HotelBookingActivity.checkin_date_str);
		monthTxt.setText(HotelBookingActivity.checkin_month_str);
		dayTxt.setText(HotelBookingActivity.checkin_day_str);

		dateTxt1.setText(HotelBookingActivity.checkout_date_str);
		monthTxt1.setText(HotelBookingActivity.checkout_month_str);
		dayTxt1.setText(HotelBookingActivity.checkout_day_str);

		nightStayValTxt.setText(HotelBookingActivity.night_stay_str);

		adultValue.setText(HotelBookingActivity.no_of_adults);
		childValue.setText(HotelBookingActivity.no_of_child);
		noOfpersons.setText(HotelBookingActivity.total_no_of_persons);

		selectRoomType.setText(HotelBookingActivity.room_type_str);

		luxTaxTxt.setText("Luxury Tax : " + String.valueOf(HotelBookingActivity.totluxtax1));// //

		serviceTaxtxt.setText("Service Tax : "
				+ String.valueOf(HotelBookingActivity.Totservtax1));// //

		grandTotal_Result.setText(String.valueOf(HotelBookingActivity.Grandtotal1));// /

		total_tariff = HotelBookingActivity.Grandtotal1
				- (HotelBookingActivity.Totservtax1 + HotelBookingActivity.totluxtax1);

		System.out.println("total tariff" + total_tariff);
		total_tariff_forday = (total_tariff / Integer
				.parseInt(HotelBookingActivity.night_stay_str));
		System.out.println("total tariff_forday" + total_tariff_forday);
		// String[] amountStr = HotelBookingActivity.total_Price_str.split(" ");
		totalAmount = HotelBookingActivity.total_Price_str;

		nameEdt.setText(SplashScreen.pref.getString(
				SplashScreen.Key_GET_USER_NAME, ""));
		phoneEdt.setText(SplashScreen.pref.getString(
				SplashScreen.Key_GET_USER_PHONE_NO, ""));

		confirmBookingBtn.setTypeface(Constants.ProximaNova_Regular);
		confirmBookingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Utility.checkInternetConnection(HotelBookingConfirm.this)) {
					if (nameEdt.getText().toString().trim().length() == 0) {
						Toast.makeText(HotelBookingConfirm.this,
								"Please enter name", Toast.LENGTH_LONG).show();
					} else {
						if (lastnameEdt.getText().toString().trim().length() == 0) {
							Toast.makeText(HotelBookingConfirm.this,
									"Please enter last name", Toast.LENGTH_LONG)
									.show();
						} else {
							if (citieEdt.getText().toString().trim().length() == 0) {
								Toast.makeText(HotelBookingConfirm.this,
										"Please enter city name",
										Toast.LENGTH_LONG).show();
							} else {
								if (phoneEdt.getText().toString().trim()
										.length() == 0) {
									Toast.makeText(
											HotelBookingConfirm.this,
											"Please enter 10 digit valid phone number",
											Toast.LENGTH_LONG).show();
								} else {
									if (pinCodeEdt.getText().toString().trim()
											.length() == 0) {
										Toast.makeText(
												HotelBookingConfirm.this,
												"Please enter 6 digit valid pin code number",
												Toast.LENGTH_LONG).show();
									} else {
										if (pinCodeEdt.getText().length() < 6) {
											Toast.makeText(
													HotelBookingConfirm.this,
													"Please enter 6 digit valid pin code number",
													Toast.LENGTH_LONG).show();
										} else {
											if (emailEdt.getText().toString()
													.trim().length() == 0) {
												Toast.makeText(
														HotelBookingConfirm.this,
														"Please enter email id",
														Toast.LENGTH_LONG)
														.show();
											} else {
												if (phoneEdt.getText().length() < 10) {
													Toast.makeText(
															HotelBookingConfirm.this,
															"Please enter 10 digit valid phone number",
															Toast.LENGTH_LONG)
															.show();
												} else {
													Matcher matcher1 = pattern1
															.matcher(emailEdt
																	.getText()
																	.toString()
																	.trim());

													if (matcher1.matches()) {

														selectedId = radioGroup_1
																.getCheckedRadioButtonId();
														selectedId1 = radioGroup_sex
																.getCheckedRadioButtonId();

														radioSexButton = (RadioButton) findViewById(selectedId);
														radioSexButton1 = (RadioButton) findViewById(selectedId1);

														if (radioSexButton1
																.getText()
																.toString()
																.equals("Male")) {
															Str_sex = "M";
														} else {
															Str_sex = "F";
														}

														if (radioSexButton
																.getText()
																.toString()
																.equals("Mr.")) {
															Str_title = "Mr.";
														} else {
															Str_title = "Mrs.";
														}
														if (Utility
																.checkInternetConnection(HotelBookingConfirm.this)) {
															new Addhoteldata()
																	.execute();
														} else {
															Utility.showAlertNoInternetService(HotelBookingConfirm.this);
														}

													} else {
														Toast.makeText(
																HotelBookingConfirm.this,
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

				} else {
					Utility.showAlertNoInternetService(HotelBookingConfirm.this);
				}

			}
		});

	}
	
	private void callEbsKit() {
		/**
		 * Set Parameters Before Initializing the EBS Gateway, All mandatory
		 * values must be provided
		 */

		/** Payment Amount Details */
		// Total Amount

		PaymentRequest.getInstance().setTransactionAmount(
				String.format("%.2f", Double.valueOf(totalAmount)));

		/** Mandatory */

		PaymentRequest.getInstance().setAccountId(ACC_ID);
		PaymentRequest.getInstance().setSecureKey(SECRET_KEY);

		// Reference No
		PaymentRequest.getInstance().setReferenceNo(Constants.new_id);
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
		PaymentRequest.getInstance().setBillingAddress("Hyderabad");
		/** Optional */
		PaymentRequest.getInstance().setBillingCity(citieEdt.getText().toString());
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
		PaymentRequest.getInstance().setShippingName("Test_Name");
		/** Optional */
		PaymentRequest.getInstance().setShippingAddress(StrID);
		/** Optional */
		PaymentRequest.getInstance().setShippingCity("Hyderabad");
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

		EBSPayment.getInstance().init(HotelBookingConfirm.this, ACC_ID, SECRET_KEY,
				Mode.ENV_TEST, Encryption.ALGORITHM_MD5, HOST_NAME);   
		
		
		
		
//		EBSPayment.getInstance().init(context, accId, secretkey, environment, algorithm, host_name);
	}

//	private void callEbsKit() {
//
//		/**
//		 * Set Parameters Before Initializing the EBS Gateway, All mandatory
//		 * values must be provided
//		 */
//		/** Payment Amount Details */
//		PaymentRequest.getInstance().setTransactionAmount(
//				String.format("%.2f", Double.valueOf(totalAmount)));
//		
////		PaymentRequest.getInstance().setTransactionAmount("%.2f", 
////				Double.valueOf(totalAmount));
//		/** Mandatory */
//		PaymentRequest.getInstance().setReferenceNo(Constants.new_id);
//		/** Mandatory */
//		// Your Reference No or Order Id for this transaction
//		PaymentRequest.getInstance().setTransactionDescription("Test Transaction");
//		/** Mandatory */
//		// PaymentRequest.getInstance().setReturnUrl(
//		// "http://202.65.147.152:8080/tgtdc/etg/pgresponse.jsp");
//		PaymentRequest.getInstance().setReturnUrl(
//		Constants.Base_url1+"hotelpgresponse.jsp");
//		/** Mandatory */
//		PaymentRequest.getInstance().setGroupId(GROUP_ID);
//		/** Mandatory */
//		/** Billing Details */
//		PaymentRequest.getInstance().setBillingName(nameEdt.getText().toString());
//		/** Mandatory */
//		PaymentRequest.getInstance().setBillingAddress("");
//		/** Mandatory */
//		PaymentRequest.getInstance().setBillingCity(citieEdt.getText().toString());
//		/** Mandatory */
//		PaymentRequest.getInstance().setBillingPostalCode(pinCodeEdt.getText().toString());
//		/** Mandatory */
//		PaymentRequest.getInstance().setBillingState("Telangana");
//		/** Optional */
//		PaymentRequest.getInstance().setBillingCountry("IND");
//		/** Mandatory */
//		PaymentRequest.getInstance().setBillingEmail(emailEdt.getText().toString());
//		/** Mandatory */
//		PaymentRequest.getInstance().setBillingPhone(phoneEdt.getText().toString());
//		PaymentRequest.getInstance().setShippingAddress(StrID);
//		/** Optional */
//		/** Shipping Details */
//		// PaymentRequest.getInstance().setShippingName("Ganesh");
//		// /** Optional */
//		// PaymentRequest.getInstance().setShippingAddress("North Mada Street");
//		// /** Optional */
//		// PaymentRequest.getInstance().setShippingCity("Chennai");
//		// /** Optional */
//		// PaymentRequest.getInstance().setShippingPostalCode("600019");
//		// /** Optional */
//		// PaymentRequest.getInstance().setShippingState("Tamilnadu");
//		// /** Optional */
//		// PaymentRequest.getInstance().setShippingCountry("IND");
//		// /** Optional */
//		// PaymentRequest.getInstance().setShippingEmail("ganesh@test.com");
//		// /** Optional */
//		// PaymentRequest.getInstance().setShippingPhone("04424578545");
//		/** Optional */
//		// PaymentRequest.getInstance().setPaymentMode(1);/**Optional*/
//		/** Initializing the EBS Gateway */
//		EBSPayment.getInstance().init(HotelBookingConfirm.this, ACC_ID,
//				SECRET_KEY, Mode.ENV_TEST, Encryption.ALGORITHM_MD5, HOST_NAME);
//
//	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(HotelBookingConfirm.this,
				"6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(HotelBookingConfirm.this);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	private class Addhoteldata extends AsyncTask<String, String, String> {

		ProgressDialog myProgressDialog;
		String re = "";

		protected void onPreExecute() {
			super.onPreExecute();
			myProgressDialog = new ProgressDialog(HotelBookingConfirm.this);
			myProgressDialog.setMessage("Loading...");
			myProgressDialog.setCancelable(false);
			myProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// Creating service handler class instance
			
			try {

				if (HotelBookingActivity.noOfRoomsArray.size() > 1) {
					HotelBookingActivity.Str_isSingle = "1";
				} else {
					HotelBookingActivity.Str_isSingle = "0";
				}
				
				
				HttpClient client = new DefaultHttpClient();
				// HttpPost post = new HttpPost(Home.url);
				HttpPost post = new HttpPost(Constants.Base_url1
						+ "posthoteltempdata.jsp");
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();

				pairs.add(new BasicNameValuePair("flag",
						HotelBookingActivity.Str_isSingle));
//				pairs.add(new BasicNameValuePair("flag",
//						"0"));
				pairs.add(new BasicNameValuePair("finstr", strFinal1));
				
				
				pairs.add(new BasicNameValuePair("ckindt",
						Constants.CheckInDateStr));
				pairs.add(new BasicNameValuePair("ckintm",
						HotelBookingActivity.strCheckinTime));
				
				
				pairs.add(new BasicNameValuePair("ckoutdt",
						Constants.CheckOutDateStr));
				pairs.add(new BasicNameValuePair("ckouttm",
						HotelBookingActivity.strCheckoutTime));

				pairs.add(new BasicNameValuePair("uc", Constants.new_id));
				pairs.add(new BasicNameValuePair("hname", DetailScreen1.tit));
				pairs.add(new BasicNameValuePair("rmtypeid",
						BlockedRoomTypeCode));// RoomTypcodes
				// ~
				// seperated
//				pairs.add(new BasicNameValuePair("rtypename", roomTypeNames
//						.replaceFirst(",", "")));// room
				pairs.add(new BasicNameValuePair("rtypename", HotelBookingActivity.strRoomTypeName
						));// room
				
				
				
				// type
				// names
				// ,
				// seperated

				// StrIndRNum
				pairs.add(new BasicNameValuePair("blockdrooms", BlockedRoomNum)); // no
																					// rooms
				// numbers
				// ~
				// seperated
				pairs.add(new BasicNameValuePair("adult", String
						.valueOf(TotalAdults)));
				pairs.add(new BasicNameValuePair("child", String
						.valueOf(TotalChilds)));
				pairs.add(new BasicNameValuePair("totamt", totalAmount));

				pairs.add(new BasicNameValuePair("rmtarif",
						HotelBookingActivity.StrIndRTariff.replace(",", "~"))); // yes saparetor ~

				pairs.add(new BasicNameValuePair("totrmtarif", String.format(
						"%.2f", total_tariff)));
				pairs.add(new BasicNameValuePair("totstax",
						HotelBookingActivity.strServiceTaxValue));

				pairs.add(new BasicNameValuePair("totrmtarifperday", String
						.format("%.2f", total_tariff_forday)));

				pairs.add(new BasicNameValuePair("nodays", nightStayValTxt
						.getText().toString()));

//				pairs.add(new BasicNameValuePair("rqty",
//						HotelBookingActivity.StrIndRNum.replace(",", "-")));
				pairs.add(new BasicNameValuePair("rqty",
						HotelBookingActivity.selectedRoomsStr1.replace(",", "-")));
				
				pairs.add(new BasicNameValuePair("rmandtype",
						HotelBookingActivity.strRumNoRumCode.replaceFirst("~",
								"")));

				pairs.add(new BasicNameValuePair("blktempno",
						HotelBookingActivity.StrTempTrackID));

				pairs.add(new BasicNameValuePair("title", Str_title));
				pairs.add(new BasicNameValuePair("fname", nameEdt.getText()
						.toString()));
				pairs.add(new BasicNameValuePair("lname", lastnameEdt.getText()
						.toString()));

				pairs.add(new BasicNameValuePair("gender", Str_sex));
				pairs.add(new BasicNameValuePair("city", citieEdt.getText()
						.toString()));
				pairs.add(new BasicNameValuePair("country", countrieEdt
						.getText().toString()));
				pairs.add(new BasicNameValuePair("email", emailEdt.getText()
						.toString()));
				pairs.add(new BasicNameValuePair("mobileno", phoneEdt.getText()
						.toString()));

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

			try {
				if (myProgressDialog.isShowing())
					myProgressDialog.dismiss();
				System.out.println("results" + result);
				try {
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
										Constants.ebs_flg = 0;
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
				}
			} catch (Exception e) {

			}
		}
	}
}
