package entities;


public class Reservation implements Entity  {

	private Concert concert;
	private int nbTickets;
	
	public Reservation(Concert concert, int nbTickets){
		this.concert = concert;
		this.nbTickets = nbTickets;
	}

	public int getNbTickets(){return nbTickets;}
	public Concert getConcert(){return concert;}
	
	@Override
	public String toString(){
		return "";
	}
}
