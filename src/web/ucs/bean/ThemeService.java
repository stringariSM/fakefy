package web.ucs.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.ArtistaDAO;
import web.ucs.dao.intf.DAOFactory;
import web.ucs.dao.postgres.PostgresDAOFactory;
import web.ucs.model.Artista;
 

 
@ManagedBean(name="themeService", eager = true)
@ApplicationScoped
public class ThemeService {
     
    private List<Artista> themes;
    
    private Artista artista;
	private DAOFactory factory = new PostgresDAOFactory();
     
    @PostConstruct
    public void init() {        
        
		ArtistaDAO dao = factory.getArtistaDAO();
		List<Artista> artistas = new ArrayList<>(); 
		try {
			artistas = dao.buscaTodos();
			
			themes = artistas;
			System.out.println("list novo aqui no get todos" + artistas.get(0).getNomeArtista());
			dao.closeConnection();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}	
        
    }
     
    public List<Artista> getThemes() {
        return themes;
    } 
}