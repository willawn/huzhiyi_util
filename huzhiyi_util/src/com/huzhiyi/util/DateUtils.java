/*
 * @(#)DateUtils.java 1.0 2010/07/25
 *
 * Copyright 2010 �ŷ�Ѷ , Inc. All rights reserved.
 */
package com.huzhiyi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.huzhiyi.model.abstraction.ValueSection;

/**
 * 
 * @ClassName: DateUtils
 * @Description: TODO(���ڲ���������)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class DateUtils {
	// ������ʽ������
	protected final static ConcurrentMap<String, DateFormat> FORMAT_CONTAINER = new ConcurrentHashMap<String, DateFormat>();

	public static String format(String dateString) {
		if (StringUtils.isEmpty(dateString)) {
			return dateString;
		}
		
		String fmtStr = "yyyy-MM-dd HH:mm:ss";
		Date date = null;
		try {
			date = parse(dateString, fmtStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return format(date, fmtStr);
	}

	public static Date formatDate(Date date) {
		String fmtStr = "yyyy-MM-dd HH:mm:ss";
		try {
			return parse(format(date, fmtStr), fmtStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * ���ڸ�ʽ��
	 * 
	 * @param date
	 * @param fmtStr
	 *            ��ʽ���ַ���
	 * @return
	 */
	public static String format(Date date, String fmtStr) {
		DateFormat fmt = FORMAT_CONTAINER.get(fmtStr);
		if (fmt == null) {
			fmt = new SimpleDateFormat(fmtStr);
			FORMAT_CONTAINER.put(fmtStr, fmt);
		}
		return fmt.format(date);
	}

	/**
	 * ʹ�������ʽ��
	 * 
	 * @param date
	 * @param dateStyle
	 *            DateFormat�ķ����
	 * @param locale
	 * @return
	 */
	public static String format(Date date, int dateStyle, Locale locale) {
		String str = locale.toString() + dateStyle;
		DateFormat fmt = FORMAT_CONTAINER.get(str);
		if (fmt == null) {
			fmt = DateFormat.getDateInstance(dateStyle, locale);
			FORMAT_CONTAINER.put(str, fmt);
		}
		return fmt.format(date);
	}

	/**
	 * ʹ��DateFormat.DEFAULT����ʽ��
	 * 
	 * @param date
	 * @param locale
	 * @return
	 */
	public static String format(Date date, Locale locale) {
		return format(date, DateFormat.DEFAULT, locale);
	}

	/**
	 * ʹ��Ĭ�������ʽ��
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, Locale.getDefault());
	}

	/**
	 * �����ַ�����ת��Ϊ����
	 * 
	 * @param dateString
	 * @param fmtStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String dateString, String fmtStr) throws ParseException {
		DateFormat fmt = FORMAT_CONTAINER.get(fmtStr);
		if (fmt == null) {
			fmt = new SimpleDateFormat(fmtStr);
			FORMAT_CONTAINER.put(fmtStr, fmt);
		}
		return fmt.parse(dateString);
	}

	/**
	 * �ж�����Ƿ�δ����
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && ((year % 100 != 0) | (year % 400 == 0))) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ��������ݵĵڼ���
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * ��������ݵĵڼ���
	 * 
	 * @return
	 */
	public static int getWeekOfYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * ��ȡ�������·ݵĵ���
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * ��ȡ�������·ݵĵ���
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * ��ȡ��ݵڼ��ܵ���ʼ���ںͽ�������
	 * 
	 * @param weekOfYear
	 * @return
	 */
	public static ValueSection<Date> getWeekSectionOfYear(final Calendar c, final int weekOfYear) {
		final int firstDayofweek = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		final int lastDayofweek = firstDayofweek + 6;
		c.set(Calendar.DAY_OF_WEEK, firstDayofweek);
		c.set(Calendar.WEEK_OF_YEAR, weekOfYear);
		return new ValueSection<Date>() {
			public Date getBegin() {
				return c.getTime();
			}

			public Date getEnd() {
				c.set(Calendar.DAY_OF_WEEK, lastDayofweek);
				return c.getTime();
			}
		};
	}

	/**
	 * ��ȡ��ݵڼ��ܵ���ʼ���ںͽ�������
	 * 
	 * @param weekOfYear
	 * @return
	 */
	public static ValueSection<Date> getWeekSectionOfYear(final int weekOfYear) {
		Calendar c = Calendar.getInstance();
		return getWeekSectionOfYear(c, weekOfYear);
	}

	/**
	 * ��ȡ���ܵĿ�ʼ�ͽ���ʱ��
	 * 
	 * @return
	 */
	public static ValueSection<Date> getWeekSectionOfYear() {
		Calendar c = Calendar.getInstance();
		return getWeekSectionOfYear(c, c.get(Calendar.WEEK_OF_YEAR));
	}
	
	/**
	 * @Title: setDate
	 * @Description: ����ָ�����ڣ����ӻ��С��
	 * 		<p>
	 * @author willter
	 * @date 2013-4-8
	 * 		<p>
	 * @param date
	 * @param field
	 * @param value
	 * @return
	 */
	public static Date addDate(Date date, int field, int value) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, value);
		
		return c.getTime();
	}
}
