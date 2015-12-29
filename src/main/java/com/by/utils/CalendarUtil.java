package com.by.utils;

import java.util.Calendar;

public class CalendarUtil {
	public static String getToday() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR) + "-" + c.get(Calendar.DAY_OF_YEAR);
	}
}
