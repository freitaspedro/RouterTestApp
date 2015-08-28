package br.pedrofreitas.myroutertestapp;

import java.util.ArrayList;

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
import android.widget.TextView;
import br.pedrofreitas.myroutertestapp.dao.AtaqueDao;
import br.pedrofreitas.myroutertestapp.dao.DadoDao;
import br.pedrofreitas.myroutertestapp.dao.GeneralDao;
import br.pedrofreitas.myroutertestapp.dao.Initialize;
import br.pedrofreitas.myroutertestapp.dao.ParamsDao;
import br.pedrofreitas.myroutertestapp.dao.PostGetDao;
import br.pedrofreitas.myroutertestapp.dao.UsuarioDao;
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
	private UsuarioDao mUsuarioDao;

	private Dado mDado;
	
	private ArrayList<Login> mListLogin;
	
	private TextView mIp;
	private TextView mGateway;
	private TextView mOperadora;
	private TextView mData;
	
	private static final int ALERT_DIALOG1 = 1;
	
	private AsyncTask<Void, String, Void> getAtaqueTask; 	
	private AsyncTask<Void, String, Object> getInfoTask; 
	private AsyncTask<Void, String, Integer> doAtaqueTask; 	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        

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
		

		/*****PARA TESTE RETIRAR*******/
		mIsInserted = true;
		/******************************/
		
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
			showDialog(ALERT_DIALOG1);			
		}
        
	}	
	
	@Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        AlertDialog.Builder builder;
        switch(id) {
	        case ALERT_DIALOG1:
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
            default:
	            dialog = null;
        }
        return dialog; 
    }
	
	@Override
	public void onTaskComplete(Object result) {		
		NamedObject obj = (NamedObject) result;
		
		mDado = (Dado) obj.getObject();		
		Log.i("DADO", mDado.toString());
		
		if(mDado != null) {
        	mIp  = (TextView) findViewById(R.id.textView1);
        	CharSequence textIp = (mDado.getIp() != null) ? "IP: " + mDado.getIp() : "IP: Não identificado";
        	mIp.setText(textIp);
        	
        	mGateway = (TextView) findViewById(R.id.textView2);
        	CharSequence textGateway = (mDado.getGateway() != null) ? "Gateway: " + mDado.getGateway() : "Gateway: Não identificado";
        	mGateway.setText(textGateway);
        	
        	mOperadora = (TextView) findViewById(R.id.textView3);
        	CharSequence textOperadora = (mDado.getOperadora() != null) ? "Operadora: " + mDado.getOperadora().toUpperCase() : "Operadora: Não identificada";
        	mOperadora.setText(textOperadora);
        	        	
        	mData  = (TextView) findViewById(R.id.textView4);
        	CharSequence textData = (mDado.getData() != null) ? "Data atual: " + mDado.getData() : "Data atual: Não identificada";
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
    	
//		new UsuarioDao(this).getLoginPorOperadora("oi");
//		new UsuarioDao(this).getLoginPorOperadora("gvt");
//		new UsuarioDao(this).getLoginPorOperadora("net");
    	
    	it = new Intent(MainActivity.this, Result.class); 
    	
		if(NetworkUtil.isWiFiConnected(this)) {			
			mAtaqueDao = new AtaqueDao(MainActivity.this);
			ArrayList<Ataque> mListAtaque = mAtaqueDao.getAtaquesWithOperadoraETipo(mDado.getOperadora(), "login"); 
			
			Log.i("LISTA_ATAQUE_LOGIN", "tamanho " + mListAtaque.size());			
			int mCountAtaques = 0;
			
	    	doAtaqueTask = new StartAtaque(MainActivity.this, mDado, mListLogin, mListAtaque, mCountAtaques, it);
	    	doAtaqueTask.execute();	     
			
//			startActivity(it);
    	}
    	else {        	
			showDialog(ALERT_DIALOG1);			
    	}  
    	
    }
    
    @Override
    protected void onStop(){
    	super.onStop();
    	
    	if(mUsuarioDao != null)
    		mUsuarioDao.close();
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
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	if(mUsuarioDao != null)
    		mUsuarioDao.close();
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
    }
}
