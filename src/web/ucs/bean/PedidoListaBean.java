package web.ucs.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import web.ucs.model.Pedido;
import web.ucs.service.PedidosService;

@ManagedBean(name="pedidoListaBean")
@ViewScoped
public class PedidoListaBean extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Pedido> pedidos;
	
	@PostConstruct
	public void init() {
		pedidos = PedidosService.getInstance().getPedidos();
	}
	
	// Métodos chamados na página
	
	public List<Pedido> getTodosPedidos() {
		return pedidos;
	}
	
	public String removePedido(Pedido pedido) {
		PedidosService.getInstance().removePedido(pedido);
		return "";
	}
		
}
