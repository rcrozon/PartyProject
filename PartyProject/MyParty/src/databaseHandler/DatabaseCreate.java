	package databaseHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCreate extends SQLiteOpenHelper {
	
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
			+ Tables.CONCERT_NAME_TITLE_CONCERT + " TEXT NOT NULL, "
			+ Tables.CONCERT_NAME_ONLINE + " INTEGER, "
			+ Tables.CONCERT_NAME_ID_TARIF + " INTEGER);";
	
	private static final String CREATE_BDD_RES ="CREATE TABLE IF NOT EXISTS " + Tables.RES_TABLE 
			+ " (" + Tables.RES_NAME_ID+ " INTEGER PRIMARY KEY, " 
			+ Tables.RES_NAME_ID_CONCERT + " INTEGER, "
			+ Tables.RES_NAME_ID_CLIENT + " INTEGER, "
			+ Tables.RES_NAME_ID_TARIF + " INTEGER, "
			+ Tables.RES_NAME_SCAN + " INTEGER, "
			+ "FOREIGN KEY ("+Tables.RES_NAME_ID_CONCERT+") REFERENCES "
			+Tables.CONCERT_TABLE+" ("+Tables.CONCERT_NAME_ID+"), "
			+ "FOREIGN KEY ("+Tables.RES_NAME_ID_CLIENT+") REFERENCES "
			+Tables.CLIENT_TABLE+" ("+Tables.CLIENT_NAME_ID+"));";
	
	private static final String CREATE_BDD_RES_MAJ ="CREATE TABLE IF NOT EXISTS " + Tables.RESMAJ_TABLE 
			+ " (" + Tables.RESMAJ_NAME_ID+ " INTEGER PRIMARY KEY, " 
			+ Tables.RESMAJ_NAME_ID_CONCERT + " INTEGER, "
			+ Tables.RESMAJ_NAME_ID_CLIENT + " INTEGER, "
			+ Tables.RESMAJ_NAME_ID_TARIF + " INTEGER, "
			+ Tables.RESMAJ_NAME_SCAN + " INTEGER);";
	
	private static final String CREATE_BDD_ARTIST = "CREATE TABLE IF NOT EXISTS " + Tables.ARTISTS_TABLE 
			+ " (" + Tables.ARTIST_NAME_ID+ " INTEGER PRIMARY KEY, " 
			+ Tables.ARTIST_NAME_ARTIST_NAME + " TEXT NOT NULL UNIQUE);";

	private static final String CREATE_BDD_STYLES = "CREATE TABLE IF NOT EXISTS " + Tables.STYLES_TABLE 
			+ " (" + Tables.STYLE_NAME_ID+ " INTEGER PRIMARY KEY, " 
			+ Tables.STYLE_NAME_STYLE_NAME + " TEXT NOT NULL UNIQUE);";
	
	private static final String CREATE_BDD_TARIFFS = "CREATE TABLE IF NOT EXISTS " + Tables.TARIFFS_TABLE 
			+ " (" + Tables.TARIFF_NAME_ID+ " INTEGER PRIMARY KEY, " 
			+ Tables.TARIFF_NAME_LABEL + " TEXT, "
			+ Tables.TARIFF_NAME_PRICE + " REAL NOT NULL );";

	private static final String CREATE_BDD_ASSOC_TARIFFS ="CREATE TABLE IF NOT EXISTS " + Tables.ASSOC_TARIFFS_TABLE 
			+ " (" + Tables.ASSOC_TARIFF_NAME_ID+ " INTEGER PRIMARY KEY, " 
			+ Tables.ASSOC_TARIFF_NAME_ID_TARIFF + " INTEGER, "
			+ Tables.ASSOC_TARIFF_NAME_ID_CONCERT + " INTEGER, "
			+ "FOREIGN KEY ("+Tables.ASSOC_TARIFF_NAME_ID_TARIFF+") REFERENCES "
			+Tables.TARIFFS_TABLE+" ("+Tables.TARIFF_NAME_ID+"), "
			+ "FOREIGN KEY ("+Tables.ASSOC_TARIFF_NAME_ID_CONCERT+") REFERENCES "
			+Tables.CONCERT_TABLE+" ("+Tables.CONCERT_NAME_ID+"));";
	

	private static final String CREATE_BDD_ASSOC_STYLES ="CREATE TABLE IF NOT EXISTS " + Tables.ASSOC_STYLES_TABLE 
			+ " (" + Tables.ASSOC_STYLES_NAME_ID+ " INTEGER PRIMARY KEY, " 
			+ Tables.ASSOC_STYLES_NAME_ID_STYLES + " INTEGER, "
			+ Tables.ASSOC_STYLES_NAME_ID_CONCERT + " INTEGER, "
			+ "FOREIGN KEY ("+Tables.ASSOC_STYLES_NAME_ID_STYLES+") REFERENCES "
			+Tables.STYLES_TABLE+" ("+Tables.STYLE_NAME_ID+"), "
			+ "FOREIGN KEY ("+Tables.ASSOC_STYLES_NAME_ID_CONCERT+") REFERENCES "
			+Tables.CONCERT_TABLE+" ("+Tables.CONCERT_NAME_ID+"));";
	

	private static final String CREATE_BDD_ASSOC_ARTISTS ="CREATE TABLE IF NOT EXISTS " + Tables.ASSOC_ARTISTS_TABLE 
			+ " (" + Tables.ASSOC_ARTIST_NAME_ID+ " INTEGER PRIMARY KEY, " 
			+ Tables.ASSOC_ARTIST_NAME_ID_ARTISTS + " INTEGER, "
			+ Tables.ASSOC_ARTIST_NAME_ID_CONCERT + " INTEGER, "
			+ "FOREIGN KEY ("+Tables.ASSOC_ARTIST_NAME_ID_ARTISTS+") REFERENCES "
			+Tables.ARTISTS_TABLE+" ("+Tables.ARTIST_NAME_ID+"), "
			+ "FOREIGN KEY ("+Tables.ASSOC_ARTIST_NAME_ID_CONCERT+") REFERENCES "
			+Tables.CONCERT_TABLE+" ("+Tables.CONCERT_NAME_ID+"));";
	
 
	public DatabaseCreate(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD);
		db.execSQL(CREATE_BDD_CONCERT);
		db.execSQL(CREATE_BDD_RES);
		db.execSQL(CREATE_BDD_RES_MAJ);
		db.execSQL(CREATE_BDD_ARTIST);
		db.execSQL(CREATE_BDD_ASSOC_ARTISTS);
		db.execSQL(CREATE_BDD_ASSOC_STYLES);
		db.execSQL(CREATE_BDD_ASSOC_TARIFFS);
		db.execSQL(CREATE_BDD_STYLES);
		db.execSQL(CREATE_BDD_TARIFFS);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + Tables.CLIENT_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.CONCERT_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.RES_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.RESMAJ_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.ARTISTS_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.ASSOC_STYLES_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.ASSOC_TARIFFS_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.ASSOC_ARTISTS_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.STYLES_TABLE + ";");
		db.execSQL("DROP TABLE " + Tables.TARIFFS_TABLE + ";");
		onCreate(db);
		
	}
}
