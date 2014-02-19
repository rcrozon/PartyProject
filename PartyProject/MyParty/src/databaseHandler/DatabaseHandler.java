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
	
	private static final int VERSION_BDD = 14;
	private static final String BDD_NAME = "myparty.db";
 
	private static final String CLIENT_TABLE = "table_livres";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_FIRSTNAME = "firstname";
	private static final int NUM_COL_FIRSTNAME = 1;
	private static final String COL_LASTNAME = "lastname";
	private static final int NUM_COL_LASTNAME = 2;
	
	/*private static final String CONCERT_TABLE = "concert";
	private static final String COL_ID_CONCERT = "id";
	private static final int NUM_COL_ID_CONCERT = 0;
	private static final String COL_NAME_CONCERT = "name";
	private static final int NUM_COL_NAME_CONCERT = 1;*/
	
	private static final String RES_TABLE = "reservation";
	private static final String COL_ID_RES = "id";
	private static final int NUM_COL_ID_RES = 0;
	private static final String COL_ID_CLIENT_RES = "id_client";
	private static final int NUM_COL_ID_CLIENT_RES = 1;
	private static final String COL_ID_CONCERT_RES = "id_concert";
	private static final int NUM_COL_ID_CONCERT_RES = 2;
 
	private SQLiteDatabase bdd;
  
	private DatabaseCreate SQLiteBase ;
	
	
	public DatabaseHandler(Context context){
		//On cr���er la BDD et sa table
		SQLiteBase = new DatabaseCreate(context, BDD_NAME, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en ���criture
		bdd = SQLiteBase.getWritableDatabase();
		/*Client c1 = new Client(1,"romain", "CROZON", new Date(), "crozonr@gmail.com", "rcrozon", "oee120");
		Client c2 = new Client(2,"simon", "SAVIN", new Date(), "simsav1@gmail.com", "simsav1", "oee121");
		Client c3 = new Client(5,"julie", "DUFOUR", new Date(), "julie@gmail.com", "julie", "oee122");
		insertClient(c1);
		insertClient(c2);
		insertClient(c3);
		Concert conc1 = new Concert(1, "", "This is it", new Date(), new Date(), "Paris", 10.0, 100, false);
		insertConcert(conc1);
		Concert conc2 = new Concert(2, "", "Yeah !", new Date(), new Date(), "Paris", 10.0, 100, false);
		insertConcert(conc2);
		insertRes(1, c1, conc1);
		insertRes(2, c2, conc1);
		insertRes(3, c3, conc2);*/
		
		/*Log.i("CLIENT 1", "Client 1 : " + getClientWithNames("romain", "CROZON").toString());
		Log.i("CLIENT 2", "Client 2 : " + getClientWithNames("simon", "SAVIN").toString());
		Log.i("CLIENT 3", "Client 3 : " + getClientWithNames("julie", "DUFOUR").toString());
		Log.i("CLIENT 1", "Client 1 : " + getClientWithId(1).toString());
		Log.i("CLIENT 2", "Client 2 : " + getClientWithId(2).toString());
		Log.i("CLIENT 3", "Client 3 : " + getClientWithId(5).toString());
		Log.i("Concert", "Concert1 : " + getConcertWithId(1).toString());
		
		List<Client> tmp = new ArrayList<Client>();
		tmp = getClientForOneConcert(1);
		for(int i=0; i<tmp.size();i++){
			Log.i("RES", "Concert :"+ getConcertWithId(1)+ " // Client : " + getClientWithId(tmp.get(i).getId()).toString());
		}
		
		List<Client> tmp2 = new ArrayList<Client>();
		tmp2 = getClientForOneConcert(2);
		for(int i=0; i<tmp2.size();i++){
			Log.i("RES", "Concert :"+ getConcertWithId(2)+ " // Client : " + getClientWithId(tmp2.get(i).getId()).toString());
		}*/
		
		/*NEW BDD*/
		Concert jh = new Concert(1, "chemin", "Jphnny", "15/03/14", "16/03/14", "Londres",
				12.0, 189, false);
		insertConcert(jh);
		Log.i("DATE", "Concert 1 : " + getConcertWithId(1).toString());
		
		
	}
 
	public void close(){
		//on ferme l'acc���s ��� la BDD
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

	public long insertClient(Client client){
		//Cr���ation d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associ��� ��� une cl��� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_ID, client.getId());
		values.put(COL_FIRSTNAME, client.getFirstName());
		values.put(COL_LASTNAME, client.getLastName());
		//on ins���re l'objet dans la BDD via le ContentValues
		return bdd.insert(CLIENT_TABLE, null, values);
	}
 
	public long insertConcert(Concert concert){
		//Cr���ation d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associ��� ��� une cl��� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(Tables.CONCERT_NAME_ID,concert.getId());
		values.put(Tables.CONCERT_NAME_START_DATE, concert.getBeginDate());
		values.put(Tables.CONCERT_NAME_END_DATE, concert.getEndDate());
		values.put(Tables.CONCERT_NAME_LOCATION, concert.getLocation());
		values.put(Tables.CONCERT_NAME_IMAGE, concert.getImagePath());
		values.put(Tables.CONCERT_NAME_NB_SEAT, concert.getNbSeets());
		values.put(Tables.CONCERT_NAME_FULL,0);
		values.put(Tables.CONCERT_NAME_ID_CREATOR,1);
		values.put(Tables.CONCERT_NAME_TITLE_CONCERT, concert.getTitle());
		//on ins���re l'objet dans la BDD via le ContentValues
		return bdd.insert(Tables.CONCERT_TABLE, null, values);
	}
	
	public long insertRes(int id_res,Client client,Concert concert){
		//Cr���ation d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associ��� ��� une cl��� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_ID_RES,id_res);
		values.put(COL_ID_CLIENT_RES,client.getId());
		values.put(COL_ID_CONCERT_RES, concert.getId());
		//on ins���re l'objet dans la BDD via le ContentValues
		return bdd.insert(RES_TABLE, null, values);
	}
	
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
 
	public Client getClientWithNames(String firstName, String lastName){ 
		//R���cup���re dans un Cursor les valeur correspondant ��� un livre contenu dans la BDD (ici on s���lectionne le livre gr���ce ��� son titre)
		Cursor c = bdd.query(CLIENT_TABLE, new String[] {COL_ID, COL_FIRSTNAME, COL_LASTNAME}, COL_LASTNAME + " LIKE \"" + lastName +"\"", null, null, null, null);
		return cursorToClient(c);
	}
	
	private Client cursorToClient(Cursor c){
		//si aucun ���l���ment n'a ���t��� retourn��� dans la requ���te, on renvoie null
		if (c.getCount() == 0){
			Log.i("ERRORO PAS COOL", "Client null");
			return null;
		}
		
		//Sinon on se place sur le premier ���l���ment
		c.moveToFirst();
		//On cr������ un livre
		Client client = new Client(c.getInt(NUM_COL_ID),c.getString(NUM_COL_FIRSTNAME), c.getString(NUM_COL_LASTNAME), new Date(), "", "rcrozon", "oee120");
		//on lui affecte toutes les infos gr���ce aux infos contenues dans le Cursor
		
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return client;
	}

	/*TODO IL FAUT REMPLACER PAR LES BONNES COLONNES !!*/
	public Boolean authentification(String login, String pwd){ 
		//R���cup���re dans un Cursor les valeur correspondant ��� un livre contenu dans la BDD (ici on s���lectionne le livre gr���ce ��� son titre)
		Cursor c = bdd.query(CLIENT_TABLE, new String[] {COL_ID, COL_FIRSTNAME, COL_LASTNAME}, COL_FIRSTNAME + " LIKE \"" + login +"\"", null, null, null, null);
		if (c.getCount() == 1){
			c.moveToFirst();
			//test si c'est le bon mot de passe
			if (pwd.equals(c.getString(NUM_COL_LASTNAME))){
				return true;
			}
		}
		return false;
	}

	public Concert getConcertWithId(int id){ 
		//R���cup���re dans un Cursor les valeur correspondant ��� un livre contenu dans la BDD (ici on s���lectionne le livre gr���ce ��� son titre)
		Cursor c = bdd.query(Tables.CONCERT_TABLE,
				new String[] {Tables.CONCERT_NAME_ID, Tables.CONCERT_NAME_START_DATE,Tables.CONCERT_NAME_END_DATE,
				Tables.CONCERT_NAME_LOCATION,Tables.CONCERT_NAME_IMAGE,Tables.CONCERT_NAME_NB_SEAT,
				Tables.CONCERT_NAME_FULL,Tables.CONCERT_NAME_ID_CREATOR,Tables.CONCERT_NAME_TITLE_CONCERT}, Tables.CONCERT_NAME_ID + " LIKE \"" + id +"\"", null, null, null, null);
		if (c.getCount() != 1){
			return null;
		}
		c.moveToFirst();
		String tmp = c.getString(Tables.CONCERT_NUM_START_DATE);
		Log.i("DATE", "ON obtient : "+ tmp);
		Concert concert = new Concert(c.getInt(Tables.CONCERT_NUM_ID), "", c.getString(Tables.CONCERT_NUM_TITLE_CONCERT), "now", "tomor", "ici", 100.0, 2, false);
		//on lui affecte toutes les infos gr���ce aux infos contenues dans le Cursor
		
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return concert;
		
	}
	
	public Client getClientWithId(int id){ 
		//R���cup���re dans un Cursor les valeur correspondant ��� un livre contenu dans la BDD (ici on s���lectionne le livre gr���ce ��� son titre)
		Cursor c = bdd.query(CLIENT_TABLE, new String[] {COL_ID, COL_FIRSTNAME, COL_LASTNAME}, COL_ID + " LIKE \"" + id +"\"", null, null, null, null);
		if (c.getCount() != 1){
			return null;
		}
		c.moveToFirst();
		
		Client client = new Client(c.getInt(NUM_COL_ID),c.getString(NUM_COL_FIRSTNAME), c.getString(NUM_COL_LASTNAME), new Date(), "", "rcrozon", "oee120");
		//on lui affecte toutes les infos gr���ce aux infos contenues dans le Cursor
		
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return client;
		
	}
	
	public List<Client> getClientForOneConcert(int id_concert){
		List<Client> cl = new ArrayList<Client>();
		
		Cursor c = bdd.query(RES_TABLE, new String[] {COL_ID_RES, COL_ID_CLIENT_RES, COL_ID_CONCERT_RES}, COL_ID_CONCERT_RES + " LIKE \"" + id_concert +"\"", null, null, null, null);
		if (c.getCount() ==0 ){
			return null;
		}
		c.moveToFirst();
		
		for (int i =0; i< c.getCount(); i++){
		if (i!=0){
			c.move(1);
		}
		Client client = getClientWithId(c.getInt(NUM_COL_ID_CLIENT_RES));
		cl.add(client);
		}
		c.close();
		
		return cl;
		
		
	}
	
	
	
}
