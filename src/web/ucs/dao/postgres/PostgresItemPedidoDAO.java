package web.ucs.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AbstractDAO;
import web.ucs.dao.intf.ItemPedidoDAO;
import web.ucs.dao.intf.ProdutoDAO;
import web.ucs.model.ItemPedido;
import web.ucs.model.Pedido;
import web.ucs.model.Produto;

public class PostgresItemPedidoDAO extends AbstractDAO implements ItemPedidoDAO {

	private Connection conn;
	private boolean emTransacao = false;

	PostgresItemPedidoDAO(Connection conn) {
		this.conn = conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
		this.emTransacao = true;
	};

	@Override
	/**
	 * 
	 * @param pedido
	 * @param buscaProduto
	 * @return Se recebe null como parâmetro no pedido, traz todos os itens de todos os pedidos, senão, só os do pedido informado. buscaProduto define se deve trazer ou não os produtos relacionados
	 * @throws FalhaAcessoAosDadosException
	 */
	public List<ItemPedido> buscaTodos(Pedido pedido, boolean buscaProduto)
			throws FalhaAcessoAosDadosException {

		List<ItemPedido> itens = new ArrayList<ItemPedido>();

		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			if (pedido == null) {
				rs = stmt
						.executeQuery("select id, quantidade, id_pedido, id_produto from paw1_pedido_item");
			} else {
				rs = stmt
						.executeQuery("select id, quantidade, id_pedido, id_produto from paw1_pedido_item where id_pedido = "
								+ pedido.getId());
			}

			while (rs.next()) {
				ItemPedido item = new ItemPedido();
				item.setId(rs.getInt("id"));
				item.setQuantidade(rs.getInt("quantidade"));

				if (buscaProduto) {
					ProdutoDAO dao = factory.getProdutoDAO();
					Produto produto = dao.buscaPorId(rs.getInt("id_produto"));
					item.setProduto(produto);
					// ou
					// item.setProduto(factory.getProdutoDAO().buscaPorId(rs.getInt("id_produto")));

				} else {
					item.setProduto(new Produto(rs.getInt("id_produto")));
				}
				itens.add(item);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(stmt);
			closeConnection();
		}

		return itens;
	}

	@Override
	public int insere(Pedido pedido, ItemPedido item)
			throws FalhaAcessoAosDadosException {

		int inseriu;

		if (item != null) {

			PreparedStatement pstmt = null;

			try {

				if (item.getId() == null) {
					pstmt = conn
							.prepareStatement("insert into paw1_pedido_item(id, quantidade, id_produto, id_pedido) values (nextval('paw1_pedido_item_seq'), ?, ?, ?)");

					pstmt.setInt(1, item.getQuantidade());
					pstmt.setInt(2, item.getProduto().getId());
					pstmt.setInt(3, pedido.getId());
				} else {
					pstmt = conn
							.prepareStatement("insert into paw1_pedido_item(id, quantidade, id_produto, id_pedido) values (?, ?, ?, ?)");

					pstmt.setInt(1, item.getId());
					pstmt.setInt(2, item.getQuantidade());
					pstmt.setInt(3, item.getProduto().getId());
					pstmt.setInt(4, pedido.getId());

				}
				inseriu = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new FalhaAcessoAosDadosException(
						"Falha ao inserir registro", e);
			} finally {
				this.fechaPreparedStatement(pstmt);
				closeConnection();
			}
		} else {
			throw new FalhaAcessoAosDadosException("Registro nulo");
		}
		return inseriu;
	}

	@Override
	public int altera(ItemPedido item) throws FalhaAcessoAosDadosException {

		System.out.println("Alterando " + item);

		int alterou;

		if (item != null) {

			PreparedStatement pstmt = null;

			try {

				pstmt = conn
						.prepareStatement("update paw1_pedido_item set id_produto = ?, quantidade = ? where id = ?");

				pstmt.setInt(1, item.getProduto().getId());
				pstmt.setInt(2, item.getQuantidade());
				pstmt.setInt(3, item.getId());

				alterou = pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new FalhaAcessoAosDadosException(
						"Falha ao alterar registro", e);
			} finally {
				this.fechaPreparedStatement(pstmt);
				closeConnection();
			}
		} else {
			throw new FalhaAcessoAosDadosException("Registro nulo");
		}
		return alterou;
	}

	@Override
	public int remove(Integer id) throws FalhaAcessoAosDadosException {

		int apagou = 0;

		if (id != null) {

			PreparedStatement pstmt = null;

			try {
				pstmt = conn
						.prepareStatement("delete from paw1_pedido_item where id =  ?");
				pstmt.setInt(1, id);
				apagou = pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new FalhaAcessoAosDadosException(
						"Falha removendo o registro", e);
			} finally {
				fechaPreparedStatement(pstmt);
				closeConnection();
			}
		} else {
			throw new FalhaAcessoAosDadosException("Id nulo");
		}
		return apagou;
	}

	@Override
	public int removeTodos(Pedido pedido) throws FalhaAcessoAosDadosException {

		int apagou = 0;

		if (pedido != null) {

			PreparedStatement pstmt = null;

			try {
				pstmt = conn
						.prepareStatement("delete from paw1_pedido_item where id_pedido =  ?");
				pstmt.setInt(1, pedido.getId());
				apagou = pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new FalhaAcessoAosDadosException(
						"Falha removendo o registro", e);
			} finally {
				fechaPreparedStatement(pstmt);
				closeConnection();
			}
		} else {
			throw new FalhaAcessoAosDadosException("id nulo");
		}
		return apagou;
	}

	public void closeConnection() {
		if (!emTransacao) {
			closeConnection(conn);
		}
	}
}