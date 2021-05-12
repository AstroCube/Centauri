package net.astrocube.commons.bukkit.utils;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeUtils {

	private static final Map<String, ChronoUnit> CHRONO_UNITS = new HashMap<>();

	static {

		CHRONO_UNITS.put("s", ChronoUnit.SECONDS);
		CHRONO_UNITS.put("m", ChronoUnit.MINUTES);
		CHRONO_UNITS.put("h", ChronoUnit.HOURS);
		CHRONO_UNITS.put("d", ChronoUnit.DAYS);
		CHRONO_UNITS.put("w", ChronoUnit.WEEKS);
		CHRONO_UNITS.put("M", ChronoUnit.MONTHS);
		CHRONO_UNITS.put("y", ChronoUnit.YEARS);

	}

	private TimeUtils() {
	}

	public static long parseDurationToLong(String duration) {
		long sum = 0;

		StringBuilder stringBuilder = new StringBuilder();

		for (char c : duration.toCharArray()) {
			if (Character.isDigit(c)) {
				stringBuilder.append(c);
			}

			if (CHRONO_UNITS.containsKey(String.valueOf(c).toLowerCase()) && stringBuilder.length() > 0) {
				long parsedLong = Long.parseLong(stringBuilder.toString());

				ChronoUnit unit = CHRONO_UNITS.get(String.valueOf(c));

				sum += unit.getDuration().multipliedBy(parsedLong).toMillis();

				stringBuilder = new StringBuilder();
			}
		}

		return sum;
	}

	public static Date addMinutes(Date date, Integer minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

}
