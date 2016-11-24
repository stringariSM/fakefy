package web.ucs.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import web.ucs.model.Album;
import web.ucs.model.Faixa;
import web.ucs.model.ItemPedido;
import web.ucs.model.Pedido;
import web.ucs.model.Produto;
import web.ucs.service.AlbumService;
import web.ucs.service.FaixaService;
import web.ucs.service.PedidosService;

@ManagedBean(name = "faixaListaBean")
@ViewScoped
public class FaixaListaBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;	
	private List<Faixa> faixas;	

	@PostConstruct
	public void init() {
		System.out.println("Chamou INIT()");		
		buscaFaixas();
	}
	
	public List<Faixa> getFaixas(){
		return faixas;
	}
	
	
	public void buscaFaixas() {
		System.out.println("--> buscaPedido(...)");
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			String sRow = (String) fc.getExternalContext()
					.getRequestParameterMap().get("idArtista");
			if (sRow != null) {
				int index = -1;
				try {
					index = Integer.parseInt(sRow);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}
				this.faixas = FaixaService.getInstance().getFaixas(index);
				System.out.println("Lista aa");
				
				
			} else {
				System.out.println("Coloca em modo de edição");
			}
		} catch (Exception e) {
			System.out.println("Coloca em modo de edição");
		}		
	}
}
