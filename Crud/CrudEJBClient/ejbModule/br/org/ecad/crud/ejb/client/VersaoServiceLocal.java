package br.org.ecad.crud.ejb.client;

import java.util.Date;

import javax.ejb.Local;

@Local
public interface VersaoServiceLocal {

	public Versao consultarVersao(long codigo) throws Exception;
	
	public Versao incluirVersao(long codigo, Date data, String numero) throws Exception;

}
