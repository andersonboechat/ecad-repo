package br.org.ecad.crud.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ITEM database table.
 * 
 */
@Entity
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_ITEM")
	private long codItem;

	@Column(name="DSC_TITULO")
	private String dscTitulo;

	@Column(name="TXT_DESCRICAO")
	private String txtDescricao;

	//bi-directional many-to-many association to Versao
	@ManyToMany
	@JoinTable(
		name="VERSAO_ITEM"
		, joinColumns={
			@JoinColumn(name="COD_ITEM")
			}
		, inverseJoinColumns={
			@JoinColumn(name="COD_VERSAO")
			}
		)
	private List<Versao> versoes;

	public Item() {
	}

	public long getCodItem() {
		return this.codItem;
	}

	public void setCodItem(long codItem) {
		this.codItem = codItem;
	}

	public String getDscTitulo() {
		return this.dscTitulo;
	}

	public void setDscTitulo(String dscTitulo) {
		this.dscTitulo = dscTitulo;
	}

	public String getTxtDescricao() {
		return this.txtDescricao;
	}

	public void setTxtDescricao(String txtDescricao) {
		this.txtDescricao = txtDescricao;
	}

	public List<Versao> getVersoes() {
		return this.versoes;
	}

	public void setVersoes(List<Versao> versoes) {
		this.versoes = versoes;
	}

}