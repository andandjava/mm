package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telanganatourism.android.phase2.date.DatePickerDialog;
import com.telanganatourism.android.phase2.date.DatePickerDialog.OnDateSetListener;
import com.telanganatourism.android.phase2.util.Utility;

public class Package_Booking extends Activity {

	private int year;
	private int month;
	private int day;

	// static final int DATE_PICKER_ID = 1111;

	TextView package_title_txt, package_type_txt, adultValue, childValue,
			noOfpersons, selectedDate, adultTariffTxt, childTariffTxt,
			singleTariffTxt, infantTariff, package_types_dropDown,
			grandTotal_ResultTxt;
	Button adult_minus_btn, adult_plus_btn, child_minus_btn, child_plus_btn;

	TextView titleTxt, textView8, textView9;

	public static String adultTariff, childTariff;
	double adultTariff1 = 0.0, childTariff1 = 0.0;
	public static String singleTariff, infantTarrif;
	public String coalesce;
	public static String packTypeCode;

	ArrayList<String> packageTypeCode = new ArrayList<String>();

	ArrayList<String> packageTypeName = new ArrayList<String>();

	String singleFareValue;

	// Utility utility;

	String validateDay;

	public static String str_total_price, str_date, no_of_people, totAdultAmt,
			totChildAmt;

	public static String seatNos1 = "", seatNos2 = "";
	public static int seatSelectionDisabe = 0;

	private final Calendar mCalendar = Calendar.getInstance();

	String pattern1 = "#,##,###.##";
	DecimalFormat decimalFormat;

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
	// Button buttonDialog, buttonDialog2;
	TextView title;

