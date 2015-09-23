package br.pedrofreitas.myroutertestapp.manager;

public class Ataque {
	
	private long id;
	private String tipo;
	private String operadora;
	private int usa_cookie;
	private String fabricante_modelo;
	
	public Ataque(long id, String tipo, String operadora, int usa_cookie, String fabricante_modelo) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.operadora = operadora;
		this.usa_cookie = usa_cookie;
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
					   ", fabricante_modelo=" + fabricante_modelo +"]";
	}

}
