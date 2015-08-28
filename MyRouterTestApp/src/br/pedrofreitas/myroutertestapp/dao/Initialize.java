package br.pedrofreitas.myroutertestapp.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import br.pedrofreitas.myroutertestapp.manager.Ataque;
import br.pedrofreitas.myroutertestapp.manager.Login;
import br.pedrofreitas.myroutertestapp.manager.Params;
import br.pedrofreitas.myroutertestapp.manager.PostGet;

public class Initialize {
	
	private Context mContext;

	private SharedPreferences mShared;
	private String TAG_CREATE_TABLES = "isCreated";
	
	private GeneralDao mGeneralDao;
	private DadoDao mDadoDao;
	private AtaqueDao mAtaqueDao;
	private PostGetDao mPostGetDao;
	private ParamsDao mParamsDao;
	private UsuarioDao mUsuarioDao;
	
	
	public Initialize(Context mContext, SharedPreferences mShared) {
		this.mContext = mContext;
		this.mShared = mShared;
	}
	
	public void start() {
		mGeneralDao = new GeneralDao(mContext);
		mDadoDao = new DadoDao(mContext);
		mAtaqueDao = new AtaqueDao(mContext);
		mPostGetDao = new PostGetDao(mContext);
		mParamsDao = new ParamsDao(mContext);         
		mUsuarioDao = new UsuarioDao(mContext);		

		SharedPreferences.Editor editor = mShared.edit();
		editor.putBoolean(TAG_CREATE_TABLES, true);
		editor.commit();

		Log.i("CREATE_TABLES", "Tabelas criadas com sucesso");
		
		/*****PARA TESTE COMENTAR******/
		insertAll();
		selectAll();
		/******************************/
	}

