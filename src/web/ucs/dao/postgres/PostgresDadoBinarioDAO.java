package web.ucs.dao.postgres;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.ucs.dao.exceptions.AlunoNaoEncontradoException;
import web.ucs.dao.exceptions.DadoReferenciadoException;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AbstractDAO;
import web.ucs.dao.intf.DadoBinarioDAO;
import web.ucs.model.DadoBinario;
import web.ucs.model.Produto;

public class PostgresDadoBinarioDAO extends AbstractDAO implements
		DadoBinarioDAO {

	private Connection conn;

	PostgresDadoBinarioDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<DadoBinario> buscaArquivos(Produto produto)
			throws FalhaAcessoAosDadosException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DadoBinario> dados = new ArrayList<>();
		if (produto != null) {
			try {

				pstmt = conn
						.prepareStatement("select d.id, d.nome_arquivo, d.tipo, d.tamanho from paw1_dado_binario d, paw1_produto_dado_binario p where d.id = p.id_dado_binario and p.id_produto = ? order by id");
                pstmt.setInt(1, produto.getId());
				rs = pstmt.executeQuery();

				while (rs.next()) {
					DadoBinario dado = new DadoBinario();
					dado.setId(rs.getInt("id"));
					dado.setNome(rs.getString("nome_arquivo"));
					dado.setTipo(rs.getString("tipo"));
					dado.setTamanho(rs.getInt("tamanho"));
					dados.add(dado);
				}

			} catch (SQLException e) {
				throw new FalhaAcessoAosDadosException(
						"Falha buscando todos os registros", e);
			} finally {
				this.fechaResultSet(rs);
				this.fechaStatement(pstmt);
				closeConnection();
			}
		}

		return dados;
	}

	@Override
	public List<DadoBinario> buscaTodos() throws FalhaAcessoAosDadosException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DadoBinario> dados = new ArrayList<>();

		try {

			pstmt = conn
					.prepareStatement("select id, nome_arquivo, tipo, tamanho from paw1_dado_binario order by id");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				DadoBinario dado = new DadoBinario();
				dado.setId(rs.getInt("id"));
				dado.setNome(rs.getString("nome_arquivo"));
				dado.setTipo(rs.getString("tipo"));
				dado.setTamanho(rs.getInt("tamanho"));
				dados.add(dado);
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(pstmt);
			closeConnection();
		}

		return dados;
	}

	@Override
	public DadoBinario buscaPorId(Integer id)
			throws FalhaAcessoAosDadosException {

		if (id == null) {
			throw new AlunoNaoEncontradoException("Id nulo");
		}

		DadoBinario dado = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select id, nome_arquivo, tipo, conteudo, tamanho from paw1_dado_binario where id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dado = new DadoBinario();
				dado.setId(rs.getInt("id"));
				dado.setNome(rs.getString("nome_arquivo"));
				dado.setTipo(rs.getString("tipo"));
				dado.setConteudo(rs.getBytes("conteudo"));
				dado.setTamanho(rs.getInt("tamanho"));
			} else {
				throw new AlunoNaoEncontradoException(id.toString());
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(pstmt);
			closeConnection();
		}

		return dado;
	}

	@Override
	public int insere(DadoBinario dado) throws FalhaAcessoAosDadosException {

		int inseriu;

		if (dado != null) {

			PreparedStatement pstmt = null;

			try {

				InputStream is = new ByteArrayInputStream(dado.getConteudo());

				pstmt = conn
						.prepareStatement("insert into paw1_dado_binario(id, nome_arquivo, tipo, tamanho, conteudo) values (nextval('paw1_dado_binario_seq'), ?, ?, ?, ?)");

				pstmt.setString(1, dado.getNome());
				pstmt.setString(2, dado.getTipo());
				pstmt.setInt(3, dado.getTamanho());
				pstmt.setBinaryStream(4, is, dado.getTamanho());

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
	public int insere(DadoBinario dado, Produto produto) throws FalhaAcessoAosDadosException {
		
		System.out.println("Chamou insere");
		System.out.println(dado);
		System.out.println(produto);

		int inseriu;

		if (dado != null && produto != null) {

			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;

			try {

				int idInserido = -1;
				
				InputStream is = new ByteArrayInputStream(dado.getConteudo());

				conn.setAutoCommit(false);
				
				pstmt = conn
						.prepareStatement("insert into paw1_dado_binario(id, nome_arquivo, tipo, tamanho, conteudo) values (nextval('paw1_dado_binario_seq'), ?, ?, ?, ?)");

				pstmt.setString(1, dado.getNome());
				pstmt.setString(2, dado.getTipo());
				pstmt.setInt(3, dado.getTamanho());
				pstmt.setBinaryStream(4, is, dado.getTamanho());

				inseriu = pstmt.executeUpdate();
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select currval('paw1_dado_binario_seq')");
				if(rs.next()) {
					idInserido = rs.getInt(1);
				}
				
				this.fechaPreparedStatement(pstmt);
				
				pstmt = conn
						.prepareStatement("insert into paw1_produto_dado_binario(id, id_produto, id_dado_binario) values (nextval('paw1_produto_dado_binario_seq'), ?, ?)");
				pstmt.setInt(1, produto.getId());
				pstmt.setInt(2, idInserido);
				
				pstmt.executeUpdate();
				
				conn.commit();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new FalhaAcessoAosDadosException(
						"Falha ao inserir registro", e);
			} finally {
				this.fechaResultSet(rs);
				this.fechaStatement(stmt);
				this.fechaPreparedStatement(pstmt);
				closeConnection();
			}
		} else {
			throw new FalhaAcessoAosDadosException("Registro nulo");
		}
		return inseriu;
	}

	
	@Override
	public int remove(Integer id) throws FalhaAcessoAosDadosException {

		int apagou = 0;

		if (id != null) {

			PreparedStatement pstmt = null;

			try {
				pstmt = conn
						.prepareStatement("delete from paw1_dado_binario where id =  ?");
				pstmt.setInt(1, id);
				apagou = pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new DadoReferenciadoException(e);
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
	public int remove(Integer idDado, Integer idProduto) throws FalhaAcessoAosDadosException {

		int apagou = 0;

		if (idDado != null && idProduto != null) {

			PreparedStatement pstmt = null;

			try {
				
				
				conn.setAutoCommit(false);
				
				pstmt = conn
						.prepareStatement("delete from paw1_produto_dado_binario where id_produto =  ? and id_dado_binario = ?");
				pstmt.setInt(1, idProduto);
				pstmt.setInt(2, idDado);
				
				pstmt.executeUpdate();
				
				pstmt.close();
				
				pstmt = conn
						.prepareStatement("delete from paw1_dado_binario where id =  ?");
				pstmt.setInt(1, idDado);
				
				apagou = pstmt.executeUpdate();
				
				conn.commit();
				conn.setAutoCommit(true);
				
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

	
	public void closeConnection() {
		closeConnection(conn);
	}
}