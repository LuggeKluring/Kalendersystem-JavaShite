package javaWindow;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConvertDate {

	Date date = JavaWindow.selectedDate;
	public static String formattedDate;
	
	public static Calendar toCalendar(Date date){
		System.out.println(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		calendarFormat(cal);
		return cal;
	}
	public static String calendarFormat(Calendar cal) {
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate = format1.format(cal.getTime());
		System.out.println("Calendar formatted: "+formattedDate);
		return formattedDate;
		
	}

}
