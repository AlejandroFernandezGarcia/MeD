package ampa.sa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SchoolCalendar {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static List<Calendar> holidays; 
	
	
	public static void create() throws ParseException{
		holidays = new ArrayList<Calendar>();
		Calendar c1 = Calendar.getInstance();
		c1.setTime(sdf.parse("31/10/2014"));
		holidays.add(c1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(sdf.parse("08/12/2014"));
		holidays.add(c2);
		
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse("08/11/2014"));
		holidays.add(c);
		
		Calendar c3 = Calendar.getInstance();
		c3.setTime(sdf.parse("22/12/2014"));
		holidays.add(c3);
		Calendar c4 = Calendar.getInstance();
		c4.setTime(sdf.parse("23/12/2014"));
		holidays.add(c4);
		Calendar c5 = Calendar.getInstance();
		c5.setTime(sdf.parse("24/12/2014"));
		holidays.add(c5);
		Calendar c6 = Calendar.getInstance();
		c6.setTime(sdf.parse("25/12/2014"));
		holidays.add(c6);
		Calendar c7 = Calendar.getInstance();
		c7.setTime(sdf.parse("26/12/2014"));
		holidays.add(c7);
		Calendar c8 = Calendar.getInstance();
		c8.setTime(sdf.parse("27/12/2014"));
		holidays.add(c8);
		Calendar c9 = Calendar.getInstance();
		c9.setTime(sdf.parse("28/12/2014"));
		holidays.add(c9);
		Calendar c10 = Calendar.getInstance();
		c10.setTime(sdf.parse("29/12/2014"));
		holidays.add(c10);
		Calendar c11 = Calendar.getInstance();
		c11.setTime(sdf.parse("30/12/2014"));
		holidays.add(c11);
		Calendar c12 = Calendar.getInstance();
		c12.setTime(sdf.parse("31/12/2014"));
		holidays.add(c12);
		
		Calendar c13 = Calendar.getInstance();
		c13.setTime(sdf.parse("01/01/2015"));
		holidays.add(c13);
		Calendar c14 = Calendar.getInstance();
		c14.setTime(sdf.parse("02/01/2015"));
		holidays.add(c14);
		Calendar c15 = Calendar.getInstance();
		c15.setTime(sdf.parse("03/01/2015"));
		holidays.add(c15);
		Calendar c16 = Calendar.getInstance();
		c16.setTime(sdf.parse("04/01/2015"));
		holidays.add(c16);
		Calendar c17 = Calendar.getInstance();
		c17.setTime(sdf.parse("05/01/2015"));
		holidays.add(c17);
		Calendar c18 = Calendar.getInstance();
		c18.setTime(sdf.parse("06/01/2015"));
		holidays.add(c18);
		Calendar c19 = Calendar.getInstance();
		c19.setTime(sdf.parse("07/01/2015"));
		holidays.add(c19);
	}
	
	
	public static boolean isNotHoliday(Calendar date) throws ParseException{
		create();
		for(Calendar cal : holidays){
			if((cal.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
					&& cal.get(Calendar.MONTH) == date.get(Calendar.MONTH)
					&& cal.get(Calendar.YEAR) == date.get(Calendar.YEAR))
					|| date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				return false;
		}
		return true;
	}
	
}
