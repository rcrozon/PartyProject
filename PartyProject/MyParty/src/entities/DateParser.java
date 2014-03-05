package entities;

import java.util.Date;

public class DateParser {

	
	/**
	 * Parse date formatted AAAA-MM-DD HH:MM:SS
	 * @param date
	 */
	public static long parseDate(String date){
		long d = 0;
		try{
			String s1 = date.split(" ")[0];
			String s[] = s1.split("-");
			d = Long.parseLong(s[0]+s[1]+s[2]);
		}catch(IndexOutOfBoundsException ex){}
		return d;
	}
	
	public static long parseDate(Date date){
		long d = 0;
		try{
			d = Long.parseLong(""+date.getYear()+date.getMonth()+date.getDate());
		}catch(IndexOutOfBoundsException ex){}
		return d;
	}
	
	public String getTodayDate(){
		return new Date().toString();
	}
	
	public static boolean isNextConcert(String date){
		return (parseDate(new Date())+7) >= parseDate(date);
	}
	public static boolean isNewConcert(String date){
		return parseDate(new Date()) <= (parseDate(date)+7);
	}
}
