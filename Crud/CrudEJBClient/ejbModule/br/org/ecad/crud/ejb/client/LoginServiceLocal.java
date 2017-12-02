package br.org.ecad.crud.ejb.client;

import javax.ejb.Local;

@Local
public interface LoginServiceLocal {

	public Login login(String login) throws Exception;

}
