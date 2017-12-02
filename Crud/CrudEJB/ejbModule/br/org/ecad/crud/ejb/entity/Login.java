package br.org.ecad.crud.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LOGIN database table.
 * 
 */
@Entity
@NamedQuery(name="Login.findAll", query="SELECT l FROM Login l")
public class Login implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NOM_LOGIN")
	private String nomLogin;	

	@Column(name="TXT_NOME")
	private String txtNome;

	public Login() {
	}

	public String getNomLogin() {
		return nomLogin;
	}

	public void setNomLogin(String nomLogin) {
		this.nomLogin = nomLogin;
	}

	public String getTxtNome() {
		return this.txtNome;
	}

	public void setTxtNome(String txtNome) {
		this.txtNome = txtNome;
	}

}