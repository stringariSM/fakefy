package web.ucs.model;

public class Album {
	
	private Integer id_album;
	private String  nome_album;
	private Integer ano;
	private Integer id_artista_album;	
	
	public Album() {}

	public Album(Integer id_album, String nome_album, Integer ano, Integer id_artista_album) {
		super();
		this.id_album = id_album;
		this.nome_album = nome_album;
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
		return nome_album;
	}

	public void setNomeAlbum(String nome_album) {
		this.nome_album = nome_album;
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
			  

}
