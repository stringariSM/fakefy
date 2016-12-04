package web.ucs.dao.intf;

import java.util.List;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.Album;
import web.ucs.model.Artista;
import web.ucs.model.Faixa;
import web.ucs.model.Pedido;

public interface FaixaDAO {

	Integer insere(Faixa faixa) throws FalhaAcessoAosDadosException;
	
	List<Faixa> buscaFaixaPorId(Integer id) throws FalhaAcessoAosDadosException;
	
	List<Faixa> buscaFaixaPorIdPlaylist(Integer id) throws FalhaAcessoAosDadosException;
	
	void closeConnection();
	
	void setFactory(DAOFactory factory);
	
}