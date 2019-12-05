package com.fixit.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimeZoneList {

	public static List<String> timeZoneList() {
		List<TimeZone> comboDropDownItem = new ArrayList<>();
		List<String> tzList = new ArrayList<String>();
		String[] ids = TimeZone.getAvailableIDs();
		for (String id : ids) {
			comboDropDownItem.add(TimeZone.getTimeZone(id));
		}
		Collections.sort(comboDropDownItem, new Comparator<TimeZone>() {
			public int compare(TimeZone s1, TimeZone s2) {
				return s1.getRawOffset() - s2.getRawOffset();
			}
		});
		for (TimeZone instance : comboDropDownItem) {
			TimeZone tz = TimeZone.getTimeZone(instance.getID());
			long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
			long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);

			String timeZoneString = String.format("%s (%s)",tz.getDisplayName(),
					instance.getID());
			tzList.add(timeZoneString);
		}
		return tzList;
	}

	public static String displayTimeZone(TimeZone tz) {

		long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
		long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
		// avoid -4:-30 issue
		minutes = Math.abs(minutes);

		String result = "";
		if (hours > 0) {
			result = String.format("(UTC+%d:%02d) %s", hours, minutes, tz.getID());
		} else {
			result = String.format("(UTC%d:%02d) %s", hours, minutes, tz.getID());
		}

		return result;

	}

	public static String displayTimeZoneGMT(String userTimezone) {
		TimeZone instance = TimeZone.getTimeZone(userTimezone);
		TimeZone tz = TimeZone.getTimeZone(instance.getID());
		long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
		long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
		String timeZoneString = String.format("%s (%s)",tz.getDisplayName(),
				instance.getID());
		return timeZoneString;
	}

}