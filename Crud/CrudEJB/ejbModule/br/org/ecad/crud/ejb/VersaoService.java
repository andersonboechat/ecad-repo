package br.org.ecad.crud.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.org.ecad.crud.ejb.client.VersaoServiceLocal;
import br.org.ecad.crud.ejb.client.Item;
import br.org.ecad.crud.ejb.client.Versao;

@Stateless
public class VersaoService implements VersaoServiceLocal {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Versao consultarVersao(long codigo) throws Exception {
		br.org.ecad.crud.ejb.entity.Versao v = manager.find(br.org.ecad.crud.ejb.entity.Versao.class, codigo);

		if (v == null) {
			throw new Exception("Não encontrada versão com código " + codigo);
		}

		List<Item> its = new ArrayList<Item>();

		for (br.org.ecad.crud.ejb.entity.Item item : v.getItems()) {
			its.add(new Item(item.getCodItem(), item.getDscTitulo(), item.getTxtDescricao()));
		}

		return new Versao(v.getCodVersao(), v.getNroVersao(), v.getDatVersao(), its);
	}

	@Override
	public Versao incluirVersao(long codigo, Date data, String numero) throws Exception {
		try {
			br.org.ecad.crud.ejb.entity.Versao v = new br.org.ecad.crud.ejb.entity.Versao();
			v.setCodVersao(codigo);
			v.setDatVersao(data);
			v.setNroVersao(numero);
			manager.persist(v);
			manager.flush();
			return new Versao(codigo, numero, data, new ArrayList<Item>());
		} catch (Exception e) {
			throw new Exception("Já existe uma versão com código " + codigo);
		}
	}

}
