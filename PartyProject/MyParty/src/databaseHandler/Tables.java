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
	public static final String CONCERT_NAME_CREATED = "created";
	public static final int CONCERT_NUM_CREATED = 11;

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
	
/************* TABLE RESERVATION MISE A JOUR *************************/
	
	public static final String RESMAJ_TABLE = "reservations_MAJ";
	
	public static final String RESMAJ_NAME_ID = "id";
	public static final int RESMAJ_NUM_ID = 0;
	public static final String RESMAJ_NAME_ID_CONCERT = "id_concert";
	public static final int RESMAJ_NUM_ID_CONCERT = 1;
	public static final String RESMAJ_NAME_ID_CLIENT = "id_client";
	public static final int RESMAJ_NUM_ID_CLIENT = 2;
	public static final String RESMAJ_NAME_ID_TARIF = "id_tarif";
	public static final int RESMAJ_NUM_ID_TARIF = 3;
	public static final String RESMAJ_NAME_SCAN = "scan";
	public static final int RESMAJ_NUM_ID_SCAN = 4;

/*************    TABLE ARTISTS  *************************/
	
	public static final String ARTISTS_TABLE = "artists";

	public static final String ARTIST_NAME_ID = "id";
	public static final int ARTIST_NUM_ID = 0;
	public static final String ARTIST_NAME_ARTIST_NAME = "name";
	public static final int ARTIST_NUM_ARTIST_NAME = 1;
	
/*************    TABLE STYLES  *************************/
	
	public static final String STYLES_TABLE = "styles";

	public static final String STYLE_NAME_ID = "id";
	public static final int STYLE_NUM_ID = 0;
	public static final String STYLE_NAME_STYLE_NAME = "name";
	public static final int STYLE_NUM_STYLE_NAME = 1;
	
/*************    TABLE TARIFS  *************************/
	
	public static final String TARIFFS_TABLE = "tarifs";

	public static final String TARIFF_NAME_ID = "id";
	public static final int TARIFF_NUM_ID = 0;
	public static final String TARIFF_NAME_LABEL = "label";
	public static final int TARIFF_NUM_LABEL = 1;
	public static final String TARIFF_NAME_PRICE = "price";
	public static final int TARIFF_NUM_PRICE = 2;

/*************    TABLE ASSOC_TARIFS  *************************/
	
	public static final String ASSOC_TARIFFS_TABLE = "assoc_tarifs";

	public static final String ASSOC_TARIFF_NAME_ID = "id";
	public static final int ASSOC_TARIFF_NUM_ID = 0;
	public static final String ASSOC_TARIFF_NAME_ID_TARIFF = "id_tarif";
	public static final int ASSOC_TARIFF_NUM_ID_TARIFF = 1;
	public static final String ASSOC_TARIFF_NAME_ID_CONCERT = "id_concert";
	public static final int ASSOC_TARIFF_NUM_ID_CONCERT = 2;

/*************    TABLE ASSOC_STYLES  *************************/
	
	public static final String ASSOC_STYLES_TABLE = "assoc_styles";

	public static final String ASSOC_STYLES_NAME_ID = "id";
	public static final int ASSOC_STYLES_NUM_ID = 0;
	public static final String ASSOC_STYLES_NAME_ID_STYLES = "id_style";
	public static final int ASSOC_STYLES_NUM_ID_STYLES = 1;
	public static final String ASSOC_STYLES_NAME_ID_CONCERT = "id_concert";
	public static final int ASSOC_STYLES_NUM_ID_CONCERT = 2;

	/*************    TABLE ASSOC_ARTISTS  *************************/
	
	public static final String ASSOC_ARTISTS_TABLE = "assoc_artists";

	public static final String ASSOC_ARTIST_NAME_ID = "id";
	public static final int ASSOC_ARTIST_NUM_ID = 0;
	public static final String ASSOC_ARTIST_NAME_ID_ARTISTS = "id_artist";
	public static final int ASSOC_ARTIST_NUM_ID_ARTIST = 1;
	public static final String ASSOC_ARTIST_NAME_ID_CONCERT = "id_concert";
	public static final int ASSOC_ARTIST_NUM_ID_CONCERT = 2;
	
/*******************              ****************************/
	
}
