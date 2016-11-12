package web.ucs.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AlunoDAO;
import web.ucs.dao.intf.ArtistaDAO;
import web.ucs.dao.intf.DAOFactory;
import web.ucs.dao.postgres.PostgresDAOFactory;
import web.ucs.model.Aluno;
import web.ucs.model.Artista;

@ManagedBean(name="artistaBean")
@SessionScoped
public class ArtistaBean {
	
	private Artista artista;
	private DAOFactory factory = new PostgresDAOFactory();
		
	public ArtistaBean() {
		this.artista = new Artista();
	}
	
	// Getters e Setters para as propriedades
	
	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}
	
	// Métodos
	
	public List<Artista> getTodosArtistas() {
		//return FakeBD.getConnection().buscaTodos();
		System.out.println("passiu aqui no get todos");
		ArtistaDAO dao = factory.getArtistaDAO();
		List<Artista> artistas = new ArrayList<>(); 
		try {
			artistas = dao.buscaTodos();
			dao.closeConnection();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		return artistas;
	}
	

}
