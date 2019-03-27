package qisi.utils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : ddv
 * @since : 2019/3/11 下午10:53
 */

public class TimeUtil {

	public static final String MONTH_FORMAT = "yyyy-MM";

	public static final String DAY_FORMAT = "yyyy-MM-dd";

	public static final String SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// 一秒钟的毫秒数
	public static final long ONE_SECOND = 1000;

	public static final ThreadLocal<SimpleDateFormat> FORMAT_TO_MONTH_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(MONTH_FORMAT);
		}
	};

	public static final ThreadLocal<SimpleDateFormat> FORMAT_TO_DAY_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DAY_FORMAT);
		}
	};

	public static final ThreadLocal<SimpleDateFormat> FORMAT_TO_SECOND_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(SECOND_FORMAT);
		}
	};

	// 获取某一月中的每一天(当天时间的零点)
	public static List<Date> getDayByMonth(int year, int month) {
		List<Date> dates = new ArrayList<>();
		if (month < 1 || month > 12) return dates;

		Calendar calendar = Calendar.getInstance();
		calendar.clear();

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, --month);

		int n = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		dates.add(calendar.getTime());

		for (int i = 0; i < n - 1; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			dates.add(calendar.getTime());
		}

		return dates;
	}

	// 获取每月开头
	public static Date getMonthByYear(int year, int month) {
		if (month < 1 || month > 12) return null;

		Calendar calendar = Calendar.getInstance();
		calendar.clear();

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, --month);
		return calendar.getTime();
	}

	// 过滤周末
	public static void filterWeekend(List<Date> dates) {
		Calendar calendar = Calendar.getInstance();
		Iterator<Date> iterator = dates.iterator();
		while (iterator.hasNext()) {
			calendar.clear();
			Date date = iterator.next();
			calendar.setTimeInMillis(date.getTime());
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
				iterator.remove();
			}
		}

	}

	// 给某一天加上指定小时
	public static Date addHours(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(date.getTime());
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}

	public static String logDateWithMonth(Date date) {
		StringBuffer stringBuffer = new StringBuffer();
		SimpleDateFormat simpleDateFormat = FORMAT_TO_MONTH_THREAD_LOCAL.get();
		String format = simpleDateFormat.format(date);
		stringBuffer.append(format);
		return stringBuffer.toString();
	}

	public static void main(String[] args) {
		Date month = TimeUtil.getMonthByYear(2019, 3);
		System.out.println(month);
		SimpleDateFormat simpleDateFormat = FORMAT_TO_MONTH_THREAD_LOCAL.get();
		String format = simpleDateFormat.format(month);
		System.out.println(format);
	}

	public static Date getLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return getMonthByYear(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
	}
}
