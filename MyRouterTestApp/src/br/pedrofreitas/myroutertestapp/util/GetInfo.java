package br.pedrofreitas.myroutertestapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import br.pedrofreitas.myroutertestapp.dao.LoginDao;
import br.pedrofreitas.myroutertestapp.manager.Dado;
import br.pedrofreitas.myroutertestapp.manager.Login;

public class GetInfo extends AsyncTask<Void, String, Object> {
	
	private WifiManager mWifiManager;
	
	private Context mContext;	
	private AsyncTaskCompleteListener<Object> mCallback;
	private ArrayList<Login> mListLogin;
	
	private ProgressDialog mProgress;
	
	private String mGateway;
	private String mMac;
	private String mSsid;
	private String mData;	
	private String mIp;		
	private String mOperadora;

	private static final String TAG_GVT = "gvt";
	private static final String TAG_OI_VELOX = "oi";
	private static final String TAG_NET = "net";
	
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	
	public GetInfo(Context mContext, WifiManager mWifiManager, ArrayList<Login> mListLogin) {
		this.mContext = mContext;
		this.mCallback = (AsyncTaskCompleteListener<Object>) mContext;
		this.mWifiManager = mWifiManager;
		this.mListLogin = mListLogin;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgress = new ProgressDialog(this.mContext);
		mProgress.setMessage("Carregando...");
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
	protected Object doInBackground(Void... params) {
//		new AtaqueDao(mContext).getAll(); 
		
//		new PostGetDao(mContext).getAll();
		
//		new ParamsDao(mContext).getAll();
		
//		new LoginDao(mContext).getLoginPorOperadora("oi");
//		new LoginDao(mContext).getLoginPorOperadora("gvt");
//		new LoginDao(mContext).getLoginPorOperadora("net");
		
//		android.os.Debug.waitForDebugger();	
		//Get gateway
		DhcpInfo mDhcpInfo = new DhcpInfo();
		try {
			mDhcpInfo = mWifiManager.getDhcpInfo();
			
			mGateway = intToIp(mDhcpInfo.gateway);				
			Log.i("GATEWAY", mGateway);
			
			mListLogin = new ArrayList<Login>();			
			
		} catch (Exception e) {
			Log.e("ERR_GATEWAY", "Exception", e);
		}		
		
		//Get mac
		WifiInfo info = mWifiManager.getConnectionInfo();
		mMac = info.getBSSID();
		
		Log.i("MAC", mMac);
		
		//Get ssid
		mSsid = info.getSSID();
		Log.i("SSID", mSsid);
		
		
		//Get data		
		mData = getDataAtual();				
		Log.i("DATA_ATUAL", mData);

		
		//Get ip	
		URL whatismyip = null;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com");
		} catch (MalformedURLException e) {
			Log.e("ERR_IP", "MalformedURLException", e);
		}
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			String ip = in.readLine();
			if(ip.matches(IPADDRESS_PATTERN)) {
				mIp = ip;				
				Log.i("IP", mIp);				
			} else {
				Log.e("IP", "Nao identificado");
			}
		} catch (IOException e) {
			Log.e("ERR1_IP", "IOException", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.e("ERR2_IP", "IOException", e);
				}
			}			
		}

		
		//Get operadora				
		URL url = null;
		try {
			url = new URL("http://lacnic.net/cgi-bin/lacnic/whois?lg=PT&query=" + mIp);
		} catch (MalformedURLException e) {
			Log.e("ERR_OPERADORA", "MalformedURLException", e);
		}
		
		InputStream content = null;
		BufferedReader inn = null;
		try {
			URLConnection connection = url.openConnection();
			
			content = connection.getInputStream();
			inn = new BufferedReader(new InputStreamReader(content));
			String line;
			
			while ((line = inn.readLine()) != null) {
				if(line.contains("Global Village Telecom")) {
					mOperadora = TAG_GVT;
				}
				
				if (line.contains("Telemar")) {
					mOperadora = TAG_OI_VELOX;
				}
					
				if (line.contains("NET Servi�os de Comunica��o S.A.") || line.contains("CLARO S.A.")) {
					mOperadora = TAG_NET;
				}					
			}			
			if(mOperadora != null) {
				Log.i("OPERADORA", mOperadora);
			} else {
				Log.e("OPERADORA", "Nao identificada");
			}
			
		} catch (MalformedURLException e) {
			Log.e("ERR_OPERADORA", "MalformedURLException", e);
		} catch (IOException e) {
			Log.e("ERR1_OPERADORA", "IOException", e);
		} finally {
			if (inn != null) {
				try {
					inn.close();
				} catch (IOException e) {
					Log.e("ERR2_OPERADORA", "IOException", e);
				}
			}
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
					Log.e("ERR3_OPERADORA", "IOException", e);
				}
			}
		}		
		
		lotaListaLogins();		
		Dado mDado = new Dado(mIp, mGateway, mMac, mSsid, mOperadora, mData);
						
		return new NamedObject<Login, Dado>(mListLogin, mDado);
	}
	
	@Override
	protected void onPostExecute(Object result) {
		mProgress.setMessage("Concluído");
		mProgress.dismiss();
		if (result != null)		
			mCallback.onTaskComplete(result);
	}

	private String intToIp(int addr) {
		return  ((addr & 0xFF) + "." + 
				((addr >>>= 8) & 0xFF) + "." + 
				((addr >>>= 8) & 0xFF) + "." + 
				((addr >>>= 8) & 0xFF));
	}
	
	private String getDataAtual() {
		Calendar mCalendar = Calendar.getInstance();
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return mSimpleDateFormat.format(mCalendar.getTime());
	}	

	private void lotaListaLogins() {
		if (mOperadora != null)	{
			switch (mOperadora) {
				case TAG_GVT:
					mListLogin.addAll(new LoginDao(mContext).getLoginPorOperadora(TAG_GVT));
					break;
				case TAG_OI_VELOX:
					mListLogin.addAll(new LoginDao(mContext).getLoginPorOperadora(TAG_OI_VELOX));
					break;
				case TAG_NET:
					mListLogin.addAll(new LoginDao(mContext).getLoginPorOperadora(TAG_NET));
					break;
			}
		}
		mListLogin.addAll(new LoginDao(mContext).getLoginPorNaoOperadora(mOperadora));
	}	

}
