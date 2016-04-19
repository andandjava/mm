package com.telanganatourism.android.phase2.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utility {
	public Context mContext;

	public Utility(Context context) {
		mContext = context;
	}

	// String baseUrl = "http://www.happeninghyderabad.com/xmlevents/";
	// String baseUrl1 = "http://www.happeninghyderabad.com/webservices/";
//	String baseUrl1 = "http://enowdev.etg.net/Webservices2/";
//	String baseUrl2 = "http://enowdev.etg.net/WebServices3/";
	
	public static ArrayList<String> nameOfEvent = new ArrayList<String>();
	public static ArrayList<String> startDates = new ArrayList<String>();
	public static ArrayList<String> endDates = new ArrayList<String>();

	public double distance(double lat1, double lon1, double lat2, double lon2,
			char unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
			dist = dist * 1.609344;
		} else if (unit == 'N') {
			dist = dist * 0.8684;
		}
		return (dist);
	}

	private double deg2rad(double deg) {

		return (deg * Math.PI / 180.0);

	}

	private double rad2deg(double rad) {

		return (rad * 180 / Math.PI);

	}

//	public boolean IsNetConnected(Context context) {
//		boolean isConnected = false;
//		ConnectivityManager connectivity = (ConnectivityManager) context
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//		if (connectivity != null) {
//			NetworkInfo[] info = connectivity.getAllNetworkInfo();
//			if (info != null)
//				for (int i = 0; i < info.length; i++) {
//
//					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//						isConnected = true;
//					}
//				}
//		}
//		System.out.println("Internet Connection is: " + isConnected);
//		return isConnected;
//	}
	
	public static boolean checkInternetConnection(Context context) {

		ConnectivityManager con_manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (con_manager.getActiveNetworkInfo() != null
				&& con_manager.getActiveNetworkInfo().isAvailable()
				&& con_manager.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}
	
	

	public static void showAlertNoInternetService(Context context) {
		AlertDialog.Builder altDialog = new AlertDialog.Builder(context);
		// altDialog.setTitle("Alert");
		altDialog
				.setMessage("Sorry, Network is not available. Please try again later"); // here
		// add
		// your
		// message
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		altDialog.show();
	}

	public void showAlertFavorites(Context context, String message) {
		AlertDialog.Builder altDialog = new AlertDialog.Builder(context);
		altDialog.setMessage(message); // here add your message
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		altDialog.show();
	}

	public   String dateConvert(String startDate, String endDate) {
		String text = null;

		boolean flag = dateComparison(startDate.replace("T", " "),
				endDate.replace("T", " "));
		if (flag) {
			try {
				String string = startDate.replace("T", " ");
				String[] parts = string.split(" ");
				String[] parts1 = parts[1].split(":");

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = formatter.parse(parts[0]);
				DateFormat out = new SimpleDateFormat("EEE dd MMM, yyyy");

				text = out.format(date1) + " " + parts1[0] + ":" + parts1[1];

			} catch (ParseException e) {
				e.printStackTrace();
			}

		} else {
			try {
				String string = startDate.replace("T", " ");
				String[] parts = string.split(" ");
				String[] parts2 = parts[1].split(":");

				String string1 = endDate.replace("T", " ");
				String[] parts1 = string1.split(" ");
				String[] parts3 = parts1[1].split(":");

				// String[] parts1 = parts[1].split("sai");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = formatter.parse(parts[0]);
				Date date12 = formatter.parse(parts1[0]);
				DateFormat out = new SimpleDateFormat("EEE dd MMM, yyyy");
				DateFormat out1 = new SimpleDateFormat("EEE dd MMM, yyyy");
				boolean flag1 = Date_Comparison(parts[0], parts1[0]);
				if (flag1) {
					text = out.format(date1) + " " + parts2[0] + ":"
							+ parts2[1] + " To " + parts3[0] + ":" + parts3[1];
				} else {
					text = out.format(date1) + " " + parts2[0] + ":"
							+ parts2[1] + " To " + out1.format(date12) + " "
							+ parts3[0] + ":" + parts3[1];
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		return text;
	}

	public String dateConvert1(String D) {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		SimpleDateFormat format2 = new SimpleDateFormat("MMM dd, yyyy",
				Locale.US);
		Date date = null;
		try {

			date = format1.parse(D);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String dateString = format2.format(date);
		dateString = dateString.replace("-", " ");
		return ((dateString));
	}

	public boolean dateComparison(String date, String dateafter) {
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss", Locale.US);
		Date convertedDate = new Date();
		Date convertedDate2 = new Date();
		try {
			convertedDate = dateFormat.parse(date);
			convertedDate2 = dateFormat.parse(dateafter);
			if (convertedDate2.equals(convertedDate)) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean dateComparison1(String date, String dateafter) {
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.US);
		Date convertedDate = new Date();
		Date convertedDate2 = new Date();
		try {
			convertedDate = dateFormat.parse(date);
			convertedDate2 = dateFormat.parse(dateafter);
			System.out.println("dates" + convertedDate + convertedDate2);
			if (convertedDate.before(convertedDate2)) {

				flag = true;
			} else {
				flag = false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean dateComparison2(String date, String dateafter) {
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.US);
		Date convertedDate = new Date();
		Date convertedDate2 = new Date();
		try {
			convertedDate = dateFormat.parse(date);
			convertedDate2 = dateFormat.parse(dateafter);
			System.out.println("before dates" + convertedDate + convertedDate2);
			if (convertedDate.after(convertedDate2)) {

				flag = true;
			} else {
				flag = false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressLint("SimpleDateFormat")
	public boolean CheckDates(String startDate, String endDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.US);
		boolean b = false;

		try {

			java.util.Date d1 = sdf.parse(startDate);
			java.util.Date d2 = sdf.parse(endDate);
			if (compareTo(d1, d2) <= 0) {
				System.out.println("d1 is before d2");
				b = true;
			} else if (compareTo(d1, d2) > 0) {
				System.out.println("d1 is after d2");
				b = false;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return b;
	}

	public long compareTo(java.util.Date date1, java.util.Date date2) {
		System.out
				.println("date1 & date2" + date1.getTime() + ":"
						+ date2.getTime() + "==="
						+ (date1.getTime() - date2.getTime()));
		return date1.getTime() - date2.getTime();
	}

	public boolean Date_Comparison(String date, String dateafter) {
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.US);
		Date convertedDate = new Date();
		Date convertedDate2 = new Date();
		try {
			convertedDate = dateFormat.parse(date);
			convertedDate2 = dateFormat.parse(dateafter);
			if (convertedDate2.equals(convertedDate)) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public void dialogExample1(String sss) {
		AlertDialog.Builder altDialog = new AlertDialog.Builder(mContext);
		altDialog.setMessage(sss); // here add your message
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		altDialog.show();
	}

	public void dialogExample() {
		/*
		 * AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		 * builder.setMessage("No data from server!"); builder.show();
		 */

		AlertDialog.Builder altDialog = new AlertDialog.Builder(mContext);
		altDialog.setMessage("No data from server!"); // here add your message
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		altDialog.show();
	}
	

}
