package com.isg.ifrend.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author kristine.furto
 *
 */
public class DateUtil {
	private static String defaultFormat = "MM/dd/yyyy HH:mm:ss.SSS";
	private static final SimpleDateFormat df = new SimpleDateFormat(
			defaultFormat);
	public static final String JAVA_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	// TODO From db/properties file date formats

	public static String getDefualtDateFormat() {
		return defaultFormat;
	}

	/**
	 * For VIEW: Formats a Date to return a String with customized format.
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		if (!(format == null || format.equals(""))) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		} else {
			return df.format(date);
		}
	}

	public static String format(Date date) {
		return new SimpleDateFormat(defaultFormat).format(date);
	}
	
	public static Date getCurrentDate(){
		return Calendar.getInstance().getTime();
	}

	public static Date add(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, i);
		return calendar.getTime();
	}

	public static Date setToMidnight(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}