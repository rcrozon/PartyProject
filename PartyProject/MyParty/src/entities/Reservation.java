package entities;


public class Reservation implements Entity  {

	private int id;
	private int nbSeets;
	private String concert;
	
	public Reservation(int id, int nbSeets,	String concert){
		this.id = id;
		this.nbSeets 	= nbSeets;
		this.concert 	= concert;
	}

	public int getId(){return id;}
	public String getConcertName(){return concert;}
	public int getNbSeets(){return nbSeets;}
	
	@Override
	public String toString(){
		return "Concert " + concert + "\n nomber of seets : " + nbSeets;
	}
}
