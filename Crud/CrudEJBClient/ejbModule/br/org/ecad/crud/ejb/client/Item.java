package br.org.ecad.crud.ejb.client;

public class Item {

	private long codigo;
	
	private String titulo;
	
	private String descricao;

	public Item(long codigo, String titulo, String descricao) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.descricao = descricao;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
