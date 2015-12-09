package model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DisplayConsole {

	public static void main(String[] args) {
		Manager m = new Manager();
		Date date = new Date();
		// Initialiser la date. 
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		calendar.add (Calendar.DATE, -6);
		date = calendar.getTime();
		
		
	}

}
