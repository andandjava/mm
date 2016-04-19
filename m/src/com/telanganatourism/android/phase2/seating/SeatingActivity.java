package com.telanganatourism.android.phase2.seating;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.telanganatourism.android.phase2.R;

public class SeatingActivity extends Activity {

	Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11,
			btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20,
			btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28, btn29,
			btn30, btn31, btn32, btn33, btn34, btn35, btn36, btn37, btn38,
			btn39, btn40, btn41, btn42, btn43, btn44, btn45, btn46, btn47,
			btn48, btn49, btn50, btn_continue;
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
	TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, chose_boarding;
	LayoutInflater layoutInflater;
	View view;
	LinearLayout lay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.seatting);

		/** Adding boarding points */

		locationname.add("Sr Nagar");
		locationname.add("Ameerpet");
		locationname.add("Panjagutta");
		locationname.add("Lakdikapool");
		locationname.add("Mehdipatnam");
		locationname.add("Kondaur");

		lay = (LinearLayout) findViewById(R.id.lay);
		layoutInflater = getLayoutInflater();
		lay.removeAllViews();

		/** Adding forms for ticket booking */

		for (int i = 1; i <= 3; i++) {

			view = layoutInflater.inflate(R.layout.person_details, lay, false);
			lay.addView(view);
		}
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

		t4 = (TextView) findViewById(R.id.textView11);
		t5 = (TextView) findViewById(R.id.textView21);
		t6 = (TextView) findViewById(R.id.textView31);
		t7 = (TextView) findViewById(R.id.textView41);
		chose_boarding = (TextView) findViewById(R.id.chose_boarding);

		chose_boarding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAreaDialog();
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

		btn_continue = (Button) findViewById(R.id.btn_continue);

		btn_continue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(SeatingActivity.this);

				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// Include dialog.xml file
				dialog.setContentView(R.layout.dialog);

				TextView titleTxt = (TextView) dialog
						.findViewById(R.id.textView1);

				TextView msgTxt = (TextView) dialog
						.findViewById(R.id.textView2);

				Button yesBtn = (Button) dialog.findViewById(R.id.btn_yes);

				Button noBtn = (Button) dialog.findViewById(R.id.btn_no);

				View view = (View) dialog.findViewById(R.id.view1);

				titleTxt.setVisibility(View.GONE);
				view.setVisibility(View.GONE);

				msgTxt.setText("Do you want to confirm");

				yesBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

				noBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

				dialog.show();
			}
		});

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
		;

		slot_booked();

		t4.setText(Html
				.fromHtml("<html><body><font color=\"black\">Seat(s):&nbsp:</font><font size=\"3\" color=\"#FF0000\">33</font>"
						+ "</body></html>"));

		t5.setText(Html
				.fromHtml("<html><body><font color=\"black\">Base Fare&nbsp:</font><font size=\"3\" color=\"#FF0000\"> 660</font>"
						+ "</body></html>"));
		t6.setText(Html
				.fromHtml("<html><body><font color=\"black\">Service Tax&nbsp:</font><font size=\"3\" color=\"#FF0000\"> 30</font>"
						+ "</body></html>"));
		t7.setText(Html
				.fromHtml("<html><body><font color=\"black\">Total Fare&nbsp:</font><font size=\"3\" color=\"#FF0000\"></font>"
						+ "</body></html>"));
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
				SeatingActivity.this);

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

		final Dialog dialog1 = new Dialog(SeatingActivity.this);

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
				SeatingActivity.this, R.layout.spinner_item, locationname);

		dialog_ListView.setAdapter(adapter);
		dialog_ListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long ids) {

				dialog1.dismiss();
			}
		});
		dialog1.show();
	}

}
