package com.telanganatourism.android.phase2;

import org.json.JSONArray;
import org.json.JSONObject;

import com.telanganatourism.android.phase2.HotelBookingActivity.BlockRoomNumber;
import com.telanganatourism.android.phase2.Package_Booking.FareService;
import com.telanganatourism.android.phase2.util.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class SeatSelctionWebview extends Activity {
	
	WebView webView;
//	Utility utility;
	
	String seatsNos, forwardResp, returnResponse;
	int seatsLength;
	Button submitBtn;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {   

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.seat_selection_browser);
		
//		utility = new Utility(SeatSelctionWebview.this);
		
		webView = (WebView) findViewById(R.id.webView1);
		
		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.getSettings().setJavaScriptEnabled(true);
//		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.addJavascriptInterface(new WebAppInterface(SeatSelctionWebview.this),
				"WebAppInterface");
		webView.setWebChromeClient(new WebChromeClient() {
		       @Override
		        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
		                    return super.onJsAlert(view, url, message, result);
		           } 
		   });
//		webView.setWebViewClient(client);
//		webView.loadUrl("http://172.16.3.30:8080/tgtdc/etg/seatLayoutReservation.jsp?upDown=UP&toCityCode=100&serviceCode=34&totSeats=2&vehicle=HITECH%20-%20AC&toDate=24-08-2015&FR=F");
		webView.loadUrl(Constants.Base_url1+"seatLayoutReservation.jsp?upDown="+SeatSelection.upDown+"&toCityCode="+PackageListing.city_Code+"&serviceCode="+PackageListing.service_Code+"&totSeats="+Package_Booking.no_of_people+"&vehicle="+PackageListing.pack_type+"&toDate="+Package_Booking.str_date+"&FR="+SeatSelection.RF);
		System.out.println("webview"+Constants.Base_url1+"seatLayoutReservation.jsp?upDown="+SeatSelection.upDown+"&toCityCode="+PackageListing.city_Code+"&serviceCode="+PackageListing.service_Code+"&totSeats="+Package_Booking.no_of_people+"&vehicle="+PackageListing.pack_type+"&toDate="+Package_Booking.str_date+"&FR="+SeatSelection.RF);
		submitBtn = (Button)findViewById(R.id.submitBtn);
		submitBtn.setTypeface(Constants.ProximaNova_Regular);
		submitBtn.setOnClickListener(new OnClickListener() {
  
//			seatLayoutReservation.jsp	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callJavaScript(webView, "getTotSeats", "");
				
//				Toast.makeText(getApplicationContext(), seatsNos, 200).show();
//				if(seatsNos.trim().contains(" ")){
//					
//				}
				if(TextUtils.isEmpty(seatsNos)){
					
				}else if(seatsNos.trim().contains(",")){
					String[] splitStr = seatsNos.split(",");
					seatsLength = splitStr.length;
					
					if(seatsLength == Integer.parseInt(Package_Booking.no_of_people)){
						
						if (Utility.checkInternetConnection(SeatSelctionWebview.this)) {
							new BlockSeats().execute();
						} else {
							Utility.showAlertNoInternetService(SeatSelctionWebview.this);
						}
						
						
						
					}else{
						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								SeatSelctionWebview.this);
						altDialog
								.setMessage("Please select "+Package_Booking.no_of_people+" seats"); // here
						altDialog
								.setNeutralButton(
										"OK",
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
				else{
					seatsLength = 1;
					
					if(seatsLength == Integer.parseInt(Package_Booking.no_of_people)){
						
						
						if (Utility.checkInternetConnection(SeatSelctionWebview.this)) {
							new BlockSeats().execute();
						} else {
							Utility.showAlertNoInternetService(SeatSelctionWebview.this);
						}
						
					}else{
						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								SeatSelctionWebview.this);
						altDialog
								.setMessage("Please select "+Package_Booking.no_of_people+" seats"); // here
																// add
																// your
																// message
						altDialog
								.setNeutralButton(
										"OK",
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
				
			}
		});
		
	}
	
	class BlockSeats extends AsyncTask<String, Integer, String> {

		ProgressDialog pDialog;
		JSONObject jObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SeatSelctionWebview.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			 System.out.println("url is"+Constants.Base_url1
						+ "blockbusseats.jsp?sc="+PackageListing.service_Code+"&dt="+Package_Booking.str_date+"&st="+seatsNos);
			return ServiceCalls1.getJSONString(Constants.Base_url1
					+ "blockbusseats.jsp?sc="+PackageListing.service_Code+"&dt="+Package_Booking.str_date+"&st="+seatsNos);
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
			
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			
			if (result.contains("error")) {
				AlertDialog.Builder altDialog = new AlertDialog.Builder(
						SeatSelctionWebview.this);
				altDialog
						.setMessage("Please select the seats"); // here
														// add
														// your
														// message
				altDialog
						.setNeutralButton(
								"OK",
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

				try {

					JSONArray json = new JSONArray(result);
					for (int i = 0; i < json.length(); i++) {
						JSONObject e = json.getJSONObject(i);
						
						if(e.has("forwardResponse")){
							forwardResp = e.getString("forwardResponse");	
						}						
						
						if(e.has("returnResponse")){
							returnResponse = e.getString("returnResponse");	
						}
						
					}
					
					if(forwardResp.equalsIgnoreCase("0") || returnResponse.equalsIgnoreCase("0")){
						AlertDialog.Builder altDialog = new AlertDialog.Builder(
								SeatSelctionWebview.this);
						altDialog
								.setMessage("Seats are booked at this moment please reselect another seat(s)."); // here
																// add
																// your
																// message
						altDialog
								.setNeutralButton(
										"OK",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												dialog.dismiss();
											}
										});
						altDialog.show();
					}else{

							AlertDialog.Builder altDialog = new AlertDialog.Builder(
									SeatSelctionWebview.this);
							altDialog
									.setMessage("You will not allowed to change the seat numbers again. Are you sure want to continue?"); // here
														
							altDialog
							.setPositiveButton(
									"PROCEED",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											Package_Booking.seatSelectionDisabe = 1;
											startActivity(new Intent(SeatSelctionWebview.this, SeatSelection.class));
											finish();
											dialog.dismiss();
										}
									});
											altDialog.setNegativeButton(
									"CANCEL",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});

						altDialog.show();
						
					}
					
					

				} catch (Exception e) {
					Log.e("TAG", ""+e);
				}
				
				if(pDialog.isShowing()){
					pDialog.dismiss();
				}

			}
		}
	}
	
	public class WebAppInterface {
		Context mContext;

		/** Instantiate the interface and set the context */
		WebAppInterface(Context c) {
			mContext = c;
		}

		@JavascriptInterface
		// must be added for API 17 or higher
		public void showToast(String toast) {
			
			seatsNos = toast;
//			Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
//			if(SeatSelection.RF.equalsIgnoreCase("F")){
				Package_Booking.seatNos1 = toast;
//			}else{
				Package_Booking.seatNos2 = toast;
//			}
			
		}
	}
	
    private void callJavaScript(WebView view, String methodName, Object...params){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:try{");
        stringBuilder.append(methodName);
        stringBuilder.append("(");
//        String separator = "";
//        for (Object param : params) {               
//            stringBuilder.append(separator);
//            separator = ",";
//            if(param instanceof String){
//                stringBuilder.append("'");
//            }
//                stringBuilder.append(param);
//            if(param instanceof String){
//                stringBuilder.append("'");
//            }
//
//        }
        stringBuilder.append(")}catch(error){console.error(error.message);}");
        final String call = stringBuilder.toString();
        Log.i("TAG", "callJavaScript: call="+call);
        
//        Toast.makeText(getApplicationContext(), call, Toast.LENGTH_LONG).show();


        view.loadUrl(call);
    }
}
