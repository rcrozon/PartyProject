package databaseHandler;

import concert.Client;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCreate extends SQLiteOpenHelper {
	 
	private SQLiteDatabase bdd;
	
	private static final String CLIENT_TABLE = "table_livres";
	private static final String COL_ID = "id";
	private static final String COL_FIRSTNAME = "firstname";
	private static final String COL_LASTNAME = "lastname";
 
	private static final String CREATE_BDD = "CREATE TABLE " + CLIENT_TABLE + " ("
	+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_FIRSTNAME + " TEXT NOT NULL, "
	+ COL_LASTNAME + " TEXT NOT NULL);";
 
	public DatabaseCreate(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		//on créé la table à partir de la requête écrite dans la variable CREATE_BDD
		db.execSQL(CREATE_BDD);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
		//comme ça lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE " + CLIENT_TABLE + ";");
		onCreate(db);
	}
}
