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
import web.ucs.service.AlbumService;

@ManagedBean(name = "faixaFormBean")
@ViewScoped
public class FaixaFormBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pedido pedido;
	private Faixa faixa;	
	private List<Album> albuns;
	private ItemPedido item;
	private boolean edicaoItem = false;
	private boolean edicaoPedido = false;

	@PostConstruct
	public void init() {
		System.out.println("Chamou INIT()");
		
		albuns = AlbumService.getInstance().getTodosAlbuns();		
		faixa = new Faixa();				
	}
	
	public void setFaixa(Faixa faixa) {
		
		System.out.println("Chamou set faixa()");
		
			
		this.faixa = faixa;
		
		
	}
	
	public List<Album> getSelectedAlbum() {
				
		System.out.println("Chamou artistaaaaaaaaab()");
		
		return albuns;
		
	}
	
	public void setSelectedAlbum(List<Album> albuns) {
		System.out.println("Chamou artistaaaaaaaaahaha()");
		this.albuns = albuns;
		
		faixa.setIdFaixaAlbum(albuns.get(0).getId());
		
	}
	
	public List<Album> getSelectedThemes() {
        return albuns;
    }
		
	public Faixa getFaixa() {
		
		System.out.println("Chamou set faixa()");
		
		return faixa;
	}
	
    public List<Album> getAlbuns() {
		
		System.out.println("Chamou set faixa()");
		
		return albuns;
	}
    
   public List<Album> getTodosAlbuns() {
		
		System.out.println("Chamou set faixa()");
		
		return albuns;
	}
    
    public void setAlbuns(List<Album> albuns) {
		
		this.albuns = albuns;
		
		faixa.setIdFaixaAlbum(albuns.get(0).getId());
		
	}
	
	// Métodos chamados na página
	
	public String salvar() {
		
		System.out.println("Chamou salvar faixa()");
		
		FaixaService.getInstance().salvaFaixa(faixa);
		return "listaFaixas";
	}
			

	
}
