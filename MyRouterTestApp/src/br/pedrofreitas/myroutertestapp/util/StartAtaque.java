package br.pedrofreitas.myroutertestapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import br.pedrofreitas.myroutertestapp.dao.AtaqueDao;
import br.pedrofreitas.myroutertestapp.dao.DadoDao;
import br.pedrofreitas.myroutertestapp.dao.ParamsDao;
import br.pedrofreitas.myroutertestapp.dao.PostGetDao;
import br.pedrofreitas.myroutertestapp.manager.Ataque;
import br.pedrofreitas.myroutertestapp.manager.Dado;
import br.pedrofreitas.myroutertestapp.manager.Login;
import br.pedrofreitas.myroutertestapp.manager.Params;
import br.pedrofreitas.myroutertestapp.manager.PostGet;

public class StartAtaque extends AsyncTask<Void, String, Integer> {

	private Intent it;
	private Context mContext;
	private ProgressDialog mProgress;
	
	private Dado mDado;

	private ArrayList<Login> mListLogin;
	private ArrayList<Ataque> mListAtaque;

	private boolean mConectou;
	
	private int mCountAtaques; 
	
	
	public StartAtaque(Context mContext, Dado mDado, ArrayList<Login> mListLogin, ArrayList<Ataque> mListAtaque, int mCountAtaques, Intent it) {
		this.mContext = mContext;
		this.mDado = mDado;
		this.mListLogin = mListLogin;
		this.mListAtaque = mListAtaque;
		this.mCountAtaques = mCountAtaques;
		this.mConectou = false;
		this.it = it;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgress = new ProgressDialog(this.mContext);
		mProgress.setMessage("Testando...");
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
	protected Integer doInBackground(Void... params) {
		android.os.Debug.waitForDebugger();	
		
		Ataque auxAtaque = mListAtaque.get(mCountAtaques);		

		PostGetDao mPostGetDao = new PostGetDao(mContext);
		List<PostGet> mListPostGet = mPostGetDao.getPostEGetWithIdAtaque(auxAtaque.getId());
		
		Log.i("LISTA_POSTGET_" + mCountAtaques, "tamanho " + mListPostGet.size());
		
		int mCountUsuarios = 0;
		int mCountPostGet = 0;
		
		while(mCountUsuarios < mListLogin.size()) {
			
			if(mCountPostGet >= mListPostGet.size()) {
				mConectou = true;
				break;
			}
			
			Log.i("LOGIN_" + mCountAtaques + "_" + mCountUsuarios, mListLogin.get(mCountUsuarios).getLoginESenha());
			
			mCountPostGet = 0;
			boolean erro = false;

			while(mCountPostGet < mListPostGet.size() && !erro) {	
				
				Iterator iterator = mListPostGet.iterator();
				PostGet auxPostGet = null;
				while(iterator.hasNext()) {
					auxPostGet = (PostGet) iterator.next();
					if(auxPostGet.getOrdem() > mCountPostGet) break;
				}
				
				Log.i("POSTGET_" + mCountAtaques + "_" + mCountUsuarios + "_" + mCountPostGet, auxPostGet.toString());
				
				if(auxPostGet.getTipo().contains("get")) {	
					
					try {
						URL url = new URL ("http://" + mDado.getGateway() + auxPostGet.getComando()); 
						Log.i("URL_LOGIN_" + mCountAtaques + "_" + mCountUsuarios, "http://" + mDado.getGateway() + auxPostGet.getComando());
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();	
						//connection.setRequestMethod("GET");
						//connection.setDoOutput(true);				
						
						if(auxPostGet.getUsa_login() == 1) {
							byte[] encodedBytes = Base64.encodeBase64(mListLogin.get(mCountUsuarios).getLoginESenha().getBytes());
							String encoding = new String(encodedBytes);
							Log.i("ENCODING_LOGIN_" + mCountAtaques + "_" + mCountUsuarios, encoding + "(" + mListLogin.get(mCountUsuarios).getLoginESenha() + ")");
							connection.setRequestProperty("Authorization", "Basic " + encoding);
						}
						
						Log.i("RESPONSE_CODE_" + mCountAtaques + "_" + mCountUsuarios, connection.getResponseCode() +":" + connection.getResponseMessage());
						
						erro = true;
						
						InputStream content = (InputStream) connection.getInputStream();
						BufferedReader in = new BufferedReader (new InputStreamReader(content));				
						String line;							
						
						while ((line = in.readLine()) != null) {
							Log.i("CONEXAO_LOGIN_" + mCountAtaques + "_" + mCountUsuarios, line);
							if(line.contains(auxPostGet.getToken())) {
								erro = false;
								mCountPostGet = auxPostGet.getOrdem();
							}
						}
						in.close();
						content.close();
														
						if(erro) {
							mCountUsuarios++;
						}
					
					} catch (MalformedURLException e) {
						Log.e("ERR_LOGIN_" + mCountAtaques + "_" + mCountUsuarios, "MalformedURLException", e);
						erro = true;
						mCountUsuarios++;
					} catch (IOException e) {
						Log.e("ERR_LOGIN_" + mCountAtaques + "_" + mCountUsuarios, "IOException", e);
						erro = true;
						mCountUsuarios++;
					}
				} else {
					if(auxPostGet.getTipo().contains("post")) {	
						
						HttpClient httpClient = new DefaultHttpClient();
						HttpPost httpPost = new HttpPost("http://" + mDado.getGateway() + auxPostGet.getComando());
				
						Log.i("URL_LOGIN_" + mCountAtaques + "_" + mCountUsuarios, "http://" + mDado.getGateway() + auxPostGet.getComando());
						
						ParamsDao mParamsDao = new ParamsDao(mContext);
						List<Params> mListParms = mParamsDao.getParamsWithIdComando(auxPostGet.getId());
						List<NameValuePair>	auxParams;					
						
						if(mListParms.size() > 0) {
							auxParams = new ArrayList<NameValuePair>(mListParms.size());
							for(int i = 0; i < mListParms.size(); i++) {
								String name = mListParms.get(i).getNome();
								String value = mListParms.get(i).getValor();
								
								if(auxPostGet.getUsa_login() == 1) {
									//O parametro e o usuario do login
									if(mListParms.get(i).getValor().contains("insereUsuario")) {
										value = mListLogin.get(mCountUsuarios).getUsuario();
									}
									
									//O parametro e a senha do login
									if(mListParms.get(i).getValor().contains("insereSenha")) {
										value = mListLogin.get(mCountUsuarios).getSenha();
									}
								}
								
								auxParams.add(new BasicNameValuePair(name, value));
								Log.i("PARAM_NAME_" + mCountAtaques + "_" + mCountUsuarios + "_" + i , name);
								Log.i("PARAM_VALUE_" + mCountAtaques + "_" + mCountUsuarios + "_" + i , value);
							}
							try {
								httpPost.setEntity(new UrlEncodedFormEntity(auxParams, "UTF-8"));
							} catch(UnsupportedEncodingException e) {
								Log.e("LOGIN_" + mCountAtaques + "_" + mCountUsuarios , "UnsupportedEncodingException", e);
							}
						}							
						
						HttpResponse httpResponse;
						try {
							httpResponse = httpClient.execute(httpPost);
							
							Log.i("RESPONSE_STATUS_" + mCountAtaques + "_" + mCountUsuarios, httpResponse.getStatusLine() + "");
							
							HttpEntity responseEntity = httpResponse.getEntity();
							
							erro = true;
							
							InputStream content = (InputStream)responseEntity.getContent();
					        BufferedReader in = new BufferedReader (new InputStreamReader (content));
					        String line;
					        
					        while((line = in.readLine()) != null) {
					        	Log.i("CONEXAO_LOGIN_" + mCountAtaques + "_" + mCountUsuarios, line);
					        	if(line.contains(auxPostGet.getToken())) {
									erro = false;
									mCountPostGet = auxPostGet.getOrdem();
								}
					        }
					        in.close();
					        content.close();

					        if(erro) {
								mCountUsuarios++;
							}
							
						} catch (ClientProtocolException e) {
							Log.e("ERR_LOGIN_" + mCountAtaques + "_" + mCountUsuarios, "ClientProtocolException", e);
							erro = true;
							mCountUsuarios++;
						} catch (IOException e) {
							Log.e("ERR_LOGIN_" + mCountAtaques + "_" + mCountUsuarios, "IOException", e);
							erro = true;
							mCountUsuarios++;
						}
						
					} else {
						Log.e("ERR_LOGIN_" + mCountAtaques, "Comando HTTP nao identificado");
						mConectou = false;
						break;						
					}
				}				
			}
		}
		
		return mCountUsuarios;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		int mCountUsuarios = (int) result;		

		DadoDao mDadoDao = new DadoDao(mContext);
		Log.i("CONECTOU", mConectou + "");
		long mIdDadoAtaque;
		
		int mCountTipoAtaques = 0;
		
		if(mConectou) {
			mDado.setLogin(mListLogin.get(mCountUsuarios).getUsuario());
			mDado.setSenha(mListLogin.get(mCountUsuarios).getSenha());

			String fabricanteModelo = mListAtaque.get(mCountAtaques).getFabricante_modelo();
			mDado.setFabricante_modelo(fabricanteModelo);
			
			Log.i("DADO", mDado.toString());
			
			mIdDadoAtaque = mDadoDao.insert(mDado);			
//			mDadoDao.getAll();			
			
			AtaqueDao mAtaqueDao = new AtaqueDao(mContext);		
			ArrayList<Ataque> mListAtaque = mAtaqueDao.getAtaquesWithOperadoraETipoEFabricanteModelo(mDado.getOperadora(), "reboot", fabricanteModelo);
			
			Log.i("LISTA_REBOOT", "tamanho " + mListAtaque.size());
			
			new FinishAtaque(mContext, mIdDadoAtaque, mListAtaque, mCountTipoAtaques, it).execute();	
		} else {
			//A lista de ataques terminou e nao foi possivel logar 
			if(mCountAtaques == mListAtaque.size()) {
				mDado.setLogin("nao identificado");
				mDado.setSenha("nao identificada");
				mDadoDao.update(mDado);	
				mIdDadoAtaque = mDadoDao.insert(mDado);		
				
//				mDadoDao.getAll();
				
				Log.i("DADO", mDado.toString());	
				
				mListAtaque = null;
				mCountAtaques = 0;
				new FinishAtaque(mContext, mIdDadoAtaque, mListAtaque, mCountTipoAtaques, it).execute();	
			} else {
				//Nao foi possivel logar, tenta outro ataque
				if(mCountAtaques < mListAtaque.size()) {
//					mDadoDao.getAll();
					
					mCountAtaques++;
					new StartAtaque(mContext, mDado, mListLogin, mListAtaque, mCountAtaques, it).execute();
				}
			}
		}			
	}
	
}
