package com.telanganatourism.android.phase2.date;

import java.util.Calendar;

import android.graphics.Typeface;

public class Constants {
	
//	public static Typeface ProximaNova_Regular, ProximaNova_Light, ProximaNova_Bold;
	
	
	static Calendar calendar=Calendar.getInstance();
	
	public static int current_day = calendar.get(Calendar.DAY_OF_MONTH);
	public static  int current_month = calendar.get(Calendar.MONTH);
	public static  int current_year = calendar.get(Calendar.YEAR);
	
	
	
}
