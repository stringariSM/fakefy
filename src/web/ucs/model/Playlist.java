package web.ucs.model;

public class Playlist {
	
	private Integer id_playlist;
	private String  nome_playlist;
	
	public Playlist() {}

	public Playlist(Integer id_playlist, String nome_playlist) {
		super();
		this.id_playlist = id_playlist;
		this.nome_playlist = nome_playlist;		
	}

	public Integer getId() {
		return id_playlist;
	}

	public void setId(Integer id) {
		this.id_playlist = id;
	}

	public String getNomePlaylist() {
		return nome_playlist;
	}

	public void setNomePlaylist(String nome_playlist) {
		this.nome_playlist = nome_playlist;
	}

}
