package com.cat.boot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cat.boot.exception.CatException;

public class CalendarUtil {

	public static String getYyyyMmDd(Calendar c) {
		if (c == null)
			return "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(c.getTime());
	}

	public static String getYyyyMmDdHHmmss(Calendar c) {

		if (c == null)
			return "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(c.getTime());
	}

	public static String getYyyyMmDdZh(Calendar c) {
		if (c == null)
			return "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		return df.format(c.getTime());
	}

	public static String getYyyyMmDdHHmmss(Calendar c, int i) {
		if (c == null)
			return "";
		c.add(Calendar.DAY_OF_MONTH, i);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(c.getTime());
	}

	public static Calendar now() {
		return Calendar.getInstance();
	}

	public static Calendar clearTime(Calendar c, boolean returnClone) {

		Calendar result = null;
		if (c == null)
			return result;
		if (returnClone) {
			result = (Calendar) c.clone();
		} else {
			result = c;
		}
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		return result;
	}

	public static Calendar StringToCalendar(String value) throws CatException {

		if (value == null) {
			return null;
		}
		Calendar result = null;
		if (value instanceof String && !value.equals("")) {
			try {
				Date date = CalendarUtil.strToDateTime(value);
				result = Calendar.getInstance();
				result.setTime(date);
			} catch (CatException e) {
				throw new CatException("时间转换出现了错误");
			}
		}
		return result;
	}

	public static final Date strToDateTime(String dateTimeString) throws CatException {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date result = df.parse((String) dateTimeString);
			return result;

		} catch (ParseException e) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date result = df.parse((String) dateTimeString);
				return result;
			} catch (ParseException e1) {

				try {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date result = df.parse((String) dateTimeString);
					return result;
				} catch (ParseException e2) {
					throw new CatException("时间转换出现了错误");
				}

			}
		}
	}

	public static String dateToWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static long compareTo(String t1, String t2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d1;
		try {
			d1 = sdf.parse("2000-01-01 " + t1);
			Date d2 = sdf.parse("2000-01-01 " + t2);
			return d1.getTime() - d2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static boolean compareTo(String t1, String t2, String t3, String t4) {
		// 当开始时间 或者结束时间在已预约之间
		if (compareTo(t1, t3) >= 0 && CalendarUtil.compareTo(t1, t4) < 0) {
			return true;
		}
		if (compareTo(t2, t3) > 0 && CalendarUtil.compareTo(t2, t4) <= 0) {
			return true;
		}
		// 当开始时间在已预约之前 结束时间在已预约之后
		if (compareTo(t1, t3) < 0 && CalendarUtil.compareTo(t2, t4) > 0) {
			return true;
		}
		// 当开始时间和结束时间等于已预约
		if (compareTo(t1, t3) == 0 && CalendarUtil.compareTo(t2, t4) == 0) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		// System.out.println(compareTo("09:00", "07:00"));
		List<String> userId = new ArrayList<String>();
		for (int i = 0; i < 10001; i++) {
			userId.add(String.valueOf(i));
		}
		for (int i = 0; i <= userId.size() / 1000; i++) {
			List<String> list = userId.subList(i * 1000,
					(i + 1) * 1000 > userId.size() ? userId.size() : (i + 1) * 1000);
			System.out.println(list.size() + "  " + list.get(0) + "   " + list.get(list.size() - 1));
		}
	}
}
