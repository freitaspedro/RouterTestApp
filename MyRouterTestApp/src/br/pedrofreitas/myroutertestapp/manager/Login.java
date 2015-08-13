package br.pedrofreitas.myroutertestapp.manager;

public class Login {

	private long id;
	private String usuario;
	private String senha;
	private String operadora;
	
	public Login(long id, String usuario, String senha, String operadora) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.senha = senha;
		this.operadora = operadora;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getOperadora() {
		return operadora;
	}
	
	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}
	
	public String getLoginESenha(){
		return this.usuario+":"+this.senha;
	}
	
}
