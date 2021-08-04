package com.ydw.platform.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间处理类
 * @author xulh
 *
 */
public class DateUtil extends DateUtils{

	public static String cronFormat="ss mm HH dd MM ? yyyy";
	
	/**
	 * 日期转cron
	 * @author xulh
	 * @date 2019年10月12日
	 * @param date
	 * @return String
	 */
	public static String dateFormatcron(Date date){  
        SimpleDateFormat sdf = new SimpleDateFormat(cronFormat);  
        String formatTimeStr = null;  
        if (date != null) {  
            formatTimeStr = sdf.format(date);  
        }  
        return formatTimeStr;  
    }

	/**
	 * 日期转cron
	 * @author xulh
	 * @date 2019年10月12日
	 * @param date
	 * @return String
	 */
	public static String dateFormatcron(LocalDateTime date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(cronFormat);
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = dateTimeFormatter.format(date);
		}
		return formatTimeStr;
	}

}
