package web.ucs.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.ucs.dao.FiltroPedido;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.exceptions.PedidoNaoEncontradoException;
import web.ucs.dao.intf.AbstractDAO;
import web.ucs.dao.intf.PedidoDAO;
import web.ucs.model.Pedido;

public class PostgresPedidoDAO extends AbstractDAO implements PedidoDAO {

	private Connection conn;
	private boolean transacaoAberta = false;

	PostgresPedidoDAO(Connection conn) {
		this.conn = conn;
	}
	
	public Connection getConnection() {
		return this.conn;
	}

	@Override
	public Pedido buscaPorId(Integer id) throws FalhaAcessoAosDadosException {

		if (id == null) {
			throw new PedidoNaoEncontradoException("Id nulo");
		}

		Pedido pedido = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select id, nome_cliente, data from paw1_pedido where id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				pedido = new Pedido();
				pedido.setId(rs.getInt("id"));
				pedido.setNomeCliente(rs.getString("nome_cliente"));
				pedido.setData(rs.getDate("data"));
			} else {
				throw new PedidoNaoEncontradoException(id.toString());
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(pstmt);
			closeConnection();
		}

		return pedido;
	}

	@Override
	public List<Pedido> buscaTodos() throws FalhaAcessoAosDadosException {
		List<Pedido> pedidos = new ArrayList<Pedido>();

		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select id, nome_cliente, data from paw1_pedido");

			while (rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setId(rs.getInt("id"));
				pedido.setNomeCliente(rs.getString("nome_cliente"));
				pedido.setData(rs.getDate("data"));
				pedidos.add(pedido);
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

		return pedidos;
	}

	@Override
	public List<Pedido> buscaTodos(FiltroPedido filtro)
			throws FalhaAcessoAosDadosException {
		List<Pedido> pedidos = new ArrayList<Pedido>();

		System.out.println("Buscando pedidos com offset "
				+ filtro.getPrimeiroRegistro() + " e limite "
				+ filtro.getQuantidadeRegistros() + " e nome"
				+ filtro.getNome());

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select id, nome_cliente, data from paw1_pedido where nome_cliente like ? limit ? offset ?");
			if (filtro.filtraPorNome()) {
				pstmt.setString(1, "%" + filtro.getNome() + "%");
			} else {
				pstmt.setString(1, "%");
			}
	
			pstmt.setInt(2, filtro.getQuantidadeRegistros());
			pstmt.setInt(3, filtro.getPrimeiroRegistro());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setId(rs.getInt("id"));
				pedido.setNomeCliente(rs.getString("nome_cliente"));
				pedido.setData(rs.getDate("data"));
				pedidos.add(pedido);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaPreparedStatement(pstmt);
			closeConnection();
		}

		return pedidos;
	}

	@Override
	public Integer insere(Pedido pedido) throws FalhaAcessoAosDadosException {

		Integer idInserido = null;

		if (pedido != null) {

			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;
			
			try {
				
				conn.setAutoCommit(false);

				pstmt = conn
						.prepareStatement("insert into paw1_pedido(id, nome_cliente, data) values (nextval('paw1_pedido_seq'), ?, ?)");

				pstmt.setString(1, pedido.getNomeCliente());
				if(pedido.getData()!=null) {
					pstmt.setDate(2, new java.sql.Date(pedido.getData().getTime()));
				} else {
					pstmt.setNull(2, java.sql.Types.DATE);
				}

				pstmt.executeUpdate();
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select currval('paw1_pedido_seq')");
				if(rs.next()) {
					idInserido = rs.getInt(1);
				}
				
				conn.commit();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new FalhaAcessoAosDadosException(
						"Falha ao inserir registro", e);
			} finally {
				this.fechaPreparedStatement(pstmt);
				this.fechaStatement(stmt);
				this.fechaResultSet(rs);
				closeConnection();
			}
		} else {
			throw new FalhaAcessoAosDadosException("Registro nulo");
		}
		return idInserido;
	}

	@Override
	public int altera(Pedido pedido) throws FalhaAcessoAosDadosException {
		
		System.out.println("Alterando " + pedido);
		
		int alterou;

		if (pedido != null) {

			PreparedStatement pstmt = null;

			try {

				pstmt = conn
						.prepareStatement("update paw1_pedido set nome_cliente = ?, data = ? where id = ?");

				pstmt.setString(1, pedido.getNomeCliente());
				if(pedido.getData()!=null) {
					pstmt.setDate(2, new java.sql.Date(pedido.getData().getTime()));
				} else {
					pstmt.setNull(2, java.sql.Types.DATE);
				}
				
				pstmt.setInt(3, pedido.getId());

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
	public int remove(Integer id, boolean cascata) throws FalhaAcessoAosDadosException {

		int apagou = 0;

		if (id != null) {

			PreparedStatement pstmt = null;

			try {
				
				conn.setAutoCommit(false);
				
				if(cascata) {
					pstmt = conn
							.prepareStatement("delete from paw1_pedido_item where id_pedido =  ?");
					pstmt.setInt(1, id);
					pstmt.executeUpdate();
					fechaPreparedStatement(pstmt);
				}
				
				pstmt = conn
						.prepareStatement("delete from paw1_pedido where id =  ?");
				pstmt.setInt(1, id);
				apagou = pstmt.executeUpdate();
				
				conn.commit();
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
	public int conta(FiltroPedido filtro) throws FalhaAcessoAosDadosException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int retorno = 0;

		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select count(*) from paw1_pedido\n");
			if (filtro.filtraPorNome()) {
				sb.append("where\n");

				if (filtro.filtraPorNome()) {
					sb.append("nome like ?\n");
				}

			}
						
			pstmt = conn.prepareStatement(sb.toString());

			if (filtro.filtraPorNome()) {

				if (filtro.filtraPorNome()) {
					pstmt.setString(1, "%" + filtro.getNome() + "%");
				}

			}
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				retorno = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha contando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaPreparedStatement(pstmt);
			closeConnection();
		}

		return retorno;
	}
	
	public void abreTransacao() {
		if(conn!=null) {
			try {
				conn.setAutoCommit(false);
				transacaoAberta = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void commitaTransacao() {
		if(conn!=null) {
			try {
				conn.commit();
				conn.setAutoCommit(true);
				transacaoAberta = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		if(!transacaoAberta) {
			closeConnection(conn);
		}
	}
}