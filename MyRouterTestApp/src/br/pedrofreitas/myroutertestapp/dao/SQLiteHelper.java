package br.pedrofreitas.myroutertestapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {	
	
	private String[] scriptSQLCreate;
	private String scriptSQLDelete;	
	
	public SQLiteHelper(Context context, String name, CursorFactory factory, int version, String[] scriptSQLCreate, String scriptSQLDelete) {
		super(context, name, factory, version);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		int qtdScripts = scriptSQLCreate.length;
		
		for(int i = 0; i < qtdScripts ; i++){
			String sql = scriptSQLCreate[i];
			db.execSQL(sql);
		}		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(scriptSQLDelete);
		onCreate(db);		
	}
	
	
	
}
