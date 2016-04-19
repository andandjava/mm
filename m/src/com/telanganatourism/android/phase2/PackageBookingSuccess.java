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

public class PackageBookingSuccess extends Activity {
	
	TextView package_title_txt, package_type_txt, selectedDate, boarding_point_txt, adultValue, childValue, noOfpersons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.package_booking_success);
		
		package_title_txt = (TextView) findViewById(R.id.package_title_txt);
		package_type_txt = (TextView) findViewById(R.id.package_type_txt);
		
		boarding_point_txt = (TextView) findViewById(R.id.boarding_point_txt);
		
		adultValue = (TextView) findViewById(R.id.adult_val);
		childValue = (TextView) findViewById(R.id.child_val);
		
		noOfpersons = (TextView) findViewById(R.id.roomsResult_val);
		
		package_title_txt.setText(PackageListing.pack_title);
		package_type_txt.setText(PackageListing.pack_type);
		
		boarding_point_txt.setText(PackageListing.boarding_pont);
		
		adultValue.setText(PackageListing.pack_no_of_adults);
		childValue.setText(PackageListing.pack_no_of_child);
		
		noOfpersons.setText(PackageListing.pack_total_no_of_persons);
		
		
		Button backBtn = (Button)findViewById(R.id.backHomeBtn);
		
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SplashScreen.exitAppFlag = "2";
				  Intent intent = new Intent(PackageBookingSuccess.this, MainActivity.class);
				    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    startActivity(intent);
//				startActivity(new Intent(PackageBookingSuccess.this, MainActivity.class));
//				finish();
			}
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(PackageBookingSuccess.this,
					PaymentScreen.class));
			finish();

		}
		return true;
	}
}