	int cin_day, cin_mon, cin_year;
	static int cout_day = 0, cout_mon = 0, cout_year = 0;
	static int inc_day = 0, inc_mon = 0, inc_year = 0;
	String selected_day, selected_mon, selected_year;
	static String title_year;
	boolean flag = false;
	SimpleDateFormat df;
	String today_date;
	String checkin_str;
	String dateStr;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.booking_package);

		decimalFormat = new DecimalFormat(pattern1);
		decimalFormat.setGroupingSize(3);
		decimalFormat.setDecimalSeparatorAlwaysShown(true);
		decimalFormat.setMinimumFractionDigits(2);

		// utility = new Utility(Package_Booking.this);

		Constants.day_string = "";
		Constants.day_number = "";

		if (PackageListing.packagesAvailability.contains("SUN")) {
			Constants.day_string = Constants.day_string + ",SUN";
			Constants.day_number = Constants.day_number + ",0";
		}
		if (PackageListing.packagesAvailability.contains("MON")) {
			Constants.day_string = Constants.day_string + ",MON";
			Constants.day_number = Constants.day_number + ",1";
		}
		if (PackageListing.packagesAvailability.contains("TUE")) {
			Constants.day_string = Constants.day_string + ",TUE";
			Constants.day_number = Constants.day_number + ",2";
		}
		if (PackageListing.packagesAvailability.contains("WED")) {
			Constants.day_string = Constants.day_string + ",WED";
			Constants.day_number = Constants.day_number + ",3";
		}
		if (PackageListing.packagesAvailability.contains("THU")) {
			Constants.day_string = Constants.day_string + ",THU";
			Constants.day_number = Constants.day_number + ",4";
		}
		if (PackageListing.packagesAvailability.contains("FRI")) {
			Constants.day_string = Constants.day_string + ",FRI";
			Constants.day_number = Constants.day_number + ",5";
		}
		if (PackageListing.packagesAvailability.contains("SAT")) {
			Constants.day_string = Constants.day_string + ",SAT";
			Constants.day_number = Constants.day_number + ",6";
		}

		Constants.day_string = Constants.day_string.replaceFirst(",", "");
		Constants.day_number = Constants.day_number.replaceFirst(",", "");

		
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

		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		Date today = c.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		Constants.ProximaNova_Bold = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "ProximaNova-Bold.otf");

		titleTxt = (TextView) findViewById(R.id.event_title);
		textView8 = (TextView) findViewById(R.id.textView8);
		textView9 = (TextView) findViewById(R.id.textView9);

		titleTxt.setTypeface(Constants.ProximaNova_Regular);
		textView8.setTypeface(Constants.ProximaNova_Regular);
		textView9.setTypeface(Constants.ProximaNova_Regular);

		titleTxt.setText(DetailScreen1.tit);

		package_title_txt = (TextView) findViewById(R.id.package_title_txt);
		package_type_txt = (TextView) findViewById(R.id.package_type_txt);

		selectedDate = (TextView) findViewById(R.id.selectDateBtn);

		adult_minus_btn = (Button) findViewById(R.id.adult_minus_btn);
		adult_plus_btn = (Button) findViewById(R.id.adult_plus_btn);

		child_minus_btn = (Button) findViewById(R.id.child_minus_btn);
		child_plus_btn = (Button) findViewById(R.id.child_plus_btn);

		adultValue = (TextView) findViewById(R.id.adult_val);
		childValue = (TextView) findViewById(R.id.child_val);

		adultTariffTxt = (TextView) findViewById(R.id.adult_Tariff);
		childTariffTxt = (TextView) findViewById(R.id.child_Tariff);
		singleTariffTxt = (TextView) findViewById(R.id.single_Tariff);
		infantTariff = (TextView) findViewById(R.id.infant_Tariff);

		noOfpersons = (TextView) findViewById(R.id.no_of_people_Val);

		grandTotal_ResultTxt = (TextView) findViewById(R.id.grandTotal_Result);

		package_title_txt.setText(PackageListing.pack_title);
		package_type_txt.setText(PackageListing.pack_type);

		package_types_dropDown = (TextView) findViewById(R.id.package_types);

		TextView durationTxt = (TextView) findViewById(R.id.durationTxt);
		TextView byRoadTxt = (TextView) findViewById(R.id.byRoadTxt);
		TextView noOfPeopleTxt = (TextView) findViewById(R.id.noOfPeopleTxt);
		TextView atxt = (TextView) findViewById(R.id.atxt);
		TextView cTxt = (TextView) findViewById(R.id.cTxt);

		Button viewSeats = (Button) findViewById(R.id.button1);

		package_title_txt.setTypeface(Constants.ProximaNova_Bold);
		package_type_txt.setTypeface(Constants.ProximaNova_Bold);
		selectedDate.setTypeface(Constants.ProximaNova_Regular);
		adultValue.setTypeface(Constants.ProximaNova_Regular);
		childValue.setTypeface(Constants.ProximaNova_Regular);
		adultTariffTxt.setTypeface(Constants.ProximaNova_Regular);
		childTariffTxt.setTypeface(Constants.ProximaNova_Regular);
		singleTariffTxt.setTypeface(Constants.ProximaNova_Regular);
		noOfpersons.setTypeface(Constants.ProximaNova_Regular);
		package_types_dropDown.setTypeface(Constants.ProximaNova_Regular);
		grandTotal_ResultTxt.setTypeface(Constants.ProximaNova_Regular);
		infantTariff.setTypeface(Constants.ProximaNova_Regular);

		durationTxt.setTypeface(Constants.ProximaNova_Regular);
		byRoadTxt.setTypeface(Constants.ProximaNova_Regular);
		noOfPeopleTxt.setTypeface(Constants.ProximaNova_Regular);
		atxt.setTypeface(Constants.ProximaNova_Regular);
		cTxt.setTypeface(Constants.ProximaNova_Regular);

		viewSeats.setTypeface(Constants.ProximaNova_Bold);

		durationTxt.setText("Duration : " + PackageListing.packagePeriod
				+ " Days");

		if (Utility.checkInternetConnection(Package_Booking.this)) {
			new GetPackageTypes().execute();

		} else {
			Utility.showAlertNoInternetService(Package_Booking.this);
		}

		package_types_dropDown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// showAreaDialog();

				showAreaDialog();
			}
		});

		selectedDate.setText(formatter.format(today));

		dateStr = formatter.format(today);

		SimpleDateFormat dayValidate = new SimpleDateFormat("E, MMM dd yyyy");

		Date date1 = new Date();

		String[] spliteStr = dayValidate.format(date1).split(",");

		validateDay = spliteStr[0].toUpperCase();

		// selectedDate.setOnClickListener(new OnClickListener() {
		//
		// String tag;
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// // showDialog(DATE_PICKER_ID);
		// Constants.date_flg = 0;
		// datePickerDialog.show(getFragmentManager(), tag);
		// ;
		// }
		// });

		selectedDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// flag = false;
				// String btn_text = buttonDialog.getText().toString();
				// dateTxt_in.getText().toString();
				Constants.package_date_flag = 1;
				parseDate(dateStr);

				// Toast.makeText(context, "Date is:"+cur_date, 1).show();
				setUp(cin_year, cin_mon, cin_day);

			}
		});

		adult_plus_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (package_types_dropDown.getText().toString()
						.equalsIgnoreCase("Select package type")) {
					AlertDialog.Builder altDialog = new AlertDialog.Builder(
							Package_Booking.this);
					altDialog.setMessage("Please select package type"); // here
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

					if (PackageListing.personsVal >= 6) {
						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								Package_Booking.this);
						altDialog
								.setMessage("Sorry!! you cannot book more than 6 seats"); // here
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
						PackageListing.adultVal = PackageListing.adultVal + 1;

						PackageListing.personsVal = PackageListing.adultVal
								+ PackageListing.childVal;

						adultValue.setText("" + PackageListing.adultVal);

						noOfpersons.setText("" + PackageListing.personsVal);

						// // if(personsVal == 1){
						// if(utility.IsNetConnected(Package_Booking.this)){
						// new FareService().execute();
						// }else{
						// utility.showAlertNoInternetService(Package_Booking.this);
						// }

						if (PackageListing.personsVal == 1) {
							// grandTotal_ResultTxt.setText("0");
							if (TextUtils.isEmpty(singleTariff)) {

							} else {
								double f = (Double.parseDouble(adultTariff) * PackageListing.adultVal)
										+ Double.parseDouble(singleTariff);
								// String.format( "%.2f", f );

								// grandTotal_ResultTxt.setText("" +
								// String.format( "%.2f", f ));////
								grandTotal_ResultTxt.setText(""
										+ decimalFormat.format(f));

								totAdultAmt = String.valueOf(String.format(
										"%.2f",
										Double.parseDouble(adultTariff)
												* PackageListing.adultVal
												+ Double.parseDouble(singleTariff)));

							}

						} else {

							if (TextUtils.isEmpty(singleTariff)) {

							} else {
								// grandTotal_ResultTxt.setText("0");
								double f1 = Double.parseDouble(adultTariff)
										* PackageListing.adultVal;
								totAdultAmt = String.valueOf(String.format(
										"%.2f", Double.parseDouble(adultTariff)
												* PackageListing.adultVal));
								double f2 = Double.parseDouble(childTariff)
										* PackageListing.childVal;
								totChildAmt = String.valueOf(String.format(
										"%.2f", Double.parseDouble(childTariff)
												* PackageListing.childVal));
								double f = f1 + f2;

								// grandTotal_ResultTxt.setText("" +
								// String.format( "%.2f", f ));////
								grandTotal_ResultTxt.setText(""
										+ decimalFormat.format(f));
							}

						}
					}

					// }else{
					// grandTotal_ResultTxt.setText(""+Float.parseFloat(adultTariff)
					// * personsVal);
					// }
				}

			}
		});

		adult_minus_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (package_types_dropDown.getText().toString()
						.equalsIgnoreCase("Select package type")) {
					AlertDialog.Builder altDialog = new AlertDialog.Builder(
							Package_Booking.this);
					altDialog.setMessage("Please select package type"); // here
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
					if (PackageListing.adultVal == 0) {

					} else {
						PackageListing.adultVal = PackageListing.adultVal - 1;
						adultValue.setText("" + PackageListing.adultVal);
					}

					PackageListing.personsVal = PackageListing.adultVal
							+ PackageListing.childVal;

					noOfpersons.setText("" + PackageListing.personsVal);

					// if(utility.IsNetConnected(Package_Booking.this)){
					// new FareService().execute();
					// }else{
					// utility.showAlertNoInternetService(Package_Booking.this);
					// }

					if (PackageListing.personsVal == 1) {
						if (!TextUtils.isEmpty(singleTariff)) {
							// grandTotal_ResultTxt.setText("0");
							Double f = (Double.parseDouble(adultTariff) * PackageListing.adultVal)
									+ Double.parseDouble(singleTariff);
							// grandTotal_ResultTxt.setText("" + String.format(
							// "%.2f", f ));////
							grandTotal_ResultTxt.setText(""
									+ decimalFormat.format(f));
						}

					} else {
						if (!TextUtils.isEmpty(singleTariff)) {
							// grandTotal_ResultTxt.setText("0");

							Double f1 = Double.parseDouble(adultTariff)
									* PackageListing.adultVal;
							totAdultAmt = String.valueOf(String.format("%.2f",
									Double.parseDouble(adultTariff)
											* PackageListing.adultVal));
							Double f2 = Double.parseDouble(childTariff)
									* PackageListing.childVal;
							totChildAmt = String.valueOf(String.format("%.2f",
									Double.parseDouble(childTariff)
											* PackageListing.childVal));

							double f = f1 + f2;

							// grandTotal_ResultTxt.setText("" + String.format(
							// "%.2f", f ));
							grandTotal_ResultTxt.setText(""
									+ decimalFormat.format(f));
						}

					}
				}

			}
		});

		child_plus_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (package_types_dropDown.getText().toString()
						.equalsIgnoreCase("Select package type")) {
					AlertDialog.Builder altDialog = new AlertDialog.Builder(
							Package_Booking.this);
					altDialog.setMessage("Please select package type"); // here
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

					if (PackageListing.adultVal == 0) {
						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								Package_Booking.this);
						altDialog.setMessage("Please set adults first"); // here
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

						if (PackageListing.personsVal >= 6) {
							AlertDialog.Builder altDialog = new AlertDialog.Builder(
									Package_Booking.this);
							altDialog
									.setMessage("Sorry!! you cannot book more than 6 seats"); // here
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
							PackageListing.childVal = PackageListing.childVal + 1;
							childValue.setText("" + PackageListing.childVal);

							PackageListing.personsVal = PackageListing.adultVal
									+ PackageListing.childVal;

							noOfpersons.setText("" + PackageListing.personsVal);

							// if(utility.IsNetConnected(Package_Booking.this)){
							// new FareService().execute();
							// }else{
							// utility.showAlertNoInternetService(Package_Booking.this);
							// }

							if (PackageListing.personsVal == 1) {
								if (!TextUtils.isEmpty(singleTariff)) {
									// grandTotal_ResultTxt.setText("0");
									Double f = (Double.parseDouble(adultTariff) * PackageListing.adultVal)
											+ Double.parseDouble(singleTariff);
									// grandTotal_ResultTxt.setText("" +
									// String.format( "%.2f", f ));
									grandTotal_ResultTxt.setText(""
											+ decimalFormat.format(f));
								}
							} else {

								if (!TextUtils.isEmpty(singleTariff)) {
									// grandTotal_ResultTxt.setText("0");
									double f1 = Double.parseDouble(adultTariff)
											* PackageListing.adultVal;
									totAdultAmt = String.valueOf(String.format(
											"%.2f",
											Double.parseDouble(adultTariff)
													* PackageListing.adultVal));
									double f2 = Double.parseDouble(childTariff)
											* PackageListing.childVal;
									totChildAmt = String.valueOf(String.format(
											"%.2f",
											Double.parseDouble(childTariff)
													* PackageListing.childVal));
									double f = f1 + f2;

									// grandTotal_ResultTxt.setText("" +
									// String.format( "%.2f", f ));
									grandTotal_ResultTxt.setText(""
											+ decimalFormat.format(f));
								}

							}
						}

					}

				}

			}
		});

		child_minus_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (package_types_dropDown.getText().toString()
						.equalsIgnoreCase("Select package type")) {
					AlertDialog.Builder altDialog = new AlertDialog.Builder(
							Package_Booking.this);
					altDialog.setMessage("Please select package type"); // here
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
					if (PackageListing.childVal == 0) {

					} else {
						PackageListing.childVal = PackageListing.childVal - 1;
						childValue.setText("" + PackageListing.childVal);
					}

					PackageListing.personsVal = PackageListing.adultVal
							+ PackageListing.childVal;

					noOfpersons.setText("" + PackageListing.personsVal);

					// if(utility.IsNetConnected(Package_Booking.this)){
					// new FareService().execute();
					// }else{
					// utility.showAlertNoInternetService(Package_Booking.this);
					// }

					if (PackageListing.personsVal == 1) {
						if (!TextUtils.isEmpty(singleTariff)) {
							// grandTotal_ResultTxt.setText("0");
							double f = (Double.parseDouble(adultTariff) * PackageListing.adultVal)
									+ Double.parseDouble(singleTariff);
							grandTotal_ResultTxt.setText(""
									+ String.format("%.2f", f));
						}
					} else {
						if (!TextUtils.isEmpty(singleTariff)) {
							// grandTotal_ResultTxt.setText("0");
							double f1 = Double.parseDouble(adultTariff)
									* PackageListing.adultVal;
							totAdultAmt = String.valueOf(String.format("%.2f",
									Double.parseDouble(adultTariff)
											* PackageListing.adultVal));
							double f2 = Double.parseDouble(childTariff)
									* PackageListing.childVal;
							totChildAmt = String.valueOf(String.format("%.2f",
									Double.parseDouble(childTariff)
											* PackageListing.childVal));
							double f = f1 + f2;

							// grandTotal_ResultTxt.setText("" + String.format(
							// "%.2f", f ));
							grandTotal_ResultTxt.setText(""
									+ decimalFormat.format(f));
						}
					}
				}

			}
		});

		RelativeLayout menu_layout = (RelativeLayout) findViewById(R.id.menuLayout);

		menu_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();
			}
		});

		viewSeats.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				PackageListing.pack_selected_date = selectedDate.getText()
						.toString();
				PackageListing.pack_no_of_adults = String
						.valueOf(PackageListing.adultVal);
				PackageListing.pack_no_of_child = String
						.valueOf(PackageListing.childVal);

				PackageListing.pack_total_no_of_persons = String
						.valueOf(PackageListing.adultVal
								+ PackageListing.childVal);

				if (Utility.checkInternetConnection(Package_Booking.this)) {

					if (package_types_dropDown.getText().toString()
							.equalsIgnoreCase("Select package type")) {
						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								Package_Booking.this);
						altDialog.setMessage("Please select package type"); // here
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

						if (PackageListing.packagesAvailability
								.contains(validateDay)) {
							if (PackageListing.adultVal == 0) {
								AlertDialog.Builder altDialog = new AlertDialog.Builder(
										Package_Booking.this);
								altDialog
										.setMessage("Please select number of adults/children"); // here
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
								str_total_price = grandTotal_ResultTxt
										.getText().toString();// //
								no_of_people = noOfpersons.getText().toString();
								str_date = selectedDate.getText().toString();
								seatNos1 = "";
								seatNos2 = "";
								seatSelectionDisabe = 0;
								startActivity(new Intent(Package_Booking.this,
										SeatSelection.class));
							}
						} else {
							// Toast.makeText(Package_Booking.this,
							// "Service is not available on that day",
							// Toast.LENGTH_LONG).show();
							AlertDialog.Builder altDialog = new AlertDialog.Builder(
									Package_Booking.this);
							altDialog
									.setMessage("This package is only available on "
											+ PackageListing.packagesAvailability); // here
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
						}

					}

				} else {
					Utility.showAlertNoInternetService(Package_Booking.this);
				}

			}
		});

	}

	// @Override
	// protected Dialog onCreateDialog(int id) {
	// switch (id) {
	// case DATE_PICKER_ID:
	//
	// // open datepicker dialog.
	// // set date picker for current date
	// // add pickerListener listner to date picker
	// // return new DatePickerDialog(this, pickerListener, year, month, day);
	//
	//
	// DatePickerDialog _date = new DatePickerDialog(this, pickerListener,
	// year,month,
	// day){
	// @Override
	// public void onDateChanged(DatePicker view, int year1, int monthOfYear,
	// int dayOfMonth)
	// {
	// if (year1 < year)
	// view.updateDate(year, month, day);
	//
	// if (monthOfYear < month && year1 == year)
	// view.updateDate(year, month, day);
	//
	// if (dayOfMonth < day && year1 == year && monthOfYear == month)
	// view.updateDate(year, month, day);
	//
	// }
	// };
	// return _date;
	//
	// }
	// return null;
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// startActivity(new Intent(Package_Booking.this,
			// PackageListing.class));
			finish();

		}
		return true;
	}

	// private DatePickerDialog.OnDateSetListener pickerListener = new
	// DatePickerDialog.OnDateSetListener() {
	//
	// // when dialog box is closed, below method will be called.
	// @SuppressLint("SimpleDateFormat")
	// @Override
	// public void onDateSet(DatePicker view, int selectedYear,
	// int selectedMonth, int selectedDay) {
	//
	// year = selectedYear;
	// month = selectedMonth;
	// day = selectedDay;
	//
	// SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	//
	// @SuppressWarnings("deprecation")
	// Date date = new Date(year - 1900, month, day);
	//
	// selectedDate.setText(formatter.format(date));
	//
	//
	// }
	// };

	final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
			new OnDateSetListener() {

				public void onDateSet(DatePickerDialog datePickerDialog,
						int year, int month, int day) {

					// tvDisplayDate.setText(
					// new StringBuilder().append(pad(day))
					// .append(" ").append(pad(month+1)).append(" ").append(pad(year)));
					//
					// tvDisplayDate.setTextColor(getResources().getColor(android.R.color.holo_blue_light));

					// year = selectedYear;
					// month = selectedMonth;
					// day = selectedDay;

					SimpleDateFormat dayValidate = new SimpleDateFormat(
							"E, MMM dd yyyy");

					Date date1 = new Date(year - 1900, month, day);

					String[] spliteStr = dayValidate.format(date1).split(",");

					validateDay = spliteStr[0].toUpperCase();

					if (PackageListing.packagesAvailability
							.contains(validateDay)) {
						SimpleDateFormat formatter = new SimpleDateFormat(
								"dd-MM-yyyy");

						@SuppressWarnings("deprecation")
						Date date = new Date(year - 1900, month, day);

						selectedDate.setText(formatter.format(date));
					} else {
						// Toast.makeText(Package_Booking.this,
						// "Service is not available on that day",
						// Toast.LENGTH_LONG).show();
						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								Package_Booking.this);
						altDialog
								.setMessage("This package is only available on "
										+ PackageListing.packagesAvailability); // here
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
					}

					// str_date = formatter.format(date);

				}

			}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
			mCalendar.get(Calendar.DAY_OF_MONTH));

	class FareService extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new ProgressDialog(Package_Booking.this);
			// pDialog.setMessage("Loading...");
			// pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {

			System.out.println("url is_for_pakagetypes" + Constants.Base_url1
					+ "fare.jsp?sc=" + PackageListing.service_Code + "&tcc="
					+ PackageListing.city_Code + "&dt=" + Constants.currentDate
					+ "&ptc=" + packTypeCode);

			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "fare.jsp?sc=" + PackageListing.service_Code + "&tcc="
					+ PackageListing.city_Code + "&dt=" + Constants.currentDate
					+ "&ptc=" + packTypeCode);

			// return "result";

		}

		@Override
		protected void onProgressUpdate(Integer... a) {
			super.onProgressUpdate(a);
			// System.out.println("time" + a);

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			// if (null != pDialog && pDialog.isShowing()) {
			// pDialog.dismiss();
			// }

			if (null == result || result.length() == 0) {

			} else {

				try {
					JSONArray json = new JSONArray(result);

					for (int i = 0; i < json.length(); i++) {
						JSONObject e = json.getJSONObject(i);

						adultTariff = e.getString("adultTariff");
						childTariff = e.getString("childTariff");
						singleTariff = e.getString("single");
						infantTarrif = e.getString("infant");
					}
					adultTariff1 = Double.parseDouble(adultTariff);
					childTariff1 = Double.parseDouble(childTariff);

					adultTariffTxt.setText("Adult Fare : "
							+ decimalFormat.format(adultTariff1));
					childTariffTxt.setText("Child Fare : "
							+ decimalFormat.format(childTariff1));
					singleTariffTxt.setText("Single Fare : " + singleTariff);
					infantTariff.setText("Infant Fare : " + infantTarrif);

				} catch (Exception e) {
					// TODO: handle exception
					Log.e("Tag", "" + e);
				}

				// if (pDialog.isShowing()) {
				// pDialog.dismiss();
				// }

			}
		}
	}

	// New Calender Picker Function

	public void setUp(int year, int mon, int day) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Package_Booking.this);
		builder.setCancelable(true);
		builder.setNegativeButton("Ignore",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// finish the application
						Package_Booking.this.finish();
					}
				});

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.calendar);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// rLayout = (LinearLayout) dialog.findViewById(R.id.text);
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
		title.setText(android.text.format.DateFormat.format("MMMM yyyy",
				gMonth1));
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

				cin_year = StringToInt(selected_year);
				cin_mon = StringToInt(selected_mon);
				cin_day = StringToInt(selected_day);
				// buttonDialog.setText("" + cin_year + "-" + cin_mon + "-"
				// + cin_day);
				checkin_str = "" + cin_year + "-" + cin_mon + "-" + cin_day;
				// dateTxt.setText("" + cin_day);
				// monthTxt.setText("" + txt_month[cin_mon - 1] + "'"
				// + subString(cin_year));
				// String dayOfTheWeek = (String)
				// android.text.format.DateFormat.format("EEE", new
				// Date(cin_year,cin_mon,cin_day));
				// dayTxt_in.setText(dayOfTheWeek);
				
				SimpleDateFormat dayValidate = new SimpleDateFormat(
						"E, MMM dd yyyy");

				Date date1 = new Date(cin_year - 1900, cin_mon - 1, cin_day);

				String[] spliteStr = dayValidate.format(date1).split(",");

				validateDay = spliteStr[0].toUpperCase();

				if (PackageListing.packagesAvailability
						.contains(validateDay)) {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd-MM-yyyy");

					@SuppressWarnings("deprecation")
					Date date = new Date(cin_year, cin_mon, cin_day);

					selectedDate.setText(formatter.format(date));
				} else {
					// Toast.makeText(Package_Booking.this,
					// "Service is not available on that day",
					// Toast.LENGTH_LONG).show();
					AlertDialog.Builder altDialog = new AlertDialog.Builder(
							Package_Booking.this);
					altDialog
							.setMessage("This package is only available on "
									+ PackageListing.packagesAvailability); // here
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
				}