	public void insertAll() {	
		mUsuarioDao.insert(new Login(1, "admin", "admin", "oi"));
		mUsuarioDao.insert(new Login(2, "", "", "net"));
		mUsuarioDao.insert(new Login(3, "admin", "", "oi"));
		mUsuarioDao.insert(new Login(4, "admin", "1234", "oi"));
		mUsuarioDao.insert(new Login(5, "admin", "gvt12345", "gvt"));
		mUsuarioDao.insert(new Login(6, "admin", "motorola", "net"));
		mUsuarioDao.insert(new Login(7, "router", "router", "net"));
		mUsuarioDao.insert(new Login(8, "cablecom", "router", "net"));
		mUsuarioDao.insert(new Login(9, "", "admin", "oi"));
		mUsuarioDao.insert(new Login(10, "", "Wireless", "oi"));
		mUsuarioDao.insert(new Login(11, "Admin", "Admin", "oi"));
		mUsuarioDao.insert(new Login(12, "Admin", "", "oi"));
		mUsuarioDao.insert(new Login(13, "", "Admin", "oi"));
		mUsuarioDao.insert(new Login(14, "Administrator", "Administrator", "oi"));
		mUsuarioDao.insert(new Login(15, "Administrator", "", "oi"));
		mUsuarioDao.insert(new Login(16, "", "Adminstrator", "oi"));
		mUsuarioDao.insert(new Login(17, "root", "root", "oi"));
		mUsuarioDao.insert(new Login(18, "root", "", "oi"));
		mUsuarioDao.insert(new Login(19, "", "root", "oi"));
		mUsuarioDao.insert(new Login(20, "admin", "password", "net"));
		mUsuarioDao.insert(new Login(21, "", "1234", "oi"));
	
		/*******************************************************Start Login*******************************************************/
//		GVT D-Link DSL-2640B	
		mAtaqueDao.insert(new Ataque(1, "login", null, "gvt", 1, 0, 0, null, null, 0, "D-Link DSL-2640B"));
			mPostGetDao.insert(new PostGet(1, 1, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(2, 1, 2, "get", "/logo.html", "logoBody", 1));
			mPostGetDao.insert(new PostGet(3, 1, 3, "get", "/menu.html", "mainMenuBody", 1));
			mPostGetDao.insert(new PostGet(4, 1, 4, "get", "/info.html", "DSL-2640B", 1));
			
//		Oi technicolor TD5136v2	
		mAtaqueDao.insert(new Ataque(2, "login", null, "oi", -1, 0, -1, null, null, 0, "technicolor TD5136v2"));
			mPostGetDao.insert(new PostGet(5, 2, 1, "post", "/login.cgi", "/index.cgi", 1));		
				mParamsDao.insert(new Params(1, 5, "isSubmit", "1"));
				mParamsDao.insert(new Params(2, 5, "username", "insereUsuario"));
				mParamsDao.insert(new Params(3, 5, "password", "insereSenha"));			
			mPostGetDao.insert(new PostGet(6, 2, 2, "get", "/index.cgi", "/login.cgi", 0));
			mPostGetDao.insert(new PostGet(7, 2, 3, "get", "/top.cgi", "Logout", 0));	
			mPostGetDao.insert(new PostGet(8, 2, 4, "get", "/code.cgi", "SETUP WIZARD", 0));	
			mPostGetDao.insert(new PostGet(9, 2, 5, "get", "/status_summary.cgi?pageAct=summary", "/login.cgi", 0));	
	
//		Oi ZyXEL AMG1202-T10B
		mAtaqueDao.insert(new Ataque(3, "login", null, "oi", -1, 0, -1, null, null, 0, "ZyXEL AMG1202-T10B"));			
			mPostGetDao.insert(new PostGet(10, 3, 1, "post", "/cgi-bin/SavingAuthorize.asp", "/MakeSession.asp", 1));												
				mParamsDao.insert(new Params(4, 10, "LoginPassword", "insereSenha"));
				mParamsDao.insert(new Params(5, 10, "LoginUsername", "insereUsuario"));
			mPostGetDao.insert(new PostGet(11, 3, 2, "get", "/MakeSession.asp", "", 0));	
			mPostGetDao.insert(new PostGet(12, 3, 3, "get", "/cgi-bin/Jump2Host.asp", "", 0));
			mPostGetDao.insert(new PostGet(13, 3, 4, "get", "/cgi-bin/rpSys.asp", "", 0));		
			mPostGetDao.insert(new PostGet(14, 3, 5, "get", "/cgi-bin/top.asp", "", 0));		
			mPostGetDao.insert(new PostGet(15, 3, 6, "get", "/cgi-bin/path.asp", "", 0));		
			mPostGetDao.insert(new PostGet(16, 3, 7, "get", "/cgi-bin/home.asp", "", 0));		
			mPostGetDao.insert(new PostGet(17, 3, 8, "get", "/cgi-bin/rpPanel.asp", "", 0));		
			mPostGetDao.insert(new PostGet(18, 3, 9, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(19, 3, 10, "get", "/cgi-bin/path.asp", "", 0));
			
//		NET Cisco DPC3925
		mAtaqueDao.insert(new Ataque(4, "login", null, "net", -1, 0, -1, null, null, 0, "Cisco DPC3925"));			
			mPostGetDao.insert(new PostGet(20, 4, 1, "post", "/goform/Docsis_system", "Setup", 1));														
				mParamsDao.insert(new Params(6, 20, "username_login", "insereUsuario"));
				mParamsDao.insert(new Params(7, 20, "password_login", "insereSenha"));
				mParamsDao.insert(new Params(8, 20, "LanguageSelect", "en"));
				mParamsDao.insert(new Params(9, 20, "Language_Submit", "0"));
				mParamsDao.insert(new Params(10, 20, "login", "Log In"));
			mPostGetDao.insert(new PostGet(21, 4, 2, "get", "/Quick_setup.asp", "Setup", 0));
			
		/********************************************************End Login********************************************************/
			
			
		/******************************************************Start Reboot*******************************************************/	
//		GVT D-Link DSL-2640B
		mAtaqueDao.insert(new Ataque(5, "reboot", null, "gvt", -1, 0, -1, null, null, 0, "D-Link DSL-2640B"));
			mPostGetDao.insert(new PostGet(22, 5, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(23, 5, 2, "get", "/logo.html", "logoBody", 1));
			mPostGetDao.insert(new PostGet(24, 5, 3, "get", "/menu.html", "mainMenuBody", 1));
			mPostGetDao.insert(new PostGet(25, 5, 4, "get", "/info.html", "DSL-2640B", 1));
			mPostGetDao.insert(new PostGet(26, 5, 5, "get", "/backupsettings.html", "", 1));
			mPostGetDao.insert(new PostGet(27, 5, 6, "get", "/resetrouter.html", "", 1));
			mPostGetDao.insert(new PostGet(28, 5, 7, "get", "/rebootinfo.cgi", "The DSL Router is rebooting", 1));
			

//		Oi technicolor TD5136v2	
		mAtaqueDao.insert(new Ataque(6, "reboot", null, "oi", -1, 0, -1, null, null, 0, "technicolor TD5136v2"));
			mPostGetDao.insert(new PostGet(29, 6, 1, "post", "/login.cgi", "/index.cgi", 1));		
				mParamsDao.insert(new Params(11, 29, "isSubmit", "1"));
				mParamsDao.insert(new Params(12, 29, "username", "insereUsuario"));
				mParamsDao.insert(new Params(13, 29, "password", "insereSenha"));			
			mPostGetDao.insert(new PostGet(30, 6, 2, "get", "/index.cgi", "/login.cgi", 0));
			mPostGetDao.insert(new PostGet(31, 6, 3, "get", "/top.cgi", "Logout", 0));	
			mPostGetDao.insert(new PostGet(32, 6, 4, "get", "/code.cgi", "SETUP WIZARD", 0));	
			mPostGetDao.insert(new PostGet(33, 6, 5, "get", "/status_summary.cgi?pageAct=summary", "/login.cgi", 0));	
			mPostGetDao.insert(new PostGet(34, 6, 6, "get", "/reboot.cgi", "", 0));	
			mPostGetDao.insert(new PostGet(35, 6, 7, "post", "/reboot.cgi", "", 0));
				mParamsDao.insert(new Params(14, 35, "submitValue", "redirect"));						
			mPostGetDao.insert(new PostGet(36, 6, 8, "post", "/reboot.cgi", "", 0));	
				mParamsDao.insert(new Params(15, 36, "submitValue", "apply"));	
		
			
		/*******************************************************End Reboot********************************************************/	
		
		
		/********************************************************Start DNS********************************************************/	
		/*********************************************************End DNS*********************************************************/

			
		/****************************************************Start Acesso Remoto**************************************************/	
		/*****************************************************End Acesso Remoto***************************************************/
				

		/******************************************************Start Tarefa 1*****************************************************/	
		/*******************************************************End Terefa 1******************************************************/
		
			
		/******************************************************Start Tarefa 2*****************************************************/	
		/*******************************************************End Terefa 2******************************************************/
			

		Log.i("INSERT_TABLES", "Tabelas preenchidas com sucesso");
		
	}
	
	public void selectAll() {
		mAtaqueDao.getAll();
		
		mPostGetDao.getAll();
		
		mParamsDao.getAll();
		
		mUsuarioDao.getLoginPorOperadora("oi");
		mUsuarioDao.getLoginPorOperadora("gvt");
		mUsuarioDao.getLoginPorOperadora("net");		
	}		
	
	
}
