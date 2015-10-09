package br.pedrofreitas.myroutertestapp.manager;

public class Ataque {
	
	private long id;
	private String tipo;
	private String operadora;
	private int usa_cookie;
	private int usa_chave_sessao;
	private String formato_chave_sessao;
	private int usa_so_get;
	private String fabricante_modelo;
	
	public Ataque(long id, String tipo, String operadora, int usa_cookie, int usa_chave_sessao, 
			String formato_chave_sessao, int usa_so_get, String fabricante_modelo) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.operadora = operadora;
		this.usa_cookie = usa_cookie;
		this.usa_chave_sessao = usa_chave_sessao;
		this.formato_chave_sessao = formato_chave_sessao;
		this.usa_so_get = usa_so_get;
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
			
	public String getOperadora() {
		return operadora;
	}
	
	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}
		
	public int getUsa_cookie() {
		return usa_cookie;
	}
	
	public void setUsa_cookie(int usa_cookie) {
		this.usa_cookie = usa_cookie;
	}
	
	public int getUsa_chave_sessao() {
		return usa_chave_sessao;
	}
	
	public void setUsa_chave_sessao(int usa_chave_sessao) {
		this.usa_chave_sessao = usa_chave_sessao;
	}
	
	public String getFormato_chave_sessao() {
		return formato_chave_sessao;
	}
	
	public void setFormato_chave_sessao(String formato_chave_sessao) {
		this.formato_chave_sessao = formato_chave_sessao;
	}
	
	public int getUsa_so_get() {
		return usa_so_get;
	}
	
	public void setUsa_so_get(int usa_so_get) {
		this.usa_so_get = usa_so_get;
	}
	
	public String getFabricante_modelo() {
		return fabricante_modelo;
	}

	public void setFabricante_modelo(String fabricante_modelo) {
		this.fabricante_modelo = fabricante_modelo;
	}
		
	@Override
	public String toString() {
		return "Ataque [id=" + id +
					   ", tipo=" + tipo	+
					   ", operadora=" + operadora +
					   ", usa_cookie=" + usa_cookie	+
					   ", usa_chave_sessao=" + usa_chave_sessao	+
					   ", formato_chave_sessao=" + formato_chave_sessao	+
					   ", usa_so_get=" + usa_so_get	+
					   ", fabricante_modelo=" + fabricante_modelo +"]";
	}

}
