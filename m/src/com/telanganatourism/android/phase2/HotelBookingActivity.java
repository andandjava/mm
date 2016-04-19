package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.telanganatourism.android.phase2.date.DatePickerDialog;
import com.telanganatourism.android.phase2.date.DatePickerDialog.OnDateSetListener;
import com.telanganatourism.android.phase2.util.Utility;

public class HotelBookingActivity extends Activity implements
		NumberPicker.OnValueChangeListener {

	static final int DATE_PICKER_ID = 1111;
	static final int DATE_PICKER_ID1 = 11;

	static final int TIME_DIALOG_ID = 999;
	static final int TIME_DIALOG_ID1 = 9999;

	public static String Str_isSingle;

	// For comma separated values
	public static double totluxtax1 = 0.00, Totservtax1 = 0.00,
			Grandtotal1 = 0.00;
	private int hour;
	private int minute;
	public static String room_type_cost, BookedRoomNo;
	public static String checkin_date_str, checkin_month_str, checkin_day_str,
			checkout_date_str, checkout_month_str, checkout_day_str,
			night_stay_str, no_of_adults, no_of_child, total_no_of_persons,
			room_type_str, room_id_str, no_of_rooms_str, dummy_no_of_rooms_str,
			total_Price_str, no_of_adults_str, available_rooms_str = "",
			StrIndRtypecode, StrIndRtypecodeWTild, StrIndRNum, StrIndRTName,
			StrIndRTariff, strDivCode, strRoomTypeName;

	TextView dateTxt, dateTxt1, monthTxt, monthTxt1, dayTxt, dayTxt1,
			adultValue, childValue, nightStayValTxt, equalTo,
			selectRoomTypeBtn, aTxt;
	public static String StrTempTrackID = "", strRumNoRumCode = "";

	TextView adultValue1, childValue1, noOfpersons1, equalTo1, roomTxt,
			selectRoomTypeBtn1, grandTotal_Result, serviceTaxtxt, luxTaxTxt;

	Button adult_minus_btn, adult_plus_btn, child_minus_btn, child_plus_btn;
	Button adult_minus_btn1, adult_plus_btn1, child_minus_btn1,
			child_plus_btn1;
	Button bookBtn, addRoomBtn;

	RelativeLayout stayNight;

	AlertDialog.Builder alertDialog, alertDialog1;
	String names[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

	ListView dialogListview, dialogListview1;

	int adultVal = 0, childVal = 0, adultVal1 = 0, childVal1 = 0,
			personsVal = 0, personsVal1 = 0, editFieldPos;

	Date date, date1, currentDate;

	LayoutInflater inflater;
	LinearLayout addedRoomLayout;
	View view;
	StringBuilder stringBuilder1, stringBuilder2, stringBuilder3,
			stringBuilder4;
	static StringBuilder selectedRoomNames;

	int addedRoomVal = 0, childBtnClickFlag = 0;
	int AddRoomNo = 0;
	public static ArrayList<Integer> addedArray = new ArrayList<Integer>();
	public static ArrayList<Integer> addedRoomNoArray = new ArrayList<Integer>();
	TextView childPopupTextView, noOfRoomsTextView;
	View vieww;
	View view1;
	EditText noOfRoomsEdit;
	ScrollView parentScroll;

	static ArrayList<String> roomIdArray = new ArrayList<String>();
	static ArrayList<String> roomTypeArray = new ArrayList<String>();
	static ArrayList<String> roomCostArray = new ArrayList<String>();
	ArrayList<String> dummyArray = new ArrayList<String>();
	ArrayList<String> responsecodeArray = new ArrayList<String>();
	ArrayList<String> notavailableroomsArray = new ArrayList<String>();
	public static HashMap<Integer, String> selectedRoomsArray = new HashMap<Integer, String>();

	LinkedHashMap<Integer, String> roomnumbers = new LinkedHashMap<Integer, String>();

	public static HashMap<Integer, String> newRoomIdsArray = new HashMap<Integer, String>();
	public static HashMap<Integer, Float> newRoomCostHash = new HashMap<Integer, Float>();
	public static HashMap<Integer, Float> newRoomCostHash1 = new HashMap<Integer, Float>();
	HashMap<Integer, Integer> newRoomCountHash = new HashMap<Integer, Integer>();
	public static HashMap<Integer, String> newRoomTypeHash = new HashMap<Integer, String>();
	public static HashMap<Integer, String> newRoomCostHash_new = new HashMap<Integer, String>();// Room

	public static HashMap<Integer, Integer> newRoomNumberHash = new HashMap<Integer, Integer>();
	public static HashMap<Integer, String> newRoomluxuryTaxHash = new HashMap<Integer, String>();
	public static HashMap<Integer, String> newRoomluxurySerViceHash = new HashMap<Integer, String>();

	public static HashMap<Integer, String> newRoomTotalAmntHash = new HashMap<Integer, String>();
	public static HashMap<Integer, String> newRoomAdultsHash = new HashMap<Integer, String>();
	public static HashMap<Integer, String> newRoomChildHash = new HashMap<Integer, String>();
	int itemPosition;
	// for room typecode and no of rooms with out dup values
	Map<String, Integer> result_type = new HashMap<String, Integer>();
	Map<String, Integer> result_type1 = new HashMap<String, Integer>();
	ArrayList<String> IndRoomtypeCodes = new ArrayList<String>();
	ArrayList<String> IndRoomTariff = new ArrayList<String>();
	// ArrayList<Integer> IndRoomTotalNum = new ArrayList<Integer>();

	// for room tarriff with out dup values
	Map<String, Integer> result_room_tariff = new HashMap<String, Integer>();

	// for room type names with out dup values
	Map<String, Integer> result_type_name = new HashMap<String, Integer>();
	ArrayList<String> IndRoomtypeName = new ArrayList<String>();

	public static ArrayList<String> new_TrackId = new ArrayList<String>();
	// new service call;
	public static ArrayList<String> TrackId = new ArrayList<String>();
	public static ArrayList<String> ReservedRoom = new ArrayList<String>();
	public static HashMap<String, String> ReservedRoomAndTypeHash = new HashMap<String, String>();
	public static ArrayList<String> ReservedRoomType = new ArrayList<String>();

	public static Float newTotalPriceVal = 0.0f;

	String gettingTag, selectedRoomsStr;
	Boolean isError = false;

	public static ArrayList<String> adultArray = new ArrayList<String>();
	public static ArrayList<String> childArray = new ArrayList<String>();
	public static HashMap<Integer, String> typeArray = new HashMap<Integer, String>();
	public static ArrayList<String> noOfRoomsArray = new ArrayList<String>();

	public static String selectedRoomCodesStr = "", selectedRoomsStr1 = "";

	int dateError = 0;
	public static String strCheckinTime = "12:00", strCheckoutTime = "12.00",
			strLuxuryTaxValue, strServiceTax, strServiceTaxValue;
	int sCheckinTime, sCheckoutTime;
	CalendarView calendar;

	private final Calendar mCalendar = Calendar.getInstance();

	private int day = mCalendar.get(Calendar.DAY_OF_MONTH);

	private int month = mCalendar.get(Calendar.MONTH);

	private int year = mCalendar.get(Calendar.YEAR);

	Spinner checkinSpnr, checkoutSpnr;

	EditText noOfRoomSpnr;

	int roomAddedFlag = 0;

	double serviceTax1, servTax, luxTax;

	int noOfRooms_added, checkin_hours, current_date_checkin_hour;
	String pattern1 = "#,##,###.##";
	DecimalFormat decimalFormat;

	String checkin_date_check;
	Dialog dialog;
	String dialogmsg;
	
	// New Calender Variables

		final Context context = this;
		private static GridView gridview;
		String sel_date = "";
		public CalendarAdapter adapter;
		
		public GregorianCalendar gMonth1, itemmonth;// calendar instances.

		public Handler handler;// for grabbing some event values for showing the dot
		// marker.
		public ArrayList<String> items; // container to store calendar items which
		// needs showing the event marker
		// ArrayList<String> event;
		ArrayList<String> desc, cal_picker_date_arry;
		String selecteddate, gridvalueString;
//		Button buttonDialog, buttonDialog2;
		TextView title;

		int cin_day, cin_mon, cin_year;
		static int cout_day = 0, cout_mon = 0, cout_year = 0;
		static int inc_day = 0, inc_mon = 0, inc_year = 0;
		String selected_day, selected_mon, selected_year;
		boolean flag = false;
		SimpleDateFormat df;
		String today_date;
		static String title_year;
		
		String checkin_str, checkout_str;

//		TextView dateTxt_in, monthTxt_in, dayTxt_in, dateTxt_out, monthTxt_out,
//				dayTxt_out;
		LinearLayout checkoutLayout, checkinLayout;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd", Locale.US);
		String[] txt_month = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
				"AUG", "SEP", "OCT", "NOV", "DEC" };

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hotel_booking);
		newRoomTypeHash.clear();

		Date dtTime = new Date();

		checkin_hours = dtTime.getHours();
		int minutes = dtTime.getMinutes();
		int seconds = dtTime.getSeconds();

		Calendar calD = Calendar.getInstance();
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
		checkin_date_check = formatter1.format(calD.getTime());

		if (checkin_date_check.equalsIgnoreCase(Constants.currentDate)) {
			current_date_checkin_hour = checkin_hours + 1;
		}
		decimalFormat = new DecimalFormat(pattern1);
		decimalFormat.setGroupingSize(3);
		decimalFormat.setDecimalSeparatorAlwaysShown(true);
		decimalFormat.setMinimumFractionDigits(2);

		newRoomCostHash.clear();
		newRoomCostHash1.clear();
		newRoomCountHash.clear();
		result_type.clear();
		result_type1.clear();
		IndRoomtypeCodes.clear();
		selectedRoomsArray.clear();
		newRoomIdsArray.clear();

		// for roomtypename
		result_type_name.clear();
		IndRoomtypeName.clear();

		Constants.day_string = "";
		Constants.day_number = "";
		totluxtax1 = 0.00;
		Totservtax1 = 0.00;
		Grandtotal1 = 0.00;

		if (Utility.checkInternetConnection(HotelBookingActivity.this)) {
			new GetRoomTypes().execute();
		} else {
			Utility.showAlertNoInternetService(HotelBookingActivity.this);
		}
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");

		Constants.CheckInDateStr = formatter2.format(new Date().getTime());
		Constants.CheckOutDateStr = formatter2.format(new Date().getTime()
				+ (1000 * 60 * 60 * 24));

		RelativeLayout menu_layout = (RelativeLayout) findViewById(R.id.menuLayout);

		menu_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// startActivity(new Intent(HotelBookingActivity.this,
				// DetailScreen1.class));
				finish();
			}
		});

		parentScroll = (ScrollView) findViewById(R.id.scrollView1);

		TextView titleTxt = (TextView) findViewById(R.id.event_title);

		TextView addressTxt = (TextView) findViewById(R.id.accAddressTxt);

		selectRoomTypeBtn = (TextView) findViewById(R.id.selectRoomTypeBtn);
		selectRoomTypeBtn.setTypeface(Constants.ProximaNova_Regular);
		titleTxt.setText(DetailScreen1.tit);

		addressTxt.setText(DetailScreen1.address);

		checkinSpnr = (Spinner) findViewById(R.id.checkinSpnr);

		checkoutSpnr = (Spinner) findViewById(R.id.checkoutSpnr);

		List<String> list = new ArrayList<String>();
		list.add("00:00");
		list.add("01:00");
		list.add("02:00");
		list.add("03:00");
		list.add("04:00");
		list.add("05:00");
		list.add("06:00");
		list.add("07:00");
		list.add("08:00");
		list.add("09:00");
		list.add("10:00");
		list.add("11:00");
		list.add("12:00");
		list.add("13:00");
		list.add("14:00");
		list.add("15:00");
		list.add("16:00");
		list.add("17:00");
		list.add("18:00");
		list.add("19:00");
		list.add("20:00");
		list.add("21:00");
		list.add("22:00");
		list.add("23:00");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);

		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		dataAdapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		checkinSpnr.setAdapter(dataAdapter);

		checkoutSpnr.setAdapter(dataAdapter1);

		checkinSpnr.setSelection(current_date_checkin_hour);

		checkoutSpnr.setSelection(current_date_checkin_hour);

		checkinSpnr.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub

				strCheckinTime = parent.getItemAtPosition(pos).toString();
				String string = parent.getItemAtPosition(pos).toString();
				String[] parts = string.split(":");
				String part1 = parts[0];
				if (part1.equals("null") || part1.equals("")) {
					part1 = "0";
				}

				if (checkin_date_check
						.equalsIgnoreCase(Constants.CheckInDateStr)) {
					if (checkin_hours > Integer.parseInt(part1)) {

						dialogmsg = "Check-in should not be less than current time";
						showCustomDialog(dialogmsg);
						checkinSpnr.setSelection(checkin_hours);
					} else {
						sCheckinTime = Integer.parseInt(part1);
					}
				} else {
					sCheckinTime = Integer.parseInt(part1);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		checkoutSpnr.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				strCheckoutTime = parent.getItemAtPosition(pos).toString();

				String string = parent.getItemAtPosition(pos).toString();
				String[] parts = string.split(":");
				String part1 = parts[0];
				if (part1.equals("null") || part1.equals("")) {
					part1 = "0";
				}

				// if (part1.equals("null") || part1.equals("")) {
				// part1 = "0";
				// }
				sCheckoutTime = Integer.parseInt(part1);
				if (Integer.parseInt(part1) > sCheckinTime) {
					showCustomDialog("Check Out time should not be more than Check-in time");
					checkoutSpnr.setSelection(checkin_hours);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		luxTaxTxt = (TextView) findViewById(R.id.luxTaxTxt);
		serviceTaxtxt = (TextView) findViewById(R.id.serviceTaxtxt);
		grandTotal_Result = (TextView) findViewById(R.id.grandTotal_Result);

		inflater = LayoutInflater.from(HotelBookingActivity.this);

		addedRoomLayout = (LinearLayout) findViewById(R.id.added_room_layout);

		addRoomBtn = (Button) findViewById(R.id.addRoomsBtn);

		adultArray.clear();
		childArray.clear();
		typeArray.clear();
		noOfRoomsArray.clear();

		System.out.println("maxallocatedrooms"
				+ Constants.max_allowed_rooms_str);

		LinearLayout checkinLayout = (LinearLayout) findViewById(R.id.checkinLayout);
		LinearLayout checkoutLayout = (LinearLayout) findViewById(R.id.checkoutLayout);

		stayNight = (RelativeLayout) findViewById(R.id.stayNightsLayout);

		dateTxt = (TextView) findViewById(R.id.dateTxt);
		dateTxt1 = (TextView) findViewById(R.id.dateTxt1);

		monthTxt = (TextView) findViewById(R.id.monthTxt);
		monthTxt1 = (TextView) findViewById(R.id.month1Txt);

		dayTxt = (TextView) findViewById(R.id.dayTxt);
		dayTxt1 = (TextView) findViewById(R.id.day1Txt);

		adultValue = (TextView) findViewById(R.id.adult_val);
		childValue = (TextView) findViewById(R.id.child_val);

		nightStayValTxt = (TextView) findViewById(R.id.nightStayValTxt);

		equalTo = (TextView) findViewById(R.id.textView7);

		TextView chkinTxt = (TextView) findViewById(R.id.chkinTxt);
		TextView chkoutTxt = (TextView) findViewById(R.id.chkoutTxt);
		TextView chkintimetxt = (TextView) findViewById(R.id.chkintimetxt);
		TextView chkouttimeTxt = (TextView) findViewById(R.id.chkouttimeTxt);
		TextView nightTxt = (TextView) findViewById(R.id.nightTxt);
		TextView roomTypeTxtt = (TextView) findViewById(R.id.roomTypeTxtt);
		TextView roomTxtx = (TextView) findViewById(R.id.roomTxtx);
		TextView textView_ta = (TextView) findViewById(R.id.textView_ta);
		TextView textView_inc = (TextView) findViewById(R.id.textView_inc);
		aTxt = (TextView) findViewById(R.id.aTxt);
		TextView cTxt = (TextView) findViewById(R.id.cTxt);
		dateTxt.setTypeface(Constants.ProximaNova_Regular);
		dateTxt1.setTypeface(Constants.ProximaNova_Regular);
		monthTxt.setTypeface(Constants.ProximaNova_Regular);
		childValue.setTypeface(Constants.ProximaNova_Regular);
		monthTxt1.setTypeface(Constants.ProximaNova_Regular);
		dayTxt.setTypeface(Constants.ProximaNova_Regular);
		dayTxt1.setTypeface(Constants.ProximaNova_Regular);
		adultValue.setTypeface(Constants.ProximaNova_Regular);
		nightStayValTxt.setTypeface(Constants.ProximaNova_Regular);
		equalTo.setTypeface(Constants.ProximaNova_Regular);
		titleTxt.setTypeface(Constants.ProximaNova_Regular);
		addressTxt.setTypeface(Constants.ProximaNova_Regular);
		selectRoomTypeBtn.setTypeface(Constants.ProximaNova_Regular);

		luxTaxTxt.setTypeface(Constants.ProximaNova_Regular);
		serviceTaxtxt.setTypeface(Constants.ProximaNova_Regular);
		grandTotal_Result.setTypeface(Constants.ProximaNova_Regular);
		addRoomBtn.setTypeface(Constants.ProximaNova_Regular);

		chkinTxt.setTypeface(Constants.ProximaNova_Regular);
		chkoutTxt.setTypeface(Constants.ProximaNova_Regular);
		chkintimetxt.setTypeface(Constants.ProximaNova_Regular);
		chkouttimeTxt.setTypeface(Constants.ProximaNova_Regular);
		nightTxt.setTypeface(Constants.ProximaNova_Regular);
		roomTypeTxtt.setTypeface(Constants.ProximaNova_Regular);
		roomTxtx.setTypeface(Constants.ProximaNova_Regular);
		aTxt.setTypeface(Constants.ProximaNova_Regular);
		cTxt.setTypeface(Constants.ProximaNova_Regular);
		textView_ta.setTypeface(Constants.ProximaNova_Regular);
		textView_inc.setTypeface(Constants.ProximaNova_Regular);

		bookBtn = (Button) findViewById(R.id.bookingBtn);

		adult_minus_btn = (Button) findViewById(R.id.adult_minus_btn);
		adult_plus_btn = (Button) findViewById(R.id.adult_plus_btn);

		child_minus_btn = (Button) findViewById(R.id.child_minus_btn);
		child_plus_btn = (Button) findViewById(R.id.child_plus_btn);

		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		Date today = c.getTime();
		date = c.getTime();

		currentDate = c.getTime();

		c.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = c.getTime();
		date1 = c.getTime();

		int days = daysBetween(date, date1);

		nightStayValTxt.setText(String.valueOf(days));

		SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy");

		String[] checkin_dateString = formatter.format(today).split(",");

		String checkin_dayStr = checkin_dateString[0];

		String[] splitDate = checkin_dateString[1].split(" ");

		String checkin_month = splitDate[1];
		String checkin_datee = splitDate[2];
		String checkin_year = splitDate[3];

		dateTxt.setText(checkin_datee);
		monthTxt.setText(checkin_month + "'" + checkin_year.replace("20", ""));
		dayTxt.setText(checkin_dayStr);

		String[] checkout_dateStr = formatter.format(tomorrow).split(",");

		String checkout_dayStr = checkout_dateStr[0];

		String[] splitDates = checkout_dateStr[1].split(" ");

		String checkout_month = splitDates[1];
		String checkout_datee = splitDates[2];
		String checkout_year = splitDates[3];

		dateTxt1.setText(checkout_datee);
		monthTxt1.setText(checkout_month + "'"
				+ checkout_year.replace("20", ""));
		dayTxt1.setText(checkout_dayStr);
		
		
		// New Calender picker
		
		Locale.setDefault(Locale.US);
		gMonth1 = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) gMonth1.clone();

		items = new ArrayList<String>();

		// get current date
		Date curdate = gMonth1.getTime();
		System.out.println("currend date(date format)" + curdate);
		df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		sel_date = df.format(curdate);
		System.out.println("currend date(string format)" + sel_date);
		adapter = new CalendarAdapter(this, gMonth1, sel_date, sel_date);

		String cur_date = adapter.getCurrentDate();
		today_date = cur_date;
		parseDate(cur_date);

//		buttonDialog = (Button) findViewById(R.id.btn);
//		buttonDialog2 = (Button) findViewById(R.id.btn2);
		
//L		buttonDialog.setText("" + cin_year + "-" + cin_mon + "-" + cin_day);
		checkin_str=""+ cin_year + "-" + cin_mon + "-" + cin_day;
		String cm_dayy="0"+cin_mon;
		int cm_wday=Integer.parseInt(cm_dayy);
		dateTxt.setText(""+cin_day);
		monthTxt.setText(""+txt_month[cin_mon-1]+"'"+subString(cin_year));
		
		
//		String dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE",curdate);
//		dayTxt_in.setText(dayOfTheWeek);
		SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
//		Date date;
		try {
			date = inFormat.parse(""+cin_day+"-"+cin_mon+"-"+cin_year);
			SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
			String goal = outFormat.format(date); 
			dayTxt.setText(goal);
			System.out.println("goal"+goal);
//			Toast.makeText(this,""+goal, 1).show();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean z = incrementday();
		cout_year = inc_year;
		cout_mon = inc_mon;
		cout_day = inc_day;

//		buttonDialog2.setText("" + cout_year + "-" + cout_mon + "-" + cout_day);
		checkout_str=""+ cout_year + "-" + cout_mon + "-" + cout_day;
//		dateTxt1.setText(""+cout_day);
//		monthTxt1.setText(""+txt_month[cout_mon-1]+"'"+subString(cout_year));
//		String dayOfTheWeek2 = (String) android.text.format.DateFormat.format("EEE", new Date(cout_year,cout_mon,cout_day));
//		dayTxt_out.setText(dayOfTheWeek2);
		SimpleDateFormat inFormat1 = new SimpleDateFormat("dd-MM-yyyy");
//		Date date1;
		try {
			date1 = inFormat.parse(""+cout_day+"-"+cout_mon+"-"+cout_year);
			SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
			String goal = outFormat.format(date1); 
			dayTxt1.setText(goal);
			System.out.println("goal"+goal);
//			Toast.makeText(this,""+goal, 1).show();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("increment val" + inc_day + "--" + inc_mon + "---"
				+ inc_year);
		System.out.println("out values" + cout_day + "--" + cout_mon + "---"
				+ cout_year);

		calDiff();
		
		// End Calendeer picker code

		bookBtn.setTypeface(Constants.ProximaNova_Regular);

		bookBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isError = false;
				System.out.println("size of array " + newRoomIdsArray.size()
						+ "array" + newRoomCostHash_new.toString() + ""
						+ newRoomTypeHash.toString());

				result_type.clear();

				for (Entry<Integer, String> entry : newRoomIdsArray.entrySet()) {

					String value = entry.getValue();

					Integer count = result_type.get(value);

					if (count == null)
						result_type.put(value, new Integer(1));
					else
						result_type.put(value, new Integer(count + 1));
				}

				System.out.println("values" + result_type);
				Set<String> keys = result_type.keySet();
				IndRoomtypeCodes.clear();
				for (String key : keys) {
					System.out.println(key);
					System.out.println(result_type.get(key));
					IndRoomtypeCodes.add(key);
				}

				stringBuilder1 = new StringBuilder();
				stringBuilder1.setLength(0);
				for (int i = 0; i < IndRoomtypeCodes.size(); i++) {
					stringBuilder1.append("," + IndRoomtypeCodes.get(i));
				}
				stringBuilder2 = new StringBuilder();
				stringBuilder2.setLength(0);

				System.out.println("adults size " + adultArray.toString());

				for (int i = 0; i < selectedRoomsArray.size(); i++) {

					if (new ArrayList<Integer>(selectedRoomsArray.keySet())
							.get(i) == i) {
						stringBuilder2.append(","
								+ new ArrayList<String>(selectedRoomsArray
										.values()).get(i));
					} /*
					 * else { stringBuilder2.append("," + "0"); }
					 */

					System.out.println("no of   adults "
							+ adultArray.get(new ArrayList<Integer>(
									selectedRoomsArray.keySet()).get(i)));

					if (adultArray.get(
							new ArrayList<Integer>(selectedRoomsArray.keySet())
									.get(i)).equalsIgnoreCase("0")) {

						isError = true;
					}
				}

				System.out.println(" string builder " + stringBuilder2);

				StrIndRtypecode = stringBuilder1.toString()
						.replaceFirst(",", "").toString();
				StrIndRtypecodeWTild = stringBuilder1.toString()
						.replaceFirst("~", "").toString();
				StrIndRNum = stringBuilder2.toString().replaceFirst(",", "")
						.toString();

				System.out.println("typecode" + StrIndRtypecode + ""
						+ StrIndRNum);

				// for roomtypename
				result_type_name.clear();
				for (Entry<Integer, String> entry : newRoomTypeHash.entrySet()) {
					String value = entry.getValue();
					Integer count = result_type_name.get(value);
					if (count == null)
						result_type_name.put(value, new Integer(1));
					else
						result_type_name.put(value, new Integer(count + 1));
				}

				System.out.println("values" + result_type_name);

				Set<String> keys1 = result_type_name.keySet();
				IndRoomtypeName.clear();
				for (String key : keys1) {
					System.out.println(key);
					IndRoomtypeName.add(key);
				}

				stringBuilder3 = new StringBuilder();
				stringBuilder3.setLength(0);
				for (int i = 0; i < IndRoomtypeName.size(); i++) {
					stringBuilder3.append("," + IndRoomtypeName.get(i));
				}

				StrIndRTName = stringBuilder3.toString().replaceFirst(",", "")
						.toString();

				result_room_tariff.clear();
				for (Entry<Integer, String> entry : newRoomCostHash_new
						.entrySet()) {

					String value = entry.getValue();

					Integer count = result_room_tariff.get(value);

					if (count == null)
						result_room_tariff.put(value, new Integer(1));
					else
						result_room_tariff.put(value, new Integer(count + 1));
				}
				System.out.println("values" + result_room_tariff);

				IndRoomTariff.clear();

				IndRoomTariff = new ArrayList<String>(result_room_tariff
						.keySet());

				stringBuilder4 = new StringBuilder();
				stringBuilder4.setLength(0);

				System.out.println("typecode_name" + StrIndRTName);
				System.out.println("values" + result_type_name);
				System.out.println("type codes " + IndRoomtypeCodes.toString());
				System.out.println("selected rooms array "
						+ selectedRoomsArray.toString());

				System.out.println("room  type names " + result_type_name);
				selectedRoomsStr = "";
				selectedRoomCodesStr = "";
				selectedRoomNames = new StringBuilder();
				selectedRoomNames.setLength(0);

				for (int i = 0; i < selectedRoomsArray.size(); i++) {

					List<Integer> selectedRoomsKeys = new ArrayList<Integer>(
							selectedRoomsArray.keySet());
					int key = selectedRoomsKeys.get(i);

					if (!selectedRoomsArray.get(key).equalsIgnoreCase("0")) {

						System.out.println(" key value "
								+ selectedRoomsKeys.get(i));

						selectedRoomsStr = selectedRoomsStr + ","
								+ selectedRoomsArray.get(key);

						System.out.println(" selected1 " + selectedRoomsStr);

						selectedRoomCodesStr = selectedRoomCodesStr + ","
								+ newRoomIdsArray.get(key);

						stringBuilder4.append("," + roomCostArray.get(key));
						selectedRoomNames.append("," + roomTypeArray.get(key));

						strRoomTypeName = selectedRoomNames.toString()
								.replaceFirst("~", "").toString().substring(1);
						StrIndRTariff = stringBuilder4.toString()
								.replaceFirst("~", "").toString().substring(1);

						System.out.println(" selected room names  "
								+ roomTypeArray.get(key));

						System.out.println(" selected room code  "
								+ newRoomIdsArray.get(key));

						System.out.println(" selected rooms "
								+ selectedRoomsArray.get(key));

					}
				}

				checkin_date_str = dateTxt.getText().toString();
				checkin_month_str = monthTxt.getText().toString();
				checkin_day_str = dayTxt.getText().toString();

				checkout_date_str = dateTxt1.getText().toString();
				checkout_month_str = monthTxt1.getText().toString();
				checkout_day_str = dayTxt1.getText().toString();

				night_stay_str = nightStayValTxt.getText().toString();

				no_of_adults = String.valueOf(adultVal);
				no_of_child = String.valueOf(childVal);

				total_no_of_persons = String.valueOf(adultVal + childVal);

				room_type_str = selectRoomTypeBtn.getText().toString();

				strLuxuryTaxValue = luxTaxTxt.getText().toString();

				// double totalTaxSL = servTax + luxTax;
				double totalTaxSL = totluxtax1 + Totservtax1;
				strServiceTaxValue = String.format("%.2f", totalTaxSL);//

				if (Integer.parseInt(night_stay_str) > 200) {

					showCustomDialog("Hotel booking days are allowed for 200 days");

				} else {

					int days = daysBetween(date, date1);

					if (days < 0) {
						showCustomDialog("Check Out date should not be less than Check-in date");
					} else {

						if (sCheckoutTime > sCheckinTime) {
							showCustomDialog("Check Out time should not be more than Check-in time");

						} else {

							if (Double.parseDouble(strServiceTaxValue) == 0.00) {
								showCustomDialog("Please select the rooms before you proceed further");
							} else {

								if (isError) {
									showCustomDialog("Please select number of adults/children");

								} else {

									if (Utility
											.checkInternetConnection(HotelBookingActivity.this)) {

										AlertDialog.Builder alertDialog = new AlertDialog.Builder(
												HotelBookingActivity.this);
										if (isError) {

											showCustomDialog("Please select atleast one adult per room");

										} else {

											alertDialog
													.setMessage("Modification of Check-in/ Check-out Time cannot be done under any circumstances. Please check the time of check-in before proceeding further.");
											alertDialog
													.setPositiveButton(
															"PROCEED",
															new DialogInterface.OnClickListener() {
																public void onClick(
																		DialogInterface dialog,
																		int which) {

																	System.out
																			.println("room_id"
																					+ newRoomIdsArray
																							.toString());

																	if (Utility
																			.checkInternetConnection(HotelBookingActivity.this)) {
																		new BlockRoomNumber()
																				.execute();
																	} else {
																		Utility.showAlertNoInternetService(HotelBookingActivity.this);
																	}

																	// startActivity(new
																	// Intent(
																	// HotelBookingActivity.this,
																	// HotelBookingConfirm.class));
																}
															});

											alertDialog
													.setNegativeButton(
															"CANCEL",
															new DialogInterface.OnClickListener() {
																public void onClick(
																		DialogInterface dialog,
																		int which) {

																	dialog.cancel();
																}
															});

											alertDialog.show();
										}
										// finish();
									} else {
										Utility.showAlertNoInternetService(HotelBookingActivity.this);
									}
								}

							}

						}

					}

				}

			}
		});

