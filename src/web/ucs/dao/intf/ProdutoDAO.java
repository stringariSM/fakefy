package web.ucs.dao.intf;

import java.util.List;

import web.ucs.dao.FiltroProduto;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.Produto;

public interface ProdutoDAO {

	Produto buscaPorId(Integer id) throws FalhaAcessoAosDadosException;

	List<Produto> buscaTodos() throws FalhaAcessoAosDadosException;

	List<Produto> buscaTodos(FiltroProduto filtro) throws FalhaAcessoAosDadosException;
	
	int insere(Produto produto) throws FalhaAcessoAosDadosException;

	int altera(Produto produto) throws FalhaAcessoAosDadosException;

	int remove(Integer id) throws FalhaAcessoAosDadosException;
	
	int conta(FiltroProduto filtro) throws FalhaAcessoAosDadosException;
	
	void closeConnection();
	
	void setFactory(DAOFactory factory);
	
}