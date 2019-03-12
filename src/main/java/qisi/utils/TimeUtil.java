package qisi.utils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : ddv
 * @since : 2019/3/11 下午10:53
 */

public class TimeUtil {

	private static final String DAY_FORMAT = "yyyy-MM-dd";

	private static final String SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// 一秒钟的毫秒数
	private static final long ONE_SECOND = 1000;

	private static final ThreadLocal<SimpleDateFormat> FORMAT_TO_DAY_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DAY_FORMAT);
		}
	};

	private static final ThreadLocal<SimpleDateFormat> FORMAT_TO_SECOND_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
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

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		List<Date> dateList = getDayByMonth(2019, 1);
		filterWeekend(dateList);
		dateList.forEach((Date date) -> {
			System.out.print(new SimpleDateFormat(SECOND_FORMAT).format(addHours(date, 17)));
			calendar.clear();
			calendar.setTimeInMillis(date.getTime());
			System.out.println(" 周" + (calendar.get(Calendar.DAY_OF_WEEK)));
		});

		System.out.println(dateList.size());
		
	}
}
