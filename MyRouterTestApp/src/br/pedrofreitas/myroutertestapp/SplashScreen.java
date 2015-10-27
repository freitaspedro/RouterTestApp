package br.pedrofreitas.myroutertestapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;
import br.pedrofreitas.myroutertestapp.util.NetworkUtil;
 
public class SplashScreen extends Activity {
 
    private Intent it;   
   	    
    @Override  
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);        

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    	
        it = new Intent(SplashScreen.this, MainActivity.class);        
        	
    	new Handler().postDelayed(new Runnable() {
	
    		@Override
			public void run() {
				if(NetworkUtil.isWiFiConnected(SplashScreen.this)) {					
					startActivity(it);
					finish();									
				}
				else {	
			    	String status = NetworkUtil.getConnectivityStatusString(SplashScreen.this);
                	Toast.makeText(SplashScreen.this, status, Toast.LENGTH_LONG).show();              	

                	Toast.makeText(SplashScreen.this, NetworkUtil.ALERT, Toast.LENGTH_LONG).show();
                	
                	new Handler().postDelayed(new Runnable() {
                		@Override
            			public void run() {
                			startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                			finish();
                		}
                	}, NetworkUtil.SLEEP_TIME_OUT);
                	
            	}
			}
    		
		}, NetworkUtil.SPLASH_TIME_OUT);
       
    }

}
