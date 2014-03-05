package databaseHandler;

import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.util.Log;
import entities.Client;
import entities.Concert;

public class DatabaseHandler {     
	
	private static final int VERSION_BDD = 103;
	private static final String BDD_NAME = "myparty.db";
	private static SQLiteDatabase bdd;
	private DatabaseCreate SQLiteBase ;
	
	public DatabaseHandler(Context context){
		SQLiteBase = new DatabaseCreate(context, BDD_NAME, null, VERSION_BDD);
	}
 
	public void open(){
		bdd = SQLiteBase.getWritableDatabase();
		//initBDD();
		
	}
 
	public void close(){
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	

	
/***************** INITIALISATION DE LA BDD ***************************/	

	public void initBDD(){
	}
	
/***************** INSERER UN CLIENT DANS LA BDD ***************************/
	
	public static long insertClient(Client client){
		ContentValues values = new ContentValues();
		values.put(Tables.CLIENT_NAME_ID, client.getId());
		values.put(Tables.CLIENT_NAME_USERNAME, client.getLogin());
		values.put(Tables.CLIENT_NAME_MAIL, client.getEmail());
		values.put(Tables.CLIENT_NAME_PASSWORD, client.getPassword());
		values.put(Tables.CLIENT_NAME_FIRSTNAME, client.getFirstName());
		values.put(Tables.CLIENT_NAME_LASTNAME, client.getLastName());
		values.put(Tables.CLIENT_NAME_ADMIN, client.getAdmin());
		values.put(Tables.CLIENT_NAME_DATE_CREATE, client.getDateCreated());
		return bdd.insert(Tables.CLIENT_TABLE, null, values);
	}
	
/***************** INSERER UN CONCERT DANS LA BDD ***************************/
	
	public static long insertConcert(Concert concert){
		ContentValues values = new ContentValues();
		values.put(Tables.CONCERT_NAME_ID,concert.getId());
		values.put(Tables.CONCERT_NAME_START_DATE, concert.getBeginDate());
		values.put(Tables.CONCERT_NAME_END_DATE, concert.getEndDate());
		values.put(Tables.CONCERT_NAME_CREATED, concert.getCreationDate());
		values.put(Tables.CONCERT_NAME_LOCATION, concert.getLocation());
		values.put(Tables.CONCERT_NAME_IMAGE, concert.getImagePath());
		values.put(Tables.CONCERT_NAME_NB_SEAT, concert.getNbSeets());
		values.put(Tables.CONCERT_NAME_FULL,concert.isFull());
		values.put(Tables.CONCERT_NAME_ID_CREATOR,concert.getIdCreator());
		values.put(Tables.CONCERT_NAME_TITLE_CONCERT, concert.getTitle());
		values.put(Tables.CONCERT_NAME_ONLINE,concert.getOnline());
		values.put(Tables.CONCERT_NAME_ID_TARIF,concert.getIdTarif());
		return bdd.insert(Tables.CONCERT_TABLE, null, values);
	}

/***************** INSERER UNE RESERVATION DANS LA BDD ***************************/
	
	public long insertRes(int id_res,int  id_concert,int id_client ,int id_tarif,int scan){
		ContentValues values = new ContentValues();
		values.put(Tables.RES_NAME_ID,id_res);
		values.put(Tables.RES_NAME_ID_CONCERT,id_concert);
		values.put(Tables.RES_NAME_ID_CLIENT, id_client);
		values.put(Tables.RES_NAME_ID_TARIF,id_tarif);
		values.put(Tables.RES_NAME_SCAN, scan);
		return bdd.insert(Tables.RES_TABLE, null, values);
	}
	
/***************** INSERER UNE RESERVATION MISE A JOUR DANS LA BDD ***************************/
	
	public long insertResMAJ(int id_res,int  id_concert,int id_client ,int id_tarif,int scan){
		ContentValues values = new ContentValues();
		values.put(Tables.RESMAJ_NAME_ID,id_res);
		values.put(Tables.RESMAJ_NAME_ID_CONCERT,id_concert);
		values.put(Tables.RESMAJ_NAME_ID_CLIENT, id_client);
		values.put(Tables.RESMAJ_NAME_ID_TARIF,id_tarif);
		values.put(Tables.RESMAJ_NAME_SCAN, scan);
		return bdd.insert(Tables.RESMAJ_TABLE, null, values);
	}

/***************** INSERER UN ARTISTE DANS LA BDD ***************************/
	
	public long insertArtist(int id_artist, String artistName){
		ContentValues values = new ContentValues();
		values.put(Tables.ARTIST_NAME_ID, id_artist);
		values.put(Tables.ARTIST_NAME_ARTIST_NAME, artistName);
		return bdd.insert(Tables.ARTISTS_TABLE, null, values);
	}

/***************** INSERER UN STYLE DANS LA BDD ***************************/
	
	public long insertStyle(int id_style, String styleName){
		ContentValues values = new ContentValues();
		values.put(Tables.STYLE_NAME_ID, id_style);
		values.put(Tables.STYLE_NAME_STYLE_NAME, styleName);
		return bdd.insert(Tables.STYLES_TABLE, null, values);
	}

/***************** INSERER UN TARIF DANS LA BDD ***************************/
	
	public long insertTariff(int id_tariff, String label, Double price){
		ContentValues values = new ContentValues();
		values.put(Tables.TARIFF_NAME_ID, id_tariff);
		values.put(Tables.TARIFF_NAME_LABEL, label);
		values.put(Tables.TARIFF_NAME_PRICE, price);
		return bdd.insert(Tables.TARIFFS_TABLE, null, values);
	}

/***************** INSERER UN ASSOC_TARIF DANS LA BDD ***************************/
	
	public long insertAssocTariff(int id, int id_tariff, int id_concert){
		ContentValues values = new ContentValues();
		values.put(Tables.ASSOC_TARIFF_NAME_ID, id);
		values.put(Tables.ASSOC_TARIFF_NAME_ID_TARIFF, id_tariff);
		values.put(Tables.ASSOC_TARIFF_NAME_ID_CONCERT, id_concert);
		return bdd.insert(Tables.ASSOC_TARIFFS_TABLE, null, values);
	}
/***************** INSERER UN ASSOC_STYLE DANS LA BDD ***************************/

	public long insertAssocStyle(int id, int id_style, int id_concert){
		ContentValues values = new ContentValues();
		values.put(Tables.ASSOC_STYLES_NAME_ID, id);
		values.put(Tables.ASSOC_STYLES_NAME_ID_STYLES, id_style);
		values.put(Tables.ASSOC_STYLES_NAME_ID_CONCERT, id_concert);
		return bdd.insert(Tables.ASSOC_STYLES_TABLE, null, values);
	}
/***************** INSERER UN ASSOC_ARTIST DANS LA BDD ***************************/

	public long insertAssocArtist(int id, int id_artist, int id_concert){
		ContentValues values = new ContentValues();
		values.put(Tables.ASSOC_ARTIST_NAME_ID, id);
		values.put(Tables.ASSOC_ARTIST_NAME_ID_ARTISTS, id_artist);
		values.put(Tables.ASSOC_TARIFF_NAME_ID_CONCERT, id_concert);
		return bdd.insert(Tables.ASSOC_ARTISTS_TABLE, null, values);
	}
	
/***************** MISE A JOUR DANS LA BDD ***************************/
//	public int updateLivre(int id, Livre livre){
//		//La mise ��� jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
//		//il faut simple pr���ciser quelle livre on doit mettre ��� jour gr���ce ��� l'ID
//		ContentValues values = new ContentValues();
//		values.put(COL_FIRSTNAME, livre.getIsbn());
//		values.put(COL_TITRE, livre.getTitre());
//		return bdd.update(CLIENT_TABLE, values, COL_ID + " = " +id, null);
//	}
 
//	public int removeLivreWithID(int id){
//		//Suppression d'un livre de la BDD gr���ce ��� l'ID
//		return bdd.delete(CLIENT_TABLE, COL_ID + " = " +id, null);
//	}
	
/***************** AUTHENTIFICATION  ***************************/	

	public Boolean authentification(String login, String pwd){ 
		Cursor c = bdd.query(Tables.CLIENT_TABLE, 
				new String[] {Tables.CLIENT_NAME_ID, 
				Tables.CLIENT_NAME_USERNAME,
				Tables.CLIENT_NAME_PASSWORD}, 
				Tables.CLIENT_NAME_USERNAME + " LIKE \"" + login +"\"", null, null, null, null);
		Log.i("ini", "ON TROUVE  "+ c.getCount());
		
		/*On vérifie que l'on obtient qu'un résultat a partir du login*/
		if (c.getCount() == 1){
			c.moveToFirst();
			Log.i("LOGIN", "Base  "+ c.getString(2));
			
			
			/* Decrypt */
			/*On decrypt le mot de passe contenu dans la table*/
			MCrypt mcrypt = new MCrypt();
			String decrypted=null;
			try {
			decrypted = new String( mcrypt.decrypt( c.getString(2) ) );
			} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*On enlève les caractères spéciaux du mdp décrypté*/
			byte bytes[] = decrypted.getBytes();
			if( bytes.length > 0)
			{
			    int trim = 0;
			    for( int i = bytes.length - 1; i >= 0; i-- ) 
			    	if( bytes[i] == 0 ) 
			    		trim++;
			    if( trim > 0 )
			    {
			        byte[] newArray = new byte[bytes.length - trim];
			        System.arraycopy(bytes, 0, newArray, 0, bytes.length - trim);
			        bytes = newArray;
			    }
			}
			decrypted = new String(bytes);
			
			Log.i("LOGIN", "Base Decr  "+ decrypted);
			Log.i("ini", "log  "+ c.getString(1) + "    MDP   "+ c.getString(2) + "  attendu "+ pwd);
			/*On compare le mdp rentré et celui de la table*/
			if (pwd.equals(decrypted)){
				return true;
			}
		}
		return false;
	}
	
	/***************** AUTHENTIFICATION  Admin***************************/	

	public Boolean authentificationAdmin(String login, String pwd){ 
		Cursor c = bdd.query(Tables.CLIENT_TABLE, 
				new String[] {Tables.CLIENT_NAME_ID, 
				Tables.CLIENT_NAME_USERNAME,
				Tables.CLIENT_NAME_PASSWORD,
				Tables.CLIENT_NAME_ADMIN}, 
				Tables.CLIENT_NAME_USERNAME + " LIKE \"" + login +"\"", null, null, null, null);
		Log.i("ini", "ON TROUVE  "+ c.getCount());
		
		/*On vérifie que l'on obtient qu'un résultat a partir du login*/
		if (c.getCount() == 1){
			c.moveToFirst();
			Log.i("LOGIN", "Base  "+ c.getString(2));
			
			
			/* Decrypt */
			/*On decrypt le mot de passe contenu dans la table*/
			MCrypt mcrypt = new MCrypt();
			String decrypted=null;
			try {
			decrypted = new String( mcrypt.decrypt( c.getString(2) ) );
			} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*On enlève les caractères spéciaux du mdp décrypté*/
			byte bytes[] = decrypted.getBytes();
			if( bytes.length > 0)
			{
			    int trim = 0;
			    for( int i = bytes.length - 1; i >= 0; i-- ) 
			    	if( bytes[i] == 0 ) 
			    		trim++;
			    if( trim > 0 )
			    {
			        byte[] newArray = new byte[bytes.length - trim];
			        System.arraycopy(bytes, 0, newArray, 0, bytes.length - trim);
			        bytes = newArray;
			    }
			}
			decrypted = new String(bytes);
			
			Log.i("LOGIN", "Base Decr  "+ decrypted);
			Log.i("ini", "log  "+ c.getString(1) + "    MDP   "+ c.getString(2) + "  attendu "+ pwd);
			/*On compare le mdp rentré et celui de la table*/
			if (pwd.equals(decrypted) && c.getInt(3)==1){
				return true;
			}
		}
		return false;
	}
	
	
	/***************** TROUVER LES TARIFS PAR ID DE CONCERT DANS LA BDD ***************************/
	public static HashMap<String, Double> getTariffsFromConcert(int id_concert){
		Log.i("TARIFF", "passe");
		HashMap<String, Double> mapTariffs = new HashMap<String, Double>();
		Cursor c1 = bdd.query(Tables.ASSOC_TARIFFS_TABLE, 
				new String[] {Tables.ASSOC_TARIFF_NAME_ID_TARIFF}, 
				Tables.ASSOC_TARIFF_NAME_ID_CONCERT + " LIKE \"" + id_concert + "\"", null, null, null, null);
		c1.moveToFirst();
		int id_tariff ;
		Cursor c;
		for(int i = 0; i < c1.getCount(); ++i){
			if (i != 0)
				c1.move(1);
			id_tariff = c1.getInt(0);
			c = bdd.query(Tables.TARIFFS_TABLE, 
				new String[] {Tables.TARIFF_NAME_LABEL, 
				Tables.TARIFF_NAME_PRICE}, 
				Tables.TARIFF_NAME_ID + " LIKE \"" + id_tariff + "\"", null, null, null, null);
			c.moveToFirst();
			mapTariffs.put(c.getString(0), c.getDouble(1));
			
		} 
		return mapTariffs;
	}

	/***************** TROUVER LES STYLES PAR ID DE CONCERT DANS LA BDD ***************************/
	public static ArrayList<String> getStylesFromConcert(int id_concert){
		ArrayList<String> listStyles = new ArrayList<String>();
		Cursor c1 = bdd.query(Tables.ASSOC_STYLES_TABLE, 
				new String[] {Tables.ASSOC_STYLES_NAME_ID_STYLES}, 
				Tables.ASSOC_STYLES_NAME_ID_CONCERT + " LIKE \"" + id_concert + "\"", null, null, null, null);
		c1.moveToFirst();
		int id_style ;
		Cursor c;
		for(int i = 0; i < c1.getCount(); ++i){
			if (i != 0)
				c1.move(1);
			id_style = c1.getInt(0);
			c = bdd.query(Tables.STYLES_TABLE, 
				new String[] {Tables.STYLE_NAME_STYLE_NAME}, 
				Tables.STYLE_NAME_ID + " LIKE \"" + id_style + "\"", null, null, null, null);
			c.moveToFirst();
			listStyles.add(c.getString(0));
		}
		return listStyles;
	}

	/***************** TROUVER LES ARTISTES PAR ID DE CONCERT DANS LA BDD ***************************/
	public static ArrayList<String> getArtistsFromConcert(int id_concert){
		ArrayList<String> listArtists = new ArrayList<String>();
		Cursor c1 = bdd.query(Tables.ASSOC_ARTISTS_TABLE, 
				new String[] {Tables.ASSOC_ARTIST_NAME_ID_ARTISTS}, 
				Tables.ASSOC_ARTIST_NAME_ID_CONCERT + " LIKE \"" + id_concert + "\"", null, null, null, null);
		c1.moveToFirst();
		int id_artist ;
		Cursor c;
		for(int i = 0; i < c1.getCount(); ++i){
			if (i != 0)
				c1.move(1);
			id_artist = c1.getInt(0);
			
			c = bdd.query(Tables.ARTISTS_TABLE, 
				new String[] {Tables.ARTIST_NAME_ARTIST_NAME}, 
				Tables.ARTIST_NAME_ID + " LIKE \"" + id_artist + "\"", null, null, null, null);
			c.moveToFirst();
			
			listArtists.add(c.getString(0));
		}
		return listArtists;
	}

	/***************** TROUVER UN CONCERT PAR ID DANS LA BDD ***************************/
	
	public Concert getConcertWithId(int id){ 
		Cursor c = bdd.query(Tables.CONCERT_TABLE,
				new String[] {Tables.CONCERT_NAME_ID, 
				Tables.CONCERT_NAME_START_DATE,
				Tables.CONCERT_NAME_END_DATE,
				Tables.CONCERT_NAME_LOCATION,
				Tables.CONCERT_NAME_IMAGE,
				Tables.CONCERT_NAME_NB_SEAT,
				Tables.CONCERT_NAME_FULL,
				Tables.CONCERT_NAME_ID_CREATOR,
				Tables.CONCERT_NAME_TITLE_CONCERT,
				Tables.CONCERT_NAME_ONLINE,
				Tables.CONCERT_NAME_ID_TARIF,
				Tables.CONCERT_NAME_CREATED}, 
				Tables.CONCERT_NAME_ID + " LIKE \"" + id +"\"", null, null, null, null);
		if (c.getCount() != 1){
			return null;
		}
		c.moveToFirst();
		Concert concert = new Concert(c.getInt(Tables.CONCERT_NUM_ID), 
				c.getString(Tables.CONCERT_NUM_IMAGE), 
				c.getString(Tables.CONCERT_NUM_TITLE_CONCERT), 
				c.getString(Tables.CONCERT_NUM_START_DATE), 
				c.getString(Tables.CONCERT_NUM_END_DATE),
				c.getString(Tables.CONCERT_NUM_CREATED),
				c.getString(Tables.CONCERT_NUM_LOCATION),
				c.getInt(Tables.CONCERT_NUM_NB_SEAT), 
				c.getInt(Tables.CONCERT_NUM_FULL),
				c.getInt(Tables.CONCERT_NUM_ID_TARIF),
				c.getInt(Tables.CONCERT_NUM_ID_CREATOR),
				c.getInt(Tables.CONCERT_NUM_ONLINE));
		c.close();
 
		return concert;
		
	}
	
/***************** TROUVER UN CLIENT PAR ID DANS LA BDD ***************************/
	
	public Client getClientWithId(int id){ 
		Cursor c = bdd.query(Tables.CLIENT_TABLE,
				new String[] {Tables.CLIENT_NAME_ID, 
				Tables.CLIENT_NAME_USERNAME, 
				Tables.CLIENT_NAME_MAIL,
				Tables.CLIENT_NAME_PASSWORD,
				Tables.CLIENT_NAME_FIRSTNAME,
				Tables.CLIENT_NAME_LASTNAME,
				Tables.CLIENT_NAME_ADMIN,
				Tables.CLIENT_NAME_DATE_CREATE}, 
				Tables.CLIENT_NAME_ID + " LIKE \"" + id +"\"", null, null, null, null);
		if (c.getCount() != 1){
			return null;
		}
		c.moveToFirst();
		
		Client client = new Client(c.getInt(Tables.CLIENT_NUM_ID), 
				c.getString(Tables.CLIENT_NUM_FIRSTNAME),
				c.getString(Tables.CLIENT_NUM_LASTNAME), 
				c.getString(Tables.CLIENT_NUM_MAIL), 
				c.getString(Tables.CLIENT_NUM_USERNAME),
				c.getString(Tables.CLIENT_NUM_PASSWORD), 
				c.getInt(Tables.CLIENT_NUM_ADMIN), 
				c.getString(Tables.CLIENT_NUM_DATE_CREATE));
		
		c.close();
		return client;
		
	}
	
	
/***************** TROUVER LA LISTE DES CLIENTS ***************************/
	
	public List<Client>  getClients(){ 
		List<Client> cl = new ArrayList<Client>();
		Cursor c = bdd.query(Tables.CLIENT_TABLE,
				new String[] {Tables.CLIENT_NAME_ID, 
				Tables.CLIENT_NAME_USERNAME, 
				Tables.CLIENT_NAME_MAIL,
				Tables.CLIENT_NAME_PASSWORD,
				Tables.CLIENT_NAME_FIRSTNAME,
				Tables.CLIENT_NAME_LASTNAME,
				Tables.CLIENT_NAME_ADMIN,
				Tables.CLIENT_NAME_DATE_CREATE}, 
				null, null, null, null, null);
		if (c.getCount() == 0){
			return null;
		}
		c.moveToFirst();
		for (int i=0; i< c.getCount();i++){
			if (i!=0)
				c.move(1);
			Client client = new Client(c.getInt(Tables.CLIENT_NUM_ID), 
					c.getString(Tables.CLIENT_NUM_FIRSTNAME),
					c.getString(Tables.CLIENT_NUM_LASTNAME), 
					c.getString(Tables.CLIENT_NUM_MAIL), 
					c.getString(Tables.CLIENT_NUM_USERNAME),
					c.getString(Tables.CLIENT_NUM_PASSWORD), 
					c.getInt(Tables.CLIENT_NUM_ADMIN), 
					c.getString(Tables.CLIENT_NUM_DATE_CREATE));
			cl.add(client);
		}
		
		c.close();
		return cl;
		
	}
	
/***************** TROUVER LA LISTE DES CLIENTS POUR UN CONCERT DANS LA BDD ***************************/
	
	public ArrayList<Client> getClientsForOneConcert(int id_concert){
		ArrayList<Client> cl = new ArrayList<Client>();
	
		Cursor c = bdd.query(true, Tables.RES_TABLE,
				new String[] {Tables.RES_NAME_ID, 
				Tables.RES_NAME_ID_CONCERT, 
				Tables.RES_NAME_ID_CLIENT,
				Tables.RES_NAME_ID_TARIF,
				Tables.RES_NAME_SCAN}, 
				Tables.RES_NAME_ID_CONCERT + " LIKE \"" + id_concert +"\"", 
				null, Tables.RES_NAME_ID_CLIENT, null, null, null);

		if (c.getCount() ==0 ){
			return null;
		}
		c.moveToFirst();
		
		for (int i =0; i< c.getCount(); i++){
		if (i!=0){
			c.move(1);
		}
		Client client = getClientWithId(c.getInt(Tables.RES_NUM_ID_CLIENT));
		cl.add(client);
		}
		c.close();
		
		return cl;
		
		
	}
/***************** TROUVER LA LISTE DE TOUS LES CONCERTS DANS LA BDD ***************************/

	public List<Concert> getConcerts(){ 
		List<Concert> cl = new ArrayList<Concert>();
		Cursor c = bdd.query(Tables.CONCERT_TABLE,
				new String[] {Tables.CONCERT_NAME_ID, 
				Tables.CONCERT_NAME_START_DATE,
				Tables.CONCERT_NAME_END_DATE,
				Tables.CONCERT_NAME_LOCATION,
				Tables.CONCERT_NAME_IMAGE,
				Tables.CONCERT_NAME_NB_SEAT,
				Tables.CONCERT_NAME_FULL,
				Tables.CONCERT_NAME_ID_CREATOR,
				Tables.CONCERT_NAME_TITLE_CONCERT,
				Tables.CONCERT_NAME_ONLINE,
				Tables.CONCERT_NAME_ID_TARIF,
				Tables.CONCERT_NAME_CREATED}, 
				null, null, null, null, null);
		if (c.getCount() == 0){
			return null;
		}
		c.moveToFirst();
		for (int i =0; i< c.getCount(); i++){
			if (i!=0){
				c.move(1);
			}
		Concert concert = new Concert(c.getInt(Tables.CONCERT_NUM_ID), 
				c.getString(Tables.CONCERT_NUM_IMAGE), 
				c.getString(Tables.CONCERT_NUM_TITLE_CONCERT), 
				c.getString(Tables.CONCERT_NUM_START_DATE), 
				c.getString(Tables.CONCERT_NUM_END_DATE), 
				c.getString(Tables.CONCERT_NUM_CREATED),
				c.getString(Tables.CONCERT_NUM_LOCATION),
				c.getInt(Tables.CONCERT_NUM_NB_SEAT), 
				c.getInt(Tables.CONCERT_NUM_FULL),
				c.getInt(Tables.CONCERT_NUM_ID_TARIF),
				c.getInt(Tables.CONCERT_NUM_ID_CREATOR),
				c.getInt(Tables.CONCERT_NUM_ONLINE));
		cl.add(concert);
		
		}
		
		c.close();
		return cl;
		
	}
/***************** TROUVER LE NOMBRE DE RESERVATION POUR UN CLIENT POUR UN CONCERT  ***************************/
	
	public int getNumberResClientForOneConcert(Concert concert, Client client){
	
		Cursor c = bdd.query(Tables.RES_TABLE, 
				new String[] {Tables.RES_NAME_ID, 
				Tables.RES_NAME_ID_CONCERT, 
				Tables.RES_NAME_ID_CLIENT,
				Tables.RES_NAME_ID_TARIF,
				Tables.RES_NAME_SCAN}, 
				Tables.RES_NAME_ID_CONCERT + " LIKE \"" + concert.getId() +"\""
				+ " AND "+Tables.RES_NAME_ID_CLIENT + " LIKE \"" + client.getId() +"\"", null, null, null, null);


		return c.getCount();
	}
	
/***************** TROUVER LE NOMBRE DE RESERVATION POUR UN CLIENT POUR UN CONCERT QUI SONT SCANNÉS ***************************/
	
	public int getNumberResClientForOneConcertScanned(Concert concert, Client client){
	
		Cursor c = bdd.query(Tables.RES_TABLE, 
				new String[] {Tables.RES_NAME_ID, 
				Tables.RES_NAME_ID_CONCERT, 
				Tables.RES_NAME_ID_CLIENT,
				Tables.RES_NAME_ID_TARIF,
				Tables.RES_NAME_SCAN}, 
				Tables.RES_NAME_ID_CONCERT + " LIKE \"" + concert.getId() +"\""
				+ " AND "+Tables.RES_NAME_ID_CLIENT + " LIKE \"" + client.getId() +"\""
				+ " AND "+Tables.RES_NAME_SCAN + " LIKE \"" + 1 +"\"", null, null, null, null);


		return c.getCount();
	}
	
/***************** TROUVER LE NOMBRE DE RESERVATION POUR UN CONCERT ***************************/
	
	public int getNumberResForOneConcert(int id_concert){
	
		Cursor c = bdd.query(Tables.RES_TABLE, 
				new String[] {Tables.RES_NAME_ID, 
				Tables.RES_NAME_ID_CONCERT, 
				Tables.RES_NAME_ID_CLIENT,
				Tables.RES_NAME_ID_TARIF,
				Tables.RES_NAME_SCAN}, 
				Tables.RES_NAME_ID_CONCERT + " LIKE \"" + id_concert +"\"", null, null, null, null);


		return c.getCount();
	}	
	
/***************** TROUVER LE NOMBRE DE RESERVATION SCANN2 POUR UN CONCERT ***************************/
	
	public int getNumberResForOneConcertScanned(int id_concert){
	
		Cursor c = bdd.query(Tables.RES_TABLE, 
				new String[] {Tables.RES_NAME_ID, 
				Tables.RES_NAME_ID_CONCERT, 
				Tables.RES_NAME_ID_CLIENT,
				Tables.RES_NAME_ID_TARIF,
				Tables.RES_NAME_SCAN}, 
				Tables.RES_NAME_ID_CONCERT + " LIKE \"" + id_concert +"\""
				+ " AND "+Tables.RES_NAME_SCAN + " LIKE \"" + 1 +"\"", null, null, null, null);


		return c.getCount();
	}	
	


/*********************** TESTER SI LA RESERVATION EST VALIDE ET SCANNER LE BILLET ******************************************/
public Boolean isValidTicket(int id_res,int id_concert,int id_client, int id_tarif, int rightConcert){
	
	Cursor c = bdd.query(Tables.RES_TABLE, 
			new String[] {Tables.RES_NAME_ID, 
			Tables.RES_NAME_ID_CONCERT, 
			Tables.RES_NAME_ID_CLIENT,
			Tables.RES_NAME_ID_TARIF,
			Tables.RES_NAME_SCAN}, 
			Tables.RES_NAME_ID + " LIKE \"" + id_res +"\"", null, null, null, null);


	if (c.getCount() != 1 ){
		return false;
	}
	c.moveToFirst();
	Log.i("SCAN", "avant : "+c.getInt(Tables.RES_NUM_ID_SCAN) );//0
	if (c.getInt(Tables.RES_NUM_ID_CONCERT) != id_concert
			|| c.getInt(Tables.RES_NUM_ID_CLIENT) != id_client
			|| c.getInt(Tables.RES_NUM_ID_TARIF) != id_tarif
			|| c.getInt(Tables.RES_NUM_ID_SCAN) == 1
			|| c.getInt(Tables.RES_NUM_ID_CONCERT) != rightConcert){
		return false;
		
	}
	//ContentValues newValues = new ContentValues();
	//newValues.put(Tables.RES_NAME_SCAN, 1);
	
	//bdd.update(Tables.RES_TABLE, newValues,Tables.RES_NAME_ID+"="+id_res, null);
	
	c.close();
	
	return true;
	
	
}

/*********************** Mise a jour du SCAN avec ID **************************************/

public void scanTicket(int id_res){
	
	Cursor c = bdd.query(Tables.RES_TABLE, 
			new String[] {Tables.RES_NAME_ID, 
			Tables.RES_NAME_ID_CONCERT, 
			Tables.RES_NAME_ID_CLIENT,
			Tables.RES_NAME_ID_TARIF,
			Tables.RES_NAME_SCAN}, 
			Tables.RES_NAME_ID + " LIKE \"" + id_res +"\"", null, null, null, null);

	c.moveToFirst();
	Log.i("SCAN", "apres : "+c.getInt(Tables.RES_NUM_ID_SCAN) );//0
	ContentValues newValues = new ContentValues();
	newValues.put(Tables.RES_NAME_SCAN, 1);
	
	bdd.update(Tables.RES_TABLE, newValues,Tables.RES_NAME_ID+"="+id_res, null);
	Log.i("SCAN", "apres 2: "+c.getInt(Tables.RES_NUM_ID_SCAN) );//1
	/*Insertion dans la 2eme table pour mise a jour serveur*/
	insertResMAJ(c.getInt(Tables.RES_NUM_ID), c.getInt(Tables.RES_NUM_ID_CONCERT),
			c.getInt(Tables.RES_NUM_ID_CLIENT), c.getInt(Tables.RES_NUM_ID_TARIF), 1);
	
	c.close();
	
	
}

/***************************  Trouver le lablel du prix par ID*******************************************/
	public static String getLabelById(int id_tarrif){
		
		Cursor c = bdd.query(Tables.TARIFFS_TABLE,
				new String[] {Tables.TARIFF_NAME_ID, 
				Tables.TARIFF_NAME_LABEL,
				Tables.TARIFF_NAME_PRICE}, 
				Tables.TARIFF_NAME_ID + " LIKE \"" + id_tarrif +"\"", null, null, null, null);
		if (c.getCount() != 1)
			return null;
		c.moveToFirst();
		Log.i("TT", "TT : "+c.getInt(0) +"/"+ c.getString(1) +"/"+ c.getDouble(2));
		String label =c.getString(1);
		c.close();
		return label;
		
	}




/*************************** Envoi du json pour mise a jour du scan*******************************************/

	public String getJsonScanMAJ(){
		Cursor c = bdd.query(Tables.RESMAJ_TABLE, 
				new String[] {Tables.RESMAJ_NAME_ID, 
				Tables.RESMAJ_NAME_ID_CONCERT, 
				Tables.RESMAJ_NAME_ID_CLIENT,
				Tables.RESMAJ_NAME_ID_TARIF,
				Tables.RESMAJ_NAME_SCAN}, 
				null, null, null, null, null);
		if(c.getCount()==0){
			return null;
		}
		c.moveToFirst();
		String json = "[";
		for (int i=0; i<c.getCount(); i++){
			if (i!=0)
				c.move(1);
			
				json+="{\"id\":\""+c.getInt(0)+"\",\"id_client\":\""+c.getInt(2)+"\",\"id_concert\":\""+
				c.getInt(1)+"\",\"id_tarif\":\""+c.getInt(3)+"\",\"scan\":\""+c.getInt(4)+"\"}";
			
			if (i!= c.getCount()-1)
				json+=",";
		}
		
		json+="]";
		return json;
	}
	
	/************************ SUPPRESSION D'UNE LIGNE DANS LA TBLE RESMAJ *****************************/
	
	public int deleteInResMAJById(int id){
		return bdd.delete(Tables.RESMAJ_TABLE, Tables.RESMAJ_NAME_ID+"="+id, null);
	}
	
	/************************ VIDER LA TABLE RESMAJ *****************************/
	
	public int deleteResMAJ(){
		return bdd.delete(Tables.RESMAJ_TABLE, null, null);
	}
	
	
	/************************ VIDER TOUTES LES TABLES *****************************/
	public static void deleteAllTable(){
		
		bdd.delete(Tables.CLIENT_TABLE,null,null);
		bdd.delete(Tables.CONCERT_TABLE,null,null);
		bdd.delete(Tables.RES_TABLE,null,null);
		bdd.delete(Tables.ARTISTS_TABLE,null,null);
		bdd.delete(Tables.ASSOC_STYLES_TABLE,null,null);
		bdd.delete(Tables.ASSOC_TARIFFS_TABLE,null,null);
		bdd.delete(Tables.ASSOC_ARTISTS_TABLE,null,null);
		bdd.delete(Tables.STYLES_TABLE,null,null);
		bdd.delete(Tables.TARIFFS_TABLE,null,null);
	}
	
	/************************ METTRE A JOUR LE MOT DE PASSE *****************************/
	
	public void updatePassword(Client client,String pwd){
		
		Cursor c = bdd.query(Tables.CLIENT_TABLE, 
				new String[] {Tables.CLIENT_NAME_ID, 
				Tables.CLIENT_NAME_PASSWORD}, 
				Tables.CLIENT_NAME_ID + " LIKE \"" + client.getId() +"\"", null, null, null, null);

		c.moveToFirst();
		ContentValues newValues = new ContentValues();
		newValues.put(Tables.CLIENT_NAME_PASSWORD, pwd);
		
		bdd.update(Tables.CLIENT_TABLE, newValues,Tables.CLIENT_NAME_ID+"="+client.getId(), null);
	
		c.close();
		
		
	}
	
	
	/******************************** BDD EXTERNE ************************************************/
	
	public static Boolean isAvailableServer(Context context){

		ThreadTestServer tPing = new ThreadTestServer(context);
		tPing.start();
		try {
			tPing.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return tPing.getResult();
	}

	public static Boolean updateAllTable(Context context){
		/********************* Test du serveur et de la connexion internet ******************************/
		if(isNetworkConnected(context) && isAvailableServer(context)){
			/*Vide la table*/
			deleteAllTable();
			/*ON ENVOI LA REQUETE*/
			DatabaseServer dbbs = new DatabaseServer(); 
			MyJsonParser parser = new MyJsonParser(context);

			String tmp =dbbs.getRequest("getAllClients");
			String concertString = dbbs.getRequest("getAllConcerts");
			String reservationString = dbbs.getRequest("getAllReservations");
			String tarrifString = dbbs.getRequest("getAllTariffs");
			String tarrifAssocString = dbbs.getRequest("getAllAssocTarifs");
			String stylesAssocString = dbbs.getRequest("getAllAssocStyles");
			String stylesString = dbbs.getRequest("getAllStyles");
			String artistsString = dbbs.getRequest("getAllArtists");
			String artistsAssocString = dbbs.getRequest("getAllAssocArtists");
			


			List<Client> clientlist = parser.getClientFromJson(tmp);
			List<Concert> concertlist = parser.getConcertFromJson(concertString);

			/*On insere les concerts dans bdd*/
			for (int i=0 ; i< concertlist.size() ; i++){
				Concert c = concertlist.get(i);
				Log.i("Concert",c.testToString());
				insertConcert(c);
			}

			/*On insere les clients dans bdd*/
			for (int i=0 ; i< clientlist.size() ; i++){
				Client c = clientlist.get(i);
				Log.i("Client",c.testToString());
				insertClient(c);
			}
			/*On insere les reservations*/
			parser.getReservationAndInsert(reservationString);

			/*On insere les Tarrifs*/
			parser.getTariffsAndInsert(tarrifString);
			
			/*On insere les AssocTarrifs*/
			parser.getAssocTariffsAndInsert(tarrifAssocString);
			
			/*On insere les AssocTStyle*/
			parser.getAssocStylesAndInsert(stylesAssocString);
			
			/*On insere les AssocTStyle*/
			parser.getStylesAndInsert(stylesString);
			
			/*On insere les Artistes*/
			parser.getArtistsAndInsert(artistsString);
			
			/*On insere les Assoc Artistes*/
			parser.getAssocArtistsAndInsert(artistsAssocString);


			Log.i("SCAN", "TARIF ADULTE ?? ::"+getLabelById(4));
			Log.i("NET", "On est connecté !!");
			ArrayList<String> test = getArtistsFromConcert(2);
			Log.i("ASSOC","SIZE : "+test.size());
			for (String s : test){
				Log.i("ASSOC","Style "+ s);
			}
			return true;
		}


		else{
			Log.i("NET", "On n'est pas connecté !!");
			return false;

		}
	}
	
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());

	}
	
	
}


//[{"id":"1","id_client":"1","id_concert":"1","id_tarif":"1","scan":"0"},
//{"id":"2","id_client":"1","id_concert":"1","id_tarif":"1","scan":"0"}]