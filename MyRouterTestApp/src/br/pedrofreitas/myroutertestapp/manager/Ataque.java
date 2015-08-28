package br.pedrofreitas.myroutertestapp.manager;

public class Ataque {
	
	private long id;
	private String tipo;
	private String comando;
	private String operadora;
	private int usa_login;
	private int usa_chave_de_sessao;
	private int usa_post_ou_get;
	private String caminho_get_chave;
	private String forma_da_chave;
	private int tamanho_da_chave;
	private String fabricante_modelo;
	
	public Ataque(long id, String tipo, String comando, String operadora,
			int usa_login, int usa_chave_de_sessao, int usa_post_ou_get, String caminho_get_chave,
			String forma_da_chave, int tamanho_da_chave, String fabricante_modelo) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.comando = comando;
		this.operadora = operadora;
		this.usa_login = usa_login;
		this.usa_chave_de_sessao = usa_chave_de_sessao;
		this.usa_post_ou_get = usa_post_ou_get;
		this.caminho_get_chave = caminho_get_chave;
		this.forma_da_chave = forma_da_chave;
		this.tamanho_da_chave = tamanho_da_chave;
		this.fabricante_modelo = fabricante_modelo;
	}
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public String getOperadora() {
		return operadora;
	}
	
	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}
	
	public int getUsa_login() {
		return usa_login;
	}
	
	public void setUsa_login(int usa_login) {
		this.usa_login = usa_login;
	}
	
	public int getUsa_chave_de_sessao() {
		return usa_chave_de_sessao;
	}
	
	public void setUsa_chave_de_sessao(int usa_chave_de_sessao) {
		this.usa_chave_de_sessao = usa_chave_de_sessao;
	}
	
	public String getCaminho_get_chave() {
		return caminho_get_chave;
	}
	
	public void setCaminho_get_chave(String caminho_get_chave) {
		this.caminho_get_chave = caminho_get_chave;
	}
	
	public String getForma_da_chave() {
		return forma_da_chave;
	}
	
	public void setForma_da_chave(String forma_da_chave) {
		this.forma_da_chave = forma_da_chave;
	}

	public int getTamanho_da_chave() {
		return tamanho_da_chave;
	}

	public void setTamanho_da_chave(int tamanho_da_chave) {
		this.tamanho_da_chave = tamanho_da_chave;
	}
	
	public String getFabricante_modelo() {
		return fabricante_modelo;
	}

	public void setFabricante_modelo(String fabricante_modelo) {
		this.fabricante_modelo = fabricante_modelo;
	}
	
	public int getUsa_post_ou_get() {
		return usa_post_ou_get;
	}

	public void setUsa_post_ou_get(int usa_post_ou_get) {
		this.usa_post_ou_get = usa_post_ou_get;
	}

	@Override
	public String toString() {
		return "Ataque [id=" + id + ", tipo=" + tipo + ", comando=" + comando
				+ ", operadora=" + operadora 
				+ ", usa_login=" + usa_login
				+ ", usa_chave_de_sessao=" + usa_chave_de_sessao
				+ ", usa_post_ou_get=" + usa_post_ou_get
				+ ", caminho_get_chave=" + caminho_get_chave
				+ ", forma_da_chave=" + forma_da_chave 
				+ ", tamanho_da_chave=" + tamanho_da_chave 
				+ ", fabricante_modelo=" + fabricante_modelo +"]";
	}

}
