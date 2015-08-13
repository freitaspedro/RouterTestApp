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
import br.pedrofreitas.myroutertestapp.dao.PostGetDao;
import br.pedrofreitas.myroutertestapp.dao.UsuarioDao;
import br.pedrofreitas.myroutertestapp.manager.Ataque;
import br.pedrofreitas.myroutertestapp.manager.Login;
import br.pedrofreitas.myroutertestapp.manager.Params;
import br.pedrofreitas.myroutertestapp.manager.PostGet;

public class GetAtaque extends AsyncTask<Void, String, Void> {

	private Context mContext;

	private SharedPreferences mShared;
	private String TAG_PRIMEIRA_VEZ = "primeiraVez";
	private GetInfo asyncTask;

	private ProgressDialog mProgress;	

	private AtaqueDao mAtaqueDao;
	private PostGetDao mPostGetDao;
	private ParamsDao mParamsDao;
	private UsuarioDao mUsuarioDao;
		
	private JSONArray mVetJsonAtaque;
	private JSONObject mObjJsonAtaque;
	private JSONArray mVetJsonPostGet;
	private JSONObject mObjJsonPostGet;
	private JSONArray mVetJsonParams;
	private JSONObject mObjJsonParams;
	private JSONArray mVetJsonUsuario;
	private JSONObject mObjJsonUsuario;
	
	private String mJsonRecebidoAtaque;	
	private String mJsonRecebidoPostGet;	
	private String mJsonRecebidoParams;	
	private String mJsonRecebidoUsuario;
	
	private String urlJsonAtaque = "http://pedrofreitas.ddns.net/selecionaataque.php";	
	private String urlJsonPostGet = "http://pedrofreitas.ddns.net/selecionapostget.php";
	private String urlJsonParams = "http://pedrofreitas.ddns.net/selecionaparams.php";
	private String urlJsonUsuario = "http://pedrofreitas.ddns.net/selecionausuario.php";
	
	private boolean flag_erro_download_ataque = true;
	private boolean flag_erro_download_postget = true;	
	private boolean flag_erro_download_params = true;
	private boolean flag_erro_download_usuario = true;
	
	private final String[] colunasAtaque = {"id", "tipo", "comando", "operadora", "usa_login", "usa_chave_de_secao", "usa_post_ou_get", "caminho_get_chave", "forma_da_chave", "tamanho_da_chave"};
	private final String[] colunasPostGet = {"id", "id_ataque", "ordem", "tipo", "comando"};
	private final String[] colunasParams = {"id", "id_comando", "nome", "valor"};
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
		mJsonRecebidoAtaque = sh.makeServiceCall(urlJsonAtaque, ServiceHandler.GET);		
		if(mJsonRecebidoAtaque != null) {
			mAtaqueDao = new AtaqueDao(mContext);
			try {
				mObjJsonAtaque = new JSONObject(mJsonRecebidoAtaque);
				
				mVetJsonAtaque = mObjJsonAtaque.getJSONArray("ataques");
				
				for(int i = 0; i < mVetJsonAtaque.length(); i++) {
					mObjJsonAtaque = mVetJsonAtaque.getJSONObject(i);
					
					mAtaqueDao.insert(new Ataque(mObjJsonAtaque.getLong(colunasAtaque[0]), mObjJsonAtaque.getString(colunasAtaque[1]), mObjJsonAtaque.getString(colunasAtaque[2]), mObjJsonAtaque.getString(colunasAtaque[3]), mObjJsonAtaque.getInt(colunasAtaque[4]), mObjJsonAtaque.getInt(colunasAtaque[5]), mObjJsonAtaque.getInt(colunasAtaque[6]), mObjJsonAtaque.getString(colunasAtaque[7]), mObjJsonAtaque.getString(colunasAtaque[8]), mObjJsonAtaque.getInt(colunasAtaque[9])));					
				}
				flag_erro_download_ataque = false;
			}
			catch(JSONException e) {
				Log.e("ATAQUEJSON", "Erro no parsing do JSON ataque", e);
			}
		}
		else {
			flag_erro_download_ataque = true;
		}		
		
//		mAtaqueDao.getAll();
		
