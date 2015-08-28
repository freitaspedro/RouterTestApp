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
	private long reboot_ataque;
	private long dns_ataque;
	private long acesso_remoto_ataque;
	private String login;
	private String senha;
	
	
	public Dado(long id, String ip,String gateway, String operadora, String data, String fabricante_modelo,
			long reboot_ataque, long dns_ataque,
			long acesso_remoto_ataque, String login, String senha) {
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
	
	public long getReboot_ataque() {
		return reboot_ataque;
	}
	
	public void setReboot_ataque(long reboot_ataque) {
		this.reboot_ataque = reboot_ataque;
	}
	
	public long getDns_ataque() {
		return dns_ataque;
	}
	
	public void setDns_ataque(long dns_ataque) {
		this.dns_ataque = dns_ataque;
	}
	
	public long getAcesso_remoto_ataque() {
		return acesso_remoto_ataque;
	}
	
	public void setAcesso_remoto_ataque(long acesso_remoto_ataque) {
		this.acesso_remoto_ataque = acesso_remoto_ataque;
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
		return "Dado [id=" + id + ", ip=" + ip + ", operadora=" + operadora
				+ ", data=" + data + ", fabricante_modelo=" + fabricante_modelo
				+ ", reboot_ataque=" + reboot_ataque + ", dns_ataque=" + dns_ataque
				+ ", acesso_remoto_ataque=" + acesso_remoto_ataque + ", login="
				+ login + ", senha=" + senha + "]";
	}
	
}
