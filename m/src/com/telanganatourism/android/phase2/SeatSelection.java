package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.telanganatourism.android.phase2.SeatSelctionWebview.BlockSeats;
import com.telanganatourism.android.phase2.util.Utility;

@SuppressLint("SimpleDateFormat")
public class SeatSelection extends Activity {

	Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11,
			btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20,
			btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28, btn29,
			btn30, btn31, btn32, btn33, btn34, btn35, btn36, btn37, btn38,
			btn39, btn40, btn41, btn42, btn43, btn44, btn45, btn46, btn47,
			btn48, btn49, btn50;

	TextView chose_boarding, right_arrow_txt, left_arrow_txt,
			chose_return_boarding,totalTxt,	seatsTxt;

	ArrayList<Button> button_ref = new ArrayList<Button>();

	ArrayList<Boolean> bbb = new ArrayList<Boolean>();
	ArrayList<String> slot_status = new ArrayList<String>();
	int slotsS;
	public static String slt_time = "", slt_id = "", No_of_seats = "",
			booked_seats = "21", available_seats = "19";

	int flog1 = 0;
	ArrayList<String> slot_time = new ArrayList<String>();
	ArrayList<String> slot_id = new ArrayList<String>();
	ArrayList<String> locationname = new ArrayList<String>();
	ArrayList<String> locationname1 = new ArrayList<String>();

	ArrayList<String> locationCode = new ArrayList<String>();
	ArrayList<String> dist = new ArrayList<String>();
	ArrayList<String> onwardJourney = new ArrayList<String>();

	ArrayList<String> locationCode1 = new ArrayList<String>();
	ArrayList<String> dist1 = new ArrayList<String>();
	ArrayList<String> onwardJourney1 = new ArrayList<String>();

	TextView titleTxt, package_title_txt, package_type_txt, selectedDate,
			seatsValTxt, destination_txt, totalVal_txt, seatNumbers1,
			seatNumbers2;

	static Date date;
	private int year;
	private int month;
	private int day;

	String changedDate;

//	Utility utility;

	Date currentDate;

	Button selectSeatBtn, selectReturnSeatBtn;

	WebView webView;
	
	int boardingFlag = 0;
	

	public static String upDown, RF,StrBoardingPoint, StrBoardingReturnPoint, departureTime, departureReturnTime,
	StrBoardingPointCode,StrBoardingReturnPointCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.seat_selection);

		/*
		 * locationname.add("Sr Nagar"); locationname.add("Ameerpet");
		 * locationname.add("Panjagutta"); locationname.add("Lakdikapool");
		 * locationname.add("Mehdipatnam"); locationname.add("Kondaur");
		 */

