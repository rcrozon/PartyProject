package concert;

import java.util.Date;

import android.R.bool;

public class Concert {

	private String    	imgPath ;
	private String    	title ;
	private Date 		beginDate ;
	private Date 		endDate ;
	private String		location;
	private Double		price;
	private int 		nbSeets;
	private boolean		full;
	
	public Concert(String imgPath, String title, Date begin, Date end, String location, Double price, int nbSeets, boolean full){
		this.imgPath = imgPath;		
		this.title = title;
		this.beginDate = begin;
		this.endDate = end;
		this.location = location;
		this.price = price;
		this.nbSeets = nbSeets;
		this.full = full;
	}

	public String getImagePath(){return this.imgPath;}
	public String getTitle(){return this.title;}
	public Date getBeginDate(){return this.beginDate;}
	public Date getEndDate(){return this.endDate;}
	public String getLocation(){return this.location;}
	public Double getPrice(){return this.price;}
	public int getNbSeets(){return this.nbSeets;}
	public boolean isFull(){return this.full;}
	
	
}
