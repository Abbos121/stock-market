package com.vention.stockmarket.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A utility class for working with dates.
 */
public class DateUtils {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy");

    public static Date convertStringToDate(String date) {
        try {
            return (DATE_FORMATTER.parse(date));
        } catch (ParseException e) {
            
        }
        return null;
    }

    public static String convertDateToString(Date date) {
        return DATE_FORMATTER.format(date);
    }


    public static java.sql.Date convertUtilDateToSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }
}
