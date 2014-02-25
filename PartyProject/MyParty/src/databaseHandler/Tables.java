package databaseHandler;

public final class Tables {
	
	/***************** ADRESSE SERVEUR *************************************/
	
	public static final String IMG_PATH_SERVER = "http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/img/Concerts/";
	
	/************* TABLE CLIENTS *************************/
	
	public static final String CLIENT_TABLE = "clients";

	public static final String CLIENT_NAME_ID = "id";
	public static final int CLIENT_NUM_ID = 0;
	public static final String CLIENT_NAME_USERNAME = "username";
	public static final int CLIENT_NUM_USERNAME = 1;
	public static final String CLIENT_NAME_MAIL = "mail";
	public static final int CLIENT_NUM_MAIL = 2;
	public static final String CLIENT_NAME_PASSWORD = "password";
	public static final int CLIENT_NUM_PASSWORD = 3;
	public static final String CLIENT_NAME_FIRSTNAME = "first_name";
	public static final int CLIENT_NUM_FIRSTNAME = 4;
	public static final String CLIENT_NAME_LASTNAME = "last_name";
	public static final int CLIENT_NUM_LASTNAME = 5;
	public static final String CLIENT_NAME_ADMIN= "admin";
	public static final int CLIENT_NUM_ADMIN = 6;
	public static final String CLIENT_NAME_DATE_CREATE= "created";
	public static final int CLIENT_NUM_DATE_CREATE = 7;
	
	/************* TABLE CONCERTS *************************/
	
	public static final String CONCERT_TABLE = "concerts";
	
	public static final String CONCERT_NAME_ID = "id";
	public static final int CONCERT_NUM_ID = 0;
	public static final String CONCERT_NAME_START_DATE = "start_datetime";
	public static final int CONCERT_NUM_START_DATE = 1;
	public static final String CONCERT_NAME_END_DATE = "end_datetime";
	public static final int CONCERT_NUM_END_DATE = 2;
	public static final String CONCERT_NAME_LOCATION = "location";
	public static final int CONCERT_NUM_LOCATION = 3;
	public static final String CONCERT_NAME_IMAGE = "image";
	public static final int CONCERT_NUM_IMAGE = 4;
	public static final String CONCERT_NAME_NB_SEAT = "nb_seats";
	public static final int CONCERT_NUM_NB_SEAT = 5;
	public static final String CONCERT_NAME_FULL = "full";
	public static final int CONCERT_NUM_FULL = 6;
	public static final String CONCERT_NAME_ID_CREATOR = "id_creator";
	public static final int CONCERT_NUM_ID_CREATOR = 7;
	public static final String CONCERT_NAME_TITLE_CONCERT = "name_concert";
	public static final int CONCERT_NUM_TITLE_CONCERT = 8;
	public static final String CONCERT_NAME_ONLINE = "online";
	public static final int CONCERT_NUM_ONLINE = 9;
	public static final String CONCERT_NAME_ID_TARIF = "id_tarif";
	public static final int CONCERT_NUM_ID_TARIF = 10;
	
/************* TABLE RESERVATION *************************/
	
	public static final String RES_TABLE = "reservations";
	
	public static final String RES_NAME_ID = "id";
	public static final int RES_NUM_ID = 0;
	public static final String RES_NAME_ID_CONCERT = "id_concert";
	public static final int RES_NUM_ID_CONCERT = 1;
	public static final String RES_NAME_ID_CLIENT = "id_client";
	public static final int RES_NUM_ID_CLIENT = 2;
	public static final String RES_NAME_ID_TARIF = "id_tarif";
	public static final int RES_NUM_ID_TARIF = 3;
	public static final String RES_NAME_SCAN = "scan";
	public static final int RES_NUM_ID_SCAN = 4;
	
/*******************              ****************************/
	
	/*
public static final String CLIENT_TABLE = "client";
	
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	
	
public static final String CLIENT_TABLE = "client";
	
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	
	
public static final String CLIENT_TABLE = "client";
	
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	public static final String CLIENT_NAME_FIRSTNAME = "firstname";
	public static final int CLIENT_NUM_FIRSTNAME = 1;
	*/
	
	
	

}
