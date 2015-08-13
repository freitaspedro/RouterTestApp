package br.pedrofreitas.myroutertestapp.dao;

import java.util.ArrayList;

import br.pedrofreitas.myroutertestapp.manager.Params;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ParamsDao {

	private SQLiteDatabase db;
	private static final String[] colunas = {"id","id_comando","nome","valor"};
	
	public ParamsDao(Context ctx) {
		db = ctx.openOrCreateDatabase("bancoProjetoFinal", Context.MODE_PRIVATE, null);
	}
	
	public void insert(Params x) {
		ContentValues values = new ContentValues();
		
		values.put(colunas[0], x.getId());
		values.put(colunas[1], x.getId_comando());
		values.put(colunas[2], x.getNome());
		values.put(colunas[3], x.getValor());
		
		db.insert("params", null, values);
	}
	
	public ArrayList<Params> getParamsWithIdComando(long id_atq) {
		ArrayList<Params> list = new ArrayList<Params>();
		Cursor c = db.query(true, "params", colunas, "id_comando" + "=" + id_atq, null, null, null, null, null);
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(colunas[0]);
			int idxId_comando = c.getColumnIndex(colunas[1]);
			int idxNome = c.getColumnIndex(colunas[2]);
			int idxValor = c.getColumnIndex(colunas[3]);
			do {
				list.add(new Params(c.getLong(idxId), c.getLong(idxId_comando), c.getString(idxNome), c.getString(idxValor)));
			} while(c.moveToNext());
		}
		c.close();
		return list;
	}		
	
	public void close(){
		db.close();
	}
	
}
