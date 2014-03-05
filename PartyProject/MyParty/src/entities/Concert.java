package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.bool;

public class Concert implements Entity {
	
	private int 		id;
	private String    	imgPath ;
	private String    	title ;
	private String 		beginDate ;
	private String 		creationDate ;
	private String		endDate ;
	private String		location;
	private int 		nbSeets;
	private int			full;
	private int			idTarif;
	private int			idCreator;
	private int 		online;
	
	/*"full":false,
	"online":false}}]*/

	public Concert(int id, String imgPath, String title, String begin, String end, String creationDate,
			String location, int nbSeets, int full,int idTarif,int idCreator, int online){
		this.id = id;
		this.imgPath = imgPath;		
		this.title = title;
		this.beginDate = begin;
		this.creationDate = creationDate;
		this.endDate = end;
		this.location = location;
		this.nbSeets = nbSeets;
		this.full = full;
		this.idTarif = idTarif;
		this.idCreator = idCreator;
		this.online = online;
	}

	public int getId(){return this.id;}
	public String getImagePath(){return this.imgPath;}
	public String getTitle(){return this.title;}
	public String getBeginDate(){return this.beginDate;}
	public String getEndDate(){return this.endDate;}
	public String getCreationDate(){return this.creationDate;}
	public String getLocation(){return this.location;}
	public int getNbSeets(){return this.nbSeets;}
	public int isFull(){return this.full;}
	public int getIdTarif() {return idTarif;}
	public int getIdCreator() {return idCreator;}
	public int getOnline() {return online;}
	
	public String toString(){
		return "Concert : " + title ;
	}
	
	public String testToString(){
		return "ID : "+id+ " Image : " + imgPath + " Titre : " + title
				+" Begin : "+ beginDate + " End : " + endDate
				+ " Location : "+location+ " NbSeats : " + nbSeets+ " Full : "+full
				+ " IdTarif : "+idTarif+ " IdCreator : " + idCreator+ " Online : "+online;
	}
	
	
}
