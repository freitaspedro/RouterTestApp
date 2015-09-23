package br.pedrofreitas.myroutertestapp.manager;

import java.io.Serializable;

public class Dado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String ip;
	private String gateway;
	private String operadora;
	private String data;
	private String fabricante_modelo;
	private int reboot_ataque;
	private int dns_ataque;
	private int acesso_remoto_ataque;
	private int filtro_mac_ataque;
	private int abrir_rede_ataque;
	private String login;
	private String senha;
	
	
	public Dado(long id, String ip,String gateway, String operadora, String data, String fabricante_modelo,
			int reboot_ataque, int dns_ataque,
			int acesso_remoto_ataque, int filtro_mac_ataque, int abrir_rede_ataque, String login, String senha) {
		super();
		this.id = id;
		this.ip = ip;
		this.gateway = gateway;
		this.operadora = operadora;
		this.data = data;
		this.fabricante_modelo = fabricante_modelo;
		this.reboot_ataque = reboot_ataque;
		this.dns_ataque = dns_ataque;
		this.acesso_remoto_ataque = acesso_remoto_ataque;
		this.filtro_mac_ataque = filtro_mac_ataque;
		this.abrir_rede_ataque = abrir_rede_ataque;
		this.login = login;
		this.senha = senha;
	}
	
	public Dado(String ip, String operadora, String gateway, String data){
		this.ip = ip;
		this.gateway = gateway;
		this.operadora = operadora;
		this.data = data;
	}
	
	public Dado() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}	
	
	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getOperadora() {
		return operadora;
	} 
	
	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getFabricante_modelo() {
		return fabricante_modelo;
	}
	
	public void setFabricante_modelo(String fabricante_modelo) {
		this.fabricante_modelo = fabricante_modelo;
	}
	
	public int getReboot_ataque() {
		return reboot_ataque;
	}
	
	public void setReboot_ataque(int reboot_ataque) {
		this.reboot_ataque = reboot_ataque;
	}
	
	public int getDns_ataque() {
		return dns_ataque;
	}
	
	public void setDns_ataque(int dns_ataque) {
		this.dns_ataque = dns_ataque;
	}
	
	public int getAcesso_remoto_ataque() {
		return acesso_remoto_ataque;
	}
	
	public void setAcesso_remoto_ataque(int acesso_remoto_ataque) {
		this.acesso_remoto_ataque = acesso_remoto_ataque;
	}
	
	public int getFiltro_mac_ataque() {
		return filtro_mac_ataque;
	}
	
	public void setFiltro_mac_ataque(int filtro_mac_ataque) {
		this.filtro_mac_ataque = filtro_mac_ataque;
	}

	public int getAbrir_rede_ataque() {
		return abrir_rede_ataque;
	}
	
	public void setAbrir_rede_ataque(int abrir_rede_ataque) {
		this.abrir_rede_ataque = abrir_rede_ataque;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Dado [id=" + id +
					", ip=" + ip +
					", gateway=" + gateway +
					", operadora=" + operadora +
					", data=" + data +
					", fabricante_modelo=" + fabricante_modelo +
					", reboot_ataque=" + reboot_ataque +
					", dns_ataque=" + dns_ataque +
					", acesso_remoto_ataque=" + acesso_remoto_ataque +					
					", filtro_mac_ataque=" + filtro_mac_ataque +
					", abrir_rede_ataque=" + abrir_rede_ataque +
					", login=" + login +
					", senha=" + senha + "]";
	}
	
}
