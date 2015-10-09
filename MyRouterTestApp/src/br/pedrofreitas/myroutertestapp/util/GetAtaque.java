package br.pedrofreitas.myroutertestapp.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import br.pedrofreitas.myroutertestapp.dao.AtaqueDao;
import br.pedrofreitas.myroutertestapp.dao.ParamsDao;
import br.pedrofreitas.myroutertestapp.dao.ParamsProxDao;
import br.pedrofreitas.myroutertestapp.dao.PostGetDao;
import br.pedrofreitas.myroutertestapp.dao.LoginDao;
import br.pedrofreitas.myroutertestapp.manager.Ataque;
import br.pedrofreitas.myroutertestapp.manager.Login;
import br.pedrofreitas.myroutertestapp.manager.Params;
import br.pedrofreitas.myroutertestapp.manager.PostGet;

public class GetAtaque extends AsyncTask<Void, String, Void> {

	private Context mContext;

	private SharedPreferences mShared;
	private String TAG_INSERT_TABLES = "isInserted";
	private GetInfo asyncTask;

	private ProgressDialog mProgress;
	
	private String urlJsonAtaque = "http://pedrofreitas.ddns.net/selecionaataque.php";	
	private String urlJsonPostGet = "http://pedrofreitas.ddns.net/selecionapostget.php";
	private String urlJsonParams = "http://pedrofreitas.ddns.net/selecionaparams.php";
	private String urlJsonParamsProx = "http://pedrofreitas.ddns.net/selecionaparamsprox.php";
	private String urlJsonLogin = "http://pedrofreitas.ddns.net/selecionalogin.php";
	
	private boolean flag_erro_download_ataque = true;
	private boolean flag_erro_download_postget = true;	
	private boolean flag_erro_download_params = true;
	private boolean flag_erro_download_paramsprox = true;
	private boolean flag_erro_download_login = true;
	
