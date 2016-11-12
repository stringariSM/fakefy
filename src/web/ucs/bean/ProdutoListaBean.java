package web.ucs.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.CursoDAO;
import web.ucs.model.Curso;
import web.ucs.model.Produto;
import web.ucs.service.PedidosService;

@ManagedBean(name="produtoListaBean")
@ViewScoped
public class ProdutoListaBean extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Produto> produtos;
	
	@PostConstruct
	public void init() {
		produtos = PedidosService.getInstance().getProdutos();
	}
	
	// Métodos chamados na página
	
	public List<Produto> getTodosProdutos() {
		return produtos;
	}
		
}
