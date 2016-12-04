package web.ucs.bean;

import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import web.ucs.model.Album;
import web.ucs.model.Artista;
import web.ucs.model.Faixa;
import web.ucs.model.ItemPedido;
import web.ucs.model.Pedido;
import web.ucs.model.Produto;
import web.ucs.service.AlbumService;
import web.ucs.service.FaixaService;
import web.ucs.service.PedidosService;
import web.ucs.service.ArtistaService;

@ManagedBean(name = "albumFormBean")
@ViewScoped
public class AlbumFormBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pedido pedido;
	private Faixa faixa;
	
	private Album album;
	
	private List<Artista> artistas;
	private ItemPedido item;
	private boolean edicaoItem = false;
	private boolean edicaoPedido = false;

	@PostConstruct
	public void init() {
		System.out.println("Chamou INIT()");
		
		artistas = ArtistaService.getInstance().getTodosArtistas();		
		album = new Album();				
	}
	
     public void setAlbum(Album album) {
		
		System.out.println("Chamou set faixa()");
		
			
		this.album = album;		
	}
     
    public Album getAlbum() {
 		 		
 		return album;		
 	}
	
		
	public Faixa getFaixa() {
		
		System.out.println("Chamou set faixa()");
		
		return faixa;
	}
	
    public List<Artista> getArtistas() {
		
		System.out.println("Chamou set faixa()");
		
		return artistas;
	}
    
   public List<Artista> getTodosArtistas() {
		
		System.out.println("Chamou set faixa()");
		
		return artistas;
	}
    
    public void setArtistas(List<Artista> artistas) {
		
		this.artistas = artistas;
						
	}
	
	// Métodos chamados na página
	
	public String salvar() {
		
		System.out.println("Chamou salvar ALBUM()");
		
		AlbumService.getInstance().salvaAlbum(album);
		return "listaFaixas";
	}
	
			
	private Part uploadedFile; 
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		UploadedFile uploadedFile = event.getFile();
	    String fileName = uploadedFile.getFileName();
	    String contentType = uploadedFile.getContentType();
	    byte[] contents = uploadedFile.getContents();
	    
	    String strFilePath = "C:\\Users\\Usuario\\git\\fakefy\\WebContent\\resources\\images\\albuns\\"+fileName+".jpg";
	    FileOutputStream fos = new FileOutputStream(strFilePath);
	    fos.write(contents);
	    fos.close();
	    
	    System.out.println("Chamou salvar ALBUM FOTO()");
	    
	    this.album.setCaminhoFoto(fileName);

        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	
}
