package br.pedrofreitas.myroutertestapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import br.pedrofreitas.myroutertestapp.dao.AtaqueDao;
import br.pedrofreitas.myroutertestapp.dao.DadoDao;
import br.pedrofreitas.myroutertestapp.dao.GeneralDao;
import br.pedrofreitas.myroutertestapp.dao.Initialize;
import br.pedrofreitas.myroutertestapp.dao.LoginDao;
import br.pedrofreitas.myroutertestapp.dao.ParamsDao;
import br.pedrofreitas.myroutertestapp.dao.ParamsProxDao;
import br.pedrofreitas.myroutertestapp.dao.PostGetDao;
import br.pedrofreitas.myroutertestapp.manager.Ataque;
import br.pedrofreitas.myroutertestapp.manager.Dado;
import br.pedrofreitas.myroutertestapp.manager.Login;
import br.pedrofreitas.myroutertestapp.util.AsyncTaskCompleteListener;
import br.pedrofreitas.myroutertestapp.util.GetAtaque;
import br.pedrofreitas.myroutertestapp.util.GetInfo;
import br.pedrofreitas.myroutertestapp.util.NamedObject;
import br.pedrofreitas.myroutertestapp.util.NetworkUtil;
import br.pedrofreitas.myroutertestapp.util.StartAtaque;
 

public class MainActivity<T> extends ActionBarActivity implements AsyncTaskCompleteListener<T> {	

	private Intent it;    	
	private WifiManager mWifiManager;
	
	private SharedPreferences mShared;
	private String TAG_APP = "app";
	private String TAG_CREATE_TABLES = "isCreated";
	private boolean mIsCreated;
	private String TAG_INSERT_TABLES = "isInserted";
	private boolean mIsInserted;

	private GeneralDao mGeneralDao;
	private DadoDao mDadoDao;
	private AtaqueDao mAtaqueDao;
	private PostGetDao mPostGetDao;
	private ParamsDao mParamsDao;
	private ParamsProxDao mParamsProxDao;
	private LoginDao mLoginDao;

	private Dado mDado;
	
	private ArrayList<Login> mListLogin;
	
	private TextView mIp;
	private TextView mGateway;
	private TextView mSSID;
	private TextView mOperadora;
	private TextView mData;
	
	private static final int ALERT_DIALOG = 1;
	private static final int ALERT_DIALOG_OPT = 2;
	
	private AsyncTask<Void, String, Void> getAtaqueTask; 	
	private AsyncTask<Void, String, Object> getInfoTask; 
	private AsyncTask<Void, String, Integer> doAtaqueTask; 	
	
	private ArrayList<String> mListAtaqueTipo;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mShared = getSharedPreferences(TAG_APP, MODE_PRIVATE);		
		mIsCreated = mShared.getBoolean(TAG_CREATE_TABLES, false);
		
