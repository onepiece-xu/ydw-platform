package com.ydw.advert.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	
	public static String cronFormat="ss mm HH dd MM ? yyyy";

	public static String parseCron(LocalDateTime localDateTime){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(cronFormat);
		return dtf.format(localDateTime);
	}
	
	public static String parseCron(Date date){
		SimpleDateFormat dtf = new SimpleDateFormat(cronFormat);
		return dtf.format(date);
	}
}
