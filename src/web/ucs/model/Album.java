package web.ucs.model;

public class Album {
	
	private Integer id_album;
	private String  nomeAlbum;
	private Integer ano;
	private Integer id_artista_album;	
	private String caminhofoto;
	
	private Artista artistas;
	
	public Album() {}

	public Album(Integer id_album, String nome_album, Integer ano, Integer id_artista_album) {
		super();
		this.id_album = id_album;
		this.nomeAlbum = nome_album;
		this.ano = ano;
		this.id_artista_album = id_artista_album;		
	}

	public Integer getId() {
		return id_album;
	}

	public void setId(Integer id) {
		this.id_album = id;
	}

	public String getNomeAlbum() {
		return nomeAlbum;
	}

	public void setNomeAlbum(String nome_album) {
		this.nomeAlbum = nome_album;
	}
	
	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public Integer getIdArtistaAlbum() {
		return id_artista_album;
	}

	public void setIdArtistaPais(Integer id_artista_album) {
		this.id_artista_album = id_artista_album;
	}
	
	public String getCaminhoFoto() {
		return caminhofoto;
	}

	public void setCaminhoFoto(String caminho_foto) {
		this.caminhofoto = caminho_foto;
	}
	
	public Artista getArtistas() {
		return artistas;
	}

	public void setArtistas(Artista artistas) {
		this.artistas = artistas;
		
		this.id_artista_album = artistas.getId();
		
	}
			  

}
