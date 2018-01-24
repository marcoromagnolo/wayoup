package com.wayoup.server.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marco Romagnolo on 02/05/2015.
 */
public class DateUtil {

    public static Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return (dateStr == null) ? null : sdf.parse(dateStr);
        } catch (ParseException ignored) {
            return null;
        }
    }

    public static Date parseDateTime(String dateTimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        try {
            return (dateTimeStr == null) ? null : sdf.parse(dateTimeStr);
        } catch (ParseException ignored) {
            return null;
        }
    }
}
