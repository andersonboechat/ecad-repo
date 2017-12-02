package br.org.ecad.crud.ejb.client;

public class Login {

	private String login;
	
	private String nome;

	public Login(String login, String nome) {
		super();
		this.login = login;
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
