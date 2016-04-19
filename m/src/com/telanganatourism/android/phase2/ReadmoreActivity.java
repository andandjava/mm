package com.telanganatourism.android.phase2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

public class ReadmoreActivity extends Activity {

//	int fontChaning = 0;
	
	TextView titleTxt, descrption;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.read_more_activity);

		RelativeLayout headerLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);

		RelativeLayout backBtn = (RelativeLayout) findViewById(R.id.menuLayout);

		titleTxt = (TextView) findViewById(R.id.event_title);

		// TextView titleTxt1 = (TextView) findViewById(R.id.event_desc);

		headerLayout.setBackgroundColor(Color
				.parseColor(MainActivity.changeColorStr));

		descrption = (TextView) findViewById(R.id.event_full_desc);
		
		titleTxt.setTypeface(Constants.ProximaNova_Regular);
		descrption.setTypeface(Constants.ProximaNova_Regular);

		if (Constants.selectLanguage.equalsIgnoreCase("1")) {
			titleTxt.setText("About " + DetailScreen1.tit);
		} else {
			titleTxt.setText(DetailScreen1.tit + " "
					+ getResources().getString(R.string.details_about));
		}

		// titleTxt1.setText("About " + DetailScreen1.tit);
		
//		String str = "\u0688\u06cc\u0644\u06cc \u0633\u0641\u0631";

		descrption.setText(Html.fromHtml(DetailScreen1.abt.replace("&nbsp;",
				" ").replace("\r\n\r\n", "<br><br>")));

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent n = new Intent(ReadmoreActivity.this,
						DetailScreen1.class);
				n.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(n);
				overridePendingTransition(R.anim.trans_right_in,
						R.anim.trans_right_out);
			}
		});
		
//		TextView tvSmallFont = (TextView) findViewById(R.id.smallFontTxt);
//		TextView tvBigFont = (TextView) findViewById(R.id.bigFontTxt);
//		
//		tvSmallFont.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (fontChaning == 0) {
//					titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//					descrption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
					
					if(SplashScreen.fontpref
							.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("1")){
						titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
						descrption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
					}else if(SplashScreen.fontpref
							.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")){
						titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
						descrption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
					}else if(SplashScreen.fontpref
							.getString(SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")){
						titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
						descrption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
					}
//				} else {
//					fontChaning--;
//					if (fontChaning > 0) {
//
//						if (fontChaning == 1) {
//							titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//							descrption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//						} else if (fontChaning == 2) {
//							titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//							descrption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//						} else if (fontChaning == 3) {
//							titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//							descrption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//						}
////						Toast.makeText(getApplicationContext(),
////								"" + fontChaning, Toast.LENGTH_LONG).show();
//					} else {
////						Toast.makeText(getApplicationContext(),
////								"Else : " + fontChaning, Toast.LENGTH_LONG)
////								.show();
//					}
//				}
//
//			}
//		});
//
//		tvBigFont.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				if (fontChaning == 3) {
//
//				} else {
//					fontChaning++;
//
//					if (fontChaning <= 3) {
//
//						if (fontChaning == 1) {
//							titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
//							descrption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//						} else if (fontChaning == 2) {
//							titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
//							descrption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//						} else if (fontChaning == 3) {
//							titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//							descrption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//						}
//
////						Toast.makeText(getApplicationContext(),
////								"" + fontChaning, Toast.LENGTH_LONG).show();
//					} else {
////						Toast.makeText(getApplicationContext(),
////								"Else : " + fontChaning, Toast.LENGTH_LONG)
////								.show();
//					}
//				}
//
//			}
//		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(ReadmoreActivity.this, "6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(ReadmoreActivity.this);
	}
}
