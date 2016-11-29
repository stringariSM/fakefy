package web.ucs.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.ucs.dao.exceptions.AlbumNaoEncontradoException;
import web.ucs.dao.exceptions.ArtistaNaoEncontradoException;
import web.ucs.dao.exceptions.FaixaNaoEncontradoException;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AbstractDAO;
import web.ucs.dao.intf.AlbumDAO;
import web.ucs.dao.intf.ArtistaDAO;
import web.ucs.dao.intf.FaixaDAO;
import web.ucs.model.Album;
import web.ucs.model.Artista;
import web.ucs.model.Faixa;


public class PostgresFaixaDAO extends AbstractDAO implements FaixaDAO {

	private Connection conn;

	PostgresFaixaDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Faixa> buscaFaixaPorId(Integer id)
			throws FalhaAcessoAosDadosException {

		if (id == null) {
			throw new FaixaNaoEncontradoException("Id nulo");
		}

		List<Faixa> faixas = new ArrayList<Faixa>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select ID_FAIXA,NOME_FAIXA,CAMINHO,ID_FAIXA_ALBUM,ORDEM,(select nome_artista from artista, album where id_artista = id_artista_album and id_faixa_album = id_album) NOME_ARTISTA,(select nome_ALBUM from album where id_faixa_album = id_album) NOME_ALBUM from FAIXA where id_faixa_album = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Faixa faixa = new Faixa();
				faixa.setId(rs.getInt("ID_FAIXA"));
				faixa.setNomeFaixa(rs.getString("NOME_FAIXA"));
				faixa.setCaminho(rs.getString("CAMINHO"));
				faixa.setIdFaixaAlbum(rs.getInt("ID_FAIXA_ALBUM"));
				faixa.setOrdem(rs.getInt("ORDEM"));
				faixa.setArtista(rs.getString("NOME_ARTISTA"));
				faixa.setAlbum(rs.getString("NOME_ALBUM"));
				 			
				faixas.add(faixa);
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(pstmt);
			closeConnection();
		}

		return faixas;
	}
	
	/*---*/
	
	@Override
	public List<Faixa> buscaFaixaPorIdPlaylist(Integer id)
			throws FalhaAcessoAosDadosException {

		if (id == null) {
			throw new FaixaNaoEncontradoException("Id nulo");
		}

		List<Faixa> faixas = new ArrayList<Faixa>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select ID_FAIXA,NOME_FAIXA,CAMINHO,ID_FAIXA_ALBUM,ORDEM,(select nome_artista from artista, album where id_artista = id_artista_album and id_faixa_album = id_album) NOME_ARTISTA,(select nome_ALBUM from album where id_faixa_album = id_album) NOME_ALBUM from FAIXA, FAIXA_PLAYLIST  where id_faixa = ID_FAIXA_PLAYLIST  AND ID_PLAYLIST_FAIXA = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Faixa faixa = new Faixa();
				faixa.setId(rs.getInt("ID_FAIXA"));
				faixa.setNomeFaixa(rs.getString("NOME_FAIXA"));
				faixa.setCaminho(rs.getString("CAMINHO"));
				faixa.setIdFaixaAlbum(rs.getInt("ID_FAIXA_ALBUM"));
				faixa.setOrdem(rs.getInt("ORDEM"));
				faixa.setArtista(rs.getString("NOME_ARTISTA"));
				faixa.setAlbum(rs.getString("NOME_ALBUM"));
				 			
				faixas.add(faixa);
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(pstmt);
			closeConnection();
		}

		return faixas;
	}
	
	
 
	public void closeConnection() {
		closeConnection(conn);
	}
}