package web.ucs.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.ucs.dao.FiltroProduto;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.exceptions.ProdutoNaoEncontradoException;
import web.ucs.dao.exceptions.ProdutoReferenciadoException;
import web.ucs.dao.intf.AbstractDAO;
import web.ucs.dao.intf.ProdutoDAO;
import web.ucs.model.Produto;

public class PostgresProdutoDAO extends AbstractDAO implements ProdutoDAO {

	private Connection conn;

	PostgresProdutoDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Produto buscaPorId(Integer id) throws FalhaAcessoAosDadosException {

		if (id == null) {
			throw new ProdutoNaoEncontradoException("Id nulo");
		}

		Produto produto = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select id, nome, codigo, descricao from paw1_produto where id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setCodigo(rs.getString("codigo"));
				produto.setDescricao(rs.getString("descricao"));
			} else {
				throw new ProdutoNaoEncontradoException(id.toString());
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(pstmt);
			closeConnection();
		}

		return produto;
	}

	@Override
	public List<Produto> buscaTodos() throws FalhaAcessoAosDadosException {
		
		System.out.println("Chamou buscaTodos() os produtos");
		
		List<Produto> produtos = new ArrayList<Produto>();

		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select id, nome, codigo, descricao from paw1_produto order by codigo, nome");

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setCodigo(rs.getString("codigo"));
				produto.setDescricao(rs.getString("descricao"));
				produtos.add(produto);
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

		System.out.println("Retornou " + produtos.size() + " produtos");
		
		return produtos;
	}

	@Override
	public List<Produto> buscaTodos(FiltroProduto filtro)
			throws FalhaAcessoAosDadosException {
		List<Produto> produtos = new ArrayList<Produto>();

		System.out.println("Buscando produtos com offset = "
				+ filtro.getPrimeiroRegistro() + " e limite = "
				+ filtro.getQuantidadeRegistros() + " e nome = "
				+ filtro.getNome() + " e código = " + filtro.getCodigo());

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			if (filtro.filtraPorNome() && filtro.filtraPorCodigo()) {
				pstmt = conn
						.prepareStatement("select id, nome, codigo, descricao from paw1_produto where nome like ? and codigo like ? order by codigo, nome limit ? offset ?");
				pstmt.setString(1, filtro.getNome() + "%");
				pstmt.setString(2, filtro.getCodigo() + "%");
				pstmt.setInt(3, filtro.getQuantidadeRegistros());
				pstmt.setInt(4, filtro.getPrimeiroRegistro());
			} else {
				if (filtro.filtraPorNome()) {
					pstmt = conn
							.prepareStatement("select id, nome, codigo, descricao from paw1_produto where nome like ? order by codigo, nome limit ? offset ?");
					pstmt.setString(1, filtro.getNome() + "%");
					pstmt.setInt(2, filtro.getQuantidadeRegistros());
					pstmt.setInt(3, filtro.getPrimeiroRegistro());
				} else {
					if (filtro.filtraPorCodigo()) {
						pstmt = conn
								.prepareStatement("select id, nome, codigo, descricao from paw1_produto where codigo like ? order by codigo, nome limit ? offset ?");
						pstmt.setString(1, filtro.getCodigo() + "%");
						pstmt.setInt(2, filtro.getQuantidadeRegistros());
						pstmt.setInt(3, filtro.getPrimeiroRegistro());
					} else {
						if (!filtro.filtraPorNome()
								&& !filtro.filtraPorCodigo()) {
							pstmt = conn
									.prepareStatement("select id, nome, codigo, descricao from paw1_produto order by codigo, nome limit ? offset ?");
							pstmt.setInt(1, filtro.getQuantidadeRegistros());
							pstmt.setInt(2, filtro.getPrimeiroRegistro());
						}
					}
				}
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setCodigo(rs.getString("codigo"));
				produto.setDescricao(rs.getString("descricao"));
				produtos.add(produto);
				System.out.println(produto);
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

		System.out.println("Busquei " + produtos.size());
		
		return produtos;
	}

	@Override
	public int insere(Produto produto) throws FalhaAcessoAosDadosException {

		int inseriu;

		if (produto != null) {

			PreparedStatement pstmt = null;

			try {

				pstmt = conn
						.prepareStatement("insert into paw1_produto(id, nome, codigo, descricao) values (nextval('paw1_produto_seq'), ?, ?, ?)");

				pstmt.setString(1, produto.getNome());
				pstmt.setString(2, produto.getCodigo());
				pstmt.setString(3, produto.getDescricao());

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
	public int altera(Produto produto) throws FalhaAcessoAosDadosException {

		System.out.println("Alterando " + produto);

		int alterou;

		if (produto != null) {

			PreparedStatement pstmt = null;

			try {

				pstmt = conn
						.prepareStatement("update paw1_produto set nome = ?, codigo = ?, descricao = ? where id = ?");

				pstmt.setString(1, produto.getNome());
				pstmt.setString(2, produto.getCodigo());
				pstmt.setString(3, produto.getDescricao());
				pstmt.setInt(4, produto.getId());

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
						.prepareStatement("delete from paw1_produto where id =  ?");
				pstmt.setInt(1, id);
				apagou = pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new ProdutoReferenciadoException(e);
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
	public int conta(FiltroProduto filtro) throws FalhaAcessoAosDadosException {

		System.out.println("Contando todos com filtro = " + filtro);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int retorno = 0;

		try {

			if (filtro.filtraPorNome() && filtro.filtraPorCodigo()) {
				pstmt = conn
						.prepareStatement("select count(*) from paw1_produto where nome like ? and codigo like ?");
				pstmt.setString(1, filtro.getNome() + "%");
				pstmt.setString(2, filtro.getCodigo() + "%");
			} else {
				if (filtro.filtraPorNome()) {
					pstmt = conn
							.prepareStatement("select count(*) from paw1_produto where nome like ?");
					pstmt.setString(1, filtro.getNome() + "%");
				} else {
					if (filtro.filtraPorCodigo()) {
						pstmt = conn
								.prepareStatement("select count(*) from paw1_produto where codigo like ?");
						pstmt.setString(1, filtro.getCodigo() + "%");
					} else {
						if (!filtro.filtraPorNome()
								&& !filtro.filtraPorCodigo()) {
							pstmt = conn
									.prepareStatement("select count(*) from paw1_produto");
						}
					}
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
		
		System.out.println("Contei " + retorno);

		return retorno;
	}

	public void closeConnection() {
		closeConnection(conn);
	}
}