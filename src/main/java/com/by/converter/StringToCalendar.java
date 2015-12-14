package com.by.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.core.convert.converter.Converter;

public class StringToCalendar implements Converter<String, Calendar> {
	private final DateFormat formate = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Calendar convert(String source) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(formate.parse(source));
		} catch (ParseException e) {
			return null;
		}
		return cal;
	}
}
