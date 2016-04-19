package com.telanganatourism.android.phase2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.R.anim;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {
	private Context mContext;

	private java.util.Calendar month;
	public GregorianCalendar pmonth; // calendar instance for previous month
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	int firstDay;
	int maxWeeknumber;
	int maxP;
	int calMaxP;
	int lastWeekDay;
	int leftDays;
	int mnthlength;

	String itemvalue, curentDateString, today_date;
	DateFormat df;
	TextView dayView;
	LinearLayout ll_date;

	private ArrayList<String> items;
	public static List<String> dayString;
	private View previousView;
	View v;
	String grid_day;
	String[] today_array;
	String today_year, today_mon, today_day;

	public CalendarAdapter(Context c, GregorianCalendar monthCalendar,
			String sel_date, String today_date) {
		CalendarAdapter.dayString = new ArrayList<String>();
		Locale.setDefault(Locale.US);
		this.today_date = today_date;
		month = monthCalendar;
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		mContext = c;
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);
		System.out.println("before date" + GregorianCalendar.DATE);
		month.add(GregorianCalendar.DATE, 0);
		System.out.println("after date" + GregorianCalendar.DATE);
		this.items = new ArrayList<String>();
		df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		// today_date = df.format(selectedDate.getTime());
		today_array = today_date.split("-");
		today_year = today_array[0].replaceFirst("^0*", "");
		today_mon = today_array[1].replaceFirst("^0*", "");
		today_day = today_array[2].replaceFirst("^0*", "");
		curentDateString = sel_date;
		System.out.println("sel_date" + sel_date);
		System.out.println("currentdate:" + curentDateString);
		refreshDays();
	}

	public void setItems(ArrayList<String> items) {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}

	public int getCount() {
		return dayString.size();
	}

	public Object getItem(int position) {
		return dayString.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		v = convertView;

		if (convertView == null) { // if it's not recycled, initialize some
									// attributes

			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.calendar_item, null);
		}
		dayView = (TextView) v.findViewById(R.id.date);
		ll_date = (LinearLayout) v.findViewById(R.id.linearlayout);

		// separates daystring into parts.
		String[] separatedTime = dayString.get(position).split("-");
		// taking last part of date. ie; 2 from 2012-12-02
		String gridvalue = separatedTime[2].replaceFirst("^0*", "");
		String grid_mon = separatedTime[1].replaceFirst("^0*", "");
		String grid_year = separatedTime[0].replaceFirst("^0*", "");
		SimpleDateFormat inFormat1 = new SimpleDateFormat("dd-MM-yyyy");
		Date date2;
		try {
			date2 = inFormat1.parse("" + gridvalue + "-" + grid_mon + "-"
					+ grid_year);
			SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
			grid_day = outFormat.format(date2);

			System.out.println("goal_gridday::" + grid_day);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("gridvalue::" + gridvalue);
		// checking whether the day is in current month or not.
		System.out.println("firstday::" + firstDay);
		System.out.println("position:" + position);

		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			// setting offdays to white color.
			dayView.setTextColor(Color.WHITE);

			System.out.println("dayView is Clickable" + dayView.isClickable());
			System.out.println("ll_date is Clickable" + ll_date.isClickable());
			dayView.setVisibility(View.GONE);
			ll_date.setVisibility(View.GONE);
			dayView.setClickable(true);
			ll_date.setClickable(true);
			// dayView.setFocusable(false);
			// ll_date.setFocusable(false);
			// ll_date.setActivated(false);
			// ll_date.setFocusableInTouchMode(false);
			// dayView.setFocusableInTouchMode(false);
			ll_date.setBackgroundColor(Color.TRANSPARENT);

			System.out.println("dayView is Clickable" + dayView.isClickable());
			System.out.println("ll_date is Clickable" + ll_date.isClickable());

		} else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {

			dayView.setTextColor(Color.WHITE);
			dayView.setVisibility(View.GONE);
			ll_date.setVisibility(View.GONE);
			ll_date.setBackgroundColor(Color.parseColor("#ededed"));
			// dayView.setClickable(false);
			// dayView.setFocusable(false);
			dayView.setClickable(true);
			ll_date.setClickable(true);
		} else {
			// setting curent month's days in blue color.

			if (Integer.parseInt(grid_year) == Integer.parseInt(today_year)) {
				if (Integer.parseInt(grid_mon) == Integer.parseInt(today_mon)) {
					if (Integer.parseInt(gridvalue) < Integer
							.parseInt(today_day)) {
						dayView.setTextColor(Color.GRAY);
						ll_date.setBackgroundColor(Color.parseColor("#ededed"));
						dayView.setClickable(true);
						ll_date.setClickable(true);
					} else {
						dayView.setTextColor(Color.BLACK);
						dayView.setClickable(false);
						ll_date.setClickable(false);
						dayView.setVisibility(View.VISIBLE);
						ll_date.setVisibility(View.VISIBLE);
					}
				} else if (Integer.parseInt(grid_mon) < Integer
						.parseInt(today_mon)) {
					dayView.setTextColor(Color.GRAY);
					ll_date.setBackgroundColor(Color.parseColor("#ededed"));
					dayView.setClickable(true);
					ll_date.setClickable(true);
				} else {
					dayView.setTextColor(Color.BLACK);
					dayView.setClickable(false);
					ll_date.setClickable(false);
					dayView.setVisibility(View.VISIBLE);
					ll_date.setVisibility(View.VISIBLE);
				}

				if(Constants.package_date_flag == 1){					
					
					if(PackageListing.packagesAvailability.contains("MON")){
						if (grid_day.toUpperCase().equalsIgnoreCase("MON")) {
							dayView.setTextColor(Color.RED);
							if ((Integer.parseInt(grid_year) == (Integer
									.parseInt(today_year)) && (Integer
									.parseInt(grid_mon) == (Integer.parseInt(today_mon)) && (Integer
									.parseInt(gridvalue) < (Integer.parseInt(today_day)))))) {
								dayView.setTextColor(Color.GRAY);
								ll_date.setBackgroundColor(Color.parseColor("#ededed"));
								dayView.setClickable(true);
								ll_date.setClickable(true);
							}

						}
					}
					if(PackageListing.packagesAvailability.contains("TUE")){
						if (grid_day.toUpperCase().equalsIgnoreCase("TUE")) {
							dayView.setTextColor(Color.RED);
							if ((Integer.parseInt(grid_year) == (Integer
									.parseInt(today_year)) && (Integer
									.parseInt(grid_mon) == (Integer.parseInt(today_mon)) && (Integer
									.parseInt(gridvalue) < (Integer.parseInt(today_day)))))) {
								dayView.setTextColor(Color.GRAY);
								ll_date.setBackgroundColor(Color.parseColor("#ededed"));
								dayView.setClickable(true);
								ll_date.setClickable(true);
							}

						}
					} 
					if(PackageListing.packagesAvailability.contains("WED")){
						if (grid_day.toUpperCase().equalsIgnoreCase("WED")) {
							dayView.setTextColor(Color.RED);
							if ((Integer.parseInt(grid_year) == (Integer
									.parseInt(today_year)) && (Integer
									.parseInt(grid_mon) == (Integer.parseInt(today_mon)) && (Integer
									.parseInt(gridvalue) < (Integer.parseInt(today_day)))))) {
								dayView.setTextColor(Color.GRAY);
								ll_date.setBackgroundColor(Color.parseColor("#ededed"));
								dayView.setClickable(true);
								ll_date.setClickable(true);
							}

						}
					} 
					if(PackageListing.packagesAvailability.contains("THU")){
						if (grid_day.toUpperCase().equalsIgnoreCase("THU")) {
							dayView.setTextColor(Color.RED);
							if ((Integer.parseInt(grid_year) == (Integer
									.parseInt(today_year)) && (Integer
									.parseInt(grid_mon) == (Integer.parseInt(today_mon)) && (Integer
									.parseInt(gridvalue) < (Integer.parseInt(today_day)))))) {
								dayView.setTextColor(Color.GRAY);
								ll_date.setBackgroundColor(Color.parseColor("#ededed"));
								dayView.setClickable(true);
								ll_date.setClickable(true);
							}

						}
					} 
					if(PackageListing.packagesAvailability.contains("FRI")){
						if (grid_day.toUpperCase().equalsIgnoreCase("FRI")) {
							dayView.setTextColor(Color.RED);
							if ((Integer.parseInt(grid_year) == (Integer
									.parseInt(today_year)) && (Integer
									.parseInt(grid_mon) == (Integer.parseInt(today_mon)) && (Integer
									.parseInt(gridvalue) < (Integer.parseInt(today_day)))))) {
								dayView.setTextColor(Color.GRAY);
								ll_date.setBackgroundColor(Color.parseColor("#ededed"));
								dayView.setClickable(true);
								ll_date.setClickable(true);
							}

						}
					} 
					if(PackageListing.packagesAvailability.contains("SAT")){
						if (grid_day.toUpperCase().equalsIgnoreCase("SAT")) {
							dayView.setTextColor(Color.RED);
							if ((Integer.parseInt(grid_year) == (Integer
									.parseInt(today_year)) && (Integer
									.parseInt(grid_mon) == (Integer.parseInt(today_mon)) && (Integer
									.parseInt(gridvalue) < (Integer.parseInt(today_day)))))) {
								dayView.setTextColor(Color.GRAY);
								ll_date.setBackgroundColor(Color.parseColor("#ededed"));
								dayView.setClickable(true);
								ll_date.setClickable(true);
							}

						}
					}
					if(PackageListing.packagesAvailability.contains("SUN")){
						if (grid_day.toUpperCase().equalsIgnoreCase("SUN")) {
							dayView.setTextColor(Color.RED);
							if ((Integer.parseInt(grid_year) == (Integer
									.parseInt(today_year)) && (Integer
									.parseInt(grid_mon) == (Integer.parseInt(today_mon)) && (Integer
									.parseInt(gridvalue) < (Integer.parseInt(today_day)))))) {
								dayView.setTextColor(Color.GRAY);
								ll_date.setBackgroundColor(Color.parseColor("#ededed"));
								dayView.setClickable(true);
								ll_date.setClickable(true);
							}

						}
					}
				}
				

			} else if (Integer.parseInt(grid_year) < Integer
					.parseInt(today_year)) {
				dayView.setTextColor(Color.GRAY);
				ll_date.setBackgroundColor(Color.parseColor("#ededed"));
				dayView.setClickable(true);
				ll_date.setClickable(true);
			} else {
				dayView.setTextColor(Color.BLACK);
				dayView.setClickable(false);
				ll_date.setClickable(false);
				dayView.setVisibility(View.VISIBLE);
				ll_date.setVisibility(View.VISIBLE);
			}
		}
		System.out.println("zzzz current date" + curentDateString);
		if (dayString.get(position).equals(curentDateString)) {
			setSelected(v);
			previousView = v;
			// v.setBackgroundResource(R.drawable.list_item_background);
		} else {
			v.setBackgroundResource(R.drawable.list_item_background);
		}
		dayView.setText(gridvalue);

		// create date string for comparison
		String date = dayString.get(position);

		if (date.length() == 1) {
			date = "0" + date;
		}
		String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}

		// show icon if date is not empty and it exists in the items array
		ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
		if (date.length() > 0 && items != null && items.contains(date)) {
			iw.setVisibility(View.VISIBLE);
		} else {
			iw.setVisibility(View.INVISIBLE);
		}
		return v;
	}

	public View setSelected(View view) {
		if (previousView != null) {
			previousView.setBackgroundResource(R.drawable.list_item_background);
		}
		previousView = view;
		view.setBackgroundResource(R.drawable.curv_cir);
		// notifyDataSetChanged();
		return view;
	}

	public void refreshDays() {
		// clear items
		items.clear();
		dayString.clear();
		Locale.setDefault(Locale.US);
		pmonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);

		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {

			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			dayString.add(itemvalue);
		}
	}

	private int getMaxP() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return maxP;
	}

	public String getCurrentDate() {
		String cur_date = curentDateString;

		return cur_date;

	}

	public View setDateSelected(View view) {
		if (previousView != null) {
			previousView.setBackgroundResource(R.drawable.list_item_background);
		}
		previousView = view;
		view.setBackgroundResource(R.drawable.curv_cir);
		// notifyDataSetChanged();
		return view;
	}

}
