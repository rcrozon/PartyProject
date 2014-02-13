package databaseHandler;

import java.util.Date;

import concert.Client;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseHandler {
	
	private static final int VERSION_BDD = 1;
	private static final String BDD_NAME = "myparty.db";
 
	private static final String CLIENT_TABLE = "table_livres";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_FIRSTNAME = "firstname";
	private static final int NUM_COL_FIRSTNAME = 1;
	private static final String COL_LASTNAME = "lastname";
	private static final int NUM_COL_LASTNAME = 2;
 
	private SQLiteDatabase bdd;
  
	private DatabaseCreate SQLiteBase ;
	
	
	public DatabaseHandler(Context context){
		//On cr�er la BDD et sa table
		SQLiteBase = new DatabaseCreate(context, BDD_NAME, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en �criture
		bdd = SQLiteBase.getWritableDatabase();
		/*Client c1 = new Client("romain", "CROZON", new Date(), "crozonr@gmail.com", "rcrozon", "oee120");
		Client c2 = new Client("simon", "SAVIN", new Date(), "simsav1@gmail.com", "simsav1", "oee121");
		Client c3 = new Client("julie", "DUFOUR", new Date(), "julie@gmail.com", "julie", "oee122");
		insertClient(c1);
		insertClient(c2);
		insertClient(c3);*/
		Log.i("CLIENT 1", "Client 1 : " + getClientWithNames("romain", "CROZON").toString());
		Log.i("CLIENT 2", "Client 2 : " + getClientWithNames("simon", "SAVIN").toString());
		Log.i("CLIENT 3", "Client 3 : " + getClientWithNames("julie", "DUFOUR").toString());
	}
 
	public void close(){
		//on ferme l'acc�s � la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}

	public long insertClient(Client client){
		//Cr�ation d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associ� � une cl� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_FIRSTNAME, client.getFirstName());
		values.put(COL_LASTNAME, client.getLastName());
		//on ins�re l'objet dans la BDD via le ContentValues
		return bdd.insert(CLIENT_TABLE, null, values);
	}
 
//	public int updateLivre(int id, Livre livre){
//		//La mise � jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
//		//il faut simple pr�ciser quelle livre on doit mettre � jour gr�ce � l'ID
//		ContentValues values = new ContentValues();
//		values.put(COL_FIRSTNAME, livre.getIsbn());
//		values.put(COL_TITRE, livre.getTitre());
//		return bdd.update(CLIENT_TABLE, values, COL_ID + " = " +id, null);
//	}
 
//	public int removeLivreWithID(int id){
//		//Suppression d'un livre de la BDD gr�ce � l'ID
//		return bdd.delete(CLIENT_TABLE, COL_ID + " = " +id, null);
//	}
 
	public Client getClientWithNames(String firstName, String lastName){ 
		//R�cup�re dans un Cursor les valeur correspondant � un livre contenu dans la BDD (ici on s�lectionne le livre gr�ce � son titre)
		Cursor c = bdd.query(CLIENT_TABLE, new String[] {COL_ID, COL_FIRSTNAME, COL_LASTNAME}, COL_LASTNAME + " LIKE \"" + lastName +"\"", null, null, null, null);
		return cursorToClient(c);
	}
	
	private Client cursorToClient(Cursor c){
		//si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null
		if (c.getCount() == 0){
			Log.i("ERRORO PAS COOL", "Client null");
			return null;
		}
		
		//Sinon on se place sur le premier �l�ment
		c.moveToFirst();
		//On cr�� un livre
		Client client = new Client(c.getString(NUM_COL_FIRSTNAME), c.getString(NUM_COL_LASTNAME), new Date(), "", "rcrozon", "oee120");
		//on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor
		
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return client;
	}

	/*IL FAUT REMPLACER PAR LES BONNES COLONNES !!*/
	public Boolean authentification(String login, String pwd){ 
		//R�cup�re dans un Cursor les valeur correspondant � un livre contenu dans la BDD (ici on s�lectionne le livre gr�ce � son titre)
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

}
