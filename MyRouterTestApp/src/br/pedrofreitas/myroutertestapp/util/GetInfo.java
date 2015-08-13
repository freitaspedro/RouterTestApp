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
import android.content.SharedPreferences;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import br.pedrofreitas.myroutertestapp.dao.AtaqueDao;
import br.pedrofreitas.myroutertestapp.dao.ParamsDao;
import br.pedrofreitas.myroutertestapp.dao.PostGetDao;
import br.pedrofreitas.myroutertestapp.dao.UsuarioDao;
import br.pedrofreitas.myroutertestapp.manager.Dado;
import br.pedrofreitas.myroutertestapp.manager.Login;

public class GetInfo extends AsyncTask<Void, String, Object> {
	
	private WifiManager mWifiManager;
	
	private Context mContext;	
	private AsyncTaskCompleteListener<Object> mCallback;
	private ArrayList<Login> mListLogin;
	
	private ProgressDialog mProgress;
	
	private DhcpInfo mDhcpInfo;
	private String mGateway;
	private String mData;

	private Calendar mCalendar;
	private SimpleDateFormat mSimpleDateFormat;
	
	private String mIp;		

	private static final String TAG_GVT = "gvt";
	private static final String TAG_OI_VELOX = "oi";
	private static final String TAG_NET = "net";
	
	private String mOperadora;
	

	
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
		
//		new PostGetDao(mContext).getPostEGetWithIdAtaque(1);
//		new PostGetDao(mContext).getPostEGetWithIdAtaque(2);
//		new PostGetDao(mContext).getPostEGetWithIdAtaque(3);
//		new PostGetDao(mContext).getPostEGetWithIdAtaque(4);
//		new PostGetDao(mContext).getPostEGetWithIdAtaque(5);
//		new PostGetDao(mContext).getPostEGetWithIdAtaque(6);
//		new PostGetDao(mContext).getPostEGetWithIdAtaque(7);
//		new PostGetDao(mContext).getPostEGetWithIdAtaque(8);
//		
//		new ParamsDao(mContext).getParamsWithIdComando(1);
//		new ParamsDao(mContext).getParamsWithIdComando(2);
//		new ParamsDao(mContext).getParamsWithIdComando(3);
//		new ParamsDao(mContext).getParamsWithIdComando(4);
//		new ParamsDao(mContext).getParamsWithIdComando(5);
//		new ParamsDao(mContext).getParamsWithIdComando(6);
//		new ParamsDao(mContext).getParamsWithIdComando(7);
//		new ParamsDao(mContext).getParamsWithIdComando(8);
//		new ParamsDao(mContext).getParamsWithIdComando(9);
//		new ParamsDao(mContext).getParamsWithIdComando(10);
//		new ParamsDao(mContext).getParamsWithIdComando(11);
//		new ParamsDao(mContext).getParamsWithIdComando(12);
//		new ParamsDao(mContext).getParamsWithIdComando(13);
//		new ParamsDao(mContext).getParamsWithIdComando(14);
//		new ParamsDao(mContext).getParamsWithIdComando(15);
//		new ParamsDao(mContext).getParamsWithIdComando(16);
//		new ParamsDao(mContext).getParamsWithIdComando(17);
//		new ParamsDao(mContext).getParamsWithIdComando(18);
//		new ParamsDao(mContext).getParamsWithIdComando(19);
//		
//		new UsuarioDao(mContext).getLoginPorOperadora("oi");
//		new UsuarioDao(mContext).getLoginPorOperadora("gvt");
//		new UsuarioDao(mContext).getLoginPorOperadora("net");
		
//		android.os.Debug.waitForDebugger();	
		//Get gateway		
		
		try {
			mDhcpInfo = mWifiManager.getDhcpInfo();
			
			mGateway = intToIp(mDhcpInfo.gateway);				
			Log.i("GATEWAY", mGateway);
			
			mListLogin = new ArrayList<Login>();			
			
			mData = getDataAtual();				
			Log.i("DATA_ATUAL", mData);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		//Get ip
		
		URL whatismyip = null;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					whatismyip.openStream()));
			String ip = in.readLine();
			mIp = ip;
			Log.i("IP", mIp);
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
		}
		
		//Get operadora
		
		URL url;
		try {
			url = new URL ("http://lacnic.net/cgi-bin/lacnic/whois?lg=PT&query=" + mIp);
			URLConnection connection = url.openConnection();
			
			InputStream content = connection.getInputStream();
			BufferedReader inn = new BufferedReader (new InputStreamReader (content));
			String line;
			
			while ((line = inn.readLine()) != null) {
				if(line.contains("Global Village Telecom"))
					mOperadora = TAG_GVT;
				else if (line.contains("Telemar")) {
					mOperadora = TAG_OI_VELOX;
				}
				else if (line.contains("NET Servi�os de Comunica��o S.A.")) {
					mOperadora = TAG_NET;
				}
			}
			
			Log.i("OPERADORA", mOperadora);
			
		} catch (MalformedURLException e4) {
			e4.printStackTrace();
		} catch (IOException e5) {
			e5.printStackTrace();
		}		
		
		lotaListaUsuarios();		
		Dado mDado = new Dado(mIp, mOperadora, mGateway, mData);
						
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
		mCalendar = Calendar.getInstance();
		mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return mSimpleDateFormat.format(mCalendar.getTime());
	}	

	private void lotaListaUsuarios() {
		switch (mOperadora) {
			case TAG_GVT:
				mListLogin.addAll(new UsuarioDao(mContext).getLoginPorOperadora(TAG_GVT));
				break;
			case TAG_OI_VELOX:
				mListLogin.addAll(new UsuarioDao(mContext).getLoginPorOperadora(TAG_OI_VELOX));
				break;
			case TAG_NET:
				mListLogin.addAll(new UsuarioDao(mContext).getLoginPorOperadora(TAG_NET));
				break;
		}
		mListLogin.addAll(new UsuarioDao(mContext).getLoginPorNaoOperadora(mOperadora));
	}	

}
