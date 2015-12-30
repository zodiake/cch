package com.by.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yagamai on 15-12-30.
 */
public class CalendarToString implements Converter<Calendar, String> {
    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String convert(Calendar source) {
        return format.format(source.getTime());
    }
}
