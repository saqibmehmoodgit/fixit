package com.fixit.utility;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class TimeConverter {

	public void timeConversionUTCTOLOCAL(DateTime s) {
		DateTimeZone zone = DateTimeZone.forID("Europe/Madrid");
		DateTime dt = new DateTime(zone);
		int day = dt.getDayOfMonth();
		int year = dt.getYear();
		int month = dt.getMonthOfYear();
		int hours = dt.getHourOfDay();
		int minutes = dt.getMinuteOfHour();
	}
}
