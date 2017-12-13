package javaWindow;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConvertDate {

	String date = JavaWindow.selectedDate;
	public static String formattedDate;
	
	public static Calendar toCalendar(Date selectedDate){
		System.out.println(selectedDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(selectedDate);
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
