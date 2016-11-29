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
import web.ucs.dao.intf.PlaylistDAO;
import web.ucs.dao.postgres.PostgresDAOFactory;
import web.ucs.model.Aluno;
import web.ucs.model.Artista;
import web.ucs.model.Playlist;

@ManagedBean(name="playlistBean")
@SessionScoped
public class PlaylistBean {
	
	private Playlist playlist;
	private DAOFactory factory = new PostgresDAOFactory();
		
	public PlaylistBean() {
		this.playlist = new Playlist();
	}
	
	// Getters e Setters para as propriedades
	
	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}
	
	// Métodos
	
	public List<Playlist> getTodosPlaylists() {
		//return FakeBD.getConnection().buscaTodos();
		System.out.println("passiu aqui no get todos");
		PlaylistDAO dao = factory.getPlaylistDAO();
		List<Playlist> playlists = new ArrayList<>(); 
		try {
			playlists = dao.buscaTodos();
			dao.closeConnection();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		return playlists;
	}
	

}
