package br.pedrofreitas.myroutertestapp.manager;

public class PostGet {

	private long id;
	private long id_ataque;
	private int ordem;
	private String tipo;
	private String comando;
	private String token;
	private int usa_login;
	
	public PostGet(long id, long id_ataque, int ordem, String tipo,
			String comando, String token, int usa_login) {
		super();
		this.id = id;
		this.id_ataque = id_ataque;
		this.ordem = ordem;
		this.tipo = tipo;
		this.comando = comando;
		this.token = token;
		this.usa_login = usa_login;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId_ataque() {
		return id_ataque;
	}
	
	public void setId_ataque(long id_ataque) {
		this.id_ataque = id_ataque;
	}
	
	public int getOrdem() {
		return ordem;
	}
	
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getComando() {
		return comando;
	}
	
	public void setComando(String comando) {
		this.comando = comando;
	}	

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public int getUsa_login() {
		return usa_login;
	}
	
	public void setUsa_login(int usa_login) {
		this.usa_login = usa_login;
	}
	
	@Override
	public String toString() {
		return "PostGet [id=" + id + ", id_ataque=" + id_ataque + ", ordem="
				+ ordem + ", tipo=" + tipo + ", comando=" + comando + ", token=" + token + ", usa_login=" + usa_login + "]";
	}
	
}
