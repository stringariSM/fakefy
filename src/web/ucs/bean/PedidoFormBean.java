package web.ucs.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import web.ucs.model.ItemPedido;
import web.ucs.model.Pedido;
import web.ucs.model.Produto;
import web.ucs.service.PedidosService;

@ManagedBean(name = "pedidoFormBean")
@ViewScoped
public class PedidoFormBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pedido pedido;
	private List<Produto> produtos;
	private ItemPedido item;
	private boolean edicaoItem = false;
	private boolean edicaoPedido = false;

	@PostConstruct
	public void init() {
		System.out.println("Chamou INIT()");
		produtos = PedidosService.getInstance().getProdutos();
		item = new ItemPedido();
		buscaPedido();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	// Métodos chamados na página

	public ItemPedido getItem() {
		return item;
	}

	public void setItem(ItemPedido item) {
		this.item = item;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public String salvar() {
		PedidosService.getInstance().salvaPedido(pedido);
		return "listaPedidos";
	}
	
	public void adicionarItem(ActionEvent event) {
		System.out.println("--> adicionarItem(...)");
		System.out.println("Chamou adicionar item");
		System.out.println("Coloca em modo \"novo\"");
		this.item = new ItemPedido();
		System.out.println(pedido);
		edicaoItem = false;
	}

	public String atualizaLista() {
		System.out.println("--> atualizaLista()");
		if (!edicaoItem) {
			System.out.println("Colocou na lista");
			this.pedido.addItem(item);
		}
		System.out.println(pedido);
		return "";
	}

	public String removeItem(ItemPedido item) {
		System.out.println("--> removeItem(...)");
		System.out.println("Mandando excluir o ítem " + item);
		this.pedido.removeItem(item);
		System.out.println(pedido);
		return "";
	}

	public void buscaItem(ActionEvent event) {
		System.out.println("--> buscaItem(...)");
		System.out.println("Coloca em modo de edição");
		edicaoItem = true;
	}

	public void buscaPedido() {
		System.out.println("--> buscaPedido(...)");
		try {			
				this.pedido = new Pedido();
			
		} catch (Exception e) {
			this.pedido = new Pedido();
		}
		System.out.println(pedido);
	}
}
