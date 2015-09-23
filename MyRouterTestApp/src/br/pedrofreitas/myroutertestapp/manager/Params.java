package br.pedrofreitas.myroutertestapp.manager;

public class Params {
	
	private long id;
	private long id_comando;
	private String nome;
	private String valor;
	
	public Params(long id, long id_comando, String nome, String valor) {
		super();
		this.id = id;
		this.id_comando = id_comando;
		this.nome = nome;
		this.valor = valor;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId_comando() {
		return id_comando;
	}
	
	public void setId_comando(long id_comando) {
		this.id_comando = id_comando;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return "Parametros [id=" + id +
				           ", id_comando=" + id_comando	+
				           ", nome=" + nome +
				           ", valor=" + valor + "]";
	}

}
