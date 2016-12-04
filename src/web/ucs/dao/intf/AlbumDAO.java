package web.ucs.dao.intf;

import java.util.List;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.Album;
import web.ucs.model.Artista;

public interface AlbumDAO {

	List<Album> buscaAlbumPorId(Integer id) throws FalhaAcessoAosDadosException;
	
	List<Album> buscaTodos() throws FalhaAcessoAosDadosException;
	
	void closeConnection();
	
	void setFactory(DAOFactory factory);
	
}