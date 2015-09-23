package br.pedrofreitas.myroutertestapp.dao;

import java.util.ArrayList;

import br.pedrofreitas.myroutertestapp.manager.Ataque;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AtaqueDao {
	
	private SQLiteDatabase db;
	
	private static final String[] colunas = {"id", "tipo", "operadora", "usa_cookie", "fabricante_modelo"};
	
	public AtaqueDao(Context ctx) {
		db = ctx.openOrCreateDatabase("bancoProjetoFinal", Context.MODE_PRIVATE, null);
	}
	
	public void insert(Ataque ataq) {
		ContentValues values = new ContentValues();
		
		values.put(colunas[0], ataq.getId());
		values.put(colunas[1], ataq.getTipo());
		values.put(colunas[2], ataq.getOperadora());
		values.put(colunas[3], ataq.getUsa_cookie());
		values.put(colunas[4], ataq.getFabricante_modelo());
	
		db.insert("ataque", null, values);
	}
	
	public Ataque getAtaqueWithId(long id_atq) {
		Ataque atq = null;
		Cursor c = db.query(true, "ataque", colunas, "id" + "=" + id_atq, null, null, null, null, null);
		if(c.getCount() > 0) {
			c.moveToFirst();
			int idxId = c.getColumnIndex(colunas[0]);
			int idxTipo = c.getColumnIndex(colunas[1]);
			int idxOperadora = c.getColumnIndex(colunas[2]);
			int idxUsa_Cookie = c.getColumnIndex(colunas[3]);
			int idxFabricante_modelo = c.getColumnIndex(colunas[4]);
			
			atq = new Ataque(c.getLong(idxId), c.getString(idxTipo), c.getString(idxOperadora), c.getInt(idxUsa_Cookie), 
					 c.getString(idxFabricante_modelo));
		}
		c.close();
		return atq;
	}	
	
	public ArrayList<Ataque> getAtaquesWithOperadoraETipo(String operadora, String tipo) {
		ArrayList<Ataque> list = new ArrayList<Ataque>();
		Cursor c = null;
		if (operadora != null) {
			c = db.query(true, "ataque", colunas, "operadora = '" + operadora + "' AND tipo = '" + tipo + "'", null, null, null, null, null);
		}
		else {
			c = db.query(true, "ataque", colunas, "tipo = '" + tipo + "'", null, null, null, null, null);
		}
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(colunas[0]);
			int idxTipo = c.getColumnIndex(colunas[1]);
			int idxOperadora = c.getColumnIndex(colunas[2]);
			int idxUsa_Cookie = c.getColumnIndex(colunas[3]);
			int idxFabricante_modelo = c.getColumnIndex(colunas[4]);
			do {
				list.add(new Ataque(c.getLong(idxId), c.getString(idxTipo), c.getString(idxOperadora), c.getInt(idxUsa_Cookie), 
						 c.getString(idxFabricante_modelo)));
			} while(c.moveToNext());
		}
		c.close();
		return list;
	}
	
	public ArrayList<Ataque> getAtaquesWithOperadoraETipoEFabricanteModelo(String operadora, String tipo, String fabricante_modelo) {
		ArrayList<Ataque> list = new ArrayList<Ataque>();
		Cursor c = null;
		if (operadora != null) {
			c = db.query(true, "ataque", colunas, "operadora = '" + operadora + "' AND tipo = '" + tipo + "' AND fabricante_modelo = '" + fabricante_modelo + "'", null, null, null, null, null);
		} else {
			c = db.query(true, "ataque", colunas, "tipo = '" + tipo + "' AND fabricante_modelo = '" + fabricante_modelo + "'", null, null, null, null, null);
		}
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(colunas[0]);
			int idxTipo = c.getColumnIndex(colunas[1]);
			int idxOperadora = c.getColumnIndex(colunas[2]);
			int idxUsa_Cookie = c.getColumnIndex(colunas[3]);
			int idxFabricante_modelo = c.getColumnIndex(colunas[4]);
			do {
				list.add(new Ataque(c.getLong(idxId), c.getString(idxTipo), c.getString(idxOperadora), c.getInt(idxUsa_Cookie), 
						 c.getString(idxFabricante_modelo)));
			} while(c.moveToNext());
		}
		c.close();
		return list;
	}
	 
	public ArrayList<Ataque> getAll() {
		ArrayList<Ataque> list = new ArrayList<Ataque>();
		Cursor c = db.query(true, "ataque", colunas, null, null, null, null, null, null);
		
		if(c.moveToFirst()) {
			int idxId = c.getColumnIndex(colunas[0]);
			int idxTipo = c.getColumnIndex(colunas[1]);
			int idxOperadora = c.getColumnIndex(colunas[2]);
			int idxUsa_Cookie = c.getColumnIndex(colunas[3]);
			int idxFabricante_modelo = c.getColumnIndex(colunas[4]);
			do {
				list.add(new Ataque(c.getLong(idxId), c.getString(idxTipo), c.getString(idxOperadora), c.getInt(idxUsa_Cookie), 
						 c.getString(idxFabricante_modelo)));
			} while(c.moveToNext());
		}
		c.close();
		return list;
	}
	
	public void close() {
		db.close();
	}
	
}

