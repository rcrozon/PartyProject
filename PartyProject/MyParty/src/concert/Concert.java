package concert;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.bool;

public class Concert {
	private int id;
	private String    	imgPath ;
	private String    	title ;
	private String 		beginDate ;
	private String		endDate ;
	private String		location;
	private int 		nbSeets;
	private int		full;
	
	public Concert(int id, String imgPath, String title, String begin, String end, String location, int nbSeets, int full){
		this.id = id;
		this.imgPath = imgPath;		
		this.title = title;
		this.beginDate = begin;
		this.endDate = end;
		this.location = location;
		this.nbSeets = nbSeets;
		this.full = full;
	}

	public int getId(){return this.id;}
	public String getImagePath(){return this.imgPath;}
	public String getTitle(){return this.title;}
	public String getBeginDate(){return this.beginDate;}
	public String getEndDate(){return this.endDate;}
	public String getLocation(){return this.location;}
	public int getNbSeets(){return this.nbSeets;}
	public int isFull(){return this.full;}
	
	public String toString(){
		return "Concert : " + title ;
	}
	
	
}
