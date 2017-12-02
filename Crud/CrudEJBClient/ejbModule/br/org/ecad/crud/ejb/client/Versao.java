package br.org.ecad.crud.ejb.client;

import java.util.Date;
import java.util.List;

public class Versao {

	private long codigo;

	private String numero;

	private Date data;

	private List<Item> itens;

	public Versao() {
		super();
	}

	public Versao(long codigo, String numero, Date data, List<Item> itens) {
		super();
		this.codigo = codigo;
		this.numero = numero;
		this.data = data;
		this.itens = itens;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
}
