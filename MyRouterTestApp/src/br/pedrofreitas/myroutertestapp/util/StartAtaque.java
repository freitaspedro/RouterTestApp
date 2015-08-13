package br.pedrofreitas.myroutertestapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.Connection;
import java.util.ArrayList;
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
import android.content.SharedPreferences;
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

public class StartAtaque extends AsyncTask<Void, String, Void> {

	private Context mContext;
	private Intent it;

	private SharedPreferences mShared;
	private String TAG_ATAQUE_TEMPORARIO_ID = "id_temporario";

	private Dado mDado;
	
	private ArrayList<Login> mListLogin;
	private ArrayList<Ataque> mListAtaque;
	private ArrayList<PostGet> mListPostGet;
	private ArrayList<Params> mListParams;
	
	private DadoDao mDadoDao;
	private AtaqueDao mAtaqueDao;
	private PostGetDao mPostGetDao;
	private ParamsDao mParamsDao;
	
	private ProgressDialog mProgress;
	
	private boolean mConectou;
	private boolean encontrou;
	
	private int mCountUsuarios;
	private int mCountAtaques;
	private int mCountPostGet;
	
	private long mIdDado;
	private String mChaveEncontrada;
	private String mUrlAtaque;
	private int ataqueSucesso;	

		
	
	public StartAtaque(Context mContext, Dado mDado, ArrayList<Login> mListLogin, Intent it, SharedPreferences mShared) {
		this.mContext = mContext;
		this.mDado = mDado;
		this.mListLogin = mListLogin;
		this.it = it;
		this.mShared = mShared;
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
	protected Void doInBackground(Void... params) {
		android.os.Debug.waitForDebugger();	

		//Get conexao
		try {
			while(!mConectou) {				
				byte[] encodedBytes = Base64.encodeBase64(mListLogin.get(mCountUsuarios).getLoginESenha().getBytes());
				String encoding = new String(encodedBytes);
				Log.i("ENCODING", encoding);
				
				URL url = new URL ("http://" + mDado.getGateway()); 
				URLConnection connection = (URLConnection) url.openConnection();	
				connection.setRequestProperty("Authorization", "Basic " + encoding);				
				InputStream content = (InputStream) connection.getInputStream();
				InputStreamReader in = new InputStreamReader(content);
				
				int numCharsRead;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();	
				
				while ((numCharsRead = in.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				
				String conexao = sb.toString();
				Log.i("CONEXAO", conexao);
				
				in.close();
				content.close();
				mConectou = true;
			}
		} catch (MalformedURLException e) {
			Log.i("ENCODING","MalformedURLException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.i("ENCODING","IOException");
			e.printStackTrace();
		} 
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		mDadoDao = new DadoDao(mContext);
		
		if(mCountUsuarios == mListLogin.size() && !mConectou) {
			mDado.setLogin("");
			mDado.setSenha("");
			mDadoDao.update(mDado);	
			
			SharedPreferences.Editor editor = mShared.edit();
			editor.putLong(TAG_ATAQUE_TEMPORARIO_ID, mDado.getId());
			editor.commit();
		}
		
		if(!mConectou) {
			mCountUsuarios++;
			new StartAtaque(mContext, mDado, mListLogin, it, mShared).execute();
		}
		else {
			mDado.setLogin(mListLogin.get(mCountUsuarios).getUsuario());
			mDado.setSenha(mListLogin.get(mCountUsuarios).getSenha());
			
			Log.i("DADO", mDado.toString());
			Log.i("CONECTOU","conectou: " + mConectou);
			
			mIdDado = mDadoDao.insert(mDado);
			
			mAtaqueDao = new AtaqueDao(mContext);
			
			mListAtaque = mAtaqueDao.getAtaquesWithOperadoraETipo(mDado.getOperadora(), "reboot"); 				
			
			mCountAtaques = 0;
			new DoAtaque().execute();
		}			
		
	}
	
		private class DoAtaque extends AsyncTask<Void, String, Void> {
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}
			
			@Override
			protected void onProgressUpdate(String... values) {
				mProgress.setMessage(values[0]);
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				android.os.Debug.waitForDebugger();	
				
				Ataque aux = mListAtaque.get(mCountAtaques);
				Log.i("ATAQUE", "Comando[" + mCountAtaques + "] = " + aux.getComando());
				
				if(aux.getUsa_chave_de_secao() == 1) {
					URL urlGetChave;
					try {
						urlGetChave = new URL ("http://" + mDado.getGateway() + aux.getCaminho_get_chave());
						Log.i("ATAQUE", "http://" + mDado.getGateway() + aux.getCaminho_get_chave());
						byte[] encodedBytes = Base64.encodeBase64((mDado.getLogin() + ":" + mDado.getSenha()).getBytes());
						String encoding = new String(encodedBytes);
						
						URLConnection connection = urlGetChave.openConnection();
						connection.setRequestProperty("Authorization", "Basic " + encoding);
						
						InputStream content = connection.getInputStream();
						BufferedReader in = new BufferedReader (new InputStreamReader(content));
						String line;
						
						while ((line = in.readLine()) != null && !encontrou) 
						{
							Log.i("ATAQUE", line);
							if(line.contains(mListAtaque.get(mCountAtaques).getForma_da_chave())){
								String forma = mListAtaque.get(mCountAtaques).getForma_da_chave();
								Log.i("INTERNET", line);
								mChaveEncontrada = line.substring(line.indexOf(forma) + forma.length(), line.indexOf(forma) + forma.length() + mListAtaque.get(mCountAtaques).getTamanho_da_chave());
								Log.i("internet", mChaveEncontrada);
								encontrou = true;
								mUrlAtaque = "http://" + mDado.getGateway() + aux.getComando() + mChaveEncontrada;
							}
						}					
						
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if(aux.getUsa_post_ou_get() == 0) {
					if(mChaveEncontrada == null) {
						mUrlAtaque = "http://" + mDado.getGateway() + aux.getComando();
					}
					URL urlExecutaAtaque;
					try {
						byte[] encodedBytes = Base64.encodeBase64((mDado.getLogin() + ":" + mDado.getSenha()).getBytes());
						String encoding = new String(encodedBytes);
					
						mChaveEncontrada = null;
						urlExecutaAtaque = new URL (URLDecoder.decode(mUrlAtaque));
						
						HttpURLConnection connection = (HttpURLConnection) urlExecutaAtaque.openConnection();
						connection.setRequestMethod("GET");
						connection.setRequestProperty("Authorization", "Basic " + encoding);
						Log.i("ATAQUE", "Codigo de retorno " + connection.getResponseCode());
						InputStream content = connection.getInputStream();
						
						BufferedReader in = new BufferedReader (new InputStreamReader(content));
						String line;
	
						ataqueSucesso = mCountAtaques;
						mCountAtaques++;
						while ((line = in.readLine()) != null) 
						{
							Log.i("RESPOSTA_ATAQUE", line);
						}					
						
					} catch (MalformedURLException e) {
						Log.i("CONEXAO","MalformedURLException");
						mCountAtaques++;						
					} catch (IOException e) {
						Log.i("CONEXAO","IOException");
						mCountAtaques++;
					}			
				}
				else {
					mPostGetDao = new PostGetDao(mContext);
					
					mListPostGet = mPostGetDao.getPostEGetWithIdAtaque(aux.getId());
					HttpClient httpClient = new DefaultHttpClient();
					
					while(mListPostGet.size() != mCountPostGet) {
						PostGet auxPG = mListPostGet.get(mCountPostGet);				
						
						HttpPost httpPost = new HttpPost("http://"+mDado.getGateway()+auxPG.getComando());
						Log.i("PARAMS", "http://" + mDado.getGateway() + auxPG.getComando());
						mParamsDao = new ParamsDao(mContext);
						
						mListParams = mParamsDao.getParamsWithIdComando(auxPG.getId());
						
						if(mListParams.size() > 0) {
							List<NameValuePair> parms = new ArrayList<NameValuePair>(mListParams.size());
							for(int k = 0; k <= mListParams.size() - 1; k++){
								parms.add(new BasicNameValuePair(mListParams.get(k).getNome(), mListParams.get(k).getValor()));
								Log.i("PARAMS", "nome = [" + mListParams.get(k).getNome() + "]; valor = [" + mListParams.get(k).getValor() +"]");
							}
							
							try {
								httpPost.setEntity(new UrlEncodedFormEntity(parms, "UTF-8"));
							} catch (UnsupportedEncodingException e1) {
								e1.printStackTrace();
							}
						}
						
						HttpResponse httpResponse;
						try {					
							httpResponse = httpClient.execute(httpPost);
							HttpEntity responseEntity = httpResponse.getEntity();
							
							InputStream content = responseEntity.getContent();
							BufferedReader in = new BufferedReader (new InputStreamReader (content));
							String line;
							mCountPostGet++;
							ataqueSucesso = mCountAtaques;
							
							while ((line = in.readLine()) != null) {
								System.out.println(line);
							}
							
						} catch (ClientProtocolException e) {
							e.printStackTrace();
							mCountPostGet++;
						} catch (IOException e) {
							e.printStackTrace();
							mCountPostGet++;
						}					
					}			
					mCountAtaques++;	
				}			
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				if(mCountAtaques < mListAtaque.size()){
					mCountPostGet = 0;
					new DoAtaque().execute();
				}
				if(mCountAtaques == mListAtaque.size()){
					Log.i("STATUS","Encerrado");
				}
				
				mProgress.setMessage("Concluído");
				mProgress.dismiss();
//		    	startActivity(it);
			}
		}

}
