package web.ucs.dao.intf;

import java.util.List;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.Artista;
import web.ucs.model.Playlist;

public interface PlaylistDAO {
	
	List<Playlist> buscaTodos() throws FalhaAcessoAosDadosException;

	void closeConnection();
	
	void setFactory(DAOFactory factory);
	
}