	private final String[] colunasAtaque = {"id", "tipo", "operadora", "usa_cookie", "usa_chave_sessao", "formato_chave_sessao", 
																								"usa_so_get", "fabricante_modelo"};
	private final String[] colunasPostGet = {"id", "id_ataque", "ordem", "tipo", "comando", "token", "usa_login"};
	private final String[] colunasParams = {"id", "id_comando", "nome", "valor"};
	private final String[] colunasParamsProx = {"id", "id_comando", "nome", "valor"};
	private final String[] colunasLogin = {"id", "usuario", "senha", "operadora"};
	
		
	public GetAtaque(Context mContext, SharedPreferences mShared, AsyncTask asyncTask) {
		this.mContext = mContext;
		this.mShared = mShared;
		this.asyncTask = (GetInfo) asyncTask;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgress = new ProgressDialog(this.mContext);
		mProgress.setMessage("Sincronizando dados com o servidor...");
		mProgress.setIndeterminate(false);
		mProgress.setMax(100);
		mProgress.setCancelable(false);
		mProgress.show();
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
		mProgress.setMessage(values[0]);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
//		android.os.Debug.waitForDebugger();	
		ServiceHandler sh = new ServiceHandler();	
		String mJsonRecebidoAtaque = sh.makeServiceCall(urlJsonAtaque, ServiceHandler.GET);		
		if(mJsonRecebidoAtaque != null) {
			AtaqueDao mAtaqueDao = new AtaqueDao(mContext);
			try {
				JSONObject mObjJsonAtaque = new JSONObject(mJsonRecebidoAtaque);
				
				JSONArray mVetJsonAtaque = mObjJsonAtaque.getJSONArray("ataques");
				
				for(int i = 0; i < mVetJsonAtaque.length(); i++) {
					mObjJsonAtaque = mVetJsonAtaque.getJSONObject(i);
					
					mAtaqueDao.insert(new Ataque(mObjJsonAtaque.getLong(colunasAtaque[0]), mObjJsonAtaque.getString(colunasAtaque[1]), 
							mObjJsonAtaque.getString(colunasAtaque[2]), mObjJsonAtaque.getInt(colunasAtaque[3]), mObjJsonAtaque.getInt(colunasAtaque[4]), 
							mObjJsonAtaque.getString(colunasAtaque[5]), mObjJsonAtaque.getInt(colunasAtaque[6]), mObjJsonAtaque.getString(colunasAtaque[7])));					
				}
				flag_erro_download_ataque = false;
			} catch(JSONException e) {
				Log.e("ATAQUEJSON", "Erro no parsing do JSON ataque", e);
			}
		} else {
			flag_erro_download_ataque = true;
		}		
		
//		new AtaqueDao(mContext).getAll();
		
		if(!flag_erro_download_ataque) {
			ServiceHandler sh1 = new ServiceHandler();		
			String mJsonRecebidoPostGet = sh1.makeServiceCall(urlJsonPostGet, ServiceHandler.GET);	
			if(mJsonRecebidoPostGet != null) {
				PostGetDao mPostGetDao = new PostGetDao(mContext);
				try {
					JSONObject mObjJsonPostGet =  new JSONObject(mJsonRecebidoPostGet);
					
					JSONArray mVetJsonPostGet = mObjJsonPostGet.getJSONArray("postgets");
					
					for(int i = 0; i < mVetJsonPostGet.length(); i++) {
						mObjJsonPostGet = mVetJsonPostGet.getJSONObject(i);
						
						mPostGetDao.insert(new PostGet(mObjJsonPostGet.getLong(colunasPostGet[0]), mObjJsonPostGet.getLong(colunasPostGet[1]), 
								mObjJsonPostGet.getInt(colunasPostGet[2]), mObjJsonPostGet.getString(colunasPostGet[3]), mObjJsonPostGet.getString(colunasPostGet[4]), 
								mObjJsonPostGet.getString(colunasPostGet[5]), mObjJsonPostGet.getInt(colunasPostGet[6])));						
					}
					flag_erro_download_postget = false;
				} catch(JSONException e) {
					Log.e("POSTGETJSON", "Erro no parsing do JSON postget", e);
				}
			} else {
				flag_erro_download_postget = true;
			}
			
		}
		
//		new PostGetDao(mContext).getAll();
		
		if(!flag_erro_download_postget) {
			ServiceHandler sh2 = new ServiceHandler();		
			String mJsonRecebidoParams = sh2.makeServiceCall(urlJsonParams, ServiceHandler.GET);	
			if(mJsonRecebidoParams != null) {
				ParamsDao mParamsDao = new ParamsDao(mContext);
				try {
					JSONObject mObjJsonParams = new JSONObject(mJsonRecebidoParams);
					
					JSONArray mVetJsonParams = mObjJsonParams.getJSONArray("params");
					
					for(int i = 0; i < mVetJsonParams.length(); i++) {
						mObjJsonParams = mVetJsonParams.getJSONObject(i);
						
						mParamsDao.insert(new Params(mObjJsonParams.getLong(colunasParams[0]), mObjJsonParams.getLong(colunasParams[1]), 
								mObjJsonParams.getString(colunasParams[2]), mObjJsonParams.getString(colunasParams[3])));						
					}
					flag_erro_download_params = false;
				} catch(JSONException e) {
					Log.e("PARAMSJSON", "Erro no parsing do JSON params", e);
				}
			} else {
				flag_erro_download_params = true;
			}
		}
		
//		new ParamsDao(mContext).getAll();
		
		if(!flag_erro_download_params) {
			ServiceHandler sh3 = new ServiceHandler();		
			String mJsonRecebidoParamsProx = sh3.makeServiceCall(urlJsonParamsProx, ServiceHandler.GET);	
			if(mJsonRecebidoParamsProx != null) {
				ParamsProxDao mParamsProxDao = new ParamsProxDao(mContext);
				try {
					JSONObject mObjJsonParamsProx = new JSONObject(mJsonRecebidoParamsProx);
					
					JSONArray mVetJsonParamsProx = mObjJsonParamsProx.getJSONArray("paramsprox");
					
					for(int i = 0; i < mVetJsonParamsProx.length(); i++) {
						mObjJsonParamsProx = mVetJsonParamsProx.getJSONObject(i);
						
						mParamsProxDao.insert(new Params(mObjJsonParamsProx.getLong(colunasParamsProx[0]), mObjJsonParamsProx.getLong(colunasParamsProx[1]), 
								mObjJsonParamsProx.getString(colunasParamsProx[2]), mObjJsonParamsProx.getString(colunasParamsProx[3])));						
					}
					flag_erro_download_paramsprox = false;
				} catch(JSONException e) {
					Log.e("PARAMSPROXJSON", "Erro no parsing do JSON paramsprox", e);
				}
			} else {
				flag_erro_download_paramsprox = true;
			}
		}
		
//		new ParamsProxDao(mContext).getAll();		
		
		ServiceHandler sh4 = new ServiceHandler();		
		String mJsonRecebidoLogin = sh4.makeServiceCall(urlJsonLogin, ServiceHandler.GET);		
		if(mJsonRecebidoLogin != null) {
			LoginDao mLoginDao = new LoginDao(mContext);
			try {
				 JSONObject mObjJsonLogin = new JSONObject(mJsonRecebidoLogin);
				
				JSONArray mVetJsonLogin = mObjJsonLogin.getJSONArray("logins");
				
				for(int i = 0; i < mVetJsonLogin.length(); i++) {
					mObjJsonLogin = mVetJsonLogin.getJSONObject(i);
					
					mLoginDao.save(new Login(mObjJsonLogin.getLong(colunasLogin[0]), mObjJsonLogin.getString(colunasLogin[1]), 
							mObjJsonLogin.getString(colunasLogin[2]), mObjJsonLogin.getString(colunasLogin[3])));
					
					flag_erro_download_login = false;
				}
			} catch(JSONException e) {
				Log.e("USUARIOJSON", "Erro no parsing do JSON login", e);
			}
		} else {
			flag_erro_download_login = true;
		}
		
//		new LoginDao(mContext).getLoginPorOperadora("oi");
//		new LoginDao(mContext).getLoginPorOperadora("gvt");
//		new LoginDao(mContext).getLoginPorOperadora("net");	

		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if(!flag_erro_download_login && !flag_erro_download_paramsprox) {
			SharedPreferences.Editor editor = mShared.edit();
			editor.putBoolean(TAG_INSERT_TABLES, true);
			editor.commit();

			Log.i("INSERT_TABLES", "Tabelas preenchidas com sucesso");
			
			mProgress.setMessage("Concluído");
			mProgress.dismiss();	
			asyncTask.execute();
		} else {
			new AlertDialog.Builder(mContext)
		    .setMessage(NetworkUtil.ALERT_DIALOG1)
		    .setCancelable(false)
		    .setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int which) { 
			    	new GetAtaque(mContext, mShared, asyncTask).execute();	
			    }
			 })
		    .show();
		}
	}
}
