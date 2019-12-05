package com.fixit.utility;

import java.text.SimpleDateFormat;

public class DateFormat {

	public static SimpleDateFormat getDateFormat(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return simpleDateFormat;
	}
}
