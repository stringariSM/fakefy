package web.ucs.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.ucs.dao.FiltroAluno;
import web.ucs.dao.exceptions.AlunoNaoEncontradoException;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AbstractDAO;
import web.ucs.dao.intf.AlunoDAO;
import web.ucs.dao.intf.CursoDAO;
import web.ucs.model.Aluno;
import web.ucs.model.Curso;

public class PostgresAlunoDAO extends AbstractDAO implements AlunoDAO {

	private Connection conn;

	PostgresAlunoDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Aluno buscaAlunoPorId(Integer id)
			throws FalhaAcessoAosDadosException {

		if (id == null) {
			throw new AlunoNaoEncontradoException("Id nulo");
		}

		Aluno aluno = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select id, nome, matricula, curso, curso_id from paw1_aluno where id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				aluno = new Aluno();
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setMatricula(rs.getString("matricula"));
				aluno.setNomeCurso(rs.getString("curso"));
				Integer cursoId = rs.getInt("curso_id");
				if(!rs.wasNull()) {
					CursoDAO dao = this.getFactory().getCursoDAO();
					Curso curso = dao.buscaPorId(cursoId);
					aluno.setCurso(curso);
				}
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

		return aluno;
	}

	@Override
	public List<Aluno> buscaTodos() throws FalhaAcessoAosDadosException {
		List<Aluno> alunos = new ArrayList<Aluno>();

		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select id, nome, matricula, curso, curso_id from paw1_aluno");

			while (rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setMatricula(rs.getString("matricula"));
				aluno.setNomeCurso(rs.getString("curso"));
				Integer cursoId = rs.getInt("curso_id");
				if(!rs.wasNull()) {
					CursoDAO dao = this.getFactory().getCursoDAO();
					Curso curso = dao.buscaPorId(cursoId);
					aluno.setCurso(curso);
				}
				alunos.add(aluno);
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
		
		return alunos;
	}

	@Override
	public List<Aluno> buscaTodos(FiltroAluno filtro)
			throws FalhaAcessoAosDadosException {
		List<Aluno> alunos = new ArrayList<Aluno>();

		System.out.println("Buscando alunos com offset "
				+ filtro.getPrimeiroRegistro() + " e limite "
				+ filtro.getQuantidadeRegistros() + " e nome" +
				filtro.getNome());

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select id, nome, matricula, curso, curso_id from paw1_aluno where nome like ? limit ? offset ?");
			if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
				pstmt.setString(1, "%" + filtro.getNome() + "%");
			} else {
				pstmt.setString(1, "%");
			}
			pstmt.setInt(2, filtro.getQuantidadeRegistros());
			pstmt.setInt(3, filtro.getPrimeiroRegistro());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setMatricula(rs.getString("matricula"));
				aluno.setNomeCurso(rs.getString("curso"));
				Curso curso = new Curso(rs.getInt("curso_id"));
				aluno.setCurso(curso);
				alunos.add(aluno);
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

		return alunos;
	}

	@Override
	public int insere(Aluno aluno) throws FalhaAcessoAosDadosException {

		int inseriu;

		if (aluno != null) {

			PreparedStatement pstmt = null;

			try {

				pstmt = conn
						.prepareStatement("insert into paw1_aluno(id, nome, matricula, curso, curso_id) values (nextval('paw1_aluno_seq'), ?, ?, ?, ?)");

				pstmt.setString(1, aluno.getNome());
				pstmt.setString(2, aluno.getMatricula());
				pstmt.setString(3, aluno.getNomeCurso());
				pstmt.setInt(4, aluno.getCurso()!=null?aluno.getCurso().getId():null);

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
	public int altera(Aluno aluno) throws FalhaAcessoAosDadosException {
		int alterou;

		if (aluno != null) {

			PreparedStatement pstmt = null;

			try {

				pstmt = conn
						.prepareStatement("update paw1_aluno set nome = ?, matricula = ?, curso = ? , curso_id=? where id = ?");

				pstmt.setString(1, aluno.getNome());
				pstmt.setString(2, aluno.getMatricula());
				pstmt.setString(3, aluno.getNomeCurso());
				pstmt.setInt(4, aluno.getCurso()!=null?aluno.getCurso().getId():null);
				pstmt.setInt(5, aluno.getId());

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
						.prepareStatement("delete from paw1_aluno where id =  ?");
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
	public int conta(FiltroAluno filtro) throws FalhaAcessoAosDadosException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int retorno = 0;

		try {

			if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
				pstmt = conn
						.prepareStatement("select count(*) from paw1_aluno where nome like ?");
				pstmt.setString(1, "%" + filtro.getNome() + "%");
			} else {
				pstmt = conn
						.prepareStatement("select count(*) from paw1_aluno");
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