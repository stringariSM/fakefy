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
import web.ucs.service.FaixaService;
import web.ucs.service.PedidosService;

@ManagedBean(name = "artistaFormBean")
@ViewScoped
public class ArtistaFormBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private UploadedFile file;
	private Artista artista;

	@PostConstruct
	public void init() {
		System.out.println("Chamou INIT()");
		artista = new Artista();
	}
	
	public void setFaixa(Artista artista) {
		this.artista = artista;
		
	}
		
	public Artista getFaixa() {
		return artista;
	}

	
	// Métodos chamados na página
	
	public String salvar() {
		//todo
		return "";
	}
			
	private Part uploadedFile; 
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		UploadedFile uploadedFile = event.getFile();
	    String fileName = uploadedFile.getFileName();
	    String contentType = uploadedFile.getContentType();
	    byte[] contents = uploadedFile.getContents();
	    
	    String strFilePath = "C:\\Users\\gabri\\git\\fakefy\\WebContent\\resources\\images\\artistas\\"+fileName+".jpg";
	    FileOutputStream fos = new FileOutputStream(strFilePath);
	    fos.write(contents);
	    fos.close();
	    
	    this.artista.setCaminhoFoto(fileName);

        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
}
