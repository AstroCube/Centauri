package net.astrocube.api.core.utils;

import me.yushust.message.MessageHandler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Utility class holding many standard {@link Time.Format}
 * implementations for ease its usage
 */
@Singleton
public final class Time {

	/**
	 * Map containing all supported time units
	 * by name, they're "ordered" from greater
	 * to less
	 */
	private static final Map<String, ChronoUnit> UNITS
			= new LinkedHashMap<>();

	static {
		UNITS.put("year", ChronoUnit.YEARS);
		UNITS.put("month", ChronoUnit.MONTHS);
		UNITS.put("week", ChronoUnit.WEEKS);
		UNITS.put("day", ChronoUnit.DAYS);
		UNITS.put("hour", ChronoUnit.HOURS);
		UNITS.put("minute", ChronoUnit.MINUTES);
		UNITS.put("second", ChronoUnit.SECONDS);
	}

	private final Format wordsFormat;

	@Inject
	public Time(MessageHandler messageHandler) {
		this.wordsFormat = new WordsFormat(messageHandler);
	}


	/** @see WordsFormat */
	public Format useWordsFormat() {
		return wordsFormat;
	}

	/**
	 * Responsible of converting computer time
	 * to human-readable time text like '2 hours
	 * 1 minute', '02:01', '2h 1m'
	 */
	public interface Format {

		/**
		 * Formats the given {@code millis} in the
		 * specified {@code entity} language (this
		 * is handled by {@link MessageHandler})
		 */
		String format(Object entity, long millis);

	}

	public static class WordsFormat implements Format {

		private final MessageHandler messageHandler;

		public WordsFormat(MessageHandler messageHandler) {
			this.messageHandler = messageHandler;
		}

		@Override
		public String format(Object entity, long millis) {

			// if time is less than a second, return the
			// formatted milliseconds
			if (millis < 1000) {
				long tenths = millis / 10;
				return messageHandler.replacing(
						entity, "time.millis",
						"%tenths%", tenths
				);
			}

			StringJoiner result = new StringJoiner(" ");

			for (Map.Entry<String, ChronoUnit> unitEntry : UNITS.entrySet()) {
				String unitName = unitEntry.getKey();
				ChronoUnit unit = unitEntry.getValue();

				// TODO: I think this can be optimized reducing some method calls
				long unitTime = millis / unit.getDuration().toMillis();

				if (unitTime > 0) {
					millis -= unit.getDuration().multipliedBy(unitTime).toMillis();
					result.add(
							unitTime + " " + messageHandler.get(
									entity,
									"time." + unitName + '.' + (unitTime == 1 ? "single" : "plural")
							)
					);
				}
			}

			return result.toString();
		}

	}

}