//				SimpleDateFormat inFormat4 = new SimpleDateFormat("dd-MM-yyyy");
//				Date date4;
//				try {
//					date4 = inFormat4.parse("" + cin_day + "-" + cin_mon + "-"
//							+ cin_year);
//
//					// Constants.CheckInDateStr = inFormat4.format(date4);
//
//					selectedDate.setText(inFormat4.format(date4));
//
//					// SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
//					// String goal = outFormat.format(date4);
//					// dayTxt.setText(goal);
//
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

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

				// Date dout = new Date(cout_year, cout_mon, cout_day);
				// Date din = new Date(cin_year, cin_mon, cin_day);
				//
				// if (dout.compareTo(din) > 0) {
				//
				// } else {
				// incrementday();
				// cout_year = inc_year;
				// cout_mon = inc_mon;
				// cout_day = inc_day;
				// // buttonDialog2.setText("" + cout_year + "-" + cout_mon
				// // + "-" + cout_day);
				// checkout_str = "" + cout_year + "-" + cout_mon + "-"
				// + cout_day;
				//
				// dateTxt1.setText("" + cout_day);
				// monthTxt1.setText("" + txt_month[cout_mon - 1] + "'"
				// + subString(cout_year));
				// // String dayOfTheWeek1 = (String)
				// // android.text.format.DateFormat.format("EEE", new
				// // Date(cout_year,cout_mon,cout_day));
				// // dayTxt_out.setText(dayOfTheWeek1);
				// SimpleDateFormat inFormat2 = new SimpleDateFormat(
				// "dd-MM-yyyy");
				// Date date2;
				// try {
				// date2 = inFormat2.parse("" + cout_day + "-" + cout_mon
				// + "-" + cout_year);
				// SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
				// String goal = outFormat.format(date2);
				// dayTxt1.setText(goal);
				// // System.out.println("goal"+goal);
				// // Toast.makeText(this,""+goal, 1).show();
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }

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
						TextView rowTextView = new TextView(
								Package_Booking.this);
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

		title.setText(android.text.format.DateFormat.format("MMMM yyyy",
				gMonth1));
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
			// event = Utility.readCalendarEvent(CalendarView.this);
			// Log.d("=====Event====", event.toString());
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

	// class SingleFareService extends AsyncTask<String, Integer, String> {
	//
	// ProgressDialog pDialog;
	// JSONObject jObject;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	//
	// }
	//
	// @Override
	// protected String doInBackground(String... params) {
	//
	// // System.out.println("called orders");
	//
	// return ServiceCalls1
	// .getJSONString(Constants.Base_url1+"singlefare.jsp?sc="+PackageListing.service_Code+"&ptc="+packTypeCode);
	//
	// // return "result";
	//
	// }
	//
	// @Override
	// protected void onProgressUpdate(Integer... a) {
	// super.onProgressUpdate(a);
	// // System.out.println("time" + a);
	//
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// super.onPostExecute(result);
	//
	// Log.e("Result : ", result);
	// if (null == result || result.length() == 0) {
	//
	// } else {
	//
	// try {
	// JSONArray json = new JSONArray(result);
	//
	// for (int i = 0; i < json.length(); i++) {
	// JSONObject e = json.getJSONObject(i);
	//
	// Toast.makeText(getApplicationContext(),
	// e.getString("singleFare").toString(), 200).show();
	//
	// singleFareValue = e.get("singleFare").toString();
	//
	// grandTotal_ResultTxt.setText(""+(Float.parseFloat(adultTariff) *
	// personsVal)+Float.parseFloat(singleFareValue));
	//
	// }
	//
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// Log.e("Tag", "" + e);
	// }
	//
	// }
	// }
	// }

	private void showAreaDialog() {

		final Dialog dialog1 = new Dialog(Package_Booking.this);

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

		TextView title = (TextView) dialog1.findViewById(R.id.title);

		title.setTypeface(Constants.ProximaNova_Bold);

		title.setText("Package Type");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Package_Booking.this, R.layout.spinner_item, packageTypeName);

		dialog_ListView.setAdapter(adapter);
		dialog_ListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long ids) {
				package_types_dropDown.setText(packageTypeName.get(position));

				packTypeCode = packageTypeCode.get(position);

				if (Utility.checkInternetConnection(Package_Booking.this)) {
					new FareService().execute();
				} else {
					Utility.showAlertNoInternetService(Package_Booking.this);
				}

				dialog1.dismiss();
			}
		});
		dialog1.show();
	}

	class GetPackageTypes extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("url is" + Constants.Base_url1
					+ "packagetypes.jsp?sc=" + PackageListing.service_Code
					+ "&tcc=" + PackageListing.city_Code);
			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "packagetypes.jsp?sc=" + PackageListing.service_Code
					+ "&tcc=" + PackageListing.city_Code);
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
			Log.e("Result : ", result);
			if (null == result || result.length() == 0) {

			} else {

				try {

					// JSONArray json = new JSONArray(result);
					JSONArray json = new JSONArray(result);

					packageTypeCode.clear();
					packageTypeName.clear();
					// [{"locationCode":"31","coalesce":"CRO BASHEERBAGH, Ph: 9848540371","dist":"14:00","onwardJourney":"B"}]
					for (int i = 0; i < json.length(); i++) {
						JSONObject e = json.getJSONObject(i);

						packageTypeCode.add(e.getString("packageTypeCode"));
						packageTypeName.add(e.getString("packageTypeName"));

					}
					if (packageTypeName.size() > 1) {
						package_types_dropDown.setText(packageTypeName.get(0)
								.toString());

						package_types_dropDown.setVisibility(View.VISIBLE);
					} else {
						package_types_dropDown.setText(packageTypeName.get(0)
								.toString());

						package_types_dropDown.setVisibility(View.GONE);
						packTypeCode = packageTypeCode.get(0).toString();
					}

					if (Utility.checkInternetConnection(Package_Booking.this)) {
						new FareService().execute();
					} else {
						Utility.showAlertNoInternetService(Package_Booking.this);
					}

				} catch (Exception e) {
					Log.e("E ", "" + e);
				}

			}
		}
	}

	public String loadJSONFromAsset1() {
		String json = null;
		try {
			InputStream is = getApplicationContext().getAssets().open(
					"singleFare.json");
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

	public String loadJSONFromAsset2() {
		String json = null;
		try {
			InputStream is = getApplicationContext().getAssets().open(
					"fare.json");
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
}
