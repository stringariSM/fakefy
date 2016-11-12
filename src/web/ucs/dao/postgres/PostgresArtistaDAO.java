package web.ucs.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import web.ucs.dao.exceptions.ArtistaNaoEncontradoException;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AbstractDAO;
import web.ucs.dao.intf.ArtistaDAO;
import web.ucs.model.Artista;


public class PostgresArtistaDAO extends AbstractDAO implements ArtistaDAO {

	private Connection conn;

	PostgresArtistaDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Artista buscaArtistaPorId(Integer id)
			throws FalhaAcessoAosDadosException {

		if (id == null) {
			throw new ArtistaNaoEncontradoException("Id nulo");
		}

		Artista artista = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select ID_ARTISTA,NOME_ARTISTA,ANO_NASCIMENTO_FORMACAO,ID_ARTISTA_PAIS,ID_ARTISTA_FOTO from ARTISTA where id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				artista = new Artista();
				artista.setId(rs.getInt("ID_ARTISTA"));
				artista.setNomeArtista(rs.getString("NOME_ARTISTA"));
				artista.setAnoNascimentoFormacao(rs.getInt("ANO_NASCIMENTO_FORMACAO"));
				artista.setIdArtistaPais(rs.getInt("ID_ARTISTA_PAIS"));
				artista.setIdArtistaFoto(rs.getInt("ID_ARTISTA_FOTO"));				
				
			} else {
				throw new ArtistaNaoEncontradoException(id.toString());
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(pstmt);
			closeConnection();
		}

		return artista;
	}

	@Override
	public List<Artista> buscaTodos() throws FalhaAcessoAosDadosException {
		List<Artista> artistas = new ArrayList<Artista>();

		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select ID_ARTISTA,NOME_ARTISTA,ANO_NASCIMENTO_FORMACAO,ID_ARTISTA_PAIS,ID_ARTISTA_FOTO from ARTISTA");

			while (rs.next()) {
				Artista artista = new Artista();
				artista.setId(rs.getInt("ID_ARTISTA"));
				artista.setNomeArtista(rs.getString("NOME_ARTISTA"));
				artista.setAnoNascimentoFormacao(rs.getInt("ANO_NASCIMENTO_FORMACAO"));
				artista.setIdArtistaPais(rs.getInt("ID_ARTISTA_PAIS"));
				artista.setIdArtistaFoto(rs.getInt("ID_ARTISTA_FOTO"));
				artistas.add(artista);
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
		
		return artistas;
	}
   
	public void closeConnection() {
		closeConnection(conn);
	}
}