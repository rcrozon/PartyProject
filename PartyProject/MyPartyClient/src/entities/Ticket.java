package entities;


public class Ticket{

	private int id;
	private Concert concert;
	
	public Ticket(int id, Concert concert){
		this.id = id;
		this.concert 	= concert;
	}

	public int getId(){return id;}
	public String getConcertName(){return concert.getTitle();}
	
}
