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
import web.ucs.dao.intf.PlaylistDAO;
import web.ucs.model.Artista;
import web.ucs.model.Playlist;


public class PostgresPlaylistDAO extends AbstractDAO implements PlaylistDAO {

	private Connection conn;

	PostgresPlaylistDAO(Connection conn) {
		this.conn = conn;
	}

	
	@Override
	public List<Playlist> buscaTodos() throws FalhaAcessoAosDadosException {
		List<Playlist> playlists = new ArrayList<Playlist>();

		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select ID_PLAYLIST,NOME_PLAYLIST from PLAYLIST");

			while (rs.next()) {
				Playlist playlist = new Playlist();
				playlist.setId(rs.getInt("ID_PLAYLIST"));
				playlist.setNomePlaylist(rs.getString("NOME_PLAYLIST"));				
				playlists.add(playlist);
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
		
		return playlists;
	}
   
	public void closeConnection() {
		closeConnection(conn);
	}
}