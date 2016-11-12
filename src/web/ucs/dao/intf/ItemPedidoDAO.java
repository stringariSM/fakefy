package web.ucs.dao.intf;

import java.sql.Connection;
import java.util.List;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.ItemPedido;
import web.ucs.model.Pedido;

public interface ItemPedidoDAO {

	List<ItemPedido> buscaTodos(Pedido pedido, boolean buscaProduto) throws FalhaAcessoAosDadosException;

	int removeTodos(Pedido pedido) throws FalhaAcessoAosDadosException;
	
	int insere(Pedido pedido, ItemPedido itemPedido) throws FalhaAcessoAosDadosException;

	int altera(ItemPedido itemPedido) throws FalhaAcessoAosDadosException;

	int remove(Integer id) throws FalhaAcessoAosDadosException;
	
	void closeConnection();
	
	void setFactory(DAOFactory factory);
	
	void setConnection(Connection conn);
	
}