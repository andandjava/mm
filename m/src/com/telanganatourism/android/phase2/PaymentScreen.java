package com.telanganatourism.android.phase2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.flurry.android.FlurryAgent;

public class PaymentScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.payment_screen);
		
		Button paymentBtn = (Button)findViewById(R.id.button1);
		
		
		paymentBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(Constants.paymentNav.equalsIgnoreCase("2")){
					startActivity(new Intent(PaymentScreen.this, PackageBookingSuccess.class));
					finish();
				}else{
					startActivity(new Intent(PaymentScreen.this, HotelBookingSuccess.class));	
					finish();
				}
				
			}
		});

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(Constants.paymentNav.equalsIgnoreCase("2")){
				startActivity(new Intent(PaymentScreen.this, SeatSelection.class));
				finish();
			}else{
				startActivity(new Intent(PaymentScreen.this, HotelBookingConfirm.class));	
				finish();
			}
		}
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(PaymentScreen.this,
				"6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(PaymentScreen.this);
	}
}
