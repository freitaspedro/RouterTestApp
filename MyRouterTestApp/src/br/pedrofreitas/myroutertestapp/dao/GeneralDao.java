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
							   "operadora" + " TEXT, " +
							   "data" + " TEXT, " +
							   "fabricante_modelo" + " TEXT, " +
							   "reboot_ataque" + " INTEGER, " +
							   "dns_ataque" + " INTEGER, " +
							   "acesso_remoto_ataque" + " INTEGER, " +
							   "login" + " TEXT, " +
							   "senha" + " INTEGER" + ");",
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
	    "CREATE TABLE ataque (" + "id" + " INTEGER PRIMARY KEY, " +
								  "tipo" + " TEXT, " +
				                  "comando" + " TEXT, " + 
				                  "operadora" + " TEXT, " +
				                  "usa_login" + " INTEGER, " +
				                  "usa_chave_de_sessao" + " INTEGER, " +
				                  "usa_post_ou_get" + " INTEGER, " +
				                  "caminho_get_chave" + " TEXT, " +
				                  "forma_da_chave" + " TEXT, " +
				                  "tamanho_da_chave" + " INTEGER, " + 
				                  "fabricante_modelo" + " TEXT " + ");",
        "CREATE TABLE usuario (" + "id" + " INTEGER PRIMARY KEY, " +
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
