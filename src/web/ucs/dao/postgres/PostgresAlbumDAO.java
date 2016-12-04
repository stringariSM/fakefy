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
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AbstractDAO;
import web.ucs.dao.intf.AlbumDAO;
import web.ucs.dao.intf.ArtistaDAO;
import web.ucs.model.Album;
import web.ucs.model.Artista;
import web.ucs.model.Faixa;


public class PostgresAlbumDAO extends AbstractDAO implements AlbumDAO {

	private Connection conn;

	PostgresAlbumDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Album> buscaAlbumPorId(Integer id)
			throws FalhaAcessoAosDadosException {

		if (id == null) {
			throw new AlbumNaoEncontradoException("Id nulo");
		}

		List<Album> albuns = new ArrayList<Album>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select ID_ALBUM,NOME_ALBUM,ANO,ID_ARTISTA_ALBUM,ID_ALBUM_FOTO, CAMINHO_FOTO_ALBUM from ALBUM where id_artista_album = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Album album = new Album();
				album.setId(rs.getInt("ID_ALBUM"));
				album.setNomeAlbum(rs.getString("NOME_ALBUM"));
				album.setCaminho(rs.getString("CAMINHO_FOTO_ALBUM"));
				
				albuns.add(album);
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(pstmt);
			closeConnection();
		}

		return albuns;
	}
	
	public List<Album> buscaTodos()
			throws FalhaAcessoAosDadosException {

		

		List<Album> albuns = new ArrayList<Album>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = conn
					.prepareStatement("select ID_ALBUM,NOME_ALBUM,ANO,ID_ARTISTA_ALBUM,ID_ALBUM_FOTO, CAMINHO_FOTO_ALBUM from ALBUM");
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Album album = new Album();
				album.setId(rs.getInt("ID_ALBUM"));
				album.setNomeAlbum(rs.getString("NOME_ALBUM"));
				album.setCaminho(rs.getString("CAMINHO_FOTO_ALBUM"));
				
				albuns.add(album);
			}

		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(
					"Falha buscando todos os registros", e);
		} finally {
			this.fechaResultSet(rs);
			this.fechaStatement(pstmt);
			closeConnection();
		}

		return albuns;
	}
	
	@Override
	public Integer insere(Album album) throws FalhaAcessoAosDadosException {

		Integer idInserido = null;

		if (album != null) {

			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;
			
			try {
				
				conn.setAutoCommit(false);

				pstmt = conn
						.prepareStatement("insert into album(ID_ALBUM,NOME_ALBUM,ANO,ID_ARTISTA_ALBUM,ID_ALBUM_FOTO,CAMINHO_FOTO_ALBUM) values (0, ?, ?,?,0,?)");    

				pstmt.setString(1, album.getNomeAlbum());
				pstmt.setInt(2, album.getAno());
				pstmt.setInt(3, album.getIdArtistaAlbum());
				pstmt.setString(4, album.getCaminhoFoto());				

				pstmt.executeUpdate();
											
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select ALBUM_SEQ.currval from dual");
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
	
 
	public void closeConnection() {
		closeConnection(conn);
	}
}