package web.ucs.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AlbumDAO;
import web.ucs.dao.intf.ArtistaDAO;
import web.ucs.dao.intf.DAOFactory;
import web.ucs.dao.postgres.PostgresDAOFactory;
import web.ucs.model.Album;
import web.ucs.model.Artista;
 

 
@ManagedBean(name="albumService", eager = true)
@ApplicationScoped
public class AlbumService {
     
    private List<Album> themes;
    
    private Album album;
	private DAOFactory factory = new PostgresDAOFactory();
     
    @PostConstruct
    public void init() {        
        
		AlbumDAO dao = factory.getAlbumDAO();
		List<Album> albuns = new ArrayList<>(); 
		try {
			albuns = dao.buscaTodos();
			
			themes = albuns;			
			dao.closeConnection();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}	
        
    }
     
    public List<Album> getThemes() {
        return themes;
    } 
}