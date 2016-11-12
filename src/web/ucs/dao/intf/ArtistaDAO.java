package web.ucs.dao.intf;

import java.util.List;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.Artista;

public interface ArtistaDAO {

	Artista buscaArtistaPorId(Integer id) throws FalhaAcessoAosDadosException;

	List<Artista> buscaTodos() throws FalhaAcessoAosDadosException;

	void closeConnection();
	
	void setFactory(DAOFactory factory);
	
}