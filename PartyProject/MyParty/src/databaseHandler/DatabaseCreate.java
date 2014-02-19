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
	
	private static final String CREATE_BDD_RES ="CREATE TABLE IF NOT EXISTS " + Tables.RES_TABLE 
			+ " (" + Tables.RES_NAME_ID+ " INTEGER PRIMARY KEY, " 
			+ Tables.RES_NAME_ID_CONCERT + " INTEGER, "
			+ Tables.RES_NAME_ID_CLIENT + " INTEGER);";
 
	public DatabaseCreate(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD);
		db.execSQL(CREATE_BDD_CONCERT);
		db.execSQL(CREATE_BDD_RES);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + Tables.CLIENT_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.CONCERT_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.RES_TABLE + ";");
		onCreate(db);
		
	}
}
