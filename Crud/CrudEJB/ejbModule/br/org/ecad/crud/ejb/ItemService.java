package br.org.ecad.crud.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.org.ecad.crud.ejb.client.Item;
import br.org.ecad.crud.ejb.client.ItemServiceLocal;
import br.org.ecad.crud.ejb.entity.Versao;

@Stateless
public class ItemService implements ItemServiceLocal {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	@SuppressWarnings("unchecked")
	public List<Item> consultarItem(long codigoVersao, String titulo) throws Exception {
		Query query = manager.createQuery("SELECT i FROM Item i JOIN i.versoes v WHERE v.codVersao = :codigo AND i.dscTitulo LIKE :titulo");
		query.setParameter("codigo", codigoVersao);
		query.setParameter("titulo", "%" + titulo + "%");
		
		List<br.org.ecad.crud.ejb.entity.Item> itens = query.getResultList();
		List<Item> its = new ArrayList<Item>();
		
		for (br.org.ecad.crud.ejb.entity.Item item : itens) {
			its.add(new Item(item.getCodItem(), item.getDscTitulo(), item.getTxtDescricao()));
		}

		return its;
	}

	@Override
	public Item incluirItem(long codigoVersao, String titulo, String descricao) throws Exception {
		ArrayList<Versao> versoes = new ArrayList<Versao>();
		versoes.add(manager.find(Versao.class, codigoVersao));
		
		br.org.ecad.crud.ejb.entity.Item item = new br.org.ecad.crud.ejb.entity.Item();
		
		item.setDscTitulo(titulo);
		item.setTxtDescricao(descricao);
		item.setVersoes(versoes);

		manager.persist(item);

		return new Item(item.getCodItem(), item.getDscTitulo(), item.getTxtDescricao());
	}

	@Override
	public void excluirItem(long codigo) throws Exception {
		manager.remove(manager.find(br.org.ecad.crud.ejb.entity.Item.class, codigo));
	}

}
