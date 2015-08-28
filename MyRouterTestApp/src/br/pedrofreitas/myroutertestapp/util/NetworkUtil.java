package br.pedrofreitas.myroutertestapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	
   public static int TYPE_WIFI = 1;
   public static int TYPE_MOBILE = 2;
   public static int TYPE_NOT_CONNECTED = 0;

   public static int SPLASH_TIME_OUT = 2000;
   public static int SLEEP_TIME_OUT = 4250;
   public static CharSequence ALERT = "Need Wifi enabled";
   public static CharSequence ALERT_DIALOG = "Tentar uma nova conexão"; 
   public static CharSequence ALERT_DIALOG1 = "Não foi possível se conectar ao servidor"; 
    
   public static int getConnectivityStatus(Context context) {
       ConnectivityManager cm = (ConnectivityManager) context
               .getSystemService(Context.CONNECTIVITY_SERVICE);
 
       NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
       if (null != activeNetwork) {
           if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
               return TYPE_WIFI;
            
           if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
               return TYPE_MOBILE;
       } 
       return TYPE_NOT_CONNECTED;
   }
    
   public static String getConnectivityStatusString(Context context) {
       int conn = NetworkUtil.getConnectivityStatus(context);
       String status = null;
       if (conn == NetworkUtil.TYPE_WIFI) {
           status = "Wifi enabled";
       } 
       else {
    	   if (conn == NetworkUtil.TYPE_MOBILE) {
    		   status = "Mobile data enabled";
    	   } 
    	   else { 
    		   if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
    			   status = "Not connected to Internet";
    		   }
    	   }
       }
       return status;
   }
   
   public static boolean existInternet(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if(ni == null) {
			return false;
		}
		if(!ni.isConnected()) {
			return false;
		}
		if(!ni.isAvailable()) {
			return false;
		}
		else {
			return true;
		}
   }
   
   public static boolean isWiFiConnected(Context ctx) {		
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = null;
		if(cm != null) {	
			ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		}
		return ni == null ? false : ni.isConnected();
	}

}
