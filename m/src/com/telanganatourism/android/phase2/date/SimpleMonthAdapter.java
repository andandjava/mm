/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.telanganatourism.android.phase2.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.telanganatourism.android.phase2.Constants;

import com.telanganatourism.android.phase2.date.SimpleMonthView.OnDayClickListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ParseException;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;

/**
 * An adapter for a list of {@link SimpleMonthView} items.
 */
public class SimpleMonthAdapter extends BaseAdapter implements
		OnDayClickListener {

	private static final String TAG = "SimpleMonthAdapter";

	private final Context mContext;
	private final DatePickerController mController;

	private CalendarDay mSelectedDay;
	private static Calendar calendar;
	protected static int WEEK_7_OVERHANG_HEIGHT = 7;
	protected static final int MONTHS_IN_YEAR = 12;
	Date checkin_date;

	/**
	 * A convenience class to represent a specific date.
	 */
	public static class CalendarDay {

		int year;
		int month;
		int day;

		public CalendarDay() {
			setTime(System.currentTimeMillis());
		}

		public CalendarDay(long timeInMillis) {
			setTime(timeInMillis);
		}

		public CalendarDay(Calendar calendar) {			
			
			
			if(Constants.date_flg == 0){
				
				year = calendar.get(Calendar.YEAR);
				month = calendar.get(Calendar.MONTH);
				day = calendar.get(Calendar.DAY_OF_MONTH);
				
			}else{
				System.out.println("checkin"+ Constants.CheckOutDateStr);
				
				try {
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
					Date date = format.parse(Constants.CheckOutDateStr);
					
					Calendar cal=Calendar.getInstance();
					format.format(date);
					cal=format.getCalendar();
					
					
					
					Calendar calendar1 = cal;
					Date today = calendar1.getTime();

//					calendar1.add(Calendar.DAY_OF_YEAR, 1);
					Date tomorrow = calendar1.getTime();
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

					String todayAsString = dateFormat.format(today);
					String tomorrowAsString = dateFormat.format(tomorrow);

					System.out.println("Today : "+todayAsString);
					System.out.println("Tomorrow : "+tomorrowAsString);
					
					String[] splitStr = tomorrowAsString.split("-");
					
					year = Integer.parseInt(splitStr[2]);
					month = Integer.parseInt(splitStr[1]) - 1;
					day = Integer.parseInt(splitStr[0]);
					
					
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

		public CalendarDay(int year, int month, int day) {
			setDay(year, month, day);
		}

		public void set(CalendarDay date) {
			year = date.year;
			month = date.month;
			day = date.day;
		}

		public void setDay(int year, int month, int day) {
			this.year = year;
			this.month = month;
			this.day = day;
		}

		private void setTime(long timeInMillis) {
			
			
//			if(Constants.date_flg == 0){
				if (calendar == null) {
					calendar = Calendar.getInstance();
				}
				calendar.setTimeInMillis(timeInMillis);
				month = calendar.get(Calendar.MONTH);
				year = calendar.get(Calendar.YEAR);
				day = calendar.get(Calendar.DAY_OF_MONTH);
//			}else{
//				day = Constants.selected_day;
//				month = Constants.selected_month;
//				year = Constants.selected_year;
//			}
		}
	}

	int cday, cmonth, cyear;

	public SimpleMonthAdapter(Context context, DatePickerController controller) {
		mContext = context;
		mController = controller;
		init();
		setSelectedDay(mController.getSelectedDay());
	}

	public SimpleMonthAdapter(Context context, DatePickerController controller,
			int current_day, int current_month, int current_year) {
		// TODO Auto-generated constructor stub

		mContext = context;
		mController = controller;
		cday = current_day;
		cmonth = current_month;
		cyear = current_year;

		System.out.println(" date " + cday + "-" + cmonth + "-" + cyear);

		init();
		setSelectedDay(mController.getSelectedDay());

	}

	/**
	 * Updates the selected day and related parameters.
	 * 
	 * @param day
	 *            The day to highlight
	 */
	public void setSelectedDay(CalendarDay day) {
		mSelectedDay = day;
		notifyDataSetChanged();
	}

	public CalendarDay getSelectedDay() {
		return mSelectedDay;
	}

	/**
	 * Set up the gesture detector and selected time
	 */
	protected void init() {
		mSelectedDay = new CalendarDay(System.currentTimeMillis());
	}

	@Override
	public int getCount() {
		return ((mController.getMaxYear() - mController.getMinYear()) + 1)
				* MONTHS_IN_YEAR;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SimpleMonthView v;
		HashMap<String, Integer> drawingParams = null;
		if (convertView != null) {
			v = (SimpleMonthView) convertView;
			// We store the drawing parameters in the view so it can be recycled
			drawingParams = (HashMap<String, Integer>) v.getTag();
		} else {
			v = new SimpleMonthView(mContext);
			// Set up the new view
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			v.setLayoutParams(params);
			v.setClickable(true);
			v.setOnDayClickListener(this);
		}
		if (drawingParams == null) {
			drawingParams = new HashMap<String, Integer>();
		}
		drawingParams.clear();

		final int month = position % MONTHS_IN_YEAR;
		final int year = position / MONTHS_IN_YEAR + mController.getMinYear();
		Log.d(TAG, "Year: " + year + ", Month: " + month);

		int selectedDay = -1;
		if (isSelectedDayInMonth(year, month)) {
			selectedDay = mSelectedDay.day;
		}

		// Invokes requestLayout() to ensure that the recycled view is set with
		// the appropriate
		// height/number of weeks before being displayed.
		v.reuse();

		drawingParams
				.put(SimpleMonthView.VIEW_PARAMS_SELECTED_DAY, selectedDay);
		drawingParams.put(SimpleMonthView.VIEW_PARAMS_YEAR, year);
		drawingParams.put(SimpleMonthView.VIEW_PARAMS_MONTH, month);
		drawingParams.put(SimpleMonthView.VIEW_PARAMS_WEEK_START,
				mController.getFirstDayOfWeek());
		v.setMonthParams(drawingParams);
		v.invalidate();
		return v;
	}

	private boolean isSelectedDayInMonth(int year, int month) {
		return mSelectedDay.year == year && mSelectedDay.month == month;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public void onDayClick(SimpleMonthView view, CalendarDay day1) {

		// if (day != null) {
		// onDayTapped(day);
		// }

		System.out.println("day is" + day1.day);
		// System.out.println("checkin"+);

		if (day1 != null) {

			System.out.println("checkin" + day1 + "" + Constants.CheckInDate);

			/*
			 * if( day1.day>= cday && day1.month>=cmonth && day1.year>=cyear){
			 * 
			 * onDayTapped(day1);
			 * 
			 * }else if(day1.year>=cyear && day1.month>=cmonth){
			 * onDayTapped(day1); }else if(day1.year>=cyear){ onDayTapped(day1);
			 * }
			 */

			if (Constants.date_flg == 0) {

				int current_day = calendar.get(Calendar.DAY_OF_MONTH);
				int current_month = calendar.get(Calendar.MONTH);
				int current_year = calendar.get(Calendar.YEAR);
				
				Constants.selected_day = day1.day;
				Constants.selected_month = day1.month;
				Constants.selected_year = day1.year;

				System.out.println("day" + current_day + "month"
						+ current_month + "year" + current_year);
				System.out.println("day1" + day1.day + "month" + day1.month
						+ "year" + day1.year);

				if (day1.year > current_year) {
					onDayTapped(day1);
				} else {

					if (day1.year == current_year && day1.month > current_month) {
						onDayTapped(day1);
					} else {
						if (day1.day >= current_day
								&& day1.month == current_month) {
							onDayTapped(day1);
						}
					}
				}
			} else {

				int current_day = Constants.selected_day;
				int current_month = Constants.selected_month;
				int current_year = Constants.selected_year;

//				System.out.println("day" + current_day + "month"
//						+ current_month + "year" + current_year);
//				System.out.println("day1" + day1.day + "month" + day1.month
//						+ "year" + day1.year);

				if (day1.year > current_year) {
					onDayTapped(day1);
				} else {

					if (day1.year == current_year && day1.month > current_month) {
						onDayTapped(day1);
					} else {
						if (day1.day > current_day
								&& day1.month == current_month) {
							onDayTapped(day1);
						}
					}
				}
			}

		}
	}

	/**
	 * Maintains the same hour/min/sec but moves the day to the tapped day.
	 * 
	 * @param day
	 *            The day that was tapped
	 */
	protected void onDayTapped(CalendarDay day) {
		mController.tryVibrate();
		mController.onDayOfMonthSelected(day.year, day.month, day.day);
		setSelectedDay(day);
	}
}