		//Cria o banco e as tabelas na memoria
		if(!mIsCreated) {			
			new Initialize(MainActivity.this, mShared).start();			
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		setContentView(R.layout.activity_main);  		
		
		mShared = getSharedPreferences(TAG_APP, MODE_PRIVATE);		
		mIsInserted = mShared.getBoolean(TAG_INSERT_TABLES, false);

		/*****RETIRAR ISSO CASO O SERVIDOR ESTIVER NO AR*******/
		mIsInserted = true;
		/******************************************************/
		
		if(NetworkUtil.isWiFiConnected(this)) {
			mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);					
			getInfoTask = new GetInfo(MainActivity.this, mWifiManager, mListLogin);
			
			if(!mIsInserted) {
				//Preenche as tabelas da memoria com os dados do servidor
				getAtaqueTask = new GetAtaque(MainActivity.this, mShared, getInfoTask);
				getAtaqueTask.execute();				
			}
			else {
				getInfoTask.execute();	
			}
		}
		else {        	
			showDialog(ALERT_DIALOG);			
		}     
	}		
	
	@Override
	public void onTaskComplete(Object result) {		
		NamedObject obj = (NamedObject) result;
		
		mDado = (Dado) obj.getObject();		
		Log.i("DADO", mDado.toString());
		
		if(mDado != null) {
        	mIp  = (TextView) findViewById(R.id.textView1);
        	CharSequence textIp = (mDado.getIp() != null) ? "IP Externo: " + mDado.getIp() : "IP Externo: Não identificado";
        	mIp.setText(textIp);
        	
        	mGateway = (TextView) findViewById(R.id.textView2);
        	CharSequence textGateway = (mDado.getGateway() != null) ? "Gateway Padrão: " + mDado.getGateway() : "Gateway Padrão: Não identificado";
        	mGateway.setText(textGateway);
        	
        	mSSID = (TextView) findViewById(R.id.textView3);
        	CharSequence textMac = (mDado.getSsid() != null) ? "SSID: " + mDado.getSsid() : "SSID: Não identificado";
        	mSSID.setText(textMac);
        	
        	mOperadora = (TextView) findViewById(R.id.textView4);
        	CharSequence textOperadora = (mDado.getOperadora() != null) ? "Operadora: " + mDado.getOperadora().toUpperCase() : "Operadora: Não identificada";
        	mOperadora.setText(textOperadora);
        	        	
        	mData  = (TextView) findViewById(R.id.textView5);
        	CharSequence textData = (mDado.getData() != null) ? "Data Atual: " + mDado.getData() : "Data atual: Não identificada";
        	mData.setText(textData);
    	}		
		
		mListLogin = (ArrayList<Login>) obj.getList();
		Log.i("LISTA_LOGIN", "tamanho " + mListLogin.size());
	} 
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	    	case R.id.action_exit:
	        	finish();
	            return true;
	    	case R.id.action_about:
		    	Intent intent = new Intent(this, About.class);
		        this.startActivity(intent);
		        return true;
		   default:
			   return super.onOptionsItemSelected(item);			   
        }
    }
    
    public void startTest(View view) {   	
//		new AtaqueDao(this).getAll(); 		
    	
//		new PostGetDao(this).getAll();
    	
//		new ParamsDao(this).getAll();
    	
//		new LoginDao(this).getLoginPorOperadora("oi");
//		new LoginDao(this).getLoginPorOperadora("gvt");
//		new LoginDao(this).getLoginPorOperadora("net");
    	
    	mListAtaqueTipo = new ArrayList<>();
    	
    	if(NetworkUtil.isWiFiConnected(this)) {			
    		showDialog(ALERT_DIALOG_OPT);   	
    	}
    	else {        	
			showDialog(ALERT_DIALOG);			
    	}     	
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog dialog;
    	AlertDialog.Builder builder;
    	switch(id) {
    	case ALERT_DIALOG:
    		builder = new AlertDialog.Builder(this);
    		builder.setMessage(NetworkUtil.ALERT_DIALOG)
		    		.setCancelable(false)
		    		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		    			@Override
		    			public void onClick(DialogInterface dialog, int id) {
		    				startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		    			}
		    		});
    		dialog = builder.create();
    		break;
    	case ALERT_DIALOG_OPT:
    		builder = new AlertDialog.Builder(this);
    		final CharSequence[] opts = {"Reboot", "DNS", "Acesso Remoto", "Filtro MAC", "Abrir Rede"};		//exibido
    		final String[] ataques = {"reboot", "dns", "acesso", "filtromac", "abrirrede"};		//usado para selecionar os ataques no repositorio
    		boolean[] checkedItems = {true, true, true, true, true};
    		mListAtaqueTipo.add(ataques[0]);	
    		mListAtaqueTipo.add(ataques[1]);	
    		mListAtaqueTipo.add(ataques[2]);	
    		mListAtaqueTipo.add(ataques[3]);	
    		mListAtaqueTipo.add(ataques[4]);	
    		builder.setTitle(NetworkUtil.ALERT_DIALOG_OPT)
    				.setMultiChoiceItems(opts, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {						
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							if(isChecked) {
								mListAtaqueTipo.add(ataques[which]);								
							} else {
								if(which == 0) {
									((AlertDialog) dialog).getListView().setItemChecked(which, true);		//reboot obrigatorio
								} else {
									mListAtaqueTipo.remove(ataques[which]);									
								}
							}							
						}
					})
					.setCancelable(false)
		    		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		    			@Override
		    			public void onClick(DialogInterface dialog, int id) {
		    				Collections.sort(mListAtaqueTipo, new Comparator<String>() {
		    					 	@Override
		    						public int compare(String s1, String s2) {		    					 		
		    					 		int index1 = -1;
		    					 		int index2 = -1;
		    					 		for(int i = 0; i < ataques.length; i++) {		    					 			
		    					 			String aux = ataques[i];
		    					 			if(aux.equals(s1)) {
		    					 				index1 = i;
		    					 			}
		    					 			if(aux.equals(s2)) {
		    					 				index2 = i;
		    					 			}
		    					 		}
		    					 		if(index1 < index2) {
		    					 			return -1;		    					 			
		    					 		} else {
		    					 			return 1;
		    					 		}
		    						}
							});
		    				
		    				Log.i("LISTA_ATAQUE", "tamanho " + mListAtaqueTipo.size());
		    				
		    				it = new Intent(MainActivity.this, Result.class); 
		    				
		    				mAtaqueDao = new AtaqueDao(MainActivity.this);
		    				ArrayList<Ataque> mListAtaque = new ArrayList<>();
		    				mListAtaque.addAll(mAtaqueDao.getAtaquesWithOperadoraETipo(mDado.getOperadora(), "login"));
		    				
		    				mListAtaque.addAll(mAtaqueDao.getAtaquesWithNOTOperadoraETipo(mDado.getOperadora(), "login"));
		    				
		    				
		    				Log.i("LISTA_ATAQUE_LOGIN", "tamanho " + mListAtaque.size());			
		    				int mCountAtaques = 0;
		    				
		    		    	doAtaqueTask = new StartAtaque(MainActivity.this, mDado, mListLogin, mListAtaque, mCountAtaques, mListAtaqueTipo, it);
		    		    	doAtaqueTask.execute();	 
		    			}
		    		});   		
    		dialog = builder.create();
    		break;  	            
    	default:
    		dialog = null;
    	}
    	return dialog; 
    }

    
    @Override
    protected void onStop(){
    	super.onStop();
    	
    	if(mLoginDao != null)
    		mLoginDao.close();
    	if(mParamsProxDao != null)
    		mParamsProxDao.close();    	
    	if(mParamsDao != null)
    		mParamsDao.close();
    	if(mPostGetDao != null)
    		mPostGetDao.close();
    	if(mAtaqueDao != null)
    		mAtaqueDao.close();
    	if(mDadoDao != null)
    		mDadoDao.close();
    	if(mGeneralDao != null)
    		mGeneralDao.close();
    	
//    	finish();
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	if(mLoginDao != null)
    		mLoginDao.close();
    	if(mParamsProxDao != null)
    		mParamsProxDao.close();
    	if(mParamsDao != null)
    		mParamsDao.close();
    	if(mPostGetDao != null)
    		mPostGetDao.close();
    	if(mAtaqueDao != null)
    		mAtaqueDao.close();
    	if(mDadoDao != null)
    		mDadoDao.close();
    	if(mGeneralDao != null)
    		mGeneralDao.close();
    	
//    	finish();
    }
}
