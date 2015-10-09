package br.pedrofreitas.myroutertestapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class GeneralDao {

	//Script para realizar o Drop na tabela
	private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS materiais";
	
	//Classe que utilizamos para abrir, criar e atualizar o banco
	SQLiteHelper dbHelper;
	
	protected SQLiteDatabase db;
	
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
			"CREATE TABLE info ("+ "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
								   "ip"+ " TEXT, " +
								   "gateway"+ " TEXT, " +
								   "mac" + " TEXT, " +
								   "ssid" + " TEXT, " +
								   "operadora" + " TEXT, " +
								   "data" + " TEXT, " +
								   "fabricante_modelo" + " TEXT, " +
								   "usuario" + " TEXT, " +
								   "senha" + " TEXT, " +
								   "reboot_ataque" + " INTEGER, " +
								   "dns_ataque" + " INTEGER, " +
								   "acesso_remoto_ataque" + " INTEGER, " +
								   "filtro_mac_ataque" + " INTEGER, " +
								   "abrir_rede_ataque" + " INTEGER" + ");",
			"CREATE TABLE postget (" + "id" + " INTEGER PRIMARY KEY, " +
										"id_ataque" + " INTEGER, " +
										"ordem" + " INTEGER, " +
										"tipo" + " TEXT, " +
										"comando" + " TEXT, " + 
										"token" + " TEXT, " + 
										"usa_login" + " INTEGER" + ");",
			"CREATE TABLE params (" + "id" + " INTEGER PRIMARY KEY, " +
									  "id_comando" + " INTEGER, " +
									  "nome" + " TEXT, " +
									  "valor" + " TEXT" + ");",
			"CREATE TABLE paramsprox (" + "id" + " INTEGER PRIMARY KEY, " +
										  "id_comando" + " INTEGER, " +
										  "nome" + " TEXT, " +
										  "valor" + " TEXT" + ");",
			"CREATE TABLE ataque (" + "id" + " INTEGER PRIMARY KEY, " +
									  "tipo" + " TEXT, " +
									  "operadora" + " TEXT, " + 
									  "usa_cookie" + " INTEGER, " +
									  "usa_chave_sessao" + " INTEGER, " +
									  "formato_chave_sessao" + " TEXT, " +
									  "usa_so_get" + " INTEGER, " +
									  "fabricante_modelo" + " TEXT" + ");",
			"CREATE TABLE login (" + "id" + " INTEGER PRIMARY KEY, " +
										  "usuario" + " TEXT, " +
										  "senha" + " TEXT, " +
										  "operadora" + " TEXT" + ");"};
	
	
	public GeneralDao(Context ctx){
		dbHelper = new SQLiteHelper(ctx, "bancoProjetoFinal", null, 1, GeneralDao.SCRIPT_DATABASE_CREATE, GeneralDao.SCRIPT_DATABASE_DELETE);
		db = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		if(db != null)
			db.close();
	}

}
