package entities;


public class TicketUnitaire{

	private int id;
	private int idTariff;
	private int idClient;
	private Concert concert;
	
	public TicketUnitaire(Concert concert, int idTariff, int idClient){
		this.concert 	= concert;
		this.idTariff = idTariff;
		this.idClient = idClient;
	}

	public int getId(){return id;}
	public int getIdTariff(){return idTariff;}
	public int getIdClient(){return idClient;}
	public int getIdConcert(){return concert.getId();}
	public Concert getConcert(){return concert;}
	
	public String getConcertName(){return concert.getTitle();}
	
}
