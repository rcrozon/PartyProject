package databaseHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Client;
import entities.Concert;
import android.app.ActionBar.Tab;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

public class DatabaseHandler {
	
	private static final int VERSION_BDD = 43;
	private static final String BDD_NAME = "myparty.db";
	private SQLiteDatabase bdd;
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
	
	public void deleteAll()
	{
	    bdd = SQLiteBase.getWritableDatabase();
	    bdd.delete(Tables.CONCERT_TABLE,null,null);
	    bdd.execSQL("delete from "+ Tables.CONCERT_TABLE);
	    bdd.execSQL("TRUNCATE table " + Tables.CONCERT_TABLE);
	    bdd.close();
	}
	
/***************** INITIALISATION DE LA BDD ***************************/	

	public void initBDD(){
	}
	
/***************** INSERER UN CLIENT DANS LA BDD ***************************/
	
	public long insertClient(Client client){
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
	
	public long insertConcert(Concert concert){
		ContentValues values = new ContentValues();
		values.put(Tables.CONCERT_NAME_ID,concert.getId());
		values.put(Tables.CONCERT_NAME_START_DATE, concert.getBeginDate());
		values.put(Tables.CONCERT_NAME_END_DATE, concert.getEndDate());
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
	
	public long insertRes(int id_res,Concert concert,Client client,int scan){
		ContentValues values = new ContentValues();
		values.put(Tables.RES_NAME_ID,id_res);
		values.put(Tables.RES_NAME_ID_CONCERT,concert.getId());
		values.put(Tables.RES_NAME_ID_CLIENT, client.getId());
		values.put(Tables.RES_NAME_ID_TARIF,concert.getIdTarif());
		values.put(Tables.RES_NAME_SCAN, scan);
		return bdd.insert(Tables.RES_TABLE, null, values);
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
		if (c.getCount() == 1){
			c.moveToFirst();
			Log.i("ini", "log  "+ c.getString(1) + "    MDP   "+ c.getString(2) + "  attendu "+ pwd);
			if (pwd.equals(c.getString(2))){
				return true;
			}
		}
		return false;
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
				Tables.CONCERT_NAME_ID_TARIF}, 
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
	
/***************** TROUVER LA LISTE DES CLIENTS POUR UN CONCERT DANS LA BDD ***************************/
	
	public List<Client> getClientForOneConcert(int id_concert){
		List<Client> cl = new ArrayList<Client>();
	
		Cursor c = bdd.query(Tables.RES_TABLE, 
				new String[] {Tables.RES_NAME_ID, 
				Tables.RES_NAME_ID_CONCERT, 
				Tables.RES_NAME_ID_CLIENT,
				Tables.RES_NAME_ID_TARIF,
				Tables.RES_NAME_SCAN}, 
				Tables.RES_NAME_ID_CONCERT + " LIKE \"" + id_concert +"\"", null, null, null, null);
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
				Tables.CONCERT_NAME_ID_TARIF}, 
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
	
	
	
}
