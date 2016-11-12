package web.ucs.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.ucs.dao.FiltroAluno;
import web.ucs.dao.FiltroCurso;
import web.ucs.dao.exceptions.AlunoNaoEncontradoException;
import web.ucs.dao.exceptions.CursoNaoEncontradoException;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AbstractDAO;
import web.ucs.dao.intf.CursoDAO;
import web.ucs.model.Aluno;
import web.ucs.model.Curso;

public class PostgresCursoDAO extends AbstractDAO implements CursoDAO {

	private Connection conn;

	PostgresCursoDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Curso buscaPorId(Integer id) throws FalhaAcessoAosDadosException {

		if (id == null) {
			throw new AlunoNaoEncontradoException("Id nulo");
		}

		Curso curso = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select id, nome, codigo from paw1_curso where id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				curso = new Curso();
				curso.setId(rs.getInt("id"));
				curso.setNome(rs.getString("nome"));
				curso.setCodigo(rs.getString("codigo"));
			} else {
				throw new CursoNaoEncontradoException(id.toString());
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(pstmt);
			closeConnection();
		}

		return curso;
	}

	@Override
	public List<Curso> buscaTodos() throws FalhaAcessoAosDadosException {
		List<Curso> cursos = new ArrayList<Curso>();

		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select id, nome, codigo from paw1_curso");

			while (rs.next()) {
				Curso curso = new Curso();
				curso.setId(rs.getInt("id"));
				curso.setNome(rs.getString("nome"));
				curso.setCodigo(rs.getString("codigo"));
				cursos.add(curso);
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

		return cursos;
	}

	@Override
	public List<Curso> buscaTodos(FiltroCurso filtro)
			throws FalhaAcessoAosDadosException {
		List<Curso> cursos = new ArrayList<Curso>();

		System.out.println("Buscando cursos com offset "
				+ filtro.getPrimeiroRegistro() + " e limite "
				+ filtro.getQuantidadeRegistros() + " e nome"
				+ filtro.getNome());

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select id, nome, codigo from paw1_curso where nome like ? and codigo like ? limit ? offset ?");
			if (filtro.filtraPorNome()) {
				pstmt.setString(1, "%" + filtro.getNome() + "%");
			} else {
				pstmt.setString(1, "%");
			}
			if (filtro.filtraPorCodigo()) {
				pstmt.setString(2, "%" + filtro.getCodigo() + "%");
			} else {
				pstmt.setString(2, "%");
			}

			pstmt.setInt(3, filtro.getQuantidadeRegistros());
			pstmt.setInt(4, filtro.getPrimeiroRegistro());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Curso curso = new Curso();
				curso.setId(rs.getInt("id"));
				curso.setNome(rs.getString("nome"));
				curso.setCodigo(rs.getString("codigo"));
				cursos.add(curso);
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

		return cursos;
	}

	@Override
	public int insere(Curso curso) throws FalhaAcessoAosDadosException {

		int inseriu;

		if (curso != null) {

			PreparedStatement pstmt = null;

			try {

				pstmt = conn
						.prepareStatement("insert into paw1_curso(id, nome, codigo) values (nextval('paw1_curso_seq'), ?, ?)");

				pstmt.setString(1, curso.getNome());
				pstmt.setString(2, curso.getCodigo());

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
	public int altera(Curso curso) throws FalhaAcessoAosDadosException {
		
		System.out.println("Alterando " + curso);
		
		int alterou;

		if (curso != null) {

			PreparedStatement pstmt = null;

			try {

				pstmt = conn
						.prepareStatement("update paw1_curso set nome = ?, codigo = ? where id = ?");

				pstmt.setString(1, curso.getNome());
				pstmt.setString(2, curso.getCodigo());
				pstmt.setInt(3, curso.getId());

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
						.prepareStatement("delete from paw1_curso where id =  ?");
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
	public int conta(FiltroCurso filtro) throws FalhaAcessoAosDadosException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int retorno = 0;

		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select count(*) from paw1_curso\n");
			if (filtro.filtraPorNome() || filtro.filtraPorCodigo()) {
				sb.append("where\n");

				if (filtro.filtraPorNome()) {
					sb.append("nome like ?\n");
				}

				if (filtro.filtraPorNome() && filtro.filtraPorCodigo()) {
					sb.append("and\n");
				}

				if (filtro.filtraPorCodigo()) {
					sb.append("codigo like ?\n");
				}
			}
						
			pstmt = conn.prepareStatement(sb.toString());

			if (filtro.filtraPorNome() || filtro.filtraPorCodigo()) {

				if (filtro.filtraPorNome()) {
					pstmt.setString(1, "%" + filtro.getNome() + "%");
				}

				if (filtro.filtraPorNome() && filtro.filtraPorCodigo()) {
					pstmt.setString(2, "%" + filtro.getCodigo() + "%");
				}

				if (filtro.filtraPorCodigo() && !filtro.filtraPorNome()) {
					pstmt.setString(1, "%" + filtro.getCodigo() + "%");
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

	public void closeConnection() {
		closeConnection(conn);
	}
}