package entities;


public class Reservation implements Entity  {

	private int id;
	private int nbSeets;
	private String concert;
	private int id_concert;
	
	public Reservation(int id, int nbSeets,	String concert, int id_concert){
		this.id = id;
		this.nbSeets 	= nbSeets;
		this.concert 	= concert;
		this.id_concert = id_concert;
	}

	public int getId(){return id;}
	public String getConcertName(){return concert;}
	public int getNbSeets(){return nbSeets;}
	public int getIdConcert(){return id_concert;}
	
	@Override
	public String toString(){
		return "Concert " + concert + "\n nomber of seets : " + nbSeets;
	}
}