//		utility = new Utility(SeatSelection.this);

		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		Date today = c.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		Constants.ProximaNova_Bold = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "ProximaNova-Bold.otf");

		selectSeatBtn = (Button) findViewById(R.id.selectSeatBtn);

		selectReturnSeatBtn = (Button) findViewById(R.id.selectReturnSeatBtn);

		titleTxt = (TextView) findViewById(R.id.event_title);
		totalTxt = (TextView) findViewById(R.id.totalTxt);
		seatsTxt = (TextView) findViewById(R.id.seatsTxt);

		titleTxt.setTypeface(Constants.ProximaNova_Regular);
		totalTxt.setTypeface(Constants.ProximaNova_Regular);
		seatsTxt.setTypeface(Constants.ProximaNova_Regular);

		titleTxt.setText(DetailScreen1.tit);

		package_title_txt = (TextView) findViewById(R.id.package_title_txt);
		package_type_txt = (TextView) findViewById(R.id.package_type_txt);

		package_title_txt.setText(PackageListing.pack_title);
		package_type_txt.setText(PackageListing.pack_type);

		right_arrow_txt = (TextView) findViewById(R.id.right_arrow_txt);
		left_arrow_txt = (TextView) findViewById(R.id.left_arrow_txt);

		selectedDate = (TextView) findViewById(R.id.selected_date_txt);

		destination_txt = (TextView) findViewById(R.id.destination_txt);

		totalVal_txt = (TextView) findViewById(R.id.totalValTxt);

		seatNumbers1 = (TextView) findViewById(R.id.seatNumbers1);

		seatNumbers2 = (TextView) findViewById(R.id.seatNumbers2);

		TextView availableSeats = (TextView) findViewById(R.id.grn);
		TextView reservedSeats = (TextView) findViewById(R.id.red);
		TextView bookedSeats = (TextView) findViewById(R.id.lad);
		TextView selectedSeats = (TextView) findViewById(R.id.yel);

		seatsValTxt = (TextView) findViewById(R.id.seatsValTxt);

		TextView chooseTxt = (TextView) findViewById(R.id.chooseTxt);
		chose_boarding = (TextView) findViewById(R.id.chose_boarding);
		chose_return_boarding = (TextView) findViewById(R.id.chose_return_boarding);

		package_title_txt.setTypeface(Constants.ProximaNova_Bold);
		package_type_txt.setTypeface(Constants.ProximaNova_Bold);
		selectedDate.setTypeface(Constants.ProximaNova_Regular);
		destination_txt.setTypeface(Constants.ProximaNova_Regular);
		totalVal_txt.setTypeface(Constants.ProximaNova_Regular);

		availableSeats.setTypeface(Constants.ProximaNova_Regular);
		reservedSeats.setTypeface(Constants.ProximaNova_Regular);
		bookedSeats.setTypeface(Constants.ProximaNova_Regular);
		selectedSeats.setTypeface(Constants.ProximaNova_Regular);

		chooseTxt.setTypeface(Constants.ProximaNova_Regular);
		chose_boarding.setTypeface(Constants.ProximaNova_Regular);
		chose_return_boarding.setTypeface(Constants.ProximaNova_Regular);

		selectSeatBtn.setTypeface(Constants.ProximaNova_Regular);
		selectReturnSeatBtn.setTypeface(Constants.ProximaNova_Regular);

		seatNumbers1.setTypeface(Constants.ProximaNova_Regular);
		seatNumbers2.setTypeface(Constants.ProximaNova_Regular);

		seatsValTxt.setTypeface(Constants.ProximaNova_Regular);

		totalVal_txt.setText(Package_Booking.str_total_price);

		selectedDate.setText(Package_Booking.str_date);

		seatsValTxt.setText(Package_Booking.no_of_people);

		if (Package_Booking.seatSelectionDisabe == 1) {
			selectSeatBtn.setEnabled(false);
			selectReturnSeatBtn.setEnabled(false);
		}

		seatNumbers1.setText(Package_Booking.seatNos1);

		seatNumbers2.setText(Package_Booking.seatNos2);

		try {
			currentDate = formatter.parse(formatter.format(today));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		selectSeatBtn.setOnClickListener(new OnClickListener() {

			@SuppressLint("SetJavaScriptEnabled")
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				upDown = "UP";
				RF = "F";
				startActivity(new Intent(SeatSelection.this,
						SeatSelctionWebview.class));

				finish();

			}
		});

		selectReturnSeatBtn.setOnClickListener(new OnClickListener() {

			@SuppressLint("SetJavaScriptEnabled")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				upDown = "DOWN";
				RF = "R";
				startActivity(new Intent(SeatSelection.this,
						SeatSelctionWebview.class));

				finish();
			}
		});

		// right_arrow_txt.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// changedDate = getNextDate(selectedDate.getText().toString());
		// selectedDate.setText(changedDate);
		// }
		// });
		//
		// left_arrow_txt.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// changedDate = getPrevioustDate(selectedDate.getText()
		// .toString());
		//
		// try {
		//
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		//
		// Date periviousDate = sdf.parse(changedDate);
		//
		// if (periviousDate.compareTo(currentDate) > 0) {
		// System.out.println("Date1 is after Date2");
		// selectedDate.setText(changedDate);
		// } else if (periviousDate.compareTo(currentDate) < 0) {
		// System.out.println("Date1 is before Date2");
		// } else if (periviousDate.compareTo(currentDate) == 0) {
		// System.out.println("Date1 is equal to Date2");
		// selectedDate.setText(changedDate);
		// } else {
		// System.out.println("How to get here?");
		// selectedDate.setText(changedDate);
		// }
		//
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// });

		package_title_txt.setText(PackageListing.pack_title);
		package_type_txt.setText(PackageListing.pack_type);
		destination_txt.setText(PackageListing.from_city + " - "
				+ PackageListing.to_city);

		RelativeLayout menu_layout = (RelativeLayout) findViewById(R.id.menuLayout);

		menu_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// startActivity(new Intent(SeatSelection.this,
				// Package_Booking.class));
				finish();
			}
		});

		slot_time.clear();
		slot_id.clear();
		slot_status.clear();

		/** status of ticket booking */

		for (int i = 1; i <= 41; i++) {
			slot_time.add("" + i);
			slot_id.add("" + i);
			if (i <= 21) {
				slot_status.add("0");
			} else if (i > 36) {
				slot_status.add("1");
			} else {
				slot_status.add("0");
			}
		}

		chose_boarding = (TextView) findViewById(R.id.chose_boarding);
		chose_return_boarding = (TextView) findViewById(R.id.chose_return_boarding);

		locationname.clear();
		locationname1.clear();
		locationCode.clear();
		locationCode1.clear();
		
		if (Utility.checkInternetConnection(SeatSelection.this)) {
			new GetBoardingReturnjourney().execute();
			new GetBoardIngonward().execute();
		} else {
			Utility.showAlertNoInternetService(SeatSelection.this);
		}
		
		
		
		
		

		chose_boarding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// showAreaDialog();

				boardingFlag = 1;
				
				if (Utility.checkInternetConnection(SeatSelection.this)) {
					
					showAreaDialog(locationname, chose_boarding);
				} else {
					Utility.showAlertNoInternetService(SeatSelection.this);
				}

			}
		});

		chose_return_boarding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				boardingFlag = 2;
				
				if (Utility.checkInternetConnection(SeatSelection.this)) {

					showAreaDialog(locationname1, chose_return_boarding);
				} else {
					Utility.showAlertNoInternetService(SeatSelection.this);
				}

			}
		});

		RelativeLayout finalResult = (RelativeLayout) findViewById(R.id.finalLayout);

		finalResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (TextUtils.isEmpty(seatNumbers1.getText().toString())) {
					AlertDialog.Builder altDialog = new AlertDialog.Builder(
							SeatSelection.this);
					altDialog.setMessage("Please select Seat Number"); // here
					// add
					// your
					// message
					altDialog.setNeutralButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					altDialog.show();
				} else {

					if (TextUtils.isEmpty(seatNumbers2.getText().toString())) {
						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								SeatSelection.this);
						altDialog
								.setMessage("Please select Return Seat Number"); // here
						// add
						// your
						// message
						altDialog.setNeutralButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								});
						altDialog.show();
					} else {

						if (chose_boarding.getText().toString()
								.equalsIgnoreCase("Boarding points")) {
							// Toast.makeText(SeatSelection.this,
							// "Please select a Boarding Point",
							// Toast.LENGTH_LONG)
							// .show();

							AlertDialog.Builder altDialog = new AlertDialog.Builder(
									SeatSelection.this);
							altDialog
									.setMessage("Please select a Boarding Point"); // here
							// add
							// your
							// message
							altDialog.setNeutralButton("OK",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									});
							altDialog.show();
						} else {

							if (chose_return_boarding.getText().toString()
									.equalsIgnoreCase("Return Boarding points")) {
								AlertDialog.Builder altDialog = new AlertDialog.Builder(
										SeatSelection.this);
								altDialog
										.setMessage("Please select a Return Boarding Point"); // here
								// add
								// your
								// message
								altDialog.setNeutralButton("OK",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												dialog.dismiss();
											}
										});
								altDialog.show();
							} else {
								startActivity(new Intent(SeatSelection.this,
										PackageTouristDetails.class));
								// finish();
							}

						}
					}
				}
			}
		});

		/** no fo seats in bus */

		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);
		btn6 = (Button) findViewById(R.id.btn6);
		btn7 = (Button) findViewById(R.id.btn7);
		btn8 = (Button) findViewById(R.id.btn8);
		btn9 = (Button) findViewById(R.id.btn9);
		btn10 = (Button) findViewById(R.id.btn10);
		btn11 = (Button) findViewById(R.id.btn11);
		btn12 = (Button) findViewById(R.id.btn12);
		btn13 = (Button) findViewById(R.id.btn13);
		btn14 = (Button) findViewById(R.id.btn14);
		btn15 = (Button) findViewById(R.id.btn15);
		btn16 = (Button) findViewById(R.id.btn16);
		btn17 = (Button) findViewById(R.id.btn17);
		btn18 = (Button) findViewById(R.id.btn18);
		btn19 = (Button) findViewById(R.id.btn19);
		btn20 = (Button) findViewById(R.id.btn20);
		btn21 = (Button) findViewById(R.id.btn21);
		btn22 = (Button) findViewById(R.id.btn22);
		btn23 = (Button) findViewById(R.id.btn23);
		btn24 = (Button) findViewById(R.id.btn24);
		btn25 = (Button) findViewById(R.id.btn25);
		btn26 = (Button) findViewById(R.id.btn26);
		btn27 = (Button) findViewById(R.id.btn27);
		btn28 = (Button) findViewById(R.id.btn28);
		btn29 = (Button) findViewById(R.id.btn29);
		btn30 = (Button) findViewById(R.id.btn30);
		btn31 = (Button) findViewById(R.id.btn31);
		btn32 = (Button) findViewById(R.id.btn32);
		btn33 = (Button) findViewById(R.id.btn33);
		btn34 = (Button) findViewById(R.id.btn34);
		btn35 = (Button) findViewById(R.id.btn35);
		btn36 = (Button) findViewById(R.id.btn36);
		btn37 = (Button) findViewById(R.id.btn37);
		btn38 = (Button) findViewById(R.id.btn38);
		btn39 = (Button) findViewById(R.id.btn39);
		btn40 = (Button) findViewById(R.id.btn40);
		btn41 = (Button) findViewById(R.id.btn41);

		button_ref.clear();

		button_ref.add(btn1);
		button_ref.add(btn2);
		button_ref.add(btn3);
		button_ref.add(btn4);
		button_ref.add(btn5);
		button_ref.add(btn6);
		button_ref.add(btn7);
		button_ref.add(btn8);
		button_ref.add(btn9);
		button_ref.add(btn10);
		button_ref.add(btn11);
		button_ref.add(btn12);
		button_ref.add(btn13);
		button_ref.add(btn14);
		button_ref.add(btn15);
		button_ref.add(btn16);
		button_ref.add(btn17);
		button_ref.add(btn18);
		button_ref.add(btn19);
		button_ref.add(btn20);
		button_ref.add(btn21);
		button_ref.add(btn22);
		button_ref.add(btn23);
		button_ref.add(btn24);
		button_ref.add(btn25);
		button_ref.add(btn26);
		button_ref.add(btn27);
		button_ref.add(btn28);
		button_ref.add(btn29);
		button_ref.add(btn30);
		button_ref.add(btn31);
		button_ref.add(btn32);
		button_ref.add(btn33);
		button_ref.add(btn34);
		button_ref.add(btn35);
		button_ref.add(btn36);
		button_ref.add(btn37);
		button_ref.add(btn38);
		button_ref.add(btn39);
		button_ref.add(btn40);
		button_ref.add(btn41);

		slot_booked();
	}

	public static String getNextDate(String curDate) {
		final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		try {
			date = format.parse(curDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return format.format(calendar.getTime());
	}

	public static String getPrevioustDate(String curDate) {
		final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		try {
			date = format.parse(curDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return format.format(calendar.getTime());
	}

	public void slot_booked() {
		if (slot_time.size() > 0) {
			vissible_Method(slot_time.size());
		}
	}

	public void vissible_Method(int size) {
		bbb.clear();
		for (int i = 1; i <= 41; i++) {
			if (i < size) {
				bbb.add(true);
			} else {
				bbb.add(false);
			}
		}
		for (int i = 0; i < bbb.size(); i++) {
			adjustImageVisibility(button_ref.get(i), (bbb.get(i)), i);

		}
	}

	public void adjustImageVisibility(final Button btn, boolean visible,
			final int pos) {
		slotsS = pos;
		if (visible) {
			btn.setVisibility(View.VISIBLE);
			/*
			 * btn.setTextSize(12); btn.setTextColor(Color.WHITE);
			 * 
			 * if ( Integer.parseInt(slot_time.get(pos)) % 2 == 0 ) {
			 * btn.setText("W"+slot_time.get(pos)); } else
			 * 
			 * 
			 * { btn.setText("A"+slot_time.get(pos)); }
			 */
			if (slot_status.get(slotsS).equalsIgnoreCase("0")) {
				btn.setBackgroundResource(R.drawable.one);//
				btn.setTextColor(Color.parseColor("#000000"));

			} else {
				btn.setTextColor(Color.parseColor("#B4B2B0"));
				btn.setBackgroundResource(R.drawable.three);
			}

			/** ticket booking buttons */

			btn.setOnClickListener(new OnClickListener() {
				@SuppressLint("ShowToast")
				public void onClick(View v) {

					flog1 = 1;

					slt_time = slot_time.get(pos);// btn.getText().toString();
					if (slt_time.contains("W")) {
						slt_time.replace("W", "");
					} else if (slt_time.contains("A")) {
						slt_time.replace("A", "");
					}
					String val = slt_time.replace("A", "").replace("W", "");

					slt_id = slot_id.get(slot_time.indexOf(val.trim()));
					if (slot_status.get(slot_time.indexOf(val.trim()))
							.equalsIgnoreCase("0")) {
						showDialog(btn, val);

						// List of numbers we want to concatenate
						// List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7);

						// The string builder used to construct the string
						/*
						 * StringBuilder commaSepValueBuilder = new
						 * StringBuilder();
						 * 
						 * //Looping through the list for ( int i = 0; i<
						 * slot_time.size(); i++){ //append the value into the
						 * builder commaSepValueBuilder.append(val);
						 * 
						 * //if the value is not the last element of the list
						 * //then append the comma(,) as well if ( i !=
						 * slot_time.size()-1){
						 * commaSepValueBuilder.append(", "); } }
						 * Toast.makeText(getApplicationContext(),
						 * commaSepValueBuilder.toString(), Toast.LENGTH_LONG);
						 * //
						 * System.out.println(commaSepValueBuilder.toString());
						 */
					} else {
					}
				}
			});
		} else {
			btn.setVisibility(View.GONE);
		}
	}

	public void showDialog(final Button btn, String tit) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				SeatSelection.this);

		// set dialog message
		String title = "Do you want to book ticket no " + tit + " ?";
		alertDialogBuilder
				.setMessage(title)
				.setCancelable(false)
				.setPositiveButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								flog1 = 0;

								btn.setBackgroundResource(R.drawable.fou);
								dialog.cancel();
							}
						})
				.setNegativeButton("Book",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								btn.setBackgroundResource(R.drawable.three);
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	private void showAreaDialog() {

		final Dialog dialog1 = new Dialog(SeatSelection.this);

		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog1.setContentView(R.layout.dialoglayout);

		dialog1.setCancelable(true);
		dialog1.setCanceledOnTouchOutside(true);

		dialog1.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface dialog) {

			}
		});

		dialog1.setOnDismissListener(new OnDismissListener() {

			public void onDismiss(DialogInterface dialog) {

			}
		});

		// Prepare ListView in dialog
		ListView dialog_ListView = (ListView) dialog1
				.findViewById(R.id.dialoglist);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				SeatSelection.this, R.layout.spinner_item, locationname);

		dialog_ListView.setAdapter(adapter);
		dialog_ListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long ids) {

				chose_boarding.setText(locationname.get(position));
				PackageListing.boarding_pont = locationname.get(position);
				dialog1.dismiss();
			}
		});
		dialog1.show();
	}

	private void showAreaDialog(final ArrayList<String> locationname2,
			final TextView txt) {

		final Dialog dialog1 = new Dialog(SeatSelection.this);

		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog1.setContentView(R.layout.dialoglayout);

		dialog1.setCancelable(true);
		dialog1.setCanceledOnTouchOutside(true);

		dialog1.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface dialog) {

			}
		});

		dialog1.setOnDismissListener(new OnDismissListener() {

			public void onDismiss(DialogInterface dialog) {

			}
		});

		// Prepare ListView in dialog
		ListView dialog_ListView = (ListView) dialog1
				.findViewById(R.id.dialoglist);
		TextView title = (TextView) dialog1
				.findViewById(R.id.title);
		title.setTypeface(Constants.ProximaNova_Regular);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				SeatSelection.this, R.layout.spinner_item, locationname2);

		dialog_ListView.setAdapter(adapter);
		dialog_ListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long ids) {
				
				
				
				if(boardingFlag == 1){
					StrBoardingPoint=locationname2.get(position);
					StrBoardingPointCode=locationCode.get(position);
					departureTime = dist.get(position);
					txt.setText(StrBoardingPoint);
				}else{
					StrBoardingReturnPoint=locationname2.get(position);
					departureReturnTime = dist1.get(position);
					StrBoardingReturnPointCode=locationCode1.get(position);
					txt.setText(StrBoardingReturnPoint);
				}
				
				dialog1.dismiss();
			}
		});
		dialog1.show();
	}

	class GetBoardIngonward extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			 System.out.println("url is"+Constants.Base_url1
						+ "boardingonwardjourney.jsp?sc="
						+ PackageListing.service_Code);
			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "boardingonwardjourney.jsp?sc="
					+ PackageListing.service_Code);
			// return "result";
		}

		@Override
		protected void onProgressUpdate(Integer... a) {
			super.onProgressUpdate(a);
			System.out.println("time" + a);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
//			Log.e("Result : ", result);
			System.out.println("result"+result);
			if (null == result || result.length() == 0) {

			} else {

				try {
					locationname.clear();
					locationCode.clear();
					dist.clear();
					onwardJourney.clear();
					// JSONArray json = new JSONArray(result);
					JSONArray json = new JSONArray(result);
System.out.println("boardingresult"+result);
					// [{"locationCode":"31","coalesce":"CRO BASHEERBAGH, Ph: 9848540371","dist":"14:00","onwardJourney":"B"}]
					for (int i = 0; i < json.length(); i++) {
						JSONObject e = json.getJSONObject(i);
						locationCode.add(e.getString("locationCode"));
						locationname.add(e.getString("coalesce"));
						dist.add(e.getString("dist"));
						onwardJourney.add(e.getString("onwardJourney"));
					}

				} catch (Exception e) {
					Log.e("E ", "" + e);
				}

			}
		}
	}

	class GetBoardingReturnjourney extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			 System.out.println("url is"+Constants.Base_url1
						+ "boardingreturnjourney.jsp?sc="
						+ PackageListing.service_Code);
			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "boardingreturnjourney.jsp?sc="
					+ PackageListing.service_Code);
			// return "result";
		}

		@Override
		protected void onProgressUpdate(Integer... a) {
			super.onProgressUpdate(a);
			System.out.println("time" + a);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
//			Log.e("Result : ", result);
			System.out.println("result"+result);
			if (null == result || result.length() == 0) {

			} else {

				try {
					locationname1.clear();
					locationCode1.clear();
					dist1.clear();
					onwardJourney1.clear();
					// JSONArray json = new JSONArray(result);

					JSONArray json = new JSONArray(result);
					System.out.println("boardingreturnresult"+result);
					// [{"locationCode":"181","coalesce":"GOA","dist":"06:00","onwardJourney":"A"}]
					for (int i = 0; i < json.length(); i++) {
						JSONObject e = json.getJSONObject(i);
						locationCode1.add(e.getString("locationCode"));
						locationname1.add(e.getString("coalesce"));
						dist1.add(e.getString("dist"));
						onwardJourney1.add(e.getString("onwardJourney"));
					}

				} catch (Exception e) {
				}

			}
		}
	}

	public String loadJSONFromAsset1(String str) {
		String json = null;
		try {
			InputStream is = getApplicationContext().getAssets().open(str);
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
			// startActivity(new Intent(SeatSelection.this,
			// Package_Booking.class));
			finish();

		}
		return true;
	}
}
