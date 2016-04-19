package com.telanganatourism.android.phase2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

public class HotelBookingSuccess extends Activity {

	TextView dateTxt, dateTxt1, monthTxt, monthTxt1, dayTxt, dayTxt1,
			adultValue, childValue, nightStayValTxt, noOfpersons,
			selectRoomType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hotel_booking_success);

		Button backBtn = (Button) findViewById(R.id.backHomeBtn);

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

		// Setting the Values
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

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SplashScreen.exitAppFlag = "2";
//				startActivity(new Intent(HotelBookingSuccess.this,
//						MainActivity.class));
//				finish();
				 Intent intent = new Intent(HotelBookingSuccess.this, MainActivity.class);
				    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    startActivity(intent);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(HotelBookingSuccess.this,
					PaymentScreen.class));
			finish();
		}
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(HotelBookingSuccess.this,
				"6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(HotelBookingSuccess.this);
	}
}
