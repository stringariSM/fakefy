package web.ucs.bean;


import java.util.List;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import web.ucs.model.Album;
import web.ucs.model.Artista;
import web.ucs.model.Faixa;
import web.ucs.model.ItemPedido;
import web.ucs.model.Pedido;
import web.ucs.model.Produto;
import web.ucs.service.FaixaService;
import web.ucs.service.PedidosService;

@ManagedBean(name = "faixaFormBean")
@ViewScoped
public class FaixaFormBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pedido pedido;
	private Faixa faixa;	
	private List<Artista> artistas;
	private ItemPedido item;
	private boolean edicaoItem = false;
	private boolean edicaoPedido = false;

	@PostConstruct
	public void init() {
		System.out.println("Chamou INIT()");
		faixa = new Faixa();
				
	}
	
	public void setFaixa(Faixa faixa) {
		this.faixa = faixa;
		
	}
	
	public void setSelectedArtista(List<Artista> artista) {
		this.artistas = artista;
		
		System.out.println("Chamou artistaaaaaaaaa()" + artista.get(0).getNomeArtista());
		
	}
		
	public Faixa getFaixa() {
		return faixa;
	}

	
	// Métodos chamados na página
	
	public String salvar() {
		FaixaService.getInstance().salvaFaixa(faixa);
		return "listaFaixas";
	}
			

	
}
