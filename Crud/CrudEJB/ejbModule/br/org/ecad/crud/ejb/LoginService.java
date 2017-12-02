package br.org.ecad.crud.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.org.ecad.crud.ejb.client.Login;
import br.org.ecad.crud.ejb.client.LoginServiceLocal;

@Stateless
public class LoginService implements LoginServiceLocal {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Login login(String nome) throws Exception {
		br.org.ecad.crud.ejb.entity.Login login;
		login = manager.find(br.org.ecad.crud.ejb.entity.Login.class, nome);

		if (login == null) {
			throw new Exception("Login não encontrado");
		}

		return new Login(login.getNomLogin(), login.getTxtNome());
	}

}
