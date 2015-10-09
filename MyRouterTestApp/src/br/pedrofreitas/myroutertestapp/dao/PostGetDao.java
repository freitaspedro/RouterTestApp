package br.pedrofreitas.myroutertestapp.dao;

import java.util.ArrayList;

import br.pedrofreitas.myroutertestapp.manager.Ataque;
import br.pedrofreitas.myroutertestapp.manager.PostGet;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PostGetDao {
	
	private SQLiteDatabase db;
	
	private static final String[] colunas = {"id", "id_ataque", "ordem", "tipo", "comando", "token", "usa_login"};
	
	public PostGetDao(Context ctx) {
		db = ctx.openOrCreateDatabase("bancoProjetoFinal", Context.MODE_PRIVATE, null);
	}
	
	public void insert(PostGet x) {
		ContentValues values = new ContentValues();
		
		values.put(colunas[0], x.getId());
		values.put(colunas[1], x.getId_ataque());
		values.put(colunas[2], x.getOrdem());
		values.put(colunas[3], x.getTipo());
		values.put(colunas[4], x.getComando());
		values.put(colunas[5], x.getToken());
		values.put(colunas[6], x.getUsa_login());
		
		db.insert("postget", null, values);
	}
	
	public ArrayList<PostGet> getPostEGetWithIdAtaque(long id_atq) {
		ArrayList<PostGet> list = new ArrayList<PostGet>();
		Cursor c = db.query(true, "postget", colunas, "id_ataque =" + id_atq, null, null, null, null, null);
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(colunas[0]);
			int idxId_ataque = c.getColumnIndex(colunas[1]);
			int idxOrdem = c.getColumnIndex(colunas[2]);
			int idxTipo = c.getColumnIndex(colunas[3]);
			int idxComando = c.getColumnIndex(colunas[4]);
			int idxToken = c.getColumnIndex(colunas[5]);
			int idxUsa_login = c.getColumnIndex(colunas[6]);
			do {
				list.add(new PostGet(c.getLong(idxId), c.getLong(idxId_ataque), c.getInt(idxOrdem), c.getString(idxTipo), c.getString(idxComando), c.getString(idxToken), c.getInt(idxUsa_login)));
			} while(c.moveToNext());
		}
		c.close();
		return list;
	}	
	
	public ArrayList<PostGet> getAll() {
		ArrayList<PostGet> list = new ArrayList<PostGet>();
		Cursor c = db.query(true, "postget", colunas, null, null, null, null, null, null);		
		if(c.moveToFirst()) {
			int idxId = c.getColumnIndex(colunas[0]);
			int idxId_ataque = c.getColumnIndex(colunas[1]);
			int idxOrdem = c.getColumnIndex(colunas[2]);
			int idxTipo = c.getColumnIndex(colunas[3]);
			int idxComando = c.getColumnIndex(colunas[4]);
			int idxToken = c.getColumnIndex(colunas[5]);
			int idxUsa_login = c.getColumnIndex(colunas[6]);
			do {
				list.add(new PostGet(c.getLong(idxId), c.getLong(idxId_ataque), c.getInt(idxOrdem), c.getString(idxTipo), c.getString(idxComando), c.getString(idxToken), c.getInt(idxUsa_login)));
			} while(c.moveToNext());
		}
		c.close();
		return list;
	}
	
	public void close(){
		db.close();
	}

}
