package br.org.ecad.crud.ejb.client;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ItemServiceLocal {

	public List<Item> consultarItem(long codigoVersao, String titulo) throws Exception;

	public Item incluirItem(long codigoVersao, String titulo, String descricao) throws Exception;

	public void excluirItem(long codigo) throws Exception;

}
