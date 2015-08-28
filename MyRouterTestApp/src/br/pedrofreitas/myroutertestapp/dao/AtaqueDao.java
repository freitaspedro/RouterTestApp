package br.pedrofreitas.myroutertestapp.dao;

import java.util.ArrayList;

import br.pedrofreitas.myroutertestapp.manager.Ataque;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AtaqueDao {
	
	private SQLiteDatabase db;
	
	private static final String[] colunas = {"id", "tipo", "comando", "operadora", "usa_login", "usa_chave_de_sessao", "usa_post_ou_get", "caminho_get_chave", 
		"forma_da_chave", "tamanho_da_chave", "fabricante_modelo"};
	
	public AtaqueDao(Context ctx) {
		db = ctx.openOrCreateDatabase("bancoProjetoFinal", Context.MODE_PRIVATE, null);
	}
	
	public void insert(Ataque ataq) {
		ContentValues values = new ContentValues();
		
		values.put(colunas[0], ataq.getId());
		values.put(colunas[1], ataq.getTipo());
		values.put(colunas[2], ataq.getComando());
		values.put(colunas[3], ataq.getOperadora());
		values.put(colunas[4], ataq.getUsa_login());
		values.put(colunas[5], ataq.getUsa_chave_de_sessao());
		values.put(colunas[6], ataq.getUsa_post_ou_get());
		values.put(colunas[7], ataq.getCaminho_get_chave());
		values.put(colunas[8], ataq.getForma_da_chave());
		values.put(colunas[9], ataq.getTamanho_da_chave());
		values.put(colunas[10], ataq.getFabricante_modelo());
		
		db.insert("ataque", null, values);
	}
	
	public Ataque getAtaqueWithId(long id_atq) {
		Ataque atq = null;
		Cursor c = db.query(true, "ataque", colunas, "id" + "=" + id_atq, null, null, null, null, null);
		if(c.getCount() > 0) {
			c.moveToFirst();
			int idxId = c.getColumnIndex(colunas[0]);
			int idxTipo = c.getColumnIndex(colunas[1]);
			int idxComando = c.getColumnIndex(colunas[2]);
			int idxOperadora = c.getColumnIndex(colunas[3]);
			int idxUsa_Login = c.getColumnIndex(colunas[4]);
			int idxUsa_Chave = c.getColumnIndex(colunas[5]);
			int idxUsa_post_ou_get = c.getColumnIndex(colunas[6]);
			int idxCaminho_Chave = c.getColumnIndex(colunas[7]);
			int idxForma_Chave = c.getColumnIndex(colunas[8]);
			int idxTamanho_Chave = c.getColumnIndex(colunas[9]);
			int idxFabricante_modelo = c.getColumnIndex(colunas[10]);
			
			atq = new Ataque(c.getLong(idxId), c.getString(idxTipo), c.getString(idxComando), c.getString(idxOperadora), c.getInt(idxUsa_Login), c.getInt(idxUsa_Chave), 
					c.getInt(idxUsa_post_ou_get), c.getString(idxCaminho_Chave), c.getString(idxForma_Chave), c.getInt(idxTamanho_Chave), c.getString(idxFabricante_modelo));
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
			int idxComando = c.getColumnIndex(colunas[2]);
			int idxOperadora = c.getColumnIndex(colunas[3]);
			int idxUsa_Login = c.getColumnIndex(colunas[4]);
			int idxUsa_Chave = c.getColumnIndex(colunas[5]);
			int idxUsa_post_ou_get = c.getColumnIndex(colunas[6]);
			int idxCaminho_Chave = c.getColumnIndex(colunas[7]);
			int idxForma_Chave = c.getColumnIndex(colunas[8]);
			int idxTamanho_Chave = c.getColumnIndex(colunas[9]);
			int idxFabricante_modelo = c.getColumnIndex(colunas[10]);
			do {
				list.add(new Ataque(c.getLong(idxId), c.getString(idxTipo), c.getString(idxComando), c.getString(idxOperadora), c.getInt(idxUsa_Login), c.getInt(idxUsa_Chave), 
						c.getInt(idxUsa_post_ou_get), c.getString(idxCaminho_Chave), c.getString(idxForma_Chave), c.getInt(idxTamanho_Chave), c.getString(idxFabricante_modelo)));
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
			int idxComando = c.getColumnIndex(colunas[2]);
			int idxOperadora = c.getColumnIndex(colunas[3]);
			int idxUsa_Login = c.getColumnIndex(colunas[4]);
			int idxUsa_Chave = c.getColumnIndex(colunas[5]);
			int idxUsa_post_ou_get = c.getColumnIndex(colunas[6]);
			int idxCaminho_Chave = c.getColumnIndex(colunas[7]);
			int idxForma_Chave = c.getColumnIndex(colunas[8]);
			int idxTamanho_Chave = c.getColumnIndex(colunas[9]);
			int idxFabricante_modelo = c.getColumnIndex(colunas[10]);
			do {
				list.add(new Ataque(c.getLong(idxId), c.getString(idxTipo), c.getString(idxComando), c.getString(idxOperadora), c.getInt(idxUsa_Login), c.getInt(idxUsa_Chave), 
						c.getInt(idxUsa_post_ou_get), c.getString(idxCaminho_Chave), c.getString(idxForma_Chave), c.getInt(idxTamanho_Chave), c.getString(idxFabricante_modelo)));
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
			int idxComando = c.getColumnIndex(colunas[2]);
			int idxOperadora = c.getColumnIndex(colunas[3]);
			int idxUsa_Login = c.getColumnIndex(colunas[4]);
			int idxUsa_Chave = c.getColumnIndex(colunas[5]);
			int idxUsa_post_ou_get = c.getColumnIndex(colunas[6]);
			int idxCaminho_Chave = c.getColumnIndex(colunas[7]);
			int idxForma_Chave = c.getColumnIndex(colunas[8]);
			int idxTamanho_Chave = c.getColumnIndex(colunas[9]);
			int idxFabricante_modelo = c.getColumnIndex(colunas[10]);
			do {
				list.add(new Ataque(c.getLong(idxId), c.getString(idxTipo), c.getString(idxComando), c.getString(idxOperadora), c.getInt(idxUsa_Login), c.getInt(idxUsa_Chave), 
						c.getInt(idxUsa_post_ou_get), c.getString(idxCaminho_Chave), c.getString(idxForma_Chave), c.getInt(idxTamanho_Chave), c.getString(idxFabricante_modelo)));
			} while(c.moveToNext());
		}
		c.close();
		return list;
	}
	
	public void close() {
		db.close();
	}
	
}

