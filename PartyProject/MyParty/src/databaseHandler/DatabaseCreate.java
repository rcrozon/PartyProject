	package databaseHandler;

import entities.Client;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseCreate extends SQLiteOpenHelper {
	private SQLiteDatabase bdd;
	
	/*private static final String CLIENT_TABLE = "table_livres";
	private static final String COL_ID = "id";
	private static final String COL_FIRSTNAME = "firstname";
	private static final String COL_LASTNAME = "lastname";*/
	
	/*private static final String CONCERT_TABLE = "concert";
	private static final String COL_ID_CONCERT = "id";
	private static final String COL_NAME_CONCERT = "name";*/
	
	private static final String RES_TABLE = "reservation";
	private static final String COL_ID_RES = "id";
	private static final String COL_ID_CLIENT_RES = "id_client";
	private static final String COL_ID_CONCERT_RES = "id_concert";
 
	private static final String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + Tables.CLIENT_TABLE 
			+ " ("+ Tables.CLIENT_NAME_ID + " INTEGER PRIMARY KEY, " 
			+ Tables.CLIENT_NAME_USERNAME + " TEXT NOT NULL, "
			+ Tables.CLIENT_NAME_MAIL + " TEXT NOT NULL, "
			+ Tables.CLIENT_NAME_PASSWORD + " TEXT NOT NULL, "
			+ Tables.CLIENT_NAME_FIRSTNAME + " TEXT NOT NULL, "
			+ Tables.CLIENT_NAME_LASTNAME + " TEXT NOT NULL, "
			+ Tables.CLIENT_NAME_ADMIN + " TEXT NOT NULL, "
			+ Tables.CLIENT_NAME_DATE_CREATE + " TEXT NOT NULL);";
	
	private static final String CREATE_BDD_CONCERT ="CREATE TABLE IF NOT EXISTS " + Tables.CONCERT_TABLE 
			+ " (" + Tables.CONCERT_NAME_ID + " INTEGER PRIMARY KEY, "
			+ Tables.CONCERT_NAME_START_DATE + " TEXT NOT NULL, "
			+ Tables.CONCERT_NAME_END_DATE + " TEXT NOT NULL, "
			+ Tables.CONCERT_NAME_LOCATION + " TEXT NOT NULL, "
			+ Tables.CONCERT_NAME_IMAGE + " TEXT NOT NULL, "
			+ Tables.CONCERT_NAME_NB_SEAT + " INTEGER, "
			+ Tables.CONCERT_NAME_FULL + " INTEGER, "
			+ Tables.CONCERT_NAME_ID_CREATOR + " INTEGER, "
			+ Tables.CONCERT_NAME_TITLE_CONCERT + " TEXT NOT NULL);";
	
	private static final String CREATE_BDD_RES ="CREATE TABLE IF NOT EXISTS " + RES_TABLE + " (" + COL_ID_RES
			+ " INTEGER PRIMARY KEY, " + COL_ID_CLIENT_RES + " INTEGER, "+ COL_ID_CONCERT_RES + " INTEGER);";
 
	public DatabaseCreate(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		//on cr�� la table � partir de la requ�te �crite dans la variable CREATE_BDD
		db.execSQL(CREATE_BDD);
		Log.i("ONCREATE", "on passe");
		db.execSQL(CREATE_BDD_CONCERT);
		db.execSQL(CREATE_BDD_RES);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//On peut fait ce qu'on veut ici moi j'ai d�cid� de supprimer la table et de la recr�er
		//comme �a lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE " + Tables.CLIENT_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.CONCERT_TABLE + ";");
		db.execSQL("DROP TABLE " + RES_TABLE + ";");
		onCreate(db);
	}
}
