package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

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

	public static Date parseDateInDate(String date){
		Date d = null;
		try{
			String s1 = date.split(" ")[0];
			String s[] = s1.split("-");
			Log.i("Date", "Mois : "+ Integer.parseInt(s[1]));
			d = new Date((Integer.parseInt(s[0]))-1900, (Integer.parseInt(s[1]))-1, Integer.parseInt(s[2]));
			
			
			
		}catch(IndexOutOfBoundsException ex){}
		return d;
	}

	public static List<Concert> sortConcert(List<Concert> listConcert){
		List<Concert> cl = new ArrayList<Concert>();
		if (listConcert != null){
			while(listConcert.size()>0){
				int num = 0;
				for (int i=1 ; i<listConcert.size() ; i++){
					if (parseDateInDate(listConcert.get(num).getBeginDate()).compareTo(parseDateInDate(listConcert.get(i).getBeginDate())) > 0){
						num =i;
					}
				}
				cl.add(listConcert.get(num));
				listConcert.remove(num);

			}
			listConcert=cl;
		}


		return listConcert;
	}
	
	public static List<Concert> sortConcertNews(List<Concert> listConcert){
		List<Concert> cl = new ArrayList<Concert>();
		if (listConcert != null){
			while(listConcert.size()>0){
				int num = 0;
				for (int i=1 ; i<listConcert.size() ; i++){
					Log.i("News", "Date: "+listConcert.get(num).getCreationDate());
					if (parseDateInDate(listConcert.get(num).getCreationDate()).compareTo(parseDateInDate(listConcert.get(i).getCreationDate())) < 0){
						num =i;
					}
				}
				cl.add(listConcert.get(num));
				listConcert.remove(num);

			}
			listConcert=cl;
		}


		return listConcert;
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
		Date now = new Date();
		long decal = now.getTime();
		long d = 86400000 * 7;
		Date seven = new Date(decal+d);
		Log.i("date", "date : "+seven.toString());
		Log.i("date", "Concert : "+ parseDateInDate(date).toString());
		if (parseDateInDate(date).compareTo(seven) <= 0)
			return true;
		else
			return false;
	}
	public static boolean isNewConcert(String date){
/*TODO Limiter à un certain nombre ou a une date*/
			return true;
	
	}
}
