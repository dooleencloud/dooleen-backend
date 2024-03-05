package com.dooleen.common.core.utils;


import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("serial")
public abstract class DateUtilPro {
	/**
	 * 时区误差
	 */
	private static long timeZoneError = 0;
	/**
	 * 添加更多支持日期字符串格式 默认支持：yyyyMMdd[ HHmmss]、yyyy-MM-dd[ HH:mm:ss]、yyyy/MM/dd[
	 * HH:mm:ss]
	 */
	private static List<String> patternList = new ArrayList<>();

	/**
	 * 全局工作日配置
	 */
	private static WorkdayConfig workdayConfig;

	static {
		timeZoneError = new DateTime("1970-01-01").getTime();
		// 添加更多日期格式
//		patternList.add("dd-MM-yyyy");
	}

	/**
	 * 工作日设置
	 * 
	 * @return
	 */
	public static WorkdayConfig workdayConfig() {
		if (workdayConfig == null) {
			workdayConfig = new WorkdayConfig();
		}
		return workdayConfig;
	}

	/**
	 * 获取一个当前的工作日配置
	 * 
	 * @return
	 */
	public static WorkdayConfig getWorkdayConfig() {
		try {
			return (WorkdayConfig) workdayConfig().clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return new WorkdayConfig();
	}

	/**
	 * 日期格式化成字符串
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null)
			return null;
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		return fmt.format(date);
	}

	/**
	 * 根据语言和格式，格式化日期成字符串
	 *
	 * @param date
	 * @param pattern
	 * @param locale
	 * @return
	 */
	public static String format(Date date, String pattern, Locale locale) {
		DateFormatSymbols symbols = new DateFormatSymbols(locale);
		SimpleDateFormat fmt = new SimpleDateFormat(pattern, symbols);
		return fmt.format(date);
	}

	/**
	 * 日期字符串转换成日期
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static DateTime parseDate(String date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return new DateTime(format.parse(date));
		} catch (ParseException e) {
			throw new IllegalArgumentException("字符串转日期失败，日期'" + date + "'与格式'" + pattern + "'不对应");
		}
	}

	/**
	 * <p>
	 * 日期字符串转换成日期，自动识别格式
	 * </p>
	 * 默认支持yyyyMMdd[ HHmmss]、yyyy-MM-dd[ HH:mm:ss]、yyyy/MM/dd[ HH:mm:ss]<br>
	 * 如果频繁使用该方法转换日期，又需要效率，那尽量使用 {@link #parseDate(String, String)} 方法
	 *
	 * @param date
	 * @return
	 * @see DateUtilPro#patternList 自定义支持列表
	 */
	public static DateTime parseDate(String date) {
		if (patternList != null && !patternList.isEmpty()) {
			for (String pattern : patternList) {
				try {
					DateTime dateTime = parseDate(date, pattern);
					if (dateTime != null) {
						return dateTime;
					}
				} finally {
				}
			}
		}
		String d = date.replaceAll("[^\\d]", "");
		String pattern = "yyyyMMdd";
		if (d.length() > pattern.length()) {
			pattern += "HH";
		}
		if (d.length() > pattern.length()) {
			pattern += "mm";
		}
		if (d.length() > pattern.length()) {
			pattern += "ss";
		}
		if (d.length() > pattern.length()) {
			pattern += "SSS";
		}
		try {
			return parseDate(d, pattern);
		} catch (Exception e) {
			throw new IllegalArgumentException("无法识别日期：" + date);
		}
	}

	/**
	 * 判断字符串是否是日期
	 *
	 * @param date
	 * @return
	 */
	public static boolean isDate(String date) {
		try {
			return parseDate(date) != null;
		} catch (Exception e) {
			return false;
		}
	}

	public static DateTime date() {
		return new DateTime();
	}

	public static DateTime date(Date date) {
		return new DateTime(date);
	}

	public static DateTime date(int year, int month, int date, int hour, int minute, int second) {
		return new DateTime().year(year).month(month).day(date).hour(hour).minute(minute).second(second);
	}

	public static DateTime date(int year, int month, int date) {
		return date(year, month, date, 0, 0, 0);
	}

	public static class DateTime extends Date {
		private Calendar calendar = Calendar.getInstance();
		private String pattern;
		private WorkdayConfig workdayConfig;

		private DateTime() {
			super();
			calendar.setTime(this);
			workdayConfig = DateUtilPro.workdayConfig();
		}

		private DateTime(long time) {
			super(time);
			calendar.setTime(this);
			workdayConfig = DateUtilPro.workdayConfig();
		}

		private DateTime(Date date) {
			this(date.getTime());
		}

		private DateTime(String date) {
			this(DateUtilPro.parseDate(date));
		}

		@Override
		public String toString() {
			return toString(StringUtils.defaultString(pattern, "yyyy-MM-dd HH:mm:ss"));
		}

		public String toString(String pattern) {
			return format(this, pattern);
		}

		public String toString(String pattern, Locale locale) {
			return format(this, pattern, locale);
		}

		public Calendar toCalendar() {
			return calendar;
		}

		public DateTime workdayConfig(WorkdayConfig workdayConfig) {
			this.workdayConfig = workdayConfig;
			return this;
		}

		/**
		 * 获取日期格式
		 */
		public String getPattern() {
			return pattern;
		}

		/**
		 * 设置日期格式
		 *
		 * @param pattern
		 */
		public DateTime pattern(String pattern) {
			if (pattern != null && pattern.trim().equals("")) {
				throw new IllegalArgumentException("日期格式不能设置为空字符串");
			}
			this.pattern = pattern;
			return this;
		}

		/**
		 * 设置时间
		 *
		 * @param field 精确度
		 * @param value 值 <br/>
		 *              {@link Calendar#SECOND} <br/>
		 *              {@link Calendar#MINUTE} <br/>
		 *              {@link Calendar#HOUR} <br/>
		 *              {@link Calendar#DATE} <br/>
		 *              {@link Calendar#MONTH} <br/>
		 *              {@link Calendar#YEAR}
		 * @return
		 */
		public DateTime set(int field, int value) {
			if (field == Calendar.MONTH) {
				value -= 1;
			}

			calendar.set(field, value);
			setTime(calendar.getTimeInMillis());
			return this;
		}

		/**
		 * 设置秒
		 *
		 * @param value
		 * @return
		 */
		public DateTime second(int value) {
			return set(Calendar.SECOND, value);
		}

		/**
		 * 设置分
		 *
		 * @param value
		 * @return
		 */
		public DateTime minute(int value) {
			return set(Calendar.MINUTE, value);
		}

		/**
		 * 设置时
		 *
		 * @param value
		 * @return
		 */
		public DateTime hour(int value) {
			return set(Calendar.HOUR, value);
		}

		/**
		 * 设置天
		 *
		 * @param value
		 * @return
		 */
		public DateTime day(int value) {
			return set(Calendar.DATE, value);
		}

		/**
		 * 设置月
		 *
		 * @param value
		 * @return
		 */
		public DateTime month(int value) {
			return set(Calendar.MONTH, value);
		}

		/**
		 * 设置年
		 *
		 * @param value
		 * @return
		 */
		public DateTime year(int value) {
			return set(Calendar.YEAR, value);
		}

		/**
		 * 添加时间
		 *
		 * @param field  精确度 <br/>
		 *               {@link Calendar#SECOND} <br/>
		 *               {@link Calendar#MINUTE} <br/>
		 *               {@link Calendar#HOUR} <br/>
		 *               {@link Calendar#DATE} <br/>
		 *               {@link Calendar#MONTH} <br/>
		 *               {@link Calendar#YEAR}
		 * @param amount 添加数
		 * @return 添加后的时间
		 */
		public DateTime add(int field, int amount) {
			calendar.add(field, amount);
			setTime(calendar.getTimeInMillis());
			return this;
		}

		/**
		 * 添加秒
		 *
		 * @param minute
		 * @return
		 */
		public DateTime addSecond(int minute) {
			return add(Calendar.SECOND, minute);
		}

		/**
		 * 添加分钟
		 *
		 * @param minute
		 * @return
		 */
		public DateTime addMinute(int minute) {
			return add(Calendar.MINUTE, minute);
		}

		/**
		 * 添加小时
		 *
		 * @param hours
		 * @return
		 */
		public DateTime addHours(int hours) {
			return add(Calendar.HOUR, hours);
		}

		/**
		 * 添加天数
		 *
		 * @param days
		 * @return
		 */
		public DateTime addDays(int days) {
			return add(Calendar.DATE, days);
		}

		/**
		 * 添加月份
		 *
		 * @param months
		 * @return
		 */
		public DateTime addMonths(int months) {
			return add(Calendar.MONTH, months);
		}

		/**
		 * 添加年份
		 *
		 * @param years
		 * @return
		 */
		public DateTime addYears(int years) {
			return add(Calendar.YEAR, years);
		}

		/**
		 * 比较两个时间
		 *
		 * @param time
		 * @param field 精确度 <br/>
		 *              {@link Calendar#SECOND} <br/>
		 *              {@link Calendar#MINUTE} <br/>
		 *              {@link Calendar#HOUR} <br/>
		 *              {@link Calendar#DATE} <br/>
		 *              {@link Calendar#MONTH} <br/>
		 *              {@link Calendar#YEAR}
		 * @return 两时间相差的值，结果=0:time1=time2，结果<0:time1&gt;time2，结果>0:time1>time2
		 */
		public int compare(Date time, int field) {
			if (time == null) {
				throw new IllegalArgumentException();
			}

			Calendar c = new DateTime(time).toCalendar();

			int accuracy = 1000;
			switch (field) {
			case Calendar.SECOND:
				accuracy = 1000;
				break;
			case Calendar.MINUTE:
				accuracy = 60 * 1000;
				break;
			case Calendar.HOUR:
				accuracy = 60 * 60 * 1000;
				break;
			case Calendar.DATE:
				accuracy = 24 * 60 * 60 * 1000;
				break;
			case Calendar.MONTH:
				int month = calendar.get(Calendar.MONTH) - c.get(Calendar.MONTH);
				int year = calendar.get(Calendar.YEAR) - c.get(Calendar.YEAR);
				return month + year * 12;
			case Calendar.YEAR:
				year = calendar.get(Calendar.YEAR) - c.get(Calendar.YEAR);
				return year;
			}
			int t1 = (int) ((calendar.getTimeInMillis() - timeZoneError) / accuracy);
			int t2 = (int) ((c.getTimeInMillis() - timeZoneError) / accuracy);
			return t1 - t2;
		}

		/**
		 * 比较两个时间，精确到秒
		 *
		 * @param time
		 * @return 两时间相差的秒数
		 */
		public int compareSeconds(Date time) {
			return compare(time, Calendar.SECOND);
		}

		/**
		 * 比较两个时间，精确到分钟
		 *
		 * @param time
		 * @return 两时间相差的分钟数
		 */
		public int compareMinutes(Date time) {
			return compare(time, Calendar.MINUTE);
		}

		/**
		 * 比较两个时间，精确到小时
		 *
		 * @param time
		 * @return 两时间相差的小时数
		 */
		public int compareHours(Date time) {
			return compare(time, Calendar.HOUR);
		}

		/**
		 * 比较两个日期，精确到天
		 *
		 * @param date
		 * @return 两日期相差的天数
		 */
		public int compareDays(Date date) {
			return compare(date, Calendar.DATE);
		}

		/**
		 * 判断时间在指定时间之前
		 *
		 * @param field 精确度 <br/>
		 *              {@link Calendar#SECOND} <br/>
		 *              {@link Calendar#MINUTE} <br/>
		 *              {@link Calendar#HOUR} <br/>
		 *              {@link Calendar#DATE} <br/>
		 *              {@link Calendar#MONTH} <br/>
		 *              {@link Calendar#YEAR}
		 * @return
		 */
		public boolean before(Date date, int field) {
			return (compare(date, field) < 0);
		}

		/**
		 * 判断时间在指定时间之前（精确到毫秒）
		 *
		 * @return
		 */
		@Override
		public boolean before(Date date) {
			return this.before(date, Calendar.MILLISECOND);
		}

		/**
		 * 判断时间在指定时间之前（精确到秒）
		 *
		 * @return
		 */
		public boolean beforeOfSecond(Date date) {
			return this.before(date, Calendar.SECOND);
		}

		/**
		 * 判断时间在指定时间之前（精确到分）
		 *
		 * @return
		 */
		public boolean beforeOfMinute(Date date) {
			return this.before(date, Calendar.MINUTE);
		}

		/**
		 * 判断时间在指定时间之前（精确到小时）
		 *
		 * @return
		 */
		public boolean beforeOfHour(Date date) {
			return this.before(date, Calendar.HOUR);
		}

		/**
		 * 判断时间在指定时间之前（精确到天）
		 *
		 * @return
		 */
		public boolean beforeOfDay(Date date) {
			return this.before(date, Calendar.DATE);
		}

		/**
		 * 判断时间在指定时间之后
		 *
		 * @param field 精确度 <br/>
		 *              {@link Calendar#SECOND} <br/>
		 *              {@link Calendar#MINUTE} <br/>
		 *              {@link Calendar#HOUR} <br/>
		 *              {@link Calendar#DATE} <br/>
		 *              {@link Calendar#MONTH} <br/>
		 *              {@link Calendar#YEAR}
		 * @return
		 */
		public boolean after(Date date, int field) {
			return (compare(date, field) > 0);
		}

		/**
		 * 判断时间在指定时间之后（精确到毫秒）
		 *
		 * @return
		 */
		@Override
		public boolean after(Date date) {
			return this.after(date, Calendar.MILLISECOND);
		}

		/**
		 * 判断时间在指定时间之后（精确到秒）
		 *
		 * @return
		 */
		public boolean afterOfSecond(Date date) {
			return this.after(date, Calendar.SECOND);
		}

		/**
		 * 判断时间在指定时间之后（精确到分）
		 *
		 * @return
		 */
		public boolean afterOfMinute(Date date) {
			return this.after(date, Calendar.MINUTE);
		}

		/**
		 * 判断时间在指定时间之后（精确到小时）
		 *
		 * @return
		 */
		public boolean afterOfHour(Date date) {
			return this.after(date, Calendar.HOUR);
		}

		/**
		 * 判断时间在指定时间之后（精确到天）
		 *
		 * @return
		 */
		public boolean afterOfDay(Date date) {
			return this.after(date, Calendar.DATE);
		}

		/**
		 * 判断时间和指定时间是否是同一个时间
		 *
		 * @param field 精确度 <br/>
		 *              {@link Calendar#SECOND} <br/>
		 *              {@link Calendar#MINUTE} <br/>
		 *              {@link Calendar#HOUR} <br/>
		 *              {@link Calendar#DATE} <br/>
		 *              {@link Calendar#MONTH} <br/>
		 *              {@link Calendar#YEAR}
		 * @return
		 */
		public boolean same(Date date, int field) {
			return (compare(date, field) == 0);
		}

		/**
		 * 判断时间和指定时间是否是一个分钟（包含年月日时）
		 *
		 * @return
		 */
		public boolean sameMinute(Date date) {
			return this.same(date, Calendar.MINUTE);
		}

		/**
		 * 判断时间和指定时间是否是一个小时（包含年月日）
		 *
		 * @return
		 */
		public boolean sameHour(Date date) {
			return this.same(date, Calendar.HOUR);
		}

		/**
		 * 判断时间和指定时间是否是一天
		 *
		 * @return
		 */
		public boolean sameDay(Date date) {
			return this.same(date, Calendar.DATE);
		}

		/**
		 * 判断时间是否在开始时间和结束时间之间
		 *
		 * @param startTime
		 * @param endTime
		 * @param field     精确度 <br/>
		 *                  {@link Calendar#SECOND} <br/>
		 *                  {@link Calendar#MINUTE} <br/>
		 *                  {@link Calendar#HOUR} <br/>
		 *                  {@link Calendar#DATE} <br/>
		 *                  {@link Calendar#MONTH} <br/>
		 *                  {@link Calendar#YEAR}
		 * @return
		 */
		public boolean isBetween(Date startTime, Date endTime, int field) {
			if (compare(startTime, field) >= 0 && compare(endTime, field) <= 0) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * 判断时间是否在开始时间和结束时间之间，精确到秒
		 *
		 * @param startTime
		 * @param endTime
		 * @return
		 */
		public boolean isBetweenOfSecond(Date startTime, Date endTime) {
			return isBetween(startTime, endTime, Calendar.SECOND);
		}

		/**
		 * 判断日期是否在开始日期和结束时间日期之间，精确到分
		 *
		 * @param startTime
		 * @param endTime
		 * @return
		 */
		public boolean isBetweenOfMinute(Date startTime, Date endTime) {
			return isBetween(startTime, endTime, Calendar.MINUTE);
		}

		/**
		 * 判断今天是否在开始日期和结束时间日期之间，精确到小时
		 *
		 * @param startDate
		 * @param endDate
		 * @return
		 */
		public boolean isBetweenOfHour(Date startDate, Date endDate) {
			return isBetween(startDate, endDate, Calendar.HOUR);
		}

		/**
		 * 判断今天是否在开始日期和结束时间日期之间，精确到天
		 *
		 * @param startDate
		 * @param endDate
		 * @return
		 */
		public boolean isBetweenOfDay(Date startDate, Date endDate) {
			return isBetween(startDate, endDate, Calendar.DATE);
		}

		/**
		 * 转到月份最后一天日期
		 *
		 * @return
		 */
		public DateTime monthLastDate() {
			addMonths(1).set(Calendar.DAY_OF_MONTH, 0);
			return this;
		}

		/**
		 * 转到所在星期的第一天
		 *
		 * @return
		 */
		public DateTime weekStart() {
			return set(Calendar.DAY_OF_WEEK, 2);
		}

		/**
		 * 转到所在星期的最后一天
		 *
		 * @return
		 */
		public DateTime weekEnd() {
			return set(Calendar.DAY_OF_WEEK, 1).addDays(7);
		}

		/**
		 * @see Calendar#get(int)
		 */
		public int get(int field) {
			return calendar.get(field);
		}

		/**
		 * 获取年
		 */
		public int year() {
			return calendar.get(Calendar.YEAR);
		}

		/**
		 * 获取月
		 */
		public int month() {
			return calendar.get(Calendar.MONTH) + 1;
		}

		/**
		 * 获取日
		 */
		public int day() {
			return calendar.get(Calendar.DATE);
		}

		/**
		 * 获取时
		 */
		public int hour() {
			return calendar.get(Calendar.HOUR);
		}

		/**
		 * 获取分
		 */
		public int minute() {
			return calendar.get(Calendar.MINUTE);
		}

		/**
		 * 获取秒
		 */
		public int second() {
			return calendar.get(Calendar.SECOND);
		}

		/**
		 * 获取毫秒
		 */
		public int millisecond() {
			return calendar.get(Calendar.MILLISECOND);
		}

		/**
		 * 获取日期是星期几
		 *
		 * @return 1-7对应星期天到星期六
		 */
		public int getWeek() {
			return calendar.get(Calendar.DAY_OF_WEEK);
		}

		/**
		 * 获取中文星期几
		 *
		 * @return
		 */
		public String getZhWeek() {
			return format(this, "EEE", Locale.SIMPLIFIED_CHINESE);
		}

		/**
		 * 是否是工作日
		 *
		 * @return
		 */
		public boolean isWorkday() {
			return workdayConfig.isWorkday(this);
		}

		/**
		 * 是否是节假日
		 *
		 * @return
		 */
		public boolean isHoliday() {
			return workdayConfig.isHoliday(this);
		}

		/**
		 * 是否是休息日，包含节假日
		 *
		 * @return
		 */
		public boolean isDayOff() {
			return workdayConfig.isDayOff(this);
		}

		/**
		 * 计算多少个工作日之后的日期
		 *
		 * @param days
		 * @return
		 */
		public DateTime addWorkdays(int days) {
			DateTime date = DateUtilPro.date(this);
			if (days > 0) {
				for (int i = days; i > 0; i--) {
					if (!date.addDays(1).isWorkday()) {
						i++;
					}
				}
			} else if (days < 0) {
				for (int i = days; i < 0; i++) {
					if (!date.addDays(-1).isWorkday()) {
						i--;
					}
				}
			}
			return date;
		}

		/**
		 * 计算两个日期之间有多少个工作日
		 *
		 * @param date
		 * @return
		 */
		public int compareWorkdays(Date date) {
			int days = compareDays(date);
			if (days == 0) {
				return 0;
			}
			Date start = days > 0 ? date : this;
			days = Math.abs(days);
			int count = 0;
			for (int i = 1; i <= days; i++) {
				if (DateUtilPro.date(start).addDays(i).isWorkday()) {
					count++;
				}
			}
			return count;
		}
	}

	public static class WorkdayConfig implements Cloneable {
		/**
		 * 补班日期
		 */
		private HashSet<String> workdays = new HashSet<>();
		/**
		 * 节假日日期
		 */
		private HashSet<String> holidays = new HashSet<>();
		/**
		 * 周休息日
		 */
		private ArrayList<Integer[]> weekDayOff = new ArrayList<>();
		/**
		 * 月休息日
		 */
		private HashMap<Integer, Integer[]> monthDayOff = new HashMap<>();

		private WorkdayConfig() {
			weekDayOff.add(new Integer[] { Calendar.SATURDAY, Calendar.SUNDAY });
		}

		/**
		 * 设置补班/调班日期
		 *
		 * @param dates
		 * @return
		 */
		public WorkdayConfig setWorkdays(List<Date> dates) {
			List<String> list = dates.stream().map(date -> {
				return DateUtilPro.date(date).toString("yyyy-MM-dd");
			}).collect(Collectors.toList());
			workdays.addAll(list);
			return this;
		}

		/**
		 * 设置节假日日期
		 *
		 * @param dates
		 * @return
		 */
		public WorkdayConfig setHolidays(List<Date> dates) {
			List<String> list = dates.stream().map(date -> {
				return DateUtilPro.date(date).toString("yyyy-MM-dd");
			}).collect(Collectors.toList());
			holidays.addAll(list);
			return this;
		}

		/**
		 * 设置周休息日，默认周六周日休息，可设置单双周
		 *
		 * @param dayOffArray
		 * @return
		 */
		public WorkdayConfig setWeekDayOff(Integer[]... dayOffArray) {
			if (dayOffArray.length > 4) {
				throw new IllegalArgumentException("最多设置四周的休息日");
			}
			Arrays.stream(dayOffArray).forEach(dayOffs -> {
				Arrays.stream(dayOffs).forEach(week -> {
					if (week < 1 || week > 7) {
						throw new IllegalArgumentException("休息日的设置范围1-7，对应星期天到星期六");
					}
				});
			});
			weekDayOff.clear();
			weekDayOff.addAll(Arrays.asList(dayOffArray));
			return this;
		}

		/**
		 * 添加月休息日
		 *
		 * @param month
		 * @param days
		 * @return
		 */
		public WorkdayConfig addMonthDayOff(int month, Integer... days) {
			if (month < 0 || month > 12) {
				throw new IllegalArgumentException("月份请设置1-12月");
			}
			DateTime date = DateUtilPro.date();
			date.month(month);
			Arrays.stream(days).forEach(day -> {
				if (date.day(day).month() != month) {
					log.info(date.toString());
					throw new IllegalArgumentException(month + "月不存在" + day + "号");
				}
			});
			monthDayOff.put(month, days);
			return this;
		}

		/**
		 * 删除指定月休息日
		 *
		 * @param month
		 * @return
		 */
		public WorkdayConfig removeMonthDayOff(int month) {
			monthDayOff.remove(month);
			return this;
		}

		/**
		 * 是否是工作日
		 *
		 * @param date
		 * @return
		 */
		public boolean isWorkday(Date date) {
			DateTime dateTime = DateUtilPro.date(date);
			String d = dateTime.toString("yyyy-MM-dd");
			// 是否是补班
			if (workdays.contains(d)) {
				return true;
			}
			// 是否是放假
			if (holidays.contains(d)) {
				return false;
			}
			// 是否是月调休
			if (!monthDayOff.isEmpty()) {
				Integer[] days = monthDayOff.get(dateTime.month());
				if (days != null && ArrayUtils.contains(days, dateTime.day())) {
					return false;
				}
			}
			// 是否是每周的休息日
			if (!weekDayOff.isEmpty()) {
				int size = weekDayOff.size();
				int i = dateTime.get(Calendar.WEEK_OF_YEAR);
				int mo = i % size;
				Integer[] dayOff = weekDayOff.get(mo);
				return !ArrayUtils.contains(dayOff, dateTime.getWeek());
			}
			return true;
		}

		/**
		 * 是否是节假日
		 *
		 * @param date
		 * @return
		 */
		public boolean isHoliday(Date date) {
			String d = DateUtilPro.date(date).toString("yyyy-MM-dd");
			return holidays.contains(d);
		}

		/**
		 * 是否是休息日，包含节假日
		 *
		 * @param date
		 * @return
		 */
		public boolean isDayOff(Date date) {
			return !isWorkday(date);
		}

		@Override
		public Object clone() throws CloneNotSupportedException {
			WorkdayConfig workdayConfig = (WorkdayConfig) super.clone();
			workdayConfig.workdays = (HashSet<String>) this.workdays.clone();
			workdayConfig.holidays = (HashSet<String>) this.holidays.clone();
			workdayConfig.weekDayOff = (ArrayList<Integer[]>) this.weekDayOff.clone();
			workdayConfig.monthDayOff = (HashMap<Integer, Integer[]>) this.monthDayOff.clone();
			return workdayConfig;
		}
	}

	/**
	 * 用SimpleDateFormat计算时间差
	 * 
	 * @throws ParseException
	 */
	public static String timeDiff(String beginDate, String endDate){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date2 = format.parse(beginDate);
			date1 = format.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long between = date1.getTime() - date2.getTime();
		long day = between / (24 * 60 * 60 * 1000);
		long hour = (between / (60 * 60 * 1000) - day * 24);
		long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		
		String returnStr = "";
		if(day > 0) {
			returnStr +=day + "天";
		}
		if(day > 0 || hour > 0) {
			returnStr += hour + "小时";
		}
		if(day > 0 || hour > 0 || min > 0) {
			returnStr +=  min + "分" ;
		}
		if(StringUtils.isEmpty(returnStr)) {
			returnStr = s+"秒";
		}
		return returnStr;
	}

	/**
	 * 获取当月天数
	 */
	public static int getMonthDays(String dateStr){
		DateTime date = DateUtilPro.date(DateUtils.getDateByStr(dateStr));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	public static void main(String[] args) throws ParseException {
		//timeDiff();
		System.out.println("--------------------");
		// 支持多种日期字符串解析
		DateTime date1 = DateUtilPro.parseDate("2018-01-22");
		System.out.println(date1);
		date1 = DateUtilPro.parseDate("20180122102830");
		System.out.println(date1);
		date1 = DateUtilPro.parseDate("20180122");
		System.out.println(date1);
		date1 = DateUtilPro.parseDate("2018-01-22 10:28:30");
		System.out.println(date1);

		System.out.println("------- 日期运算 --------");
		DateTime date2 = DateUtilPro.date().addYears(1).addMonths(1).addDays(1).addHours(1).addMinute(10).addSecond(30);
		DateTime date3 = DateUtilPro.date(date1).addHours(9).minute(30);

		System.out.println("date1: " + date1.toString("dd/MM/yyyy HH:mm:ss"));
		System.out.println("date2: " + date2);
		System.out.println("date3: " + date3);

		System.out.println("compareSeconds: " + date1.compareSeconds(date3));
		System.out.println("compareMinutes: " + date1.compareMinutes(date3));
		System.out.println("compareHours: " + date1.compareHours(date3));
		System.out.println("compareDays: " + date1.compareDays(date3));

		System.out.println("before: " + date1.before(date2)); // true
		System.out.println("after: " + date1.after(date2)); // false
		System.out.println("beforeOfHour: " + date1.beforeOfHour(date2)); // true
		System.out.println("afterOfHour: " + date1.afterOfHour(date2)); // false
		System.out.println("afterOfDay: " + date1.afterOfDay(date3)); // false
		System.out.println("beforeOfDay: " + date1.beforeOfDay(date3)); // false

		System.out.println("sameDay: " + date1.sameDay(date3)); // true
		System.out.println("sameHour: " + date1.sameHour(date3)); // false
		System.out.println("sameMinute： " + date1.sameMinute(date3)); // false

		System.out.println("isBetweenOfDay: " + date3.isBetweenOfDay(date1, date2)); // true
		System.out.println("isBetweenOfMinute: " + date3.isBetweenOfMinute(date1, date2)); // false

		System.out.println("这个月最后一天是：" + DateUtilPro.date().monthLastDate().toString("yyyy-MM-dd"));
		System.out.println("今天是" + DateUtilPro.date().getZhWeek());
		System.out.println("这个星期的星期一是：" + DateUtilPro.date().weekStart().toString("yyyy-MM-dd"));
		System.out.println("这个星期的星期日是：" + DateUtilPro.date().weekEnd().toString("yyyy-MM-dd"));

		List<Date> workdays = new ArrayList<>();
		List<Date> holidays = new ArrayList<>();
		workdays.add(DateUtilPro.parseDate("2018-05-05")); // 五一补班
		workdays.add(DateUtilPro.parseDate("2018-04-28")); // 五一补班
		holidays.add(DateUtilPro.parseDate("2018-04-29")); // 五一放假
		holidays.add(DateUtilPro.parseDate("2018-04-30")); // 五一放假
		holidays.add(DateUtilPro.parseDate("2018-05-01")); // 五一放假
		holidays.add(DateUtilPro.parseDate("2018-05-02")); // 五一放假

		DateUtilPro.workdayConfig().setWorkdays(workdays);
		DateUtilPro.workdayConfig().setHolidays(holidays);
		System.out.println("日期2018-04-26五个工作日之后的日期是：" + DateUtilPro.parseDate("2018-04-26").addWorkdays(5).toString());
		System.out.println("日期2018-05-04五个工作日之前的日期是：" + DateUtilPro.parseDate("2018-05-04").addWorkdays(-5).toString());
		System.out.println("2018-04-26与2018-05-04之间的工作日有："
				+ DateUtilPro.parseDate("2018-05-04 ").compareWorkdays(DateUtilPro.parseDate("2018-04-26")));

		// 设置大小周/单双周
		WorkdayConfig workdayConfig = DateUtilPro.getWorkdayConfig();
		workdayConfig.setWeekDayOff(new Integer[] { Calendar.SUNDAY }, // 单周只休星期天
				new Integer[] { Calendar.SATURDAY, Calendar.SUNDAY }); // 双周休两天
		System.out.println(
				"2018-05-05是休息日吗：" + DateUtilPro.parseDate("2018-05-05").workdayConfig(workdayConfig).isDayOff());
		System.out.println(
				"2018-05-12是休息日吗：" + DateUtilPro.parseDate("2018-05-12").workdayConfig(workdayConfig).isDayOff());

		DateUtilPro.workdayConfig().addMonthDayOff(5, new Integer[] { 29, 30, 31 });
		DateTime date = DateUtilPro.date(DateUtils.getDateByStr("2018-06-01"));
		System.out.println(" 天数 "+DateUtilPro.getMonthDays("2018-06-01"));
		System.out.printf("5月份上班天数：%s天，上班时间安排：\n", date.compareWorkdays(DateUtilPro.date(2018, 5, 31)));
		for (int i = 1; i <= 31; i++) {
//			System.out.println(new Date()+"====sssss=="+date);
			System.out.print(String.format("%s(%s)  ", date.toString("MM/dd"), date.day(i).isWorkday() ? "班" : "休"));
			if (i % 7 == 0) {
				System.out.println();
			}
		}
	}
}