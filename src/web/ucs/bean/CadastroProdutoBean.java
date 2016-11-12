package web.ucs.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import web.ucs.bean.lazy.ProdutoLazyDataModel;
import web.ucs.dao.FiltroProduto;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.Produto;
import web.ucs.service.PedidosService;

@ManagedBean(name = "cadastroProdutoBean")
@ViewScoped
public class CadastroProdutoBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Produto> produtos;
	private Produto produto;
	private Produto produtoSelecionado;
	private FiltroProduto filtro;
	private ProdutoLazyDataModel model;

	@PostConstruct
	public void init() {
		System.out.println("Init()");
		produtos = new ArrayList<>();
		produto = new Produto();
		filtro = new FiltroProduto();
		filtro.setPrimeiroRegistro(0);
		filtro.setQuantidadeRegistros(5);
		model = new ProdutoLazyDataModel(produtos, filtro);

		System.out.println(produto);
		System.out.println(filtro);
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {

		System.out.println("Chamou produto selecionado " + produtoSelecionado);
		this.produtoSelecionado = produtoSelecionado;
		if (produtoSelecionado != null) {
			this.produto = produtoSelecionado;
		}
	}

	public Integer getOffset() {
		return filtro.getPrimeiroRegistro();
	}

	public void setOffset(Integer offset) {
		this.filtro.setPrimeiroRegistro(offset);
	}

	public Integer getQuantidade() {
		return filtro.getQuantidadeRegistros();
	}

	public void setQuantidade(Integer quantidade) {
		this.filtro.setQuantidadeRegistros(quantidade);
	}

	public FiltroProduto getFiltro() {
		return filtro;
	}

	// Métodos chamados na página

	public void pesquisar(ActionEvent event) {
		System.out.println("Mandou pesquisar");
		System.out.println("Produto " + produto);
		filtro.setPrimeiroRegistro(0);
		filtro.setCodigo(produto.getCodigo());
		filtro.setNome(produto.getNome());
		System.out.println("Filtro " + filtro);
	}

	public void limpar(ActionEvent event) {
		System.out.println("Mandou limpar");
		produto = new Produto();
		filtro.resetCampos();
		setProdutoSelecionado(null);
	}

	public void salvar(ActionEvent event) {
		System.out.println("Mandou salvar");
		System.out.println("produto " + produto);
		System.out.println("filtro " + filtro);
		PedidosService.getInstance().salvaProduto(produto);
		produto = new Produto();
		filtro.resetCampos();
	}

	public String remove(Produto produto) {
		System.out.println("Mandou excluir");
		try {
			PedidosService.getInstance().removeProduto(produto);
		} catch (FalhaAcessoAosDadosException f) {
			FacesMessage msg = new FacesMessage("Não foi possível executar a operação : ", f.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return null;
	}

	public ProdutoLazyDataModel getLazyModel() {
		return model;
	}

	public void onRowSelect(SelectEvent event) {
		System.out.println("Disparou onRowSelect(...)");
	}

}
