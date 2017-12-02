package br.org.ecad.ws;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

/**
 * The persistent class for the TWEET database table.
 * 
 */
@Entity
@XmlRootElement
public class Tweet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private long id;	

	@Column(name="TEXTO")
	private String texto;

	public Tweet() {
		super();
	}

	public Tweet(long id, String texto) {
		super();
		this.id = id;
		this.texto = texto;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
