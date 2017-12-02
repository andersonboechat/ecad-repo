package br.org.ecad.crud.bean;

import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import br.org.ecad.crud.ejb.client.Item;
import br.org.ecad.crud.ejb.client.ItemServiceLocal;
import br.org.ecad.crud.ejb.client.Login;
import br.org.ecad.crud.ejb.client.LoginServiceLocal;
import br.org.ecad.crud.ejb.client.Versao;
import br.org.ecad.crud.ejb.client.VersaoServiceLocal;

@ManagedBean
@SessionScoped
public class CrudBean {

	@EJB
	private LoginServiceLocal loginServiceLocal;

	@EJB
	private VersaoServiceLocal versaoServiceLocal;

	@EJB
	private ItemServiceLocal itemServiceLocal;
	
	private Login login;

	private Versao versao;

	private String nomeLogin;

	private Long codigoVersao;

	private Date dataVersao;

	private String numeroVersao;

	private String tituloItem;

	private String descricaoItem;

	public void onReset() {
		versao = null;
		codigoVersao = null;
		dataVersao = null;
		numeroVersao = null;
		tituloItem = null;
		descricaoItem = null;
	}

	public String onLogin() {
		try {
			if (login == null) {
				login = loginServiceLocal.login(nomeLogin);				
			}

			return "success";
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage("message", msg);
		}

		nomeLogin = null;

		return "fail";
	}

	public void onConsultar() {
		try {
			versao = versaoServiceLocal.consultarVersao(codigoVersao);

			if (StringUtils.isNotEmpty(tituloItem)) {
				versao.setItens(itemServiceLocal.consultarItem(codigoVersao, tituloItem));
			}
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage("message", msg);
		}
	}

	public void onIncluir() {
		try {
			if (versao == null) {
				versao = versaoServiceLocal.incluirVersao(codigoVersao, dataVersao, numeroVersao);
			}

			if (StringUtils.isNotEmpty(tituloItem)) {
				Item item = itemServiceLocal.incluirItem(codigoVersao, tituloItem, descricaoItem);
				versao.getItens().add(item);
				tituloItem = null;
				descricaoItem = null;
			}

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage("message", msg);
		}
	}
	
	public void onExcluir(long itemCodigo) {
		try {
			itemServiceLocal.excluirItem(itemCodigo);
			versao = versaoServiceLocal.consultarVersao(codigoVersao);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage("message", msg);
		}
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Versao getVersao() {
		return versao;
	}

	public void setVersao(Versao versao) {
		this.versao = versao;
	}

	public String getNomeLogin() {
		return nomeLogin;
	}

	public void setNomeLogin(String nomeLogin) {
		this.nomeLogin = nomeLogin;
	}

	public Long getCodigoVersao() {
		return codigoVersao;
	}

	public void setCodigoVersao(Long codigoVersao) {
		this.codigoVersao = codigoVersao;
	}

	public Date getDataVersao() {
		return dataVersao;
	}

	public void setDataVersao(Date dataVersao) {
		this.dataVersao = dataVersao;
	}

	public String getNumeroVersao() {
		return numeroVersao;
	}

	public void setNumeroVersao(String numeroVersao) {
		this.numeroVersao = numeroVersao;
	}

	public String getTituloItem() {
		return tituloItem;
	}

	public void setTituloItem(String tituloItem) {
		this.tituloItem = tituloItem;
	}

	public String getDescricaoItem() {
		return descricaoItem;
	}

	public void setDescricaoItem(String descricaoItem) {
		this.descricaoItem = descricaoItem;
	}

}
