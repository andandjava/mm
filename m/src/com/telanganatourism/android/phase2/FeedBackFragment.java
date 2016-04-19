package com.telanganatourism.android.phase2;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.telanganatourism.android.phase2.util.Utility;

public class FeedBackFragment extends Fragment {
	Pattern pattern1 = Pattern
			.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

	// int fontChaning = 0;

	TextView t1, nameWarning, emailWarningTxt, phoneWarningTxt, msgWarning;

	public FeedBackFragment() {
	}

//	Utility utility;
	EditText nameEdit, emailEdit, phoneEdit, msgEdit;
	Button btnSubmit;

	// public static SharedPreferences pref;
	// public static Editor editor;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.feed_back, container, false);
//		utility = new Utility(getActivity());
		// getActivity().getActionBar().setTitle("Contact Us");
		// getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActivity().getActionBar().setBackgroundDrawable(
		// new ColorDrawable(Color.parseColor("#F2F2F2")));
		// // MainActivity.getRightMenuList(getActivity(),MainActivity.stay);

		// SplashScreen.pref =
		// getActivity().getSharedPreferences("telangana_tourism",
		// getActivity().MODE_PRIVATE);
		// SplashScreen.editor = SplashScreen.pref.edit();

		MainActivity.txt_logo.setVisibility(View.GONE);

		MainActivity.txt_logo1.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setVisibility(View.VISIBLE);

		MainActivity.txt_title.setText(getResources().getString(
				R.string.feed_back_sidemenu));

		MainActivity.txt_title.setTextColor(Color.parseColor("#000000"));

		MainActivity.txt_filter.setVisibility(View.GONE);

		MainActivity.txt_location.setVisibility(View.GONE);

		MainActivity.txt_search.setVisibility(View.GONE);

		MainActivity.txt_Edit.setVisibility(View.GONE);

		MainActivity.btn_menu.setBackgroundResource(R.drawable.menu_green);

		MainActivity.headerLayout.setBackgroundColor(Color
				.parseColor("#ffffff"));

