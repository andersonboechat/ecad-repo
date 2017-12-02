package br.org.ecad.crud.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the VERSAO database table.
 * 
 */
@Entity
@NamedQuery(name="Versao.findAll", query="SELECT v FROM Versao v")
public class Versao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_VERSAO")
	private long codVersao;

	@Temporal(TemporalType.DATE)
	@Column(name="DAT_VERSAO")
	private Date datVersao;

	@Column(name="IND_LIBERADO", insertable=false)
	private String indLiberado;

	@Column(name="IND_SITUACAO", insertable=false)
	private String indSituacao;

	@Column(name="NRO_VERSAO")
	private String nroVersao;

	//bi-directional many-to-many association to Item
	@ManyToMany(mappedBy="versoes")
	private List<Item> items;

	public Versao() {
	}

	public long getCodVersao() {
		return this.codVersao;
	}

	public void setCodVersao(long codVersao) {
		this.codVersao = codVersao;
	}

	public Date getDatVersao() {
		return this.datVersao;
	}

	public void setDatVersao(Date datVersao) {
		this.datVersao = datVersao;
	}

	public String getIndLiberado() {
		return this.indLiberado;
	}

	public void setIndLiberado(String indLiberado) {
		this.indLiberado = indLiberado;
	}

	public String getIndSituacao() {
		return this.indSituacao;
	}

	public void setIndSituacao(String indSituacao) {
		this.indSituacao = indSituacao;
	}

	public String getNroVersao() {
		return this.nroVersao;
	}

	public void setNroVersao(String nroVersao) {
		this.nroVersao = nroVersao;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}