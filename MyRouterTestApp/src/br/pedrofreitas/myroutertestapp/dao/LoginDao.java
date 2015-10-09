package br.pedrofreitas.myroutertestapp.dao;

import java.util.ArrayList;

import br.pedrofreitas.myroutertestapp.manager.Login;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LoginDao {
	
	private SQLiteDatabase db;
	
	private static final String[] colunas = {"id", "usuario", "senha", "operadora"};
	
	public LoginDao(Context ctx) {
		db = ctx.openOrCreateDatabase("bancoProjetoFinal", Context.MODE_PRIVATE, null);
	}
	
	
	public void save(Login login) {
		long id = login.getId();
		
		//Select * from ataque where id = ataq.id
		Cursor c = db.query(true, "login", colunas, colunas[0] + "=" + id, null, null, null, null, null);
		
		if(c.getCount() == 0){
			insert(login);
		}
		else{
			update(login);
		}
		c.close();
	}
	
	public void update(Login login) {
		ContentValues values = new ContentValues();
		
		values.put(colunas[0], login.getId());
		values.put(colunas[1], login.getUsuario());
		values.put(colunas[2], login.getSenha());
		values.put(colunas[3], login.getOperadora());
		
		String id = String.valueOf(login.getId());
		String where = colunas[0] + "=?";
		String[] whereArgs = new String[] { id };
		
		db.update("login", values, where, whereArgs);
	}
	
	public void insert(Login login) {
		ContentValues values = new ContentValues();
		
		values.put(colunas[0], login.getId());
		values.put(colunas[1], login.getUsuario());
		values.put(colunas[2], login.getSenha());
		values.put(colunas[3], login.getOperadora());
		
		db.insert("login", null, values);
	}
	
	public ArrayList<Login> getLoginPorOperadora(String operadora) {
		ArrayList<Login> list = new ArrayList<Login>();
		Cursor c = db.query(true, "login", colunas, "operadora =" + "'" + operadora + "'", null, null, null, null, null);
		
		if(c.moveToFirst()) {
			int idxId = c.getColumnIndex(colunas[0]);
			int idxUsuario = c.getColumnIndex(colunas[1]);
			int idxSenha = c.getColumnIndex(colunas[2]);
			int idxOperadora = c.getColumnIndex(colunas[3]);
			do {
				list.add(new Login(c.getLong(idxId), c.getString(idxUsuario), c.getString(idxSenha), c.getString(idxOperadora)));
			} while(c.moveToNext());
		}
		c.close();
		return list;
	}
	
	public ArrayList<Login> getLoginPorNaoOperadora(String operadora) {
		ArrayList<Login> list = new ArrayList<Login>();
		Cursor c = db.query(true, "login", colunas, "operadora <>" + "'" + operadora + "'", null, null, null, null, null);
		
		if(c.moveToFirst()) {
			int idxId = c.getColumnIndex(colunas[0]);
			int idxUsuario = c.getColumnIndex(colunas[1]);
			int idxSenha = c.getColumnIndex(colunas[2]);
			int idxOperadora = c.getColumnIndex(colunas[3]);
			do {
				list.add(new Login(c.getLong(idxId), c.getString(idxUsuario), c.getString(idxSenha), c.getString(idxOperadora)));
			} while(c.moveToNext());
		}
		c.close();
		return list;
	}
	
	public void close(){
		db.close();
	}

}
