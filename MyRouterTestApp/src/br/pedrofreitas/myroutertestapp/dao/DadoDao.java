package br.pedrofreitas.myroutertestapp.dao;

import java.util.ArrayList;

import br.pedrofreitas.myroutertestapp.manager.Dado;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DadoDao {

	private SQLiteDatabase db;
	private static final String[] colunas = {"id", "ip", "gateway", "operadora", "data", "fabricante_modelo", 
		 "reboot_ataque", "dns_ataque", "acesso_remoto_ataque", "login", "senha"};
	
	public DadoDao(Context ctx) {
		db = ctx.openOrCreateDatabase("bancoProjetoFinal", Context.MODE_PRIVATE, null);
	}
	
	public void update(Dado dado) {
		ContentValues values = new ContentValues();
		
		values.put("id", dado.getId());
		values.put("ip", dado.getIp());
		values.put("gateway", dado.getGateway());
		values.put("operadora", dado.getOperadora());
		values.put("data", dado.getData());
		values.put("fabricante_modelo", dado.getFabricante_modelo());
		values.put("reboot_ataque", dado.getReboot_ataque());
		values.put("dns_ataque", dado.getDns_ataque());
		values.put("acesso_remoto_ataque", dado.getAcesso_remoto_ataque());
		values.put("login", dado.getLogin());
		values.put("senha", dado.getSenha());
		
		String id = String.valueOf(dado.getId());
		String where = colunas[0] + "=?";
		String[] whereArgs = new String[] {id};
		db.update("info", values, where, whereArgs);
	}
	
	public long insert(Dado dado) {
		ContentValues values = new ContentValues();
		
		//values.put("id", "");
		values.put("ip", dado.getIp());
		values.put("gateway", dado.getGateway());
		values.put("operadora", dado.getOperadora());
		values.put("data", dado.getData());
		values.put("fabricante_modelo", dado.getFabricante_modelo());
		values.put("reboot_ataque", dado.getReboot_ataque());
		values.put("dns_ataque", dado.getDns_ataque());
		values.put("acesso_remoto_ataque", dado.getAcesso_remoto_ataque());
		values.put("login", dado.getLogin());
		values.put("senha", dado.getSenha());
		
		return db.insert("info", "", values);
	}
	
	public Dado getDadoWithId(long id_dado) {
		Dado dado = null;
		Cursor c = db.query(true, "info", colunas, "id" + "=" + id_dado, null, null, null, null, null);
		
		if(c.getCount() > 0) {
			c.moveToFirst();
			int idxId = c.getColumnIndex(colunas[0]);
			int idxIp = c.getColumnIndex(colunas[1]);
			int idxGateway = c.getColumnIndex(colunas[2]);
			int idxOperadora = c.getColumnIndex(colunas[3]);
			int idxData = c.getColumnIndex(colunas[4]);
			int idxFabricante_modelo = c.getColumnIndex(colunas[5]);
			int idxReboot = c.getColumnIndex(colunas[6]);
			int idxDns = c.getColumnIndex(colunas[7]);
			int idxAcesso = c.getColumnIndex(colunas[8]);
			int idxLogin = c.getColumnIndex(colunas[9]);
			int idxSenha = c.getColumnIndex(colunas[10]);
			dado = new Dado(c.getLong(idxId), c.getString(idxIp), c.getString(idxGateway), c.getString(idxOperadora), c.getString(idxData), c.getString(idxFabricante_modelo), 
					c.getLong(idxReboot), c.getLong(idxDns), c.getLong(idxAcesso), c.getString(idxLogin), c.getString(idxSenha));
		}
		c.close();
		return dado;
	}
	
	public ArrayList<Dado> getAll() {
		ArrayList<Dado> list = new ArrayList<Dado>();
		Cursor c = db.query("info", colunas, null, null, null, null, null);
		if(c.moveToFirst()) {
			int idxId = c.getColumnIndex(colunas[0]);
			int idxIp = c.getColumnIndex(colunas[1]);
			int idxGateway = c.getColumnIndex(colunas[2]);
			int idxOperadora = c.getColumnIndex(colunas[3]);
			int idxData = c.getColumnIndex(colunas[4]);
			int idxFabricante_modelo = c.getColumnIndex(colunas[5]);
			int idxReboot = c.getColumnIndex(colunas[6]);
			int idxDns = c.getColumnIndex(colunas[7]);
			int idxAcesso = c.getColumnIndex(colunas[8]);
			int idxLogin = c.getColumnIndex(colunas[9]);
			int idxSenha = c.getColumnIndex(colunas[10]);
			do {
				list.add(new Dado(c.getLong(idxId), c.getString(idxIp), c.getString(idxGateway), c.getString(idxOperadora), c.getString(idxData), c.getString(idxFabricante_modelo), 
						c.getLong(idxReboot), c.getLong(idxDns), c.getLong(idxAcesso), c.getString(idxLogin), c.getString(idxSenha)));
			} while(c.moveToNext());
		}
		c.close();
		return list;
	}
	
	public void close(){
		db.close();
	}
	
}