		nameEdit = (EditText) rootView.findViewById(R.id.nameEdit);
		emailEdit = (EditText) rootView.findViewById(R.id.emailEdit);
		phoneEdit = (EditText) rootView.findViewById(R.id.phoneEdit);
		msgEdit = (EditText) rootView.findViewById(R.id.msgEdit);
		btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);

		emailWarningTxt = (TextView) rootView.findViewById(R.id.email_warning);
		phoneWarningTxt = (TextView) rootView.findViewById(R.id.phone_warning);
		nameWarning = (TextView) rootView.findViewById(R.id.name_warning);
		msgWarning = (TextView) rootView.findViewById(R.id.msg_warning);
		
		nameEdit.setText(SplashScreen.pref.getString(
				SplashScreen.Key_GET_USER_NAME, ""));
		phoneEdit.setText(SplashScreen.pref.getString(
				SplashScreen.Key_GET_USER_PHONE_NO, ""));

		// t1 = (TextView) rootView.findViewById(R.id.t1);

		/*
		 * nameEdit.setText(SplashScreen.pref.getString(
		 * SplashScreen.Key_GET_USER_NAME, ""));
		 * phoneEdit.setText(SplashScreen.pref.getString(
		 * SplashScreen.Key_GET_USER_PHONE_NO, ""));
		 */

		// TextView tvSmallFont = (TextView)
		// rootView.findViewById(R.id.smallFontTxt);
		// TextView tvBigFont = (TextView)
		// rootView.findViewById(R.id.bigFontTxt);
		//
		// tvSmallFont.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (fontChaning == 0) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// nameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// emailEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// phoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// msgEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		// btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

		if (SplashScreen.fontpref.getString(SplashScreen.Key_GET_FONT_ID, "")
				.equalsIgnoreCase("1")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			// t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			nameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			emailEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			phoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			msgEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("2")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			// t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			nameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			emailEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			phoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			msgEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		} else if (SplashScreen.fontpref.getString(
				SplashScreen.Key_GET_FONT_ID, "").equalsIgnoreCase("3")) {
			MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
			// t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			nameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			emailEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			phoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			msgEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		}
		// } else {
		// fontChaning--;
		// if (fontChaning > 0) {
		//
		// if (fontChaning == 1) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// nameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// emailEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// phoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// msgEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// } else if (fontChaning == 2) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// nameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// emailEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// phoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// msgEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// } else if (fontChaning == 3) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
		// // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// nameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// emailEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// phoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// msgEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// }
		// // Toast.makeText(getApplicationContext(),
		// // "" + fontChaning, Toast.LENGTH_LONG).show();
		// } else {
		// // Toast.makeText(getApplicationContext(),
		// // "Else : " + fontChaning, Toast.LENGTH_LONG)
		// // .show();
		// }
		// }
		//
		// }
		// });
		//
		// tvBigFont.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// if (fontChaning == 3) {
		//
		// } else {
		// fontChaning++;
		//
		// if (fontChaning <= 3) {
		//
		// if (fontChaning == 1) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		// nameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// emailEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// phoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// msgEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// } else if (fontChaning == 2) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		// // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		// nameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// emailEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// phoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// msgEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		// btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// } else if (fontChaning == 3) {
		// MainActivity.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
		// // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		// nameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// emailEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// phoneEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// msgEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
		// btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		// }
		//
		// // Toast.makeText(getApplicationContext(),
		// // "" + fontChaning, Toast.LENGTH_LONG).show();
		// } else {
		// // Toast.makeText(getApplicationContext(),
		// // "Else : " + fontChaning, Toast.LENGTH_LONG)
		// // .show();
		// }
		// }
		//
		// }
		// });

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(Utility.checkInternetConnection(getActivity())){
					if (nameEdit.getText().toString().trim().length() == 0) {
						nameWarning.setVisibility(View.VISIBLE);
						emailWarningTxt.setVisibility(View.GONE);
						phoneWarningTxt.setVisibility(View.GONE);
						msgWarning.setVisibility(View.GONE);
						Toast.makeText(getActivity(), "Please enter name",
								Toast.LENGTH_LONG).show();
					} else {
						nameWarning.setVisibility(View.GONE);
						if (emailEdit.getText().toString().trim().length() == 0) {
							emailWarningTxt.setVisibility(View.VISIBLE);
							Toast.makeText(getActivity(),
									"Please enter email id", Toast.LENGTH_LONG)
									.show();
						} else {
							
							Matcher matcher1 = pattern1
									.matcher(emailEdit.getText()
											.toString().trim());

							if (matcher1.matches()) {
								nameWarning.setVisibility(View.GONE);
								emailWarningTxt
										.setVisibility(View.GONE);
								msgWarning.setVisibility(View.GONE);
								if (phoneEdit.getText().toString().trim().length() == 0) {
									phoneWarningTxt.setVisibility(View.VISIBLE);
									Toast.makeText(
											getActivity(),
											"Please enter 10 digit valid phone number",
											Toast.LENGTH_LONG).show();
								} else {
									if (phoneEdit.getText().length() < 10) {
										phoneWarningTxt
												.setVisibility(View.VISIBLE);
										emailWarningTxt.setVisibility(View.GONE);
										nameWarning.setVisibility(View.GONE);
										msgWarning.setVisibility(View.GONE);
										Toast.makeText(
												getActivity(),
												"Please enter 10 digit valid phone number",
												Toast.LENGTH_LONG).show();
									} else {
										nameWarning.setVisibility(View.GONE);
										emailWarningTxt
												.setVisibility(View.GONE);
										phoneWarningTxt
												.setVisibility(View.GONE);
										
										
										if (msgEdit.getText().toString().trim()
												.length() == 0) {
											msgWarning.setVisibility(View.VISIBLE);
											emailWarningTxt.setVisibility(View.GONE);
											phoneWarningTxt.setVisibility(View.GONE);
											nameWarning.setVisibility(View.GONE);
											Toast.makeText(getActivity(),
													"Please Enter Message",
													Toast.LENGTH_LONG).show();
										} else {
											msgWarning.setVisibility(View.GONE);
											emailWarningTxt.setVisibility(View.GONE);
											phoneWarningTxt.setVisibility(View.GONE);
											nameWarning.setVisibility(View.GONE);
											
											InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
												      Context.INPUT_METHOD_SERVICE);
												imm.hideSoftInputFromWindow(msgWarning.getWindowToken(), 0);
											
											if(Utility.checkInternetConnection(getActivity())){
												new FeedbackTask().execute();
											}else{
												Utility.showAlertNoInternetService(getActivity());
											}
											
											
										}
									}
								}
								

							} else {
								emailWarningTxt
										.setVisibility(View.VISIBLE);
								nameWarning.setVisibility(View.GONE);
								msgWarning.setVisibility(View.GONE);
								phoneWarningTxt
										.setVisibility(View.GONE);
								Toast.makeText(getActivity(),
										"Please enter valid email",
										Toast.LENGTH_LONG).show();
							}
//							if (phoneEdit.getText().toString().trim().length() == 0) {
//								phoneWarningTxt.setVisibility(View.VISIBLE);
//								Toast.makeText(
//										getActivity(),
//										"Please enter 10 digit valid phone number",
//										Toast.LENGTH_LONG).show();
//							} else {
//								
//								
//								if (phoneEdit.getText().length() < 10) {
//									phoneWarningTxt
//											.setVisibility(View.VISIBLE);
//									Toast.makeText(
//											getActivity(),
//											"Please enter 10 digit valid phone number",
//											Toast.LENGTH_LONG).show();
//								} else {
//									phoneWarningTxt.setVisibility(View.GONE);
//								}
//								
//								if (msgEdit.getText().toString().trim()
//										.length() == 0) {
//									msgWarning.setVisibility(View.VISIBLE);
//									emailWarningTxt.setVisibility(View.GONE);
//									phoneWarningTxt.setVisibility(View.GONE);
//									Toast.makeText(getActivity(),
//											"Please Enter Message",
//											Toast.LENGTH_LONG).show();
//								} else {
//									msgWarning.setVisibility(View.GONE);
//									emailWarningTxt.setVisibility(View.GONE);
//									phoneWarningTxt.setVisibility(View.GONE);
//									
//								}
//							}
						}

					}
				}
			}
		});

		return rootView;
	}

	private class FeedbackTask extends AsyncTask<String, Void, String> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("Loading ...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			HttpClient httpclient = new DefaultHttpClient();
			System.out.println("url is"+Constants.Base_url
					+ "feedback?name=" + nameEdit.getText().toString().trim()
					+ "&email=" + emailEdit.getText().toString().trim()
					+ "&phone=" + phoneEdit.getText().toString().trim()
					+ "&feedback="
					+ msgEdit.getText().toString().trim().replace(" ", "%20"));
			HttpPost httppost = new HttpPost(Constants.Base_url
					+ "feedback?name=" + nameEdit.getText().toString().trim()
					+ "&email=" + emailEdit.getText().toString().trim()
					+ "&phone=" + phoneEdit.getText().toString().trim()
					+ "&feedback="
					+ msgEdit.getText().toString().trim().replace(" ", "%20"));
			String result = null;
			try {

				/*
				 * List<NameValuePair> nameValuePairs = new
				 * ArrayList<NameValuePair>(2); nameValuePairs.add(new
				 * BasicNameValuePair("name",
				 * nameEdit.getText().toString().trim()));
				 * nameValuePairs.add(new BasicNameValuePair("email",
				 * emailEdit.getText().toString().trim()));
				 * nameValuePairs.add(new BasicNameValuePair("phone",
				 * phoneEdit.getText().toString().trim()));
				 * nameValuePairs.add(new BasicNameValuePair("message",
				 * msgEdit.getText().toString().trim()));
				 * 
				 * httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				 */

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, HTTP.UTF_8).trim();

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println("result1" + result);

			if (null != progressDialog && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}

			// Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
			try {
				JSONObject jsonObject = new JSONObject(result);
				System.out.println("feedbaceresult"
						+ jsonObject.optString("message"));
				if (result.contains("message")) {

					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							getActivity());
					builder1.setMessage("" + jsonObject.optString("message"));
					builder1.setCancelable(true);
					builder1.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									nameEdit.setText("");
									emailEdit.setText("");
									phoneEdit.setText("");
									msgEdit.setText("");
									dialog.cancel();
									
									
									
//									getActivity().getWindow().setSoftInputMode(
//										    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
//										);
									
									final FragmentTransaction ft = getFragmentManager()
											.beginTransaction();
									ft.replace(R.id.frame_container, new HomeFragment(),
											getResources().getString(R.string.home_sidemenu));
									ft.addToBackStack(
											getResources().getString(R.string.home_sidemenu))
											.commit();
								}
							});

					AlertDialog alert11 = builder1.create();
					alert11.show();

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(getActivity(), "6JH538X28S58R9MTR7J8");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(getActivity());
	}

}