		if(!flag_erro_download_ataque){
			ServiceHandler sh1 = new ServiceHandler();		
			mJsonRecebidoPostGet = sh1.makeServiceCall(urlJsonPostGet, ServiceHandler.GET);	
			if(mJsonRecebidoPostGet != null) {
				mPostGetDao = new PostGetDao(mContext);
				try {
					mObjJsonPostGet =  new JSONObject(mJsonRecebidoPostGet);
					
					mVetJsonPostGet = mObjJsonPostGet.getJSONArray("postgets");
					
					for(int i = 0; i < mVetJsonPostGet.length(); i++) {
						mObjJsonPostGet = mVetJsonPostGet.getJSONObject(i);
						
						mPostGetDao.insert(new PostGet(mObjJsonPostGet.getLong(colunasPostGet[0]), mObjJsonPostGet.getLong(colunasPostGet[1]), mObjJsonPostGet.getInt(colunasPostGet[2]), mObjJsonPostGet.getString(colunasPostGet[3]), mObjJsonPostGet.getString(colunasPostGet[4])));						
					}
					flag_erro_download_postget = false;
				}
				catch(JSONException e) {
					Log.e("POSTGETJSON", "Erro no parsing do JSON postget", e);
				}
			}
			else {
				flag_erro_download_postget = true;
			}
			
		}
		
//		mPostGetDao.getPostEGetWithIdAtaque(1);
//		mPostGetDao.getPostEGetWithIdAtaque(2);
//		mPostGetDao.getPostEGetWithIdAtaque(3);
//		mPostGetDao.getPostEGetWithIdAtaque(4);
//		mPostGetDao.getPostEGetWithIdAtaque(5);
//		mPostGetDao.getPostEGetWithIdAtaque(6);
//		mPostGetDao.getPostEGetWithIdAtaque(7);
//		mPostGetDao.getPostEGetWithIdAtaque(8);
		
		if(!flag_erro_download_postget){
			ServiceHandler sh2 = new ServiceHandler();		
			mJsonRecebidoParams = sh2.makeServiceCall(urlJsonParams, ServiceHandler.GET);	
			if(mJsonRecebidoParams != null) {
				mParamsDao = new ParamsDao(mContext);
				try {
					mObjJsonParams = new JSONObject(mJsonRecebidoParams);
					
					mVetJsonParams = mObjJsonParams.getJSONArray("params");
					
					for(int i = 0; i < mVetJsonParams.length(); i++) {
						mObjJsonParams = mVetJsonParams.getJSONObject(i);
						
						mParamsDao.insert(new Params(mObjJsonParams.getLong(colunasParams[0]), mObjJsonParams.getLong(colunasParams[1]), mObjJsonParams.getString(colunasParams[2]), mObjJsonParams.getString(colunasParams[3])));						
					}
					flag_erro_download_params = false;
				}
				catch(JSONException e) {
					Log.e("PARAMSJSON", "Erro no parsing do JSON params", e);
				}
			}
			else {
				flag_erro_download_params = true;
			}
		}
		
//		mParamsDao.getParamsWithIdComando(1);
//		mParamsDao.getParamsWithIdComando(2);
//		mParamsDao.getParamsWithIdComando(3);
//		mParamsDao.getParamsWithIdComando(4);
//		mParamsDao.getParamsWithIdComando(5);
//		mParamsDao.getParamsWithIdComando(6);
//		mParamsDao.getParamsWithIdComando(7);
//		mParamsDao.getParamsWithIdComando(8);
//		mParamsDao.getParamsWithIdComando(9);
//		mParamsDao.getParamsWithIdComando(10);
//		mParamsDao.getParamsWithIdComando(11);
//		mParamsDao.getParamsWithIdComando(12);
//		mParamsDao.getParamsWithIdComando(13);
//		mParamsDao.getParamsWithIdComando(14);
//		mParamsDao.getParamsWithIdComando(15);
//		mParamsDao.getParamsWithIdComando(16);
//		mParamsDao.getParamsWithIdComando(17);
//		mParamsDao.getParamsWithIdComando(18);
//		mParamsDao.getParamsWithIdComando(19);	
		
		ServiceHandler sh3 = new ServiceHandler();		
		mJsonRecebidoUsuario = sh3.makeServiceCall(urlJsonUsuario, ServiceHandler.GET);		
		if(mJsonRecebidoUsuario != null) {
			mUsuarioDao = new UsuarioDao(mContext);
			try {
				mObjJsonUsuario = new JSONObject(mJsonRecebidoUsuario);
				
				mVetJsonUsuario = mObjJsonUsuario.getJSONArray("usuarios");
				
				for(int i = 0; i < mVetJsonUsuario.length(); i++) {
					mObjJsonUsuario = mVetJsonUsuario.getJSONObject(i);
					
					mUsuarioDao.save(new Login(mObjJsonUsuario.getLong(colunasLogin[0]), mObjJsonUsuario.getString(colunasLogin[1]), mObjJsonUsuario.getString(colunasLogin[2]), mObjJsonUsuario.getString(colunasLogin[3])));
					
					flag_erro_download_usuario = false;
				}
			}
			catch(JSONException e) {
				Log.e("USUARIOJSON", "Erro no parsing do JSON usuario", e);
			}
		}
		else {
			flag_erro_download_usuario = true;
		}
		
//		mUsuarioDao.getLoginPorOperadora("oi");
//		mUsuarioDao.getLoginPorOperadora("gvt");
//		mUsuarioDao.getLoginPorOperadora("net");	

		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if(!flag_erro_download_usuario && !flag_erro_download_params) {
			SharedPreferences.Editor editor = mShared.edit();
			editor.putBoolean(TAG_PRIMEIRA_VEZ, false);
			editor.commit();
			
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