//		checkinLayout.setOnClickListener(new OnClickListener() {
//
//			String tag;
//
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				// v.requestFocusFromTouch();
//				dateError = 1;
//				Constants.date_flg = 0;
//				datePickerDialog.show(getFragmentManager(), tag);
//				;
//			}
//		});
		
		checkinLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				flag = false;
				Constants.package_date_flag = 0;
//				String btn_text = buttonDialog.getText().toString();
				// dateTxt_in.getText().toString();
				parseDate(checkin_str);

				// Toast.makeText(context, "Date is:"+cur_date, 1).show();
				setUp(cin_year, cin_mon, cin_day);

			}
		});

//		checkoutLayout.setOnClickListener(new OnClickListener() {
//			String tag1;
//
//			@SuppressWarnings("deprecation")
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Constants.date_flg = 1;
//				datePickerDialog1.show(getFragmentManager(), tag1);
//				;
//			}
//		});
		
		checkoutLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = true;
				Constants.package_date_flag = 0;
//				String btn2_text = buttonDialog2.getText().toString();
				
				parsecoutDate(checkout_str);
				setUp(cout_year, cout_mon, cout_day);
				// setUp(inc_year, inc_mon, inc_day);
				// setUp(cout_year,cout_mon,cout_day);
			}
		});
	}

	public void showCustomDialog(String str) {

		dialog = new Dialog(HotelBookingActivity.this,
				android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog1);

		TextView textView = (TextView) dialog.findViewById(R.id.tv);
		textView.setText(str);
		Button btnSearch = (Button) dialog.findViewById(R.id.btnsearch);

		btnSearch.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		// btnCancel.setOnClickListener(this);

		dialog.show();
	}

	int globalPos = 0;

	private void addingRooms(final int pos) {
		// TODO Auto-generated method stub

		System.out.println("size_newRoomCountHash" + newRoomCountHash.size()
				+ "" + newRoomCountHash.toString());

		globalPos = pos;
		// for (int i = 0; i < addedArray.size(); i++) {
		view = inflater.inflate(R.layout.room_layout, addedRoomLayout, false);

		roomTxt = (TextView) view.findViewById(R.id.textView4);

		noOfRoomsTextView = (TextView) view.findViewById(R.id.noOfRoomsTxt);
		noOfRoomSpnr = (EditText) view.findViewById(R.id.noOfRoomSpnr);

		adultValue1 = (TextView) view.findViewById(R.id.adult_val1);
		childValue1 = (TextView) view.findViewById(R.id.child_val1);

		equalTo1 = (TextView) view.findViewById(R.id.equal);
		noOfpersons1 = (TextView) view.findViewById(R.id.roomsResult_val1);

		adult_minus_btn1 = (Button) view.findViewById(R.id.adult_minus_btn1);
		adult_plus_btn1 = (Button) view.findViewById(R.id.adult_plus_btn1);

		child_minus_btn1 = (Button) view.findViewById(R.id.child_minus_btn1);
		child_plus_btn1 = (Button) view.findViewById(R.id.child_plus_btn1);
		TextView textView_room = (TextView) view
				.findViewById(R.id.textView_room);
		TextView text_child = (TextView) view.findViewById(R.id.text_child);
		TextView text_adult = (TextView) view.findViewById(R.id.text_adult);

		selectRoomTypeBtn1 = (TextView) view
				.findViewById(R.id.selectRoomTypeBtn1);

		if (personsVal1 == 0) {
			equalTo1.setVisibility(View.INVISIBLE);
			noOfpersons1.setVisibility(View.INVISIBLE);
		}

		text_adult.setTypeface(Constants.ProximaNova_Regular);
		text_child.setTypeface(Constants.ProximaNova_Regular);
		textView_room.setTypeface(Constants.ProximaNova_Regular);
		roomTxt.setTypeface(Constants.ProximaNova_Regular);
		noOfRoomsTextView.setTypeface(Constants.ProximaNova_Regular);
		adultValue1.setTypeface(Constants.ProximaNova_Regular);
		childValue1.setTypeface(Constants.ProximaNova_Regular);
		equalTo1.setTypeface(Constants.ProximaNova_Regular);
		noOfpersons1.setTypeface(Constants.ProximaNova_Regular);
		selectRoomTypeBtn1.setTypeface(Constants.ProximaNova_Regular);

		noOfRoomSpnr.setTag(pos);
		adult_plus_btn1.setTag(pos);
		adult_minus_btn1.setTag(pos);
		child_plus_btn1.setTag(pos);
		child_minus_btn1.setTag(pos);

		selectRoomTypeBtn1.setTag(addedRoomLayout.getChildCount());

		adultArray.add(pos, "0");
		childArray.add(pos, "0");

		showRoomTypes(pos);

		noOfRoomSpnr.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					System.out.println(" edit box position" + v.getTag());

					editFieldPos = (Integer) v.getTag();
				}
			}
		});
		noOfRoomSpnr.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (s.length() > 0
						&& !s.toString().trim().equalsIgnoreCase("0")) {

					spnSelectedRooms = Integer.parseInt(s.toString());

					new_TrackId.set(editFieldPos, editFieldPos + ","
							+ spnSelectedRooms);
					newRoomCountHash.put(editFieldPos, spnSelectedRooms);

					room_id_str = roomIdArray.get(editFieldPos);

					System.out.println("selected rooms count"
							+ spnSelectedRooms);
					System.out.println("selected rooms count_hash"
							+ newRoomCountHash.toString());

					if (Utility
							.checkInternetConnection(HotelBookingActivity.this)) {
						new GetRoomTypesDetails().execute();
					} else {
						Utility.showAlertNoInternetService(HotelBookingActivity.this);
					}

				} else {

					// new_TrackId.remove(editFieldPos);//
					// ,editFieldPos+","+spnSelectedRooms);
					// newRoomCountHash.remove(editFieldPos);

					// room_id_str = roomIdArray.get(editFieldPos);
					selectedRoomsArray.remove(editFieldPos);

					spnSelectedRooms = 0;
					getTotalAmount(editFieldPos, 0, Integer
							.parseInt(nightStayValTxt.getText().toString()));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		adult_plus_btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int val = 0;
				System.out.println(" tag " + v.getTag().toString());
				System.out.println(" tag "
						+ selectedRoomsArray.get(v.getTag().toString()));

				int position = Integer.parseInt(v.getTag().toString());
				if (!new ArrayList<Integer>(selectedRoomsArray.keySet())
						.contains(position)) {
					showCustomDialog("Please select room type");

				} else {

					int childCount = addedRoomLayout.getChildCount();

					for (int c = 0; c < childCount; c++) {

						View childView = addedRoomLayout.getChildAt(c);

						TextView childTextView = (TextView) (childView
								.findViewById(R.id.adult_val1));

						TextView childTextView1 = (TextView) (childView
								.findViewById(R.id.child_val1));

						EditText equalSpinner = (EditText) (childView
								.findViewById(R.id.noOfRoomSpnr));

						// System.out.println("   max value "
						// + Integer.parseInt(maxAdultPaxList.get(Integer
						// .parseInt(v.getTag().toString())))
						// * Integer.parseInt(equalSpinner
						// .getText().toString()));

						TextView resultval = (TextView) (childView
								.findViewById(R.id.roomsResult_val1));

						adultVal1 = 0;

						if (position == c) {

							adultVal1 = Integer.parseInt(childTextView
									.getText().toString()) + 1;

							// System.out.println("values " +);

							if (selectedRoomsArray.get(position)
									.equalsIgnoreCase("0")) {

								showCustomDialog("selected rooms are not available");

							} else {
								String edt_vlaue = equalSpinner.getText()
										.toString();
								if (edt_vlaue.equals("")) {
									edt_vlaue = "0";
								}
								if (adultVal1 > Integer.parseInt(maxAdultPaxList
										.get(Integer.parseInt(v.getTag()
												.toString())))
										* Integer.parseInt(edt_vlaue)) {
									if (Integer.parseInt(edt_vlaue) == 0) {
										showCustomDialog("Please make sure before no of rooms");

									} else {

										adultVal1 = adultVal1 - 1;
										showCustomDialog("Maximum No.of Adults Per Room Is "
												+ maxAdultPaxList.get(Integer
														.parseInt(v.getTag()
																.toString())));
									}
								} else {

									personsVal1 = Integer.parseInt(resultval
											.getText().toString());

									childTextView.setText("" + adultVal1);

									val = Integer.parseInt(childTextView
											.getText().toString())
											+ Integer.parseInt(childTextView1
													.getText().toString());

									resultval.setText("" + val);

									if (adultArray.size() > c) {

										adultArray.set(c, "" + adultVal1);

										childArray.set(
												c,
												""
														+ Integer
																.parseInt(childTextView1
																		.getText()
																		.toString()));
									} else {
										childArray.add(childTextView1.getText()
												.toString());
										adultArray.add("" + adultVal1);
									}

								}
							}
						}
					}

					parentScroll.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							parentScroll.smoothScrollTo(0, bookBtn.getBottom());
							selectRoomTypeBtn1.setFocusable(true);
						}
					});
				}
			}
		});

		adult_minus_btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int position = Integer.parseInt(v.getTag().toString());

				int childCount = addedRoomLayout.getChildCount();

				int val = 0;

				for (int c = 0; c < childCount; c++) {

					View childView = addedRoomLayout.getChildAt(c);

					TextView childTextView = (TextView) (childView
							.findViewById(R.id.adult_val1));

					TextView childTextView1 = (TextView) (childView
							.findViewById(R.id.child_val1));

					TextView equal = (TextView) (childView
							.findViewById(R.id.equal));

					TextView resultval = (TextView) (childView
							.findViewById(R.id.roomsResult_val1));

					adultVal1 = 0;
					if (position == c) {

						if (Integer
								.parseInt(childTextView.getText().toString()) == 0) {

						} else {
							adultVal1 = Integer.parseInt(childTextView
									.getText().toString()) - 1;
							childTextView.setText("" + adultVal1);

						}

						personsVal1 = Integer.parseInt(resultval.getText()
								.toString());

						val = Integer.parseInt(childTextView.getText()
								.toString())
								+ Integer.parseInt(childTextView1.getText()
										.toString());

						if (adultArray.size() > c) {
							adultArray.set(c, "" + adultVal1);
							childArray.set(c, ""
									+ childTextView1.getText().toString());
						} else {
							adultArray.add("" + adultVal1);
							childArray.add(childTextView1.getText().toString());
						}

						resultval.setText("" + val);
					}
				}

				parentScroll.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						parentScroll.smoothScrollTo(0, bookBtn.getBottom());
						selectRoomTypeBtn1.setFocusable(true);
					}
				});
			}
		});

		child_plus_btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				childBtnClickFlag = 1;

				int val = 0;

				int position = Integer.parseInt(v.getTag().toString());

				int childCount = addedRoomLayout.getChildCount();

				for (int c = 0; c < childCount; c++) {

					View childView = addedRoomLayout.getChildAt(c);

					TextView childTextView1 = (TextView) (childView
							.findViewById(R.id.child_val1));

					TextView childTextView = (TextView) (childView
							.findViewById(R.id.adult_val1));

					EditText equalSpinner = (EditText) (childView
							.findViewById(R.id.noOfRoomSpnr));

					TextView resultval = (TextView) (childView
							.findViewById(R.id.roomsResult_val1));

					childVal1 = 0;

					/*
					 * if (position == c) {
					 * 
					 * adultVal1 = Integer.parseInt(childTextView
					 * .getText().toString()) + 1;
					 * 
					 * if (adultVal1 > Integer.parseInt(maxAdultPaxList
					 * .get(Integer .parseInt(v.getTag().toString())))
					 * Integer.parseInt(equalSpinner .getText().toString())) {
					 * if (Integer.parseInt(equalSpinner .getText().toString())
					 * == 0) {
					 * 
					 * AlertDialog.Builder altDialog = new AlertDialog.Builder(
					 * HotelBookingActivity.this); // altDialog //
					 * .setMessage("Please make sure before no of rooms" // +
					 * no_of_adults_str); // here altDialog
					 * .setMessage("Please make sure before no of rooms"); //
					 * here altDialog .setNeutralButton( "OK", new
					 * DialogInterface.OnClickListener() {
					 * 
					 * @Override public void onClick( DialogInterface dialog,
					 * int which) {
					 * 
					 * dialog.dismiss(); } }); altDialog.show();
					 * 
					 * } else {
					 * 
					 * adultVal1 = adultVal1 - 1; AlertDialog.Builder altDialog
					 * = new AlertDialog.Builder( HotelBookingActivity.this);
					 * altDialog.setMessage("Maximum No.of Adults Per Room Is "
					 * + maxAdultPaxList.get(Integer .parseInt(v.getTag()
					 * .toString()))); // here altDialog .setNeutralButton(
					 * "OK", new DialogInterface.OnClickListener() {
					 * 
					 * @Override public void onClick( DialogInterface dialog,
					 * int which) {
					 * 
					 * dialog.dismiss(); } }); altDialog.show(); } } else {
					 * 
					 * personsVal1 = Integer.parseInt(resultval
					 * .getText().toString());
					 * 
					 * childTextView.setText("" + adultVal1);
					 * 
					 * val = Integer.parseInt(childTextView.getText()
					 * .toString()) + Integer.parseInt(childTextView1
					 * .getText().toString());
					 * 
					 * resultval.setText("" + val);
					 * 
					 * if (adultArray.size() > c) {
					 * 
					 * adultArray.set(c, "" + adultVal1);
					 * 
					 * childArray.set( c, "" + Integer .parseInt(childTextView1
					 * .getText() .toString())); } else {
					 * childArray.add(childTextView1.getText() .toString());
					 * adultArray.add("" + adultVal1); }
					 * 
					 * } }
					 */

					if (position == c) {

						// if (adultVal1 == 0) {
						if (Integer
								.parseInt(childTextView.getText().toString()) == 0) {// praveen
							showCustomDialog("Please select adults first");
						} else {

							childVal1 = Integer.parseInt(childTextView1
									.getText().toString()) + 1;

							// if (childVal1 > Integer
							// .parseInt(no_of_adults_str)) {
							if (childVal1 > Integer.parseInt(maxAdultPaxList
									.get(Integer
											.parseInt(v.getTag().toString())))
									* Integer.parseInt(equalSpinner.getText()
											.toString())) {

								childVal1 = childVal1 - 1;
								showCustomDialog("Maximum No.of Children Per Room Is "
										+ maxAdultPaxList.get(Integer
												.parseInt(v.getTag().toString())));

							} else {

								personsVal1 = Integer.parseInt(resultval
										.getText().toString());
								childTextView1.setText("" + childVal1);

								val = Integer.parseInt(childTextView.getText()
										.toString())
										+ Integer.parseInt(childTextView1
												.getText().toString());

								resultval.setText("" + val);

								if (childArray.size() > c) {
									childArray.set(c, childTextView1.getText()
											.toString());
								} else {
									childArray.add(childTextView1.getText()
											.toString());
								}
							}
						}
					}
				}

				parentScroll.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						parentScroll.smoothScrollTo(0, bookBtn.getBottom());
						selectRoomTypeBtn1.setFocusable(true);
					}
				});

				// totalArray.set(Integer.parseInt(v.getTag().toString()),
				// ""+personsVal1);
				// }

			}
		});

		child_minus_btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// v.requestFocusFromTouch();
				childBtnClickFlag = 1;

				int position = Integer.parseInt(v.getTag().toString());

				int childCount = addedRoomLayout.getChildCount();

				int val = 0;

				for (int c = 0; c < childCount; c++) {

					View childView = addedRoomLayout.getChildAt(c);

					TextView childTextView = (TextView) (childView
							.findViewById(R.id.child_val1));

					TextView childTextView1 = (TextView) (childView
							.findViewById(R.id.adult_val1));

					TextView equal = (TextView) (childView
							.findViewById(R.id.equal));

					TextView resultval = (TextView) (childView
							.findViewById(R.id.roomsResult_val1));

					childVal1 = 0;
					if (position == c) {

						if (Integer
								.parseInt(childTextView.getText().toString()) == 0) {

						} else {
							childVal1 = Integer.parseInt(childTextView
									.getText().toString()) - 1;
							childTextView.setText("" + childVal1);
						}

						personsVal1 = Integer.parseInt(resultval.getText()
								.toString());

						val = Integer.parseInt(childTextView.getText()
								.toString())
								+ Integer.parseInt(childTextView1.getText()
										.toString());

						resultval.setText("" + val);

						if (childArray.size() > c) {
							childArray.set(c,

							childTextView.getText().toString());
						} else {
							childArray.add(childTextView.getText().toString());
						}

					}
				}

				parentScroll.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						parentScroll.smoothScrollTo(0, bookBtn.getBottom());
						selectRoomTypeBtn1.setFocusable(true);
					}
				});

			}
		});

		// i = i + 1;
		// roomTxt.setText("ROOM TYPE");

		addedRoomLayout.addView(view);
		// }

	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
			new OnDateSetListener() {

				public void onDateSet(DatePickerDialog datePickerDialog,
						int year, int month, int day) {

					SimpleDateFormat formatter = new SimpleDateFormat(
							"E, MMM dd yyyy");

					date = new Date(year - 1900, month, day);

					Constants.CheckInDate = formatter.format(date);

					SimpleDateFormat formatter1 = new SimpleDateFormat(
							"dd-MM-yyyy");

					Constants.CheckInDateStr = formatter1.format(date);

					// Print what date is today!
					System.out.println("Report Date: " + Constants.CheckInDate);

					int days;
					if (dateError == 0) {
						days = daysBetween(date, date1) + 1;
					} else {
						Calendar c = Calendar.getInstance();
						
						c.setTime(date);
						c.add(Calendar.DATE, 1);
						
						date1 = c.getTime();
						days = daysBetween(date, date1);
					}

					if (days <= 0) {
						nightStayValTxt.setText(String.valueOf(0));
					} else {
						nightStayValTxt.setText(String.valueOf(days));

						getRoomsSpinnerList();
						//
						getTotalAmount(spnPos, spnSelectedRooms, days);
					}

					String[] dateStr = formatter.format(date).split(",");

					String dayStr = dateStr[0];

					String[] splitDate = dateStr[1].split(" ");

					String month1 = splitDate[1];
					String datee = splitDate[2];
					String year1 = splitDate[3];

					dateTxt.setText(datee);
					monthTxt.setText(month1 + "'" + year1.replace("20", ""));
					dayTxt.setText(dayStr);
					
					Calendar c = Calendar.getInstance();
					
					c.setTime(date);
					c.add(Calendar.DATE, 1);
					SimpleDateFormat df = new SimpleDateFormat("E, MMM dd yyyy");
					String formattedDate = df.format(c.getTime());
					
					date1 = c.getTime();
					
					Constants.CheckOutDateStr = formatter1.format(date1);
					
//					Toast.makeText(getApplicationContext(), formattedDate, 200).show();
					
					String[] dateStr2 = formattedDate.split(",");

					String dayStr2 = dateStr2[0];

					String[] splitDate2 = dateStr2[1].split(" ");

					String month2 = splitDate2[1];
					String datee2 = splitDate2[2];
					String year2 = splitDate2[3];

					dateTxt1.setText(datee2);
					monthTxt1.setText(month2 + "'"
							+ year2.replace("20", ""));
					dayTxt1.setText(dayStr2);

					if (Constants.CheckInDateStr
							.equalsIgnoreCase(Constants.currentDate)) {
						checkinSpnr.setSelection(current_date_checkin_hour);
						checkoutSpnr.setSelection(current_date_checkin_hour);
					} else {
						checkinSpnr.setSelection(checkin_hours);
						checkoutSpnr.setSelection(checkin_hours);
					}

				}

			}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
			mCalendar.get(Calendar.DAY_OF_MONTH));

	final DatePickerDialog datePickerDialog1 = DatePickerDialog.newInstance(
			new OnDateSetListener() {

				public void onDateSet(DatePickerDialog datePickerDialog,
						int year, int month, int day) {

					SimpleDateFormat formatter = new SimpleDateFormat(
							"E, MMM dd yyyy");

					date1 = new Date(year - 1900, month, day);

					SimpleDateFormat formatter1 = new SimpleDateFormat(
							"dd-MM-yyyy");

					Constants.CheckOutDateStr = formatter1.format(date1);

					int days;
					if (dateError == 0) {
						days = daysBetween(date, date1) + 1;
					} else {
						days = daysBetween(date, date1);
					}

					if (days <= 0) {
						showCustomDialog("Check Out date should not be less than Check-in date");
					} else {
						// spnSelectedRooms = 0;
						// spnPos = 0;
						getRoomsSpinnerList();

						nightStayValTxt.setText(String.valueOf(days));

						getTotalAmount(spnPos, spnSelectedRooms, days);

						String[] dateStr = formatter.format(date1).split(",");

						String dayStr = dateStr[0];

						String[] splitDate = dateStr[1].split(" ");

						String month2 = splitDate[1];
						String datee2 = splitDate[2];
						String year2 = splitDate[3];

						dateTxt1.setText(datee2);
						monthTxt1.setText(month2 + "'"
								+ year2.replace("20", ""));
						dayTxt1.setText(dayStr);
					}

				}

			}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
			mCalendar.get(Calendar.DAY_OF_MONTH));

	public int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	
	// New Calender Picker Function

	public void setUp(int year, int mon, int day) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				HotelBookingActivity.this);
		builder.setCancelable(true);
		builder.setNegativeButton("Ignore",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// finish the application
						HotelBookingActivity.this.finish();
					}
				});

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.calendar);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
//		rLayout = (LinearLayout) dialog.findViewById(R.id.text);
		gridview = (GridView) dialog.findViewById(R.id.gridview);

		String monStr = "" + mon;
		if (monStr.length() == 1) {
			monStr = "0" + mon;
		}

		String dayStr = "" + day;
		if (dayStr.length() == 1) {
			dayStr = "0" + day;
		}
		String sel_date2 = "" + year + "-" + monStr + "-" + dayStr;
		// month = (GregorianCalendar) GregorianCalendar.getInstance();
		// change month Georgee............

		String[] curdate_array = sel_date.split("-");
		int cur_year = Integer.parseInt(curdate_array[0]
				.replaceFirst("^0*", ""));
		int cur_mon = Integer
				.parseInt(curdate_array[1].replaceFirst("^0*", ""));

		cur_mon = cur_mon - 1;

		mon = mon - 1;
		while (gMonth1.get(GregorianCalendar.MONTH) != mon) {
			if (gMonth1.get(GregorianCalendar.MONTH) < mon) {
				gMonth1.set(GregorianCalendar.MONTH,
						gMonth1.get(GregorianCalendar.MONTH) + 1);
			} else {
				gMonth1.set(GregorianCalendar.MONTH,
						gMonth1.get(GregorianCalendar.MONTH) - 1);
			}
		}

		int mon_val = gMonth1.get(GregorianCalendar.MONTH);
		int year_val = gMonth1.get(GregorianCalendar.YEAR);

		adapter = new CalendarAdapter(this, gMonth1, sel_date2, sel_date);
		gridview.setAdapter(adapter);

		handler = new Handler();
		handler.post(calendarUpdater);

		title = (TextView) dialog.findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", gMonth1));
		selecteddate = title.getText().toString();
		System.out.println("datedate" + selecteddate);
		String[] title_array = selecteddate.split(" ");
		title_year = title_array[1].replaceFirst("^0*", "");
		RelativeLayout previous = (RelativeLayout) dialog
				.findViewById(R.id.previous);

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String[] curdate_array = sel_date.split("-");
				int cur_year = Integer.parseInt(curdate_array[0].replaceFirst(
						"^0*", ""));
				int cur_mon = Integer.parseInt(curdate_array[1].replaceFirst(
						"^0*", ""));

				int mon_val = gMonth1.get(GregorianCalendar.MONTH);
				mon_val = mon_val + 1;
				int year_val = gMonth1.get(GregorianCalendar.YEAR);

				if ((mon_val > cur_mon) || (year_val > cur_year)) {
					setPreviousMonth();
					refreshCalendar();
				}
			}
		});

		RelativeLayout next = (RelativeLayout) dialog.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// removing the previous view if added

				desc = new ArrayList<String>();
				cal_picker_date_arry = new ArrayList<String>();
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString
						.get(position);

				String[] separatedTime = selectedGridDate.split("-");
				gridvalueString = separatedTime[2].replaceFirst("^0*", "");
				selected_day = separatedTime[2].replaceFirst("^0*", "");
				selected_mon = separatedTime[1].replaceFirst("^0*", "");
				selected_year = separatedTime[0].replaceFirst("^0*", "");
				if (!flag) {
					cin_year = StringToInt(selected_year);
					cin_mon = StringToInt(selected_mon);
					cin_day = StringToInt(selected_day);
//					buttonDialog.setText("" + cin_year + "-" + cin_mon + "-"
//							+ cin_day);
					checkin_str = "" + cin_year + "-" + cin_mon + "-"
							+ cin_day;
					dateTxt.setText("" + cin_day);
					monthTxt.setText("" + txt_month[cin_mon - 1] + "'"
							+ subString(cin_year));
					// String dayOfTheWeek = (String)
					// android.text.format.DateFormat.format("EEE", new
					// Date(cin_year,cin_mon,cin_day));
					// dayTxt_in.setText(dayOfTheWeek);
					SimpleDateFormat inFormat4 = new SimpleDateFormat(
							"dd-MM-yyyy");
					Date date4;
					try {
						date4 = inFormat4.parse("" + cin_day + "-" + cin_mon
								+ "-" + cin_year);
						
						Constants.CheckInDateStr = inFormat4.format(date4);
						
						SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
						String goal = outFormat.format(date4);
						dayTxt.setText(goal);
						
						if (Constants.CheckInDateStr
								.equalsIgnoreCase(Constants.currentDate)) {
							checkinSpnr.setSelection(current_date_checkin_hour);
							checkoutSpnr.setSelection(current_date_checkin_hour);
						} else {
							checkinSpnr.setSelection(12);
							checkoutSpnr.setSelection(12);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// if((cout_year==cin_year)||(cout_mon==cin_mon))
					// if(cout_day<=cin_day)
					// {
					// incrementday();
					// cout_year=inc_year;
					// cout_mon=inc_mon;
					// cout_day=inc_day;
					// buttonDialog2.setText(""+cout_year+"-"+cout_mon+"-"+cout_day);
					// }
					//
					// incrementday();
					// cout_year=inc_year;
					// cout_mon=inc_mon;
					// cout_day=inc_day;
					// buttonDialog2.setText(""+cout_year+"-"+cout_mon+"-"+cout_day);

					Date dout = new Date(cout_year, cout_mon, cout_day);
					Date din = new Date(cin_year, cin_mon, cin_day);

					if (dout.compareTo(din) > 0) {

					} else {
						incrementday();
						cout_year = inc_year;
						cout_mon = inc_mon;
						cout_day = inc_day;
//						buttonDialog2.setText("" + cout_year + "-" + cout_mon
//								+ "-" + cout_day);
						checkout_str=""+ cout_year + "-" + cout_mon + "-" + cout_day;

						dateTxt1.setText("" + cout_day);
						monthTxt1.setText("" + txt_month[cout_mon - 1] + "'"
								+ subString(cout_year));
						// String dayOfTheWeek1 = (String)
						// android.text.format.DateFormat.format("EEE", new
						// Date(cout_year,cout_mon,cout_day));
						// dayTxt_out.setText(dayOfTheWeek1);
						SimpleDateFormat inFormat2 = new SimpleDateFormat(
								"dd-MM-yyyy");
						Date date2;
						try {
							date2 = inFormat2.parse("" + cout_day + "-"
									+ cout_mon + "-" + cout_year);
							SimpleDateFormat outFormat = new SimpleDateFormat(
									"EEE");
							String goal = outFormat.format(date2);
							dayTxt1.setText(goal);
							// System.out.println("goal"+goal);
							// Toast.makeText(this,""+goal, 1).show();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					cout_year = StringToInt(selected_year);
					cout_mon = StringToInt(selected_mon);
					cout_day = StringToInt(selected_day);
					// Toast.makeText(context,
					// ""+cout_day+"--"+cout_mon+"--"+cout_year, 1).show();
					if ((cout_day > cin_day) || (cout_mon > cin_mon)
							|| (cout_year > cin_year)) {
//						buttonDialog2.setText("" + cout_year + "-" + cout_mon
//								+ "-" + cout_day);
						checkout_str=""+ cout_year + "-" + cout_mon + "-" + cout_day;
						dateTxt1.setText("" + cout_day);
						monthTxt1.setText("" + txt_month[cout_mon - 1] + "'"
								+ subString(cout_year));
						// String dayOfTheWeek = (String)
						// android.text.format.DateFormat.format("EEE", new
						// Date(cout_year,cout_mon,cout_day));
						// dayTxt_out.setText(dayOfTheWeek);
						SimpleDateFormat inFormat3 = new SimpleDateFormat(
								"dd-MM-yyyy");
						Date date3;
						try {
							date3 = inFormat3.parse("" + cout_day + "-"
									+ cout_mon + "-" + cout_year);
							
							Constants.CheckOutDateStr = inFormat3.format(date3);
							
							SimpleDateFormat outFormat = new SimpleDateFormat(
									"EEE");
							String goal = outFormat.format(date3);
							dayTxt1.setText(goal);
							// System.out.println("goal"+goal);
							// Toast.makeText(this,""+goal, 1).show();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
//						Toast.makeText(context,
//								"Date must be greater than Chekin Date", 1)
//								.show();
						
						dialogmsg = "Check-out date should not be less than Check-in date";
						showCustomDialog(dialogmsg);
					}

				}

				System.out.println("selected date::" + gridvalueString);
				// buttonDialog.setText(""+gridvalueString);
				// buttonDialog.setText(""+gridvalueString);

				// System.out.println("hello display date"+txt_selected.getText().toString());

				// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking
				// offdays.

				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v);

				for (int i = 0; i < Utility.startDates.size(); i++) {
					if (Utility.startDates.get(i).equals(selectedGridDate)) {
						desc.add(Utility.nameOfEvent.get(i));
					}
				}

				if (desc.size() > 0) {
					for (int i = 0; i < desc.size(); i++) {
						TextView rowTextView = new TextView(HotelBookingActivity.this);
						// set some properties of rowTextView or
						// something
						rowTextView.setText("Event:" + desc.get(i));
						// Toast.makeText(context, "Date is:"+desc.get(i),
						// 1).show();
						rowTextView.setTextColor(Color.BLACK);

						// add the textview to the linearlayout
					}
				}
				desc = null;
				calDiff();
				// dialog.cancel();
				dialog.dismiss();
			}
		});
		// txt_selected.setText(gridvalueString+" "+selecteddate);
		dialog.show();
	}
	
	protected void setNextMonth() {

		if (gMonth1.get(GregorianCalendar.MONTH) == gMonth1
				.getActualMaximum(GregorianCalendar.MONTH)) {
			gMonth1.set((gMonth1.get(GregorianCalendar.YEAR) + 1),
					gMonth1.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			gMonth1.set(GregorianCalendar.MONTH,
					gMonth1.get(GregorianCalendar.MONTH) + 1);
		}
	}

	protected void setPreviousMonth() {

		if (gMonth1.get(GregorianCalendar.MONTH) == gMonth1
				.getActualMinimum(GregorianCalendar.MONTH)) {
			gMonth1.set((gMonth1.get(GregorianCalendar.YEAR) - 1),
					gMonth1.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			gMonth1.set(GregorianCalendar.MONTH,
					gMonth1.get(GregorianCalendar.MONTH) - 1);
		}

	}

	// protected void showToast(String string) {
	// Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	//
	// }

	public void refreshCalendar() {
		// TextView title = (TextView)findViewById(R.id.title);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", gMonth1));
		selecteddate = title.getText().toString();
		System.out.println("datedate" + selecteddate);

	}
	
	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String itemvalue;
//			event = Utility.readCalendarEvent(CalendarView.this);
//			Log.d("=====Event====", event.toString());
			Log.d("=====Date ARRAY====", Utility.startDates.toString());

			for (int i = 0; i < Utility.startDates.size(); i++) {
				itemvalue = df.format(itemmonth.getTime());
				System.out.println("itemvalue" + itemvalue);
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add(Utility.startDates.get(i).toString());
			}
			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};
	
	public boolean incrementday() {

		// inc_day=cin_day+1;
		System.out.println("countval" + inc_day);

		if (inc_year % 4 == 0) {

			// leap year
			if ((cin_mon == 1) || (cin_mon == 3) || (cin_mon == 5)
					|| (cin_mon == 7) || (cin_mon == 8) || (cin_mon == 10)
					|| (cin_mon == 12)) {
				if (cin_day == 31) {
					if (cin_mon == 12) {
						inc_mon = 1;
						inc_year = cin_year + 1;
						inc_day = 1;
					} else {
						inc_day = 1;
						inc_mon = cin_mon + 1;
						inc_year = cin_year;
					}

				} else {
					inc_day = cin_day + 1;
					inc_mon = cin_mon;
					inc_year = cin_year;
				}
			}

			else if ((cin_mon == 4) || (cin_mon == 6) || (cin_mon == 9)
					|| (cin_mon == 11)) {
				if (cin_day == 30) {
					inc_day = 1;
					inc_mon = cin_mon + 1;
				} else {
					inc_day = cin_day + 1;
					inc_mon = cin_mon;
				}
			} else {
				if (cin_day == 29) {
					inc_day = 1;
					inc_mon = cin_mon + 1;
				} else {
					inc_day = cin_day + 1;
					inc_mon = cin_mon;
				}
			}
		} else {
			// non leap year
			if ((cin_mon == 1) || (cin_mon == 3) || (cin_mon == 5)
					|| (cin_mon == 7) || (cin_mon == 8) || (cin_mon == 10)
					|| (cin_mon == 12)) {
				if (cin_day == 31) {
					inc_day = 1;
					inc_mon = cin_mon + 1;
				} else {
					inc_day = cin_day + 1;
					inc_mon = cin_mon;
				}
			}

			else if ((cin_mon == 4) || (cin_mon == 6) || (cin_mon == 9)
					|| (cin_mon == 11)) {
				if (cin_day == 30) {
					inc_day = 1;
					inc_mon = cin_mon + 1;
				} else {
					inc_day = cin_day + 1;
					inc_mon = cin_mon;
				}
			} else {
				if (cin_day == 28) {
					inc_day = 1;
					inc_mon = cin_mon + 1;
				} else {
					inc_day = cin_day + 1;
					inc_mon = cin_mon;
				}
			}
		}
		return true;

	}
	
	public void parseDate(String date) {
		// 2014-01-14
		String[] str = date.split("-");
		int size = str.length;
		cin_year = Integer.parseInt(str[0]);
		cin_mon = Integer.parseInt(str[1]);
		cin_day = Integer.parseInt(str[2]);
		System.out.println("checkin date---" + cin_day + "" + cin_mon + ""
				+ cin_year);
	}

	public void parsecoutDate(String date) {
		// 2014-01-14
		String[] str = date.split("-");
		cout_year = Integer.parseInt(str[0]);
		cout_mon = Integer.parseInt(str[1]);
		cout_day = Integer.parseInt(str[2]);
		System.out.println("checkout date---" + cout_day + "" + cout_mon + ""
				+ cout_year);
	}
	
	public int StringToInt(String str) {
		int n = Integer.parseInt(str);
		return n;
	}
	
	public void calDiff() {
		SimpleDateFormat inFormat1 = new SimpleDateFormat("dd-MM-yyyy");
//		Date date1;
		try {
			date1 = inFormat1.parse(""+cout_day+"-"+cout_mon+"-"+cout_year);
			date = inFormat1.parse(""+cin_day+"-"+cin_mon+"-"+cin_year);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Date d1 = new Date(cin_year, cin_mon, cin_day);
//		Date d2 = new Date(cout_year, cout_mon, cout_day);
		long diff = date1.getTime() - date.getTime();
		long l = 1000 * 60 * 60 * 24;
		long num_days = diff / l;
		System.out.println("num_days" + num_days);
		if (num_days > 0) {
//			Toast.makeText(context, "num_days" + num_days, 1).show();
			
			nightStayValTxt.setText(String.valueOf(num_days));
			//getRoomsSpinnerList();
		}
	}
	public String subString(int n)
	{
		String str=""+n;
		String strr=str.substring(2);
		return strr;
	}

	class GetRoomTypes extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(HotelBookingActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is" + Constants.Base_url1
					+ "hoteltypes.jsp?code=" + Constants.new_id + "&dt="
					+ Constants.currentDate);

			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "hoteltypes.jsp?code=" + Constants.new_id + "&dt="
					+ Constants.currentDate);

		}

		@Override
		protected void onProgressUpdate(Integer... a) {
			super.onProgressUpdate(a);
			System.out.println("time" + a);

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			Log.e("Result : ", result);
			System.out.println("result1" + result);
			if (null == result || result.length() == 0) {

			} else {

				try {

					JSONArray json = new JSONArray(result);

					roomTypeArray.clear();
					roomCostArray.clear();
					roomIdArray.clear();
					new_TrackId.clear();
					for (int i = 0; i < json.length(); i++) {

						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						JSONObject e = json.getJSONObject(i);

						map.put("roomTypeCode",
								"Room Type  : " + e.getString("roomTypeCode"));
						map.put("roomTypeName",
								"City Name:" + e.getString("roomTypeName"));
						map.put("roomTariff",
								"Unit Code: " + e.getString("roomTariff"));

						roomIdArray.add(e.getString("roomTypeCode").toString());
						roomTypeArray.add(e.getString("roomTypeName")
								.toString());
						roomCostArray.add(e.getString("roomTariff").toString());

						strDivCode = e.getString("divisionCode");
						int k = 0;
						newRoomCountHash.put(i, k);
						new_TrackId.add("" + i + "," + k);
						addingRooms(i);

					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					Log.e("Tag", "" + e);
				}
			}

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
		}

	}

	class GetRoomTypesDetails extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(HotelBookingActivity.this);
			pDialog.setMessage("Checking rooms availability. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is" + Constants.Base_url1
					+ "roomavailability.jsp?uc=" + Constants.new_id + "&rt="
					+ room_id_str + "&chekindt=" + Constants.CheckInDateStr
					+ "&chekintm=" + strCheckinTime + "&chekoutdt="
					+ Constants.CheckOutDateStr + "&chekouttm="
					+ strCheckoutTime);

			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "roomavailability.jsp?uc=" + Constants.new_id + "&rt="
					+ room_id_str + "&chekindt=" + Constants.CheckInDateStr
					+ "&chekintm=" + strCheckinTime + "&chekoutdt="
					+ Constants.CheckOutDateStr + "&chekouttm="
					+ strCheckoutTime);
		}

		@Override
		protected void onProgressUpdate(Integer... a) {
			super.onProgressUpdate(a);
			System.out.println("time" + a);

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.e("Result : ", result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {

			} else {

				try {

					roomnumbers.clear();
					JSONArray json = new JSONArray(result);

					// map.clear();

					List<String> list = new ArrayList<String>();

					for (int i = 0; i < json.length(); i++) {

						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						JSONObject e = json.getJSONObject(i);

						map.put("maxAllowedRooms",
								"Max Rooms  : "
										+ e.getString("maxAllowedRooms"));
						map.put("maxExtraBedPax",
								"Max Extra Beds:"
										+ e.getString("maxExtraBedPax"));
						map.put("maxAdultPax",
								"Max Adult Pax: " + e.getString("maxAdultPax"));

						no_of_adults_str = e.getString("maxAdultPax")
								.toString();
						maxAdultPaxList.put(editFieldPos, no_of_adults_str);
						Constants.max_allowed_rooms_str = e.getString(
								"maxAllowedRooms").toString();

						// for (int j = 0; j <= Integer
						// .parseInt(Constants.max_allowed_rooms_str); j++)
						// {
						// list.add(String.valueOf(j));
						// }

						available_rooms_str = e.getString("availableRooms")
								.toString();

						String[] str1 = available_rooms_str.split("~");

						if (str1[0].equalsIgnoreCase("0")) {

							list.add("0");

						} else {
							int roomsCounr = str1.length;

							for (int j = 0; j <= roomsCounr; j++) {
								list.add(String.valueOf(j));
							}
						}

					}

					Treemap();

					for (int c = 0; c < addedRoomLayout.getChildCount(); c++) {

						View childView = addedRoomLayout.getChildAt(c);

						childPopupTextView = (TextView) (childView
								.findViewById(R.id.selectRoomTypeBtn1));

						TextView adult_val1 = (TextView) (childView
								.findViewById(R.id.adult_val1));
						TextView child_val1 = (TextView) (childView
								.findViewById(R.id.child_val1));

						if (editFieldPos == c) {

							System.out.println("inside failed dialog"
									+ globalPos + ":" + c + ":"
									+ addedRoomLayout.getChildCount());
							adult_val1.setText("0");
							child_val1.setText("0");

							adultArray.add(globalPos, "0");
							childArray.add(globalPos, "0");

						}

					}

					if (result_type1.get(room_id_str) > Integer
							.parseInt(Constants.max_allowed_rooms_str)) {
						System.out.println("max allowed rooms"
								+ Constants.max_allowed_rooms_str);
						roomAddedFlag = 1;

						for (int c = 0; c < addedRoomLayout.getChildCount(); c++) {

							View childView = addedRoomLayout.getChildAt(c);

							childPopupTextView = (TextView) (childView
									.findViewById(R.id.selectRoomTypeBtn1));
							//
							TextView adult_val1 = (TextView) (childView
									.findViewById(R.id.adult_val1));
							TextView child_val1 = (TextView) (childView
									.findViewById(R.id.child_val1));

							if (globalPos == c) {

								System.out.println("inside failed dialog"
										+ globalPos + ":" + c + ":"
										+ addedRoomLayout.getChildCount());
								adultArray.add(globalPos, "0");
								childArray.add(globalPos, "0");

								adult_val1.setText("0");
								child_val1.setText("0");

								// praveen
								room_type_str = "";

								room_id_str = "";
								room_type_cost = "";

								newRoomIdsArray.remove(globalPos);

								newRoomTypeHash.remove(globalPos);
								newRoomCostHash_new.remove(globalPos);

								no_of_rooms_str = "";

								dummy_no_of_rooms_str = "";

							}
						}
						showCustomDialog("Only "
								+ Constants.max_allowed_rooms_str
								+ " Rooms are allowed");
					} else {

						String[] temp = available_rooms_str.split("~");
						Constants.RTypeAvailableRooms = temp.length;
						if (!available_rooms_str.equalsIgnoreCase("0")) {
							newRoomNumberHash.put(editFieldPos, temp.length);
						} else {
							newRoomNumberHash.put(editFieldPos, 0);
						}

						if (result_type1.get(room_id_str) > Constants.RTypeAvailableRooms) {
							System.out.println("values"
									+ Constants.RTypeAvailableRooms);
							roomAddedFlag = 1;
							for (int c = 0; c < addedRoomLayout.getChildCount(); c++) {

								View childView = addedRoomLayout.getChildAt(c);

								childPopupTextView = (TextView) (childView
										.findViewById(R.id.selectRoomTypeBtn1));

								TextView adult_val1 = (TextView) (childView
										.findViewById(R.id.adult_val1));
								TextView child_val1 = (TextView) (childView
										.findViewById(R.id.child_val1));

								if (globalPos == c) {

									System.out.println("inside failed dialog"
											+ globalPos + ":" + c + ":"
											+ addedRoomLayout.getChildCount());

									adultArray.add(globalPos, "0");
									childArray.add(globalPos, "0");

									adult_val1.setText("0");
									child_val1.setText("0");

									// praveen
									room_type_str = "";

									room_id_str = "";
									room_type_cost = "";

									newRoomIdsArray.remove(globalPos);

									newRoomTypeHash.remove(globalPos);
									newRoomCostHash_new.remove(globalPos);

									no_of_rooms_str = "";
									roomAddedFlag = 1;
									dummy_no_of_rooms_str = "";

								}
							}

							showCustomDialog("Only "
									+ Constants.RTypeAvailableRooms
									+ " Rooms are Available");
						} else {

							if (dummyArray.size() > 0) {
								if (dummyArray.contains(itemPosition + ","
										+ roomIdArray.get(itemPosition))) {

								} else {

									dummyArray.add(itemPosition + ","
											+ roomIdArray.get(itemPosition));

								}

							} else {

								dummyArray.add(itemPosition + ","
										+ roomIdArray.get(itemPosition));

							}

							parentScroll.post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									parentScroll.smoothScrollTo(0,
											bookBtn.getBottom());
									selectRoomTypeBtn1.setFocusable(true);
								}
							});

							typeArray.put(itemPosition,
									"" + roomTypeArray.get(itemPosition));

							noOfRoomsArray.add("" + itemPosition);

						}
					}

					spnPos = editFieldPos;

					if (selectedRoomsArray.size() > spnPos) {

						if (newRoomNumberHash.get(spnPos) >= spnSelectedRooms) {

							if (spnSelectedRooms == 0) {
								if (selectedRoomsArray.containsKey(spnPos)) {

									selectedRoomsArray.remove(spnPos);
								} else {

								}
							} else {

								selectedRoomsArray.put(spnPos,
										String.valueOf(spnSelectedRooms));
							}
						} else {
							spnSelectedRooms = 0;
							selectedRoomsArray.remove(spnPos);
							selectedRoomsArray.put(spnPos,
									String.valueOf(spnSelectedRooms));
							// new_TrackId.remove(editFieldPos);
							new_TrackId.set(editFieldPos, editFieldPos + ","
									+ 0);
							getAlertDialog();
						}

					} else {

						if (newRoomNumberHash.get(spnPos) >= spnSelectedRooms) {

							if (spnSelectedRooms == 0) {
								if (selectedRoomsArray.containsKey(spnPos)) {

									selectedRoomsArray.remove(spnPos);
								} else {

								}
							} else {

								selectedRoomsArray.put(spnPos,
										String.valueOf(spnSelectedRooms));
							}
						} else {
							// new_TrackId.remove(editFieldPos);
							new_TrackId.set(editFieldPos, editFieldPos + ","
									+ 0);
							getAlertDialog();

							// for (int c = 0; c <=
							// addedRoomLayout.getChildCount(); c++)
							// {

							// AlertDialog.Builder altDialog = new
							// AlertDialog.Builder(
							// HotelBookingActivity.this);
							// altDialog
							// .setMessage("selected rooms are not available ");
							// // here
							// altDialog.setNeutralButton("OK",
							// new DialogInterface.OnClickListener() {
							// @Override
							// public void onClick(
							// DialogInterface dialog,
							// int which) {
							//
							// View childView = addedRoomLayout
							// .getChildAt(spnPos);
							//
							// EditText popupSpinner = (EditText) (childView
							// .findViewById(R.id.noOfRoomSpnr));
							//
							// popupSpinner.setText("");
							//
							// dialog.dismiss();
							// }
							// });
							// altDialog.show();

							// Toast.makeText(HotelBookingActivity.this,
							// "selected rooms are not available ",
							// Toast.LENGTH_SHORT).show();
						}
					}

					getTotalAmount(spnPos, spnSelectedRooms,
							Integer.parseInt(nightStayValTxt.getText()
									.toString()));

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
	}

	HashMap<Integer, String> maxAdultPaxList = new HashMap<Integer, String>();

	// dynamic
	private void showRoomTypes(int positoin) {

		// view1 = v;
		System.out.println("position" + positoin);

		itemPosition = positoin;
		int pos = positoin;

		int childCount = addedRoomLayout.getChildCount();

		for (int c = 0; c <= childCount; c++) {

			View childView = addedRoomLayout.getChildAt(c);

			room_type_str = roomTypeArray.get(itemPosition);

			room_id_str = roomIdArray.get(itemPosition);
			room_type_cost = roomCostArray.get(itemPosition);

			newRoomIdsArray.put(pos, room_id_str);

			newRoomTypeHash.put(pos, room_type_str);

			newRoomCostHash_new.put(pos, room_type_cost);

			no_of_rooms_str = "" + itemPosition;

			dummy_no_of_rooms_str = "" + itemPosition;
		}

		try {

			selectRoomTypeBtn1.setText(roomTypeArray.get(positoin));
			room_id_str = roomIdArray.get(positoin);

			// String str;
			// try {
			// str = new GetRoomTypesDetails().execute().get();
			//
			// System.out.println("str " + str);
			// roomnumbers.clear();
			// JSONArray json;
			// json = new JSONArray(str);
			//
			// // map.clear();
			//
			// List<String> list = new ArrayList<String>();
			//
			// for (int i = 0; i < json.length(); i++) {
			//
			// LinkedHashMap<String, String> map = new LinkedHashMap<String,
			// String>();
			// JSONObject e = json.getJSONObject(i);
			//
			// map.put("maxAllowedRooms",
			// "Max Rooms  : " + e.getString("maxAllowedRooms"));
			// map.put("maxExtraBedPax",
			// "Max Extra Beds:" + e.getString("maxExtraBedPax"));
			// map.put("maxAdultPax",
			// "Max Adult Pax: " + e.getString("maxAdultPax"));
			//
			// no_of_adults_str = e.getString("maxAdultPax").toString();
			// maxAdultPaxList.add(no_of_adults_str);
			// Constants.max_allowed_rooms_str = e.getString(
			// "maxAllowedRooms").toString();
			//
			// // for (int j = 0; j <= Integer
			// // .parseInt(Constants.max_allowed_rooms_str); j++) {
			// // list.add(String.valueOf(j));
			// // }
			//
			// available_rooms_str = e.getString("availableRooms")
			// .toString();
			//
			// String[] str1 = available_rooms_str.split("~");
			//
			// if (str1[0].equalsIgnoreCase("0")) {
			//
			// list.add("0");
			//
			// } else {
			// int roomsCounr = str1.length;
			//
			// for (int j = 0; j <= roomsCounr; j++) {
			// list.add(String.valueOf(j));
			// }
			// }
			//
			// }
			//
			// // ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
			// // HotelBookingActivity.this,
			// // android.R.layout.simple_spinner_item, list);
			// //
			// // dataAdapter
			// //
			// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			//
			// // noOfRoomSpnr.setAdapter(dataAdapter);
			// // noOfRoomSpnr.setTag(itemPosition);
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// } catch (ExecutionException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// } catch (JSONException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }

			Treemap();

			if (result_type1.get(room_id_str) > Integer
					.parseInt(Constants.max_allowed_rooms_str)) {
				System.out.println("max allowed rooms"
						+ Constants.max_allowed_rooms_str);
				roomAddedFlag = 1;

				for (int c = 0; c < childCount; c++) {

					View childView = addedRoomLayout.getChildAt(c);

					childPopupTextView = (TextView) (childView
							.findViewById(R.id.selectRoomTypeBtn1));

					if (pos == c) {
						System.out.println("inside failed dialog" + pos + ":"
								+ c + ":" + childCount);
						// childPopupTextView.setText("Select room type");
						// praveen
						room_type_str = "";

						room_id_str = "";
						room_type_cost = "";

						newRoomIdsArray.remove(pos);

						newRoomTypeHash.remove(pos);
						newRoomCostHash_new.remove(pos);

						no_of_rooms_str = "";

						dummy_no_of_rooms_str = "";

					}
				}

				showCustomDialog("Only " + Constants.max_allowed_rooms_str
						+ " Rooms are allowed");

			} else {

				String[] temp = available_rooms_str.split("~");
				Constants.RTypeAvailableRooms = temp.length;
				if (!available_rooms_str.equalsIgnoreCase("0")) {
					newRoomNumberHash.put(pos, temp.length);
				} else {
					newRoomNumberHash.put(pos, 0);
				}

				if (result_type1.get(room_id_str) > Constants.RTypeAvailableRooms) {
					System.out
							.println("values" + Constants.RTypeAvailableRooms);
					roomAddedFlag = 1;
					for (int c = 0; c < childCount; c++) {

						View childView = addedRoomLayout.getChildAt(c);

						childPopupTextView = (TextView) (childView
								.findViewById(R.id.selectRoomTypeBtn1));
						if (pos == c) {
							System.out.println("inside else dialog" + pos + ":"
									+ c + ":" + childCount);
							// praveen
							room_type_str = "";

							room_id_str = "";
							room_type_cost = "";

							newRoomIdsArray.remove(pos);

							newRoomTypeHash.remove(pos);
							newRoomCostHash_new.remove(pos);

							no_of_rooms_str = "";
							roomAddedFlag = 1;
							dummy_no_of_rooms_str = "";

						}
					}
					showCustomDialog("Only " + Constants.RTypeAvailableRooms
							+ " Rooms are Available");
				} else {

					if (dummyArray.size() > 0) {
						if (dummyArray.contains(itemPosition + ","
								+ roomIdArray.get(itemPosition))) {

						} else {

							dummyArray.add(itemPosition + ","
									+ roomIdArray.get(itemPosition));

						}

					} else {

						dummyArray.add(itemPosition + ","
								+ roomIdArray.get(itemPosition));

					}

					parentScroll.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							parentScroll.smoothScrollTo(0, bookBtn.getBottom());
							selectRoomTypeBtn1.setFocusable(true);
						}
					});

					typeArray.put(itemPosition,
							"" + roomTypeArray.get(itemPosition));

					noOfRoomsArray.add("" + itemPosition);

				}
			}

			// noOfRoomSpnr.setOnTouchListener(new OnTouchListener() {
			//
			// @Override
			// public boolean onTouch(View v, MotionEvent arg1) {
			// // TODO Auto-generated method stub
			//
			// System.out.println(" view id "
			// + Integer.parseInt(v.getTag().toString()));
			//
			// spnPos = Integer.parseInt(v.getTag().toString());
			// // //
			//
			// return false;
			// }
			//
			// });

			// noOfRoomSpnr
			// .setOnItemSelectedListener(new OnItemSelectedListener() {
			//
			// @Override
			// public void onItemSelected(AdapterView<?> arg0,
			// View arg1, int arg2, long arg3) {
			// // TODO Auto-generated method
			// // stub
			// spnSelectedRooms = arg2;
			// System.out.println(" view item id ");
			//
			// // if (arg2 > 0) {
			//
			// if (selectedRoomsArray.size() > spnPos) {
			//
			// if (newRoomNumberHash.get(spnPos) >= spnSelectedRooms) {
			//
			// if (spnSelectedRooms == 0) {
			// if (selectedRoomsArray
			// .containsKey(spnPos)) {
			//
			// selectedRoomsArray.remove(spnPos);
			// } else {
			//
			// }
			// } else {
			//
			// selectedRoomsArray.put(spnPos, String
			// .valueOf(spnSelectedRooms));
			// }
			// } else {
			//
			// Toast.makeText(
			// HotelBookingActivity.this,
			// "selected rooms are not available ",
			// Toast.LENGTH_SHORT).show();
			//
			// AlertDialog.Builder altDialog = new AlertDialog.Builder(
			// HotelBookingActivity.this);
			// altDialog
			// .setMessage("selected rooms are not available "); // here
			// altDialog
			// .setNeutralButton(
			// "OK",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(
			// DialogInterface dialog,
			// int which) {
			//
			// View childView = addedRoomLayout
			// .getChildAt(spnPos);
			//
			// Spinner popupSpinner = (Spinner) (childView
			// .findViewById(R.id.noOfRoomSpnr));
			//
			// popupSpinner
			// .setSelection(0);
			//
			// dialog.dismiss();
			// }
			// });
			// altDialog.show();
			//
			// }
			//
			// } else {
			//
			// if (newRoomNumberHash.get(spnPos) >= spnSelectedRooms) {
			//
			// if (spnSelectedRooms == 0) {
			// if (selectedRoomsArray
			// .containsKey(spnPos)) {
			//
			// selectedRoomsArray.remove(spnPos);
			// } else {
			//
			// }
			// } else {
			//
			// selectedRoomsArray.put(spnPos, String
			// .valueOf(spnSelectedRooms));
			// }
			// } else {
			//
			// // for (int c = 0; c <=
			// // addedRoomLayout.getChildCount(); c++)
			// // {
			//
			// AlertDialog.Builder altDialog = new AlertDialog.Builder(
			// HotelBookingActivity.this);
			// altDialog
			// .setMessage("selected rooms are not available "); // here
			// altDialog
			// .setNeutralButton(
			// "OK",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(
			// DialogInterface dialog,
			// int which) {
			//
			// View childView = addedRoomLayout
			// .getChildAt(spnPos);
			//
			// Spinner popupSpinner = (Spinner) (childView
			// .findViewById(R.id.noOfRoomSpnr));
			//
			// popupSpinner
			// .setSelection(0);
			//
			// dialog.dismiss();
			// }
			// });
			// altDialog.show();
			//
			// Toast.makeText(
			// HotelBookingActivity.this,
			// "selected rooms are not available ",
			// Toast.LENGTH_SHORT).show();
			// }
			// }
			//
			// getTotalAmount(spnPos, spnSelectedRooms, Integer
			// .parseInt(nightStayValTxt.getText()
			// .toString()));
			//
			// // } else {
			// //
			// // }
			// }
			//
			// @Override
			// public void onNothingSelected(AdapterView<?> arg0) {
			// // TODO Auto-generated method
			// // stub
			//
			// }
			// });

			// }
			// dialog2.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }
		// });
		// dialog2.show();
	}

	// ProgressDialog progressDialog;// =

	private void getRoomsSpinnerList() {

		Constants.max_allowed_rooms_str = "";

		available_rooms_str = "";
		spnSelectedRooms = 0;
		spnPos = 0;
		adultArray.clear();
		childArray.clear();
		newRoomNumberHash.clear();
		spnSelectedRooms = 0;
		spnPos = 0;
		newRoomCostHash.clear();
		newRoomCostHash1.clear();
		newRoomCountHash.clear();

		// progressDialog.show();
		// ProgressDialog pdialog = new
		// ProgressDialog(HotelBookingActivity.this);
		// pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// pdialog.setMessage("Loading. Please wait...");
		// pdialog.setIndeterminate(true);
		// pdialog.setCanceledOnTouchOutside(false);
		// pdialog.setCancelable(false);
		// pdialog.show();

		// Thread mThread = new Thread() {
		// @Override
		// public void run() {

		// String str = "";
		for (int k = 0; k < roomIdArray.size(); k++) {

			adultArray.add(k, "0");
			childArray.add(k, "0");
			room_id_str = roomIdArray.get(k);
			// luxTaxTxt.setText("Luxury Tax : " + "0");
			// serviceTaxtxt.setText("Service Tax : " + "0");
			// grandTotal_Result.setText("Rs " + "0");

			// new GetRoomTypesDetails1().execute();

			// System.out.println("url is" + Constants.Base_url1
			// + "roomavailability.jsp?uc=" + Constants.new_id + "&rt="
			// + room_id_str + "&chekindt=" + Constants.CheckInDateStr
			// + "&chekintm=" + strCheckinTime + "&chekoutdt="
			// + Constants.CheckOutDateStr + "&chekouttm="
			// + strCheckoutTime);

			/*
			 * System.out.println("str " + str); roomnumbers.clear(); JSONArray
			 * json; json = new JSONArray(str);
			 * 
			 * // map.clear();
			 * 
			 * List<String> list = new ArrayList<String>();
			 * 
			 * for (int i = 0; i < json.length(); i++) {
			 * 
			 * LinkedHashMap<String, String> map = new LinkedHashMap<String,
			 * String>(); JSONObject e = json.getJSONObject(i);
			 * 
			 * map.put("maxAllowedRooms", "Max Rooms  : " +
			 * e.getString("maxAllowedRooms")); map.put("maxExtraBedPax",
			 * "Max Extra Beds:" + e.getString("maxExtraBedPax"));
			 * map.put("maxAdultPax", "Max Adult Pax: " +
			 * e.getString("maxAdultPax"));
			 * 
			 * no_of_adults_str = e.getString("maxAdultPax").toString();
			 * 
			 * Constants.max_allowed_rooms_str = e.getString(
			 * "maxAllowedRooms").toString();
			 * 
			 * for (int j = 0; j <= Integer
			 * .parseInt(Constants.max_allowed_rooms_str); j++) {
			 * list.add(String.valueOf(j)); }
			 * 
			 * available_rooms_str = e.getString("availableRooms") .toString();
			 * 
			 * }
			 * 
			 * ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
			 * HotelBookingActivity.this, android.R.layout.simple_spinner_item,
			 * list);
			 * 
			 * dataAdapter .setDropDownViewResource(android.R.layout.
			 * simple_spinner_dropdown_item);
			 */
			// noOfRoomSpnr.setAdapter(dataAdapter);

			View childView = addedRoomLayout.getChildAt(k);

			EditText popupSpinner = (EditText) (childView
					.findViewById(R.id.noOfRoomSpnr));

			TextView adultTextView = (TextView) (childView
					.findViewById(R.id.adult_val1));

			TextView childTextView = (TextView) (childView
					.findViewById(R.id.child_val1));
			//
			popupSpinner.setText("");
			adultTextView.setText("0");
			childTextView.setText("0");
			// // luxTaxTxt.setText("Luxury Tax : " + "0");
			// // serviceTaxtxt.setText("Service Tax : " + "0");
			// // grandTotal_Result.setText("Rs " + "0");
			//
			// String[] temp = available_rooms_str.split("~");
			// Constants.RTypeAvailableRooms = temp.length;
			// if (!available_rooms_str.equalsIgnoreCase("0")) {
			// newRoomNumberHash.put(k, temp.length);
			// } else {
			// newRoomNumberHash.put(k, 0);
			// }
			//
			// if (k == roomIdArray.size() - 1) {
			// pdialog.dismiss();
			// }
		}

		// }
		// };
		// mThread.start();
	}

	int spnPos = 0, spnSelectedRooms = 0;

	// int spnCount=0
	protected void Treemap() {
		// TODO Auto-generated method stub
		result_type1.clear();
		for (Entry<Integer, String> entry : newRoomIdsArray.entrySet()) {

			String value = entry.getValue();

			Integer count = result_type1.get(value);

			if (count == null)
				result_type1.put(value, new Integer(1));
			else
				result_type1.put(value, new Integer(count + 1));
		}
		System.out.println("values" + result_type1);
	}

	private void getAlertDialog() {

		AlertDialog.Builder altDialog = new AlertDialog.Builder(
				HotelBookingActivity.this);
		altDialog.setMessage("selected rooms are not available "); // here
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				View childView = addedRoomLayout.getChildAt(spnPos);

				EditText popupSpinner = (EditText) (childView
						.findViewById(R.id.noOfRoomSpnr));

				TextView adult_val1 = (TextView) (childView
						.findViewById(R.id.adult_val1));
				TextView child_val1 = (TextView) (childView
						.findViewById(R.id.child_val1));

				popupSpinner.setText("");
				adult_val1.setText("0");
				child_val1.setText("0");
				dialog.dismiss();
			}
		});
		altDialog.show();
	}

	@SuppressLint("NewApi")
	public void showCalenderDialog() {

		final Dialog d = new Dialog(HotelBookingActivity.this);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.setContentView(R.layout.calender_dialog);

		calendar = (CalendarView) d.findViewById(R.id.calendar);

		Button doneButton = (Button) d.findViewById(R.id.button1);

		// sets whether to show the week number.
		calendar.setShowWeekNumber(false);

		// sets the first day of week according to Calendar.
		// here we set Monday as the first day of the Calendar
		calendar.setFirstDayOfWeek(2);

		// The background color for the selected week.
		calendar.setSelectedWeekBackgroundColor(getResources().getColor(
				R.color.green));

		// sets the color for the dates of an unfocused month.
		calendar.setUnfocusedMonthDateColor(getResources().getColor(
				R.color.transparent));

		// sets the color for the separator line between weeks.
		calendar.setWeekSeparatorLineColor(getResources().getColor(
				R.color.transparent));

		// sets the color for the vertical bar shown at the beginning and at the
		// end of the selected date.
		calendar.setSelectedDateVerticalBar(R.color.darkgreen);

		// sets the listener to be notified upon selected date change.
		calendar.setOnDateChangeListener(new OnDateChangeListener() {

			// show the selected date as a toast
			@Override
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int day) {
				Toast.makeText(getApplicationContext(),
						day + "/" + month + "/" + year, Toast.LENGTH_LONG)
						.show();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"E, MMM dd yyyy");

				date = new Date(year - 1900, month, day);

				String[] dateStr = formatter.format(date).split(",");

				String dayStr = dateStr[0];

				String[] splitDate = dateStr[1].split(" ");

				String monthe = splitDate[1];
				String datee = splitDate[2];
				String yeare = splitDate[3];

				dateTxt.setText(datee);
				monthTxt.setText(monthe + "'" + yeare.replace("20", ""));
				dayTxt.setText(dayStr);
				// d.dismiss();
			}
		});

		doneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				d.dismiss();
			}
		});
		d.show();

	}

	public String loadJSONFromAsset1() {
		String json = null;
		try {
			InputStream is = getApplicationContext().getAssets().open(
					"roomType.json");
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// startActivity(new Intent(HotelBookingActivity.this,
			// DetailScreen1.class));
			finish();

		}
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(HotelBookingActivity.this,
				"6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(HotelBookingActivity.this);
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// TODO Auto-generated method stub
		Log.i("value is", "" + newVal);
	}

	static public <T> void executeAsyncTask(AsyncTask<T, ?, ?> task,
			T... params) {

		task.execute(params);
	}

	public class CustomHotelAdapter extends BaseAdapter {
		ArrayList<String> roomtype;

		// String[] address;
		// int[] images;
		Context context;

		LayoutInflater inflater = null;

		public CustomHotelAdapter(Context con, ArrayList<String> roomTypeArray) {
			// TODO Auto-generated constructor stub
			roomtype = roomTypeArray;
			context = con;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return roomtype.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class Holder {
			TextView tv_name;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder = new Holder();
			View rowView;
			rowView = inflater.inflate(R.layout.single_item_row, null);
			holder.tv_name = (TextView) rowView
					.findViewById(R.id.tv_hotel_address);
			holder.tv_name.setTypeface(Constants.ProximaNova_Regular);
			holder.tv_name.setText(roomtype.get(position));
			return rowView;
		}

	}

	float amt, amt1;
	int roomcount;

	private void getTotalAmount(int pos, int selectedRooms, int stayValue) {

		if (selectedRoomsArray.size() > 0) {

			amt1 = Float.parseFloat(roomCostArray.get(pos));

			float roomtariff = 0;
			int RoomCount = 0;
			double includingServiceTaxTotal = 0.0;

			if (spnSelectedRooms != 0) {
				newRoomCostHash.put(pos, amt);
				newRoomCostHash1.put(pos, amt1);
				newRoomCountHash.put(pos, spnSelectedRooms);
			} else {
				newRoomCostHash.remove(pos);
				newRoomCostHash1.remove(pos);
				newRoomCountHash.remove(pos);
			}

			totluxtax1 = 0.00;
			Totservtax1 = 0.00;
			Grandtotal1 = 0.00;

			for (Map.Entry<Integer, Float> entry1 : newRoomCostHash1.entrySet()) {

				String[] parts = new_TrackId.get(entry1.getKey()).split(",");
				String part2 = parts[1]; // 034556

				RoomCount = Integer.parseInt(part2);
				System.out.println("roomcount" + RoomCount);

				System.out.println("room_count1 " + RoomCount);

				System.out.println("total_value1 " + " Pos " + entry1.getKey()
						+ " Value1 " + entry1.getValue());

				roomtariff = entry1.getValue();

//				if (roomtariff < 599) {
//
//					double serviceTax_1 = (double) (roomtariff * (60.0f / 100.0f));
//					serviceTax1 = Math.ceil(serviceTax_1);
//					double servTax_1 = (double) (serviceTax1 * (14.0f / 100.0f));
//					servTax = Math.ceil(servTax_1) * RoomCount * stayValue;
//					luxTax = 0.0;
//
//					includingServiceTaxTotal = roomtariff * RoomCount
//							* stayValue;
//
//					includingServiceTaxTotal = (includingServiceTaxTotal + servTax);
//
//				} else if (roomtariff < 999) {
//					servTax = 0.0;
//					double luxTax_1 = (double) (roomtariff * (5.0f / 100.0f)
//							* RoomCount * stayValue);
//					luxTax = Math.ceil(luxTax_1);
//
//					includingServiceTaxTotal = roomtariff * RoomCount
//							* stayValue;
//
//					includingServiceTaxTotal = (includingServiceTaxTotal + luxTax);
//
//				} else {
//
//					double serviceTax_1 = (double) (roomtariff * (60.0f / 100.0f));
//					serviceTax1 = Math.ceil(serviceTax_1);
//					double servTax_1 = (double) (serviceTax1 * (14.0f / 100.0f));
//					servTax = Math.ceil(servTax_1) * RoomCount * stayValue;
//					double luxTax_1 = (double) (roomtariff * (5.0f / 100.0f)
//							* RoomCount * stayValue);
//					luxTax = Math.ceil(luxTax_1);
//
//					includingServiceTaxTotal = roomtariff * RoomCount
//							* stayValue;
//					includingServiceTaxTotal = (includingServiceTaxTotal
//							+ servTax + luxTax);
//				}
				
				
				
				
				if (roomtariff > 599) {


					if (roomtariff > 999) {

						double serviceTax_1 = (double) (roomtariff * (60.0f / 100.0f));
						serviceTax1 = Math.ceil(serviceTax_1);
						double servTax_1 = (double) (serviceTax1 * (14.0f / 100.0f));
						servTax = Math.ceil(servTax_1) * RoomCount * stayValue;
						//luxTax = 0.0;
						
						double luxTax_1 = (double) (roomtariff * (5.0f / 100.0f)
								* RoomCount * stayValue);
						luxTax = Math.ceil(luxTax_1);

						includingServiceTaxTotal = roomtariff * RoomCount
								* stayValue;

						includingServiceTaxTotal = (includingServiceTaxTotal + servTax +luxTax);

					}else{

						servTax = 0.0;
						double luxTax_1 = (double) (roomtariff * (5.0f / 100.0f)
								* RoomCount * stayValue);
						luxTax = Math.ceil(luxTax_1);

						includingServiceTaxTotal = roomtariff * RoomCount
								* stayValue;

						includingServiceTaxTotal = (includingServiceTaxTotal + luxTax);

					}


					} else{
						includingServiceTaxTotal = roomtariff * RoomCount
								* stayValue;
						
					}

				

				
				
				System.out.println("total amount" + includingServiceTaxTotal);
				System.out.println(" in loop luxTax amount" + luxTax);
				System.out.println("in loop servTax amount" + servTax);

				totluxtax1 = (totluxtax1 + luxTax);
				Totservtax1 = (Totservtax1 + servTax);
				Grandtotal1 = (Grandtotal1 + includingServiceTaxTotal);

			}

			System.out.println("Total luxTax amount" + totluxtax1);
			System.out.println("Total servTax amount" + Totservtax1);
			System.out.println("Total  amount" + Grandtotal1);

			total_Price_str = String.format("%.2f", Grandtotal1);

			luxTaxTxt.setText("Luxury Tax : " + String.valueOf(totluxtax1));
			serviceTaxtxt.setText("Service Tax : "
					+ String.valueOf(Totservtax1));
			grandTotal_Result.setText("Rs " + String.valueOf(Grandtotal1));

			strServiceTax = serviceTaxtxt.getText().toString();

		} else {
			totluxtax1 = 0.00;
			Totservtax1 = 0.00;
			Grandtotal1 = 0.00;
			luxTaxTxt.setText("Luxury Tax : " + "0");
			serviceTaxtxt.setText("Service Tax : "
					+ String.valueOf(Totservtax1));
			grandTotal_Result.setText("Rs " + String.valueOf(Grandtotal1));

		}
	}

	class BlockRoomNumber extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;
		String responseCode;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(HotelBookingActivity.this);
			pDialog.setMessage("Loading...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			selectedRoomsStr1 = selectedRoomsStr.replaceFirst(",", " ").trim();
			System.out.println("url is" + Constants.Base_url1
					+ "blockroomnums.jsp?tr="
					+ selectedRoomsStr.replaceFirst(",", " ").trim() + "&rtc="
					+ selectedRoomCodesStr.replaceFirst(",", " ").trim()
					+ "&cc=" + Constants.id + "&dc=" + strDivCode + "&uc="
					+ Constants.new_id + "&ckindt=" + Constants.CheckInDateStr
					+ "&ckintm=" + HotelBookingActivity.strCheckinTime
					+ "&ckoutdt=" + Constants.CheckOutDateStr + "&ckouttm="
					+ HotelBookingActivity.strCheckoutTime);

			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "blockroomnums.jsp?tr="
					+ selectedRoomsStr.replaceFirst(",", " ").trim() + "&rtc="
					+ selectedRoomCodesStr.replaceFirst(",", " ").trim()
					+ "&cc=" + Constants.id + "&dc=" + strDivCode + "&uc="
					+ Constants.new_id + "&ckindt=" + Constants.CheckInDateStr
					+ "&ckintm=" + HotelBookingActivity.strCheckinTime
					+ "&ckoutdt=" + Constants.CheckOutDateStr + "&ckouttm="
					+ HotelBookingActivity.strCheckoutTime);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			System.out.println("result" + "" + result);
			try {
				TrackId.clear();
				ReservedRoom.clear();
				ReservedRoomType.clear();
				ReservedRoomAndTypeHash.clear();
				strRumNoRumCode = "";
				responsecodeArray.clear();
				notavailableroomsArray.clear();
				JSONArray json = new JSONArray(result);

				for (int i = 0; i < json.length(); i++) {

					JSONObject e = json.getJSONObject(i);

					responseCode = e.getString("response");

					responsecodeArray.add(responseCode);
					TrackId.add(e.getString("trackID"));

					String reservedSeat = e.getString("reservedSeat");
					ReservedRoom.add(e.getString("reservedSeat").replace(",",
							"~"));
					ReservedRoomType.add(e.getString("roomType"));
					StrTempTrackID = e.getString("trackID");

					if (responseCode.equalsIgnoreCase("0")) {
						notavailableroomsArray.add(e.getString("roomType"));
					}

					String[] seatnum;

					if (reservedSeat.contains(",")) {

						seatnum = reservedSeat.split(",");

						for (int j = 0; j < seatnum.length; j++) {
							strRumNoRumCode = strRumNoRumCode + "~"
									+ seatnum[j] + "$"
									+ e.getString("roomType");

							ReservedRoomAndTypeHash.put(seatnum[j],
									e.getString("roomType"));
						}
					} else {

						strRumNoRumCode = strRumNoRumCode + "~" + reservedSeat
								+ "$" + e.getString("roomType");
						ReservedRoomAndTypeHash.put(reservedSeat,
								e.getString("roomType"));
					}

					System.out.println("reserved seats and room types "
							+ strRumNoRumCode);
					// ege:115$20~114$20~202$09

				}

				if (responsecodeArray.contains("0")) {
					System.out.println("roomIDs"
							+ notavailableroomsArray.toString());
					showCustomDialog("Available rooms are blocked. Please try after sometime");
				} else {
					startActivity(new Intent(HotelBookingActivity.this,
							HotelBookingConfirm.class));
				}
				System.out.println("TrackId" + TrackId.toString()
						+ "ReservedRoom" + ReservedRoom.toString() + ""
						+ ReservedRoomType.toString());
				if (pDialog.isShowing())
					pDialog.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class GetRoomTypesDetails1 extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(HotelBookingActivity.this);
			pDialog.setMessage("Loading please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is" + Constants.Base_url1
					+ "roomavailability.jsp?uc=" + Constants.new_id + "&rt="
					+ room_id_str + "&chekindt=" + Constants.CheckInDateStr
					+ "&chekintm=" + strCheckinTime + "&chekoutdt="
					+ Constants.CheckOutDateStr + "&chekouttm="
					+ strCheckoutTime);

			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "roomavailability.jsp?uc=" + Constants.new_id + "&rt="
					+ room_id_str + "&chekindt=" + Constants.CheckInDateStr
					+ "&chekintm=" + strCheckinTime + "&chekoutdt="
					+ Constants.CheckOutDateStr + "&chekouttm="
					+ strCheckoutTime);
		}

		@Override
		protected void onProgressUpdate(Integer... a) {
			super.onProgressUpdate(a);
			System.out.println("time" + a);

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.e("Result : ", result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {

			} else {

				try {
					roomnumbers.clear();
					JSONArray json = null;
					json = new JSONArray(result);

					List<String> list = new ArrayList<String>();

					for (int i = 0; i < json.length(); i++) {

						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						JSONObject e = json.getJSONObject(i);

						map.put("maxAllowedRooms",
								"Max Rooms  : "
										+ e.getString("maxAllowedRooms"));
						map.put("maxExtraBedPax",
								"Max Extra Beds:"
										+ e.getString("maxExtraBedPax"));
						map.put("maxAdultPax",
								"Max Adult Pax: " + e.getString("maxAdultPax"));

						no_of_adults_str = e.getString("maxAdultPax")
								.toString();

						Constants.max_allowed_rooms_str = e.getString(
								"maxAllowedRooms").toString();

						// for (int j = 0; j <= Integer
						// .parseInt(Constants.max_allowed_rooms_str); j++) {
						// list.add(String.valueOf(j));
						// }

						available_rooms_str = e.getString("availableRooms")
								.toString();

						String[] str = available_rooms_str.split("~");

						if (str[0].equalsIgnoreCase("0")) {

							list.add("0");

						} else {
							int roomsCounr = str.length;

							for (int j = 0; j <= roomsCounr; j++) {
								list.add(String.valueOf(j));
							}
						}

					}

					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
							HotelBookingActivity.this,
							android.R.layout.simple_spinner_item, list);

					dataAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					Log.e("Tag", "" + e);
				}

			}
		}
	}
}
