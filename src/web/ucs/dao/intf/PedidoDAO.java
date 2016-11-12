package web.ucs.dao.intf;

import java.sql.Connection;
import java.util.List;

import web.ucs.dao.FiltroPedido;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.Pedido;

public interface PedidoDAO {

	Pedido buscaPorId(Integer id) throws FalhaAcessoAosDadosException;

	List<Pedido> buscaTodos() throws FalhaAcessoAosDadosException;

	List<Pedido> buscaTodos(FiltroPedido filtro) throws FalhaAcessoAosDadosException;
	
	Integer insere(Pedido pedido) throws FalhaAcessoAosDadosException;

	int altera(Pedido pedido) throws FalhaAcessoAosDadosException;

	int remove(Integer id, boolean cascata) throws FalhaAcessoAosDadosException;
	
	int conta(FiltroPedido filtro) throws FalhaAcessoAosDadosException;
	
	public Connection getConnection();
	
	void closeConnection();
	
	void setFactory(DAOFactory factory);
	
	void abreTransacao();
	
	void commitaTransacao();
	
}