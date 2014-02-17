	package databaseHandler;

import concert.Client;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseCreate extends SQLiteOpenHelper {
	private SQLiteDatabase bdd;
	
	private static final String CLIENT_TABLE = "table_livres";
	private static final String COL_ID = "id";
	private static final String COL_FIRSTNAME = "firstname";
	private static final String COL_LASTNAME = "lastname";
	
	private static final String CONCERT_TABLE = "concert";
	private static final String COL_ID_CONCERT = "id";
	private static final String COL_NAME_CONCERT = "name";
 
	private static final String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + CLIENT_TABLE + " ("
	+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_FIRSTNAME + " TEXT NOT NULL, "
	+ COL_LASTNAME + " TEXT NOT NULL);";
	
	private static final String CREATE_BDD_CONCERT ="CREATE TABLE IF NOT EXISTS " + CONCERT_TABLE + " (" + COL_ID_CONCERT
	+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME_CONCERT + " TEXT NOT NULL);";
 
	public DatabaseCreate(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		//on cr�� la table � partir de la requ�te �crite dans la variable CREATE_BDD
		db.execSQL(CREATE_BDD);
		Log.i("ONCREATE", "on passe");
		db.execSQL(CREATE_BDD_CONCERT);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//On peut fait ce qu'on veut ici moi j'ai d�cid� de supprimer la table et de la recr�er
		//comme �a lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE " + CLIENT_TABLE + ";");
		db.execSQL("DROP TABLE " + CONCERT_TABLE + ";");
		onCreate(db);
	}
}
